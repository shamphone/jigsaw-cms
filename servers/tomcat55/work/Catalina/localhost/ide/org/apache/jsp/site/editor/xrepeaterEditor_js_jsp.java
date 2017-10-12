package org.apache.jsp.site.editor;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class xrepeaterEditor_js_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

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
      response.setContentType("text/javascript; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("/**\r\n");
      out.write(" * 配置在页面上使用的按钮\r\n");
      out.write(" */\r\n");
      out.write("FCKConfig.ToolbarSets[\"Default\"] = [\r\n");
      out.write("\t['Source','Cut','Copy','Paste','PasteText','PasteWord','-','Print','SpellCheck'],\r\n");
      out.write("\t['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],\r\n");
      out.write("\t['Bold','Italic','Underline','StrikeThrough','-','Subscript','Superscript'],\r\n");
      out.write("\t['OrderedList','UnorderedList','-','Outdent','Indent','Blockquote'],\r\n");
      out.write("\t['JustifyLeft','JustifyCenter','JustifyRight','JustifyFull'],\r\n");
      out.write("\t['Link','Unlink','Anchor'],\r\n");
      out.write("\t['Image','Flash','Media','Table','Rule','Smiley','SpecialChar','PageBreak'],\r\n");
      out.write("\t['FontFormat','FontName','FontSize'],\r\n");
      out.write("\t['TextColor','BGColor','-','ExForm','Portlet'],\r\n");
      out.write("        '/',\r\n");
      out.write("        ['TagPath']\r\n");
      out.write("] ;\r\n");
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
