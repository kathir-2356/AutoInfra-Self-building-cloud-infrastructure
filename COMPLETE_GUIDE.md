# 🚀 AutoInfra - Complete Deployment Guide

## ⚡ ONE COMMAND DEPLOYMENT

```powershell
deploy-all.bat
```

This single command will:
1. ✅ Check all prerequisites (Node.js, Maven, Terraform, AWS CLI)
2. ✅ Install frontend dependencies (npm)
3. ✅ Build backend (Maven)
4. ✅ Deploy AWS infrastructure (Terraform)
5. ✅ Start backend server (port 8080)
6. ✅ Start frontend dashboard (port 3001)

---

## 📋 Prerequisites

Before running `deploy-all.bat`, ensure you have:

- ✅ **Node.js 18+** - https://nodejs.org/
- ✅ **Maven 3.9+** - https://maven.apache.org/download.cgi
- ✅ **Terraform 1.5+** - https://www.terraform.io/downloads
- ✅ **AWS CLI** - https://aws.amazon.com/cli/
- ✅ **Java 17+** - https://adoptium.net/

---

## 🔧 First Time Setup

### 1. Configure AWS Credentials:
```powershell
aws configure
```
Enter:
- AWS Access Key ID
- AWS Secret Access Key
- Default region: `ap-south-1`
- Default output: `json`

### 2. Run Complete Deployment:
```powershell
cd D:\CAPSTONE
deploy-all.bat
```

### 3. Access Application:
- **Dashboard:** http://localhost:3001
- **Backend API:** http://localhost:8080/api/health
- **AWS Console:** Check S3 bucket in ap-south-1 region

---

## 📦 What Gets Deployed

### Frontend (React + Vite)
- Port: 3001
- Auto-reload on code changes
- Connects to backend API

### Backend (Java Spring Boot)
- Port: 8080
- REST API endpoints
- Deployment simulation engine

### AWS Infrastructure (Terraform)
- **S3 Bucket** - Encrypted storage with versioning
- **Region** - ap-south-1 (Mumbai)
- **Tags** - Environment: production, ManagedBy: terraform

---

## 🎯 Manual Deployment Steps

If you prefer step-by-step:

### Step 1: Install Dependencies
```powershell
cd D:\CAPSTONE
scripts\install.bat
```

### Step 2: Deploy AWS Infrastructure
```powershell
cd terraform
terraform init
terraform apply -auto-approve
```

### Step 3: Start Backend
```powershell
cd D:\CAPSTONE\backend
mvn spring-boot:run
```

### Step 4: Start Frontend (New Terminal)
```powershell
cd D:\CAPSTONE\frontend
npm run dev
```

---

## 🧪 Test the Application

1. Open http://localhost:3001
2. Enter a GitHub URL (e.g., `https://github.com/username/repo`)
3. Click "🚀 Deploy to AWS"
4. Watch real-time deployment progress
5. See deployment complete with generated URL

---

## 📊 API Endpoints

- `GET /api/health` - Health check
- `POST /api/deploy` - Start deployment
- `GET /api/deployment/{id}/status` - Get deployment status
- `GET /api/deployments` - List all deployments

---

## 🛑 Stop the Application

Press `Ctrl+C` in both terminal windows (backend and frontend)

---

## 🗑️ Cleanup AWS Resources

```powershell
cd D:\CAPSTONE\terraform
terraform destroy -auto-approve
```

---

## 🔄 Restart Application

```powershell
cd D:\CAPSTONE
scripts\start.bat
```

This starts backend and frontend without redeploying AWS infrastructure.

---

## 📁 Project Structure

```
D:\CAPSTONE\
├── deploy-all.bat          # ONE COMMAND DEPLOYMENT
├── terraform/              # AWS Infrastructure
│   ├── main.tf            # S3, encryption, versioning
│   ├── variables.tf       # Configuration variables
│   └── outputs.tf         # Deployment outputs
├── backend/               # Java Spring Boot
│   ├── pom.xml           # Maven dependencies
│   └── src/              # Java source code
├── frontend/             # React Dashboard
│   ├── package.json      # NPM dependencies
│   └── src/              # React components
└── scripts/              # Helper scripts
    ├── install.bat       # Install dependencies
    ├── start.bat         # Start app
    └── deploy.bat        # Deploy infrastructure
```

---

## ⚠️ Troubleshooting

### Backend won't start:
```powershell
# Check Maven
mvn -version

# Check Java
java -version

# Rebuild
cd backend
mvn clean install
```

### Frontend won't start:
```powershell
# Check Node.js
node -version

# Reinstall dependencies
cd frontend
npm install
```

### Terraform fails:
```powershell
# Check AWS credentials
aws sts get-caller-identity

# Reinitialize
cd terraform
terraform init -upgrade
```

### Port already in use:
```powershell
# Check what's using port 8080
netstat -ano | findstr :8080

# Check what's using port 3001
netstat -ano | findstr :3001
```

---

## 🎉 Success Indicators

✅ Backend: See "Started BackendApplication" in terminal
✅ Frontend: See "Local: http://127.0.0.1:3001/" in terminal
✅ AWS: Run `terraform output` to see S3 bucket name
✅ Health: Visit http://localhost:8080/api/health shows "OK"

---

## 📞 Support

For issues:
1. Check logs in terminal windows
2. Verify all prerequisites are installed
3. Ensure AWS credentials are configured
4. Check ports 8080 and 3001 are available

---

## 🚀 Quick Reference

| Command | Purpose |
|---------|---------|
| `deploy-all.bat` | Deploy everything (one command) |
| `scripts\install.bat` | Install dependencies only |
| `scripts\start.bat` | Start app (no AWS deploy) |
| `terraform apply` | Deploy AWS infrastructure |
| `terraform destroy` | Remove AWS resources |
| `mvn spring-boot:run` | Start backend only |
| `npm run dev` | Start frontend only |
