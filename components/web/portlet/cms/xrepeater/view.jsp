<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portal"%><%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<table id="<bean:write name='javax.portlet.id'/>" class="<bean:write name="preferences" property="value(table-style)" ignore="true"/>" cellspacing="0" cellpadding="1" border="0" width="100%">
<bean:define id="definitionId" name="preferences" property="value(category)" type="java.lang.String"/>
<bean:define id="columnCount" name="preferences" property="value(column)" type="java.lang.String"/>
<bean:define id="path" name="path" type="java.lang.String"/>
<bean:define id="contextName" name="contextName" type="java.lang.String"/>
<logic:notEmpty name="preferences" property="value(row)">
<bean:define id="rowCount" name="preferences" property="value(row)" type="java.lang.String"/>
<fulong:for id="row" name="preferences" property="value(row)"><tr>
  <fulong:for id="column" name="preferences" property="value(column)">
      <td>
        <site:insert page='<%= path +"?portlet.mode=final&definition="+ definitionId %>' contextName="<%= contextName %>" flush="false"></site:insert>
      </td>
  </fulong:for>
</tr>
</fulong:for>
</logic:notEmpty>
<logic:empty name="preferences" property="value(row)">
    <tr>
      <fulong:for id="column" name="preferences" property="value(column)">
      <td>
        <site:insert page='<%= path +"?portlet.mode=final&definition="+ definitionId %>' contextName="<%= contextName %>" flush="false"></site:insert>
      </td>
      </fulong:for>
    </tr>
</logic:empty>
</table>
<logic:equal value="true" name="preferences" property="value(show-pager)">
    <div class="<bean:write name='preferences' property="value(pager-style)" ignore="true"/>">共计100项内容，当前为3/5页，<a href="#">首页</a>，<a href="#">上一页</a>，<a href="#">下一页</a>，<a href="#">末页</a></div>
</logic:equal>
