@echo off

::�����̷�
set DISK_NAME=T

::��ǰĿ¼·��
set SRC_DIR=%~dp0
set SRC_DIR=%SRC_DIR:~0,-1%

::�����ǰ�������Ѿ���Ҫ���������������ˣ����ٴ����������
if "%SRC_DIR:~0,1%" == "%DISK_NAME%" (
    echo �������%DISK_NAME%�Ѿ����ڣ����贴����
    pause
    exit /b 0
)

::���ݲ���ϵͳ���ͣ���ȡ����Ŀ¼��·��
for /f "tokens=1* delims=[" %%a in ('ver') do set b=%%b
set b=%b:* =%

goto %b:~0,4%%PROCESSOR_ARCHITECTURE:~-1%

:5.1.6
echo ����ϵͳ����Ϊ��XP_32λ
set STARTUP_DIR=%USERPROFILE%\����ʼ���˵�\����\����
goto:CreateVirtualDisks

:6.1.6
echo ����ϵͳ����Ϊ��WIN7_32λ
set STARTUP_DIR=%USERPROFILE%\AppData\Roaming\Microsoft\Windows\Start Menu\Programs\Startup
goto:CreateVirtualDisks

:6.1.4
echo ����ϵͳ����Ϊ��WIN7_64λ
set STARTUP_DIR=%USERPROFILE%\AppData\Roaming\Microsoft\Windows\Start Menu\Programs\Startup
goto:CreateVirtualDisks

:6.0.6
echo ����ϵͳ����Ϊ��VISTA_32��
set STARTUP_DIR=%USERPROFILE%\AppData\Roaming\Microsoft\Windows\Start Menu\Programs\Startup
goto:CreateVirtualDisks

:6.0.4
echo ����ϵͳ����Ϊ��VISTA_64��
set STARTUP_DIR=%USERPROFILE%\AppData\Roaming\Microsoft\Windows\Start Menu\Programs\Startup
goto:CreateVirtualDisks

:CreateVirtualDisks

::��Ҫ���ɵ�����Ŀ¼���������ļ�������
set FILE_NAME="%STARTUP_DIR%\CreateVirtualDriver%DISK_NAME%.bat"
set INI_FILE_NAME="%SRC_DIR%\VirtualDriver%DISK_NAME%.ini"

::���ɴ��������̷����������ļ�
echo ���ɴ��������̷����������ļ�%FILE_NAME%
echo ::��ɾ���ٴ���> %FILE_NAME%
echo if exist %DISK_NAME%: (>> %FILE_NAME%
echo     subst /d %DISK_NAME%:>> %FILE_NAME%
echo )>> %FILE_NAME%
echo subst %DISK_NAME%: "%SRC_DIR%">> %FILE_NAME%
echo echo %%date%% %%time%%^> %INI_FILE_NAME%>> %FILE_NAME%
echo echo %DISK_NAME%="%SRC_DIR%"^>^> %INI_FILE_NAME%>> %FILE_NAME%


::ִ�д��������̷����������ļ�
echo ִ�д��������̷����������ļ�%FILE_NAME%
call %FILE_NAME%

echo.
echo.
pause