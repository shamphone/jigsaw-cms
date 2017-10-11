<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form action="save" method="POST">
  	<html:hidden property="preference(definitionId)"/>
    <tr>
      <td class="pannelDiv" rowspan="2">
        <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
          <option value="0" selected="selected">基本设置</option>
          <option value="1" >空项设置</option>
        </select>
      </td>
      <td>
        <fieldset>
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
            <tr>
              <td class="formTitle">关联属性</td>
              <td class="formComponent">
              		<table cellpadding="0" cellspacing="0" border="0">                  
	                  <tr>
		                  <td>
			                    <select multiple="multiple" name="preferences(propertyIds)" size="5" style="width:210px;">
			                      <logic:notEmpty name="properties">
			                        <logic:iterate id="selectedProp" name="properties">
			                          <option value='<bean:write name="selectedProp" property="ID" ignore="true"/>'><bean:write name="selectedProp" property="name" ignore="true"/></option>
			                        </logic:iterate>
			                      </logic:notEmpty>
			                    </select>
		                  </td>
		                  <td valign="top">
		                      <button type="button" class="commonbut" onclick="addPropertyDefinition(form.elements['preference(definitionId)'],form.elements['preferences(propertyIds)'],['0','2','3','4','5','6','7','8','9','10'])">添 加</button><br/>
		                      <button type="button" class="commonbut" onclick="deleteOption(form.elements['preferences(propertyIds)'])">删 除</button><br>
		                      <button type="button" class="commonbut" onclick="upperShift(form.elements['preferences(propertyIds)'])">上 移</button><br/>
	                  		  <button type="button" class="commonbut" onclick="lowerShift(form.elements['preferences(propertyIds)'])">下 移</button>
		                  </td>
	                  </tr>
                  </table>
              </td>
            </tr>
            <tr>
              <td class="formTitle">字典大纲</td>
              <td class="formComponent">
              		<html:hidden property="preference(category)"/>
                  	<input type="text" name="categoryName" readonly="readonly" value="<bean:write name="category" property="name" ignore="true"/>" />
                  	<button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(category)'],form.elements['categoryName'])">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">复合属性</td>
              <td class="formComponent">
              	<html:hidden property="preference(compositeProp)"/>
                <input type="text" name="compositePropName" readonly="readonly" value="<bean:write name="composite" property="name" ignore="true"/>">
                <button type="button" class="commonbut" onclick="searchPropertyDefinition(form.elements['preference(category)'],form.elements['preference(compositeProp)'],form.elements['compositePropName'],['1','2','3','4','5','6','7','8','9','10'])">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">显示属性</td>
              <td class="formComponent">
                <html:hidden property="preference(propertyId)"/>
                <input type="text" name="fieldName" readonly="readonly" value="<bean:write name="property" property="name" ignore="true"/>" />
             	<button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(category)'],form.elements['preference(propertyId)'],form.elements['fieldName'],['0','2','3','4','5','6','7','8','9','10'])">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">&nbsp;</td>
              <td class="formComponent">
              		<html:checkbox styleId="IDValue" onclick="clickIDValue(this)" property="preference(IDValue)"><label for="IDValue">以节点ID作为选项值</label></html:checkbox>
              </td>
            </tr>
            <tr id="optValue">
              <td class="formTitle">选项值</td>
              <td class="formComponent">
                <html:hidden property="preference(value)"/>
                <input type="text" name="fieldValue" readonly="readonly" value="<bean:write name="value" property="name" ignore="true"/>" />
              	<button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(category)'],form.elements['preference(value)'],form.elements['fieldValue'],['0','2','3','4','5','6','7','8','9','10'])">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">样式</td>
              <td class="formComponent">
                <html:text property="preference(style)"/>
                <button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(style)'],'')">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle"></td>
              <td class="formComponent">
              	<html:radio property="preference(echo)" value="searchEcho">搜索页回显</html:radio>&nbsp;&nbsp;
              	<html:radio property="preference(echo)" value="nodeEcho">编辑页回显</html:radio>
              </td>
            </tr>
          </table>
        </fieldset>
        <fieldset style="display: none;">
	       	 <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
        	  	<tr>
	              <td class="formTitle">&nbsp;</td>
	              <td class="formComponent">
	                <html:checkbox style="width:25px" property="preference(hasNull)"/>添加空项
	              </td>
	            </tr>
	            <tr>
	              <td class="formTitle">空项值</td>
	              <td class="formComponent"><html:text property="preference(nullValue)"/></td>
	            </tr>
	            <tr>
	              <td class="formTitle">空项文本</td>
	              <td class="formComponent"><html:text property="preference(nullText)"/></td>
	            </tr>
	          </table>
        </fieldset>
        <div class="toolbar">
          <button type="button" onclick="UnchangeSaveConfig(this.form);">保存</button>
          <button type="button" onclick="window.parent.close()">取消</button>
        </div>
      </td>
    </tr>
  </portlet:form>
</table>
 <script type="text/javascript">
	function clickIDValue(oChk){
		if(oChk.checked){
			document.getElementById("optValue").style.display = "none";
		}else{
			document.getElementById("optValue").style.display = "";
		}
	}
	var idv = document.getElementById("IDValue");
	if(idv.fireEvent){
		idv.fireEvent("onclick");
	}else{
		var evt = document.createEvent("HTMLEvents");
		evt.initEvent("click",true,true);
		idv.dispatchEvent(evt);
	}
	 
  	function UnchangeSaveConfig(oform){
		 var propIds = oform.elements['preferences(propertyIds)'];
		 if(propIds.options.length==0){
			alert("关联属性不能为空!");
			return;
		 }
	     selectAll(propIds);
	     validatorRequired(new Array(oform.elements['preference(propertyId)']),new Array('显示属性'),oform)
	}
  </script>
