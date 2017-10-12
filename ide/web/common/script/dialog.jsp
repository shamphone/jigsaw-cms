<%@ page contentType="text/javascript; charset=UTF-8" %>
var ContextPath = '<html:rewrite page="/" module=""/>';
if (window.parent != null)
		window.parent.document.title = this.document.title;
/**
 * 设置ESC为取消键，当没有选中组件时回车为确认键。
 */
document.onkeydown = function() {
	var btn;
	if (event.keyCode == 13) {
		var src = event.srcElement;
		if (!src.tagName || src.tagName.toLowerCase() != "textarea")
			btn = document.getElementById("btnOk");
	} else if (event.keyCode == 27) {
		btn = document.getElementById("btnCancel");
	}
	if (btn && !btn.disabled) {
		btn.click();
		if (event.keyCode == 13)
			return false;
	}
}
