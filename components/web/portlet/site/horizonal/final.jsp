<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<bean:define name="selected" property="ID" id="ID" type="String"/>
<div class="<bean:write name="preferences" property="value(list-style)" ignore="true"/>"  id="<bean:write name='javax.portlet.id'/>">
<logic:iterate id="theChannel" name="channels" indexId="index">
  <logic:greaterThan value="0" name="index">
    <span class="<bean:write name="preferences" property="value(seperator-style)" ignore="true"/>"><bean:write name="preferences" property="value(seperator)" filter="false" ignore="true"/></span>
  </logic:greaterThan>
  <span class="<logic:equal value="<%=ID%>" name="theChannel" property="ID"><bean:write name="preferences" property="value(page-style-selected)" ignore="true"/></logic:equal><logic:notEqual value="<%=ID%>" name="theChannel" property="ID"><bean:write name="preferences" property="value(page-style)" ignore="true"/></logic:notEqual>">
  <a href="<site:channelURL name="theChannel"/>" target="<bean:write name="preferences" property="value(target)"/>">
  <bean:write name="theChannel" property="displayName"/>
</a>
</span>
</logic:iterate>
</div>
