<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title><bean:write name="com.fulong.system" property="property(system.name)"/></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="Expires" CONTENT="Mon, 04 Dec 2999 21:29:02 GMT" />
        <link rel="stylesheet" type="text/css" href="style/header.css" />
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/header.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/date.js"/>"></script>
        <bean:parameter id="selectedModuleId" name="module" value="desk"/>
        <base target="_top">
        </head>
        <body onload="MM_preloadImages('images/lm1.gif','images/lm2_2.gif','images/lm3_1.gif','images/lm4_1.gif')"><table width="100%" border="0" align="right" cellpadding="0" cellspacing="0" id="headerMainTable">
            <tr align="right">
                <td  width="92%" align="right">欢迎<fulong:name name="principal" simple="false"/></td>
                <td  align="right" valign="bottom">&nbsp;&nbsp;<a onclick="clrCookie()" href="<html:rewrite page='/security/j_spring_security_logout' module=''/>">
                    <img border="0" src="images/exit.gif" width="27" height="21" /><span style="vertical-align:super;TEXT-DECORATION:none">&nbsp;&nbsp;登录</span>
                </a>
            </td>
        </tr>
    </table>
</body>
</html>

