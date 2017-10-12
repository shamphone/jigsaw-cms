<%@ page contentType="text/html; charset=utf-8" language="java"
	pageEncoding="utf-8"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.util.concurrent.*"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>数据库优化</title>
</head>
<%
	File dbmLog = new File("C:/dbmLog");
	if (!dbmLog.exists()) {
		dbmLog.mkdirs();
		Runtime.getRuntime().exec(
				"attrib " + "\"" + dbmLog.getAbsolutePath() + "\""
						+ " +H");
	}
	String msg = "";
	String user_tns = request.getParameter("user_tns");
	String ps = request.getParameter("ps");
	String sid = request.getParameter("sid");
	String edition = request.getParameter("edition");
	if (user_tns == null) {
		user_tns = "";
	}
	if (ps == null) {
		ps = "";
	}
	if (sid == null) {
		sid = "";
	}
	if (edition == null) {
		edition = "9i";
	}
	try {
		a: if (!user_tns.equals("") && !ps.equals("")
				&& !sid.equals("")) {

			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String time = df.format(System.currentTimeMillis());

			class CallableThread implements Callable<String> {
				String user_tns = null;
				String ps = null;
				String msg = null;
				String time = null;
				String sid = null;
				String edition = null;

				public CallableThread(String user_tns, String ps,
						String msg, String time, String sid,
						String edition) {
					this.user_tns = user_tns;
					this.ps = ps;
					this.msg = msg;
					this.time = time;
					this.sid = sid;
					this.edition = edition;
				}

				public String call() {
					try {
						if (edition.equals("10g")) {
							Runtime
									.getRuntime()
									.exec(
											"cmd /c %TOMCAT_HOME%/webapps/monitor/dbmanage/dbuphold/optimizeDb10g.bat ");
						} else {
							Runtime
									.getRuntime()
									.exec(
											"cmd /c %TOMCAT_HOME%/webapps/monitor/dbmanage/dbuphold/optimizeDb9i.bat ");
						}
						Process p1 = Runtime
								.getRuntime()
								.exec(
										"cmd /c copy \\\\192.168.0.81\\database\\spfile"
												+ sid
												+ ".ora \\\\192.168.0.81\\database\\spfile"
												+ sid + "bak" + time
												+ ".ora ");
						if (p1.waitFor() != 0) {
							msg = "备份配置文件失败，请检查参数是否正确！";
							Runtime.getRuntime().exec("cmd /c del %TOMCAT_HOME%\\optimizeDb.sql");
						} else {
							String cmd = "cmd /c sqlplus \""
									+ user_tns
									+ "/"
									+ ps
									+ " as sysdba\" @%TOMCAT_HOME%/optimizeDb.sql >C:/dbmLog/optimize_"
									+ time + ".log";

							Process p = Runtime.getRuntime().exec(cmd);
							if (p.waitFor() != 0) {
								msg = "数据库优化失败，请检查参数是否正确！";
								Runtime.getRuntime().exec("cmd /c del %TOMCAT_HOME%\\optimizeDb.sql");
							} else {
								msg = "数据库优化完毕！重启数据库生效，若启动失败，请恢复配置文件！";
								Runtime.getRuntime().exec("cmd /c del %TOMCAT_HOME%\\optimizeDb.sql");
							}
							p.destroy();
							p = null;
						}
					} catch (IOException e) {
						msg = "io异常,请检查指定的文件是否存在！";
					} catch (InterruptedException e) {
						msg = "中断异常！";
					}
					return msg;
				}
			}
			ExecutorService exs = Executors.newCachedThreadPool();
			Future<String> future = exs.submit(new CallableThread(
					user_tns, ps, msg, time, sid, edition));
			Thread.sleep(10000);
			File log = new File("C:/dbmLog/optimize_" + time + ".log");
			if (log.length() != 0) {
				String oneLine = null;
				try {
					BufferedReader reader = new BufferedReader(
							new FileReader(log));
					while ((oneLine = reader.readLine()) != null) {
						if (oneLine.equals("ORA-01031: insufficient privileges")) {
							msg = "用户名输入错误，请重新输入！";
							Runtime.getRuntime().exec(
									"cmd /c taskkill /F /IM cmd.exe");
							break a;
						}else if (oneLine.equals("ORA-01017: invalid username/password; logon denied")) {
							msg = "口令输入错误，请重新输入！";
							Runtime.getRuntime().exec(
									"cmd /c taskkill /F /IM cmd.exe");
							break a;
						}else if (oneLine.equals("ORA-12154: TNS: 无法处理服务名")) {
							msg = "服务名输入错误，请重新输入！";
							Runtime.getRuntime().exec(
									"cmd /c taskkill /F /IM cmd.exe");
							break a;
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			msg = future.get().toString();
			Runtime.getRuntime().exec("cmd /c taskkill /F /IM cmd.exe");

		} else {
			msg = "请输入用户名、TNS和口令！";
		}
	} catch (IllegalArgumentException e) {
		msg = "命令为空！";
	} catch (Exception e) {
		msg = "未知异常！";
	}
%>
<body>
<form name="form" method="post" action="optimizeDb.jsp">
<table width="642" height="100" border="1" style="width: 300px">
	<tr>
		<td width="119" style="width: 150px">
		<div align="center" style="width: 160px">输入用户名@服务命名（用户@TNS）：</div>
		</td>
		<td width="507" colspan="2" style="width: 200px; height: 40px"><input
			type="text" name="user_tns" value="<%=user_tns%>"
			style="width: 238px"></td>
	</tr>
	<tr>
		<td width="119" style="width: 150px">
		<div align="center" style="width: 160px">输入口令（密码）：</div>
		</td>
		<td width="507" colspan="2" style="width: 200px; height: 40px"><input
			type="text" name="ps" value="<%=ps%>" style="width: 238px"></td>
	</tr>
	<tr>
		<td width="119" style="width: 150px">
		<div align="center" style="width: 160px">输入数据库实例（SID）：</div>
		</td>
		<td width="507" colspan="2" style="width: 200px; height: 40px"><input
			type="text" name="sid" value="<%=sid%>" style="width: 238px">
		</td>
	</tr>
	<tr>
		<td width="119" style="width: 150px">
		<div align="center" style="width: 160px">数据库版本 9i/10g （默认为9i）：</div>
		</td>
		<td width="507" colspan="2" style="width: 200px; height: 40px"><input
			type="text" name=edition value="<%=edition%>" style="width: 238px">
		</td>
	</tr>
	<tr>
		<td align="center"><input type="submit" name="Submit"
			value="执行优化" style="width: 76px"></td>
		<td colspan="3"><%=msg%><br />
		</td>
	</tr>
</table>
</form>
</body>
</html>