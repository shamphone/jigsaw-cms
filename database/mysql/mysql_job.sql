--每晚零点调用统计过程的job
# set GLOBAL event_scheduler=on ;

CREATE EVENT `count_job` ON SCHEDULE 
EVERY 1 DAY 
STARTS concat(date_format(now(),'%Y-%m-%d'),' 00:00:00')
ON COMPLETION NOT PRESERVE ENABLE COMMENT '每晚零点调用统计过程的job'
DO 
  call pro_count_new();

--每月一次优化表的job

CREATE EVENT `optimize_job` ON SCHEDULE 
EVERY 1 MONTH 
STARTS concat(date_format(now(),'%Y-%m-%d'),' 00:00:00')
ON COMPLETION NOT PRESERVE ENABLE COMMENT '--每月一次优化表的job'
DO 
  call optimize_all_tables(