新存储模式下数据库部署：

1.先用coolink2.sql将表结构构建好

2.再用coolink2.dmp导入初始数据(coolink_database1.0.1.sql亦可)

3.exec.sql为安装程序调用的建库sql

4.count_job.sql为建立统计job的sql