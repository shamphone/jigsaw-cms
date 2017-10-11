<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<bean:define id="preferences" name="preferences" type="javax.portlet.PortletPreferences"/>
<%int num = 0;
int num1 = 0;
num = Integer.valueOf(request.getAttribute("size").toString()).intValue();
num1 = num-1;
String propid = preferences.getValue("showField","");
String field = preferences.getValue("field","");
%>
<logic:iterate id="category" name="categories"  indexId="index">
<span id='<%=(String)request.getAttribute("javax.portlet.id")+index.intValue()%>' class='<bean:write name="preferences" property="value(middle-style)"/>'>
<logic:equal value="index" name="preferences" property="value(type)">
<logic:equal name="preferences" property="value(isContentId)" value="true">
<a target='<bean:write name="preferences" property="value(openMode)" ignore="true"/>' href="<%=request.getContextPath()%>/<bean:write name="preferences" property="value(channel)" ignore="true"/>?contentId=<bean:write name="category" property="ID" ignore="true"/>">
</logic:equal>
<logic:notEqual name="preferences" property="value(isContentId)" value="true">
<a target='<bean:write name="preferences" property="value(openMode)" ignore="true"/>' href="<%=request.getContextPath()%>/<bean:write name="preferences" property="value(channel)" ignore="true"/>?fileterField=<%=field %>&filterValue=<cms:node name="category" propertyName="<%=field %>" ignore="true"/>" >
</logic:notEqual>
</logic:equal>
<cms:node name="category" propertyName='<%=propid %>'/></a></span>
<%if(index.intValue() != num1 ){ %>
<span class='<bean:write name="preferences" property="value(seperator-style)"/>'><bean:write name="preferences" property="value(seperator)"/></span>
<%}else { %>
<span class='<bean:write name="preferences" property="value(end-style)"/>'><cms:node name="category" propertyName='<%=propid %>'/></span>
<%} %>
</logic:iterate>
