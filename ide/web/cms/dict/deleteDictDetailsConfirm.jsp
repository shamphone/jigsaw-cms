<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert definition="content_frame">
  <tiles:put name="body">
    请确认删除如下内容：
    <ul>
      <logic:iterate id="entity" name="list">
        <li><bean:write name="entity" property="displayName"/></li>
      </logic:iterate>
    </ul>
    <div><button onclick="window.location='deleteDictDetails.do?dictId=<bean:write name="dictId"/><logic:iterate id="entity" name="list">&id=<bean:write name="entity" property="ID"/></logic:iterate>'">确定</button></div>
  </tiles:put>
</tiles:insert>
