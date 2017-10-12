/**
 *
 * 新建文件夹
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author lixf
 * @version 2.0
 */
var FCKNewFolderCommand = function(){
};
FCKNewFolderCommand.prototype.Execute = function()
{
	var url = FCKPlugins.Items['folder'].Path + 'create.jsp?';
	var args = new Object();
	args.ResourceEditor = ResourceEditor;
	args.window = window;
	var ret = showDialog(  url, args, 60,110, null, null, true );	
	if (ret) {
		top.Editor.channelTree.getSelected().addFolder(ret);
	}
 };
 FCKNewFolderCommand.prototype.GetState = function(){
	return FCK_TRISTATE_OFF ;
 };

FCKCommands.RegisterCommand( 'NewFolder', new FCKNewFolderCommand() ) ;

var oItem = new FCKToolbarButton( 'NewFolder', FCKLang['NewFolderBtn'],FCKLang['NewFolderTitle'] ) ;
oItem.IconPath = FCKPlugins.Items['folder'].Path + 'newfolder.png' ;
oItem.Style = FCK_TOOLBARITEM_ICONTEXT ;

FCKToolbarItems.RegisterItem( 'NewFolder', oItem ) ;

/**
 * 修改文件夹
 */
var FCKEditFolderCommand = function(){
};
FCKEditFolderCommand.prototype.Execute = function()
{
	var folder = top.Editor.channelTree.getSelected().element;
	if(!folder.deletable()){
		alert("这是系统内置文件夹,不能重命名!");
		return;
	}
	var url = FCKPlugins.Items['folder'].Path + 'edit.jsp?';
	var args = new Object();
	args.folder = folder;
	args.window = window;
	var ret = showDialog(url,args,250,110,null,"none",true );	
	if (ret) {
		top.Editor.channelTree.getSelected().setText(ret.getName());
	}
 };
 FCKEditFolderCommand.prototype.GetState = function(){
	return FCK_TRISTATE_OFF ;
 };

FCKCommands.RegisterCommand( 'EditFolder', new FCKEditFolderCommand() ) ;

var oItem = new FCKToolbarButton( 'EditFolder', FCKLang['EditFolderBtn'],FCKLang['EditFolderTitle'] ) ;
oItem.IconPath = FCKPlugins.Items['folder'].Path + 'edit.gif' ;
oItem.Style = FCK_TOOLBARITEM_ICONTEXT ;

FCKToolbarItems.RegisterItem( 'EditFolder', oItem ) ;

/**
 * 删除文件夹
 */
var FCKRemoveFolderCommand = function(){
};
FCKRemoveFolderCommand.prototype.Execute = function()
{
	var folder = top.Editor.channelTree.getSelected().element;
	if(!folder.deletable()){
		alert("这是系统内置文件夹,不能删除!");
		return;
	}
	if(confirm("删除文件将有可能导致这个文件夹中的文件在页面上的原有的链接无法正常使用,该操作不可恢复，确认删除文件夹'"+ folder.getContextPath() +"' ?" )){
		var result = folder.remove();
		if( result.status <300 && result.status >=200){
			alert("删除成功！");
			top.Editor.channelTree.getSelected().remove();
			window.location="blank.jsp";
		}else
			alert("删除失败,失败代码："+ result.status+".");
	}
 };
 FCKRemoveFolderCommand.prototype.GetState = function(){
	return FCK_TRISTATE_OFF ;
 };

FCKCommands.RegisterCommand( 'RemoveFolder', new FCKRemoveFolderCommand() ) ;

var oItem = new FCKToolbarButton( 'RemoveFolder', FCKLang['RemoveFolderBtn'],FCKLang['RemoveFolderTitle'] ) ;
oItem.IconPath = FCKPlugins.Items['folder'].Path + 'remove.gif' ;
oItem.Style = FCK_TOOLBARITEM_ICONTEXT ;

FCKToolbarItems.RegisterItem( 'RemoveFolder', oItem ) ;

/**
 * 复制文件夹
 */
var FCKCopyFolderCommand = function(){
};
FCKCopyFolderCommand.prototype.Execute = function()
{
	var ret = SiteDialog.selectFolder(top.Editor.template,parent.siteFactory);
	if(ret !=null){	
		var currentNode = top.Editor.channelTree.getSelected();
		var folder = currentNode.element;
		if(ret.getPath()==folder.getParent().getPath()){
			alert("无法转复制自己的直接父文件夹，操作取消。");
			return;
		}
		if(folder.isParent(ret)){
			alert("无法复制到自己或子文件夹，操作取消。");
			return;
		}
		folder.copyTo(ret);
		currentNode.copyTo(ret);
	}
 };
 FCKCopyFolderCommand.prototype.GetState = function(){
	return FCK_TRISTATE_OFF ;
 };

FCKCommands.RegisterCommand( 'CopyFolder', new FCKCopyFolderCommand() ) ;

var oItem = new FCKToolbarButton( 'CopyFolder', FCKLang['CopyFolderBtn'],FCKLang['CopyFolderTitle'] ) ;
oItem.IconPath = FCKPlugins.Items['folder'].Path + 'copy.png' ;
oItem.Style = FCK_TOOLBARITEM_ICONTEXT ;

FCKToolbarItems.RegisterItem( 'CopyFolder', oItem ) ;

/**
 * 移动文件夹
 */
