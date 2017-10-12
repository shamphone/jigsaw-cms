<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
<tiles:put name="title">选择待显示的列</tiles:put>
	<tiles:put name="javascript">
		<script language="JavaScript" type="text/Javascript">
			function save($form) {
				var options = document.getElementById("properties").options;
				var ret = [];
				for (var i=0; i<options.length; i++) {
					if (options[i].selected) {
						var prop = {};
						prop.value = options[i].value;
						prop.text = (options[i].prefix ? options[i].prefix + "." : "") + options[i].text;
						ret.push(prop);
					}
				}
				window.returnValue = ret;
				window.close();
			}
			window.onload = function() {
				var args = window.dialogArguments;
				if (args && args.length) {
					var options = document.getElementById("properties").options;
					for (var i=0; i<args.length; i++) {
						if (options(args[i]))
							options.remove(options(args[i]).index);
					}
				}
			}
    	</script>
	</tiles:put>
	<tiles:put name="dialog">
		<html:form action="saveView.do">
			<table width="100%" class="sheetClass" cellpadding="2" cellspacing="0" border="0">
				<tr>
					<td>选择待显示的列</td>
				</tr>
				<tr>
					<td class="searchSelectors">
						<select size="13" style="width: 282px" name="allP" id="properties" multiple="multiple">
							<logic:iterate id="allp" name="properties" indexId="index">
								<logic:present name="allp">
									<logic:equal value="0" name="allp" property="type">
										<logic:equal value="false" name="allp" property="multiple">
											<optgroup label="<bean:write name="allp" property="name"/>">
												<logic:present name="allP" property="nodeDefinition">
													<logic:iterate id="subP" name="allp" indexId="subIndex" property="nodeDefinition.propertyDefinitionCollection">
														<option id="<bean:write name='allp' property='ID'/>.<bean:write name='subP' property='ID'/>" prefix="<bean:write name='allp' property='name'/>" value="<bean:write name='allp' property='ID'/>.<bean:write name='subP' property='ID'/>"><bean:write name="subP" property="name"/></option>
													</logic:iterate>
												</logic:present>
											</optgroup>
										</logic:equal>
									</logic:equal>
									<logic:equal value="9" name="allp" property="type">
										<optgroup label="<bean:write name="allp" property="name"/>">
											<logic:iterate id="subP" name="allp" indexId="subIndex" property="referenceDefinition.propertyDefinitionCollection">
												<option id="<bean:write name='allp' property='ID'/>.<bean:write name='subP' property='ID'/>" prefix="<bean:write name='allp' property='name'/>" value="<bean:write name='allp' property='ID'/>.<bean:write name='subP' property='ID'/>"><bean:write name="subP" property="name" /></option>
											</logic:iterate>
										</optgroup>
									</logic:equal>
									<logic:notEqual value="0" name="allp" property="type">
										<logic:notEqual value="9" name="allp" property="type">
											<option id="<bean:write name='allp' property='ID'/>" value="<bean:write name='allp' property='ID'/>"><bean:write name="allp" property="name" /></option>
										</logic:notEqual>
									</logic:notEqual>
								</logic:present>
							</logic:iterate>
						</select>
					</td>
				</tr>
			</table>
			<div class="operation">
				<button class="commonbut" id="commonbut" onclick="save(this.form)">保存</button>
			</div>
		</html:form>
	</tiles:put>
</tiles:insert>
