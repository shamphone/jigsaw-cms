package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class bdyjBjyj_jsp_bak_bak extends org.apache.jasper.runtime.HttpJspBase
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

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fsite_005fchannel;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fsite_005ftitle_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005ffulong_005fpreference;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005ffulong_005fname;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005ffulong_005fvalue;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fsite_005fchannel = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fsite_005ftitle_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005ffulong_005fpreference = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005ffulong_005fname = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005ffulong_005fvalue = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fsite_005fchannel.release();
    _005fjspx_005ftagPool_005fsite_005ftitle_005fnobody.release();
    _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.release();
    _005fjspx_005ftagPool_005ffulong_005fpreference.release();
    _005fjspx_005ftagPool_005ffulong_005fname.release();
    _005fjspx_005ftagPool_005ffulong_005fvalue.release();
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.release();
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

      if (_jspx_meth_site_005fchannel_005f0(_jspx_page_context))
        return;
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

  private boolean _jspx_meth_site_005fchannel_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  site:channel
    com.fulong.taglib.site.ChannelTag _jspx_th_site_005fchannel_005f0 = (com.fulong.taglib.site.ChannelTag) _005fjspx_005ftagPool_005fsite_005fchannel.get(com.fulong.taglib.site.ChannelTag.class);
    _jspx_th_site_005fchannel_005f0.setPageContext(_jspx_page_context);
    _jspx_th_site_005fchannel_005f0.setParent(null);
    int _jspx_eval_site_005fchannel_005f0 = _jspx_th_site_005fchannel_005f0.doStartTag();
    if (_jspx_eval_site_005fchannel_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_site_005fchannel_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_site_005fchannel_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_site_005fchannel_005f0.doInitBody();
      }
      do {
        out.write("<head><title>");
        if (_jspx_meth_site_005ftitle_005f0(_jspx_th_site_005fchannel_005f0, _jspx_page_context))
          return true;
        out.write("</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><meta http-equiv=\"Author\" content=\"Beijing Zhongke Fulong Computer Corp.\" /><script type=\"text/javascript\" language=\"javascript\" src=\"../../portal/portal.js.jsp\"></script><link href=\"style/style.css\" type=\"text/css\" rel=\"stylesheet\" /></head><body text=\"#000000\" bgcolor=\"#ffffff\" leftmargin=\"0\" topmargin=\"0\" onLoad=\"preload_()\" marginwidth=\"0\" marginheight=\"0\"><center><table cellspacing=\"0\" cellpadding=\"0\" width=\"98%\" border=\"0\"><tbody><tr></tr><tr><td valign=\"top\">");
        if (_jspx_meth_fulong_005fportlet_005f0(_jspx_th_site_005fchannel_005f0, _jspx_page_context))
          return true;
        out.write("</td></tr><tr><td valign=\"top\"><table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" border=\"0\"><tbody><tr><td valign=\"top\" width=\"110\">");
        if (_jspx_meth_fulong_005fportlet_005f1(_jspx_th_site_005fchannel_005f0, _jspx_page_context))
          return true;
        out.write("</td><td valign=\"top\" width=\"5\"></td><td></td><td valign=\"top\"><form action=\"#\" method=\"post\" enctype=\"multipart/form-data\" definition=\"2547455076421\" definitionname=\"病毒预警\" onsubmit=\"return Validator.ValidateForm(this);\" submit=\"function(){for ( var name in FCKeditorAPI.__Instances ){var oEditor = FCKeditorAPI.__Instances[ name ] ;if ( oEditor.GetParentForm &amp;&amp; oEditor.GetParentForm() == this )oEditor.UpdateLinkedField() ;}this._FCKOriginalSubmit() ;}\" name=\"form1\"><table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" border=\"0\"><tbody><tr><td><img alt=\"\" src=\"/sites/gamioa/pic/xinwen.JPG\" /></td><td width=\"100%\" background=\"pic/t_bg01.gif\"><table cellspacing=\"0\" cellpadding=\"0\" background=\"\" border=\"0\"><tbody><tr><td class=\"size4\"><b><font color=\"#264cae\">病毒预警</font></b></td><td><img height=\"8\" alt=\"\" width=\"15\" src=\"pic/clear.gif\" /></td><td><b><font color=\"#264cae\">- 编辑预警 -</font></b></td></tr></tbody></table></td><td><img alt=\"\" src=\"pic/t_end.gif\" /></td></tr></tbody></table><table width=\"95%\" align=\"center\" border=\"0\"><tbody><tr><td colspan=\"2\"></td></tr><tr><td valign=\"top\" align=\"center\"><b><font color=\"#000000\">主&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题：</font></b></td><td>");
        if (_jspx_meth_fulong_005fportlet_005f2(_jspx_th_site_005fchannel_005f0, _jspx_page_context))
          return true;
        out.write("</td></tr><tr><td valign=\"top\" align=\"center\"><b>日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期：</b></td><td>");
        if (_jspx_meth_fulong_005fportlet_005f3(_jspx_th_site_005fchannel_005f0, _jspx_page_context))
          return true;
        out.write("</td></tr><tr><td valign=\"top\" align=\"center\"><b><font color=\"#000000\">正&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;文：</font></b></td><td>");
        if (_jspx_meth_fulong_005fportlet_005f4(_jspx_th_site_005fchannel_005f0, _jspx_page_context))
          return true;
        out.write("</td></tr><tr><td valign=\"top\"></td><td></td></tr><tr><td valign=\"top\"></td><td></td></tr><tr><td valign=\"top\">&nbsp;</td><td><div align=\"left\"><table cellspacing=\"0\" cellpadding=\"1\" border=\"0\"><tbody><tr><td>");
        if (_jspx_meth_fulong_005fportlet_005f5(_jspx_th_site_005fchannel_005f0, _jspx_page_context))
          return true;
        out.write("</td><td>&nbsp;&nbsp; <img height=\"8\" alt=\"\" width=\"5\" src=\"pic/clear.gif\" /></td><td nowrap=\"nowrap\">");
        if (_jspx_meth_fulong_005fportlet_005f6(_jspx_th_site_005fchannel_005f0, _jspx_page_context))
          return true;
        out.write("</td></tr></tbody></table></div></td></tr></tbody></table></form></td></tr></tbody></table>");
        if (_jspx_meth_fulong_005fportlet_005f7(_jspx_th_site_005fchannel_005f0, _jspx_page_context))
          return true;
        out.write("</td></tr></tbody></table></center><br /></body>");
        int evalDoAfterBody = _jspx_th_site_005fchannel_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_site_005fchannel_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_site_005fchannel_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fsite_005fchannel.reuse(_jspx_th_site_005fchannel_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fsite_005fchannel.reuse(_jspx_th_site_005fchannel_005f0);
    return false;
  }

  private boolean _jspx_meth_site_005ftitle_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_site_005fchannel_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  site:title
    com.fulong.taglib.site.TitleTag _jspx_th_site_005ftitle_005f0 = (com.fulong.taglib.site.TitleTag) _005fjspx_005ftagPool_005fsite_005ftitle_005fnobody.get(com.fulong.taglib.site.TitleTag.class);
    _jspx_th_site_005ftitle_005f0.setPageContext(_jspx_page_context);
    _jspx_th_site_005ftitle_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_site_005fchannel_005f0);
    int _jspx_eval_site_005ftitle_005f0 = _jspx_th_site_005ftitle_005f0.doStartTag();
    if (_jspx_th_site_005ftitle_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fsite_005ftitle_005fnobody.reuse(_jspx_th_site_005ftitle_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fsite_005ftitle_005fnobody.reuse(_jspx_th_site_005ftitle_005f0);
    return false;
  }

  private boolean _jspx_meth_fulong_005fportlet_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_site_005fchannel_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:portlet
    com.fulong.taglib.portal.PortletTag _jspx_th_fulong_005fportlet_005f0 = (com.fulong.taglib.portal.PortletTag) _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.get(com.fulong.taglib.portal.PortletTag.class);
    _jspx_th_fulong_005fportlet_005f0.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fportlet_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_site_005fchannel_005f0);
    _jspx_th_fulong_005fportlet_005f0.setId("pt388515");
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
        out.write("clip-paths");
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
        if (_jspx_meth_fulong_005fvalue_005f0(_jspx_th_fulong_005fpreference_005f1, _jspx_page_context))
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
        out.write("source");
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

  private boolean _jspx_meth_fulong_005fvalue_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f0 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f0.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f1);
    int _jspx_eval_fulong_005fvalue_005f0 = _jspx_th_fulong_005fvalue_005f0.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f0.doInitBody();
      }
      do {
        out.write("header001");
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

  private boolean _jspx_meth_fulong_005fportlet_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_site_005fchannel_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:portlet
    com.fulong.taglib.portal.PortletTag _jspx_th_fulong_005fportlet_005f1 = (com.fulong.taglib.portal.PortletTag) _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.get(com.fulong.taglib.portal.PortletTag.class);
    _jspx_th_fulong_005fportlet_005f1.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fportlet_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_site_005fchannel_005f0);
    _jspx_th_fulong_005fportlet_005f1.setId("pt403875");
    _jspx_th_fulong_005fportlet_005f1.setType("page_clip");
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
        out.write("clip-paths");
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
        if (_jspx_meth_fulong_005fvalue_005f1(_jspx_th_fulong_005fpreference_005f3, _jspx_page_context))
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
        out.write("source");
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

  private boolean _jspx_meth_fulong_005fvalue_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f1 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f1.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f3);
    int _jspx_eval_fulong_005fvalue_005f1 = _jspx_th_fulong_005fvalue_005f1.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f1.doInitBody();
      }
      do {
        out.write("lefter001");
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

  private boolean _jspx_meth_fulong_005fportlet_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_site_005fchannel_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:portlet
    com.fulong.taglib.portal.PortletTag _jspx_th_fulong_005fportlet_005f2 = (com.fulong.taglib.portal.PortletTag) _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.get(com.fulong.taglib.portal.PortletTag.class);
    _jspx_th_fulong_005fportlet_005f2.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fportlet_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_site_005fchannel_005f0);
    _jspx_th_fulong_005fportlet_005f2.setId("pt311062");
    _jspx_th_fulong_005fportlet_005f2.setType("textinput");
    int _jspx_eval_fulong_005fportlet_005f2 = _jspx_th_fulong_005fportlet_005f2.doStartTag();
    if (_jspx_eval_fulong_005fportlet_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fportlet_005f2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fportlet_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fportlet_005f2.doInitBody();
      }
      do {
        if (_jspx_meth_fulong_005fpreference_005f4(_jspx_th_fulong_005fportlet_005f2, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f5(_jspx_th_fulong_005fportlet_005f2, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f6(_jspx_th_fulong_005fportlet_005f2, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f7(_jspx_th_fulong_005fportlet_005f2, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f8(_jspx_th_fulong_005fportlet_005f2, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f9(_jspx_th_fulong_005fportlet_005f2, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f10(_jspx_th_fulong_005fportlet_005f2, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f11(_jspx_th_fulong_005fportlet_005f2, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f12(_jspx_th_fulong_005fportlet_005f2, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f13(_jspx_th_fulong_005fportlet_005f2, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f14(_jspx_th_fulong_005fportlet_005f2, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f15(_jspx_th_fulong_005fportlet_005f2, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f16(_jspx_th_fulong_005fportlet_005f2, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f17(_jspx_th_fulong_005fportlet_005f2, _jspx_page_context))
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

  private boolean _jspx_meth_fulong_005fpreference_005f4(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f4 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f4.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f2);
    int _jspx_eval_fulong_005fpreference_005f4 = _jspx_th_fulong_005fpreference_005f4.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f4(_jspx_th_fulong_005fpreference_005f4, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f2(_jspx_th_fulong_005fpreference_005f4, _jspx_page_context))
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
        out.write("password");
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

  private boolean _jspx_meth_fulong_005fvalue_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f4, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f2 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f2.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f4);
    int _jspx_eval_fulong_005fvalue_005f2 = _jspx_th_fulong_005fvalue_005f2.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f2.doInitBody();
      }
      do {
        out.write("false");
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

  private boolean _jspx_meth_fulong_005fpreference_005f5(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f5 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f5.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f2);
    int _jspx_eval_fulong_005fpreference_005f5 = _jspx_th_fulong_005fpreference_005f5.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f5(_jspx_th_fulong_005fpreference_005f5, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f3(_jspx_th_fulong_005fpreference_005f5, _jspx_page_context))
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
        out.write("propertyWhich");
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

  private boolean _jspx_meth_fulong_005fvalue_005f3(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f3 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f3.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f5);
    int _jspx_eval_fulong_005fvalue_005f3 = _jspx_th_fulong_005fvalue_005f3.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f3 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f3.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f3.doInitBody();
      }
      do {
        out.write('1');
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

  private boolean _jspx_meth_fulong_005fpreference_005f6(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f6 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f6.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f2);
    int _jspx_eval_fulong_005fpreference_005f6 = _jspx_th_fulong_005fpreference_005f6.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f6 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f6(_jspx_th_fulong_005fpreference_005f6, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f4(_jspx_th_fulong_005fpreference_005f6, _jspx_page_context))
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
        out.write("category-id");
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

  private boolean _jspx_meth_fulong_005fvalue_005f4(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f6, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f4 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f4.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f6);
    int _jspx_eval_fulong_005fvalue_005f4 = _jspx_th_fulong_005fvalue_005f4.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f4 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f4.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f4.doInitBody();
      }
      do {
        out.write("2547455076421");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f4.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f4 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f4);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f7(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f7 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f7.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f2);
    int _jspx_eval_fulong_005fpreference_005f7 = _jspx_th_fulong_005fpreference_005f7.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f7 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f7(_jspx_th_fulong_005fpreference_005f7, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f5(_jspx_th_fulong_005fpreference_005f7, _jspx_page_context))
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
        out.write("maxLength");
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

  private boolean _jspx_meth_fulong_005fvalue_005f5(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f7, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f5 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f5.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f7);
    int _jspx_eval_fulong_005fvalue_005f5 = _jspx_th_fulong_005fvalue_005f5.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f5);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f5);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f8(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f8 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f8.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f2);
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
        out.write("value");
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
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f6 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f6.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f8);
    int _jspx_eval_fulong_005fvalue_005f6 = _jspx_th_fulong_005fvalue_005f6.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f6);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f6);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f9(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f9 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f9.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f2);
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
        out.write("tabindex");
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

  private boolean _jspx_meth_fulong_005fpreference_005f10(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f10 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f10.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f2);
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
        out.write("vcard");
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
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f8 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f8.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f10);
    int _jspx_eval_fulong_005fvalue_005f8 = _jspx_th_fulong_005fvalue_005f8.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f8);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f8);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f11(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f11 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f11.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f11.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f2);
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
        out.write("disabled");
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
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f9 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f9.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f11);
    int _jspx_eval_fulong_005fvalue_005f9 = _jspx_th_fulong_005fvalue_005f9.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f9);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f9);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f12(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f12 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f12.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f12.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f2);
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
        out.write("echo");
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
        out.write("nodeEcho");
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

  private boolean _jspx_meth_fulong_005fpreference_005f13(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f13 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f13.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f13.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f2);
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
        out.write("contentType");
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
        out.write("default");
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

  private boolean _jspx_meth_fulong_005fpreference_005f14(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f14 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f14.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f14.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f2);
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
        out.write("propertyId");
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
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f12 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f12.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f12.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f14);
    int _jspx_eval_fulong_005fvalue_005f12 = _jspx_th_fulong_005fvalue_005f12.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f12 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f12 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f12.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f12.doInitBody();
      }
      do {
        out.write("title");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f12.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f12 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f12);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f12);
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
        out.write("readonly");
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
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f13 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f13.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f13.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f15);
    int _jspx_eval_fulong_005fvalue_005f13 = _jspx_th_fulong_005fvalue_005f13.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f13.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f13);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f13);
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
        if (_jspx_meth_fulong_005fvalue_005f14(_jspx_th_fulong_005fpreference_005f16, _jspx_page_context))
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

  private boolean _jspx_meth_fulong_005fvalue_005f14(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f16, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f14 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f14.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f14.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f16);
    int _jspx_eval_fulong_005fvalue_005f14 = _jspx_th_fulong_005fvalue_005f14.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f14.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f14);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f14);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f17(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f17 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f17.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f17.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f2);
    int _jspx_eval_fulong_005fpreference_005f17 = _jspx_th_fulong_005fpreference_005f17.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f17 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f17(_jspx_th_fulong_005fpreference_005f17, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f15(_jspx_th_fulong_005fpreference_005f17, _jspx_page_context))
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

  private boolean _jspx_meth_fulong_005fvalue_005f15(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f17, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f15 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f15.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f15.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f17);
    int _jspx_eval_fulong_005fvalue_005f15 = _jspx_th_fulong_005fvalue_005f15.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f15.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f15);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f15);
    return false;
  }

  private boolean _jspx_meth_fulong_005fportlet_005f3(javax.servlet.jsp.tagext.JspTag _jspx_th_site_005fchannel_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:portlet
    com.fulong.taglib.portal.PortletTag _jspx_th_fulong_005fportlet_005f3 = (com.fulong.taglib.portal.PortletTag) _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.get(com.fulong.taglib.portal.PortletTag.class);
    _jspx_th_fulong_005fportlet_005f3.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fportlet_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_site_005fchannel_005f0);
    _jspx_th_fulong_005fportlet_005f3.setId("pt397093");
    _jspx_th_fulong_005fportlet_005f3.setType("calendar");
    int _jspx_eval_fulong_005fportlet_005f3 = _jspx_th_fulong_005fportlet_005f3.doStartTag();
    if (_jspx_eval_fulong_005fportlet_005f3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fportlet_005f3 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fportlet_005f3.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fportlet_005f3.doInitBody();
      }
      do {
        if (_jspx_meth_fulong_005fpreference_005f18(_jspx_th_fulong_005fportlet_005f3, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f19(_jspx_th_fulong_005fportlet_005f3, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f20(_jspx_th_fulong_005fportlet_005f3, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f21(_jspx_th_fulong_005fportlet_005f3, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f22(_jspx_th_fulong_005fportlet_005f3, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f23(_jspx_th_fulong_005fportlet_005f3, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f24(_jspx_th_fulong_005fportlet_005f3, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f25(_jspx_th_fulong_005fportlet_005f3, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f26(_jspx_th_fulong_005fportlet_005f3, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f27(_jspx_th_fulong_005fportlet_005f3, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f28(_jspx_th_fulong_005fportlet_005f3, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f29(_jspx_th_fulong_005fportlet_005f3, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f30(_jspx_th_fulong_005fportlet_005f3, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f31(_jspx_th_fulong_005fportlet_005f3, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f32(_jspx_th_fulong_005fportlet_005f3, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f33(_jspx_th_fulong_005fportlet_005f3, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f34(_jspx_th_fulong_005fportlet_005f3, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fportlet_005f3.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fportlet_005f3 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fportlet_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.reuse(_jspx_th_fulong_005fportlet_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.reuse(_jspx_th_fulong_005fportlet_005f3);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f18(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f18 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f18.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f18.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f3);
    int _jspx_eval_fulong_005fpreference_005f18 = _jspx_th_fulong_005fpreference_005f18.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f18 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f18(_jspx_th_fulong_005fpreference_005f18, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f16(_jspx_th_fulong_005fpreference_005f18, _jspx_page_context))
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
        out.write("propertyWhich");
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

  private boolean _jspx_meth_fulong_005fvalue_005f16(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f18, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f16 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f16.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f16.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f18);
    int _jspx_eval_fulong_005fvalue_005f16 = _jspx_th_fulong_005fvalue_005f16.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f16 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f16 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f16.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f16.doInitBody();
      }
      do {
        out.write('1');
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f16.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f16 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f16.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f16);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f16);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f19(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f19 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f19.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f19.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f3);
    int _jspx_eval_fulong_005fpreference_005f19 = _jspx_th_fulong_005fpreference_005f19.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f19 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f19(_jspx_th_fulong_005fpreference_005f19, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f17(_jspx_th_fulong_005fpreference_005f19, _jspx_page_context))
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
        out.write("allowWrite");
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

  private boolean _jspx_meth_fulong_005fvalue_005f17(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f19, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f17 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f17.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f17.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f19);
    int _jspx_eval_fulong_005fvalue_005f17 = _jspx_th_fulong_005fvalue_005f17.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f17 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f17 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f17.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f17.doInitBody();
      }
      do {
        out.write("false");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f17.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f17 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f17.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f17);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f17);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f20(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f20 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f20.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f20.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f3);
    int _jspx_eval_fulong_005fpreference_005f20 = _jspx_th_fulong_005fpreference_005f20.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f20 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f20(_jspx_th_fulong_005fpreference_005f20, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f18(_jspx_th_fulong_005fpreference_005f20, _jspx_page_context))
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
        out.write("category-id");
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

  private boolean _jspx_meth_fulong_005fvalue_005f18(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f20, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f18 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f18.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f18.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f20);
    int _jspx_eval_fulong_005fvalue_005f18 = _jspx_th_fulong_005fvalue_005f18.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f18 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f18 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f18.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f18.doInitBody();
      }
      do {
        out.write("2547455076421");
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

  private boolean _jspx_meth_fulong_005fpreference_005f21(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f21 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f21.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f21.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f3);
    int _jspx_eval_fulong_005fpreference_005f21 = _jspx_th_fulong_005fpreference_005f21.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f21 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f21(_jspx_th_fulong_005fpreference_005f21, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f19(_jspx_th_fulong_005fpreference_005f21, _jspx_page_context))
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
        out.write("tabindex");
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

  private boolean _jspx_meth_fulong_005fvalue_005f19(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f21, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f19 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f19.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f19.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f21);
    int _jspx_eval_fulong_005fvalue_005f19 = _jspx_th_fulong_005fvalue_005f19.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f19.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f19);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f19);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f22(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f22 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f22.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f22.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f3);
    int _jspx_eval_fulong_005fpreference_005f22 = _jspx_th_fulong_005fpreference_005f22.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f22 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f22(_jspx_th_fulong_005fpreference_005f22, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f20(_jspx_th_fulong_005fpreference_005f22, _jspx_page_context))
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
        out.write("buttonStyle");
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

  private boolean _jspx_meth_fulong_005fvalue_005f20(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f22, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f20 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f20.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f20.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f22);
    int _jspx_eval_fulong_005fvalue_005f20 = _jspx_th_fulong_005fvalue_005f20.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f20.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f20);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f20);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f23(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f23 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f23.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f23.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f3);
    int _jspx_eval_fulong_005fpreference_005f23 = _jspx_th_fulong_005fpreference_005f23.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f23 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f23(_jspx_th_fulong_005fpreference_005f23, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f21(_jspx_th_fulong_005fpreference_005f23, _jspx_page_context))
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
        out.write("defaultValue");
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

  private boolean _jspx_meth_fulong_005fvalue_005f21(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f23, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f21 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f21.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f21.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f23);
    int _jspx_eval_fulong_005fvalue_005f21 = _jspx_th_fulong_005fvalue_005f21.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f21.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f21);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f21);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f24(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f24 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f24.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f24.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f3);
    int _jspx_eval_fulong_005fpreference_005f24 = _jspx_th_fulong_005fpreference_005f24.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f24 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f24(_jspx_th_fulong_005fpreference_005f24, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f22(_jspx_th_fulong_005fpreference_005f24, _jspx_page_context))
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
        out.write("unit");
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

  private boolean _jspx_meth_fulong_005fvalue_005f22(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f24, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f22 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f22.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f22.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f24);
    int _jspx_eval_fulong_005fvalue_005f22 = _jspx_th_fulong_005fvalue_005f22.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f22 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f22 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f22.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f22.doInitBody();
      }
      do {
        out.write('6');
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f22.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f22 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f22.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f22);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f22);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f25(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f25 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f25.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f25.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f3);
    int _jspx_eval_fulong_005fpreference_005f25 = _jspx_th_fulong_005fpreference_005f25.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f25 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f25(_jspx_th_fulong_005fpreference_005f25, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f23(_jspx_th_fulong_005fpreference_005f25, _jspx_page_context))
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
        out.write("echo");
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

  private boolean _jspx_meth_fulong_005fvalue_005f23(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f25, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f23 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f23.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f23.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f25);
    int _jspx_eval_fulong_005fvalue_005f23 = _jspx_th_fulong_005fvalue_005f23.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f23 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f23 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f23.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f23.doInitBody();
      }
      do {
        out.write("nodeEcho");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f23.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f23 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f23.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f23);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f23);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f26(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f26 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f26.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f26.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f3);
    int _jspx_eval_fulong_005fpreference_005f26 = _jspx_th_fulong_005fpreference_005f26.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f26 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f26(_jspx_th_fulong_005fpreference_005f26, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f24(_jspx_th_fulong_005fpreference_005f26, _jspx_page_context))
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
        out.write("defaultMode");
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

  private boolean _jspx_meth_fulong_005fvalue_005f24(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f26, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f24 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f24.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f24.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f26);
    int _jspx_eval_fulong_005fvalue_005f24 = _jspx_th_fulong_005fvalue_005f24.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f24 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f24 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f24.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f24.doInitBody();
      }
      do {
        out.write("none");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f24.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f24 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f24.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f24);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f24);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f27(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f27 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f27.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f27.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f3);
    int _jspx_eval_fulong_005fpreference_005f27 = _jspx_th_fulong_005fpreference_005f27.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f27 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f27(_jspx_th_fulong_005fpreference_005f27, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f25(_jspx_th_fulong_005fpreference_005f27, _jspx_page_context))
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
        out.write("contentType");
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

  private boolean _jspx_meth_fulong_005fvalue_005f25(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f27, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f25 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f25.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f25.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f27);
    int _jspx_eval_fulong_005fvalue_005f25 = _jspx_th_fulong_005fvalue_005f25.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f25 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f25 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f25.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f25.doInitBody();
      }
      do {
        out.write("default");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f25.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f25 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f25.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f25);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f25);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f28(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f28 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f28.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f28.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f3);
    int _jspx_eval_fulong_005fpreference_005f28 = _jspx_th_fulong_005fpreference_005f28.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f28 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f28(_jspx_th_fulong_005fpreference_005f28, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f26(_jspx_th_fulong_005fpreference_005f28, _jspx_page_context))
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
        out.write("offset");
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

  private boolean _jspx_meth_fulong_005fvalue_005f26(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f28, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f26 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f26.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f26.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f28);
    int _jspx_eval_fulong_005fvalue_005f26 = _jspx_th_fulong_005fvalue_005f26.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f26 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f26 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f26.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f26.doInitBody();
      }
      do {
        out.write('0');
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f26.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f26 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f26.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f26);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f26);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f29(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f29 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f29.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f29.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f3);
    int _jspx_eval_fulong_005fpreference_005f29 = _jspx_th_fulong_005fpreference_005f29.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f29 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f29(_jspx_th_fulong_005fpreference_005f29, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f27(_jspx_th_fulong_005fpreference_005f29, _jspx_page_context))
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
        out.write("hasTime");
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

  private boolean _jspx_meth_fulong_005fvalue_005f27(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f29, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f27 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f27.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f27.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f29);
    int _jspx_eval_fulong_005fvalue_005f27 = _jspx_th_fulong_005fvalue_005f27.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f27 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f27 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f27.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f27.doInitBody();
      }
      do {
        out.write("false");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f27.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f27 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f27.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f27);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f27);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f30(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f30 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f30.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f30.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f3);
    int _jspx_eval_fulong_005fpreference_005f30 = _jspx_th_fulong_005fpreference_005f30.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f30 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f30(_jspx_th_fulong_005fpreference_005f30, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f28(_jspx_th_fulong_005fpreference_005f30, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f30.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f30.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f30);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f30);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f30(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f30, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f30 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f30.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f30.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f30);
    int _jspx_eval_fulong_005fname_005f30 = _jspx_th_fulong_005fname_005f30.doStartTag();
    if (_jspx_eval_fulong_005fname_005f30 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f30 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f30.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f30.doInitBody();
      }
      do {
        out.write("propertyId");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f30.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f30 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f30.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f30);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f30);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f28(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f30, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f28 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f28.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f28.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f30);
    int _jspx_eval_fulong_005fvalue_005f28 = _jspx_th_fulong_005fvalue_005f28.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f28 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f28 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f28.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f28.doInitBody();
      }
      do {
        out.write("date");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f28.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f28 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f28.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f28);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f28);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f31(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f31 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f31.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f31.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f3);
    int _jspx_eval_fulong_005fpreference_005f31 = _jspx_th_fulong_005fpreference_005f31.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f31 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f31(_jspx_th_fulong_005fpreference_005f31, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f29(_jspx_th_fulong_005fpreference_005f31, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f31.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f31.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f31);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f31);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f31(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f31, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f31 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f31.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f31.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f31);
    int _jspx_eval_fulong_005fname_005f31 = _jspx_th_fulong_005fname_005f31.doStartTag();
    if (_jspx_eval_fulong_005fname_005f31 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f31 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f31.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f31.doInitBody();
      }
      do {
        out.write("textSize");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f31.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f31 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f31.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f31);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f31);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f29(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f31, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f29 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f29.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f29.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f31);
    int _jspx_eval_fulong_005fvalue_005f29 = _jspx_th_fulong_005fvalue_005f29.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f29.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f29);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f29);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f32(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f32 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f32.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f32.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f3);
    int _jspx_eval_fulong_005fpreference_005f32 = _jspx_th_fulong_005fpreference_005f32.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f32 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f32(_jspx_th_fulong_005fpreference_005f32, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f30(_jspx_th_fulong_005fpreference_005f32, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f32.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f32.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f32);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f32);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f32(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f32, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f32 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f32.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f32.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f32);
    int _jspx_eval_fulong_005fname_005f32 = _jspx_th_fulong_005fname_005f32.doStartTag();
    if (_jspx_eval_fulong_005fname_005f32 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f32 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f32.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f32.doInitBody();
      }
      do {
        out.write("submit");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f32.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f32 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f32.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f32);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f32);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f30(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f32, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f30 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f30.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f30.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f32);
    int _jspx_eval_fulong_005fvalue_005f30 = _jspx_th_fulong_005fvalue_005f30.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f30 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f30 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f30.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f30.doInitBody();
      }
      do {
        out.write("false");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f30.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f30 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f30.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f30);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f30);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f33(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f33 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f33.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f33.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f3);
    int _jspx_eval_fulong_005fpreference_005f33 = _jspx_th_fulong_005fpreference_005f33.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f33 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f33(_jspx_th_fulong_005fpreference_005f33, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f31(_jspx_th_fulong_005fpreference_005f33, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f33.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f33.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f33);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f33);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f33(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f33, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f33 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f33.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f33.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f33);
    int _jspx_eval_fulong_005fname_005f33 = _jspx_th_fulong_005fname_005f33.doStartTag();
    if (_jspx_eval_fulong_005fname_005f33 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f33 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f33.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f33.doInitBody();
      }
      do {
        out.write("buttonWord");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f33.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f33 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f33.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f33);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f33);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f31(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f33, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f31 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f31.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f31.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f33);
    int _jspx_eval_fulong_005fvalue_005f31 = _jspx_th_fulong_005fvalue_005f31.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f31 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f31 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f31.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f31.doInitBody();
      }
      do {
        out.write('选');
        out.write('择');
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f31.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f31 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f31.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f31);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f31);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f34(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f34 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f34.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f34.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f3);
    int _jspx_eval_fulong_005fpreference_005f34 = _jspx_th_fulong_005fpreference_005f34.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f34 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f34(_jspx_th_fulong_005fpreference_005f34, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f32(_jspx_th_fulong_005fpreference_005f34, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f34.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f34.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f34);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f34);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f34(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f34, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f34 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f34.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f34.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f34);
    int _jspx_eval_fulong_005fname_005f34 = _jspx_th_fulong_005fname_005f34.doStartTag();
    if (_jspx_eval_fulong_005fname_005f34 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f34 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f34.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f34.doInitBody();
      }
      do {
        out.write("textStyle");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f34.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f34 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f34.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f34);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f34);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f32(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f34, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f32 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f32.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f32.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f34);
    int _jspx_eval_fulong_005fvalue_005f32 = _jspx_th_fulong_005fvalue_005f32.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f32.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f32);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f32);
    return false;
  }

  private boolean _jspx_meth_fulong_005fportlet_005f4(javax.servlet.jsp.tagext.JspTag _jspx_th_site_005fchannel_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:portlet
    com.fulong.taglib.portal.PortletTag _jspx_th_fulong_005fportlet_005f4 = (com.fulong.taglib.portal.PortletTag) _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.get(com.fulong.taglib.portal.PortletTag.class);
    _jspx_th_fulong_005fportlet_005f4.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fportlet_005f4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_site_005fchannel_005f0);
    _jspx_th_fulong_005fportlet_005f4.setId("pt451734");
    _jspx_th_fulong_005fportlet_005f4.setType("richtext");
    int _jspx_eval_fulong_005fportlet_005f4 = _jspx_th_fulong_005fportlet_005f4.doStartTag();
    if (_jspx_eval_fulong_005fportlet_005f4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fportlet_005f4 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fportlet_005f4.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fportlet_005f4.doInitBody();
      }
      do {
        if (_jspx_meth_fulong_005fpreference_005f35(_jspx_th_fulong_005fportlet_005f4, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f36(_jspx_th_fulong_005fportlet_005f4, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f37(_jspx_th_fulong_005fportlet_005f4, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f38(_jspx_th_fulong_005fportlet_005f4, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f39(_jspx_th_fulong_005fportlet_005f4, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f40(_jspx_th_fulong_005fportlet_005f4, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f41(_jspx_th_fulong_005fportlet_005f4, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fportlet_005f4.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fportlet_005f4 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fportlet_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.reuse(_jspx_th_fulong_005fportlet_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.reuse(_jspx_th_fulong_005fportlet_005f4);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f35(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f4, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f35 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f35.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f35.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f4);
    int _jspx_eval_fulong_005fpreference_005f35 = _jspx_th_fulong_005fpreference_005f35.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f35 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f35(_jspx_th_fulong_005fpreference_005f35, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f33(_jspx_th_fulong_005fpreference_005f35, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f35.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f35.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f35);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f35);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f35(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f35, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f35 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f35.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f35.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f35);
    int _jspx_eval_fulong_005fname_005f35 = _jspx_th_fulong_005fname_005f35.doStartTag();
    if (_jspx_eval_fulong_005fname_005f35 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f35 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f35.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f35.doInitBody();
      }
      do {
        out.write("category-id");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f35.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f35 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f35.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f35);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f35);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f33(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f35, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f33 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f33.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f33.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f35);
    int _jspx_eval_fulong_005fvalue_005f33 = _jspx_th_fulong_005fvalue_005f33.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f33 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f33 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f33.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f33.doInitBody();
      }
      do {
        out.write("2547455076421");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f33.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f33 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f33.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f33);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f33);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f36(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f4, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f36 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f36.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f36.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f4);
    int _jspx_eval_fulong_005fpreference_005f36 = _jspx_th_fulong_005fpreference_005f36.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f36 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f36(_jspx_th_fulong_005fpreference_005f36, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f34(_jspx_th_fulong_005fpreference_005f36, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f36.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f36.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f36);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f36);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f36(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f36, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f36 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f36.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f36.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f36);
    int _jspx_eval_fulong_005fname_005f36 = _jspx_th_fulong_005fname_005f36.doStartTag();
    if (_jspx_eval_fulong_005fname_005f36 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f36 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f36.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f36.doInitBody();
      }
      do {
        out.write("value");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f36.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f36 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f36.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f36);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f36);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f34(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f36, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f34 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f34.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f34.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f36);
    int _jspx_eval_fulong_005fvalue_005f34 = _jspx_th_fulong_005fvalue_005f34.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f34.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f34);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f34);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f37(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f4, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f37 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f37.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f37.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f4);
    int _jspx_eval_fulong_005fpreference_005f37 = _jspx_th_fulong_005fpreference_005f37.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f37 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f37(_jspx_th_fulong_005fpreference_005f37, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f35(_jspx_th_fulong_005fpreference_005f37, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f37.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f37.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f37);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f37);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f37(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f37, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f37 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f37.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f37.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f37);
    int _jspx_eval_fulong_005fname_005f37 = _jspx_th_fulong_005fname_005f37.doStartTag();
    if (_jspx_eval_fulong_005fname_005f37 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f37 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f37.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f37.doInitBody();
      }
      do {
        out.write("tabindex");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f37.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f37 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f37.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f37);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f37);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f35(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f37, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f35 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f35.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f35.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f37);
    int _jspx_eval_fulong_005fvalue_005f35 = _jspx_th_fulong_005fvalue_005f35.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f35.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f35);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f35);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f38(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f4, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f38 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f38.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f38.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f4);
    int _jspx_eval_fulong_005fpreference_005f38 = _jspx_th_fulong_005fpreference_005f38.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f38 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f38(_jspx_th_fulong_005fpreference_005f38, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f36(_jspx_th_fulong_005fpreference_005f38, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f38.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f38.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f38);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f38);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f38(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f38, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f38 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f38.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f38.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f38);
    int _jspx_eval_fulong_005fname_005f38 = _jspx_th_fulong_005fname_005f38.doStartTag();
    if (_jspx_eval_fulong_005fname_005f38 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f38 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f38.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f38.doInitBody();
      }
      do {
        out.write("width");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f38.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f38 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f38.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f38);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f38);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f36(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f38, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f36 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f36.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f36.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f38);
    int _jspx_eval_fulong_005fvalue_005f36 = _jspx_th_fulong_005fvalue_005f36.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f36 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f36 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f36.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f36.doInitBody();
      }
      do {
        out.write('6');
        out.write('0');
        out.write('0');
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f36.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f36 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f36.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f36);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f36);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f39(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f4, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f39 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f39.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f39.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f4);
    int _jspx_eval_fulong_005fpreference_005f39 = _jspx_th_fulong_005fpreference_005f39.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f39 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f39(_jspx_th_fulong_005fpreference_005f39, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f37(_jspx_th_fulong_005fpreference_005f39, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f39.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f39.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f39);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f39);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f39(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f39, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f39 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f39.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f39.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f39);
    int _jspx_eval_fulong_005fname_005f39 = _jspx_th_fulong_005fname_005f39.doStartTag();
    if (_jspx_eval_fulong_005fname_005f39 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f39 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f39.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f39.doInitBody();
      }
      do {
        out.write("height");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f39.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f39 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f39.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f39);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f39);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f37(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f39, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f37 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f37.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f37.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f39);
    int _jspx_eval_fulong_005fvalue_005f37 = _jspx_th_fulong_005fvalue_005f37.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f37 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f37 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f37.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f37.doInitBody();
      }
      do {
        out.write('4');
        out.write('0');
        out.write('0');
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f37.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f37 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f37.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f37);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f37);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f40(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f4, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f40 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f40.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f40.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f4);
    int _jspx_eval_fulong_005fpreference_005f40 = _jspx_th_fulong_005fpreference_005f40.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f40 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f40(_jspx_th_fulong_005fpreference_005f40, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f38(_jspx_th_fulong_005fpreference_005f40, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f40.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f40.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f40);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f40);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f40(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f40, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f40 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f40.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f40.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f40);
    int _jspx_eval_fulong_005fname_005f40 = _jspx_th_fulong_005fname_005f40.doStartTag();
    if (_jspx_eval_fulong_005fname_005f40 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f40 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f40.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f40.doInitBody();
      }
      do {
        out.write("style");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f40.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f40 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f40.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f40);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f40);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f38(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f40, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f38 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f38.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f38.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f40);
    int _jspx_eval_fulong_005fvalue_005f38 = _jspx_th_fulong_005fvalue_005f38.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f38.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f38);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f38);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f41(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f4, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f41 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f41.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f41.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f4);
    int _jspx_eval_fulong_005fpreference_005f41 = _jspx_th_fulong_005fpreference_005f41.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f41 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f41(_jspx_th_fulong_005fpreference_005f41, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f39(_jspx_th_fulong_005fpreference_005f41, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f41.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f41.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f41);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f41);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f41(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f41, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f41 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f41.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f41.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f41);
    int _jspx_eval_fulong_005fname_005f41 = _jspx_th_fulong_005fname_005f41.doStartTag();
    if (_jspx_eval_fulong_005fname_005f41 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f41 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f41.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f41.doInitBody();
      }
      do {
        out.write("propertyId");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f41.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f41 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f41.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f41);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f41);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f39(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f41, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f39 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f39.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f39.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f41);
    int _jspx_eval_fulong_005fvalue_005f39 = _jspx_th_fulong_005fvalue_005f39.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f39 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f39 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f39.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f39.doInitBody();
      }
      do {
        out.write("content");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f39.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f39 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f39.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f39);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f39);
    return false;
  }

  private boolean _jspx_meth_fulong_005fportlet_005f5(javax.servlet.jsp.tagext.JspTag _jspx_th_site_005fchannel_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:portlet
    com.fulong.taglib.portal.PortletTag _jspx_th_fulong_005fportlet_005f5 = (com.fulong.taglib.portal.PortletTag) _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.get(com.fulong.taglib.portal.PortletTag.class);
    _jspx_th_fulong_005fportlet_005f5.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fportlet_005f5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_site_005fchannel_005f0);
    _jspx_th_fulong_005fportlet_005f5.setId("pt673953");
    _jspx_th_fulong_005fportlet_005f5.setType("singleSavebutton");
    int _jspx_eval_fulong_005fportlet_005f5 = _jspx_th_fulong_005fportlet_005f5.doStartTag();
    if (_jspx_eval_fulong_005fportlet_005f5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fportlet_005f5 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fportlet_005f5.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fportlet_005f5.doInitBody();
      }
      do {
        if (_jspx_meth_fulong_005fpreference_005f42(_jspx_th_fulong_005fportlet_005f5, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f43(_jspx_th_fulong_005fportlet_005f5, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f44(_jspx_th_fulong_005fportlet_005f5, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f45(_jspx_th_fulong_005fportlet_005f5, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f46(_jspx_th_fulong_005fportlet_005f5, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f47(_jspx_th_fulong_005fportlet_005f5, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f48(_jspx_th_fulong_005fportlet_005f5, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f49(_jspx_th_fulong_005fportlet_005f5, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f50(_jspx_th_fulong_005fportlet_005f5, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f51(_jspx_th_fulong_005fportlet_005f5, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f52(_jspx_th_fulong_005fportlet_005f5, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f53(_jspx_th_fulong_005fportlet_005f5, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f54(_jspx_th_fulong_005fportlet_005f5, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f55(_jspx_th_fulong_005fportlet_005f5, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f56(_jspx_th_fulong_005fportlet_005f5, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f57(_jspx_th_fulong_005fportlet_005f5, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f58(_jspx_th_fulong_005fportlet_005f5, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f59(_jspx_th_fulong_005fportlet_005f5, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f60(_jspx_th_fulong_005fportlet_005f5, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f61(_jspx_th_fulong_005fportlet_005f5, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f62(_jspx_th_fulong_005fportlet_005f5, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f63(_jspx_th_fulong_005fportlet_005f5, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fportlet_005f5.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fportlet_005f5 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fportlet_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.reuse(_jspx_th_fulong_005fportlet_005f5);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.reuse(_jspx_th_fulong_005fportlet_005f5);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f42(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f42 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f42.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f42.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f5);
    int _jspx_eval_fulong_005fpreference_005f42 = _jspx_th_fulong_005fpreference_005f42.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f42 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f42(_jspx_th_fulong_005fpreference_005f42, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f40(_jspx_th_fulong_005fpreference_005f42, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f42.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f42.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f42);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f42);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f42(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f42, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f42 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f42.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f42.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f42);
    int _jspx_eval_fulong_005fname_005f42 = _jspx_th_fulong_005fname_005f42.doStartTag();
    if (_jspx_eval_fulong_005fname_005f42 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f42 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f42.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f42.doInitBody();
      }
      do {
        out.write("value");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f42.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f42 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f42.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f42);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f42);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f40(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f42, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f40 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f40.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f40.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f42);
    int _jspx_eval_fulong_005fvalue_005f40 = _jspx_th_fulong_005fvalue_005f40.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f40 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f40 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f40.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f40.doInitBody();
      }
      do {
        out.write('保');
        out.write('存');
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f40.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f40 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f40.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f40);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f40);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f43(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f43 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f43.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f43.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f5);
    int _jspx_eval_fulong_005fpreference_005f43 = _jspx_th_fulong_005fpreference_005f43.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f43 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f43(_jspx_th_fulong_005fpreference_005f43, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f41(_jspx_th_fulong_005fpreference_005f43, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f43.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f43.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f43);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f43);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f43(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f43, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f43 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f43.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f43.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f43);
    int _jspx_eval_fulong_005fname_005f43 = _jspx_th_fulong_005fname_005f43.doStartTag();
    if (_jspx_eval_fulong_005fname_005f43 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f43 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f43.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f43.doInitBody();
      }
      do {
        out.write("tabindex");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f43.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f43 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f43.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f43);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f43);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f41(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f43, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f41 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f41.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f41.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f43);
    int _jspx_eval_fulong_005fvalue_005f41 = _jspx_th_fulong_005fvalue_005f41.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f41.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f41);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f41);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f44(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f44 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f44.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f44.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f5);
    int _jspx_eval_fulong_005fpreference_005f44 = _jspx_th_fulong_005fpreference_005f44.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f44 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f44(_jspx_th_fulong_005fpreference_005f44, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f42(_jspx_th_fulong_005fpreference_005f44, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f44.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f44.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f44);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f44);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f44(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f44, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f44 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f44.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f44.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f44);
    int _jspx_eval_fulong_005fname_005f44 = _jspx_th_fulong_005fname_005f44.doStartTag();
    if (_jspx_eval_fulong_005fname_005f44 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f44 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f44.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f44.doInitBody();
      }
      do {
        out.write("activity");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f44.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f44 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f44.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f44);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f44);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f42(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f44, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f42 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f42.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f42.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f44);
    int _jspx_eval_fulong_005fvalue_005f42 = _jspx_th_fulong_005fvalue_005f42.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f42 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f42 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f42.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f42.doInitBody();
      }
      do {
        out.write("begin");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f42.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f42 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f42.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f42);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f42);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f45(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f45 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f45.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f45.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f5);
    int _jspx_eval_fulong_005fpreference_005f45 = _jspx_th_fulong_005fpreference_005f45.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f45 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f45(_jspx_th_fulong_005fpreference_005f45, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f43(_jspx_th_fulong_005fpreference_005f45, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f45.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f45.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f45);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f45);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f45(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f45, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f45 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f45.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f45.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f45);
    int _jspx_eval_fulong_005fname_005f45 = _jspx_th_fulong_005fname_005f45.doStartTag();
    if (_jspx_eval_fulong_005fname_005f45 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f45 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f45.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f45.doInitBody();
      }
      do {
        out.write("imgsrc");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f45.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f45 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f45.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f45);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f45);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f43(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f45, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f43 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f43.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f43.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f45);
    int _jspx_eval_fulong_005fvalue_005f43 = _jspx_th_fulong_005fvalue_005f43.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f43.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f43);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f43);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f46(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f46 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f46.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f46.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f5);
    int _jspx_eval_fulong_005fpreference_005f46 = _jspx_th_fulong_005fpreference_005f46.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f46 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f46(_jspx_th_fulong_005fpreference_005f46, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f44(_jspx_th_fulong_005fpreference_005f46, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f46.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f46.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f46);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f46);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f46(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f46, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f46 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f46.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f46.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f46);
    int _jspx_eval_fulong_005fname_005f46 = _jspx_th_fulong_005fname_005f46.doStartTag();
    if (_jspx_eval_fulong_005fname_005f46 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f46 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f46.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f46.doInitBody();
      }
      do {
        out.write("owner");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f46.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f46 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f46.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f46);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f46);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f44(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f46, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f44 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f44.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f44.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f46);
    int _jspx_eval_fulong_005fvalue_005f44 = _jspx_th_fulong_005fvalue_005f44.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f44 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f44 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f44.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f44.doInitBody();
      }
      do {
        out.write("site");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f44.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f44 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f44.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f44);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f44);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f47(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f47 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f47.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f47.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f5);
    int _jspx_eval_fulong_005fpreference_005f47 = _jspx_th_fulong_005fpreference_005f47.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f47 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f47(_jspx_th_fulong_005fpreference_005f47, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f45(_jspx_th_fulong_005fpreference_005f47, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f47.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f47.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f47);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f47);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f47(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f47, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f47 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f47.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f47.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f47);
    int _jspx_eval_fulong_005fname_005f47 = _jspx_th_fulong_005fname_005f47.doStartTag();
    if (_jspx_eval_fulong_005fname_005f47 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f47 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f47.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f47.doInitBody();
      }
      do {
        out.write("category");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f47.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f47 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f47.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f47);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f47);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f45(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f47, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f45 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f45.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f45.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f47);
    int _jspx_eval_fulong_005fvalue_005f45 = _jspx_th_fulong_005fvalue_005f45.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f45 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f45 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f45.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f45.doInitBody();
      }
      do {
        out.write("2547455076421");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f45.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f45 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f45.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f45);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f45);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f48(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f48 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f48.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f48.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f5);
    int _jspx_eval_fulong_005fpreference_005f48 = _jspx_th_fulong_005fpreference_005f48.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f48 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f48(_jspx_th_fulong_005fpreference_005f48, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f46(_jspx_th_fulong_005fpreference_005f48, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f48.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f48.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f48);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f48);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f48(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f48, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f48 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f48.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f48.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f48);
    int _jspx_eval_fulong_005fname_005f48 = _jspx_th_fulong_005fname_005f48.doStartTag();
    if (_jspx_eval_fulong_005fname_005f48 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f48 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f48.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f48.doInitBody();
      }
      do {
        out.write("imgheight");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f48.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f48 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f48.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f48);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f48);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f46(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f48, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f46 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f46.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f46.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f48);
    int _jspx_eval_fulong_005fvalue_005f46 = _jspx_th_fulong_005fvalue_005f46.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f46.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f46);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f46);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f49(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f49 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f49.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f49.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f5);
    int _jspx_eval_fulong_005fpreference_005f49 = _jspx_th_fulong_005fpreference_005f49.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f49 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f49(_jspx_th_fulong_005fpreference_005f49, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f47(_jspx_th_fulong_005fpreference_005f49, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f49.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f49.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f49);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f49);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f49(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f49, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f49 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f49.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f49.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f49);
    int _jspx_eval_fulong_005fname_005f49 = _jspx_th_fulong_005fname_005f49.doStartTag();
    if (_jspx_eval_fulong_005fname_005f49 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f49 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f49.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f49.doInitBody();
      }
      do {
        out.write("contentType");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f49.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f49 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f49.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f49);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f49);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f47(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f49, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f47 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f47.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f47.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f49);
    int _jspx_eval_fulong_005fvalue_005f47 = _jspx_th_fulong_005fvalue_005f47.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f47 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f47 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f47.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f47.doInitBody();
      }
      do {
        out.write("default");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f47.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f47 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f47.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f47);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f47);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f50(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f50 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f50.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f50.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f5);
    int _jspx_eval_fulong_005fpreference_005f50 = _jspx_th_fulong_005fpreference_005f50.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f50 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f50(_jspx_th_fulong_005fpreference_005f50, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f48(_jspx_th_fulong_005fpreference_005f50, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f50.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f50.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f50);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f50);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f50(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f50, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f50 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f50.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f50.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f50);
    int _jspx_eval_fulong_005fname_005f50 = _jspx_th_fulong_005fname_005f50.doStartTag();
    if (_jspx_eval_fulong_005fname_005f50 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f50 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f50.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f50.doInitBody();
      }
      do {
        out.write("imgstyle");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f50.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f50 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f50.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f50);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f50);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f48(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f50, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f48 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f48.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f48.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f50);
    int _jspx_eval_fulong_005fvalue_005f48 = _jspx_th_fulong_005fvalue_005f48.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f48.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f48);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f48);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f51(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f51 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f51.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f51.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f5);
    int _jspx_eval_fulong_005fpreference_005f51 = _jspx_th_fulong_005fpreference_005f51.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f51 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f51(_jspx_th_fulong_005fpreference_005f51, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f49(_jspx_th_fulong_005fpreference_005f51, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f51.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f51.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f51);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f51);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f51(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f51, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f51 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f51.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f51.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f51);
    int _jspx_eval_fulong_005fname_005f51 = _jspx_th_fulong_005fname_005f51.doStartTag();
    if (_jspx_eval_fulong_005fname_005f51 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f51 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f51.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f51.doInitBody();
      }
      do {
        out.write("imgwidth");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f51.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f51 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f51.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f51);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f51);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f49(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f51, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f49 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f49.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f49.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f51);
    int _jspx_eval_fulong_005fvalue_005f49 = _jspx_th_fulong_005fvalue_005f49.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f49.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f49);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f49);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f52(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f52 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f52.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f52.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f5);
    int _jspx_eval_fulong_005fpreference_005f52 = _jspx_th_fulong_005fpreference_005f52.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f52 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f52(_jspx_th_fulong_005fpreference_005f52, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f50(_jspx_th_fulong_005fpreference_005f52, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f52.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f52.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f52);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f52);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f52(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f52, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f52 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f52.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f52.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f52);
    int _jspx_eval_fulong_005fname_005f52 = _jspx_th_fulong_005fname_005f52.doStartTag();
    if (_jspx_eval_fulong_005fname_005f52 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f52 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f52.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f52.doInitBody();
      }
      do {
        out.write("imgtitle");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f52.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f52 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f52.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f52);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f52);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f50(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f52, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f50 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f50.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f50.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f52);
    int _jspx_eval_fulong_005fvalue_005f50 = _jspx_th_fulong_005fvalue_005f50.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f50.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f50);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f50);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f53(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f53 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f53.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f53.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f5);
    int _jspx_eval_fulong_005fpreference_005f53 = _jspx_th_fulong_005fpreference_005f53.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f53 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f53(_jspx_th_fulong_005fpreference_005f53, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f51(_jspx_th_fulong_005fpreference_005f53, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f53.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f53.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f53);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f53);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f53(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f53, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f53 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f53.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f53.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f53);
    int _jspx_eval_fulong_005fname_005f53 = _jspx_th_fulong_005fname_005f53.doStartTag();
    if (_jspx_eval_fulong_005fname_005f53 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f53 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f53.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f53.doInitBody();
      }
      do {
        out.write("saveLimit");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f53.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f53 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f53.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f53);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f53);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f51(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f53, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f51 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f51.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f51.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f53);
    int _jspx_eval_fulong_005fvalue_005f51 = _jspx_th_fulong_005fvalue_005f51.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f51.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f51);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f51);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f54(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f54 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f54.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f54.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f5);
    int _jspx_eval_fulong_005fpreference_005f54 = _jspx_th_fulong_005fpreference_005f54.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f54 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f54(_jspx_th_fulong_005fpreference_005f54, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f52(_jspx_th_fulong_005fpreference_005f54, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f54.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f54.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f54);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f54);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f54(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f54, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f54 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f54.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f54.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f54);
    int _jspx_eval_fulong_005fname_005f54 = _jspx_th_fulong_005fname_005f54.doStartTag();
    if (_jspx_eval_fulong_005fname_005f54 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f54 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f54.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f54.doInitBody();
      }
      do {
        out.write("create");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f54.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f54 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f54.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f54);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f54);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f52(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f54, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f52 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f52.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f52.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f54);
    int _jspx_eval_fulong_005fvalue_005f52 = _jspx_th_fulong_005fvalue_005f52.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f52 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f52 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f52.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f52.doInitBody();
      }
      do {
        out.write("false");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f52.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f52 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f52.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f52);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f52);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f55(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f55 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f55.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f55.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f5);
    int _jspx_eval_fulong_005fpreference_005f55 = _jspx_th_fulong_005fpreference_005f55.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f55 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f55(_jspx_th_fulong_005fpreference_005f55, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f53(_jspx_th_fulong_005fpreference_005f55, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f55.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f55.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f55);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f55);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f55(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f55, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f55 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f55.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f55.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f55);
    int _jspx_eval_fulong_005fname_005f55 = _jspx_th_fulong_005fname_005f55.doStartTag();
    if (_jspx_eval_fulong_005fname_005f55 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f55 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f55.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f55.doInitBody();
      }
      do {
        out.write("process");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f55.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f55 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f55.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f55);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f55);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f53(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f55, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f53 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f53.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f53.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f55);
    int _jspx_eval_fulong_005fvalue_005f53 = _jspx_th_fulong_005fvalue_005f53.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f53 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f53 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f53.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f53.doInitBody();
      }
      do {
        out.write("blank");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f53.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f53 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f53.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f53);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f53);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f56(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f56 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f56.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f56.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f5);
    int _jspx_eval_fulong_005fpreference_005f56 = _jspx_th_fulong_005fpreference_005f56.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f56 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f56(_jspx_th_fulong_005fpreference_005f56, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f54(_jspx_th_fulong_005fpreference_005f56, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f56.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f56.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f56);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f56);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f56(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f56, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f56 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f56.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f56.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f56);
    int _jspx_eval_fulong_005fname_005f56 = _jspx_th_fulong_005fname_005f56.doStartTag();
    if (_jspx_eval_fulong_005fname_005f56 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f56 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f56.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f56.doInitBody();
      }
      do {
        out.write("createFixNode");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f56.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f56 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f56.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f56);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f56);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f54(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f56, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f54 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f54.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f54.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f56);
    int _jspx_eval_fulong_005fvalue_005f54 = _jspx_th_fulong_005fvalue_005f54.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f54 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f54 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f54.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f54.doInitBody();
      }
      do {
        out.write("false");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f54.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f54 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f54.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f54);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f54);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f57(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f57 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f57.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f57.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f5);
    int _jspx_eval_fulong_005fpreference_005f57 = _jspx_th_fulong_005fpreference_005f57.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f57 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f57(_jspx_th_fulong_005fpreference_005f57, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f55(_jspx_th_fulong_005fpreference_005f57, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f57.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f57.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f57);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f57);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f57(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f57, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f57 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f57.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f57.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f57);
    int _jspx_eval_fulong_005fname_005f57 = _jspx_th_fulong_005fname_005f57.doStartTag();
    if (_jspx_eval_fulong_005fname_005f57 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f57 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f57.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f57.doInitBody();
      }
      do {
        out.write("style");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f57.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f57 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f57.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f57);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f57);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f55(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f57, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f55 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f55.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f55.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f57);
    int _jspx_eval_fulong_005fvalue_005f55 = _jspx_th_fulong_005fvalue_005f55.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f55 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f55 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f55.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f55.doInitBody();
      }
      do {
        out.write("baocun");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f55.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f55 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f55.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f55);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f55);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f58(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f58 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f58.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f58.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f5);
    int _jspx_eval_fulong_005fpreference_005f58 = _jspx_th_fulong_005fpreference_005f58.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f58 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f58(_jspx_th_fulong_005fpreference_005f58, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f56(_jspx_th_fulong_005fpreference_005f58, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f58.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f58.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f58);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f58);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f58(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f58, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f58 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f58.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f58.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f58);
    int _jspx_eval_fulong_005fname_005f58 = _jspx_th_fulong_005fname_005f58.doStartTag();
    if (_jspx_eval_fulong_005fname_005f58 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f58 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f58.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f58.doInitBody();
      }
      do {
        out.write("hasIDParameter");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f58.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f58 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f58.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f58);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f58);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f56(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f58, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f56 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f56.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f56.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f58);
    int _jspx_eval_fulong_005fvalue_005f56 = _jspx_th_fulong_005fvalue_005f56.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f56 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f56 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f56.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f56.doInitBody();
      }
      do {
        out.write("false");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f56.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f56 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f56.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f56);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f56);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f59(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f59 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f59.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f59.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f5);
    int _jspx_eval_fulong_005fpreference_005f59 = _jspx_th_fulong_005fpreference_005f59.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f59 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f59(_jspx_th_fulong_005fpreference_005f59, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f59.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f59.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f59);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f59);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f59(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f59, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f59 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f59.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f59.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f59);
    int _jspx_eval_fulong_005fname_005f59 = _jspx_th_fulong_005fname_005f59.doStartTag();
    if (_jspx_eval_fulong_005fname_005f59 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f59 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f59.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f59.doInitBody();
      }
      do {
        out.write("size");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f59.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f59 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f59.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f59);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f59);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f60(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f60 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f60.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f60.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f5);
    int _jspx_eval_fulong_005fpreference_005f60 = _jspx_th_fulong_005fpreference_005f60.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f60 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f60(_jspx_th_fulong_005fpreference_005f60, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f60.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f60.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f60);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f60);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f60(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f60, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f60 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f60.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f60.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f60);
    int _jspx_eval_fulong_005fname_005f60 = _jspx_th_fulong_005fname_005f60.doStartTag();
    if (_jspx_eval_fulong_005fname_005f60 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f60 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f60.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f60.doInitBody();
      }
      do {
        out.write("principals");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f60.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f60 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f60.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f60);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f60);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f61(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f61 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f61.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f61.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f5);
    int _jspx_eval_fulong_005fpreference_005f61 = _jspx_th_fulong_005fpreference_005f61.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f61 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f61(_jspx_th_fulong_005fpreference_005f61, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f57(_jspx_th_fulong_005fpreference_005f61, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f61.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f61.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f61);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f61);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f61(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f61, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f61 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f61.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f61.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f61);
    int _jspx_eval_fulong_005fname_005f61 = _jspx_th_fulong_005fname_005f61.doStartTag();
    if (_jspx_eval_fulong_005fname_005f61 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f61 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f61.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f61.doInitBody();
      }
      do {
        out.write("default-values");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f61.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f61 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f61.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f61);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f61);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f57(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f61, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f57 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f57.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f57.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f61);
    int _jspx_eval_fulong_005fvalue_005f57 = _jspx_th_fulong_005fvalue_005f57.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f57 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f57 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f57.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f57.doInitBody();
      }
      do {
        out.write("propFbr equal $currentUser");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f57.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f57 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f57.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f57);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f57);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f62(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f62 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f62.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f62.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f5);
    int _jspx_eval_fulong_005fpreference_005f62 = _jspx_th_fulong_005fpreference_005f62.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f62 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f62(_jspx_th_fulong_005fpreference_005f62, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f58(_jspx_th_fulong_005fpreference_005f62, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f62.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f62.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f62);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f62);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f62(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f62, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f62 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f62.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f62.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f62);
    int _jspx_eval_fulong_005fname_005f62 = _jspx_th_fulong_005fname_005f62.doStartTag();
    if (_jspx_eval_fulong_005fname_005f62 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f62 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f62.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f62.doInitBody();
      }
      do {
        out.write("name");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f62.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f62 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f62.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f62);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f62);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f58(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f62, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f58 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f58.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f58.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f62);
    int _jspx_eval_fulong_005fvalue_005f58 = _jspx_th_fulong_005fvalue_005f58.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f58.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f58);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f58);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f63(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f63 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f63.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f63.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f5);
    int _jspx_eval_fulong_005fpreference_005f63 = _jspx_th_fulong_005fpreference_005f63.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f63 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f63(_jspx_th_fulong_005fpreference_005f63, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f59(_jspx_th_fulong_005fpreference_005f63, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f63.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f63.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f63);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f63);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f63(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f63, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f63 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f63.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f63.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f63);
    int _jspx_eval_fulong_005fname_005f63 = _jspx_th_fulong_005fname_005f63.doStartTag();
    if (_jspx_eval_fulong_005fname_005f63 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f63 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f63.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f63.doInitBody();
      }
      do {
        out.write("channel");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f63.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f63 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f63.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f63);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f63);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f59(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f63, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f59 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f59.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f59.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f63);
    int _jspx_eval_fulong_005fvalue_005f59 = _jspx_th_fulong_005fvalue_005f59.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f59 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f59 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f59.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f59.doInitBody();
      }
      do {
        out.write("channelBdyj2");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f59.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f59 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f59.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f59);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f59);
    return false;
  }

  private boolean _jspx_meth_fulong_005fportlet_005f6(javax.servlet.jsp.tagext.JspTag _jspx_th_site_005fchannel_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:portlet
    com.fulong.taglib.portal.PortletTag _jspx_th_fulong_005fportlet_005f6 = (com.fulong.taglib.portal.PortletTag) _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.get(com.fulong.taglib.portal.PortletTag.class);
    _jspx_th_fulong_005fportlet_005f6.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fportlet_005f6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_site_005fchannel_005f0);
    _jspx_th_fulong_005fportlet_005f6.setId("pt794187");
    _jspx_th_fulong_005fportlet_005f6.setType("resetbutton");
    int _jspx_eval_fulong_005fportlet_005f6 = _jspx_th_fulong_005fportlet_005f6.doStartTag();
    if (_jspx_eval_fulong_005fportlet_005f6 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fportlet_005f6 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fportlet_005f6.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fportlet_005f6.doInitBody();
      }
      do {
        if (_jspx_meth_fulong_005fpreference_005f64(_jspx_th_fulong_005fportlet_005f6, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f65(_jspx_th_fulong_005fportlet_005f6, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f66(_jspx_th_fulong_005fportlet_005f6, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f67(_jspx_th_fulong_005fportlet_005f6, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f68(_jspx_th_fulong_005fportlet_005f6, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f69(_jspx_th_fulong_005fportlet_005f6, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f70(_jspx_th_fulong_005fportlet_005f6, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fportlet_005f6.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fportlet_005f6 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fportlet_005f6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.reuse(_jspx_th_fulong_005fportlet_005f6);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.reuse(_jspx_th_fulong_005fportlet_005f6);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f64(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f6, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f64 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f64.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f64.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f6);
    int _jspx_eval_fulong_005fpreference_005f64 = _jspx_th_fulong_005fpreference_005f64.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f64 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f64(_jspx_th_fulong_005fpreference_005f64, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f60(_jspx_th_fulong_005fpreference_005f64, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f64.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f64.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f64);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f64);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f64(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f64, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f64 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f64.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f64.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f64);
    int _jspx_eval_fulong_005fname_005f64 = _jspx_th_fulong_005fname_005f64.doStartTag();
    if (_jspx_eval_fulong_005fname_005f64 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f64 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f64.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f64.doInitBody();
      }
      do {
        out.write("imgwidth");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f64.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f64 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f64.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f64);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f64);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f60(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f64, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f60 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f60.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f60.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f64);
    int _jspx_eval_fulong_005fvalue_005f60 = _jspx_th_fulong_005fvalue_005f60.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f60.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f60);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f60);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f65(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f6, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f65 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f65.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f65.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f6);
    int _jspx_eval_fulong_005fpreference_005f65 = _jspx_th_fulong_005fpreference_005f65.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f65 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f65(_jspx_th_fulong_005fpreference_005f65, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f61(_jspx_th_fulong_005fpreference_005f65, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f65.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f65.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f65);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f65);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f65(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f65, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f65 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f65.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f65.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f65);
    int _jspx_eval_fulong_005fname_005f65 = _jspx_th_fulong_005fname_005f65.doStartTag();
    if (_jspx_eval_fulong_005fname_005f65 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f65 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f65.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f65.doInitBody();
      }
      do {
        out.write("value");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f65.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f65 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f65.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f65);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f65);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f61(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f65, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f61 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f61.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f61.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f65);
    int _jspx_eval_fulong_005fvalue_005f61 = _jspx_th_fulong_005fvalue_005f61.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f61 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f61 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f61.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f61.doInitBody();
      }
      do {
        out.write('重');
        out.write('置');
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f61.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f61 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f61.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f61);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f61);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f66(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f6, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f66 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f66.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f66.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f6);
    int _jspx_eval_fulong_005fpreference_005f66 = _jspx_th_fulong_005fpreference_005f66.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f66 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f66(_jspx_th_fulong_005fpreference_005f66, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f62(_jspx_th_fulong_005fpreference_005f66, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f66.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f66.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f66);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f66);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f66(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f66, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f66 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f66.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f66.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f66);
    int _jspx_eval_fulong_005fname_005f66 = _jspx_th_fulong_005fname_005f66.doStartTag();
    if (_jspx_eval_fulong_005fname_005f66 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f66 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f66.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f66.doInitBody();
      }
      do {
        out.write("tabindex");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f66.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f66 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f66.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f66);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f66);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f62(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f66, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f62 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f62.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f62.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f66);
    int _jspx_eval_fulong_005fvalue_005f62 = _jspx_th_fulong_005fvalue_005f62.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f62.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f62);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f62);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f67(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f6, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f67 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f67.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f67.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f6);
    int _jspx_eval_fulong_005fpreference_005f67 = _jspx_th_fulong_005fpreference_005f67.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f67 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f67(_jspx_th_fulong_005fpreference_005f67, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f63(_jspx_th_fulong_005fpreference_005f67, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f67.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f67.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f67);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f67);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f67(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f67, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f67 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f67.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f67.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f67);
    int _jspx_eval_fulong_005fname_005f67 = _jspx_th_fulong_005fname_005f67.doStartTag();
    if (_jspx_eval_fulong_005fname_005f67 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f67 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f67.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f67.doInitBody();
      }
      do {
        out.write("imgsrc");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f67.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f67 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f67.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f67);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f67);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f63(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f67, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f63 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f63.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f63.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f67);
    int _jspx_eval_fulong_005fvalue_005f63 = _jspx_th_fulong_005fvalue_005f63.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f63.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f63);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f63);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f68(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f6, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f68 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f68.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f68.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f6);
    int _jspx_eval_fulong_005fpreference_005f68 = _jspx_th_fulong_005fpreference_005f68.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f68 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f68(_jspx_th_fulong_005fpreference_005f68, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f64(_jspx_th_fulong_005fpreference_005f68, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f68.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f68.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f68);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f68);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f68(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f68, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f68 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f68.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f68.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f68);
    int _jspx_eval_fulong_005fname_005f68 = _jspx_th_fulong_005fname_005f68.doStartTag();
    if (_jspx_eval_fulong_005fname_005f68 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f68 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f68.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f68.doInitBody();
      }
      do {
        out.write("style");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f68.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f68 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f68.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f68);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f68);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f64(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f68, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f64 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f64.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f64.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f68);
    int _jspx_eval_fulong_005fvalue_005f64 = _jspx_th_fulong_005fvalue_005f64.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f64 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f64 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f64.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f64.doInitBody();
      }
      do {
        out.write("baocun");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f64.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f64 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f64.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f64);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f64);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f69(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f6, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f69 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f69.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f69.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f6);
    int _jspx_eval_fulong_005fpreference_005f69 = _jspx_th_fulong_005fpreference_005f69.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f69 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f69(_jspx_th_fulong_005fpreference_005f69, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f65(_jspx_th_fulong_005fpreference_005f69, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f69.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f69.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f69);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f69);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f69(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f69, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f69 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f69.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f69.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f69);
    int _jspx_eval_fulong_005fname_005f69 = _jspx_th_fulong_005fname_005f69.doStartTag();
    if (_jspx_eval_fulong_005fname_005f69 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f69 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f69.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f69.doInitBody();
      }
      do {
        out.write("imgheight");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f69.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f69 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f69.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f69);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f69);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f65(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f69, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f65 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f65.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f65.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f69);
    int _jspx_eval_fulong_005fvalue_005f65 = _jspx_th_fulong_005fvalue_005f65.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f65.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f65);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f65);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f70(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f6, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f70 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f70.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f70.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f6);
    int _jspx_eval_fulong_005fpreference_005f70 = _jspx_th_fulong_005fpreference_005f70.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f70 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f70(_jspx_th_fulong_005fpreference_005f70, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f66(_jspx_th_fulong_005fpreference_005f70, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f70.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f70.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f70);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f70);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f70(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f70, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f70 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f70.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f70.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f70);
    int _jspx_eval_fulong_005fname_005f70 = _jspx_th_fulong_005fname_005f70.doStartTag();
    if (_jspx_eval_fulong_005fname_005f70 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f70 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f70.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f70.doInitBody();
      }
      do {
        out.write("imgstyle");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f70.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f70 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f70.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f70);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f70);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f66(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f70, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f66 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f66.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f66.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f70);
    int _jspx_eval_fulong_005fvalue_005f66 = _jspx_th_fulong_005fvalue_005f66.doStartTag();
    if (_jspx_th_fulong_005fvalue_005f66.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f66);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue_005fnobody.reuse(_jspx_th_fulong_005fvalue_005f66);
    return false;
  }

  private boolean _jspx_meth_fulong_005fportlet_005f7(javax.servlet.jsp.tagext.JspTag _jspx_th_site_005fchannel_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:portlet
    com.fulong.taglib.portal.PortletTag _jspx_th_fulong_005fportlet_005f7 = (com.fulong.taglib.portal.PortletTag) _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.get(com.fulong.taglib.portal.PortletTag.class);
    _jspx_th_fulong_005fportlet_005f7.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fportlet_005f7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_site_005fchannel_005f0);
    _jspx_th_fulong_005fportlet_005f7.setId("pt469875");
    _jspx_th_fulong_005fportlet_005f7.setType("page_clip");
    int _jspx_eval_fulong_005fportlet_005f7 = _jspx_th_fulong_005fportlet_005f7.doStartTag();
    if (_jspx_eval_fulong_005fportlet_005f7 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fportlet_005f7 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fportlet_005f7.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fportlet_005f7.doInitBody();
      }
      do {
        if (_jspx_meth_fulong_005fpreference_005f71(_jspx_th_fulong_005fportlet_005f7, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fpreference_005f72(_jspx_th_fulong_005fportlet_005f7, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fportlet_005f7.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fportlet_005f7 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fportlet_005f7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.reuse(_jspx_th_fulong_005fportlet_005f7);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fportlet_0026_005ftype_005fid.reuse(_jspx_th_fulong_005fportlet_005f7);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f71(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f7, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f71 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f71.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f71.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f7);
    int _jspx_eval_fulong_005fpreference_005f71 = _jspx_th_fulong_005fpreference_005f71.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f71 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f71(_jspx_th_fulong_005fpreference_005f71, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f71.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f71.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f71);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f71);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f71(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f71, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f71 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f71.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f71.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f71);
    int _jspx_eval_fulong_005fname_005f71 = _jspx_th_fulong_005fname_005f71.doStartTag();
    if (_jspx_eval_fulong_005fname_005f71 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f71 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f71.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f71.doInitBody();
      }
      do {
        out.write("clip-paths");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f71.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f71 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f71.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f71);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f71);
    return false;
  }

  private boolean _jspx_meth_fulong_005fpreference_005f72(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fportlet_005f7, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:preference
    com.fulong.taglib.portal.PreferenceTag _jspx_th_fulong_005fpreference_005f72 = (com.fulong.taglib.portal.PreferenceTag) _005fjspx_005ftagPool_005ffulong_005fpreference.get(com.fulong.taglib.portal.PreferenceTag.class);
    _jspx_th_fulong_005fpreference_005f72.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fpreference_005f72.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fportlet_005f7);
    int _jspx_eval_fulong_005fpreference_005f72 = _jspx_th_fulong_005fpreference_005f72.doStartTag();
    if (_jspx_eval_fulong_005fpreference_005f72 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_fulong_005fname_005f72(_jspx_th_fulong_005fpreference_005f72, _jspx_page_context))
          return true;
        if (_jspx_meth_fulong_005fvalue_005f67(_jspx_th_fulong_005fpreference_005f72, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_fulong_005fpreference_005f72.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_fulong_005fpreference_005f72.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f72);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fpreference.reuse(_jspx_th_fulong_005fpreference_005f72);
    return false;
  }

  private boolean _jspx_meth_fulong_005fname_005f72(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f72, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:name
    com.fulong.taglib.portal.NameTag _jspx_th_fulong_005fname_005f72 = (com.fulong.taglib.portal.NameTag) _005fjspx_005ftagPool_005ffulong_005fname.get(com.fulong.taglib.portal.NameTag.class);
    _jspx_th_fulong_005fname_005f72.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fname_005f72.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f72);
    int _jspx_eval_fulong_005fname_005f72 = _jspx_th_fulong_005fname_005f72.doStartTag();
    if (_jspx_eval_fulong_005fname_005f72 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fname_005f72 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fname_005f72.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fname_005f72.doInitBody();
      }
      do {
        out.write("source");
        int evalDoAfterBody = _jspx_th_fulong_005fname_005f72.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fname_005f72 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fname_005f72.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f72);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fname.reuse(_jspx_th_fulong_005fname_005f72);
    return false;
  }

  private boolean _jspx_meth_fulong_005fvalue_005f67(javax.servlet.jsp.tagext.JspTag _jspx_th_fulong_005fpreference_005f72, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fulong:value
    com.fulong.taglib.portal.ValueTag _jspx_th_fulong_005fvalue_005f67 = (com.fulong.taglib.portal.ValueTag) _005fjspx_005ftagPool_005ffulong_005fvalue.get(com.fulong.taglib.portal.ValueTag.class);
    _jspx_th_fulong_005fvalue_005f67.setPageContext(_jspx_page_context);
    _jspx_th_fulong_005fvalue_005f67.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005fpreference_005f72);
    int _jspx_eval_fulong_005fvalue_005f67 = _jspx_th_fulong_005fvalue_005f67.doStartTag();
    if (_jspx_eval_fulong_005fvalue_005f67 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fulong_005fvalue_005f67 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fulong_005fvalue_005f67.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fulong_005fvalue_005f67.doInitBody();
      }
      do {
        out.write("footer001");
        int evalDoAfterBody = _jspx_th_fulong_005fvalue_005f67.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fulong_005fvalue_005f67 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_fulong_005fvalue_005f67.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f67);
      return true;
    }
    _005fjspx_005ftagPool_005ffulong_005fvalue.reuse(_jspx_th_fulong_005fvalue_005f67);
    return false;
  }
}
