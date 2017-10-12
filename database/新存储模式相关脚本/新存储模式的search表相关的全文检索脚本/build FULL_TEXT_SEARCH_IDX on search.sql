/**
*CATEGORY:COOLINK
*PROCEDURE:create lexer and build full_text_search_idx on search(text)
*CORP.:FULONG
*AUTHOR:LIULEI
*DATE:07/05/2009
*VERSION:2.2
*/
begin
-------------first step--------------------
begin
    ctx_ddl.drop_preference('full_text_search_lexer');
    ctx_ddl.create_preference('full_text_search_lexer','chinese_lexer');
end;


------------second step---------------------

begin
    create index full_text_search_idx on search(text) indextype is ctxsys.context
    parameters('lexer full_text_search_lexer');
end;
end;