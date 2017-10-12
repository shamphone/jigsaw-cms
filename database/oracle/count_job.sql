--每晚零点调用统计过程的job

declare job number;
begin
  sys.dbms_job.submit( job,
                      'pro_count_new();',
                       Sysdate,
                       'TRUNC(sysdate) + 1');
  commit;
end;
/
--每晚一点调用分析表过程的job
declare job number;
begin
  sys.dbms_job.submit( job,
                      'analyzealltable();',
                       Sysdate,
                       'TRUNC(sysdate) + 1 +1/ (24)');
  commit;
end;
/