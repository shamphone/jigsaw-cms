<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form  action="save" method="POST">
    <tr>
      <td class="pannelDiv">
        <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
          <option value="0" selected="selected">内容抽取</option>
          <option value="1">过滤属性</option>
          <option value="2">图片按钮</option>
        </select></td>
        <td>
          <fieldset>
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
                            <td class="formTitle">样式</td>
                            <td class="formComponent">
                                <html:text property="preference(style)"/>
                                <button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(style)'],'')">选择...</button>
                            </td>
                        </tr>
              <tr>
                <td nowrap="nowrap">内容分类</td>
                <td>
                  <html:hidden property="preference(category)"/>
                  <input type="text" name="categoryName" readonly="readonly" value="<bean:write name="category" property="name" ignore="true"/>" />
                  <button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(category)'],form.elements['categoryName'])">选择...</button>
                </td>
              </tr>
              <tr>
                <td></td>
                <td><html:checkbox style="width:25px" property="preference(recursive)" styleId="a1" value="true"/><label for="a1">包含子分类</label></td>
              </tr>
              <tr>
                <td></td>
                <td><html:checkbox style="width:25px" property="preference(global)" styleId="a2" value="true"/><label for="a2">从本系统所有网站的内容库中抽取</label></td>
              </tr>
              <tr>
                <td class="formTitle" valign="top">引用/复合属性</td>
                <td class="formComponent" valign="top">
                  <html:hidden property="preference(refField)"/>
                  <html:hidden property="preference(refDefinition)"/>
                  <input type="text" name="comDefinitionName" ```````````````````readonly="readonly" value="<bean:write name="property" property="name" ignore="true"/>" />
                  <button type="button" class="commonbut" id="search" onclick="searchRefPropertyDefinition(form.elements['preference(category)'],form.elements['preference(refField)'],form.elements['preference(refDefinition)'],form.elements['comDefinitionName'])">选择...</button>
                  <button type="button" class="commonbut" id="clear" onclick="clearTxt(form.elements['preference(refField)'],form.elements['comDefinitionName'])">清空</button>
                </td>
              </tr>
              <tr>
                <td>排序属性</td>
                <td><html:hidden property="preference(order-field)"/>
                  <input type="text" name="fieldName" readonly="readonly" value="<bean:write name="orderProperty" property="name" ignore="true"/>" />
                  <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(category)'],form.elements['preference(order-field)'],form.elements['fieldName'])">选择...</button>
                  <button type="button" class="commonbut" id="clear" onclick="clearTxt(form.elements['preference(order-field)'],form.elements['fieldName'])">清空</button>
                  </td>
                </tr>
                <tr>
                  <td></td>
                  <td><html:radio style="width:25px" value="ASC" property="preference(order-style)"><bean:message  bundle="list" key="com.fulong.cms.portlet.list.edit.formTitle11.option7.ASC"/></html:radio> &nbsp;&nbsp;<html:radio style="width:25px" value="DESC" property="preference(order-style)"><bean:message  bundle="list" key="com.fulong.cms.portlet.list.edit.formTitle11.option8.DESC"/></html:radio>                            </td>
                </tr>
                <tr>
                	<td></td>
                  <td class="formComponent" valign="top"><html:checkbox styleId="b1" style="width:25px" property="preference(lucene)"><label for="b1">lucene全文搜索</label></html:checkbox></td>
                </tr>
                <tr>
                            <td class="formTitle">导航到</td>
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
                  <td class="formComponent" valign="top"><html:checkbox styleId="b1" style="width:25px" property="preference(filterByParamet)"><label for="b1">自动接收URL参数</label></html:checkbox></td>
                </tr>
                <tr>
                  <td class="formComponent" valign="top">
                  	<html:checkbox style="width:25px" property="preference(fromSearchDef)" styleId="a3" value="true"/><label for="a3">从搜索大纲中提取过滤条件</label>
                  	<button type="button" style="margin-left: 103px;" onclick="autoAddFilterPattern(form.elements['preference(category)'].value,form.elements['preferences(filter-patterns)'],'<bean:write name="searchDefinition" property="ID" ignore="true"/>')">自动添加</button>
                  </td>
                </tr>
                <tr>
                  <td class="formComponent">
                  	<select multiple="multiple" name="preferences(filter-patterns)" size="14" style="width:350px;">
                      <logic:present name="preferences" property="values(filter-patterns)">
                        <logic:iterate id="pattern" name="preferences" property="values(filter-patterns)">
	                        <logic:present name="searchDefinition">
	                        	<option value='<bean:write name="pattern" filter="false"/>'><fulong:filter name="pattern" definition="definition" searchDef="searchDefinition"/></option>
	                        </logic:present>
	                        <logic:notPresent name="searchDefinition">
	                        	<option value='<bean:write name="pattern" filter="false"/>'><fulong:filter name="pattern" definition="definition"/></option>
	                        </logic:notPresent>
                        </logic:iterate>
                      </logic:present>
                    </select>
                    <div style="margin-top:5px;" align="right">
                    	<button type="button" class="commonbut" onclick="newFilter_Search(form.elements['preference(category)'],form.elements['preferences(filter-patterns)'],'<bean:write name="searchDefinition" property="ID" ignore="true"/>')">添 加</button>
                    	<button type="button" class="commonbut" onclick="deleteOption(form.elements['preferences(filter-patterns)'])">删 除</button>
                    </div>                    
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
            <div class="toolbar">
          		<button type="submit" onclick="doSaveConfig(this.form)">保存</button>
          		<button type="button" onclick="window.parent.close()">取消</button>
            </div>
      </td>
    </tr>
  </portlet:form>
  </table>
  <script type="text/Javascript" language="Javascript">
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
  
    var definitionId='<bean:write name="preferences" property="value(category)" ignore="true"/>';
      function doSaveConfig(oForm){
          selectAll(oForm.elements['preferences(filter-patterns)']);
      }

      /**
      *
      *选择引用属性
      **/
      function searchRefPropertyDefinition(oCategory, oID, oRefID, oRefName){
        var arr =  CMSDialog.PropertyDefinitionSelector(oCategory.value,['1','2','3','4','5','6','7','8','10']);
        if(arr!=null&&arr.ID!=null){
          oID.value = arr.ID;
          oRefName.value = arr.name;
          oRefID.value = arr.refID;
        }
      }

    </script>
