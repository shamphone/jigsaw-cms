<%@ page contentType="text/html; charset=UTF-8" %><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<script language="javascript" src='/ide/common/script/common.js' type="text/javascript" ></script>
<script type="text/javascript" language="javascript">
	var nameMap = {};
	<logic:present name="properties">
		<logic:iterate name="properties" id="prop">
			nameMap['<bean:write name="prop" property="ID" />'] = '<bean:write name="prop" property="name" />';
		</logic:iterate>
	</logic:present>
  	window.onload = function() {
  	  	var fm = window.parent.dialogArguments.form;
      	if (fm) {
          	var options = document.getElementById("propertyId").options;
			var inputs = fm.elements;
			for (var i=0; i<inputs.length; i++) {
				if (inputs[i].name && !options[inputs[i].name&& nameMap[inputs[i].name]]) {
					var option = document.createElement("option");
					option.id = inputs[i].name;
					option.value = inputs[i].name;
					if(document.all){
						if (nameMap[inputs[i].name])
							option.text = nameMap[inputs[i].name]; 
						else
							option.text = inputs[i].title ? inputs[i].title : inputs[i].name;
						options.add(option);
					}else{
						if (nameMap[inputs[i].name])
							option.textContent = nameMap[inputs[i].name]; 
						else
							option.textContent = inputs[i].getAttribute("title") ? inputs[i].getAttribute("title") : inputs[i].name;
						options.add(option,null);
					}
				}
			}
			document.getElementById("formName").value=window.parent.dialogArguments.form.name;
			setSelectValue(document.getElementsByName('preference(propertyId)')[0],'<bean:write name="preferences" property="value(propertyId)" />');
		}
	}
</script>
<table cellpadding="0" cellspacing="0" border="0">
    <portlet:form action="save" method="POST">
        <html:hidden property="preference(form)" styleId="formName"/>
        <html:hidden property="preference(contentType)"/>
        <tr>
            <td class="pannelDiv" rowspan="2">
                <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
                    <option value="0" selected="selected">基本设置</option>
                </select></td>
                <td><fieldset>
                    <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
                        <tr>
                            <td class="formTitle">控件</td>
                            <td class="formComponent">
                                <html:select property="preference(propertyId)" styleId="propertyId">
                            </html:select>
                        </td>
                        </tr>
                        <tr>
                        <td></td>
                        <td><html:checkbox style="width:25px" property="preference(pw)" styleId="a1" value="true"/><label for="a1">加密</label></td>
                        </tr>
                        <tr>
                          <td class="formTitle">属性</td>
                          <td class="formComponent">
                          <html:hidden property="preference(apropertyId)"/>
                          <input type="text" name="fieldName" readonly="readonly" value="<bean:write name="property" property="name" ignore="true"/>" />
                          <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition('<bean:write name="definition" ignore="true"/>')">选择...</button>
                          </td>
                      </tr>
                    <tr>
                        <td class="formTitle">提示文字</td>
                        <td class="formComponent">
                            <html:text property="preference(tips)"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="formTitle">提示样式</td>
                        <td class="formComponent">
                            <html:text property="preference(tips-style)"/><button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(tips-style)'],'')">选择...</button>
                        </td>
                    </tr>
                    <tr>
                        <td class="formTitle">错误提示</td>
                        <td class="formComponent">
                            <html:text property="preference(error-tips)"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="formTitle">错误样式</td>
                        <td class="formComponent">
                            <html:text property="preference(error-style)"/><button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(error-style)'],'')">选择...</button>
                        </td>
                    </tr>
                </table>
            </fieldset>
            <div class="toolbar">
              <button type="button" onclick="validatorRequired(new Array(form.elements['preference(apropertyId)']),new Array('属性'),this.form)">保存</button>
              <button type="button" onclick="window.parent.close()">取消</button>
            </div>
            </td>
        </tr></portlet:form>
</table>
<script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
<script type="text/javascript" language="javascript">
  /**
  *选择待显示的属性
  */
  function searchPropertyDefinition($categoryId){
    var arr = CMSDialog.PropertyDefinitionSelector($categoryId,['0','2','6','7','8','9','10']);
    if(arr!=null){
      document.getElementsByName("fieldName")[0].value = arr.name;
      document.getElementsByName("preference(apropertyId)")[0].value = arr.ID;
    }
  }
</script>