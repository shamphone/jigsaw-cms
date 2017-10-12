<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
  <tiles:put name="title">选择内容分类</tiles:put>
  <tiles:put name="dialog">
    <html:form action="insertNodeDefinition.do">
      <logic:notEqual value="true" name="readOnly">
      <div id="DLGToolbar">
        <span onclick="create()" ><html:img page="/images/newprop.png" module="/common" alt="新建分类" border="0"/><span>新建</span></span>
        <span onclick="edit()"><html:img page="/images/editprop.png" module="/common" alt="修改分类"  border="0"/><span>修改</span></span>
        <span onclick="del()" ><html:img page="/images/delete.png" module="/common" alt="删除分类"  border="0"/><span>删除</span></span>
      </div>
      </logic:notEqual>
      <table width="100%" class="sheetClass" cellpadding="2" cellspacing="0">
        <tr>
          <td>
            <div class="insetDiv" style="height:230px">
              <fulong:xtree name="categorylist" nodeId="categoryNode" treeNode="category">
                <fulong:xtreeText>
                  <bean:define id="ID" name="categoryNode" property="ID" type="String"></bean:define>
                    <logic:equal value="true" name="category" property="enabled">
                      <logic:notEqual value="m" name="sel">
                      <html:radio onclick="setSelect(this);" style="height:10px;" property="parentID" value="<%=ID%>" styleId="<%=ID%>"><label for="<%=ID%>"><bean:write name="categoryNode" property="name"/></label></html:radio>
                      </logic:notEqual>
                      <logic:equal value="m" name="sel">
                        <html:checkbox onclick="setSelect(this);"  style="height:10px;" property="parentID" value="<%=ID%>" styleId="<%=ID%>"><label for="<%=ID%>"><bean:write name="categoryNode" property="name"/></label></html:checkbox>
                      </logic:equal>
                    </logic:equal>
                    <logic:notEqual value="true" name="category" property="enabled">
                      <logic:notEqual value="m" name="sel">
                      <html:radio style="height:13px;" property="parentID" value="<%=ID%>" styleId="<%=ID%>"><label for="<%=ID%>"><bean:write name="categoryNode" property="name"/></label></html:radio>
                    </logic:notEqual>
                      <logic:equal value="m" name="sel">
                        <html:checkbox  style="height:15px;" property="parentID" value="<%=ID%>" styleId="<%=ID%>"><label for="<%=ID%>"><bean:write name="categoryNode" property="name"/></label></html:checkbox>
                      </logic:equal>
                    </logic:notEqual>
                </fulong:xtreeText>
              </fulong:xtree>
            </div>
          </td>
        </tr>
      </table>
      <div class="operation">
        <button type="button" onclick="ok(this.form)" class="commonbut" id="tijiao">确定</button>
        <button type="button" onclick="window.close()" class="commonbut" id="back">取消</button>
      </div>
    </html:form>
