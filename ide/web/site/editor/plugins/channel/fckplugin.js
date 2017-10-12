var channelTree = Editor.channelTree;
var checkout = oChannel.checkLock();;
Editor.resetTitle();
/**
 * 兼容老版本 替换老版本的title和site:title标记为site:title2
 * @param $html
 * @return
 */
function _FixTitle($html) {
	return $html.replace(/<title.*?>[\s\S]*?<\/title>/i, "<site:title2 format=\"$C-$S\"></site:title2>");
}

function Confirm($question, $yesText, $noText){
    var args = {};
    args["question"] = $question;
	    args["yesText"] = $yesText;
    args["noText"] = $noText;
    return showDialog("confirm.jsp?timestamp=" + new Date().getTime(), args, 340, 60, false, false, false);
}

window.onbeforeunload = function () {
	//只有当前用户已经签出时，离开页面才会检查用户是否保存
	if(checkout){
		if(FCK.IsDirty()){
			if(Confirm("当前页面尚未保存,是否保存？","保存","不保存")){
				FCK.ToolbarSet.CurrentInstance.Commands.GetCommand("SaveChannel").Execute();
			}
		}
		//离开页面时签入
	 	oChannel.mildCheckin();
	}
}

/**
 * 提供和页面相关的操作功能，提供给页面编辑器使用，提供如下按钮和相关功能：
 * 1. 预览：预览当前页面
 * 2. 转移：将页面转移到其它页面下
 * 3. 修改：修改页面属性
 * 4. 删除：删除当前页面
 * 5. 发布： 将修改发布，前台页面可以看到变更。
 * 6. 恢复：放弃当前的修改，恢复页面页面到已发布版本
 * 7. 保存: 保存当前Jsp页面
 * 8. 解锁: 解除其他用户的锁
 * 9. 导入：从文本文件做增量导入
 */

/**
*
* 删除页面
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
var FCKDeleteChannelCommand = function(){
}
/**
* 注意，这里的变量currentPath是在主页面上定义的变量，指定当前编辑的文件的地址
*/
FCKDeleteChannelCommand.prototype.Execute = function(){
	if(!oChannel.deletable()) {
		alert("这是系统内置页面,不能删除!");
		return;
	}
	if(!oChannel.checkLock()){
		return false;
	}
	if (confirm("删除页面将是无法恢复的操作,请确定！")){
		var status = oChannel.remove();
		if(status){
			try{
				FCK.ResetIsDirty();
			}catch(e){}
			channelTree.getSelected().removeNode();
			window.location = "blank.jsp";
		}else{
			alert("删除栏目时出错，请与管理员联系.");
		}
	}
}
FCKDeleteChannelCommand.prototype.GetState = function(){
	return checkout?FCK_TRISTATE_OFF:FCK_TRISTATE_DISABLED
}

FCKCommands.RegisterCommand( 'DeleteChannel', new FCKDeleteChannelCommand() ) ;

var oDeleteChannelItem = new FCKToolbarButton( 'DeleteChannel', FCKLang['DeleteChannelBtn'],FCKLang['DeleteChannelTitle'] ) ;
oDeleteChannelItem.IconPath = FCKPlugins.Items['channel'].Path + 'delete.gif' ;
oDeleteChannelItem.Style = FCK_TOOLBARITEM_ICONTEXT ;
FCKToolbarItems.RegisterItem( 'DeleteChannel', oDeleteChannelItem ) ;


/**
* 复制栏目
*/
var FCKCopyChannelCommand = function(){
}

