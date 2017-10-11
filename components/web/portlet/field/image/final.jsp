<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<bean:define id="type" name="preferences" property="value(type)" type="java.lang.String" />
<bean:define id="preferences" name="preferences" type="javax.portlet.PortletPreferences" />
<%String picNum= preferences.getValue("picNum","");
boolean scalingByRatio = "true".equals(preferences.getValue(("scalingByRatio"),"false"));
String imgWidth = preferences.getValue("width","0");
String imgHeight = preferences.getValue("height","0");
String target = preferences.getValue("target","");
if(target==null||target.equals("")){
	  target = preferences.getValue("newWindows","_blank");
}
%>
<ul id='<%=(String)request.getAttribute("javax.portlet.id")%>' <logic:notEmpty name="preferences" property="value(style)">class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty>>

	<logic:present name="paths">
	<logic:iterate id="path" name="paths" length="<%=picNum%>" indexId="indexTemp">
	<li>
	<logic:notEqual value="item" name="type">
	<a target="<%=target%>" <logic:equal value="index" name="type"> href="<bean:write name="preferences" property="value(channel)"/>?contentId=<bean:write name="oNode" property="ID"/>"></logic:equal>
<logic:notEqual value="index" name="type"> href="<site:nodeURL name="oNode" type="<%=type%>"/>"></logic:notEqual>
<logic:notEmpty name="path"><img id='<%=(String)request.getAttribute("javax.portlet.id")+"_"+indexTemp %>' src="<bean:write name="path" ignore="true"/>" <%if(scalingByRatio){%>onload="scalingImg(this,'<%= imgWidth%>','<%= imgHeight%>')"<%;}else{%>
 <logic:notEmpty name="preferences" property="value(width)">width="<bean:write name="preferences" property="value(width)"/>"</logic:notEmpty>
 <logic:notEmpty name="preferences" property="value(height)">height="<bean:write name="preferences" property="value(height)"/>"</logic:notEmpty><%}%> 
 <logic:notEmpty name="preferences" property="value(align)">align="<bean:write name="preferences" property="value(align)"/>"</logic:notEmpty>
 <logic:notEmpty name="preferences" property="value(border)">border="<bean:write name="preferences" property="value(border)"/>"</logic:notEmpty>
  <logic:notEmpty name="preferences" property="value(hasAlt)"><logic:equal value="false" name="preferences" property="value(hasAlt)">alt="<bean:write name="alt" ignore="true"/>"</logic:equal></logic:notEmpty> /></logic:notEmpty></a>
	</logic:notEqual>
	<logic:equal value="item" name="type"><logic:notEmpty name="path">
  	<img id='<%=(String)request.getAttribute("javax.portlet.id")+"_"+indexTemp %>' src="<bean:write name="path" ignore="true"/>"  <%if(scalingByRatio){%>onload="scalingImg(this,'<%= imgWidth%>','<%= imgHeight%>')"<%;}else{%> <logic:notEmpty name="preferences" property="value(width)">width="<bean:write name="preferences" property="value(width)"/>"</logic:notEmpty><logic:notEmpty name="preferences" property="value(height)"> height="<bean:write name="preferences" property="value(height)"/>"</logic:notEmpty><%}%> <logic:notEmpty name="preferences" property="value(align)"> align="<bean:write name="preferences" property="value(align)"/>"</logic:notEmpty><logic:notEmpty name="preferences" property="value(border)"> border="<bean:write name="preferences" property="value(border)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(hasAlt)"><logic:equal value="false" name="preferences" property="value(hasAlt)">alt="<bean:write name="alt" ignore="true"/>"</logic:equal></logic:notEmpty> /></logic:notEmpty></logic:equal>
	</li>
	</logic:iterate>
	</logic:present>
	<logic:notPresent name="paths">
	<li>
	<logic:notEqual value="item" name="type">
	<a target="<%=target%>" <logic:equal value="index" name="type"> href="<bean:write name="preferences" property="value(channel)"/>?contentId=<bean:write name="oNode" property="ID"/>"></logic:equal>
<logic:notEqual value="index" name="type"> href="<site:nodeURL name="oNode" type="<%=type%>"/>"></logic:notEqual>
<img src="<bean:write name="preferences" property="value(defaultImage)"/>"  <%if(scalingByRatio){%>onload="scalingImg(this,'<%= imgWidth%>','<%= imgHeight%>')"<%;}else{%>
 <logic:notEmpty name="preferences" property="value(width)"> width="<bean:write name="preferences" property="value(width)"/>"</logic:notEmpty>
 <logic:notEmpty name="preferences" property="value(height)"> height="<bean:write name="preferences" property="value(height)"/>"</logic:notEmpty><%}%> 
 <logic:notEmpty name="preferences" property="value(align)"> align="<bean:write name="preferences" property="value(align)"/>"</logic:notEmpty>
 <logic:notEmpty name="preferences" property="value(border)"> border="<bean:write name="preferences" property="value(border)"/>"</logic:notEmpty>
  <logic:notEmpty name="preferences" property="value(hasAlt)"><logic:equal value="false" name="preferences" property="value(hasAlt)">alt="<bean:write name="alt" ignore="true"/>"</logic:equal></logic:notEmpty> /></a>
	</logic:notEqual>
	<logic:equal value="item" name="type">
  <img src="<bean:write name="preferences" property="value(defaultImage)"/>"  <%if(scalingByRatio){%>onload="scalingImg(this,'<%= imgWidth%>','<%= imgHeight%>')"<%;}else{%> 
  <logic:notEmpty name="preferences" property="value(width)">width="<bean:write name="preferences" property="value(width)"/>"</logic:notEmpty><logic:notEmpty name="preferences" property="value(height)">height="<bean:write name="preferences" property="value(height)"/>"</logic:notEmpty><%}%> <logic:notEmpty name="preferences" property="value(align)">align="<bean:write name="preferences" property="value(align)"/>"</logic:notEmpty><logic:notEmpty name="preferences" property="value(border)">border="<bean:write name="preferences" property="value(border)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(hasAlt)"><logic:equal value="false" name="preferences" property="value(hasAlt)">alt="<bean:write name="alt" ignore="true"/>"</logic:equal></logic:notEmpty> /></logic:equal>
	</li>
	</logic:notPresent>
  </ul>