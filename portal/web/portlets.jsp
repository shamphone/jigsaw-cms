<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"  %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>插入占位符</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="EXPIRES" content="0"/>
        <meta http-equiv="Pragma" Content="No-cach" />
        <link rel="stylesheet" href="<html:rewrite page='/portal/style.css' module=''/>"/>
    </head>
    <body>
        <table width="100%" cellpadding="2" cellspacing="2" border="0">
            <logic:iterate id="portletCategory" name="com.fulong.longcon.portlet.container" property="portletCategories">
                <logic:iterate id="portletDefinition" name="portletCategory" property="portletDeginitions">
                    <tr>
                        <!--td class="formTitle"><bean:write name="portletDefinition" property="name"/></td-->
                        <td class="formTitle"><bean:write name="portletDefinition" property="displayName(zh)"/></td>
                    </tr>
                </logic:iterate>
            </logic:iterate>
        </table></body>
    </html>
