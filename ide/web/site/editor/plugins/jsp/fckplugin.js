
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
	showDialog(  FCKPlugins.Items['jsp'].Path + 'property.jsp',
              window,
              510,
              400,
              null,
              null,
              true );
 };
FCKDEditChannelCommand.prototype.GetState = function()
{
	return checkout?FCK_TRISTATE_OFF:FCK_TRISTATE_DISABLED;
 };

FCKCommands.RegisterCommand( 'EditChannel', new FCKDEditChannelCommand() ) ;

var oNewChannelItem = new FCKToolbarButton( 'EditChannel', FCKLang['EditChannelBtn'],FCKLang['EditChannelTitle'] ) ;
oNewChannelItem.IconPath = FCKPlugins.Items['channel'].Path + 'property.gif' ;
oNewChannelItem.Style = FCK_TOOLBARITEM_ICONTEXT ;

FCKToolbarItems.RegisterItem( 'EditChannel', oNewChannelItem ) ;


/**
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
var FCKJspProcessor = function(){
	FCK.HTMLContent   = ""; //缓存的HTML内容，
	FCK.JspContent    = ""; //缓存的源代码
	FCK.PorteltMap    = new Array();
	FCK.BuiltInTaglib   = new Array('<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="fulong"%>','<%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>',
			'<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>','<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>',
			'<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>','<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>'
		);
	FCK.BuiltInPage   = new Array('<%@page contentType=\"'+oChannel.getContentType()+'\" info=\"'+oChannel.displayName+'\"%>');
	//FCKRegexLib.PortalScript= /portal\.js\.jsp/i ;
	FCKRegexLib.Portlet = /(<fulong\:portlet)([\s\S]*?)(<\/fulong\:portlet>)/gi ;
	FCKRegexLib.JspPageInstruction= /(<\%\@page)(.*?)(\%\>)/gi ;
	FCKRegexLib.JspTaglibInstruction= /(<\%\@taglib)(.*?)(\%\>)/gi ;
	FCKRegexLib.Title = /(<title)(.*?)(<\/title>)/gi ;
	FCKRegexLib.Title1 = /(<site\:title)(.*?)(<\/site\:title\>)/gi ;
	FCKRegexLib.Title2 = /(<site\:title2)(.*?)(<\/site\:title2\>)/gi ;
	FCKRegexLib.SiteScript = /(<site:script(.*?)<\/site:script>)|(<site:script(.*?)\/>)/gi ;
	FCKRegexLib.Script = /<script(.*?)src=(\'|\")(.*?)(\'|\")(.*?)<\/script>/gi ;
	//FCK.PortalScript  = "<script type=\"text/javascript\" language=\"javascript\" src=\"/portal/portal.js.jsp\"></script>";
	FCK.Title = null; //缓存的标题;
	FCK.Html = null; //缓存的Channel标签前半部分；	
	FCK.SiteScript = null; //cache site:script tag
	FCK.Script = null; //cache script tag
	FCK.JspPageInstructions = null; //缓存的Jsp tag指令标签
	FCK.JspTaglibInstructions = null; //缓存的Jsp taglib指令标签
	//FCK.NewScripts = new Array();//调整后的脚本，如果用户使用的页面属性设置，则将脚本缓存在这里，切换到编辑模式的时候再清空；
	//FCK.NewScripts.Validate = false; //标记为无效；
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
FCKJspProcessor.prototype.ConvertToHtml = function( data ){
   // Save the DOCTYPE.
    FCK.DocTypeDeclaration = data.match( FCKRegexLib.DocTypeTag );   
    
    //保存 jsp page预处理标签;
    data = this._ProcessJspPageInstructions(data);
    
    //保存 jsp taglib预处理标签;添加默认的jsp标签声明
    data = this._ProcessJspTaglibInstructions(data);
   
    //处理<site:html>标签
    data = data.replace(/<site:html(.*?)>/g,FCK.DataProcessor._ProcessHtmlTag);
    data = data.replace(/<\/site:html>/g,"</html>");
    //处理site：script标签
    data = data.replace(FCKRegexLib.SiteScript,FCK.DataProcessor._ProcessScriptTag);
    
    FCK.Script = new Array();
    //保存 script标签;
    data = data.replace(FCKRegexLib.Script,FCK.DataProcessor._ProcessScriptTags);
    // Check if the <body> tag is available.
    if ( !FCKRegexLib.HasBodyTag.test( data ) )
            data = '<body>' + data + '</body>' ;
    // Check if the <html> tag is available.
    if ( !FCKRegexLib.HtmlOpener.test( data ) )
        data = '<html  dir="' + FCKConfig.ContentLangDirection + '">' + data + '</html>' ;

    // Check if the <head> tag is available.
    if ( !FCKRegexLib.HeadOpener.test( data ) )   	
    	data = data.replace( FCKRegexLib.HtmlOpener, '$&<head><title>'+oChannel.displayName+'</title></head>' ) ;
    //处理title标签；   
    data = data.replace(FCKRegexLib.Title1, FCK.DataProcessor._ProcessTitle1Tag);
    data = data.replace(FCKRegexLib.Title, FCK.DataProcessor._ProcessTitleTag);
    data = data.replace(FCKRegexLib.Title2, FCK.DataProcessor._ProcessTitle2Tag);
    //将占位符转化为对应的html代码
    data = data.replace(FCKRegexLib.Portlet, FCK.DataProcessor._ProcessPortletTag);
    //添加编辑时的样式
    var sHeadExtra = '' ;
    sHeadExtra += FCK.DataProcessor._LoadStyleTags() ;
    data = data.replace( FCKRegexLib.HeadCloser, sHeadExtra + '$&' ) ;
    return data ;
};


/**
 * Process PageInstructions <title> Tag;
 * @param data
 * @return
 */
