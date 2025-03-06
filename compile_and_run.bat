@echo off
echo Compiling Song of Twelve Feathers...
javac --module-path "C:\Program Files\Java\javafx-sdk-23.0.2\lib" --add-modules javafx.controls,javafx.fxml,javafx.media -d bin -cp src src/application/*.java src/ui/*.java src/soundmanager/*.java

if %ERRORLEVEL% NEQ 0 (
  echo Compilation failed!
  pause
  exit /b %ERRORLEVEL%
)

echo Compilation successful!
echo Starting the game...
java --module-path "C:\Program Files\Java\javafx-sdk-23.0.2\lib" --add-modules javafx.controls,javafx.fxml,javafx.media -cp bin application.MainApp

pause