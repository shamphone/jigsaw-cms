<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
	<tiles:put name="title">修改页面信息</tiles:put>
  	<tiles:put name="javascript">
  		<meta http-equiv="pragma" content="no-cache">
    	<html:javascript formName="editChannelForm"/>
	    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
	    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms" page="/classes/cmsdialog.js"/>"></script>
    	<script type="text/javascript" language="javascript">
	        function disableButton()
	        {
	        	document.getElementById("btnOk").disabled = "true";
	        }
	        
	        function enableButton()
	        {
	        	document.getElementById("btnOk").disabled = "";
	        }
        
			function validate($form) {
				if (validateEditChannelForm($form)) {
					disableButton();
					return true;
				}
				return false;
			}
			
		</script>
		<style>
		  select {width:133px}
		</style>
  	</tiles:put>
  	<tiles:put name="dialog">
        <html:form action="/modify.do" method="POST" onsubmit="return validate(this)">
			<html:hidden property="ID"/>
			<table border="0" cellpadding="2" cellspacing="2" align="center" width="100%">
				<tr>
					<td nowrap="nowrap">页面名称</td>
					<td>
						<html:text property="name" size="32" disabled="true" title="页面的实际名称,不能修改"/>
					</td>
				</tr>
				<tr>
					<td nowrap="nowrap">显示名称</td>
					<td><html:text property="displayName" size="32" title="在页面以及客户端浏览器上显示的页面名称"/></td>
				</tr>
			</table>
			<div class="operation">
				<button id="btnOk" type="submit">确定</button>
				<button id="btnCancel" onclick="window.close()">取消</button>
			</div>
		</html:form>
	</tiles:put>
</tiles:insert>
