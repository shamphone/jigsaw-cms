<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<bean:parameter id="moduleId" name="module" value="cms"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>组织模型管理</title>
</head>
<frameset id="main" rows="37,*" cols="*"  frameborder="0" border="1" framespacing="0">
  <frame src="toolbar.jsp" name="index" rows="20"  scrolling="no" marginheight="0" marginwidth="0" scrolling="no" noresize />
  <frameset id="main2" rows="*" cols="230,*"  frameborder="1" border="1" framespacing="3">
    <frame src="left.do" name="tree" marginheight="0" marginwidth="0" scrolling="no" />
   	<frame src="blank.jsp" name="list" marginheight="1" marginwidth="1" scrolling="no"/>
  </frameset>
</frameset>
</html>
