<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
	<tiles:put name="title">选择模板</tiles:put>
	<tiles:put name="javascript">
		<meta http-equiv="pragma" content="no-cache">
		<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/list.css"/>" />
		<style type="text/css">
		      #oListPanel {width:100%;background-color:#ffffff;border:2px inset;}
		</style>
		<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/sitelist.js"/>"></script>
		<script language="Javascript" type="text/Javascript">
			function ok(){
				var selectedRows = ListTable.GetSelectedRow();
				if(selectedRows.length>0){
					var templates = new Array();
					for(var i=0;i<selectedRows.length;i++){
						var oTemplate = new Object();
						oTemplate.ID = selectedRows[i].id;
						oTemplate.displayName = selectedRows[i].getAttribute("displayName");
						templates.push(oTemplate);
					}
					window.returnValue = templates;
					window.close();
				}else{
					alert("请选择模板！")
				}
			}

	        window.onload = function() {
				ListTable.Init(document.getElementById("listTable"));
				<logic:present name="defaultTemplateIds">
		  			<logic:iterate id="defaultTemplateId" name="defaultTemplateIds">
		  				var oRadios = document.getElementsByName('cb_<bean:write name="defaultTemplateId"/>');
		  				if(oRadios){
			  				for(var i=0;i<oRadios.length;i++){
				  				if(oRadios[i].type="checkbox"){
				  					oRadios[i].checked = true;
				  				}
			  				}
		  				}
		  			</logic:iterate>
		    	</logic:present>
	        }
	        
		</script>
	</tiles:put>
	<tiles:put name="dialog">
		<table width="100%" cellpadding="2" cellspacing="0" border="1">
			<tr>
				<td id="listViewer" valign="top">
					<div id="oListPanel" style="height: 275px;">
						<table id="listTable" cellpadding="2" cellspacing="0">
							<thead>
							<tr>
								<th width="20"><input type="checkbox" id="chkAll"></th>
								<th width="20">&nbsp;</th>
								<th width="100">名称</th>
								<th width="80">分辨率</th>
								<th width="80">网站数</th>
								</tr>
							</thead>
							<tbody id="templateList">
									<logic:iterate id="template" name="templates" length="20" indexId="id">
									<logic:notEqual value="default" name="template" property="name">
										<tr
										     id='<bean:write name="template" property="ID"/>'
											siteCount='<bean:write name="template" property="siteCount"/>'
											templateName='<bean:write name="template" property="name"/>'
											displayName='<bean:write name="template" property="displayName"/>'>
											<td width="20">
											<input id="<bean:write name="template" property="ID"/>"
												type="checkbox"  name="cb_<bean:write name="template" property="ID"/>" value='<bean:write name="template" property="ID"/>'></td>
											<td><%=(id.intValue()+1) %></td>
											<td><bean:write name="template" property="displayName" /></td>
											<td title="<logic:notEmpty name="template" property="resolution"><bean:write name="template" property="resolution" /></logic:notEmpty><logic:empty name="template" property="resolution">default</logic:empty>">
												<logic:notEmpty name="template" property="resolution">
													<bean:write name="template" property="resolution" />
												</logic:notEmpty>
												<logic:empty name="template" property="resolution">
													default
												</logic:empty>
											</td>
											<td><bean:write name="template" property="siteCount" /></td>
										</tr>
										</logic:notEqual>
									</logic:iterate>
							</tbody>
						</table>
					</div>
				</td>
			</tr>
		</table>
		<div class="operation">
		<!-- <button id="btnPreview" onclick="preview()">预览</button> -->
		<button id="btnOk" onclick="ok()" type="submit">确定</button>
		<button id="btnCancel" onclick="window.close()">取消</button>
		</div>
	</tiles:put>
</tiles:insert>

