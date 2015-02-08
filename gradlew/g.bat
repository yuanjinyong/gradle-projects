@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  Gradle startup script for Windows
@rem
@rem ##########################################################################

:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
set DIRNAME=%~dp0
set CUR_DIR=%DIRNAME:~0,-1%
::如果没有设置过环境变量，则先设置
if "%GRADLE_HOME%" == "" (
    echo 设置环境变量GRADLE_HOME
    set  GRADLE_HOME=%CUR_DIR%
    setx GRADLE_HOME %CUR_DIR%
    echo.
    echo.
)
if "%GRADLE_USER_HOME%" == "" (
    echo 设置环境变量GRADLE_USER_HOME
    set  GRADLE_USER_HOME=%CUR_DIR%\.gradle
    setx GRADLE_USER_HOME %CUR_DIR%\.gradle
    echo.
    echo.
)


::设置Gradle命令搜索路径
SET REG_PATH=HKEY_CURRENT_USER\Environment
if exist %GRADLE_HOME%\bin (SET ADD_PATH=%%GRADLE_HOME%%\bin) else (SET ADD_PATH=%%GRADLE_HOME%%)

Setlocal enabledelayedexpansion
for /f "skip=2 tokens=1,2,*" %%i in ('reg query "%REG_PATH%" /v "Path"') do (
   set VAR_VALUE=%%k
)
::如果已经添加过了，就不再添加
reg query "%REG_PATH%" /v "Path"|find /i "%ADD_PATH%"||(reg add "%REG_PATH%" /v Path /t REG_EXPAND_SZ /d "%ADD_PATH%;!VAR_VALUE!" /f)


::显示设置后的结果
ECHO.
set VAR_VALUE=
for /f "skip=2 tokens=1,2,*" %%i in ('reg query "%REG_PATH%" /v "GRADLE_HOME"') do (
   set VAR_VALUE=%%k
)
ECHO GRADLE_HOME      is: [!VAR_VALUE!]

set VAR_VALUE=
for /f "skip=2 tokens=1,2,*" %%i in ('reg query "%REG_PATH%" /v "GRADLE_USER_HOME"') do (
   set VAR_VALUE=%%k
)
ECHO GRADLE_USER_HOME is: [!VAR_VALUE!]

set VAR_VALUE=
for /f "skip=2 tokens=1,2,*" %%i in ('reg query "%REG_PATH%" /v "Path"') do (
   set VAR_VALUE=%%k
)
ECHO Path             is: [!VAR_VALUE!]

endlocal

echo.
echo.
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::



@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

@rem Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windowz variants

if not "%OS%" == "Windows_NT" goto win9xME_args
if "%@eval[2+2]" == "4" goto 4NT_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*
goto execute

:4NT_args
@rem Get arguments from the 4NT Shell from JP Software
set CMD_LINE_ARGS=%$

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar

@rem Execute Gradle
"%JAVA_EXE%" -version
echo.
echo.
echo "%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS% "-Dorg.gradle.appname=%APP_BASE_NAME%" -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %CMD_LINE_ARGS%
echo.
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS% "-Dorg.gradle.appname=%APP_BASE_NAME%" -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable GRADLE_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%GRADLE_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
