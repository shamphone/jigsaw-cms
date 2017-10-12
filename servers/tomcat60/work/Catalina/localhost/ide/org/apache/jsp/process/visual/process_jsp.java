package org.apache.jsp.process.visual;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class process_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(4);
    _jspx_dependants.add("/WEB-INF/struts-bean.tld");
    _jspx_dependants.add("/WEB-INF/struts-logic.tld");
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
    _jspx_dependants.add("/WEB-INF/struts-tiles.tld");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.release();
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
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\">\r\n");
      out.write("<html xmlns:v=\"urn:schemas-microsoft-com:vml\">\r\n");
      out.write("<HEAD>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n");
      out.write("<meta http-equiv=\"EXPIRES\" content=\"0\" />\r\n");
      out.write("<meta http-equiv=\"Pragma\" Content=\"No-cach\" />\r\n");
      out.write("<TITLE>流程定义</TITLE>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"");
      if (_jspx_meth_html_005frewrite_005f0(_jspx_page_context))
        return;
      out.write("\"></script>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"");
      if (_jspx_meth_html_005frewrite_005f1(_jspx_page_context))
        return;
      out.write("\"></script>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"pmsconfig.js\"></script>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"classes/pmspin.js\"></script>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"classes/pmdefinition.js\"></script>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"classes/pmactivity.js\"></script>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"classes/pmeditor.js\"></script>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"classes/pmtransition.js\"></script>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"classes/pmparameters.js\"></script>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"classes/pmvmlproperties.js\"></script>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"classes/pmevents.js\"></script>\r\n");
      out.write("<xml id=\"oXML\" src=\"../process/source.do?id=");
      out.print(request.getParameter("processID"));
      out.write("&timestamp=");
      out.print( System.currentTimeMillis() );
      out.write("\"></xml>\r\n");
      out.write("</HEAD>\r\n");
      out.write("<BODY onload='PMEditor.init()' onkeydown=\"PMKBEventHandlers.fire()\">\r\n");
      out.write("<div id=\"VMLEditor\"  onclick=\"PMEditor_FocusOnProcess(this)\">\r\n");
      out.write("\t<v:shapetype id=\"laure\" coordorigin=\"-120 50\" coordsize=\"240 240\" o:master=\"True\" style=\"top:1;left:1;width:50;height:50\" path=\"m 1,28 l -143,168, 1,308, 140,168 x e\"></v:shapetype>\r\n");
      out.write("</div>\r\n");
      out.write("<v:group id=\"FlowVML\" coordsize=\"30000,25000\" style=\"top:1px;left:1px;width:6000px;height:5000px;position:absolute;\">\r\n");
      out.write("</v:group><textarea id=\"CodeEditor\" rows=\"100\" cols=\"100\" style=\"display:none\" wrap=\"off\"></textarea>\r\n");
      out.write("</BODY>\r\n");
      out.write("</HTML>\r\n");
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

  private boolean _jspx_meth_html_005frewrite_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f0 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f0.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f0.setParent(null);
    // /process/visual/process.jsp(14,58) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f0.setModule("/common");
    // /process/visual/process.jsp(14,58) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f0.setPage("/script/common.js");
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
    // /process/visual/process.jsp(15,58) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f1.setModule("/common");
    // /process/visual/process.jsp(15,58) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f1.setPage("/script/ajax.js");
    int _jspx_eval_html_005frewrite_005f1 = _jspx_th_html_005frewrite_005f1.doStartTag();
    if (_jspx_th_html_005frewrite_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f1);
    return false;
  }
}
