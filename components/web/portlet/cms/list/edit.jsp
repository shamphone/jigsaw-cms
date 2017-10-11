<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/fulong.tld" prefix="fulong" %>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<script type="text/Javascript" src='<html:rewrite page="/script/portlet.js" module="/common"/>'></script>
<portlet:form action="save">
  <table cellpadding="0" cellspacing="0" border="0">
    <tr>
      <td class="pannelDiv">
        <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
          <option value="0" selected="selected"><bean:message key="com.fulong.cms.portlet.list.edit.option0.baseSet"/></option>
          <option value="1"><bean:message key="com.fulong.cms.portlet.list.edit.option1.styleSet"/></option>
          <option value="2"><bean:message key="com.fulong.cms.portlet.list.edit.option2.firstBlock"/></option>
          <option value="3"><bean:message key="com.fulong.cms.portlet.list.edit.option3.secondBlock"/></option>
          <option value="4"><bean:message key="com.fulong.cms.portlet.list.edit.option4.thirdBlock"/></option>
          <option value="5"><bean:message key="com.fulong.cms.portlet.list.edit.option5.fouthBlock"/></option>
          <option value="6"><bean:message key="com.fulong.cms.portlet.list.edit.option6.fifthBlock"/></option>
          <option value="7"><bean:message key="com.fulong.cms.portlet.list.edit.option7.sixthBlock"/></option>
          <option value="8"><bean:message key="com.fulong.cms.portlet.list.edit.option8.seventhBlock"/></option>
          <option value="9"><bean:message key="com.fulong.cms.portlet.list.edit.option9.eighthBlick"/></option>
          <option value="10"><bean:message key="com.fulong.cms.portlet.list.edit.option10.ninthBlock"/></option>
          <option value="11"><bean:message key="com.fulong.cms.portlet.list.edit.option11.tenthBlock"/></option>
          <option value="12"><bean:message key="com.fulong.cms.portlet.list.edit.option12.eleventhBlock"/></option>
          <option value="13"><bean:message key="com.fulong.cms.portlet.list.edit.option13.twelfthBlock"/></option>
          <option value="14"><bean:message key="com.fulong.cms.portlet.list.edit.option14.thirteenthBlock"/></option>
        </select></td>
        <td><fieldset>
          <table width="95%" cellpadding="2" cellspacing="2" border="0">
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
              <td class="formTitle">打开方式</td>
              <td><html:select property="preference(newWindows)" styleId="itemColumn" style="width:210px">
                <html:option value="_self">原窗口</html:option>
                <html:option value="_blank">新窗口</html:option>
                <html:option value="_parent">父窗口</html:option>
                <html:option value="_top">顶级窗口</html:option>
              </html:select>
            </td>
          </tr>
            <tr>
              <td nowrap="nowrap">行数</td>
              <td class="formComponent" nowrap="nowrap"><html:text property="preference(row)" onblur="validatorInteger(this)"/></td>

            </tr>
            <tr>
              <td class="formTitle" nowrap="nowrap"><bean:message key="com.fulong.cms.portlet.list.edit.formTitle6.dataBlockNum"/></td>
              <td class="formComponent"><html:text property="preference(block)" onblur="validatorInteger(this)"/></td>
            </tr>
            <tr>
              <td>排序属性</td>
              <td><html:hidden property="preference(order-field)"/>
                <input size="10" type="text" name="fieldName" readonly="readonly" value="<bean:write name="orderProperty" property="name" ignore="true"/>" />
                <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(category)'],form.elements['preference(order-field)'],form.elements['fieldName'],['0','9','10'])">选择...</button>
                <html:select property="preference(order-style)" style="width:210px">
                <html:option value="ASC"><bean:message  bundle="list" key="com.fulong.cms.portlet.list.edit.formTitle11.option7.ASC"/></html:option>
                <html:option value="DESC"><bean:message  bundle="list" key="com.fulong.cms.portlet.list.edit.formTitle11.option8.DESC"/></html:option>
                </html:select>
                </td>
            </tr>
            <tr>
              <td class="formTitle" valign="top">过滤属性</td>
              <td nowrap="nowrap"  class="selectionEditor">
					<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td><select multiple="multiple" name="preferences(filter-patterns)" size="5" style="width: 210px;">
								<logic:present name="preferences" property="values(filter-patterns)">
									<logic:iterate id="pattern" name="preferences" property="values(filter-patterns)">
										<option value='<bean:write name="pattern" filter="false"/>'><bean:write	name="pattern" filter="false" /></option>
									</logic:iterate>
								</logic:present>
							</select></td>
							<td valign="top">
							<button type="button" class="commonbut" onclick="newFilter(form.elements['preference(category)'],form.elements['preferences(filter-patterns)'])">添 加</button>
							<br />
							<button type="button" class="commonbut" onclick="deleteOption(form.elements['preferences(filter-patterns)'])">删 除</button>
							</td>
						</tr>
					</table>
					</td>
            </tr>
          </table>

      </fieldset>
      <fieldset style="display:none">
              <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
                <tr>
                  <td class="formTitle"><bean:message key="com.fulong.cms.portlet.list.edit.formTitle12.tableStyle"/></td>
                  <td class="formComponent"><html:text property="preference(list-style)"></html:text>
                  <button type="button" class="commonbut" onclick="selectStyle(this.form.elements['preference(list-style)'])"><bean:message key="com.fulong.cms.portlet.list.edit.formTitle12.button1"/></button></td>
                </tr>
                <tr>
                  <td class="formTitle"><bean:message key="com.fulong.cms.portlet.list.edit.formTitle13.paginationStyle"/></td>
                  <td class="formComponent"><html:text property="preference(pager-style)"></html:text>
                  <button type="button" class="commonbut" onclick="selectStyle(this.form.elements['preference(pager-style)'])"><bean:message key="com.fulong.cms.portlet.list.edit.formTitle12.button2"/></button></td>
                </tr>
                <tr>
                  <td class="formTitle"><html:hidden property="preference(show-pager)"/></td>
                  <td class="formComponent"><input style="width: 25px;" type="checkbox" <logic:equal name="preferences" property="value(show-pager)" value="true">checked="checked"</logic:equal> onclick="syn_checkbox(this,'preference(show-pager)')"/><span class="formTitle"><bean:message key="com.fulong.cms.portlet.list.edit.formTitle13.span.formTitle"/></span></td>
                </tr>
              </table>
            </fieldset>
      <% for(int i=0;i<13;i++){ %><fieldset style="display:none">
            <% String propertyID = "arrayPreference(block-fields).value["+i+"]";
            String properyName = "fieldName"+i;
          %>
        <table ondblclick="alert(this.innerHTML)" width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
          <tr>
            <%String changeType = "if(this.value=='radio'||this.value=='checkbox')clearProperty('"+properyName+"','"+propertyID+"')"; %>
            <td class="formTitle">块 类 型</td>
            <td class="formComponent"><html:select style="width:210px" onchange="<%=changeType%>" property='<%= "arrayPreference(block-types).value["+i+"]" %>'>
              <html:option value="item"><bean:message key="com.fulong.cms.portlet.list.edit.formTitle14.option1.item"/></html:option>
              <html:option value="radio">radio按钮</html:option>
              <html:option value="checkbox">checkbox按钮</html:option>
              <html:optionsCollection name="channel.types" label="displayName" value="name"/>
            </html:select></td>
          </tr>
          <tr>
            <td class="formTitle"><bean:message key="com.fulong.cms.portlet.list.edit.formTitle15.attrubiteShow"/></td>
            <td class="formComponent">
              <html:hidden property='<%=propertyID%>'/>
              <input type="text" name='<%=properyName%>' readonly="readonly" value="<bean:write name='<%="property"+i%>' property="name" ignore="true"/>" />
              <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(category)'],form.elements['<%= "arrayPreference(block-fields).value["+i+"]" %>'],form.elements['<%="fieldName"+i%>'])">选择...</button>
          </td>
        </tr>
        <tr>
          <td class="formTitle">自定义值</td>
          <%String onchange = "clearProperty('"+properyName+"','"+propertyID+"')"; %>
          <td class="formComponent"><html:text onchange='<%=onchange%>' property='<%= "arrayPreference(customalValues).value["+i+"]" %>'/></td>
          </tr>
          <tr>
            <td class="formTitle"><bean:message key="com.fulong.cms.portlet.list.edit.formTitle18.showMax"/></td>
            <td class="formComponent"><html:text onblur="validatorInteger(this)" property='<%= "arrayPreference(length).value["+i+"]" %>'/> <bean:message key="com.fulong.cms.portlet.list.edit.formTitle18.text.gezi"/></td>
            </tr>
            <!--tr>
            <td class="formTitle">自定义</td>
            <td class="formComponent"><html:text property='<%= "arrayPreference(block-expressions).value["+i+"]" %>' styleId="templateColumn"/></li>
            </tr-->
            <tr>
              <td class="formTitle">块 样 式</td>
              <td class="formComponent"><html:text  property='<%= "arrayPreference(block-styles).value["+i+"]" %>'/>
              <button type="button" class="commonbut" onclick="selectStyle(this.form.elements['<%= "arrayPreference(block-styles).value["+i+"]" %>'])">选择...</button></td>
              </tr>
            </table>
          </fieldset><%} %>

            <div class="toolbar">
    	   		<button type="button" onclick="listdoSaveConfig(this.form)">保存</button>
	    		<button type="button" onclick="window.parent.close()">取消</button>
            </div>
          </td>
        </tr>
      </table>
      <script type="text/Javascript" language="Javascript">

      function clearProperty(name,id){
        document.forms[0].elements(name).value="";
        document.forms[0].elements(id).value="";
      }
      function listdoSaveConfig(oForm){
        selectAll(oForm.elements['preferences(filter-patterns)'])
        oForm.submit();
      }
       <bean:define name="preferences" property="value(category)" id="categoryID" type="String"/>
      var propertys=new Array();
      <logic:iterate id="pattern" name="preferences" property="values(filter-patterns)">
      <bean:define id="patternS" name="pattern" type="String"/>
      <%String propertyID=patternS.split(" ")[0];%>
      <fulong:propertyDefinition definitionID="<%=categoryID%>"  propertyID="<%=propertyID%>" ID="pro"/>
      propertys['<%=propertyID%>']=new Object();
      propertys['<%=propertyID%>'].name='<bean:write name="pro" property="name"/>';
      propertys['<%=propertyID%>'].ID='<bean:write name="pro" property="ID"/>';
      </logic:iterate>
      var filter = document.forms[0].elements['preferences(filter-patterns)'];
      var ops = filter.options;
      for(var i=0;i<ops.length;i++){
        FilterParser.init(ops[i].value,propertys)
        var pro = FilterParser.parserProperty();
        var op = FilterParser.parserOperation();
        var va = FilterParser.parserValue();
        ops[i].text = pro+" "+op+" "+va;
      }
      </script>
    </portlet:form>

