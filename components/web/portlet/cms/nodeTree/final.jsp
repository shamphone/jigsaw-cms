<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%><%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<% String style="";%>
<logic:notEmpty name="preferences" property="value(style)">
  <bean:define id="style1" name="preferences" property="value(style)" type="java.lang.String"/>
  <%style=style1;%>
</logic:notEmpty>
<bean:define id="path" name="path" type="java.lang.String"/>
<fulong:tree name="tree" objectId="node" style="<%=style %>">
		<bean:define id="node" name="node"></bean:define>
		<%request.setAttribute("com.fulong.longcon.Content",node);%>
      <site:insert page='<%=path%>' flush="false"></site:insert>
    <%request.setAttribute("com.fulong.longcon.Content",null);%>
 </fulong:tree>
