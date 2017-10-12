package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class footer001_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(6);
    _jspx_dependants.add("/WEB-INF/fulong-portal.tld");
    _jspx_dependants.add("/WEB-INF/fulong-site.tld");
    _jspx_dependants.add("/WEB-INF/struts-bean.tld");
    _jspx_dependants.add("/WEB-INF/struts-logic.tld");
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
    _jspx_dependants.add("/WEB-INF/struts-tiles.tld");
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
      response.setContentType("text/html; charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<table cellspacing=\"0\" cellpadding=\"0\" width=\"98%\" border=\"0\">\n");
      out.write("  <tbody>\n");
      out.write("    <tr>\n");
      out.write("      <td><img height=\"50\" alt=\"\" width=\"17\" src=\"http://oa.fulong.com.cn/oasv1/img/f_03.gif\" /></td>\n");
      out.write("      <td width=\"100%\">\n");
      out.write("      <table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" border=\"0\">\n");
      out.write("        <tbody>\n");
      out.write("          <tr>\n");
      out.write("            <td nowrap=\"nowrap\" width=\"100%\" background=\"http://oa.fulong.com.cn/oasv1/img/f_04.gif\"><font color=\"#3a5069\"><br />\n");
      out.write("            [ <a style=\"cursor: pointer\" onclick=\"window.open('http://oa.fulong.com.cn/oasv1/version.html', '_blank', 'width=400,height=250,menubar=no,toolbar=no')\">LongCon OA SV1</a> ]　 </font></td>\n");
      out.write("            <td><img height=\"50\" alt=\"\" width=\"126\" src=\"http://oa.fulong.com.cn/oasv1/img/f_05.gif\" /></td>\n");
      out.write("          </tr>\n");
      out.write("        </tbody>\n");
      out.write("      </table>\n");
      out.write("      </td>\n");
      out.write("      <td><img height=\"50\" alt=\"\" width=\"17\" src=\"http://oa.fulong.com.cn/oasv1/img/f_06.gif\" /></td>\n");
      out.write("    </tr>\n");
      out.write("  </tbody>\n");
      out.write("</table>\n");
      out.write("<br />");
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
