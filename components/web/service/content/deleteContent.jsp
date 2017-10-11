<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>设置删除内容</title>
<base target="_self" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="/components/service/styles.css">
<script language="Javascript" type="text/Javascript" src="/ide/common/script/common.js"></script>
<script language="Javascript" type="text/Javascript" src="/ide/service/scripts.js.jsp""></script>
<script language="Javascript" type="text/Javascript" src="/ide/cms/classes/cmsdialog.js""></script>
<script language="Javascript" type="text/Javascript" src="/ide/common/script/xmlhttp.js"></script>
<script language="Javascript" type="text/Javascript" src="/ide/common/script/ajax.js"></script>
<script type="text/javascript" language="javascript">
	var config = window.dialogArguments.parameters;
	function doInit(){
		config.populateForm(configForm);
		if(!document.all){
			document.getElementById("toolbar").style.top = document.body.clientHeight-40;
			document.getElementById("toolbar").style.left = 105;
			document.getElementById("toolbar").style.textAlign = "center";
			document.getElementById("pannelSelect").style.height = document.body.clientHeight-2;
			document.body.style.overflow = "hidden"
		}
	}
	function ok(){
		config.updateFromForm(configForm);
		window.returnValue = config;
		window.close();   
	}
	function selectLocalCategory(){
		  var definition=CMSDialog.NodeDefinitionSelector();
			if(definition!=null){
		   if(definition.length>0){
		       for(var j=0;j<definition.length;j++){
		           document.getElementById("localCategoryID").value=definition[j].ID;
		           document.getElementById("localCategoryName").value=definition[j].name;
		       }
		     }
		  }
		}
	function chooseSendRemoteCategory(objName){
		var remoteURL = document.getElementById(objName+"Domain").value+"/ide";
		var ret = CMSDialog.RemoteNodeDefinitionSelector(remoteURL);
		if(ret!=null){
			document.all(objName+"CategoryID").value = ret.ID;
			document.all(objName+"CategoryName").value = ret.name;	
		}
	}
	
    function getName(url,objName){
    	var ret = Remote.testURL(url);
    	if(ret!=null){
    		document.getElementById(objName+"siteNameSpan").innerHTML=ret.name;
            document.getElementById(objName+"BtnAdd").disabled = false;
    	}
    }

</script>
</head>
<body onload="doInit()">
<table cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<td class="pannelDiv" valign="top"><select id="pannelSelect" name="pannelSelect" onchange="Service.SelectPanel(this)" size="10">
					<option value="0">基本信息</option>
					<option value="1">远程分类1</option>
					<option value="2">远程分类2</option>
					<option value="3">远程分类3</option>
					<option value="4">远程分类4</option>
					<option value="5">远程分类5</option>
			</select></td>
			<td id="tdFieldsets" valign="top" align="center">
			<form name="configForm">
			<fieldset class="fieldPanel">
			<div
				style="height: 200px;">
				<table>
				<tr>
				<td>
			<label>本地内容分类</label>
			<!-- IE下会默认ID和Name相同，但是Mozila不会 -->
			<input type="hidden" name=localCategoryID  id="localCategoryID"/>
			</td>
			<td>
			<input type="text" name="localCategoryName" id="localCategoryName" readonly="readonly" />
			</td>
			<td>
			<button  type="button" onclick="selectLocalCategory()">选择...</button>
			</td>
			</tr>
			</table>
				</div>
			</fieldset>
			<!-- --------------------------------------------------------------------------------------------------------------------------- -->
			<!-- --------------------------------------------------------------------------------------------------------------------------- -->
			<fieldset style="display: none" class="fieldPanel">
			<div
				style="height: 200px;">
			<table>
			<tr>
			<td>
				远程网站
				</td>
				<td>
				<input type="text" name="remote1Domain" size="24"  id="remote1Domain"  onblur="getName(document.getElementById('remote1Domain').value,'remote1');"/>
               </td><td> 
                <button type="button" onclick="getName(document.getElementById('remote1Domain').value,'remote1');">检测...</button>
			</td>
			</tr>
			<tr>
			<td height="20px">网站名称</td>
			<td>
			<span id="remote1siteNameSpan"></span>
			</td>
			</tr>
			<tr>
			<td height="20px">
			远程分类
			</td>
			<td>
				<input type="hidden" name="remote1CategoryID" id="remote1CategoryID" />
			    <input type="text" name="remote1CategoryName" id="remote1CategoryName" readonly="readonly" size="30"/>
				</td>
				<td>
				<button type="button" id="remote1BtnAdd" disabled="disabled"  onclick="chooseSendRemoteCategory('remote1')">添加...</button>
				</td>
				</tr>
				</table>
				</div>
			</fieldset>
			<!-- --------------------------------------------------------------------------------------------------------------------------- -->
			<!-- --------------------------------------------------------------------------------------------------------------------------- -->
			<fieldset style="display: none" class="fieldPanel">
			<div
				style="height: 200px;">
			<table>
			<tr>
			<td>
				远程网站
				</td>
				<td>
<<<<<<< .mine
				http://<input type="text" name="remote2Domain"  id="remote2Domain" size="24" onblur="getName(document.getElementById('remote2Domain').value,'remote2');"/>
=======
				<input type="text" name="remote2Domain" size="24" onblur="getName(document.getElementById('remote2Domain').value,'remote2');"/>
