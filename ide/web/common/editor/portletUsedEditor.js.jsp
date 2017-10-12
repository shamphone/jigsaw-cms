<%@ page contentType="text/javascript; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>



FCKConfig.Plugins.Add( 'media','en,zh-cn' ) ;
FCKConfig.Plugins.Add( 'paragraph','en,zh-cn');
FCKConfig.Plugins.Add( 'word','en,zh-cn');
FCKConfig.Plugins.Add( 'currentdate','en,zh-cn');
FCKConfig.Plugins.Add( 'currenttime','en,zh-cn');
FCKConfig.Plugins.Add( 'pagebreak','en,zh-cn');
FCKConfig.Plugins.Add( 'marquee','en,zh-cn');
FCKConfig.Plugins.Add( 'wordart','en,zh-cn');
FCKConfig.Plugins.Add( 'hrule','en,zh-cn');
FCKConfig.Plugins.Add( 'bgpic','en,zh-cn');
FCKConfig.Plugins.Add( 'uploadflash','en,zh-cn');
FCKConfig.Plugins.Add( 'uploadimage','en,zh-cn');

FCKConfig.ToolbarSets["Default"] = [
	['Source','FontFormat','FontName','FontSize'],
	['Cut','Copy','Paste','PasteText','PasteWord'],
	['Undo','Redo','SelectAll','RemoveFormat'],
	['Bold','Italic','Underline','StrikeThrough','Superscript','Subscript'],
	['TextColor','BGColor','Bgpic'],
        ['Image','Flash','SpecialChar','Smiley','Wordart','CurrentDate','CurrentTime']
] ;

FCKConfig.ToolbarSets["Basic"] = [
	['Bold','Italic','-','OrderedList','UnorderedList','-','Link','Unlink','-','About']
] ;

FCKConfig.EnterMode = 'br' ;			// p | div | br
FCKConfig.ShiftEnterMode = 'br' ;	// p | div | br


FCKConfig.FontFormats	= 'p;h1;h2;h3;h4;h5;h6;pre;address;div' ;
FCKConfig.FontNames		= 'Arial;Comic Sans MS;Courier New;Tahoma;Times New Roman;Verdana' ;
FCKConfig.FontSizes		= 'smaller;larger;xx-small;x-small;small;medium;large;x-large;xx-large;' ;


FCKConfig.LinkBrowser = true ;
FCKConfig.LinkBrowserURL = '<html:rewrite module="/site/resource" page="/index.do"/>?templateID='+oTemplate.ID;
FCKConfig.LinkBrowserWindowWidth	=  FCKConfig.ScreenWidth * 0.7  ;		// 70%
FCKConfig.LinkBrowserWindowHeight	=  FCKConfig.ScreenWidth * 0.8  ;	// 70%

FCKConfig.ImageBrowser = true ;
FCKConfig.ImageBrowserURL = '<html:rewrite module="/site/resource" page="/index.do"/>?templateID='+oTemplate.ID;
FCKConfig.ImageBrowserWindowWidth  =  FCKConfig.ScreenWidth * 0.7  ;	// 70% ;
FCKConfig.ImageBrowserWindowHeight =  FCKConfig.ScreenWidth * 0.8  ;	// 70% ;

FCKConfig.FlashBrowser = true ;
FCKConfig.FlashBrowserURL = '<html:rewrite module="/site/resource" page="/index.do"/>?templateID='+oTemplate.ID;
FCKConfig.FlashBrowserWindowWidth  =  FCKConfig.ScreenWidth * 0.7  ;	//70% ;
FCKConfig.FlashBrowserWindowHeight =  FCKConfig.ScreenWidth * 0.8  ;	//70% ;

FCKConfig.LinkUpload = false ;
FCKConfig.LinkUploadURL = '<html:rewrite module="/site/resource" page="/index.do"/>?templateID='+oTemplate.ID;