FCKJspProcessor.prototype._ProcessJspPageInstructions = function(data){
	  //保存 jsp page预处理标签;
    FCK.JspPageInstructions = data.match(FCKRegexLib.JspPageInstruction);
    var flag = true;
    if(FCK.JspPageInstructions==null){
		FCK.JspPageInstructions = new Array();
	}
    for(var i=0;i<FCK.JspPageInstructions.length;i++){
    	if(FCK.JspPageInstructions[i].indexOf("contentType")>0){
    		flag = false;
    	}
    }
    if(flag){
    	for(var i=0;i<FCK.BuiltInPage.length;i++){
    		FCK.JspPageInstructions.unshift(FCK.BuiltInPage[i]);
    	}
    }
    data = data.replace(FCKRegexLib.JspPageInstruction, '');
	return data;
};

/**
 * Process PageInstructions <title> Tag;
 * @param data
 * @return
 */
FCKJspProcessor.prototype._ProcessJspTaglibInstructions = function(data){
	FCK.JspTaglibInstructions = data.match(FCKRegexLib.JspTaglibInstruction);
	if(FCK.JspTaglibInstructions==null){
		FCK.JspTaglibInstructions = new Array();
	}
    for(var i=0;i<FCK.BuiltInTaglib.length;i++){
    	if(!FCK.JspTaglibInstructions.contains(FCK.BuiltInTaglib[i])){
    		FCK.JspTaglibInstructions.push(FCK.BuiltInTaglib[i]);
    	}
    }
    data = data.replace(FCKRegexLib.JspTaglibInstruction, '');
	return data;
};

/**
 * Process Html <title> Tag;
 * @param xml
 * @return
 */
FCKJspProcessor.prototype._ProcessTitleTag = function(xml){
	var data = xml;
//	data = data.replace("\<title\>","<site:title2>");
//	data = data.replace("\<\/title\>","</site:title2>");
	FCK.Title = data;
	return xml;
};

/**
 * 处理老版本的site:title tag;
 * @param xml
 * @return
 */
FCKJspProcessor.prototype._ProcessTitle1Tag = function(xml){
	return oChannel.displayName;
};

/**
 * 处理 site:title2 tag;
 * @param xml
 * @return
 */
FCKJspProcessor.prototype._ProcessTitle2Tag =  function(xml){
	FCK.Title = xml;
	var title  = xml;
	title = title.replace(/<site:title2(.*?)>/,"<title>");
	title = title.replace("\<\/site\:title2\>","</title>");    
	return title;	
};

/**
 * 处理site:html tag;
 * @param xml
 * @return
 */
FCKJspProcessor.prototype._ProcessHtmlTag = function(xml){
	FCK.Html = xml;
	return "<html>";
};

