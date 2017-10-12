<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<tiles:insert page="/site/framework.jsp">
  <tiles:put name="title"><tiles:getAsString name="title" ignore="true"/> </tiles:put>
  <tiles:put name="module">/page</tiles:put>
  <tiles:putList name="pathes">
    <tiles:add><html:link page="/site.do" module=""><bean:write name='site' property="name"/></html:link></tiles:add>
    <tiles:add><html:link page="/index.do" module="/site/page">页面管理</html:link></tiles:add>
    <tiles:add><html:link page="/index.do">样式管理</html:link></tiles:add>
  </tiles:putList>
  <tiles:put name="content">
       <tiles:getAsString name="content"/>
  </tiles:put>
</tiles:insert>
