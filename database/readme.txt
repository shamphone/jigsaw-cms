这个目录存放coolink所有的数据库文件及脚本。数据库目录和文件按照如下规范命名：
1.按照数据库类型，划分一级目录，如oracle\sqlserver\mysql\postgres
2.在每个数据库目录下，文件分为三类:
   1.全新安装的系统的初始化文件，命名规范为init1.0.3.sql（表结构初始化）或init1.0.3.dmp(数据初始化)
   2.用来更新原数据库的升级脚本，如update1.0.1-1.0.3.sql(版本1.0.1升级到版本1.0.3的脚本)
3.这里仅放置Coolink本身的数据库脚本。各应用的数据库脚本放置在其相应的目录下

database
  	--oracle
       		--init1.0.3.sql
		--init1.0.3.dmp
		--update1.0.1-1.0.3.sql
		--update1.0.2-1.0.3.sql
	--sqlserver
		--init1.0.3.sql
		--init1.0.3.dmp
		--update1.0.1-1.0.3.sql
		--update1.0.2-1.0.3.sql
  
