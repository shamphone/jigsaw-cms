
function makeQueryString(){
	// 取得内容列表页上的checkbox，对选中的内容进行编辑
	// 弹出相应的新页面
	 var selected = CMS.GetContentIDs();
	 var query = "categoryID="+CMS.GetDefinitionID();
	 for(var i=0;i<selected.length;i++)
   	     query = query +"&contentID="+selected[i];
	 return query;
}

//创建内容
/**
 * liulei modified in 2009-12-15
 * 在没有选择内容分类的情况下，如果点击“新建”将会出现服务器错误。
 * 本次修改修正这个错误，在网页上弹出对话框，提醒用户是非法操作。
 */
var oCreateNode= new CMSToolbarButton('createNode', '新建', '在当前分类下新增一条内容');
oCreateNode.IconPath = CMS.Plugins['content'].Path+"newNode.gif";
oCreateNode.Click	=	function()
{	
	if(CMS.GetDefinitionID()==null){
	    alert("请选择内容分类！");
	    return;
	}    
	else if(CMS.GetDefinitionID()=='no-properties-scheme'){
		alert("根分类下不允许创建内容！");
		return;
	}
    else
    	window.open(CMS.BasePath+"content/create.do?categoryID="+CMS.GetDefinitionID(),"");
};
CMSToolbarItems.RegisterItem('createNode',oCreateNode);

// 修改内容
var oEditNode= new CMSToolbarButton('editNode', '修改', '修改当前选中的内容');
oEditNode.IconPath = CMS.Plugins['content'].Path+"editNode.gif";
oEditNode.Click	=	function(){	
	// 取得内容列表页上的checkbox，对选中的内容进行编辑
	// 弹出相应的新页面
	 var ids = CMS.GetContentIDs();	
	 if(ids.length<1){
		alert("请选择待修改的内容！");
		return;
	}
	else
		for (var i=0; i < ids.length; i++){
				var paremeter= "&contentID="+ids[i];
				window.open(CMS.BasePath+"content/edit.do?categoryID="+CMS.GetDefinitionID()+paremeter , "");    
	}
};
CMSToolbarItems.RegisterItem('editNode',oEditNode);

// 批量修改内容
var oBatEditNode= new CMSToolbarButton('batEditNode', '批量修改', '批量修改当前选中的内容');
oBatEditNode.IconPath = CMS.Plugins['content'].Path+"batMod.png";
oBatEditNode.Click	=	function()
{	
	// 取得内容列表页上的checkbox，对选中的内容进行编辑
	// 弹出相应的新页面
	 var ids = CMS.GetContentIDs();	
	 if(ids.length<1)
	 {
		alert("请选择待修改的内容！");
		return;
	 }
	else
	   {
		   var paremeter;
		   for(var i=0;i<ids.length;i++)
			   paremeter = paremeter + "&contentID=" + ids[i];
		   window.open(CMS.BasePath+"content/batEdit.do?categoryID="+CMS.GetDefinitionID()+paremeter , "");
	   }
};
CMSToolbarItems.RegisterItem('batEditNode',oBatEditNode);

// 删除内容列表中被选择的内容
var oDeleteNode= new CMSToolbarButton('deleteNode', '删除', '删除当前选中的内容');
oDeleteNode.IconPath = CMS.Plugins['content'].Path+"deleteNode.gif";
oDeleteNode.Click	=	function(){	
	var query = makeQueryString();
	if( CMS.GetContentIDs().length ==0 ){
		 alert("请选择待删除的内容！");
		 return ;
	}
	if(confirm("确认删除选中的内容?")){
		var url = "content/delete.do?"+ query;
		var oHttp = new HttpRequest(url);
		if(oHttp.Send("get")) 
			parent.frames['list'].location.reload();
		else 
			alert("删除失败！");
	}
};
CMSToolbarItems.RegisterItem('deleteNode',oDeleteNode);