FCKCopyChannelCommand.prototype.Execute = function(){
	if(!oChannel.checkLock()){
		return false;
	}
	url = Globals.contextPath +"/site/channel/copy.jsp";
	var args = new Object();
	args.oChannel = oChannel;
	args.siteFactory = parent.siteFactory;
	var ret = showDialog(url,args,276,140);
	if(ret!=null){
		var siteFolder = ret.siteFolder;
		var name = ret.name;
		var displayName = ret.displayName;
		var newChannel = oChannel.copyTo(siteFolder,name,displayName);
		if(newChannel){
			alert("复制成功！");
			if(siteFolder.template.name==oTemplate.name){
				var parentNode = channelTree.getItem(siteFolder.path);
				if(parentNode!=null){
					parentNode.addChannel(newChannel);
				}
			}
		}else{
			alert("复制失败,请与管理员联系.");
		}
	}
}
FCKCopyChannelCommand.prototype.GetState = function(){
	return checkout?FCK_TRISTATE_OFF:FCK_TRISTATE_DISABLED
}

FCKCommands.RegisterCommand( 'CopyChannel', new FCKCopyChannelCommand() ) ;

var oCopyChannelItem = new FCKToolbarButton( 'CopyChannel', FCKLang['CopyChannelBtn'],FCKLang['CopyChannelTitle'] ) ;
oCopyChannelItem.IconPath = FCKPlugins.Items['channel'].Path + 'delete.gif' ;
oCopyChannelItem.Style = FCK_TOOLBARITEM_ICONTEXT ;
FCKToolbarItems.RegisterItem( 'CopyChannel', oCopyChannelItem ) ;

/**
*
* 转移页面
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
var FCKDMoveChannelCommand = function(){
}
/**
* 注意，这里的变量currentPath是在主页面上定义的变量，指定当前编辑的文件的地址
*/
FCKDMoveChannelCommand.prototype.Execute = function(){
	if(!oChannel.deletable()) {
       alert("这是系统内置页面,不能转移!");
       return;
	}
	if(!oChannel.checkLock()){
		return false;
	}
	
	var folder = SiteDialog.selectFolder(top.Editor.template,parent.siteFactory)
	
	if(folder == null)
		return;
	if(folder.getPath()==oChannel.getFolder().getPath()){
		alert("无法转移到当前文件夹，操作取消。");
		return;
	}
	var newChannel = folder.template.getChannel(folder.contextPath+"/"+oChannel.getFullName());

	if(newChannel.exists()){
		if(!confirm("此文件夹已包含一个名为\""+oChannel.name+"\"的栏目，是否覆盖？")){
			return;
		}
	}
	
	var currentNode = channelTree.getSelected();
	var status = oChannel.setParent(folder);
	if (status){
		//改变栏目在树中位置
		currentNode.setParentFolder(folder);
		alert("转移成功！");
	}else{
		alert("转移失败！");
	}
}
FCKDMoveChannelCommand.prototype.GetState = function()
{
	return checkout?FCK_TRISTATE_OFF:FCK_TRISTATE_DISABLED
}

FCKCommands.RegisterCommand( 'MoveChannel', new FCKDMoveChannelCommand() ) ;

var oNewChannelItem = new FCKToolbarButton( 'MoveChannel', FCKLang['MoveChannelBtn'],FCKLang['MoveChannelTitle'] ) ;
oNewChannelItem.IconPath = FCKPlugins.Items['channel'].Path + 'move.gif' ;
oNewChannelItem.Style = FCK_TOOLBARITEM_ICONTEXT ;

FCKToolbarItems.RegisterItem( 'MoveChannel', oNewChannelItem ) ;



var ChannelModulePath = modulePath + 'channel' ;


/**
 * 覆盖御览方法的实现,直接使用原有的预览图标和设置。
 */
FCK.Preview = function(){
    var html=FCK.GetData();
    html = _FixTitle(html);
    var resolution = oTemplate.getResolution();
    var status = oChannel.setPreviewSource(html);
    if(!status){
    	//转换失败，如页面上有未关闭的标签，将导致编辑的源代码通过JSP编译器编译出错，则返回空。
    	alert("页面保存时出错，请修改源代码后再保存。");
    }else{
    	var url= oChannel.path + ".bak.bak";
    	if(resolution==null||resolution.isDefault()){
    		window.open(url+"?timeStamp=" + new Date().getTime(),"_blank");
    	}else{
    		var queryString = "?resolution="+ encodeURIComponent(resolution.toString())+"&url="+encodeURIComponent(url);
		    queryString += "&timeStamp=" + new Date().getTime();
		    window.open( FCKPlugins.Items['channel'].Path+"mobilepreview.jsp"+queryString,"_blank");
	   }
   }
}
FCKToolbarItems.LoadedItems['Preview']= new FCKToolbarButton( 'Preview'	, FCKLang.Preview, null, FCK_TOOLBARITEM_ICONTEXT, true, null, 5  ) ;

