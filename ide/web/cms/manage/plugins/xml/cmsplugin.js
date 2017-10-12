var oDefExporter = new CMSExporter("导出分类格式","将当前分类导出到XML文件");
oDefExporter.execute = function(){
	var repository = window.top.repository;
	var ret = CMS.ShowDialog(CMS.Plugins['xml'].Path+"exportDefinitions.jsp",repository);
	if(ret== null)
		return;
	var url = CMS.BasePath+"definition/export.do?time="+new Date().getTime()+ "&exportDefinition=true";
	for(var i=0;i<ret.definitions.length;i++)
		url = url +"&ID="+ret.definitions[i];
	if(ret.exportNodes)
		url = url +"&exportAllNodes=true";
	window.open(url);
	
};
CMSExporterItems.RegisterItem("expDefinitions", oDefExporter);
/**
 * 导出内容到XML文件
 */
var oXmlExporter = new CMSExporter("导出内容到XML文件","将内容导出到XML文件");

oXmlExporter.execute = function(){
	var ret = CMS.ShowDialog(CMS.Plugins['xml'].Path+"exportNodes.jsp");
	if(ret== null)
		return;
	var url = CMS.BasePath+"definition/export.do?time="+new Date().getTime() + "&ID="+CMS.GetDefinitionID() + "&exportDefinition=" + ret.exportDefinition + "&exportAllNodes=" + ret.exportAll;
	if(!ret.exportAll)
	{
		var ids = CMS.GetContentIDs();
		if(ids!=null&&ids.length>0)
		{
			for(var i=0;i<ids.length;i++)
				url = url+"&nodeID="+ids[i];
		}
		else
		{
			alert("请选择被导出的内容!");
			return;
		}
	}
	window.open(url);
};
CMSExporterItems.RegisterItem("expXML", oXmlExporter);
/**
 * 导入XML文件内容
 */
var oXmlImporter = new CMSImporter("导入XML文件内容","将内容导入到内容库");

oXmlImporter.execute = function(){
	var url = CMS.BasePath+"definition/importxml.jsp?time="+new Date().getTime();
	CMS.ShowDialog(url,null, 500, 400);
};
CMSImporterItems.RegisterItem("impXML", oXmlImporter);