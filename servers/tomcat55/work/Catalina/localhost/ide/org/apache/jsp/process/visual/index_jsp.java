package org.apache.jsp.process.visual;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(4);
    _jspx_dependants.add("/WEB-INF/struts-bean.tld");
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
    _jspx_dependants.add("/WEB-INF/struts-logic.tld");
    _jspx_dependants.add("/WEB-INF/struts-tiles.tld");
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

      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("<title>流程设计</title>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"classes/pms.js\"></script>\r\n");
      out.write("</head>\r\n");
      out.write("<frameset rows=\"30,*\" cols=\"*\" frameborder=\"no\" border=\"1\" framespacing=\"0\">\r\n");
      out.write("\t<frame src=\"toolbar.jsp?id=");
 if(request.getParameter("id")!=null) out.print(request.getParameter("id")); 
      out.write("\" name=\"topFrame\" scrolling=\"No\" noresize=\"noresize\" id=\"topFrame\" title=\"topFrame\">\r\n");
      out.write("\t<frameset cols=\"230,*\" frameborder=\"1\" border=\"1\" framespacing=\"1\">\r\n");
      out.write("\t\t<frame marginwidth=\"0\" marginheight=\"0\" src=\"left.do?id=");
 if(request.getParameter("id")!=null) out.print(request.getParameter("id")); 
      out.write("\" name=\"leftFrame\" scrolling=\"No\" id=\"leftFrame\" title=\"流程列表\"></frame>\r\n");
      out.write("\t\t<frameset rows=\"*,120\">\r\n");
      out.write("\t\t<frame marginwidth=\"0\" marginheight=\"0\"  src=\"blank.jsp\" name=\"mainFrame\" id=\"mainFrame\" title=\"流程设计器\"></frame>\r\n");
      out.write("\t\t<frame marginwidth=\"0\" marginheight=\"0\"  src=\"blank.jsp\" name=\"rightFrame\" id=\"rightFrame\" title=\"属性编辑器\" scrolling=\"no\"></frame>\r\n");
      out.write("\t\t</frameset>\r\n");
      out.write("\t</frameset>\r\n");
      out.write("</frameset>\r\n");
      out.write("<noframes>\r\n");
      out.write("<body><span>本浏览器不支持框架页面。</span>\r\n");
      out.write("</body>\r\n");
      out.write("</noframes>\r\n");
      out.write("\r\n");
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
