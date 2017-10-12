package org.apache.jsp.Webmanager.usermanage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class manageUsers_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(6);
    _jspx_dependants.add("/WEB-INF/struts-bean.tld");
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
    _jspx_dependants.add("/WEB-INF/struts-logic.tld");
    _jspx_dependants.add("/WEB-INF/fulong.tld");
    _jspx_dependants.add("/WEB-INF/struts-tiles.tld");
    _jspx_dependants.add("/common/dialogheader.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.release();
    _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.release();
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.release();
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
      response.setContentType("text/html; charset=gb2312");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("\tsrc=\"");
      if (_jspx_meth_html_005frewrite_005f0(_jspx_page_context))
        return;
      out.write("\"></script>\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("\tsrc=\"");
      if (_jspx_meth_html_005frewrite_005f1(_jspx_page_context))
        return;
      out.write("\"></script>\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("\tsrc=\"");
      if (_jspx_meth_html_005frewrite_005f2(_jspx_page_context))
        return;
      out.write("\"></script>\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("\tsrc=\"");
      if (_jspx_meth_html_005frewrite_005f3(_jspx_page_context))
        return;
      out.write("\"></script>\r\n");
      out.write("<link rel=\"stylesheet\"\r\n");
      out.write("\thref=\"");
      if (_jspx_meth_html_005frewrite_005f4(_jspx_page_context))
        return;
      out.write("\" type=\"text/css\"></link>\r\n");
      out.write("<link rel=\"stylesheet\"\r\n");
      out.write("\thref=\"");
      if (_jspx_meth_html_005frewrite_005f5(_jspx_page_context))
        return;
      out.write("\" type=\"text/css\"></link>\r\n");
      out.write("<link rel=\"stylesheet\"\r\n");
      out.write("\thref=\"");
      if (_jspx_meth_html_005frewrite_005f6(_jspx_page_context))
        return;
      out.write("\" type=\"text/css\"></link>\r\n");
      out.write("<style type=\"text/css\">\r\n");
      out.write("img {\r\n");
      out.write("\tborder: 0px;\r\n");
      out.write("}\r\n");
      out.write("</style>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\t//--------------------------------\r\n");
      out.write("\t// Save functions\r\n");
      out.write("\t//--------------------------------\r\n");
      out.write("\tvar ajaxObjects = new Array();\r\n");
      out.write("\r\n");
      out.write("\t// Use something like this if you want to save data by Ajax.\r\n");
      out.write("\tfunction saveMyTree()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tsaveString = treeObj.getNodeOrders();\r\n");
      out.write("\t\tvar ajaxIndex = ajaxObjects.length;\r\n");
      out.write("\t\tajaxObjects[ajaxIndex] = new sack();\r\n");
      out.write("\t\tvar url = 'saveNodes.php?saveString=' + saveString;\r\n");
      out.write("\t\tajaxObjects[ajaxIndex].requestFile = url;\t// Specifying which file to get\r\n");
      out.write("\t\tajaxObjects[ajaxIndex].onCompletion = function() { saveComplete(ajaxIndex); } ;\t// Specify function that will be executed after file has been found\r\n");
      out.write("\t\tajaxObjects[ajaxIndex].runAJAX();\t\t// Execute AJAX function\r\n");
      out.write("\t}\r\n");
      out.write("\tfunction saveComplete(index)\r\n");
      out.write("\t{\r\n");
      out.write("\t\talert(ajaxObjects[index].response);\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t// Call this function if you want to save it by a form.\r\n");
      out.write("\tfunction saveMyTree_byForm()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tdocument.myForm.elements['saveString'].value = treeObj.getNodeOrders();\r\n");
      out.write("\t\tdocument.myForm.submit();\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction pageTitle()\r\n");
      out.write("\t{\r\n");
      out.write("\t\treturn (\"管理组织和联系人\");\r\n");
      out.write("\t}\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("<body style=\"height: 100%; margin: 0; padding: 0;\">\r\n");
      if (_jspx_meth_logic_005fnotPresent_005f0(_jspx_page_context))
        return;
      out.write("</body>\r\n");
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
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f0 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f0.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f0.setParent(null);
    _jspx_th_html_005frewrite_005f0.setPage("/scripts/ajax.js");
    int _jspx_eval_html_005frewrite_005f0 = _jspx_th_html_005frewrite_005f0.doStartTag();
    if (_jspx_th_html_005frewrite_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f0);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f1 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f1.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f1.setParent(null);
    _jspx_th_html_005frewrite_005f1.setPage("/scripts/context-menu.js");
    int _jspx_eval_html_005frewrite_005f1 = _jspx_th_html_005frewrite_005f1.doStartTag();
    if (_jspx_th_html_005frewrite_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f1);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f2 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f2.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f2.setParent(null);
    _jspx_th_html_005frewrite_005f2.setPage("/scripts/modal-message.js");
    int _jspx_eval_html_005frewrite_005f2 = _jspx_th_html_005frewrite_005f2.doStartTag();
    if (_jspx_th_html_005frewrite_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f2);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f3 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f3.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f3.setParent(null);
    _jspx_th_html_005frewrite_005f3.setPage("/scripts/drag-drop-folder-tree.js");
    int _jspx_eval_html_005frewrite_005f3 = _jspx_th_html_005frewrite_005f3.doStartTag();
    if (_jspx_th_html_005frewrite_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f3);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f4 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f4.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f4.setParent(null);
    _jspx_th_html_005frewrite_005f4.setPage("/css/modal-message.css");
    int _jspx_eval_html_005frewrite_005f4 = _jspx_th_html_005frewrite_005f4.doStartTag();
    if (_jspx_th_html_005frewrite_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f4);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f5(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f5 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f5.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f5.setParent(null);
    _jspx_th_html_005frewrite_005f5.setPage("/css/drag-drop-folder-tree.css");
    int _jspx_eval_html_005frewrite_005f5 = _jspx_th_html_005frewrite_005f5.doStartTag();
    if (_jspx_th_html_005frewrite_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f5);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f5);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f6(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f6 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f6.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f6.setParent(null);
    _jspx_th_html_005frewrite_005f6.setPage("/css/context-menu.css");
    int _jspx_eval_html_005frewrite_005f6 = _jspx_th_html_005frewrite_005f6.doStartTag();
    if (_jspx_th_html_005frewrite_005f6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f6);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f6);
    return false;
  }

  private boolean _jspx_meth_logic_005fnotPresent_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:notPresent
    org.apache.struts.taglib.logic.NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (org.apache.struts.taglib.logic.NotPresentTag) _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.get(org.apache.struts.taglib.logic.NotPresentTag.class);
    _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
    _jspx_th_logic_005fnotPresent_005f0.setParent(null);
    _jspx_th_logic_005fnotPresent_005f0.setName("isFindUser");
    int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
    if (_jspx_eval_logic_005fnotPresent_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("<html>\r\n");
        out.write("<body>\r\n");
        out.write("<div style=\"height:48px; width:100%; background:url('");
        if (_jspx_meth_html_005frewrite_005f7(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
          return true;
        out.write("')\">\r\n");
        out.write("\t<table width=\"95%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\r\n");
        out.write("\t\t<tr>\r\n");
        out.write("\t\t\t<td>\r\n");
        out.write("\t\t\t\t<font size=\"2\" color=\"#173891\"><script>document.write(pageTitle());</script></font>\r\n");
        out.write("\t\t\t</td>\r\n");
        out.write("\t\t\t<td align=\"right\" >\r\n");
        out.write("\t\t\t\t<img src=\"");
        if (_jspx_meth_html_005frewrite_005f8(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
          return true;
        out.write("\" />\r\n");
        out.write("\t\t\t</td>\r\n");
        out.write("\t\t</tr>\r\n");
        out.write("\t</table>\r\n");
        out.write("</div>\r\n");
        out.write("</body>\r\n");
        out.write("</html>");
        out.write("<table id=\"membertree\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" >\r\n");
        out.write("\t\t<tr>\r\n");
        out.write("\t\t\t<td width=\"60%\" style=\"padding: 6px;\">\r\n");
        out.write("\t\t\t<div id=\"treediv\" style=\"width:100%; overflow-y: scroll; border: 1px solid #ACA899;\">\r\n");
        out.write("\t\t\t<script type=\"text/javascript\">\r\n");
        out.write("\t\t\t\tvar tbl=document.getElementById(\"membertree\");\r\n");
        out.write("\t\t\t\tvar treediv=document.getElementById(\"treediv\");\r\n");
        out.write("\t\t\t\ttbl.style.height=document.body.clientHeight-50+\"px\";\r\n");
        out.write("\t\t\t\ttreediv.style.height=document.body.clientHeight-64+\"px\";\r\n");
        out.write("\t\t\t</script>\r\n");
        out.write("\r\n");
        out.write("\t\t\t<ul id=\"tree\" class=\"dhtmlgoodies_tree\">\r\n");
        out.write("\t\t\t\t");
        if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
          return true;
        out.write("</ul>\r\n");
        out.write("\t\t\t<!-- Form - if you want to save it by form submission and not Ajax -->\r\n");
        out.write("\t\t\t<form name=\"myForm\" method=\"post\"\r\n");
        out.write("\t\t\t\taction=\"");
        if (_jspx_meth_html_005frewrite_005f9(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
          return true;
        out.write("\"><input\r\n");
        out.write("\t\t\t\ttype=\"hidden\" name=\"saveString\">\r\n");
        out.write("\t\t\t</form>\r\n");
        out.write("\t\t\t</div>\r\n");
        out.write("\t\t\t</td>\r\n");
        out.write("\t\t\t<td align=\"center\" valign=\"top\">\r\n");
        out.write("\t\t\t<table cellspacing=\"2\" cellpadding=\"4\">\r\n");
        out.write("\t\t\t\t<tr><td align=\"left\">\r\n");
        out.write("\t\t\t\t\t\t<div style=\"font-size:13px; color:navy; font-weight:normal;\"\">说明：</div>\r\n");
        out.write("\t\t\t\t</td></tr>\r\n");
        out.write("\t\t\t\t<tr><td align=\"left\" style=\"height:1px\" bgcolor=\"gray\">\r\n");
        out.write("\t\t\t\t</td></tr>\r\n");
        out.write("\t\t\t\t<tr><td align=\"left\">\r\n");
        out.write("\t\t\t\t\t\t<div style=\"font-size:13px; color:blue;\">\r\n");
        out.write("\t\t\t\t\t\t通过鼠标拖动左边的组织或人员，把所有联系人分层次组织起来。</div>\r\n");
        out.write("\t\t\t\t</td></tr>\r\n");
        out.write("\t\t\t\t<tr><td align=\"left\">\r\n");
        out.write("\t\t\t\t\t\t<div style=\"font-size:13px; color:blue;\">\r\n");
        out.write("\t\t\t\t\t\t在组织或人员节点上点击鼠标右键可进行删除、重置密码、查看详细信息等操作。</div>\r\n");
        out.write("\t\t\t\t</td></tr>\r\n");
        out.write("\t\t\t\t<tr><td align=\"left\">\r\n");
        out.write("\t\t\t\t\t\t<div style=\"font-size:13px; color:blue;\">\r\n");
        out.write("\t\t\t\t\t\t在拖动过程中请避免交换上下层组织的隶属关系。</div>\r\n");
        out.write("\t\t\t\t</td></tr>\r\n");
        out.write("\t\t\t\t<tr><td align=\"left\">\r\n");
        out.write("\t\t\t\t\t\t<div style=\"font-size:13px; color:blue;\">\r\n");
        out.write("\t\t\t\t\t\t删除操作是立即执行的，请慎重，拖动操作在确认后集中执行。</div>\r\n");
        out.write("\t\t\t\t</td></tr>\r\n");
        out.write("\t\t\t</table>\r\n");
        out.write("\t\t\t\t<br /><br />\r\n");
        out.write("\t\t\t\t<a href=\"#\" onclick=\"treeObj.collapseAll()\">全部收起</a>\r\n");
        out.write("\t\t\t\t<a href=\"#\" onclick=\"treeObj.expandAll()\">全部展开</a><br />\r\n");
        out.write("\t\t\t\t<br />\r\n");
        out.write("\t\t\t\t<a href=\"");
        if (_jspx_meth_html_005frewrite_005f10(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
          return true;
        out.write("\">添加用户</a>\r\n");
        out.write("\t\t\t\t<a href=\"");
        if (_jspx_meth_html_005frewrite_005f11(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
          return true;
        out.write("\">添加组</a><br />\r\n");
        out.write("\t\t\t\t<br />\r\n");
        out.write("\t\t\t\t<form>\r\n");
        out.write("\t\t\t\t\t<input type=\"button\" onclick=\"saveMyTree_byForm()\"\r\n");
        out.write("\t\t\t\t\t\tstyle='color: #173891; background: url(");
        if (_jspx_meth_html_005frewrite_005f12(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
          return true;
        out.write("); border: none; width: 96px; height: 22px;'\r\n");
        out.write("\t\t\t\t\t\tvalue=\"确定\" name=\"ok\" id=\"ButtonOK\">\r\n");
        out.write("\t\t\t\t\t<input style='color: #173891; background: url(");
        if (_jspx_meth_html_005frewrite_005f13(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
          return true;
        out.write("); border: none; width: 96px; height: 22px;'\r\n");
        out.write("\t\t\t\t\t\ttype=\"button\" name=\"cancel\" id=\"ButtonCancel\" value=\"取消\">\r\n");
        out.write("\t\t\t\t</Form>\r\n");
        out.write("\t\t\t</td>\r\n");
        out.write("\t\t</tr>\r\n");
        out.write("\t</table>\r\n");
        out.write("\t<script type=\"text/javascript\">\r\n");
        out.write("\ttreeObj = new JSDragDropTree();\r\n");
        out.write("\ttreeObj.setTreeId('tree');\r\n");
        out.write("\ttreeObj.setMaximumDepth(7);\r\n");
        out.write("\ttreeObj.setMessageMaximumDepthReached('层次过多，超过限制'); // If you want to show a message when maximum depth is reached, i.e. on drop.\r\n");
        out.write("\ttreeObj.setRenameAllowed(false);\r\n");
        out.write("\ttreeObj.initTree();\r\n");
        out.write("\ttreeObj.expandAll();\r\n");
        out.write("\ttreeObj.setFileNameDelete(\"");
        if (_jspx_meth_html_005frewrite_005f14(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
          return true;
        out.write("\");\r\n");
        out.write("\t</script>\r\n");
        int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.reuse(_jspx_th_logic_005fnotPresent_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.reuse(_jspx_th_logic_005fnotPresent_005f0);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f7(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f7 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f7.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fnotPresent_005f0);
    _jspx_th_html_005frewrite_005f7.setPage("/images/dialogtitle.gif");
    int _jspx_eval_html_005frewrite_005f7 = _jspx_th_html_005frewrite_005f7.doStartTag();
    if (_jspx_th_html_005frewrite_005f7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f7);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f7);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f8(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f8 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f8.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fnotPresent_005f0);
    _jspx_th_html_005frewrite_005f8.setPage("/images/viewrightimg.gif");
    int _jspx_eval_html_005frewrite_005f8 = _jspx_th_html_005frewrite_005f8.doStartTag();
    if (_jspx_th_html_005frewrite_005f8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f8);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f8);
    return false;
  }

  private boolean _jspx_meth_bean_005fwrite_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f0 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fwrite_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fnotPresent_005f0);
    _jspx_th_bean_005fwrite_005f0.setName("mlist");
    _jspx_th_bean_005fwrite_005f0.setFilter(false);
    int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
    if (_jspx_th_bean_005fwrite_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f9(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f9 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f9.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fnotPresent_005f0);
    _jspx_th_html_005frewrite_005f9.setPage("/usermanage/listTheGroup.do");
    int _jspx_eval_html_005frewrite_005f9 = _jspx_th_html_005frewrite_005f9.doStartTag();
    if (_jspx_th_html_005frewrite_005f9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f9);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f9);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f10(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f10 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f10.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fnotPresent_005f0);
    _jspx_th_html_005frewrite_005f10.setPage("/usermanage/addMember.do");
    int _jspx_eval_html_005frewrite_005f10 = _jspx_th_html_005frewrite_005f10.doStartTag();
    if (_jspx_th_html_005frewrite_005f10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f10);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f10);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f11(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f11 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f11.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f11.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fnotPresent_005f0);
    _jspx_th_html_005frewrite_005f11.setPage("/usermanage/addGroup.do");
    int _jspx_eval_html_005frewrite_005f11 = _jspx_th_html_005frewrite_005f11.doStartTag();
    if (_jspx_th_html_005frewrite_005f11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f11);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f11);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f12(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f12 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f12.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f12.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fnotPresent_005f0);
    _jspx_th_html_005frewrite_005f12.setPage("/images/lbl.gif");
    int _jspx_eval_html_005frewrite_005f12 = _jspx_th_html_005frewrite_005f12.doStartTag();
    if (_jspx_th_html_005frewrite_005f12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f12);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f12);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f13(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f13 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f13.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f13.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fnotPresent_005f0);
    _jspx_th_html_005frewrite_005f13.setPage("/images/lbl.gif");
    int _jspx_eval_html_005frewrite_005f13 = _jspx_th_html_005frewrite_005f13.doStartTag();
    if (_jspx_th_html_005frewrite_005f13.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f13);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f13);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f14(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f14 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f14.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f14.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fnotPresent_005f0);
    _jspx_th_html_005frewrite_005f14.setPage("/usermanage/delMember.do");
    int _jspx_eval_html_005frewrite_005f14 = _jspx_th_html_005frewrite_005f14.doStartTag();
    if (_jspx_th_html_005frewrite_005f14.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f14);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fnobody.reuse(_jspx_th_html_005frewrite_005f14);
    return false;
  }
}
