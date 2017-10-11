<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portal"%><%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<bean:define id="definitionId" name="preferences" property="value(category)" type="java.lang.String"/>
<bean:define id="path" name="path" type="java.lang.String"/>
<bean:define id="contextName" name="contextName" type="java.lang.String"/>
<ul id="<bean:write name='javax.portlet.id'/>" class="<bean:write name="preferences" property="value(list-style)" ignore="true"/>">
<site:insert page='<%= path +"?javax.portlet.page.mode=view&definition="+ definitionId %>' contextName="<%= contextName %>" flush="false"></site:insert>
<site:insert page='<%= path +"?javax.portlet.page.mode=view&definition="+ definitionId %>' contextName="<%= contextName %>" flush="false"></site:insert>
<site:insert page='<%= path +"?javax.portlet.page.mode=view&definition="+ definitionId %>' contextName="<%= contextName %>" flush="false"></site:insert>
<site:insert page='<%= path +"?javax.portlet.page.mode=view&definition="+ definitionId %>' contextName="<%= contextName %>" flush="false"></site:insert>
<site:insert page='<%= path +"?javax.portlet.page.mode=view&definition="+ definitionId %>' contextName="<%= contextName %>" flush="false"></site:insert>
</ul>