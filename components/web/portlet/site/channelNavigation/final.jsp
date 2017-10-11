<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<logic:present name="preferences" property="value(discription)">
<bean:define id="description" name="preferences" property="value(discription)" type="java.lang.String"/>
<%
if(description!=null&&!description.equals("")){%>
<span class='<bean:write name="preferences" property="value(head-style)"/>'><bean:write name="preferences" property="value(discription)"/></span>
<%}%>
</logic:present>
<%int num = 0;
int num1 = 0;
num = Integer.valueOf(request.getAttribute("size").toString()).intValue();
num1 = num-1;
if(num1>0){
%>
<logic:iterate id="channel" name="channels" length='<%=""+num1%>'><span class='<bean:write name="preferences" property="value(middle-style)"/>'><a target="<bean:write name="preferences" property="value(newWindows)"/>" href='<bean:write name="channel" property="name" ignore="true"/>.jsp'><bean:write name="channel" property="displayName" ignore="true"/></a></span><span class='<bean:write name="preferences" property="value(seperator-style)"/>'><bean:write name="preferences" property="value(seperator)" filter="false"/></span></logic:iterate><logic:iterate id="channel" name="channels"><span class='<bean:write name="preferences" property="value(end-style)"/>'><bean:write name="channel" property="displayName" ignore="true"/></span></logic:iterate>
<%}else{%>
<logic:iterate id="channel" name="channels"><span class='<bean:write name="preferences" property="value(end-style)"/>'><bean:write name="channel" property="displayName" ignore="true"/></span></logic:iterate>
<%} %>
