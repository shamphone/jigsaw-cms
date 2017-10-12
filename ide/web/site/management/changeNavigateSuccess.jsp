<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
    	<base target="_self"/>
    </head>
    <body>
    </body>
    <script type="text/javascript" language="javascript">
	    var site = new Object();
	    site.ID = '<bean:write name="site" property="ID" ignore="true"/>';
	    site.name = '<bean:write name="site" property="name" ignore="true"/>';
	    site.displayName = '<cms:node name="site" propertyName="displayName" ignore="true"/>';
	    site.domain = '<bean:write name="site" property="name" ignore="true"/>';
	    window.returnValue=site;
	    window.close();
    </script>
</html>
