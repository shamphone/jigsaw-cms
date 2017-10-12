
var FCKExFormCommand = function()
{
}
/**
 * 注意，这里的变量currentPath是在主页面上定义的变量，指定当前编辑的文件的地址
 */
FCKExFormCommand.prototype.Execute = function()
{
    var url = FCKPlugins.Items['exform'].Path + 'fck_form.jsp'
    FCKDialog.OpenDialog( 'FCKDialog_' + FCKLang['ExFormDlgTitle'],
                              FCKLang['ExFormDlgTitle'],
                              url,
                              450,
                              310,
                              null,
                              null,
                              false );

 }
FCKExFormCommand.prototype.GetState = function()
{
  return FCK_TRISTATE_OFF ;
}

FCKCommands.RegisterCommand( 'ExForm', new FCKExFormCommand() ) ;
FCKCommands.RegisterCommand( 'Form', new FCKExFormCommand() ) ;
var oExFormItem = new FCKToolbarButton( 'ExForm', FCKLang.Form, null, null, null, null, 48 )
FCKToolbarItems.RegisterItem( 'ExForm', oExFormItem ) ;
