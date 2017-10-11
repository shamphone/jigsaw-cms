<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form action="save" method="POST">
    <tr>
      <td class="pannelDiv" rowspan="2">
        <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
          <option value="0" selected="selected">基本设置</option>
          <option value="1">样式设计</option>
        </select>
      </td>
      <td>
        <fieldset>
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
          <tr>
              <td class="formTitle" valign="top">内容来源</td>
              <td class="formComponent" valign="top">
                <table border=0 cellpadding=0 cellspacing=0>
                  <tr>
                    <td>
                      <html:radio style="width:25px" styleId="b1" property="preference(contentType)" value="default">
                        <label for="b1">使用URL参数指定内容</label></html:radio>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <html:radio style="width:25px" styleId="b2" property="preference(contentType)" value="user"><label for="b2">使用当前登录用户</label></html:radio>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <html:radio style="width:25px" styleId="b3" property="preference(contentType)" value="site"><label for="b3">使用当前网站所属用户</label></html:radio>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            <tr>
              <td class="formTitle" valign="top">内容分类</td>
              <td class="formComponent" valign="top">
			    <html:hidden property="preference(category)"/><input type="text" name="categoryName" readonly="readonly" value="<bean:write name="category" property="name" ignore="true"/>" />
                  <button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(category)'],form.elements['categoryName'])">选择...</button>
                <br><html:checkbox property="preference(isShowRoot)" value="true" />显示根
              </td>
            </tr>
            <tr>
                <td class="formTitle" valign="top">引用属性</td>
                <td class="formComponent" valign="top">
                  <html:hidden property="preference(refField)"/>
                  <html:hidden property="preference(refDefinition)"/>
                  <input type="text" name="comDefinitionName" readonly="readonly" value="<bean:write name="property" property="name" ignore="true"/>" />
                  <button type="button" class="commonbut" id="search" onclick="searchRefPropertyDefinition(form.elements['preference(category)'],form.elements['preference(refField)'],form.elements['preference(refDefinition)'],form.elements['comDefinitionName'])">选择...</button>
                  <button type="button" class="commonbut" id="clear" onclick="clearTxt(form.elements['preference(refField)'],form.elements['comDefinitionName'])">清空</button>
                </td>
              </tr>
            <tr>
              <td class="formTitle">显示属性</td>
              <td class="formComponent">
                <html:hidden property="preference(showField)"/>
                  <input type="text" name="fieldName1" readonly="readonly" value="<bean:write name="property1Name" ignore="true"/>"/>
                  <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(category)'],form.elements['preference(showField)'],form.elements['fieldName1'],['0','2','5','6','7','8','9'])">选择...</button>
            </td>
            </tr>
              <tr>
                <td class="formTitle">类型</td>
                <td class="formComponent">
                <html:select property='preference(type)' style="width:210px" onchange="showChannel(this)">
                  <html:option value="item">基本属性块</html:option>
                  <html:option value="index">栏目页</html:option>
                </html:select></td>
              </tr>
            <tbody id="selectChannel" style="display:none">
            <tr>
              <td class="formTitle" valign="top">跳转到 </td>
                 <td class="formComponent">
			          <html:hidden property="preference(channel)"/>
<input type="text" name="channelName" <logic:notEmpty name="channel">value="<bean:write name="siteTemplate" property="displayName" ignore="true"/>.<bean:write name="channel" property="displayName" ignore="true"/>"</logic:notEmpty>/><button type="button" class="commonbut" onclick="chooseColumn('<bean:write name="siteTemplate" property="name"/>',this.form.elements['preference(channel)'],this.form.elements['channelName'])">选择...</button>
	             </td>
            </tr>
            <tr>
              <td class="formTitle">参数</td>
              <td class="formComponent">
                <html:hidden property="preference(field)"/>
                  <input type="text" name="fieldName2" readonly="readonly" value="<bean:write name="property2" property="name" ignore="true"/>" id="fieldId"/>
                  <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(category)'],form.elements['preference(field)'],form.elements['fieldName2'],['0','2','5','6','7','8','9'])">选择...</button>
                <br><html:checkbox property="preference(isContentId)" value="true" onclick="check(this)"/>将节点id作为参数
            </td>
            </tr>
            <tr>
              <td class="formTitle" valign="top">打开方式 </td>
              <td class="formComponent" valign="top"><html:text property="preference(openMode)" size="8"/></td>
            </tr>
            </tbody>
            </table>
            </fieldset>
           <fieldset style="display:none">
           <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
            <tr>
              <td class="formTitle" valign="top">导航样式 </td>
              <td class="formComponent" valign="top"><html:text property="preference(middle-style)" size="19"/><input type="button" value='选择..' onclick="doSelectStyle(this.form.elements['preference(middle-style)'],'')"/> </td>
            </tr>
            <tr>
              <td class="formTitle" valign="top">末项样式 </td>
              <td class="formComponent" valign="top"><html:text property="preference(end-style)" size="19"/><input type="button" value='选择..' onclick="doSelectStyle(this.form.elements['preference(end-style)'],'')"/> </td>
            </tr>
            <tr>
              <td class="formTitle">分隔符</td>
              <td class="formComponent"><html:text property="preference(seperator)"size="19"/></td>
            </tr>
            <tr>
              <td class="formTitle">分隔符样式</td>
              <td class="formComponent"><html:text property="preference(seperator-style)" size="19"/><input type="button" value='选择..' onclick="doSelectStyle(this.form.elements['preference(seperator-style)'],'')"/></td>
            </tr>
          </table>
        </fieldset>
        <div class="toolbar">
          <input type="submit" value='保存'/>
          <button type="button" onclick="window.parent.close()"></button>
        </div>
      </td>
    </tr>
  </portlet:form>
</table>
<script>
<logic:equal value="index" name="configForm" property="preference(type)">
	document.getElementById( "selectChannel" ).style.display='';
</logic:equal>
function showChannel(oSelect){
  if(oSelect.value=="index"){
	  document.getElementById( "selectChannel" ).style.display='';
  }else{
	  document.getElementById( "selectChannel" ).style.display='none';
  }
}

function searchRefPropertyDefinition(oCategory, oID, oRefID, oRefName){
    var arr =  CMSDialog.PropertyDefinitionSelector(oCategory.value,['0','1','2','3','4','5','6','7','8','10']);
    if(arr!=null&&arr.ID!=null){
      oID.value = arr.ID;
      oRefName.value = arr.name;
      oRefID.value = arr.refID;
    }
  }

function check(obj){
	var field = document.getElementById("fieldId");
	if(obj.checked == true){
		field.value = "";
		field.disabled = true;
	}else{
		field.disabled = false;
	}
}
</script>