/**
 *
 * 保存Jsp页面的命令
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

var FCKSaveChannelCommand = function(){
	this.Name = 'SaveChannel' ;
}

FCKSaveChannelCommand.prototype.Execute = function(){
	if(!oChannel.checkLock()){
		return false;
	}
    var sHtml = FCK.GetXHTML( FCKConfig.FormatSource ) ;
    sHtml = _FixTitle(sHtml);
    var status = oChannel.setWorkingSource(sHtml);
    if(status){
    	oChannel.published = false;
    	Editor.resetTitle();
		alert("保存成功！");
		FCK.ResetIsDirty();
    } else
        ShowError("页面保存时出错，请修改源代码后再保存。", req.status);
}

FCKSaveChannelCommand.prototype.GetState = function(){
	return checkout?FCK_TRISTATE_OFF:FCK_TRISTATE_DISABLED;
}

FCKCommands.RegisterCommand( 'SaveChannel', new FCKSaveChannelCommand() ) ;

var oNewChannelItem = new FCKToolbarButton( 'SaveChannel', FCKLang['SaveChannelBtn'],FCKLang['SaveChannelTitle'],FCK_TOOLBARITEM_ICONTEXT, true, true, 3  ) ;
oNewChannelItem.Style = FCK_TOOLBARITEM_ICONTEXT ;
FCKToolbarItems.RegisterItem( 'SaveChannel', oNewChannelItem ) ;


/**
 *
 * 发布页面
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

var FCKPuslishChannelCommand = function(){
}
/**
 * 注意，这里的变量currentPath是在主页面上定义的变量，指定当前编辑的文件的地址
 */
FCKPuslishChannelCommand.prototype.Execute = function(){
    var url= Globals.contextPath + '/site/channel/publish.do?';
    url+='templateID='+oTemplate.name;
	url+='&ID='+oChannel.contextPath
	var width;
	if(navigator.userAgent.indexOf("MSIE 6.0")>=0){
		width = 410;
	}else{ 
		width = 400;
	}
	var channels = showDialog(url,"",width,280);
	if(channels==null){
		return;
	}
	channels = new Array().concat(channels);
	//设置标题为已发布状态 ,只有把当前页面发布在之后，才设置这个状态
	if (channels.contains(oChannel.ID)){
		oChannel.refresh();
		channelTree.getItem(oChannel.path).setText(oChannel.displayName);
		Editor.resetTitle();
	}
}
FCKPuslishChannelCommand.prototype.GetState = function(){
	return checkout?FCK_TRISTATE_OFF:FCK_TRISTATE_DISABLED
}

FCKCommands.RegisterCommand( 'PublishChannel', new FCKPuslishChannelCommand() ) ;

var oItem = new FCKToolbarButton( 'PublishChannel', FCKLang['PublishChannelBtn'],FCKLang['PublishChannelTitle'],null,true) ;
oItem.IconPath = FCKPlugins.Items['channel'].Path + 'publish.gif' ;
oItem.Style = FCK_TOOLBARITEM_ICONTEXT ;
FCKToolbarItems.RegisterItem( 'PublishChannel', oItem ) ;

/**
 *
 * 恢复页面
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
var FCKRestoreChannelCommand = function()
{
}
/**
 * 注意，这里的变量currentPath是在主页面上定义的变量，指定当前编辑的文件的地址
 */
