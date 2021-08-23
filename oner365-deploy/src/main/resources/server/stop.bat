
@echo off
for /F "tokens=5" %%i in ('netstat -ano ^| findstr :8083') do taskkill /F /PID %%i /T
