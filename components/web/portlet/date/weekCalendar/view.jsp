<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portal"%><%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<table id="<bean:write name='javax.portlet.id'/>" cellspacing="0" cellpadding="1" border="0" width="100%" <logic:notEmpty name="preferences" property="value(style)"> style="<bean:write name="preferences" property="value(style)" ignore="true"/>"</logic:notEmpty><logic:notEmpty  name="preferences" property="value(table-style)"> class="<bean:write name="preferences" property="value(table-style)" ignore="true"/>"</logic:notEmpty>>
<bean:define id="preferences" name="preferences" type="javax.portlet.PortletPreferences"/>
<bean:define id="path" name="path" type="java.lang.String"/>
<bean:define id="contextName" name="contextName" type="java.lang.String"/>
<fulong:for id="row" name="preferences" property="value(row)"><tr>
  <fulong:for id="column" name="preferences" property="value(column)">
      <td>
   <site:insert page='<%=path%>' contextName="<%= contextName %>" flush="false"></site:insert>
      </td>
  </fulong:for>
</tr>
</fulong:for>
</table>
