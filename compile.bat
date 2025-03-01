@echo off
echo Compiling JavaFX project...
mkdir bin 2>nul
dir /s /b src\*.java > sources.txt
javac -d bin --module-path "C:\Program Files\Java\javafx-sdk-23.0.2\lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.media @sources.txt
if %ERRORLEVEL% EQU 0 (
    echo Compilation successful!
) else (
    echo Compilation failed!
    pause
)