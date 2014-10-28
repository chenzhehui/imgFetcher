@echo off
set lib=%~dp0lib
set classpath=%lib%\imgFetcher.jar;%lib%\jsoup-1.8.1.jar;

start javaw -Xms64m -Xmx1024m com.chevic.startup.Excetor