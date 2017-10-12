create or replace procedure search_sync
is 
begin
ctx_ddl.sync_index('full_text_search_idx','20M');
end;