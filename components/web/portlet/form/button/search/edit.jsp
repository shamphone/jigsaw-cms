<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
<table cellpadding="0" cellspacing="0" border="0">
    <portlet:form action="save" method="POST" onsubmit="doSubmit(this)">
        <tr>
            <td class="pannelDiv" rowspan="2">
                <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
                    <option value="0" selected="selected">基本设置</option>
                    <option value="1">图片按钮</option>
                </select></td>
                <td><fieldset>
                    <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
                    <tr>
                        <td class="formTitle">按钮名称</td>
                        <td class="formComponent"><html:text property="preference(value)"/> </td>
                    </tr>
                    <tr>
                        <td class="formTitle">Tab键顺序</td>
                        <td class="formComponent">
                            <html:text property="preference(tabindex)"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="formTitle">样式</td>
                        <td class="formComponent">
                            <html:text property="preference(style)"/>
                            <button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(style)'],'')">选择...</button>
                        </td>
                      </tr>
                      <tr>
                        <td nowrap="nowrap">条件</td>
                        <td nowrap="nowrap"  class="selectionEditor">
                  <table cellpadding="0" cellspacing="0" border="0">
                  <tr>
                  <td>
                            <select multiple="multiple" name="preferences(conditions)" size="5" multiple="true" style="width:210px;">
                                <logic:present name="preferences" property="values(conditions)">
                                    <logic:iterate id="pattern" name="preferences" property="values(conditions)">
                                        <option value='<bean:write name="pattern" filter="false"/>'><fulong:filter name="pattern" definition="category"/></option>
                                    </logic:iterate>
                                </logic:present>
                            </select>
                    </td><td valign="top">
                          <html:hidden property="preference(category)"/>
                              <button type="button" class="commonbut" onclick="newFilter(form.elements['preference(category)'],form.elements['preferences(conditions)'])">添加</button><br/>
                              <button type="button" class="commonbut" onclick="deleteOption(form.elements['preferences(conditions)'])">删除</button>
                    </td>
                    </tr>
                    </table>
                        </td>
                      </tr>
                      <tr>
                        <td class="formTitle">搜索结果页</td>
                       	<td class="formComponent">
		                      <html:hidden property="preference(channel)"/>
		                      <input type="text" name="channelName" <logic:notEmpty name="channel">value="<bean:write name="siteTemplate" property="displayName" ignore="true"/>.<bean:write name="channel" property="displayName" ignore="true"/>"</logic:notEmpty>/><button type="button" class="commonbut" onclick="chooseColumn('<bean:write name="siteTemplate" property="name"/>',this.form.elements['preference(channel)'],this.form.elements['channelName'])">选择...</button>
	                    </td>
                      </tr>
                </table>
            </fieldset>
            <fieldset style="display:none">
                  <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
                        <tr>
                            <td class="formTitle">图片地址</td>
                            <td class="formComponent"><html:text property="preference(imgsrc)"/><button type="button" class="commonbut" onclick="openSelectorFileSelector(this.form['preference(imgsrc)']);">选择...</button> </td>
                          </tr>
                        <tr>
                            <td class="formTitle">图片高度</td>
                            <td class="formComponent">
                                <html:text property="preference(imgheight)"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="formTitle">图片宽度</td>
                            <td class="formComponent">
                                <html:text property="preference(imgwidth)"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="formTitle">图片样式</td>
                            <td class="formComponent">
                                <html:text property="preference(imgstyle)"/>
                                <button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(imgstyle)'],'')">选择...</button>
                            </td>
                        </tr>
                    </table>
                </fieldset>
                <div class="toolbar">
          		<button type="button" onclick="doSubmit(this.form)">保存</button>
          		<button type="button" onclick="window.parent.close()">取消</button></div></td>
            </tr></portlet:form>
        </table>

            <script type="text/Javascript" src='/ide/common/script/portlet.js'></script>
            <script type="text/javascript" language="javascript">
            function doSubmit(oForm){
              selectAll(document.getElementsByName('preferences(conditions)')[0]);
              validatorSub(new Array(oForm.elements['preference(channel)']),new Array('搜索结果页'),oForm)
            }
            function openSelectorFileSelector($oEcho)
            {
                var templateID = window.parent.dialogArguments.template.ID;
                var url = "/ide/site/resource/index.do?templateID="+templateID;
                var sOptions = "dialogHeight=600px;dialogWidth=780px;center=yes;resizable=no;status=no";
                var ret = window.showModalDialog(url, null, sOptions);
                if (ret != null && ret[0])
                $oEcho.value = ret[0];
            }
            </script>
        