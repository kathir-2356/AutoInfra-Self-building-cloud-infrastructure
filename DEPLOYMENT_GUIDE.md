# 🚀 AutoInfra - Complete Deployment Guide

## ✅ What's Fixed

### Project Deployment
- ✅ Clears Apache default content
- ✅ Properly builds React/Node.js projects (`npm run build`)
- ✅ Copies build output to `/var/www/html/`
- ✅ Runs Spring Boot apps on port 80
- ✅ Serves static HTML projects correctly

### Real AWS Deployment
- ✅ Creates actual EC2 instance
- ✅ Installs dependencies (Node.js, Java, Maven)
- ✅ Clones user's GitHub repo
- ✅ Builds and deploys the project
- ✅ Returns working URL to user's live app

### URL Behavior
- ✅ Clicking "🚀 Live App" opens the deployed project
- ✅ NOT the GitHub repository
- ✅ Shows user's actual running application

---

## 🎯 How to Run

### Step 1: Start Backend
```powershell
cd D:\CAPSTONE\backend
mvn clean compile
mvn spring-boot:run
```

Wait for: `Started BackendApplication`

### Step 2: Start Frontend (New Terminal)
```powershell
cd D:\CAPSTONE\frontend
npm run dev
```

Wait for: `Local: http://127.0.0.1:3001/`

### Step 3: Open Dashboard
Open browser: **http://localhost:3001**

---

## 🚀 Deploy Your Project

### 1. Enter GitHub URL
Examples:
- `https://github.com/username/react-app`
- `https://github.com/username/portfolio`
- `https://github.com/username/static-website`

### 2. Click "🚀 Deploy to AWS"

### 3. Wait 3-5 Minutes
Watch the progress:
- Creating S3 bucket and security groups...
- Launching EC2 instance...
- Cloning repository and building project...
- Deploying application to server...
- 🚀 Deployment completed! Your app is live!

### 4. Click "🚀 Live App"
Opens your deployed project at: `http://XX.XX.XX.XX`

---

## 📊 What Gets Created in AWS

### EC2 Instance
- **Type:** t2.micro
- **OS:** Amazon Linux 2023
- **Software:** Apache, Node.js, Java, Maven, Git
- **Port:** 80 (HTTP)

### Security Group
- **Inbound:** Port 80 (HTTP) from 0.0.0.0/0
- **Outbound:** All traffic

### S3 Bucket
- **Encryption:** AES256
- **Versioning:** Enabled
- **Purpose:** Static assets storage

---

## 🎨 Dashboard Features

### Real-time Monitoring
- 📈 Response time (updates every 5 seconds)
- 🔥 Requests per minute
- 💻 CPU usage
- 🌐 Active instances

### Security Status
- 🔒 SSL/TLS status
- 🛡️ WAF protection
- 🚫 DDoS shield
- ⚠️ Vulnerability scanning

### Cost Analysis
- 💰 Monthly spending
- 📊 Projected costs
- 📈 Cost trends

### Deployment History
- 📋 All deployments
- ⏱️ Duration tracking
- 🔗 Live app links
- 🔄 One-click redeploy

---

## 🧪 Test Projects

### Static HTML
```
https://github.com/github/personal-website
```

### React App
```
https://github.com/facebook/create-react-app
```

### Portfolio
```
https://github.com/username/portfolio
```

---

## 🔍 Troubleshooting

### Backend Won't Start
```powershell
# Kill existing Java processes
taskkill /F /IM java.exe

# Restart
cd D:\CAPSTONE\backend
mvn spring-boot:run
```

### Frontend Won't Start
```powershell
# Reinstall dependencies
cd D:\CAPSTONE\frontend
npm install
npm run dev
```

### Deployment Fails
Check backend console for errors:
- AWS credentials configured? `aws configure`
- Correct region? `ap-south-1`
- Valid GitHub URL?

### URL Shows GitHub Repo
Wait 5 minutes for EC2 to:
1. Clone repo
2. Install dependencies
3. Build project
4. Start serving

---

## 📝 Deployment Process

```
User enters GitHub URL
        ↓
Backend creates S3 bucket
        ↓
Backend launches EC2 instance
        ↓
EC2 installs: Apache, Node.js, Java, Maven
        ↓
EC2 clones GitHub repository
        ↓
EC2 detects project type (React/Java/HTML)
        ↓
EC2 builds project (npm build / mvn package)
        ↓
EC2 deploys to Apache (/var/www/html/)
        ↓
Backend returns public IP URL
        ↓
User clicks "🚀 Live App"
        ↓
Browser opens deployed project
```

---

## 🎉 Success Indicators

✅ Backend console shows:
```
✅ Successfully deployed to: http://XX.XX.XX.XX
🔗 User can access their project at: http://XX.XX.XX.XX
```

✅ Dashboard shows:
- Status: COMPLETED
- Message: 🚀 Deployment completed! Your app is live!
- URL: http://XX.XX.XX.XX

✅ Clicking URL opens:
- Your actual project
- NOT GitHub repository
- Live, working application

---

## 🚀 Quick Start (One Command)

```powershell
cd D:\CAPSTONE
.\deploy-all.bat
```

This will:
1. ✅ Start backend
2. ✅ Start frontend
3. ✅ Open dashboard

Then just enter GitHub URL and deploy!

---

## 📞 Support

**Dashboard:** http://localhost:3001
**Backend API:** http://localhost:8080
**Health Check:** http://localhost:8080/api/health

**AWS Console:** https://console.aws.amazon.com/ec2/
**Region:** ap-south-1 (Mumbai)

---

## 🎯 Summary

Your AutoInfra platform now:
- ✅ Deploys real projects to AWS EC2
- ✅ Builds and serves user applications
- ✅ Returns working URLs
- ✅ Shows real-time monitoring
- ✅ Tracks costs and security
- ✅ Provides enterprise dashboard

**Just enter a GitHub URL and click deploy - your project will be live on AWS in 5 minutes!**
