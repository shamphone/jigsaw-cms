<%@ page contentType="text/javascript; charset=UTF-8" %><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
var ContextPath = '<html:rewrite page="/" module=""/>';
var IconsPath = ContextPath +'common/module/icons/';
var ModuleSpanClassName = "spanModule";
var HilightModuleSpanClassName = "spanModuleHilight";

var CModuleCollection = new Object();
CModuleCollection.items = new Array();
CModuleCollection.add = function(module){
	this.items.push(module);
};

CModuleCollection.get = function(id){
	for(var i=0;i<this.items.length;i++){
		if(this.items[i].id == id)
			return this.items[i];
	}
}
CModuleCollection.render = function(oParent, hilight){
	for(var i=0;i<this.items.length;i++){
		var child = this.items[i].render();
		oParent.appendChild(child);
		if(hilight == this.items[i].id)
			this.items[i].hilight();
	}
};

var CModule = function(id, title, path, icon){
	this.id = id;
	this.title = title;
	this.path = path;
	this.icon = icon;
};
CModule.prototype.render=function(){
	var oSpan = document.createElement("span");
	oSpan.setAttribute("title",this.title);
	oSpan.id = this.id;	
	oSpan.className = ModuleSpanClassName;
	oSpan.onmouseover = CMSModule_MouseOver(this);
	oSpan.onmouseout = CMSModule_MouseOut(this);
	oSpan.onclick = CMSModule_Click(this);
	var oImg = document.createElement("img");
	oImg.align="absmiddle";
	
	//oImg.style.verticalAlign = "middle";
	oImg.src = this.icon;
	oImg.border = 0;
	oImg.width = 16;
	oImg.height =16;
	oImg.style.paddingRight = "7px";
	oImg.style.paddingTop = "3px";
	oImg.alt = this.title;
	oSpan.appendChild(oImg);
	this.element = oSpan;
	return oSpan;
};
CModule.prototype.hilight = function(){
	this.element.className = HilightModuleSpanClassName;	
	CModuleCollection.selectedModule = this;
};

var CMSModule_Click= function(oModule){
	return function(){
		window.top.location = oModule.path;
	}
};


var CMSModule_MouseOver= function(oModule){
	return function(){
	 oModule.element.className = "spanModuleHilight";
	}
};

var CMSModule_MouseOut = function(oModule){
	return function(){
	if(CModuleCollection.selectedModule != oModule)
		 oModule.element.className = "spanModule";
	}
};
var cmsModule = new CModule("security","组织结构", ContextPath + "security/index.jsp",IconsPath+ "ums.png");
CModuleCollection.add(cmsModule);
var cmsModule = new CModule("cms","数据管理", ContextPath + "cms/manage/index.jsp",IconsPath+ "cms.png");
CModuleCollection.add(cmsModule);
var cmsModule = new CModule("site","网站设计", ContextPath + "site",IconsPath+ "site.png");
CModuleCollection.add(cmsModule);
var serviceModule = new CModule("service","服务管理", ContextPath + "service/sms/index.jsp",IconsPath+ "service.png");
CModuleCollection.add(serviceModule);
var cmsModule = new CModule("process","流程设计", ContextPath + "process/visual/index.jsp",IconsPath+ "workflow.png");
CModuleCollection.add(cmsModule);
var cmsModule = new CModule("system","系统监控", "/monitor/system/index.jsp",IconsPath+ "sysmgn.png");
CModuleCollection.add(cmsModule);
var cmsModule = new CModule("log","访问量分析", "/monitor/awstats/index.jsp",IconsPath+ "log.png");
CModuleCollection.add(cmsModule);