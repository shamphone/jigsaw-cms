FCKConfig.EditorAreaCSS = '/ide/site/editor/tableXrepeaterEditor.css' ;

FCKConfig.Plugins.Add( 'table') ;

FCKTableHandler._ReplaceCellsByMarker = function( tableMap, marker, substitute )
{	
	var allRows = tableMap["allRows"];
	for ( var i = 0 ; i < allRows.length ; i++ )
	{
		for ( var j = 0 ; j < allRows[i].length ; j++ )
		{
			if ( allRows[i][j][marker] )
				allRows[i][j] = substitute ;
		}
	}
}

FCKTableHandler._GetMarkerGeometry = function( tableMap, rowIdx, colIdx, markerName )
{
	var selectionWidth = 0 ;
	var selectionHeight = 0 ;
	var cellsLeft = 0 ;
	var cellsUp = 0 ;
	var allRows = tableMap["allRows"];
	for ( var i = colIdx ; allRows[rowIdx][i] && allRows[rowIdx][i][markerName] ; i++ )
		selectionWidth++ ;
	for ( var i = colIdx - 1 ; allRows[rowIdx][i] && allRows[rowIdx][i][markerName] ; i-- )
	{
		selectionWidth++ ;
		cellsLeft++ ;
	}
	for ( var i = rowIdx ; allRows[i] && allRows[i][colIdx] && allRows[i][colIdx][markerName] ; i++ )
		selectionHeight++ ;
	for ( var i = rowIdx - 1 ; allRows[i] && allRows[i][colIdx] && allRows[i][colIdx][markerName] ; i-- )
	{
		selectionHeight++ ;
		cellsUp++ ;
	}
	return { 'width' : selectionWidth, 'height' : selectionHeight, 'x' : cellsLeft, 'y' : cellsUp } ;
}

FCKTableHandler.MergeRight = function()
{
	var target = this.GetMergeRightTarget() ;
	if ( target == null )
		return ;
	var refCell = target.refCell ;
	var tableMap = target.tableMap ;
	var nextCell = target.nextCell ; 

	var cellContents = FCK.EditorDocument.createDocumentFragment() ;
	while ( nextCell && nextCell.childNodes && nextCell.childNodes.length > 0 )
		cellContents.appendChild( nextCell.removeChild( nextCell.firstChild ) ) ;

	nextCell.parentNode.removeChild( nextCell ) ;
	refCell.appendChild( cellContents ) ;
	this._MarkCells( [nextCell], '_Replace' ) ;
	this._ReplaceCellsByMarker( tableMap, '_Replace', refCell ) ;
	this._InstallTableMap( tableMap, FCKTools.GetElementAscensor(refCell, "TABLE") ) ;

	this._MoveCaretToCell( refCell, false ) ;
}

FCKTableHandler.MergeDown = function()
{
	var target = this.GetMergeDownTarget() ;
	if ( target == null )
		return ;
	var refCell = target.refCell ;
	var tableMap = target.tableMap ;
	var nextCell = target.nextCell ;

	var cellContents = refCell.ownerDocument.createDocumentFragment() ;
	while ( nextCell && nextCell.childNodes && nextCell.childNodes.length > 0 )
		cellContents.appendChild( nextCell.removeChild( nextCell.firstChild ) ) ;
	if ( cellContents.firstChild )
		cellContents.insertBefore( nextCell.ownerDocument.createElement( 'br' ), cellContents.firstChild ) ;
	refCell.appendChild( cellContents ) ;
	this._MarkCells( [nextCell], '_Replace' ) ;
	this._ReplaceCellsByMarker( tableMap, '_Replace', refCell ) ;
	this._InstallTableMap( tableMap, FCKTools.GetElementAscensor(refCell, "TABLE") ) ;

	this._MoveCaretToCell( refCell, false ) ;
}