FCKRestoreChannelCommand.prototype.Execute = function(){
  if(!oChannel.checkLock()){
	return false;
  }
  if(!confirm("确实要放弃当前的修改,恢复到最近发布的版本?"))
    return;
  var status = oChannel.rollback();
  if (status){
	  alert("恢复成功！");
	  try{
		  FCK.ResetIsDirty();
	  }catch(e){};
	  window.location.reload();
  } else{
	  alert("恢复失败，请和系统管理员联系！");
  }

 }
FCKRestoreChannelCommand.prototype.GetState = function(){
	return checkout?FCK_TRISTATE_OFF:FCK_TRISTATE_DISABLED;
}

FCKCommands.RegisterCommand( 'RestoreChannel', new FCKRestoreChannelCommand() ) ;

var oNewChannelItem = new FCKToolbarButton( 'RestoreChannel', FCKLang['RestoreChannelBtn'],FCKLang['RestoreChannelTitle'] ) ;
oNewChannelItem.IconPath = FCKPlugins.Items['channel'].Path + 'restore.gif' ;
oNewChannelItem.Style = FCK_TOOLBARITEM_ICONTEXT ;
FCKToolbarItems.RegisterItem( 'RestoreChannel', oNewChannelItem ) ;

/**
* 强制签出栏目
*/
var FCKCheckoutCommand = function(){
}
FCKCheckoutCommand.prototype.Execute = function(){
	var info = oChannel.getCheckoutInfo();
	//如果栏目没有被签出或
    if(info==null){
    	window.location.reload();
    	return false;
	//当前ip已签出
	}else if(info["self"]=="true"){
		return false;
	}
	//已经被其他ip签出
	else{
		if(!confirm("ip为" +info["ip"]+ "的用户在 "+info["time"]+" 锁定了该栏目，您确定要解锁吗？")){
			return false;
		}
	}
    var bSuccess = oChannel.checkin();
	if(bSuccess){
		window.location.reload();
	}else{
		alert("解锁失败！");
	}
}
FCKCheckoutCommand.prototype.GetState = function(){
	return !checkout?FCK_TRISTATE_OFF:FCK_TRISTATE_DISABLED;
}

FCKCommands.RegisterCommand( 'Checkout', new FCKCheckoutCommand() ) ;

var oCheckoutItem = new FCKToolbarButton( 'Checkout', FCKLang['CheckoutBtn'],FCKLang['CheckoutTitle'],FCK_TOOLBARITEM_ICONTEXT,true,true ) ;
oCheckoutItem.IconPath = FCKPlugins.Items['channel'].Path + "checkout.gif" ;

FCKToolbarItems.RegisterItem( 'Checkout', oCheckoutItem) ;

/**
*
* 检测页面的大小
* <p>Title: 龙驭网站内容管理系统核心引擎</p>
*
* <p>Description: 龙驭网站内容管理系统核心引擎</p>
*
* <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
*
* <p>Company: 北京中科辅龙计算机技术有限公司</p>
*
* @version 2.0
*/
var FCKPageSizeCommand = function(){
}
FCKPageSizeCommand.prototype.Execute = function(){
	if(!oChannel.checkLock()){
		return false;
	}
    var html=FCK.GetData();
    html = _FixTitle(html);
    var status = oChannel.setPreviewSource(html);
	if(!status){
		//转换失败，如页面上有未关闭的标签，将导致编辑的源代码通过JSP编译器编译出错，则返回空。
		alert("页面保存时出错，请修改源代码后再保存！");
	}else{
		var url = Globals.contextPath +"/site/channel/channelPageSize.do?channelPath="+encodeURIComponent(oChannel.path) +"&timestamp=";
		url += new Date().getTime();
		var req = getXMLHttpRequest();
		req.open("POST",url,false);
		req.send(null);
		if(req.status==200){
			 ret = req.responseText;
			 alert("页面大小大约为"+ret+"byte");
	    }else{
	         alert("页面分析时出错!");
	    }
	}
}
FCKPageSizeCommand.prototype.GetState = function(){
	return checkout?FCK_TRISTATE_OFF:FCK_TRISTATE_DISABLED
}

