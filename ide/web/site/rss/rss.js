
/**
 * 选择活动
 */
  function doSelectActivity(oActivity,oName,oTemplate){
	  var ret = showDialog(Globals.contextPath+'/process/activity/selector.jsp',oTemplate,620,450);
	  if(ret!=null){
		  oActivity.value = ret.definition.ID+"."+ret.activity.ID;
		  oName.value=ret.definition.name +"."+ret.activity.name;
	  }
  }
  
  function click_chk(chk){
	  var bDisplay = chk.checked?"":"none";
	  document.getElementById("imageTitleTip").style.display = bDisplay;
	  document.getElementById("imageLinkTip").style.display = bDisplay;
	  document.getElementById("imageDescriptionTip").style.display = bDisplay;
  }

/**
 * 分页展示页面的选择Panel
 */
function selectPanel(oSelect) {
	var index = oSelect.options[oSelect.selectedIndex].value;
	var fieldsets = oSelect.form.getElementsByTagName("fieldset");
	for (i = 0; i < fieldsets.length; i++) {
		fieldsets[i].style.display = "none";
	}
	fieldsets[parseInt(index)].style.display = "";
};

function selectChannel(objID, objName){
	var ret = SiteDialog.selectChannel(oTemplate.name,objID.value,true,false);
	if(ret){
		objID.value = ret.path;
		objName.value = ret.displayName;
	}
	
}
	
function selectCategory(objID, objName) {
	var ret = CMSDialog.NodeDefinitionSelector('no-properties-scheme', null, true, false, false);
	if (ret) {
		objID.value = ret.ID;
		objName.value = ret.name;
	}
}

function selectFolder(oPath,oDisplayName){
	var folder = SiteDialog.selectFolder(oTemplate);
	if(folder!=null){
		oPath.value = folder.contextPath;
		oDisplayName.value = folder.displayName;
		parentFolder = folder;
	}
}

function selectImage(imageLink){
	var selectedPahts = SiteDialog.resourceSelector(oTemplate,"image");
	if(selectedPahts!=null&&selectedPahts.length>0){
		imageLink.value = selectedPahts[0];
	}
}

/**
 *清空按钮
 **/
 function clearTxt(oPreference,oName){
 if (oPreference != null) {
     oPreference.value="";
     }
     oName.value="";
 }

/**
*
*选择引用属性
**/
function searchRefPropertyDefinition(oCategory, oID, oRefID, oRefName){
  var arr =  CMSDialog.PropertyDefinitionSelector(oCategory.value,['1','2','3','4','5','6','7','8','10']);
  if(arr!=null){
    oID.value = arr.ID;
    oRefName.value = arr.name;
    oRefID.value = arr.refID;
  }
}

function selectProperty(categoryID, idObj,nameObj, excludeType) {
	if (categoryID == null) {
		alert("请选择分类！");
		return;
	}
	var result = CMSDialog.PropertyDefinitionSelector(categoryID, excludeType);
	if (result != null) {
		idObj.value = result.ID;
		nameObj.value = result.name;
	}
}

function validateChannel(name) {
	return true;
}
function validate() {
	var name = document.getElementById("name").value = document.getElementById("name").value.trim();
	if(!validateName(name)){
		alert("栏目名称只能包含字母、数字和下划线！");
		return false;
	}
	if(!validateLength(name,4,33)){
		alert("栏目名称长度必须在4和32之间！");
		return false;
	}
	if(!validateChannel(name)){
		alert("该页面已存在!");
		return false;
	}
	var displayName = document.getElementById("displayName").value;
	if(!validateRequired(displayName)){
		alert("栏目显示名称不能为空！");
		return false;
	}
	if(!validateLength(displayName,1,33)){
		alert("栏目显示名称长度必须在1和32之间！");
		return false;
	}
	//校验definitionID
	var definitionID = document.getElementById("definitionID").value;
	if(!validateRequired(definitionID)){
		alert("关联分类不能为空！");
		return false;
	}
	//校验link
	var link = document.getElementById("link").value;
	if(!validateRequired(link)){
		alert("频道URL不能为空！");
		return false;
	}
	//校验description
	var description = document.getElementById("description").value;
	if(!validateRequired(description)){
		alert("频道描述不能为空！");
		return false;
	}
	
	//设置itemNum
	var itemNum = document.getElementById("itemNum").value;
	if(!validateRequired(itemNum)){
		alert("item数描述不能为空！");
		return false;
	}
	
	//设置itemNum
	var itemNum = document.getElementById("itemNum").value;
	if(!validatorInt(itemNum)){
		alert("item数必须是数字！");
		return false;
	}
	//设置itemTitle
	var itemTitle = document.getElementById("itemTitle").value;
	if(!validateRequired(itemTitle)){
		alert("项目标题不能为空！");
		return false;
	}
	//设置itemLink
	var itemLink = document.getElementById("itemLink").value;
	if(!validateRequired(itemLink)){
		alert("项目链接不能为空！");
		return false;
	}
	//设置itemDescription
	var itemDescription = document.getElementById("itemDescription").value;
	if(!validateRequired(itemDescription)){
		alert("项目描述不能为空！");
		return false;
	}
	var containImage = document.getElementById("img").checked;
	if(containImage){
		//设置imageTitle
		var imageTitle = document.getElementById("imageTitle").value;
		if(!validateRequired(imageTitle)){
			alert("图片标题不能为空！");
			return false;
		}
		//设置imageLink
		var imageLink = document.getElementById("imageLink").value;
		if(!validateRequired(imageLink)){
			alert("图片链接不能为空！");
			return false;
		}
		//设置imageDescription
		var imageDescription = document.getElementById("imageDescription").value;
		if(!validateRequired(imageDescription)){
			alert("图片描述不能为空！");
			return false;
		}
	}
	return true;
}

