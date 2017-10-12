<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<tiles:insert definition="dialog_frame">
	<tiles:put name="title">修改模板属性</tiles:put>
	<tiles:put name="javascript">
		<meta http-equiv="pragma" content="no-cache">
		<html:javascript formName="editTemplateForm"/>
	<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
    	<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/dialog.js"/>"></script>
    	<script language="Javascript" type="text/Javascript">
			function showCreateCategoryDialog($select) {
				var ret = SiteDialog.createCategory();
				if (ret) {
					var option = document.createElement("option");
					option.value = ret.ID;
					option.text = ret.displayName;
					$select.add(option);
					$select.value = option.value;
				}
			}
			function validate($form) {
				if (validateEditTemplateForm($form)) {
					disableButton();
					return true;
				}
				return false;
			}
			function _submit(){
				document.forms[0].submit();
				if(document.all){
					window.close();
				}else{
					window.parent.close();
				}
			}
			function _close(){
				if(document.all){
					window.close();
				}else{
					window.parent.close();
				}
			}
		</script>
 	</tiles:put>
	<tiles:put name="dialog">
        <html:form action="update.do" method="post" enctype="multipart/form-data" onsubmit="return validate(this)">
          <html:hidden property="id"/>
          <table width="100%">
            <tr>
              <th>模板名称</th>
              <td><html:text property="name" size="32" disabled="true"/></td>
            </tr>
            <tr>
              <th>显示名称</th>
              <td><html:text property="displayName" maxlength="16" size="32" title="最多16个字符"></html:text></td>
            </tr>
			<tr>
				<th>模板语言</th>
				<td>
					<html:select property="language" style="width:193px">
						<logic:iterate id="language" name="languages">
							<bean:define id="ID" name="language" property="ID" type="String"/>
							<html:option value="<%=ID%>"><cms:node name="language" propertyName="title"/></html:option>
						</logic:iterate>
					</html:select>
				</td>
			</tr>
            <tr>
              <th>模板类别</th>
              <td>
	          	<html:select property="category" style="width:128px">
	          		<html:optionsCollection name="categories" label="displayName" value="ID"/>
	          	</html:select>
	            <button type="button" onclick="showCreateCategoryDialog(this.form.category)" class="btnMore">新建. . .</button>
              </td>
            </tr>
            <tr style="display:none"><!-- 暂时去掉这个功能-->
              <th>缩略图</th>
              <td><div class=formTips><html:file property="photo"/>上传模板缩略图</div>
                <div class="image">
                  <img width="90" height="90" src="../../sites/<bean:write name='editTemplateForm' property='name'/>/preview.jpg" alt=""/>
                </div>
              </td>
            </tr>
          </table>
          <div class="operation">
            <button type="button" onclick="SiteDialog.selectTemplate(document.forms[0].all('id').value, document.forms[0].all('category').value)" style="padding:0px; margin-left:0px; margin-right:40px" >管理模板...</button>
            <button id="btnOk" type="button" onclick="_submit()">确定</button>
            <button type="button" id="btnCancel" onclick="_close()">取消</button>
          </div>
        </html:form>
	</tiles:put>
</tiles:insert>