FCKCommands.RegisterCommand( 'PageSize', new FCKPageSizeCommand() ) ;

var oPageSizeItem = new FCKToolbarButton( 'PageSize', FCKLang['PageSizeBtn'],FCKLang['PageSizeTitle'] ) ;
oPageSizeItem.IconPath = FCKPlugins.Items['channel'].Path + "help.gif" ;
oPageSizeItem.Style = FCK_TOOLBARITEM_ICONTEXT ;

FCKToolbarItems.RegisterItem( 'PageSize', oPageSizeItem) ;

/**
 *
 * 导入页面模板
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
var FCKDImportHTMLCommand = function(){
}
FCKDImportHTMLCommand.prototype.Execute = function(){
	if(!oChannel.checkLock()){
		return false;
	}
    url = Globals.contextPath +"/site/channel/import.do?templateName="+oTemplate.name+"&contextPath="+oChannel.contextPath+"&channelType="+oChannel.type;
    var ret = showDialog(url,oChannel,400,100);
    if (ret) {
	   // 导入成功，刷新页面
	   FCK.ResetIsDirty();
	   window.location.reload();
    }
}
FCKDImportHTMLCommand.prototype.GetState = function(){
	return checkout?FCK_TRISTATE_OFF:FCK_TRISTATE_DISABLED
}

FCKCommands.RegisterCommand( 'ImportHTML', new FCKDImportHTMLCommand() ) ;

var oNewChannelItem = new FCKToolbarButton( 'ImportHTML', FCKLang['ImportHTMLBtn'],FCKLang['ImportHTMLTitle'] ) ;
oNewChannelItem.IconPath = FCKPlugins.Items['channel'].Path + 'import.gif' ;
oNewChannelItem.Style = FCK_TOOLBARITEM_ICONTEXT ;

FCKToolbarItems.RegisterItem( 'ImportHTML', oNewChannelItem ) ;

/**
 *
 * 设置页面权限
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
var FCKAuthorizationCommand = function(){
}
FCKAuthorizationCommand.prototype.Execute = function(){
	 if(!oChannel.checkLock()){
		 return false;
	 }
	 var url=Globals.contextPath + "/site/channel/auth.do";
	 showDialog(url, window, 320, 280, false, false, false);
}
FCKAuthorizationCommand.prototype.GetState = function(){
	return checkout?FCK_TRISTATE_OFF:FCK_TRISTATE_DISABLED
}

FCKCommands.RegisterCommand( 'Authorization', new FCKAuthorizationCommand() ) ;

var oNewChannelItem = new FCKToolbarButton( 'Authorization', FCKLang['AuthorizationBtn'],FCKLang['AuthorizationTitle'] ) ;
oNewChannelItem.IconPath = FCKPlugins.Items['channel'].Path + 'auth.png' ;
oNewChannelItem.Style = FCK_TOOLBARITEM_ICONTEXT ;

FCKToolbarItems.RegisterItem( 'Authorization', oNewChannelItem ) ;

/**
 * 管理模板资源
 
var FCKResourcesCommand = function() {};
FCKResourcesCommand.prototype.Execute = function(){
	var url = Globals.contextPath + "/site/resource/index.jsp?template=" + oTemplate.name;
	var ret = showDialog(url,"",780,600);
	if(typeof(ret) == "string" && ret == 'webdav')
		oWevDAV.click();	
};

FCKResourcesCommand.prototype.GetState = function(){
  return FCK_TRISTATE_OFF ;
};

FCKCommands.RegisterCommand('Resources', new FCKResourcesCommand()) ;
var oItem = new FCKToolbarButton('Resources', FCKLang.ResourcesTitle, FCKLang.ResourcesTip, FCK_TOOLBARITEM_ICONTEXT, true, true, 4);
oItem.IconPath = FCKPlugins.Items['template'].Path + 'resources.png' ;
FCKToolbarItems.RegisterItem('Resources', oItem) ;
*/