FCKTableHandler.HorizontalSplitCell = function()
{
	var cells = this.GetSelectedCells() ;
	if ( cells.length != 1 )
		return ;

	var refCell = cells[0] ;
	var tableMap = this._CreateTableMap( FCKTools.GetElementAscensor(refCell, "TABLE") ) ;
	var rowIdx = refCell.parentNode.rowIndex ;
	var colIdx = this._GetCellIndexSpan( tableMap, rowIdx, refCell ) ;
	var cellSpan = isNaN( refCell.colSpan ) ? 1 : refCell.colSpan ;

	if ( cellSpan > 1 )
	{
		// Splittng a multi-column cell - original cell gets ceil(colSpan/2) columns,
		// new cell gets floor(colSpan/2).
		var newCellSpan = Math.ceil( cellSpan / 2 ) ;
		var newCell = refCell.ownerDocument.createElement( 'td' ) ;
		if ( FCKBrowserInfo.IsGeckoLike )
			FCKTools.AppendBogusBr( newCell ) ;
		var startIdx = colIdx + newCellSpan ;
		var endIdx = colIdx + cellSpan ;
		var rowSpan = isNaN( refCell.rowSpan ) ? 1 : refCell.rowSpan ;
		for ( var r = rowIdx ; r < rowIdx + rowSpan ; r++ )
		{
			for ( var i = startIdx ; i < endIdx ; i++ )
				tableMap.allRows[r][i] = newCell ;
		}
	}
	else
	{
		// Splitting a single-column cell - add a new cell, and expand 
		// cells crossing the same column.
		var newTableMap = {} ;
		var allRows = [];
		if (tableMap["tHeadMap"]) {
			newTableMap["tHeadMap"] = [];
			for (var i=0; i<tableMap["tHeadMap"].length ; i++ ) {
				var newRow = tableMap["tHeadMap"][i].slice( 0, colIdx ) ;
				if ( tableMap["tHeadMap"][i].length <= colIdx )
				{
					newTableMap["tHeadMap"].push( newRow ) ;
					continue ;
				}
				if ( tableMap["tHeadMap"][i][colIdx] == refCell )
				{
					newRow.push( refCell ) ;
					newRow.push( refCell.ownerDocument.createElement( 'td' ) ) ;
					if ( FCKBrowserInfo.IsGeckoLike )
						FCKTools.AppendBogusBr( newRow[newRow.length - 1] ) ;
				}
				else
				{
					newRow.push( tableMap["tHeadMap"][i][colIdx] ) ;
					newRow.push( tableMap["tHeadMap"][i][colIdx] ) ;
				}
				for ( var j = colIdx + 1 ; j < tableMap["tHeadMap"][i].length ; j++ )
					newRow.push( tableMap["tHeadMap"][i][j] ) ;
				newTableMap["tHeadMap"].push( newRow ) ;
			}
			allRows = allRows.concat(newTableMap["tHeadMap"]);
		}
		
		newTableMap["tBodyMap"] = [];
		for (var i=0; i<tableMap["tBodyMap"].length ; i++ ) {
			var newRow = tableMap["tBodyMap"][i].slice( 0, colIdx ) ;
			if ( tableMap["tBodyMap"][i].length <= colIdx )
			{
				newTableMap["tBodyMap"].push( newRow ) ;
				continue ;
			}
			if ( tableMap["tBodyMap"][i][colIdx] == refCell )
			{
				newRow.push( refCell ) ;
				newRow.push( refCell.ownerDocument.createElement( 'td' ) ) ;
				if ( FCKBrowserInfo.IsGeckoLike )
					FCKTools.AppendBogusBr( newRow[newRow.length - 1] ) ;
			}
			else
			{
				newRow.push( tableMap["tBodyMap"][i][colIdx] ) ;
				newRow.push( tableMap["tBodyMap"][i][colIdx] ) ;
			}
			for ( var j = colIdx + 1 ; j < tableMap["tBodyMap"][i].length ; j++ )
				newRow.push( tableMap["tBodyMap"][i][j] ) ;
			newTableMap["tBodyMap"].push( newRow ) ;
		}
		allRows = allRows.concat(newTableMap["tBodyMap"]);
		
		if (tableMap["tFootMap"]) {
			newTableMap["tFootMap"] = [];
			for (var i=0; i<tableMap["tFootMap"].length ; i++ ) {
				var newRow = tableMap["tFootMap"][i].slice( 0, colIdx ) ;
				if ( tableMap["tFootMap"][i].length <= colIdx )
				{
					newTableMap["tFootMap"].push( newRow ) ;
					continue ;
				}
				if ( tableMap["tFootMap"][i][colIdx] == refCell )
				{
					newRow.push( refCell ) ;
					newRow.push( refCell.ownerDocument.createElement( 'td' ) ) ;
					if ( FCKBrowserInfo.IsGeckoLike )
						FCKTools.AppendBogusBr( newRow[newRow.length - 1] ) ;
				}
				else
				{
					newRow.push( tableMap["tFootMap"][i][colIdx] ) ;
					newRow.push( tableMap["tFootMap"][i][colIdx] ) ;
				}
				for ( var j = colIdx + 1 ; j < tableMap["tFootMap"][i].length ; j++ )
					newRow.push( tableMap["tFootMap"][i][j] ) ;
				newTableMap["tFootMap"].push( newRow ) ;
			}
			allRows = allRows.concat(newTableMap["tFootMap"]);
		}
		
		newTableMap["allRows"] = allRows;
		tableMap = newTableMap ;
	}

	this._InstallTableMap( tableMap, FCKTools.GetElementAscensor(refCell, "TABLE") ) ;
}

