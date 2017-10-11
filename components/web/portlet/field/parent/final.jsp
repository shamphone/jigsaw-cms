<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<ul id="<bean:write name='javax.portlet.id'/>" class="<bean:write name="preferences" property="value(style)" ignore="true"/>">
<li>
<bean:define id="path" name="path" type="java.lang.String"/>
  <logic:present name="nodeparent">
   <bean:define name="nodeparent" id="parent"/>
    <%request.setAttribute("com.fulong.longcon.Content",parent); %>
     <site:insert page='<%=path%>' flush="false"></site:insert>
     <%request.setAttribute("com.fulong.longcon.Content",null); %>
   </logic:present>
</li>
</ul>