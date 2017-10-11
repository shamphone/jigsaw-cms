<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<a><logic:equal value="name" name="preferences" property="value(showWhat)">文件名</logic:equal>
    <logic:notEqual value="name" name="preferences" property="value(showWhat)">
        <logic:notEmpty name="preferences" property="value(text)"><bean:write name="preferences" property="value(text)" filter="false"/></logic:notEmpty>
        <logic:empty name="preferences" property="value(text)"><font color="red">未设置下载文字</font></logic:empty>
    </logic:notEqual></a>