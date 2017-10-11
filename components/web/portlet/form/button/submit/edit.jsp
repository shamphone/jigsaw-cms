<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<table cellpadding="0" cellspacing="0" border="0">
    <portlet:form action="save" method="POST" onsubmit="doSubmit(this)">
        <tr>
            <td class="pannelDiv" rowspan="2">
                <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="8">
                    <option value="0" selected="selected">基本设置</option>
                    <option value="1">缺 省 值</option>
                    <option value="2">内容操作</option>
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
                            <td class="formTitle">宽度</td>
                            <td class="formComponent">
                                <html:text property="preference(size)"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="formTitle">保存后导航到</td>
                            <td class="formComponent">
		                      <html:hidden property="preference(channel)"/>
		                      <input type="text" name="channelName" <logic:notEmpty name="channel">value="<bean:write name="siteTemplate" property="displayName" ignore="true"/>.<bean:write name="channel" property="displayName" ignore="true"/>"</logic:notEmpty>/><button type="button" class="commonbut" onclick="chooseColumn('<bean:write name="siteTemplate" property="name"/>',this.form.elements['preference(channel)'],this.form.elements['channelName'])">选择...</button>
	                    	</td>
                        </tr>
                    </table>
                </fieldset><fieldset style="display:none"><table width="100%" cellpadding="0" cellspacing="0" border="0">
                    <tr>
                        <td nowrap="nowrap"  class="selectionEditor">
                            <select multiple="multiple" name="preferences(default-values)" size="15" multiple="true" style="width:300px;">
                                <logic:present name="preferences" property="values(default-values)">
                                    <logic:iterate id="pattern" name="preferences" property="values(default-values)">
                                        <option value='<bean:write name="pattern" filter="false"/>'><bean:write name="pattern" filter="false"/></option>
                                    </logic:iterate>
                                </logic:present>
                            </select>
                        </td>
                        <td valign="top">
                          <input type="hidden" name="preference(category)" value="<bean:write name="category" property="ID"/>">
                          <button type="button" class="commonbut" onclick="newFilter(form.elements['preference(category)'],form.elements['preferences(default-values)'])">添加...</button><br/>
                          <button type="button" class="commonbut" onclick="deleteFilter(form.elements['preference(category)'],form.elements['preferences(default-values)'])">删 除</button>
                        </td>
                    </tr>
                </table>
                </fieldset><fieldset style="display:none"><table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
                        <tr>
                            <td class="formTitle">复制到</td>
                            <td class="formComponent"><table width="100%" cellpadding="0" cellspacing="0" border="0">
                    <tr>
                        <td>
                              <html:select property="preferences(copyTo)" multiple="true" size="4" style="width:210px">
                                  <logic:iterate id="category" name="copyCategorys">
                                    <option value='<bean:write name="category" property="ID"/>'><bean:write name="category" property="name"/></option>
                                  </logic:iterate>
                              </html:select>
                        </td>
                        <td valign="top">
                                <button type="button" class="commonbut" onclick="addNodeDefinition(this.form.elements['preferences(copyTo)'])">添加...</button><br/>
                                <button type="button" class="commonbut" onclick="deleteOption(form.elements['preferences(copyTo)'])">删 除</button>
                        </td>
                    </tr>
                </table>
                            </td>
                        </tr>
                        <tr>
                            <td class="formTitle">移动到</td>
                            <td class="formComponent"><table width="100%" cellpadding="0" cellspacing="0" border="0">
                    <tr>
                        <td>
                              <html:select property="preferences(moveTo)" multiple="true" size="4" style="width:210px">
                                  <logic:iterate id="category" name="moveCategorys">
                                    <option value='<bean:write name="category" property="ID"/>'><bean:write name="category" property="name"/></option>
                                  </logic:iterate>
                              </html:select>
                        </td>
                        <td valign="top">
                                <button type="button" class="commonbut" onclick="addNodeDefinition(this.form.elements['preferences(moveTo)'])">添加...</button><br/>
                                <button type="button" class="commonbut" onclick="deleteOption(form.elements['preferences(moveTo)'])">删 除</button>
                        </td>
                    </tr>
                </table>
                            </td>
                        </tr>
                        <tr>
                            <td class="formTitle">推荐到</td>
                            <td class="formComponent"><table width="100%" cellpadding="0" cellspacing="0" border="0">
                    <tr>
                        <td>
                              <html:select property="preferences(recommendTo)" multiple="true" size="4" style="width:210px">
                                  <logic:iterate id="category" name="recommendCategorys">
                                    <option value='<bean:write name="category" property="ID"/>'><bean:write name="category" property="name"/></option>
                                  </logic:iterate>
                              </html:select>
                        </td>
                        <td valign="top">
                                <button type="button" class="commonbut" onclick="addNodeDefinition(this.form.elements['preferences(recommendTo)'])">添加...</button><br/>
                                <button type="button" class="commonbut" onclick="deleteOption(form.elements['preferences(recommendTo)'])">删 除</button>
                        </td>
                    </tr>
                </table>
                            </td>
                        </tr>
                </table>
                </fieldset>
                <div class="toolbar">
          <button type="button" onclick="validatorRequired(new Array(form.elements['preference(channel)']),new Array('保存后导航到'),this.form)">保存</button>
          <button type="button" onclick="window.parent.close()">取消</button></div></td>
                </tr></portlet:form>
            </table>
            <script type="text/Javascript" src='/ide/common/script/portlet.js'></script>
            <script type="text/javascript" language="javascript">
            function doSubmit(oForm){
              selectAll(document.getElementsByName('preferences(default-values)')[0]);
              return true;
            }
            <bean:define name="preferences" property="value(category)" id="categoryID" type="String"/>
            var propertys=new Array();
            <logic:iterate id="defaultValues" name="preferences" property="values(default-values)">
            <bean:define id="defaultValueS" name="defaultValues" type="String"/>
            <%String propertyID=defaultValueS.split(" ")[0];%>
            <fulong:propertyDefinition definitionID="<%=categoryID%>"  propertyID="<%=propertyID%>" ID="pro"/>
            propertys['<%=propertyID%>']=new Object();
            propertys['<%=propertyID%>'].name='<bean:write name="pro" property="name"/>';
            propertys['<%=propertyID%>'].ID='<bean:write name="pro" property="ID"/>';
            </logic:iterate>
            var filter = document.forms[0].elements['preferences(default-values)'];
            var ops = filter.options;
            for(var i=0;i<ops.length;i++){
              FilterParser.init(ops[i].value,propertys)
              var pro = FilterParser.parserProperty();
              var op = FilterParser.parserOperation();
              var va = FilterParser.parserValue();
              ops[i].text = pro+" "+op+" "+va;
            }
            </script>
