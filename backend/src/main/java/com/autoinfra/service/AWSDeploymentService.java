package com.autoinfra.service;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;
import software.amazon.awssdk.core.sync.RequestBody;
import java.util.Base64;

public class AWSDeploymentService {
    
    private final S3Client s3Client;
    private final Ec2Client ec2Client;
    
    public AWSDeploymentService() {
        this.s3Client = S3Client.builder()
            .region(Region.AP_SOUTH_1)
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build();
        this.ec2Client = Ec2Client.builder()
            .region(Region.AP_SOUTH_1)
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build();
    }
    
    public String deployStaticWebsite(String bucketName, String githubUrl) throws Exception {
        // Create S3 bucket
        createBucket(bucketName);
        enableWebsiteHosting(bucketName);
        uploadSampleHTML(bucketName, githubUrl);
        setBucketPolicy(bucketName);
        
        // Create EC2 instance
        String instanceId = createEC2Instance(bucketName, githubUrl);
        String publicIp = getInstancePublicIp(instanceId);
        
        // Return EC2 URL instead of S3
        return "http://" + publicIp;
    }
    
    private String createEC2Instance(String name, String githubUrl) throws Exception {
        // Get latest Amazon Linux AMI
        DescribeImagesRequest imageRequest = DescribeImagesRequest.builder()
            .owners("amazon")
            .filters(
                Filter.builder().name("name").values("al2023-ami-*-x86_64").build(),
                Filter.builder().name("state").values("available").build()
            )
            .build();
        
        DescribeImagesResponse imageResponse = ec2Client.describeImages(imageRequest);
        String amiId = imageResponse.images().get(0).imageId();
        
        // User data script - clone and deploy actual project
        String userData = "#!/bin/bash\n" +
            "yum update -y\n" +
            "yum install -y httpd git nodejs npm java-17-amazon-corretto maven\n" +
            "systemctl start httpd\n" +
            "systemctl enable httpd\n" +
            "# Clear default Apache content\n" +
            "rm -rf /var/www/html/*\n" +
            "cd /tmp\n" +
            "git clone " + githubUrl + " project\n" +
            "cd project\n" +
            "# Detect and deploy project type\n" +
            "if [ -f package.json ]; then\n" +
            "  # React/Node.js project\n" +
            "  npm install\n" +
            "  if npm run build; then\n" +
            "    # Copy build output to Apache\n" +
            "    cp -r build/* /var/www/html/ 2>/dev/null || cp -r dist/* /var/www/html/ 2>/dev/null\n" +
            "  else\n" +
            "    # If build fails, copy source files\n" +
            "    cp -r * /var/www/html/\n" +
            "  fi\n" +
            "elif [ -f pom.xml ]; then\n" +
            "  # Java Spring Boot project\n" +
            "  mvn clean package -DskipTests\n" +
            "  # Stop Apache and run Spring Boot on port 80\n" +
            "  systemctl stop httpd\n" +
            "  nohup java -jar target/*.jar --server.port=80 > /var/log/app.log 2>&1 &\n" +
            "elif [ -f index.html ] || [ -f index.htm ]; then\n" +
            "  # Static HTML project\n" +
            "  cp -r * /var/www/html/\n" +
            "else\n" +
            "  # Default: copy all files\n" +
            "  cp -r * /var/www/html/\n" +
            "fi\n" +
            "# Ensure Apache is running for static content\n" +
            "systemctl restart httpd\n" +
            "# Set proper permissions\n" +
            "chown -R apache:apache /var/www/html\n" +
            "chmod -R 755 /var/www/html";
        
        String encodedUserData = Base64.getEncoder().encodeToString(userData.getBytes());
        
        // Create security group
        String sgId = createSecurityGroup(name);
        
        // Launch instance
        RunInstancesRequest runRequest = RunInstancesRequest.builder()
            .imageId(amiId)
            .instanceType(InstanceType.T2_MICRO)
            .maxCount(1)
            .minCount(1)
            .securityGroupIds(sgId)
            .userData(encodedUserData)
            .tagSpecifications(
                TagSpecification.builder()
                    .resourceType(ResourceType.INSTANCE)
                    .tags(
                        software.amazon.awssdk.services.ec2.model.Tag.builder().key("Name").value(name).build(),
                        software.amazon.awssdk.services.ec2.model.Tag.builder().key("ManagedBy").value("AutoInfra").build()
                    )
                    .build()
            )
            .build();
        
        RunInstancesResponse response = ec2Client.runInstances(runRequest);
        return response.instances().get(0).instanceId();
    }
    
