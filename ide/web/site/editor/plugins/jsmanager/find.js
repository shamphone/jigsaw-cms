var _first = true;
var _editArea = window.dialogArguments['editArea'];
var _range = _editArea.createTextRange();
var _flag;
function _CheckInput() {
	var findText = document.getElementById("findText").value;
	if (!findText) {
		alert("请输入关键字");
		return false;
	}
	return true;
}
function _Find($flag) {
	if (_CheckInput()) {
		var findText = document.getElementById("findText").value;
		var caseSensitive = document.getElementById("caseSensitive").checked ? 4 : 0;
		var wholeWord = document.getElementById("wholeWord").checked ? 2 : 0;
		if ($flag)
			_flag = $flag;
		else
			$flag = _flag;
		if (_first)
			_first = false;
		else
			_range.collapse($flag < 0);
		if (_range.findText(findText, $flag, caseSensitive + wholeWord)) {
			_range.select();
			document.getElementById("replaceButton").disabled = false;
		} else {
			alert("未找到搜索项");
			document.getElementById("replaceButton").disabled = true;
		}
	}
}
function _Replace() {
	var replaceText = document.getElementById("replaceText").value;
	_range.text = replaceText;
	_Find();
}
function _ReplaceAll() {
	if (_CheckInput()) {
		var counter = 0;
		var findText = document.getElementById("findText").value;
		var replaceText = document.getElementById("replaceText").value;
		var caseSensitive = document.getElementById("caseSensitive").checked ? 4 : 0;
		var wholeWord = document.getElementById("wholeWord").checked ? 2 : 0;
		_range.expand("textedit");
		_range.collapse();
		_range.select();
        while (_range.findText(findText, 1000000000, caseSensitive + wholeWord)){
        	_range.select();
			_range.text = replaceText;
			counter++;
        }
		alert(counter == 0 ? "未找到搜索项" : "共" + counter + "项被替换");
	}
}