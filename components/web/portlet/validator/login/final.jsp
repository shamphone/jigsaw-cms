<%@ page contentType="text/html; charset=UTF-8" %><%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %><%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<logic:present parameter="loginerror">
  <logic:notEqual value="false" name="preferences" property='value(isAlert)'>
    <script>
      alert('<bean:write name="preferences" property="value(error-tips)"/>');
    </script>
  </logic:notEqual>
  <logic:equal value="false" name="preferences" property='value(isAlert)'>
    <span id="<bean:write name='javax.portlet.id'/>_0" <logic:notEmpty name='preferences' property='value(error-style)'> class=<bean:write name='preferences' property='value(error-style)'/></logic:notEmpty>><bean:write name="preferences" property="value(error-tips)"/></span>
  </logic:equal>
</logic:present>
<logic:notPresent parameter="loginerror"><span id="<bean:write name='javax.portlet.id'/>_1" <logic:notEmpty name='preferences' property='value(tips-style)'> class=<bean:write name='preferences' property='value(tips-style)'/></logic:notEmpty>><bean:write name="preferences" property="value(tips)"/></span></logic:notPresent>
