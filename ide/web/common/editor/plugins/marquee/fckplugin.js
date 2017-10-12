var FCKMarqueeCommand = function()
{
}
/**
 * 注意，这里的变量currentPath是在主页面上定义的变量，指定当前编辑的文件的地址
 */
FCKMarqueeCommand.prototype.Execute = function()
{
    var url = FCKPlugins.Items['marquee'].Path + 'marquee.html'
    FCKDialog.OpenDialog( 'FCKDialog_' + FCKLang['MarqueeDlgTitle'],
                              FCKLang['MarqueeDlgTitle'],
                              url,
                              450,
                              310,
                              null,
                              null,
                              false );
 }
FCKMarqueeCommand.prototype.GetState = function()
{
  return FCK_TRISTATE_OFF ;
 }

FCKCommands.RegisterCommand( 'Marquee', new FCKMarqueeCommand() ) ;
var oMarqueeItem = new FCKToolbarButton( 'Marquee', FCKLang['MarqueeDlgTitle']) ;

oMarqueeItem.IconPath = FCKPlugins.Items['marquee'].Path + 'marquee.gif' ;
FCKToolbarItems.RegisterItem( 'Marquee', oMarqueeItem ) ;
