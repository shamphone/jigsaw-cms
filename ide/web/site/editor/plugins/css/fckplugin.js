
/**
 *
 * 修改页面属性
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author lichengzhao
 * @version 2.0
 */
 var FCKDEditChannelCommand = function(){
};
FCKDEditChannelCommand.prototype.Execute = function()
{
	if(!oChannel.checkLock()){
		return false;
	}
	
	showDialog(  FCKPlugins.Items['css'].Path + 'property.jsp',
              window,
              510,
              400,
              null,
              null,
              true );
 };
FCKDEditChannelCommand.prototype.GetState = function()
{
	return checkout?FCK_TRISTATE_OFF:FCK_TRISTATE_DISABLED
 };

FCKCommands.RegisterCommand( 'EditChannel', new FCKDEditChannelCommand() ) ;

var oNewChannelItem = new FCKToolbarButton( 'EditChannel', FCKLang['EditChannelBtn'],FCKLang['EditChannelTitle'] ) ;
oNewChannelItem.IconPath = FCKPlugins.Items['channel'].Path + 'property.gif' ;
oNewChannelItem.Style = FCK_TOOLBARITEM_ICONTEXT ;

FCKToolbarItems.RegisterItem( 'EditChannel', oNewChannelItem ) ;

/* 
 **
 * 针对Jsp页面编辑器对FCK的源代码编辑部分功能作的改进。改进功能包括：
 * 1. 重写FCK.GetLinkedFieldValue方法，页面初始化的时候，不是从系统绑定的输入域中获取工作内容，而是根据提供的参数中加载Jsp页面
 * 2. 重写FCK.Preview方法，预览时，在新窗口打开预览状态的页面。
 * 3. 覆盖FCK切换模式的操作。做小修改：在从代码模式切换到编辑模式时，如果代码有误导致JSP页面无法编译通过，则不切换，要求用户修改代码
 * 4.  修改DataProcessor类，使用服务器端操作来实现从源代码到所见即所得界面代码的切换
 * 5. 重写保存按钮对应的代码，提供FCKSaveJspCommand类保存时，不使用表单提交，而是采用ajax直接提交到后台
 */
/**
 * 数据处理器，在源代码和html代码之间转化
 */
var FCKCssProcessor = function(){
};
/*
 * Returns a string representing the HTML format of "data". The returned
 * value will be loaded in the editor.
 * The HTML must be from <html> to </html>, including <head>, <body> and
 * eventually the DOCTYPE.
 * Note: HTML comments may already be part of the data because of the
 * pre-processing made with ProtectedSource.
 *     @param {String} data The data to be converted in the
 *            DataProcessor specific format.
 */
FCKCssProcessor.prototype.ConvertToHtml = function( data ){
	return "<pre>"+FCKTools.HTMLEncode(data)+"</pre>";
};


/*
 * Converts a DOM (sub-)tree to a string in the data format.
 *     @param {Object} rootNode The node that contains the DOM tree to be
 *            converted to the data format.
 *     @param {Boolean} excludeRoot Indicates that the root node must not
 *            be included in the conversion, only its children.
 *     @param {Boolean} format Indicates that the data must be formatted
 *            for human reading. Not all Data Processors may provide it.
 */
FCKCssProcessor.prototype.ConvertToDataFormat = function( root, excludeRoot, ignoreIfEmptyParagraph, format ){
	var rootNode=root.cloneNode(true);	
    var data = FCK.GetInnerHTML(rootNode) ;
    return data.trim();
};

/*
 * Makes any necessary changes to a piece of HTML for insertion in the
 * editor selection position.
 *     @param {String} html The HTML to be fixed.
 */
FCKCssProcessor.prototype.FixHtml = function( html ){
	return html ;
};

FCK.GetInnerHTML = function (root){
	var data = "";
	if(root.nodeType == 3)
		return root.data;
	if(root.nodeType==1&&root.nodeName=="BR"){
		return "\n";
	}
	for(var i=0;i<root.childNodes.length;i++)
		data += FCK.GetInnerHTML(root.childNodes[i]);
	return data;
};

/**
 * 在启动编辑器时调用，覆盖FCK的实现，不是从系统绑定的输入域中获取工作内容，而是根据提供的参数中加载Jsp页面。
 */
FCK.GetLinkedFieldValue = function(){
  var html= oChannel.getWorkingSource();
  if(html==null)
    html="无法获取所要编辑的内容，请选择其它栏目。";
  return html;
};
/**
 * 覆盖FCK的实现，不需要GetParentForm操作。
 */
FCK.GetParentForm = function(){
};


FCK.DataProcessor = new FCKCssProcessor();

