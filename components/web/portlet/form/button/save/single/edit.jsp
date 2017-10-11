<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<table cellpadding="0" cellspacing="0" border="0">
    <portlet:form action="save" method="POST">
    <html:hidden property="preference(contentType)"/>
        <tr>
            <td class="pannelDiv" rowspan="2">
                <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="8">
                    <option value="0" selected="selected">基本设置</option>
                    <option value="1">其它设置</option>
                    <option value="2">图片按钮</option>
                    <option value="3">缺 省 值</option>
                </select></td>
                <td><fieldset>
                  <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
                        <tr>
                            <td class="formTitle">按钮名称</td>
                            <td class="formComponent"><html:text property="preference(value)"/> </td>
                          </tr>
                        <tr>
                            <td class="formTitle">标识符</td>
                            <td class="formComponent"><html:text property="preference(name)"/> </td>
                          </tr>
                          <tr>
                            <td></td>
                            <td>
                            <html:checkbox style="width:25px" property="preference(create)" value="true"/><span>创建新内容</span>
                            <!-- <span id="fixSaveTr" <logic:equal value="false" name="preferences" property="value(create)"> style="display:none;" </logic:equal>><html:checkbox style="width:25px" property="preference(createFixNode)" value="true"/>创建复合节点</span> -->
                            </td>
                          </tr>
                          <tr>
                             <td>父节点分类</td>
                             <td><html:hidden property="preference(parentCategory)"/><input type="text" name="parentCategoryName" readonly="readonly" value="<bean:write name="parentCategory" property="name" ignore="true"/>" />
                                 <button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(parentCategory)'],form.elements['parentCategoryName'],form.elements['parentFieldName'])">选择...</button>
                                 <!-- <button type="button" class="commonbut" id="clear" onclick="clearTxt(form.elements['preference(parentCategory)'],form.elements['parentCategoryName'])">清空</button> -->
                             </td>
                          </tr>
                          <tr>
                             <td class="formTitle" valign="top">复合属性</td>
                             <td class="formComponent" valign="top">
                                 <html:hidden property="preference(parentField)"/>
                                 <input type="text" name="parentFieldName" readonly="readonly" value="<bean:write name="parentPropertyName" ignore="true"/>" />
                                 <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(parentCategory)'],form.elements['preference(parentField)'],form.elements['parentFieldName'],['1','2','5','6','7','8','9'])">选择...</button>
                             </td>
                          </tr>
                        <tr>
                            <td class="formTitle">内容保存到</td>
                            <td class="formComponent">
                                <html:radio style="width:25px" property="preference(owner)" value="site"/>以当前网站为父节点<br />
                                <html:radio style="width:25px" property="preference(owner)" value="member"/>以登入会员为父节点<br />
                                <html:radio style="width:25px" property="preference(owner)" value="currentContent"/>以当前内容为为父节点
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
                            <td><html:checkbox style="width:25px" property="preference(forwardSelf)" value="true"/><span>保存后跳转到原页面</span></td>
                         </tr>
              		   <tr>
                            <td></td>
                            <td><html:checkbox style="width:25px" property="preference(hasIDParameter)" value="true"/><span>跳转时附带保存后节点的ID作为参数</span></td>
                          </tr>  
                    </table>
                </fieldset>
                <fieldset style="display:none">
                  <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
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
               			    <td class="formTitle">保存限制</td>
                			<td class="formComponent">
                  				<html:select property="preference(saveLimit)" style="width:213px">
                    			<html:option value="">无限制</html:option>
                     			<html:option value="PCOnly">每台PC只能保存一次</html:option>
                     			<html:option value="PCPerDate">每台PC24小时内只能保存一次</html:option>
                  				</html:select>
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
                        <tr>
                            <td class="formTitle">文字提示</td>
                            <td class="formComponent"><html:text property="preference(imgtitle)"/></td>
                        </tr>
                    </table>
                </fieldset>
                <fieldset style="display:none"><table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
                    <tr>
                        <td nowrap="nowrap"  class="selectionEditor">
                            <select multiple="multiple" name="preferences(default-values)" size="15" multiple="multiple" style="width:300px;">
                                <logic:present name="preferences" property="values(default-values)">
                                    <logic:iterate id="pattern" name="preferences" property="values(default-values)">
                                        <option value='<bean:write name="pattern" filter="false"/>'>
                                        <fulong:filter name="pattern" definition="category"/></option>
                                    </logic:iterate>
                                </logic:present>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                          <input type="hidden" name="preference(category)" value="<bean:write name="category" property="ID"/>">
                          <button type="button" onclick="newFilter(form.elements['preference(category)'],form.elements['preferences(default-values)'])">添加</button>
                          <button type="button" onclick="deleteOption(form.elements['preferences(default-values)'])">删除</button>
                        </td>
                    </tr>
                </table>
                </fieldset><div class="toolbar">
          <button type="button"  onclick="doSubmit(this.form)">保存</button>
          <button type="button" onclick="window.parent.close()">取消</button></div></td>
                </tr></portlet:form>
            </table>
            <script type="text/javascript" language="javascript">
            showActivityFullName(document.getElementsByName('preference(process)')[0],document.getElementsByName('preference(activity)')[0],document.getElementsByName('activityName')[0]);
            function doSubmit(oForm){
                var isCreateBox = oForm.elements['preference(create)'];
                var categoryID = '<bean:write name="category" property="ID" ignore="true"/>';
                if(isCreateBox.checked==true&&categoryID!=null&&categoryID!="principal-scheme"){
                    var parentCategoryID = oForm.elements['preference(parentCategory)'].value;
                    var parentFieldID = oForm.elements['preference(parentField)'].value;
                    if(categoryID==null||categoryID==""||parentFieldID==null||parentFieldID==""){
                        alert("表单分类或者复合属性配置为空！");
                        return false;
                    }
                	var xmlhttp = getXMLHttpRequest();
            		var urlTemp = '/ide/common/validateInheritance.do?categoryID=' + categoryID + '&parentCategoryID=' + parentCategoryID + "&parentFieldID=" + parentFieldID;
            		xmlhttp.open("Post",urlTemp,false);
            		xmlhttp.setRequestHeader("If-Modified-Since","0"); //不缓存Ajax
            		xmlhttp.send(null);
            		if(xmlhttp.responseText == "false"){
                		alert("表单分类与父节点分类无继承关系！");
                		return false;
            		}else{
            			selectAll(oForm.elements['preferences(default-values)']);
            			validatorSub(new Array(oForm.elements['preference(channel)'],oForm.elements['preference(forwardSelf)'],oForm.elements['preference(parentField)']),new Array('导航','跳转到原页面','复合属性'),oForm)
            		}
                }else{
                	selectAll(oForm.elements['preferences(default-values)']);
        			validatorSub(new Array(oForm.elements['preference(channel)'],oForm.elements['preference(forwardSelf)']),new Array('导航','跳转到原页面'),oForm)
                }
                   
            }
            <bean:define name="preferences" property="value(category)" id="categoryID" type="String"/>
            var propertys=new Array();
            <logic:iterate id="defaultValues" name="preferences" property="values(default-values)">
            <bean:define id="defaultValueS" name="defaultValues" type="String"/>
            <%String propertyID=defaultValueS.split(" ")[0];%>
            <cms:propertyDefinition definitionID="<%=categoryID%>"  propertyID="<%=propertyID%>" ID="pro"/>
            propertys['<%=propertyID%>']=new Object();
            propertys['<%=propertyID%>'].name='<bean:write name="pro" property="name" ignore="true"/>';
            propertys['<%=propertyID%>'].ID='<bean:write name="pro" property="ID" ignore="true"/>';
            </logic:iterate>
            function openSelectorFileSelector($oEcho)
            {
              var templateID = window.parent.dialogArguments.template.ID;
              var url = "/ide/site/resource/index.do?templateID="+templateID;
              var sOptions = "dialogHeight=600px;dialogWidth=780px;center=yes;resizable=no;status=no";
              var ret = window.showModalDialog(url, null, sOptions);
              if (ret != null && ret[0])
              $oEcho.value = ret[0];
            }
            function showFixSave(oCheckbox,oForm){
                var fixSaveTr = document.getElementById("fixSaveTr");
                if(oCheckbox.checked==true){
                	fixSaveTr.style.display = "";
                }else{
                	fixSaveTr.style.display = "none";
                }
            }
            </script>
