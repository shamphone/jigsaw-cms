package org.apache.jsp.site.editor;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class selectCreateType_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(3);
    _jspx_dependants.add("/WEB-INF/struts-logic.tld");
    _jspx_dependants.add("/WEB-INF/struts-bean.tld");
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
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

      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\" >\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("\t<head>\r\n");
      out.write("\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n");
      out.write("\t\t<meta content=\"noindex, nofollow\" name=\"robots\" />\r\n");
      out.write("\t\t<script src=\"");
      if (_jspx_meth_html_005frewrite_005f0(_jspx_page_context))
        return;
      out.write("\" type=\"text/javascript\"></script>\r\n");
      out.write("\t\t<script language=\"Javascript\" type=\"text/Javascript\" src=\"");
      if (_jspx_meth_html_005frewrite_005f1(_jspx_page_context))
        return;
      out.write("\"></script>\t\t\r\n");
      out.write("\t\t<script language=\"Javascript\" type=\"text/Javascript\" src=\"");
      if (_jspx_meth_html_005frewrite_005f2(_jspx_page_context))
        return;
      out.write("\"></script>\r\n");
      out.write("\t\t<script type=\"text/javascript\"><!--\r\n");
      out.write("\t\t\tvar oEditor = window.parent.InnerDialogLoaded() ;\r\n");
      out.write("\t\t\t//var oDOM = oEditor.FCK.EditorDocument ;\r\n");
      out.write("\t\t\twindow.onload = function() {\r\n");
      out.write("\t\t\t\tclearParams();\r\n");
      out.write("\t\t\t\t// First of all, translate the dialog box texts\r\n");
      out.write("\t\t\t\toEditor.FCKLanguageManager.TranslatePage(document) ;\r\n");
      out.write("\t\t\t\tSetAttribute(document.getElementById(\"advanced1\"),\"checked\",\"true\");\r\n");
      out.write("\t\t\t\tonRadioChange(\"blank\",document.getElementById(\"oForm\"));\r\n");
      out.write("\t\t\t\twindow.parent.SetOkButton( true ) ;\r\n");
      out.write("\t\t\t\twindow.parent.SetAutoSize( true ) ;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\tfunction Ok() {\r\n");
      out.write("\t\t\t\tvar oFrom = document.getElementById(\"oForm\");\r\n");
      out.write("\t\t\t\tvar advance = GetRadioValue(oFrom.all(\"advanced\"));\r\n");
      out.write("\t\t\t\tvar from;\r\n");
      out.write("\t\t\t\tswitch (advance) {\r\n");
      out.write("\t\t\t\t\tcase \"template\" :\r\n");
      out.write("\t\t\t\t\t\tif (!oFrom.template.value) {\r\n");
      out.write("\t\t\t\t\t\t\talert(\"请选择想要导入的模版页面\");\r\n");
      out.write("\t\t\t\t\t\t\treturn false;\r\n");
      out.write("\t\t\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\t\t\tfrom = oFrom.template.value;\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\tbreak;\r\n");
      out.write("\t\t\t\t\tcase \"url\" :\r\n");
      out.write("\t\t\t\t\t\tif (!oFrom.url.value) {\r\n");
      out.write("\t\t\t\t\t\t\talert(\"请输入想要导入的页面地址\");\r\n");
      out.write("\t\t\t\t\t\t\treturn false;\r\n");
      out.write("\t\t\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\t\t\tfrom = oFrom.url.value;\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\tbreak;\r\n");
      out.write("\t\t\t\t\tdefault :\r\n");
      out.write("\t\t\t\t\t\tfrom = \"blank\";\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\tsetParam(\"advance\",advance);\r\n");
      out.write("\t\t\t\tsetParam(\"from\",from);\r\n");
      out.write("\t\t\t\treturn true;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\tfunction clearParams(){\r\n");
      out.write("\t\t\t\tif(oEditor.FCK.params){\r\n");
      out.write("\t\t\t\t\toEditor.FCK.params = null;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\tfunction setParam(sName,sValue){\r\n");
      out.write("\t\t\t\tif(oEditor.FCK.params){\r\n");
      out.write("\t\t\t\t\toEditor.FCK.params[sName] = sValue;\r\n");
      out.write("\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\tvar param = new Object();\r\n");
      out.write("\t\t\t\t\tparam[sName] = sValue;\r\n");
      out.write("\t\t\t\t\toEditor.FCK.params = param;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\t\tfunction onRadioChange($advanced, $form){\r\n");
      out.write("\t\t\t    if($advanced=='blank') {\r\n");
      out.write("\t\t\t      $form.template.disabled=\"true\";\r\n");
      out.write("\t\t\t      $form.url.disabled=\"true\";\r\n");
      out.write("\t\t\t    } else if($advanced=='template') {\r\n");
      out.write("\t\t\t      $form.template.disabled=\"\";\r\n");
      out.write("\t\t\t      $form.url.disabled=\"true\";\r\n");
      out.write("\t\t\t    } else /*if($advanced=='url') */{\r\n");
      out.write("\t\t\t      $form.template.disabled=\"true\";\r\n");
      out.write("\t\t\t      $form.url.disabled=\"\";\r\n");
      out.write("\t\t\t    }\r\n");
      out.write("\t\t\t  }\r\n");
      out.write("\t\t</script>\r\n");
      out.write("\t</head>\r\n");
      out.write("\t<body style=\"overflow: hidden\">\r\n");
      out.write("\t  <form name=\"oForm\">\r\n");
      out.write("      <table border=\"0\" cellpadding=\"2\" cellspacing=\"2\" align=\"center\">\r\n");
      out.write("        <tr>\r\n");
      out.write("          <td noWrap=\"nowrap\">\r\n");
      out.write("            <input type=\"radio\" id=\"advanced1\" onclick=\"onRadioChange(this.value, this.form)\" name=\"advanced\" value=\"blank\"/>\r\n");
      out.write("            <label for=\"advanced1\">使用空白模板</label>\r\n");
      out.write("          </td>\r\n");
      out.write("        </tr>\r\n");
      out.write("        <tr>\r\n");
      out.write("          <td noWrap=\"nowrap\">\r\n");
      out.write("            <input type=\"radio\" Id=\"advanced2\" onclick=\"onRadioChange(this.value, this.form)\" name=\"advanced\" value=\"template\"/>\r\n");
      out.write("            <label for=\"advanced2\">从模板页面中导入</label>\r\n");
      out.write("          </td>\r\n");
      out.write("          <td align=left>\r\n");
      out.write("            <input type=\"file\" name=\"template\"  disabled=\"true\" title=\"选择本地的静态页面作为页面初始内容。如果没有选择将初始化为空白页面。\"/>\r\n");
      out.write("          </td>\r\n");
      out.write("        </tr>\r\n");
      out.write("        <tr>\r\n");
      out.write("          <td colspan=\"3\" noWrap=\"nowrap\">\r\n");
      out.write("          </td>\r\n");
      out.write("        </tr>\r\n");
      out.write("        <tr>\r\n");
      out.write("          <td noWrap=\"nowrap\">\r\n");
      out.write("            <input type=\"radio\" Id=\"advanced4\" onclick=\"onRadioChange(this.value, this.form)\" name=\"advanced\" value=\"url\"/>\r\n");
      out.write("            <label for=\"advanced4\">从URL地址复制&nbsp;&nbsp;http://</label>\r\n");
      out.write("          </td>\r\n");
      out.write("          <td colspan=\"2\" noWrap=\"nowrap\">\r\n");
      out.write("            <input type=\"text\"  name=\"url\" size=\"32\" style=\"ime-mode:disabled\" title=\"只能输入半角的字母、数字、小数点和减号\"/>\r\n");
      out.write("          </td>\r\n");
      out.write("        </tr>\r\n");
      out.write("      </table>\r\n");
      out.write("      </form>\r\n");
      out.write("\t</body>\r\n");
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
    _jspx_th_html_005frewrite_005f0.setPage("/editor/dialog/common/fck_dialog_common.js");
    _jspx_th_html_005frewrite_005f0.setModule("/common");
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
    _jspx_th_html_005frewrite_005f1.setModule("/site");
    _jspx_th_html_005frewrite_005f1.setPage("/dialog.js.jsp");
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
    _jspx_th_html_005frewrite_005f2.setModule("/common");
    _jspx_th_html_005frewrite_005f2.setPage("/script/common.js.jsp");
    int _jspx_eval_html_005frewrite_005f2 = _jspx_th_html_005frewrite_005f2.doStartTag();
    if (_jspx_th_html_005frewrite_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f2);
    return false;
  }
}