FCKTableHandler.VerticalSplitCell = function()
{
	var cells = this.GetSelectedCells() ;
	if ( cells.length != 1 )
		return ;

	var currentCell = cells[0] ;
	var tableMap = this._CreateTableMap( FCKTools.GetElementAscensor(currentCell, "TABLE") ) ;
	var cellIndex = this._GetCellIndexSpan( tableMap, currentCell.parentNode.rowIndex, currentCell ) ;
	var currentRowSpan = currentCell.rowSpan ;
	var currentRowIndex = currentCell.parentNode.rowIndex ;
	var currentSectionRowIndex = currentCell.parentNode.sectionRowIndex ;
	if ( isNaN( currentRowSpan ) )
		currentRowSpan = 1 ;
	var rows;
	switch (currentCell.parentNode.parentNode.tagName) {
		case "THEAD":
			rows = tableMap["tHeadMap"];
			break;
		case "TFOOT":
			rows = tableMap["tFootMap"];
			break;
		default :
			rows = tableMap["allRows"];
	}

	if ( currentRowSpan > 1 )
	{
		// 1. Set the current cell's rowSpan to 1.
		currentCell.rowSpan = Math.ceil( currentRowSpan / 2 ) ;

		// 2. Find the appropriate place to insert a new cell at the next row.
		var newCellRowIndex = currentRowIndex + Math.ceil( currentRowSpan / 2 ) ;
		var insertMarker = null ;
		for ( var i = cellIndex+1 ; i < tableMap["allRows"][newCellRowIndex].length ; i++ )
		{
			if ( tableMap["allRows"][newCellRowIndex][i].parentNode.rowIndex == newCellRowIndex )
			{
				insertMarker = tableMap["allRows"][newCellRowIndex][i] ;
				break ;
			}
		}

		// 3. Insert the new cell to the indicated place, with the appropriate rowSpan, next row.
		var newCell = FCK.EditorDocument.createElement( 'td' ) ;
		newCell.rowSpan = Math.floor( currentRowSpan / 2 ) ;
		if ( FCKBrowserInfo.IsGeckoLike )
			FCKTools.AppendBogusBr( newCell ) ;
		FCKTools.GetElementAscensor(currentCell, "TABLE").rows[newCellRowIndex].insertBefore( newCell, insertMarker ) ;
	}
	else
	{
		// 1. Insert a new row.
		var newCellSectionRowIndex = currentSectionRowIndex + 1 ;
		var newRow = FCK.EditorDocument.createElement( 'tr' ) ;
		currentCell.parentNode.parentNode.rows[currentSectionRowIndex].insertAdjacentElement("afterEnd", newRow);
		//currentCell.parentNode.parentNode.insertBefore( newRow, currentCell.parentNode.parentNode.rows[currentSectionRowIndex] ) ;
		
		// 2. +1 to rowSpan for all cells crossing currentCell's row.
		for ( var i = 0 ; i < tableMap["allRows"][currentRowIndex].length ; )
		{
			var colSpan = tableMap["allRows"][currentRowIndex][i].colSpan ;
			if ( isNaN( colSpan ) || colSpan < 1 )
				colSpan = 1 ;
			if ( i == cellIndex )
			{
				i += colSpan ;
				continue ;
			}
			var rowSpan = tableMap["allRows"][currentRowIndex][i].rowSpan ;
			if ( isNaN( rowSpan ) )
				rowSpan = 1 ;
			tableMap["allRows"][currentRowIndex][i].rowSpan = rowSpan + 1 ;
			i += colSpan ;
		}

		// 3. Insert a new cell to new row.
		var newCell = FCK.EditorDocument.createElement( 'td' ) 
		if ( FCKBrowserInfo.IsGeckoLike )
			FCKTools.AppendBogusBr( newCell	) ;
		newRow.appendChild( newCell ) ;
	}
}

