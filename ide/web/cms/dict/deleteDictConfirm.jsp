<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fl"%>
<tiles:insert definition="content_frame">
  <tiles:put name="body">
    <div>这个操作将删除当前内容分类，并且不可恢复，确认删除？</div>
    <div><button onclick="window.location='delDict.do?dictID=<bean:write name="dictID"/>'">确定</button></div>
  </tiles:put>
</tiles:insert>
