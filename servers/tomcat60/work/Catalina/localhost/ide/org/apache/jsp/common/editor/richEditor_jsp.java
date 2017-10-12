package org.apache.jsp.common.editor;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class richEditor_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\r\n");

/* <p>完整的多文本编辑器，可以处理文件操作的</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author 李雄锋
 * @version 1.0
 **/

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("\t<title>FCKeditor</title>\r\n");
      out.write("\t<meta name=\"robots\" content=\"noindex, nofollow\" />\r\n");
      out.write("\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n");
      out.write("\t<meta http-equiv=\"Cache-Control\" content=\"public\" />\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("// Instead of loading scripts and CSSs using inline tags, all scripts are\r\n");
      out.write("// loaded by code. In this way we can guarantee the correct processing order,\r\n");
      out.write("// otherwise external scripts and inline scripts could be executed in an\r\n");
      out.write("// unwanted order (IE).\r\n");
      out.write("\r\n");
      out.write("function LoadScript( url )\r\n");
      out.write("{\r\n");
      out.write("\tdocument.write( '<script type=\"text/javascript\" src=\"' + url + '\"><\\/script>' ) ;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function LoadCss( url )\r\n");
      out.write("{\r\n");
      out.write("\tdocument.write( '<link href=\"' + url + '\" type=\"text/css\" rel=\"stylesheet\" />' ) ;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("// Main editor scripts.\r\n");
      out.write("var sSuffix = ( /*@cc_on!@*/false ) ? 'ie' : 'gecko' ;\r\n");
      out.write("\r\n");
      out.write("/*\r\n");
      out.write(" *\r\n");
      out.write(" *\r\n");
      out.write("LoadScript( 'js/fckeditorcode_' + sSuffix + '.js' ) ;\r\n");
      out.write(" *\r\n");
      out.write(" *\r\n");
      out.write(" */\r\n");
      out.write("\r\n");
      out.write("LoadScript( '_source/fckconstants.js' ) ;\r\n");
      out.write("LoadScript( '_source/fckjscoreextensions.js' ) ;\r\n");
      out.write("\r\n");
      out.write("if ( sSuffix == 'ie' )\r\n");
      out.write("\tLoadScript( '_source/classes/fckiecleanup.js' ) ;\r\n");
      out.write("\r\n");
      out.write("LoadScript( '_source/internals/fckbrowserinfo.js' ) ;\r\n");
      out.write("LoadScript( '_source/internals/fckurlparams.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fckevents.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fckdataprocessor.js' ) ;\r\n");
      out.write("LoadScript( '_source/internals/fck.js' ) ;\r\n");
      out.write("LoadScript( '_source/internals/fck_' + sSuffix + '.js' ) ;\r\n");
      out.write("LoadScript( '_source/internals/fckconfig.js' ) ;\r\n");
      out.write("\r\n");
      out.write("LoadScript( '_source/internals/fckdebug.js' ) ;\r\n");
      out.write("LoadScript( '_source/internals/fckdomtools.js' ) ;\r\n");
      out.write("LoadScript( '_source/internals/fcktools.js' ) ;\r\n");
      out.write("LoadScript( '_source/internals/fcktools_' + sSuffix + '.js' ) ;\r\n");
      out.write("LoadScript( '_source/fckeditorapi.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fckimagepreloader.js' ) ;\r\n");
      out.write("LoadScript( '_source/internals/fckregexlib.js' ) ;\r\n");
      out.write("LoadScript( '_source/internals/fcklistslib.js' ) ;\r\n");
      out.write("LoadScript( '_source/internals/fcklanguagemanager.js' ) ;\r\n");
      out.write("LoadScript( '_source/internals/fckxhtmlentities.js' ) ;\r\n");
      out.write("LoadScript( '_source/internals/fckxhtml.js' ) ;\r\n");
      out.write("LoadScript( '_source/internals/fckxhtml_' + sSuffix + '.js' ) ;\r\n");
      out.write("LoadScript( '_source/internals/fckcodeformatter.js' ) ;\r\n");
      out.write("LoadScript( '_source/internals/fckundo.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fckeditingarea.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fckkeystrokehandler.js' ) ;\r\n");
      out.write("\r\n");
      out.write("LoadScript( 'dtd/fck_xhtml10transitional.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fckstyle.js' ) ;\r\n");
      out.write("LoadScript( '_source/internals/fckstyles.js' ) ;\r\n");
      out.write("\r\n");
      out.write("LoadScript( '_source/internals/fcklisthandler.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fckelementpath.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fckdomrange.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fckdocumentfragment_' + sSuffix + '.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fckw3crange.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fckdomrange_' + sSuffix + '.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fckdomrangeiterator.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fckenterkey.js' ) ;\r\n");
      out.write("\r\n");
      out.write("LoadScript( '_source/internals/fckdocumentprocessor.js' ) ;\r\n");
      out.write("LoadScript( '_source/internals/fckselection.js' ) ;\r\n");
      out.write("LoadScript( '_source/internals/fckselection_' + sSuffix + '.js' ) ;\r\n");
      out.write("\r\n");
      out.write("LoadScript( '_source/internals/fcktablehandler.js' ) ;\r\n");
      out.write("LoadScript( '_source/internals/fcktablehandler_' + sSuffix + '.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fckxml.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fckxml_' + sSuffix + '.js' ) ;\r\n");
      out.write("\r\n");
      out.write("LoadScript( '_source/commandclasses/fcknamedcommand.js' ) ;\r\n");
      out.write("LoadScript( '_source/commandclasses/fckstylecommand.js' ) ;\r\n");
      out.write("LoadScript( '_source/commandclasses/fck_othercommands.js' ) ;\r\n");
      out.write("LoadScript( '_source/commandclasses/fckshowblocks.js' ) ;\r\n");
      out.write("LoadScript( '_source/commandclasses/fckspellcheckcommand_' + sSuffix + '.js' ) ;\r\n");
      out.write("LoadScript( '_source/commandclasses/fcktextcolorcommand.js' ) ;\r\n");
      out.write("LoadScript( '_source/commandclasses/fckpasteplaintextcommand.js' ) ;\r\n");
      out.write("LoadScript( '_source/commandclasses/fckpastewordcommand.js' ) ;\r\n");
      out.write("LoadScript( '_source/commandclasses/fcktablecommand.js' ) ;\r\n");
      out.write("LoadScript( '_source/commandclasses/fckfitwindow.js' ) ;\r\n");
      out.write("LoadScript( '_source/commandclasses/fcklistcommands.js' ) ;\r\n");
      out.write("LoadScript( '_source/commandclasses/fckjustifycommands.js' ) ;\r\n");
      out.write("LoadScript( '_source/commandclasses/fckindentcommands.js' ) ;\r\n");
      out.write("LoadScript( '_source/commandclasses/fckblockquotecommand.js' ) ;\r\n");
      out.write("LoadScript( '_source/commandclasses/fckcorestylecommand.js' ) ;\r\n");
      out.write("LoadScript( '_source/commandclasses/fckremoveformatcommand.js' ) ;\r\n");
      out.write("LoadScript( '_source/internals/fckcommands.js' ) ;\r\n");
      out.write("\r\n");
      out.write("LoadScript( '_source/classes/fckpanel.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fckicon.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fcktoolbarbuttonui.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fcktoolbarbutton.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fckspecialcombo.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fcktoolbarspecialcombo.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fcktoolbarstylecombo.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fcktoolbarfontformatcombo.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fcktoolbarfontscombo.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fcktoolbarfontsizecombo.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fcktoolbarpanelbutton.js' ) ;\r\n");
      out.write("LoadScript( '_source/internals/fcktoolbaritems.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fcktoolbar.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fcktoolbarbreak_' + sSuffix + '.js' ) ;\r\n");
      out.write("LoadScript( '_source/internals/fcktoolbarset.js' ) ;\r\n");
      out.write("LoadScript( '_source/internals/fckdialog.js' ) ;\r\n");
      out.write("LoadScript( '_source/internals/fckdialog_' + sSuffix + '.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fckmenuitem.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fckmenublock.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fckmenublockpanel.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fckcontextmenu.js' ) ;\r\n");
      out.write("LoadScript( '_source/internals/fck_contextmenu.js' ) ;\r\n");
      out.write("LoadScript( '_source/classes/fckplugin.js' ) ;\r\n");
      out.write("LoadScript( '_source/internals/fckplugins.js' ) ;\r\n");
      out.write("\r\n");
      out.write("// @Packager.Remove.End\r\n");
      out.write("\r\n");
      out.write("// Base configuration file.\r\n");
      out.write("LoadScript( '../fckconfig.js' ) ;\r\n");
      out.write("\r\n");
      out.write("//FCKConfig.BasePath=\"");
      if (_jspx_meth_html_005frewrite_005f0(_jspx_page_context))
        return;
      out.write("\";\r\n");
      out.write("//FCKConfig.EditorPath=\"");
      if (_jspx_meth_html_005frewrite_005f1(_jspx_page_context))
        return;
      out.write("\";\r\n");
      out.write("\r\n");
      out.write("// Base configuration file.\r\n");
      out.write("LoadScript( 'richEditor.js.jsp') ;\r\n");
      out.write("\t</script>\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("if ( FCKBrowserInfo.IsIE )\r\n");
      out.write("{\r\n");
      out.write("\t// Remove IE mouse flickering.\r\n");
      out.write("\ttry\r\n");
      out.write("\t{\r\n");
      out.write("\t\tdocument.execCommand( 'BackgroundImageCache', false, true ) ;\r\n");
      out.write("\t}\r\n");
      out.write("\tcatch (e)\r\n");
      out.write("\t{\r\n");
      out.write("\t\t// We have been reported about loading problems caused by the above\r\n");
      out.write("\t\t// line. For safety, let's just ignore errors.\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t// Create the default cleanup object used by the editor.\r\n");
      out.write("\tFCK.IECleanup = new FCKIECleanup( window ) ;\r\n");
      out.write("\tFCK.IECleanup.AddItem( FCKTempBin, FCKTempBin.Reset ) ;\r\n");
      out.write("\tFCK.IECleanup.AddItem( FCK, FCK_Cleanup ) ;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("// The first function to be called on selection change must the the styles\r\n");
      out.write("// change checker, because the result of its processing may be used by another\r\n");
      out.write("// functions listening to the same event.\r\n");
      out.write("FCK.Events.AttachEvent( 'OnSelectionChange', function() { FCKStyles.CheckSelectionChanges() ; } ) ;\r\n");
      out.write("\r\n");
      out.write("// The config hidden field is processed immediately, because\r\n");
      out.write("// CustomConfigurationsPath may be set in the page.\r\n");
      out.write("FCKConfig.ProcessHiddenField() ;\r\n");
      out.write("\r\n");
      out.write("// Load the custom configurations file (if defined).\r\n");
      out.write("if ( FCKConfig.CustomConfigurationsPath.length > 0 )\r\n");
      out.write("\tLoadScript( FCKConfig.CustomConfigurationsPath ) ;\r\n");
      out.write("\r\n");
      out.write("\t</script>\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("// Load configurations defined at page level.\r\n");
      out.write("FCKConfig_LoadPageConfig() ;\r\n");
      out.write("\r\n");
      out.write("FCKConfig_PreProcess() ;\r\n");
      out.write("\r\n");
      out.write("// Load the active skin CSS.\r\n");
      out.write("LoadCss( FCKConfig.SkinPath + 'fck_editor.css' ) ;\r\n");
      out.write("\r\n");
      out.write("// Load the language file.\r\n");
      out.write("FCKLanguageManager.Initialize() ;\r\n");
      out.write("LoadScript( 'lang/' + FCKLanguageManager.ActiveLanguage.Code + '.js' ) ;\r\n");
      out.write("\r\n");
      out.write("\t</script>\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("// Initialize the editing area context menu.\r\n");
      out.write("FCK_ContextMenu_Init() ;\r\n");
      out.write("\r\n");
      out.write("FCKPlugins.Load() ;\r\n");
      out.write("\r\n");
      out.write("\t</script>\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("// Set the editor interface direction.\r\n");
      out.write("window.document.dir = FCKLang.Dir ;\r\n");
      out.write("\r\n");
      out.write("\t</script>\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("window.onload = function()\r\n");
      out.write("{\r\n");
      out.write("\tInitializeAPI() ;\r\n");
      out.write("\r\n");
      out.write("\tif ( FCKBrowserInfo.IsIE )\r\n");
      out.write("\t\tFCK_PreloadImages() ;\r\n");
      out.write("\telse\r\n");
      out.write("\t\tLoadToolbarSetup() ;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function LoadToolbarSetup()\r\n");
      out.write("{\r\n");
      out.write("\tFCKeditorAPI._FunctionQueue.Add( LoadToolbar ) ;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function LoadToolbar()\r\n");
      out.write("{\r\n");
      out.write("\tvar oToolbarSet = FCK.ToolbarSet = FCKToolbarSet_Create() ;\r\n");
      out.write("\r\n");
      out.write("\tif ( oToolbarSet.IsLoaded )\r\n");
      out.write("\t\tStartEditor() ;\r\n");
      out.write("\telse\r\n");
      out.write("\t{\r\n");
      out.write("\t\toToolbarSet.OnLoad = StartEditor ;\r\n");
      out.write("\t\toToolbarSet.Load( FCKURLParams['Toolbar'] || 'Default' ) ;\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function StartEditor()\r\n");
      out.write("{\r\n");
      out.write("\t// Remove the onload listener.\r\n");
      out.write("\tFCK.ToolbarSet.OnLoad = null ;\r\n");
      out.write("\r\n");
      out.write("\tFCKeditorAPI._FunctionQueue.Remove( LoadToolbar ) ;\r\n");
      out.write("\r\n");
      out.write("\tFCK.Events.AttachEvent( 'OnStatusChange', WaitForActive ) ;\r\n");
      out.write("\r\n");
      out.write("\t// Start the editor.\r\n");
      out.write("\tFCK.StartEditor() ;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function WaitForActive( editorInstance, newStatus )\r\n");
      out.write("{\r\n");
      out.write("\tif ( newStatus == FCK_STATUS_ACTIVE )\r\n");
      out.write("\t{\r\n");
      out.write("\t\tif ( FCKBrowserInfo.IsGecko )\r\n");
      out.write("\t\t\tFCKTools.RunFunction( window.onresize ) ;\r\n");
      out.write("\r\n");
      out.write("\t\t_AttachFormSubmitToAPI() ;\r\n");
      out.write("\r\n");
      out.write("\t\tFCK.SetStatus( FCK_STATUS_COMPLETE ) ;\r\n");
      out.write("\r\n");
      out.write("\t\t// Call the special \"FCKeditor_OnComplete\" function that should be present in\r\n");
      out.write("\t\t// the HTML page where the editor is located.\r\n");
      out.write("\t\tif ( typeof( window.parent.FCKeditor_OnComplete ) == 'function' )\r\n");
      out.write("\t\t\twindow.parent.FCKeditor_OnComplete( FCK ) ;\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("// Gecko browsers doens't calculate well that IFRAME size so we must\r\n");
      out.write("// recalculate it every time the window size changes.\r\n");
      out.write("if ( FCKBrowserInfo.IsGecko )\r\n");
      out.write("{\r\n");
      out.write("\tfunction Window_OnResize()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tif ( FCKBrowserInfo.IsOpera )\r\n");
      out.write("\t\t\treturn ;\r\n");
      out.write("\r\n");
      out.write("\t\tvar oCell = document.getElementById( 'xEditingArea' ) ;\r\n");
      out.write("\r\n");
      out.write("\t\tvar eInnerElement = oCell.firstChild ;\r\n");
      out.write("\t\tif ( eInnerElement )\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\teInnerElement.style.height = 0 ;\r\n");
      out.write("\t\t\teInnerElement.style.height = oCell.scrollHeight - 2 ;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\twindow.onresize = Window_OnResize ;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\t</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"height: 100%; table-layout: fixed\">\r\n");
      out.write("\t\t<tr id=\"xToolbarRow\" style=\"display: none\">\r\n");
      out.write("\t\t\t<td id=\"xToolbarSpace\" style=\"overflow: hidden\">\r\n");
      out.write("\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr id=\"xCollapsed\" style=\"display: none\">\r\n");
      out.write("\t\t\t\t\t\t<td id=\"xExpandHandle\" class=\"TB_Expand\" colspan=\"3\">\r\n");
      out.write("\t\t\t\t\t\t\t<img class=\"TB_ExpandImg\" alt=\"\" src=\"images/spacer.gif\" width=\"8\" height=\"4\" /></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr id=\"xExpanded\" style=\"display: none\">\r\n");
      out.write("\t\t\t\t\t\t<td id=\"xTBLeftBorder\" class=\"TB_SideBorder\" style=\"width: 1px; display: none;\"></td>\r\n");
      out.write("\t\t\t\t\t\t<td id=\"xCollapseHandle\" style=\"display: none\" class=\"TB_Collapse\" valign=\"bottom\">\r\n");
      out.write("\t\t\t\t\t\t\t<img class=\"TB_CollapseImg\" alt=\"\" src=\"images/spacer.gif\" width=\"8\" height=\"4\" /></td>\r\n");
      out.write("\t\t\t\t\t\t<td id=\"xToolbar\" class=\"TB_ToolbarSet\"></td>\r\n");
      out.write("\t\t\t\t\t\t<td class=\"TB_SideBorder\" style=\"width: 1px\"></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td id=\"xEditingArea\" valign=\"top\" style=\"height: 100%\"></td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t</table>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
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
    _jspx_th_html_005frewrite_005f0.setPage("/common/fckeditor/editor/");
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
    _jspx_th_html_005frewrite_005f1.setPage("/common/fckeditor/");
    _jspx_th_html_005frewrite_005f1.setModule("");
    int _jspx_eval_html_005frewrite_005f1 = _jspx_th_html_005frewrite_005f1.doStartTag();
    if (_jspx_th_html_005frewrite_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f1);
    return false;
  }
}
