package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  public String getServletInfo() {
    return "首页";
  }

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

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

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fsite_005fhtml;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fsite_005ftitle2_0026_005fformat_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fsite_005fscript_0026_005fportlets_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fsite_005fhtml = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fsite_005ftitle2_0026_005fformat_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fsite_005fscript_0026_005fportlets_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fsite_005fhtml.release();
    _005fjspx_005ftagPool_005fsite_005ftitle2_0026_005fformat_005fnobody.release();
    _005fjspx_005ftagPool_005fsite_005fscript_0026_005fportlets_005fnobody.release();
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
      response.setContentType("text/html; charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			"/error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      if (_jspx_meth_site_005fhtml_005f0(_jspx_page_context))
        return;
      out.write('\n');
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

  private boolean _jspx_meth_site_005fhtml_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  site:html
    com.fulong.taglib.site.HtmlTag _jspx_th_site_005fhtml_005f0 = (com.fulong.taglib.site.HtmlTag) _005fjspx_005ftagPool_005fsite_005fhtml.get(com.fulong.taglib.site.HtmlTag.class);
    _jspx_th_site_005fhtml_005f0.setPageContext(_jspx_page_context);
    _jspx_th_site_005fhtml_005f0.setParent(null);
    int _jspx_eval_site_005fhtml_005f0 = _jspx_th_site_005fhtml_005f0.doStartTag();
    if (_jspx_eval_site_005fhtml_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_site_005fhtml_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_site_005fhtml_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_site_005fhtml_005f0.doInitBody();
      }
      do {
        out.write("\n");
        out.write("<head>\n");
        out.write("  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\n");
        out.write("  <meta name=\"Author\" content=\"Beijing Zhongke Fulong Computer Corp.\"/>\n");
        out.write("  ");
        if (_jspx_meth_site_005ftitle2_005f0(_jspx_th_site_005fhtml_005f0, _jspx_page_context))
          return true;
        out.write('\n');
        out.write(' ');
        out.write(' ');
        if (_jspx_meth_site_005fscript_005f0(_jspx_th_site_005fhtml_005f0, _jspx_page_context))
          return true;
        out.write("\n");
        out.write("</head>\n");
        out.write("<body>\n");
        out.write("</body>\n");
        int evalDoAfterBody = _jspx_th_site_005fhtml_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_site_005fhtml_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_site_005fhtml_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fsite_005fhtml.reuse(_jspx_th_site_005fhtml_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fsite_005fhtml.reuse(_jspx_th_site_005fhtml_005f0);
    return false;
  }

  private boolean _jspx_meth_site_005ftitle2_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_site_005fhtml_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  site:title2
    com.fulong.taglib.site.Title2Tag _jspx_th_site_005ftitle2_005f0 = (com.fulong.taglib.site.Title2Tag) _005fjspx_005ftagPool_005fsite_005ftitle2_0026_005fformat_005fnobody.get(com.fulong.taglib.site.Title2Tag.class);
    _jspx_th_site_005ftitle2_005f0.setPageContext(_jspx_page_context);
    _jspx_th_site_005ftitle2_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_site_005fhtml_005f0);
    // /index.jsp(12,2) name = format type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_site_005ftitle2_005f0.setFormat("$C-$T");
    int _jspx_eval_site_005ftitle2_005f0 = _jspx_th_site_005ftitle2_005f0.doStartTag();
    if (_jspx_th_site_005ftitle2_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fsite_005ftitle2_0026_005fformat_005fnobody.reuse(_jspx_th_site_005ftitle2_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fsite_005ftitle2_0026_005fformat_005fnobody.reuse(_jspx_th_site_005ftitle2_005f0);
    return false;
  }

  private boolean _jspx_meth_site_005fscript_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_site_005fhtml_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  site:script
    com.fulong.taglib.site.ScriptTag _jspx_th_site_005fscript_005f0 = (com.fulong.taglib.site.ScriptTag) _005fjspx_005ftagPool_005fsite_005fscript_0026_005fportlets_005fnobody.get(com.fulong.taglib.site.ScriptTag.class);
    _jspx_th_site_005fscript_005f0.setPageContext(_jspx_page_context);
    _jspx_th_site_005fscript_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_site_005fhtml_005f0);
    // /index.jsp(13,2) name = portlets type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_site_005fscript_005f0.setPortlets("");
    int _jspx_eval_site_005fscript_005f0 = _jspx_th_site_005fscript_005f0.doStartTag();
    if (_jspx_th_site_005fscript_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fsite_005fscript_0026_005fportlets_005fnobody.reuse(_jspx_th_site_005fscript_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fsite_005fscript_0026_005fportlets_005fnobody.reuse(_jspx_th_site_005fscript_005f0);
    return false;
  }
}
