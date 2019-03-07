@echo off
set home=%cd%
echo Deleting old files...
cd "lib"
del "*.*" /q
cd %home%
echo Compiling Modules...
cd clocmath
call mvn clean install
cd %home%
copy clocmath\target\*.jar clocturn\lib
cd clocturn
call mvn clean compile assembly:single
cd %home%
echo Copying jars...
copy clocmath\target\*.jar lib
copy clocturn\target\*.jar lib
call mvn tomcat7:run
pause