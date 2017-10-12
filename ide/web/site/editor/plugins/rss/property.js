var oChannel = window.dialogArguments.oChannel;
var oTemplate = oChannel.template;
var channelSource = window.dialogArguments.channelSource;

window.onload = function() {
	initBaseProperty();
	initStyleTable();
	var definitionID = channelSource.getMetadataAttribute("definitionID");
	initItemProperty(definitionID);
	initImageProperty(definitionID);
	initAdvanceProperty(definitionID);
}

/**
 * 初始化；
 * @return
 */
function initBaseProperty(){
	document.getElementById("name").value = oChannel.name;
	var displayName = channelSource.getMetadataAttribute("displayName");
	if(displayName==null||displayName==""){
		displayName = oChannel.name;
	}
	document.getElementById("displayName").value = displayName;
	
	//设置language
	document.getElementById("language").value = channelSource.getAttribute("language");
	
	//设置definitionID
	var definitionID = channelSource.getMetadataAttribute("definitionID");
	document.getElementById("definitionID").value = definitionID;
	
	//设置title
	document.getElementById("title").value = channelSource.getAttribute("title");
	
	//设置link
	document.getElementById("link").value = channelSource.getAttribute("link");
	
	//设置description
	document.getElementById("description").value = channelSource.getAttribute("description");
	
	//设置docs
	document.getElementById("docs").value = channelSource.getAttribute("docs");
	
	//设置ttl
	document.getElementById("ttl").value = channelSource.getAttribute("ttl");
	
	//设置copyright
	document.getElementById("copyright").value = channelSource.getAttribute("copyright");
	
};

function initItemProperty(definitionID){
	//设置itemNum
	var itemNum =  channelSource.getMetadataAttribute("itemNum");
	if(itemNum==null){
		itemNum = 20;
	}
	document.getElementById("itemNum").value = itemNum;
	
	//设置itemTitle
	var itemTitle =  channelSource.getItemAttribute("title");
	if(definitionID!=null&&itemTitle!=null){
		var name = CMSDialog.GetPropertyDefinitionName(definitionID,itemTitle);
		if(name!=null){
			document.getElementById("itemTitle").value = itemTitle;
			document.getElementById("itemTitleName").value = name;
		}
	}
	
	//设置itemLink
	var itemLink =  channelSource.getItemAttribute("link");
	if(itemLink!=null){
		var channel = oTemplate.getChannel(itemLink.substring(itemLink.indexOf("/",1)));
		channel.refresh();
		document.getElementById("itemLink").value = itemLink;
		document.getElementById("itemLinkName").value = channel.displayName;
	}
	
	//设置itemDescription
	var itemDescription =  channelSource.getItemAttribute("description");
	if(definitionID!=null&&itemDescription!=null){
		var name = CMSDialog.GetPropertyDefinitionName(definitionID,itemDescription);
		if(name!=null){
			document.getElementById("itemDescription").value = itemDescription;
			document.getElementById("itemDescriptionName").value = name;
		}
	}
	
	//设置itemAuthor
	var itemAuthor =  channelSource.getItemAttribute("author");
	if(definitionID!=null&&itemAuthor!=null){
		var name = CMSDialog.GetPropertyDefinitionName(definitionID,itemAuthor);
		if(name!=null){
			document.getElementById("itemAuthor").value = itemAuthor;
			document.getElementById("itemAuthorName").value = name;
		}
	}
	
	//设置itemPubDate
	var itemPubDate =  channelSource.getItemAttribute("pubDate");
	if(definitionID!=null&&itemPubDate!=null){
		var name = CMSDialog.GetPropertyDefinitionName(definitionID,itemPubDate);
		if(name!=null){
			document.getElementById("itemPubDate").value = itemPubDate;
			document.getElementById("itemPubDateName").value = name;
		}
	}
}

function initImageProperty(definitionID){
	var containImage = channelSource.hasImage();
	document.getElementById("img").checked = containImage;
	if(containImage){
		//设置imageTitle
		var imageTitle =  channelSource.getImageAttribute("title");
			document.getElementById("imageTitle").value = imageTitle;
		
		//设置imageLink
		var imageLink =  channelSource.getImageAttribute("link");
			document.getElementById("imageLink").value = imageLink;
		
		//设置imageDescription
		document.getElementById("imageDescription").value = channelSource.getImageAttribute("description");
	}
}

function initAdvanceProperty(definitionID){
	
	document.getElementById("recursive").checked = (channelSource.getMetadataAttribute("recursive")=="true");
	
	document.getElementById("global").checked = (channelSource.getMetadataAttribute("global")=="true");
	
	var orderField = channelSource.getMetadataAttribute("orderField");
	if(definitionID!=null&&orderField!=null){
		var name = CMSDialog.GetPropertyDefinitionName(definitionID,orderField);
		if(name!=null){
			document.getElementById("orderField").value = orderField;
			document.getElementById("fieldName").value = name;
		}
	}
	
	var orderStyle = channelSource.getMetadataAttribute("orderStyle");
	setRadioValue(document.all("orderStyle"),orderStyle);
	
	var filters = channelSource.getFilters();
	if(filters.length>0){
		var repository = new Repository();
		var definition = repository.getNodeDefinition(definitionID);
		var properties = definition.getProperties();
		var props = new Array();
		for(var i=0;i<properties.length;i++){
			var prop = new Object();
			prop.ID = properties[i].getID();
			prop.name = properties[i].getName();
			props[prop.ID] = prop;
		}
		var filterPatterns = document.getElementById("filterPatterns").options;
		for(i=0;i<filters.length;i++){
			FilterParser.init( filters[i] ,props)
			var text = FilterParser.parserProperty()+" "+FilterParser.parserOperation()+" "+ FilterParser.parserValue();
			var opt = document.createElement("option");
			opt.value = filters[i];
			opt.text = text;
			filterPatterns.add(opt);
		}
	}
	
	
}

/**
 * 初始化样式
 * @return
 */
function initStyleTable(){
	var channels = oTemplate.getChannelsOfType("style");
	for(var i=0;i<channels.length;i++ ){
		var channel = channels[i];
		var opt=document.createElement("option");
		opt.text = channel.displayName+"  ("+channel.contextPath+")";
		opt.value = channel.path;
		opt.id = channel.path;
		document.getElementById("allStyles").options.add(opt);
	};
	var styles = channelSource.getStyles();
	for(i=0;i<styles.length;i++){
		var style = styles[i];
		if((style.trim().length>0)&&(style.indexOf("fck")<0) && (style.indexOf("portal")<0)){
			var opt = document.getElementById(style);
			if(opt!=null){
				var newOpt = document.createElement("option");
				newOpt.value = opt.value;
				if(document.all){
					newOpt.text = opt.text;
					document.getElementsByName("currentStyles")[0].options.add(newOpt);
				}else{
					newOpt.textContent = opt.textContent;
					document.getElementsByName("currentStyles")[0].add(newOpt,null);
				}
			}
		}
	}
};

function doOK() {
	if(validate()){
		var channelSource = renderSource();
		var param = new Object();
		window.returnValue = channelSource;
		window.close();
	}
	enableButton();
}
