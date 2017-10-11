<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form action="save" method="POST">
    <tr>
      <td class="pannelDiv" rowspan="2">
        <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
          <option value="0" selected="selected">基本设置</option>
        </select>
      </td>
      <td>
        <fieldset>
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
            <tr>
              <td class="formTitle">属性</td>
              <td class="formComponent">
                <input name="preference(category-id)" type="hidden" value="<bean:write name="definition" ignore="true"/>" />
                <html:hidden property="preference(propertyId)"/>
                <input type="text" name="fieldName" readonly="readonly" value="<logic:present name="property"><bean:write name="property" property="name" ignore="true"/></logic:present><logic:notPresent name="property"><bean:write name="propertyDeleted" ignore="true"/></logic:notPresent>" />
                <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(category-id)'],form.elements['preference(propertyId)'],form.elements['fieldName'],['0','2','3','4','5','6','7','8','9','10'])">选择...</button>
              </td>
            </tr>
            <!-- <tr>
              <td class="formTitle">初始值</td>
              <td class="formComponent"><html:text property="preference(value)"/> </td>
            </tr> --> 
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
            	<td nowrap="nowrap">过滤属性</td>
                <td nowrap="nowrap"  class="selectionEditor">
                  <table cellpadding="0" cellspacing="0" border="0">
            		<tr>
                  		<td>
                            <select multiple="multiple" name="preferences(conditions)" size="5" multiple="true" style="width:210px;">
                                <logic:present name="preferences" property="values(conditions)">
                                    <logic:iterate id="pattern" name="preferences" property="values(conditions)">
                                        <option value='<bean:write name="pattern" filter="false"/>'>
                                        <fulong:filter name="pattern" definition="category"/></option>
                                    </logic:iterate>
                                </logic:present>
                            </select>
                    	</td>
                    	<td valign="top">
                        	<button type="button" class="commonbut" onclick="newFilter(form.elements['preference(category)'],form.elements['preferences(conditions)'])">添加</button><br/>
                        	<button type="button" class="commonbut" onclick="deleteOption(form.elements['preferences(conditions)'])">删除</button>
                    	</td>
                    </tr>
                  </table>
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
              <td class="formComponent">
              	<html:text property="preference(size)" onblur="validatorInteger(this)" style="width:60px;"/>
              	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最多字符数&nbsp;&nbsp;<html:text property="preference(maxLength)" onblur="validatorInteger(this)" style="width:60px;"/>
              </td>
            </tr>
            <tr>
              <td class="formTitle"></td>
              <td class="formComponent">
              	<html:radio property="preference(echo)" value="searchEcho">搜索页回显</html:radio>&nbsp;&nbsp;
              	<html:radio property="preference(echo)" value="nodeEcho">编辑页回显</html:radio>
              </td>
            </tr>
            <tr>
                	<td class="formTitle"></td>
                  <td class="formComponent" valign="top"><html:checkbox styleId="b1" style="width:25px" property="preference(lucene)"><label for="b1">lucene全文搜索</label></html:checkbox></td>
                </tr>
          </table>
        </fieldset>
        <div class="toolbar">
          <button type="button" onclick="doSubmit(this.form)">保存</button>
          <button type="button" onclick="window.parent.close()">取消</button>
        </div>
      </td>
    </tr>
  </portlet:form>
</table>
<script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
<script type="text/javascript" language="javascript">
	function doSubmit(oForm){
    	selectAll(document.getElementsByName('preferences(conditions)')[0]);
    	validatorRequired(new Array(oForm.elements['preference(propertyId)'],oForm.elements['preference(category)']),new Array('属性','提示字库'),oForm)
	}
</script>