
 var FCKDEditChannelCommand = function(){
};
FCKDEditChannelCommand.prototype.Execute = function()
{
	if(!oChannel.checkLock()){
		return false;
	}
	showDialog(  FCKPlugins.Items['jspf'].Path + 'property.jsp',
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

/**
 * 数据处理器，在源代码和html代码之间转化
 */
var FCKJspfProcessor = function(){
	FCK.HTMLContent   = ""; //缓存的HTML内容，
	FCK.JspContent    = ""; //缓存的源代码
	FCK.PorteltMap    = new Array();
	FCK.BuiltInTaglib   = new Array('<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="fulong"%>','<%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>',
			'<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>','<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>',
			'<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>','<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>'
		);//系统内置的taglib指令
	FCK.BuiltInPage   = new Array('<%@page contentType=\"'+oChannel.getContentType()+'\" info=\"'+oChannel.displayName+'\"%>');
	//FCKRegexLib.PortalScript= /portal\.js\.jsp/i ;
	FCKRegexLib.Portlet = /(<fulong\:portlet)([\s\S]*?)(<\/fulong\:portlet>)/g ;
	FCKRegexLib.JspInstructions= /(<\%\@)(.*?)(\%\>)/gi ;
	FCKRegexLib.Script = /(<site:script(.*?)<\/site:script>)|(<site:script(.*?)\/>)/g ;
	FCK.Script = null; //cache site:script tag
	FCK.JspInstructions = null; //缓存的Jsp指令标签
	FCK.NewScripts = new Array();//调整后的脚本，如果用户使用的页面属性设置，则将脚本缓存在这里，切换到编辑模式的时候再清空；
	FCK.NewScripts.Validate = false; //标记为无效；
//	FCK.NewStyles = new Array(); //调整后的样式，如果用户使用的页面属性设置，则将样式缓存在这里，切换到编辑模式的时候再清空；
//	FCK.NewStyles.Validate = false; //标记为无效；
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
FCKJspfProcessor.prototype.ConvertToHtml = function( data ){
	 //保存 jsp预处理标签;
    data = this._ProcessJspInstructions(data);
    
    //处理site：script标签
    data = data.replace(FCKRegexLib.Script,FCK.DataProcessor._ProcessScriptTag);
    
    //将占位符转化为对应的html代码
    data = data.replace(FCKRegexLib.Portlet, FCK.DataProcessor._ProcessPortletTag);
	 
    var html = FCKConfig.DocType +'<html dir="' + FCKConfig.ContentLangDirection + '"' ;
    // On IE, if you are using a DOCTYPE different of HTML 4 (like
    // XHTML), you must force the vertical scroll to show, otherwise
    // the horizontal one may appear when the page needs vertical scrolling.
    // TODO : Check it with IE7 and make it IE6- if it is the case.
    if ( FCKBrowserInfo.IsIE && FCKConfig.DocType.length > 0 && !FCKRegexLib.Html4DocType.test( FCKConfig.DocType ) )
            html += ' style="overflow-y: scroll"' ;
    html += '><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/><title></title>' +FCK.DataProcessor._LoadStyleTags() +
            '</head><body' + FCKConfig.GetBodyAttributes() + '>' +
            data +'</body></html>' ;
    return html ;
};

/**
 * 处理jsp instruction
 * @param data
 * @return
 */
FCKJspfProcessor.prototype._ProcessJspInstructions = function(data){
	FCK.JspInstructions = data.match(FCKRegexLib.JspInstructions);
	if(FCK.JspInstructions==null){
		FCK.JspInstructions = new Array();
	}
	
	//添加jsp  page指令
	var flag = true;
    for(var i=0;i<FCK.JspInstructions.length;i++){
    	if(FCK.JspInstructions[i].indexOf("contentType")>0){
    		flag = false;
    	}
    }
    if(flag){
    	for(var i=0;i<FCK.BuiltInPage.length;i++){
    		FCK.JspInstructions.unshift(FCK.BuiltInPage[i].replace("${displayName}",oChannel.displayName));
    	}
    }
    
  //添加taglib  taglib指令
    for(var i=0;i<FCK.BuiltInTaglib.length;i++){
    	if(!FCK.JspInstructions.contains(FCK.BuiltInTaglib[i])){
    		FCK.JspInstructions.push(FCK.BuiltInTaglib[i]);
    	}
    }
    data = data.replace(FCKRegexLib.JspInstructions, '');
	
	return data;
};
/**
 * 处理site:scritp tag;
 * @param xml
 * @return
 */
FCKJspfProcessor.prototype._ProcessScriptTag = function(xml){
	FCK.Script = xml;
	return "";
};

/**
 * 加载编辑时的样式
 */
FCKJspfProcessor.prototype._LoadStyleTags  =   function () {
	var sTags = '' ;
    var aCSSs = FCKConfig.EditorAreaCSS ;
    //var sStyles = FCKConfig.EditorAreaStyles ;
    for ( var i = 0 ; i < aCSSs.length ; i++ )
          sTags += '<link id="fckextrasheets" href="' + aCSSs[i] + '" rel="stylesheet" type="text/css" _fcktemp=\"true\" />' ;
    return sTags ;
};

 /**
 * 将XML转化成HTML
 */
FCKJspfProcessor.prototype._ProcessPortletTag =  function(xml){
    var id=FCK.DataProcessor._ParsePortletID(xml);
    var type=FCK.DataProcessor._ParsePortletType(xml);
    if((id!=null)&&(FCK.PorteltMap[id]!=null))
        return FCK.PorteltMap[id].html;
    if((id!=null)&&(FCK.PorteltMap[id]==null)){
        if(FCK.PorteltMap[id] ==null)
        	FCK.PorteltMap[id]=new Object();
        FCK.PorteltMap[id].xml = xml;    
        return FCK.DataProcessor._GenPortletHTML(id,type,xml);
    }
    return xml;
};
/**
 * 提取ID
 * @param xml
 * @return
 */
FCKJspfProcessor.prototype._ParsePortletID =  function (xml){
    var idexpr=/(id\=['"])(\S*?)(['"])/;
    var idstring=idexpr.exec(xml);    
    if(idstring!=null)
      return idstring[2];
    return null;
};
/**
 * 从占位符XML片段中提炼其类型
 */
FCKJspfProcessor.prototype._ParsePortletType =  function (xml){
    var idexpr=/(type\=['"])(\S*?)(['"])/;
    var idstring=idexpr.exec(xml);
    if(idstring!=null)
      return idstring[2];    
    return null;
};
/**
 * 从占位符XML片段中提炼其类型
 */
FCKJspfProcessor.prototype._ParsePortletPreference =   function (xml){
    var portletReg=/(<fulong\:portlet)(.*?)>/g;
    var result=xml.replace(portletReg,"");
    result=result.replace("</fulong:portlet>","");
    return result;
};
/**
 * 根据Id 和type产生占位符的html代码
 */
FCKJspfProcessor.prototype._GenPortletHTML  = function( id , type ,xml){
	 var url =Globals.contextPath+"/site/editor/compilePortlet.do";
	 var content="channel.page="+oChannel.path;	 
	 content=content+"&portlet.pref="+encodeURIComponent(xml);
	 
	 var req=getXMLHttpRequest();
    req.open("POST",url,false);
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
    req.send(content);
    var ret =req.responseText;    
    var html='<div contenteditable="false" class="portletWindow" id="';
    html+=id;
    html+='" type="';
    html+= type+ '">';
//    html+='" preferences="';
//    html+= encodeURIComponent(pref);
    if((req.status == 200)&&(ret.length>0))
    	return ret;
    else
    	html+='占位符出错，请重新配置。';    
    html+='</div>';
    return html;    
    
//    return '<div id="'+id+'" type="'+type+'">占位符出错，请重新配置。</div>';
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
FCKJspfProcessor.prototype.ConvertToDataFormat = function( root, excludeRoot, ignoreIfEmptyParagraph, format ){
	var rootNode=root.cloneNode(true);  
	var portletTypes = this._getPortletTypes(rootNode);
    this._RestorePortlet(rootNode);
    var data = FCKXHtml.GetXHTML( rootNode, !excludeRoot, format ) ;
    data = this._RestoreScript(data,portletTypes);
    data = this._RestoreJspInstructions(data);   
    if ( ignoreIfEmptyParagraph && FCKRegexLib.EmptyOutParagraph.test( data ) )
    	return '' ;
    rootNode.removeNode(true);
  
   return data;
};
/**
 * 将占位符的html标签转成xml格式；
 * 占位符html格式标签是class="portletWindow"为特征，对应的xml缓存在FCK.PorteltMap数组的xml字段中；
 * @param rootNode
 * @return
 */
FCKJspfProcessor.prototype._RestorePortlet = function(rootNode){
	  //将占位符转换成fulong:portlet标签  
    var portlets=rootNode.getElementsByTagName("DIV");
    //将占位符div节点转化成对应的标签
    for(var i=0;i<portlets.length;i++){
        if(portlets[i].className=='portletWindow'){
        	if(FCK.PorteltMap[portlets[i].id]!=null){
        		//FCK.PorteltMap[portlets[i].id]=new Object();
        	//保存html表示，以便在切换回来时恢复
        	FCK.PorteltMap[portlets[i].id].html=portlets[i].outerHTML;
          //there is some problem when using insertAdjanceHTML directly;
        	var tempNode=rootNode.ownerDocument.createElement("div");
        	tempNode.innerHTML=FCK.PorteltMap[portlets[i].id].xml;
        	portlets[i].insertAdjacentElement('beforeBegin',tempNode.children[0]);
        	}
        }
    }
    //删除占位符节点
    for(i=portlets.length-1;i>=0;i--){
        if(portlets[i].className=='portletWindow'){
         portlets[i].removeNode(true);
      }
    }
};

/**
 * 获取所有的占位符类型
 * @param rootNode
 * @return
 */
FCKJspfProcessor.prototype._getPortletTypes= function(rootNode){
	var portletTypes = new Array();
	var hasForm = rootNode.getElementsByTagName("form");
	if(hasForm&&hasForm.length>0){
		portletTypes.push("constant");
	}
	var portlets=rootNode.getElementsByTagName("DIV");
    //将占位符div节点转化成对应的标签
    for(var i=0;i<portlets.length;i++){
        if(portlets[i].className=='portletWindow'){
        	if(!portletTypes.contains(portlets[i].type)){
        		portletTypes.push(portlets[i].type);
        	}
        }
    }
	return portletTypes;
};
/**
 * 恢复
 * @param rootNode
 * @return
 */
FCKJspfProcessor.prototype._RestoreScript= function(data,portletTypes){
	if(FCK.Script == null)
		FCK.Script="<site:script portlets=\"\"></site:script>";
	var portletsStr = portletTypes.join(",");
	FCK.Script = FCK.Script.replace(/(<site:script(.*?)portlets=\")(.*?)(\"(.*?)>)/i,"$1"+portletsStr+"$4");
	data = FCK.Script + '\r\n' +data;
	return data;
};

/**
 * 恢复Jsp标签
 * @return
 */
FCKJspfProcessor.prototype._RestoreJspInstructions = function(data){
	var ins="";
	if(FCK.JspInstructions!=null){
		for(var i=0;i<FCK.JspInstructions.length;i++){
			ins += FCK.JspInstructions[i]+'\r\n';		
		}
	}
	data = ins + data;
	return data;
};
/*
 * Makes any necessary changes to a piece of HTML for insertion in the
 * editor selection position.
 *     @param {String} html The HTML to be fixed.
 */
FCKJspfProcessor.prototype.FixHtml = function( html ){
	return html ;
};
/**
 * 在启动编辑器时调用，覆盖FCK的实现，不是从系统绑定的输入域中获取工作内容，而是根据提供的参数中加载Jsp页面。
 */
FCK.GetLinkedFieldValue = function(){
  var html= oChannel.getWorkingSource();
  if(html==null)
    html="无法获取所要编辑的内容，请选择其它栏目。";
  //加载特定的样式
  //插入预定义的脚本文件
  //if( ! FCKRegexLib.PortalScript.test(html))
   //      html  = html.replace(FCKRegexLib.HeadCloser,  FCK.PortalScript  +'$&');
  return html;
};
/**
 * 覆盖FCK的实现，不需要GetParentForm操作。
 */
FCK.GetParentForm = function(){
};


FCK.DataProcessor = new FCKJspfProcessor();

/**
 * 在源代码和编辑器之间切换时，原来的实现没有清空TempBin，导致TempBin越来越大，引发内存泄露，系统崩溃；
 */
FCKSourceCommand.prototype.Execute = function()
{
	if ( FCKConfig.SourcePopup )	// Until v2.2, it was mandatory for FCKBrowserInfo.IsGecko.
	{
		var iWidth	= FCKConfig.ScreenWidth * 0.65 ;
		var iHeight	= FCKConfig.ScreenHeight * 0.65 ;
		FCKDialog.OpenDialog( 'FCKDialog_Source', FCKLang.Source, 'dialog/fck_source.html', iWidth, iHeight, null, null, true ) ;
	}
	else{
	    FCK.SwitchEditMode() ;
	    if ( FCK.EditMode == FCK_EDITMODE_SOURCE ){
	    	FCKTempBin.Reset();
	    }
	}
}

/**
 * 增加对jsp的支持
 */
FCKConfig.ProtectedSource.RegexEntries = FCKConfig.ProtectedSource.RegexEntries.concat(/<%((?!@)|(@include))([\s\S]*?)%>/gi);
