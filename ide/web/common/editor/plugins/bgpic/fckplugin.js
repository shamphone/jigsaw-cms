var FCKBgpicCommand = function()
{
}
/**
 * 注意，这里的变量currentPath是在主页面上定义的变量，指定当前编辑的文件的地址
 */
FCKBgpicCommand.prototype.Execute = function()
{
    var url = FCKPlugins.Items['bgpic'].Path + 'bgpic.jsp'
    FCKDialog.OpenDialog( 'FCKDialog_' + FCKLang['BgpicDlgTitle'],
                              FCKLang['BgpicDlgTitle'],
                              url,
                              100,
                              100,
                              null,
                              null,
                              false );
 }
FCKBgpicCommand.prototype.GetState = function()
{
  return FCK_TRISTATE_OFF ;
 }

FCKCommands.RegisterCommand( 'Bgpic', new FCKBgpicCommand() ) ;
var oBgpicItem = new FCKToolbarButton( 'Bgpic', FCKLang['BgpicDlgTitle']) ;

oBgpicItem.IconPath = FCKPlugins.Items['bgpic'].Path + 'bgpic.gif' ;
FCKToolbarItems.RegisterItem( 'Bgpic', oBgpicItem ) ;
