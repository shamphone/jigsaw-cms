<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>

<tiles:insert definition="dialog_frame">
  <tiles:put name="title">模板管理</tiles:put>
  <tiles:put name="javascript">
	<meta http-equiv="pragma" content="no-cache">
  	<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/list.css"/>"/>
    <style>
      #oListPanel {width:450px;height:340px;background-color:#ffffff;border:2px inset;overflow:scroll;};
      #category {width:100px;height:275px;}
    </style>
    <link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/list.css"/>" />
	<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/sitelist.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/dialog.js"/>"></script>
    <script language="javascript" type="text/javascript">
		function ok(){
			var rows = ListTable.GetSelectedRow();
			if (rows) {
				var templates = new Array();
				for(var i=0;i<rows.length;i++){
					var template = new Object();
					template.ID = rows[i].id;
					template.name = rows[i].getAttribute("templateName");
					template.displayName = rows[i].getAttribute("displayName");
					templates.push(template);
				}
				window.returnValue = templates;
			}else{
				alert("请选择导航的模板");
				return;
			}
			window.close();
		}

		function updateInf(o){
			var oListTable = document.getElementById("listTable");
			var rows = oListTable.tBodies[0].getElementsByTagName("tr");
			for(var i=0;i<rows.length;i++){
				if(rows[i].name=="resolution_"+o.value){
					rows[i].style.display = "block";
				}else{
					rows[i].style.display = "none";
				}
			}
		}
		
        window.onload = function() {
			ListTable.Init(document.getElementById("listTable"),false);
			<logic:present name="defaultTemplateIds">
	  			<logic:iterate id="defaultTemplateId" name="defaultTemplateIds">
	  				var oRadio = document.getElementById('cb_<bean:write name="defaultTemplateId"/>');
	  				if(oRadio){
	  					oRadio.checked = true;
	  				}
	  			</logic:iterate>
	    	</logic:present>
	    	document.getElementById("resolutionSelect").selectedIndex = 0;
			document.getElementById("resolutionSelect").fireEvent("onchange");
        }
        </script>
      </tiles:put>
      <tiles:put name="dialog">
       <table width="100%" cellpadding="2" cellspacing="0">
          <tr>
            <td width="50px" valign="top">
            	<select id="resolutionSelect" name="category"  onchange="updateInf(this)" size="16" style="width:100px;">
              	<logic:iterate id="resolution" name="resolutions">
                	<option value='<bean:write name="resolution" ignore="true"/>'><bean:write name="resolution" ignore="true"/></option>
              	</logic:iterate>
            	</select>
            </td>
			<td id="listViewer" valign="top">
				<div id="oListPanel" style="height: 264">
					<table id="listTable" cellpadding="2" cellspacing="0">
						<thead>
						<tr>
							<th width="20">&nbsp;</th>
							<th width="20">&nbsp;</th>
							<th width="100">名称</th>
							<th width="80">网站数</th>
							</tr>
						</thead>
							<tbody >
									<logic:iterate id="template" name="templates" indexId="id">
										<tr name="resolution_<bean:write name="template" property="resolution" ignore="true"/>" id='<bean:write name="template" property="ID"/>'
											siteCount='<bean:write name="template" property="siteCount"/>'
											templateName='<bean:write name="template" property="name"/>'
											displayName='<bean:write name="template" property="displayName"/>'>
											<td width="20">
												<input id="cb_<bean:write name="template" property="ID"/>"
												<logic:notPresent name="defaultTemplateIds">
	        										<logic:equal name="id"  value="0">
	        											checked = true 
	        										</logic:equal>
	      										</logic:notPresent>
												type="radio" name="cb_<bean:write name="template" property="resolution" ignore="true"/>"
												value='<bean:write name="template" property="ID"/>'>
											</td>
											<td><%=(id.intValue()+1) %></td>
											<td><bean:write name="template" property="displayName" /></td>
											<td><bean:write name="template" property="siteCount" /></td>
										</tr>
									</logic:iterate>
						 </tbody>
					</table>
				</div>
			</td>
			</tr>
        </table>
        <div class="operation">
          <button id="btnOk" onclick="ok()" id="tijiao">确定</button>
        </div>
      </tiles:put>
    </tiles:insert>
