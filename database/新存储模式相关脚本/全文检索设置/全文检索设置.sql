grant execute on ctx_ddl to username; --授权

--创建词法分析器
begin 
ctx_ddl.create_preference('my_lexer','chinese_lexer');
end;