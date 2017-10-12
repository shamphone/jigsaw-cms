create or replace procedure search_opt
is 
begin
ctx_ddl.optimize_index('full_text_search_idx','full');
end;