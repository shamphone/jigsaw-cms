var FCKCurrentTimeCommand = function()
{
}
/**
 * 注意，这里的变量currentPath是在主页面上定义的变量，指定当前编辑的文件的地址
 */
FCKCurrentTimeCommand.prototype = {
    Execute : function()
    {
        FCK.InsertHtml((new Date()).toLocaleTimeString());
    },

    GetState : function()
    {
        return FCK_TRISTATE_OFF ;
    }
}

FCKCommands.RegisterCommand('CurrentTime', new FCKCurrentTimeCommand() ) ;
var oCurrentTimeItem = new FCKToolbarButton( 'CurrentTime', FCKLang['CurrentTime']) ;

oCurrentTimeItem.IconPath = FCKPlugins.Items['currenttime'].Path + 'currenttime.gif' ;
FCKToolbarItems.RegisterItem( 'CurrentTime', oCurrentTimeItem ) ;
