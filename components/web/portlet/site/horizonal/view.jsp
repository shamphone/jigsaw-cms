<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<div class="<bean:write name="preferences" property="value(list-style)" ignore="true"/>"  id="<bean:write name='javax.portlet.id'/>">
<logic:iterate id="theChannel" name="selectedChannels" indexId="index">
  <logic:greaterThan value="0" name="index">
    <span class="<bean:write name="preferences" property="value(seperator-style)" ignore="true"/>"><bean:write name="preferences" property="value(seperator)" filter="false" ignore="true"/></span>
  </logic:greaterThan>
  <span class="<bean:write name="preferences" property="value(page-style)" ignore="true"/>"><a><bean:write name="theChannel" property="displayName"/></a></span>
</logic:iterate>
</div>
