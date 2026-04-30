@echo off
setlocal

:: Clean and Compile
if exist bin rmdir /s /q bin
mkdir bin

dir /s /B backend\*.java frontend\*.java > sources.txt
javac -d bin @sources.txt
del sources.txt

:: Run if success
if %ERRORLEVEL% == 0 (
    echo [OK] Launching GrabNGo...
    java -cp bin frontend.App
) else (
    echo [ERROR] Build failed!
    pause
)

endlocal