<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
  <tiles:put name="title">创建脚本</tiles:put>
  <tiles:put name="javascript">
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/site.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/dialog.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="create.js"></script>
	<style type="text/css">
		input {width: 225px}
	</style>
  </tiles:put>
  <tiles:put name="dialog">
    <form>
    	<input type="hidden" id="templateName" value="<bean:write name="templateName" ignore="true"/>"/>
    	<table width="100%" border="0" cellpadding="4" cellspacing="0">
			<tr>
				<td width="180" valign="middle"><html:img
					page="/images/paddingleft.jpg" module="/common"
					style="border:2px inset"></html:img></td>
				<td valign="bottom">
					    <table border="0" cellpadding="2" cellspacing="2" align="center" width="100%">
					        <tr>
					          <td width="80px" >栏目名称</td>
					          <td>
					            <input type="text" name="name" value="<bean:write name="name" ignore="true"/>" style="IME-MODE: disabled" id="name" maxlength="32" title="系统中的ID及文件名称，全模板唯一。"/>
					          </td>
					        </tr>
					        <tr>
					          <td >显示名称</td>
					          <td>
					            <input type="text" value="<bean:write name="displayName" ignore="true"/>" id="displayName" title="系统中的展示名称，管理页面的时候看到的是这个。"/>
					          </td>
					        </tr>
					        <tr>
					          <td >父文件夹</td>
					          <td>
					          <input type="hidden"  name="folderPath" id="folderPath" />
					            <input style="width: 156px;" type="text" value="<bean:write name="ParentDisplayName" ignore="true"/>" id="parentDisplayName" readonly="true"/>
					         	<input  type="button" class="btnMore"  onclick="selectFolder(document.getElementById('folderPath').value,document.getElementById('parentDisplayName'))" value="选择...">
					          </td>
					        </tr>
					      </table>
				<fieldset style="padding: 5px 5px 5px 5px; height: 200px;">
				<legend>说明</legend>
				<div id="description"></div>
				</fieldset>
				</td>
			</tr>
		</table>
      <div class="operation">
        <button id="btnOk" onclick="doOk()" >确定</button>
        <button id="btnCancel" onclick="window.close()">取消</button>
      </div>
    </form>
  </tiles:put>
</tiles:insert>
