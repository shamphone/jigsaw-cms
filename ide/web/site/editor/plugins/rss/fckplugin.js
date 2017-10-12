
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
	var channelSource = oChannel.getWorkingChannelSource();
	var param = new Object();
	param.oChannel = oChannel;
	param.channelSource = channelSource;
	var ret = showDialog( Globals.contextPath + '/site/rss/toProperty.do',
			param,
              510,
              400,
              null,
              null,
              true );
	if(ret!=null){
		oChannel.setWorkingSource(channelSource.toString());
		document.getElementById("xEditingFrame").src = oChannel.path+".bak?timestamp="+new Date().getTime();
		return false;
	}
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

/**
 * 数据处理器，在源代码和html代码之间转化
 */
var FCKJsProcessor = function(){
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
FCKJsProcessor.prototype.ConvertToHtml = function( data ){
	return "<html<body><pre>"+FCKTools.HTMLEncode(data)+"</pre></body></html>";
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
FCKJsProcessor.prototype.ConvertToDataFormat = function( root, excludeRoot, ignoreIfEmptyParagraph, format ){
    return "";
};

/*
 * Makes any necessary changes to a piece of HTML for insertion in the
 * editor selection position.
 *     @param {String} html The HTML to be fixed.
 */
FCKJsProcessor.prototype.FixHtml = function( html ){
	return html ;
};

/**
 * 覆盖FCK的实现，不需要GetParentForm操作。
 */
FCK.GetParentForm = function(){
};

FCK.DataProcessor = new FCKJsProcessor();

FCK.StartEditor = function(){
	document.getElementById("xEditingFrame").src = oChannel.path+".bak";
}
