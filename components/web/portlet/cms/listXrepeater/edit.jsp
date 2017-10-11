<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
    <script type="text/Javascript" src='/ide/common/script/ajax.js'></script>
    <script type="text/Javascript" src='/ide/common/script/portlet.js'></script>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form  action="save" method="POST">
    <tr>
      <td class="pannelDiv">
        <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
          <option value="0" selected="selected">内容抽取</option>
          <option value="1">内容展示</option>
          <option value="2">过滤属性</option>
          <option value="3">重复内容</option>
        </select></td>
        <td>
          <fieldset>
            <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
              <tr>
                <td nowrap="nowrap">内容分类</td>
                <td>
                  <html:hidden property="preference(category)"/>
                  <input type="text" name="categoryName" readonly="readonly" value="<bean:write name="category" property="name" ignore="true"/>" />
                  <button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(category)'],form.elements['categoryName'])">选择...</button>
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
                	<td><html:checkbox style="width:25px" value="recursion" property="preference(recursion)">递归抽取</html:checkbox></td>
                </tr>
              <tr>
                <td class="formTitle" valign="top">引用/复合属性</td>
                <td class="formComponent" valign="top">
                  <html:hidden property="preference(refField)"/>
                  <html:hidden property="preference(refDefinition)"/>
                  <logic:notPresent name="propertyDeleted">
                  	<input type="text" name="comDefinitionName" readonly="readonly" value="<bean:write name="property" property="name" ignore="true"/>" />
                  </logic:notPresent>
                  <logic:present name="propertyDeleted">
                  	<input type="text" name="comDefinitionName" readonly="readonly" value="<bean:write name="propertyDeleted"  ignore="true"/>" />
                  </logic:present>
                  <button type="button" class="commonbut" id="search" onclick="searchRefPropertyDefinition(form.elements['preference(category)'],form.elements['preference(refField)'],form.elements['preference(refDefinition)'],form.elements['comDefinitionName'])">选择...</button>
                  <button type="button" class="commonbut" id="clear" onclick="clearTxt(form.elements['preference(refField)'],form.elements['comDefinitionName'])">清空</button>
                </td>
              </tr>
              <tr>
                <td>排序属性</td>
                <td><html:hidden property="preference(order-field)"/>
                  <input type="text" name="fieldName" readonly="readonly" value="<bean:write name="orderProperty" property="name" ignore="true"/>" />
                  <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(category)'],form.elements['preference(order-field)'],form.elements['fieldName'])">选择...</button>
                  <button type="button" class="commonbut" id="clear" onclick="clearTxt(form.elements['preference(order-field)'],form.elements['fieldName'])">清空</button>
                  </td>
                </tr>
                <tr>
                  <td></td>
                  <td><html:radio style="width:25px" value="ASC" property="preference(order-style)"><bean:message  bundle="list" key="com.fulong.cms.portlet.list.edit.formTitle11.option7.ASC"/></html:radio>&nbsp;&nbsp;<html:radio style="width:25px" value="DESC" property="preference(order-style)"><bean:message  bundle="list" key="com.fulong.cms.portlet.list.edit.formTitle11.option8.DESC"/></html:radio></td>
                </tr> 
                <tr>
                	<td></td>
                  <td class="formComponent" valign="top"><html:checkbox styleId="b1" style="width:25px" property="preference(lucene)"><label for="b1">lucene全文搜索</label></html:checkbox></td>
                </tr>
              </table>
            </fieldset>
            <fieldset style="display:none">
              <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
                <tr>
                  <td nowrap="nowrap">列表行数</td>
                  <td class="formComponent" nowrap="nowrap"><html:text property="preference(row)" onblur="validatorInteger(this)"/></td>
                </tr>
                <tr>
                  <td nowrap="nowrap">从第</td>
                  <td class="formComponent" nowrap="nowrap"><html:text property="preference(startloc)" onblur="validatorInteger(this)"/>个开始展示</td>
                </tr>             
                <tr>
                  <td class="formTitle">列表样式</td>
                  <td class="formComponent"><html:text  property="preference(list-style)"/>
                  <button type="button" class="commonbut" onclick="selectStyle(this.form.elements['preference(list-style)'])">选择...</button>
                  </td>
                </tr>
                <tr>
                  <td class="formTitle">分页模式</td>
                  <td class="formComponent"><html:select property="preference(show-pager)"  style="width:210px">
                  <html:option value="false">不显示分页</html:option>
                  <html:option value="true">显示不带跳转输入的分页</html:option>
                  <html:option value="isGotoPager">显示带跳转输入的分页</html:option>
                  <html:option value="baidu1">百度分页一</html:option>
                  <html:option value="baidu2">百度分页二</html:option>
                  </html:select>
                  </td>
                </tr>
                
                <tr>
                  <td class="formTitle">分页样式</td>
                  <td class="formComponent"><html:text  property="preference(pager-style)"/>
					<button type="button" class="commonbut" onclick="selectStyle(this.form.elements['preference(pager-style)'])">选择...</button>	
					</td>
                </tr>
                <tr>
                <td></td>
                <td><html:checkbox style="width:25px" property="preference(showPage)" styleId="a0" value="true"/><label for="a0">重复器上方显示分页</label></td>
                </tr>
              </table>
            </fieldset>
            <fieldset style="display:none">
              <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
                <tr>
                  <td class="formComponent" valign="top"><html:checkbox styleId="b1" style="width:25px" property="preference(filterByParamet)"><label for="b1">自动接收URL参数</label></html:checkbox></td>
                </tr>
                <tr>
                  <td class="formComponent" valign="top">
                  	<html:checkbox style="width:25px" property="preference(fromSearchDef)" styleId="a3" value="true"/><label for="a3">从搜索大纲中提取过滤条件</label>
                  	<button type="button" style="margin-left: 103px;" onclick="autoAddFilterPattern(form.elements['preference(category)'].value,form.elements['preferences(filter-patterns)'],'<bean:write name="searchDefinition" property="ID" ignore="true"/>')">自动添加</button>
                  </td>
                </tr>
                <tr>
                  <td class="formComponent">
                  	<select multiple="multiple" name="preferences(filter-patterns)" size="14" style="width:350px;">
                      <logic:present name="preferences" property="values(filter-patterns)">
                        <logic:iterate id="pattern" name="preferences" property="values(filter-patterns)">
	                        <logic:present name="searchDefinition">
	                        	<option value='<bean:write name="pattern" filter="false"/>'><fulong:filter name="pattern" definition="definition" searchDef="searchDefinition"/></option>
	                        </logic:present>
	                        <logic:notPresent name="searchDefinition">
	                        	<option value='<bean:write name="pattern" filter="false"/>'><fulong:filter name="pattern" definition="definition"/></option>
	                        </logic:notPresent>
                        </logic:iterate>
                      </logic:present>
                    </select>
                    <div style="margin-top:5px;" align="right">
                    	<button type="button" class="commonbut" onclick="newFilter_Search(form.elements['preference(category)'],form.elements['preferences(filter-patterns)'],'<bean:write name="searchDefinition" property="ID" ignore="true"/>')">添 加</button>
                    	<button type="button" class="commonbut" onclick="deleteOption(form.elements['preferences(filter-patterns)'])">删 除</button>
                    </div>                    
				  </td>
                </tr>
              </table>
              </fieldset>
            <fieldset style="display:none">
              <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
                <tr>
                  <td class="formTitle">重复内容</td>
                  <td class="formComponent" width="80%"><bean:parameter id="formDefinition" name="formDefinition" value="" />
                  <html:hidden property="preference(contextName)"/>
          			<button type="button" onclick="editClipFile('<bean:write name="path"/>',form.elements['preference(category)'],document.getElementById('editorFrame'),'<bean:write name="formDefinition" ignore="true"/>',window.parent.dialogArguments.styleSheets)">修改...</button>
                  </td>
                </tr>
                <tr>
                  <td colspan="2">
                    <iframe scrolling="yes" marginheight="0" marginwidth="0" frameborder="1" src='<bean:write name="clipPath"/>?javax.portlet.page.mode=view' width="350px" height="280px" id="editorFrame">                </iframe>
                  </td>
                </tr>
              </table>
            </fieldset>
            <div class="toolbar">
          		<button type="button" type="submit" onclick="XRepeaterdoSaveConfig(this.form)">保存</button>
          		<button type="button" onclick="window.parent.close()">取消</button>
            </div>
      </td>
    </tr>
  </portlet:form>
  </table>
  <script type="text/Javascript" language="Javascript">
    var definitionId='<bean:write name="preferences" property="value(category)" ignore="true"/>';
      function XRepeaterdoSaveConfig(oForm){
    	 oForm.elements['preference(contextName)'].value=window.parent.dialogArguments.template.name;
          selectAll(oForm.elements['preferences(filter-patterns)'])
          oForm.submit();
      }

      /**
      *
      *选择引用属性
      **/
      function searchRefPropertyDefinition(oCategory, oID, oRefID, oRefName){
        var arr =  CMSDialog.PropertyDefinitionSelector(oCategory.value,['1','2','3','4','5','6','7','8','10']);
        if(arr!=null){
          oID.value = arr.ID;
          oRefName.value = arr.name;
          oRefID.value = arr.refID;
        }
      }

    </script>
