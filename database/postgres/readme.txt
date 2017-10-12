PostgreSQL环境下数据库部署：

1.先用init1.0.3.sql脚本将表结构构建好，并导入初始数据(第三方软件可以使用Navicat for PostgreSQL 8.2.12，编码使用UTF8)

2.然后执行initFunctions.sql脚本，创建存储过程

3.执行pgagent中的pgagent.sql语句，即可建立PostgreSQL的count_job作业。
  然后拷贝剩余的三个文件到PostgreSQL安装目录的bin文件夹下，运行cmd，进入PostgreSQL的bin文件夹目录，执行：
  bin>pgagent INSTALL pgagent -u postgres -p 初始密码 hostaddr=localhost dbname=建立的数据库名 user=postgres password=初始密码
  注册pgagent服务成功后，第一次需手动启动此服务，则count_job作业可按计划运行。
  （附：在执行pgagent.sql前，搜索pgagent.sql中的newCoolink，将此字符串替换为建立的数据库名）

4.如需使用TR_NodeDefinition_INSERT触发器，则执行tr_nodedefinition_insert.sql
  会添加NODE_QUOTA表