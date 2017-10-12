package org.apache.jsp.site.css;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class style_js_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  public Object getDependants() {
    return _jspx_dependants;
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
      out.write("function Style() {\r\n");
      out.write("\tthis['color']\t\t\t\t\t= \"\";\r\n");
      out.write("\tthis['font-family'] \t\t\t= \"\";\r\n");
      out.write("\tthis['font-size']\t\t\t\t= \"\";\r\n");
      out.write("\tthis['font-style']\t\t\t\t= \"\";\r\n");
      out.write("\tthis['text-decoration']\t\t\t= \"\";\r\n");
      out.write("\tthis['line-height']\t\t\t\t= \"\";\r\n");
      out.write("\tthis['font-weight']\t\t\t\t= \"\";\r\n");
      out.write("\tthis['text-transform']\t\t\t= \"\";\r\n");
      out.write("\tthis['background-color']\t\t= \"\";\r\n");
      out.write("\tthis['background-image']\t\t= \"\";\r\n");
      out.write("\tthis['background-attachment']\t= \"\";\r\n");
      out.write("\tthis['background-repeat']\t\t= \"\";\r\n");
      out.write("\tthis['background-position']\t\t= \"\";\r\n");
      out.write("\tthis['word-spacing']\t\t\t= \"\";\r\n");
      out.write("\tthis['letter-spacing']\t\t\t= \"\";\r\n");
      out.write("\tthis['text-align']\t\t\t\t= \"\";\r\n");
      out.write("\tthis['vertical-align']\t\t\t= \"\";\r\n");
      out.write("\tthis['text-indent']\t\t\t\t= \"\";\r\n");
      out.write("\tthis['white-space']\t\t\t\t= \"\";\r\n");
      out.write("\tthis['display']\t\t\t\t\t= \"\";\r\n");
      out.write("\tthis['overflow']\t\t\t\t= \"\";\r\n");
      out.write("\tthis['border-color']\t\t\t= \"\";\r\n");
      out.write("\tthis['border-style']\t\t\t= \"\";\r\n");
      out.write("\tthis['border-size']\t\t\t\t= \"\";\r\n");
      out.write("\tthis['border-top']\t\t\t\t= \"\";\r\n");
      out.write("\tthis['border-bottom']\t\t\t= \"\";\r\n");
      out.write("\tthis['border-left']\t\t\t\t= \"\";\r\n");
      out.write("\tthis['border-right']\t\t\t= \"\";\r\n");
      out.write("\tthis['margin-top']\t\t\t\t= \"\";\r\n");
      out.write("\tthis['margin-bottom']\t\t\t= \"\";\r\n");
      out.write("\tthis['margin-left']\t\t\t\t= \"\";\r\n");
      out.write("\tthis['margin-right']\t\t\t= \"\";\r\n");
      out.write("\tthis['padding-top']\t\t\t\t= \"\";\r\n");
      out.write("\tthis['padding-bottom']\t\t\t= \"\";\r\n");
      out.write("\tthis['padding-left']\t\t\t= \"\";\r\n");
      out.write("\tthis['padding-right']\t\t\t= \"\";\r\n");
      out.write("\tthis['width']\t\t\t\t\t= \"\";\r\n");
      out.write("\tthis['height']\t\t\t\t\t= \"\";\r\n");
      out.write("\tthis['float']\t\t\t\t\t= \"\";\r\n");
      out.write("\tthis['visibility']\t\t\t\t= \"\";\r\n");
      out.write("\tthis['position']\t\t\t\t= \"\";\r\n");
      out.write("\tthis['top']\t\t\t\t\t\t= \"\";\r\n");
      out.write("\tthis['left']\t\t\t\t\t= \"\";\r\n");
      out.write("\tthis['z-index']\t\t\t\t\t= \"\";\r\n");
      out.write("\tthis['clip']\t\t\t\t\t= \"\";\r\n");
      out.write("\tthis['list-style-type']\t\t\t= \"\";\r\n");
      out.write("\tthis['list-style-image']\t\t= \"\";\r\n");
      out.write("\tthis['list-style-position']\t\t= \"\";\r\n");
      out.write("\tthis['cursor']\t\t\t\t\t= \"\";\r\n");
      out.write("\tthis['behavior']\t\t\t\t= \"\";\r\n");
      out.write("\tthis['filter']\t\t\t\t\t= \"\";\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("Style.prototype.toString = function() {\r\n");
      out.write("\tvar arr = [];\r\n");
      out.write("\tfor (var e in this) {\r\n");
      out.write("\t\tif (this[e] && typeof(this[e]) != \"function\")\r\n");
      out.write("\t\t\tarr.push(e + \": \" + this[e]);\r\n");
      out.write("\t}\r\n");
      out.write("\treturn arr.join(\"; \");\r\n");
      out.write("}\r\n");
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
}
