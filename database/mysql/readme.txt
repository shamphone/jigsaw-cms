Mysql环境下数据库部署：

1.先用init1.0.3.sql将表结构构建好，并导入初始数据(第三方软件可以使用MySQL-Front 5.1),存储过程需修改`root`@`%`

2.count_job.sql为建立统计job的sql，optimize_job.sql为表优化job的sql。执行此sql，如未启动event_scheduler，
  则执行set GLOBAL event_scheduler=on

附：1.如需添加NODE_DEFINITION_SUPER表, 则执行pro_insert(update)_nodedefinition.sql
      会修改pro_insert(update)_NodeDefinition存储过程

    2.如需使用pro_copy_NodeDefinition存储过程，则执行pro_copy_nodedefinition.sql
      会添加NODEDEF_PROCESSDEF，NODE_DEFINITION_AUTHORIZATION和NODE_VIEW表

    3.如需使用TR_NodeDefinition_INSERT触发器，则执行tr_nodedefinition_insert.sql
      会添加NODE_QUOTA表

Packet for query is too large错误
解决方法，在my.ini里的[mysqld]增加如下部分 (里面其余参数的设置可参考my_?.ini)
[mysqld] 
max_allowed_packet=64M(大小可调)

从oracle导入mysql，先把表的collation-server改为utf8_general_ci，再导入数据，避免乱码.
mysql的备份直接用mysql-front中的导出为sql文件即可
