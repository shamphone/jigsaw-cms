<%@ page contentType="text/html; charset=UTF-8" %><%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %><%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<bean:define id="preferences" name="preferences" type="javax.portlet.PortletPreferences"/>
<bean:define id="propertyId" name="preferences" property="value(propertyId)"/>
<img id="img_<%=request.getAttribute("javax.portlet.id")%>" style="cursor:hand" id="authCodeImage" border=0 height='<bean:write name="height"/>' width='<bean:write name="preferences" property="value(width)" ignore="true"/>'/>
<script type="text/javascript">
	var numeral_<%=request.getAttribute("javax.portlet.id")%> = document.getElementById('<bean:write name="propertyId" ignore="true"/>');
	if(numeral_<%=request.getAttribute("javax.portlet.id")%>!=null){
		var code = numeral_<%=request.getAttribute("javax.portlet.id")%>.innerHTML.trim();
		var url = "/components/portlet/effects/imageWeave/authCodeImage.jsp?code="+code;
		url += '&name=<bean:write name="preferences" property="value(name)" ignore="true"/>';
		url += '&charWidth=<bean:write name="charWidth"/>';
		document.getElementById('img_<%=request.getAttribute("javax.portlet.id")%>').src = encodeURI(url);
		<logic:empty name="preferences" property="value(width)">
			document.getElementById('img_<%=request.getAttribute("javax.portlet.id")%>').width = code.length*<bean:write name="charWidth"/>;
		</logic:empty>
	}
</script>




