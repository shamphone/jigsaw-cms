<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<table cellpadding="0" cellspacing="0" border="0">
    <portlet:form action="save" method="POST" onsubmit="doSubmit(this)">
        <tr>
            <td class="pannelDiv" rowspan="2">
                <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="8">
                    <option value="0" selected="selected">基本设置</option>
                    <option value="1">缺 省 值</option>
                    <option value="2">内容权限</option>
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
                            <td class="formTitle">按钮样式</td>
                            <td class="formComponent">
                                <html:text property="preference(style)"/>
                                <button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(style)'],'')">选择...</button>
                            </td>
                        </tr>
                        <!--
                        <tr>
                            <td class="formTitle">按钮宽度</td>
                            <td class="formComponent">
                                <html:text property="preference(size)"/>
                            </td>
                        </tr>-->
                        <tr>
                            <td class="formTitle">关联活动</td>
                            <td class="formComponent"><html:hidden property="preference(process)"/><html:hidden property="preference(activity)"/>
                            <input type="text" name="activityName" /><button type="button" class="commonbut" onclick="doSelectActivity(this.form.elements['preference(process)'],this.form.elements['preference(activity)'],this.form.elements['activityName'],window.parent.dialogArguments.window.oTemplate)">选择...</button>
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
                            <td><html:checkbox style="width:25px" property="preference(notIgnoreRequest)" value="true"/><span>附带url地址中所带来的参数</span></td>
                         </tr>
                    </table>
                </fieldset>
                <fieldset style="display:none"><table width="100%" cellpadding="0" cellspacing="0" border="0">
                    <tr>
                        <td>
                          <input type="hidden" name="preference(category)" value="<bean:write name="category" property="ID" ignore="true"/>"/>
                            <select multiple="multiple" name="preferences(default-values)" size="15" multiple="true" style="width:300px;">
                                <logic:present name="preferences" property="values(default-values)">
                                    <logic:iterate id="pattern" name="preferences" property="values(default-values)">
                                        <option value='<bean:write name="pattern" filter="false"/>'>
                                        <fulong:filter name="pattern" definition="category"/></option>
                                    </logic:iterate>
                                </logic:present>
                            </select>
                        </td>
                        <td valign="top">
                          <button type="button" class="commonbut" onclick="newFilter(form.elements['preference(category)'],form.elements['preferences(default-values)'])">添加...</button><br/>
                          <button type="button" class="commonbut" onclick="deleteOption(form.elements['preferences(default-values)'])">删 除</button>
                        </td>
                    </tr>
                </table>
                </fieldset><fieldset style="display:none">
                    <table width="100%" cellpadding="0" cellspacing="0" border="0">
                        <tr>
                            <td colspan="2"> <html:checkbox style="width:25px" property="preference(free)" value="true"/>提交后所有用户都能看到这个内容
                            </td>
                        </tr>
                        <tr>
                            <td class="formComponent"><html:select property="preferences(principals)" size="15" multiple="true" style="width:300px;">
                                <logic:present name="preferences" property="values(principals)">
                                    <logic:iterate id="principal" name="preferences" property="values(principals)" type="java.lang.String">
                                        <cms:nodeDefinition ID="prinDefinition" definitionID='<%= principal %>'/>
                                            <option value='<bean:write name="principal"/>'><bean:write name="prinDefinition" property="name"/> </option>
                                        </logic:iterate>
                                    </logic:present>
                                </html:select>
                            </td>
                            <td valign="top">
                                <button type="button" class="commonbut" type="button" onclick="addPrincipals()">添加...</button><br/>
                                <button type="button" class="commonbut" type="button" onclick="delPrincipals()">删 除</button>
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
            showActivityFullName(document.getElementsByName('preference(process)')[0],document.getElementsByName('preference(activity)')[0],document.getElementsByName('activityName')[0]);
            function doSubmit(oForm){
           	  selectAll(document.getElementsByName('preferences(default-values)')[0]);
             // selectAll(document.getElementsByName('preferences(copyTo)')[0]);
              //selectAll(document.getElementsByName('preferences(moveTo)')[0]);
             // selectAll(document.getElementsByName('preferences(recommendTo)')[0]);
              selectAll(document.getElementsByName('preferences(principals)')[0]);
              validatorSub(new Array(oForm.elements['preference(channel)'],oForm.elements['preference(forwardSelf)']),new Array('导航','跳转到原页面'),oForm)
              return true;
            }
                function addPrincipals(){
                    var principals=CMSDialog.NodeDefinitionSelector("principal-scheme");
                    if(principals!=null){
                        var option=document.createElement("option");
                        option.value=principals.ID;
                        option.text=principals.name;
                        if(document.all){
                        	document.getElementsByName('preferences(principals)')[0].add(option);
                        }else{
                        	document.getElementsByName('preferences(principals)')[0].add(option,null);
                        }
                    }
                }
                function delPrincipals(){
                    var principals=document.getElementsByName('preferences(principals)')[0].options;
                    if(principals!=null){
                        for(var i=0;i<principals.length;i++)
                        if(principals[i].selected){
                            if(document.all){
                            	principals[i].removeNode(true);
                            }else{
                            	principals[i].parentNode.removeChild(principals[i]);
                            }
                        }
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
            </script>
