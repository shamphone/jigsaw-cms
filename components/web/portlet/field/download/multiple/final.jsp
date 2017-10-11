<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
 	<logic:notEmpty name="paths">
  		<ul <logic:notEmpty name="preferences" property="value(style)">class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty>>
			<logic:iterate id="path" name="paths" type="String">
				<logic:empty name="path">
					<logic:equal value="1" name="preferences" property="value(perch)"><li></li></logic:equal>
				</logic:empty>
				<logic:notEmpty name="path">
					<li>
						<a href="<bean:write name="path"/>">
							<logic:equal value="name" name="preferences" property="value(showWhat)">
								<bean:define id="path" name="path" type="String"/>
									<%=path.indexOf("/")!=-1 ? path.substring(path.lastIndexOf("/")+1, path.length()) : path %>
							</logic:equal>
							<logic:equal value="text" name="preferences" property="value(showWhat)">
								<logic:notEmpty name="preferences" property="value(text)">
									<bean:write name="preferences" property="value(text)" filter="false"/>
								</logic:notEmpty>
							</logic:equal>
						</a>
					</li>
				</logic:notEmpty>
			</logic:iterate>
		</ul>
	</logic:notEmpty>