/**
 * 处理site:scritp tag;
 * @param xml
 * @return
 */
FCKJspProcessor.prototype._ProcessScriptTag = function(xml){
	FCK.SiteScript = xml;
	return "";
};

/**
 * 处理site:scritp tag;
 * @param xml
 * @return
 */
FCKJspProcessor.prototype._ProcessScriptTags = function(xml,$2,$3,$4){
	FCK.Script.push($4);
	return "";
};

/**
 * 加载编辑时的样式
 */
FCKJspProcessor.prototype._LoadStyleTags  =   function () {
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
FCKJspProcessor.prototype._ProcessPortletTag =  function(xml){
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
FCKJspProcessor.prototype._ParsePortletID =   function (xml){
    var idexpr=/(id\=['"])(\S*?)(['"])/;
    var idstring=idexpr.exec(xml);    
    if(idstring!=null)
      return idstring[2];
    return null;
};
/**
 * 从占位符XML片段中提炼其类型
 */
FCKJspProcessor.prototype._ParsePortletType =   function (xml){
    var idexpr=/(type\=['"])(\S*?)(['"])/;
    var idstring=idexpr.exec(xml);
    if(idstring!=null)
      return idstring[2];    
    return null;
};
/**
 * 从占位符XML片段中提炼其类型
 */
FCKJspProcessor.prototype._ParsePortletPreference =   function (xml){
    var portletReg=/(<fulong\:portlet)(.*?)>/g;
    var result=xml.replace(portletReg,"");
    result=result.replace("</fulong:portlet>","");
    return result;
};
/**
 * 根据Id 和type产生占位符的html代码
 */
FCKJspProcessor.prototype._GenPortletHTML  = function( id , type ,xml){
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
    	return ret.trim();
    else
    	html+='占位符出错，请重新配置。';    
    html+='</div>';
  
    /*删除多加div层 modified by lkl
    var html='<div contenteditable="false" class="portletWindow" id="';
    html+=id;
    html+='" type="';
    html+= type+ '">';
//    html+='" preferences="';
//    html+= encodeURIComponent(pref);
    var html = "";
    if((req.status == 200)&&(ret.length>0))
    	html+=ret;
    else{
    	html='<div contenteditable="false" class="portletWindow" id="';
        html+=id;
        html+='" type="';
        html+= type+ '">';
    	html+='占位符出错，请重新配置。';
    	html+='</div>';
    }    */
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
FCKJspProcessor.prototype.ConvertToDataFormat = function( root, excludeRoot, ignoreIfEmptyParagraph, format ){
	var rootNode=root.cloneNode(true);  
	var portletTypes = this._getPortletTypes(rootNode);
    this._RestorePortlet(rootNode);
    //FCKXHtml.GetXHTML( rootNode, !excludeRoot, format )改为FCKXHtml.GetXHTML( root, !excludeRoot, format )
    //cloneNode有bug，某些情况下root和rootNode不一致。。比如area的coords属性
    var data = FCKXHtml.GetXHTML( rootNode, !excludeRoot, format ) ;
    data = this._RestoreJspInstructions(data);   
    data = this._RestoreHtml(data);
    data = this._RestoreTitle(data);
    data = this._RestoreScript(data,portletTypes);
    data = this._RestoreScripts(data);
    data = this._RestoreFrameSet(data);
    if ( ignoreIfEmptyParagraph && FCKRegexLib.EmptyOutParagraph.test( data ) )
    	return '' ;
    if(document.all){
    	rootNode.removeNode(true);  	
    }else{
    	if(rootNode.hasChildNodes()){
    		for(var i=0;i<rootNode.childNodes.length;i++){
    			rootNode.removeChild(rootNode.childNodes[i]);
    		}
    	}
    	//rootNode.parentNode.removeChild(rootNode);
    }
   return data;
};
/**
 * 将占位符的html标签转成xml格式；
 * 占位符html格式标签是class="portletWindow"为特征，对应的xml缓存在FCK.PorteltMap数组的xml字段中；
 * @param rootNode
 * @return
 */
FCKJspProcessor.prototype._RestorePortlet = function(rootNode){
	  //将占位符转换成fulong:portlet标签  
    var portlets=rootNode.getElementsByTagName("DIV");
    //将占位符div节点转化成对应的标签
    for(var i=0;i<portlets.length;i++){
        if(portlets[i].className=='portletWindow'){
        	if(FCK.PorteltMap[portlets[i].id]!=null){
        		//FCK.PorteltMap[portlets[i].id]=new Object();
        	//保存html表示，以便在切换回来时恢复
        	var portletOuterHtml;
        	if(!document.all){
        		var clonePortlet = portlets[i].cloneNode(true);
        		var containerNode = document.createElement("DIV");
        		containerNode.appendChild(clonePortlet);
        		portletOuterHtml = containerNode.innerHTML.trim();
        		containerNode.removeChild(clonePortlet);
        	}else{
        		portletOuterHtml=portlets[i].outerHTML.trim();
        	}
        	FCK.PorteltMap[portlets[i].id].html=portletOuterHtml;
          //there is some problem when using insertAdjanceHTML directly;
        	var tempNode=rootNode.ownerDocument.createElement("div");
        	tempNode.innerHTML=FCK.PorteltMap[portlets[i].id].xml;
        	if(document.all){
        		portlets[i].insertAdjacentElement('beforeBegin',tempNode.children[0]);
        	}else{
        		//获取portlets[i]的非text的上一节点
        		portlets[i].parentNode.insertBefore(tempNode.children[0],portlets[i]);
        		//insertAdjacentElement('beforeBegin',tempNode.children[0]);
        	}
        	}
        }
    }
    //删除占位符节点
    for(i=portlets.length-1;i>=0;i--){
        if(portlets[i].className=='portletWindow'){
        	if(!document.all){
        		portlets[i].parentNode.removeChild(portlets[i]);
        	}else{
        		portlets[i].removeNode(true);
        	}
      }
    }
};
/**
 * 恢复标题
 * @param rootNode
 * @return
 */
FCKJspProcessor.prototype._RestoreTitle= function(data){
	if(FCK.Title == null)
		FCK.Title="<site:title2>"+oChannel.displayName +"</site:title2>";
	return data.replace(FCKRegexLib.Title, FCK.Title);
};

/**
 * 恢复site:html
 * @param data
 * @return
 */
FCKJspProcessor.prototype._RestoreHtml= function(data){
	if(FCK.Html == null)
		FCK.Html="<site:html>";
	data = data.replace(/<html(.*?)>/g, FCK.Html);
	data = data.replace(/<\/html>/g, "</site:html>");
	return data;
};
/**
 * 获取所有的占位符类型
 * @param rootNode
 * @return
 */
FCKJspProcessor.prototype._getPortletTypes= function(rootNode){
	var portletTypes = new Array();
	var hasForm = rootNode.getElementsByTagName("form");
    if(hasForm&&hasForm.length>0){
    	portletTypes.push("constant");
    }
	var portlets=rootNode.getElementsByTagName("DIV");
    //将占位符div节点转化成对应的标签
    for(var i=0;i<portlets.length;i++){
        if(portlets[i].className=='portletWindow'){
        	if(document.all){
        		if(!portletTypes.contains(portlets[i].type)){
            		portletTypes.push(portlets[i].type);
            	}
        	}else{
        		if(!portletTypes.contains(portlets[i].getAttribute("type"))){
            		portletTypes.push(portlets[i].getAttribute("type"));
            	}
        	}
        }
    }
	return portletTypes;
};
/**
 * 恢复site:script
 * @param rootNode
 * @return
 */
FCKJspProcessor.prototype._RestoreScript= function(data,portletTypes){
	if(FCK.SiteScript == null)
		FCK.SiteScript="<site:script portlets=\"\"></site:script>";
	var portletsStr = portletTypes.join(",");
	FCK.SiteScript = FCK.SiteScript.replace(/(<site:script(.*?)portlets=\")(.*?)(\"(.*?)>)/i,"$1"+portletsStr+"$4");
	data = data.replace( FCKRegexLib.HeadCloser, FCK.SiteScript + '$&' ) ;
	return data;
};

/**
 * 处理通过属性设置的脚本
 * @param rootNode
 * @return
 */
FCKJspProcessor.prototype._RestoreScripts = function(data){
	data = data.replace(FCKRegexLib.Script,"");
	if(FCK.Script==null||FCK.Script.length==0){
		return data;
	}
	var html = "";
	for(var i=0;i<FCK.Script.length;i++){
		html += '<script type="text/javascript" src="'+FCK.Script[i]+'"></script>\n';
	}
	data = data.replace("\<\/head\>", html+"</head>");
	return data;
};
/**
 * 恢复Jsp标签
 * @return
 */
FCKJspProcessor.prototype._RestoreJspInstructions = function(data){
	var ins="";
	if(FCK.JspPageInstructions!=null){
		for(var i=0;i<FCK.JspPageInstructions.length;i++){
			ins += FCK.JspPageInstructions[i]+'\r\n';		
		}
	}
	if(FCK.JspTaglibInstructions!=null){
		for(i=0;i<FCK.JspTaglibInstructions.length;i++){
			ins += FCK.JspTaglibInstructions[i]+'\r\n';		
		}
	}
	data = ins + data;
	return data;
};
/**
 * 处理frameset自动加上contentEditable=true属性的bug
 * @return
 */
FCKJspProcessor.prototype._RestoreFrameSet = function(data){
	return data.replace(/(<frameset[^<>]*?)contenteditable=\"[^<>]*?\"([^<>]*?>)/i,"$1$2");
};
/*
 * Makes any necessary changes to a piece of HTML for insertion in the
 * editor selection position.
 *     @param {String} html The HTML to be fixed.
 */
FCKJspProcessor.prototype.FixHtml = function( html ){
	return html ;
};
/**
 * 在源代码和编辑器之间切换时，原来的实现没有清空TempBin，导致TempBin越来越大，引发内存泄露，系统崩溃；
 */
FCKConfig.ProtectedSource.Revert = function( html, clearBin )
{
	if(html == null)
		return "";
	function _Replace( m, opener, index )
	{
		var protectedValue = clearBin ? FCKTempBin.RemoveElement( index ) : FCKTempBin.Elements[ index ] ;
		// There could be protected source inside another one.
		return FCKConfig.ProtectedSource.Revert( protectedValue, clearBin ) ;
	}

	var regex = new RegExp( "(<|&lt;)!--\\{" + this._CodeTag + "(\\d+)\\}--(>|&gt;)", "g" ) ;
	var ret = html.replace( regex, _Replace ) ;
	return ret;
}
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

FCK.DataProcessor = new FCKJspProcessor();

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
 * 1.为实现对脚本的操作，对于从外部文件引入的脚本(即有src属性的script标记)采用自己的缓存实现，<script type="text/javascript" src="/tempalte001/script/script.js"></script>
 * 直接写在界面上的script(无src属性的script标记)继续采用FCK的实现，使得切换源代码时直接写在界面上的script位置不会变化.<script type="text/javascript"> alert(location); </script>
 * 
 * 2.增加对jsp的支持,对于jsp page指令和taglib指令，把指令缓存在FCK.JspPageInstructions和FCK.JspTaglibInstructions
 * 数组中，以实现内置jsp指令的自动添加，并且jsp指令会自动添加到jsp的头部。对于其他的如include指令 <%@inlcude %>,
 * jsp声明 <%!声明 %>、表达式 <%=表达式 %>、程序代码段/小型指令<%程序代码片段 %>、注释<%-- 注释 --%>等 采用fck的保护机制，
 * 如<%=request.getParamter("path")%>   ———>在html代码中会生成    <--${当前时间毫秒数}${FCKTempBin数组中的索引}-->
 * 这样可以保持代码的位置不变。
 */
FCKConfig.ProtectedSource.RegexEntries = [
	// First of any other protection, we must protect all comments to avoid
	// loosing them (of course, IE related).
	/<!--[\s\S]*?-->/g ,
	
	/<script(?![^<>]*?\ssrc\s*=[^<>]*?)[\s\S]*?<\/script>/gi,	
	
	// <noscript> tags (get lost in IE and messed up in FF).
	/<noscript[\s\S]*?<\/noscript>/gi,
	
	// Protect <object> tags. See #359.
  	/<object[\s\S]+?<\/object>/gi,
  	/<%((?!@)|(@include))([\s\S]*?)%>/gi
	//new RegExp("/("+FCK.BuiltInTaglib.join(")|(")+")/","gi")
  ]
