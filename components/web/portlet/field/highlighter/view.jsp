<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<logic:present name="property">这里是<bean:write name="property" property="name" ignore="true"/></logic:present>
<logic:notPresent name="property">未定义内容域</logic:notPresent>
