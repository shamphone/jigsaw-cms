package org.apache.jsp.system.jsp.ajax;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class osinfo_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/WEB-INF/jstripe.tld");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005finf_005fvolume_0026_005fvalue_005ffractions_005fnobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005finf_005fvolume_0026_005fvalue_005ffractions_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody.release();
    _005fjspx_005ftagPool_005finf_005fvolume_0026_005fvalue_005ffractions_005fnobody.release();
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    JspFactory _jspxFactory = null;
    PageContext pageContext = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      _jspxFactory = JspFactory.getDefaultFactory();
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, false, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<div class=\"shadow\" style=\"clear: none;\">\n");
      out.write("    <div class=\"info\">\n");
      out.write("        <p>\n");
      out.write("            ");
      if (_jspx_meth_spring_005fmessage_005f0(_jspx_page_context))
        return;
      out.write("<span class=\"value\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${runtime.osName}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("</span>\n");
      out.write("            ");
      if (_jspx_meth_spring_005fmessage_005f1(_jspx_page_context))
        return;
      out.write("<span class=\"value\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${runtime.osVersion}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("</span>\n");
      out.write("            ");
      if (_jspx_meth_spring_005fmessage_005f2(_jspx_page_context))
        return;
      out.write("<span class=\"value\">");
      if (_jspx_meth_inf_005fvolume_005f0(_jspx_page_context))
        return;
      out.write("</span>\n");
      out.write("            ");
      if (_jspx_meth_spring_005fmessage_005f3(_jspx_page_context))
        return;
      out.write("<span class=\"value\">");
      if (_jspx_meth_inf_005fvolume_005f1(_jspx_page_context))
        return;
      out.write("</span>\n");
      out.write("            ");
      if (_jspx_meth_spring_005fmessage_005f4(_jspx_page_context))
        return;
      out.write("<span class=\"value\">");
      if (_jspx_meth_inf_005fvolume_005f2(_jspx_page_context))
        return;
      out.write("</span>\n");
      out.write("            ");
      if (_jspx_meth_spring_005fmessage_005f5(_jspx_page_context))
        return;
      out.write("<span class=\"value\">");
      if (_jspx_meth_inf_005fvolume_005f3(_jspx_page_context))
        return;
      out.write("</span>\n");
      out.write("            ");
      if (_jspx_meth_spring_005fmessage_005f6(_jspx_page_context))
        return;
      out.write("<span class=\"value\">");
      if (_jspx_meth_inf_005fvolume_005f4(_jspx_page_context))
        return;
      out.write("</span>\n");
      out.write("        </p>\n");
      out.write("    </div>\n");
      out.write("</div>\n");
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

  private boolean _jspx_meth_spring_005fmessage_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  spring:message
    org.springframework.web.servlet.tags.MessageTag _jspx_th_spring_005fmessage_005f0 = (org.springframework.web.servlet.tags.MessageTag) _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody.get(org.springframework.web.servlet.tags.MessageTag.class);
    _jspx_th_spring_005fmessage_005f0.setPageContext(_jspx_page_context);
    _jspx_th_spring_005fmessage_005f0.setParent(null);
    _jspx_th_spring_005fmessage_005f0.setCode("probe.jsp.os.card.name");
    int[] _jspx_push_body_count_spring_005fmessage_005f0 = new int[] { 0 };
    try {
      int _jspx_eval_spring_005fmessage_005f0 = _jspx_th_spring_005fmessage_005f0.doStartTag();
      if (_jspx_th_spring_005fmessage_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_spring_005fmessage_005f0[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_spring_005fmessage_005f0.doCatch(_jspx_exception);
    } finally {
      _jspx_th_spring_005fmessage_005f0.doFinally();
      _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody.reuse(_jspx_th_spring_005fmessage_005f0);
    }
    return false;
  }

  private boolean _jspx_meth_spring_005fmessage_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  spring:message
    org.springframework.web.servlet.tags.MessageTag _jspx_th_spring_005fmessage_005f1 = (org.springframework.web.servlet.tags.MessageTag) _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody.get(org.springframework.web.servlet.tags.MessageTag.class);
    _jspx_th_spring_005fmessage_005f1.setPageContext(_jspx_page_context);
    _jspx_th_spring_005fmessage_005f1.setParent(null);
    _jspx_th_spring_005fmessage_005f1.setCode("probe.jsp.os.card.version");
    int[] _jspx_push_body_count_spring_005fmessage_005f1 = new int[] { 0 };
    try {
      int _jspx_eval_spring_005fmessage_005f1 = _jspx_th_spring_005fmessage_005f1.doStartTag();
      if (_jspx_th_spring_005fmessage_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_spring_005fmessage_005f1[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_spring_005fmessage_005f1.doCatch(_jspx_exception);
    } finally {
      _jspx_th_spring_005fmessage_005f1.doFinally();
      _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody.reuse(_jspx_th_spring_005fmessage_005f1);
    }
    return false;
  }

  private boolean _jspx_meth_spring_005fmessage_005f2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  spring:message
    org.springframework.web.servlet.tags.MessageTag _jspx_th_spring_005fmessage_005f2 = (org.springframework.web.servlet.tags.MessageTag) _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody.get(org.springframework.web.servlet.tags.MessageTag.class);
    _jspx_th_spring_005fmessage_005f2.setPageContext(_jspx_page_context);
    _jspx_th_spring_005fmessage_005f2.setParent(null);
    _jspx_th_spring_005fmessage_005f2.setCode("probe.jsp.os.card.totalMemory");
    int[] _jspx_push_body_count_spring_005fmessage_005f2 = new int[] { 0 };
    try {
      int _jspx_eval_spring_005fmessage_005f2 = _jspx_th_spring_005fmessage_005f2.doStartTag();
      if (_jspx_th_spring_005fmessage_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_spring_005fmessage_005f2[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_spring_005fmessage_005f2.doCatch(_jspx_exception);
    } finally {
      _jspx_th_spring_005fmessage_005f2.doFinally();
      _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody.reuse(_jspx_th_spring_005fmessage_005f2);
    }
    return false;
  }

  private boolean _jspx_meth_inf_005fvolume_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  inf:volume
    org.jstripe.tags.VolumeTag _jspx_th_inf_005fvolume_005f0 = (org.jstripe.tags.VolumeTag) _005fjspx_005ftagPool_005finf_005fvolume_0026_005fvalue_005ffractions_005fnobody.get(org.jstripe.tags.VolumeTag.class);
    _jspx_th_inf_005fvolume_005f0.setPageContext(_jspx_page_context);
    _jspx_th_inf_005fvolume_005f0.setParent(null);
    _jspx_th_inf_005fvolume_005f0.setValue(((java.lang.Long) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${runtime.totalPhysicalMemorySize}", java.lang.Long.class, (PageContext)_jspx_page_context, null, false)).longValue());
    _jspx_th_inf_005fvolume_005f0.setFractions(2);
    int _jspx_eval_inf_005fvolume_005f0 = _jspx_th_inf_005fvolume_005f0.doStartTag();
    if (_jspx_th_inf_005fvolume_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005finf_005fvolume_0026_005fvalue_005ffractions_005fnobody.reuse(_jspx_th_inf_005fvolume_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005finf_005fvolume_0026_005fvalue_005ffractions_005fnobody.reuse(_jspx_th_inf_005fvolume_005f0);
    return false;
  }

  private boolean _jspx_meth_spring_005fmessage_005f3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  spring:message
    org.springframework.web.servlet.tags.MessageTag _jspx_th_spring_005fmessage_005f3 = (org.springframework.web.servlet.tags.MessageTag) _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody.get(org.springframework.web.servlet.tags.MessageTag.class);
    _jspx_th_spring_005fmessage_005f3.setPageContext(_jspx_page_context);
    _jspx_th_spring_005fmessage_005f3.setParent(null);
    _jspx_th_spring_005fmessage_005f3.setCode("probe.jsp.os.card.freeMemory");
    int[] _jspx_push_body_count_spring_005fmessage_005f3 = new int[] { 0 };
    try {
      int _jspx_eval_spring_005fmessage_005f3 = _jspx_th_spring_005fmessage_005f3.doStartTag();
      if (_jspx_th_spring_005fmessage_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_spring_005fmessage_005f3[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_spring_005fmessage_005f3.doCatch(_jspx_exception);
    } finally {
      _jspx_th_spring_005fmessage_005f3.doFinally();
      _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody.reuse(_jspx_th_spring_005fmessage_005f3);
    }
    return false;
  }

  private boolean _jspx_meth_inf_005fvolume_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  inf:volume
    org.jstripe.tags.VolumeTag _jspx_th_inf_005fvolume_005f1 = (org.jstripe.tags.VolumeTag) _005fjspx_005ftagPool_005finf_005fvolume_0026_005fvalue_005ffractions_005fnobody.get(org.jstripe.tags.VolumeTag.class);
    _jspx_th_inf_005fvolume_005f1.setPageContext(_jspx_page_context);
    _jspx_th_inf_005fvolume_005f1.setParent(null);
    _jspx_th_inf_005fvolume_005f1.setValue(((java.lang.Long) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${runtime.freePhysicalMemorySize}", java.lang.Long.class, (PageContext)_jspx_page_context, null, false)).longValue());
    _jspx_th_inf_005fvolume_005f1.setFractions(2);
    int _jspx_eval_inf_005fvolume_005f1 = _jspx_th_inf_005fvolume_005f1.doStartTag();
    if (_jspx_th_inf_005fvolume_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005finf_005fvolume_0026_005fvalue_005ffractions_005fnobody.reuse(_jspx_th_inf_005fvolume_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005finf_005fvolume_0026_005fvalue_005ffractions_005fnobody.reuse(_jspx_th_inf_005fvolume_005f1);
    return false;
  }

  private boolean _jspx_meth_spring_005fmessage_005f4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  spring:message
    org.springframework.web.servlet.tags.MessageTag _jspx_th_spring_005fmessage_005f4 = (org.springframework.web.servlet.tags.MessageTag) _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody.get(org.springframework.web.servlet.tags.MessageTag.class);
    _jspx_th_spring_005fmessage_005f4.setPageContext(_jspx_page_context);
    _jspx_th_spring_005fmessage_005f4.setParent(null);
    _jspx_th_spring_005fmessage_005f4.setCode("probe.jsp.os.card.committedVirtualMemory");
    int[] _jspx_push_body_count_spring_005fmessage_005f4 = new int[] { 0 };
    try {
      int _jspx_eval_spring_005fmessage_005f4 = _jspx_th_spring_005fmessage_005f4.doStartTag();
      if (_jspx_th_spring_005fmessage_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_spring_005fmessage_005f4[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_spring_005fmessage_005f4.doCatch(_jspx_exception);
    } finally {
      _jspx_th_spring_005fmessage_005f4.doFinally();
      _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody.reuse(_jspx_th_spring_005fmessage_005f4);
    }
    return false;
  }

  private boolean _jspx_meth_inf_005fvolume_005f2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  inf:volume
    org.jstripe.tags.VolumeTag _jspx_th_inf_005fvolume_005f2 = (org.jstripe.tags.VolumeTag) _005fjspx_005ftagPool_005finf_005fvolume_0026_005fvalue_005ffractions_005fnobody.get(org.jstripe.tags.VolumeTag.class);
    _jspx_th_inf_005fvolume_005f2.setPageContext(_jspx_page_context);
    _jspx_th_inf_005fvolume_005f2.setParent(null);
    _jspx_th_inf_005fvolume_005f2.setValue(((java.lang.Long) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${runtime.committedVirtualMemorySize}", java.lang.Long.class, (PageContext)_jspx_page_context, null, false)).longValue());
    _jspx_th_inf_005fvolume_005f2.setFractions(2);
    int _jspx_eval_inf_005fvolume_005f2 = _jspx_th_inf_005fvolume_005f2.doStartTag();
    if (_jspx_th_inf_005fvolume_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005finf_005fvolume_0026_005fvalue_005ffractions_005fnobody.reuse(_jspx_th_inf_005fvolume_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005finf_005fvolume_0026_005fvalue_005ffractions_005fnobody.reuse(_jspx_th_inf_005fvolume_005f2);
    return false;
  }

  private boolean _jspx_meth_spring_005fmessage_005f5(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  spring:message
    org.springframework.web.servlet.tags.MessageTag _jspx_th_spring_005fmessage_005f5 = (org.springframework.web.servlet.tags.MessageTag) _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody.get(org.springframework.web.servlet.tags.MessageTag.class);
    _jspx_th_spring_005fmessage_005f5.setPageContext(_jspx_page_context);
    _jspx_th_spring_005fmessage_005f5.setParent(null);
    _jspx_th_spring_005fmessage_005f5.setCode("probe.jsp.os.card.totalSwap");
    int[] _jspx_push_body_count_spring_005fmessage_005f5 = new int[] { 0 };
    try {
      int _jspx_eval_spring_005fmessage_005f5 = _jspx_th_spring_005fmessage_005f5.doStartTag();
      if (_jspx_th_spring_005fmessage_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_spring_005fmessage_005f5[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_spring_005fmessage_005f5.doCatch(_jspx_exception);
    } finally {
      _jspx_th_spring_005fmessage_005f5.doFinally();
      _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody.reuse(_jspx_th_spring_005fmessage_005f5);
    }
    return false;
  }

  private boolean _jspx_meth_inf_005fvolume_005f3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  inf:volume
    org.jstripe.tags.VolumeTag _jspx_th_inf_005fvolume_005f3 = (org.jstripe.tags.VolumeTag) _005fjspx_005ftagPool_005finf_005fvolume_0026_005fvalue_005ffractions_005fnobody.get(org.jstripe.tags.VolumeTag.class);
    _jspx_th_inf_005fvolume_005f3.setPageContext(_jspx_page_context);
    _jspx_th_inf_005fvolume_005f3.setParent(null);
    _jspx_th_inf_005fvolume_005f3.setValue(((java.lang.Long) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${runtime.totalSwapSpaceSize}", java.lang.Long.class, (PageContext)_jspx_page_context, null, false)).longValue());
    _jspx_th_inf_005fvolume_005f3.setFractions(2);
    int _jspx_eval_inf_005fvolume_005f3 = _jspx_th_inf_005fvolume_005f3.doStartTag();
    if (_jspx_th_inf_005fvolume_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005finf_005fvolume_0026_005fvalue_005ffractions_005fnobody.reuse(_jspx_th_inf_005fvolume_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005finf_005fvolume_0026_005fvalue_005ffractions_005fnobody.reuse(_jspx_th_inf_005fvolume_005f3);
    return false;
  }

  private boolean _jspx_meth_spring_005fmessage_005f6(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  spring:message
    org.springframework.web.servlet.tags.MessageTag _jspx_th_spring_005fmessage_005f6 = (org.springframework.web.servlet.tags.MessageTag) _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody.get(org.springframework.web.servlet.tags.MessageTag.class);
    _jspx_th_spring_005fmessage_005f6.setPageContext(_jspx_page_context);
    _jspx_th_spring_005fmessage_005f6.setParent(null);
    _jspx_th_spring_005fmessage_005f6.setCode("probe.jsp.os.card.freeSwap");
    int[] _jspx_push_body_count_spring_005fmessage_005f6 = new int[] { 0 };
    try {
      int _jspx_eval_spring_005fmessage_005f6 = _jspx_th_spring_005fmessage_005f6.doStartTag();
      if (_jspx_th_spring_005fmessage_005f6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_spring_005fmessage_005f6[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_spring_005fmessage_005f6.doCatch(_jspx_exception);
    } finally {
      _jspx_th_spring_005fmessage_005f6.doFinally();
      _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody.reuse(_jspx_th_spring_005fmessage_005f6);
    }
    return false;
  }

  private boolean _jspx_meth_inf_005fvolume_005f4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  inf:volume
    org.jstripe.tags.VolumeTag _jspx_th_inf_005fvolume_005f4 = (org.jstripe.tags.VolumeTag) _005fjspx_005ftagPool_005finf_005fvolume_0026_005fvalue_005ffractions_005fnobody.get(org.jstripe.tags.VolumeTag.class);
    _jspx_th_inf_005fvolume_005f4.setPageContext(_jspx_page_context);
    _jspx_th_inf_005fvolume_005f4.setParent(null);
    _jspx_th_inf_005fvolume_005f4.setValue(((java.lang.Long) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${runtime.freeSwapSpaceSize}", java.lang.Long.class, (PageContext)_jspx_page_context, null, false)).longValue());
    _jspx_th_inf_005fvolume_005f4.setFractions(2);
    int _jspx_eval_inf_005fvolume_005f4 = _jspx_th_inf_005fvolume_005f4.doStartTag();
    if (_jspx_th_inf_005fvolume_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005finf_005fvolume_0026_005fvalue_005ffractions_005fnobody.reuse(_jspx_th_inf_005fvolume_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005finf_005fvolume_0026_005fvalue_005ffractions_005fnobody.reuse(_jspx_th_inf_005fvolume_005f4);
    return false;
  }
}
