<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程设计</title>
<script language="Javascript" type="text/Javascript" src="classes/pms.js"></script>
</head>
<frameset rows="30,*" cols="*" frameborder="no" border="1" framespacing="0">
	<frame src="toolbar.jsp?id=<% if(request.getParameter("id")!=null) out.print(request.getParameter("id")); %>" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame">
	<frameset cols="230,*" frameborder="1" border="1" framespacing="1">
		<frame marginwidth="0" marginheight="0" src="left.do?id=<% if(request.getParameter("id")!=null) out.print(request.getParameter("id")); %>" name="leftFrame" scrolling="No" id="leftFrame" title="流程列表"></frame>
		<frameset rows="*,120">
		<frame marginwidth="0" marginheight="0"  src="blank.jsp" name="mainFrame" id="mainFrame" title="流程设计器"></frame>
		<frame marginwidth="0" marginheight="0"  src="blank.jsp" name="rightFrame" id="rightFrame" title="属性编辑器" scrolling="no"></frame>
		</frameset>
	</frameset>
</frameset>
<noframes>
<body><span>本浏览器不支持框架页面。</span>
</body>
</noframes>

</html>