>>>>>>> .r28222
               </td><td> 
                <button type="button" onclick="getName(document.getElementById('remote2Domain').value,'remote2');">检测...</button>
			</td>
			</tr>
			<tr>
			<td height="20px">网站名称</td>
			<td>
			<span id="remote2siteNameSpan"></span>
			</td>
			</tr>
			<tr>
			<td height="20px">
			远程分类
			</td>
			<td>
				<input type="hidden" name="remote2CategoryID" id="remote2CategoryID" />
			    <input type="text" name="remote2CategoryName" id="remote2CategoryName" readonly="readonly" size="30"/>
				</td>
				<td>
				<button type="button" id="remote2BtnAdd" disabled="disabled"  onclick="chooseSendRemoteCategory('remote2')">添加...</button>
				</td>
				</tr>
				</table>
				</div>
			</fieldset>
			<!-- --------------------------------------------------------------------------------------------------------------------------- -->
			<!-- --------------------------------------------------------------------------------------------------------------------------- -->
			<fieldset style="display: none" class="fieldPanel">
			<div
				style="height: 200px;">
			<table>
			<tr>
			<td>
				远程网站
				</td>
				<td>
<<<<<<< .mine
				http://<input type="text" name="remote3Domain"  id="remote3Domain"  size="24" onblur="getName(document.getElementById('remote3Domain').value,'remote3');"/>
=======
				<input type="text" name="remote3Domain" size="24" onblur="getName(document.getElementById('remote3Domain').value,'remote3');"/>
>>>>>>> .r28222
               </td><td> 
                <button type="button" onclick="getName(document.getElementById('remote3Domain').value,'remote3');">检测...</button>
			</td>
			</tr>
			<tr>
			<td height="20px">网站名称</td>
			<td>
			<span id="remote3siteNameSpan"></span>
			</td>
			</tr>
			<tr>
			<td height="20px">
			远程分类
			</td>
			<td>
				<input type="hidden" name="remote3CategoryID"  id="remote3CategoryID"s />
			    <input type="text" name="remote3CategoryName" id="remote3CategoryName" readonly="readonly" size="30"/>
				</td>
				<td>
				<button type="button" id="remote3BtnAdd" disabled="disabled"  onclick="chooseSendRemoteCategory('remote3')">添加...</button>
				</td>
				</tr>
				
				</table>
				</div>
			</fieldset>
			<!-- --------------------------------------------------------------------------------------------------------------------------- -->
			<!-- --------------------------------------------------------------------------------------------------------------------------- -->
			<fieldset style="display: none" class="fieldPanel">
			<div
				style="height: 200px;">
			<table>
			<tr>
			<td>
				远程网站
				</td>
				<td>
<<<<<<< .mine
				http://<input type="text" name="remote4Domain" id="remote4Domain" size="24" onblur="getName(document.getElementById('remote4Domain').value,'remote4');"/>
=======
				<input type="text" name="remote4Domain" size="24" onblur="getName(document.getElementById('remote4Domain').value,'remote4');"/>
>>>>>>> .r28222
               </td><td> 
                <button type="button" onclick="getName(document.getElementById('remote4Domain').value,'remote4');">检测...</button>
			</td>
			</tr>
			<tr>
			<td height="20px">网站名称</td>
			<td>
			<span id="remote4siteNameSpan"></span>
			</td>
			</tr>
			<tr>
			<td height="20px">
			远程分类
			</td>
			<td>
				<input type="hidden" name="remote4CategoryID" id="remote4CategoryID" />
			    <input type="text" name="remote4CategoryName" id="remote4CategoryName" readonly="readonly" size="30"/>
				</td>
				<td>
				<button type="button" id="remote4BtnAdd" disabled="disabled" onclick="chooseSendRemoteCategory('remote4')">添加...</button>
				</td>
				</tr>
				
				</table>
				</div>
			</fieldset>
			<!-- --------------------------------------------------------------------------------------------------------------------------- -->
			<!-- --------------------------------------------------------------------------------------------------------------------------- -->
			<fieldset style="display: none" class="fieldPanel">
			<div
				style="height: 200px;">
			<table>
			<tr>
			<td>
				远程网站
				</td>
				<td>
<<<<<<< .mine
				http://<input type="text" name="remote5Domain"  id="remote5Domain" size="24" onblur="getName(document.getElementById('remote5Domain').value,'remote5');"/>
=======
				<input type="text" name="remote5Domain" size="24" onblur="getName(document.getElementById('remote5Domain').value,'remote5');"/>
>>>>>>> .r28222
               </td><td> 
                <button type="button" onclick="getName(document.getElementById('remote5Domain').value,'remote5');">检测...</button>
			</td>
			</tr>
			<tr>
			<td height="20px">网站名称</td>
			<td>
			<span id="remote5siteNameSpan"></span>
			</td>
			</tr>
			<tr>
			<td height="20px">
			远程分类
			</td>
			<td>
				<input type="hidden" name="remote5CategoryID" id="remote5CategoryID" />
			    <input type="text" name="remote5CategoryName" id="remote5CategoryName" readonly="readonly" size="30"/>
				</td>
				<td>
				<button type="button" id="remote5BtnAdd" disabled="disabled" onclick="chooseSendRemoteCategory('remote5')">添加...</button>
				</td>
				</tr>
				
				</table>
				</div>
			</fieldset>
			</form>
			<!-- --------------------------------------------------------------------------------------------------------------------------- -->
			<!-- --------------------------------------------------------------------------------------------------------------------------- -->
			<div id="toolbar">
		    <button type="button" onclick="ok()" id="btnOK">确定</button>
		    <button type="button" onclick="window.close()" id="btnCancel">取消</button>
			</div>
			</td>
		</tr>
</table>
</body>
</html>
