
var sites = {};
function _OnRowClick($row) {
	if (event.srcElement.type != "checkbox")
		$row.getElementsByTagName("input")[0].click();
}
function _OnCheckboxClick($checkbox) {
	if ($checkbox.checked) {
		var chks =  document.all("listTable").tBodies[0].getElementsByTagName("input");
		for (var i=0; i<chks.length; i++) {
			if (!chks[i].checked)
				return;
		}
		document.all("chkAll").checked = true;
	} else {
		if (document.all("chkAll").checked)
			document.all("chkAll").checked = false;
	}
}
function _OnCheckboxAllClick($checkbox) {
	var chks =  document.all("listTable").tBodies[0].getElementsByTagName("input");
	for (var i=0; i<chks.length; i++)
		chks[i].checked = $checkbox.checked;
}
function GetSelectedSites() {
	var selectedSites = [];
	var chks =  document.all("listTable").tBodies[0].getElementsByTagName("input");
	for (var i=0; i<chks.length; i++) {
		if (chks[i].checked)
			selectedSites.push(sites[chks[i].value]);
	}
	return selectedSites;
}
function SetCellText($rowId, $column, $text) {
	document.getElementById("listTable").rows($rowId).cells($column).innerHTML = $text;
}
