var FCKWordartCommand = function()
{
}
/**
 * 注意，这里的变量currentPath是在主页面上定义的变量，指定当前编辑的文件的地址
 */
FCKWordartCommand.prototype.Execute = function()
{
    var url = FCKPlugins.Items['wordart'].Path + 'wordart.jsp'
    FCKDialog.OpenDialog( 'FCKDialog_' + FCKLang['DlgArt'],
                              FCKLang['DlgArt'],
                              url,
                              450,
                              310,
                              null,
                              null,
                              false );
 }
FCKWordartCommand.prototype.GetState = function()
{
  return FCK_TRISTATE_OFF ;
 }

FCKCommands.RegisterCommand( 'Wordart', new FCKWordartCommand() ) ;
var oWordartItem = new FCKToolbarButton( 'Wordart', FCKLang['DlgArt']) ;

oWordartItem.IconPath = FCKPlugins.Items['wordart'].Path + 'wordart.gif' ;
FCKToolbarItems.RegisterItem( 'Wordart', oWordartItem ) ;
