package org.apache.jsp.common.calendar;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class calendar_js_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/javascript; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!-- import the calendar script -->\r\n");
      out.write("document.write('<script type=\"text/javascript\" src=\"");
      if (_jspx_meth_html_005frewrite_005f0(_jspx_page_context))
        return;
      out.write("\"></script>');\r\n");
      out.write("<!-- import the language module -->\r\n");
      out.write("document.write('<script type=\"text/javascript\" src=\"");
      if (_jspx_meth_html_005frewrite_005f1(_jspx_page_context))
        return;
      out.write("\"></script>');\r\n");
      out.write("document.write('<link rel=\"stylesheet\" type=\"text/css\" media=\"all\" href=\"");
      if (_jspx_meth_html_005frewrite_005f2(_jspx_page_context))
        return;
      out.write("\" title=\"Aqua\" />');\r\n");
      out.write("<!-- helper script that uses the calendar -->\r\n");
      out.write("var _dynarch_popupCalendar=null;\r\n");
      out.write("\r\n");
      out.write("function showCalendar(btn, el, format, showsTime, showsOtherMonths) {\r\n");
      out.write("    if (_dynarch_popupCalendar != null) {\r\n");
      out.write("      _dynarch_popupCalendar.hide();\r\n");
      out.write("    } else {\r\n");
      out.write("      var cal = new Calendar(1, null, editor_selected, editor_closeHandler);\r\n");
      out.write("      if (typeof showsTime == \"string\") {\r\n");
      out.write("        cal.showsTime = true;\r\n");
      out.write("        cal.time24 = (showsTime == \"24\");\r\n");
      out.write("      }\r\n");
      out.write("      if (showsOtherMonths) {\r\n");
      out.write("        cal.showsOtherMonths = true;\r\n");
      out.write("      }\r\n");
      out.write("      _dynarch_popupCalendar = cal;\r\n");
      out.write("      cal.setRange(1900, 2070);\r\n");
      out.write("      cal.create();\r\n");
      out.write("    }\r\n");
      out.write("    _dynarch_popupCalendar.setDateFormat(format);\r\n");
      out.write("    _dynarch_popupCalendar.parseDate(el.value);\r\n");
      out.write("    _dynarch_popupCalendar.sel = el;\r\n");
      out.write("    _dynarch_popupCalendar.showAtElement(btn, \"Br\");\r\n");
      out.write("\r\n");
      out.write("    return false;\r\n");
      out.write("  }\r\n");
      out.write("  function editor_selected(cal, date) {\r\n");
      out.write("    cal.sel.value = date;\r\n");
      out.write("    if (cal.dateClicked )\r\n");
      out.write("      cal.callCloseHandler();\r\n");
      out.write("  }\r\n");
      out.write("\r\n");
      out.write("  function editor_closeHandler(cal) {\r\n");
      out.write("    cal.hide();\r\n");
      out.write("    _dynarch_popupCalendar = null;\r\n");
      out.write("  }");
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
    _jspx_th_html_005frewrite_005f0.setPage("/calendar/calendar.js");
    int _jspx_eval_html_005frewrite_005f0 = _jspx_th_html_005frewrite_005f0.doStartTag();
    if (_jspx_th_html_005frewrite_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f0);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f1 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f1.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f1.setParent(null);
    _jspx_th_html_005frewrite_005f1.setModule("/common");
    _jspx_th_html_005frewrite_005f1.setPage("/calendar/lang/cn_utf8.js");
    int _jspx_eval_html_005frewrite_005f1 = _jspx_th_html_005frewrite_005f1.doStartTag();
    if (_jspx_th_html_005frewrite_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f1);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f2 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f2.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f2.setParent(null);
    _jspx_th_html_005frewrite_005f2.setModule("/common");
    _jspx_th_html_005frewrite_005f2.setPage("/calendar/skins/aqua/theme.css");
    int _jspx_eval_html_005frewrite_005f2 = _jspx_th_html_005frewrite_005f2.doStartTag();
    if (_jspx_th_html_005frewrite_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f2);
    return false;
  }
}
