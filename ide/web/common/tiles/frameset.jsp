<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<bean:parameter id="moduleId" name="module" value="cms"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>欢迎<fulong:name name="principal" simple="false"/>-<bean:write name="com.fulong.system" property='<%= "module("+moduleId+").name" %>'/>-<bean:write name="com.fulong.system" property="property(system.name)"/></title>
</head>
<frameset id="main" rows="*" cols="190,*"  frameborder="0" border="1" framespacing="2">
  <frame src="<tiles:getAsString name='left-url'/>" name="index"  scrolling="no" marginheight="0" marginwidth="0" />
  <frameset id="main2" rows="285,*" cols="*"  frameborder="1" border="1" framespacing="2">
    <frame src="<tiles:getAsString name='list-url' ignore='true'/>" name="list" marginheight="0" marginwidth="0" />
    <frame src="<tiles:getAsString name='content-url' ignore='true'/>" name="content" marginheight="0" marginwidth="0" />
  </frameset>
</frameset>
</html>

