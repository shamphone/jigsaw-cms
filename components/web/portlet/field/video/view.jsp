<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<img src="/ide/common/images/doctype/wmv.gif" <logic:notEmpty name="preferences" property="value(width)">width="<bean:write name="preferences" property="value(width)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(height)">height="<bean:write name="preferences" property="value(height)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(border)">border="<bean:write name="preferences" property="value(border)"/>"</logic:notEmpty> />