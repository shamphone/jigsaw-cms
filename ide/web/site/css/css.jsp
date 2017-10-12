<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%
/** 样式选择器。针对指定页面编辑该页面上的样式。用户可以修改当前样式文件，也可以添加、删除样式文件
  *  输入：
  *     1. styleSheets：数组，页面上当前的样式列表
  *		2. templateID：当前网站模板ID
  *     3. selector: 当前选中的选择器，可以为空。如果提供，则高亮这个选择器。
  *	 输出：
  *     1. 选中的选择器。
  **/
%>
<tiles:insert definition="dialog_frame">
	<tiles:put name="title">选择样式</tiles:put>
	<tiles:put name="javascript">
		<script language="Javascript" type="text/Javascript" src="style.js.jsp"></script>
		<script language="JavaScript" type="text/javascript">
			var hasChanged = false;
			var fileIndex = 0;
			function doSelected() {
				var option = selRules.options[selRules.selectedIndex]; 
				var cssText = option.getAttribute("css") ? option.getAttribute("css").toString() : option.getAttribute("originalCss").toString();
				oCode.value = cssText;
				oCode.disabled = false;
				oPrev.style.cssText = cssText;
			}
			function _HasChanged() {
				if (hasChanged)
					return true;
				var options = selRules.options;
				for (var i=0; i<options.length; i++) {
					if (options[i].css && !_CompareStyle(options[i].css, options[i].originalCss))
						return true;
				}
				return false;
			}
			function _CompareStyle($src, $dest) {
				if (!($src instanceof Style) || !($dest instanceof Style))
					return false;
				for (var e in $src) {
					if (typeof($src[e]) != "function" && $src[e] != $dest[e])
						return false;
				}
				return true;
			}
			function doSave() {
				if(document.getElementById("cssFiles").options.length == 0){
					alert("保存失败，请在页面属性中添加脚本文件");
					return false;
				}else{
					var cssPath = document.getElementById("cssFiles").options[fileIndex].text; 
					var options = document.getElementById("selRules").options;
					var cssContent = [];
					for(var i=0; i<options.length; i++)
						cssContent.push(options[i].value + " {" + (options[i].css ? options[i].css.toString() : options[i].originalCss.toString()) + "}");
					var params = [];
					params.push("cssPath=" + encodeURIComponent(cssPath));
					params.push("timestamp=" + new Date().getTime());
					var url = '<html:rewrite module="/site" page="/css/saveCSSDlg.do"/>?' + params.join("&");
					var request = getXMLHttpRequest();
					request.open("post", url, false);
				    //request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
					request.send(cssContent.join("\n"));
				    if (request.status != 200 || !request.responseText) {
				    	alert("保存失败");
				    	return;
				    }
			       	alert("保存成功！");
					for (var i=0; i<options.length; i++) {
						if (options[i].css) {
							options[i].originalCss = options[i].css;
							options[i].css = null;
						}
					}
					hasChanged = false;
					var sheet = window.top.dialogArguments.styleSheets[fileIndex];
			       	sheet.href = cssPath + "?timestamp=" + new Date().getTime();
			       	return true;
				}
			}
			function doOk() {
				var selectItems = getSelectedItems(document.getElementById("selRules"));
				if (!selectItems) {
					alert("请选择样式");
					return;
				}
				if (_HasChanged()) {
					if(confirm("Css文件已改变，是否保存"))
						var savesucess = doSave();
					if(!savesucess){
						window.close();
						return;
					}
				}
				var ret = [];
				var style;
				for (var i=0; i<selectItems.length; i++) {
					style = selectItems[i].value;
					var pos=style.lastIndexOf(".");
					style=style.substring(pos+1, style.length);
					var reg = new RegExp("\\W");
					pos = style.search(reg);
					if(pos > 0)
					style=style.substring(0, pos);
					ret.push(style);
				}
				window.returnValue = ret.join(" ");
				window.close();
			}
			function doClean() {
				if (_HasChanged()) {
					if(confirm("Css文件已改变，是否保存"))
						doSave();
				}
				window.returnValue = ":none";
				window.close();
			}
			function doCancel() {
				if (_HasChanged()) {
					if(confirm("Css文件已改变，是否保存"))
						doSave();
				}
				window.close();
			}
			function _ChangeFile($index) {
				if (_HasChanged()) {
					if(confirm("Css文件已改变，是否保存"))
						doSave();
				}
				hasChanged = false;
				fileIndex = $index;
				oCode.value = "";
				oCode.disabled = true;
				oPrev.style.cssText = "";
				selRules.options.length = 0;
				var sheet = window.top.dialogArguments.styleSheets[$index];
				var rules;
				if(document.all){
					rules = sheet.rules;
				}else{
					rules = sheet.cssRules;
				}
				var style, option, Scripting;
				for(var i=0; i<rules.length; i++) {
					style = new Style();
					
					for (var e in style) {
						scripting = _HtmlCssToScriptingCss(e);
						if (rules[i].style[scripting]){
							style[e] = rules[i].style[scripting];
						}
						
					}
					var arr = new Array();
					for(var m in style){
						if(style[m] && typeof(style[m]) != "function"){
							arr.push(m+":"+style[m]);
						}
					}
					option = document.createElement("option");
					if(document.all){
						option.value = option.text = rules[i].selectorText;
						option.originalCss = style;
						selRules.options.add(option);
					}else{
						option.value = option.textContent = rules[i].selectorText;
						option.setAttribute("originalCss",arr.join(";"));
						selRules.add(option,null);
					}
				}
			}
			function _HtmlCssToScriptingCss($str) {
				if ($str.indexOf("-") == -1)
					return $str;
				var arr = $str.split("-");
				for (var i=1; i<arr.length; i++) {
					arr[i] = arr[i].substr(0, 1).toUpperCase() + arr[i].substr(1);
				}
				return arr.join("");
			}
		    function doCreate($defaultSelector){
			    var args;
			    if ($defaultSelector)
			    	args = $defaultSelector;
		        var selector = showModalDialog("selector.jsp", args, "dialogWidth:250px;dialogHeight:110px;help:no;scrollbars:yes;status:no");
		        if (selector) {
		        	selRules.value = null;
		            var option = document.createElement("option");
		            option.originalCss = new Style();
		            option.selected = true;
		            if(document.all){
		            	selRules.add(option);
		            	option.text = option.value = selector;
		            }else{
		            	option.textContent = option.value = selector;
		            	selRules.add(option,null);
		            }
		            doSelected();
					hasChanged = true;
					doEdit();
		        }
		    }
			function doEdit() {
				if (selRules.selectedIndex == -1) {
					alert("请选择css");
					return;
				}
				var args = {};
				var currentPath=window.top.dialogArguments.path;
		    	var template = currentPath.split("/");
		    	args.templateId = template.length>=2 ? template[2] : null;
				
				var option = selRules.options[selRules.selectedIndex];
				if(document.all){
					args.css = option.css ? option.css : option.originalCss; 	
				}else{
					args.css = option.getAttribute("css") ? option.getAttribute("css") : option.getAttribute("originalCss"); 
				}
				
				var ret = showModalDialog("ruleDlg.jsp", args, "dialogWidth:360px;dialogHeight:350px;help:no;scrollbars:yes;status:no");
				if (ret) {
					var dest = new Style();
					_CloneStyle(ret, dest);
					option.setAttribute("css",dest);
		            doSelected();
				}
			}
			function _CloneStyle($src, $dest) {
				for (var e in $src) {
					if ($src[e])
						$dest[e] = $src[e];
				}
			}
			function doUpdate(){
				var option = selRules.options[selRules.selectedIndex];
				var code = document.getElementById("oCode").value.toLowerCase();
				code = code.replace(/\s+/gm, "");
				var style = new Style();
				var arr = code.split(";");
				var pos, selectorText, value;
				for (var i=0; i<arr.length; i++) {
					pos = arr[i].indexOf(":");
					if (pos > 0 && pos < arr[i].length - 1) {
						styleName = arr[i].substr(0, pos);
						styleValue= arr[i].substr(pos + 1);
						if (style[styleName] != null)
							style[styleName] = styleValue;
					}
				}
				option.css = style;
		        oPrev.style.cssText = style.toString();
			}
			function doDisplay() {
				if (event.ctrlKey)
			        oPrev.style.cssText = document.getElementById("oCode").value;
			}
			function doDelete() {
				if (selRules.selectedIndex == -1) {
					alert('请选择css');
					return;
				}
				selRules.remove(selRules.selectedIndex);
				oCode.value = oPrev.style.cssText = "";
				hasChanged = true;
			}
			function doInput($value) {
				if ($value) {
					var option = findSelectItem(selRules, $value);
					if (!option) {
						if(confirm("该css类未找到，是否创建？"))
							doCreate($value);
						return;
					}
					selRules.value = $value;
					doSelected();
				}
			}
			window.onload = function() {
				var styleSheets = window.top.dialogArguments.styleSheets;
				if(styleSheets) {
					var href, pos;
					for(var i=0; i<styleSheets.length; i++) {
						href = styleSheets[i].href;
						if (href) {
							pos = href.indexOf("?");
							if (pos > 0)
								href = href.substr(0, pos);
							cssFiles.options[cssFiles.options.length] = new Option(href, i);
						}
					}
				}
				if (cssFiles.options.length > 0){
					_ChangeFile(cssFiles.options[0].value);
				}else{
					
				}
			}
      	</script>
	</tiles:put>
	<tiles:put name="dialog">
		<div id="DLGToolbar"><span onclick="doCreate()"><html:img page="/images/css16.gif" module="/common" alt="添加样式" border="0" /><span>添加</span></span> <span onclick="doEdit()"><html:img page="/images/editcss16.gif" module="/common" alt="修改样式" border="0" /><span>修改</span></span> <span onclick="doSave()"><html:img page="/images/save2web.gif" module="/common" alt="保存样式" border="0" /><span>保存</span></span> <span onclick="doDelete()"><html:img page="/images/delete.png" module="/common" alt="删除样式" border="0" /><span>删除</span></span> <span class="seperator"></span></div>
		<table width="100%" cellspacing="3" cellpadding="1" border="0">
			<tr>
				<td width="160px">输入名称或从列表中选择</td>
				<td align="left">显示样式来源</td>
			</tr>
			<tr>
				<td><input type="text" id="keywords" name="keywords" onchange="doInput(this.value)" size="12" style="width: 160px" /></td>
				<td align="left"><select id="cssFiles" style="width: 280px;" onchange="_ChangeFile(this.value)"></select></td>
			</tr>
			<tr>
				<td rowspan="3">
					<select multiple="multiple" size="20" id="selRules" style="width: 160px" onchange="doSelected()" />
				</td>
				<td>
					<div style="color:#ff0000;">注意：空的样式保存后会被自动清除</div>
					<textarea id="oCode" style="width: 280px; height: 133px;" onchange="doUpdate()" disabled="true" onkeydown="doDisplay()" title="按ctrl健预览"></textarea>
				</td>
			</tr>
			<tr>
				<td>预览</td>
			</tr>
			<tr>
				<td>
				<div style='color: #c0c0c0; background-color: white; border: 2px inset; height: 150px; width: 280px; padding: 2px 2px 2px 2px; overflow: hidden'>Coolink协同平台不是一个完成单一功能的业务系统。
				<div id="oPrev">每个实施机构能够根据应用领域的工作方式建立符合业务流程的协同工作全面解决方案。</div>
				为此，Coolink协同工作支撑平台必须是高度可定制、可扩展、可重用的，并提供一个可描述的框架，支持实施人员能够在平台上重建甚至优化公司的协同流程。</div>
				</td>
			</tr>
		</table>
		</td>
		</tr>
		</table>
		<div class="operation"><!-- button type="button" onclick="doSelectCSS()" style="margin-right:130px">管理页面样式文件...</button-->
		<button type="button" onclick="doOk()" title="使用选中样式">确定</button>
		<button type="button" onclick="doClean()" title="取消已使用的样式">清除</button>
		<button type="button" onclick="doCancel()" title="关闭对话框">取消</button>
		</div>
	</tiles:put>
</tiles:insert>
