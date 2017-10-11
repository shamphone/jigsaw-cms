<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portal"%><%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<bean:define id="path" name="path" type="java.lang.String"/>
<bean:define id="contextName" name="contextName" type="java.lang.String"/>
<logic:present name="tree">
<fulong:tree name="tree" objectId="category" nodeId="node">
  <bean:define id="nodeDefinition" name="category" type="com.fulong.longcon.repository.NodeDefinition"/>
  <%request.setAttribute("com.fulong.longcon.nodeDefinition",nodeDefinition);%>
    <site:insert page='<%= path +"?javax.portlet.page.mode=view"%>' flush="false" contextName="<%= contextName %>"></site:insert>
  <%request.setAttribute("com.fulong.longcon.nodeDefinition",null);%>
</fulong:tree>
</logic:present>
<logic:notPresent name="tree">
内容分类重复器
</logic:notPresent>