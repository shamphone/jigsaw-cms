DSO的接口文档

/*
1.新建
*/
//新建Word
document.all.FramerControl1.CreateNew("Word.Document");
//新建Excel
document.all.FramerControl1.CreateNew("Excel.Sheet");
/*
2.打开文件
*/
//打开制定的本地文件
document.all.FramerControl1.Open("C:\\TestBook.xls");
//制定用Word来打开c:\plain.txt文件
document.all.FramerControl1.Open("C:\\Plain.txt",false, "Word.Document");
//打开服务器的文件 
document.all.FramerControl1.Open "https://secureserver/test/mytest.asp?id=123",true, "Excel.Sheet", "MyUserAccount", "MyPassword");
//打开服务器的文件 
document.all.FramerControl1.Open("http://localhost/1.doc", true);
/*
3.保存文件
*/
//到本地
document.all.FramerControl1.Save("c:\\1.doc",true);
//服务器   
/*增加Http协议Post上传接口,可以Post一个动态页面(jsp,asp,php...),由动态页面负责解析数据
bool HttpInit();
bool HttpAddPostString(BSTR strName, BSTR strValue);
bool HttpAddPostCurrFile(BSTR strFileID, BSTR strFileName);
BSTR HttpPost(BSTR bstr);        
*/
//初始化Http引擎
document.all.FramerControl1.HttpInit();
//增加Post变量
document.all.FramerControl1.HttpAddPostString("RecordID","20060102200");
document.all.FramerControl1.HttpAddPostString("UserID","李局长");
//上传打开的文件
document.all.FramerControl1.HttpAddPostCurrFile("FileData", "文档名.doc");
//执行上传动作
document.all.FramerControl1.HttpPost("http://xxxx.com/uploadfile.asp"); 
/*
4.修订留痕
*/
//进入留痕状态
document.all.FramerControl1.SetTrackRevisions(1);
//进入非留痕状态
document.all.FramerControl1.SetTrackRevisions(0);
//接受当前修订
document.all.FramerControl1.SetTrackRevisions(4);
/*
5.设置当前用户
*/
document.all.FramerControl1.SetCurrUserName("张三");        
/*
6.设置当前时间(笔迹留痕会显示("Like 2006:02:07 11:11:11")
*/
document.all.FramerControl1.SetCurrTime("2006:02:07 11:11:11");
/*
7.设置和创建书签，此功能比较强大，设置书签数据、添加书签和添加红头文件就靠他了
SetFieldValue(BSTR strFieldName, BSTR strValue, BSTR strCmdOrSheetName)
strFieldName:书签名
strValue：要设置的值
strCmdOrSheetName：
命令 
::ADDMARK::    添加BookMark
::DELMARK::          删除这个BookMark
::GETMARK::    定位到这个BookMark
::FILE::                         插入的是文件
::JPG::        插入的是图片
一般来说：WORD中书签是做好的，可以通过此接口把外界数据设置进书签中去。
*/
//在当前WORD位置插入标签,标签名为"book1",数值为"test"
document.all.FramerControl1.SetFieldValue("book1","test","::ADDMARK::");
//设置书签"Time",数值为"2006-03-16 22:22:22"
document.all.FramerControl1.SetFieldValue("Time","2006-03-16 22:22:22","");
//在书签位置"hongtou",插入红头文件"http://222.222.222.222/hongtou1.doc" 这样，红头就自动插进去了
document.all.FramerControl1.SetFieldValue("hongtou","http://222.222.222.222/hongtou1.doc","::FILE::");
/*
8.设置菜单显示情况
BOOL SetMenuDisplay(long lMenuFlag)
lMenuFlag为以下数值的组合
#define MNU_NEW                         0x01
#define MNU_OPEN                        0x02
#define MNU_CLOSE                       0x04
#define MNU_SAVE                        0x08
#define MNU_SAVEAS                      0x16
#define MNU_PGSETUP                     0x64
#define MNU_PRINT                       0x256
#define MNU_PROPS                       0x32
#define MNU_PRINTPV                     0x126
*/
//只有“新建”菜单可用 
document.all.FramerControl1..SetMenuDisplay(1);
//只有“打开”菜单可用 
document.all.FramerControl1.SetMenuDisplay(2);
//只有“打开”和“新建”菜单可用 
document.all.FramerControl1.SetMenuDisplay(3);
/*
9.保护文档和解保护文档
lProOrUn:1:保护文档；0:解除保护
lProType:  
    wdNoProtection = -1,
    wdAllowOnlyRevisions = 0,
    wdAllowOnlyComments = 1,
    wdAllowOnlyFormFields = 2
strProPWD:密码
*/
//完全保护文档，密码为"pwd"    
document.all.FramerControl1.ProtectDoc(1,1,"pwd");
//解除文档保护                 
document.all.FramerControl1.ProtectDoc(0,1,"pwd");
/*
10.显示或隐藏修订内容
ShowRevisions(long nNewValue)
nNewValue = 0 则隐藏修订
          = 1 则显示修订
*/
//显示修订留痕
document.all.FramerControl1.ShowRevisions(1);
//隐藏修订留痕
document.all.FramerControl1.ShowRevisions(0);
/*
11.插入合并文件，
strFieldPath 文件路径,可以是http,ftp的路径
pPos = 0 //当前鼠标位置
1;文件开头
2;文件末尾
pPos的第4位为1的时候，代表插入的是图片
InSertFile(BSTR strFieldPath, long lPos)
*/
//文件头部插入文件
document.all.FramerControl1.InSertFile("http://XX.com/XX.doc",1);
//文件尾部插入文件 
document.all.FramerControl1.InSertFile("http://XX.com/XX.doc",2);
//当前光标位置插入文件
document.all.FramerControl1.InSertFile("http://XX.com/XX.doc",0);
//文件头部插入图片
document.all.FramerControl1.InSertFile("http://XX.com/XX.jpg",9);
//文件尾部插入图片
document.all.FramerControl1.InSertFile("http://XX.com/XX.jpg",10);
//当前光标位置插入图片
document.all.FramerControl1.InSertFile("http://XX.com/XX.jpg",8);
/*
0x31. 文档另存为
                HRESULT SaveAs([in] VARIANT strFileName, [in] VARIANT dwFileFormat, [out,retval] long* pbool);        
参数：
        strFileName：文件本地路径，如c:\\11.doc
        dwFileFormat: 文件格式
dwFileFormat的数值为：
Excel: Type
enum XlFileFormat
{
    xlAddIn = 18,
    xlCSV = 6,
    xlCSVMac = 22,
    xlCSVMSDOS = 24,
    xlCSVWindows = 23,
    xlDBF2 = 7,
    xlDBF3 = 8,
    xlDBF4 = 11,
    xlDIF = 9,
    xlExcel2 = 16,
    xlExcel2FarEast = 27,
    xlExcel3 = 29,
    xlExcel4 = 33,
    xlExcel5 = 39,
    xlExcel7 = 39,
    xlExcel9795 = 43,
    xlExcel4Workbook = 35,
    xlIntlAddIn = 26,
    xlIntlMacro = 25,
    xlWorkbookNormal = -4143,
    xlSYLK = 2,
    xlTemplate = 17,
    xlCurrentPlatformText = -4158,
    xlTextMac = 19,
    xlTextMSDOS = 21,
    xlTextPrinter = 36,
    xlTextWindows = 20,
    xlWJ2WD1 = 14,
    xlWK1 = 5,
    xlWK1ALL = 31,
    xlWK1FMT = 30,
    xlWK3 = 15,
    xlWK4 = 38,
    xlWK3FM3 = 32,
    xlWKS = 4,
    xlWorks2FarEast = 28,
    xlWQ1 = 34,
    xlWJ3 = 40,
    xlWJ3FJ3 = 41,
    xlUnicodeText = 42,
    xlHtml = 44
};
Word: Type
enum WdSaveFormat
{
    wdFormatDocument = 0,
    wdFormatTemplate = 1,
    wdFormatText = 2,
    wdFormatTextLineBreaks = 3,
    wdFormatDOSText = 4,
    wdFormatDOSTextLineBreaks = 5,
    wdFormatRTF = 6,
    wdFormatUnicodeText = 7,
    wdFormatEncodedText = 7,
    wdFormatHTML = 8
};
PPT:
enum PpSaveAsFileType
{
    ppSaveAsPresentation = 1,
    ppSaveAsPowerPoint7 = 2,
    ppSaveAsPowerPoint4 = 3,
    ppSaveAsPowerPoint3 = 4,
    ppSaveAsTemplate = 5,
    ppSaveAsRTF = 6,
    ppSaveAsShow = 7,
    ppSaveAsAddIn = 8,
    ppSaveAsPowerPoint4FarEast = 10,
    ppSaveAsDefault = 11,
    ppSaveAsHTML = 12,
    ppSaveAsHTMLv3 = 13,
    ppSaveAsHTMLDual = 14,
    ppSaveAsMetaFile = 15,
    ppSaveAsGIF = 16,
    ppSaveAsJPG = 17,
    ppSaveAsPNG = 18,
    ppSaveAsBMP = 19
};
*/
/*
0x32. 删除本地文件
                        HRESULT DeleteLocalFile([in] BSTR strFilePath);        
参数：
        strFileName：文件本地路径，如c:\\11.doc                        
*/                
/*
0x33.创建临时文件
                HRESULT GetTempFilePath([out,retval] BSTR* strValue);        
返回：
    临时文件的路径地址。使用完后，用DeleteLocalFile 删除
*/
/*
0x34.设置文档显示模式
                HRESULT ShowView([in] long dwViewType, [out,retval] long * pbool);        
dwViewType的可取值为：
enum WdViewType
{
    wdNormalView = 1,
    wdOutlineView = 2,
    wdPrintView = 3,
    wdPrintPreview = 4,
    wdMasterView = 5, //这个是大纲
    wdWebView = 6
};
*/ 
//大纲模式
document.all.FramerControl1.ShowView(5);
/*
0x39:下载远程文件
                HRESULT DownloadFile( [in] BSTR strRemoteFile, [in] BSTR strLocalFile, [out,retval]  BSTR* strValue);        
参数：
         strRemoteFile：远程路径地址,http or Ftp
         strLocalFile: 本地保存地址，if strLocalFile == NULL then Create Temp File and return TempFile's Path
*/
/*
0x40：增加Http上传时候的，附加其他文件
                HRESULT HttpAddPostFile([in] BSTR strFileID, [in]  BSTR strFileName, [out,retval] long* pbool);
参数：
         strFileID：文件的ID，供服务器端页面解析
         strFileName: 本地文件地址 
*/
/*
0x41,0x42.获取详细的修订信息。
   GetRevCount( [out,retval] long * pbool);
   GetRevInfo([in] long lIndex, [in]  long lType, [out,retval] BSTR* pbool);
   例子如下
*/
var vCount;
vCount = document.all.FramerControl1.GetRevCount();
alert(vCount);
var vOpt = 0;
var vDate;
for(var i=1; i<= vCount; i++){
        vOpt = document.all.FramerControl1.GetRevInfo(i,2);
        if("1" == vOpt){
                vOpt = "插入";
        }else if("2" == vOpt){
                vOpt = "删除";
        }else{
                vOpt = "未知操作";
        }
        vDate = new String(document.all.FramerControl1.GetRevInfo(i,1));
        vDate = parseFloat(vDate);
        alert(vDate);
        dateObj = new Date(vDate);
  alert(dateObj.getYear()   + "年" + dateObj.getMonth() + 1 + "月" + dateObj.getDate() +"日" +  dateObj.getHours() +"时" +  dateObj.getMinutes() +"分" +  dateObj.getSeconds() +"秒" );
        alert("用户:"+document.all.FramerControl1.GetRevInfo(i,0) + "\r\n操作:" + vOpt + "\r\n内容:" + document.all.FramerControl1.GetRevInfo(i,3));
}
/*
0x43.设置基本信息：
                HRESULT SetValue([in] BSTR strValue, [in]  BSTR strName, [out,retval] long* pbool);
1.设置文件只读密码
        SetValue("password","::DOCPROP:PassWord");
2.设置文件修改密码
        SetValue("password","::DOCPROP:WritePW");
返回值：
0 正确
-1：不支持此命令，请确定您的第二个参数没有传错
-127:异常
*/
//设置文件只读密码
document.all.FramerControl1.SetValue("password","::DOCPROP:PassWord");
//设置文件修改密码
document.all.FramerControl1.SetValue("password","::DOCPROP:WritePW");
/*
0x44.设置文档变量，这个很少能用到
                HRESULT SetDocVariable([in] BSTR strVarName, [in]  BSTR strValue,[in] long lOpt, [out,retval] long* pbool);
strVarName: 变量名
strVlaue:变量值
lOpt: 操作类型，
按位
第一位为1:  表示update域关联的
第二位为1:  表示如果没有这个变量则添加
第三位为1:  未来支持
return:
0:OK
-127:异常
*/
/*
0x45: 分页保存
HRESULT SetPageAs([in] BSTR strLocalFile, [in]  long lPageNum, [in]  long lType,[out,retval] long* pbool);
strLocalFile：本地路径
lPageNum：页数
*/

