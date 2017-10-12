
/**
*CATEGORY:COOLINK
*job:job for updating full_text_idx
*description: updating in every min
*CORP.:FULONG
*AUTHOR:LIULEI
*DATE:07/05/2009
*VERSION:2.2
*/

begin
  sys.dbms_job.submit(job => job,
                      what => 'search_sync;',
                      next_date => to_date('%your next date%', 'dd-mm-yyyy hh24:mi:ss'), 
                                           --example:31-07-2009 14:40:26
                      interval => 'sysdate+1/1440');
  commit;
end;
/