</tiles:put>
<tiles:put name="javascript">
      <script language="JavaScript" type="text/Javascript" for="document.body" event="onload()">
        tree.select();
        </script>
       <script language="JavaScript" type="text/Javascript">
       var definitions=new Array();
       var flags=new Array();
        <logic:iterate name="categorylist" property="nodes" id="categoryNode">
            definitions['<bean:write name="categoryNode" property="node.ID"/>']=new Object();
            definitions['<bean:write name="categoryNode" property="node.ID"/>'].name='<bean:write name="categoryNode" property="node.name"/>';
            definitions['<bean:write name="categoryNode" property="node.ID"/>'].ID='<bean:write name="categoryNode" property="node.ID"/>';
            flags['<bean:write name="categoryNode" property="node.ID"/>']=0;
            </logic:iterate>

      var selectedID=new Array();
      function setSelect(input){
        var imgs = input.parentElement.parentElement.getElementsByTagName("img");
        imgs[imgs.length-1].click()
      }
      function create(){
        var url = 'createNodeDefinition.do?parentID='+GetRadioObject(document.forms[0].parentID).value;
        var arr = showModalDialog(url,window,"dialogWidth:100px;dialogHeight:50px;help:no;scrollbars:yes;status:no");
        if(arr!=null){
          var flag='<%=request.getAttribute("sel")%>';
          if(flag!='m'){
            var name = '<input type="radio" onclick="setSelect(this);" name="parentID" value="'+arr.ID+
            '" id="'+arr.ID+'" style="height:13px;"><label for="'+arr.ID+'">'+arr.name+'</label>';
          }else{
          var name = '<input type="checkbox" onclick="setSelect(this);" name="parentID" value="'+arr.ID+
            '" id="'+arr.ID+'" style="height:13px;"><label for="'+arr.ID+'">'+arr.name+'</label>';
          }
         
            var i=new WebFXTreeItem(name,'',tree.getSelected(),'','');
            i.setID(arr.ID);
            definitions[arr.ID]=new Object();
            definitions[arr.ID].name=arr.name;
            definitions[arr.ID].ID=arr.ID;
            flags[arr.ID]=0;
          
        }

      }
      function edit(){
        url = "../../common/modalWrapper.jsp?title="
        +encodeURIComponent(encodeURIComponent("修改分类"))+
        "&url="+encodeURI('../cms/dialog/editNodeDefinition.do?ID='+GetRadioObject(document.forms[0].parentID).value);
        var arr = showModalDialog(url,window,"dialogWidth:100px;dialogHeight:50px;help:no;scrollbars:yes;status:no");
        if(arr!=null){
          var flag='<%=request.getAttribute("sel")%>';
          if(flag!='m'){
            var name = '<input type="radio" onclick="setSelect(this);" checked="checked" name="parentID" value="'+arr.ID+
            '" id="'+arr.ID+'" style="height:13px;"><label for="'+arr.ID+'">'+arr.name+'</label>';
          }else{
          var name = '<input type="checkbox" onclick="setSelect(this);" checked="checked" name="parentID" value="'+arr.ID+
            '" id="'+arr.ID+'" style="height:13px;"><label for="'+arr.ID+'">'+arr.name+'</label>';
          }
          tree.getSelected().setTitle(name);
          definitions[arr.ID].name=arr.name;
        }
      }
      function del(){
        if(confirm('确定删除此分类？')) {
          var req = getXMLHttpRequest();
          var url = 'deleteNodeDefinition.do?ID='+GetRadioObject(document.forms[0].parentID).value;
          req.open("get",encodeURI(url),false);
          req.send(null);
          var data=req.responseText;
              if(data!=null&&data!=""){
                tree.getSelected().remove();
          }
        }
      }
      function ok(aForm){
        var results=new Array();
        var flag='<%=request.getAttribute("sel")%>';
        if(flag=="m"){
          for(var i=0;i<aForm.elements['parentID'].length;i++){
            if(aForm.elements['parentID'][i].checked){
              selectedID.push(aForm.elements['parentID'][i].value);
            }
          }
          if(selectedID!=null){
            for(var i=0;i<selectedID.length;i++){
              results.push(definitions[selectedID[i]]);
            }
          }
          window.returnValue=results;
          window.close();
        }
          else{
            for(var i=0;i<aForm.elements['parentID'].length;i++){
              if(aForm.elements['parentID'][i].checked){
                selectedID.push(aForm.elements['parentID'][i].value);
              }
            }
            if(selectedID!=null){
              for(var i=0;i<selectedID.length;i++){
            	  results.push(definitions[selectedID[i]]);              }
            }
            window.returnValue=results;
            
			window.close();
          }
          
        }
WebFXTreeAbstractNode.prototype.toggle = function() {
  if (this.folder) {
    if (this.open) {
      this.collapse();
    }
    else {
      if(this!=tree.root){
        var req = getXMLHttpRequest();
        if(flags[this.ID]==0){
          var url='<html:rewrite page="/dialog/searchThisNodeDefinition.do?ID=" module="/cms"/>'+this.ID;
          req.open("get",encodeURI(url),false);
          req.send(null);
          var data=req.responseXML;
          if(data!=null){
            var xmlDoc = checkXMLDocObj(req.responseXML);
            var n =req.responseXML.getElementsByTagName('parameter').length;
            for(var i=0;i<n;i++){
              var name=xmlDoc.getElementsByTagName('value')[i].text;
              var id=xmlDoc.getElementsByTagName('name')[i].text;
              var isMuti='<%=request.getAttribute("sel")%>';
              if(isMuti!='m'){
              var  r= '<input type="radio" onclick="setSelect(this);" name="parentID" value="'+id+
              '" id="'+id+'" style="height:13px;"><label for="'+id+'">'+name+'</label>';
              }
              else{
            	  var  r= '<input type="checkbox" onclick="setSelect(this);" name="parentID" value="'+id+
                  '" id="'+id+'" style="height:13px;"><label for="'+id+'">'+name+'</label>';
                  }
              var item=new WebFXTreeItem(r,'',this,'','');
              item.setID(id);
              definitions[id]=new Object();
              definitions[id].name=name;
              definitions[id].ID=id;
              flags[id]=0;
            }
          }
          flags[this.ID]=1;
        }
      }
      if(n==0){
          document.getElementById(this.id + '-icon').src = webFXTreeConfig.fileIcon;
          document.getElementById(this.id + '-plus').src = (this==this.parentNode.getLast())?webFXTreeConfig.lIcon:webFXTreeConfig.tIcon;
          this.folder=false;
        }else{
          this.expand();
        }
    }
  }
}

