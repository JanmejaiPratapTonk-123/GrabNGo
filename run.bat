@echo off
setlocal

:: ── Clean previous build ──────────────────────────────────────────────────────
if exist bin rmdir /s /q bin
mkdir bin

:: ── Gather all .java source files ────────────────────────────────────────────
dir /s /B backend\*.java frontend\*.java > sources.txt 2>nul

:: ── Compile into bin/ (git-ignored, never committed) ─────────────────────────
javac -d bin @sources.txt
del sources.txt

if %ERRORLEVEL% neq 0 (
    echo.
    echo [ERROR] Build failed. Check errors above.
    pause
    exit /b 1
)

echo.
echo [OK] Build successful. Launching GrabNGo...
java -cp bin App

endlocal
