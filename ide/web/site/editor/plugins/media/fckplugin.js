var FCKMediaCommand = function()
{
}
/**
 * 注意，这里的变量currentPath是在主页面上定义的变量，指定当前编辑的文件的地址
 */
FCKMediaCommand.prototype.Execute = function()
{
    var url = FCKPlugins.Items['media'].Path + 'media.jsp'
    FCKDialog.OpenDialog( 'FCKDialog_' + FCKLang['MediaDlgTitle'],
                              FCKLang['MediaDlgTitle'],
                              url,
                              450,
                              310,
                              null,
                              null,
                              false );

 }
FCKMediaCommand.prototype.GetState = function()
{
  return FCK_TRISTATE_OFF ;
}

FCKCommands.RegisterCommand( 'Media', new FCKMediaCommand() ) ;
var oMediaItem = new FCKToolbarButton( 'Media', FCKLang['MediaDlgTitle']) ;

oMediaItem.IconPath = FCKPlugins.Items['media'].Path + 'media.gif' ;
FCKToolbarItems.RegisterItem( 'Media', oMediaItem ) ;
