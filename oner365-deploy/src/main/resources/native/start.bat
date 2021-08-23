
@echo off
cd %~dp0
call oner365-gateway/start.bat
timeout /T 15 /NOBREAK

cd %~dp0
call oner365-system/start.bat
timeout /T 15 /NOBREAK

cd %~dp0
call oner365-monitor/start.bat
timeout /T 15 /NOBREAK
