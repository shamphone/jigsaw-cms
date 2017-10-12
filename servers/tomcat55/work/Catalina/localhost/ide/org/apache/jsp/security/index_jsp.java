package org.apache.jsp.security;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(4);
    _jspx_dependants.add("/WEB-INF/struts-bean.tld");
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
    _jspx_dependants.add("/WEB-INF/struts-tiles.tld");
    _jspx_dependants.add("/WEB-INF/fulong.tld");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fparameter_0026_005fvalue_005fname_005fid_005fnobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fbean_005fparameter_0026_005fvalue_005fname_005fid_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fbean_005fparameter_0026_005fvalue_005fname_005fid_005fnobody.release();
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

      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      //  bean:parameter
      java.lang.String moduleId = null;
      org.apache.struts.taglib.bean.ParameterTag _jspx_th_bean_005fparameter_005f0 = (org.apache.struts.taglib.bean.ParameterTag) _005fjspx_005ftagPool_005fbean_005fparameter_0026_005fvalue_005fname_005fid_005fnobody.get(org.apache.struts.taglib.bean.ParameterTag.class);
      _jspx_th_bean_005fparameter_005f0.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fparameter_005f0.setParent(null);
      _jspx_th_bean_005fparameter_005f0.setId("moduleId");
      _jspx_th_bean_005fparameter_005f0.setName("module");
      _jspx_th_bean_005fparameter_005f0.setValue("cms");
      int _jspx_eval_bean_005fparameter_005f0 = _jspx_th_bean_005fparameter_005f0.doStartTag();
      moduleId = (java.lang.String) _jspx_page_context.findAttribute("moduleId");
      if (_jspx_th_bean_005fparameter_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005fbean_005fparameter_0026_005fvalue_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fparameter_005f0);
        return;
      }
      moduleId = (java.lang.String) _jspx_page_context.findAttribute("moduleId");
      _005fjspx_005ftagPool_005fbean_005fparameter_0026_005fvalue_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fparameter_005f0);
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\r\n");
      out.write("<title>组织模型管理</title>\r\n");
      out.write("</head>\r\n");
      out.write("<frameset id=\"main\" rows=\"37,*\" cols=\"*\"  frameborder=\"0\" border=\"1\" framespacing=\"0\">\r\n");
      out.write("  <frame src=\"toolbar.jsp\" name=\"index\" rows=\"20\"  scrolling=\"no\" marginheight=\"0\" marginwidth=\"0\" scrolling=\"no\" noresize />\r\n");
      out.write("  <frameset id=\"main2\" rows=\"*\" cols=\"230,*\"  frameborder=\"1\" border=\"1\" framespacing=\"3\">\r\n");
      out.write("    <frame src=\"left.do\" name=\"tree\" marginheight=\"0\" marginwidth=\"0\" scrolling=\"no\" />\r\n");
      out.write("   \t<frame src=\"blank.jsp\" name=\"list\" marginheight=\"1\" marginwidth=\"1\" scrolling=\"no\"/>\r\n");
      out.write("  </frameset>\r\n");
      out.write("</frameset>\r\n");
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
}
