<%@page contentType="text/html; charset=utf-8" %>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<fulong:portlet id='<%= request.getParameter("portlet.window.owner") %>' type='<%= request.getParameter("portlet.type") %>'>
  <logic:present name="preferences">
  <logic:iterate id="preference" name="preferences" property="preferences">
    <fulong:preference>
      <fulong:name><bean:write name="preference" property="name"/></fulong:name>
      <logic:iterate id="value" name="preference" property="values">
        <fulong:value><bean:write name="value" ignore="true" filter="false"/></fulong:value>
      </logic:iterate>
    </fulong:preference>
  </logic:iterate>
    </logic:present>
  </fulong:portlet>
