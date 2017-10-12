<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<tiles:insert definition="dialog_frame">
	<tiles:put name="title">导入word文档</tiles:put>
	<tiles:put name="javascript">
		<script language="javascript" type="text/javascript">
			var btnOK;
			var fileWord;
			var oForm;
			var divPrompt;
			var divContent;
			var timer;
			var timerKey = new Date().getTime();
			var arrFinished = new Array(false,false,false,false,false,false,false);

			window.onload = function () {
				btnOK = document.getElementById("btnOk");
				fileWord = document.getElementById("file");
				oForm = document.getElementById("fm");
				divPrompt = document.getElementById("waitPrompt");
				divContent = document.getElementById("content");
			}

			function doWaiting() {
				divPrompt.style.display = "";
				divContent.style.display = "none";
				btnOK.disabled = true;
				timer = window.setInterval(doTracing , 1000);         
			}
			/**
			 * 跟踪执行状态
			 */
			function doTracing() {
				var url='<html:rewrite module="/cms" page="/status.do"/>?key='+timerKey+'&time='+ new Date().getTime();
				var req= new HttpRequest(url);
				var status = req.Get();         
				if(status != null && status.length > 0) {
					status = parseInt(status);
					if (arrFinished[status])
						return;
					for (var i=0; i<status; i++) {
						if (arrFinished[i])
							continue;
						var oCheckbox = document.all("impStatus")[i];
						var oLabel = oCheckbox.nextSibling;
						oCheckbox.checked = true;
						oLabel.innerText =oLabel.innerText+"完成！"; 
						arrFinished[i]= true;
					}
				}
			}
			/**
			 * 在加载成功页面调用 
			 */
			function doReady(strHTML) {
				window.returnValue=strHTML;
				if(timer)
					clearInterval(timer);
				window.close();
			}
			/**
			 * 在加载失败页面调用 
			 */
			function doFailed(msg) {
				if(timer)
					clearInterval(timer);
				alertError(msg);
			}

			function alertError(sMessage_) {
				alert(sMessage_);
				divPrompt.style.display = "none";
				divContent.style.display = "";
				btnOK.disabled = false;
			}

			function doOK() {
				if (!fileWord.value) {
					alert("请选择待上传的文件！");
					return false;
				}
				if (fileWord.value.lastIndexOf(".doc")==-1 && fileWord.value.lastIndexOf(".zip")==-1 
						&& fileWord.value.lastIndexOf(".docx")==-1 && fileWord.value.lastIndexOf(".rtf")==-1 
						&& fileWord.value.lastIndexOf(".dot")==-1) {
					alert("只接受 doc 、 docs 、rtf、dot或者  zip 文件");
					return false;
				}
				oForm.elements["key"].value = timerKey;
				doWaiting();
				oForm.submit();
				return false;
			}
			/**
			 * 关闭窗口
			 */
			function doCancel() {
				if(timer)
					clearInterval(timer);
				window.close();
			}
		</script>
	</tiles:put>
	<tiles:put name="dialog">
		<div id="content">
			<form action='<html:rewrite module="/cms" page="/importWord.do"/>' id="fm" method="post" enctype="multipart/form-data" target="hidden_frame">
				<table cellSpacing="2" cellPadding="2" width="100%" border="0" valign="top">
					<input type="hidden" name="key" />
					<tr>
						<td>请上传待导入的文件：</td>
					</tr>
					<tr>
						<td><input type="file" name="file" size="40" /></td>
					</tr>
					<!--tr>
		                <td><input type="checkbox" name="filter" value="true" id="filterCSS" checked="checked"/><label for="filterCSS">删除Office附加的标签</label></td>
		              </tr>
					<tr>
						<td><input checked="checked" type="checkbox" name="image" value="true" id="filterImage" /><label for="filterImage">转换为图片</label></td>
					</tr-->
					<tr>
						<td>支持的文档格式：</td>
					</tr>
					<tr>
						<td>
							<ol>
								<li>Word文档doc</li>
								<li>Word 2007文档docx</li>
								<li>包含上述文档类型的压缩的zip格式文件。</li>
							</ol>
						</td>
					</tr>
				</table>
				<iframe name='hidden_frame' id="hidden_frame" style='display: none'></iframe>
			</form>
		</div>
		<div id="waitPrompt" style="display: none">
			<ol>
				<li><input type="checkbox" readonly="readonly" disabled="disabled" name="impStatus" value="1" id="status1" /><label for="status1">连接到服务器...</label></li>
				<li><input type="checkbox" readonly="readonly" disabled="disabled" name="impStatus" value="2" id="status2" /><label for="status2">正在上传文件...</label></li>
				<li><input type="checkbox" readonly="readonly" disabled="disabled" name="impStatus" value="3" id="status3" /><label for="status3">正在解压缩文件...</label></li>
				<li><input type="checkbox" readonly="readonly" disabled="disabled" name="impStatus" value="4" id="status4" /><label for="status4">正在转换文件...</label></li>
				<li><input type="checkbox" readonly="readonly" disabled="disabled" name="impStatus" value="5" id="status5" /><label for="status5">正在清理格式...</label></li>
				<li><input type="checkbox" readonly="readonly" disabled="disabled" name="impStatus" value="6" id="status6" /><label for="status6">导入成功！</label></li>
			</ol>
		</div>
		<div class="operation">
			<button type="button" onclick="doOK()" id="btnOK">确定</button>
			<button type="button" onclick="doCancel()" id="btnCancel">取消</button>
		</div>
	</tiles:put>
</tiles:insert>