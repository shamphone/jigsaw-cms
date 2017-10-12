<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>页面设计</title>
  </head>
        <script Language='Javascript' src='<html:rewrite page="/script/common.js" module="/common"/>' type="text/javascript"></script>
        <script Language='Javascript' src='<html:rewrite page="/script/ajax.js" module="/common"/>' type="text/javascript"></script>    
		<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/webdav.js"/>"></script>
		<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/smsitefactory.js"/>"></script>
		<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/smtemplate.js"/>"></script>
		<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/channelsource.js"/>"></script>
		<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/smresource.js"/>"></script>      
       	<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/smchannel.js"/>"></script>
		<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/smfolder.js"/>"></script>  
		<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/smeditor.js"/>"></script>
    <script type="text/javascript">
	    var contextPath="<html:rewrite module='' page=''/>";
	    Globals.contextPath = contextPath;    
	    Editor.start('<%= request.getParameter("path")%>',siteFactory);   
    </script>
  </head> 
  <frameset id="main" rows="*" cols="230,*" frameborder="0" border="0" framespacing="0">
    <frame src="leftTree.jsp" name="tree" marginheight="0" marginwidth="0" scrolling="no" noresize/>
    <frame  src="blank.jsp" name="subContent" scrolling="no" marginheight="0" marginwidth="0" noresize/>
  </frameset>
  <noframes></noframes>
</html>