/**
 * 导入Word文档
 */
// Register the related command.
//FCKCommands.RegisterCommand( 'ImportWord', new FCKDialogCommand( 'ImportWord', FCKLang.ImportWordDlgTitle, FCKPlugins.Items['word'].Path + 'fck_word.jsp', 340, 170 ) ) ;

// Create the "Plaholder" toolbar button.
//var oImportWordItem = new FCKToolbarButton( 'ImportWord', FCKLang.ImportWordBtn ) ;
//oImportWordItem.IconPath = FCKPlugins.Items['word'].Path + 'word.gif' ;

//FCKToolbarItems.RegisterItem( 'ImportWord', oImportWordItem ) ;

var FCKImportWordCommand = function(){
	
}

FCKImportWordCommand.prototype.Execute = function()
{
	var url = "../../cms/editor/import.jsp?timestamp=" + Math.random();
	var html = showModalDialog(url,"","dialogWidth:320px;dialogHeight:200px;help:no;scrollbars:yes;status:no");
	if(html)
		FCK.InsertHtml(html);
}
FCKImportWordCommand.prototype.GetState = function()
{
  return FCK_TRISTATE_OFF ;
}

FCKCommands.RegisterCommand( 'ImportWord', new FCKImportWordCommand() ) ;

var oNewCommandItem = new FCKToolbarButton( 'ImportWord',  FCKLang.ImportWordDlgTitle, FCKLang.ImportWordDlgTitle ) ;
oNewCommandItem.IconPath = FCKPlugins.Items['word'].Path + 'word.gif' ;
FCKToolbarItems.RegisterItem( 'ImportWord', oNewCommandItem ) ;


/**
 * Word文档分析转换器
 */
var FCKWordConvertor= function(){
    //可配置参数
    this.FilterIF       =   true;   //过滤IF指令
    this.FilterMSO      =   true;   //过滤掉MSO废代码
    this.ExtractBody    =   true;   //过滤出body标签内的内容
    this.HtmlContent    =   null;
    this.ForReading     =   1; //只读
    this.wordApp        =   null;//Word Application ActiveXObject;
    this.htmlPath       =   "";
    this.fileName       =   "";
}
/**
 * 从word文档中提取HTML内容
 */
FCKWordConvertor.prototype.convertHtml= function(path){
    try{
        this.fso = new ActiveXObject("Scripting.FileSystemObject");//访问文件系统的句柄
    }catch(e){
        alert("为了使用这个功能,需要请把本站点设置为可信站点,设置方法为:在浏览器的菜单栏中,选择工具－internet选项－安全－受信任的站点－站点（按钮）－在将该网站添加到区域中");
        return null;
    }
    this.wordPath=path;
    try{
         this.wordApp = new ActiveXObject("Word.Application");
         this.wordApp.Visible=false;
    }catch(e){
        alert("为了使用这个功能,需要请把本站点设置为可信站点,设置方法为:在浏览器的菜单栏中,选择工具－internet选项－安全－受信任的站点－站点（按钮）－在将该网站添加到区域中");
        return null;
    }
    //将文件转换成HTML文件并保存到临时文件目录中，这个目录中包含html文件和对应的图片文件
    this.htmlPath=this.makeTempHtmlPath(this.wordPath);
    var doc ;
    try{
        doc=this.wordApp.Documents.Open(this.wordPath);
        doc.saveAs(this.htmlPath + "/" + this.fileName,8);
    }catch(e){
        alert(e.message);
        return null;
    } finally{
        doc.close();
        this.wordApp.quit(0);
    }
    //以下是处理HTML文件
    var content= this.readHTML(this.htmlPath + "/" + this.fileName);
    //抽取Body内容
    if(this.ExtractBody)
        content= this.extractBodyContent(content);
    //过滤IF指令
    if(this.FilterIF)
        content=this.filterIFTag(content);
    //过滤掉MSO样式代码
    if(this.FilterMSO)
        content=this.filterMSOStyle(content);
    return content;
}


/**
 * 获取 html文件保存的临时路径
 */
FCKWordConvertor.prototype.makeTempHtmlPath=function(wordPath){
    this.fileName=this.fso.getFileName(wordPath);
    var TemporaryFolder=2;
    return this.fso.GetSpecialFolder(TemporaryFolder).path;
}
/**
 * 获取 html文件的内容
 */
FCKWordConvertor.prototype.readHTML=function(htmlPath){
    var ForReading =   1;
    var htmlFile = this.fso.OpenTextFile(htmlPath, this.ForReading);
    var content = "";
    try{
      content = htmlFile.ReadAll();
      return content;
    }finally{
      htmlFile.close();
    }
}
/**
 * 过滤掉IF指令标签内的内容
 */
FCKWordConvertor.prototype.filterIFTag=function(content){
    var match=/<\!\[if([\s\S]*?)<\!\[endif\]>/ig;
    return content.replace(match,'');
}
/**
 * 过滤掉MSO标签内的内容
 */
FCKWordConvertor.prototype.filterMSOStyle=function(content){
    return content;
}

/**
 * 过滤出Body标签内的内容
 */
FCKWordConvertor.prototype.extractBodyContent= function(content){
    var matcher=/<body([\s\S]*)<\/body>/;
    var bodyContent=content.match(matcher)[0];
    var start=bodyContent.indexOf('>');//first end of <body ...>
    matcher=/<\/body>/;
    var bodyEnd=bodyContent.match(matcher)[0];
    var end=bodyContent.length-bodyEnd.length;
    return bodyContent.substring(start+1,end);
}

var WordConvertor= new FCKWordConvertor();

