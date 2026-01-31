@echo off
echo Installing AutoInfra...
echo.

REM Check Node.js
where node >nul 2>nul
if %errorlevel% neq 0 (
    echo ERROR: Node.js is required but not installed
    echo Please install Node.js from https://nodejs.org/
    exit /b 1
)

REM Install frontend dependencies
echo [1/3] Installing frontend dependencies...
cd frontend
call npm install
if %errorlevel% neq 0 (
    echo ERROR: Failed to install frontend dependencies
    cd ..
    exit /b 1
)
cd ..
echo Frontend dependencies installed successfully
echo.

REM Check Maven (optional)
where mvn >nul 2>nul
if %errorlevel% equ 0 (
    echo [2/3] Installing backend dependencies...
    cd backend
    call mvn clean install -DskipTests
    cd ..
    echo Backend dependencies installed successfully
) else (
    echo [2/3] Maven not found - skipping backend build
    echo Note: Install Maven from https://maven.apache.org/ to build backend
)
echo.

REM Check Terraform (optional)
where terraform >nul 2>nul
if %errorlevel% equ 0 (
    echo [3/3] Initializing Terraform...
    cd terraform
    terraform init
    cd ..
    echo Terraform initialized successfully
) else (
    echo [3/3] Terraform not found - skipping infrastructure setup
    echo Note: Install Terraform from https://www.terraform.io/ for AWS deployment
)
echo.

echo ========================================
echo AutoInfra installation complete!
echo ========================================
echo.
echo Next steps:
echo 1. Start application: scripts\start.bat
echo 2. Access dashboard: http://localhost:3000
echo.
echo Optional:
echo - Configure AWS: aws configure
echo - Deploy infrastructure: scripts\deploy.bat
