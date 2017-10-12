<%@ page contentType="text/javascript; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

/**
 * 对服务参数的dom模型的封装。这个dom模型对应xpdl中对ActualParameters元素的定义。
 **/
var Parameters = function(oXMLParameters){
		this.element = oXMLParameters;
		if(this.element == null){
			var oXML = document.getElementById("serviceParameters");
			if(document.all){
				if(oXML == null){
					oXML = document.createElement("xml");
					oXML.id = "serviceParameters";
					document.getElementsByTagName("head")[0].appendChild(oXML);
				}else{
					this.element = oXML.XMLDocument.documentElement;
				}
				if(this.element == null){
					this.element = oXML.XMLDocument.createElement("ActualParameters");
					oXML.XMLDocument.appendChild(this.element);
				}
			}else{
			//在火狐下第二次点击服务采用删除零时节点，重新建立零时节点传递参数
				if(oXML == null){
					oXML = document.createElement("xml");
					oXML.id = "serviceParameters";
					document.getElementsByTagName("head")[0].appendChild(oXML);
				}else{
					oXML.parentNode.removeChild(oXML);
					oXML = document.createElement("xml");
					oXML.id = "serviceParameters";
					document.getElementsByTagName("head")[0].appendChild(oXML);
				}
				if(this.element == null){
					this.element = document.createElement("ActualParameters");
					document.lastChild.appendChild(this.element);
				}
			}
		}
};

Parameters.prototype.getParameterNode = function(param){
	var nodes = this.element.getElementsByTagName("ActualParameter");
	for(var i=0; i<nodes.length; i++){
		var node = nodes[i];
		var names = node.getElementsByTagName("name");
		if(names.length == 1 ){
			var name = names[0].text;
			if(name==param){
				return node;
			}
		}
	}
	return null;

};
/**
  * 获取参数param对应的值，返回值可能是单值、数组或者空值
  *
  **/
Parameters.prototype.getValue	=	function( param ){
	var node = this.getParameterNode(param);
	if(node == null)
		return null;
	var values = node.getElementsByTagName("value");
	if(values.length == 0)
		return null;
	if(values.length == 1)
		return values[0].text;
	var result = new Array();
	for(var v=0;v<values.length;v++)
		result.push(values[v].text);
	return result;
};

/**
 * 获取参数param对应的值，返回值可能是单值、数组或者空值
 *
 **/
Parameters.prototype.getValues	=	function( param ){
	var node = this.getParameterNode(param);
	if(node == null)
		return null;
	var values = node.getElementsByTagName("value");
	var result = new Array();
	for(var v=0;v<values.length;v++)
		result.push(values[v].text);
	return result;
};
/**
 *设置单值
**/
Parameters.prototype.setValue	=	function( param , value){
	var node =this.getParameterNode(param);
	if((value == null) && (node!=null))
		this.element.removeChild(node);
	else if(value == null)
		return;
	else
		this.setValues(param, [value]);
}
/**
 *设置单值
**/
Parameters.prototype.addValue	=	function( param , value){
	var values = this.getValues(param);
	if(values == null)
		values = [value];
	else 
		values.push(value);
	this.setValues(param, values);
};


/**
  *设置多值
 **/

Parameters.prototype.setValues	=	function( param , values){
	var doc =	this.element.ownerDocument;
	var node = this.getParameterNode(param);
	if(node == null){
		node = doc.createElement("ActualParameter");
		this.element.appendChild(node);
	}
	else{
		while(node.hasChildNodes())
			node.removeChild(node.firstChild);
	}
	var nameElement = doc.createElement("name");
	nameElement.appendChild(doc.createTextNode(param));
	node.appendChild(nameElement);
	for(i=0;i<values.length;i++){
		var valueElement = doc.createElement("value");
		valueElement.appendChild(doc.createTextNode(values[i]));
		node.appendChild(valueElement);
	}
};
/**
 * 填充Form表单，如果控件的名称和参数名称一致，则填充。
 */
