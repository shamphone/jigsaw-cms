<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<ul class="<bean:write name="preferences" property="value(list-style)" ignore="true"/>">
 <bean:define id="length" name="preferences" property="value(row)"/>
  <logic:present name="sites">
    <logic:iterate id="site" name="sites" length='<%=length+""%>'>
      <li><span class="<bean:write name="preferences" property="value(list-style)" ignore="true"/>">
        <a href="http://<bean:write name="site" property="domain"/>" target="_blank">
        <bean:write name="site" property="displayName"/>　
        </a>
    </span></li>
  </logic:iterate>
</logic:present>
</ul>
