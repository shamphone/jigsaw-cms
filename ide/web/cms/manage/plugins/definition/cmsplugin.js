/**
 * 创建子分类插件
 */
var oNewDefinition= new CMSToolbarButton('newdef', '创建分类', '在当前分类下创建子分类');
oNewDefinition.IconPath = CMS.Plugins['definition'].Path+"newCategory.png";
oNewDefinition.Click	=	function(){
	if(CMS.GetDefinitionID()==null){
		alert("请选择一个父分类！");
		return;
	}
	var tree = CMS.DefinitionTree.tree; 
	var parent = tree.getSelected();
	var defs = parent.childNodes;
	//获取创建分类页面的绝对路径 add by lkl
	var basepath = CMS.EditorPath.substring(0,CMS.EditorPath.indexOf("/manage"));
	var arr=CMS.ShowDialog(basepath+"/definition/create.do?parentID="+ CMS.GetDefinitionID(),defs,150,100);
	if(arr!=null){	
		var child = parent.addNodeDefinition(arr.ID);
	}
};
CMSToolbarItems.RegisterItem('newdef',oNewDefinition);

/**
 * 复制分类插件
 */
var oCopyDefinition= new CMSToolbarButton('copydef', '复制分类', '复制分类');
oCopyDefinition.IconPath = CMS.Plugins['definition'].Path+"copyCategory.gif";
oCopyDefinition.Click	=	function(){
	if(CMS.GetDefinitionID()!=null){
		if(CMS.GetDefinitionID()=='no-properties-scheme'){
			alert("根分类不允许复制！");
			return;
		}
		var tree = CMS.DefinitionTree.tree;
		var parent = tree.getSelected().getParent();
		var defs = parent.childNodes;
		var basepath = CMS.EditorPath.substring(0,CMS.EditorPath.indexOf("/manage"));
		var result = CMS.ShowDialog( basepath+"/definition/copy.do?categoryID="+ CMS.GetDefinitionID(),defs,150,110);
		if(result!=null){
			var child = parent.addNodeDefinition(result.ID);			
		}
	}else{
		alert("请选择内容分类！");
	}

};

CMSToolbarItems.RegisterItem('copydef',oCopyDefinition);

/**
 * 删除分类插件
 */

var oDelDefinition= new CMSToolbarButton('deldef', '删除分类', '删除当前分类及其所有子分类');
oDelDefinition.IconPath = CMS.Plugins['definition'].Path+"delCategory20.gif";
oDelDefinition.Click	=	function(){
	if(CMS.GetDefinitionID()!=null)
	{	
		if(CMS.GetDefinitionID()=='no-properties-scheme'){
			alert("根分类不允许删除！");
			return;
		}
		
		var tree = CMS.DefinitionTree.tree;
		var item = tree.getSelected();
		
		if(item.childNodes.length !=0){
			alert("父分类不允许删除，请先删除子分类！");
			return;
		}
		
		//增加提示
		if(confirm("确定删除该内容分类?")){
			 var parent =item.parentNode;
			 //发送ajax请求，抛异常则不删除
			 if(item.element.remove() != "200"){
				 alert("该分类内容被引用，无法删除！");
				 return;
			 }
			 
			 item.remove();
			 parent.focus();			 
			 parent.select();
		}
	}
    else
	     alert("请选择内容分类！");

};
CMSToolbarItems.RegisterItem('deldef',oDelDefinition);


/**
 * 修改子分类插件
 */

var oEditDefinition= new CMSToolbarButton('editdef', '修改分类', '修改分类名称');
oEditDefinition.IconPath = CMS.Plugins['definition'].Path+"editCategory20.gif";
oEditDefinition.Click	=	function(){
	if(CMS.GetDefinitionID()!=null){
		//var parent = CMS.DefinitionTree.tree.getSelected().getParent();
		//var defs = parent.childNodes;
		//defs.remove(CMS.DefinitionTree.tree.getSelected());
		var arr =CMS.ShowDialog(Globals.contextPath +"/cms/definition/edit.do?ID="+ CMS.GetDefinitionID(),CMS.DefinitionTree.tree,150,100);
		if(arr!=null){
			CMS.DefinitionTree.tree.getSelected().setTitle(arr.name);
		    //让树重载  by mali
			//window.parent.frames[1].location.reload();
		}	
	}
	else{
		alert("请选择内容分类！");
	}
};
CMSToolbarItems.RegisterItem('editdef',oEditDefinition);


/**
 * 转移子分类插件
 */

var oMoveDefinition= new CMSToolbarButton('movedef', '转移分类', '将当前分类转移到其他分类下成为子分类');
oMoveDefinition.IconPath = CMS.Plugins['definition'].Path+"moveCategory.gif";
oMoveDefinition.Click	=	function(){
	if(CMS.GetDefinitionID()!=null){
		if(CMS.GetDefinitionID()=='no-properties-scheme'){
			alert("根分类不允许转移！");
			return;
		}
        var url= Globals.contextPath +'/cms/definition/synDefinitions.jsp';
        var args = new Object();
        args.multiple = false;
        args.definition = "no-properties-scheme";
	    var result = showDialog(url, args, 320, 280);				
		if(result!=null){
			//防止加载DefinitionTree是出现的死循环错误 by mali 2010-7-7
			var tree = CMS.DefinitionTree.tree;
			if(result.ID == CMS.GetDefinitionID()){
				alert("请勿向己分类转移！");
				return;
			}
			
			var currentNode = tree.getSelected(); 
			var child = currentNode.element;
			var children;
			for(var j=0; j<currentNode.childNodes.length; j++){
				children = currentNode.childNodes[j].element;
				if(children.getID() == result.ID){
					alert("请勿向选中分类的子分类转移！");
					return;
				}
			}	
			
			//判断同一级节点里是否有名称重复的,重复则不执行 by mali 2010-7-1
			var parentNode = tree.getTreeItem(result.ID);
			for(var i=0; i<parentNode.childNodes.length; i++){
				if(document.all){
					if(parentNode.childNodes[i].text == child.getName()){
						alert("分类名已存在！");
						return;
					}
				}else{
					if(parentNode.childNodes[i].textContent == child.getName()){
						alert("分类名已存在！");
						return;
					}
				}
			}
			child.move(result.ID); 
			currentNode.setParent(parentNode);
		}
	}else{
		alert("请选择内容分类！");		
	}
};
CMSToolbarItems.RegisterItem('movedef',oMoveDefinition);

/**
 * 属性管理插件
 */

var oPropertyManagement= new CMSToolbarButton('property', '属性管理', '设置分类属性');
oPropertyManagement.IconPath = CMS.Plugins['definition'].Path+"manage.gif";
oPropertyManagement.Click	=	function(){
	if(CMS.GetDefinitionID()==null){
		alert("请选择分类！");
		return;
	}
	if(CMS.GetDefinitionID()=='no-properties-scheme'){
		alert("根分类下不允许进行属性操作！");
		return;
	}
	CMSDialog.PropertyDefinitionSelector(CMS.GetDefinitionID());
};
CMSToolbarItems.RegisterItem('property',oPropertyManagement);

/**
 * 系统管理
 */

var oSystem = new CMSToolbarButton('system', '系统管理', '转到系统管理');
oSystem.IconPath = CMS.Plugins['definition'].Path+"sysmgn.png";
oSystem.Click	=	function(){
	top.location='../../system/index.jsp';
};
CMSToolbarItems.RegisterItem('system',oSystem);

