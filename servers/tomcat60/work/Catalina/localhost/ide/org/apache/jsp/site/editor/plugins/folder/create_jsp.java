package org.apache.jsp.site.editor.plugins.folder;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class create_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

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
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fstyle_005fpage_005fmodule_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fstyle_005fpage_005fmodule_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fstyle_005fpage_005fmodule_005fnobody.release();
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
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"");
      if (_jspx_meth_html_005frewrite_005f2(_jspx_page_context))
        return;
      out.write("\"></script>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"");
      if (_jspx_meth_html_005frewrite_005f3(_jspx_page_context))
        return;
      out.write("\"></script>\r\n");
      out.write("<style type=\"text/css\">\r\n");
      out.write("\t\tinput {width: 225px}\r\n");
      out.write("\t\tbody{over-flow:hidden}\r\n");
      out.write("</style>\r\n");
      out.write("<script type=\"text/javascript\" language=\"javascript\">\r\n");
      out.write("\tvar siteFolder = window.dialogArguments;\r\n");
      out.write("\tvar oTemplate  = siteFolder.template;\r\n");
      out.write("\twindow.onload = function(){\r\n");
      out.write("\t\tdocument.getElementById(\"parentDisplayName\").value = siteFolder.displayName;\r\n");
      out.write("\t}\r\n");
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
      out.write("\tfunction checkName(name){\r\n");
      out.write("\t\tif(!validateRequired(name)){\r\n");
      out.write("\t\t\talert(\"文件夹名称不能为空！\");\r\n");
      out.write("\t\t\treturn false;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tif(!validateName(name)){\r\n");
      out.write("\t\t\talert(\"文件夹名称只能包含字母、数字和下划线！\");\r\n");
      out.write("\t\t\treturn false;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\treturn true;\r\n");
      out.write("\t}\r\n");
      out.write("  \tfunction selectFolder(oDisplayName){\r\n");
      out.write("\t\tvar folder = SiteDialog.selectFolder(oTemplate);\r\n");
      out.write("\t\tif(folder!=null){\r\n");
      out.write("\t\t\toDisplayName.value = folder.displayName;\r\n");
      out.write("\t\t\tsiteFolder = folder;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\tfunction doOK(oForm) {\r\n");
      out.write("\t\tvar parent = siteFolder;\r\n");
      out.write("\t\tvar fileName = oForm.newName.value;\r\n");
      out.write("\t\tif(!checkName(fileName)){\r\n");
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
      out.write("\t\tvar param = new Object();\r\n");
      out.write("\t\tparam.folder = folder;\r\n");
      out.write("\t\tparam.parent = siteFolder;\r\n");
      out.write("\t\twindow.returnValue = param;\r\n");
      out.write("\t\twindow.close();\r\n");
      out.write("\t\treturn true;\r\n");
      out.write("\t}\r\n");
      out.write("</script>\r\n");
      out.write("<base target=\"_self\" />\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<form method=\"GET\" action=\"#\">\r\n");
      out.write("\t\t<table width=\"100%\" border=\"0\" cellpadding=\"4\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td width=\"180\" valign=\"middle\">");
      if (_jspx_meth_html_005fimg_005f0(_jspx_page_context))
        return;
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td valign=\"bottom\">\r\n");
      out.write("\t\t\t\t<div>\r\n");
      out.write("\t\t\t\t\t<table  width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<td>文件夹名称</td>\r\n");
      out.write("\t\t\t\t\t\t\t<td><input id=\"fileName\" type=\"text\" name=\"newName\" size=\"32\" maxlength=\"20\" /></td>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t          <td >父文件夹</td>\r\n");
      out.write("\t\t\t\t          <td>\r\n");
      out.write("\t\t\t\t            <input style=\"width: 159px;\" type=\"text\" id=\"parentDisplayName\" readonly=\"true\"/>\r\n");
      out.write("\t\t\t\t         \t<input  type=\"button\" class=\"btnMore\"  onclick=\"selectFolder(document.getElementById('parentDisplayName'))\" value=\"选择...\">\r\n");
      out.write("\t\t\t\t          </td>\r\n");
      out.write("\t\t\t\t        </tr>\r\n");
      out.write("\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<fieldset style=\"padding: 5px 5px 5px 5px; height: 180px;width: 100%;\">\r\n");
      out.write("\t\t\t\t<legend>说明</legend>\r\n");
      out.write("\t\t\t\t<div id=\"description\"></div>\r\n");
      out.write("\t\t\t\t</fieldset>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("<div class=\"operation\">\r\n");
      out.write("<button class=\"commonbut\" type=\"button\"  onclick=\"doOK(this.form)\">确定</button>\r\n");
      out.write("<button class=\"commonbut\" type=\"button\" onclick=\"window.close();\">取消</button>\r\n");
      out.write("</div>\r\n");
      out.write("</form>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
      out.write("\r\n");
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
    // /site/editor/plugins/folder/create.jsp(15,45) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f0.setModule("/common");
    // /site/editor/plugins/folder/create.jsp(15,45) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
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
    // /site/editor/plugins/folder/create.jsp(17,58) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f1.setModule("/common");
    // /site/editor/plugins/folder/create.jsp(17,58) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f1.setPage("/script/common.js");
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
    // /site/editor/plugins/folder/create.jsp(18,58) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f2.setModule("/site");
    // /site/editor/plugins/folder/create.jsp(18,58) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f2.setPage("/classes/site.js");
    int _jspx_eval_html_005frewrite_005f2 = _jspx_th_html_005frewrite_005f2.doStartTag();
    if (_jspx_th_html_005frewrite_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f2);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f3 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f3.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f3.setParent(null);
    // /site/editor/plugins/folder/create.jsp(19,58) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f3.setModule("/site");
    // /site/editor/plugins/folder/create.jsp(19,58) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f3.setPage("/dialog.js");
    int _jspx_eval_html_005frewrite_005f3 = _jspx_th_html_005frewrite_005f3.doStartTag();
    if (_jspx_th_html_005frewrite_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f3);
    return false;
  }

  private boolean _jspx_meth_html_005fimg_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:img
    org.apache.struts.taglib.html.ImgTag _jspx_th_html_005fimg_005f0 = (org.apache.struts.taglib.html.ImgTag) _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fstyle_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.ImgTag.class);
    _jspx_th_html_005fimg_005f0.setPageContext(_jspx_page_context);
    _jspx_th_html_005fimg_005f0.setParent(null);
    // /site/editor/plugins/folder/create.jsp(98,36) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fimg_005f0.setPage("/images/paddingleft.jpg");
    // /site/editor/plugins/folder/create.jsp(98,36) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fimg_005f0.setModule("/common");
    // /site/editor/plugins/folder/create.jsp(98,36) name = style type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fimg_005f0.setStyle("border:2px inset");
    int _jspx_eval_html_005fimg_005f0 = _jspx_th_html_005fimg_005f0.doStartTag();
    if (_jspx_th_html_005fimg_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fstyle_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005fimg_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fstyle_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005fimg_005f0);
    return false;
  }
}
