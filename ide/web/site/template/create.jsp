<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<tiles:insert definition="dialog_frame">
  <tiles:put name="title">创建网站模板</tiles:put>
  <tiles:put name="javascript">
    <html:javascript formName="createTemplateForm"/>
	<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms" page="/classes/cmsdialog.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/dialog.js"/>"></script>
    <script type="text/javascript" language="javascript">
		function disableButton() {
			document.getElementById("btnOk").disabled = "true";
		}
		function enableButton() {
			document.getElementById("btnOk").disabled = "";
		}
		function chooseTemplate($form) {
	    	var template=SiteDialog.selectTemplate();
	        if(template!=null&&template.length>0){
	        	document.getElementsByName("sourceDisplayName")[0].value=template[0].displayName;
	        	document.getElementsByName("sourceName")[0].value=template[0].ID;
	        }
		}
	    function chooseOwner($valueControl, $textControl) {
	    	var user=CMSDialog.NodeSelector('principal-scheme');
	    	if(user) {
	    		$valueControl.value=user['id'];
	    		$textControl.value=user['user-username'];
	    	}
	    }
		function validate($form) {
			if (!validateCreateTemplateForm($form))
				return false;
			if (GetRadioValue($form.all("advanced")) == "template") {
				var fileName = $form["staticPage"].value;
				if (fileName.toLowerCase().indexOf(".zip") != fileName.length - 4) {
					alert("导入的文件必须是ZIP格式");
					return false;
				}
			}
			disableButton();
			return true;
		}
		function onRadioChange($advanced, $form) {
			var btn = document.getElementById("btnCopy");
			if ($advanced == 'blank') {
				$form.staticPage.disabled = "true";
				$form.sourceDisplayName.disabled = "true";
				btn.disabled = "true";
			} else if ($advanced == 'template') {
				$form.staticPage.disabled = "";
				$form.sourceDisplayName.disabled = "true";
				btn.disabled = "true";
			} else /*if($advanced=='copy') */{
				$form.staticPage.disabled = "true";
				$form.sourceDisplayName.disabled = "";
				btn.disabled = "";
			}
		}
	    function validateDomain($domain){
        	if (!$domain.value) 
	        	return;
			var url='<html:rewrite page="/management/validateDomain.do" module="/site"/>?domain=' + $domain.value + "&timestamp=" + Math.random();
			var request = new HttpRequest(url);
			if (request.Get() == "domainExits") {
				alert("您所指定的域名已被使用，请重新输入");
				toggleButton("disable");
				$domain.select();
				return;
			}
			toggleButton("enable");
	    }
		function validateTemplate($templateName) {
			if ($templateName.value)
				return;
			var url = '<html:rewrite module="/site/template" page="/check.do"/>?name=' + $templateName.value + "&timestamp=" + Math.random();
			var request = new HttpRequest(url);
			if (request.Get() == "false") {
				alert("您所指定的模板名称已被使用，请重新输入");
				toggleButton("disable");
				$templateName.select();
				return;
			}
			toggleButton("enable");
		}
		function _Valid() {
			var code = event.keyCode;
			return (code>=97 && code<=122) || (code>=48 && code<=57) || (code>=65 && code<=90) || code==8 || code==9 || code==45 || code==46;	
		}
		function _submit(){
       		var template = new Object();
       		template.displayName = document.getElementsByName("displayName")[0].value;
	    	window.parent.returnValue = template;
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
    <html:form  action="/insert.do" method="post" enctype="multipart/form-data" onsubmit="return validate(this)">
      <div style="color:red; margin-left:6px"><html:errors/></div>
      <table border="0" cellpadding="2" cellspacing="2" align="center" width="100%">
        <tr>
          <td>模板名称</td>
          <td><html:text onchange="validateTemplate(this)" property="name" size="45" maxlength="16" style="ime-mode :disabled" title="4-16个英文字母或数字" /></td>
        </tr>
        <tr>
          <td>显示名称</td>
          <td><html:text property="displayName" size="45" maxlength="16" title="最多16个字符"/></td>
        </tr>
		<tr>
        <td>分辨率</td>
          <td>
          	<html:select styleId="resolution" property="resolution" style="width:260px">
          		<html:options name="resolutions"/>
          	</html:select>
          	<script type="text/javascript">
          		window.onload = function(){
					var oSelect = document.getElementById("resolution");
					var options = oSelect.options;
					var len = options.length;
					for(var i=0;i<len;i++){
						if(options[i].value=="default"){
							options[i].text = "默认";
						}
					}
					onRadioChange('<bean:write name="createTemplateForm" property="advanced"/>',document.forms[0]);
          		}
          	</script>
          </td>
        </tr>
        <tr>
          <td>模板语言</td>
          <td>
            <html:select property="language" style="width:260px">
              <logic:iterate id="language" name="languages">
                <bean:define id="ID" name="language" property="ID" type="String"/>
                <html:option value="<%=ID%>"><cms:node name="language" propertyName="title"/></html:option>
              </logic:iterate>
            </html:select>
          </td>
        </tr>
        </table>
        <table border="0" cellpadding="2" cellspacing="2" align="center" width="100%">
        <tr>
          <td><html:radio value="blank" property="advanced" styleId="blank" onclick="onRadioChange(this.value, this.form)"/><label for="blank">创建空白模板</label></td>
          <td>&nbsp;</td>
        </tr>
        <tr>
		  <td>
		  <html:radio value="template" property="advanced" styleId="template" onclick="onRadioChange(this.value, this.form)"/><label  for="template">导入设计包</label>
		  </td>
		  <td>
		  <html:file  property="staticPage" size="24" title="请将设计页面和图片打包成zip文件并上传"/>
		  </td>
        </tr>
        <tr>
           <td>
           <html:radio value="copy" property="advanced" styleId="copy" onclick="onRadioChange(this.value, this.form)"/>
           <label  for="copy">复制现有模板</label>
           </td>
		   <html:hidden property="sourceName"/>
		   <td>
           <html:text readonly="true" property="sourceDisplayName" size="24"/>
           <button type="button" id="btnCopy" class="btnMore" style="width:68px;" onclick="chooseTemplate(this.form)">选择•••</button>
           </td>
        </tr>
      </table>
     
      <div style="color:red; margin-left:6px"><html:errors property="domain.existent"/></div>
      <div class="operation">
        <button type="button" id="btnOk" onclick="_submit()">确定</button>
        <button type="button" id="btnCancel" onclick="_close()">取消</button>
      </div>
    </html:form>
  </tiles:put>
</tiles:insert>
