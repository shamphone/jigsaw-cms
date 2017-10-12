@echo off
del %TOMCAT_HOME%\optimizeDb.sql
echo.alter system set processes=500 scope=spfile;>>%TOMCAT_HOME%\optimizeDb.sql
echo.alter system set sessions=555 scope=spfile;>>%TOMCAT_HOME%\optimizeDb.sql
echo.alter system set open_cursors=30000 scope=spfile;>>%TOMCAT_HOME%\optimizeDb.sql
echo.alter system set shared_pool_size=209715200 scope=spfile;>>%TOMCAT_HOME%\optimizeDb.sql
echo.alter system set db_cache_size=848576000 scope=spfile;>>%TOMCAT_HOME%\optimizeDb.sql
echo.alter system set sga_max_size=1151690352 scope=spfile;>>%TOMCAT_HOME%\optimizeDb.sql
echo.alter system set large_pool_size=16777216 scope=spfile;>>%TOMCAT_HOME%\optimizeDb.sql
echo.alter system set java_pool_size=33554432 scope=spfile;>>%TOMCAT_HOME%\optimizeDb.sql
echo.alter system set pga_aggregate_target=529145600 scope=spfile;>>%TOMCAT_HOME%\optimizeDb.sql
echo.alter system set QUERY_REWRITE_INTEGRITY = TRUSTED scope=spfile;>>%TOMCAT_HOME%\optimizeDb.sql
echo.alter system set QUERY_REWRITE_ENABLED = TRUE scope=spfile;>>%TOMCAT_HOME%\optimizeDb.sql
echo./>>%TOMCAT_HOME%\optimizeDb.sql
echo.exit>>%TOMCAT_HOME%\optimizeDb.sql
exit