// 复制内容
var oCopyNode= new CMSToolbarButton('copyNode', '复制', '复制当前选中的内容');
oCopyNode.IconPath = CMS.Plugins['content'].Path+"copyNode.gif";
oCopyNode.Click	=	function(){	
	var query = makeQueryString();
	if( CMS.GetContentIDs().length ==0 ){
		 alert("请选择待复制的内容！");
		 return ;
	}
	var definitions= CMSDialog.NodeDefinitionSelector(null,null,false,true,true);
	if(definitions!=null){
		var url = "content/copy.do?op=copy";
		for(var i=0;i<definitions.length;i++){
			url=url+ "&definition=" + definitions[i].ID;
		}
		url = url +"&" + query;
		var oHttp = new HttpRequest(url);
		if(oHttp.Send()) 
			alert("复制成功！");
		else 
			alert("复制失败！");
	}
};
CMSToolbarItems.RegisterItem('copyNode',oCopyNode);

// 推荐内容
var oRecNode= new CMSToolbarButton('recNode', '推荐', '将当前内容添加到其他分类');
oRecNode.IconPath = CMS.Plugins['content'].Path+"recNode.gif";
oRecNode.Click	=	function(){	
	var query = makeQueryString();
	if( CMS.GetContentIDs().length ==0 ){
		 alert("请选择待推荐的内容！");
		 return ;
	}
	var definitions= CMSDialog.NodeDefinitionSelector(null,null,false,true,true);
	if(definitions!=null){
		var url = "content/recommend.do?op=recommend";
		for(var i=0;i<definitions.length;i++){
			url=url+ "&definition=" + definitions[i].ID;
		}
		url = url +"&" + query;
		var oHttp = new HttpRequest(url);
		if(oHttp.Send()) 
			alert("推荐成功！");
		else 
			alert("推荐失败！");
	}
};
CMSToolbarItems.RegisterItem('recNode',oRecNode);



// 转移内容
var oMoveNode= new CMSToolbarButton('moveNode', '转移', '将当前内容转移到其他分类');
oMoveNode.IconPath = CMS.Plugins['content'].Path+"moveNode.gif";
oMoveNode.Click	=	function(){	
	var query = makeQueryString();
	if( CMS.GetContentIDs().length ==0 ){
		 alert("请选择待转移的内容！");
		 return ;
	}
	var definitions= CMSDialog.NodeDefinitionSelector(null,null,true,true,true);
	if(definitions!=null){
		var url = "content/move.do?op=move";
		url=url+ "&definition=" + definitions.ID;
		url = url +"&" + query;
		var oHttp = new HttpRequest(url);
		if(oHttp.Send()) 
			alert("转移成功！");
		else 
			alert("转移失败！");
		parent.frames["list"].location.reload();
	}
};
CMSToolbarItems.RegisterItem('moveNode',oMoveNode);




//运行服务
var oService= new CMSToolbarButton('service', '服务', '运行服务');
oService.IconPath = CMS.Plugins['content'].Path+"service.png";
oService.Click	=	function(){	
	var service = SMS.selectService();
	if(service==null)
		return;
	var params = SMS.editService(service,CMS.GetDefinitionID());
	if(CMS.GetDefinitionID() == "no-properties-scheme"){
		alert("请勿选择根分类！");
		return;
	}
	if(CMS.GetDefinitionID() == null){
		alert("请选择分类！");
		return;
	}
	if(params == null)
		return;
	if(!document.all){
		var tempElement = params.element.cloneNode(true);
		var parsHidden = document.createElement("div");
		document.body.appendChild(parsHidden);
		parsHidden.style.hidden = "hidden";
		parsHidden.appendChild(tempElement);
		var paramsXML = parsHidden.innerHTML;
		//params.element.parentNode.removeChild(params.element);
		parsHidden.parentNode.removeChild(parsHidden)
	}
	var msgCode = new Date().getTime();
	var end = false;
	while(!end){
		var url = SMS.modulePath+"execute.do";
		var query;
		if(document.all){
			query = "msgCode=" + msgCode 
			+"&serviceID="+service 
			+"&"+makeQueryString()
			+"&parameters="+ encodeURIComponent(params.element.xml);
		}else{
			query = "msgCode=" + msgCode 
			+"&serviceID="+service 
			+"&"+makeQueryString()
			+"&parameters="+ encodeURIComponent(paramsXML);
		}
		var req = getXMLHttpRequest();
	    req.open("POST",url,true);
	    req.setRequestHeader("Content-type","application/x-www-form-urlencoded");   	
	    req.send(query);
	    var retry = SMS.showMessages(msgCode);
	    end = (retry ==null);
	}   					
};
CMSToolbarItems.RegisterItem('service',oService);


	
