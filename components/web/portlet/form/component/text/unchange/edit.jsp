<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form action="save" method="POST">
    <tr>
      <td class="pannelDiv" rowspan="2">
        <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
          <option value="0" selected="selected">基本设置</option>
          <option value="1" >其它设置</option>
        </select>
      </td>
      <td>
        <fieldset>
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
            <!-- <tr>
              <td class="formTitle">属性</td>
              <td class="formComponent">
                <input name="preference(category-id)" type="hidden" value="<bean:write name="definition" ignore="true"/>" />
                <html:hidden property="preference(propertyId)"/>
                <input type="text" name="fieldName" readonly="readonly" value="<bean:write name="property" property="name" ignore="true"/>" />
                <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(category-id)'],form.elements['preference(propertyId)'],form.elements['fieldName'],['0','2','3','4','5','6','7','8','9','10'])">选择...</button>
              </td>
            </tr> -->
            <tr>
                  <td class="formTitle" valign="top">关联属性</td>
                  <td nowrap="nowrap"  class="selectionEditor">
                  <input name="preference(category-id)" type="hidden" value="<bean:write name="definition" ignore="true"/>" />
                  <table cellpadding="0" cellspacing="0" border="0">                  
                  <tr>
                  <td>
                    <select multiple="multiple" name="preferences(filter-patterns)" size="5" style="width:210px;">
                      <logic:present name="preferences" property="values(filter-patterns)">
                        <logic:iterate id="selectedProp" name="selectedProps">
                          <option value='<bean:write name="selectedProp" property="ID" ignore="true"/>'><bean:write name="selectedProp" property="name" ignore="true"/></option>
                        </logic:iterate>
                      </logic:present>
                    </select>
                    </td><td valign="top">
                      <button type="button" class="commonbut" onclick="addPropertyDefinition(form.elements['preference(category-id)'],form.elements['preferences(filter-patterns)'],['0','2','3','4','5','6','7','8','9','10'])">添 加</button><br/>
                      <button type="button" class="commonbut" onclick="deleteOption(form.elements['preferences(filter-patterns)'])">删 除</button>
                    </td>
                    </tr>
                    </table>
                  </td>
                </tr>
            <tr>
                <td nowrap="nowrap">提示字库</td>
                <td>
                  <html:hidden property="preference(category)"/>
                  <input type="text" name="categoryName" readonly="readonly" value="<bean:write name="category" property="name" ignore="true"/>" />
                  <button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(category)'],form.elements['categoryName'])">选择...</button>
                </td>
              </tr>
              <tr>
              <td class="formTitle">提示属性</td>
              <td class="formComponent">
                <html:hidden property="preference(suggestPropId)"/>
                <input type="text" name="suggestPropName" readonly="readonly" value="<bean:write name="suggestProp" property="name" ignore="true"/>" />
                <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(category)'],form.elements['preference(suggestPropId)'],form.elements['suggestPropName'],['0','2','3','4','5','6','7','8','9','10'])">选择...</button>
              </td>
            </tr>
              <tr>
              <td class="formTitle"></td>
              <td class="formComponent">
                <html:checkbox style="width:25px" property="preference(hasButton)">带选择按钮</html:checkbox>
              </td>
            </tr>
            <tr>
                	<td class="formTitle"></td>
                  <td class="formComponent" valign="top"><html:checkbox styleId="b1" style="width:25px" property="preference(lucene)"><label for="b1">lucene全文搜索</label></html:checkbox></td>
                </tr>
            </table>
            </fieldset>
            <fieldset style="display:none;">
            <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
            <tr>
              <td class="formTitle">按钮样式</td>
              <td class="formComponent">
                <html:text property="preference(buttonStyle)"/><button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(buttonStyle)'],'')">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">Tab键顺序</td>
              <td class="formComponent">
                <html:text property="preference(tabindex)"/>
              </td>
            </tr>
            <tr>
              <td class="formTitle">样式</td>
              <td class="formComponent">
                <html:text property="preference(style)"/><button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(style)'],'')">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">长度</td>
              <td class="formComponent"><html:text property="preference(size)" onblur="validatorInteger(this)"/></td>
            </tr>
            <tr>
            <tr>
              <td class="formTitle">最多字符数</td>
              <td class="formComponent"><html:text property="preference(maxLength)" onblur="validatorInteger(this)"/></td>
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
        <div class="toolbar">
          <button type="button" onclick="UnchangeSaveConfig(this.form),validatorRequired(new Array(form.elements['preference(category)']),new Array('提示字库'),this.form)">保存</button>
          <button type="button" onclick="window.parent.close()">取消</button>
        </div>
      </td>
    </tr>
  </portlet:form>
</table>
<script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
<script type="text/Javascript" language="Javascript">
function UnchangeSaveConfig(oform){
	 var filter = oform.elements['preferences(filter-patterns)'];
     var ops = filter.options;
     for(var i=0;i<ops.length;i++){
    	 ops[i].selected =true;
     }
}
</script>