@echo off
echo 请确认已经设置了JAVA_HOME环境变量
set JAVA_BIN=%JAVA_HOME%\bin
set JAVA_KEYTOOL=%JAVA_BIN%\keytool
echo 删除已经存在的证书
rem keytool -delete -alias coolink -keystore %JAVA_HOME%/jre/lib/security/cacerts -storepass fulong
rem keytool -delete -alias coolink -storepass fulong -keystore coolink.keystore
echo 服务器中生成证书：(注：生成证书时，CN要和服务器的域名相同，如果在本地测试，则使用localhost)   
%JAVA_KEYTOOL% -genkey -alias coolink -keyalg RSA -keysize 1024 -validity 70000 -dname "CN=egotour.coolink.cn, OU=coolink.cn, O=coolink.cn, L=Beijing, S=Beijing, C=SE" -keypass fulong -storepass fulong -keystore coolink.keystore
echo 导出证书，由客户端安装
%JAVA_KEYTOOL% -export -alias coolink -keystore coolink.keystore -file coolinkcerts.cer -storepass fulong   
echo 客户端配置：为客户端的JVM导入密钥(将服务器下发的证书导入到JVM中) 
%JAVA_KEYTOOL% -import -trustcacerts -alias coolink -keystore "%JAVA_HOME%/JRE/LIB/SECURITY/cacerts" -file coolinkcerts.cer -storepass fulong



