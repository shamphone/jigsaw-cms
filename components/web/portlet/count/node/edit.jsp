<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form  action="save" method="POST">
    <tr>
      <td class="pannelDiv">
        <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
          <option value="0" selected="selected">内容抽取</option>
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
                  <td class="formTitle" valign="top">过滤属性</td>
                  <td nowrap="nowrap"  class="selectionEditor">
                  <table cellpadding="0" cellspacing="0" border="0">
                  <tr><td><html:checkbox styleId="b1" style="width:25px" property="preference(filterByParamet)"><label for="b1">自动接收URL参数</label></html:checkbox></td></tr>
                  <tr>
                  <td>
                    <select multiple="multiple" name="preferences(filter-patterns)" size="5" style="width:210px;">
                      <logic:present name="preferences" property="values(filter-patterns)">
                        <logic:iterate id="pattern" name="preferences" property="values(filter-patterns)">
                          <option value='<bean:write name="pattern" filter="false"/>'><bean:write name="pattern" filter="false"/></option>
                        </logic:iterate>
                      </logic:present>
                    </select>
                    </td><td valign="top">
                      <button type="button" class="commonbut" onclick="newFilter(form.elements['preference(category)'],form.elements['preferences(filter-patterns)'])">添加</button><br/>
                      <button type="button" class="commonbut" onclick="deleteOption(form.elements['preferences(filter-patterns)'])">删除</button>
                    </td>
                    </tr>
                    <tr>
                  <td class="formComponent" valign="top"><html:checkbox styleId="b1" style="width:25px" property="preference(lucene)"><label for="b1">lucene全文搜索</label></html:checkbox></td>
                </tr>
                    </table>
                  </td>
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
  <script type="text/Javascript" src="/ide/cms/classes/filterParser.js.jsp" language="Javascript"></script>
  <script type="text/Javascript" language="Javascript">
    var definitionId='<bean:write name="preferences" property="value(category)" ignore="true"/>';
    //兼容火狐 在提交按钮时调用该函数
      function doSubmit(oForm){
          selectAll(oForm.elements['preferences(filter-patterns)'])
          oForm.submit();
      }
       <bean:define name="preferences" property="value(category)" id="categoryID" type="String"/>
      var propertys=new Array();
      <logic:iterate id="pattern" name="preferences" property="values(filter-patterns)">
      <bean:define id="patternS" name="pattern" type="String"/>
      <%String propertyID=patternS.split(" ")[0];%>
      <cms:propertyDefinition definitionID="<%=categoryID%>"  propertyID="<%=propertyID%>" ID="pro"/>
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
