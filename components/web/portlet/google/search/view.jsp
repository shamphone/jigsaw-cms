<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<input type="text" disabled="disabled" <logic:notEmpty name="preferences" property="value(textStyle)">class="<bean:write name="preferences" property="value(textStyle)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(textSize)">size="<bean:write name="preferences" property="value(textSize)"/>"</logic:notEmpty> name="<bean:write name="preferences" property="value(fieldName)" ignore="true"/>"/>
<input type="submit" <logic:notEmpty name="preferences" property="value(buttonName)"> value="<bean:write name="preferences" property="value(buttonName)"/>"</logic:notEmpty><logic:notEmpty name="preferences" property="value(buttonStyle)"> class="<bean:write name="preferences" property="value(buttonStyle)"/>"</logic:notEmpty> disabled/>

