<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<object id="player" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" name="player" <logic:notEmpty name="preferences" property="value(width)">width="<bean:write name="preferences" property="value(width)"/>"</logic:notEmpty>  <logic:notEmpty name="preferences" property="value(height)">height="<bean:write name="preferences" property="value(height)"/>"</logic:notEmpty> id="<bean:write name="contentId"/><bean:write name="preferences" property="value(field)"/>" <logic:notEmpty name="preferences" property="value(style)">class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(align)">align="<bean:write name="preferences" property="value(align)"/>"</logic:notEmpty>>
		<param name="movie" value="/components/portlet/field/flash/player.swf" />
		<param name="allowfullscreen" value="true" />
		<param name="allowscriptaccess" value="always" />
		<param name="flashvars" value="file=<bean:write name="pathValue"/><logic:notEqual value="false" name="preferences" property="value(loop)">&repeat=always</logic:notEqual><logic:notEqual value="false" name="preferences" property="value(play)">&autostart=true</logic:notEqual><logic:notEqual value="Low" name="preferences" property="value(quality)">&quality=false</logic:notEqual>" />
   <!--<logic:notEmpty name="preferences" property="value(salign)"> <param name="salign" value="<bean:write name="preferences" property="value(salign)"/>"></logic:notEmpty>
   <logic:notEmpty name="preferences" property="value(scale)"> <param name="scale" value="<bean:write name="preferences" property="value(scale)"/>"></logic:notEmpty>
		--><embed
			type="application/x-shockwave-flash"
			id="player"
			name="player"
			src="/components/portlet/field/flash/player.swf" 
			<logic:notEmpty name="preferences" property="value(width)">width="<bean:write name="preferences" property="value(width)"/>"</logic:notEmpty>  <logic:notEmpty name="preferences" property="value(height)">height="<bean:write name="preferences" property="value(height)"/>"</logic:notEmpty>
			allowscriptaccess="always" 
			allowfullscreen="true"
			flashvars="file=<bean:write name="pathValue"/><logic:notEqual value="false" name="preferences" property="value(loop)">&repeat=always</logic:notEqual><logic:notEqual value="false" name="preferences" property="value(play)">&autostart=true</logic:notEqual><logic:notEqual value="Low" name="preferences" property="value(quality)">&quality=false</logic:notEqual>" />
</object>