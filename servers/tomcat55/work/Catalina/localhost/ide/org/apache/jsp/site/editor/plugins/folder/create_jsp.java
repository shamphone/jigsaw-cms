package org.apache.jsp.site.editor.plugins.folder;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class create_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(5);
    _jspx_dependants.add("/WEB-INF/struts-bean.tld");
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
    _jspx_dependants.add("/WEB-INF/struts-logic.tld");
    _jspx_dependants.add("/WEB-INF/struts-tiles.tld");
    _jspx_dependants.add("/WEB-INF/fulong.tld");
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

      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("<title>新建文件夹</title>\r\n");
      out.write("<meta http-equiv=Content-Type content=\"text/html; charset=UTF-8\" />\r\n");
      out.write("<meta http-equiv=\"Cache-Control\" content=\"no-cache, must-revalidate\" />\r\n");
      out.write("<meta http-equiv=\"pragma\" content=\"no-cache\">\r\n");
      out.write("<meta http-equiv=\"expires\" content=\"Wed, 26 Feb 1997 08:21:57 GMT\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      if (_jspx_meth_html_005frewrite_005f0(_jspx_page_context))
        return;
      out.write("\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"property.css\" />\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"");
      if (_jspx_meth_html_005frewrite_005f1(_jspx_page_context))
        return;
      out.write("\"></script>\r\n");
      out.write("<script type=\"text/javascript\" language=\"javascript\">\r\n");
      out.write("\tvar ResourceEditor = window.dialogArguments.ResourceEditor;\r\n");
      out.write("\tfunction checkFileName($input) {\r\n");
      out.write("\t\tvar fileName = $input.value = $input.value.trim();\r\n");
      out.write("\t\tif (!fileName) {\r\n");
      out.write("\t\t\talert(\"文件名不能为空\");\r\n");
      out.write("\t\t\treturn null;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tvar reg1 = /^[^\\\\\\/:\\*\\?\\\"<>\\|&#]+$/;\r\n");
      out.write("\t\tif (!reg1.test(fileName)) {\r\n");
      out.write("\t\t\talert(\"文件名不能包含以下字符：\\n \\\\ / : * ? < > | & #\");\r\n");
      out.write("\t\t\treturn null;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tvar reg2 = /^[^\\.]+.*$/;\r\n");
      out.write("\t\tif (!reg2.test(fileName)) {\r\n");
      out.write("\t\t\talert(\"文件名不能为空\");\r\n");
      out.write("\t\t\treturn null;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tvar reg3 = /^(.+?)\\.+$/;\r\n");
      out.write("\t\tvar temp = fileName.match(reg3);\r\n");
      out.write("\t\tif (temp)\r\n");
      out.write("\t\t\tfileName = temp[1];\r\n");
      out.write("\t\treturn fileName;\r\n");
      out.write("\t};\r\n");
      out.write("\tfunction doOK(oForm) {\r\n");
      out.write("\t\tvar parent = ResourceEditor.folder;\r\n");
      out.write("\t\tvar fileName = checkFileName(oForm.newName);\r\n");
      out.write("\t\tif (fileName == null) {\r\n");
      out.write("\t\t\toForm.newName.select();\r\n");
      out.write("\t\t\treturn false;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tvar folder = parent.getChild(fileName);\r\n");
      out.write("\t\tif (folder.exists()) {\r\n");
      out.write("\t\t\talert(\"该名称已被使用，请更名后重试\");\r\n");
      out.write("\t\t\toForm.newName.select();\r\n");
      out.write("\t\t\treturn false;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tfolder.create();\r\n");
      out.write("\t\twindow.returnValue = folder;\r\n");
      out.write("\t\twindow.close();\r\n");
      out.write("\t\treturn true;\r\n");
      out.write("\t}\r\n");
      out.write("</script>\r\n");
      out.write("<base target=\"_self\" />\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<form method=\"GET\" action=\"#\">\r\n");
      out.write("<table width=\"100%\" cellpadding=\"2\" cellspacing=\"2\" border=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td>新建文件夹名称:</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td><input id=\"fileName\" type=\"text\" name=\"newName\" size=\"32\" maxlength=\"20\" /></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("<div class=\"operation\">\r\n");
      out.write("<button class=\"commonbut\" id=\"submit\" type=\"button\" onclick=\"doOK(this.form)\">确定</button>\r\n");
      out.write("<button class=\"commonbut\" onclick=\"window.close();\">取消</button>\r\n");
      out.write("</div>\r\n");
      out.write("</form>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
      out.write("\r\n");
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
    _jspx_th_html_005frewrite_005f0.setPage("/style/dialog.jsp");
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
    _jspx_th_html_005frewrite_005f1.setPage("/script/common.js.jsp");
    int _jspx_eval_html_005frewrite_005f1 = _jspx_th_html_005frewrite_005f1.doStartTag();
    if (_jspx_th_html_005frewrite_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f1);
    return false;
  }
}
