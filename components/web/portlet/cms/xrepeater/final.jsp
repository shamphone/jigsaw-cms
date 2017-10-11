<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%><%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<logic:equal value="true" name="preferences" property="value(showPage)">
<logic:equal value="true" name="preferences" property="value(show-pager)">
	<div class="<bean:write name='preferences' property="value(pager-style)" ignore="true"/>"><portlet:pager name="pager" pattern="default" /></div>
</logic:equal>
   <logic:equal value="isGotoPager" name="preferences" property="value(show-pager)">
	<div class="<bean:write name='preferences' property="value(pager-style)" ignore="true"/>"><portlet:pager name="pager" pattern="goto" /></div>
</logic:equal>
</logic:equal>
<table id="<bean:write name='javax.portlet.id'/>"<logic:notEmpty name="preferences" property="value(cellspacing)"> cellspacing="<bean:write name="preferences" property="value(cellspacing)" ignore="true"/>"</logic:notEmpty><logic:notEmpty  name="preferences" property="value(cellpadding)"> cellpadding="<bean:write name="preferences" property="value(cellpadding)" ignore="true"/>"</logic:notEmpty><logic:notEmpty name="preferences" property="value(width)"> width="<bean:write name='preferences' property='value(width)'/>"</logic:notEmpty><logic:notEmpty name="preferences" property="value(height)"> height="<bean:write name="preferences" property="value(height)" ignore="true"/>"</logic:notEmpty><logic:notEmpty name="preferences" property="value(border)"> border="<bean:write name="preferences" property="value(border)" ignore="true"/>"</logic:notEmpty><logic:notEmpty name="preferences" property="value(bordercolor)"> border="<bean:write name="preferences" property="value(bordercolor)" ignore="true"/>"</logic:notEmpty><logic:notEmpty name="preferences" property="value(style)"> style="<bean:write name="preferences" property="value(style)" ignore="true"/>"</logic:notEmpty><logic:notEmpty  name="preferences" property="value(table-style)"> class="<bean:write name="preferences" property="value(table-style)" ignore="true"/>"</logic:notEmpty>>
<bean:define id="columnCount" name="preferences" property="value(column)" type="java.lang.String"/>
<bean:define id="path" name="path" type="java.lang.String"/>
<logic:notEmpty name="preferences" property="value(row)">
<bean:define id="rowCount" name="preferences" property="value(row)" type="java.lang.String"/>
<fulong:for id="row" name="preferences" property="value(row)"><tr>
  <fulong:for id="column" name="preferences" property="value(column)">
    <logic:iterate indexId="index" id="content" name="contents" offset="<%=String.valueOf(row.intValue()*Integer.parseInt(columnCount)+column.intValue())%>" length="1">
    <%request.setAttribute("com.fulong.longcon.Content",content);request.setAttribute("indexId",index); %>
      <td id='<bean:write name="content" property="ID" ignore="true"/>'><site:insert page='<%=path%>' flush="false"></site:insert></td>
      <%request.setAttribute("com.fulong.longcon.Content",null); request.setAttribute("indexId",null); %>
    </logic:iterate>
  </fulong:for>
</tr>
</fulong:for>
</logic:notEmpty>
<logic:empty name="preferences" property="value(row)">
<logic:present name="rowTemp">
<bean:define id="rowCount" name="rowTemp"/>
<fulong:for id="row" name="rowTemp"><tr>
  <fulong:for id="column" name="preferences" property="value(column)">
    <logic:iterate indexId="index" id="content" name="contents" offset="<%=String.valueOf(row.intValue()*Integer.parseInt(columnCount)+column.intValue())%>" length="1">
    <%request.setAttribute("com.fulong.longcon.Content",content);request.setAttribute("indexId",index); %>
      <td id='<bean:write name="content" property="ID" ignore="true"/>'><site:insert page='<%=path%>' flush="false"></site:insert></td>
      <%request.setAttribute("com.fulong.longcon.Content",null); request.setAttribute("indexId",null); %>
    </logic:iterate>
  </fulong:for>
</tr>
</fulong:for>
</logic:present>
</logic:empty>
</table>
<logic:equal value="true" name="preferences" property="value(show-pager)">
	<div class="<bean:write name='preferences' property="value(pager-style)" ignore="true"/>"><portlet:pager name="pager" pattern="default" /></div>
</logic:equal>
   <logic:equal value="isGotoPager" name="preferences" property="value(show-pager)">
	<div class="<bean:write name='preferences' property="value(pager-style)" ignore="true"/>"><portlet:pager name="pager" pattern="goto" /></div>
</logic:equal>
<logic:equal value="baidu1" name="preferences" property="value(show-pager)">
	<div class="<bean:write name='preferences' property="value(pager-style)" ignore="true"/>"><portlet:pager name="pager" pattern="baidu1" /></div>
</logic:equal>
<logic:equal value="baidu2" name="preferences" property="value(show-pager)">
	<div class="<bean:write name='preferences' property="value(pager-style)" ignore="true"/>"><portlet:pager name="pager" pattern="baidu2" /></div>
</logic:equal>

