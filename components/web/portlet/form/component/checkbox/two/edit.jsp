<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
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
                  <td class="formTitle" valign="top">关联属性</td>
                  <td nowrap="nowrap"  class="selectionEditor">
                  <input name="preference(category-id)" type="hidden" value="<bean:write name="definition" ignore="true"/>" />
                  <table cellpadding="0" cellspacing="0" border="0">                  
                  <tr>
                  <td>
                    <select multiple="multiple" name="preferences(filter-patterns)" size="5" style="width:210px;">
                      <logic:present name="preferences" property="values(filter-patterns)">
                        <logic:iterate id="selectedProp" name="selectedProps">
                        <logic:empty name="selectedProp">
                        	  <option value='<bean:write name="selectedProp" property="ID" ignore="true"/>'><bean:write name="selectedProp" property="name" ignore="true"/></option>
                        </logic:empty>
                        <logic:notEmpty name="selectedProp">
                        	  <option>属性已被删除，请重新配置</option>
                        </logic:notEmpty>
                        </logic:iterate>
                      </logic:present>
                    </select>
                    </td><td valign="top">
                      <button type="button" class="commonbut" onclick="addPropertyDefinition(form.elements['preference(category-id)'],form.elements['preferences(filter-patterns)'],['0','2','3','4','5','6','7','8','9','10'])">添 加</button><br/>
                      <button type="button" class="commonbut" onclick="deleteOption(form.elements['preferences(filter-patterns)'])">删 除</button>
                    </td>
                    </tr>
                    </table>
                  </td>
                </tr>
            <tr>
                <td nowrap="nowrap">一级字典库</td>
                <td>
                  <html:hidden property="preference(category)"/>
                  <input type="text" name="categoryName" readonly="readonly" value="<bean:write name="category" property="name" ignore="true"/>" />
                  <button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(category)'],form.elements['categoryName'])">选择...</button>
                </td>
              </tr>
            <tr>
              <td class="formTitle">一级样式</td>
              <td class="formComponent">
                <html:text property="preference(firstStyle)"/><button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(firstStyle)'],'')">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">二级样式</td>
              <td class="formComponent">
                <html:text property="preference(secondStyle)"/><button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(secondStyle)'],'')">选择...</button>
              </td>
            </tr>
          </table>
        </fieldset>
        <div class="toolbar">
          <button type="submit"  onclick="UnchangeSaveConfig(this.form)">保存</button>
          <button type="button" onclick="window.parent.close()">取消</button>
        </div>
      </td>
    </tr>
  </portlet:form>
</table>
<script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
<script type="text/Javascript" language="Javascript">
function UnchangeSaveConfig(oform){
	 var filter = oform.elements['preferences(filter-patterns)'];
    var ops = filter.options;
    for(var i=0;i<ops.length;i++){
   	 ops[i].selected =true;
    }
}
</script>