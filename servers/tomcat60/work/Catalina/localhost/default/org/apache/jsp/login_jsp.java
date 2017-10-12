package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

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

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fsite_005ftitle2_0026_005fformat;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005ffulong_005fpreference;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005ffulong_005fname;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005ffulong_005fvalue;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fsite_005ftitle2_0026_005fformat = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005ffulong_005fpreference = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005ffulong_005fname = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005ffulong_005fvalue = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fsite_005ftitle2_0026_005fformat.release();
    _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.release();
    _005fjspx_005ftagPool_005ffulong_005fpreference.release();
    _005fjspx_005ftagPool_005ffulong_005fname.release();
    _005fjspx_005ftagPool_005ffulong_005fvalue.release();
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.release();
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
      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\n");
      out.write("  <head>\n");
      out.write("    ");
      if (_jspx_meth_site_005ftitle2_005f0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n");
      out.write("    <meta http-equiv=\"Author\" content=\"Beijing Zhongke Fulong Computer Corp.\" /><script type=\"text/javascript\" language=\"javascript\" src=\"../../portal/portal.js.jsp\"></script>\n");
      out.write("  </head>\n");
      out.write("  <body>\n");
      out.write("    <form action=\"#\" method=\"post\" definition=\"principal-scheme\" definitionname=\"用户信息库\" onsubmit=\"return Validator.ValidateForm(this);\" name=\"fm187\">\n");
      out.write("      <br />\n");
      out.write("      <br />\n");
      out.write("      &nbsp;&nbsp;&nbsp; 用户登入：\n");
      out.write("      <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n");
      out.write("        <tbody>\n");
      out.write("          <tr>\n");
      out.write("            <td width=\"60\">用户名：</td>\n");
      out.write("            <td width=\"200\">");
      if (_jspx_meth_fulong_005fportlet_005f0(_jspx_page_context))
        return;
      out.write("</td>\n");
      out.write("          </tr>\n");
      out.write("          <tr>\n");
      out.write("            <td>密码：</td>\n");
      out.write("            <td>");
      if (_jspx_meth_fulong_005fportlet_005f1(_jspx_page_context))
        return;
      out.write("</td>\n");
      out.write("          </tr>\n");
      out.write("          <tr>\n");
      out.write("            <td>校验码：</td>\n");
      out.write("            <td></td>\n");
      out.write("          </tr>\n");
      out.write("          <tr>\n");
      out.write("            <td></td>\n");
      out.write("            <td>");
      if (_jspx_meth_fulong_005fportlet_005f2(_jspx_page_context))
        return;
      out.write("</td>\n");
      out.write("          </tr>\n");
      out.write("        </tbody>\n");
      out.write("      </table>\n");
      out.write("      <br />\n");
      out.write("      <br />\n");
      out.write("    </form>\n");
      out.write("    <br />\n");
      out.write("  </body>\n");
      out.write("</html>");
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

  private boolean _jspx_meth_site_005ftitle2_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  site:title2
    com.fulong.taglib.site.Title2Tag _jspx_th_site_005ftitle2_005f0 = (com.fulong.taglib.site.Title2Tag) _005fjspx_005ftagPool_005fsite_005ftitle2_0026_005fformat.get(com.fulong.taglib.site.Title2Tag.class);
    _jspx_th_site_005ftitle2_005f0.setPageContext(_jspx_page_context);
    _jspx_th_site_005ftitle2_005f0.setParent(null);
    // /login.jsp(10,4) name = format type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_site_005ftitle2_005f0.setFormat("#");
    int _jspx_eval_site_005ftitle2_005f0 = _jspx_th_site_005ftitle2_005f0.doStartTag();
    if (_jspx_eval_site_005ftitle2_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_site_005ftitle2_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_site_005ftitle2_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_site_005ftitle2_005f0.doInitBody();
      }
      do {
        out.write('登');
        out.write('录');
        out.write('页');
        int evalDoAfterBody = _jspx_th_site_005ftitle2_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_site_005ftitle2_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_site_005ftitle2_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fsite_005ftitle2_0026_005fformat.reuse(_jspx_th_site_005ftitle2_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fsite_005ftitle2_0026_005fformat.reuse(_jspx_th_site_005ftitle2_005f0);
    return false;
  }

  private boolean _jspx_meth_fulong_005fportlet_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:portlet
    com.fulong.taglib.portal.PortletTag _jspx_th_fulong_005fportlet_005f0 = (com.fulong.taglib.portal.PortletTag) _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.get(com.fulong.taglib.portal.PortletTag.class);
    _jspx_th_fulong_005fportlet_005f0.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fportlet_005f0.setParent(null);
    // /login.jsp(23,28) name = id type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_fulong_005fportlet_005f0.setId("pt0421");
    // /login.jsp(23,28) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_fulong_005fportlet_005f0.setType("textinput");
    int _jspx_eval_fulong_005fportlet_005f0 = _jspx_th_fulong_005fportlet_005f0.doStartTag();
    if (_jspx_eval_fulong_005fportlet_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fportlet_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fportlet_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fportlet_005f0.doInitBody();
      }
      do {
        if (_jspx_meth_fulong_005fpreference_005f0(_jspx_th_fulong_005fportlet_005f0, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f1(_jspx_th_fulong_005fportlet_005f0, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f2(_jspx_th_fulong_005fportlet_005f0, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f3(_jspx_th_fulong_005fportlet_005f0, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f4(_jspx_th_fulong_005fportlet_005f0, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f5(_jspx_th_fulong_005fportlet_005f0, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f6(_jspx_th_fulong_005fportlet_005f0, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f7(_jspx_th_fulong_005fportlet_005f0, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f8(_jspx_th_fulong_005fportlet_005f0, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f9(_jspx_th_fulong_005fportlet_005f0, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f10(_jspx_th_fulong_005fportlet_005f0, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f11(_jspx_th_fulong_005fportlet_005f0, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f12(_jspx_th_fulong_005fportlet_005f0, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f13(_jspx_th_fulong_005fportlet_005f0, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fportlet_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fportlet_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fportlet_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.reuse(_jspx_th_fulong_005fportlet_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.reuse(_jspx_th_fulong_005fportlet_005f0);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f0 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f0.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f0);
    int _jspx_eval_fulong_005fpreference_005f0 = _jspx_th_fulong_005fpreference_005f0.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f0(_jspx_th_fulong_005fpreference_005f0, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f0(_jspx_th_fulong_005fpreference_005f0, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f0);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f0 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f0.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f0);
    int _jspx_eval_fulong_005fname_005f0 = _jspx_th_fulong_005fname_005f0.doStartTag();
    if (_jspx_eval_fulong_005fname_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f0.doInitBody();
      }
      do {
        out.write("password");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f0);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f0 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f0.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f0);
    int _jspx_eval_fulong_005fvalue_005f0 = _jspx_th_fulong_005fvalue_005f0.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f0.doInitBody();
      }
      do {
        out.write("false");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f0);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f1 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f1.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f0);
    int _jspx_eval_fulong_005fpreference_005f1 = _jspx_th_fulong_005fpreference_005f1.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f1(_jspx_th_fulong_005fpreference_005f1, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f1(_jspx_th_fulong_005fpreference_005f1, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f1);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f1 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f1.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f1);
    int _jspx_eval_fulong_005fname_005f1 = _jspx_th_fulong_005fname_005f1.doStartTag();
    if (_jspx_eval_fulong_005fname_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f1.doInitBody();
      }
      do {
        out.write("propertyWhich");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f1);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f1 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f1.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f1);
    int _jspx_eval_fulong_005fvalue_005f1 = _jspx_th_fulong_005fvalue_005f1.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f1.doInitBody();
      }
      do {
        out.write('1');
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f1);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f2 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f2.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f0);
    int _jspx_eval_fulong_005fpreference_005f2 = _jspx_th_fulong_005fpreference_005f2.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f2(_jspx_th_fulong_005fpreference_005f2, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f2(_jspx_th_fulong_005fpreference_005f2, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f2);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f2 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f2.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f2);
    int _jspx_eval_fulong_005fname_005f2 = _jspx_th_fulong_005fname_005f2.doStartTag();
    if (_jspx_eval_fulong_005fname_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f2.doInitBody();
      }
      do {
        out.write("category-id");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f2);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f2 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f2.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f2);
    int _jspx_eval_fulong_005fvalue_005f2 = _jspx_th_fulong_005fvalue_005f2.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f2.doInitBody();
      }
      do {
        out.write("principal-scheme");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f2);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f3(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f3 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f3.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f0);
    int _jspx_eval_fulong_005fpreference_005f3 = _jspx_th_fulong_005fpreference_005f3.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f3(_jspx_th_fulong_005fpreference_005f3, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f3(_jspx_th_fulong_005fpreference_005f3, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f3.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f3);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f3(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f3 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f3.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f3);
    int _jspx_eval_fulong_005fname_005f3 = _jspx_th_fulong_005fname_005f3.doStartTag();
    if (_jspx_eval_fulong_005fname_005f3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f3 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f3.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f3.doInitBody();
      }
      do {
        out.write("maxLength");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f3.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f3 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f3);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f3(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f3 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f3.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f3);
    int _jspx_eval_fulong_005fvalue_005f3 = _jspx_th_fulong_005fvalue_005f3.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f3);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f4(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f4 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f4.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f0);
    int _jspx_eval_fulong_005fpreference_005f4 = _jspx_th_fulong_005fpreference_005f4.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f4(_jspx_th_fulong_005fpreference_005f4, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f4(_jspx_th_fulong_005fpreference_005f4, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f4.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f4);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f4(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f4, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f4 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f4.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f4);
    int _jspx_eval_fulong_005fname_005f4 = _jspx_th_fulong_005fname_005f4.doStartTag();
    if (_jspx_eval_fulong_005fname_005f4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f4 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f4.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f4.doInitBody();
      }
      do {
        out.write("value");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f4.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f4 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f4);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f4(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f4, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f4 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f4.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f4);
    int _jspx_eval_fulong_005fvalue_005f4 = _jspx_th_fulong_005fvalue_005f4.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f4);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f5(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f5 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f5.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f0);
    int _jspx_eval_fulong_005fpreference_005f5 = _jspx_th_fulong_005fpreference_005f5.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f5(_jspx_th_fulong_005fpreference_005f5, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f5(_jspx_th_fulong_005fpreference_005f5, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f5.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f5);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f5);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f5(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f5 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f5.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f5);
    int _jspx_eval_fulong_005fname_005f5 = _jspx_th_fulong_005fname_005f5.doStartTag();
    if (_jspx_eval_fulong_005fname_005f5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f5 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f5.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f5.doInitBody();
      }
      do {
        out.write("tabindex");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f5.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f5 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f5);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f5);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f5(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f5 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f5.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f5);
    int _jspx_eval_fulong_005fvalue_005f5 = _jspx_th_fulong_005fvalue_005f5.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f5);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f5);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f6(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f6 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f6.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f0);
    int _jspx_eval_fulong_005fpreference_005f6 = _jspx_th_fulong_005fpreference_005f6.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f6 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f6(_jspx_th_fulong_005fpreference_005f6, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f6(_jspx_th_fulong_005fpreference_005f6, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f6.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f6);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f6);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f6(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f6, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f6 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f6.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f6);
    int _jspx_eval_fulong_005fname_005f6 = _jspx_th_fulong_005fname_005f6.doStartTag();
    if (_jspx_eval_fulong_005fname_005f6 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f6 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f6.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f6.doInitBody();
      }
      do {
        out.write("vcard");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f6.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f6 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f6);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f6);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f6(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f6, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f6 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f6.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f6);
    int _jspx_eval_fulong_005fvalue_005f6 = _jspx_th_fulong_005fvalue_005f6.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f6);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f6);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f7(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f7 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f7.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f0);
    int _jspx_eval_fulong_005fpreference_005f7 = _jspx_th_fulong_005fpreference_005f7.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f7 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f7(_jspx_th_fulong_005fpreference_005f7, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f7(_jspx_th_fulong_005fpreference_005f7, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f7.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f7);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f7);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f7(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f7, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f7 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f7.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f7);
    int _jspx_eval_fulong_005fname_005f7 = _jspx_th_fulong_005fname_005f7.doStartTag();
    if (_jspx_eval_fulong_005fname_005f7 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f7 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f7.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f7.doInitBody();
      }
      do {
        out.write("disabled");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f7.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f7 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f7);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f7);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f7(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f7, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f7 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f7.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f7);
    int _jspx_eval_fulong_005fvalue_005f7 = _jspx_th_fulong_005fvalue_005f7.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f7);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f7);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f8(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f8 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f8.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f0);
    int _jspx_eval_fulong_005fpreference_005f8 = _jspx_th_fulong_005fpreference_005f8.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f8 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f8(_jspx_th_fulong_005fpreference_005f8, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f8(_jspx_th_fulong_005fpreference_005f8, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f8.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f8);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f8);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f8(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f8, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f8 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f8.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f8);
    int _jspx_eval_fulong_005fname_005f8 = _jspx_th_fulong_005fname_005f8.doStartTag();
    if (_jspx_eval_fulong_005fname_005f8 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f8 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f8.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f8.doInitBody();
      }
      do {
        out.write("echo");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f8.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f8 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f8);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f8);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f8(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f8, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f8 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f8.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f8);
    int _jspx_eval_fulong_005fvalue_005f8 = _jspx_th_fulong_005fvalue_005f8.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f8 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f8 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f8.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f8.doInitBody();
      }
      do {
        out.write("nodeEcho");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f8.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f8 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f8);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f8);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f9(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f9 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f9.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f0);
    int _jspx_eval_fulong_005fpreference_005f9 = _jspx_th_fulong_005fpreference_005f9.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f9 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f9(_jspx_th_fulong_005fpreference_005f9, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f9(_jspx_th_fulong_005fpreference_005f9, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f9.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f9);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f9);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f9(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f9, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f9 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f9.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f9);
    int _jspx_eval_fulong_005fname_005f9 = _jspx_th_fulong_005fname_005f9.doStartTag();
    if (_jspx_eval_fulong_005fname_005f9 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f9 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f9.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f9.doInitBody();
      }
      do {
        out.write("contentType");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f9.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f9 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f9);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f9);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f9(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f9, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f9 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f9.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f9);
    int _jspx_eval_fulong_005fvalue_005f9 = _jspx_th_fulong_005fvalue_005f9.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f9 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f9 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f9.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f9.doInitBody();
      }
      do {
        out.write("default");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f9.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f9 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f9);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f9);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f10(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f10 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f10.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f0);
    int _jspx_eval_fulong_005fpreference_005f10 = _jspx_th_fulong_005fpreference_005f10.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f10 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f10(_jspx_th_fulong_005fpreference_005f10, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f10(_jspx_th_fulong_005fpreference_005f10, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f10.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f10);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f10);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f10(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f10, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f10 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f10.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f10);
    int _jspx_eval_fulong_005fname_005f10 = _jspx_th_fulong_005fname_005f10.doStartTag();
    if (_jspx_eval_fulong_005fname_005f10 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f10 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f10.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f10.doInitBody();
      }
      do {
        out.write("propertyId");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f10.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f10 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f10);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f10);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f10(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f10, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f10 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f10.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f10);
    int _jspx_eval_fulong_005fvalue_005f10 = _jspx_th_fulong_005fvalue_005f10.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f10 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f10 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f10.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f10.doInitBody();
      }
      do {
        out.write("user-username");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f10.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f10 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f10);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f10);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f11(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f11 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f11.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f11.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f0);
    int _jspx_eval_fulong_005fpreference_005f11 = _jspx_th_fulong_005fpreference_005f11.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f11 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f11(_jspx_th_fulong_005fpreference_005f11, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f11(_jspx_th_fulong_005fpreference_005f11, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f11.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f11);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f11);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f11(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f11, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f11 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f11.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f11.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f11);
    int _jspx_eval_fulong_005fname_005f11 = _jspx_th_fulong_005fname_005f11.doStartTag();
    if (_jspx_eval_fulong_005fname_005f11 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f11 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f11.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f11.doInitBody();
      }
      do {
        out.write("readonly");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f11.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f11 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f11);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f11);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f11(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f11, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f11 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f11.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f11.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f11);
    int _jspx_eval_fulong_005fvalue_005f11 = _jspx_th_fulong_005fvalue_005f11.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f11);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f11);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f12(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f12 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f12.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f12.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f0);
    int _jspx_eval_fulong_005fpreference_005f12 = _jspx_th_fulong_005fpreference_005f12.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f12 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f12(_jspx_th_fulong_005fpreference_005f12, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f12(_jspx_th_fulong_005fpreference_005f12, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f12.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f12);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f12);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f12(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f12, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f12 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f12.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f12.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f12);
    int _jspx_eval_fulong_005fname_005f12 = _jspx_th_fulong_005fname_005f12.doStartTag();
    if (_jspx_eval_fulong_005fname_005f12 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f12 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f12.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f12.doInitBody();
      }
      do {
        out.write("style");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f12.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f12 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f12);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f12);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f12(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f12, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f12 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f12.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f12.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f12);
    int _jspx_eval_fulong_005fvalue_005f12 = _jspx_th_fulong_005fvalue_005f12.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f12);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f12);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f13(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f13 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f13.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f13.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f0);
    int _jspx_eval_fulong_005fpreference_005f13 = _jspx_th_fulong_005fpreference_005f13.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f13 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f13(_jspx_th_fulong_005fpreference_005f13, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f13(_jspx_th_fulong_005fpreference_005f13, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f13.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f13.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f13);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f13);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f13(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f13, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f13 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f13.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f13.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f13);
    int _jspx_eval_fulong_005fname_005f13 = _jspx_th_fulong_005fname_005f13.doStartTag();
    if (_jspx_eval_fulong_005fname_005f13 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f13 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f13.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f13.doInitBody();
      }
      do {
        out.write("size");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f13.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f13 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f13.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f13);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f13);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f13(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f13, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f13 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f13.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f13.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f13);
    int _jspx_eval_fulong_005fvalue_005f13 = _jspx_th_fulong_005fvalue_005f13.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f13.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f13);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f13);
    return false;
  }

  private boolean _jspx_meth_fulong_005fportlet_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:portlet
    com.fulong.taglib.portal.PortletTag _jspx_th_fulong_005fportlet_005f1 = (com.fulong.taglib.portal.PortletTag) _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.get(com.fulong.taglib.portal.PortletTag.class);
    _jspx_th_fulong_005fportlet_005f1.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fportlet_005f1.setParent(null);
    // /login.jsp(27,16) name = id type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_fulong_005fportlet_005f1.setId("pt1515");
    // /login.jsp(27,16) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_fulong_005fportlet_005f1.setType("passwordinput");
    int _jspx_eval_fulong_005fportlet_005f1 = _jspx_th_fulong_005fportlet_005f1.doStartTag();
    if (_jspx_eval_fulong_005fportlet_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fportlet_005f1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fportlet_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fportlet_005f1.doInitBody();
      }
      do {
        if (_jspx_meth_fulong_005fpreference_005f14(_jspx_th_fulong_005fportlet_005f1, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f15(_jspx_th_fulong_005fportlet_005f1, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f16(_jspx_th_fulong_005fportlet_005f1, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f17(_jspx_th_fulong_005fportlet_005f1, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f18(_jspx_th_fulong_005fportlet_005f1, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f19(_jspx_th_fulong_005fportlet_005f1, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fportlet_005f1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fportlet_005f1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fportlet_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.reuse(_jspx_th_fulong_005fportlet_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.reuse(_jspx_th_fulong_005fportlet_005f1);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f14(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f14 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f14.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f14.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f1);
    int _jspx_eval_fulong_005fpreference_005f14 = _jspx_th_fulong_005fpreference_005f14.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f14 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f14(_jspx_th_fulong_005fpreference_005f14, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f14(_jspx_th_fulong_005fpreference_005f14, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f14.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f14.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f14);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f14);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f14(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f14, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f14 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f14.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f14.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f14);
    int _jspx_eval_fulong_005fname_005f14 = _jspx_th_fulong_005fname_005f14.doStartTag();
    if (_jspx_eval_fulong_005fname_005f14 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f14 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f14.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f14.doInitBody();
      }
      do {
        out.write("maxLength");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f14.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f14 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f14.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f14);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f14);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f14(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f14, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f14 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f14.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f14.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f14);
    int _jspx_eval_fulong_005fvalue_005f14 = _jspx_th_fulong_005fvalue_005f14.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f14.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f14);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f14);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f15(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f15 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f15.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f15.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f1);
    int _jspx_eval_fulong_005fpreference_005f15 = _jspx_th_fulong_005fpreference_005f15.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f15 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f15(_jspx_th_fulong_005fpreference_005f15, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f15(_jspx_th_fulong_005fpreference_005f15, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f15.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f15.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f15);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f15);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f15(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f15, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f15 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f15.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f15.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f15);
    int _jspx_eval_fulong_005fname_005f15 = _jspx_th_fulong_005fname_005f15.doStartTag();
    if (_jspx_eval_fulong_005fname_005f15 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f15 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f15.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f15.doInitBody();
      }
      do {
        out.write("tabindex");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f15.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f15 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f15.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f15);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f15);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f15(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f15, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f15 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f15.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f15.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f15);
    int _jspx_eval_fulong_005fvalue_005f15 = _jspx_th_fulong_005fvalue_005f15.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f15.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f15);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f15);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f16(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f16 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f16.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f16.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f1);
    int _jspx_eval_fulong_005fpreference_005f16 = _jspx_th_fulong_005fpreference_005f16.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f16 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f16(_jspx_th_fulong_005fpreference_005f16, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f16(_jspx_th_fulong_005fpreference_005f16, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f16.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f16.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f16);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f16);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f16(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f16, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f16 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f16.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f16.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f16);
    int _jspx_eval_fulong_005fname_005f16 = _jspx_th_fulong_005fname_005f16.doStartTag();
    if (_jspx_eval_fulong_005fname_005f16 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f16 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f16.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f16.doInitBody();
      }
      do {
        out.write("style");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f16.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f16 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f16.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f16);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f16);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f16(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f16, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f16 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f16.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f16.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f16);
    int _jspx_eval_fulong_005fvalue_005f16 = _jspx_th_fulong_005fvalue_005f16.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f16.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f16);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f16);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f17(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f17 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f17.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f17.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f1);
    int _jspx_eval_fulong_005fpreference_005f17 = _jspx_th_fulong_005fpreference_005f17.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f17 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f17(_jspx_th_fulong_005fpreference_005f17, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f17(_jspx_th_fulong_005fpreference_005f17, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f17.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f17.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f17);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f17);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f17(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f17, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f17 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f17.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f17.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f17);
    int _jspx_eval_fulong_005fname_005f17 = _jspx_th_fulong_005fname_005f17.doStartTag();
    if (_jspx_eval_fulong_005fname_005f17 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f17 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f17.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f17.doInitBody();
      }
      do {
        out.write("size");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f17.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f17 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f17.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f17);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f17);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f17(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f17, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f17 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f17.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f17.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f17);
    int _jspx_eval_fulong_005fvalue_005f17 = _jspx_th_fulong_005fvalue_005f17.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f17.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f17);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f17);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f18(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f18 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f18.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f18.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f1);
    int _jspx_eval_fulong_005fpreference_005f18 = _jspx_th_fulong_005fpreference_005f18.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f18 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f18(_jspx_th_fulong_005fpreference_005f18, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f18(_jspx_th_fulong_005fpreference_005f18, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f18.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f18.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f18);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f18);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f18(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f18, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f18 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f18.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f18.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f18);
    int _jspx_eval_fulong_005fname_005f18 = _jspx_th_fulong_005fname_005f18.doStartTag();
    if (_jspx_eval_fulong_005fname_005f18 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f18 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f18.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f18.doInitBody();
      }
      do {
        out.write("contentType");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f18.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f18 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f18.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f18);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f18);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f18(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f18, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f18 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f18.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f18.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f18);
    int _jspx_eval_fulong_005fvalue_005f18 = _jspx_th_fulong_005fvalue_005f18.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f18 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f18 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f18.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f18.doInitBody();
      }
      do {
        out.write("default");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f18.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f18 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f18.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f18);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f18);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f19(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f19 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f19.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f19.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f1);
    int _jspx_eval_fulong_005fpreference_005f19 = _jspx_th_fulong_005fpreference_005f19.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f19 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f19(_jspx_th_fulong_005fpreference_005f19, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f19(_jspx_th_fulong_005fpreference_005f19, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f19.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f19.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f19);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f19);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f19(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f19, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f19 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f19.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f19.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f19);
    int _jspx_eval_fulong_005fname_005f19 = _jspx_th_fulong_005fname_005f19.doStartTag();
    if (_jspx_eval_fulong_005fname_005f19 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f19 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f19.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f19.doInitBody();
      }
      do {
        out.write("propertyId");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f19.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f19 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f19.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f19);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f19);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f19(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f19, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f19 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f19.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f19.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f19);
    int _jspx_eval_fulong_005fvalue_005f19 = _jspx_th_fulong_005fvalue_005f19.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f19 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f19 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f19.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f19.doInitBody();
      }
      do {
        out.write("user-password");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f19.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f19 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f19.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f19);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f19);
    return false;
  }

  private boolean _jspx_meth_fulong_005fportlet_005f2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:portlet
    com.fulong.taglib.portal.PortletTag _jspx_th_fulong_005fportlet_005f2 = (com.fulong.taglib.portal.PortletTag) _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.get(com.fulong.taglib.portal.PortletTag.class);
    _jspx_th_fulong_005fportlet_005f2.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fportlet_005f2.setParent(null);
    // /login.jsp(35,16) name = id type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_fulong_005fportlet_005f2.setId("pt5437");
    // /login.jsp(35,16) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_fulong_005fportlet_005f2.setType("loginbutton");
    int _jspx_eval_fulong_005fportlet_005f2 = _jspx_th_fulong_005fportlet_005f2.doStartTag();
    if (_jspx_eval_fulong_005fportlet_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fportlet_005f2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fportlet_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fportlet_005f2.doInitBody();
      }
      do {
        if (_jspx_meth_fulong_005fpreference_005f20(_jspx_th_fulong_005fportlet_005f2, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f21(_jspx_th_fulong_005fportlet_005f2, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f22(_jspx_th_fulong_005fportlet_005f2, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f23(_jspx_th_fulong_005fportlet_005f2, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f24(_jspx_th_fulong_005fportlet_005f2, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f25(_jspx_th_fulong_005fportlet_005f2, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f26(_jspx_th_fulong_005fportlet_005f2, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f27(_jspx_th_fulong_005fportlet_005f2, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f28(_jspx_th_fulong_005fportlet_005f2, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f29(_jspx_th_fulong_005fportlet_005f2, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fportlet_005f2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fportlet_005f2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fportlet_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.reuse(_jspx_th_fulong_005fportlet_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.reuse(_jspx_th_fulong_005fportlet_005f2);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f20(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f20 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f20.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f20.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f2);
    int _jspx_eval_fulong_005fpreference_005f20 = _jspx_th_fulong_005fpreference_005f20.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f20 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f20(_jspx_th_fulong_005fpreference_005f20, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f20(_jspx_th_fulong_005fpreference_005f20, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f20.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f20.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f20);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f20);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f20(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f20, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f20 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f20.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f20.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f20);
    int _jspx_eval_fulong_005fname_005f20 = _jspx_th_fulong_005fname_005f20.doStartTag();
    if (_jspx_eval_fulong_005fname_005f20 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f20 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f20.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f20.doInitBody();
      }
      do {
        out.write("imgwidth");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f20.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f20 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f20.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f20);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f20);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f20(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f20, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f20 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f20.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f20.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f20);
    int _jspx_eval_fulong_005fvalue_005f20 = _jspx_th_fulong_005fvalue_005f20.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f20.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f20);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f20);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f21(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f21 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f21.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f21.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f2);
    int _jspx_eval_fulong_005fpreference_005f21 = _jspx_th_fulong_005fpreference_005f21.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f21 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f21(_jspx_th_fulong_005fpreference_005f21, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f21(_jspx_th_fulong_005fpreference_005f21, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f21.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f21.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f21);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f21);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f21(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f21, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f21 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f21.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f21.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f21);
    int _jspx_eval_fulong_005fname_005f21 = _jspx_th_fulong_005fname_005f21.doStartTag();
    if (_jspx_eval_fulong_005fname_005f21 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f21 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f21.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f21.doInitBody();
      }
      do {
        out.write("value");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f21.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f21 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f21.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f21);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f21);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f21(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f21, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f21 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f21.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f21.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f21);
    int _jspx_eval_fulong_005fvalue_005f21 = _jspx_th_fulong_005fvalue_005f21.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f21 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f21 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f21.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f21.doInitBody();
      }
      do {
        out.write('登');
        out.write('录');
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f21.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f21 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f21.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f21);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f21);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f22(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f22 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f22.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f22.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f2);
    int _jspx_eval_fulong_005fpreference_005f22 = _jspx_th_fulong_005fpreference_005f22.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f22 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f22(_jspx_th_fulong_005fpreference_005f22, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f22(_jspx_th_fulong_005fpreference_005f22, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f22.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f22.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f22);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f22);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f22(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f22, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f22 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f22.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f22.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f22);
    int _jspx_eval_fulong_005fname_005f22 = _jspx_th_fulong_005fname_005f22.doStartTag();
    if (_jspx_eval_fulong_005fname_005f22 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f22 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f22.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f22.doInitBody();
      }
      do {
        out.write("channnel");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f22.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f22 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f22.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f22);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f22);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f22(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f22, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f22 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f22.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f22.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f22);
    int _jspx_eval_fulong_005fvalue_005f22 = _jspx_th_fulong_005fvalue_005f22.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f22.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f22);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f22);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f23(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f23 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f23.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f23.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f2);
    int _jspx_eval_fulong_005fpreference_005f23 = _jspx_th_fulong_005fpreference_005f23.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f23 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f23(_jspx_th_fulong_005fpreference_005f23, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f23(_jspx_th_fulong_005fpreference_005f23, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f23.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f23.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f23);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f23);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f23(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f23, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f23 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f23.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f23.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f23);
    int _jspx_eval_fulong_005fname_005f23 = _jspx_th_fulong_005fname_005f23.doStartTag();
    if (_jspx_eval_fulong_005fname_005f23 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f23 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f23.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f23.doInitBody();
      }
      do {
        out.write("tabindex");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f23.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f23 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f23.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f23);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f23);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f23(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f23, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f23 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f23.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f23.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f23);
    int _jspx_eval_fulong_005fvalue_005f23 = _jspx_th_fulong_005fvalue_005f23.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f23.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f23);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f23);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f24(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f24 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f24.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f24.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f2);
    int _jspx_eval_fulong_005fpreference_005f24 = _jspx_th_fulong_005fpreference_005f24.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f24 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f24(_jspx_th_fulong_005fpreference_005f24, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f24(_jspx_th_fulong_005fpreference_005f24, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f24.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f24.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f24);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f24);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f24(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f24, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f24 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f24.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f24.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f24);
    int _jspx_eval_fulong_005fname_005f24 = _jspx_th_fulong_005fname_005f24.doStartTag();
    if (_jspx_eval_fulong_005fname_005f24 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f24 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f24.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f24.doInitBody();
      }
      do {
        out.write("imgsrc");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f24.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f24 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f24.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f24);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f24);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f24(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f24, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f24 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f24.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f24.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f24);
    int _jspx_eval_fulong_005fvalue_005f24 = _jspx_th_fulong_005fvalue_005f24.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f24.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f24);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f24);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f25(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f25 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f25.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f25.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f2);
    int _jspx_eval_fulong_005fpreference_005f25 = _jspx_th_fulong_005fpreference_005f25.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f25 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f25(_jspx_th_fulong_005fpreference_005f25, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f25(_jspx_th_fulong_005fpreference_005f25, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f25.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f25.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f25);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f25);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f25(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f25, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f25 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f25.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f25.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f25);
    int _jspx_eval_fulong_005fname_005f25 = _jspx_th_fulong_005fname_005f25.doStartTag();
    if (_jspx_eval_fulong_005fname_005f25 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f25 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f25.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f25.doInitBody();
      }
      do {
        out.write("style");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f25.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f25 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f25.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f25);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f25);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f25(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f25, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f25 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f25.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f25.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f25);
    int _jspx_eval_fulong_005fvalue_005f25 = _jspx_th_fulong_005fvalue_005f25.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f25.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f25);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f25);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f26(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f26 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f26.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f26.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f2);
    int _jspx_eval_fulong_005fpreference_005f26 = _jspx_th_fulong_005fpreference_005f26.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f26 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f26(_jspx_th_fulong_005fpreference_005f26, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f26(_jspx_th_fulong_005fpreference_005f26, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f26.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f26.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f26);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f26);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f26(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f26, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f26 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f26.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f26.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f26);
    int _jspx_eval_fulong_005fname_005f26 = _jspx_th_fulong_005fname_005f26.doStartTag();
    if (_jspx_eval_fulong_005fname_005f26 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f26 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f26.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f26.doInitBody();
      }
      do {
        out.write("size");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f26.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f26 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f26.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f26);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f26);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f26(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f26, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f26 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f26.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f26.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f26);
    int _jspx_eval_fulong_005fvalue_005f26 = _jspx_th_fulong_005fvalue_005f26.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f26.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f26);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f26);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f27(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f27 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f27.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f27.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f2);
    int _jspx_eval_fulong_005fpreference_005f27 = _jspx_th_fulong_005fpreference_005f27.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f27 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f27(_jspx_th_fulong_005fpreference_005f27, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f27(_jspx_th_fulong_005fpreference_005f27, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f27.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f27.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f27);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f27);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f27(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f27, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f27 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f27.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f27.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f27);
    int _jspx_eval_fulong_005fname_005f27 = _jspx_th_fulong_005fname_005f27.doStartTag();
    if (_jspx_eval_fulong_005fname_005f27 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f27 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f27.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f27.doInitBody();
      }
      do {
        out.write("imgheight");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f27.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f27 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f27.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f27);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f27);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f27(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f27, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f27 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f27.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f27.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f27);
    int _jspx_eval_fulong_005fvalue_005f27 = _jspx_th_fulong_005fvalue_005f27.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f27.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f27);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f27);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f28(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f28 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f28.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f28.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f2);
    int _jspx_eval_fulong_005fpreference_005f28 = _jspx_th_fulong_005fpreference_005f28.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f28 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f28(_jspx_th_fulong_005fpreference_005f28, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f28(_jspx_th_fulong_005fpreference_005f28, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f28.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f28.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f28);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f28);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f28(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f28, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f28 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f28.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f28.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f28);
    int _jspx_eval_fulong_005fname_005f28 = _jspx_th_fulong_005fname_005f28.doStartTag();
    if (_jspx_eval_fulong_005fname_005f28 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f28 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f28.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f28.doInitBody();
      }
      do {
        out.write("channel");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f28.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f28 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f28.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f28);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f28);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f28(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f28, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f28 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f28.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f28.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f28);
    int _jspx_eval_fulong_005fvalue_005f28 = _jspx_th_fulong_005fvalue_005f28.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f28.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f28);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f28);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f29(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f29 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f29.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f29.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f2);
    int _jspx_eval_fulong_005fpreference_005f29 = _jspx_th_fulong_005fpreference_005f29.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f29 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f29(_jspx_th_fulong_005fpreference_005f29, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f29(_jspx_th_fulong_005fpreference_005f29, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f29.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f29.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f29);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f29);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f29(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f29, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f29 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f29.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f29.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f29);
    int _jspx_eval_fulong_005fname_005f29 = _jspx_th_fulong_005fname_005f29.doStartTag();
    if (_jspx_eval_fulong_005fname_005f29 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f29 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f29.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f29.doInitBody();
      }
      do {
        out.write("imgstyle");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f29.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f29 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f29.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f29);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f29);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f29(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f29, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f29 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f29.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f29.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f29);
    int _jspx_eval_fulong_005fvalue_005f29 = _jspx_th_fulong_005fvalue_005f29.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f29.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f29);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f29);
    return false;
  }
}
