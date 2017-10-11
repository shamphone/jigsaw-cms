

function Container(properties,value,text,firstSelect,hasNull,nullValue,nullText,portletId,compositeProp){
	this.value = value;
	this.text = text;
	this.hasNull = hasNull?true:false;
	this.nullValue = nullValue;
	this.nullText = nullText;
	this.properties = properties;
	this.repository = new Repository();
	this.portletId = portletId;
	this.compositeProp = compositeProp;
	this.form = firstSelect.form;
	var headerSentinel = new SelectComponent(null,this);
	headerSentinel.select = firstSelect;
	setSelectValue(headerSentinel.select,properties[0].value);
	
	if(document.all){  
		headerSentinel.select.onchange =  SELECT_ONCHANGE(headerSentinel);
		headerSentinel.select.fireEvent("onchange");
	}else{
		headerSentinel.select.onchange = SELECT_ONCHANGE(headerSentinel);
		headerSentinel.select.onchange();
	}
}


function SelectComponent(preComponent,container){
	this.level = preComponent?(preComponent.level+1):0;
	this.container = container?container:preComponent.container;
	this.name = this.container.properties[this.level].name;
	this.value = this.container.properties[this.level].value;
	this.nextComponent = null;
	this.preComponent = preComponent;
	this.form = this.container.form;
	if(preComponent){
		preComponent.nextComponent = this;
	}
}

SelectComponent.prototype._getChild = function(){
	var select = this.select;
	var opt = select.options[select.selectedIndex];
	if(opt.getAttribute("nodeId")){
		var parentNode = this.container.repository.getNode(opt.getAttribute("nodeId"),false);
		if(this.container.compositeProp){
			return parentNode.children(this.container.compositeProp);
		}
		return parentNode.children();
	}
	return new Array();
}


SelectComponent.prototype.initSelect = function(nodes){
	this.select = this.preComponent.select.cloneNode(false);
	this.select.id = this.container.portletId+this.level;
	if(document.all){
		this.preComponent.select.insertAdjacentElement("afterEnd",this.select);
	}else{
		this.preComponent.select.parentNode.insertBefore(this.select,this.preComponent.select.nextSibling);
	}
	if(this.container.hasNull){
		var opt = document.createElement("option");
		opt.value = this.container.nullValue;
		opt.innerHTML = this.container.nullText;
		this.select.appendChild(opt);
	}
	for(var i=0;i<nodes.length;i++){
		var opt = document.createElement("option");
		if(this.container.value==null){
			opt.value = nodes[i].getID();
		}else{
			opt.value = nodes[i].getValue(this.container.value);
		}
		if(document.all){
			opt.nodeId = nodes[i].getID();
		}else{
			opt.setAttribute("nodeId",nodes[i].getID());
		}
		opt.innerHTML = nodes[i].getValue(this.container.text);
		this.select.appendChild(opt);
	}
	setSelectValue(this.select,this.value);
	
	if(document.all){
		this.select.onchange = SELECT_ONCHANGE(this);
		this.select.fireEvent("onchange");  
	}else{
		this.select.onchange = SELECT_ONCHANGE(this);
		this.select.onchange();
	} 
}

SelectComponent.prototype.renderNextComponent = function(){
	if(this.nextComponent){
		this.nextComponent.remove();
	}
	if(this.container.properties.length==this.level+1){
		return null;
	}
	
	var children = this._getChild();
	if(children.length==0){
		return null;
	}
	var component = new SelectComponent(this);
	component.initSelect(children);
	return component;
}


SelectComponent.prototype.remove = function(){
	var nextComponent = this.nextComponent;
	while(nextComponent){
		nextComponent.remove();
		nextComponent = nextComponent.nextComponent;
	}
	this.preComponent.nextComponent = null;
	var hiddenInput = this.form.elements[this.name];
	hiddenInput.value = "";
	if(document.all){
		this.select.removeNode(true);
	}else{
		this.select.parentNode.removeChild(this.select);
	}
}

SelectComponent.prototype.updateHidden = function(){
	var hiddenInput = this.form.elements[this.name];
	hiddenInput.value = this.select.value;
}
function SELECT_ONCHANGE(component){
	return function(){
		component.updateHidden();
		component.renderNextComponent();
	}
}
