/**
 * 处理 占位符 按钮相关的事件和操作。提供插入占位符的按钮以及相关操作。占位符支持：
 * 1. 插入后，即启动占位符编辑界面。
 * 2. 在用户点击占位符内部代码时，让其选中占位符，而不是内部代码。
 * 3. 双击占位符即启动占位符编辑界面。
 */

var FCKInsertPortletCommand = function(){	
};

FCKInsertPortletCommand.prototype.Execute = function(){
    //set current portlet to null;
    FCKPortlets.CurrentPortlet = null;
    //Open dialog to insert portlet;
    showDialog(  FCKPlugins.Items['portlet'].Path + 'portlets.jsp',
    		                  window,
    		                  408,
    		                  426,
                              null,
                              null,
                              true );
    //if the portlet is successfully inserted, then fire the portlet editing dialog;
    if(FCKPortlets.CurrentPortlet!=null)
      FCKPortlets._ShowPortletDialog(FCKPortlets.CurrentPortlet);
};

FCKInsertPortletCommand.prototype.GetState = function(){
	return FCK_TRISTATE_OFF ;
};

FCKCommands.RegisterCommand( 'Portlet', new FCKInsertPortletCommand() ) ;

//Create the "Portlet" toolbar button.
var oPortletItem = new FCKToolbarButton( 'Portlet', FCKLang['PortletBtn'] ) ;
oPortletItem.IconPath = FCKPlugins.Items['portlet'].Path + 'portlet.gif' ;

FCKToolbarItems.RegisterItem( 'Portlet', oPortletItem ) ;

/**
 * 右键编辑占位符
 */
var FCKEditPortletCommand = function(){}

FCKEditPortletCommand.prototype.Execute = function() {
	FCKPortlets._DBLClickListener();
}

FCKEditPortletCommand.prototype.GetState = function()
{
	return FCK_TRISTATE_OFF ;
}

FCKCommands.RegisterCommand( 'EditPortlet', new FCKEditPortletCommand() ) ;


// The object used for all Portlet operations.
var FCKPortlets = new Object() ;

/**
 * 将占位符添加到页面上去
 * @param type :占位符类型
 * @param id   :占位符唯一标识
 * @param name :占位符显示名称
 */
FCKPortlets.Add = function( type ,id , name )
{
    FCKUndo.SaveUndoStep() ;
    if(document.all){
    	//Insert portlet html into current document body.
        var html="<div atomicselection=\"true\" contenteditable=\"false\" class=\"portletWindow\"  type=\"";
        html+= type+"\"  id=\""+id+"\"  title=\""+name+"\" preferences=\"\"></div>";
    	oDiv = FCK.EditorDocument.createElement( html ) ;
    }else{
    	//创建oDiv元素 IE会自动处理createElement里面的HTML字符串，而其他浏览器不会
    	oDiv = FCK.EditorDocument.createElement("DIV");
    	oDiv.setAttribute("atomicselection","true");
		oDiv.setAttribute("contenteditable","false");
    	oDiv.setAttribute("title",name);
    	oDiv.setAttribute("preferences","");
    	oDiv.type=type;
    	oDiv.id = id;
    	oDiv.className = "portletWindow";
    }
    oDiv.innerHTML=name;
    
    
    oDiv.onresizestart = function()
    {
	FCK.EditorWindow.event.returnValue = false;
	return false ;
    }
    //为了避免占位符被强制换行，将div标签暂时设置为非块标签
    var saved=FCKListsLib.BlockElements['div'];
    FCKListsLib.BlockElements['div']=null;
    oDiv = FCK.InsertElement( oDiv ) ;
    FCKListsLib.BlockElements['div']=saved;

    FCKPortlets.CurrentPortlet=oDiv;
    //Update the editing file;
 //  alert(FCK.EditorDocument.body.innerHTML);
//    var sHtml = FCK.GetXHTML( FCKConfig.FormatSource ) ;

}


/**
 * 判断一个节点是不是portlet节点或者在portletNode里面
 */
