@echo off
echo 🚀 Deploying AutoInfra to AWS...

REM Check AWS configuration
aws sts get-caller-identity >nul 2>nul
if %errorlevel% neq 0 (
    echo ❌ AWS not configured. Run: aws configure
    exit /b 1
)

REM Deploy infrastructure
cd terraform

echo 📋 Planning infrastructure...
terraform plan -out=plan.out

echo 🚀 Applying infrastructure...
terraform apply -auto-approve plan.out

echo ✅ Infrastructure deployed successfully!

REM Get outputs
echo 📊 Deployment Outputs:
terraform output

cd ..
