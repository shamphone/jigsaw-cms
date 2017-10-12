package org.apache.jsp.site.editor.plugins.folder;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class import_jsp extends org.apache.jasper.runtime.HttpJspBase
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
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.release();
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
      out.write("<title>上传文件</title>\r\n");
      out.write("<meta http-equiv=Content-Type content=\"text/html; charset=UTF-8\" />\r\n");
      out.write("<meta http-equiv=\"Cache-Control\" content=\"no-cache, must-revalidate\" />\r\n");
      out.write("<meta http-equiv=\"pragma\" content=\"no-cache\">\r\n");
      out.write("<meta http-equiv=\"expires\" content=\"Wed, 26 Feb 1997 08:21:57 GMT\" />\r\n");
      out.write("<base target=\"_self\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      if (_jspx_meth_html_005frewrite_005f0(_jspx_page_context))
        return;
      out.write("\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"property.css\" />\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"");
      if (_jspx_meth_html_005frewrite_005f1(_jspx_page_context))
        return;
      out.write("\"></script>\r\n");
      out.write("      <script type=\"text/javascript\" language=\"javascript\">\r\n");
      out.write("      var Editor = window.dialogArguments.Editor;\r\n");
      out.write("      function doInit(){\r\n");
      out.write("          document.all(\"folder\").value = Editor.channelTree.getSelected().element.getContextPath();\r\n");
      out.write("          document.all(\"template\").value = Editor.template.name;\r\n");
      out.write("      }\r\n");
      out.write("        function save(submitter){\r\n");
      out.write("            submitter.form.submit();\r\n");
      out.write("            submitter.disabled=true;\r\n");
      out.write("        }\r\n");
      out.write("        </script>\r\n");
      out.write("</head>\r\n");
      out.write("<body onload=\"doInit()\">\r\n");
      out.write("   <form action=\"/ide/site/resource/import.do\"  enctype=\"multipart/form-data\" method=\"post\">\r\n");
      out.write("      <table width=\"100%\" cellpadding=\"2\" cellspacing=\"2\" border=\"0\" align=\"center\">\r\n");
      out.write("      <input type=\"hidden\" name=\"template\" />\r\n");
      out.write("        <tr>\r\n");
      out.write("          <td width=\"80\" align=\"right\"  nowrap=\"nowrap\">目标文件夹:</td>\r\n");
      out.write("          <td><input class=\"disabledInput\" type=\"text\"  name=\"folder\" readonly=\"readonly\" size=\"40\" /> </td>\r\n");
      out.write("          <td><input type=\"checkbox\" id=\"upload\" name=\"upload\" value=\"true\"/><label for=\"upload\">上传水印图片</label></td>\r\n");
      out.write("          </tr>\r\n");
      out.write("        </table>\r\n");
      out.write("        <table border=\"0\" cellpadding=\"2\" cellspacing=\"2\" align=\"center\" width=\"100%\">\r\n");
      out.write("          <tr>\r\n");
      out.write("            <td  width=\"20px\"><hr size=\"1\" align=\"left\" width=\"100%\"/></td>\r\n");
      out.write("            <td width=\"50px\" align=\"center\">逐个导入</td>\r\n");
      out.write("            <td valign=\"middle\"><hr size=\"1\" align=\"left\" width=\"100%\"/></td>\r\n");
      out.write("          </tr>\r\n");
      out.write("        </table>\r\n");
      out.write("        <table border=\"0\" cellpadding=\"2\" cellspacing=\"2\" align=\"center\" width=\"100%\">\r\n");
      out.write("          <tr>\r\n");
      out.write("            <td width=\"80\" align=\"right\" nowrap=\"nowrap\">文件1:</td><td><input type=\"file\" contentEditable=\"false\" name=\"file[0]\" size=\"40\"/></td>\r\n");
      out.write("          </tr>\r\n");
      out.write("          <tr>\r\n");
      out.write("            <td align=\"right\" >文件2:</td><td><input type=\"file\" contentEditable=\"false\" name=\"file[1]\" size=\"40\"/></td>\r\n");
      out.write("          </tr>\r\n");
      out.write("          <tr>\r\n");
      out.write("            <td align=\"right\" >文件3:</td><td><input type=\"file\" contentEditable=\"false\" name=\"file[2]\" size=\"40\"/></td>\r\n");
      out.write("          </tr>\r\n");
      out.write("          <tr>\r\n");
      out.write("            <td align=\"right\" >文件4:</td><td><input type=\"file\" contentEditable=\"false\" name=\"file[3]\" size=\"40\"/></td>\r\n");
      out.write("          </tr>\r\n");
      out.write("          <tr>\r\n");
      out.write("            <td align=\"right\" >文件5:</td><td><input type=\"file\" contentEditable=\"false\" name=\"file[4]\" size=\"40\"/></td>\r\n");
      out.write("          </tr>\r\n");
      out.write("          <tr>\r\n");
      out.write("            <td align=\"right\" >文件6:</td><td><input type=\"file\" contentEditable=\"false\" name=\"file[5]\" size=\"40\"/></td>\r\n");
      out.write("          </tr>\r\n");
      out.write("          <tr>\r\n");
      out.write("            <td align=\"right\" >文件7:</td><td><input type=\"file\" contentEditable=\"false\" name=\"file[6]\" size=\"40\"/></td>\r\n");
      out.write("          </tr>\r\n");
      out.write("          <tr>\r\n");
      out.write("            <td align=\"right\" >文件8:</td><td><input type=\"file\" contentEditable=\"false\" name=\"file[7]\" size=\"40\"/></td>\r\n");
      out.write("          </tr>\r\n");
      out.write("        </table>\r\n");
      out.write("        <table border=\"0\" cellpadding=\"2\" cellspacing=\"2\" align=\"center\" width=\"100%\">\r\n");
      out.write("          <tr>\r\n");
      out.write("            <td  width=\"20px\"><hr size=\"1\" align=\"left\" width=\"100%\"/></td>\r\n");
      out.write("            <td width=\"90px\" align=\"center\">导入zip压缩包</td>\r\n");
      out.write("            <td valign=\"middle\"><hr size=\"1\" align=\"left\" width=\"100%\"/></td>\r\n");
      out.write("          </tr>\r\n");
      out.write("        </table>\r\n");
      out.write("        <div style=\"color:red\">");
      if (_jspx_meth_html_005ferrors_005f0(_jspx_page_context))
        return;
      out.write("</div>\r\n");
      out.write("        <table border=\"0\" cellpadding=\"2\" cellspacing=\"2\" align=\"center\" width=\"100%\">\r\n");
      out.write("          <tr>\r\n");
      out.write("            <td width=\"80\" align=\"right\" >压缩文件：</td><td><input type=\"file\" contentEditable=\"false\" name=\"zip\" size=\"40\"/></td>\r\n");
      out.write("          </tr>\r\n");
      out.write("        </table>\r\n");
      out.write("        <div class=\"operation\">\r\n");
      out.write("          <input type=\"button\" class=\"commonbut\" value=\"确定\" onclick=\"save(this)\"/>\r\n");
      out.write("          <input type=\"button\" class=\"commonbut\" value=\"取消\" onclick=\"window.close()\"/>\r\n");
      out.write("        </div>\r\n");
      out.write("      </form>\r\n");
      out.write("      </body>      \r\n");
      out.write("      </html>\r\n");
      out.write("      \r\n");
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

  private boolean _jspx_meth_html_005ferrors_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:errors
    org.apache.struts.taglib.html.ErrorsTag _jspx_th_html_005ferrors_005f0 = (org.apache.struts.taglib.html.ErrorsTag) _005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.get(org.apache.struts.taglib.html.ErrorsTag.class);
    _jspx_th_html_005ferrors_005f0.setPageContext(_jspx_page_context);
    _jspx_th_html_005ferrors_005f0.setParent(null);
    int _jspx_eval_html_005ferrors_005f0 = _jspx_th_html_005ferrors_005f0.doStartTag();
    if (_jspx_th_html_005ferrors_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.reuse(_jspx_th_html_005ferrors_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.reuse(_jspx_th_html_005ferrors_005f0);
    return false;
  }
}
