document.write( '<script type="text/javascript" src="/ide/common/script/common.js"></script>' );
/**
 * 用来控制所有的弹出窗口
 *
**/
var SiteDialog	=	new Object();
 /**
   * 创建栏目、模板、网站的集成对话框
   * @return SiteTemplate 对象
   * @throws Exception
   * @author lichengzhao
   */
SiteDialog.create = function(templateName, folderPath){
	var url= Globals.contextPath + "/site/create.jsp";
	url += 'templateName=' + templateName;
    url += '&folderPath=' + folderPath;
    return showDialog(url,null,620,460);
}

/**
   * 修改栏目对话框
   * @param channelID 缺省栏目ID
   * @return Channel 对象
   * @throws Exception
   * @author lichengzhao
   */
SiteDialog.editChannel = function($channelID){
	var url= Globals.contextPath + '/site/channel/edit.do?&channelID=' + $channelID;
    return showDialog(url,null,510,400);
}

/**
   * 修改js和css栏目属性对话框
   * @param channelID 缺省栏目ID
   * @return Channel 对象
   * @throws Exception
   */
SiteDialog.editeCssAndStyle = function($channelID){
	var url= Globals.contextPath + '/site/css/toModify.do?&channelID=' + $channelID;
    return showDialog(url,null,273,115);
}

 /**
   * 选择网站模板
   * @param defaultCategoryId 默认选中的模板分类
   * @param defaultTemplateIds 默认选中的模板
   * @return null 或 SiteTemplate[] 数组，该数组长度可以为0
   * @throws Exception
   */
SiteDialog.selectTemplate = function($defaultCategoryId,$defaultTemplateIds){
    var url = Globals.contextPath +  '/site/template/selectTemplate.do?type=selectTemplate';
	if($defaultCategoryId!=null){
		url += '&defaultCategoryId=' + $defaultCategoryId;
	}
	if($defaultTemplateIds != null){
		for(var i=0;i<$defaultTemplateIds.length;i++){
			url += '&defaultTemplateId=' + $defaultTemplateIds[i];
		}
	}
    return showDialog(url, null, 580,330);
}

 /**
   * 选择网站下的模板
   * @param domain 网站域名 如果不指定则使用当前域名
   * @return null 或 SiteTemplate,
   * @throws Exception
   */
SiteDialog.selectTemplateBySite = function($domain){
    var url = Globals.contextPath + '/site/template/selectTemplate.do?type=selectTemplateBySite';
    if($domain!=null){
    	url += '&domain=' + $domain
    }
    return showDialog(url, null, 500,330);
}

 /**
   * 选择导航网站模板
   * @param templateIds 模板ID,数组，不可为空
   * @param defaultTemplateIds 默认选中模板ID,数组，不可为空
   * @return null 或 SiteTemplate[] 数组，该数组长度可以为0
   * @throws Exception
   */
SiteDialog.selectNavigateTemplate = function($templateIds,$defaultTemplateIds){
    var url = Globals.contextPath + '/site/template/selectTemplate.do?type=selectNavigateTemplate'
	for(var i=0;i<$templateIds.length;i++){
		url += '&templateId=' + $templateIds[i];
	}
	if($defaultTemplateIds != null){
		for(var i=0;i<$defaultTemplateIds.length;i++){
			url += '&defaultTemplateId=' + $defaultTemplateIds[i];
		}
	}
    return showDialog(url, null, 500,320);
}

/**
   * 选择网站栏目
   * @param templateName 缺省模板name，可以为空
   * @param path 缺省选择中的栏目path，可以为空
   * @param bSingle 是否支持多选，缺省为true,不支持
   * @param openTemplate 是否支持打开其它的网站模版
   * @param title  标题，缺省为“选择栏目”
   * @param sType 过滤掉指定类型
   * @return Channel 对象
   * @throws Exception
   */
SiteDialog.selectChannel= function(templateName, path, bSingle, openTemplate, title ,sType){
	var url = [];
    url.push(Globals.contextPath + '/site/channel/open.do?time='+new Date().getTime());
	url.push('&templateName='+templateName);
    if(path!=null){
	    if(typeof path=="Array"){
	    	for(var i=0;i<path.length;i++){
	    		url.push('&path='+path[i]);
	    	}
		}else{
			url.push('&path='+path);
		}
    }
    if(openTemplate === true)
        url.push('&openTemplate=true');
	if(!bSingle)
	    url.push("&multi=true");
	if(title == null)
    	title = "选择页面";
	//url.push("&title" + title);
	if(sType!=null){
		for(var i=0; i<sType.length; i++)
			url.push('&filterType='+sType[i]);
	}
	var width;
	if(navigator.userAgent.indexOf("MSIE 6.0")>=0){
		width = 410;
	}else{
		width = 400;
	}
    return showDialog(url.join(""), title, width,380);
}


/**
   * 转移栏目
   * @param templateId 模板ID，必须提供
   * @param channelId 栏目ID，必须提供
   * @return true:操作成功
   * @throws Exception
   * @author lichengzhao
   * @modified by lkl
   */
   /**
SiteDialog.moveChannel = function(templateId,channelId){
	    var url='<html:rewrite page="/move.do" module="/site/channel"/>?';
        url+='templateID='+templateId;
		url+='&ID='+channelId;
		
        return showDialog(url,"",400,350);
}
**/
/**
   * 栏目授权
   * @param oTemplate 模板ID，必须提供
   * @param principalId 栏目ID，必须提供
   * @return true:操作成功
   * @throws Exception
   */
SiteDialog.Permission = function(oTemplate){
    var url= Globals.contextPath + '/site/channel/permission.do';
    return showDialog(url,oTemplate,350,370);
}

