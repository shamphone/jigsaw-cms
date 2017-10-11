<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<logic:present name="preferences" property="value(allowWrite)">
	<logic:equal value="false" name="preferences" property="value(allowWrite)">
		<input onblur="Validator.ValidateComponent(this, this.name)" readonly="readonly" <logic:equal value="on" name="preferences" property="value(submit)"> onpropertychange="if (event.propertyName=='value') this.form.submit()"</logic:equal> type="text" <logic:notEmpty name="calendar">value="<bean:write name="calendar"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(textStyle)">class="<bean:write name="preferences" property="value(textStyle)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(textSize)">size="<bean:write name="preferences" property="value(textSize)"/>"</logic:notEmpty> name="<bean:write name="preferences" property="value(propertyId)" ignore="true"/>"/><button type="button" <logic:notEmpty name="preferences" property="value(buttonStyle)">class="<bean:write name="preferences" property="value(buttonStyle)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty> onclick="showCalendarComponent(this, <logic:notEqual name="preferences" property="value(hasTime)" value="on">'%Y-%m-%d', null</logic:notEqual><logic:equal name="preferences" property="value(hasTime)" value="on">'%Y-%m-%d %H:%M:%S', '24'</logic:equal>, true);"><bean:write name="preferences" property="value(buttonWord)" ignore="true"/></button>
	</logic:equal>
	<logic:notEqual value="false" name="preferences" property="value(allowWrite)">
		<input onblur="Validator.ValidateComponent(this, this.name)" <logic:equal value="on" name="preferences" property="value(submit)"> onpropertychange="if (event.propertyName=='value') this.form.submit()"</logic:equal> type="text" <logic:notEmpty name="calendar">value="<bean:write name="calendar"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(textStyle)">class="<bean:write name="preferences" property="value(textStyle)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(textSize)">size="<bean:write name="preferences" property="value(textSize)"/>"</logic:notEmpty> name="<bean:write name="preferences" property="value(propertyId)" ignore="true"/>"/><button type="button" <logic:notEmpty name="preferences" property="value(buttonStyle)">class="<bean:write name="preferences" property="value(buttonStyle)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty> onclick="showCalendarComponent(this, <logic:notEqual name="preferences" property="value(hasTime)" value="on">'%Y-%m-%d', null</logic:notEqual><logic:equal name="preferences" property="value(hasTime)" value="on">'%Y-%m-%d %H:%M:%S', '24'</logic:equal>, true);"><bean:write name="preferences" property="value(buttonWord)" ignore="true"/></button>
	</logic:notEqual>
</logic:present>
<logic:notPresent name="preferences" property="value(allowWrite)">
	<input onblur="Validator.ValidateComponent(this, this.name)" readonly="readonly" <logic:equal value="on" name="preferences" property="value(submit)"> onchange="if (event.propertyName=='value') this.form.submit()"</logic:equal> type="text" <logic:notEmpty name="calendar">value="<bean:write name="calendar"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(textStyle)">class="<bean:write name="preferences" property="value(textStyle)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(textSize)">size="<bean:write name="preferences" property="value(textSize)"/>"</logic:notEmpty> name="<bean:write name="preferences" property="value(propertyId)" ignore="true"/>"/><button type="button" <logic:notEmpty name="preferences" property="value(buttonStyle)">class="<bean:write name="preferences" property="value(buttonStyle)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty> onclick="showCalendarComponent(this, <logic:notEqual name="preferences" property="value(hasTime)" value="on">'%Y-%m-%d', null</logic:notEqual><logic:equal name="preferences" property="value(hasTime)" value="on">'%Y-%m-%d %H:%M:%S', '24'</logic:equal>, true);"><bean:write name="preferences" property="value(buttonWord)" ignore="true"/></button>
</logic:notPresent>