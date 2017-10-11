<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<input name="<bean:write name="preferences" property="value(referenceId)" ignore="true"/>" type="radio" checked="checked" />选项1
<input type="radio" />选项2
<input type="radio" />选项3
<input type="radio" />选项4
<input type="radio" />选项5
<input type="radio" />选项6