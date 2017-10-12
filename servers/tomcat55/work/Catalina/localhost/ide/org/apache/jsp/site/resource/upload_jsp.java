package org.apache.jsp.site.resource;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class upload_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(3);
    _jspx_dependants.add("/WEB-INF/struts-bean.tld");
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
    _jspx_dependants.add("/WEB-INF/struts-logic.tld");
  }

  public Object getDependants() {
    return _jspx_dependants;
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
      out.write("<title>文件上传</title>\r\n");
      out.write("<meta http-equiv=Content-Type content=\"text/html; charset=UTF-8\" />\r\n");
      out.write("<meta http-equiv=\"Cache-Control\" content=\"no-cache, must-revalidate\" />\r\n");
      out.write("<meta http-equiv=\"expires\" content=\"Wed, 26 Feb 1997 08:21:57 GMT\" />\r\n");
      out.write("<base target=\"_self\"/>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("var doRefresh = '");
      out.print( request.getParameter("refresh"));
      out.write("';\r\n");
      out.write("function doInit(){\r\n");
      out.write("\tif(doRefresh == \"true\"){\r\n");
      out.write("\t\tvar node = window.parent.oTree.getSelected();\r\n");
      out.write("\t\tnode.click();\r\n");
      out.write("\t}\r\n");
      out.write("};\r\n");
      out.write("function doSubmit(oBtn){\r\n");
      out.write("\tvar oForm = oBtn.form;\r\n");
      out.write("\toBtn.disabled = true;\r\n");
      out.write("\toForm.elements(\"folder\").value = window.parent.oTree.getSelected().resource.getContextPath();\r\n");
      out.write("\toForm.elements(\"template\").value = window.parent.oTemplate.name;\r\n");
      out.write("\toForm.submit();\r\n");
      out.write("}\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body bgcolor=\"buttonface\" style=\"padding: 0px; margin: 0px\" onload=\"doInit()\">\r\n");
      out.write("<form action=\"upload.do\" enctype=\"multipart/form-data\" method=\"post\" style=\"padding: 0px; margin: 0px\"><input type=\"file\" name=\"file[0]\" style=\"width:500px\"/>\r\n");
      out.write("<input type=\"hidden\" name=\"template\" /><input type=\"hidden\" name=\"folder\" value=\"/\"/> \r\n");
      out.write("<input type=\"button\" value=\"上传\" onclick=\"doSubmit(this)\"/></form>\r\n");
      out.write("</body>\r\n");
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
