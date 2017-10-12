<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Background Picture</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta content="noindex, nofollow" name="robots">
		<script src="../common/script/common.js" type="text/javascript"></script>
		<script src="../../dialog/common/fck_dialog_common.js" type="text/javascript"></script>
		<script src="script.js" type="text/javascript"></script>
		<style type="text/css">
			body,input,.inputFile { font-size: 8pt }
			.inputText { width: 121px; height: 18px; }
			.inputButton { width: 58px; height: 18px; font-size: 7.5pt }
			.waitPrompt {
				position: absolute;
				background-color: #3a6ea5;
				color: #ffffff;
				width: 300px;
				padding: 10px 25px;
				border: 1px solid black;
			}
		</style>
		<script language="javascript" type="text/javascript">
			var dialog   	= window.parent ;
			var oEditor   	= dialog.InnerDialogLoaded() ;
			var FCK       	= oEditor.FCK ;
			var FCKLang   	= oEditor.FCKLang ;
			var FCKDocument = FCK.EditorDocument;
			var FCKConfig	= oEditor.FCKConfig ;
			
			var _CONTEXT_PATH = "<%= request.getContextPath()%>/resources";
			var _oPreviewer;
			var _oWaitPrompt;
			var _oBtnOk;
			var _iMaxWidth = 160;
			var _iMaxHeight = 160;
			var _iProportion = _iMaxWidth / _iMaxHeight;
			
			function Ok() {
				if (document.getElementById("source1").checked) {
					var path = GetE("txtUrl").value;
					if (!path) {
						alert(FCKLang["BgpicDlgPathNull"]);
						return false;
					}
					_SetBgImg(path);
				} else {
					_SetBgImg(null);
				}
				return true;
			}
			
			window.onload = function() {
				oEditor.FCKLanguageManager.TranslatePage(document) ;
				dialog.SetAutoSize( true ) ;
				dialog.SetOkButton( true ) ;
				_AdjustDialog();
				_oPreviewer = GetE("previewer");
				_oBtnOk = GetE("btnOk");
			}
		</script>
	</head>
	<body scroll="no" style="overflow: hidden">
		<table id="tabDialogSize" cellpadding="3" cellspacing="3">
			<tr>
				<td noWrap colspan="4"><span fckLang="DlgBgpicRsc">Background Picture Resource:</span></td>
				<td><span fckLang="BgpicDlgPreview">Preview:</span></td>
			</tr>
			<tr>
				<td noWrap>
					<input type=radio id="source1" name="source" onclick="radioClick(this.value)" value="server" checked="checked">
					<label for="source1" fckLang="DlgBgpicFromServer">Server:</label>
				</td>
				<td noWrap colspan="3">
					<input type=text id="txtUrl" size=20 value='' class="inputText" /> <input id="btnServer" onclick="BrowseServer();" type="button" value="Browser..." fcklang="DlgBgpicbtnBrowserServer" class="inputButton" />
				</td>
				<td rowspan="5">
					<div style="width: 160px; height: 160px;" id="previewer"></div>
				</td>
			</tr>
			<tr>
				<td noWrap colspan="4">
					<input type=radio id="source2" name="source" onclick="radioClick(this.value)" value="cancel">
					<label for="source2" fcklang=DlgBgpicCancel>Cancel Background Picture</label>
				</td>
			</tr>
			<tr>
				<td colspan="4" height="30px">&nbsp;</td>
			</tr>
			<tr>
				<td noWrap colspan="4"><span fckLang="DlgBgpicproperties">Picture Properties</span>:</td>
			</tr>
			<tr>
				<td noWrap colspan="2"><span fckLang="DlgBgpicAttachment">Background Attachment </span>: <select name="bgpic_Attachment" style="width: 70">
					<option fcklang="DlgAttScroll" value="scroll">scroll</option>
					<option fcklang="DlgAttFixed" value="fixed">fixed</option>
				</select></td>
				<td noWrap colspan="2"><span fckLang="DlgBgpicRepeat">Background Repeat </span>: <select name="bgpic_Repeat" style="width: 80">
					<option fcklang="DlgAttNoRepeat" value="no-repeat">no-repeat</option>
					<option fcklang="DlgAttRepeat-x" value="repeat-x">repeat-x</option>
					<option fcklang="DlgAttRepeat-y" value="repeat-y">repeat-y</option>
				</select></td>
			</tr>
		</table>
	</body>
</html>
