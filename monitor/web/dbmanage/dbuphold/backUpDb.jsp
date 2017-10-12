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
<title>数据库备份</title>
</head>
<%
	File dbmLog = new File("C:/dbmLog");
	if (!dbmLog.exists()) {
		dbmLog.mkdirs();
		Runtime.getRuntime().exec(
				"attrib " + "\"" + dbmLog.getAbsolutePath() + "\""
						+ " +H");
	}
	String user = request.getParameter("user");
	String tns = request.getParameter("tns");
	String ps = request.getParameter("ps");
	if (user == null) {
		user = "";
	}
	if (tns == null) {
		tns = "";
	}
	if (ps == null) {
		ps = "";
	}
	String msg = "";
	try {
		if (!user.equals("") && !ps.equals("") && !tns.equals("")) {

			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String time = df.format(System.currentTimeMillis());

			class CallableThread implements Callable<String> {
				String user = null;
				String tns = null;
				String ps = null;
				String msg = null;
				String time = null;

				public CallableThread(String user, String tns,
						String ps, String msg, String time) {
					this.user = user;
					this.tns = tns;
					this.ps = ps;
					this.msg = msg;
					this.time = time;
				}

				public String call() {

					try {
						String cmd = "cmd /c start EXP "
								+ user
								+ "/"
								+ ps
								+ "@"
								+ tns
								+ " file=//192.168.0.81/database/backup/bak"
								+ user + time + ".dmp log=C:/dbmLog/back_"
								+ user + "_" + time
								+ ".log buffer=300000 ";
						Process p = Runtime.getRuntime().exec(cmd);
						p.waitFor();
						p.destroy();
						p = null;
						msg = "用户名或者密码输入错误！";
					} catch (IOException e) {
						return msg = "io异常,请检查指定的文件是否存在！";
					} catch (InterruptedException e) {
						return msg = "中断异常！";
					}
					return msg;
				}
			}
			ExecutorService exs = Executors.newCachedThreadPool();
			Future<String> future = exs.submit(new CallableThread(user,
					tns, ps, msg, time));
			Thread.sleep(10000);
			File log = new File("C:/dbmLog/back_" + user + "_" + time
					+ ".log");
			if (log.length() != 0) {
				String oneLine = null;
				try {
					BufferedReader reader = new BufferedReader(
							new FileReader(log));
					a: while ((oneLine = reader.readLine()) != null) {
						if (oneLine.equals("EXP-00000: 导出终止失败")) {
							future.cancel(true);
							msg = "TNS输入错误！";
							break;
						} else if (oneLine
								.equals(". 正在导出 pre-schema 过程对象和操作")) {
							while (true) {
								Thread.sleep(10000);
								File logs = new File("C:/dbmLog/back_" + user
										+ "_" + time + ".log");
								BufferedReader readers = new BufferedReader(
										new FileReader(logs));
								while ((oneLine = reader.readLine()) != null) {
									if (oneLine.equals("导出成功终止，但出现警告。")) {
										msg = "备份数据库完毕！";
										break a;
									}
								}
							}
						}
					}
				} catch (IOException e) {
					msg = "io异常,请检查指定的文件是否存在！";
				}
			} else {
				msg = future.get().toString();
				Runtime.getRuntime().exec(
						"cmd /c taskkill /F /IM EXP.exe");
			}
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
<form name="form" method="post" action="backUpDb.jsp">
<table width="642" height="100" border="1" style="width: 300px">
	<tr>
		<td width="119" style="width: 150px">
		<div align="center" style="width: 160px">输入用户名（用户）：</div>
		</td>
		<td width="507" colspan="2" style="width: 200px; height: 40px"><input
			type="text" name="user" value="<%=user%>" style="width: 198px">
		</td>
	</tr>
	<tr>
		<td width="119" style="width: 150px">
		<div align="center" style="width: 160px">输入口令（密码）：</div>
		</td>
		<td width="507" colspan="2" style="width: 200px; height: 40px"><input
			type="text" name="ps" value="<%=ps%>" style="width: 198px"></td>
	</tr>
	<tr>
		<td width="119" style="width: 150px">
		<div align="center" style="width: 160px">输入服务命名（TNS）：</div>
		</td>
		<td width="507" colspan="2" style="width: 200px; height: 40px"><input
			type="text" name="tns" value="<%=tns%>" style="width: 198px">
		</td>
	</tr>
	<tr>
		<td align="center"><input type="submit" name="Submit"
			value="执行备份" style="width: 76px"></td>
		<td colspan="3"><%=msg%><br />
		</td>
	</tr>
</table>
</form>
</body>
</html>