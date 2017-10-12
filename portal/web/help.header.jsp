<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fulong-bean.tld" prefix="fbean"%>
<%@taglib uri="/WEB-INF/fulong-logic.tld" prefix="flogic"%>
<portlet:defineObjects/>
<table class="portalTable" id="<fbean:write name='javax.portlet.id'/>" border="0" cellpadding="0" cellspacing="0">
<script type="text/javascript" language="javascript">
	enableToggle=false;
</script>
<tr>
<td class="portletHeader <flogic:equal value='<%= ""+ request.getParameter("portlet_name") %>' name='javax.portlet.id'>activeHeader</flogic:equal>">
<a href="<portlet:renderURL portletMode="view"/>"><html:img border="0" alt="浏览" page="/images/browse_2.gif"/></a>
<a href="<portlet:renderURL portletMode="edit"/>"><html:img border="0" alt="编辑" page="/images/edit_2.gif"/></a>
<html:img border="0" alt="帮助" page="/images/help.gif"/></a>
<span class='portletName'><bean:write name='portletConfig' property='displayName(zh)'/>(<bean:write name='javax.portlet.id'/>)</span>
</td>
</tr><tr>
<td class="portletBody">
