@echo off
call mvn clean install
call mvn tomcat7:run