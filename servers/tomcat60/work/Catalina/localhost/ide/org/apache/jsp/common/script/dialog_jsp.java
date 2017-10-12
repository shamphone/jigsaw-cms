package org.apache.jsp.common.script;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class dialog_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("var ContextPath = '<html:rewrite page=\"/\" module=\"\"/>';\r\n");
      out.write("if (window.parent != null)\r\n");
      out.write("\t\twindow.parent.document.title = this.document.title;\r\n");
      out.write("/**\r\n");
      out.write(" * 设置ESC为取消键，当没有选中组件时回车为确认键。\r\n");
      out.write(" */\r\n");
      out.write("document.onkeydown = function() {\r\n");
      out.write("\tvar btn;\r\n");
      out.write("\tif (event.keyCode == 13) {\r\n");
      out.write("\t\tvar src = event.srcElement;\r\n");
      out.write("\t\tif (!src.tagName || src.tagName.toLowerCase() != \"textarea\")\r\n");
      out.write("\t\t\tbtn = document.getElementById(\"btnOk\");\r\n");
      out.write("\t} else if (event.keyCode == 27) {\r\n");
      out.write("\t\tbtn = document.getElementById(\"btnCancel\");\r\n");
      out.write("\t}\r\n");
      out.write("\tif (btn && !btn.disabled) {\r\n");
      out.write("\t\tbtn.click();\r\n");
      out.write("\t\tif (event.keyCode == 13)\r\n");
      out.write("\t\t\treturn false;\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
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
