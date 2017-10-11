<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form action="save" method="POST" onsubmit="doSubmit(this)">
    <tr>
      <td class="pannelDiv" rowspan="2">
        <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
          <option value="0" selected="selected">基本设置</option>
          <option value="1">详细设置</option>
          <option value="2">选择页面</option>
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
                <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['categoryId'],form.elements['preference(propertyId)'],form.elements['fieldName'],['0','1','2','3','4','5','6','7','8','10'])">选择...</button>
                
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
                <html:text property="preference(size)" onblur="validatorInteger(this)"/>
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
                    	<button type="button" class="commonbut" onclick="nodeSelect.multipleNodeSelect(this.form['preference(category)'].value,this.form['preferences(defaultValues)'],this.form['preference(viewPropertyId)'].value,null,null,null,this.form.elements['preference(viewPropertyId)'].value)">选择...</button><br/>
						<button type="button" class="commonbut" onclick="nodeSelect.user(this.form['preferences(defaultValues)'])">当前用户</button><br/>
						<button type="button" class="commonbut" onclick="upperShift(this.form.elements['preferences(defaultValues)'])">上 移</button><br/>
						<button type="button" class="commonbut" onclick="lowerShift(this.form.elements['preferences(defaultValues)'])">下 移</button><br/>
						<button type="button" class="commonbut" onclick="deleteOption(this.form.elements['preferences(defaultValues)'])">删 除</button>
                      </td></tr>
                 </table>
              </td>
            </tr>
          </table>
        </fieldset>
        <fieldset style="display:none">
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">

            <tr>
              <td>排序属性</td>
              <td><html:hidden property="preference(orderfield)"/>
                <input type="text" name="OrderfieldName" readonly="readonly" value="<bean:write name="orderProperty" property="name" ignore="true"/>" />
                <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(category)'],form.elements['preference(orderfield)'],form.elements['OrderfieldName'])">选择...</button>
                <button type="button" class="commonbut" id="clear" onclick="clearTxt(form.elements['preference(orderfield)'],form.elements['OrderfieldName'])">清空</button>
                </td>
            </tr>
            <tr>
              <td class="formTitle"></td>
              <td class="formComponent">
                <html:radio style="width:25px" property="preference(sort)" value="asc"/>升序&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:radio style="width:25px" property="preference(sort)" value="desc"/>降序
            </tr>
            <tr>
                  <td class="formTitle" valign="top">属性</td>
                  <td nowrap="nowrap"  class="selectionEditor">
                  <table cellpadding="0" cellspacing="0" border="0">                  
                  <tr>
                  <td>
                    <select multiple="multiple" name="preferences(filterpatterns)" size="5" style="width:210px;">
                      <logic:present name="preferences" property="values(filterpatterns)">
                        <logic:iterate id="selectedProp" name="selectedProps">
                          <option value='<bean:write name="selectedProp" property="ID" ignore="true"/>'><bean:write name="selectedProp" property="name" ignore="true"/></option>
                        </logic:iterate>
                      </logic:present>
                    </select>
                    </td><td valign="top">
                      <button type="button" class="commonbut" onclick="addPropertyDefinition(form.elements['preference(category)'],form.elements['preferences(filterpatterns)'])">添 加</button><br/>
                      <button type="button" class="commonbut" onclick="deleteOption(form.elements['preferences(filterpatterns)'])">删 除</button>
                    </td>
                    </tr>
                    </table>
                  </td>
                </tr>
              <tr>
              <td class="formTitle"></td>
              <td class="formComponent">
                <html:checkbox style="width:25px" property="preference(left)">不显示左侧窗口</html:checkbox>&nbsp;&nbsp;&nbsp;&nbsp;<html:checkbox style="width:25px" property="preference(searchText)">不显示搜索栏</html:checkbox>
             </td>
             </tr>
            <tr>
              <td class="formTitle">最多显示</td>
              <td class="formComponent"><html:text property="preference(length)" onblur="validatorInteger(this)"></html:text>个字</td>
            </tr>
            <tr>
              <td class="formTitle">显示按钮</td>
              <td class="formComponent">
                <html:checkbox style="width:25px" property="preference(hasUp)">上移</html:checkbox>&nbsp;&nbsp;<html:checkbox style="width:25px" property="preference(hasDown)">下移</html:checkbox>&nbsp;&nbsp;<html:checkbox style="width:25px" property="preference(delete)">删除</html:checkbox>
              </td>
            </tr>
           </table>
        </fieldset>
        <fieldset style="display:none">
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
            <tr valign="top">
               <td class="formTitle">选择页面</td>
               <td class="formComponent">
               		<html:radio style="width:25px" property="preference(selectChannel)" value="defaultPage"/>默认页面<br />
               		<html:radio style="width:25px" property="preference(selectChannel)" value="customerPage"/>自定义页面&nbsp;
                   <html:hidden property="preference(channel)"/>
                      <input type="text" name="channelName" <logic:notEmpty name="channel">value="<bean:write name="siteTemplate" property="displayName" ignore="true"/>.<bean:write name="channel" property="displayName" ignore="true"/>"</logic:notEmpty>/><button type="button" class="commonbut" onclick="chooseColumn('<bean:write name="siteTemplate" property="name"/>',this.form.elements['preference(channel)'],this.form.elements['channelName'])">选择...</button>
                  </td>
            </tr>
            <tr valign="top">
                  <td class="formTitle">自定义页面宽度</td>
                  <td><html:text  property="preference(width)" size="8"/></td>
                              </tr>
                              <tr valign="top">
                  <td class="formTitle">自定义页面高度</td>
                  <td><html:text  property="preference(height)" size="8"/></td>
            </tr>
            <tr>
              <td class="formTitle">按钮样式</td>
              <td class="formComponent">
                <html:text property="preference(buttonStyle)"/><button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(buttonStyle)'],'')">选择...</button>
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
        <div class="toolbar">
          <button type="button" onclick="UnchangeSaveConfig(this.form),validate(this.form)">保存</button>
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
            function UnchangeSaveConfig(oform){
           	 var filter = oform.elements['preferences(filterpatterns)'];
                var ops = filter.options;
                for(var i=0;i<ops.length;i++){
               	 ops[i].selected =true;
                }
           }
            function validate(oForm){
                var arr = oForm("preference(selectChannel)");
                for(var i=arr.length-1;i>=0;i--){
					if(arr[i].checked==true&&arr[i].value=="customerPage"){
						if(oForm("preference(channel)").value==""){
	        				alert("自定义页面不能为空！");
	        				return false;
	        			}
					}
                }
            	validatorRequired(new Array(oForm.elements['preference(propertyId)'],oForm.elements['preference(viewPropertyId)']),new Array('关联属性','显示属性'),oForm)
              }
            </script>
<script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
<script language="javascript" src="/components/portlet/form/component/node/script.js.jsp" type="text/javascript" ></script>
