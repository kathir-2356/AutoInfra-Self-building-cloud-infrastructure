# AWS Deployment Guide

## Prerequisites

1. **AWS Account** - You need an active AWS account
2. **AWS CLI Configured** - Run `aws configure` with your credentials
3. **Terraform Installed** - Version 1.5+

## Step-by-Step Deployment

### 1. Configure AWS Credentials

```bash
aws configure
```

Enter:
- AWS Access Key ID: `YOUR_ACCESS_KEY`
- AWS Secret Access Key: `YOUR_SECRET_KEY`
- Default region: `us-east-1`
- Default output format: `json`

### 2. Verify AWS Connection

```bash
aws sts get-caller-identity
```

You should see your AWS account ID and user ARN.

### 3. Deploy to AWS

```bash
deploy-aws.bat
```

This will:
- ✅ Check AWS credentials
- ✅ Initialize Terraform
- ✅ Plan infrastructure
- ✅ Deploy to AWS (with confirmation)

### 4. View Deployed Resources

After deployment, you'll see:
- EC2 instance public IP
- EC2 web server URL
- S3 bucket name

## What Gets Deployed

- **VPC** with public subnet
- **EC2 Instance** (t2.micro) running Apache web server
- **S3 Bucket** with encryption and versioning
- **Security Groups** for web access
- **Internet Gateway** for public access

## Costs

Estimated monthly cost: **~$10-15**
- EC2 t2.micro: ~$8.50/month
- S3 storage: ~$0.50/month
- Data transfer: ~$1/month

## Troubleshooting

### Error: "AWS not configured"
**Solution:** Run `aws configure` and enter your credentials

### Error: "Terraform init failed"
**Solution:** 
```bash
cd terraform
terraform init -upgrade
```

### Error: "Insufficient permissions"
**Solution:** Your AWS user needs these permissions:
- EC2 full access
- S3 full access
- VPC full access

### Error: "Resource already exists"
**Solution:** 
```bash
cd terraform
terraform destroy -auto-approve
terraform apply -auto-approve
```

## Accessing Your Deployment

After deployment completes, access your web server:

```
http://YOUR_EC2_PUBLIC_IP
```

The IP address will be shown in the terraform output.

## Cleanup

To remove all AWS resources:

```bash
cd terraform
terraform destroy -auto-approve
```

This will delete all created resources and stop billing.

## Manual Deployment

If the script doesn't work, deploy manually:

```bash
cd terraform
terraform init
terraform plan
terraform apply
```

## Getting Help

1. Check AWS credentials: `aws sts get-caller-identity`
2. Check Terraform version: `terraform version`
3. View Terraform logs: `terraform apply` (without -auto-approve)
4. Check AWS Console for created resources
