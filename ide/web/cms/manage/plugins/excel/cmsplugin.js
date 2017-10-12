/**
 * 导出内容到Excel文件
 */
var oExcelExporter = new CMSExporter("导出内容到Excel文件","将内容导出到将Microsoft Excel 格式文件");

oExcelExporter.execute = function(){
	var args = new Object();
	args.definitionID = CMS.GetDefinitionID();
	args.CMS = CMS;
	var url = CMS.Plugins['excel'].Path+"options.jsp?time="+new Date().getTime();
	var options = CMS.ShowDialog(url,args, 500, 400);
	if(options==null)
		return;
	url = CMS.BasePath+"definition/exportExcel.do?definitionID="+CMS.GetDefinitionID();
	for(var i=0;i<options.properties.length;i++)
		url = url+"&column="+options.properties[i];
	if(options.selected){
		var ids = CMS.GetContentIDs();
		if(ids!=null)
			for(var i=0;i<ids.length;i++){
				url = url+"&ID="+ids[i];
			}
	}
	url = url + "&selectedNodes="+options.selected;
	window.open(url);
};
CMSExporterItems.RegisterItem("expExcel", oExcelExporter);
/**
 * 导入Excel文件内容
 */
var oExcelImporter = new CMSImporter("导入Excel文件内容","将Microsoft Excel的内容导入到内容库");

oExcelImporter.execute = function(){
	var url = CMS.BasePath+"definition/importexcel.jsp?time="+new Date().getTime();
	CMS.ShowDialog(url,null, 500, 400);
};
CMSImporterItems.RegisterItem("impExcel", oExcelImporter);