WebFXTreeItem.prototype.toString = function (nItem, nItemCount) {
	var foo = this.parentNode;
	var indent = '';
	if (nItem + 1 == nItemCount) { this.parentNode._last = true; this.parentNode.lastNode=this; }
	var i = 0;
	while (foo.parentNode) {
		foo = foo.parentNode;
		indent = "<img id=\"" + this.id + "-indent-" + i + "\" src=\"" + ((foo._last)?webFXTreeConfig.blankIcon:webFXTreeConfig.iIcon) + "\">" + indent;
		i++;
	}
	this._level = i;
        this.folder = 1;
        this.open = false;
	if ((this.folder) || (webFXTreeHandler.behavior != 'classic')) {
		if (!this.icon) { this.icon = webFXTreeConfig.folderIcon; }
		if (!this.openIcon) { this.openIcon = webFXTreeConfig.openFolderIcon; }
	}
	else if (!this.icon) { this.icon = webFXTreeConfig.fileIcon; }
	var str = "<div id=\"" + this.id + "\" ondblclick=\"webFXTreeHandler.toggle(this);\" class=\"webfx-tree-item\" onkeydown=\"return webFXTreeHandler.keydown(this, event)\" value=\""+this.id+"\">" +		indent +
		"<img id=\"" + this.id + "-plus\" src=\"" + ((this.folder)?((this.open)?((this.parentNode._last)?webFXTreeConfig.lMinusIcon:webFXTreeConfig.tMinusIcon):((this.parentNode._last)?webFXTreeConfig.lPlusIcon:webFXTreeConfig.tPlusIcon)):((this.parentNode._last)?webFXTreeConfig.lIcon:webFXTreeConfig.tIcon)) + "\" onclick=\"webFXTreeHandler.toggle(this);\">" +
		"<img id=\"" + this.id + "-icon\" class=\"webfx-tree-icon\" src=\"" + ((webFXTreeHandler.behavior == 'classic' && this.open)?this.openIcon:this.icon) + "\" onclick=\"webFXTreeHandler.select(this);\">" +
		"<a href=\"" + this.action + "\" id=\"" + this.id + "-anchor\" onfocus=\"webFXTreeHandler.focus(this);\" onblur=\"webFXTreeHandler.blur(this);\"" +
		(this.target ? " target=\"" + this.target + "\"" : "") +
		">" + WebFXTree_unfilter(this.text) + "</a></div>" +
		"<div id=\"" + this.id + "-cont\" class=\"webfx-tree-container\" style=\"display: " + ((this.open)?'block':'none') + ";\">";
	var sb = [];
	for (var i = 0; i < this.childNodes.length; i++) {
		sb[i] = this.childNodes[i].toString(i,this.childNodes.length);
	}
	this.plusIcon = ((this.parentNode._last)?webFXTreeConfig.lPlusIcon:webFXTreeConfig.tPlusIcon);
	this.minusIcon = ((this.parentNode._last)?webFXTreeConfig.lMinusIcon:webFXTreeConfig.tMinusIcon);
	return str + sb.join("") + "</div>";
}

// 加载xml文档
loadXML     = function(xmlFile)
{
     var xmlDoc;
     if(window.ActiveXObject)
     {
         xmlDoc     = new ActiveXObject('Microsoft.XMLDOM');
         xmlDoc.async     = false;
         xmlDoc.load(xmlFile);
     }
     else if (document.implementation&&document.implementation.createDocument)
     {
         xmlDoc     = document.implementation.createDocument('', '', null);
         xmlDoc.load(xmlFile);
     }
     else
     {
         return null;
     }

     return xmlDoc;
}
// 首先对xml对象进行判断
checkXMLDocObj     = function(xmlFile)
{
     var xmlDoc     = loadXML(xmlFile);
     if(xmlDoc==null)
     {
         alert('您的浏览器不支持xml文件读取,于是本页面禁止您的操作,推荐使用IE5.0以上可以解决此问题!');
     }

     return xmlDoc;
}
WebFXTree.prototype.getLast = function() {
	if (this.childNodes[this.childNodes.length - 1].open) { return this.childNodes[this.childNodes.length - 1].getLast(); }
	else { return this.childNodes[this.childNodes.length - 1]; }
}

WebFXTreeItem.prototype.getPreviousSibling = function(b) {
	for (var i = 0; i < this.parentNode.childNodes.length; i++) {
		if (this == this.parentNode.childNodes[i]) { break; }
	}
	if (i == 0) { return this.parentNode; }
	else {

         if ((this.parentNode.childNodes[--i].open) || (b && this.parentNode.childNodes[i].folder&&this.parentNode.childNodes[i].childNodes.length)) {
         return this.parentNode.childNodes[i].getLast(); }
         else { return this.parentNode.childNodes[i]; }
}
}

