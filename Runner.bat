@echo off 
set ANT_HOME=.\lib\apache-ant-1.8.0

set ANT_OPTS=-Xms512M -Xmx1024M
set CLASSPATH=%ANT_HOME%\lib
set PATH=.;%ANT_HOME%\bin


echo Selenium tests execution started.....
call %ANT_HOME%\bin\ant -buildfile "build.xml" 
echo Selenium tests execution completed.....

pause
