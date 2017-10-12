var FCKPageBreakCommand = function()
{
}
/**
 * 注意，这里的变量currentPath是在主页面上定义的变量，指定当前编辑的文件的地址
 */
FCKPageBreakCommand.prototype =
{
    Execute : function()
    {
        FCKUndo.SaveUndoStep();
        var oRange = FCK.EditorDocument.selection.createRange();
        oRange.pasteHTML("<div style='page-break-after:always; width:100%; background-color:#c0c0c0; font-size:1px; vertical-align:middle; height:1px'>&nbsp;</div>");
        FCKUndo.SaveUndoStep();
    },

    GetState : function()
    {
        return FCK_TRISTATE_OFF ;
    }
}

FCKCommands.RegisterCommand('PageBreak', new FCKPageBreakCommand()) ;
var oPageBreakItem = new FCKToolbarButton( 'PageBreak', FCKLang['PageBreak']) ;

oPageBreakItem.IconPath = FCKPlugins.Items['pagebreak'].Path + 'pagebreak.png' ;
FCKToolbarItems.RegisterItem( 'PageBreak', oPageBreakItem) ;