// Get the cell index from a TableMap.
FCKTableHandler._GetCellIndexSpan = function( tableMap, rowIndex, cell )
{
	var allRows = tableMap["allRows"];
	if ( allRows.length < rowIndex + 1 )
		return null ;

	var oRow = allRows[ rowIndex ] ;

	for ( var c = 0 ; c < oRow.length ; c++ )
	{
		if ( oRow[c] == cell )
			return c ;
	}

	return null ;
}

// Get the cell location from a TableMap. Returns an array with an [x,y] location
FCKTableHandler._GetCellLocation = function( tableMap, cell  )
{
	var allRows = tableMap["allRows"];
	for ( var i = 0 ; i < allRows.length; i++ )
	{
		for ( var c = 0 ; c < allRows[i].length ; c++  )
		{
			if ( allRows[i][c] == cell  ) return [i,c];
		}
	}
	return null ;
}

// Get the cells available in a column of a TableMap.
FCKTableHandler._GetColumnCells = function( tableMap, columnIndex )
{
	var aCollCells = new Array() ;
	var allRows = tableMap["allRows"];

	for ( var r = 0 ; r < allRows.length ; r++ )
	{
		var oCell = allRows[r][columnIndex] ;
		if ( oCell && ( aCollCells.length == 0 || aCollCells[ aCollCells.length - 1 ] != oCell ) )
			aCollCells[ aCollCells.length ] = oCell ;
	}

	return aCollCells ;
}

// This function is quite hard to explain. It creates a matrix representing all cells in a table.
// The difference here is that the "spanned" cells (colSpan and rowSpan) are duplicated on the matrix
// cells that are "spanned". For example, a row with 3 cells where the second cell has colSpan=2 and rowSpan=3
// will produce a bi-dimensional matrix with the following values (representing the cells):
//		Cell1, Cell2, Cell2, Cell 3
//		Cell4, Cell2, Cell2, Cell 5
FCKTableHandler._CreateTableMap = function( table )
{
	var tableMap = {};
	var allRows = []; 
	var tHead = table.tHead;
	var tFoot = table.tFoot;
	var tBody = table.tBodies[0];
	
	if (tHead) {
		tableMap["tHeadMap"] = this._StuffMap(tHead.rows);
		allRows = allRows.concat(tableMap["tHeadMap"]);
	}
	
	var tBodyRows = [];
	for (var i=0; i<table.tBodies.length; i++) {
		for (var j=0; j<table.tBodies[i].rows.length; j++)
			tBodyRows.push(table.tBodies[i].rows[j]);
	}
	tableMap["tBodyMap"] = this._StuffMap(tBodyRows);
	allRows = allRows.concat(tableMap["tBodyMap"]);
	
	if (tFoot) {
		tableMap["tFootMap"] = this._StuffMap(tFoot.rows);
		allRows = allRows.concat(tableMap["tFootMap"]);
	}
	tableMap['allRows'] = allRows;
	return tableMap ;
}

FCKTableHandler._StuffMap = function( aRows )
{
	// Row and Column counters.
	var r = -1 ;

	var aMap = new Array() ;

	for ( var i = 0 ; i < aRows.length ; i++ )
	{
		r++ ;
		if ( !aMap[r] )
			aMap[r] = new Array() ;

		var c = -1 ;

		for ( var j = 0 ; j < aRows[i].cells.length ; j++ )
		{
			var oCell = aRows[i].cells[j] ;

			c++ ;
			while ( aMap[r][c] )
				c++ ;

			var iColSpan = isNaN( oCell.colSpan ) ? 1 : oCell.colSpan ;
			var iRowSpan = isNaN( oCell.rowSpan ) ? 1 : oCell.rowSpan ;

			for ( var rs = 0 ; rs < iRowSpan ; rs++ )
			{
				if ( !aMap[r + rs] )
					aMap[r + rs] = new Array() ;

				for ( var cs = 0 ; cs < iColSpan ; cs++ )
				{
					aMap[r + rs][c + cs] = aRows[i].cells[j] ;
				}
			}

			c += iColSpan - 1 ;
		}
	}
	return aMap;
}

