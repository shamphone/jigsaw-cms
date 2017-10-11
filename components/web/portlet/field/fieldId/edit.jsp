<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form action="save" method="POST" onsubmit="validateForward(this)">
    <input type="hidden" name="defaultCategoryId" value="<bean:write name='defaultCategory' property="ID" ignore="true"/>"/>
    <tr>
      <td class="pannelDiv" rowspan="2">
        <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
          <option value="0" selected="selected"><bean:message key="com.fulong.cms.portlet.field.edit.option0.baseSet"/></option>
        </select>
      </td>
      <td>
        <fieldset>
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
            <tr>
              <td class="formTitle" valign="top"><bean:message key="com.fulong.cms.portlet.field.edit.formTitle2.content"/>来源</td>
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
                <td class="formTitle"><bean:message key="com.fulong.cms.portlet.field.edit.formTitle3.type"/></td>
                <td class="formComponent">
                <html:select property='preference(type)' style="width:210px" onchange="showChannel(this)">
                  <html:option value="item"><bean:message key="com.fulong.cms.portlet.field.edit.formTitle3.option.item"/></html:option>
                  <html:option value="index">栏目页</html:option>
                </html:select></td>
              </tr>
              <tbody id="selectChannel" style="display:none">
              	<tr>
                  <td class="formTitle">跳转到</td>
                   <td class="formComponent">
                      <html:hidden property="preference(channel)"/>
                      <input type="text" name="channelName" <logic:notEmpty name="channel">value="<bean:write name="siteTemplate" property="displayName" ignore="true"/>.<bean:write name="channel" property="displayName" ignore="true"/>"</logic:notEmpty>/><button type="button" class="commonbut" onclick="chooseColumn('<bean:write name="siteTemplate" property="name"/>',this.form.elements['preference(channel)'],this.form.elements['channelName'])">选择...</button>
                  </td>
                </tr>
              </tbody>
              <!-- <tr>
                <td class="formTitle">自定义网址</td>
                <td class="formComponent">
                	<logic:equal name="type" value="custom">
                		<html:text styleId="customUrl" property="preference(url)"/>
                	</logic:equal>
                	<logic:notEqual name="type" value="custom">
                		<html:text styleId="customUrl" property="preference(url)" disabled="true"/>
                	</logic:notEqual>
				</td>
              </tr> -->
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
            <!--
              <tr>
                <td class="formTitle">最多显示</td>
                <td class="formComponent"><html:text property="preference(length)"></html:text>个字</td>
              </tr>
              <tr>
                <td class="formTitle">替代文字</td>
                <td class="formComponent"><html:text title="被截去的文字部分的替代文字" property="preference(suffix)"></html:text></td>
              </tr>
               -->
            </table>
         </fieldset>
          <div class="toolbar">
          		<input type="submit" value="保存"/>
          		<button onclick="window.parent.close()">取消</button>
          </div>
        </td>
      </tr>
    </portlet:form>
  </table>
  <script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
  <script type="text/javascript" language="javascript">
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
    </script>
