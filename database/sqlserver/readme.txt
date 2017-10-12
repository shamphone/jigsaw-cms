Sqlserver环境下数据库部署：

1.先用init1.0.3.sql将表结构构建好

2.再用init1.0.3.mdb导入初始数据

3.count_job.sql为建立统计job的sql，执行此sql前要填入所使用数据库的名称
  执行此sql后，要将SQL Sever Agent服务启动,并设置为自动启动,否则作业不会被执行
  设置SQL Sever Agent服务方法: 
  我的电脑--控制面板--管理工具--服务--右键 SQLSERVERAGENT--属性--启动类型--选择"自动启动"--确定

附：1.如需添加NODE_DEFINITION_SUPER表, 则执行pro_insert(update)_nodedefinition.sql
      会修改pro_insert(update)_NodeDefinition存储过程

    2.如需使用pro_copy_NodeDefinition存储过程，则执行pro_copy_nodedefinition.sql
      会添加NODEDEF_PROCESSDEF，NODE_DEFINITION_AUTHORIZATION和NODE_VIEW表

    3.如需使用TR_NodeDefinition_INSERT触发器，则执行tr_nodedefinition_insert.sql
      会添加NODE_QUOTA表