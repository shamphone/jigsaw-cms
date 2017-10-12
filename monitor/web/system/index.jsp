<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>系统管理</title>
</head>
<frameset id="main" rows="37,*" cols="*"  frameborder="0" border="1" framespacing="0">
  <frame src="toolbar.jsp" name="index" rows="20"  scrolling="no" marginheight="0" marginwidth="0" scrolling="no" noresize />
  <frameset id="main2" rows="*" cols="230,*"  frameborder="1" border="1" framespacing="3">
    <frame src="left.jsp" name="left" marginheight="0" marginwidth="0" scrolling="no"/>
   	<frame src="probe/index.htm" name="content" marginheight="1" marginwidth="1" />
  </frameset>
</frameset>
</html>