var FCKMoveFolderCommand = function(){
};
FCKMoveFolderCommand.prototype.Execute = function()
{
	var currentNode = top.Editor.channelTree.getSelected();
	var folder = currentNode.element;
	if(!folder.deletable()){
		alert("这是系统内置文件夹,不能移动!");
		return;
	}
	var ret = SiteDialog.selectFolder(top.Editor.template,parent.siteFactory);
	if(ret !=null){
		if(ret.getPath()==folder.getParent().getPath()){
			alert("无法转移到自己的父文件夹，操作取消。");
			return;
		}
		if(folder.isParent(ret)){
			alert("无法转移到自己或子文件夹，操作取消。");
			return;
		}
		folder.setParent(ret);
		currentNode.setParentFolder(ret);
	}
 };
 FCKMoveFolderCommand.prototype.GetState = function(){
	return FCK_TRISTATE_OFF ;
 };

FCKCommands.RegisterCommand( 'MoveFolder', new FCKMoveFolderCommand() ) ;

var oItem = new FCKToolbarButton( 'MoveFolder', FCKLang['MoveFolderBtn'],FCKLang['MoveFolderTitle'] ) ;
oItem.IconPath = FCKPlugins.Items['folder'].Path + 'move.png' ;
oItem.Style = FCK_TOOLBARITEM_ICONTEXT ;

FCKToolbarItems.RegisterItem( 'MoveFolder', oItem ) ;

/**
 * WebDAV
 */
var FCKWebDAVCommand = function(){
	var node = document.createElement("A");
	node.href = "#";
	node.folder ="#";
	node.innerHTML = "WebDAV";
	node.style.display = "none";
	node.target = "_blank";
	node.style.behavior= "url(#default#AnchorClick);";
	this.node = node;
	FCKTools.AddEventListener( window, 'load', function(){
		document.documentElement.appendChild(node);
	}) ;
	//document.documentElement.appendChild(this.node);
};
FCKWebDAVCommand.prototype.Execute = function(){
	var path = "http://"+ location.hostname;
	if(location.port!=80)
		path +=":80";
	path += "/webdav" + top.Editor.channelTree.getSelected().element.getPath();
	
	this.node.href = path;
	this.node.folder = path;
	if(this.node.click){
		this.node.click();
	}else{
		var evt = document.createEvent("HTMLEvents");
		evt.initEvent("click",false,false);
		this.node.dispatchEvent(evt);
	}
 };
FCKWebDAVCommand.prototype.GetState = function(){
	return FCK_TRISTATE_OFF ;
 };

FCKCommands.RegisterCommand( 'WebDAV', new FCKWebDAVCommand() ) ;

var oItem = new FCKToolbarButton( 'WebDAV', FCKLang['WebDAVBtn'],FCKLang['WebDAVTitle'] ) ;
oItem.IconPath = FCKPlugins.Items['folder'].Path + 'webdav.png' ;
oItem.Style = FCK_TOOLBARITEM_ICONTEXT ;

FCKToolbarItems.RegisterItem( 'WebDAV', oItem );



/**
 * WebDAV
 */
var FCKRefreshCommand = function(){
	
};
FCKRefreshCommand.prototype.Execute = function(){
	window.location.reload();
 };
FCKRefreshCommand.prototype.GetState = function(){
	return FCK_TRISTATE_OFF ;
 };

FCKCommands.RegisterCommand( 'RefreshFolder', new FCKRefreshCommand() ) ;

var oItem = new FCKToolbarButton( 'RefreshFolder', FCKLang['RefreshFolderBtn'],FCKLang['RefreshFolderTitle'] ) ;
oItem.IconPath = FCKPlugins.Items['folder'].Path + 'refresh.png' ;
oItem.Style = FCK_TOOLBARITEM_ICONTEXT ;

FCKToolbarItems.RegisterItem( 'RefreshFolder', oItem );

/**
 * 导入文件夹
 */
var FCKImportCommand = function(){
};
FCKImportCommand.prototype.Execute = function()
{
	var url =FCKPlugins.Items['folder'].Path + 'import.jsp?';
	var args = new Object();
	args.Editor = top.Editor;
	args.window = window;
	var ret = showDialog(  url, args, 455, 385, null, null,  true );	
	if(ret !=null){
		window.location.reload();
	}
 };
 FCKImportCommand.prototype.GetState = function(){
	return FCK_TRISTATE_OFF ;
 };

FCKCommands.RegisterCommand( 'Import', new FCKImportCommand() ) ;

var oItem = new FCKToolbarButton( 'Import', FCKLang['ImportBtn'],FCKLang['ImportTitle'] ) ;
oItem.IconPath = FCKPlugins.Items['folder'].Path + 'import.gif' ;
oItem.Style = FCK_TOOLBARITEM_ICONTEXT ;

FCKToolbarItems.RegisterItem( 'Import', oItem ) ;




/**
 * 导入文件夹
 */
var FCKExportCommand = function(){
};
FCKExportCommand.prototype.Execute = function(){
	if(ResourceEditor.selected.length==0){
		alert("请选择要导出的文件！");
		return false;
	}
	var url = "/ide/site/resource/export.do?";
	url +="template="+Editor.template.name;
	for(var i=0;i<ResourceEditor.selected.length;i++){
		url +="&path=" + encodeURIComponent(ResourceEditor.selected[i].file.getContextPath());
	}
  	window.open(url);
};
 FCKExportCommand.prototype.GetState = function(){
	return FCK_TRISTATE_OFF ;
 };

FCKCommands.RegisterCommand( 'Export', new FCKExportCommand() ) ;

var oItem = new FCKToolbarButton( 'Export', FCKLang['ExportBtn'],FCKLang['ExportTitle'] ) ;
oItem.IconPath = FCKPlugins.Items['folder'].Path + 'export.gif' ;
oItem.Style = FCK_TOOLBARITEM_ICONTEXT ;

FCKToolbarItems.RegisterItem( 'Export', oItem ) ;
