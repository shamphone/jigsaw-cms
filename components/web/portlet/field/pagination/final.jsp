<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<span>
	<div <logic:present name="preferences" property="value(contentStyle)">class="<bean:write name="preferences" property="value(contentStyle)"/>"</logic:present>><bean:write name="content" filter="false" /></div>
	<div <logic:present name="preferences" property="value(pageStyle)">class="<bean:write name="preferences" property="value(pageStyle)"/>"</logic:present>>
		<logic:greaterThan value="1" name="pageCount">
			<fulong:for id="current" name="pageCount">
				<portlet:renderURL var="pagerURL">
					<portlet:param name="pageNo">
						<bean:write name="current" format="#" />
					</portlet:param>
				</portlet:renderURL>
				<logic:greaterThan value="0" name="current">
					<bean:write name="preferences" property="value(separator)" ignore="true" filter="false" />
				</logic:greaterThan>
				<logic:equal name="pageNo" value='<%="" + current%>'>
					<span class='<bean:write name="preferences" property="value(currentPageStyle)"/>'><bean:write name="leftAround" ignore="true" filter="false"/><%=current.intValue() + 1%><bean:write name="rightAround" ignore="true" filter="false"/></span>
				</logic:equal>
				<logic:notEqual name="pageNo" value='<%="" + current%>'>
					<span><a href='<bean:write name="pagerURL"/>'><bean:write name="leftAround" ignore="true" filter="false"/><%=current.intValue() + 1%><bean:write name="rightAround" ignore="true" filter="false"/></a></span>
				</logic:notEqual>
			</fulong:for>
		</logic:greaterThan>
	</div>
</span>
