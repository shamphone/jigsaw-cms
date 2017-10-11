<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
  <ul <logic:notEmpty name="preferences" property="value(style)">class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty>>
    <li><input name="<bean:write name="preferences" property="value(referenceId)" ignore="true"/>" type="radio" checked="checked" />选项1</li>
    <li><input type="radio" />选项2</li>
    <li><input type="radio" />选项3</li>
    <li><input type="radio" />选项4</li>
    <li><input type="radio" />选项5</li>
    <li><input type="radio" />选项6</li>
  </ul>