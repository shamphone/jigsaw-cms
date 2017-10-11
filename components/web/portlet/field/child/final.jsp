<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<table id="<bean:write name='javax.portlet.id'/>" class="<bean:write name="preferences" property="value(style)" ignore="true"/>" cellspacing="0" cellpadding="1" border="0" width="100%">
<bean:define id="rowCount" name="preferences" property="value(row)" type="java.lang.String"/>
<bean:define id="columnCount" name="preferences" property="value(column)" type="java.lang.String"/>
<bean:define id="path" name="path" type="java.lang.String"/>
<fulong:for id="row" name="preferences" property="value(row)"><tr>
  <fulong:for id="column" name="preferences" property="value(column)">
    <logic:present name="nodes">
    <logic:iterate indexId="index" id="content" name="nodes" length="1">
    <%request.setAttribute("com.fulong.longcon.Content",content);request.setAttribute("indexId",index); %>
      <td>
      <site:insert page='<%=path%>' flush="false"></site:insert>
      </td>
      <%request.setAttribute("com.fulong.longcon.Content",null); request.setAttribute("indexId",null); %>
    </logic:iterate>
    </logic:present>
  </fulong:for>
</tr>
</fulong:for>
</table>
