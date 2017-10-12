var FCKHruleCommand = function()
{
}
/**
 * 注意，这里的变量currentPath是在主页面上定义的变量，指定当前编辑的文件的地址
 */
FCKHruleCommand.prototype.Execute = function()
{
    var url = FCKPlugins.Items['hrule'].Path + 'hrule.html'
    FCKDialog.OpenDialog( 'FCKDialog_' + FCKLang['HruleDlgTitle'],
                              FCKLang['HruleDlgTitle'],
                              url,
                              300,
                              180,
                              null,
                              null,
                              false );
 }
FCKHruleCommand.prototype.GetState = function()
{
  return FCK_TRISTATE_OFF ;
 }

FCKCommands.RegisterCommand( 'Hrule', new FCKHruleCommand() ) ;
var oHruleItem = new FCKToolbarButton( 'Hrule', FCKLang['Hrule']) ;

oHruleItem.IconPath = FCKPlugins.Items['hrule'].Path + 'hrule.gif' ;
FCKToolbarItems.RegisterItem( 'Hrule', oHruleItem ) ;
