@echo off
echo ========================================
echo   AutoInfra - Local Development Setup
echo ========================================
echo.
echo NOTE: This runs the application locally.
echo For AWS deployment, use: deploy-aws.bat
echo.

REM Check prerequisites
echo [1/3] Checking prerequisites...
where node >nul 2>nul || (echo ERROR: Node.js not installed && exit /b 1)
where mvn >nul 2>nul || (echo ERROR: Maven not installed && exit /b 1)
echo Prerequisites OK
echo.

REM Install dependencies
echo [2/3] Installing dependencies...
cd frontend
call npm install --silent
cd ..\backend
call mvn clean install -q -DskipTests
cd ..
echo Dependencies installed
echo.

REM Start services
echo [3/3] Starting services...
start "AutoInfra Backend" cmd /k "cd /d %~dp0backend && mvn spring-boot:run"
timeout /t 15 /nobreak >nul
start "AutoInfra Frontend" cmd /k "cd /d %~dp0frontend && npm run dev"
echo.

echo ========================================
echo   Local Development Running!
echo ========================================
echo.
echo Frontend: http://localhost:3001
echo Backend:  http://localhost:8080
echo.
echo To deploy to AWS, run: deploy-aws.bat
echo.
echo Press any key to exit...
pause >nul
