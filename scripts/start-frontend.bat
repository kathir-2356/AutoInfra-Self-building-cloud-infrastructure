@echo off
echo Starting AutoInfra Frontend...
echo.

cd /d %~dp0..\frontend
npm run dev

pause
