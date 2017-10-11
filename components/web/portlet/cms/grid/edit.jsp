<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<script type="text/Javascript" src='<html:rewrite page="/script/portlet.js" module="/common"/>'></script>
<portlet:form  action="save" method="POST">
  <table cellpadding="0" cellspacing="0" border="0" width="100%">
    <tr>
      <td class="pannelDiv">
        <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
          <option value="0" selected="selected"><bean:message key="com.fulong.cms.portlet.grid.edit.option0.baseSet"bundle="grid"/></option>
          <option value="1"><bean:message key="com.fulong.cms.portlet.grid.edit.option1.tableStyle" bundle="grid"/></option>
          <option value="2"><bean:message key="com.fulong.cms.portlet.grid.edit.option2.firstColumn" bundle="grid"/></option>
          <option value="3"><bean:message key="com.fulong.cms.portlet.grid.edit.option3.secondColumn" bundle="grid"/></option>
          <option value="4"><bean:message key="com.fulong.cms.portlet.grid.edit.option4.thirdColumn" bundle="grid"/></option>
          <option value="5"><bean:message key="com.fulong.cms.portlet.grid.edit.option5.fouthColumn" bundle="grid"/></option>
          <option value="6"><bean:message key="com.fulong.cms.portlet.grid.edit.option6.fifthColumn" bundle="grid"/></option>
          <option value="7"><bean:message key="com.fulong.cms.portlet.grid.edit.option7.sixthColumn" bundle="grid"/></option>
          <option value="8"><bean:message key="com.fulong.cms.portlet.grid.edit.option8.seventhColumn" bundle="grid"/></option>
          <option value="9"><bean:message key="com.fulong.cms.portlet.grid.edit.option9.eighthColumn" bundle="grid"/></option>
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
              <td><html:select property="preference(newWindows)" styleId="itemColumn">
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
              <td class="formTitle" nowrap="nowrap">表格数</td>
              <td class="formComponent"><html:text property="preference(table)" onblur="validatorInteger(this)"/></td>
            </tr>
            <tr>
              <td class="formTitle" nowrap="nowrap">列数</td>
              <td class="formComponent"><html:text property="preference(column)" onblur="validatorInteger(this)"/></td>
            </tr>
            <tr>
              <td>排序属性</td>
              <td><html:hidden property="preference(order-field)"/>
                <input size="10" type="text" name="fieldName" readonly="readonly" value="<bean:write name="orderProperty" property="name" ignore="true"/>" />
                <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(category)'],form.elements['preference(order-field)'],form.elements['fieldName'],['0','9','10'])">选择...</button>
                <html:select property="preference(order-style)">
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
				<td>
                <select multiple="multiple" name="preferences(filter-patterns)" size="5" style="width:210px;">
                  <logic:present name="preferences" property="values(filter-patterns)">
                    <logic:iterate id="pattern" name="preferences" property="values(filter-patterns)">
                      <option value='<bean:write name="pattern" filter="false"/>'><bean:write name="pattern" filter="false"/></option>
                    </logic:iterate>
                  </logic:present>
                </select>
                </td>
                <td>
                  <button type="button" onclick="newFilter(form.elements['preference(category)'],form.elements['preferences(filter-patterns)'])">添加</button><br />
                  <!-- <button type="button" onclick="editFilter(form.elements['preference(category)'],form.elements['preferences(filter-patterns)'])">修改</button><br />-->
                  <button type="button" onclick="deleteOption(form.elements['preferences(filter-patterns)'])">删除</button><br />
                </td>
            </tr>
          </table>
          </td>
          </tr>
          </table>

      </fieldset><fieldset style="display:none">
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
            <tr>
              <td class="formTitle"><bean:message key="com.fulong.cms.portlet.grid.edit.formTitle11.tableStyle"bundle="grid"/></td>
              <td class="formComponent"><html:text property="preference(table-style)"/>
              <button type="button" class="commonbut" onclick="selectStyle(this.form.elements['preference(table-style)'])"><bean:message key="com.fulong.cms.portlet.grid.edit.formTitle11.button1" bundle="grid"/></button>
              </td>
            </tr>
            <tr>
              <td class="formTitle"><bean:message key="com.fulong.cms.portlet.grid.edit.formTitle12.tableHeadStyle"bundle="grid"/></td>
              <td class="formComponent"><html:text property="preference(header-style)"></html:text>
              <button type="button" class="commonbut" onclick="selectStyle(this.form.elements['preference(header-style)'])"><bean:message key="com.fulong.cms.portlet.grid.edit.formTitle12.button2" bundle="grid"/></button>
              </td>
            </tr>
            <tr>
              <td><html:hidden property="preference(show-header)"/></td>
              <td class="formComponent"><input style="width:25px" type="checkbox" <logic:equal name="preferences" property="value(show-header)" value="true">checked="checked"</logic:equal> onclick="syn_checkbox(this,'preference(show-header)')"/><span class="formTitle"><bean:message key="com.fulong.cms.portlet.grid.edit.hidden.span1.showTableHead"bundle="grid"/></span></td>
            </tr>
            <tr>
              <td class="formTitle"><bean:message key="com.fulong.cms.portlet.grid.edit.formTitle13.tableTileStyle"bundle="grid"/></td>
              <td class="formComponent"><html:text property="preference(footer-style)"></html:text>
              <button type="button" class="commonbut" onclick="selectStyle(this.form.elements['preference(footer-style)'])"><bean:message key="com.fulong.cms.portlet.grid.edit.formTitle12.button2" bundle="grid"/></button>
              </td>
            </tr>
            <tr>
              <td><html:hidden property="preference(show-footer)"/></td>
              <td class="formComponent"><input style="width:25px" type="checkbox" <logic:equal name="preferences" property="value(show-footer)" value="true">checked="checked"</logic:equal> onclick="syn_checkbox(this,'preference(show-footer)')"/><span class="formTitle"><bean:message key="com.fulong.cms.portlet.grid.edit.hidden.span2.showTableTiles"bundle="grid"/></span></td>
            </tr>
            <tr>
              <td class="formTitle"><bean:message key="com.fulong.cms.portlet.grid.edit.formTitle14.paginationStyle"bundle="grid"/></td>
              <td class="formComponent"><html:text property="preference(pager-style)"></html:text>
              <button type="button" class="commonbut" onclick="selectStyle(this.form.elements['preference(pager-style)'])"><bean:message key="com.fulong.cms.portlet.grid.edit.formTitle12.button2" bundle="grid"/></button> 
              </td>
            </tr>
            <tr>
              <td> <html:hidden property="preference(show-pager)"/><html:hidden property="preference(show-index)"/></td>
              <td class="formComponent">
                <input style="width:25px" type="checkbox" <logic:equal  name="preferences" property="value(show-pager)" value="true">checked="checked"</logic:equal> onclick="syn_checkbox(this,'preference(show-pager)')"/>
                <span class="formTitle"><bean:message key="com.fulong.cms.portlet.grid.edit.formTitle14.span3.formTitleShow"bundle="grid"/></span>
                <input style="width:25px" type="checkbox" <logic:equal  name="preferences" property="value(show-index)" value="true">checked="checked"</logic:equal> onclick="syn_checkbox(this,'preference(show-index)')"/>
                <span class="formTitle"><bean:message key="com.fulong.cms.portlet.grid.edit.formTitle14.span4.formNumShow"bundle="grid"/></span>
              </td>
            </tr>
          </table>
        </fieldset><%for(int i=0;i<8;i++){ %>
            <% String propertyID = "arrayPreference(column-fields).value["+i+"]";
            String properyName = "fieldName"+i;
          %>
          <fieldset style="display:none">
            <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
              <tr>
                <%String changeType = "if(this.value=='radio'||this.value=='checkbox')clearProperty('"+properyName+"','"+propertyID+"')"; %>
                <td class="formTitle"><bean:message key="com.fulong.cms.portlet.grid.edit.formTitle15.linkTo"bundle="grid"/></td>
                <td class="formComponent"><html:select onchange="<%=changeType%>" property='<%= "arrayPreference(column-types).value["+i+"]" %>'>
                  <html:option value="item"><bean:message key="com.fulong.cms.portlet.grid.edit.formTitle15.linkTo.option1.noLink"bundle="grid"/></html:option>
                  <html:option value="radio">radio按钮</html:option>
                  <html:option value="checkbox">checkbox按钮</html:option>
                  <html:optionsCollection name="channel.types" label="displayName" value="name"/>
                </html:select></td>
              </tr>
              <tr>
                <td class="formTitle"><bean:message key="com.fulong.cms.portlet.grid.edit.formTitle16.attrubiteShow" bundle="grid"/></td>
                <td class="formComponent">
                  <html:hidden property='<%=propertyID%>'/>
                    <input type="text" name='<%=properyName%>' readonly="readonly" value="<bean:write name='<%="property"+i%>' property="name" ignore="true"/>" />
                    <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(category)'],form.elements['<%= propertyID %>'],form.elements['<%=properyName%>'])">选择...</button>
                </td>
              </tr>
            <tr>
              <td class="formTitle"><bean:message key="com.fulong.cms.portlet.grid.edit.formTitle17.columnHeadWord"bundle="grid"/></td>
              <td class="formComponent"><html:text property='<%= "arrayPreference(column-headers).value["+i+"]" %>'/></li>
              </td>
            </tr>
            <tr>
              <td class="formTitle"><bean:message key="com.fulong.cms.portlet.grid.edit.formTitle18.columnTilesWord"bundle="grid"/></td>
              <td class="formComponent"><html:text property='<%= "arrayPreference(column-footers).value["+i+"]" %>'/></li>
              </td>
            </tr>
            <tr>
              <td class="formTitle"><bean:message key="com.fulong.cms.portlet.grid.edit.formTitle19.style"bundle="grid"/></td>
              <td class="formComponent"><html:text  property='<%= "arrayPreference(column-styles).value["+i+"]" %>'/>
                <button type="button" class="commonbut" onclick="selectStyle(this.form.elements['<%= "arrayPreference(column-styles).value["+i+"]" %>'])">选择...</button>
              </td>  </tr>
            </table>
          </fieldset><%} %>
          <div class="toolbar">
            <button type="button" onclick="listdoSaveConfig(this.form)"><bean:message key="com.fulong.cms.portlet.grid.edit.toolbar.submit.save"bundle="grid"/></button>
	    	<button type="button" onclick="window.parent.close()"><bean:message key="com.fulong.cms.portlet.grid.edit.toolbar.button.cancel"bundle="grid"/></button>
          </div>
        </td>
            </tr>
</table>
</portlet:form>
  <script type="text/Javascript" language="Javascript">
      function clearProperty(name,id){
        document.forms[0].elements[name].value="";
        document.forms[0].elements[id].value="";
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
