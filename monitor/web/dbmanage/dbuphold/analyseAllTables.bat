@echo off
del %TOMCAT_HOME%\analyseAllTables.sql
echo.declare>>%TOMCAT_HOME%\analyseAllTables.sql
echo.begin>>%TOMCAT_HOME%\analyseAllTables.sql
echo.pro_AnalyzeAllTables();>>%TOMCAT_HOME%\analyseAllTables.sql
echo.end;>>%TOMCAT_HOME%\analyseAllTables.sql
echo./>>%TOMCAT_HOME%\analyseAllTables.sql
echo.exit>>%TOMCAT_HOME%\analyseAllTables.sql
echo.
exit