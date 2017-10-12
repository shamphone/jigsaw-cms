package org.apache.jsp.site.editor.plugins.portlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class edit_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(4);
    _jspx_dependants.add("/WEB-INF/struts-bean.tld");
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
    _jspx_dependants.add("/WEB-INF/struts-logic.tld");
    _jspx_dependants.add("/WEB-INF/struts-tiles.tld");
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
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<html>\r\n");
      out.write("  <head>\r\n");
      out.write("    <title>编辑占位符</title>\r\n");
      out.write("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n");
      out.write("    <meta http-equiv=\"EXPIRES\" content=\"0\"/>\r\n");
      out.write("    <meta http-equiv=\"Pragma\" Content=\"No-cach\" />\r\n");
      out.write("    <base target=\"_self\"/>\r\n");
      out.write("    <script type=\"text/javascript\">\r\n");
      out.write("      function doPost(){\r\n");
      out.write("        var param=window.dialogArguments;\r\n");
      out.write("        oForm.action=\"");
      if (_jspx_meth_html_005frewrite_005f0(_jspx_page_context))
        return;
      out.write("\";\r\n");
      out.write("        oForm.elements(\"portlet.window.owner\").value=param.portlet;\r\n");
      out.write("        oForm.elements(\"portlet.pref\").value=param.preferences;\r\n");
      out.write("        oForm.elements(\"portlet.type\").value= param.type;\r\n");
      out.write("        oForm.elements(\"channel.page\").value= param.page;\r\n");
      out.write("        if(param.channel!=null)\r\n");
      out.write("            oForm.elements(\"channel\").value=param.channel.ID;\r\n");
      out.write("        if(param.definition!=null)\r\n");
      out.write("            oForm.elements(\"definition\").value=param.definition;\r\n");
      out.write("        if(param.formDefinition!=null)\r\n");
      out.write("            oForm.elements(\"formDefinition\").value=param.form.formDefinition;\r\n");
      out.write("        if(param.node!=null)\r\n");
      out.write("            oForm.elements(\"node\").value=param.node;\r\n");
      out.write("        if(param.form){\r\n");
      out.write("          if(param.form.definition!=null)\r\n");
      out.write("          \toForm.elements(\"formDefinition\").value=param.form.definition;\r\n");
      out.write("          if(param.form.node!=null)\r\n");
      out.write("          oForm.elements(\"node\").value=param.form.node;\r\n");
      out.write("        }\r\n");
      out.write("        oForm.submit();\r\n");
      out.write("      }\r\n");
      out.write("      </script>\r\n");
      out.write("      </head>\r\n");
      out.write("      <body onload=\"doPost()\">\r\n");
      out.write("        <form action=\"\" method=\"POST\" id=\"oForm\">\r\n");
      out.write("          <input type=\"hidden\" name=\"portlet.mode\" value=\"edit\"/>\r\n");
      out.write("          <input type=\"hidden\" name=\"portlet.window.owner\"/>\r\n");
      out.write("          <input type=\"hidden\" name=\"definition\"/>\r\n");
      out.write("          <input type=\"hidden\" name=\"formDefinition\"/>\r\n");
      out.write("          <input type=\"hidden\" name=\"node\"/>\r\n");
      out.write("          <input type=\"hidden\" name=\"portlet.pref\"/>\r\n");
      out.write("          <input type=\"hidden\" name=\"portlet.type\"/>\r\n");
      out.write("          <input type=\"hidden\" name=\"channel.page\"/>\r\n");
      out.write("          <input type=\"hidden\" name=\"channel\"/>\r\n");
      out.write("        </form>\r\n");
      out.write("      </body>\r\n");
      out.write("    </html>\r\n");
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
    _jspx_th_html_005frewrite_005f0.setModule("/site/editor");
    _jspx_th_html_005frewrite_005f0.setPage("/editPortlet.do");
    int _jspx_eval_html_005frewrite_005f0 = _jspx_th_html_005frewrite_005f0.doStartTag();
    if (_jspx_th_html_005frewrite_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f0);
    return false;
  }
}
