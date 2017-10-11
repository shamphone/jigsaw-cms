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
          <option value="0" selected="selected">基本设置</option>
          <option value="1">父分类重复内容设置</option>
        </select></td>
        <td><fieldset>
                <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
               	  <tr>
              			<td class="formTitle" valign="center">分类来源</td>
              			<td class="formComponent" valign="top">
                			<table border=0 cellpadding=0 cellspacing=0>
                  				<tr>
                    				<td>
                      					<html:radio style="width:25px" styleId="b1" property="preference(defSource)" value="getFromURL">
                        				<label for="b1">使用URL参数指定内容</label></html:radio>
                     				</td>
                    			</tr>
                    			<tr>
                      				<td>
                        				<html:radio style="width:25px" styleId="b2" property="preference(defSource)" value="customerSelect"><label for="b2">选择</label></html:radio>
                        				<html:hidden property="preference(category)"/>
                     					<input type="text" name="categoryName" readonly="readonly" value="<bean:write name="category" property="name" ignore="true"/>" />
                      					<button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(category)'],form.elements['categoryName'])">选择...</button>
                      				</td>
                    			</tr>
                  			</table>
                		</td>
              	  </tr>
                  <tr>
                    <td class="formTitle">列表样式</td>
                    <td class="formComponent">
                    	<html:text  property="preference(style)"/>
                    	<button type="button" class="commonbut" onclick="selectStyle(this.form.elements['preference(style)'])">选择...</button>
                    </td>
                  </tr>
                  <tr>
                    <td class="formTitle">显示子分类个数</td>
                    <td class="formComponent">
                    	<html:text  property="preference(num)"/>(默认全显示)
                    </td>
                  </tr>
                  <tr>
                    <td class="formTitle">子分类重复内容</td>
                    <td class="formComponent" width="80%"><bean:parameter id="formDefinition" name="formDefinition" value="" />
                  <html:hidden property="preference(contextName)"/>
          			<button type="button" onclick="editClipFile('<bean:write name="childrenPath"/>',form.elements['preference(category)'],document.getElementById('childEditorFrame'),'<bean:write name="formDefinition" ignore="true"/>',window.parent.dialogArguments.styleSheets)">修改...</button>
                  </td>
                  </tr>
                  <tr>
                    <td colspan="2">
                    <iframe scrolling="yes" marginheight="0" marginwidth="0" frameborder="1" src='<bean:write name="childrenClipPath"/>?javax.portlet.page.mode=view' width="100%" height="180px" scrolling="none" id="childEditorFrame">                </iframe>
                  </td>
                  </tr>
                  </table>
                </fieldset>
                <fieldset style="display:none" >
                <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
                <tr><td colspan="2" class="formTitle"><html:checkbox property="preference(showrootmsg)" onclick="show(this);" styleId="showrootmsg"/> 显示分类根项 </td></tr>
                  <tr id="content" style="display:none">
                    <td class="formTitle">父分类重复内容</td>
                    <td class="formComponent" width="80%"><bean:parameter id="formDefinition" name="formDefinition" value="" />
          			<button type="button" onclick="editClipFile('<bean:write name="parentPath"/>',form.elements['preference(category)'],document.getElementById('parentEditorFrame'),'<bean:write name="formDefinition" ignore="true"/>',window.parent.dialogArguments.styleSheets)">修改...</button>
                  </td>
                  </tr>
                  <tr id="clip" style="display:none">
                  <td colspan="2">
                    <iframe scrolling="yes" marginheight="0" marginwidth="0" frameborder="1" src='<bean:write name="parentClipPath"/>?javax.portlet.page.mode=view' width="350px" height="280px" id="parentEditorFrame">                </iframe>
                  </td>
                  </tr>
                  </table>
                </fieldset>
                <div class="toolbar">
                <button type="button" class="commonbut" type="submit" onclick="XRepeaterdoSaveConfig(this.form)">保存</button>
                <button type="button" class="commonbut" onclick="window.parent.close()">取消</button>
            </div>
      </td>
    </tr>
  </table>
    <script type="text/Javascript" language="Javascript">
		if(document.getElementById("showrootmsg").checked){
			document.getElementById("content").style.display = "";
			document.getElementById("clip").style.display = "";}
		else{
			document.getElementById("content").style.display = "none";
			document.getElementById("clip").style.display = "none";}
	  function show(checkbox){
		if(checkbox.checked){
			document.getElementById("content").style.display = "";
			document.getElementById("clip").style.display = "";
		}else{
			document.getElementById("content").style.display = "none";
			document.getElementById("clip").style.display = "none";
		}
      }
      function XRepeaterdoSaveConfig(oForm){
    	 oForm.elements['preference(contextName)'].value=window.parent.dialogArguments.template.name;
    	 oForm.submit();
       }
    </script>
  </portlet:form>