FCKPortlets.getContainedPortletNode = function(element){
    var node=element;
    var portletNode = null;
    while(node!=null){
      if(FCKPortlets.IsPortletNode(node)){
    	  portletNode = node;
      }
      node=node.parentNode;
    }
    return portletNode;
}

/**
 * 获取所在的form节点
 *
 */
FCKPortlets.getContainedFormNode = function(element){
    var node=element;
    while(node!=null){
      if(node.tagName.toLowerCase()=='form'){
            return node;
      }else if(node.tagName.toLowerCase() == "body"){
    	  return null;
      }
      node=node.parentNode;
    }
    return null;
}

/**
 * 判断一个节点是不是portlet节点或者在portletNode里面
 */
FCKPortlets.IsPortletNode = function(node){
      return (node.tagName == 'DIV' && node.className == 'portletWindow');
}

/*
 * 高亮占位符
 */
FCKPortlets.HighLight = function(portlet){
	var oTextArea = FCK.EditingArea.Textarea;
	var portletId = portlet.id;
	var sHtml = FCK.GetData();
	var LStart = sHtml.search(new RegExp("<fulong:portlet(.*?)id=\""+portletId+"\"([\\s\\S]*?)</fulong:portlet>","i"));
	if(LStart<0){
		return;
	}
	var portletXml = RegExp["$&"];
	var LEnd = LStart+portletXml.length;
	if(FCKBrowserInfo.IsIE){
		//计算字符串长度时，\n被当成两个字符
		//而textRange把\n当成一个字符,因此计算开始偏移量时，LStart需要减去\n的个数
		var start = LStart;
		for(var i=start;i>=0;i--){
			if(sHtml.charAt(i)=="\n"){
				start --;
			}
		}
		var end = sHtml.length-LEnd;
		for(var i =LEnd;i<sHtml.length;i++){
			if(sHtml.charAt(i)=="\n"){
				end --;
			}
		}
		var range = oTextArea.createTextRange();
		range.moveStart("character",start);
		range.moveEnd("character",-end);
		range.select();
		oTextarea.focus();
	}else{
		oTextArea.select();   
		oTextArea.selectionStart = LStart;   
		oTextArea.selectionEnd = LEnd;   
	}
}


/**
 * 在设置完html代码后的一些预处理
 */
FCKPortlets.OnAfterSetHTML= function(){
  //代码编辑状态
  if ( FCK.EditMode != FCK_EDITMODE_WYSIWYG ){
    FCK.EditingArea.Textarea.wrap='off';
    //处理高亮,如果有占位符被选中,则高亮占位符
    if(FCKPortlets.CurrentPortlet!=null){
    	FCKPortlets.HighLight(FCKPortlets.CurrentPortlet);
		FCKPortlets.CurrentPortlet = null;
    }
  } else{
      //页面编辑状态
	  /*
	  for(var is in FCK.EditorDocument){
		  alert(is+"FCK.EditorDocument["+is+"]是："+FCK.EditorDocument[is])
	  }*/
	  //FCKTools.AddEventListener( FCK.EditorDocument, 'dblclick', "alert(12312321)") ;
      FCKTools.AddEventListener( FCK.EditorDocument, 'click', FCKPortlets._ClickListener) ;
      
      if(FCKBrowserInfo.IsIE){
    	  FCKTools.AddEventListener( FCK.EditorDocument, 'dblclick', FCKPortlets._DBLClickListener) ;
      }else{
    	  FCK.EditorDocument.addEventListener( 'dblclick', FCKPortlets._DBLClickListener, true )
      }
      /*
       * 为解决编辑器加载慢的问题，占位符的mouseover和mouseout事件由 behavior: url(/portal/portlet.htc 方式加载
       * 改为由js加载，极大减少下载htc文件的次数和缩短加载编辑器的时间。
       */
      FCKTools.AddEventListener( FCK.EditorDocument, 'mouseover', FCKPortlets._MouseoverListener ) ;
      FCKTools.AddEventListener( FCK.EditorDocument, 'mouseout', FCKPortlets._MouseoutListener ) ;
  }
}


