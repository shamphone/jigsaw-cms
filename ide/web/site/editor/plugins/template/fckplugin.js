var channelTree = Editor.channelTree;

/**
 * 添加栏目和文件夹集成对话框
 * @return
 */
var FCKNewObjectCommand = function()
{
}
/**
 * 如果当前选中栏目 则返回栏目的父文件夹节点
 * 如果当前选中文件夹，则返回文件夹节点
 * @return 栏目树的节点(WebXTreeItem)
 */
FCKNewObjectCommand.prototype._getParentNode = function(){
	var item = channelTree.getSelected();
	if(item==null){
		return null;
	}
	if(item.isFolder()){
		return item;
	}else{
		return item.parentNode;
	}
}
/**
 * @return 栏目树节点对应的网站文件夹对象（SMFolder）
 */
FCKNewObjectCommand.prototype._getParentFolder = function(){
	var node = this._getParentNode();
	if(node==null){
		return null;
	}else{
		return node.element;
	}
}
/**
 * 添加jsp栏目
 */
FCKNewObjectCommand.prototype._addJspChannel = function(siteFolder,channelType){
	var channelSource = siteFolder.template.getInitSource(channelType);
	var url = Globals.contextPath + "/site/channel/selectCreateType.jsp";
	var param = showDialog(url, null, 510, 405, false, false, false);
	if(param!=null){
		var advance = param.advance; 
		var o = new Object();
		o.channelSource = channelSource;
		o.siteFolder = siteFolder;
		switch (advance) {
		case "blank":
			url = Globals.contextPath + "/site/channel/create.do?templateName="+siteFolder.template.name+"&folderPath="+siteFolder.contextPath+"&channelType="+channelType;
			var ret = showDialog(url, o, 510, 405, false, false, false);
			if(ret){
				var channelName = ret.channelName;
				var channelSource = ret.channelSource;
				var parentFolder = ret.siteFolder;
				var channel = parentFolder.addChannel(channelName,channelType,channelSource);
				var parentNode = channelTree.getItem(parentFolder.path);
				if(parentNode!=null){
					parentNode.addChannel(channel);
				}
			}
			break;
		case "template":
			url = Globals.contextPath + "/site/channel/createFromPage.do?templateName="+siteFolder.template.name+"&folderPath="+siteFolder.contextPath+"&channelType="+channelType;
			var ret = showDialog(url, o, 510, 405, false, false, false);
			if(ret){ 
				var contextPath = ret.contextPath;
				var folderContextPath = ret.folderContextPath;
				var folderPath = "/"+siteFolder.template.name + folderContextPath;

				var parentNode = channelTree.getItem(folderPath);
				if(parentNode!=null){
					var channel = siteFolder.template.getChannel(contextPath);
					channel.refresh();
					parentNode.addChannel(channel);
				}
			}
			break;
		case "url":
			url = Globals.contextPath + "/site/channel/createFromURL.do?templateName="+siteFolder.template.name+"&folderPath="+siteFolder.contextPath+"&channelType="+channelType;
			var ret = showDialog(url, o, 510, 405, false, false, false);
			if(ret){
				var contextPath = ret.contextPath;
				var folderContextPath = ret.folderContextPath;
				var folderPath = "/"+siteFolder.template.name + folderContextPath;

				var parentNode = channelTree.getItem(folderPath);
				if(parentNode!=null){
					var channel = siteFolder.template.getChannel(contextPath);
					channel.refresh();
					parentNode.addChannel(channel);
				}
			}
			break;
		default:
			break;
		}
	}
}
/**
 * 添加片段栏目
 */
FCKNewObjectCommand.prototype._addClipChannel = function(siteFolder,channelType){
	this._addChannel(siteFolder, channelType, "clip");
}
/**
 * 添加css栏目
 */
FCKNewObjectCommand.prototype._addCssChannel = function(siteFolder,channelType){
	this._addChannel(siteFolder, channelType, "css");
}
/**
 * 添加script栏目
 */
FCKNewObjectCommand.prototype._addScriptChannel = function(siteFolder,channelType){
	this._addChannel(siteFolder, channelType, "script");
}

/**
 * 添加rss栏目
 */
FCKNewObjectCommand.prototype._addRssChannel = function(siteFolder,channelType){
	this._addChannel(siteFolder, channelType, "rss");
}

FCKNewObjectCommand.prototype._addChannel = function(siteFolder,channelType,module){
	var channelSource = siteFolder.template.getInitSource(channelType);
	url = Globals.contextPath + "/site/"+module+"/create.do?templateName="+siteFolder.template.name+"&folderPath="+siteFolder.contextPath+"&channelType="+channelType;
	var o = new Object();
	o.channelSource = channelSource;
	o.siteFolder = siteFolder;
	var ret = showDialog(url, o, 510, 405, false, false, false);
	if(ret){
		var channelName = ret.channelName;
		var channelSource = ret.channelSource;
		var parentFolder = ret.siteFolder;
		var channel = parentFolder.addChannel(channelName,channelType,channelSource);
		var parentNode = channelTree.getItem(parentFolder.path);
		if(parentNode!=null){
			parentNode.addChannel(channel);
		}
	}
}

/**
 * 添加folder
 */
FCKNewObjectCommand.prototype._addFolder = function(siteFolder){
	var url = Globals.contextPath + '/site/editor/plugins/folder/create.jsp?';
	var param = showDialog(  url, siteFolder, 510,405, null, null, true );	
	if(param){
		var folder = param.folder;
		var parent = param.parent;
		var parentNode = channelTree.getItem(parent.path);
		if(parentNode!=null){
			parentNode.addFolder(folder);
		}
	}
}

/**
 * 添加栏目
 */
FCKNewObjectCommand.prototype.Execute = function(){
	var siteFolder = this._getParentFolder();
	if(siteFolder){
		var url = Globals.contextPath + "/site/channel/selectChannelType.do";
		var channelType = showDialog(url, null, 518, 405, false, false, false);
		if(channelType!=null){
			switch(channelType){
				case  "index":
				case  "word":
				case  "excel":
				case  "pdf":
					this._addJspChannel(siteFolder,channelType);
					break;
				case  "rss":
					this._addRssChannel(siteFolder,channelType);
					break;
				case  "clip":
					this._addClipChannel(siteFolder,channelType);
					break;
				case  "style":
					this._addCssChannel(siteFolder, channelType);
					break;
				case  "script":
					this._addScriptChannel(siteFolder, channelType);
					break;
				case  "folder":
					this._addFolder(siteFolder);
					break;
			}
		}
	}else{
		alert("请选择父文件夹！");
	}
}
FCKNewObjectCommand.prototype.GetState = function(){
	return FCK_TRISTATE_OFF
}

FCKCommands.RegisterCommand( 'NewObject', new FCKNewObjectCommand() ) ;

var oNewObjectItem = new FCKToolbarButton( 'NewObject', FCKLang['NewObjectBtn'],FCKLang['NewObjectTips'],FCK_TOOLBARITEM_ICONTEXT, true, true, 4  ) ;
oNewObjectItem.Style = FCK_TOOLBARITEM_ICONTEXT ;
FCKToolbarItems.RegisterItem( 'NewObject', oNewObjectItem ) ;