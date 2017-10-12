<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理网站</title>
<base target="_self">
</head>
<body>
	<iframe name="sites" width="800px" height="620px" src='<html:rewrite module="/site/management" page="/sites.do"/>?<%=request.getQueryString() %>'></iframe>
</body>
</html>