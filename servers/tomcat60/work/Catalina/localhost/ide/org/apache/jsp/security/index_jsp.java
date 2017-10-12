package org.apache.jsp.security;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(4);
    _jspx_dependants.add("/WEB-INF/struts-bean.tld");
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
    _jspx_dependants.add("/WEB-INF/struts-tiles.tld");
    _jspx_dependants.add("/WEB-INF/fulong.tld");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fparameter_0026_005fvalue_005fname_005fid_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fbean_005fparameter_0026_005fvalue_005fname_005fid_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fbean_005fparameter_0026_005fvalue_005fname_005fid_005fnobody.release();
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
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      //  bean:parameter
      java.lang.String moduleId = null;
      org.apache.struts.taglib.bean.ParameterTag _jspx_th_bean_005fparameter_005f0 = (org.apache.struts.taglib.bean.ParameterTag) _005fjspx_005ftagPool_005fbean_005fparameter_0026_005fvalue_005fname_005fid_005fnobody.get(org.apache.struts.taglib.bean.ParameterTag.class);
      _jspx_th_bean_005fparameter_005f0.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fparameter_005f0.setParent(null);
      // /security/index.jsp(8,0) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fparameter_005f0.setId("moduleId");
      // /security/index.jsp(8,0) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fparameter_005f0.setName("module");
      // /security/index.jsp(8,0) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fparameter_005f0.setValue("cms");
      int _jspx_eval_bean_005fparameter_005f0 = _jspx_th_bean_005fparameter_005f0.doStartTag();
      moduleId = (java.lang.String) _jspx_page_context.findAttribute("moduleId");
      if (_jspx_th_bean_005fparameter_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005fbean_005fparameter_0026_005fvalue_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fparameter_005f0);
        return;
      }
      moduleId = (java.lang.String) _jspx_page_context.findAttribute("moduleId");
      _005fjspx_005ftagPool_005fbean_005fparameter_0026_005fvalue_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fparameter_005f0);
      out.write("\r\n");
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
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
