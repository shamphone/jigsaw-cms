<%@ page contentType="text/html; charset=UTF-8" %><%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %><%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<logic:empty  name="preferences" property="value(tips)">点击设置数据校验和信息提示。</logic:empty><bean:write name="preferences" property="value(tips)"/>
