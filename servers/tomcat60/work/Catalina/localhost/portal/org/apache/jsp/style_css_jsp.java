package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class style_css_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
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
      response.setContentType("text/css;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("\n");
      out.write(".editorBody {background-color: menu; border-style: none; border-top: 0px; border-bottom: 0px; border-left: 0px; border-right: 0px; margin-top: 0px; margin-bottom: 0px; margin-left: 0px; margin-right: 0px; padding-top: 0px; padding-bottom: 0px; padding-left: 0px; padding-right: 0px}\n");
      out.write(".editorBody FIELDSET {font-size: 12px; padding-top: 5px; padding-bottom: 5px; padding-left: 5px; padding-right: 5px; width: 380px;width: 360px\\9; height: 320px}\n");
      out.write(".formTable {font-size: 10px; padding-top: 5px; padding-bottom: 5px; padding-left: 5px; padding-right: 5px; width: 380px;}\n");
      out.write(".formTable TH {font-size: 12px; font-weight: normal}\n");
      out.write(".formTable TD {font-size: 12px;}\n");
      out.write(".formTable SELECT {width: 150px}\n");
      out.write(".formTable SPAN {margin-right: 2px}\n");
      out.write("formTable .input {width: 205px}\n");
      out.write(".selectionEditor BUTTON {margin-top: 2px; margin-right: 6px}\n");
      out.write("HR.seperator {color: #3399cc}\n");
      out.write("UL.noprefix {list-style-type: none}\n");
      out.write(".tips {margin-top: 10em; margin-bottom: 10em; margin-left: 10em; margin-right: 10em}\n");
      out.write(".h2tips {color: gray; margin-top: 1em; margin-bottom: 1em; margin-left: 1em; margin-right: 1em}\n");
      out.write(".tip {font-size: 12px; margin-top: 5em; margin-bottom: 5em; margin-left: 5em; margin-right: 5em}\n");
      out.write(".operation {line-height: 20px; text-align: center; border-top: #c0c0c0 1px solid; margin-top: 2em; margin-bottom: 1em; margin-left: 1em; margin-right: 1em; height: 30px}\n");
      out.write(".operation INPUT {margin-top: 5px; margin-bottom: 5px; margin-left: 5px; margin-right: 5px}\n");
      out.write(".commonbut {margin-left: 0px;margin-left: 5px\\9; width:55px;white-space: nowrap}\n");
      out.write(".formTitle {white-space: nowrap}\n");
      out.write(".toolbar BUTTON {margin-left: 6px; width: 75px; height: 21px}\n");
      out.write(".toolbar {text-align: right}\n");
      out.write(".portletWindow {background-color: #f0f0f0; display: inline; border-color: #c0c0c0; border-style: dashed; border-top: #c0c0c0 1px dashed; border-bottom: #c0c0c0 1px dashed; border-left: #c0c0c0 1px dashed; border-right: #c0c0c0 1px dashed; }\n");
      out.write(".portletBody {background-color: #ffffff}\n");
      out.write(".portletHeader {font-size: 10pt; background-color: inactivecaption; padding-top: 2px; padding-left: 2px; height: 20px; cursor: hand}\n");
      out.write(".activeHeader {background-color: activecaption}\n");
      out.write(".portletPreview {display: inline; border-color: #808080; border-style: dotted; border-top: #808080 1px dotted; border-bottom: #808080 1px dotted; border-left: #808080 1px dotted; border-right: #808080 1px dotted}\n");
      out.write("#pannelSelect {background-color: menu; width: 100px; height: 360px}\n");
      out.write(".pannelDiv {width: 100px; height: 345px}\n");
      out.write(".stylePreviewWindow {background-color: #ffffff; border-color: buttonface; border-style: ridge; border-top: buttonface 1px ridge; border-bottom: buttonface 1px ridge; border-left: buttonface 1px ridge; border-right: buttonface 1px ridge; margin-right: 2px; padding-top: 5px; padding-bottom: 5px; padding-left: 5px; padding-right: 5px; width: 120px; height: 160px}\n");
      out.write(".portlet_title {color: #000; font-weight: bold; text-align: right; white-space: nowrap}\n");
      out.write("SPAN.portletName {color: captiontext}\n");
      out.write("TD.navSelectors {vertical-align: top; width: 130px}\n");
      out.write("TD.navSelectors SELECT {width: 130px}\n");
      out.write("TD.navTools {width: 60px}\n");
      out.write("#staticContentEditor {background-color: white; overflow: scroll; border-color: activeborder; border-style: inset; border-top: activeborder 2px inset; border-bottom: activeborder 2px inset; border-left: activeborder 2px inset; border-right: activeborder 2px inset; width: 100%; height: 200px}\n");
      out.write(".commonSelection {width: 155px}\n");
      out.write("#fields {width: 155px}\n");
      out.write("åå¯¹åæ¹é½æ¯ {font-family: System}\n");
      out.write("#ZL {font-family: Terminal}");
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
}
