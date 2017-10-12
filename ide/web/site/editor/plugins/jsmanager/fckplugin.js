var FCKJsManagerCommand = function()
{
}
/**
 * 注意，这里的变量currentPath是在主页面上定义的变量，指定当前编辑的文件的地址
 */
FCKJsManagerCommand.prototype.Execute = function()
{
    var url = FCKPlugins.Items['jsmanager'].Path + 'manage.do?path=' + jsFolder;
    window.open(url, "_blank");
}
FCKJsManagerCommand.prototype.GetState = function()
{
  return FCK_TRISTATE_OFF ;
}

FCKCommands.RegisterCommand( 'JsManager', new FCKJsManagerCommand() ) ;

var jsManagerItem = new FCKToolbarButton( 'JsManager', '脚本编辑器', '脚本编辑器', true, null, 5) ;
jsManagerItem.Style = FCK_TOOLBARITEM_ICONTEXT ;
jsManagerItem.IconPath = FCKPlugins.Items['jsmanager'].Path + 'jsmanager.gif' ;
FCKToolbarItems.RegisterItem( 'JsManager', jsManagerItem ) ;

