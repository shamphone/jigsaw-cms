package org.apache.jsp.common.style;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class toolbar_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
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
      out.write("body\r\n");
      out.write("{\r\n");
      out.write("\toverflow:visible;\r\n");
      out.write("\tpadding: 0px 0px 0px 0px;\r\n");
      out.write("\tmargin: 0px 0px 0px 0px;\t\r\n");
      out.write("\tbackground-color:buttonface;\r\n");
      out.write("\tvertical-align:middle;\r\n");
      out.write("\tfont-size: 12px;\r\n");
      out.write("}\r\n");
      out.write("#DLGToolbar20{\r\n");
      out.write("  border-bottom:2px groove;\r\n");
      out.write("  margin: 1px 1px 1px 1px;\r\n");
      out.write("  padding: 0px 0px 4px 0px;\r\n");
      out.write("  height: 30px;\r\n");
      out.write("  line-height:30px;\r\n");
      out.write("  vertical-align : middle;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write(".seperator{\r\n");
      out.write("    margin: 1px 3px 1px 3px;\r\n");
      out.write("    padding: 0px 1px 0px 1px;\r\n");
      out.write("    height: 20px;\r\n");
      out.write("    line-height:20px;\r\n");
      out.write("    vertical-align : middle;\r\n");
      out.write("    border-left:1px outset buttonface;\r\n");
      out.write("    display:inline-block;\r\n");
      out.write("}\r\n");
      out.write(".DLGButton\r\n");
      out.write("{\r\n");
      out.write("    margin: 1px 2px 1px 2px;\r\n");
      out.write("    padding: 0px 1px 0px 1px;\r\n");
      out.write("    height: 20px;\r\n");
      out.write("    line-height:20px;\r\n");
      out.write("    vertical-align : middle;\r\n");
      out.write("    border: buttonface 1px solid;\r\n");
      out.write("    cursor:pointer;\r\n");
      out.write("    display:inline-block;\r\n");
      out.write("    behavior: url(");
      if (_jspx_meth_html_005frewrite_005f0(_jspx_page_context))
        return;
      out.write(");\r\n");
      out.write("}\r\n");
      out.write(".DLGButton img{\r\n");
      out.write("  border: 0px;\r\n");
      out.write("  vertical-align:middle;\r\n");
      out.write("  margin-right:2px;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".DLGButtonDisabled\r\n");
      out.write("{\r\n");
      out.write("   color: graytext;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".DLGButtonDisabled img\r\n");
      out.write("{\r\n");
      out.write("    filter: gray() alpha(opacity=50);\r\n");
      out.write("    opacity: 0.30; \r\n");
      out.write("}\r\n");
      out.write(".DLGButton_On_Over\r\n");
      out.write("{\r\n");
      out.write("    border: #0A246A 1px solid;\r\n");
      out.write("    background-color: #B6BDD2;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write(".ToogleButton\r\n");
      out.write("{\r\n");
      out.write("    margin: 1px 2px 1px 2px;\r\n");
      out.write("    padding: 0px 5px 0px 5px;\r\n");
      out.write("    height: 20px;\r\n");
      out.write("    line-height:20px;\r\n");
      out.write("    vertical-align : middle;\r\n");
      out.write("    border: #0A246A 1px solid;\r\n");
      out.write("    cursor:pointer;\r\n");
      out.write("    display:inline-block;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".ToogleButtonDown{\r\n");
      out.write("\tbackground-color:#ffffff;\r\n");
      out.write("    border: #0A246A 1px solid;\t\r\n");
      out.write("}\r\n");
      out.write(".ToogleButton img{\r\n");
      out.write("  border: 0px;\r\n");
      out.write("  vertical-align:middle;\r\n");
      out.write("  margin-right:2px;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write(".userInfo{\r\n");
      out.write("   position:absolute; \r\n");
      out.write("   right:5px;\r\n");
      out.write("    margin: 1px 0px 1px 0px;\r\n");
      out.write("    padding: 0px 1px 0px 1px;\r\n");
      out.write("    height: 20px;\r\n");
      out.write("    line-height:20px;\r\n");
      out.write("    vertical-align : middle;\r\n");
      out.write("    border: buttonface 1px solid;\r\n");
      out.write("    display:inline-block;\r\n");
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

  private boolean _jspx_meth_html_005frewrite_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f0 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f0.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f0.setParent(null);
    _jspx_th_html_005frewrite_005f0.setModule("/common");
    _jspx_th_html_005frewrite_005f0.setPage("/style/dlgbutton.htc");
    int _jspx_eval_html_005frewrite_005f0 = _jspx_th_html_005frewrite_005f0.doStartTag();
    if (_jspx_th_html_005frewrite_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f0);
    return false;
  }
}
