<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>重置密码并发送邮件</title>
<base target="_self" />
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="/ide/service/styles.css"/>
<base target="_self" />
<script language="Javascript" type="text/Javascript" src="/ide/common/script/common.js"></script>
<script language="Javascript" type="text/Javascript" src="/ide/service/scripts.js.jsp"></script>
<script language="Javascript" type="text/Javascript" src="/ide/cms/classes/cmsdialog.js"></script>
<script language="Javascript" type="text/Javascript" src="/ide/common/script/xmlhttp.js"></script>
<script language="Javascript" type="text/Javascript" src="/ide/common/script/ajax.js"></script>
<script type="text/javascript" language="javascript">
	var config = window.dialogArguments.parameters;
	function doInit() {
		config.populateForm(configForm);
	}
	function ok() {
		config.updateFromForm(configForm);
		config.setValue("userNamePropID",document.all("userNamePropID").value);	 
		config.setValue("userNamePropID2",document.all("userNamePropID2").value);	 
		config.setValue("passwordPropID",document.all("passwordPropID").value);
		config.setValue("emailPropID",document.all("emailPropID").value);
		config.setValue("isReset",""+document.all("isReset").checked ); 
		config.setValue("emailTitle",document.all("emailTitle").value);
		config.setValue("content",document.all("content").value);
		config.setValue("server",document.all("server").value);
		config.setValue("from",document.all("from").value);
		config.setValue("mailPassword",document.all("mailPassword").value);
		window.returnValue = config;
		window.close();
	}
	function selectProperty(categoryID,objID,objName){
		if(categoryID==null){
			alert("请选择分类！");
			return;
			}
		    var result=CMSDialog.PropertyDefinitionSelector(categoryID,null,[1]);
		    if(result!=null){
		    	objID.value=result.ID;
		    	objName.value=result.name;
		      }
	}
	function selectCategory(objID,objName){
		  var definition=CMSDialog.NodeDefinitionSelector();
			if(definition!=null){
		   if(definition.length>0){
		       for(var j=0;j<definition.length;j++){
		    	   objID.value=definition[j].ID;
		    	   objName.value=definition[j].name;
		       }
		     }
		  }
		}
</script>
</head>
<body onload="doInit()">
<table cellpadding="0" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="pannelDiv" valign="top">
       <select id="pannelSelect" name="pannelSelect" onchange="Service.SelectPanel(this)" size="10">
          <option value="0">用户分类信息</option>
          <option value="1">重置记录信息</option>
		  <option value="2">邮件信息</option>
	   </select>
	</td>
	<td id="tdFieldsets" valign="top" align="center">
	  <form name="configForm">
	   <fieldset class="fieldPanel">
		<table cellpadding="2" cellspacing="0" border="0">
		   <tr>
			  <td>用户分类</td>
			  <td>
                 <input type="hidden" name="CategoryID" />
                 <input type="text" name="CategoryName" readonly="readonly" />
              </td>
			  <td>
			     <button onclick="selectCategory(document.getElementById('CategoryID'),document.getElementById('CategoryName'))">选择...</button>
			  </td>
		   </tr>
		   <tr>
			  <td>用户名</td>
			  <td>
			     <input type="hidden" name="userNamePropID" /><input type="text" name="userNamePropName" readonly="readonly" />
			  </td>
			  <td>
				 <button id="sourceButton" onclick="selectProperty(document.getElementById('CategoryID').value,document.getElementById('userNamePropID'),document.getElementById('userNamePropName'))">选择...</button>
		      </td>
			</tr>
			<tr>
			  <td>密码</td>
			  <td>
			     <input type="hidden" name="passwordPropID" /><input type="text" name="passwordPropName"  readonly="readonly"/>
			  </td>
			  <td>
				 <button id="btnDest" onclick="selectProperty(document.getElementById('CategoryID').value,document.getElementById('passwordPropID'),document.getElementById('passwordPropName'))">选择...</button>
			  </td>
			</tr>
			<tr>
			  <td>e-mail</td>
			  <td>
			     <input type="hidden" name="emailPropID" /><input type="text" name="emailPropName"  readonly="readonly"/>
			  </td>
			  <td>
				 <button id="btnDest" onclick="selectProperty(document.getElementById('CategoryID').value,document.getElementById('emailPropID'),document.getElementById('emailPropName'))">选择...</button>
			  </td>
			</tr>
			<tr>
			  <td></td>
			  <td colspan="2">
			     <input type="checkbox" name="isReset"/> 重置密码
			  </td>
			</tr>
		</table>
	   </fieldset>
	   <fieldset class="fieldPanel" style="display:none">
		<table cellpadding="2" cellspacing="0" border="0">
			<tr>
			  <td>重置记录分类</td>
			  <td>
                 <input type="hidden" name="CategoryID2" />
                 <input type="text" name="CategoryName2" readonly="readonly" />
              </td>
			  <td>
			     <button onclick="selectCategory(document.getElementById('CategoryID2'),document.getElementById('CategoryName2'))">选择...</button>
			  </td>
		    </tr>
			<tr>
			  <td>用户名属性</td>
			  <td>
			     <input type="hidden" name="userNamePropID2" /><input type="text" name="userNamePropName2" readonly="readonly" />
			  </td>
		      <td>
				 <button id="sourceButton" onclick="selectProperty(document.getElementById('CategoryID2').value,document.getElementById('userNamePropID2'),document.getElementById('userNamePropName2'))">选择...</button>
			  </td>
			</tr>
		</table>
	   </fieldset>
	   <fieldset class="fieldPanel" style="display:none">
		<table cellpadding="2" cellspacing="0" border="0">
			<tr>
			  <td>邮件服务器地址</td>
			  <td>
                 <input type="text" name="server" />
              </td>
		    </tr>
			<tr>
			  <td>邮箱地址</td>
			  <td><input type="text" name="from" /></td>
			</tr>
			<tr>
			  <td>邮箱密码</td>
			  <td><input type="text" name="mailPassword" /></td>
			</tr>
			<tr>
			  <td>邮件标题：</td>
			  <td>
			     <input type="text" name="emailTitle" />
			  </td>
			</tr>
			<tr>
			  <td>邮件内容：</td>
			  <td  COLSPAN="2">
			     <textarea cols="30" rows="10" name="content">	您好！						您的用户名为$username,密码为：$password。</textarea>
			  </td>
			</tr>
		</table>
	   </fieldset>
	  </form>
	  <div id="toolbar">
		<button onclick="ok()" id="btnOK">确定</button>
		<button onclick="window.close()" id="btnCancel">取消</button>
	  </div>
	</td>
  </tr>	
</table>
</body>
</html>
