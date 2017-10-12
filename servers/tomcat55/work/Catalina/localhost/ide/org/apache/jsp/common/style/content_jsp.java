package org.apache.jsp.common.style;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class content_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\n");
      out.write("body,td,th {font-size: 12px;}\n");
      out.write("body {  overflow-y:auto;\n");
      out.write("        overflow-x:hidden;\n");
      out.write("        padding:5px 5px 5px 5px;\n");
      out.write("        margin:0px 0px 0px 0px;\n");
      out.write("        border:3px solid buttonshadow;\n");
      out.write("        }\n");
      out.write("form{margin: 0px 0px 0px 0px;padding: 0px 0px 0px 0px;}\n");
      out.write("a {font-size: 12px;color: #000000;}\n");
      out.write("a:link{\n");
      out.write("    color:#000000;\n");
      out.write("\ttext-decoration:none;\n");
      out.write("}\n");
      out.write("a:visited {color: #000000;\n");
      out.write("\ttext-decoration:none;}\n");
      out.write("a:hover {color: #666666;\n");
      out.write("\ttext-decoration:none;}\n");
      out.write("\n");
      out.write(".anbg{\n");
      out.write("   background-image:url(../images/anbg.gif);\n");
      out.write("}\n");
      out.write(".anbgx{\n");
      out.write("   background-image:url(../images/anbgx.gif);\n");
      out.write("}\n");
      out.write(".card {\n");
      out.write("   text-align:center;\n");
      out.write("   background-image:url(../images/an1.gif);\n");
      out.write("}\n");
      out.write(".card a:link{\n");
      out.write("   color:#000000;\n");
      out.write("   text-decoration:none;\n");
      out.write("}\n");
      out.write(".card a:visited{\n");
      out.write("   color:#000000;\n");
      out.write("   text-decoration:none;\n");
      out.write("}\n");
      out.write(".card1{\n");
      out.write("   background-image:url(../images/an2.gif);\n");
      out.write("   text-align:center;\n");
      out.write("}\n");
      out.write(".card1 a:link{\n");
      out.write("   color:#000000;\n");
      out.write("   text-decoration:none;\n");
      out.write("}\n");
      out.write(".card1 a:visited{\n");
      out.write("   color:#000000;\n");
      out.write("   text-decoration:none;\n");
      out.write("}\n");
      out.write(".serch{\n");
      out.write("   background-image:url(../images/sousuo.gif);\n");
      out.write("   width:64px;\n");
      out.write("   height:23px;\n");
      out.write("}\n");
      out.write(".indentpx a:link{\n");
      out.write("   padding-left:25px;\n");
      out.write("   color:#000000;\n");
      out.write("   text-decoration:none;\n");
      out.write("}\n");
      out.write(".indentpx a:visited{\n");
      out.write("   padding-left:25px;\n");
      out.write("   color:#000000;\n");
      out.write("   text-decoration:none;\n");
      out.write("}\n");
      out.write(".red{ color:#FF0000;}\n");
      out.write(".red a:link{\n");
      out.write("   color:#FF0000;\n");
      out.write("}\n");
      out.write(".red a:visited{\n");
      out.write("   color:#FF0000;\n");
      out.write("}\n");
      out.write(".zuobian{\n");
      out.write("   background-image:url(../images/aotu2.gif);\n");
      out.write("}\n");
      out.write(".topNavigation{\n");
      out.write("   behavior: url(");
      if (_jspx_meth_html_005frewrite_005f0(_jspx_page_context))
        return;
      out.write(")\n");
      out.write("   margin-left:15px;\n");
      out.write("   margin-top:8px;\n");
      out.write("   line-height:22px;\n");
      out.write("}\n");
      out.write(".topNavigation img{\n");
      out.write("   margin-left:10px;\n");
      out.write("   margin-right:10px;\n");
      out.write("}\n");
      out.write(".jianju{\n");
      out.write("  margin-bottom:0px;\n");
      out.write("}\n");
      out.write(".jianju2{\n");
      out.write("   margin-top:5px;\n");
      out.write("   line-height:22px;\n");
      out.write("}\n");
      out.write(".emphasize {\n");
      out.write("   color:#ff5500;font-weight:bold;\n");
      out.write("}\n");
      out.write(".errors {\n");
      out.write("   font-size:12px;font-weight:bold;color:#ff0000;\n");
      out.write("}\n");
      out.write(".formTips{\n");
      out.write("   color: gray;\n");
      out.write("}\n");
      out.write(".disabled{\n");
      out.write("   color: gray;\n");
      out.write("}\n");
      out.write(".tdindent td{\n");
      out.write("   padding-left:5px;\n");
      out.write("}\n");
      out.write(".hand{\n");
      out.write("   cursor:pointer;\n");
      out.write("}\n");
      out.write("/* 主框架的 表格*/\n");
      out.write("#mainTable{ height:expression(document.body.clientHeight-1);}\n");
      out.write("/* 列表表头 */\n");
      out.write("#headerTable{\n");
      out.write("        width:100%;\n");
      out.write("        border-collapse:collapse;\n");
      out.write("\tborder:1 solid #cccccc;\n");
      out.write("\tline-height: 20px;\n");
      out.write("\ttext-indent:3px;\n");
      out.write("\tmargin-left:1px;}\n");
      out.write("#headerTable th{\n");
      out.write("  border:solid;\n");
      out.write("  border-width: 1px;\n");
      out.write("  background-color:#eeeeee;\n");
      out.write("  border-lightcolor:#ffffff;\n");
      out.write("  border-darkcolor:#cccccc;\n");
      out.write("  height:25px;\n");
      out.write("  }\n");
      out.write("/* 表单区 */\n");
      out.write(" #formBlock{\n");
      out.write("    height:expression(document.body.clientHeight-100);\n");
      out.write("    overflow-y: scroll;\n");
      out.write("    border-bottom:1px solid #c0c0c0;\n");
      out.write(" }\n");
      out.write("\n");
      out.write(".contentBlock{\n");
      out.write("    height:expression(document.body.clientHeight-130);\n");
      out.write("    overflow-y: scroll;\n");
      out.write("    border-bottom:1px solid #c0c0c0;\n");
      out.write("}\n");
      out.write(".ellipses{  white-space:nowrap;\n");
      out.write("            text-overflow:ellipsis;\n");
      out.write("            overflow:hidden;\n");
      out.write("            width:expression(parentElement.width-2);\n");
      out.write("        }\n");
      out.write("#propertyTable select{width:120px;}\n");
      out.write("#contentEditor ul{list-style-type:none}\n");
      out.write("#contentEditor .fileComponent{width:500px;}\n");
      out.write("#contentEditor .textComponent{width:500px;}\n");
      out.write("#contentEditor th{width:200px;}\n");
      out.write("\n");
      out.write("table.tableClass { behavior: url(");
      if (_jspx_meth_html_005frewrite_005f1(_jspx_page_context))
        return;
      out.write("); }\n");
      out.write(".odd {\n");
      out.write("  background-color:#f9f9f9;\n");
      out.write("}\n");
      out.write("table.sheetClass textarea { behavior: url(");
      if (_jspx_meth_html_005frewrite_005f2(_jspx_page_context))
        return;
      out.write("); }\n");
      out.write("table.tableClass,table.sheetClass{\n");
      out.write("        background-color:#fff;\n");
      out.write("        border-collapse:collapse;\n");
      out.write("\tborder:1 solid #cccccc;\n");
      out.write("\tline-height: 20px;\n");
      out.write("\ttext-indent:3px;\n");
      out.write("\tmargin-left:1px;\n");
      out.write("}\n");
      out.write("table.sheetClass{\n");
      out.write("  behavior: url(");
      if (_jspx_meth_html_005frewrite_005f3(_jspx_page_context))
        return;
      out.write(");\n");
      out.write("}\n");
      out.write("table.tableClass th,table.sheetClass th{\n");
      out.write("  border:solid;\n");
      out.write("  border-width: 1px;\n");
      out.write("  background-color:#eeeeee;\n");
      out.write("  border-lightcolor:#ffffff;\n");
      out.write("  border-darkcolor:#cccccc;\n");
      out.write("}\n");
      out.write("table.tableClass td,table.sheetClass td{\n");
      out.write("  border:solid;\n");
      out.write("  border-width: 1px;\n");
      out.write("  border-lightcolor:#ffffff;\n");
      out.write("  LINE-HEIGHT: 20px;\n");
      out.write("}\n");
      out.write("\n");
      out.write("table.tableClass tfoot td,table.sheetClass tfoot td{\n");
      out.write("  border:0px;\n");
      out.write("  text-align:right;\n");
      out.write("}\n");
      out.write(".leftDiv{\n");
      out.write("  height:expression(document.documentElement.clientHeight-30);\n");
      out.write("  overflow-x: hidden;\n");
      out.write("  overflow-y: hidden;\n");
      out.write("}\n");
      out.write(".scrollBar{\n");
      out.write("   position:absolute;\n");
      out.write("   bottom:0px;\n");
      out.write("   height:24px;\n");
      out.write("   text-align:center;\n");
      out.write("   border-top:1px solid #72b4c9;\n");
      out.write("   width:100%;\n");
      out.write("   background-color:#9bcaf6;\n");
      out.write("}\n");
      out.write(".footClass{\n");
      out.write("   line-height:24px;\n");
      out.write("   border-top:1px solid #72b4c9;\n");
      out.write("   background-color:#9bcaf6;\n");
      out.write("   text-align:center;\n");
      out.write("}\n");
      out.write("\n");
      out.write(".operation{\n");
      out.write("   margin-top:5px;\n");
      out.write("   text-align:center;\n");
      out.write("}\n");
      out.write(".operation button{\n");
      out.write("   margin-left:10px;\n");
      out.write("   margin-right:10px;\n");
      out.write("}\n");
      out.write(".operation img{\n");
      out.write("   margin-left:5px;\n");
      out.write("   margin-right:5px;\n");
      out.write("}\n");
      out.write("tr.treeVis{\n");
      out.write(" display:visible;\n");
      out.write("}\n");
      out.write("tr.treeHid{\n");
      out.write(" display:none;\n");
      out.write("}\n");
      out.write("img.treeSign{\n");
      out.write(" margin-right: 3px;\n");
      out.write(" cursor:pointer;\n");
      out.write("}\n");
      out.write(".treeSelected{\n");
      out.write("    background-color:#fff;\n");
      out.write("    font-weight:bold;\n");
      out.write("}\n");
      out.write(".treeSelected a:link{\n");
      out.write("    color:#000000;\n");
      out.write("    text-decoration:none;\n");
      out.write("}\n");
      out.write(".treeSelected a:visited{\n");
      out.write("    color:#000000;\n");
      out.write("    text-decoration:none;\n");
      out.write("}\n");
      out.write(".treeUnselected A{\n");
      out.write("        COLOR: #000000;\n");
      out.write("TEXT-DECORATION: none;\n");
      out.write("padding-top: 1px;\n");
      out.write("padding-left: 2px;\n");
      out.write("}\n");
      out.write("\n");
      out.write(".fontStyle{font-size:14px;color:#0057b9;}\n");
      out.write(".imageContentTitle{font-size:14px;color:#0057b9;}\n");
      out.write("\n");
      out.write(".border0603{ border:1px solid #999999;}\n");
      out.write(".bule0603{ font-size:14px; color:#0046ae; font-weight:bold;}\n");
      out.write(".black0603{font-size:14px;}\n");
      out.write(".commonbut0603{\n");
      out.write(" background-image:url(../images/button1.gif);\n");
      out.write(" width:80px;\n");
      out.write(" height:26px;\n");
      out.write(" padding-left:0px;\n");
      out.write(" border:0px;\n");
      out.write(" font-size:14px;\n");
      out.write(" color:#FF0000;\n");
      out.write("}\n");
      out.write(".cccommonbut0603{\n");
      out.write(" background-image:url(../images/button2.gif);\n");
      out.write(" color:#000000;\n");
      out.write("  width:80px;\n");
      out.write(" height:26px;\n");
      out.write(" padding-left:0px;\n");
      out.write(" border:0px;\n");
      out.write(" font-size:14px;\n");
      out.write("}\n");
      out.write(".cscommonbut0603{\n");
      out.write(" background-image:url(../images/button2.gif);\n");
      out.write(" color:#969696;\n");
      out.write("  width:80px;\n");
      out.write(" height:26px;\n");
      out.write(" padding-left:0px;\n");
      out.write(" border:0px;\n");
      out.write(" font-size:14px;\n");
      out.write("}\n");
      out.write(".buleborder0603{\n");
      out.write(" background-image:url(../images/openWindow.gif); width:500px; height:165px;\n");
      out.write("}\n");
      out.write(".sure0603{\n");
      out.write(" background-image:url(../images/button3.gif);\n");
      out.write(" color:#000000;\n");
      out.write("  width:50px;\n");
      out.write(" height:23px;\n");
      out.write(" padding-left:0px;\n");
      out.write(" border:0px;\n");
      out.write(" font-size:12px;\n");
      out.write("}\n");
      out.write(".pageClass{\n");
      out.write("text-align:right;\n");
      out.write("padding-right:15px;\n");
      out.write("}\n");
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
    _jspx_th_html_005frewrite_005f0.setPage("/style/navigation.htc");
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
    _jspx_th_html_005frewrite_005f1.setPage("/style/odd.htc");
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
    _jspx_th_html_005frewrite_005f2.setPage("/style/textarea.htc");
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
    _jspx_th_html_005frewrite_005f3.setModule("/common");
    _jspx_th_html_005frewrite_005f3.setPage("/style/th.htc");
    int _jspx_eval_html_005frewrite_005f3 = _jspx_th_html_005frewrite_005f3.doStartTag();
    if (_jspx_th_html_005frewrite_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f3);
    return false;
  }
}
