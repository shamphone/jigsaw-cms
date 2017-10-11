<%@page contentType="text/html; charset=UTF-8"%><%@taglib
	uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib
	uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib
	uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib
	uri="/WEB-INF/fulong-portal.tld" prefix="pt"%>
<logic:notEqual value="on" name="preferences" property="value(showHref)">
	<logic:empty name="preferences" property="value(imgsrc)">
		<button type="button"
			<logic:notEmpty name="preferences" property="value(style)">class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty>
			<logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty>
			<logic:notEmpty name="preferences" property="value(size)">size="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty>
			onclick="EditButton.GotoChannel('<bean:write name="preferences" property="value(channel)"/>','<bean:write name="oNode" property="ID" ignore="true"/>','<bean:write name="preferences" property="value(openInNewWindow)" ignore="true"/>')"
			<logic:notEmpty name="preferences" property="value(size)">size="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty>><logic:notEmpty
			name="preferences" property="value(value)">
			<bean:write name="preferences" property="value(value)" />
		</logic:notEmpty></button>
	</logic:empty>
	<logic:notEmpty name="preferences" property="value(imgsrc)">
		<image 
			<logic:notEmpty name="preferences" property="value(imgtitle)">title="<bean:write name="preferences" property="value(imgtitle)"/>"</logic:notEmpty>
			<logic:notEmpty name="preferences" property="value(imgstyle)">class="<bean:write name="preferences" property="value(imgstyle)"/>"</logic:notEmpty>
			<logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty>
			onclick="EditButton.GotoChannel('<bean:write name="preferences" property="value(channel)"/>','<bean:write name="oNode" property="ID" ignore="true"/>','<bean:write name="preferences" property="value(openInNewWindow)" ignore="true"/>');return false;"
			<logic:notEmpty name="preferences" property="value(imgsrc)">src="<bean:write name="preferences" property="value(imgsrc)"/>"</logic:notEmpty>
			<logic:notEmpty name="preferences" property="value(imghwidth)">width="<bean:write name="preferences" property="value(imgwidth)"/>"</logic:notEmpty>
			<logic:notEmpty name="preferences" property="value(imgheight)">height="<bean:write name="preferences" property="value(imgheight)"/>"</logic:notEmpty> />
	</logic:notEmpty>
</logic:notEqual>
<logic:equal value="on" name="preferences" property="value(showHref)">
		<a
			href="javascript:EditButton.GotoChannel('<bean:write name="preferences" property="value(channel)"/>','<bean:write name="oNode" property="ID" ignore="true"/>','<bean:write name="preferences" property="value(openInNewWindow)" ignore="true"/>')"
			<logic:notEmpty name="preferences" property="value(style)"> class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty>
			<logic:notEmpty name="preferences" property="value(tabindex)"> tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty>><bean:write
			name="preferences" property="value(value)" /></a>
</logic:equal>