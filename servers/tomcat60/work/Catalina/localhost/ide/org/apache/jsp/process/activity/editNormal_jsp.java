package org.apache.jsp.process.activity;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class editNormal_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(5);
    _jspx_dependants.add("/WEB-INF/struts-bean.tld");
    _jspx_dependants.add("/WEB-INF/struts-logic.tld");
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
    _jspx_dependants.add("/WEB-INF/struts-tiles.tld");
    _jspx_dependants.add("/WEB-INF/fulong.tld");
  }

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
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
      out.write("\r\n");
      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("<title>活动属性</title>\r\n");
      out.write("<meta http-equiv=Content-Type content=\"text/html; charset=UTF-8\" />\r\n");
      out.write("<meta http-equiv=\"Cache-Control\" content=\"no-cache, must-revalidate\" />\r\n");
      out.write("<meta http-equiv=\"expires\" content=\"Wed, 26 Feb 1997 08:21:57 GMT\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"../style.css\" />\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"../script.js\"></script>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\">\r\n");
      out.write("\tvar PMEditor = parent.frames[\"mainFrame\"].PMEditor;\r\n");
      out.write("\tvar activity = PMEditor.selectedObject;\r\n");
      out.write("\twindow.onload = function() {\r\n");
      out.write("\t\tactForm[\"ID\"].value = activity.getId();\r\n");
      out.write("\t\tactForm[\"name\"].value = activity.getName();\r\n");
      out.write("\t\tactForm[\"description\"].value = activity.getDescription();\r\n");
      out.write("\t};\r\n");
      out.write("\tfunction updateName(name) {\r\n");
      out.write("\t\tactivity.setName(name);\r\n");
      out.write("\t\t//parent.frames[\"leftFrame\"].PMTree.Tree.getSelected().setTitle(name);\r\n");
      out.write("\t}\r\n");
      out.write("\tfunction updateDesc(desc) {\r\n");
      out.write("\t\tactivity.setDescription(desc);\r\n");
      out.write("\t}\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<table cellpadding=\"2\" cellspacing=\"0\" border=\"0\">\r\n");
      out.write("\t<form name=\"actForm\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td valign=\"top\">\r\n");
      out.write("\t\t<table cellpadding=\"2\" cellspacing=\"0\" border=\"0\">\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<th>活动ID：</th>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" name=\"ID\" size=\"25\" readonly=\"readonly\" disabled=\"disabled\" /></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<th>活动名称：</th>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" name=\"name\" size=\"25\" onblur=\"updateName(this.value)\" /></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t\t<td valign=\"top\">\r\n");
      out.write("\t\t<table  cellpadding=\"2\" cellspacing=\"0\" border=\"0\">\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<th>活动描述：</th>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td><textarea rows=\"4\" cols=\"40\" name=\"description\" onblur=\"updateDesc(this.value)\"></textarea></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t</form>\r\n");
      out.write("</table>\r\n");
      out.write("</body>\r\n");
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
}
