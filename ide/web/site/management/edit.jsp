<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<tiles:insert definition="dialog_frame">
  <tiles:put name="title">编辑网站信息</tiles:put>
  	<tiles:put name="javascript">
		<link rel="stylesheet" type="text/css" media="all" href='<html:rewrite module="/common" page="/calendar/skins/aqua/theme.css"/>' title="Aqua" />
	    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
		<script type="text/javascript" src='<html:rewrite page="/classes/cmsdialog.js" module="/cms"/>'></script>
		<script type="text/javascript" src='<html:rewrite module="/common" page="/calendar/calendar.js"/>'></script>
		<script type="text/javascript" src='<html:rewrite module="/common" page="/calendar/lang/cn_utf8.js"/>'></script>
		<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/dialog.js"/>"></script>
		<script type="text/javascript" language="javascript">
			function toggleButton(state) {
				document.getElementById("btnOk").disabled = state == "disable";
			}
		    function selectTemplate(templateIDs,navigateTemplateIDs) {
			    var options = templateIDs.options;
			    var templates = new Array();
			    for(var i=0;i<options.length;i++){
			    	templates.push(options[i].value);
			    }
		        var ret = SiteDialog.selectTemplate(null,templates);
		        if(ret) {
		        	 templateIDs.options.length = 0;
		        	 navigateTemplateIDs.options.length = 0;
		        	 templates = new Array();
		        	 for(var i=0;i<ret.length;i++){
			        	templates.push(ret[i].ID);
					 }
		        	 var navigateRet = SiteDialog.selectNavigateTemplate(templates);
		        	 if(navigateRet!=null&&navigateRet.length>0){
		        		 var  navigateTemplates = new Array();
		        		 for(var i=0;i<navigateRet.length;i++){
			        		 navigateTemplates.push(navigateRet[i].ID);
			        		 addOption(navigateTemplateIDs,navigateRet[i].ID,navigateRet[i].displayName);
			        	 }
		        		 for(var i=0;i<ret.length;i++){
			        		addOption(templateIDs,ret[i].ID,ret[i].displayName);
						 }
		        	 }
	            }
		    }
		    function validate ($form) {
			    var templateIDs = document.getElementById("templateIDs");
			    var navigateTemplateIDs = document.getElementById("navigateTemplateIDs");
			    if(templateIDs.options.length==0){
					alert("请选择模板！");
					return false;
			    }
			    if(navigateTemplateIDs.options.length==0){
					alert("请选择导航模板！");
					return false;
			    }
			    if(!validateDisplayName()){
	    			return false;
	    		}
				toggleButton("disable");
				selectAll(templateIDs);
				selectAll(navigateTemplateIDs);
				return true;
	        }

		    function validateDisplayName(){
		        var displayName=document.getElementById("displayName").value;
		    	if(trim(displayName).length == 0) {
		    			alert("请输入网站名称！");
		    			return false;
		    		}
		    	return true;
	        }
		    function _submit(oForm){
		    	if( !validate(oForm)){
					return false;
				}
		    	window.parent.returnValue = 1;
				document.forms[0].submit();
				if(document.all){
					window.close();
				}else{
					window.parent.close();
				}
			}
			function _close(){
				if(document.all){
					window.close();
				}else{
					window.parent.close();
				}
			}
    	</script>
	</tiles:put>
  <tiles:put name="dialog">
    <html:form action="update.do" method="post">
      <html:hidden property="ID"/>
      <table width="100%" cellpadding="2" cellspacing="2" border="0">
        <tr>
          <td><table width="100%" cellpadding="2" cellspacing="2" border="0">
            <tr>
              <td scope="row" nowrap="nowrap">网站域名</td>
              <td>
                <html:text property="domain" size="18" disabled="true"></html:text>
              </td>
               <td scope="row" nowrap="nowrap">建站用户</td>
              <td>
                <html:hidden property="ownerID"/>
                <html:text property="ownerName" size="18" disabled="true"/>
              </td>
            </tr>
            <tr>
                <td scope="row">网站名称</td>
              <td>
                <html:text property="displayName" size="18"/>
              </td>
              <td></td><td></td>
            </tr>
			<tr>
				<td style="vertical-align: top">网站模板</td>
				<td>
					<html:select style="width: 122px;" multiple="true" size="8" property="templateIDs" styleId="templateIDs">
						<logic:iterate id="template" name="templates">
							 <option title="<bean:write name="template" property="displayName"/>" value="<bean:write name="template" property="ID"/>">
							 	<bean:write name="template" property="displayName"/>
							 </option>
						</logic:iterate>
					</html:select>
					<button type="button" style="vertical-align: top" onclick="selectTemplate(this.form.elements['templateIDs'],this.form.elements['navigateTemplateIDs'])">选择...</button>
				</td>
				<td style="vertical-align: top">导航模板</td>
				<td>
					<html:select style="width: 122px;" multiple="true" size="8" property="navigateTemplateIDs" styleId="navigateTemplateIDs">
						<logic:iterate id="template" name="navigateTemplates">
							 <option title="<bean:write name="template" property="displayName"/>" value="<bean:write name="template" property="ID"/>">
							 	<bean:write name="template" property="displayName"/>
							 </option>
						</logic:iterate>
					</html:select>
				</td>
			</tr>
          </table>
        </td>
      </tr>
    </table>
    <div class="operation">
      <button id="btnOk" type="button" onclick="_submit(this.form)">确定</button>
      <button type="button" id="btnCancel" onclick="_close()">取消</button>
    </div>
  </html:form>
</tiles:put>
</tiles:insert>
