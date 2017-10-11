<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form action="save" method="POST">
    <html:hidden property="preference(definitionId)"/>
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
              <td class="formTitle">引用属性</td>
              <td class="formComponent">
                <input type="hidden" name="categoryId" value="<bean:write name="definition" ignore="true"/>"/>
                <html:hidden property="preference(propertyId)"/>
                <input type="text" name="fieldName" readonly="readonly" value="<bean:write name="property" property="name" ignore="true"/>" />
                <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(this.form['categoryId'],this.form['preference(propertyId)'],this.form['fieldName'])">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">Key/Value</td>
              <td class="formComponent">
                 <table cellpadding="0" cellspacing="0" border="0" >
                 <tr><td>
                    <select multiple="multiple" name="preferences(values)" size="5" style="width:210px;">
                      <logic:present name="preferences" property="values(values)">
                        <logic:iterate id="value" name="preferences" property="values(values)">
                          <option value='<bean:write name="value" filter="false"/>'><bean:write name="value" filter="false"/></option>
                        </logic:iterate>
                      </logic:present>
                    </select>
                    </td><td valign="top">
                      <button type="button" class="commonbut" onclick="newOption(form.elements['preferences(values)'])">添加...</button><br/>
                      <button type="button" class="commonbut" onclick="deleteOption(form.elements['preferences(values)'])">删 除</button><br/>
                      <button type="button" class="commonbut" onclick="upperShift(form.elements['preferences(values)'])">上 移</button><br/>
                      <button type="button" class="commonbut" onclick="lowerShift(form.elements['preferences(values)'])">下 移</button>
                    </td></tr>
                 </table>
              </td>
            </tr>
            <tr>
              <td class="formTitle">默认值</td>
              <td class="formComponent">
                <html:text property="preference(default-value)"/>
              </td>
            </tr>
            <tr>
              <td class="formTitle">Tab键顺序</td>
              <td class="formComponent">
                <html:text property="preference(tabindex)"/>
              </td>
            </tr>
            <tr>
              <td class="formTitle">列表样式</td>
              <td class="formComponent">
                <html:text property="preference(style)"/>
                <button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(style)'],'')">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">列表行数</td>
              <td class="formComponent"><html:text property="preference(size)" onblur="validatorInteger(this)"/></td>
            </tr>
            <tr>
              <td class="formTitle"></td>
              <td class="formComponent">
              	<html:radio property="preference(echo)" value="searchEcho">搜索页回显</html:radio>&nbsp;&nbsp;
              	<html:radio property="preference(echo)" value="nodeEcho">编辑页回显</html:radio>
              </td>
            </tr>
            <tr>
              <td class="formTitle">&nbsp;</td>
              <td class="formComponent">
                <html:checkbox style="width:25px" property="preference(multiple)"/>可以多选
              </td>
            </tr>
          </table>
        </fieldset>
        <div class="toolbar">
          <button type="button" onClick="ok(this.form),validatorRequired(new Array(form.elements['preference(propertyId)']),new Array('引用属性'),this.form)">保存</button>
          <button type="button" onclick="window.parent.close()">取消</button>
        </div>
      </td>
    </tr>
  </portlet:form>
</table>
<script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
<script type="text/javascript" language="javascript">
function newOption(oSelect){
	var ret = showModalDialog("/components/portlet/form/component/select/manual/newOption.jsp","","dialogWidth:250px;dialogHeight:110px;help:no;scrollbars:no;status:no");
	if(ret!=null){
    addOption(oSelect,ret.value+","+ret.text,ret.value+","+ret.text);
	}
}
function ok(oForm){
    selectAll(oForm.elements['preferences(values)']);
}
</script>
