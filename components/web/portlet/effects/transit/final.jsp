<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="pt"%>
<bean:define id="preferences" name="preferences" type="javax.portlet.PortletPreferences"/>
<% String toURL = preferences.getValue("channel", "index.jsp");
%>
<script language="JavaScript" type="text/Javascript">
var prop = '<bean:write name="propName" ignore="true" />';
var propValue = '<bean:write name="value" ignore="true" />';
if(prop!=null&&prop!=""){
	window.location='<%=toURL%>' + '?' + prop + '=' + encodeURIComponent(propValue);
}else{
	window.location='<%=toURL%>';
}

</script>