FCK.Events.AttachEvent( 'OnAfterSetHTML', FCKPortlets.OnAfterSetHTML );
/**
 * 单击时,选中这个占位符
 */
FCKPortlets._ClickListener = function( event )
{		
      FCKPortlets.CurrentPortlet=null;
      var portlet=FCKPortlets.getContainedPortletNode(event.srcElement);
      if(portlet!=null){
        //FCKSelection.SelectNode(portlet);
        FCKPortlets.CurrentPortlet=portlet;
      }
      //火狐下，添加对该div选中
      
      /*
      if(!document.all){
    	  
    	  /*
    	   * 试图对portletDIV添加外层框架 左顶角 右上角 左下角 右下角 各有一个小框
    	   *
    	  var frameDiv = document.createElement("DIV");
    	  
    	  
    	  var left = document.createElement("DIV");
    	  
    	  frameDiv.style.width = (getFirstElement(portlet).offsetWidth+5)+"px";
    	  frameDiv.style.height = (getFirstElement(portlet).offsetHeight+5)+"px";
    	  
    	  alert("portlet.offsetHeight="+getFirstElement(portlet).offsetHeight)
    	  alert("getFirstElement(portlet).offsetWidth="+getFirstElement(portlet).offsetWidth)
    	  
    	  left.style.border = "#000000 1px  solid";
    	  left.style.height = "5px";
    	  left.style.width = "5px";
    	  
    	  var right = left.cloneNode(false);
    	  var leftBottom = left.cloneNode(false);
    	  var rightBottom = left.cloneNode(false);
    	  
    	  left.style.cssFloat = "left";
    	  right.style.cssFloat = "right";
    	  leftBottom.style.cssFloat = "left";
    	  rightBottom.style.cssFloat = "right";
    	  
    	  left.style.marginLeft = "-3px";
    	  right.style.marginRight = "-4px";
    	  leftBottom.style.marginLeft = "-3px";
    	  rightBottom.style.marginRight = "-4px";
    	  
    	  left.style.marginTop = "-2px";
    	  right.style.marginTop = "-2px";
    	  leftBottom.style.marginTop = "-4px";
    	  rightBottom.style.marginTop = "-4px";
    	  
    	  //portlet.style.display = "none";
    	  portlet.parentNode.parentNode.insertBefore(frameDiv,portlet.parentNode);
    	  var newPortletNode = portlet.cloneNode();
    	  document.removeChild(portlet);
    	  
    	  
    	  frameDiv.appendChild(left);
    	  frameDiv.appendChild(right);
    	  frameDiv.appendChild(portlet.parentNode);
    	  frameDiv.appendChild(leftBottom);
    	  frameDiv.appendChild(rightBottom);
    	  
    	  alert("frameDiv.innerHTML="+frameDiv.innerHTML)
    	  
    	  portlet.style.border = "#999999 1px dashed";
    	  if(FCKPortlets.LastPortlet != null && FCKPortlets.LastPortlet != undefined){
    		  FCKPortlets.LastPortlet.style.border = "";
    		  FCKPortlets.LastPortlet.className = "portletWindow"
    	  }
    	  对当前单击选中div添加contenteditable属性
    	  portlet.className = "";
    	 alert("portlet.getAttribute(‘contenteditable‘)="+portlet.getAttribute("contenteditable"))
    	  portlet.style.border="#999999 3px dashed"
    	  FCKPortlets.LastPortlet = portlet;
      }*/
}

/**
 * 进入占位符时 高亮
 */
FCKPortlets._MouseoverListener = function( event )
{
      var portlet=FCKPortlets.getContainedPortletNode(event.srcElement);
      if(portlet!=null){
    	  portlet.style.backgroundColor  = "#c0c0c0";
      }
}

/**
 * 离开占位符时 恢复
 */
FCKPortlets._MouseoutListener = function( event )
{
      var portlet=FCKPortlets.getContainedPortletNode(event.srcElement);
      if(portlet!=null){
    	  portlet.style.backgroundColor  = "#f0f0f0";
      }
}

