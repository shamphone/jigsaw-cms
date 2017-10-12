@echo off
sqlplus -L "system/%SystemPassword%@%ServiceName%" @exec.sql || exit
exit
