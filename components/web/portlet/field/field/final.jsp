<%@ page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<bean:define id="preferences" name="preferences" type="javax.portlet.PortletPreferences"/>
<bean:define id="type" name="preferences" property="value(type)" type="java.lang.String" />
<%String hasNoSpan = preferences.getValue("hasNoSpan","false");
  String target = preferences.getValue("target","");
  if(target==null||target.equals("")){
	  target = preferences.getValue("newWindows","_blank");
  }
%>
<logic:notEqual value="item" name="type">
	<a id='<%=request.getAttribute("javax.portlet.id")%>' target="<%=target%>" title="<bean:write name="title" ignore="true"/>" href='<bean:write name="forwardURL" ignore="true"/>'><bean:write name="value" ignore="true" /></a>
</logic:notEqual>
<logic:equal value="item" name="type">
<%if(hasNoSpan.equals("false")){%>
	<span id='<%=request.getAttribute("javax.portlet.id")%>'><bean:write name="value" ignore="true" filter="false" /></span>
<%}else{%>
	<bean:write name="value" ignore="true" filter="false" />
<%}%>
</logic:equal>