/**
 * 开启自定义复制粘贴功能，改功能主要针对占位符的复制和粘贴进行处理.
 * 复制占位符，不仅会复制占位符的html代码，同时也会记录下占位符的xml代码及所属栏目
 * 粘贴占位符时，找出所有占位符的xml源码及其所属栏目，如果占位符含有片段，使用ajax请求服务端，复制占位符的片段到当前栏目下
 */
if(FCKConfig.UseCustomPaste){
	/* 
	 * 覆盖fck 复制命令
	 */
	FCKCopyCommand.prototype.Execute = function(){
		//源码状态使用浏览器提供的默认复制功能
		if ( FCK.EditMode == FCK_EDITMODE_SOURCE ){
	    	return false;
	    }
		var range = new FCKDomRange( FCK.EditorWindow ) ;
		range.MoveToSelection() ;
		
		var docFrag = range.CloneContents() ;
		var portlets = FCKPortlets.getChildrenPortletNode(docFrag.RootNode);
		for(var i=0;i<portlets.length;i++){
			//把占位符的xml代码及占位符所属页面地址作为div的属性存储起来
			portlets[i].xml = FCKTools.HTMLEncode(FCK.PorteltMap[portlets[i].id].xml);
			portlets[i].channelPath = oChannel.path;
		}
		window.clipboardData.setData("Text",docFrag.RootNode.innerHTML);
		range.Release();
	};
	
	/*
	 * 覆盖原实现	将 FCK.ExecuteNamedCommand( 'Paste' ) ;
	 * 	改成自己的实现   FCK.PasteHTML()
	 */
	FCK._ExecPaste = function()
	{
		// As we call ExecuteNamedCommand('Paste'), it would enter in a loop. So, let's use a semaphore.
		if ( FCK._PasteIsRunning )
			return true ;
	
		if ( FCKConfig.ForcePasteAsPlainText )
		{
			FCK.PasteAsPlainText() ;
			return false ;
		}
	
		var sHTML = FCK._CheckIsPastingEnabled( true ) ;
	
		if ( sHTML === false ){
			//FCKTools.RunFunction( FCKDialog.OpenDialog, FCKDialog, ['FCKDialog_Paste', FCKLang.Paste, 'dialog/fck_paste.html', 400, 330, 'Security'] ) ;
		}else {
			if ( FCKConfig.AutoDetectPasteFromWord && sHTML.length > 0 )
			{
				var re = /<\w[^>]*(( class="?MsoNormal"?)|(="mso-))/gi ;
				if ( re.test( sHTML ) )
				{
					if ( confirm( FCKLang.PasteWordConfirm ) )
					{
						FCK.PasteFromWord() ;
						return false ;
					}
				}
			}
	
			// Instead of inserting the retrieved HTML, let's leave the OS work for us,
			// by calling FCK.ExecuteNamedCommand( 'Paste' ). It could give better results.
	
			// Enable the semaphore to avoid a loop.
			FCK._PasteIsRunning = true ;
	
			//FCK.ExecuteNamedCommand( 'Paste' ) ;
			FCK.PasteHTML();
	
			// Removes the semaphore.
			delete FCK._PasteIsRunning ;
		}
	
		// Let's always make a custom implementation (return false), otherwise
		// the new Keyboard Handler may conflict with this code, and the CTRL+V code
		// could result in a simple "V" being pasted.
		return false ;
	}
}

/**
 * 添加CloneContents方法
 * 复制range内容到documentFragment上
 */
FCKDomRange.prototype.CloneContents = function()
{
	if ( this._Range )
	{
		var docFrag = this._Range.cloneContents() ;
		this._UpdateElementInfo() ;
		return docFrag ;
	}
}

/*
 * 获取element元素及其子元素中所有不被其他占位符包含的的占位符
 */
FCKPortlets.getChildrenPortletNode = function( element )
{
	var ret = new Array();
	if(FCKPortlets.IsPortletNode(element)){
		ret.push(element);
		return ret;
	}
	
	var divs = element.getElementsByTagName("div");
	for(var i=0;i<divs.length;i++){
		if(divs[i].className == 'portletWindow'&&FCKPortlets.getContainedPortletNode(divs[i])==divs[i]){
			ret.push(divs[i]);
		}
	}
	return ret;
}

/**
 * 粘贴占位符
 */
FCK.PasteHTML = function(){
	var sHTML = window.clipboardData.getData("Text");
	var docFrag = new FCKDocumentFragment( FCK.EditorDocument ) ;
	docFrag.AppendHtml(sHTML);
	var portlets = FCKPortlets.getChildrenPortletNode(docFrag.RootNode);
	
	for(var i=0;i<portlets.length;i++){
		//给占位符重新生成ID，并且去掉portlet节点上的xml属性
		var posfix=""+new Date().getTime();
        posfix=posfix.substring(posfix.length-6);
        var portletId = "pt"+posfix;
		portlets[i].id = portletId;
		FCK.PorteltMap[portletId] = new Object();
		var xml = FCKTools.HTMLDecode(portlets[i].xml).trim();
		
		if(!new RegExp("^(<fulong:portlet(.*?)id=\")(.*?)(\"(.*)</fulong:portlet>)$","i").test(xml)){
			continue;
		}
		var orignalPortletId = RegExp["$3"];
		var channelPath = portlets[i].channelPath;
		portlets[i].removeAttribute("channelPath");
		
		//替换xml中的portletId
		xml = xml.replace(new RegExp("^(<fulong:portlet(.*?)id=\")(.*?)(\"(.*)</fulong:portlet>)$","i"),"$1"+portletId+"$4")
		FCK.PorteltMap[portletId].xml = xml;
		portlets[i].removeAttribute("xml");
		
		//复制片段
		if(FCKPortlets.needCopyClip(portlets[i])){
			FCKPortlets.copyClip(orignalPortletId,channelPath,portletId,oChannel.path);
		}
	}
	
	FCK.InsertHtml(docFrag.RootNode.innerHTML) ;
}

/**
 * 判断占位符是否含片段
 * @param portlet		占位符htmlDom对象
 * @return boolean
 */
FCKPortlets.needCopyClip = function(portlet){
	//需要复制片段的占位符类型
	var portlets = ["content-xrepeater","content-listXrepeater","content-tableXrepeater","nodeDefinition-xrepeater",
			"weekCalendar","monthCalendar","yearCalendar","mixNodeDefinition-list","conditions","parent","child","reference"]; 
	return portlets.contains(portlet.type);
}

/**
 * 如果占位符含片段，复制占位符的片段
 * @param orignalPortletId		之前的展位符id
 * @param orignalChannelPath	之前栏目的路径
 * @param portletId				目标占位符id
 * @param channelPath			目标栏目
 * @return
 */
FCKPortlets.copyClip = function(orignalPortletId,orignalChannelPath,portletId,channelPath){
	 var url = Globals.contextPath+"/site/editor/copyClip.do";
	 url = url+"?orignalPortletId="+orignalPortletId;	 
	 url = url+"&orignalChannelPath="+orignalChannelPath;
	 url = url+"&portletId="+portletId;
	 url = url+"&channelPath="+channelPath;
	 url = url+"&timestamp="+new Date().getTime();
	 var req = getXMLHttpRequest();
	 req.open("GET",url,false);
	 req.send(null);
	 return req.status == 200;
}

/**
 * 双击时，启动编辑界面
 */
FCKPortlets._DBLClickListener = function( event )
{
	if (event) {
		FCKPortlets.CurrentPortlet=null;
		var portlet=FCKPortlets.getContainedPortletNode(event.srcElement);
		if(portlet!=null) {
			//FCKSelection.SelectNode(portlet);
			FCKPortlets.CurrentPortlet=portlet;
			//启动编辑界面
			FCKPortlets._ShowPortletDialog(portlet);
		}
	} else {
		var temp = FCKSelection.GetSelectedElement();
		if (!temp)
			temp = FCKSelection.MoveToAncestorNode("DIV");
		var portlet;
		while (temp) {
			portlet = temp;
			temp = FCKPortlets.getContainedPortletNode(temp.parentNode);
		}
		FCKPortlets.CurrentPortlet=null;
		if(portlet!=null) {
			FCKSelection.SelectNode(portlet);
			FCKPortlets.CurrentPortlet=portlet;
			//启动编辑界面
			FCKPortlets._ShowPortletDialog(portlet);
		}
	}
}
/**
 * 显示编辑占位符的对话框
 */
FCKPortlets._ShowPortletDialog = function(portlet){
      var portletId=portlet.id;
      var param=new Object();
      param.page=oChannel.path;
      param.window=window;
      param.document=document;
      param.portlet=portletId;
      if(portlet.type){
    	  param.type=portlet.type;
      }else if(portlet.getAttribute("type") && (typeof portlet.type == "undefined")){
    	  param.type=portlet.getAttribute("type");
      }
    //  param.preferences=decodeURIComponent(portlet.preferences);
      if(FCK.PorteltMap[portletId])
    	  param.preferences=FCK.PorteltMap[portletId].xml;
      else
    	  param.preferences='<fulong:portlet id="'+portletId+'" type="'+portlet.type+'"></fulong:portlet>';
      param.channel = oChannel;
      param.template = oTemplate;
      param.FCKDocument = FCK.EditorDocument;
      if(oChannel.definition!=null)
          param.definition=oChannel.definition;
      if(window.dialogArguments!=null){    	  
          param.definition=window.dialogArguments.definition;
      }
      /*if(PortletPage.formDefinition!=null)
          param.formDefinition=PortletPage.formDefinition;
      if(PortletPage.node!=null)
        param.node=PortletPage.node;*/
      if(window.dialogArguments!=null){    	  
          param.form=window.dialogArguments.form;
      }
      var oForm = FCKPortlets.getContainedFormNode(portlet);
      if(oForm!=null)
            param.form=oForm;
//      param.scripts=scripts;
      //获取页面样式，为占位符编辑器提供样式选择
      param.styleSheets=new Array();
      var num=0;
      var sheets=FCK.EditorDocument.styleSheets;
      for(var i=0;i<sheets.length;i++){
        var href=sheets[i].href;
        if(href!=null &&　(!((href.indexOf('/fck_')>=0)||(href.indexOf('/portal/')>=0)||(href.length==0)||(href.indexOf('/ide/')>=0))))
           param.styleSheets.push(sheets[i]);
      }
      // modified by mali 2010-7-22
      if((typeof styleSheets!="undefined") && styleSheets!=null)
    	  param.styleSheets = param.styleSheets.concat(styleSheets)
	var arr;
	if(document.all){
		var arr = showDialog(FCKPlugins.Items['portlet'].Path+"edit.jsp", param,508,430);
	}else{
		var arr = showDialog(FCKPlugins.Items['portlet'].Path+"editFrame.jsp", param,508,430);
	}
     //var arr = showModalDialog(FCKPlugins.Items['portlet'].Path+"edit.jsp", param, "dialogWidth:500px;dialogHeight:430px;help:no;scrollbars:yes;status:no");
     if(arr!=null){
        var saved=FCKListsLib.BlockElements['div'];
        FCKListsLib.BlockElements['div']=null;
        var newObj=document.createElement("div");
        newObj.innerHTML=arr.html;
        FCK.PorteltMap[portletId] = new Object();
        FCK.PorteltMap[portletId].xml=arr.xml;
        FCK.PorteltMap[portletId].html=arr.html;
        if(document.all){
        	portlet.insertAdjacentElement("afterEnd",newObj.children[0]);
        	portlet.removeNode(true);
        }else{
        	//重构inserAdjaventElement Firefox实现
        	portlet.parentNode.insertBefore(newObj.children[0],portlet.nextSibling);
        	portlet.parentNode.removeChild(portlet);
        }

        FCKListsLib.BlockElements['div']=saved;
        //FCKPortlets.CurrentPortlet=newObj;
      }else{
    	if(FCK.PorteltMap[portletId] == null){
    		var oElement = FCK.EditorDocument.getElementById(portletId);
    		if(oElement!=null){
    			oElement.parentNode.removeChild(oElement);
    		}
    	}
      }
     }

