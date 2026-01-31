# Install Maven on Windows

## Quick Install Steps:

### 1. Download Maven
- Go to: https://maven.apache.org/download.cgi
- Download: `apache-maven-3.9.6-bin.zip` (Binary zip archive)

### 2. Extract
- Extract to: `C:\Program Files\Apache\maven`

### 3. Set Environment Variables

**Option A: Using PowerShell (Run as Administrator)**
```powershell
[System.Environment]::SetEnvironmentVariable("MAVEN_HOME", "C:\Program Files\Apache\maven", "Machine")
[System.Environment]::SetEnvironmentVariable("Path", $env:Path + ";C:\Program Files\Apache\maven\bin", "Machine")
```

**Option B: Using GUI**
1. Press `Win + X` → System → Advanced system settings
2. Click "Environment Variables"
3. Under "System variables":
   - Click "New"
   - Variable name: `MAVEN_HOME`
   - Variable value: `C:\Program Files\Apache\maven`
4. Find "Path" variable → Click "Edit" → "New"
   - Add: `C:\Program Files\Apache\maven\bin`
5. Click OK on all windows

### 4. Verify Installation
Close and reopen PowerShell, then run:
```powershell
mvn -version
```

### 5. Run Backend
```powershell
cd D:\CAPSTONE\backend
mvn spring-boot:run
```

Backend will start on: http://localhost:8080
