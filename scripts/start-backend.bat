@echo off
echo Starting AutoInfra Backend...
echo.

where mvn >nul 2>nul
if %errorlevel% neq 0 (
    echo ERROR: Maven is not installed
    echo.
    echo Please install Maven first:
    echo 1. See INSTALL_MAVEN.md for instructions
    echo 2. Or download from: https://maven.apache.org/download.cgi
    echo.
    pause
    exit /b 1
)

cd /d %~dp0..\backend
echo Building and starting backend...
mvn spring-boot:run

pause
