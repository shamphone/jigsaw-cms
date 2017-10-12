package org.apache.jsp.common.style;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class contents_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
  }

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
      response.setContentType("text/css; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("body,td,th {font-size: 12px;}\r\n");
      out.write("body {\r\n");
      out.write("    overflow:hidden;\r\n");
      out.write("    margin:2px 2px 0px 0px;\r\n");
      out.write("    padding:0px 0px 0px 0px;\r\n");
      out.write("    border-width:0px;\r\n");
      out.write("    }\r\n");
      out.write("form{margin: 0px 0px 0px 0px;padding: 0px 0px 0px 0px;}\r\n");
      out.write("fieldset{\r\n");
      out.write(" height:115px;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("#searchDiv{\r\n");
      out.write("    background-color:scrollbar;\r\n");
      out.write("    border: 1px solid buttonshadow;\r\n");
      out.write("    padding-right:2px;\r\n");
      out.write("    padding-bottom:0px;\r\n");
      out.write("    }\r\n");
      out.write("button{\r\n");
      out.write(" margin-right:2px;\r\n");
      out.write(" margin-bottom:2px;\r\n");
      out.write("}\r\n");
      out.write("#oListPanel {\r\n");
      out.write("    width: 100%;\r\n");
      out.write("    overflow-y: scroll;\r\n");
      out.write("    overflow-x: hidden;\r\n");
      out.write("    padding:0px 0px 0px 0px;\r\n");
      out.write("    text-align:center;\r\n");
      out.write("    border-left:1px solid buttonshadow;\r\n");
      out.write("    }\r\n");
      out.write(".tableClass thead{\r\n");
      out.write("}\r\n");
      out.write(".tableClass th{\r\n");
      out.write("\ttext-align: center;\r\n");
      out.write("\tbackground-color: window;\r\n");
      out.write("\tfont-weight: bold;\r\n");
      out.write("\tborder-top: solid 1px buttonshadow;\r\n");
      out.write("\tborder-bottom: solid 1px buttonshadow;\r\n");
      out.write("\tborder-right: solid 1px buttonshadow;\r\n");
      out.write("\t}\r\n");
      out.write("#listTable {width:100%; border: none; background-color: window;}\r\n");
      out.write("#listTable thead tr{position:relative; top:expression(offsetParent.scrollTop); }\r\n");
      out.write("#listTable thead th {\r\n");
      out.write("\ttext-align: center;\r\n");
      out.write("\tbackground-color: buttonface;\r\n");
      out.write("\tfont-weight: bold;\r\n");
      out.write("\tborder-top: 1px solid white;\r\n");
      out.write("\tborder-right:1px  solid buttonshadow;\r\n");
      out.write("\tborder-left:1px  solid window;\r\n");
      out.write("        border-bottom:2px  ridge buttonface;\r\n");
      out.write("\t}\r\n");
      out.write("#listTable tbody td{\r\n");
      out.write("\tborder-bottom: solid 1px buttonshadow;\r\n");
      out.write("\tborder-right: solid 1px buttonshadow;\r\n");
      out.write("        height:16px;\r\n");
      out.write("        cursor:hand\r\n");
      out.write("        }\r\n");
      out.write("#listTable tbody th{text-align: left;border-bottom: solid 1px buttonshadow;height:16px;cursor:pointer;font-weight:normal;}\r\n");
      out.write("\r\n");
      out.write("#listTable input{height:20px;font-size:11px;}\r\n");
      out.write("\r\n");
      out.write("#listTable tfoot tr{\r\n");
      out.write("  position: relative; \r\n");
      out.write("      overflow-x: hidden;\r\n");
      out.write("      top: expression(parentNode.parentNode.offsetHeight >=offsetParent.offsetHeight ? 0 - parentNode.parentNode.offsetHeight + offsetParent.offsetHeight + offsetParent.scrollTop : 0);\r\n");
      out.write("      }\r\n");
      out.write("}\r\n");
      out.write("#listTable tfoot td{\r\n");
      out.write("\ttext-align: right;\r\n");
      out.write("\tbackground-color: buttonface;\r\n");
      out.write("\tborder-top: solid 1px slategray;\r\n");
      out.write("}\r\n");
      out.write("#listTable input{\r\n");
      out.write(" \theight:16px;\r\n");
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
