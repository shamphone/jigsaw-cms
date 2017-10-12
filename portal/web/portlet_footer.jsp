<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<portlet:defineObjects/><% String portlet_mode=renderRequest.getPortletMode().toString(); %><% if(portlet_mode.equals("view")||portlet_mode.equals("edit")||portlet_mode.equals("help")) {%></div></div><%}else if(portlet_mode.equals("preview")){%></div><%} %>
