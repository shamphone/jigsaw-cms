package org.apache.jsp.site.editor;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class confirm_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(4);
    _jspx_dependants.add("/WEB-INF/struts-bean.tld");
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
    _jspx_dependants.add("/WEB-INF/struts-logic.tld");
    _jspx_dependants.add("/WEB-INF/struts-tiles.tld");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.release();
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
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
      out.write("<html>\r\n");
      out.write("\t<head>\r\n");
      out.write("\t\t<title>Windows Internet Explorer</title>\r\n");
      out.write("\t\t<script Language='Javascript' src='");
      if (_jspx_meth_html_005frewrite_005f0(_jspx_page_context))
        return;
      out.write("' type=\"text/javascript\"></script>\r\n");
      out.write("\t\t<script type=\"text/javascript\" language=\"javascript\">\r\n");
      out.write("\t\t\twindow.onload = function() {\r\n");
      out.write("\t\t\t\tvar args = window.dialogArguments;\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"question\").innerHTML = args.question;\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"btnOk\").innerHTML = args.yesText;\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"btnCancel\").innerHTML = args.noText;\r\n");
      out.write("\t\t\t\t_AdjustDialog();\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tfunction _AdjustDialog(){\r\n");
      out.write("\t\t\t\tvar version = isVersion();\r\n");
      out.write("\t\t\t\tif (version == 'ie6') {\r\n");
      out.write("\t\t\t\t\tvar w = tabDialogSize.offsetWidth + 200;\r\n");
      out.write("\t\t\t\t\tvar h = tabDialogSize.offsetHeight + 25;\r\n");
      out.write("\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\tvar w = tabDialogSize.offsetWidth + 16;\r\n");
      out.write("\t\t\t\t\tvar h = tabDialogSize.offsetHeight + 25;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\t/*\r\n");
      out.write("\t\t\t\tif(config.IsSP2){\r\n");
      out.write("\t\t\t\t\th += 20;\r\n");
      out.write("\t\t\t\t}*/\r\n");
      out.write("\t\t\t\twindow.dialogWidth = w + \"px\";\r\n");
      out.write("\t\t\t\twindow.dialogHeight = h + \"px\";\r\n");
      out.write("\t\t\t\twindow.dialogLeft = (screen.availWidth - w) / 2;\r\n");
      out.write("\t\t\t\twindow.dialogTop = (screen.availHeight - h) / 2;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\t\tfunction _Close($retValue) {\r\n");
      out.write("\t\t\t\twindow.returnValue = $retValue;\r\n");
      out.write("\t\t\t\twindow.close();\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t</script>\r\n");
      out.write("\t\t<style type=\"text/css\">\r\n");
      out.write("\t\t\tbody {background-color:#ECE9D8; margin:0px; padding:0px;overflow: hidden; }\r\n");
      out.write("\t\t\t#tabDialogSize {font-size:9pt; }\r\n");
      out.write("\t\t\tbutton {font-size:9pt; height:21px;width:76px;}\r\n");
      out.write("\t\t\t.iconBar {padding-top:10px; padding-left:10px; }\r\n");
      out.write("\t\t\t.buttonBar {padding-top:10px;padding-left:20px; }\r\n");
      out.write("\t\t\t#question {padding-top:10px;}\r\n");
      out.write("\t\t</style>\r\n");
      out.write("\t</head>\r\n");
      out.write("\t<body>\r\n");
      out.write("\t\t<table id=\"tabDialogSize\">\r\n");
      out.write("\t\t\t<tr valign=\"top\">\r\n");
      out.write("\t\t\t\t<td width=\"50\" align=\"left\" class=\"iconBar\">\r\n");
      out.write("\t\t\t\t\t<img src=\"images/question.gif\">\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t<td valign=\"center\">\r\n");
      out.write("\t\t\t\t\t<div id=\"question\"></div>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t<center>\r\n");
      out.write("\t\t<div class=\"buttonBar\">\r\n");
      out.write("\t\t\t<button type=\"button\" class=\"commonbut\" id=\"btnOk\" onclick=\"_Close(true)\">确定</button>&nbsp;\r\n");
      out.write("\t\t\t<button type=\"button\" class=\"commonbut\" id=\"btnCancel\" onclick=\"_Close(false)\">取消</button>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t</center>\r\n");
      out.write("\t</body>\r\n");
      out.write("</html>\r\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
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
    // /site/editor/confirm.jsp(9,37) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f0.setPage("/script/common.js");
    // /site/editor/confirm.jsp(9,37) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f0.setModule("/common");
    int _jspx_eval_html_005frewrite_005f0 = _jspx_th_html_005frewrite_005f0.doStartTag();
    if (_jspx_th_html_005frewrite_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f0);
    return false;
  }
}
