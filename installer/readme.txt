安装文件制作说明：

1.使用免安装版的Tomcat6

2.使用免安装版的jdk1.6

3.将http://192.168.0.147/svn/coolink/studio目录中的文件复制到%TomcatHome%/webapps/目录中(注意将所有模块中web中的内容切到上一级,然后删除web文件夹)

4.将http://192.168.0.147/svn/coolink/installer/lib替换掉%TomcatHome%/lib
(并修改longcon-core.jar根目录下的config3.xml文件,将siteFactory下的directory一栏去掉)

5.从http://192.168.0.147/svn/coolink/database/oracle上取得最新的coolink_database1.0.1.sql和exec.sql放入安装程序根目录

6.复制过后的程序目录中有些文件需要重写或覆盖，用于覆盖的文件存于“needed rewrite files”目录中


