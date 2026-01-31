@echo off
echo Starting AutoInfra...
echo.

REM Check if Maven is available
where mvn >nul 2>nul
if %errorlevel% equ 0 (
    echo Starting Java backend...
    start "AutoInfra Backend" cmd /k "cd /d %~dp0..\backend && mvn spring-boot:run"
    echo Backend starting in new window...
    timeout /t 5 /nobreak >nul
) else (
    echo Maven not found - skipping backend
    echo Install Maven to run the Java backend
)

REM Start frontend
echo Starting React frontend...
start "AutoInfra Frontend" cmd /k "cd /d %~dp0..\frontend && npm run dev"
echo Frontend starting in new window...
echo.

echo ========================================
echo AutoInfra is starting!
echo ========================================
echo.
echo Dashboard: http://localhost:3000
echo Backend API: http://localhost:8080
echo Health Check: http://localhost:8080/api/health
echo.
echo Press any key to exit this window...
pause >nul
