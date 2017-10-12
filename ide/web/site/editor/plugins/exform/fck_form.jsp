<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>新建FORM</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta content="noindex, nofollow" name="robots" />
		<script src="<html:rewrite page='/editor/dialog/common/fck_dialog_common.js' module="/common"/>" type="text/javascript"></script>
		<script src="<html:rewrite page="/script/portlet.js" module="/common"/>" type="text/javascript"></script>
		<script type="text/javascript"><!--
			var oEditor = window.parent.InnerDialogLoaded() ;
			var oDOM = oEditor.FCK.EditorDocument ;
			var oActiveEl = oEditor.FCKSelection.MoveToAncestorNode( 'FORM' ) ;
			window.onload = function() {
				// First of all, translate the dialog box texts
				oEditor.FCKLanguageManager.TranslatePage(document) ;
				
				if ( oActiveEl ) {
					document.title = "修改FORM(" + oActiveEl.name + ")";
					GetE('txtName').value	= oActiveEl.name ;
					GetE('txtMethod').value	= oActiveEl.method
					try{
						GetE('txtDefinitionID').value	= oActiveEl.attributes("definition").value ;
						GetE('txtDefinitionName').value	= oActiveEl.attributes("definitionname").value ;
					}catch(e){}
					try{
						setRadioValue(nodeType, oActiveEl.attributes("node").value );
					}catch(e){}

					if (oActiveEl.target)
						GetE('txtTarget').value = oActiveEl.target ;

					//GetE('txtDefinitionName').value	=  oActiveEl.attributes("title").value ;
			        GetE('isMultipart').checked = (oActiveEl.enctype=='multipart/form-data');
				} else {
			        oActiveEl = null ;
					GetE('txtName').value	= 'fm'+ new Date().getMilliseconds() ;
					GetE('txtMethod').value	= 'post' ;
		        }
				window.parent.SetOkButton( true ) ;
				window.parent.SetAutoSize( true ) ;
			}
			function setRadioValue($object, value) {
				for (var i=0; i<$object.length; i++) {
					if($object[i].value == value)
						$object[i].checked = true;
				}
			}
			function GetRadioObject($object) {
				if ($object.length == null) {
					if ($object.checked == true)
					    return $object;
					else
						return null;
			    } else {
				    for(var i=0; i<$object.length; i++) {
					    if($object[i].checked == true)
					    	return $object[i];
				    }
				    return null;
			    }
			}
			function selectNodeDefinition() {
			    var definition = CMSDialog.NodeDefinitionSelector('no-properties-scheme', null, true, false, false);
			    if(definition != null) {
			    	GetE('txtDefinitionName').value=definition.name;
			    	GetE('txtDefinitionID').value=definition.ID;
			    }
			}
			function Ok() {
				if(GetE('txtDefinitionID').value==null||GetE('txtDefinitionID').value==""){
					alert("请选择内容分类!");
					return false;
				}
				if (!oActiveEl) {
			          //插入form标签
					oActiveEl = oEditor.FCK.InsertElement( 'form' ) ;
			
					if ( oEditor.FCKBrowserInfo.IsGeckoLike )
						oEditor.FCKTools.AppendBogusBr( oActiveEl ) ;
				}
				oActiveEl.name = GetE('txtName').value ;
				SetAttribute( oActiveEl, 'action', '#' ) ;
				oActiveEl.method = GetE('txtMethod').value ;
		        //设置隐藏域，内容分类的ID
		        SetAttribute(oActiveEl, 'definition',GetE('txtDefinitionID').value);
		        SetAttribute(oActiveEl, 'definitionname',GetE('txtDefinitionName').value);
		        //node类别
		        SetAttribute(oActiveEl, 'node', GetRadioObject(document.getElementsByName("nodeType")).value);
	        	//SetAttribute(oActiveEl, 'node', GetRadioObject(nodeType).value);
		        //SetAttribute(oActiveEl, 'title', GetE('txtDefinitionName').value);
		        if(GetE('isMultipart').checked)
		              SetAttribute( oActiveEl, 'enctype', 'multipart/form-data');
		        else
		             SetAttribute( oActiveEl, 'enctype', 'application/x-www-form-urlencoded');
		        //设置隐藏域，内容ID，
		        if(oActiveEl.getAttribute("onsubmit") == null && oActiveEl.onsubmit == null && oActiveEl.getAttribute("onsubmit_fckprotectedatt") == null)
		            oActiveEl.onsubmit="return Validator.ValidateForm(this);";
		            
				if (GetE('txtTarget').value)
		          	oActiveEl.target = GetE('txtTarget').value;
				return true ;
			}
		</script>
	</head>
	<body style="overflow: hidden">
		<table width="100%" style="height: 100%">
			<tr>
				<td align="center">
					<table cellspacing="2" cellpadding="2" width="80%" border="0">
						<tr>
							<td align="right"><span fcklang="DlgFormName">Name</span></td>
							<td align="left"><input  size="40"  type="text" id="txtName" readonly="readonly" disabled="disabled"/></td>
						</tr>
						<tr>
							<td align="right"><span fcklang="DlgFormMethod">Method</span></td>
							<td align="left">
								<select id="txtMethod">
									<option value="get" selected="selected">GET</option>
									<option value="post">POST</option>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right"></td>
							<td align="left"><input type="checkbox" value="true" name="isMultipart"/>支持文件上传</td>
						</tr>
						<tr>
							<td align="right">目标</td>
							<td align="left"><input name="txtTarget" id="txtTarget"/></td>
						</tr>
						<tr>
							<td align="right"><span>关联内容分类</span></td>
							<td align="left">
								<input type="text"   id="txtDefinitionName" name="txtDefinitionName"/>
								<input type="hidden" id="txtDefinitionID" name="txtDefinitionID"/>
								<input type="button" name="btnRepository" value="选择..." onclick="selectNodeDefinition()"/>
							</td>
						</tr>
						<tr>
							<td align="right">内容来源</td>
							<td align="left">
								<table border=0 cellpadding=0 cellspacing=0>
									<tr>
										<td><input type="radio" id="b0" name="nodeType" value="" checked="true"/><label for="b0">无</label></td>
									</tr>
									<tr>
										<td><input type="radio" id="b1" name="nodeType" value="default"/><label for="b1">使用URL参数指定内容</label></td>
									</tr>
									<tr>
										<td><input type="radio" id="b2" name="nodeType" value="user"/><label for="b2">使用当前登录用户</label></td>
									</tr>
									<tr>
										<td><input type="radio" id="b3" name="nodeType" value="site"/><label for="b3">使用当前网站所属用户</label></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