WebFXTreeItem.prototype.remove = function() {
	var iconSrc = document.getElementById(this.id + '-plus').src;
	var parentNode = this.parentNode;
	var prevSibling = this.getPreviousSibling(true);
	var nextSibling = this.getNextSibling(true);
	var folder = this.parentNode.folder;
	var last = ((nextSibling) && (nextSibling.parentNode) && (nextSibling.parentNode.id == parentNode.id))?false:true;
	this.getPreviousSibling().focus();
	this._remove();
	if (parentNode.childNodes.length == 0) {
		document.getElementById(parentNode.id + '-cont').style.display = 'none';
		parentNode.doCollapse();
		parentNode.folder = false;
		parentNode.open = false;
	}
	if (!nextSibling || last) { parentNode.indent(null, true, last, this._level, parentNode.childNodes.length); }

	if ((prevSibling == parentNode) && !(parentNode.childNodes.length)) {
		prevSibling.folder = false;
		prevSibling.open = false;
		iconSrc = document.getElementById(prevSibling.id + '-plus').src;
		iconSrc = iconSrc.replace('minus', '').replace('plus', '');
		document.getElementById(prevSibling.id + '-plus').src = iconSrc;
		document.getElementById(prevSibling.id + '-icon').src = webFXTreeConfig.fileIcon;
	}
	if (document.getElementById(prevSibling.id + '-plus')) {
		if (parentNode == prevSibling.parentNode) {
		//	iconSrc = iconSrc.replace('minus', '').replace('plus', '');
		//	document.getElementById(prevSibling.id + '-plus').src = iconSrc;
}	}	}


WebFXTree.prototype.toString = function() {
	var str = "<div id=\"" + this.id + "\" ondblclick=\"webFXTreeHandler.toggle(this);\" class=\"webfx-tree-item\" onkeydown=\"return webFXTreeHandler.keydown(this, event)\" value=\""+this.id+"\">" +
		"<img id=\"" + this.id + "-icon\" class=\"webfx-tree-icon\" src=\"" + ((webFXTreeHandler.behavior == 'classic' && this.open)?this.openIcon:this.icon) + "\" onclick=\"webFXTreeHandler.select(this);\">" +
		"<a href=\"" + this.action + "\" id=\"" + this.id + "-anchor\" onfocus=\"webFXTreeHandler.focus(this);\" onblur=\"webFXTreeHandler.blur(this);\"" +
		(this.target ? " target=\"" + this.target + "\"" : "") +
		">" + WebFXTree_unfilter(this.text)+ "</a></div>" +
		"<div id=\"" + this.id + "-cont\" class=\"webfx-tree-container\" style=\"display: " + ((this.open)?'block':'none') + ";\">";
	var sb = [];
	for (var i = 0; i < this.childNodes.length; i++) {
		sb[i] = this.childNodes[i].toString(i, this.childNodes.length);
	}
	this.rendered = true;
	return str + sb.join("") + "</div>";
};

WebFXTreeItem.prototype.setID=function(sID){
  this.ID=sID;
}
WebFXTreeItem.prototype.getID=function(){
  if(this.ID!=null){
    return this.ID;
  }else{
    return null;
  }
}
WebFXTree.prototype.setID=function(sID){
  this.ID=sID;
}
WebFXTree.prototype.getID=function(){
  if(this.ID!=null){
    return this.ID;
  }else{
    return null;
  }
}
WebFXTree.prototype.setRoot=function(){
  this.root=this;
}
WebFXTreeAbstractNode.prototype.focus = function() {
	
	if ((webFXTreeHandler.selected) && (webFXTreeHandler.selected != this)) { webFXTreeHandler.selected.deSelect(); }
	webFXTreeHandler.selected = this;
	if ((this.openIcon) && (webFXTreeHandler.behavior != 'classic')) { document.getElementById(this.id + '-icon').src = this.openIcon; }
	document.getElementById(this.id + '-anchor').className = 'selected';
	document.getElementById(this.id + '-anchor').focus();
	var bInput=document.getElementById(this.ID).checked;
	var flag='<%=request.getAttribute("sel")%>';
	if(flag=='m'){
	if(this!=tree.root){
	if(bInput==true){
		bInput=false;
		if(this.childNodes.length==0){
		this.toggle();
		}
		if(this.childNodes.length!=0){
			for(var i=0;i<this.childNodes.length;i++){
				document.getElementById(this.childNodes[i].ID).checked=true;
				}
			}
	}
	else{
		bInput=true;
		
		if(this.childNodes.length!=0){
			for(var i=0;i<this.childNodes.length;i++){
				document.getElementById(this.childNodes[i].ID).checked=false;
				}
			}
		}
	}
}
	else{
		bInput.checked=true;
		}
	if (webFXTreeHandler.onSelect) { webFXTreeHandler.onSelect(this); }
}
      </script>
      </tiles:put>
    </tiles:insert>
