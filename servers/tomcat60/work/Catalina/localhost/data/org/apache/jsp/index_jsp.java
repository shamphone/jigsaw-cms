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
    _005fjspx_005ftagPool_005fsite_005fhtml = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fsite_005ftitle2_0026_005fformat_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fsite_005fscript_0026_005fportlets_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005ffulong_005fpreference = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005ffulong_005fname = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005ffulong_005fvalue = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fsite_005fhtml.release();
    _005fjspx_005ftagPool_005fsite_005ftitle2_0026_005fformat_005fnobody.release();
    _005fjspx_005ftagPool_005fsite_005fscript_0026_005fportlets_005fnobody.release();
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
      			"/error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      if (_jspx_meth_site_005fhtml_005f0(_jspx_page_context))
        return;
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
        out.write("  <head>\n");
        out.write("    ");
        if (_jspx_meth_site_005ftitle2_005f0(_jspx_th_site_005fhtml_005f0, _jspx_page_context))
          return true;
        out.write("\n");
        out.write("    <link href=\"/data/style/style_0831.css\" type=\"text/css\" rel=\"stylesheet\" />\n");
        out.write("    <meta content=\"text/html; charset=utf-8\" http-equiv=\"content-type\" />\n");
        out.write("    <meta content=\"Beijing Zhongke Fulong Computer Corp.\" name=\"author\" />\n");
        out.write("  ");
        if (_jspx_meth_site_005fscript_005f0(_jspx_th_site_005fhtml_005f0, _jspx_page_context))
          return true;
        out.write("</head>\n");
        out.write("  <body>\n");
        out.write("    ");
        if (_jspx_meth_fulong_005fportlet_005f0(_jspx_th_site_005fhtml_005f0, _jspx_page_context))
          return true;
        out.write("\n");
        out.write("    <table style=\"margin-top: 80px\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" border=\"0\">\n");
        out.write("      <tbody>\n");
        out.write("        <tr>\n");
        out.write("          <td valign=\"top\" align=\"center\" height=\"93\"><img height=\"58\" alt=\"\" width=\"371\" src=\"/data/images/logo.jpg\" /></td>\n");
        out.write("        </tr>\n");
        out.write("      </tbody>\n");
        out.write("    </table>\n");
        out.write("    <form action=\"#\" method=\"post\" onsubmit=\"return Validator.ValidateForm(this);\" definitionname=\"企业信息\" definition=\"2560565990901\">\n");
        out.write("      <table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" border=\"0\">\n");
        out.write("        <tbody>\n");
        out.write("          <tr>\n");
        out.write("            <td>\n");
        out.write("            <table cellspacing=\"0\" cellpadding=\"0\" width=\"540\" align=\"center\" border=\"0\">\n");
        out.write("              <tbody>\n");
        out.write("                <tr>\n");
        out.write("                  <td><span class=\"nav\"><span class=\"nav10\"><a href=\"/data/companyFore/channel001.jsp\">企业</a><font color=\"#000000\" size=\"2\">　 </font></span><a class=\"nav\" href=\"/data/brandFore/channel001.jsp\">品牌</a>　 <a class=\"nav\" href=\"/data/productFore/productPF.jsp\">产品</a>　 <a class=\"nav\" href=\"/data/supDemFore/channel006.jsp\">供求</a>　 <a class=\"nav\" href=\"/data/jobFore/jobJF.jsp\">职位</a></span></td>\n");
        out.write("                </tr>\n");
        out.write("              </tbody>\n");
        out.write("            </table>\n");
        out.write("            </td>\n");
        out.write("          </tr>\n");
        out.write("          <tr>\n");
        out.write("            <td align=\"center\"><input class=\"kw2\" id=\"keyword\" maxlength=\"30\" size=\"62\" onblur=\"Validator.ValidateComponent(this, this.name)\" name=\"com_name\" type=\"text\" /></td>\n");
        out.write("          </tr>\n");
        out.write("        </tbody>\n");
        out.write("      </table>\n");
        out.write("      <table style=\"margin-top: 37px\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" border=\"0\">\n");
        out.write("        <tbody>\n");
        out.write("          <tr>\n");
        out.write("            <td align=\"center\">");
        if (_jspx_meth_fulong_005fportlet_005f1(_jspx_th_site_005fhtml_005f0, _jspx_page_context))
          return true;
        out.write("　</td>\n");
        out.write("          </tr>\n");
        out.write("        </tbody>\n");
        out.write("      </table>\n");
        out.write("    </form>\n");
        out.write("    ");
        if (_jspx_meth_fulong_005fportlet_005f2(_jspx_th_site_005fhtml_005f0, _jspx_page_context))
          return true;
        out.write("<br />\n");
        out.write("  </body>\n");
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
    // /index.jsp(11,4) name = format type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
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
    // /index.jsp(15,2) name = portlets type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_site_005fscript_005f0.setPortlets("constant,page_clip,searchbutton");
    int _jspx_eval_site_005fscript_005f0 = _jspx_th_site_005fscript_005f0.doStartTag();
    if (_jspx_th_site_005fscript_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fsite_005fscript_0026_005fportlets_005fnobody.reuse(_jspx_th_site_005fscript_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fsite_005fscript_0026_005fportlets_005fnobody.reuse(_jspx_th_site_005fscript_005f0);
    return false;
  }

  private boolean _jspx_meth_fulong_005fportlet_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_site_005fhtml_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:portlet
    com.fulong.taglib.portal.PortletTag _jspx_th_fulong_005fportlet_005f0 = (com.fulong.taglib.portal.PortletTag) _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.get(com.fulong.taglib.portal.PortletTag.class);
    _jspx_th_fulong_005fportlet_005f0.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fportlet_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_site_005fhtml_005f0);
    // /index.jsp(17,4) name = id type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_fulong_005fportlet_005f0.setId("pt646375");
    // /index.jsp(17,4) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_fulong_005fportlet_005f0.setType("page_clip");
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
        out.write("source");
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
        out.write("/segment/header.jspf");
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
        out.write("clip-paths");
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

  private boolean _jspx_meth_fulong_005fportlet_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_site_005fhtml_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:portlet
    com.fulong.taglib.portal.PortletTag _jspx_th_fulong_005fportlet_005f1 = (com.fulong.taglib.portal.PortletTag) _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.get(com.fulong.taglib.portal.PortletTag.class);
    _jspx_th_fulong_005fportlet_005f1.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fportlet_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_site_005fhtml_005f0);
    // /index.jsp(47,31) name = id type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_fulong_005fportlet_005f1.setId("pt277562");
    // /index.jsp(47,31) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_fulong_005fportlet_005f1.setType("searchbutton");
    int _jspx_eval_fulong_005fportlet_005f1 = _jspx_th_fulong_005fportlet_005f1.doStartTag();
    if (_jspx_eval_fulong_005fportlet_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fportlet_005f1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fportlet_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fportlet_005f1.doInitBody();
      }
      do {
        if (_jspx_meth_fulong_005fpreference_005f2(_jspx_th_fulong_005fportlet_005f1, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f3(_jspx_th_fulong_005fportlet_005f1, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f4(_jspx_th_fulong_005fportlet_005f1, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f5(_jspx_th_fulong_005fportlet_005f1, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f6(_jspx_th_fulong_005fportlet_005f1, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f7(_jspx_th_fulong_005fportlet_005f1, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f8(_jspx_th_fulong_005fportlet_005f1, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f9(_jspx_th_fulong_005fportlet_005f1, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f10(_jspx_th_fulong_005fportlet_005f1, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f11(_jspx_th_fulong_005fportlet_005f1, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f12(_jspx_th_fulong_005fportlet_005f1, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f13(_jspx_th_fulong_005fportlet_005f1, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f14(_jspx_th_fulong_005fportlet_005f1, _jspx_page_context))
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

  private boolean _jspx_meth_fulong_005fpreference_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f2 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f2.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f1);
    int _jspx_eval_fulong_005fpreference_005f2 = _jspx_th_fulong_005fpreference_005f2.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f2(_jspx_th_fulong_005fpreference_005f2, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f1(_jspx_th_fulong_005fpreference_005f2, _jspx_page_context))
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
        out.write("imgwidth");
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

  private boolean _jspx_meth_fulong_005fvalue_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f1 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f1.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f2);
    int _jspx_eval_fulong_005fvalue_005f1 = _jspx_th_fulong_005fvalue_005f1.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f1.doInitBody();
      }
      do {
        out.write('1');
        out.write('0');
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

  private boolean _jspx_meth_fulong_005fpreference_005f3(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f3 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f3.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f1);
    int _jspx_eval_fulong_005fpreference_005f3 = _jspx_th_fulong_005fpreference_005f3.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f3(_jspx_th_fulong_005fpreference_005f3, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f2(_jspx_th_fulong_005fpreference_005f3, _jspx_page_context))
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
        out.write("imgstyle");
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

  private boolean _jspx_meth_fulong_005fvalue_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f2 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f2.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f3);
    int _jspx_eval_fulong_005fvalue_005f2 = _jspx_th_fulong_005fvalue_005f2.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f2);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f4(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f4 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f4.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f1);
    int _jspx_eval_fulong_005fpreference_005f4 = _jspx_th_fulong_005fpreference_005f4.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f4(_jspx_th_fulong_005fpreference_005f4, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f3(_jspx_th_fulong_005fpreference_005f4, _jspx_page_context))
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
        out.write("imgheight");
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

  private boolean _jspx_meth_fulong_005fvalue_005f3(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f4, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f3 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f3.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f4);
    int _jspx_eval_fulong_005fvalue_005f3 = _jspx_th_fulong_005fvalue_005f3.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f3 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f3.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f3.doInitBody();
      }
      do {
        out.write('3');
        out.write('3');
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f3.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f3 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f3);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f5(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f5 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f5.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f1);
    int _jspx_eval_fulong_005fpreference_005f5 = _jspx_th_fulong_005fpreference_005f5.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f5(_jspx_th_fulong_005fpreference_005f5, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f4(_jspx_th_fulong_005fpreference_005f5, _jspx_page_context))
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

  private boolean _jspx_meth_fulong_005fvalue_005f4(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f4 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f4.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f5);
    int _jspx_eval_fulong_005fvalue_005f4 = _jspx_th_fulong_005fvalue_005f4.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f4);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f6(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f6 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f6.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f1);
    int _jspx_eval_fulong_005fpreference_005f6 = _jspx_th_fulong_005fpreference_005f6.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f6 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f6(_jspx_th_fulong_005fpreference_005f6, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f5(_jspx_th_fulong_005fpreference_005f6, _jspx_page_context))
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
        out.write("size");
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

  private boolean _jspx_meth_fulong_005fvalue_005f5(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f6, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f5 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f5.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f6);
    int _jspx_eval_fulong_005fvalue_005f5 = _jspx_th_fulong_005fvalue_005f5.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f5);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f5);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f7(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f7 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f7.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f1);
    int _jspx_eval_fulong_005fpreference_005f7 = _jspx_th_fulong_005fpreference_005f7.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f7 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f7(_jspx_th_fulong_005fpreference_005f7, _jspx_page_context))
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
        out.write("conditions");
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

  private boolean _jspx_meth_fulong_005fpreference_005f8(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f8 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f8.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f1);
    int _jspx_eval_fulong_005fpreference_005f8 = _jspx_th_fulong_005fpreference_005f8.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f8 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f8(_jspx_th_fulong_005fpreference_005f8, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f6(_jspx_th_fulong_005fpreference_005f8, _jspx_page_context))
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
        out.write("category");
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

  private boolean _jspx_meth_fulong_005fvalue_005f6(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f8, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f6 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f6.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f8);
    int _jspx_eval_fulong_005fvalue_005f6 = _jspx_th_fulong_005fvalue_005f6.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f6 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f6 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f6.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f6.doInitBody();
      }
      do {
        out.write("2560565990901");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f6.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f6 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f6);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f6);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f9(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f9 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f9.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f1);
    int _jspx_eval_fulong_005fpreference_005f9 = _jspx_th_fulong_005fpreference_005f9.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f9 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f9(_jspx_th_fulong_005fpreference_005f9, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f7(_jspx_th_fulong_005fpreference_005f9, _jspx_page_context))
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
        out.write("style");
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

  private boolean _jspx_meth_fulong_005fvalue_005f7(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f9, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f7 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f7.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f9);
    int _jspx_eval_fulong_005fvalue_005f7 = _jspx_th_fulong_005fvalue_005f7.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f7);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f7);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f10(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f10 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f10.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f1);
    int _jspx_eval_fulong_005fpreference_005f10 = _jspx_th_fulong_005fpreference_005f10.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f10 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f10(_jspx_th_fulong_005fpreference_005f10, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f8(_jspx_th_fulong_005fpreference_005f10, _jspx_page_context))
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
        out.write("imgsrc");
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

  private boolean _jspx_meth_fulong_005fvalue_005f8(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f10, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f8 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f8.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f10);
    int _jspx_eval_fulong_005fvalue_005f8 = _jspx_th_fulong_005fvalue_005f8.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f8 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f8 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f8.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f8.doInitBody();
      }
      do {
        out.write("/data/images/index_01.gif");
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

  private boolean _jspx_meth_fulong_005fpreference_005f11(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f11 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f11.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f11.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f1);
    int _jspx_eval_fulong_005fpreference_005f11 = _jspx_th_fulong_005fpreference_005f11.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f11 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f11(_jspx_th_fulong_005fpreference_005f11, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f9(_jspx_th_fulong_005fpreference_005f11, _jspx_page_context))
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
        out.write("value");
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

  private boolean _jspx_meth_fulong_005fvalue_005f9(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f11, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f9 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f9.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f11);
    int _jspx_eval_fulong_005fvalue_005f9 = _jspx_th_fulong_005fvalue_005f9.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f9 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f9 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f9.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f9.doInitBody();
      }
      do {
        out.write('搜');
        out.write('索');
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

  private boolean _jspx_meth_fulong_005fpreference_005f12(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f12 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f12.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f12.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f1);
    int _jspx_eval_fulong_005fpreference_005f12 = _jspx_th_fulong_005fpreference_005f12.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f12 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f12(_jspx_th_fulong_005fpreference_005f12, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f10(_jspx_th_fulong_005fpreference_005f12, _jspx_page_context))
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
        out.write("channel");
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

  private boolean _jspx_meth_fulong_005fvalue_005f10(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f12, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f10 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f10.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f12);
    int _jspx_eval_fulong_005fvalue_005f10 = _jspx_th_fulong_005fvalue_005f10.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f10 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f10 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f10.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f10.doInitBody();
      }
      do {
        out.write("/data/companyFore/company_list.jsp");
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

  private boolean _jspx_meth_fulong_005fpreference_005f13(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f13 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f13.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f13.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f1);
    int _jspx_eval_fulong_005fpreference_005f13 = _jspx_th_fulong_005fpreference_005f13.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f13 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f13(_jspx_th_fulong_005fpreference_005f13, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f11(_jspx_th_fulong_005fpreference_005f13, _jspx_page_context))
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
        out.write("forwardSelf");
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

  private boolean _jspx_meth_fulong_005fvalue_005f11(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f13, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f11 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f11.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f11.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f13);
    int _jspx_eval_fulong_005fvalue_005f11 = _jspx_th_fulong_005fvalue_005f11.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f11 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f11 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f11.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f11.doInitBody();
      }
      do {
        out.write("false");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f11.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f11 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f11);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f11);
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
        if (_jspx_meth_fulong_005fvalue_005f12(_jspx_th_fulong_005fpreference_005f14, _jspx_page_context))
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
        out.write("channnel");
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

  private boolean _jspx_meth_fulong_005fvalue_005f12(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f14, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f12 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f12.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f12.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f14);
    int _jspx_eval_fulong_005fvalue_005f12 = _jspx_th_fulong_005fvalue_005f12.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f12);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f12);
    return false;
  }

  private boolean _jspx_meth_fulong_005fportlet_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_site_005fhtml_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:portlet
    com.fulong.taglib.portal.PortletTag _jspx_th_fulong_005fportlet_005f2 = (com.fulong.taglib.portal.PortletTag) _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.get(com.fulong.taglib.portal.PortletTag.class);
    _jspx_th_fulong_005fportlet_005f2.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fportlet_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_site_005fhtml_005f0);
    // /index.jsp(52,4) name = id type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_fulong_005fportlet_005f2.setId("pt081750");
    // /index.jsp(52,4) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_fulong_005fportlet_005f2.setType("page_clip");
    int _jspx_eval_fulong_005fportlet_005f2 = _jspx_th_fulong_005fportlet_005f2.doStartTag();
    if (_jspx_eval_fulong_005fportlet_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fportlet_005f2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fportlet_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fportlet_005f2.doInitBody();
      }
      do {
        if (_jspx_meth_fulong_005fpreference_005f15(_jspx_th_fulong_005fportlet_005f2, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f16(_jspx_th_fulong_005fportlet_005f2, _jspx_page_context))
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

  private boolean _jspx_meth_fulong_005fpreference_005f15(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f15 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f15.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f15.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f2);
    int _jspx_eval_fulong_005fpreference_005f15 = _jspx_th_fulong_005fpreference_005f15.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f15 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f15(_jspx_th_fulong_005fpreference_005f15, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f13(_jspx_th_fulong_005fpreference_005f15, _jspx_page_context))
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
        out.write("source");
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

  private boolean _jspx_meth_fulong_005fvalue_005f13(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f15, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f13 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f13.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f13.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f15);
    int _jspx_eval_fulong_005fvalue_005f13 = _jspx_th_fulong_005fvalue_005f13.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f13 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f13 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f13.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f13.doInitBody();
      }
      do {
        out.write("/segment/banquan.jspf");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f13.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f13 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f13.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f13);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f13);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f16(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f16 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f16.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f16.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f2);
    int _jspx_eval_fulong_005fpreference_005f16 = _jspx_th_fulong_005fpreference_005f16.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f16 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f16(_jspx_th_fulong_005fpreference_005f16, _jspx_page_context))
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
        out.write("clip-paths");
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
}
