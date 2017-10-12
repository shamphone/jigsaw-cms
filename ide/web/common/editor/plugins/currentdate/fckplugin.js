var FCKCurrentDateCommand = function()
{
}
/**
 * 注意，这里的变量currentPath是在主页面上定义的变量，指定当前编辑的文件的地址
 */
FCKCurrentDateCommand.prototype = {
    Execute : function()
    {
        FCK.InsertHtml((new Date()).toLocaleDateString());
    },

    GetState : function()
    {
        return FCK_TRISTATE_OFF ;
    }
}

FCKCommands.RegisterCommand('CurrentDate', new FCKCurrentDateCommand() ) ;
var oCurrentDateItem = new FCKToolbarButton( 'CurrentDate', FCKLang['CurrentDate']) ;

oCurrentDateItem.IconPath = FCKPlugins.Items['currentdate'].Path + 'currentdate.gif' ;
FCKToolbarItems.RegisterItem( 'CurrentDate', oCurrentDateItem ) ;
