package org.apache.jsp.site.editor;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class pageEditor_js_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(4);
    _jspx_dependants.add("/WEB-INF/struts-logic.tld");
    _jspx_dependants.add("/WEB-INF/struts-bean.tld");
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
    _jspx_dependants.add("/WEB-INF/struts-tiles.tld");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.release();
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    JspFactory _jspxFactory = null;
    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      _jspxFactory = JspFactory.getDefaultFactory();
      response.setContentType("text/javascript; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("FCKConfig.BasePath=\"");
      if (_jspx_meth_html_005frewrite_005f0(_jspx_page_context))
        return;
      out.write("\";\r\n");
      out.write("FCKConfig.EditorPath=\"");
      if (_jspx_meth_html_005frewrite_005f1(_jspx_page_context))
        return;
      out.write("\";\r\n");
      out.write("FCKConfig.FullBasePath = document.location.protocol + '//' + document.location.host + FCKConfig.BasePath ;\r\n");
      out.write("FCKConfig.CustomConfigurationsPath = '' ;\r\n");
      out.write("FCKConfig.EditorAreaCSS = [FCKConfig.BasePath + 'css/fck_editorarea.css',portalStyleURL] ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.EditorAreaStyles = '' ;\r\n");
      out.write("FCKConfig.ToolbarComboPreviewCSS = '' ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.DocType = '' ;\r\n");
      out.write("\r\n");
      out.write("//FCKConfig.BaseHref = toAbsoluteURL(editingFilePath);\r\n");
      out.write("FCKConfig.BaseHref = '';\r\n");
      out.write("\r\n");
      out.write("// The following option determines whether the \"Show Blocks\" feature is enabled or not at startup.\r\n");
      out.write("FCKConfig.StartupShowBlocks = false ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.Debug = false ;\r\n");
      out.write("FCKConfig.AllowQueryStringDebug = true ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.SkinPath = FCKConfig.BasePath + 'skins/default/' ;\r\n");
      out.write("FCKConfig.PreloadImages = [ FCKConfig.SkinPath + 'images/toolbar.start.gif', FCKConfig.SkinPath + 'images/toolbar.buttonarrow.gif' ] ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.PluginsPath = FCKConfig.EditorPath + 'plugins/' ;\r\n");
      out.write("FCKConfig.Plugins.Add( 'portlet','en,zh-cn' ) ;\r\n");
      out.write("FCKConfig.Plugins.Add( 'jspsource') ;\r\n");
      out.write("FCKConfig.Plugins.Add( 'tagpath') ;\r\n");
      out.write("FCKConfig.Plugins.Add( 'channel','en,zh-cn') ;\r\n");
      out.write("FCKConfig.Plugins.Add( 'template','en,zh-cn') ;\r\n");
      out.write("FCKConfig.Plugins.Add( 'exform','en,zh-cn') ;\r\n");
      out.write("FCKConfig.Plugins.Add( 'style','en,zh-cn') ;\r\n");
      out.write("//FCKConfig.Plugins.Add( 'jsmanager') ;\r\n");
      out.write("//FCKConfig.Plugins.Add('tablecommands','en,zh-cn', FCKConfig.BasePath+ 'plugins/' );\r\n");
      out.write("FCKConfig.Plugins.Add( 'fck_media','en,zh-cn', FCKConfig.BasePath+ 'plugins/' ) ;\r\n");
      out.write("// highlight_plugins\r\n");
      out.write("//FCKConfig.Plugins.Add('ugeshi', 'zh-cn', FCKConfig.BasePath+ 'plugins/');\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("FCKConfig.AutoGrowMax = 400 ;\r\n");
      out.write("FCKConfig.AutoDetectLanguage\t= true ;\r\n");
      out.write("FCKConfig.DefaultLanguage\t= 'en' ;\r\n");
      out.write("FCKConfig.ContentLangDirection\t= 'ltr' ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.ProcessHTMLEntities\t= true ;\r\n");
      out.write("FCKConfig.IncludeLatinEntities\t= true ;\r\n");
      out.write("FCKConfig.IncludeGreekEntities\t= true ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.ProcessNumericEntities = false ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.AdditionalNumericEntities = ''  ;\t\t// Single Quote: \"'\"\r\n");
      out.write("\r\n");
      out.write("FCKConfig.FillEmptyBlocks\t= false ;\r\n");
      out.write("FCKConfig.IgnoreEmptyParagraphValue = true ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.FormatSource\t\t= true ;\r\n");
      out.write("FCKConfig.FormatOutput\t\t= true ;\r\n");
      out.write("FCKConfig.FormatIndentator\t= '  ' ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.StartupFocus\t= true ;\r\n");
      out.write("FCKConfig.ForcePasteAsPlainText\t= false ;\r\n");
      out.write("FCKConfig.AutoDetectPasteFromWord = true ;\t// IE only.\r\n");
      out.write("FCKConfig.ShowDropDialog = true ;\r\n");
      out.write("FCKConfig.ForceSimpleAmpersand\t= false ;\r\n");
      out.write("FCKConfig.TabSpaces\t\t= 0 ;\r\n");
      out.write("FCKConfig.ShowBorders\t= true ;\r\n");
      out.write("FCKConfig.SourcePopup\t= false ;\r\n");
      out.write("FCKConfig.ToolbarStartExpanded\t= true ;\r\n");
      out.write("FCKConfig.ToolbarCanCollapse\t= true ;\r\n");
      out.write("FCKConfig.IgnoreEmptyParagraphValue = true ;\r\n");
      out.write("FCKConfig.PreserveSessionOnFileBrowser = false ;\r\n");
      out.write("FCKConfig.FloatingPanelsZIndex = 10000 ;\r\n");
      out.write("FCKConfig.HtmlEncodeOutput = false ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.TemplateReplaceAll = true ;\r\n");
      out.write("FCKConfig.TemplateReplaceCheckbox = true ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.ToolbarLocation = 'In' ;\r\n");
      out.write("/**\r\n");
      out.write(" * 配置在页面上使用的按钮\r\n");
      out.write(" */\r\n");
      out.write("FCKConfig.ToolbarSets[\"Default\"] = [\r\n");
      out.write("\t['NewObject','SaveChannel','Preview','PublishChannel','RestoreChannel','DeleteChannel','MoveChannel','Unlock','PageSize','ImportHTML','EditChannel','Authorization'],\r\n");
      out.write("    ['Resources'],\r\n");
      out.write("     '/',\r\n");
      out.write("\t['Source','Cut','Copy','Paste','PasteText','PasteWord','-','Print','SpellCheck'],\r\n");
      out.write("\t['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],\r\n");
      out.write("\t['Bold','Italic','Underline','StrikeThrough','-','Subscript','Superscript'],\r\n");
      out.write("\t['OrderedList','UnorderedList','-','Outdent','Indent','Blockquote'],\r\n");
      out.write("\t['JustifyLeft','JustifyCenter','JustifyRight','JustifyFull'],\r\n");
      out.write("\t['Link','Unlink','Anchor'],\r\n");
      out.write("        '/',\r\n");
      out.write("\t['Image','Flash','Media','Table','Rule','Smiley','SpecialChar','PageBreak'],\r\n");
      out.write("\t['CSS','FontFormat','FontName','FontSize'],\r\n");
      out.write("\t['TextColor','BGColor','-','ExForm','Portlet'],\r\n");
      out.write("        '/',\r\n");
      out.write("        ['TagPath']\r\n");
      out.write("] ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.ToolbarSets[\"Basic\"] = [\r\n");
      out.write("\t['Bold','Italic','-','OrderedList','UnorderedList','-','Link','Unlink','-','About']\r\n");
      out.write("] ;\r\n");
      out.write("\r\n");
      out.write("\t/**\r\n");
      out.write("\t * 删除表格时出错\r\n");
      out.write("\t * 经测试 FCKeditor在EnterMode为br时删除表格时会有bug  \r\n");
      out.write("\t */\r\n");
      out.write("FCKConfig.EnterMode = 'br' ;\t\t\t// p | div | br\r\n");
      out.write("FCKConfig.ShiftEnterMode = 'p' ;\t// p | div | br\r\n");
      out.write("\r\n");
      out.write("FCKConfig.Keystrokes = [\r\n");
      out.write("\t[ CTRL + 65 /*A*/, true ],\r\n");
      out.write("\t[ CTRL + 67 /*C*/, true ],\r\n");
      out.write("\t[ CTRL + 70 /*F*/, true ],\r\n");
      out.write("\t[ CTRL + 83 /*S*/, true ],\r\n");
      out.write("\t[ CTRL + 88 /*X*/, true ],\r\n");
      out.write("\t[ CTRL + 86 /*V*/, 'Paste' ],\r\n");
      out.write("\t[ SHIFT + 45 /*INS*/, 'Paste' ],\r\n");
      out.write("\t[ CTRL + 88 /*X*/, 'Cut' ],\r\n");
      out.write("\t[ SHIFT + 46 /*DEL*/, 'Cut' ],\r\n");
      out.write("\t[ CTRL + 90 /*Z*/, 'Undo' ],\r\n");
      out.write("\t[ CTRL + 89 /*Y*/, 'Redo' ],\r\n");
      out.write("\t[ CTRL + SHIFT + 90 /*Z*/, 'Redo' ],\r\n");
      out.write("\t[ CTRL + 76 /*L*/, 'Link' ],\r\n");
      out.write("\t[ CTRL + 66 /*B*/, 'Bold' ],\r\n");
      out.write("\t[ CTRL + 73 /*I*/, 'Italic' ],\r\n");
      out.write("\t[ CTRL + 85 /*U*/, 'Underline' ],\r\n");
      out.write("\t[ CTRL + SHIFT + 83 /*S*/, 'Save' ],\r\n");
      out.write("\t[ CTRL + ALT + 13 /*ENTER*/, 'FitWindow' ],\r\n");
      out.write("\t[ CTRL + 9 /*TAB*/, 'Source' ]\r\n");
      out.write("] ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.ContextMenu = ['Generic','Link','Anchor','Image','Flash','Media','EditPortlet','Select','Textarea','Checkbox','Radio','TextField','HiddenField','ImageButton','Button','BulletedList','NumberedList','Table','Form'] ;\r\n");
      out.write("FCKConfig.BrowserContextMenuOnCtrl = false ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.EnableMoreFontColors = true ;\r\n");
      out.write("FCKConfig.FontColors = '000000,993300,333300,003300,003366,000080,333399,333333,800000,FF6600,808000,808080,008080,0000FF,666699,808080,FF0000,FF9900,99CC00,339966,33CCCC,3366FF,800080,999999,FF00FF,FFCC00,FFFF00,00FF00,00FFFF,00CCFF,993366,C0C0C0,FF99CC,FFCC99,FFFF99,CCFFCC,CCFFFF,99CCFF,CC99FF,FFFFFF' ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.FontFormats\t= 'p;h1;h2;h3;h4;h5;h6;pre;address;div' ;\r\n");
      out.write("FCKConfig.FontNames\t\t= 'Arial;Comic Sans MS;Courier New;Tahoma;Times New Roman;Verdana' ;\r\n");
      out.write("FCKConfig.FontSizes\t\t= '42pt/初号;36pt/小初;26pt/一号;24pt/小一;22pt/二号;18pt/小二;16pt/三号;15pt/小三;14pt/四号;12pt/小四;10pt/五号;9pt/小五;7pt/六号;6pt/小六;5pt/七号;5pt/八号;8px/8;9px/9;10px/10;11px/11;12px/12;14px/14;16px/16;18px/18;20px/20;22px/22;24px/24;26px/26;28px/28;36px/36;48px/48;72px/72';\r\n");
      out.write("\r\n");
      out.write("FCKConfig.StylesXmlPath\t\t= FCKConfig.BasePath + 'fckstyles.xml' ;\r\n");
      out.write("FCKConfig.TemplatesXmlPath\t= FCKConfig.BasePath + 'fcktemplates.xml' ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.SpellChecker\t\t\t= 'ieSpell' ;\t// 'ieSpell' | 'SpellerPages'\r\n");
      out.write("FCKConfig.IeSpellDownloadUrl\t= 'http://www.iespell.com/download.php' ;\r\n");
      out.write("FCKConfig.SpellerPagesServerScript = 'server-scripts/spellchecker.php' ;\t// Available extension: .php .cfm .pl\r\n");
      out.write("FCKConfig.FirefoxSpellChecker\t= false ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.MaxUndoLevels = 15 ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.DisableObjectResizing = false ;\r\n");
      out.write("FCKConfig.DisableFFTableHandles = true ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.LinkDlgHideTarget\t\t= false ;\r\n");
      out.write("FCKConfig.LinkDlgHideAdvanced\t= false ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.ImageDlgHideLink\t\t= false ;\r\n");
      out.write("FCKConfig.ImageDlgHideAdvanced\t= false ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.FlashDlgHideAdvanced\t= false ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.ProtectedTags = '' ;\r\n");
      out.write("\r\n");
      out.write("// This will be applied to the body element of the editor\r\n");
      out.write("FCKConfig.BodyId = '' ;\r\n");
      out.write("FCKConfig.BodyClass = '' ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.DefaultStyleLabel = '' ;\r\n");
      out.write("FCKConfig.DefaultFontFormatLabel = '' ;\r\n");
      out.write("FCKConfig.DefaultFontLabel = '' ;\r\n");
      out.write("FCKConfig.DefaultFontSizeLabel = '' ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.DefaultLinkTarget = '' ;\r\n");
      out.write("\r\n");
      out.write("// The option switches between trying to keep the html structure or do the changes so the content looks like it was in Word\r\n");
      out.write("FCKConfig.CleanWordKeepsStructure = false ;\r\n");
      out.write("\r\n");
      out.write("// Only inline elements are valid.\r\n");
      out.write("FCKConfig.RemoveFormatTags = 'b,big,code,del,dfn,em,font,i,ins,kbd,q,samp,small,span,strike,strong,sub,sup,tt,u,var' ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.CustomStyles =\r\n");
      out.write("{\r\n");
      out.write("\t'Red Title'\t: { Element : 'h3', Styles : { 'color' : 'Red' } }\r\n");
      out.write("};\r\n");
      out.write("\r\n");
      out.write("// Do not add, rename or remove styles here. Only apply definition changes.\r\n");
      out.write("FCKConfig.CoreStyles =\r\n");
      out.write("{\r\n");
      out.write("\t// Basic Inline Styles.\r\n");
      out.write("\t'Bold'\t\t\t: { Element : 'b', Overrides : 'strong' },\r\n");
      out.write("\t'Italic'\t\t: { Element : 'i', Overrides : 'em' },\r\n");
      out.write("\t'Underline'\t\t: { Element : 'u' },\r\n");
      out.write("\t'StrikeThrough'\t: { Element : 'strike' },\r\n");
      out.write("\t'Subscript'\t\t: { Element : 'sub' },\r\n");
      out.write("\t'Superscript'\t: { Element : 'sup' },\r\n");
      out.write("\r\n");
      out.write("\t// Basic Block Styles (Font Format Combo).\r\n");
      out.write("\t'p'\t\t\t\t: { Element : 'p' },\r\n");
      out.write("\t'div'\t\t\t: { Element : 'div' },\r\n");
      out.write("\t'pre'\t\t\t: { Element : 'pre' },\r\n");
      out.write("\t'address'\t\t: { Element : 'address' },\r\n");
      out.write("\t'h1'\t\t\t: { Element : 'h1' },\r\n");
      out.write("\t'h2'\t\t\t: { Element : 'h2' },\r\n");
      out.write("\t'h3'\t\t\t: { Element : 'h3' },\r\n");
      out.write("\t'h4'\t\t\t: { Element : 'h4' },\r\n");
      out.write("\t'h5'\t\t\t: { Element : 'h5' },\r\n");
      out.write("\t'h6'\t\t\t: { Element : 'h6' },\r\n");
      out.write("\r\n");
      out.write("\t// Other formatting features.\r\n");
      out.write("\t'FontFace' :\r\n");
      out.write("\t{\r\n");
      out.write("\t\tElement\t\t: 'span',\r\n");
      out.write("\t\tStyles\t\t: { 'font-family' : '#(\"Font\")' },\r\n");
      out.write("\t\tOverrides\t: [ { Element : 'font', Attributes : { 'face' : null } } ]\r\n");
      out.write("\t},\r\n");
      out.write("\r\n");
      out.write("\t'Size' :\r\n");
      out.write("\t{\r\n");
      out.write("\t\tElement\t\t: 'span',\r\n");
      out.write("\t\tStyles\t\t: { 'font-size' : '#(\"Size\",\"fontSize\")' },\r\n");
      out.write("\t\tOverrides\t: [ { Element : 'font', Attributes : { 'size' : null } } ]\r\n");
      out.write("\t},\r\n");
      out.write("\r\n");
      out.write("\t'Color' :\r\n");
      out.write("\t{\r\n");
      out.write("\t\tElement\t\t: 'span',\r\n");
      out.write("\t\tStyles\t\t: { 'color' : '#(\"Color\",\"color\")' },\r\n");
      out.write("\t\tOverrides\t: [ { Element : 'font', Attributes : { 'color' : null } } ]\r\n");
      out.write("\t},\r\n");
      out.write("\r\n");
      out.write("\t'BackColor'\t\t: { Element : 'span', Styles : { 'background-color' : '#(\"Color\",\"color\")' } }\r\n");
      out.write("};\r\n");
      out.write("\r\n");
      out.write("// The distance of an indentation step.\r\n");
      out.write("FCKConfig.IndentLength = 40 ;\r\n");
      out.write("FCKConfig.IndentUnit = 'px' ;\r\n");
      out.write("\r\n");
      out.write("// Alternatively, FCKeditor allows the use of CSS classes for block indentation.\r\n");
      out.write("// This overrides the IndentLength/IndentUnit settings.\r\n");
      out.write("FCKConfig.IndentClasses = [] ;\r\n");
      out.write("\r\n");
      out.write("// [ Left, Center, Right, Justified ]\r\n");
      out.write("FCKConfig.JustifyClasses = [] ;\r\n");
      out.write("\r\n");
      out.write("// The following value defines which File Browser connector and Quick Upload\r\n");
      out.write("// \"uploader\" to use. It is valid for the default implementaion and it is here\r\n");
      out.write("// just to make this configuration file cleaner.\r\n");
      out.write("// It is not possible to change this value using an external file or even\r\n");
      out.write("// inline when creating the editor instance. In that cases you must set the\r\n");
      out.write("// values of LinkBrowserURL, ImageBrowserURL and so on.\r\n");
      out.write("// Custom implementations should just ignore it.\r\n");
      out.write("var _FileBrowserLanguage\t= 'php' ;\t// asp | aspx | cfm | lasso | perl | php | py\r\n");
      out.write("var _QuickUploadLanguage\t= 'php' ;\t// asp | aspx | cfm | lasso | perl | php | py\r\n");
      out.write("\r\n");
      out.write("// Don't care about the following two lines. It just calculates the correct connector\r\n");
      out.write("// extension to use for the default File Browser (Perl uses \"cgi\").\r\n");
      out.write("var _FileBrowserExtension = _FileBrowserLanguage == 'perl' ? 'cgi' : _FileBrowserLanguage ;\r\n");
      out.write("var _QuickUploadExtension = _QuickUploadLanguage == 'perl' ? 'cgi' : _QuickUploadLanguage ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.LinkBrowser = true ;\r\n");
      out.write("FCKConfig.LinkBrowserURL = '");
      if (_jspx_meth_html_005frewrite_005f2(_jspx_page_context))
        return;
      out.write("?templateID='+oTemplate.ID;\r\n");
      out.write("FCKConfig.LinkBrowserWindowWidth\t=  780  ;\t \r\n");
      out.write("FCKConfig.LinkBrowserWindowHeight\t=  600  ;\t\r\n");
      out.write("\r\n");
      out.write("FCKConfig.ImageBrowser = true ;\r\n");
      out.write("FCKConfig.ImageBrowserURL = '");
      if (_jspx_meth_html_005frewrite_005f3(_jspx_page_context))
        return;
      out.write("?templateID='+oTemplate.ID;\r\n");
      out.write("FCKConfig.ImageBrowserWindowWidth  =  780  ;\t\r\n");
      out.write("FCKConfig.ImageBrowserWindowHeight =  600   ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.FlashBrowser = true ;\r\n");
      out.write("FCKConfig.FlashBrowserURL = '");
      if (_jspx_meth_html_005frewrite_005f4(_jspx_page_context))
        return;
      out.write("?templateID='+oTemplate.ID;\r\n");
      out.write("FCKConfig.FlashBrowserWindowWidth  =  780  ;\t\r\n");
      out.write("FCKConfig.FlashBrowserWindowHeight =  600  ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.LinkUpload = false ;\r\n");
      out.write("FCKConfig.LinkBrowserURL = '");
      if (_jspx_meth_html_005frewrite_005f5(_jspx_page_context))
        return;
      out.write("?templateID='+oTemplate.ID;\r\n");
      out.write("FCKConfig.LinkUploadAllowedExtensions\t= \".(7z|aiff|asf|avi|bmp|csv|doc|fla|flv|gif|gz|gzip|jpeg|jpg|mid|mov|mp3|mp4|mpc|mpeg|mpg|ods|odt|pdf|png|ppt|pxd|qt|ram|rar|rm|rmi|rmvb|rtf|sdc|sitd|swf|sxc|sxw|tar|tgz|tif|tiff|txt|vsd|wav|wma|wmv|xls|xml|zip)$\" ;\t\t\t// empty for all\r\n");
      out.write("FCKConfig.LinkUploadDeniedExtensions\t= \"\" ;\t// empty for no one\r\n");
      out.write("\r\n");
      out.write("FCKConfig.ImageUpload = false ;\r\n");
      out.write("FCKConfig.ImageUploadURL = FCKConfig.BasePath + 'filemanager/connectors/' + _QuickUploadLanguage + '/upload.' + _QuickUploadExtension + '?Type=Image' ;\r\n");
      out.write("FCKConfig.ImageUploadAllowedExtensions\t= \".(jpg|gif|jpeg|png|bmp)$\" ;\t\t// empty for all\r\n");
      out.write("FCKConfig.ImageUploadDeniedExtensions\t= \"\" ;\t\t\t\t\t\t\t// empty for no one\r\n");
      out.write("\r\n");
      out.write("FCKConfig.FlashUpload = false ;\r\n");
      out.write("FCKConfig.FlashUploadURL = FCKConfig.BasePath + 'filemanager/connectors/' + _QuickUploadLanguage + '/upload.' + _QuickUploadExtension + '?Type=Flash' ;\r\n");
      out.write("FCKConfig.FlashUploadAllowedExtensions\t= \".(swf|flv)$\" ;\t\t// empty for all\r\n");
      out.write("FCKConfig.FlashUploadDeniedExtensions\t= \"\" ;\t\t\t\t\t// empty for no one\r\n");
      out.write("\r\n");
      out.write("FCKConfig.SmileyPath\t= FCKConfig.BasePath + 'images/smiley/msn/' ;\r\n");
      out.write("FCKConfig.SmileyImages\t= ['regular_smile.gif','sad_smile.gif','wink_smile.gif','teeth_smile.gif','confused_smile.gif','tounge_smile.gif','embaressed_smile.gif','omg_smile.gif','whatchutalkingabout_smile.gif','angry_smile.gif','angel_smile.gif','shades_smile.gif','devil_smile.gif','cry_smile.gif','lightbulb.gif','thumbs_down.gif','thumbs_up.gif','heart.gif','broken_heart.gif','kiss.gif','envelope.gif'] ;\r\n");
      out.write("FCKConfig.SmileyColumns = 8 ;\r\n");
      out.write("FCKConfig.SmileyWindowWidth\t\t= 320 ;\r\n");
      out.write("FCKConfig.SmileyWindowHeight\t= 240 ;\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("// fck_media\r\n");
      out.write("\r\n");
      out.write("FCKConfig.MediaBrowser = true ;\r\n");
      out.write("FCKConfig.MediaBrowserURL = '");
      if (_jspx_meth_html_005frewrite_005f6(_jspx_page_context))
        return;
      out.write("?templateID='+oTemplate.ID;\r\n");
      out.write("FCKConfig.MediaBrowserWindowWidth  = FCKConfig.ScreenWidth * 0.7 ;\t\t// 70% ;\r\n");
      out.write("FCKConfig.MediaBrowserWindowHeight = FCKConfig.ScreenHeight * 0.7 ;\t// 70% ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig.MediaUpload = false ;\r\n");
      out.write("FCKConfig.MediaUploadURL = FCKConfig.BasePath + 'filemanager/connectors/' + _QuickUploadLanguage + '/upload.' + _QuickUploadExtension + '?Type=Flash' ;\r\n");
      out.write("FCKConfig.MediaUploadAllowedExtensions\t= \".(asf|avi|fla|flv|mid|mov|mp3|mp4|mpc|mpeg|mpg|ram|rm|rmi|rmvb|swf|wav|wma|wmv)$\" ;\t\t// empty for all\r\n");
      out.write("FCKConfig.MediaUploadDeniedExtensions\t= \"\" ;\t\t\t\t\t// empty for no one\r\n");
      out.write("\r\n");
      out.write("// Media Embeds.\r\n");
      out.write("var FCKMediaProcessor = FCKDocumentProcessor.AppendNew() ;\r\n");
      out.write("FCKMediaProcessor.ProcessDocument = function( document )\r\n");
      out.write("{\r\n");
      out.write("\t/*\r\n");
      out.write("\tSample code:\r\n");
      out.write("\tThis is some <embed src=\"/UserFiles/Media/Yellow_Runners.avi\"></embed><strong>sample text</strong>. You are&nbsp;<a name=\"fred\"></a> using <a href=\"http://www.fckeditor.net/\">FCKeditor</a>.\r\n");
      out.write("\t*/\r\n");
      out.write("\r\n");
      out.write("\tvar bIsDirty = FCK.IsDirty() ;\r\n");
      out.write("\r\n");
      out.write("\tvar aEmbeds = document.getElementsByTagName( 'EMBED' ) ;\r\n");
      out.write("\r\n");
      out.write("\tvar oEmbed ;\r\n");
      out.write("\tvar i = aEmbeds.length - 1 ;\r\n");
      out.write("\twhile ( i >= 0 && ( oEmbed = aEmbeds[i--] ) )\r\n");
      out.write("\t{\r\n");
      out.write("\t\t// IE doesn't return the type attribute with oEmbed.type or oEmbed.getAttribute(\"type\") \r\n");
      out.write("\t\t// But it turns out that after accessing it then it doesn't gets copied later\r\n");
      out.write("\t\tvar oType = oEmbed.attributes[ 'type' ] ;\r\n");
      out.write("\r\n");
      out.write("\t\t// Check the extension and the type. Now it should be enough with just the type\r\n");
      out.write("\t\t// Opera doesn't return oEmbed.src so oEmbed.src.EndsWith will fail\r\n");
      out.write("\t\tif ( oType && oType.nodeValue == 'application/x-mplayer2' )\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tvar oCloned = oEmbed.cloneNode( true ) ;\r\n");
      out.write("\r\n");
      out.write("\t\t\tvar oImg = FCKDocumentProcessor_CreateFakeImage( 'FCK__Media', oCloned ) ;\r\n");
      out.write("\t\t\toImg.setAttribute( '_fckmedia', 'true', 0 ) ;\r\n");
      out.write("\r\n");
      out.write("\t\t\tFCKMediaProcessor.RefreshView( oImg, oEmbed ) ;\r\n");
      out.write("\r\n");
      out.write("\t\t\toEmbed.parentNode.insertBefore( oImg, oEmbed ) ;\r\n");
      out.write("\t\t\toEmbed.parentNode.removeChild( oEmbed ) ;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t// Fix the IsDirty state (#1406).\r\n");
      out.write("\tif ( !bIsDirty )\r\n");
      out.write("\t\tFCK.ResetIsDirty() ;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("FCKMediaProcessor.RefreshView = function( placeHolderImage, originalEmbed )\r\n");
      out.write("{\r\n");
      out.write("\tif ( originalEmbed.getAttribute( 'width' ) > 0 )\r\n");
      out.write("\t\tplaceHolderImage.style.width = FCKTools.ConvertHtmlSizeToStyle( originalEmbed.getAttribute( 'width' ) ) ;\r\n");
      out.write("\r\n");
      out.write("\tif ( originalEmbed.getAttribute( 'height' ) > 0 )\r\n");
      out.write("\t\tplaceHolderImage.style.height = FCKTools.ConvertHtmlSizeToStyle( originalEmbed.getAttribute( 'height' ) ) ;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function FCK_ContextMenu_GetListener( listenerName )\r\n");
      out.write("{\r\n");
      out.write("\tswitch ( listenerName )\r\n");
      out.write("\t{\r\n");
      out.write("\t\tcase 'Generic' :\r\n");
      out.write("\t\t\treturn {\r\n");
      out.write("\t\t\tAddItems : function( menu, tag, tagName )\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tmenu.AddItem( 'Cut'\t\t, FCKLang.Cut\t, 7, FCKCommands.GetCommand( 'Cut' ).GetState() == FCK_TRISTATE_DISABLED ) ;\r\n");
      out.write("\t\t\t\tmenu.AddItem( 'Copy'\t, FCKLang.Copy\t, 8, FCKCommands.GetCommand( 'Copy' ).GetState() == FCK_TRISTATE_DISABLED ) ;\r\n");
      out.write("\t\t\t\tmenu.AddItem( 'Paste'\t, FCKLang.Paste\t, 9, FCKCommands.GetCommand( 'Paste' ).GetState() == FCK_TRISTATE_DISABLED ) ;\r\n");
      out.write("\t\t\t}} ;\r\n");
      out.write("\r\n");
      out.write("\t\tcase 'Table' :\r\n");
      out.write("\t\t\treturn {\r\n");
      out.write("\t\t\tAddItems : function( menu, tag, tagName )\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tvar bIsTable\t= ( tagName == 'TABLE' ) ;\r\n");
      out.write("\t\t\t\tvar bIsCell\t\t= ( !bIsTable && FCKSelection.HasAncestorNode( 'TABLE' ) ) ;\r\n");
      out.write("\r\n");
      out.write("\t\t\t\tif ( bIsCell )\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tmenu.AddSeparator() ;\r\n");
      out.write("\t\t\t\t\tvar oItem = menu.AddItem( 'Cell'\t, FCKLang.CellCM ) ;\r\n");
      out.write("\t\t\t\t\toItem.AddItem( 'TableInsertCellBefore'\t, FCKLang.InsertCellBefore, 69 ) ;\r\n");
      out.write("\t\t\t\t\toItem.AddItem( 'TableInsertCellAfter'\t, FCKLang.InsertCellAfter, 58 ) ;\r\n");
      out.write("\t\t\t\t\toItem.AddItem( 'TableDeleteCells'\t, FCKLang.DeleteCells, 59 ) ;\r\n");
      out.write("\t\t\t\t\tif ( FCKBrowserInfo.IsGecko )\r\n");
      out.write("\t\t\t\t\t\toItem.AddItem( 'TableMergeCells'\t, FCKLang.MergeCells, 60,\r\n");
      out.write("\t\t\t\t\t\t\tFCKCommands.GetCommand( 'TableMergeCells' ).GetState() == FCK_TRISTATE_DISABLED ) ;\r\n");
      out.write("\t\t\t\t\telse\r\n");
      out.write("\t\t\t\t\t{\r\n");
      out.write("\t\t\t\t\t\toItem.AddItem( 'TableMergeRight'\t, FCKLang.MergeRight, 60,\r\n");
      out.write("\t\t\t\t\t\t\tFCKCommands.GetCommand( 'TableMergeRight' ).GetState() == FCK_TRISTATE_DISABLED ) ;\r\n");
      out.write("\t\t\t\t\t\toItem.AddItem( 'TableMergeDown'\t\t, FCKLang.MergeDown, 60,\r\n");
      out.write("\t\t\t\t\t\t\tFCKCommands.GetCommand( 'TableMergeDown' ).GetState() == FCK_TRISTATE_DISABLED ) ;\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\toItem.AddItem( 'TableHorizontalSplitCell'\t, FCKLang.HorizontalSplitCell, 61,\r\n");
      out.write("\t\t\t\t\t\tFCKCommands.GetCommand( 'TableHorizontalSplitCell' ).GetState() == FCK_TRISTATE_DISABLED ) ;\r\n");
      out.write("\t\t\t\t\toItem.AddItem( 'TableVerticalSplitCell'\t, FCKLang.VerticalSplitCell, 61,\r\n");
      out.write("\t\t\t\t\t\tFCKCommands.GetCommand( 'TableVerticalSplitCell' ).GetState() == FCK_TRISTATE_DISABLED ) ;\r\n");
      out.write("\t\t\t\t\toItem.AddSeparator() ;\r\n");
      out.write("\t\t\t\t\toItem.AddItem( 'TableCellProp'\t\t, FCKLang.CellProperties, 57,\r\n");
      out.write("\t\t\t\t\t\tFCKCommands.GetCommand( 'TableCellProp' ).GetState() == FCK_TRISTATE_DISABLED ) ;\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\tmenu.AddSeparator() ;\r\n");
      out.write("\t\t\t\t\toItem = menu.AddItem( 'Row'\t\t\t, FCKLang.RowCM ) ;\r\n");
      out.write("\t\t\t\t\toItem.AddItem( 'TableInsertRowBefore'\t\t, FCKLang.InsertRowBefore, 70 ) ;\r\n");
      out.write("\t\t\t\t\toItem.AddItem( 'TableInsertRowAfter'\t\t, FCKLang.InsertRowAfter, 62 ) ;\r\n");
      out.write("\t\t\t\t\toItem.AddItem( 'TableDeleteRows'\t, FCKLang.DeleteRows, 63 ) ;\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\tmenu.AddSeparator() ;\r\n");
      out.write("\t\t\t\t\toItem = menu.AddItem( 'Column'\t\t, FCKLang.ColumnCM ) ;\r\n");
      out.write("\t\t\t\t\toItem.AddItem( 'TableInsertColumnBefore', FCKLang.InsertColumnBefore, 71 ) ;\r\n");
      out.write("\t\t\t\t\toItem.AddItem( 'TableInsertColumnAfter'\t, FCKLang.InsertColumnAfter, 64 ) ;\r\n");
      out.write("\t\t\t\t\toItem.AddItem( 'TableDeleteColumns'\t, FCKLang.DeleteColumns, 65 ) ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\t\t\tif ( bIsTable || bIsCell )\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tmenu.AddSeparator() ;\r\n");
      out.write("\t\t\t\t\tmenu.AddItem( 'TableDelete'\t\t\t, FCKLang.TableDelete ) ;\r\n");
      out.write("\t\t\t\t\tmenu.AddItem( 'TableProp'\t\t\t, FCKLang.TableProperties, 39 ) ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}} ;\r\n");
      out.write("\r\n");
      out.write("\t\tcase 'Link' :\r\n");
      out.write("\t\t\treturn {\r\n");
      out.write("\t\t\tAddItems : function( menu, tag, tagName )\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tvar bInsideLink = ( tagName == 'A' || FCKSelection.HasAncestorNode( 'A' ) ) ;\r\n");
      out.write("\r\n");
      out.write("\t\t\t\tif ( bInsideLink || FCK.GetNamedCommandState( 'Unlink' ) != FCK_TRISTATE_DISABLED )\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\t// Go up to the anchor to test its properties\r\n");
      out.write("\t\t\t\t\tvar oLink = FCKSelection.MoveToAncestorNode( 'A' ) ;\r\n");
      out.write("\t\t\t\t\tvar bIsAnchor = ( oLink && oLink.name.length > 0 && oLink.href.length == 0 ) ;\r\n");
      out.write("\t\t\t\t\t// If it isn't a link then don't add the Link context menu\r\n");
      out.write("\t\t\t\t\tif ( bIsAnchor )\r\n");
      out.write("\t\t\t\t\t\treturn ;\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\tmenu.AddSeparator() ;\r\n");
      out.write("\t\t\t\t\tif ( bInsideLink )\r\n");
      out.write("\t\t\t\t\t\tmenu.AddItem( 'Link', FCKLang.EditLink\t\t, 34 ) ;\r\n");
      out.write("\t\t\t\t\tmenu.AddItem( 'Unlink'\t, FCKLang.RemoveLink\t, 35 ) ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}} ;\r\n");
      out.write("\r\n");
      out.write("\t\tcase 'Image' :\r\n");
      out.write("\t\t\treturn {\r\n");
      out.write("\t\t\tAddItems : function( menu, tag, tagName )\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif ( tagName == 'IMG' && !tag.getAttribute( '_fckfakelement' ) )\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tmenu.AddSeparator() ;\r\n");
      out.write("\t\t\t\t\tmenu.AddItem( 'Image', FCKLang.ImageProperties, 37 ) ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}} ;\r\n");
      out.write("\r\n");
      out.write("\t\tcase 'Anchor' :\r\n");
      out.write("\t\t\treturn {\r\n");
      out.write("\t\t\tAddItems : function( menu, tag, tagName )\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\t// Go up to the anchor to test its properties\r\n");
      out.write("\t\t\t\tvar oLink = FCKSelection.MoveToAncestorNode( 'A' ) ;\r\n");
      out.write("\t\t\t\tvar bIsAnchor = ( oLink && oLink.name.length > 0 ) ;\r\n");
      out.write("\r\n");
      out.write("\t\t\t\tif ( bIsAnchor || ( tagName == 'IMG' && tag.getAttribute( '_fckanchor' ) ) )\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tmenu.AddSeparator() ;\r\n");
      out.write("\t\t\t\t\tmenu.AddItem( 'Anchor', FCKLang.AnchorProp, 36 ) ;\r\n");
      out.write("\t\t\t\t\tmenu.AddItem( 'AnchorDelete', FCKLang.AnchorDelete ) ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}} ;\r\n");
      out.write("\r\n");
      out.write("\t\tcase 'Flash' :\r\n");
      out.write("\t\t\treturn {\r\n");
      out.write("\t\t\tAddItems : function( menu, tag, tagName )\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif ( tagName == 'IMG' && tag.getAttribute( '_fckflash' ) )\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tmenu.AddSeparator() ;\r\n");
      out.write("\t\t\t\t\tmenu.AddItem( 'Flash', FCKLang.FlashProperties, 38 ) ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}} ;\r\n");
      out.write("\r\n");
      out.write("\t\tcase 'Form' :\r\n");
      out.write("\t\t\treturn {\r\n");
      out.write("\t\t\tAddItems : function( menu, tag, tagName )\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif ( FCKSelection.HasAncestorNode('FORM') )\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tmenu.AddSeparator() ;\r\n");
      out.write("\t\t\t\t\tmenu.AddItem( 'Form', FCKLang.FormProp, 48 ) ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}} ;\r\n");
      out.write("\r\n");
      out.write("\t\tcase 'Checkbox' :\r\n");
      out.write("\t\t\treturn {\r\n");
      out.write("\t\t\tAddItems : function( menu, tag, tagName )\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif ( tagName == 'INPUT' && tag.type == 'checkbox' )\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tmenu.AddSeparator() ;\r\n");
      out.write("\t\t\t\t\tmenu.AddItem( 'Checkbox', FCKLang.CheckboxProp, 49 ) ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}} ;\r\n");
      out.write("\r\n");
      out.write("\t\tcase 'Radio' :\r\n");
      out.write("\t\t\treturn {\r\n");
      out.write("\t\t\tAddItems : function( menu, tag, tagName )\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif ( tagName == 'INPUT' && tag.type == 'radio' )\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tmenu.AddSeparator() ;\r\n");
      out.write("\t\t\t\t\tmenu.AddItem( 'Radio', FCKLang.RadioButtonProp, 50 ) ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}} ;\r\n");
      out.write("\r\n");
      out.write("\t\tcase 'TextField' :\r\n");
      out.write("\t\t\treturn {\r\n");
      out.write("\t\t\tAddItems : function( menu, tag, tagName )\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif ( tagName == 'INPUT' && ( tag.type == 'text' || tag.type == 'password' ) )\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tmenu.AddSeparator() ;\r\n");
      out.write("\t\t\t\t\tmenu.AddItem( 'TextField', FCKLang.TextFieldProp, 51 ) ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}} ;\r\n");
      out.write("\r\n");
      out.write("\t\tcase 'HiddenField' :\r\n");
      out.write("\t\t\treturn {\r\n");
      out.write("\t\t\tAddItems : function( menu, tag, tagName )\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif ( tagName == 'IMG' && tag.getAttribute( '_fckinputhidden' ) )\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tmenu.AddSeparator() ;\r\n");
      out.write("\t\t\t\t\tmenu.AddItem( 'HiddenField', FCKLang.HiddenFieldProp, 56 ) ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}} ;\r\n");
      out.write("\r\n");
      out.write("\t\tcase 'ImageButton' :\r\n");
      out.write("\t\t\treturn {\r\n");
      out.write("\t\t\tAddItems : function( menu, tag, tagName )\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif ( tagName == 'INPUT' && tag.type == 'image' )\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tmenu.AddSeparator() ;\r\n");
      out.write("\t\t\t\t\tmenu.AddItem( 'ImageButton', FCKLang.ImageButtonProp, 55 ) ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}} ;\r\n");
      out.write("\r\n");
      out.write("\t\tcase 'Button' :\r\n");
      out.write("\t\t\treturn {\r\n");
      out.write("\t\t\tAddItems : function( menu, tag, tagName )\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif ( tagName == 'INPUT' && ( tag.type == 'button' || tag.type == 'submit' || tag.type == 'reset' ) )\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tmenu.AddSeparator() ;\r\n");
      out.write("\t\t\t\t\tmenu.AddItem( 'Button', FCKLang.ButtonProp, 54 ) ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}} ;\r\n");
      out.write("\r\n");
      out.write("\t\tcase 'Select' :\r\n");
      out.write("\t\t\treturn {\r\n");
      out.write("\t\t\tAddItems : function( menu, tag, tagName )\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif ( tagName == 'SELECT' )\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tmenu.AddSeparator() ;\r\n");
      out.write("\t\t\t\t\tmenu.AddItem( 'Select', FCKLang.SelectionFieldProp, 53 ) ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}} ;\r\n");
      out.write("\r\n");
      out.write("\t\tcase 'Textarea' :\r\n");
      out.write("\t\t\treturn {\r\n");
      out.write("\t\t\tAddItems : function( menu, tag, tagName )\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif ( tagName == 'TEXTAREA' )\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tmenu.AddSeparator() ;\r\n");
      out.write("\t\t\t\t\tmenu.AddItem( 'Textarea', FCKLang.TextareaProp, 52 ) ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}} ;\r\n");
      out.write("\r\n");
      out.write("\t\tcase 'BulletedList' :\r\n");
      out.write("\t\t\treturn {\r\n");
      out.write("\t\t\tAddItems : function( menu, tag, tagName )\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif ( FCKSelection.HasAncestorNode('UL') )\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tmenu.AddSeparator() ;\r\n");
      out.write("\t\t\t\t\tmenu.AddItem( 'BulletedList', FCKLang.BulletedListProp, 27 ) ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}} ;\r\n");
      out.write("\r\n");
      out.write("\t\tcase 'NumberedList' :\r\n");
      out.write("\t\t\treturn {\r\n");
      out.write("\t\t\tAddItems : function( menu, tag, tagName )\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif ( FCKSelection.HasAncestorNode('OL') )\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tmenu.AddSeparator() ;\r\n");
      out.write("\t\t\t\t\tmenu.AddItem( 'NumberedList', FCKLang.NumberedListProp, 26 ) ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}} ;\r\n");
      out.write("\t\t// 自定义\r\n");
      out.write("\t\tcase 'Media' :\r\n");
      out.write("\t\t\treturn {\r\n");
      out.write("\t\t\tAddItems : function( menu, tag, tagName )\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif ( tagName == 'IMG' && tag.getAttribute( '_fckmedia' ) )\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tmenu.AddSeparator() ;\r\n");
      out.write("\t\t\t\t\tmenu.AddItem( 'Media', '多媒体属性', 'plugins/fck_media/media.gif' ) ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}} ;\r\n");
      out.write("\t\t// 自定义\r\n");
      out.write("\t\tcase 'EditPortlet' :\r\n");
      out.write("\t\t\treturn {\r\n");
      out.write("\t\t\tAddItems : function( menu, tag, tagName )\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif ( tagName == 'DIV' && tag.getAttribute( 'className' ) == 'portletWindow' )\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tmenu.AddSeparator() ;\r\n");
      out.write("\t\t\t\t\tmenu.AddItem( 'EditPortlet', '占位符属性' ) ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}} ;\r\n");
      out.write("\t}\r\n");
      out.write("\treturn null ;\r\n");
      out.write("}\r\n");
      out.write("//修复删除table删除出错的bug\r\n");
      out.write("//覆盖DeleteRows实现\r\n");
      out.write("FCKTableHandler.DeleteTable = function( table ){\r\n");
      out.write("\t// If no table has been passed as a parameter,\r\n");
      out.write("\t// then get the table where the selection is placed in.\r\n");
      out.write("\tif ( !table )\r\n");
      out.write("\t{\r\n");
      out.write("\t\ttable = FCKSelection.GetSelectedElement() ;\r\n");
      out.write("\t\tif ( !table || table.tagName != 'TABLE' )\r\n");
      out.write("\t\t\ttable = FCKSelection.MoveToAncestorNode( 'TABLE' ) ;\r\n");
      out.write("\t}\r\n");
      out.write("\tif ( !table ) return ;\r\n");
      out.write("\r\n");
      out.write("\t// Delete the table.\r\n");
      out.write("\tFCKSelection.SelectNode( table ) ;\r\n");
      out.write("\tFCKSelection.Collapse();\r\n");
      out.write("\r\n");
      out.write("\t// if the table is wrapped with a singleton <p> ( or something similar ), remove\r\n");
      out.write("\t// the surrounding tag -- which likely won't show after deletion anyway\r\n");
      out.write("\tif ( table.parentNode.childNodes.length == 1 &&table.parentNode.nodeName==\"p\")\r\n");
      out.write("\t\ttable.parentNode.parentNode.removeChild( table.parentNode );\r\n");
      out.write("\telse\r\n");
      out.write("\t\ttable.parentNode.removeChild( table  ) ;\r\n");
      out.write("}");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      if (_jspxFactory != null) _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_html_005frewrite_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f0 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f0.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f0.setParent(null);
    _jspx_th_html_005frewrite_005f0.setPage("/common/editor/");
    _jspx_th_html_005frewrite_005f0.setModule("");
    int _jspx_eval_html_005frewrite_005f0 = _jspx_th_html_005frewrite_005f0.doStartTag();
    if (_jspx_th_html_005frewrite_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f0);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f1 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f1.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f1.setParent(null);
    _jspx_th_html_005frewrite_005f1.setPage("/site/editor/");
    _jspx_th_html_005frewrite_005f1.setModule("");
    int _jspx_eval_html_005frewrite_005f1 = _jspx_th_html_005frewrite_005f1.doStartTag();
    if (_jspx_th_html_005frewrite_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f1);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f2 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f2.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f2.setParent(null);
    _jspx_th_html_005frewrite_005f2.setModule("/site/resource");
    _jspx_th_html_005frewrite_005f2.setPage("/index.do");
    int _jspx_eval_html_005frewrite_005f2 = _jspx_th_html_005frewrite_005f2.doStartTag();
    if (_jspx_th_html_005frewrite_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f2);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f3 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f3.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f3.setParent(null);
    _jspx_th_html_005frewrite_005f3.setModule("/site/resource");
    _jspx_th_html_005frewrite_005f3.setPage("/index.do");
    int _jspx_eval_html_005frewrite_005f3 = _jspx_th_html_005frewrite_005f3.doStartTag();
    if (_jspx_th_html_005frewrite_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f3);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f4 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f4.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f4.setParent(null);
    _jspx_th_html_005frewrite_005f4.setModule("/site/resource");
    _jspx_th_html_005frewrite_005f4.setPage("/index.do");
    int _jspx_eval_html_005frewrite_005f4 = _jspx_th_html_005frewrite_005f4.doStartTag();
    if (_jspx_th_html_005frewrite_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f4);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f5(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f5 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f5.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f5.setParent(null);
    _jspx_th_html_005frewrite_005f5.setModule("/site/resource");
    _jspx_th_html_005frewrite_005f5.setPage("/index.do");
    int _jspx_eval_html_005frewrite_005f5 = _jspx_th_html_005frewrite_005f5.doStartTag();
    if (_jspx_th_html_005frewrite_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f5);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f5);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f6(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f6 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f6.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f6.setParent(null);
    _jspx_th_html_005frewrite_005f6.setModule("/site/resource");
    _jspx_th_html_005frewrite_005f6.setPage("/index.do");
    int _jspx_eval_html_005frewrite_005f6 = _jspx_th_html_005frewrite_005f6.doStartTag();
    if (_jspx_th_html_005frewrite_005f6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f6);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f6);
    return false;
  }
}