Parameters.prototype.populateForm = function (oForm){
	
	for(var i=0;i<oForm.elements.length;i++){
		var comp = oForm.elements[i];
		if(this.getValues(comp.name)!=null)
		switch(comp.tagName){
		case "SELECT":
			this._populateSelect(comp);
			break;
		case "TEXTAREA":
			this._populateText(comp);
			break;
		case "INPUT":
			switch(comp.type){
			case "hidden":
			case "text":
			case "password":
				this._populateText(comp);
				break;
			case "checkbox":
			case "radio":
				this._populateSelection(comp);
				break;
			}
			break;
		}
	}
};
/**
 * 从form控件中接收数据更新；
 */
Parameters.prototype.updateFromForm = function (oForm){
	for(var i=0;i<oForm.elements.length;i++){
		var comp = oForm.elements[i];
		this.setValue(comp.name, null);
	}
	for(var i=0;i<oForm.elements.length;i++){
		var comp = oForm.elements[i];		
		switch(comp.tagName){
		case "SELECT":			
			this._updateSelect(comp);
			break;
		case "TEXTAREA":
			this._updateText(comp);
			break;
		case "INPUT":
			switch(comp.type){
			case "hidden":
			case "text":
			case "password":
				this._updateText(comp);
				break;
			case "checkbox":
			case "radio":
				this._updateSelection(comp);
				break;
			}
			break;
		}
	}
};

/**
 * 初始化select控件
 */
Parameters.prototype._populateSelect = function(comp){
	var values = this.getValues(comp.name);
	for(var i=0;i<comp.options.length;i++)
		comp.options[i].selected = values.contains(comp.options[i].value);
};
Parameters.prototype._updateSelect = function(comp){
	for(var i=0;i<comp.options.length;i++)
		if(comp.options[i].selected)
			this.addValue(comp.name,comp.options[i].value);
};

/**
 * 初始化textarea控件
 */
Parameters.prototype._populateText = function(comp){
	var values = this.getValues(comp.name);	
	if(comp.value!=null && comp.value.length>0)
		return;
	if(values.length == 0)
		return;
	comp.value = values[0];
};

Parameters.prototype._updateText = function(comp){
	this.addValue(comp.name,comp.value);
};
/**
 * 初始checkbox和radio类控件
 */
Parameters.prototype._populateSelection = function(comp){
	var values = this.getValues(comp.name);	
	if(comp.checked)
		return;
	comp.checked = values.contains(comp.value);
};
Parameters.prototype._updateSelection = function(comp){
	if(comp.checked)
		this.addValue(comp.name,comp.value);
};
var Service = new Object();
//选择当前选项框
Service.SelectPanel= function(oSelect){
	var index=oSelect.options[oSelect.selectedIndex].value;
	var fieldsets=document.getElementsByTagName("fieldset");
	for(i=0;i<fieldsets.length;i++){
		fieldsets[i].style.display="none";
	}
	fieldsets[parseInt(index)].style.display="";
	Service.OnPanelSelect(index);
};
/**
 * 需要在Panel被展示时作初始化时调用，则可以覆盖这个方法
 */
Service.OnPanelSelect = function(index){
	return;
};
//用户点击OK按钮时的事件处理
Service.OK = function(oForm){
	
}
//用户点击cancel按钮时的事件处理
Service.Cancel	=	function(oForm){
	window.close();
}

Service.Init	=	function(){
	document.getElementById('pannelSelect').selectedIndex = 0;
	var fieldsets = tdFieldsets.children.tags("fieldset");
	if(fieldsets.length>0)
	 	fieldsets[0].style.display = "";
}

/**
 * 服务管理的所有对话框；注意使用这个脚本依赖于common/script/dialog.jsp;common.js脚本
 */
var SMS = {
	modulePath	: 	'<html:rewrite page="/" module="/service"/>',
	/**
	  * 选择服务
	  **/
	selectService : function(){
		return showDialog(this.modulePath+"dialog/services.do","",450,360);
	},
	/**
	 *
	 * 修改服务
	 */
	editService	:	function(serviceID, definitionID, oXMLParameters){
		
		var args = new Object();
		args.parameters = new Parameters(oXMLParameters);
		args.element = args.parameters.element;
		args.definitionID = definitionID;
		return showDialog(this.modulePath+"edit.do?serviceID="+serviceID,args,450,360);
	},
	/**
	 *
	 * 显示服务执行时的消息
	 */
	showMessages	:	function(msgcode){
		return showDialog(this.modulePath+"dialog/messages.jsp",msgcode,620,400);
	}	
}