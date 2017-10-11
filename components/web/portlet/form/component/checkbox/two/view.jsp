<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<ul <logic:notEmpty name="preferences" property="value(firstStyle)">class="<bean:write name="preferences" property="value(firstStyle)"/>"</logic:notEmpty>>
<li><input type="checkbox" checked="checked" />一级选项</li>
<ul <logic:notEmpty name="preferences" property="value(secondStyle)">class="<bean:write name="preferences" property="value(secondStyle)"/>"</logic:notEmpty>>
<li><input type="checkbox" checked="checked" />二级选项1</li>
    <li><input type="checkbox" />二级选项2</li>
    <li><input type="checkbox" />二级选项3</li>
    <li><input type="checkbox" />二级选项4</li>
    <li><input type="checkbox" />二级选项5</li>
    <li><input type="checkbox" />二级选项6</li>
</ul>
</ul>