// This function is the inverse of _CreateTableMap - it takes in a table map and converts it to an HTML table.
FCKTableHandler._InstallTableMap = function( tableMap, table )
{
	// Clear the table of all rows first.
	while ( table.rows.length > 0 )
	{
		var row = table.rows[0] ;
		row.parentNode.removeChild( row ) ;
	}

	var allRows = tableMap["allRows"];
	// Disconnect all the cells in tableMap from their parents, set all colSpan and rowSpan attributes to 1.
	for ( var i = 0 ; i < allRows.length ; i++ )
	{
		for ( var j = 0 ; j < allRows[i].length ; j++ )
		{
			var cell = allRows[i][j] ;
			if ( cell.parentNode )
				cell.parentNode.removeChild( cell ) ;
			cell.colSpan = cell.rowSpan = 1 ;
		}
	}

	// Scan by rows and set colSpan.
	var maxCol = 0 ;
	for ( var i = 0 ; i < allRows.length ; i++ )
	{
		for ( var j = 0 ; j < allRows[i].length ; j++ )
		{
			var cell = allRows[i][j] ;
			if ( ! cell)
				continue ;
			if ( j > maxCol )
				maxCol = j ;
			if ( cell._colScanned === true )
				continue ;
			if ( allRows[i][j-1] == cell )
				cell.colSpan++ ;
				
			if ( allRows[i][j+1] != cell )
				cell._colScanned = true ;
		}
	}

	// Scan by columns and set rowSpan.
	for ( var i = 0 ; i <= maxCol ; i++ )
	{
		for ( var j = 0 ; j < allRows.length ; j++ )
		{
			if ( ! allRows[j] )
				continue ;
			var cell = allRows[j][i] ;
			if ( ! cell || cell._rowScanned === true )
				continue ;
			if ( allRows[j-1] && allRows[j-1][i] == cell )
				cell.rowSpan++ ;
			if ( ! allRows[j+1] || allRows[j+1][i] != cell )
				cell._rowScanned = true ;
		}
	}

	// Clear all temporary flags.
	for ( var i = 0 ; i < allRows.length ; i++ )
	{
		for ( var j = 0 ; j < allRows[i].length ; j++)
		{
			var cell = allRows[i][j] ;
			if ( FCKBrowserInfo.IsIE )
			{
				cell.removeAttribute( '_colScanned' ) ;
				cell.removeAttribute( '_rowScanned' ) ;
			}
			else
			{
				delete cell._colScanned ;
				delete cell._rowScanned ;
			}
		}
	}

	// Insert physical rows and columns to the table.
	if (tableMap["tHeadMap"]) {
		var tHead = table["tHead"];
		for ( var i = 0 ; i < tableMap["tHeadMap"].length ; i++ )
		{
			var rowObj = tHead.insertRow();
			for ( var j = 0 ; j < tableMap["tHeadMap"][i].length ; )
			{
				var cell = tableMap["tHeadMap"][i][j] ;
				if ( tableMap["tHeadMap"][i-1] && tableMap["tHeadMap"][i-1][j] == cell )
				{
					j += cell.colSpan ;
					continue ;
				}
				rowObj.appendChild( cell ) ;
				j += cell.colSpan ;
				if ( cell.colSpan == 1 )
					cell.removeAttribute( 'colspan' ) ;
				if ( cell.rowSpan == 1 )
					cell.removeAttribute( 'rowspan' ) ;
			}
		}
	}
	
	var tBody = table.tBodies[0];
	for ( var i = 0 ; i < tableMap["tBodyMap"].length ; i++ )
	{
		var rowObj = tBody.insertRow();
		for ( var j = 0 ; j < tableMap["tBodyMap"][i].length ; )
		{
			var cell = tableMap["tBodyMap"][i][j] ;
			if ( tableMap["tBodyMap"][i-1] && tableMap["tBodyMap"][i-1][j] == cell )
			{
				j += cell.colSpan ;
				continue ;
			}
			rowObj.appendChild( cell ) ;
			j += cell.colSpan ;
			if ( cell.colSpan == 1 )
				cell.removeAttribute( 'colspan' ) ;
			if ( cell.rowSpan == 1 )
				cell.removeAttribute( 'rowspan' ) ;
		}
	}
	
	if (tableMap["tFootMap"]) {
		var tFoot = table["tFoot"];
		for ( var i = 0 ; i < tableMap["tFootMap"].length ; i++ )
		{
			var rowObj = tFoot.insertRow();
			for ( var j = 0 ; j < tableMap["tFootMap"][i].length ; )
			{
				var cell = tableMap["tFootMap"][i][j] ;
				if ( tableMap["tFootMap"][i-1] && tableMap["tFootMap"][i-1][j] == cell )
				{
					j += cell.colSpan ;
					continue ;
				}
				rowObj.appendChild( cell ) ;
				j += cell.colSpan ;
				if ( cell.colSpan == 1 )
					cell.removeAttribute( 'colspan' ) ;
				if ( cell.rowSpan == 1 )
					cell.removeAttribute( 'rowspan' ) ;
			}
		}
	}
}

