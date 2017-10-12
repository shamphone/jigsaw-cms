
var FCKTableHeadCommand = function() {}

FCKTableHeadCommand.prototype.Execute = function() {
	var table = FCKSelection.MoveToAncestorNode( 'TABLE' ) ;
	var tHead = table.tHead;
	if (tHead) {
		table.deleteTHead();
	} else {
		var tableMap = FCKTableHandler._CreateTableMap(table);
		tHead =  table.ownerDocument.createElement("thead");
		table.insertAdjacentElement("afterBegin",tHead);
		var row = tHead.insertRow();
		for (var i=0; i<tableMap["tBodyMap"][0].length; i++) {
			row.insertCell().innerText = "表头";
		}
	}
}
FCKTableHeadCommand.prototype.GetState = function() {
	return FCK_TRISTATE_OFF ;
}
FCKCommands.RegisterCommand( 'TableHead', new FCKTableHeadCommand() ) ;


var FCKTableFootCommand = function() {}

FCKTableFootCommand.prototype.Execute = function() {
	var table = FCKSelection.MoveToAncestorNode( 'TABLE' ) ;
	var tFoot = table.tFoot;
	if (tFoot) {
		table.deleteTFoot();
	} else {
		var tableMap = FCKTableHandler._CreateTableMap(table);
		tFoot =  table.ownerDocument.createElement("tfoot");
		table.appendChild(tFoot);
		var row = tFoot.insertRow();
		for (var i=0; i<tableMap["tBodyMap"][0].length; i++) {
			row.insertCell().innerText = "表尾";
		}
	}
}

FCKTableFootCommand.prototype.GetState = function() {
	return FCK_TRISTATE_OFF ;
}
FCKCommands.RegisterCommand( 'TableFoot', new FCKTableFootCommand() ) ;

