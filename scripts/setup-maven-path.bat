@echo off
echo Setting up Maven environment variables...
echo.
echo This requires Administrator privileges.
echo Right-click this file and select "Run as administrator"
echo.

setx MAVEN_HOME "C:\Program Files\Apache\maven" /M
setx PATH "%PATH%;C:\Program Files\Apache\maven\bin" /M

echo.
echo Maven environment variables set!
echo Please close and reopen PowerShell/Command Prompt
echo.
pause
