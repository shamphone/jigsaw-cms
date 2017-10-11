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
                </select></td>
                <td><fieldset>
                    <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
                        	<tr>
				              <td class="formTitle" valign="top">内容来源</td>
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
				              <td class="formTitle">参数值内容分类</td>
				              <td class="formComponent">
			                  <html:hidden property="preference(definitionId1)"/>
			                  <input type="text" name="categoryName1" readonly="readonly" value="<bean:write name="category1" property="name" ignore="true"/>" />
			                  <button type="button" class="commonbut" id="searchN1" onclick="searchNodeDefinition(form.elements['preference(definitionId1)'],form.elements['categoryName1'])">选择...</button>
			                </td>
				            </tr>
				            <tr>
				              <td class="formTitle">参数值属性</td>
				              <td class="formComponent">
				              	<html:hidden property="preference(propertyValue)"/>
			                  	<input type="text" name="fieldName" readonly="readonly" value="<bean:write name="property" property="name" ignore="true"/>" />
			                  	<button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(definitionId1)'],form.elements['preference(propertyValue)'],form.elements['fieldName'])">选择...</button>
			                  </td>
			                </tr>
			                <tr>
				              <td class="formTitle">&nbsp;</td>
				              <td class="formComponent"><html:checkbox style="width:25px" property="preference(contentIDForValue)">取ID值作为参数值</html:checkbox></td>
				            </tr>
			                <tr>
				              <td class="formTitle">参数名内容分类</td>
				              <td class="formComponent">
			                  <html:hidden property="preference(definitionId2)"/>
			                  <input type="text" name="categoryName2" readonly="readonly" value="<bean:write name="category2" property="name" ignore="true"/>" />
			                  <button type="button" class="commonbut" id="searchN2" onclick="searchNodeDefinition(form.elements['preference(definitionId2)'],form.elements['categoryName2'])">选择...</button>
			                </td>
				            </tr>			                
				            <tr>
				              <td class="formTitle">参数名属性</td>
				              <td class="formComponent">
				              	<html:hidden property="preference(propertyName)"/>
			                  	<input type="text" name="fieldName2" readonly="readonly" value="<bean:write name="property2" property="name" ignore="true"/>" />
			                  	<button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(definitionId2)'],form.elements['preference(propertyName)'],form.elements['fieldName2'])">选择...</button>
			                </td>
				            </tr>
				            <tr>
				              <td class="formTitle">&nbsp;</td>
				              <td class="formComponent"><html:checkbox style="width:25px" property="preference(contentIDForName)">取ID值作为参数名</html:checkbox></td>
				            </tr>
                        <tr>
                            <td class="formTitle">跳转到</td>
                            <td class="formComponent">
		                      <html:hidden property="preference(channel)"/>
		                      <input type="text" name="channelName" <logic:notEmpty name="channel">value="<bean:write name="siteTemplate" property="displayName" ignore="true"/>.<bean:write name="channel" property="displayName" ignore="true"/>"</logic:notEmpty>/><button type="button" class="commonbut" onclick="chooseColumn('<bean:write name="siteTemplate" property="name"/>',this.form.elements['preference(channel)'],this.form.elements['channelName'])">选择...</button>
                  			</td>
                        </tr>
                    </table>
                </fieldset>
                <div class="toolbar">
                  <button type="button" onclick="validatorRequired(new Array(form.elements['preference(propertyValue)'],form.elements['preference(propertyName)'],form.elements['preference(channel)']),new Array('参数值属性','参数名属性','跳转到'),this.form)">保存</button>
                  <button type="button" onclick="window.parent.close()">取消</button>
               </div></td>
                </tr></portlet:form>
            </table>
            <script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
            <script type="text/javascript" language="javascript">
            function doSubmit(oForm){
              return true;
            }
            </script>
