package org.apache.jsp.site.channel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class auth_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(4);
    _jspx_dependants.add("/WEB-INF/struts-bean.tld");
    _jspx_dependants.add("/WEB-INF/struts-logic.tld");
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
    _jspx_dependants.add("/WEB-INF/struts-tiles.tld");
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
      out.write("<title>权限设置</title>\r\n");
      out.write("<meta http-equiv=Content-Type content=\"text/html; charset=UTF-8\" />\r\n");
      out.write("<meta http-equiv=\"Cache-Control\" content=\"no-cache, must-revalidate\" />\r\n");
      out.write("<meta http-equiv=\"pragma\" content=\"no-cache\">\r\n");
      out.write("<meta http-equiv=\"expires\" content=\"Wed, 26 Feb 1997 08:21:57 GMT\" />\r\n");
      out.write("<base target=\"_self\" />   \r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      if (_jspx_meth_html_005frewrite_005f0(_jspx_page_context))
        return;
      out.write("\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"create.css\" />\r\n");
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
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"");
      if (_jspx_meth_html_005frewrite_005f4(_jspx_page_context))
        return;
      out.write("\"></script>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"");
      if (_jspx_meth_html_005frewrite_005f5(_jspx_page_context))
        return;
      out.write("\"></script>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"");
      if (_jspx_meth_html_005frewrite_005f6(_jspx_page_context))
        return;
      out.write("\"></script>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"");
      if (_jspx_meth_html_005frewrite_005f7(_jspx_page_context))
        return;
      out.write("\"></script>   \r\n");
      out.write("    <script language=\"Javascript\" type=\"text/Javascript\">\r\n");
      out.write("    \tvar FCK = window.dialogArguments.FCK;\r\n");
      out.write("    \tvar oTemplate = window.dialogArguments.oTemplate;\r\n");
      out.write("    \tvar allows = getAllow();\r\n");
      out.write("    \tvar tree;\r\n");
      out.write("    \tvar repository = new Repository();\r\n");
      out.write("    \tvar definition = repository.getNodeDefinition(\"principal-scheme\");\r\n");
      out.write("    \tWebFXTreeAbstractNode.prototype.getText = function(){\r\n");
      out.write("        \tif(this.element){\r\n");
      out.write("\t        \tvar id = this.element.getID();\r\n");
      out.write("\t        \tvar name = this.element.getName();\r\n");
      out.write("\t        \tvar disabled = !document.all(\"openBtn\").checked;\r\n");
      out.write("\t        \tvar text ='<input type=\"checkbox\" style=\"height:12px;width:14px\" name=\"principals\" ';\r\n");
      out.write("\t        \tif(disabled)\r\n");
      out.write("\t        \t\ttext +='disabled=\"disabled\"';\r\n");
      out.write("        \t\tif(allows!=null&&allows.contains(id)){\r\n");
      out.write("        \t\t\ttext +=' checked=\"checked\" ';\r\n");
      out.write("        \t\t}\r\n");
      out.write("\t        \ttext +=' value=\"'+id+ '\" id=\"'+id+ '\" />';\r\n");
      out.write("\t        \ttext +='<label for=\"'+id+'\">'+name+'</label>';\r\n");
      out.write("\t        \treturn text;\r\n");
      out.write("        \t}else{\r\n");
      out.write("            \treturn this.text;\r\n");
      out.write("        \t}\r\n");
      out.write("    \t};\r\n");
      out.write("\r\n");
      out.write("    \t\r\n");
      out.write("    \tfunction setAllow(allow){\r\n");
      out.write("        \tif(allow==null){\r\n");
      out.write("        \t\tremoveAllow();\r\n");
      out.write("        \t\treturn;\r\n");
      out.write("        \t}\r\n");
      out.write("        \tallow = allow.join(\",\");\r\n");
      out.write("\t\t\tif(/<site:html(.*?)allow=\\\"([^\\\"]*)\\\"(.*?)/i.test(FCK.Html)){\r\n");
      out.write("\t\t\t\tFCK.Html = FCK.Html.replace(/(<site:html(.*?)allow=\\\")([^\\\"]*)(\\\"(.*?))/i,\"$1\"+allow+\"$4\");\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\tFCK.Html = FCK.Html.replace(/(<site:html(.*?))(>)/i,\"$1 allow=\\\"\"+allow+\"\\\"$3\");\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tfunction removeAllow(){\r\n");
      out.write("\t\t\tif(/<site:html(.*?)allow=\\\"[^\\\"]*\\\"(.*?)/i.test(FCK.Html)){\r\n");
      out.write("\t\t\t\tFCK.Html = FCK.Html.replace(/(<site:html(.*?))(allow=\\\"[^\\\"]*\\\")(.*?)/i,\"$1$4\");\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tfunction getAllow(){\r\n");
      out.write("\t\t\tif(/<site:html(.*?)allow=\\\"([^\\\"]*)\\\"(.*?)/i.test(FCK.Html)){\r\n");
      out.write("\t\t\t\tvar s = RegExp[\"$2\"];\r\n");
      out.write("\t\t\t\treturn s.split(\",\");\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\treturn null;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tfunction initTree(){\r\n");
      out.write("\t   \t\ttree = new CMSTree(definition);\r\n");
      out.write("\t\t\tdocument.all(\"d1\").innerHTML = tree.toString();\r\n");
      out.write("\t\t\ttree.expand(); \r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tfunction initAuth(){\r\n");
      out.write("\t\t\tvar secure = document.getElementById(\"openBtn\");\r\n");
      out.write("\t\t\tsecure.checked = allows?true:false;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tfunction doInit(){\r\n");
      out.write("\t\t\tinitAuth();\r\n");
      out.write("\t\t\tinitTree();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tfunction saveAuth(){\r\n");
      out.write("\t\t\tvar secure = document.getElementById(\"openBtn\");\r\n");
      out.write("\t\t\tvar category = null;\r\n");
      out.write("\t\t\tif(secure.checked){\r\n");
      out.write("\t\t\t\tcategory = new Array();\r\n");
      out.write("\t\t\t\tvar checkboxes = document.getElementsByName(\"principals\");\r\n");
      out.write("\t\t        for(var i=0;i<checkboxes.length;i++){\r\n");
      out.write("\t\t            if( checkboxes[i].checked){\r\n");
      out.write("\t\t            \tcategory.push(checkboxes[i].value);\r\n");
      out.write("\t\t            }\r\n");
      out.write("\t\t        }\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\tsetAllow(category);\r\n");
      out.write("\t\t\twindow.close();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tfunction _Grant() {\r\n");
      out.write("\t    \tvar ret = SiteDialog.Permission(oTemplate)\r\n");
      out.write("\t    \tif (ret)\r\n");
      out.write("\t    \t\ttop.Reload();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tfunction synSelection(){\r\n");
      out.write("\t        var checkboxes = document.getElementsByName(\"principals\");\r\n");
      out.write("\t        for(var i=0;i<checkboxes.length;i++)\r\n");
      out.write("\t             checkboxes[i].disabled = !document.all(\"openBtn\").checked;\r\n");
      out.write("\t    }\r\n");
      out.write("    </script>\r\n");
      out.write("</head>\r\n");
      out.write("<body onload=\"doInit()\">\r\n");
      out.write("      <table width=\"100%\" cellpadding=\"2\" cellspacing=\"2\" border=\"0\">\r\n");
      out.write("        <tr>\r\n");
      out.write("          <td>\r\n");
      out.write("            <input style=\"width:14px;height:12px\" type=\"checkbox\" value=\"true\" id=\"openBtn\" name=\"secure\" onclick=\"synSelection()\"/><label for=\"openBtn\">用户必需登入方可访问这个页面。</label>\r\n");
      out.write("          </td>\r\n");
      out.write("        </tr>\r\n");
      out.write("        <tr>\r\n");
      out.write("          <td>\r\n");
      out.write("             <div id=\"d1\" class=\"insetDiv\" style=\"height:180px;width:100%\">\r\n");
      out.write("             </div>\r\n");
      out.write("           </td>           \r\n");
      out.write("            </tr>\r\n");
      out.write("          </table>\r\n");
      out.write("          <div class=\"operation\">\r\n");
      out.write("            <!--  <button onclick=\"_Grant()\">用户授权...</button> -->\r\n");
      out.write("            <button onclick=\"saveAuth()\">确定</button>\r\n");
      out.write("            <button onclick=\"window.close()\">取消</button>\r\n");
      out.write("          </div>\r\n");
      out.write("     </body>\r\n");
      out.write("     </html>");
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

  private boolean _jspx_meth_html_005frewrite_005f2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f2 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f2.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f2.setParent(null);
    _jspx_th_html_005frewrite_005f2.setModule("/common");
    _jspx_th_html_005frewrite_005f2.setPage("/script/ajax.js");
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
    _jspx_th_html_005frewrite_005f3.setPage("/xtree/tree.js.jsp");
    int _jspx_eval_html_005frewrite_005f3 = _jspx_th_html_005frewrite_005f3.doStartTag();
    if (_jspx_th_html_005frewrite_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f3);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f4 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f4.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f4.setParent(null);
    _jspx_th_html_005frewrite_005f4.setModule("/common");
    _jspx_th_html_005frewrite_005f4.setPage("/script/dialog.jsp");
    int _jspx_eval_html_005frewrite_005f4 = _jspx_th_html_005frewrite_005f4.doStartTag();
    if (_jspx_th_html_005frewrite_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f4);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f5(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f5 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f5.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f5.setParent(null);
    _jspx_th_html_005frewrite_005f5.setModule("/site");
    _jspx_th_html_005frewrite_005f5.setPage("/dialog.js.jsp");
    int _jspx_eval_html_005frewrite_005f5 = _jspx_th_html_005frewrite_005f5.doStartTag();
    if (_jspx_th_html_005frewrite_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f5);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f5);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f6(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f6 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f6.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f6.setParent(null);
    _jspx_th_html_005frewrite_005f6.setModule("/cms");
    _jspx_th_html_005frewrite_005f6.setPage("/classes/objects.js");
    int _jspx_eval_html_005frewrite_005f6 = _jspx_th_html_005frewrite_005f6.doStartTag();
    if (_jspx_th_html_005frewrite_005f6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f6);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f6);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f7(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f7 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f7.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f7.setParent(null);
    _jspx_th_html_005frewrite_005f7.setModule("/cms");
    _jspx_th_html_005frewrite_005f7.setPage("/classes/cmstree.js");
    int _jspx_eval_html_005frewrite_005f7 = _jspx_th_html_005frewrite_005f7.doStartTag();
    if (_jspx_th_html_005frewrite_005f7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f7);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f7);
    return false;
  }
}
