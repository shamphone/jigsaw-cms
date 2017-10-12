package org.apache.jsp.site.editor;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class css_editor_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(5);
    _jspx_dependants.add("/WEB-INF/struts-logic.tld");
    _jspx_dependants.add("/WEB-INF/struts-bean.tld");
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
    _jspx_dependants.add("/WEB-INF/struts-tiles.tld");
    _jspx_dependants.add("/WEB-INF/fulong.tld");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fignore_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fwidth_005fstyleClass_005fpage_005fmodule_005fheight_005falt_005fnobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fignore_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fwidth_005fstyleClass_005fpage_005fmodule_005fheight_005falt_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.release();
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fignore_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fwidth_005fstyleClass_005fpage_005fmodule_005fheight_005falt_005fnobody.release();
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

      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\r\n");

/* <p>模板页面编辑器</p>
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

      out.write("<html>\r\n");
      out.write("    <head>\r\n");
      out.write("        <title>样式编辑器</title>\r\n");
      out.write("        <meta name=\"robots\" content=\"noindex, nofollow\" />\r\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n");
      out.write("        <meta http-equiv=\"Cache-Control\" content=\"public\" />\r\n");
      out.write("        <link rel=\"stylesheet\" href=\"");
      if (_jspx_meth_html_005frewrite_005f0(_jspx_page_context))
        return;
      out.write("\"/>\r\n");
      out.write("        <script Language='Javascript' src='");
      if (_jspx_meth_html_005frewrite_005f1(_jspx_page_context))
        return;
      out.write("' type=\"text/javascript\"></script>\r\n");
      out.write("        <script Language='Javascript' src='");
      if (_jspx_meth_html_005frewrite_005f2(_jspx_page_context))
        return;
      out.write("' type=\"text/javascript\"></script>\r\n");
      out.write("        <script Language='Javascript' src='");
      if (_jspx_meth_html_005frewrite_005f3(_jspx_page_context))
        return;
      out.write("' type=\"text/javascript\"></script>\r\n");
      out.write("        <script type=\"text/javascript\">\r\n");
      out.write("        var portalStyleURL=\"/portal/style.css\";\r\n");
      out.write("        var modulePath = \"");
      if (_jspx_meth_html_005frewrite_005f4(_jspx_page_context))
        return;
      out.write("\";\r\n");
      out.write("        var contextPath=\"");
      if (_jspx_meth_html_005frewrite_005f5(_jspx_page_context))
        return;
      out.write("\";\r\n");
      out.write("        var portalStyleLink=\"<link id=\\\"portlet.style\\\" href=\\\"\"+portalStyleURL+\"\\\" type=\\\"text/css\\\" rel=\\\"stylesheet\\\">\";                \r\n");
      out.write("        </script>\r\n");
      out.write("        <script type=\"text/javascript\">\r\n");
      out.write("        var Editor = top.Editor;\r\n");
      out.write("        var oTemplate = Editor.template;        \r\n");
      out.write("        var oChannel = Editor.channelTree.getSelected().element;\r\n");
      out.write("        function resetTitle(){\r\n");
      out.write("            var oTitle =oChannel.displayName + \"—\" + oTemplate.displayName;\r\n");
      out.write("            if(oChannel.state!='published'){\r\n");
      out.write("               oTitle = \"*\" + oTitle;\r\n");
      out.write("            }\r\n");
      out.write("            window.top.document.title = oTitle;\r\n");
      out.write("        }\r\n");
      out.write("        resetTitle();\r\n");
      out.write("        var diableToolBar = false;\r\n");
      out.write("        /**\r\n");
      out.write("        * 显示错误信息\r\n");
      out.write("        * @param html: HTML内容\r\n");
      out.write("        */\r\n");
      out.write("        function ShowError(error,msg)\r\n");
      out.write("        {\r\n");
      out.write("          var msg=new Object();\r\n");
      out.write("          msg.error=error;\r\n");
      out.write("          msg.errorMsg=msg;\r\n");
      out.write("          window.showModalDialog(\"fckerror.html?&timeStamp=\"+ new Date().getTime(),msg);\r\n");
      out.write("          return null;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        // Instead of loading scripts and CSSs using inline tags, all scripts are\r\n");
      out.write("        // loaded by code. In this way we can guarantee the correct processing order,\r\n");
      out.write("        // otherwise external scripts and inline scripts could be executed in an\r\n");
      out.write("        // unwanted order (IE).\r\n");
      out.write("        function LoadScript( url )\r\n");
      out.write("        {\r\n");
      out.write("            document.write( '<script type=\"text/javascript\" src=\"' + url + '\"><\\/script>' ) ;\r\n");
      out.write("        }\r\n");
      out.write("        function LoadCss( url )\r\n");
      out.write("        {\r\n");
      out.write("            document.write( '<link href=\"' + url + '\" type=\"text/css\" rel=\"stylesheet\" />' ) ;\r\n");
      out.write("        }\r\n");
      out.write("        // Main editor scripts.\r\n");
      out.write("        var sSuffix = ( /*@cc_on!@*/false ) ? 'ie' : 'gecko' ;\r\n");
      out.write("\r\n");
      out.write("        LoadScript(\"");
      if (_jspx_meth_html_005frewrite_005f6(_jspx_page_context))
        return;
      out.write("\"+ 'js/fckeditorcode_' + sSuffix + '.js' ) ;\r\n");
      out.write("\r\n");
      out.write("        //加载配置文件\r\n");
      out.write("        LoadScript( 'css.editor.js') ;\r\n");
      out.write("        //expand end\r\n");
      out.write("        // Base configuration file.\r\n");
      out.write("        </script>\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("  \r\n");
      out.write("\t\r\n");
      out.write("        if ( FCKBrowserInfo.IsIE )\r\n");
      out.write("        {\r\n");
      out.write("            // Remove IE mouse flickering.\r\n");
      out.write("            try\r\n");
      out.write("            {\r\n");
      out.write("                document.execCommand( 'BackgroundImageCache', false, true ) ;\r\n");
      out.write("            }\r\n");
      out.write("            catch (e)\r\n");
      out.write("            {\r\n");
      out.write("                // We have been reported about loading problems caused by the above\r\n");
      out.write("                // line. For safety, let's just ignore errors.\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            // Create the default cleanup object used by the editor.\r\n");
      out.write("            FCK.IECleanup = new FCKIECleanup( window ) ;\r\n");
      out.write("            FCK.IECleanup.AddItem( FCKTempBin, FCKTempBin.Reset ) ;\r\n");
      out.write("            FCK.IECleanup.AddItem( FCK, FCK_Cleanup ) ;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        // The first function to be called on selection change must the the styles\r\n");
      out.write("        // change checker, because the result of its processing may be used by another\r\n");
      out.write("        // functions listening to the same event.\r\n");
      out.write("        FCK.Events.AttachEvent( 'OnSelectionChange', function() { FCKStyles.CheckSelectionChanges() ; } ) ;\r\n");
      out.write("\r\n");
      out.write("        // The config hidden field is processed immediately, because\r\n");
      out.write("        // CustomConfigurationsPath may be set in the page.\r\n");
      out.write("        FCKConfig.ProcessHiddenField() ;\r\n");
      out.write("\r\n");
      out.write("        // Load the custom configurations file (if defined).\r\n");
      out.write("        if ( FCKConfig.CustomConfigurationsPath.length > 0 )\r\n");
      out.write("        LoadScript( FCKConfig.CustomConfigurationsPath ) ;\r\n");
      out.write("\r\n");
      out.write("        </script>\r\n");
      out.write("        <script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("        // Load configurations defined at page level.\r\n");
      out.write("        FCKConfig_LoadPageConfig() ;\r\n");
      out.write("\r\n");
      out.write("        FCKConfig_PreProcess() ;\r\n");
      out.write("\r\n");
      out.write("        // Load the active skin CSS.\r\n");
      out.write("        LoadCss( FCKConfig.SkinPath + 'fck_editor.css' ) ;\r\n");
      out.write("        LoadCss( \"");
      if (_jspx_meth_html_005frewrite_005f7(_jspx_page_context))
        return;
      out.write("\" ) ;\r\n");
      out.write("        // Load the language file.\r\n");
      out.write("        FCKLanguageManager.Initialize() ;\r\n");
      out.write("        LoadScript(\"");
      if (_jspx_meth_html_005frewrite_005f8(_jspx_page_context))
        return;
      out.write("\"+ 'lang/' + FCKLanguageManager.ActiveLanguage.Code + '.js' ) ;\r\n");
      out.write("\r\n");
      out.write("        </script>\r\n");
      out.write("        <script type=\"text/javascript\">\r\n");
      out.write("        // Initialize the editing area context menu.\r\n");
      out.write("        FCK_ContextMenu_Init() ;\r\n");
      out.write("        FCKPlugins.Load() ;\r\n");
      out.write("\t</script>\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("        // Set the editor interface direction.\r\n");
      out.write("        window.document.dir = FCKLang.Dir ;\r\n");
      out.write("\t</script>\r\n");
      out.write("            <script type=\"text/javascript\">\r\n");
      out.write("            //初始化PortletPage对象。\r\n");
      out.write("            window.onload = function()\r\n");
      out.write("            {\r\n");
      out.write("                InitializeAPI() ;\r\n");
      out.write("                if ( FCKBrowserInfo.IsIE )\r\n");
      out.write("                FCK_PreloadImages() ;\r\n");
      out.write("                else\r\n");
      out.write("                LoadToolbarSetup() ;\r\n");
      out.write("\r\n");
      out.write("                oChannel.checkLock();\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            function LoadToolbarSetup()\r\n");
      out.write("            {\r\n");
      out.write("                FCKeditorAPI._FunctionQueue.Add( LoadToolbar ) ;\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            function LoadToolbar()\r\n");
      out.write("            {\r\n");
      out.write("                var oToolbarSet = FCK.ToolbarSet = FCKToolbarSet_Create() ;\r\n");
      out.write("\r\n");
      out.write("                if ( oToolbarSet.IsLoaded )\r\n");
      out.write("                StartEditor() ;\r\n");
      out.write("                else\r\n");
      out.write("                {\r\n");
      out.write("                    oToolbarSet.OnLoad = StartEditor ;\r\n");
      out.write("                    oToolbarSet.Load( FCKURLParams['Toolbar'] || 'Default' ) ;\r\n");
      out.write("                }\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            function StartEditor()\r\n");
      out.write("            {\r\n");
      out.write("                // Remove the onload listener.\r\n");
      out.write("                FCK.ToolbarSet.OnLoad = null ;\r\n");
      out.write("\r\n");
      out.write("                FCKeditorAPI._FunctionQueue.Remove( LoadToolbar ) ;\r\n");
      out.write("\r\n");
      out.write("                FCK.Events.AttachEvent( 'OnStatusChange', WaitForActive ) ;\r\n");
      out.write("\r\n");
      out.write("                // Start the editor.\r\n");
      out.write("                FCK.StartEditor();               \r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            function WaitForActive( editorInstance, newStatus )\r\n");
      out.write("            {\r\n");
      out.write("                if ( newStatus == FCK_STATUS_ACTIVE )\r\n");
      out.write("                {\r\n");
      out.write("                    if ( FCKBrowserInfo.IsGecko )\r\n");
      out.write("                    FCKTools.RunFunction( window.onresize ) ;\r\n");
      out.write("\r\n");
      out.write("                    _AttachFormSubmitToAPI() ;\r\n");
      out.write("\r\n");
      out.write("                    FCK.SetStatus( FCK_STATUS_COMPLETE ) ;\r\n");
      out.write("\r\n");
      out.write("                    // Call the special \"FCKeditor_OnComplete\" function that should be present in\r\n");
      out.write("                    // the HTML page where the editor is located.\r\n");
      out.write("                    if ( typeof( window.parent.FCKeditor_OnComplete ) == 'function' )\r\n");
      out.write("                    window.parent.FCKeditor_OnComplete( FCK ) ;                    \r\n");
      out.write("                }\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            // Gecko browsers doens't calculate well that IFRAME size so we must\r\n");
      out.write("            // recalculate it every time the window size changes.\r\n");
      out.write("            if ( FCKBrowserInfo.IsGecko )\r\n");
      out.write("            {\r\n");
      out.write("                function Window_OnResize()\r\n");
      out.write("                {\r\n");
      out.write("                    if ( FCKBrowserInfo.IsOpera )\r\n");
      out.write("                    return ;\r\n");
      out.write("\r\n");
      out.write("                    var oCell = document.getElementById( 'xEditingArea' ) ;\r\n");
      out.write("\r\n");
      out.write("                    var eInnerElement = oCell.firstChild ;\r\n");
      out.write("                    if ( eInnerElement )\r\n");
      out.write("                    {\r\n");
      out.write("                        eInnerElement.style.height = 0 ;\r\n");
      out.write("                        eInnerElement.style.height = oCell.scrollHeight - 2 ;\r\n");
      out.write("                    }\r\n");
      out.write("                }\r\n");
      out.write("                window.onresize = Window_OnResize ;\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            var categoryId = '");
      if (_jspx_meth_bean_005fwrite_005f0(_jspx_page_context))
        return;
      out.write("';\r\n");
      out.write("            window.onbeforeunload = function () {\r\n");
      out.write("\t            // if (FCK.IsDirty() === true)\r\n");
      out.write("\t           // \t event.returnValue = \"当前页面尚未保存！\";\r\n");
      out.write("\t           //离开页面时解除锁定\r\n");
      out.write("             \tif(diableToolBar){\r\n");
      out.write(" \t\t\t\t\treturn;\r\n");
      out.write("             \t}\r\n");
      out.write("             \tvar url = Globals.contextPath +\"/site/channel/unlockChannel.do?templateID=\"+oTemplate.ID+\"&channelID=\"+oChannel.ID +\"&timestamp=\";\r\n");
      out.write("             \turl = url + new Date().getTime();\r\n");
      out.write("             \tvar req = getXMLHttpRequest();\r\n");
      out.write("             \treq.open(\"GET\", url ,false);\r\n");
      out.write("             \tif(req.send()){\r\n");
      out.write(" \t\t\t\t\treturn;\r\n");
      out.write("             \t}\r\n");
      out.write("\t        }\r\n");
      out.write("\r\n");
      out.write("            function Confirm($question, $yesText, $noText){\r\n");
      out.write("                var args = {};\r\n");
      out.write("                args[\"question\"] = $question;\r\n");
      out.write("            \t    args[\"yesText\"] = $yesText;\r\n");
      out.write("                args[\"noText\"] = $noText;\r\n");
      out.write("                return window.showModalDialog(\"confirm.jsp?timestamp=\" + new Date().getTime(), args, \"dialogWidth:10px;dialogHeight:10px;help:no;scrollbars:yes;status:no\");\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            function confirmSaveChannel(){\r\n");
      out.write("            \tif (FCK.IsDirty() === true) {\r\n");
      out.write("                \tvar ret = Confirm(\"当前页面未保存，是否保存？\", \"保存\", \"不保存\");\r\n");
      out.write("                \tif (ret)\r\n");
      out.write("                \t\tFCK.ToolbarSet.CurrentInstance.Commands.GetCommand(\"SaveChannel\").Execute();\r\n");
      out.write("                \telse if (ret !== false)\r\n");
      out.write("                \t\treturn;\r\n");
      out.write("                }\r\n");
      out.write("            }\r\n");
      out.write("           \r\n");
      out.write("            </script>\r\n");
      out.write("        </head>\r\n");
      out.write("        <body>\r\n");
      out.write("            <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"height: 100%; table-layout: fixed\">\r\n");
      out.write("                <tr id=\"xToolbarRow\" style=\"display: none\">\r\n");
      out.write("                    <td id=\"xToolbarSpace\" style=\"overflow: hidden\">\r\n");
      out.write("                        <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("                            <tr id=\"xCollapsed\" style=\"display: none\">\r\n");
      out.write("                                <td id=\"xExpandHandle\" class=\"TB_Expand\" colspan=\"3\">\r\n");
      out.write("                                    ");
      if (_jspx_meth_html_005fimg_005f0(_jspx_page_context))
        return;
      out.write("</td>\r\n");
      out.write("                                </tr>\r\n");
      out.write("                                <tr id=\"xExpanded\" style=\"display: none\">\r\n");
      out.write("                                    <td id=\"xTBLeftBorder\" class=\"TB_SideBorder\" style=\"width: 1px; display: none;\"></td>\r\n");
      out.write("                                    <td id=\"xCollapseHandle\" style=\"display: none\" class=\"TB_Collapse\" valign=\"bottom\">\r\n");
      out.write("                                        ");
      if (_jspx_meth_html_005fimg_005f1(_jspx_page_context))
        return;
      out.write("</td>\r\n");
      out.write("                                        <td id=\"xToolbar\" class=\"TB_ToolbarSet\"></td>\r\n");
      out.write("                                        <td class=\"TB_SideBorder\" style=\"width: 1px\"></td>\r\n");
      out.write("                                    </tr>\r\n");
      out.write("                                </table>\r\n");
      out.write("                            </td>\r\n");
      out.write("                        </tr>\r\n");
      out.write("                        <tr>\r\n");
      out.write("                            <td id=\"xEditingArea\" valign=\"top\" style=\"height:100%;width:100%\"></td>\r\n");
      out.write("                        </tr>\r\n");
      out.write("                    </table>\r\n");
      out.write("                </body>\r\n");
      out.write("            </html>\r\n");
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
    _jspx_th_html_005frewrite_005f0.setPage("/portal/style.css.jsp");
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
    _jspx_th_html_005frewrite_005f1.setPage("/script/common.js.jsp");
    _jspx_th_html_005frewrite_005f1.setModule("/common");
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
    _jspx_th_html_005frewrite_005f2.setPage("/script/ajax.js");
    _jspx_th_html_005frewrite_005f2.setModule("/common");
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
    _jspx_th_html_005frewrite_005f3.setPage("/dialog.js.jsp");
    _jspx_th_html_005frewrite_005f3.setModule("/site");
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
    _jspx_th_html_005frewrite_005f4.setModule("/site");
    _jspx_th_html_005frewrite_005f4.setPage("/");
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
    _jspx_th_html_005frewrite_005f5.setModule("");
    _jspx_th_html_005frewrite_005f5.setPage("");
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
    _jspx_th_html_005frewrite_005f6.setPage("/editor/");
    _jspx_th_html_005frewrite_005f6.setModule("/common");
    int _jspx_eval_html_005frewrite_005f6 = _jspx_th_html_005frewrite_005f6.doStartTag();
    if (_jspx_th_html_005frewrite_005f6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f6);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f6);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f7(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f7 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f7.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f7.setParent(null);
    _jspx_th_html_005frewrite_005f7.setPage("/css.editor.css");
    _jspx_th_html_005frewrite_005f7.setModule("/site/editor");
    int _jspx_eval_html_005frewrite_005f7 = _jspx_th_html_005frewrite_005f7.doStartTag();
    if (_jspx_th_html_005frewrite_005f7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f7);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f7);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f8(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f8 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f8.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f8.setParent(null);
    _jspx_th_html_005frewrite_005f8.setPage("/editor/");
    _jspx_th_html_005frewrite_005f8.setModule("/common");
    int _jspx_eval_html_005frewrite_005f8 = _jspx_th_html_005frewrite_005f8.doStartTag();
    if (_jspx_th_html_005frewrite_005f8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f8);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f8);
    return false;
  }

  private boolean _jspx_meth_bean_005fwrite_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f0 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fignore_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fwrite_005f0.setParent(null);
    _jspx_th_bean_005fwrite_005f0.setName("categoryId");
    _jspx_th_bean_005fwrite_005f0.setIgnore(true);
    int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
    if (_jspx_th_bean_005fwrite_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fignore_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fignore_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
    return false;
  }

  private boolean _jspx_meth_html_005fimg_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:img
    org.apache.struts.taglib.html.ImgTag _jspx_th_html_005fimg_005f0 = (org.apache.struts.taglib.html.ImgTag) _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fwidth_005fstyleClass_005fpage_005fmodule_005fheight_005falt_005fnobody.get(org.apache.struts.taglib.html.ImgTag.class);
    _jspx_th_html_005fimg_005f0.setPageContext(_jspx_page_context);
    _jspx_th_html_005fimg_005f0.setParent(null);
    _jspx_th_html_005fimg_005f0.setStyleClass("TB_ExpandImg");
    _jspx_th_html_005fimg_005f0.setAlt("");
    _jspx_th_html_005fimg_005f0.setPage("/editor/images/spacer.gif");
    _jspx_th_html_005fimg_005f0.setModule("/common");
    _jspx_th_html_005fimg_005f0.setWidth("8");
    _jspx_th_html_005fimg_005f0.setHeight("4");
    int _jspx_eval_html_005fimg_005f0 = _jspx_th_html_005fimg_005f0.doStartTag();
    if (_jspx_th_html_005fimg_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fwidth_005fstyleClass_005fpage_005fmodule_005fheight_005falt_005fnobody.reuse(_jspx_th_html_005fimg_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fwidth_005fstyleClass_005fpage_005fmodule_005fheight_005falt_005fnobody.reuse(_jspx_th_html_005fimg_005f0);
    return false;
  }

  private boolean _jspx_meth_html_005fimg_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:img
    org.apache.struts.taglib.html.ImgTag _jspx_th_html_005fimg_005f1 = (org.apache.struts.taglib.html.ImgTag) _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fwidth_005fstyleClass_005fpage_005fmodule_005fheight_005falt_005fnobody.get(org.apache.struts.taglib.html.ImgTag.class);
    _jspx_th_html_005fimg_005f1.setPageContext(_jspx_page_context);
    _jspx_th_html_005fimg_005f1.setParent(null);
    _jspx_th_html_005fimg_005f1.setStyleClass("TB_CollapseImg");
    _jspx_th_html_005fimg_005f1.setAlt("");
    _jspx_th_html_005fimg_005f1.setPage("/editor/images/spacer.gif");
    _jspx_th_html_005fimg_005f1.setModule("/common");
    _jspx_th_html_005fimg_005f1.setWidth("8");
    _jspx_th_html_005fimg_005f1.setHeight("4");
    int _jspx_eval_html_005fimg_005f1 = _jspx_th_html_005fimg_005f1.doStartTag();
    if (_jspx_th_html_005fimg_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fwidth_005fstyleClass_005fpage_005fmodule_005fheight_005falt_005fnobody.reuse(_jspx_th_html_005fimg_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fwidth_005fstyleClass_005fpage_005fmodule_005fheight_005falt_005fnobody.reuse(_jspx_th_html_005fimg_005f1);
    return false;
  }
}
