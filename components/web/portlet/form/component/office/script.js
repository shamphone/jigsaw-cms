
function DsoFramer (officeEditor){
	this.officeEditor = officeEditor;
	this.officeEditor.SetMenuDisplay(0x02|0x16|0x64|0x256|0x32);
	this.OLEType = {
		Excel_Spreadsheet 		:	"Excel.Sheet" ,
		Excel_Chart				:	"Excel.Chart" ,
		PowerPoint_Presentation : 	"PowerPoint.Show" ,
		Project_Project    		:  	"MSProject.Project" ,
		Visio_Drawing      		: 	"Visio.Drawing" ,
		Word_Document       	:	"Word.Document"
	};
}

DsoFramer.prototype = {
	openServer		:	function(path,OLEType){
		OLEType = OLEType||this.OLEType.Word_Document;
		try{
			this.officeEditor.Open (encodeURI(path),false,OLEType);
			return true;
		}catch(e){
			return false;
		}
	},
	createNew		:	function(content,OLEType){
		OLEType = OLEType||this.OLEType.Word_Document;
		try{
			this.officeEditor.CreateNew(OLEType);
			if(content!=null){
				this.officeEditor.ActiveDocument.Content.text = content;
			}
			return true;
		}catch(e){
			return false;
		}
	},
	save		:	function(docName){			// docName 文件名
		var url = location.protocol +"//"+location.host +'/ide/cms/resource/officeUpload.do';
		this.officeEditor.HttpInit();
		this.officeEditor.HttpAddPostCurrFile("file[0]",docName);
		return this.officeEditor.HttpPost(encodeURI(url)); 
	},
	close		:	function(){
		this.officeEditor.Close();
		this.officeEditor = null;
	},
	getOfficeVersion:	function(OLEType){
		OLEType = OLEType||this.OLEType.Word_Document;
		var StrVersion = this.officeEditor.GetOfficeVersion(OLEType);
		var VERSION_PATTERN = /^(.*?)\.([^\.]*)$/gi;
		VERSION_PATTERN.test(StrVersion)
		var version = RegExp["$2"];
		switch(version){
			case "8":	return "2003";
			case "12":	return "2007";
			default :	return null;
		}
	}
}