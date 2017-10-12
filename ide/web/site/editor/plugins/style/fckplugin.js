var CSSModulePath = modulePath + 'css' ;
var FCKCssCommand = function()
{
}
/**
 * 注意，这里的变量currentPath是在主页面上定义的变量，指定当前编辑的文件的地址
 */
FCKCssCommand.prototype.Execute = function()
{
    var url = Globals.contextPath + '/site/css/css.jsp'
    var param=new Object();
    param.styleSheets= new Array();
    param.path=oChannel.path;
    var sheets=FCK.EditorDocument.styleSheets;
    for(var i=0;i<sheets.length;i++){
      var href=sheets[i].href;
      if(!((href.indexOf('/fck_')>=0)||(href.indexOf('/portal/')>=0)||(href.length==0)))
         param.styleSheets.push(sheets[i]);
    }
//  param.selectedStyle=""+oInput.value;
//  param.filter=styleFilter;
	var ret = showModalDialog(url, param, "dialogWidth:470px;dialogHeight:460px;help:no;resizable:yes;status:no");
	if (!ret)
		return;
	var range = new FCKDomRange( FCK.EditorWindow ) ;
	range.MoveToSelection() ;
	range.Expand('inline_elements');
	var elem= FCK.Selection.GetParentElement();
	if(elem==null)
		return;
    FCKUndo.SaveUndoStep() ;
	if (ret == ":none")
		elem.className = "";
	else
		elem.className = ret;
}
FCKCssCommand.prototype.GetState = function()
{
  return FCK_TRISTATE_OFF ;
}

FCKCommands.RegisterCommand( 'CSS', new FCKCssCommand() ) ;
var oStyleItem = new FCKToolbarButton( 'CSS', FCKLang['StyleDlgTitle']) ;

oStyleItem.IconPath = FCKPlugins.Items['style'].Path + 'style.png' ;
FCKToolbarItems.RegisterItem( 'CSS', oStyleItem ) ;

