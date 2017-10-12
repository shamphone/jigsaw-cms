<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/fulong.tld" prefix="fulong" %>
<tiles:insert definition="dialog_frame">
  <tiles:put name="javascript">
  <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/dialog.js"/>"></script>
  <script type="text/javascript" language="javascript">
	function validateForm($form){
		var navigateTemplateIDs = $form.navigateTemplateIDs;
		if(navigateTemplateIDs.options.length==0){
			alert("请选择一个模板");
			return false;
		}
		selectAll(navigateTemplateIDs);
		return true;
    }

    function selectTemplate(oSelect){
	  	var templates = new Array();
	  	<logic:iterate id="template" name="templates">
	  		templates.push('<bean:write name="template" property="ID"/>');
		</logic:iterate>
		var navigateTemplates = new Array();
		var navigateTemplateIDs = oSelect.form['navigateTemplateIDs'];
		for(var i=0;i<navigateTemplateIDs.options.length;i++){
			navigateTemplates.push(navigateTemplateIDs.options[i].value);
		}
		var ret = SiteDialog.selectNavigateTemplate(templates,navigateTemplates);
		if(ret){
			oSelect.options.length = 0;
			for(var i=0;i<ret.length;i++){
		   		addOption(oSelect,ret[i].ID,ret[i].displayName);
			}
		}
	}

    function _submit(){
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
  <tiles:put name="title">设置导航模板</tiles:put>
  <tiles:put name="dialog">
  		<html:form action="changeNavigate.do" method="post" onsubmit="return validateForm(this)">
			<div style="margin-top: 20px;"><label style="vertical-align: top;">导航模板:</label>
				<html:select style="width: 193px;" multiple="true" size="8" property="navigateTemplateIDs" styleId="navigateTemplateIDs">
				<logic:present name="navigateTemplates">
					<logic:iterate id="template" name="navigateTemplates">
							 <option title="<bean:write name="template" property="displayName"/>" value="<bean:write name="template" property="ID"/>">
							 	<bean:write name="template" property="displayName"/>
							 </option>
					</logic:iterate>
					</logic:present>
				</html:select>
				<button type="button" style="vertical-align: top" onclick="selectTemplate(this.form.elements['navigateTemplateIDs'])">选择...</button>
			</div>
			<html:hidden property="ID"/>
			<div class="operation">
	      	<button type="button" id="btnOk" class="commonbut" onclick="_submit()">确定</button>
	      	<button type="button" id="btnCancel"  class="commonbut" onclick="_close()">取消</button>
   		 </div>
		</html:form>
  </tiles:put>
  </tiles:insert>
