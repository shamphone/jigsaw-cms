<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<div>来自页面片断<bean:write name="preferences" property="value(source)"/>的内容</div>