    private String createSecurityGroup(String name) {
        try {
            CreateSecurityGroupRequest sgRequest = CreateSecurityGroupRequest.builder()
                .groupName(name + "-sg")
                .description("Security group for " + name)
                .build();
            
            CreateSecurityGroupResponse sgResponse = ec2Client.createSecurityGroup(sgRequest);
            String sgId = sgResponse.groupId();
            
            // Allow HTTP
            AuthorizeSecurityGroupIngressRequest httpRule = AuthorizeSecurityGroupIngressRequest.builder()
                .groupId(sgId)
                .ipPermissions(
                    IpPermission.builder()
                        .ipProtocol("tcp")
                        .fromPort(80)
                        .toPort(80)
                        .ipRanges(IpRange.builder().cidrIp("0.0.0.0/0").build())
                        .build()
                )
                .build();
            
            ec2Client.authorizeSecurityGroupIngress(httpRule);
            return sgId;
        } catch (Exception e) {
            // Security group might already exist, find it
            DescribeSecurityGroupsRequest describeRequest = DescribeSecurityGroupsRequest.builder()
                .groupNames(name + "-sg")
                .build();
            DescribeSecurityGroupsResponse describeResponse = ec2Client.describeSecurityGroups(describeRequest);
            return describeResponse.securityGroups().get(0).groupId();
        }
    }
    
    private String getInstancePublicIp(String instanceId) throws Exception {
        // Wait for instance to get public IP and deploy (up to 5 minutes)
        for (int i = 0; i < 150; i++) {
            DescribeInstancesRequest request = DescribeInstancesRequest.builder()
                .instanceIds(instanceId)
                .build();
            
            DescribeInstancesResponse response = ec2Client.describeInstances(request);
            Instance instance = response.reservations().get(0).instances().get(0);
            
            if (instance.publicIpAddress() != null) {
                return instance.publicIpAddress();
            }
            
            Thread.sleep(2000);
        }
        throw new Exception("Timeout waiting for public IP");
    }
    
    private void createBucket(String bucketName) throws Exception {
        CreateBucketRequest request = CreateBucketRequest.builder()
            .bucket(bucketName)
            .createBucketConfiguration(
                CreateBucketConfiguration.builder()
                    .locationConstraint(BucketLocationConstraint.AP_SOUTH_1)
                    .build()
            )
            .build();
        s3Client.createBucket(request);
    }
    
    private void enableWebsiteHosting(String bucketName) {
        WebsiteConfiguration config = WebsiteConfiguration.builder()
            .indexDocument(IndexDocument.builder().suffix("index.html").build())
            .build();
            
        PutBucketWebsiteRequest request = PutBucketWebsiteRequest.builder()
            .bucket(bucketName)
            .websiteConfiguration(config)
            .build();
            
        s3Client.putBucketWebsite(request);
    }
    
    private void uploadSampleHTML(String bucketName, String githubUrl) {
        String html = "<!DOCTYPE html><html><head><title>Deployed by AutoInfra</title></head>" +
            "<body style='font-family:Arial;text-align:center;padding:50px'>" +
            "<h1>🚀 Deployment Successful!</h1>" +
            "<p>Your application has been deployed by AutoInfra</p>" +
            "<p>Source: <a href='" + githubUrl + "'>" + githubUrl + "</a></p>" +
            "</body></html>";
            
        PutObjectRequest request = PutObjectRequest.builder()
            .bucket(bucketName)
            .key("index.html")
            .contentType("text/html")
            .build();
            
        s3Client.putObject(request, RequestBody.fromString(html));
    }
    
    private void setBucketPolicy(String bucketName) {
        String policy = "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Sid\":\"PublicReadGetObject\"," +
            "\"Effect\":\"Allow\",\"Principal\":\"*\",\"Action\":\"s3:GetObject\"," +
            "\"Resource\":\"arn:aws:s3:::" + bucketName + "/*\"}]}";
            
        PublicAccessBlockConfiguration publicAccessConfig = PublicAccessBlockConfiguration.builder()
            .blockPublicAcls(false)
            .ignorePublicAcls(false)
            .blockPublicPolicy(false)
            .restrictPublicBuckets(false)
            .build();
            
        PutPublicAccessBlockRequest publicAccessRequest = PutPublicAccessBlockRequest.builder()
            .bucket(bucketName)
            .publicAccessBlockConfiguration(publicAccessConfig)
            .build();
            
        s3Client.putPublicAccessBlock(publicAccessRequest);
        
        PutBucketPolicyRequest request = PutBucketPolicyRequest.builder()
            .bucket(bucketName)
            .policy(policy)
            .build();
            
        s3Client.putBucketPolicy(request);
    }
}
