<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<bean:define id="path" name="path" type="java.lang.String"/>
<ul id="<bean:write name='javax.portlet.id'/>" class="<bean:write name="preferences" property="value(style)" ignore="true"/>">
  <logic:present name="defs">
    <logic:iterate id="def" name="defs">
      <%request.setAttribute("com.fulong.longcon.nodeDefinition",def); %>
      <li>
      <site:insert page='<%=path%>' flush="false"></site:insert>
      </li>
      <%request.setAttribute("com.fulong.longcon.nodeDefinition",null); %>
    </logic:iterate>
  </logic:present>
</ul>
