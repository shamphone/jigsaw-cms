package org.apache.jsp.cms.manage.plugins.exchange;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class import_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(5);
    _jspx_dependants.add("/WEB-INF/struts-bean.tld");
    _jspx_dependants.add("/WEB-INF/struts-logic.tld");
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
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
      out.write("<title>选择导入方式</title>\r\n");
      out.write("<meta http-equiv=Content-Type content=\"text/html; charset=UTF-8\" />\r\n");
      out.write("<meta http-equiv=\"Cache-Control\" content=\"no-cache, must-revalidate\" />\r\n");
      out.write("<meta http-equiv=\"expires\" content=\"Wed, 26 Feb 1997 08:21:57 GMT\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      if (_jspx_meth_html_005frewrite_005f0(_jspx_page_context))
        return;
      out.write("\">\r\n");
      out.write("<base target=\"_self\" />\r\n");
      out.write("    <script language=\"JavaScript\" type=\"text/Javascript\">\r\n");
      out.write("    function doInit(){\r\n");
      out.write("        var sets = window.dialogArguments.CMS.ImporterSet;\r\n");
      out.write("        var items = window.dialogArguments.CMSImporterItems;\r\n");
      out.write("        for(var i=0;i<sets.length;i++){\r\n");
      out.write("            var option = document.createElement(\"option\");\r\n");
      out.write("            option.value = sets[i];\r\n");
      out.write("            option.text = items.GetItem(sets[i]).name;\r\n");
      out.write("            option.desc = items.GetItem(sets[i]).desc;\r\n");
      out.write("            document.all('importers').options.add(option);\r\n");
      out.write("        }        \r\n");
      out.write("        document.all('importers').options[0].selected = true;\r\n");
      out.write("        document.all(\"desc\").innerText = document.all('importers').options[0].desc;\r\n");
      out.write("    }\r\n");
      out.write("    function showDesc(oSel){\r\n");
      out.write("        document.all(\"desc\").innerText = oSel.options[oSel.selectedIndex].desc;\r\n");
      out.write("    }\r\n");
      out.write("    function check(form){\r\n");
      out.write("      window.returnValue = document.all('importers').value;\r\n");
      out.write("      window.close();\r\n");
      out.write("    }\r\n");
      out.write("    </script>\r\n");
      out.write("</head>\r\n");
      out.write("<body onload=\"doInit()\">\r\n");
      out.write("<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\">\r\n");
      out.write("    <form action=\"#\" name=\"expForm\">\r\n");
      out.write("    <tr>\r\n");
      out.write("    <td>请选择导入方式：</td>\r\n");
      out.write("    </tr>\r\n");
      out.write("<tr><td>\r\n");
      out.write("    <select id=\"importers\" name=\"importers\" multiple=\"multiple\" style=\"width:480px;height:240px;\" onchange=\"showDesc(this)\">\r\n");
      out.write("    </select>\r\n");
      out.write("    </td></tr>\r\n");
      out.write("    <tr>\r\n");
      out.write("    <td valign=\"top\">\r\n");
      out.write("    <fieldset style=\"width:100%;height:80px\">\r\n");
      out.write("    <div id=\"desc\" style=\"padding:3px 3px 3px 3px;\"></div>\r\n");
      out.write("    </fieldset>\r\n");
      out.write("    </td>\r\n");
      out.write("    </tr>\r\n");
      out.write("    <tr>\r\n");
      out.write("    <td>\r\n");
      out.write("      <div class=\"operation\">\r\n");
      out.write("                        <button type=\"button\" onclick=\"check(this.form)\" id=\"btnOk\">确定</button>\r\n");
      out.write("                        <button type=\"button\" onclick=\"window.close()\" id=\"btnCancel\">取消</button>\r\n");
      out.write("                    </div>\r\n");
      out.write("                    </td>\r\n");
      out.write("    </tr>\r\n");
      out.write("    </form>\r\n");
      out.write("    </table>\r\n");
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
}
