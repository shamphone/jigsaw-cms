<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
    <script type="text/Javascript" src='/ide/common/script/xmlhttp.js'></script>
    <script type="text/Javascript" src='/ide/common/script/portlet.js'></script>
    <script type="text/Javascript" language="Javascript">
    function UnchangeSaveConfig(oform){
	     selectAll(oform.elements['preferences(filter-patterns)']);
	}
	function addFilter(definition,filter){
		if(definition.value==""){
			alert("请选择内容分类！")
			return;
		}
		newFilter(definition,filter);				
	}
	function Ok(oForm){
		if(oForm.elements['preference(direct)'].checked==false&&oForm.elements['preference(row)'].value==""){
			alert("分类标签不能为空");
			return false;
		}
		UnchangeSaveConfig(oForm);
		validatorRequired(new Array(oForm.elements['preference(property)']),new Array('统计属性'),oForm);
	}


		                 	
	
</script>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form  action="save" method="POST">
    <tr>
      <td class="pannelDiv">
        <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
          <option value="0" selected="selected">内容抽取</option>
          <option value="1">图例样式</option>
        </select></td>
        <td>
          <fieldset>
            <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
              <tr>
                <td nowrap="nowrap">内容分类</td>
                <td>
                  <html:hidden property="preference(definition)"/>
                  <input type="text" name="categoryName" readonly="readonly" value="<bean:write name="category" property="name" ignore="true"/>" />
                  <button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(definition)'],form.elements['categoryName'])">选择...</button>
                </td>
              </tr>
              <tr>
                <td></td>
                <td><html:checkbox style="width:25px" property="preference(recursive)" styleId="a1" value="true"/><label for="a1">包含子分类</label></td>
              </tr>
              <tr>
                <td></td>
                <td><html:checkbox style="width:25px" property="preference(global)" styleId="a2" value="true"/><label for="a2">从本系统所有网站的内容库中抽取</label></td>
              </tr>
              <tr>
                <td></td>
                <td><html:checkbox style="width:25px" property="preference(direct)" styleId="a3" value="true"/><label for="a3">同值属性累计分组统计</label></td>
              </tr>
              <tr>
                <td>统计属性</td>
                <td>
                <!--  
                <table cellpadding="0" cellspacing="0" border="0">
                  <tr>
	                  <td>
	                  <html:select multiple="multiple" property="preferences(property)" size="5" style="width:210px;">
	                    <logic:present name="properties">
	                      <logic:iterate id="property" name="properties">
	                        <option value='<bean:write name="property" property="ID"/>'><bean:write name="property" property="name"/></option>
	                      </logic:iterate>
	                    </logic:present>
	                  </html:select>
	                  </td><td valign="top">
	                    <button type="button" class="commonbut" onclick="addPropertyDefinition(form.elements['preference(definition)'],form.elements['preferences(property)'],null)">添 加</button><br/>
                      	<button type="button" class="commonbut" onclick="deleteOption(form.elements['preferences(property)'])">删 除</button>
	                  </td>
                  </tr>
                </table> -->
                <html:hidden property="preference(property)"/>
                <logic:present name="property">
                	<input type="text" name="fieldCoutName" readonly="readonly" value="<bean:write name="property" property="name" ignore="true"/>" />
                </logic:present>
                <logic:notPresent name="property">
                		<input type="text" name="fieldCoutName" readonly="readonly" value="<bean:write name="propertyDeleted" ignore="true"/>" />
                </logic:notPresent>
                  <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(definition)'],form.elements['preference(property)'],form.elements['fieldCoutName'])">选择...</button>
                  </td>
                </tr>
                <tr>
                <td>分类标签</td>
                <td><html:hidden property="preference(row)"/>
                <logic:present name="row">
                  <input type="text" name="fieldName" readonly="readonly" value="<bean:write name="row" property="name" ignore="true"/>" />
                 </logic:present>
                 <logic:notPresent name="row">
                 	<input type="text" name="fieldName" readonly="readonly" value="<bean:write name="rowDeleted" ignore="true"/>" />
                 </logic:notPresent>
                  <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(definition)'],form.elements['preference(row)'],form.elements['fieldName'],[0,2,5,6,7,8,9,10])">选择...</button></td>
                </tr>
                <tr>
                  <td class="formTitle" valign="top">过滤属性</td>
                  <td nowrap="nowrap"  class="selectionEditor">
                  <table cellpadding="0" cellspacing="0" border="0">
                  <tr><td><html:checkbox styleId="b1" style="width:25px" property="preference(filterByParamet)"><label for="b1">自动接收URL参数</label></html:checkbox></td></tr>
                  <tr>
                  <td>
                    <select multiple="multiple" name="preferences(filter-patterns)" size="5" style="width:210px;">
                      <logic:present name="preferences" property="values(filter-patterns)">
                        <logic:iterate id="pattern" name="preferences" property="values(filter-patterns)">
                          <option value='<bean:write name="pattern" filter="false"/>'><fulong:filter name="pattern" definition="category"/></option>
                        </logic:iterate>
                      </logic:present>
                    </select>
                    </td><td valign="top">
                      <button type="button" class="commonbut" onclick="addFilter(form.elements['preference(definition)'],form.elements['preferences(filter-patterns)'])">添 加</button><br/>
                      <button type="button" class="commonbut" onclick="deleteOption(form.elements['preferences(filter-patterns)'])">删 除</button>
                    </td>
                    </tr>
                    </table>
                  </td>
                </tr>
              </table>
            </fieldset><fieldset style="display:none">
            <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
              <tr>
                <td nowrap="nowrap">标题</td>
                <td>
                  <html:text property="preference(title)"/>
                </td>
              </tr>
              <tr>
                <td nowrap="nowrap">高度</td>
                <td>
                  <html:text property="preference(height)"/>
                </td>
              </tr>
              <tr>
                <td nowrap="nowrap">宽度</td>
                <td>
                  <html:text property="preference(width)"/>
                </td>
              </tr>
              <tr>
                <td nowrap="nowrap"></td>
                <td>
                  <html:checkbox property="preference(legend)" style="width:25px"  value="true" styleId="legend"/><label for="legend">显示图例</label>
                </td>
              </tr>
              </table>
            </fieldset>
            
            <div class="toolbar">
          		<button type="button" onclick="Ok(this.form)">保存</button>
          		<button type="button" onclick="window.parent.close()">取消</button>
            </div>
      </td>
    </tr>
  </portlet:form>
  </table>
