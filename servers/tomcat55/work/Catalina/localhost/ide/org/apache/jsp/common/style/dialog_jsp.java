package org.apache.jsp.common.style;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class dialog_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
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
      response.setContentType("text/css; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("body, td, th,input\r\n");
      out.write("{\r\n");
      out.write("font-size: 12px;\r\n");
      out.write("}\r\n");
      out.write("th{\r\n");
      out.write("  font-weight:normal;\r\n");
      out.write("}\r\n");
      out.write("body\r\n");
      out.write("{\r\n");
      out.write("\toverflow:visible;\r\n");
      out.write("\tpadding: 6px 6px 6px 6px;\r\n");
      out.write("\tmargin: 0px 0px 0px 0px;\r\n");
      out.write("        background-color:buttonface;\r\n");
      out.write("        \r\n");
      out.write("}\r\n");
      out.write("form\r\n");
      out.write("{\r\n");
      out.write("\tmargin: 0px 0px 0px 0px;\r\n");
      out.write("\tpadding: 0px 0px 0px 0px;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("ul{\r\n");
      out.write("\tlist-style:none;\r\n");
      out.write("\tmargin:5px 5px 5px 5px;\r\n");
      out.write("}\r\n");
      out.write("button{\r\n");
      out.write("  height:24px;\r\n");
      out.write("  font-size:12px;\r\n");
      out.write("  margin-left: 2px;\r\n");
      out.write("  margin-right: 2px;\r\n");
      out.write("}\r\n");
      out.write(".seperator{ width:1px;border:1px inset;}\r\n");
      out.write(".btnMore{  width:63px;margin-left:2px; height:21px;}\r\n");
      out.write("A {COLOR: #000000;TEXT-DECORATION: none}\r\n");
      out.write("A:active {COLOR: #000000;TEXT-DECORATION: none}\r\n");
      out.write("A:link {COLOR: #000000;TEXT-DECORATION: none}\r\n");
      out.write("A:visited {COLOR: #000000;TEXT-DECORATION: none}\r\n");
      out.write("A:hover {COLOR: #000000;TEXT-DECORATION: none}\r\n");
      out.write(".operation\r\n");
      out.write("{\r\n");
      out.write("border-top:2px groove;\r\n");
      out.write("position:absolute;\r\n");
      out.write("  left: 0px;\r\n");
      out.write("  top: expression(document.body.clientHeight-40);\r\n");
      out.write("  padding: 5px 5px 5px 5px;\r\n");
      out.write("  margin-left:5px;\r\n");
      out.write("  text-align: right;\r\n");
      out.write("  width:95%;\r\n");
      out.write("}\r\n");
      out.write("*+html .operation{\r\n");
      out.write(" width:100%;\r\n");
      out.write("}\r\n");
      out.write("*html .operation{\r\n");
      out.write(" width:100%;\r\n");
      out.write("}\r\n");
      out.write(".operation button\r\n");
      out.write("{\r\n");
      out.write("        padding-left:12px;\r\n");
      out.write("        padding-right:12px;\r\n");
      out.write("}\r\n");
      out.write(".operation img\r\n");
      out.write("{\r\n");
      out.write("\tmargin-left: 5px;\r\n");
      out.write("\tmargin-right: 5px;\r\n");
      out.write("}\r\n");
      out.write(".insetDiv{\r\n");
      out.write("    background-color: white;\r\n");
      out.write("    border: 2px inset;\r\n");
      out.write("    overflow-y: scroll;\r\n");
      out.write("    overflow-x: auto;\r\n");
      out.write("    padding: 2px 2px 2px 2px;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("#definitionTree\r\n");
      out.write("{\r\n");
      out.write("\theight: 220px;\r\n");
      out.write("}\r\n");
      out.write("#DLGToolbar{\r\n");
      out.write("  border-bottom:2px groove;\r\n");
      out.write("  margin: 2px 2px 2px 2px;\r\n");
      out.write("  height: 26px;\r\n");
      out.write("  vertical-align : middle;\r\n");
      out.write("  behavior: url(");
      if (_jspx_meth_html_005frewrite_005f0(_jspx_page_context))
        return;
      out.write(");\r\n");
      out.write("}\r\n");
      out.write("#DLGToolbar img{\r\n");
      out.write("  border: 0px;\r\n");
      out.write("  height: 16px;\r\n");
      out.write("  width: 16px;\r\n");
      out.write("}\r\n");
      out.write("#DLGToolbar input{\r\n");
      out.write("border: 1px solid darkblue;\r\n");
      out.write("height: 20px;\r\n");
      out.write("margin: 1px 1px 1px 1px;\r\n");
      out.write("padding: 0px 1px 0px 1px;\r\n");
      out.write("vertical-align : middle;\r\n");
      out.write("}\r\n");
      out.write("#DLGToolbar select{\r\n");
      out.write("border: 1px solid darkblue;\r\n");
      out.write("height: 20px;\r\n");
      out.write("margin: 1px 1px 1px 1px;\r\n");
      out.write("padding: 0px 1px 0px 1px;\r\n");
      out.write("vertical-align : middle;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("#DLGToolbar20{\r\n");
      out.write("  border-bottom:2px groove;\r\n");
      out.write("  margin: 2px 2px 2px 2px;\r\n");
      out.write("  height: 30px;\r\n");
      out.write("  vertical-align : middle;\r\n");
      out.write("  behavior: url(");
      if (_jspx_meth_html_005frewrite_005f1(_jspx_page_context))
        return;
      out.write(");\r\n");
      out.write("}\r\n");
      out.write("#DLGToolbar20 img{\r\n");
      out.write("  border: 0px;\r\n");
      out.write("  height: 20px;\r\n");
      out.write("  width: 20px;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".DLGButton\r\n");
      out.write("{\r\n");
      out.write("    margin: 1px 1px 1px 1px;\r\n");
      out.write("    padding: 0px 1px 0px 1px;\r\n");
      out.write("    height: 16px;\r\n");
      out.write("    vertical-align : middle;\r\n");
      out.write("    border: buttonface 1px solid;\r\n");
      out.write("    cursor:pointer;\r\n");
      out.write("    display:inline-block;\r\n");
      out.write("    behavior: url(");
      if (_jspx_meth_html_005frewrite_005f2(_jspx_page_context))
        return;
      out.write(");\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".DLGButton_On_Over\r\n");
      out.write("{\r\n");
      out.write("    border: #0A246A 1px solid;\r\n");
      out.write("    background-color: #B6BDD2;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".DLGButton span{\r\n");
      out.write("   vertical-align : middle;\r\n");
      out.write("   height: 16px;\r\n");
      out.write("   margin-left: 2px;\r\n");
      out.write("}\r\n");
      out.write(".selectRightBtn {\r\n");
      out.write(" width:80px;\r\n");
      out.write(" margin-bottom: 10px;\r\n");
      out.write(" font-size:10px;\r\n");
      out.write("}\r\n");
      out.write(".hilight{\r\n");
      out.write("  color:white;\r\n");
      out.write("  background-color:darkblue;\r\n");
      out.write("}\r\n");
      out.write(".listCheckbox{\r\n");
      out.write(" margin: 0 0 0 0;\r\n");
      out.write(" padding: 0 0 0 0;\r\n");
      out.write(" vertical-align:middle;\r\n");
      out.write(" text-align:center;\r\n");
      out.write(" width:14px;\r\n");
      out.write(" height:14px;\r\n");
      out.write("}\r\n");
      out.write("#oListPanel {\r\n");
      out.write("    width: 100%;\r\n");
      out.write("    height:expression(document.body.clientHeight-50);\r\n");
      out.write("    overflow-y: scroll;\r\n");
      out.write("    overflow-x: hidden;\r\n");
      out.write("    padding:0px 0px 0px 0px;\r\n");
      out.write("    text-align:center;\r\n");
      out.write("    border-left:1px solid buttonshadow;\r\n");
      out.write("    }\r\n");
      out.write("#listTable {width:100%; border: none; background-color: window;}\r\n");
      out.write("#listTable thead tr{position:relative; top:expression(offsetParent.scrollTop); }\r\n");
      out.write("#listTable thead th {\r\n");
      out.write("\ttext-align: center;\r\n");
      out.write("\tbackground-color: buttonface;\r\n");
      out.write("\tfont-weight: bold;\r\n");
      out.write("\tborder-top: 1px solid white;\r\n");
      out.write("\tborder-right:1px  solid buttonshadow;\r\n");
      out.write("\tborder-left:1px  solid window;\r\n");
      out.write("        border-bottom:2px  ridge buttonface;\r\n");
      out.write("\t}\r\n");
      out.write("#listTable tbody td{\r\n");
      out.write("        text-align: left;\r\n");
      out.write("\tborder-bottom: solid 1px buttonshadow;\r\n");
      out.write("\tborder-right: solid 1px buttonshadow;\r\n");
      out.write("        height:16px;\r\n");
      out.write("        cursor:pointer\r\n");
      out.write("        }\r\n");
      out.write("#listTable tbody th{text-align: left;border-bottom: solid 1px buttonshadow;height:16px;cursor:pointer;font-weight:normal;}\r\n");
      out.write("\r\n");
      out.write("#listTable input{height:16px;font-size:11px;}\r\n");
      out.write("\r\n");
      out.write("*+html #listTable input{height:20px;font-size:11px;}\r\n");
      out.write("\r\n");
      out.write("*html #listTable input{height:20px;font-size:11px;}\r\n");
      out.write("\r\n");
      out.write("#listTable tfoot td{text-align: right;background-color: buttonface;border-top: solid 1px buttonshadow;}\r\n");
      out.write("\r\n");
      out.write("#mappingContainer table{\r\n");
      out.write("\tborder-collapse:collapse;\r\n");
      out.write("\tborder:1px solid buttonface;\t\r\n");
      out.write("}\r\n");
      out.write("#mappingContainer td{\r\n");
      out.write("\theight:22px;\r\n");
      out.write("}\r\n");
      out.write("#mappingContainer input{\r\n");
      out.write("\tborder:0px;\r\n");
      out.write("\tpadding:0px 0px 0px 0px;\r\n");
      out.write("\tmargin:0px 0px 0px 0px;\r\n");
      out.write("\twidth:expression(document.body.clientWidth-335);\t\r\n");
      out.write("}\r\n");
      out.write("#mappingContainer button{\r\n");
      out.write("\tpadding:0px 0px 0px 0px;\r\n");
      out.write("\tmargin:0px 0px 0px 0px;\r\n");
      out.write("\twidth:20px;\r\n");
      out.write("\theight:20px;\r\n");
      out.write("\tborder-width:1px;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".PopupTitleBorder{\r\n");
      out.write("\tborder-bottom: #d5d59d 1px solid;\r\n");
      out.write("}\r\n");
      out.write(".PopupTitle{\r\n");
      out.write("\tfont-weight: bold;\r\n");
      out.write("\tfont-size: 14pt;\r\n");
      out.write("\tcolor: #737357;\r\n");
      out.write("\tbackground-color: #e3e3c7;\r\n");
      out.write("\tpadding: 3px 10px 3px 10px;\r\n");
      out.write("\tmargin-bottom: 10px;\r\n");
      out.write("}\r\n");
      out.write(".errorTip {color:red}\r\n");
      out.write(".indent {margin-left:6px}\r\n");
      out.write("#navcontainer input{width:16px;height:14px;}\r\n");
      out.write("\r\n");
      out.write("#footer{\r\n");
      out.write("  height:20px;vertical-align:middle;text-align:right;padding:2px 10px 2px 2px;font-size:12px;\r\n");
      out.write("}\r\n");
      out.write("#footer a{font-size:12px;\r\n");
      out.write("} \r\n");
      out.write("*+html #footer{\r\n");
      out.write("  height:20px;vertical-align:middle;text-align:right;padding:2px 10px 2px 2px;width:auto;\r\n");
      out.write("}\r\n");
      out.write("*html #footer{\r\n");
      out.write("  height:20px;vertical-align:middle;text-align:right;padding:2px 10px 2px 2px;width:auto;\r\n");
      out.write("}\r\n");
      out.write(".treeDiv {\r\n");
      out.write("\tborder-style: inset;\r\n");
      out.write("\tborder-width: 2;\r\n");
      out.write("\tmargin-right: 5px;\r\n");
      out.write("\tbackground-color: window;\r\n");
      out.write("\theight: 505px;\r\n");
      out.write("\twidth: 200px;\r\n");
      out.write("\toverflow: scroll;\r\n");
      out.write("\tpadding-left:2 ;\r\n");
      out.write("\toverflow-x:hidden;\r\n");
      out.write("}\r\n");
      out.write("*html .treeDiv {\r\n");
      out.write("\tborder-style: inset;\r\n");
      out.write("\tborder-width: 2;\r\n");
      out.write("\tmargin-right: 5px;\r\n");
      out.write("\tbackground-color: window;\r\n");
      out.write("\twidth: 200px;\r\n");
      out.write("\theight: 480px;\r\n");
      out.write("\toverflow: scroll;\r\n");
      out.write("\tpadding-left:2 ;\r\n");
      out.write("}\r\n");
      out.write("*+html .treeDiv {\r\n");
      out.write("\tborder-style: inset;\r\n");
      out.write("\tborder-width: 2;\r\n");
      out.write("\tmargin-right: 5px;\r\n");
      out.write("\tbackground-color: window;\r\n");
      out.write("\twidth: 200px;\r\n");
      out.write("\theight: 480px;\r\n");
      out.write("\toverflow: scroll;\r\n");
      out.write("\tpadding-left:2 ;\r\n");
      out.write("}");
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
    _jspx_th_html_005frewrite_005f0.setPage("/style/dlgtoolbar.htc");
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
    _jspx_th_html_005frewrite_005f1.setPage("/style/dlgtoolbar.htc");
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
    _jspx_th_html_005frewrite_005f2.setPage("/style/dlgbutton.htc");
    int _jspx_eval_html_005frewrite_005f2 = _jspx_th_html_005frewrite_005f2.doStartTag();
    if (_jspx_th_html_005frewrite_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f2);
    return false;
  }
}
