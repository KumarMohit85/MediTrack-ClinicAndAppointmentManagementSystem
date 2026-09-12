# Setup Instructions

## 1. Prerequisites
- Java Development Kit (JDK) 8 or later. JDK 17/21 is recommended for development.
- Command Prompt or PowerShell on Windows, or a terminal on Linux/macOS.
- Git for source-control submission.
- No Maven/Gradle build is required by the assignment; the project can be compiled directly with `javac`.

Verify Java:

```text
java -version
javac -version
```

## 2. Project structure

```text
MediTrack-ClinicAndAppointmentManagementSystem/
├── docs/
├── src/
│   ├── main/java/com/airtribe/meditrack/
│   └── test/java/com/airtribe/meditrack/test/
├── README.md
└── .gitignore
```

## 3. Compile from project root (Windows PowerShell)

```powershell
Remove-Item -Recurse -Force out -ErrorAction SilentlyContinue
New-Item -ItemType Directory out | Out-Null
$files = Get-ChildItem -Recurse -Path src\main\java -Filter *.java | Select-Object -ExpandProperty FullName
javac -d out $files
```

## 4. Run

The entry point is:

```text
com.airtribe.meditrack.Main
```

Run with:

```powershell
java -cp out com.airtribe.meditrack.Main
```

If persistence is implemented in the final source version, the assignment specifies the optional command-line flag:

```powershell
java -cp out com.airtribe.meditrack.Main --loadData
```

## 5. Git workflow

```bash
git status
git add .
git commit -m "Complete MediTrack clinic management system"
git push origin main
```

If working on a feature branch:

```bash
git checkout -b feature/meditrack-completion
git add .
git commit -m "Implement MediTrack requirements"
git push -u origin feature/meditrack-completion
```

## 6. Troubleshooting

### `javac` not recognized
Install a JDK and ensure its `bin` directory is on `PATH`.

### Package/class not found
Compile from the project root and use `-d out`; do not compile individual files without their dependencies.

### Wrong main class
Use the fully qualified name `com.airtribe.meditrack.Main`.

### CSV files not found
Run from the project root so relative persistence paths resolve consistently.