FCKTableHandler.GetMergeRightTarget = function()
{
	var cells = this.GetSelectedCells() ;
	if ( cells.length != 1 )
		return null ;

	var refCell = cells[0] ;
	var tableMap = this._CreateTableMap( FCKTools.GetElementAscensor(refCell, "TABLE") ) ;
	var rowIdx = refCell.parentNode.rowIndex ;
	var colIdx = this._GetCellIndexSpan( tableMap, rowIdx, refCell ) ;
	var nextColIdx = colIdx + ( isNaN( refCell.colSpan ) ? 1 : refCell.colSpan ) ;
	var nextCell = tableMap.allRows[rowIdx][nextColIdx] ;

	if ( ! nextCell )
		return null ;

	// The two cells must have the same vertical geometry, otherwise merging does not make sense.
	this._MarkCells( [refCell, nextCell], '_SizeTest' ) ;
	var refGeometry = this._GetMarkerGeometry( tableMap, rowIdx, colIdx, '_SizeTest' ) ;
	var nextGeometry = this._GetMarkerGeometry( tableMap, rowIdx, nextColIdx, '_SizeTest' ) ;
	this._UnmarkCells( [refCell, nextCell], '_SizeTest' ) ;

	if ( refGeometry.height != nextGeometry.height || refGeometry.y != nextGeometry.y )
		return null ;

	return { 'refCell' : refCell, 'nextCell' : nextCell, 'tableMap' : tableMap } ;
}

FCKTableHandler.GetMergeDownTarget = function()
{
	var cells = this.GetSelectedCells() ;
	if ( cells.length != 1 )
		return null ;

	var refCell = cells[0] ;
	var tableMap = this._CreateTableMap( FCKTools.GetElementAscensor(refCell, "TABLE") ) ;
	var rowIdx = refCell.parentNode.rowIndex ;
	var sectionRowIdx = refCell.parentNode.sectionRowIndex ;
	var colIdx = this._GetCellIndexSpan( tableMap, rowIdx, refCell ) ;
	var newRowIdx = rowIdx + ( isNaN( refCell.rowSpan ) ? 1 : refCell.rowSpan ) ;
	var newSectionRowIdx = sectionRowIdx + ( isNaN( refCell.rowSpan ) ? 1 : refCell.rowSpan ) ;
	
	var rows;
	switch (refCell.parentNode.parentNode.tagName) {
		case "THEAD":
			rows = tableMap["tHeadMap"];
			break;
		case "TFOOT":
			rows = tableMap["tFootMap"];
			break;
		default :
			rows = tableMap["tBodyMap"];
	}
	
	if ( ! rows[newSectionRowIdx] )
		return null ;

	var allRows = tableMap["allRows"];
	var nextCell = allRows[newRowIdx][colIdx] ;

	if ( ! nextCell )
		return null ;

	// The two cells must have the same horizontal geometry, otherwise merging does not makes sense.
	this._MarkCells( [refCell, nextCell], '_SizeTest' ) ;
	var refGeometry = this._GetMarkerGeometry( tableMap, rowIdx, colIdx, '_SizeTest' ) ;
	var nextGeometry = this._GetMarkerGeometry( tableMap, newRowIdx, colIdx, '_SizeTest' ) ;
	this._UnmarkCells( [refCell, nextCell], '_SizeTest' ) ;

	if ( refGeometry.width != nextGeometry.width || refGeometry.x != nextGeometry.x )
		return null ;

	return { 'refCell' : refCell, 'nextCell' : nextCell, 'tableMap' : tableMap } ;
}

////////////////////////////////////
////////////////////////////////////