/**
 * 选择文件夹
 * @param oTempalte 模板对象，必须提供
 * @param path 路径，缺省为根目录
 * @return folder对象
 */
SiteDialog.selectFolder = function(oTempalte,siteFactory) {
	var url = Globals.contextPath + "/site/editor/plugins/folder/folders.jsp";
	var args = new Object();
	args.template = oTempalte;
	args.siteFactory = siteFactory;
	return showDialog(  url, args, 320,280, null, null,  true );	
}

/**
   * 编辑模板的资源目录
   * @param templateId 模板ID，必须提供
   * @param path 路径，缺省为根目录
   * @return true:操作成功
   * @throws Exception
   */
SiteDialog.editResourcesFolder = function(templateId, path){
		var url=Globals.contextPath + '/site/resource/edit.do?';
        url+='templateID='+templateId;
        if(path)
			url += '&path=' + encodeURIComponent(path);
		url += '&timestamp=' + Math.random();
        return showDialog(url,"",260,80);
}

/**
   * 修改模版信息
   * @param templateId 模板ID，必须提供
   * @return SiteTemplate对象
   * @throws Exception
   * @author lichengzhao
   */

SiteDialog.editTemplate = function(templateId){
    var url = Globals.contextPath + '/site/template/edit.do?templateId=' + templateId;
    /*
    var version =isVersion();
	var ds="dialogWidth:300px;dialogHeight:160px;help:no;scrollbars:yes;status:no";
	if(version=='ie6')
		ds="dialogWidth:300px;dialogHeight:210px;help:no;scrollbars:yes;status:no";
	*/
	return showDialog(url,"",300,210);
}
/**
   * 重命名模版显示名称
   * @param templateId 模板ID，必须提供
   * @return SiteTemplate对象
   * @throws Exception
   * @author lichengzhao
   */

SiteDialog.renameTemplate = function(templateId){
    var url = Globals.contextPath + '/site/template/rename.do?templateId=' + templateId;
    /*
    var version =isVersion();
		var ds="dialogWidth:300px;dialogHeight:110px;help:no;scrollbars:yes;status:no";
		if(version=='ie6'){
			ds="dialogWidth:300px;dialogHeight:160px;help:no;scrollbars:yes;status:no";
			}
		if(version=='ie7'){
			ds="dialogWidth:300px;dialogHeight:110px;help:no;scrollbars:yes;status:no";
			}
	*/
	return showDialog(url,"",300,110);
}
/**
   * 管理网站
   * @param templateId 模板ID，可以为空
   * @param $title 对话空的title，可以为空
   * @return 无
   * @throws Exception
   */

SiteDialog.manageSite = function(templateId, $title){
    var url = Globals.contextPath + '/site/management/sitesWapper.jsp?';
  /*  if(templateId!=null)
	    url = url+"templateId=" + templateId + "&";
	if ($title)
		url += "title=" + $title;*/
	var version =isVersion();
		var ds="dialogWidth:800px;dialogHeight:620px;help:no;scrollbars:yes;status:no";
		if(version=='ie6'){
			ds="dialogWidth:807px;dialogHeight:672px;help:no;scrollbars:yes;status:no";
			}
		if(version=='ie7'){
			ds="dialogWidth:800px;dialogHeight:620px;help:no;scrollbars:yes;status:no";
			}	
	return showModalDialog(url,"",ds);
}
/**
   * 管理网站
   * @param siteId 网站ID，可以为空，如果为空，则使用当前域名对应的网站
   * @return Site对象
   * @throws Exception
   */
SiteDialog.editSite = function(siteId){
    var url = Globals.contextPath + '/site/management/edit.do?';
     if(siteId!=null)
	     url = url+"siteID=" + siteId;
	url = url +"&timestamp=" + new Date().getTime();
	return showDialog(url,"",380,220);

}


/**
 *
 * 编辑页面片段
 **/
SiteDialog.editClipFile	=	function(path,definitionId, formDefinition , styleSheets){
        var url=Globals.contextPath + '/site/editor/xrepeaterEditorFrame.jsp?definition='+definitionId+'&path='+path;
        if((formDefinition!=null)&&(formDefinition.length>0))
        url = url+"&formDefinition="+formDefinition;
        var args=new Object();
        args.window=window;        
        if(document.all){
        	args.EditorWindow = window.dialogArguments.window;
        	args.form = window.dialogArguments.form;
        }else{
        	 args.EditorWindow = window.parent.dialogArguments.window;
        	 args.form = window.parent.dialogArguments.form;
        }
        args.definition = definitionId;
        args.styleSheets=styleSheets;
        return showDialog(url,args,600,300);
}

/**
 *
 * 编辑页面片段（表格占位符专用）
 **/
SiteDialog.tableEditClipFile	=	function(path,definitionId, formDefinition){
		path = '/'+ window.parent.dialogArguments.template.name+ path;
        var url=Globals.contextPath + '/site/editor/tableXrepeaterEditorFrame.jsp?definition='+definitionId+'&path='+path;
        if((formDefinition!=null)&&(formDefinition.length>0))
        url = url+"&formDefinition="+formDefinition;
        var args=new Object();
        args.window=window;        
        args.EditorWindow = window.parent.dialogArguments.window;   
        args.form = window.parent.dialogArguments.form;
        args.definition = definitionId;
        return showDialog(url,args,600,300);
}

/**
 * resourceType可能值 null,image,flash
 * 
 */
SiteDialog.resourceSelector = function(oTemplate,resourceType){
	var url = Globals.contextPath+'/site/resource/index.jsp?template='+oTemplate.name;
	switch(resourceType){
	case "image":
		url += "&types=jpg,gif,jpeg,bmp,png,emf,wmf,xmp";
		break;
	case "flash":
		url += "&types=swf,fla";
		break;
	}
	return showDialog(url,null,780,600);
}
