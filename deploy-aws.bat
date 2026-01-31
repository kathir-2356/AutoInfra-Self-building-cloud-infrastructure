@echo off
echo ========================================
echo   AutoInfra - AWS Deployment
echo ========================================
echo.

REM Check AWS credentials
echo [1/4] Checking AWS credentials...
aws sts get-caller-identity >nul 2>nul
if %errorlevel% neq 0 (
    echo ERROR: AWS not configured
    echo Run: aws configure
    echo.
    echo You need:
    echo - AWS Access Key ID
    echo - AWS Secret Access Key
    echo - Default region (e.g., us-east-1)
    exit /b 1
)
echo AWS credentials OK
echo.

REM Show AWS account info
echo AWS Account Info:
aws sts get-caller-identity
echo.

REM Initialize Terraform
echo [2/4] Initializing Terraform...
cd terraform
terraform init
if %errorlevel% neq 0 (
    echo ERROR: Terraform init failed
    cd ..
    exit /b 1
)
echo.

REM Plan deployment
echo [3/4] Planning deployment...
terraform plan
if %errorlevel% neq 0 (
    echo ERROR: Terraform plan failed
    cd ..
    exit /b 1
)
echo.

REM Apply deployment
echo [4/4] Deploying to AWS...
echo This will create real AWS resources that may incur costs.
echo.
set /p CONFIRM="Continue with deployment? (yes/no): "
if /i not "%CONFIRM%"=="yes" (
    echo Deployment cancelled
    cd ..
    exit /b 0
)

terraform apply -auto-approve
if %errorlevel% neq 0 (
    echo ERROR: Terraform apply failed
    cd ..
    exit /b 1
)
echo.

echo ========================================
echo   AWS Deployment Complete!
echo ========================================
echo.
echo Resources Created:
terraform output
echo.

cd ..
echo Press any key to exit...
pause >nul
