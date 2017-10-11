<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
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
                            <td class="formTitle">样式</td>
                            <td class="formComponent">
                                <html:text property="preference(style)"/>
                                <button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(style)'],'')">选择...</button>
                            </td>
			            </tr>
				            <tr>
				              <td class="formTitle">内容分类</td>
				              <td class="formComponent">
			                  <html:hidden property="preference(definitionId)"/>
			                  <input type="text" name="categoryName" readonly="readonly" value="<bean:write name="category" property="name" ignore="true"/>" />
			                  <button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(definitionId)'],form.elements['categoryName'])">选择...</button>
			                  <button type="button" class="commonbut" id="clear" onclick="clearTxt(form.elements['preference(definitionId)'],form.elements['categoryName'])">清空</button>
			                </td>
				            </tr>
				            <tr>
				              <td class="formTitle">参数属性</td>
				              <td class="formComponent">
				              	<html:hidden property="preference(propertyId)"/>
				              	<logic:notEmpty name="property">
				              		<input type="text" name="fieldName" readonly="readonly" value="<bean:write name="property" property="name" ignore="true"/>" />
				                </logic:notEmpty>
				                <logic:empty name="property">
				                <input type="text" name="fieldName" readonly="readonly" value="<bean:write name="propertyName1" ignore="true"/>" />
				                </logic:empty>
			                  	<button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(definitionId)'],form.elements['preference(propertyId)'],form.elements['fieldName'])">选择...</button>
			                  	<button type="button" class="commonbut" id="clear" onclick="clearTxt(form.elements['preference(propertyId)'],form.elements['fieldName'])">清空</button>
			                </td>
				            <tr>
				              <td class="formTitle">参数属性2</td>
				              <td class="formComponent">
				              	<html:hidden property="preference(propertyId2)"/>
				              	<logic:notEmpty name="property2">
				              		<input type="text" name="fieldName2" readonly="readonly" value="<bean:write name="property2" property="name" ignore="true"/>" />
				              	</logic:notEmpty>
				              	<logic:empty name="property2">
				              		<input type="text" name="fieldName2" readonly="readonly" value="<bean:write name="propertyName2" ignore="true"/>" />
				              	</logic:empty>
			                  	<button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(definitionId)'],form.elements['preference(propertyId2)'],form.elements['fieldName2'])">选择...</button>
			                  	<button type="button" class="commonbut" id="clear" onclick="clearTxt(form.elements['preference(propertyId2)'],form.elements['fieldName2'])">清空</button>
			                </td>
				            </tr>
				            <tr>
              					<td class="formTitle"></td>
              					<td class="formComponent">
                					<html:checkbox style="width:25px" property="preference(noParameter)">不传递参数</html:checkbox>
              					</td>
            				</tr>
                        <tr>
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
            			<tr>
              				<td class="formTitle"></td>
              				<td class="formComponent">
                				<html:checkbox style="width:25px" property="preference(showHref)">以超链接展示</html:checkbox>
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
                  <button type="button" onclick="validatorRequired(new Array(form.elements['preference(channel)']),new Array('跳转到'),this.form)">保存</button>
                  <button type="button" onclick="window.parent.close()">取消</button>
               </div></td>
                </tr></portlet:form>
            </table>
            <script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
            <script type="text/javascript" language="javascript">
            function doSubmit(oForm){
              return true;
            }
            function openSelectorFileSelector($oEcho)
            {
                var templateID = window.parent.dialogArguments.template.ID;
                //var url = "/ide/site/resource/index.do?templateID="+templateID;
                var url = "/ide/site/resource/index.jsp?template="+templateID;
                var sOptions = "dialogHeight=600px;dialogWidth=780px;center=yes;resizable=no;status=no";
                var ret = window.showModalDialog(url, null, sOptions);
                if (ret != null && ret[0])
                $oEcho.value = ret[0];
            }
            function validatorRequired(object, name, form) {
            	var temp = true;
            	for(var j = 0; j < object.length; j++ ) {
            	if (object[j] != null && object[j].value != undefined) {
            		var s = object[j].value;
            		if (s.trim() == "") {
            			temp = false;
            			alert(name[j] + "不能为空");
            		}
            	} else {
            		temp = false;
            		alert(name[j] + "控件不存在，校验失败");
            	}
            	}
            	if (temp == true) {
                    form.submit();
            	}
            }
            </script>
