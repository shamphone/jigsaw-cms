<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form action="save" method="POST" onsubmit="return validateForward(this)">
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
              <td class="formTitle" valign="top">时间来源</td>
              <td class="formComponent" valign="top">
                <table border=0 cellpadding=0 cellspacing=0>
                    <tr>
                      <td>
                        <html:radio style="width:25px" styleId="b2" property="preference(timeSource)" value="calendar"><label for="b2">使用当前日历重复器指定时间</label></html:radio>
                      </td>
                    </tr>
                    <tr>
                    <td>
                      <html:radio style="width:25px" styleId="b1" property="preference(timeSource)" value="url">
                        <label for="b1">使用URL参数指定时间</label></html:radio>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td class="formTitle">日期格式</td>
                <td class="formComponent">
                  <html:select property="preference(dateFormat)" style="width:210px">
                    <bean:define id="dateMap" name="dateFormats" type="java.util.Map"/>
                    <logic:iterate id="key" name="dateFormatKeys" type="String">
                      <html:option value="<%=key %>"><%=dateMap.get(key) %></html:option>
                    </logic:iterate>
                      <html:option value="E">一</html:option>
                      <html:option value="shortDate">三月十四日</html:option>
                      <html:option value="fullDate">二○○一年三月十四日</html:option>
                  </html:select>
                </td>
              </tr>
              <tr>
                  <td class="formTitle">样式</td>
                  <td class="formComponent"><html:text  property="preference(table-style)"/>
                  <button type="button" class="commonbut" onclick="selectStyle(this.form.elements['preference(table-style)'])">选择...</button>
                  </td>
                </tr>
              <tr>
                <td class="formTitle">类型</td>
                <td class="formComponent">
                <html:select property='preference(type)' style="width:210px" onchange="showChannel(this)">
                  <html:option value="item">简单属性块</html:option>
                  <html:option value="link">超链接</html:option>
                </html:select></td>
              </tr>
              <tr id="selectChannel" style="display: none;">
                <td class="formTitle">跳转到</td>
                <td class="formComponent">
                      <html:hidden property="preference(channel)"/>
                      <input type="text" name="channelName" <logic:notEmpty name="channel">value="<bean:write name="siteTemplate" property="displayName" ignore="true"/>.<bean:write name="channel" property="displayName" ignore="true"/>"</logic:notEmpty>/><button type="button" class="commonbut" onclick="chooseColumn('<bean:write name="siteTemplate" property="name"/>',this.form.elements['preference(channel)'],this.form.elements['channelName'])">选择...</button>
                </td>
              </tr>
              <tr>
              	<td class="formTitle"></td>
              	<td class="formComponent">
                	<html:checkbox style="width:25px" property="preference(openInNewWindow)">在新窗口打开</html:checkbox>
              	</td>
             </tr>
          </table>
        </fieldset>
        <div class="toolbar">
       		<!--  <button type="submit">保存</button> -->
       		<input type="submit" value="保存"/>
          	<button type="button" onclick="window.parent.close()">取消</button>
        </div>
      </td>
    </tr>
  </portlet:form>
</table>
<script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
<script type="text/javascript" language="javascript">
<logic:equal value="link" name="configForm" property="preference(type)">
	document.getElementById( "selectChannel" ).style.display='';
</logic:equal>
function showChannel(oSelect){
	  if(oSelect.value=="link"){
		  document.getElementById( "selectChannel" ).style.display='';
	  }else{
		  document.getElementById( "selectChannel" ).style.display='none';
	  }
}
</script>