function renderSource(){
	renderBaseProperty();
	renderItemProperty();
	renderImageProperty();
	renerAdvanceProperty();
	return channelSource;
}

function renderBaseProperty(){
	//设置显示名称
	var displayName = document.getElementById("displayName").value;
	channelSource.setMetadataAttribute("displayName",displayName);
	
	//设置language
	var language = document.getElementById("language").value;
	channelSource.setAttribute("language",language);
	
	//设置definitionID
	var definitionID = document.getElementById("definitionID").value;
	channelSource.setMetadataAttribute("definitionID",definitionID);
	
	//设置title
	var title = document.getElementById("title").value;
	channelSource.setAttribute("title",title);
	
	//设置link
	var link = document.getElementById("link").value;
	channelSource.setAttribute("link",link);
	
	//设置description
	var description = document.getElementById("description").value;
	channelSource.setAttribute("description",description);
	
	//设置docs
	var docs = document.getElementById("docs").value;
	channelSource.setAttribute("docs",docs);
	
	//设置ttl
	var ttl = document.getElementById("ttl").value;
	channelSource.setAttribute("ttl",ttl);
	
	//设置copyright
	var copyright = document.getElementById("copyright").value;
	channelSource.setAttribute("copyright",copyright);
}

function renderItemProperty(){
	//设置itemNum
	var itemNum = document.getElementById("itemNum").value;
	channelSource.setMetadataAttribute("itemNum",itemNum);
	
	//设置itemTitle
	var itemTitle = document.getElementById("itemTitle").value;
	channelSource.setItemAttribute("title",itemTitle);
	
	//设置itemLink
	var itemLink = document.getElementById("itemLink").value;
	channelSource.setItemAttribute("link",itemLink);
	
	//设置itemDescription
	var itemDescription = document.getElementById("itemDescription").value;
	channelSource.setItemAttribute("description",itemDescription);
	
	//设置itemDescription
	var itemAuthor = document.getElementById("itemAuthor").value;
	channelSource.setItemAttribute("author",itemAuthor);
	
	//设置itemDescription
	var itemPubDate = document.getElementById("itemPubDate").value;
	channelSource.setItemAttribute("pubDate",itemPubDate);
}

function renderImageProperty(){
	var containImage = document.getElementById("img").checked;
	if(containImage){
		//设置imageTitle
		var imageTitle = document.getElementById("imageTitle").value;
		channelSource.setImageAttribute("title",imageTitle);
		
		//设置imageLink
		var imageLink = document.getElementById("imageLink").value;
		channelSource.setImageAttribute("link",imageLink);
		
		//设置imageDescription
		var imageDescription = document.getElementById("imageDescription").value;
		channelSource.setImageAttribute("description",imageDescription);
	}else{
		channelSource.removeImageElement();
	}
}

function renerAdvanceProperty(){
	//设置样式
	var styles = new Array();
	var currentStyles = document.getElementById("currentStyles").options;
	for(var i=0;i<currentStyles.length;i++){
		styles.push(currentStyles[i].value);
	}
	channelSource.setStyles(styles);
	
	var recursive = document.getElementById("recursive").checked;
	channelSource.setMetadataAttribute("recursive",recursive+"");
	
	var global = document.getElementById("global").checked;
	channelSource.setMetadataAttribute("global",global+"");
	
	var orderField = document.getElementById("orderField").value;
	channelSource.setMetadataAttribute("orderField",orderField);
	
	var orderStyle = GetRadioValue(document.all("orderStyle"));
	channelSource.setMetadataAttribute("orderStyle",orderStyle);
	
	var filters = new Array();
	var filterPatterns = document.getElementById("filterPatterns").options;
	for(var i=0;i<filterPatterns.length;i++){
		filters.push(filterPatterns[i].value);
	}
	channelSource.setFilters(filters);
}

