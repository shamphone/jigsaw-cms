
// send内容
var oSendNode= new CMSToolbarButton('sendNode', '导出', '将服务器上的内容按照指定格式导出');
oSendNode.IconPath = CMS.Plugins['exchange'].Path+"download.png";
oSendNode.Click	=	function(){	
	if (CMS.GetDefinitionID() == null) {
		alert("请选择分类！")
		return;
	}
	var args = new Object();
	args.CMSExporterItems = CMSExporterItems;
	args.CMS = CMS;
	var ret = CMS.ShowDialog(CMS.Plugins['exchange'].Path+'export.jsp?tp'+ new Date().getTime(),args,500,400);
	if(ret!=null){
		var exporter = CMSExporterItems.GetItem(ret);
		exporter.execute();
	}
};
CMSToolbarItems.RegisterItem('sendNode',oSendNode);

// 接受内容
var oReceiveNode= new CMSToolbarButton('receiveNode', '导入', '将文件系统中的内容导入到服务器上');
oReceiveNode.IconPath = CMS.Plugins['exchange'].Path+"upload.png";
oReceiveNode.Click	=	function(){	
	if (CMS.GetDefinitionID() == null) {
		alert("请选择分类！")
		return;
	}
	var args = new Object();
	args.CMSImporterItems = CMSImporterItems;
	args.CMS = CMS;
	var ret = CMS.ShowDialog(CMS.Plugins['exchange'].Path+'import.jsp?tp'+ new Date().getTime(),args,500,400);
	if(ret!=null){
		var exporter = CMSImporterItems.GetItem(ret);
		exporter.execute();
	}
};
CMSToolbarItems.RegisterItem('receiveNode',oReceiveNode);





	
