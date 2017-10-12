<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert definition="dialog_frame">
	<tiles:put name="title">修改模板属性</tiles:put>
	<tiles:put name="javascript">
		<html:javascript formName="renameTemplateForm"/>
	<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
		<meta http-equiv="pragma" content="no-cache">
    	<script language="Javascript" type="text/Javascript">
			function validate($form) {
				if (validateRenameTemplateForm($form)) {
					disableButton();
					return true;
				}
				return false;
			}
		</script>
 	</tiles:put>
	<tiles:put name="dialog">
        <html:form action="renamed.do" method="post" onsubmit="return validate(this)">
          <html:hidden property="id"/>
          <table width="100%">
            <tr>
              <th>模板名称</th>
              <td><html:text property="name" size="32" disabled="true"/></td>
            </tr>
            <tr>
	        <td>分辨率</td>
	          <td>
	          	<html:select styleId="resolution" property="resolution" style="width:193px">
	             	<html:options name="resolutions"/>
          		</html:select>
          		<script type="text/javascript">
	          		function initResolutionComponent(){
						var oSelect = document.getElementById("resolution");
						var options = oSelect.options;
						var len = options.length;
						for(var i=0;i<len;i++){
							if(options[i].value=="default"){
								options[i].text = "默认";
								return;
							}
						}
	          		}
	          		window.attachEvent("onload",initResolutionComponent);
          		</script>
	          </td>
	        </tr>  
            <tr>
              <th>显示名称</th>
              <td><html:text property="displayName" maxlength="32" size="32" title="最多32个字符"></html:text></td>
            </tr>
          </table>
          <div class="operation">
            <button id="btnOk" type="submit">确定</button>
            <button id="btnCancel" onclick="window.close()">取消</button>
          </div>
        </html:form>
	</tiles:put>
</tiles:insert>
