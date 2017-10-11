<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="pt"%>
<!--Google站内搜索开始-->
<form method=get action="http://www.google.com/search" target="_blank">
<input type="text" name=q <logic:notEmpty name="preferences" property="value(textStyle)"> class="<bean:write name="preferences" property="value(textStyle)"/>"</logic:notEmpty><logic:notEmpty name="preferences" property="value(textSize)"> size="<bean:write name="preferences" property="value(textSize)"/>"</logic:notEmpty><logic:notEmpty name="preferences" property="value(textMaxLength)"> maxLength="<bean:write name="preferences" property="value(textMaxLength)"/>"</logic:notEmpty>>
<input type="submit" name="btnG" <logic:notEmpty name="preferences" property="value(buttonName)"> value="<bean:write name="preferences" property="value(buttonName)"/>"</logic:notEmpty><logic:notEmpty name="preferences" property="value(buttonStyle)"> class="<bean:write name="preferences" property="value(buttonStyle)"/>"</logic:notEmpty>>
<input type=hidden name=ie value=UTF-8>
<input type=hidden name=oe value=UTF-8>
<input type=hidden name=hl value=zh-CN>
<input type=hidden name=domains <logic:notEmpty name="preferences" property="value(buttonName)"> value="<bean:write name="preferences" property="value(fieldName)"/>"</logic:notEmpty>>
<input type=hidden name=sitesearch <logic:notEmpty name="preferences" property="value(buttonName)"> value="<bean:write name="preferences" property="value(fieldName)"/>"</logic:notEmpty>>
</form>
<!--Google站内搜索结束-->