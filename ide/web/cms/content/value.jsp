<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<cms:node name="node" propertyName='<%= request.getParameter("property") %>' filter="false"/>