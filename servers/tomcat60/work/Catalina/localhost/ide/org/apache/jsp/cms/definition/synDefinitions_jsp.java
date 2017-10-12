package org.apache.jsp.cms.definition;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class synDefinitions_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(4);
    _jspx_dependants.add("/WEB-INF/struts-bean.tld");
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
    _jspx_dependants.add("/WEB-INF/struts-logic.tld");
    _jspx_dependants.add("/WEB-INF/struts-tiles.tld");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.release();
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

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n");
      out.write("<head>\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n");
      out.write("<title>选择内容分类</title>\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      if (_jspx_meth_html_005frewrite_005f0(_jspx_page_context))
        return;
      out.write("\" />\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"");
      if (_jspx_meth_html_005frewrite_005f1(_jspx_page_context))
        return;
      out.write("\"></script>\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"");
      if (_jspx_meth_html_005frewrite_005f2(_jspx_page_context))
        return;
      out.write("\"></script>\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"");
      if (_jspx_meth_html_005frewrite_005f3(_jspx_page_context))
        return;
      out.write("\"></script>\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"../classes/objects.js\"></script>\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"../classes/cmstree.js\"></script>\n");
      out.write(" <script language=\"JavaScript\" type=\"text/Javascript\">\n");
      out.write(" \tvar repository= new Repository();\n");
      out.write(" \tvar defId = window.dialogArguments.definition;\n");
      out.write(" \tif(defId == null)\n");
      out.write(" \t \tdefId = \"no-properties-scheme\";\t \n");
      out.write(" \tvar definition = repository.getNodeDefinition(defId);\n");
      out.write(" \tvar tree;\n");
      out.write(" \tvar multiple = window.dialogArguments.multiple;\n");
      out.write(" \tfunction doInit(){ \t\t \n");
      out.write(" \t\t tree = new CMSTree(definition);\n");
      out.write(" \t\t document.getElementById(\"navcontainer\").innerHTML = tree.toString();\n");
      out.write(" \t\t tree.expand();\t \n");
      out.write(" \t} \t\t  \t  \t\n");
      out.write(" </script>    \n");
      out.write("  \t<script language=\"JavaScript\" type=\"text/Javascript\">\n");
      out.write("  \tWebFXTreeAbstractNode.prototype.getText = function(){\n");
      out.write("  \t  \tif(this.element == null)\n");
      out.write("  \t  \t  \treturn \"\";\n");
      out.write("  \t  \tif(multiple){\n");
      out.write("  \t  \t  \tvar text = \"<input type='checkbox' name='definition' label='\" +this.element.getName()+ \"' id='\"+this.element.getID()+\"' value='\"+this.element.getID()+\"'/>\";\n");
      out.write("  \t  \t  \ttext +=\"<label for='\"+this.element.getID()+\"'>\"+this.element.getName()+\"</label>\";\n");
      out.write("  \t  \t  \treturn text;\n");
      out.write("  \t  \t}else{\n");
      out.write("  \t  \t  \tvar text = \"<input type='radio' name='definition' label='\" +this.element.getName()+ \"' id='\"+this.element.getID()+\"' value='\"+this.element.getID()+\"'/>\";\n");
      out.write("  \t  \t  \ttext +=\"<label for='\"+this.element.getID()+\"'>\"+this.element.getName()+\"</label>\";\n");
      out.write("  \t  \t  \treturn text;\n");
      out.write("  \t  \t  \t\n");
      out.write("  \t  \t}\n");
      out.write("  \t};\n");
      out.write("\t</script>\n");
      out.write("    <script language=\"JavaScript\" type=\"text/Javascript\">\n");
      out.write("      var selectedID=new Array();\n");
      out.write("      function setSelect(input){\n");
      out.write("        var imgs = input.parentElement.parentElement.getElementsByTagName(\"img\");\n");
      out.write("        imgs[imgs.length-1].click()\n");
      out.write("      }\n");
      out.write("      function ok(aForm){\n");
      out.write("        var results=new Array();\n");
      out.write("        for(var i=0;i<aForm.elements['definition'].length;i++){\n");
      out.write("              var elem =aForm.elements['definition'][i]; \n");
      out.write("            if(elem.checked){\n");
      out.write("               var obj = new Object();\n");
      out.write("               obj.ID = elem.value;\n");
      out.write("               obj.name = elem.getAttribute(\"label\");\n");
      out.write("               results.push(obj);\n");
      out.write("            }\n");
      out.write("          }\n");
      out.write("        if(multiple)\n");
      out.write("          window.parent.returnValue=results;       \n");
      out.write("        else{\n");
      out.write("            if(results.length >0)\n");
      out.write("                window.parent.returnValue = results[0];\n");
      out.write("        }          \n");
      out.write("        if(navigator.userAgent.toLowerCase().indexOf(\"firefox\")>=0){\n");
      out.write("            window.parent.close();\n");
      out.write("        }else{\n");
      out.write("       \t window.close();\n");
      out.write("        }\n");
      out.write("      }\n");
      out.write("      function _close(){\n");
      out.write("         if(navigator.userAgent.toLowerCase().indexOf(\"firefox\")>=0){\n");
      out.write("             window.parent.close();\n");
      out.write("         }else{\n");
      out.write("        \t window.close();\n");
      out.write("         }\n");
      out.write("      }\n");
      out.write("     </script>\n");
      out.write("</head>\n");
      out.write("<body onload=\"doInit()\"><form action=\"#\">\n");
      out.write("      <table width=\"100%\" class=\"sheetClass\" cellpadding=\"2\" cellspacing=\"0\">\n");
      out.write("        <tr>\n");
      out.write("          <td>\n");
      out.write("            <div class=\"insetDiv\" style=\"height:198px\" id=\"navcontainer\">\n");
      out.write("            </div>\n");
      out.write("          </td>\n");
      out.write("        </tr>\n");
      out.write("      </table>\n");
      out.write("      <div class=\"operation\">\n");
      out.write("        <button type=\"button\" onclick=\"ok(this.form)\" class=\"commonbut\" id=\"tijiao\">确定</button>\n");
      out.write("        <button type=\"button\" onclick=\"_close()\" class=\"commonbut\" id=\"back\">取消</button>\n");
      out.write("      </div>   \n");
      out.write("      </form>\n");
      out.write("</body>\n");
      out.write("</html>");
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
    // /cms/definition/synDefinitions.jsp(11,45) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f0.setModule("/common");
    // /cms/definition/synDefinitions.jsp(11,45) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
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
    // /cms/definition/synDefinitions.jsp(12,58) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f1.setModule("/common");
    // /cms/definition/synDefinitions.jsp(12,58) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
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
    // /cms/definition/synDefinitions.jsp(13,58) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f2.setModule("/common");
    // /cms/definition/synDefinitions.jsp(13,58) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
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
    // /cms/definition/synDefinitions.jsp(14,58) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f3.setModule("/common");
    // /cms/definition/synDefinitions.jsp(14,58) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f3.setPage("/xtree/tree.js.jsp");
    int _jspx_eval_html_005frewrite_005f3 = _jspx_th_html_005frewrite_005f3.doStartTag();
    if (_jspx_th_html_005frewrite_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f3);
    return false;
  }
}
