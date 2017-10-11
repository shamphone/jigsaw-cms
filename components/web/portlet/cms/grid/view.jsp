<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<bean:define id="styles" name="preferences" property="values(column-styles)" type="java.lang.String[]"/>
<bean:define id="fields" name="preferences" property="values(column-fields)" type="java.lang.String[]"/>
<bean:define id="formats" name="preferences" property="values(column-formats)" type="java.lang.String[]"/>
<bean:define id="tables" name="preferences" property="value(table)" type="java.lang.String"/>
<bean:define id="rows" name="preferences" property="value(row)" type="java.lang.String"/>
<bean:define id="columnTypes" name="preferences" property="values(column-types)" type="java.lang.String[]"/>
<bean:define id="columnCount" name="preferences" property="value(column)" type="java.lang.String"/>
<bean:define id="target" name="preferences" property="value(newWindows)" type="java.lang.String"/>

<table id="<bean:write name='javax.portlet.id'/>" class="<bean:write name="preferences" property="value(table-style)" ignore="true"/>" cellspacing="0" cellpadding="0" border="1">
<!-- 表头 -->
<logic:equal value="true" name="preferences" property="value(show-header)">
  <thead class="<bean:write name="preferences" property="value(header-style)" ignore="true"/>">
  <tr>
    <fulong:for id="table" name="preferences" property="value(table)">
    <logic:equal value="true" name="preferences" property="value(show-index)">
      <th align="center">序号</th>
    </logic:equal>
    <logic:iterate id='header' name="preferences" property="values(column-headers)" length="<%= columnCount %>">
      <th><bean:write name='header' ignore="true"/></th>
    </logic:iterate>
    <logic:equal value="true" name="preferences" property="value(optionColumn)">
      <th><bean:write name="preferences" property="value(optionColumnWord)" ignore="true"/></th>
    </logic:equal>
  </fulong:for></tr>
</thead>
</logic:equal>

<!-- 表中内容 -->
<fulong:for id="row" name="preferences" property="value(row)"><tr>
  <fulong:for id="table" name="preferences" property="value(table)">
    <logic:equal value="true" name="preferences" property="value(show-index)">
      <td align="center">
        <%=row%>
      </td>
    </logic:equal>
    <logic:iterate id="content" name="contents" offset='<%= ""+(Integer.parseInt(tables)*row.intValue()+table.intValue())%>' length="1">
      <fulong:for id="column" name="preferences" property="value(column)">
        <td>
          <cms:field target="<%=target%>" name="content" propertyName="<%=fields[column.intValue()]%>" type="<%=columnTypes[column.intValue()]%>"/>
        </td>
        </fulong:for>
    </logic:iterate>
      </fulong:for>
  </tr></fulong:for>
  <!-- 表尾 -->
  <logic:equal value="true" name="preferences" property="value(show-footer)">
    <tfoot class="<bean:write name="preferences" property="value(footer-style)" ignore="true"/>">
    <tr>
    <fulong:for id="table" name="preferences" property="value(table)">
    <logic:equal value="true" name="preferences" property="value(optionColumn)">
      <logic:equal name="preferences" property="value(multiSelect)" value="true">
        <td><input type="checkbox"  onclick="selectAll()"/></td>
      </logic:equal>
    </logic:equal>
      <logic:equal value="true" name="preferences" property="value(show-index)">
        <td align="center">序号</td>
      </logic:equal>
      <logic:iterate id='footer' name="preferences" property="values(column-footers)" length="<%= columnCount %>">
        <td><bean:write name='footer' ignore="true"/></td>
      </logic:iterate></fulong:for>
    </tr>
  </tfoot>
</logic:equal>
</table>
<logic:equal value="true" name="preferences" property="value(show-pager)">
  <div class="<bean:write name='preferences' property="value(pager-style)" ignore="true"/>">首页，上一页，下一页，末页</div>
</logic:equal>
