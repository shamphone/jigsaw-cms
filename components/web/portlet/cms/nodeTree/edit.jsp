<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
    <script type="text/Javascript" src='/ide/site/dialog.js'></script>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form  action="save" method="POST">
    <tr>
      <td class="pannelDiv">
        <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
          <option value="0" selected="selected">基本设置</option>
          <option value="1">重复内容</option>
        </select></td>
        <td><fieldset>
                <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
                  <tr>
              <td class="formTitle" valign="top">内容来源</td>
              <td class="formComponent" valign="top">
                <table border=0 cellpadding=0 cellspacing=0>
                <tr>
                    <td>
                      <html:radio style="width:25px" styleId="b1" property="preference(contentType)" value="customnode" >
                        <label for="b1">使用缺省值</label></html:radio>
                      </td>
                      <td>
                    </tr>
                  <tr>
                    <td>
                      <html:radio style="width:25px" styleId="b2" property="preference(contentType)" value="default">
                        <label for="b2">使用URL参数指定内容</label></html:radio>
                      </td>
                      <td>
                    </tr>
                    <tr>
                      <td>
                        <html:radio style="width:25px" styleId="b3" property="preference(contentType)" value="user"><label for="b2">使用当前登录用户</label></html:radio>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <html:radio style="width:25px" styleId="b4" property="preference(contentType)" value="site"><label for="b3">使用当前网站所属用户</label></html:radio>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
                  <tr>
                    <td nowrap="nowrap">分类</td>
                    <td>
                      <html:hidden property="preference(categoryId)"/>
                      <html:text property="preference(categoryName)" readonly="readonly"/>
                      <button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(categoryId)'], form.elements['preference(categoryName)'])">选择...</button>
                    </td>
                  </tr>
                  <tr>
	                <td class="formTitle" valign="top">显示属性</td>
	                <td class="formComponent" valign="top">
	                  <html:hidden property="preference(field)"/>
	                  <input type="text" name="fieldName" readonly="readonly" value="<bean:write name="propertyName" ignore="true"/>" />
	                  <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(categoryId)'],form.elements['preference(field)'],form.elements['fieldName'],['1','2','3','4','5','6','7','8'])">选择...</button>
	                </td>
             	 </tr>
                  <tr>
                    <td nowrap="nowrap">内容</td>
                    <td>
                      <html:hidden property="preference(nodeId)"/>
                      <html:text property="preference(nodeName)" readonly="readonly"/>
                      <button type="button" class="commonbut" id="searchN" onclick="chooseNode(form.elements['preference(nodeId)'], form.elements['preference(nodeName)'], form.elements['preference(categoryId)'])">选择...</button>
                    </td>
                  </tr>
                  <tr>
                    <td class="formTitle">列表样式</td>
                    <td class="formComponent"><html:text property="preference(style)"/><button type="button" class="commonbut" onclick="selectStyle(this.form.elements['preference(style)'])">选择...</button></td>
                  </tr>
                  </table>
                </fieldset>
                 <fieldset style="display:none" >
                <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
                <tr>
                  <td class="formTitle">重复内容</td>
                  <td class="formComponent" width="80%"><bean:parameter id="formDefinition" name="formDefinition" value="" />
                  <html:hidden property="preference(contextName)"/>
          			<button type="button" onclick="editClipFile('<bean:write name="path"/>',form.elements['preference(categoryId)'],document.getElementById('editorFrame'),'<bean:write name="formDefinition" ignore="true"/>',window.parent.dialogArguments.styleSheets)">修改...</button>
                  </td>
                </tr>
                <tr>
                  <td colspan="2">
                    <iframe scrolling="yes" marginheight="0" marginwidth="0" frameborder="1" src='<bean:write name="clipPath"/>?javax.portlet.page.mode=view' width="350px" height="280px" id="editorFrame">                </iframe>
                  </td>
                </tr>
              </table>
                </fieldset>
                <div class="toolbar">
                <button type="button" class="commonbut" onclick="XRepeaterdoSaveConfig(this.form)">保存</button>
                <button type="button" class="commonbut" onclick="window.parent.close()">取消</button>
            </div>
      </td>
    </tr>
  </table>
  <script type="text/Javascript" language="Javascript">
    </script>
    <script type="text/Javascript" language="Javascript">
		function chooseProperty($oId, $oName, $oCategoryId){
			var categoryId = $oCategoryId.value;
			if (!categoryId) {
				alert("请选择依赖分类！")
				return;
			}
			var property = CMSDialog.PropertyDefinitionSelector(categoryId, null, null);
			if(property != null){
				$oId.value = property.ID;
				$oName.value = property.name;
			}
		}
	
		function chooseNode($oId, $oName, $oCategoryId){
			var categoryId = $oCategoryId.value;
			if (!categoryId) {
				alert("请选择依赖分类！")
				return;
			}
			var node = CMSDialog.NodeSelector(categoryId, false,false,false,'title','title','asc');
			if(node != null){
				$oId.value = node.id;
				if(typeof node['title'] != "undefined" && node['title']!=""){
					$oName.value = node['title'];
				}else if(node.id){
					$oName.value = node.id;
				}else{
					for(var i in node){
						if(node[i]){
							$oName.value = node[i];
							break;
						}
					}
				}
			}
		}
		function XRepeaterdoSaveConfig(oForm){
	    	 oForm.elements['preference(contextName)'].value=window.parent.dialogArguments.template.name;
	    	 oForm.submit();
	       }
    </script>
  </portlet:form>
