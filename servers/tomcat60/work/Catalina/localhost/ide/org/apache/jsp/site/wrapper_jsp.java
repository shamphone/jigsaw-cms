package org.apache.jsp.site;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class wrapper_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(4);
    _jspx_dependants.add("/WEB-INF/struts-bean.tld");
    _jspx_dependants.add("/WEB-INF/struts-logic.tld");
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
    _jspx_dependants.add("/WEB-INF/struts-tiles.tld");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fparameter_0026_005fvalue_005fname_005fid_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fignore_005fnobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fbean_005fparameter_0026_005fvalue_005fname_005fid_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fignore_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fbean_005fparameter_0026_005fvalue_005fname_005fid_005fnobody.release();
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fignore_005fnobody.release();
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
      out.write("<html>\r\n");
      out.write("  <head>\r\n");
      out.write("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\r\n");
      out.write("    <meta http-equiv=\"EXPIRES\" content=\"0\"/>\r\n");
      out.write("    <meta http-equiv=\"Pragma\" Content=\"No-cach\" />\r\n");
      out.write("    ");
      //  bean:parameter
      java.lang.String title = null;
      org.apache.struts.taglib.bean.ParameterTag _jspx_th_bean_005fparameter_005f0 = (org.apache.struts.taglib.bean.ParameterTag) _005fjspx_005ftagPool_005fbean_005fparameter_0026_005fvalue_005fname_005fid_005fnobody.get(org.apache.struts.taglib.bean.ParameterTag.class);
      _jspx_th_bean_005fparameter_005f0.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fparameter_005f0.setParent(null);
      _jspx_th_bean_005fparameter_005f0.setId("title");
      _jspx_th_bean_005fparameter_005f0.setName("title");
      _jspx_th_bean_005fparameter_005f0.setValue("");
      int _jspx_eval_bean_005fparameter_005f0 = _jspx_th_bean_005fparameter_005f0.doStartTag();
      title = (java.lang.String) _jspx_page_context.findAttribute("title");
      if (_jspx_th_bean_005fparameter_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005fbean_005fparameter_0026_005fvalue_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fparameter_005f0);
        return;
      }
      title = (java.lang.String) _jspx_page_context.findAttribute("title");
      _005fjspx_005ftagPool_005fbean_005fparameter_0026_005fvalue_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fparameter_005f0);
      out.write("\r\n");
      out.write("    <title>");
      if (_jspx_meth_bean_005fwrite_005f0(_jspx_page_context))
        return;
      out.write("</title>\r\n");
      out.write("  </head>\r\n");
      out.write("  <body style=\"border:0px;padding:0 0 0 0;margin:0 0 0 0;\" scroll=\"no\">\r\n");
      out.write("    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\"  width=\"100%\" height=\"100%\"  style=\"table-layout: fixed\">\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td valign=\"top\">\r\n");
      out.write("          <input type=\"hidden\" value=\"");
      out.print( request.getParameter("url"));
      out.write("\"/>\r\n");
      out.write("            <iframe id=\"theFrame\" frameborder=\"0\" src=\"");
      out.print( request.getParameter("url"));
      out.write("\" scrolling=\"no\" style=\"height:100%; width:100%\"></iframe>\r\n");
      out.write("          </td>\r\n");
      out.write("        </tr>\r\n");
      out.write("      </table>\r\n");
      out.write("    </body>\r\n");
      out.write("    <script type=\"text/javascript\">\r\n");
      out.write("      function  document.all.theFrame.contentWindow.onload(){\r\n");
      out.write("        document.title=document.all.theFrame.contentWindow.document.title;\r\n");
      out.write("      }\r\n");
      out.write("      function Reload() {\r\n");
      out.write("\t\tdocument.frames(\"theFrame\").location.replace(document.frames(\"theFrame\").window.location.href);  \r\n");
      out.write("      }\r\n");
      out.write("      </script>\r\n");
      out.write("      </html>\r\n");
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

  private boolean _jspx_meth_bean_005fwrite_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f0 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fignore_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fwrite_005f0.setParent(null);
    _jspx_th_bean_005fwrite_005f0.setName("title");
    _jspx_th_bean_005fwrite_005f0.setIgnore(true);
    int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
    if (_jspx_th_bean_005fwrite_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fignore_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fignore_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
    return false;
  }
}
