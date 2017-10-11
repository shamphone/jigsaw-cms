<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<table cellpadding="0" cellspacing="0" border="0">
    <portlet:form action="save" method="POST">
        <tr>
            <td class="pannelDiv" rowspan="2">
                <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="8">
                    <option value="0" selected="selected">基本设置</option>
                </select></td>
                <td><fieldset>
                    <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
                        <tr>
                            <td class="formTitle">按钮名称</td>
                            <td class="formComponent"><html:text property="preference(value)"/> </td>
                        </tr>
                        <tr>
                            <td class="formTitle">样式</td>
                            <td class="formComponent">
                                <html:text property="preference(style)"/>
                                <button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(style)'],'')">选择...</button>
                            </td>
                        </tr>
                        <tr>
                            <td class="formTitle">目标分类</td>
                            <td class="formComponent">
                              <html:hidden property="preference(categorys)"/><input type="text" name="categoryName" readonly="readonly" value="<bean:write name="categorys" property="name" ignore="true"/>" />
                  			  <button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(categorys)'],form.elements['categoryName'],form.elements['fieldName'])">选择...</button>
                            </td>
                        </tr>
                        <tr>
                            <td class="formTitle"></td>
                            <td class="formComponent"><html:checkbox style="width:25px" property="preference(choose)" styleId="a1" value="true"/><label for="a1">允许用户自己选择待接受的分类</label></td>
                        </tr>
                        <tr>
                            <td class="formTitle">关联活动</td>
                            <td class="formComponent"><html:hidden property="preference(process)"/><html:hidden property="preference(activity)"/>
                            <input type="text" name="activityName" /><button type="button" class="commonbut" onclick="doSelectActivity(this.form.elements['preference(process)'],this.form.elements['preference(activity)'],this.form.elements['activityName'],window.parent.dialogArguments.window.oTemplate)">选择...</button>
                            </td>
                        </tr>
                        <tr>
                            <td class="formTitle">保存后导航到</td>
                            <td class="formComponent">
		                      <html:hidden property="preference(channel)"/>
		                      <input type="text" name="channelName" <logic:notEmpty name="channel">value="<bean:write name="siteTemplate" property="displayName" ignore="true"/>.<bean:write name="channel" property="displayName" ignore="true"/>"</logic:notEmpty>/><button type="button" class="commonbut" onclick="chooseColumn('<bean:write name="siteTemplate" property="name"/>',this.form.elements['preference(channel)'],this.form.elements['channelName'])">选择...</button>
		                    </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td><html:checkbox style="width:25px" property="preference(forwardSelf)" value="true"/><span>保存后跳转到原页面</span></td>
                         </tr> 
                    </table>
                </fieldset>
                <div class="toolbar">
          		<button type="button" onclick="validatorSub(new Array(form.elements['preference(channel)'],form.elements['preference(forwardSelf)']),new Array('导航','跳转到原页面'),this.form)">保存</button>
          		<button type="button" onclick="window.parent.close()">取消</button></div></td>
                </tr></portlet:form>
            </table>
            <script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
            <script language="javascript" type="text/javascript">
            showActivityFullName(document.getElementsByName('preference(process)')[0],document.getElementsByName('preference(activity)')[0],document.getElementsByName('activityName')[0]);
            </script>
