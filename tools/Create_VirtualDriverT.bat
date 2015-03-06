@echo off

::虚拟盘符
set DISK_NAME=T

::当前目录路径
set CODE_DIR=%~dp0
set CODE_DIR=%CODE_DIR:~0,-1%

::如果当前批处理已经在要创建的虚拟盘下了，则不再创建虚拟磁盘
if "%CODE_DIR:~0,1%" == "%DISK_NAME%" (
    echo 虚拟磁盘%DISK_NAME%已经存在，无需创建。
    pause
    exit /b 0
)

::根据操作系统类型，获取启动目录的路径
for /f "tokens=1* delims=[" %%a in ('ver') do set b=%%b
set b=%b:* =%

goto %b:~0,4%%PROCESSOR_ARCHITECTURE:~-1%

:5.1.6
echo 操作系统类型为：XP_32位
set STARTUP_DIR=%USERPROFILE%\「开始」菜单\程序\启动
goto:CreateVirtualDisks

:6.1.6
echo 操作系统类型为：WIN7_32位
set STARTUP_DIR=%USERPROFILE%\AppData\Roaming\Microsoft\Windows\Start Menu\Programs\Startup
goto:CreateVirtualDisks

:6.1.4
echo 操作系统类型为：WIN7_64位
set STARTUP_DIR=%USERPROFILE%\AppData\Roaming\Microsoft\Windows\Start Menu\Programs\Startup
goto:CreateVirtualDisks

:6.0.6
echo 操作系统类型为：VISTA_32λ
set STARTUP_DIR=%USERPROFILE%\AppData\Roaming\Microsoft\Windows\Start Menu\Programs\Startup
goto:CreateVirtualDisks

:6.0.4
echo 操作系统类型为：VISTA_64λ
set STARTUP_DIR=%USERPROFILE%\AppData\Roaming\Microsoft\Windows\Start Menu\Programs\Startup
goto:CreateVirtualDisks

:CreateVirtualDisks

::将要生成到启动目录的批处理文件的名称
set FILE_NAME="%STARTUP_DIR%\CreateVirtualDisks_%DISK_NAME%.bat"

::生成创建虚拟盘符的批处理文件
echo 生成创建虚拟盘符的批处理文件%FILE_NAME%
echo ::先删除再创建> %FILE_NAME%
echo if exist %DISK_NAME%: (>> %FILE_NAME%
echo     subst /d %DISK_NAME%:>> %FILE_NAME%
echo )>> %FILE_NAME%
echo subst %DISK_NAME%: "%CODE_DIR%">> %FILE_NAME%

::执行创建虚拟盘符的批处理文件
echo 执行创建虚拟盘符的批处理文件%FILE_NAME%
call %FILE_NAME%

echo.
echo.
pause
