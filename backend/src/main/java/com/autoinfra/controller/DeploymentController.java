package com.autoinfra.controller;

import com.autoinfra.service.AWSDeploymentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class DeploymentController {

    private Map<String, DeploymentStatus> deployments = new HashMap<>();
    private AWSDeploymentService awsService = new AWSDeploymentService();

    @PostMapping("/deploy")
    public ResponseEntity<Map<String, Object>> deploy(@RequestBody DeploymentRequest request) {
        String deploymentId = UUID.randomUUID().toString();
        
        DeploymentStatus status = new DeploymentStatus();
        status.setDeploymentId(deploymentId);
        status.setGithubUrl(request.getGithubUrl());
        status.setStatus("IN_PROGRESS");
        status.setStartTime(LocalDateTime.now());
        status.setMessage("Analyzing repository...");
        
        deployments.put(deploymentId, status);
        
        // Simulate deployment process
        simulateDeployment(deploymentId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("deploymentId", deploymentId);
        response.put("status", "STARTED");
        response.put("message", "Deployment initiated successfully");
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/deployment/{id}/status")
    public ResponseEntity<DeploymentStatus> getDeploymentStatus(@PathVariable String id) {
        DeploymentStatus status = deployments.get(id);
        if (status == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(status);
    }

    @GetMapping("/deployments")
    public ResponseEntity<List<DeploymentStatus>> getAllDeployments() {
        return ResponseEntity.ok(new ArrayList<>(deployments.values()));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
            "status", "OK",
            "service", "AutoInfra Backend",
            "timestamp", LocalDateTime.now().toString()
        ));
    }

    private void simulateDeployment(String deploymentId) {
        new Thread(() -> {
            try {
                DeploymentStatus status = deployments.get(deploymentId);
                
                // Step 1: Create S3 bucket
                Thread.sleep(2000);
                status.setMessage("Creating S3 bucket and security groups...");
                String bucketName = "autoinfra-app-" + deploymentId.substring(0, 8).toLowerCase();
                
                // Step 2: Launch EC2 instance
                Thread.sleep(3000);
                status.setMessage("Launching EC2 instance...");
                
                // Step 3: Clone and build project
                Thread.sleep(5000);
                status.setMessage("Cloning repository and building project...");
                
                // Step 4: Deploy application
                Thread.sleep(4000);
                status.setMessage("Deploying application to server...");
                
                // Step 5: Complete
                Thread.sleep(2000);
                status.setMessage("Finalizing deployment...");
                
                // Actually deploy to AWS
                deployToAWS(bucketName, status);
                
                if (!status.getStatus().equals("FAILED")) {
                    status.setStatus("COMPLETED");
                    status.setMessage("🚀 Deployment completed! Your app is live!");
                    status.setEndTime(LocalDateTime.now());
                }
                
            } catch (Exception e) {
                DeploymentStatus status = deployments.get(deploymentId);
                status.setStatus("FAILED");
                status.setMessage("Deployment failed: " + e.getMessage());
            }
        }).start();
    }
    
    private void deployToAWS(String bucketName, DeploymentStatus status) {
        try {
            String url = awsService.deployStaticWebsite(bucketName, status.getGithubUrl());
            status.setApplicationUrl(url);
            System.out.println("✅ Successfully deployed to: " + url);
            System.out.println("🔗 User can access their project at: " + url);
        } catch (Exception e) {
            status.setStatus("FAILED");
            status.setMessage("❌ Deployment failed: " + e.getMessage());
            System.err.println("AWS deployment error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

class DeploymentRequest {
    private String githubUrl;
    private String userId;

    // Getters and setters
    public String getGithubUrl() { return githubUrl; }
    public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}

class DeploymentStatus {
    private String deploymentId;
    private String githubUrl;
    private String status;
    private String message;
    private String applicationUrl;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // Getters and setters
    public String getDeploymentId() { return deploymentId; }
    public void setDeploymentId(String deploymentId) { this.deploymentId = deploymentId; }
    public String getGithubUrl() { return githubUrl; }
    public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getApplicationUrl() { return applicationUrl; }
    public void setApplicationUrl(String applicationUrl) { this.applicationUrl = applicationUrl; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
}
