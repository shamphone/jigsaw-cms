<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<table cellpadding="0" cellspacing="0" border="0">
    <portlet:form action="save" method="POST" onsubmit="doSubmit(this)">
        <html:hidden property="preference(category)"/>
        <tr>
            <td class="pannelDiv" rowspan="2">
                <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
                    <option value="0" selected="selected">基本设置</option>
                    <option value="1">图片按钮</option>
                </select></td>
                <td><fieldset height="180px">
                  <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
                    <tr>
                      <td nowrap="nowrap">内容分类</td>
                      <td>
                        <html:hidden property="preference(category-id)"/>
                        <input type="text" name="categoryName" readonly="readonly" value="<bean:write name="category" property="name" ignore="true"/>" />
                        <button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(category-id)'],form.elements['categoryName'])">选择...</button>
                      </td>
                    </tr>
                    <tr>
                      <td class="formTitle">按钮名称</td>
                      <td class="formComponent"><html:text property="preference(value)"/> </td>
                    </tr>
                    <tr>
                      <td class="formTitle">样式</td>
                      <td class="formComponent">
                      <html:text property="preference(style)"/>
                      <button type="button" class="commonbut" id="searchN" onclick="doSelectStyle(this.form.elements['preference(style)'],'')">选择...</button>
                    </tr>
                    <tr>
                      <td class="formTitle">删除模式</td>
                      <td class="formComponent">
                        <html:select style="width:210px" property="preference(method)">
                          <html:option value="deleteNode">删除内容</html:option>
                          <html:option value="deleteLink">仅删除推荐关系</html:option>
                        </html:select>
                      </td>
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
                            <td><html:checkbox style="width:25px" property="preference(forwardSelf)" value="true"/><span>跳转到原页面</span></td>
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
          		<button type="button" onclick="checkForm(this.form)">保存</button>
          		<button type="button" onclick="window.parent.close()">取消</button></div></td>
                </tr></portlet:form>
            </table>
            <script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
            <script type="text/javascript" language="javascript">
            showActivityFullName(document.getElementsByName('preference(process)')[0],document.getElementsByName('preference(activity)')[0],document.getElementsByName('activityName')[0]);
            function doSubmit(oForm){
              return true;
            }
            function checkForm(oForm){
                var forwardSelf = oForm.elements['preference(forwardSelf)'].checked;
                if(!forwardSelf){
                	validatorSub(new Array(oForm.elements['preference(channel)'],oForm.elements['preference(forwardSelf)']),new Array('导航','跳转到原页面'),oForm)
                }else{
                	oForm.submit();
                }
            }
            function openSelectorFileSelector($oEcho)
            {
                var templateID = window.parent.dialogArguments.template.ID;
                var url = "/ide/site/resource/index.do?templateID="+templateID;
               // var sOptions = "dialogHeight=600px;dialogWidth=780px;center=yes;resizable=no;status=no";
                var ret = window.showDialog(url, null, 780,600);
                if (ret != null && ret[0])
                $oEcho.value = ret[0];
            }
            </script>
