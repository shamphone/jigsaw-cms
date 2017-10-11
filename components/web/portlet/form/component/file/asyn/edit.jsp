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
              <td class="formTitle">属性</td>
              <td class="formComponent">
                <html:hidden property="preference(propertyId)"/>
                <logic:present name="property">
                	<input type="text" name="fieldName" readonly="readonly" value="<bean:write name="property" property="name" ignore="true"/>" />
                </logic:present>
                <logic:notPresent name="property">
                	<input type="text" name="fieldName" readonly="readonly" value="<bean:write name="propertyDeleted" ignore="true"/>" />
                </logic:notPresent>
                <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition('<bean:write name="definition" ignore="true"/>')">选择...</button>
              </td>
            </tr>
            <!-- <tr>
              <td class="formTitle">上传控件样式</td>
              <td class="formComponent">
                <html:text property="preference(fileStyle)"/><button type="button" onclick="doSelectStyle(this.form.elements['preference(fileStyle)'],'')">选择...</button>
              </td>
            </tr> -->
            <tr>
              <td class="formTitle">宽度</td>
              <td class="formComponent">
                <html:text property="preference(width)" onblur="validatorInteger(this);checkWidth(this)"/>
              </td>
            </tr>
            <tr>
                <td class="formTitle">内容保存到</td>
                <td class="formComponent">
                    <html:radio style="width:25px" property="preference(owner)" value="site"/>当前网站
                    <html:radio style="width:25px" property="preference(owner)" value="member"/>登入会员
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
          <button type="button" onclick="validatorRequired(new Array(form.elements['preference(propertyId)']),new Array('属性'),this.form)">保存</button>
          <button type="button" onclick="window.parent.close()">取消</button>
        </div>
      </td>
    </tr>
  </portlet:form>
</table>
<script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
<script type="text/javascript" language="javascript">
  /**
  *选择待显示的属性
  */
  function searchPropertyDefinition($categoryId){
    var arr =  CMSDialog.PropertyDefinitionSelector($categoryId,['0','1','2','3','4','5','6','7','9','10']);
    if(arr!=null){
      document.getElementsByName("fieldName")[0].value = arr.name;
      document.getElementsByName("preference(propertyId)")[0].value = arr.ID;
    }
  }
  function checkWidth(width){
	    var arr = width.value;
		if(arr < 300){
			alert("宽度不能小于300");
			width.value='300';
		}
	  }
</script>
