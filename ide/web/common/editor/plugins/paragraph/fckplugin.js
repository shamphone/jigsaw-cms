var FCKParagraphCommand = function()
{
}
/**
 * 注意，这里的变量currentPath是在主页面上定义的变量，指定当前编辑的文件的地址
 */
FCKParagraphCommand.prototype.Execute = function()
{
    var url = FCKPlugins.Items['paragraph'].Path + 'paragraph.html'
    FCKDialog.OpenDialog( 'FCKDialog_' + FCKLang['ParagraphDlgTitle'],
                              FCKLang['ParagraphDlgTitle'],
                              url,
                              450,
                              310,
                              null,
                              null,
                              false );

 }
FCKParagraphCommand.prototype.GetState = function()
{
  return FCK_TRISTATE_OFF ;
 }

FCKCommands.RegisterCommand( 'Paragraph', new FCKParagraphCommand() ) ;
var oParagraphItem = new FCKToolbarButton( 'Paragraph', FCKLang['ParagraphDlgTitle']) ;

oParagraphItem.IconPath = FCKPlugins.Items['paragraph'].Path + 'paragraph.gif' ;
FCKToolbarItems.RegisterItem( 'Paragraph', oParagraphItem ) ;
