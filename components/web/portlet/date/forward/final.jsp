<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="pt"%><pt:actionURL portletMode="final" var="formURL"><pt:param name="portlet_action_name">saveNode</pt:param> </pt:actionURL>
<logic:notEqual value="on"  name="preferences" property="value(showHref)">
<logic:empty name="preferences" property="value(imgsrc)"><button type="button" <logic:notEmpty name="preferences" property="value(style)">class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty><logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(size)">size="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty> onclick="GotoChannel('<bean:write name="preferences" property="value(channel)"/>','<bean:write name="date" ignore="true"/>','<bean:write name="preferences" property="value(openInNewWindow)" ignore="true"/>')"   <logic:notEmpty name="preferences" property="value(size)">size="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty> ><logic:notEmpty name="preferences" property="value(value)"><bean:write name="preferences" property="value(value)"/></logic:notEmpty></button></logic:empty>
<logic:notEmpty name="preferences" property="value(imgsrc)"><image <logic:notEmpty name="preferences" property="value(imgstyle)">class="<bean:write name="preferences" property="value(imgstyle)"/>"</logic:notEmpty><logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty> onclick="GotoChannel('<bean:write name="preferences" property="value(channel)"/>','<bean:write name="date" ignore="true"/>','<bean:write name="preferences" property="value(openInNewWindow)" ignore="true"/>')" <logic:notEmpty name="preferences" property="value(imgsrc)">src="<bean:write name="preferences" property="value(imgsrc)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(imghwidth)">width="<bean:write name="preferences" property="value(imgwidth)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(imgheight)">height="<bean:write name="preferences" property="value(imgheight)"/>"</logic:notEmpty>/></logic:notEmpty>
</logic:notEqual>
<bean:define id="path" name="preferences" property="value(channel)" type="java.lang.String"/>
<bean:define id="date" name="date"/>
<logic:equal value="on"  name="preferences" property="value(showHref)">
<logic:equal value="false"  name="preferences" property="value(openInNewWindow)">
<a href="<%=path%>?dateParameter=<%=date%>" <logic:notEmpty name="preferences" property="value(style)">class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty>><bean:write name="preferences" property="value(value)"/></a>
</logic:equal>
<logic:notEqual value="false"  name="preferences" property="value(openInNewWindow)">
<a href="<%=path%>?dateParameter=<%=date%>" target="_blank" <logic:notEmpty name="preferences" property="value(style)">class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty>><bean:write name="preferences" property="value(value)"/></a>
</logic:notEqual>
</logic:equal>
<script type="text/javascript" language="javascript">
function GotoChannel(channel,date,openInWindow){
	if(openInWindow!=null&&openInWindow!=""){
		if(openInWindow=="false"){
			document.location=channel+'?dateParameter='+date;
		}else{
			window.open(channel+'?dateParameter='+date);
		}
	}else{
		document.location=channel+'?dateParameter='+date;
	}
}
</script>