@echo off

::�����̷�
set DISK_NAME=P

::��ǰĿ¼·��
set CODE_DIR=%~dp0.

::���ݲ���ϵͳ���ͣ���ȡ����Ŀ¼��·��
for /f "tokens=1* delims=[" %%a in ('ver') do set b=%%b
set b=%b:* =%

goto %b:~0,4%%PROCESSOR_ARCHITECTURE:~-1%

:5.1.6
echo XP_32λ
set STARTUP_DIR=%USERPROFILE%\����ʼ���˵�\����\����
goto:CreateVirtualDisks

:6.1.6
echo WIN7_32λ
set STARTUP_DIR=%USERPROFILE%\AppData\Roaming\Microsoft\Windows\Start Menu\Programs\Startup
goto:CreateVirtualDisks

:6.1.4
echo WIN7_64λ
set STARTUP_DIR=%USERPROFILE%\AppData\Roaming\Microsoft\Windows\Start Menu\Programs\Startup
goto:CreateVirtualDisks

:6.0.6
echo VISTA_32��
set STARTUP_DIR=%USERPROFILE%\AppData\Roaming\Microsoft\Windows\Start Menu\Programs\Startup
goto:CreateVirtualDisks

:6.0.4
echo VISTA_64��
set STARTUP_DIR=%USERPROFILE%\AppData\Roaming\Microsoft\Windows\Start Menu\Programs\Startup
goto:CreateVirtualDisks

:CreateVirtualDisks

::��Ҫ���ɵ�����Ŀ¼���������ļ�������
set FILE_NAME="%STARTUP_DIR%\CreateVirtualDisks_%DISK_NAME%.bat"

::���ɴ��������̷����������ļ�
echo subst /d %DISK_NAME%: > %FILE_NAME%
echo subst %DISK_NAME%: "%CODE_DIR%" >> %FILE_NAME%

::ִ�д��������̷����������ļ�
call %FILE_NAME%
