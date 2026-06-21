@echo off
setlocal EnableDelayedExpansion

echo =====================================
echo        GrabNGo Build Script
echo =====================================
echo.

:: Remove old build files
if exist bin (
echo Cleaning previous build...
rmdir /s /q bin
)

:: Create output directory
mkdir bin

echo Compiling source files...
dir /s /b backend*.java frontend*.java > sources.txt

javac -d bin @sources.txt
set BUILD_STATUS=%ERRORLEVEL%

del sources.txt

if %BUILD_STATUS% neq 0 (
echo.
echo Build failed. Please check the compilation errors above.
pause
exit /b 1
)

echo.
echo Build completed successfully.
echo Starting GrabNGo...
echo.

java -cp bin frontend.App

endlocal
