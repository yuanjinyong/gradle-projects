@echo off

::虚拟盘符
set DISK_NAME=P

::当前目录路径
set CODE_DIR=%~dp0.

::根据操作系统类型，获取启动目录的路径
for /f "tokens=1* delims=[" %%a in ('ver') do set b=%%b
set b=%b:* =%

goto %b:~0,4%%PROCESSOR_ARCHITECTURE:~-1%

:5.1.6
echo XP_32位
set STARTUP_DIR=%USERPROFILE%\「开始」菜单\程序\启动
goto:CreateVirtualDisks

:6.1.6
echo WIN7_32位
set STARTUP_DIR=%USERPROFILE%\AppData\Roaming\Microsoft\Windows\Start Menu\Programs\Startup
goto:CreateVirtualDisks

:6.1.4
echo WIN7_64位
set STARTUP_DIR=%USERPROFILE%\AppData\Roaming\Microsoft\Windows\Start Menu\Programs\Startup
goto:CreateVirtualDisks

:6.0.6
echo VISTA_32λ
set STARTUP_DIR=%USERPROFILE%\AppData\Roaming\Microsoft\Windows\Start Menu\Programs\Startup
goto:CreateVirtualDisks

:6.0.4
echo VISTA_64λ
set STARTUP_DIR=%USERPROFILE%\AppData\Roaming\Microsoft\Windows\Start Menu\Programs\Startup
goto:CreateVirtualDisks

:CreateVirtualDisks

::将要生成到启动目录的批处理文件的名称
set FILE_NAME="%STARTUP_DIR%\CreateVirtualDisks_%DISK_NAME%.bat"

::生成创建虚拟盘符的批处理文件
echo subst /d %DISK_NAME%: > %FILE_NAME%
echo subst %DISK_NAME%: "%CODE_DIR%" >> %FILE_NAME%

::执行创建虚拟盘符的批处理文件
call %FILE_NAME%
