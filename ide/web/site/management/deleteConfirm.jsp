<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert definition="dialog_frame">
  <tiles:put name="body">
    请确认删除如下内容：
    <ul>
    <logic:iterate id="item" name="list">
      <li><bean:write name="item" property="name"/></li>
    </logic:iterate>
    </ul>
    <div><button onclick="window.location='delete.do?categoryID=<bean:write name="categoryID" ignore="true"/><logic:iterate id="item" name="list">&id=<bean:write name="item" property="ID"/></logic:iterate>'">确定</button></div>
  </tiles:put>
</tiles:insert>
