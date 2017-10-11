<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<ul <logic:notEmpty name="preferences" property="value(style)">class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty>>
  <logic:equal value="horizontal" name="preferences" property="value(arrangement)">
    <li style="float:left"><input type="checkbox" checked="checked" />选项1</li>
    <li style="float:left"><input type="checkbox" />选项2</li>
    <li style="float:left"><input type="checkbox" />选项3</li>
    <li style="float:left"><input type="checkbox" />选项4</li>
    <li style="float:left"><input type="checkbox" />选项5</li>
    <li style="float:left"><input type="checkbox" />选项6</li>
  </logic:equal>
  <logic:equal value="vertical" name="preferences" property="value(arrangement)">
    <li><input type="checkbox" checked="checked" />选项1</li>
    <li><input type="checkbox" />选项2</li>
    <li><input type="checkbox" />选项3</li>
    <li><input type="checkbox" />选项4</li>
    <li><input type="checkbox" />选项5</li>
    <li><input type="checkbox" />选项6</li>
  </logic:equal>
</ul>
