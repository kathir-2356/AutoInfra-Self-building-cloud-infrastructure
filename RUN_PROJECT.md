# 🚀 How to Run AutoInfra Project

## Quick Start (2 Commands)

### Terminal 1 - Start Backend:
```powershell
cd D:\CAPSTONE\backend
mvn spring-boot:run
```
Wait until you see: `Started BackendApplication`

### Terminal 2 - Start Frontend:
```powershell
cd D:\CAPSTONE\frontend
npm run dev
```

### Access:
- **Dashboard:** http://localhost:3001
- **Backend API:** http://localhost:8080/api/health

---

## Or Use Scripts

### Option 1: Start Both (Separate Windows)
```powershell
cd D:\CAPSTONE
scripts\start.bat
```

### Option 2: Start Frontend Only
```powershell
cd D:\CAPSTONE
scripts\start-frontend.bat
```

### Option 3: Start Backend Only
```powershell
cd D:\CAPSTONE
scripts\start-backend.bat
```

---

## Full Setup (First Time)

1. **Install Dependencies:**
```powershell
cd D:\CAPSTONE
scripts\install.bat
```

2. **Configure AWS (Optional):**
```powershell
aws configure
```

3. **Deploy Infrastructure (Optional):**
```powershell
cd terraform
terraform apply -auto-approve
```

4. **Start Application:**
```powershell
scripts\start.bat
```

---

## What's Running

✅ **Frontend (React):** Port 3001
- Dashboard UI
- Deployment interface
- Real-time status updates

✅ **Backend (Java Spring Boot):** Port 8080
- REST API endpoints
- Deployment simulation
- Health monitoring

✅ **AWS Infrastructure:** 
- S3 bucket for assets
- Configured in ap-south-1 region

---

## Stop the Project

Press `Ctrl+C` in both terminal windows

---

## Troubleshooting

**Backend won't start:**
- Check Maven is installed: `mvn -version`
- Check port 8080 is free

**Frontend won't start:**
- Check Node.js is installed: `node -version`
- Check port 3001 is free
- Run: `npm install` in frontend folder

**Can't connect to backend:**
- Ensure backend is running first
- Check http://localhost:8080/api/health
