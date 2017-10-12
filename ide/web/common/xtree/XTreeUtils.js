/*
 * author by lichengzhao
 */
/*
WebFXTreeItem.prototype.remove = function()
{
    var iconSrc = document.getElementById(this.id + '-plus').src;
    var parentNode = this.parentNode;
    var prevSibling = this.getPreviousSibling(true);
    var nextSibling = this.getNextSibling(true);
    var folder = this.parentNode.folder;
    var last = ((nextSibling) && (nextSibling.parentNode) && (nextSibling.parentNode.id == parentNode.id))?false:true;
    //this.getPreviousSibling().focus();
    webFXTreeHandler.selected = null;
    this._remove();
    if (parentNode.childNodes.length == 0) {
        document.getElementById(parentNode.id + '-cont').style.display = 'none';
        parentNode.doCollapse();
        parentNode.folder = false;
        parentNode.open = false;
    }
    if (!nextSibling || last) { parentNode.indent(null, true, last, this._level, parentNode.childNodes.length); }
   
    if (document.getElementById(prevSibling.id + '-plus')) {
        if (parentNode == prevSibling.parentNode) {
            iconSrc = iconSrc.replace('minus', '').replace('plus', '');
            document.getElementById(prevSibling.id + '-plus').src = iconSrc;
        }
    }
}
*/
var XTreeUtils = {
    AddNode : function($id, $parentId, $text, $action, $icon, $openIcon)
    {
        var variableName = "item" + $id.replace("-", "_");
        var parentVariableName = "item" + $parentId.replace("-", "_");
        var statement = [];
        statement.push(variableName + " = new WebFXTreeItem(");
        statement.push("'" + ($text ? $text : "") + "'");
        statement.push(", \"" + ($action ? $action : "") + "\"");
        statement.push(", " + parentVariableName);
        statement.push(", '" + ($icon ? $icon : "") + "'");
        statement.push(", '" + ($openIcon ? $openIcon : "") + "');\n");
        eval(statement.join(""));
    },
    DeleteNode : function($id)
    {
        var variableName = "item" + $id.replace("-", "_");
        eval(variableName + ".remove();");
    },
    MoveNode : function($id, $parentId, $text, $action, $icon, $openIcon)
    {
        var variableName = "item" + $id.replace("-", "_");
        eval(variableName + ".remove();");
        this.AddNode($id, $parentId, $text, $action, $icon, $openIcon);
    },
    GetNode : function($id)
    {
        var variableName = "item" + $id.replace("-", "_");
        var ret = null;
        try {
        ret = eval(variableName);
        } catch (e){}
        return ret;
    },
    SetText : function($id, $text)
    {
        var node = this.GetNode($id);
        if (node && $text) {
            node.text = $text;
            var item = document.getElementById(node.id + "-anchor");
            if (item)
                item.innerHTML = $text;
        }
    },
    Focus : function($id)
    {
        var variableName = "item" + $id.replace("-", "_");
        if (webFXTreeHandler.selected)
            webFXTreeHandler.selected.deSelect();
        eval("this.ExpandAllParent(" + variableName + ");");
        eval(variableName + ".focus();");
        eval(variableName + ".blur();");
//        window.setTimeout("webFXTreeHandler.selected.blur();", 1000);
//	eval("alert(document.getElementById(" + variableName + ".id + '-anchor').className);");
//	eval("document.getElementById(" + variableName + ".id + '-anchor').className = '';");
    },
    Blur : function()
    {
        webFXTreeHandler.selected.blur();
    },
    ExpandAllParent : function($item)
    {
        if (!$item.parentNode || !$item.parentNode.action)
            return;
        this.ExpandAllParent($item.parentNode);
        if (!$item.parentNode.open)
            $item.parentNode.expand();
    }
};


