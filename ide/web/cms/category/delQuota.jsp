<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<tiles:insert definition="content_frame">
  <tiles:put name="title">删除配额确认</tiles:put>

<tiles:put name="body">
  <html:form  action="delQuota.do">
       请确认删除如下配额
    <input type="hidden" name="categoryID" value="<bean:write name="categoryID"/>"/>
    <logic:iterate id="principal" name="principals"  scope="request" indexId="Index">
    <input type="hidden" name="principalIDs" value="<bean:write name="principal" property="ID"/>">
    <ul>
     <li>
     <cms:node name="principal" propertyName="commonname"/>
     </li>
    </ul>
    </logic:iterate>
    <button type="submit">确认</button>
   </html:form>
</tiles:put>
</tiles:insert>
