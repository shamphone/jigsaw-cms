<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form  action="save" method="POST">
    <tr>
      <td class="pannelDiv">
        <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
          <option value="0" selected="selected">内容抽取</option>
          <option value="1">表格设置</option>
          <option value="2">分页设置</option>
          <option value="3">过滤属性</option>
          <option value="4">重复内容</option>
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
                  <td><html:radio style="width:25px" value="ASC" property="preference(order-style)"><bean:message  bundle="tableXrepeater" key="com.fulong.cms.portlet.xrepeater.edit.formTitle11.option7.ASC"/></html:radio> &nbsp;&nbsp;<html:radio style="width:25px" value="DESC" property="preference(order-style)"><bean:message  bundle="tableXrepeater" key="com.fulong.cms.portlet.xrepeater.edit.formTitle11.option8.DESC"/></html:radio>                            </td>
                </tr>
            <tr>
                	<td></td>
                  <td class="formComponent" valign="top"><html:checkbox styleId="b1" style="width:25px" property="preference(lucene)"><label for="b1">lucene全文搜索</label></html:checkbox></td>
                </tr>
                                          
              </table>
            </fieldset>
            <fieldset style="display:none">
              <table width="100%" cellpadding="2" cellspacing="2" border="0">
                <tr>
                  <td nowrap="nowrap">单元格衬距</td>
                  <td><html:text  property="preference(cellspacing)" size="8" onblur="validatorInteger(this)"/></td>
                  <td>间距</td>
                  <td><html:text  property="preference(cellpadding)" size="8" onblur="validatorInteger(this)"/></td>
                </tr>
                <tr>
                  <td>边框粗细</td>
                  <td><html:text  property="preference(border)" size="8" onblur="validatorInteger(this)"/></td>
                  <td>颜色</td>
                  <td><html:text  property="preference(bordercolor)" size="8"/></td>
                </tr>
                <tr>
                  <td>表格宽度</td>
                  <td><html:text  property="preference(width)" size="8"/></td>
                  <td>高度</td>
                  <td><html:text  property="preference(height)" size="8"/></td>
                </tr>
                <tr>
                  <td nowrap="nowrap">表格行数</td>
                  <td><html:text property="preference(row)" size="8" onblur="validatorInteger(this)"/></td>
                  <!--<td>水平对齐</td>
                  <td class="formComponent" colspan="3"><html:select property="preference(align)"  style="width:83px">
                  <html:option value="left">居左</html:option>
                  <html:option value="center">居中</html:option>
                  <html:option value="right">居右</html:option>
                  </html:select>
                  </td>-->
                </tr>
                <tr>
                  <td>表格样式类</td>
                  <td colspan="3"><html:text  property="preference(table-style)"/>
                  <button type="button" class="commonbut" onclick="selectStyle(this.form.elements['preference(table-style)'])">选择...</button>
                  </td>
                </tr>            
                <tr>
                  <td>表格样式</td>
                  <td colspan="3"><html:text  property="preference(style)" size="30"/>
                  </td>
                </tr>
                <tr>
                  <td><html:radio property="preference(nonode)" value="character" onclick="showCharacter(this)"></html:radio></td>
                  <td>无结果文字</td>
                  <td colspan="2"><html:text  property="preference(noContentWord)" size="25" readonly="true"/>
                  </td>
                </tr>
                <tr>
                <td><html:radio property="preference(nonode)" value="table" onclick="showCharacter(this)"></html:radio></td>
                <td>无结果时显示表格</td>
                <td colspan="2"></td>
                </tr>
                <tr>
                  <td>无结果文字样式</td>
                  <td colspan="3"><html:text  property="preference(noContentStyle)" />
                  <button type="button" class="commonbut" onclick="selectStyle(this.form.elements['preference(noContentStyle)'])">选择...</button>
                  </td>
                </tr> 
              </table>
            </fieldset>
              <fieldset style="display:none">
              <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
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
                <tr>
                  <td></td>
                  <td>从第<html:text property="preference(startloc)" style="width:60px" onblur="validatorInteger(this)"/>条开始展示</td>
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
          		<button type="button" onclick="XRepeaterdoSaveConfig(this.form)">保存</button>
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
      /**
       *编辑页面片段
       **/
       function editClipFile(path,oCategory,oFrame, formDefinition){
     	  var definitionId=null;
     	  if(oCategory!=null){
     		  definitionId=oCategory.value;
     	  }
         var ret=SiteDialog.tableEditClipFile(path,definitionId, formDefinition);
         if(ret!=null&&oFrame!=null){
         	oFrame.contentWindow.location.href=oFrame.contentWindow.location.href+"&a="+Math.random();
         }
       }

      /*
      * 操作无结果是显示逻辑
      */
      function showCharacter(e){
          if(e && e.value == "character"){
              document.getElementsByName("preference(noContentWord)")[0].readOnly = false;
          }else if(e && e.value == "table"){
        	  document.getElementsByName("preference(noContentWord)")[0].value = "";
        	  document.getElementsByName("preference(noContentWord)")[0].readOnly = true;
          }
      }
    </script>
