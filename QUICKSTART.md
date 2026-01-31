# 🚀 Quick Start Guide

## Prerequisites Check

You have:
- ✅ Node.js installed
- ❌ Maven not installed (optional for backend)
- ✅ Terraform installed (optional for AWS deployment)

## Start the Application (Frontend Only)

Since Maven is not installed, you can run the frontend in demo mode:

### Option 1: Use the simple script
```bash
scripts\start-frontend.bat
```

### Option 2: Manual start
```bash
cd frontend
npm run dev
```

Then open: **http://localhost:3000**

## The frontend will work in demo mode
- The UI will be fully functional
- Deployment simulations will work
- Backend API calls will fail gracefully (you'll see them in the browser console)

## To get full functionality:

### Install Maven (for Java backend)
1. Download from: https://maven.apache.org/download.cgi
2. Extract and add to PATH
3. Run: `scripts\install.bat`
4. Run: `scripts\start.bat`

### Or use the frontend-only mode
The React dashboard works standalone and demonstrates all UI features!

## Current Status
- ✅ Frontend: Ready to run
- ⚠️ Backend: Requires Maven installation
- ⚠️ AWS Deployment: Requires AWS CLI configuration

## Next Steps
1. Run: `scripts\start-frontend.bat`
2. Open: http://localhost:3000
3. Explore the AutoInfra dashboard!