function FCK_ContextMenu_GetListener( listenerName )
{
	switch ( listenerName )
	{
		case 'Generic' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				menu.AddItem( 'Cut'		, FCKLang.Cut	, 7, FCKCommands.GetCommand( 'Cut' ).GetState() == FCK_TRISTATE_DISABLED ) ;
				menu.AddItem( 'Copy'	, FCKLang.Copy	, 8, FCKCommands.GetCommand( 'Copy' ).GetState() == FCK_TRISTATE_DISABLED ) ;
				menu.AddItem( 'Paste'	, FCKLang.Paste	, 9, FCKCommands.GetCommand( 'Paste' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			}} ;

		case 'Table' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				var bIsTable	= ( tagName == 'TABLE' ) ;
				var tempTable =  FCKSelection.MoveToAncestorNode( 'TABLE' );
				var bIsCell		= ( !bIsTable && tempTable) ;

				if ( bIsCell )
				{
					menu.AddSeparator() ;
					var oItem = menu.AddItem( 'Cell'	, FCKLang.CellCM ) ;
					oItem.AddItem( 'TableInsertCellBefore'	, FCKLang.InsertCellBefore, 69 ) ;
					oItem.AddItem( 'TableInsertCellAfter'	, FCKLang.InsertCellAfter, 58 ) ;
					oItem.AddItem( 'TableDeleteCells'	, FCKLang.DeleteCells, 59 ) ;
					if ( FCKBrowserInfo.IsGecko )
						oItem.AddItem( 'TableMergeCells'	, FCKLang.MergeCells, 60,
							FCKCommands.GetCommand( 'TableMergeCells' ).GetState() == FCK_TRISTATE_DISABLED ) ;
					else
					{
						oItem.AddItem( 'TableMergeRight'	, FCKLang.MergeRight, 60,
							FCKCommands.GetCommand( 'TableMergeRight' ).GetState() == FCK_TRISTATE_DISABLED ) ;
						oItem.AddItem( 'TableMergeDown'		, FCKLang.MergeDown, 60,
							FCKCommands.GetCommand( 'TableMergeDown' ).GetState() == FCK_TRISTATE_DISABLED ) ;
					}
					oItem.AddItem( 'TableHorizontalSplitCell'	, FCKLang.HorizontalSplitCell, 61,
						FCKCommands.GetCommand( 'TableHorizontalSplitCell' ).GetState() == FCK_TRISTATE_DISABLED ) ;
					oItem.AddItem( 'TableVerticalSplitCell'	, FCKLang.VerticalSplitCell, 61,
						FCKCommands.GetCommand( 'TableVerticalSplitCell' ).GetState() == FCK_TRISTATE_DISABLED ) ;
					oItem.AddSeparator() ;
					oItem.AddItem( 'TableCellProp'		, FCKLang.CellProperties, 57,
						FCKCommands.GetCommand( 'TableCellProp' ).GetState() == FCK_TRISTATE_DISABLED ) ;

					menu.AddSeparator() ;
					oItem = menu.AddItem( 'Row'			, FCKLang.RowCM ) ;
					oItem.AddItem( 'TableInsertRowBefore'		, FCKLang.InsertRowBefore, 70 ) ;
					oItem.AddItem( 'TableInsertRowAfter'		, FCKLang.InsertRowAfter, 62 ) ;
					oItem.AddItem( 'TableDeleteRows'	, FCKLang.DeleteRows, 63 ) ;

					menu.AddSeparator() ;
					oItem = menu.AddItem( 'Column'		, FCKLang.ColumnCM ) ;
					oItem.AddItem( 'TableInsertColumnBefore', FCKLang.InsertColumnBefore, 71 ) ;
					oItem.AddItem( 'TableInsertColumnAfter'	, FCKLang.InsertColumnAfter, 64 ) ;
					oItem.AddItem( 'TableDeleteColumns'	, FCKLang.DeleteColumns, 65 ) ;
					
					menu.AddSeparator() ;
					menu.AddItem( 'TableHead'			, "表头" , tempTable.tHead ? '/ide/common/images/checked.gif' : '' ) ;
					menu.AddItem( 'TableFoot'			, "表尾" , tempTable.tFoot ? '/ide/common/images/checked.gif' : '' ) ;
				}

				if ( bIsTable || bIsCell )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'TableDelete'			, FCKLang.TableDelete ) ;
					menu.AddItem( 'TableProp'			, FCKLang.TableProperties, 39 ) ;
				}
			}} ;

		case 'Link' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				var bInsideLink = ( tagName == 'A' || FCKSelection.HasAncestorNode( 'A' ) ) ;

				if ( bInsideLink || FCK.GetNamedCommandState( 'Unlink' ) != FCK_TRISTATE_DISABLED )
				{
					// Go up to the anchor to test its properties
					var oLink = FCKSelection.MoveToAncestorNode( 'A' ) ;
					var bIsAnchor = ( oLink && oLink.name.length > 0 && oLink.href.length == 0 ) ;
					// If it isn't a link then don't add the Link context menu
					if ( bIsAnchor )
						return ;

					menu.AddSeparator() ;
					if ( bInsideLink )
						menu.AddItem( 'Link', FCKLang.EditLink		, 34 ) ;
					menu.AddItem( 'Unlink'	, FCKLang.RemoveLink	, 35 ) ;
				}
			}} ;

		case 'Image' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( tagName == 'IMG' && !tag.getAttribute( '_fckfakelement' ) )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'Image', FCKLang.ImageProperties, 37 ) ;
				}
			}} ;

		case 'Anchor' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				// Go up to the anchor to test its properties
				var oLink = FCKSelection.MoveToAncestorNode( 'A' ) ;
				var bIsAnchor = ( oLink && oLink.name.length > 0 ) ;

				if ( bIsAnchor || ( tagName == 'IMG' && tag.getAttribute( '_fckanchor' ) ) )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'Anchor', FCKLang.AnchorProp, 36 ) ;
					menu.AddItem( 'AnchorDelete', FCKLang.AnchorDelete ) ;
				}
			}} ;

		case 'Flash' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( tagName == 'IMG' && tag.getAttribute( '_fckflash' ) )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'Flash', FCKLang.FlashProperties, 38 ) ;
				}
			}} ;

		case 'Form' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( FCKSelection.HasAncestorNode('FORM') )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'Form', FCKLang.FormProp, 48 ) ;
				}
			}} ;

		case 'Checkbox' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( tagName == 'INPUT' && tag.type == 'checkbox' )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'Checkbox', FCKLang.CheckboxProp, 49 ) ;
				}
			}} ;

		case 'Radio' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( tagName == 'INPUT' && tag.type == 'radio' )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'Radio', FCKLang.RadioButtonProp, 50 ) ;
				}
			}} ;

		case 'TextField' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( tagName == 'INPUT' && ( tag.type == 'text' || tag.type == 'password' ) )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'TextField', FCKLang.TextFieldProp, 51 ) ;
				}
			}} ;

		case 'HiddenField' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( tagName == 'IMG' && tag.getAttribute( '_fckinputhidden' ) )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'HiddenField', FCKLang.HiddenFieldProp, 56 ) ;
				}
			}} ;

		case 'ImageButton' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( tagName == 'INPUT' && tag.type == 'image' )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'ImageButton', FCKLang.ImageButtonProp, 55 ) ;
				}
			}} ;

		case 'Button' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( tagName == 'INPUT' && ( tag.type == 'button' || tag.type == 'submit' || tag.type == 'reset' ) )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'Button', FCKLang.ButtonProp, 54 ) ;
				}
			}} ;

		case 'Select' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( tagName == 'SELECT' )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'Select', FCKLang.SelectionFieldProp, 53 ) ;
				}
			}} ;

		case 'Textarea' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( tagName == 'TEXTAREA' )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'Textarea', FCKLang.TextareaProp, 52 ) ;
				}
			}} ;

		case 'BulletedList' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( FCKSelection.HasAncestorNode('UL') )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'BulletedList', FCKLang.BulletedListProp, 27 ) ;
				}
			}} ;

		case 'NumberedList' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( FCKSelection.HasAncestorNode('OL') )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'NumberedList', FCKLang.NumberedListProp, 26 ) ;
				}
			}} ;
		// 自定义
		case 'Media' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( tagName == 'IMG' && tag.getAttribute( '_fckmedia' ) )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'Media', '多媒体属性', 'plugins/fck_media/media.gif' ) ;
				}
			}} ;
		// 自定义
		case 'EditPortlet' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( tagName == 'DIV' && tag.getAttribute( 'className' ) == 'portletWindow' )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'EditPortlet', '占位符属性' ) ;
				}
			}} ;
	}
	return null ;
}