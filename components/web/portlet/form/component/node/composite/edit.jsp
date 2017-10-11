<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@ taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
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
              <td class="formTitle">关联属性</td>
              <td class="formComponent">
                <input type="hidden" name="categoryId" value="<bean:write name="definition" ignore="true"/>"/>
                <html:hidden property="preference(propertyId)"/>
                <input type="text" name="fieldName" readonly="readonly" value="<logic:present name="property"><bean:write name="property" property="name" ignore="true"/></logic:present><logic:notPresent name="property"><bean:write name="propertyDeleted" ignore="true"/></logic:notPresent>" />
                <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['categoryId'],form.elements['preference(propertyId)'],form.elements['fieldName'],['1','2','3','4','5','6','7','8','9','10'])">选择...</button>
              </td>
            </tr>
              <tr>
                <td class="formTitle">选择范围</td>
                <td class="formComponent"><html:hidden property="preference(category)"/><input type="text" name="categoryName" readonly="readonly" value="<bean:write name="category" property="name" ignore="true"/>" />
                  <button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(category)'],form.elements['categoryName'])">选择...</button>
                </td>
              </tr>
            <tr>
              <td class="formTitle">显示属性</td>
              <td class="formComponent">
                <html:hidden property="preference(viewPropertyId)"/>
                <input type="text" name="viewFieldName" readonly="readonly" value="<bean:write name="viewProperty" property="name" ignore="true"/>" />
                <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(category)'],this.form.elements['preference(viewPropertyId)'],this.form.elements['viewFieldName'])">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">列表行数</td>
              <td class="formComponent">
                <html:text property="preference(size)"/>
              </td>
            </tr>
            <tr>
              <td class="formTitle">列表样式</td>
              <td class="formComponent">
                <html:text property="preference(listStyle)"/><button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(listStyle)'],'')">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">默认值</td>
              <td class="formComponent">
                 <table cellpadding="0" cellspacing="0" border="0" >
                 <tr><td>
                    <html:select multiple="true" property="preferences(defaultValues)" size="7" style="width:210px;">
                    	<logic:notEmpty name="values">
                 		<bean:define id="property" name="preferences" property="value(viewPropertyId)" type="String"/>
						<logic:iterate id="value" name="values">
							<option value="<bean:write name="value" property="ID"/>"><cms:node name="value" propertyName="<%=property%>"/></option>
						</logic:iterate>
						</logic:notEmpty>
						<logic:present name="preferences" property="value(defaultValues)">
						<logic:iterate id="value" name="preferences" property="values(defaultValues)">
						<logic:equal value="user" name="value">
							<option value="user">当前用户</option>
						</logic:equal>
						</logic:iterate>
						</logic:present>
                    </html:select>
                    </td><td valign="top">
                    	<button type="button" class="commonbut" onclick="nodeSelect.multipleNodeSelect(this.form['preference(category)'].value,this.form['preferences(defaultValues)'],this.form['preference(viewPropertyId)'].value)">选择...</button><br/>
						<button type="button" class="commonbut" onclick="nodeSelect.user(this.form['preferences(defaultValues)'])">当前用户</button><br/>
						<button type="button" class="commonbut" onclick="upperShift(this.form.elements['preferences(defaultValues)'])">上 移</button><br/>
						<button type="button" class="commonbut" onclick="lowerShift(this.form.elements['preferences(defaultValues)'])">下 移</button><br/>
						<button type="button" class="commonbut" onclick="deleteOption(this.form.elements['preferences(defaultValues)'])">删 除</button>
                      </td></tr>
                 </table>
              </td>
            </tr>
            <tr>
              <td class="formTitle">显示按钮</td>
              <td class="formComponent">
                <html:checkbox style="width:25px" property="preference(hasModify)">修改</html:checkbox>&nbsp;<html:checkbox style="width:25px" property="preference(hasUp)">上移</html:checkbox>&nbsp;<html:checkbox style="width:25px" property="preference(hasDown)">下移</html:checkbox>
              </td>
            </tr>
          </table>
        </fieldset>
        <div class="toolbar">
          <button type="button" onclick="doSubmit(this.form);validatorRequired(new Array(form.elements['preference(propertyId)'],form.elements['preference(category)'],form.elements['preference(viewPropertyId)']),new Array('关联属性','选择范围','显示属性'),this.form)">保存</button>
          <button type="button" onclick="window.parent.close()">取消</button>
        </div>
      </td>
    </tr>
  </portlet:form>
</table><script type="text/javascript" language="javascript">
            function doSubmit(oForm){
              selectAll(document.getElementsByName('preferences(defaultValues)')[0]);
              return true;
            }
            </script>
<script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
<script language="javascript" src="/components/portlet/form/component/node/script.js.jsp" type="text/javascript" ></script>
