package org.apache.jsp.common.module;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class modules_js_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/javascript; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("var ContextPath = '");
      if (_jspx_meth_html_005frewrite_005f0(_jspx_page_context))
        return;
      out.write("';\r\n");
      out.write("var IconsPath = ContextPath +'common/module/icons/';\r\n");
      out.write("var ModuleSpanClassName = \"spanModule\";\r\n");
      out.write("var HilightModuleSpanClassName = \"spanModuleHilight\";\r\n");
      out.write("\r\n");
      out.write("var CModuleCollection = new Object();\r\n");
      out.write("CModuleCollection.items = new Array();\r\n");
      out.write("CModuleCollection.add = function(module){\r\n");
      out.write("\tthis.items.push(module);\r\n");
      out.write("};\r\n");
      out.write("\r\n");
      out.write("CModuleCollection.get = function(id){\r\n");
      out.write("\tfor(var i=0;i<this.items.length;i++){\r\n");
      out.write("\t\tif(this.items[i].id == id)\r\n");
      out.write("\t\t\treturn this.items[i];\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("CModuleCollection.render = function(oParent, hilight){\r\n");
      out.write("\tfor(var i=0;i<this.items.length;i++){\r\n");
      out.write("\t\tvar child = this.items[i].render();\r\n");
      out.write("\t\toParent.appendChild(child);\r\n");
      out.write("\t\tif(hilight == this.items[i].id)\r\n");
      out.write("\t\t\tthis.items[i].hilight();\r\n");
      out.write("\t}\r\n");
      out.write("};\r\n");
      out.write("\r\n");
      out.write("var CModule = function(id, title, path, icon){\r\n");
      out.write("\tthis.id = id;\r\n");
      out.write("\tthis.title = title;\r\n");
      out.write("\tthis.path = path;\r\n");
      out.write("\tthis.icon = icon;\r\n");
      out.write("};\r\n");
      out.write("CModule.prototype.render=function(){\r\n");
      out.write("\tvar oSpan = document.createElement(\"span\");\r\n");
      out.write("\toSpan.title = this.title;\r\n");
      out.write("\toSpan.id = this.id;\t\r\n");
      out.write("\toSpan.className = ModuleSpanClassName;\r\n");
      out.write("\toSpan.onmouseover = CMSModule_MouseOver(this);\r\n");
      out.write("\toSpan.onmouseout = CMSModule_MouseOut(this);\r\n");
      out.write("\toSpan.onclick = CMSModule_Click(this);\r\n");
      out.write("\tvar oImg = document.createElement(\"img\");\r\n");
      out.write("\toImg.align=\"absmiddle\";\r\n");
      out.write("\toImg.src = this.icon;\r\n");
      out.write("\toImg.border = 0;\r\n");
      out.write("\toImg.width = 16;\r\n");
      out.write("\toImg.height =16;\r\n");
      out.write("\toImg.alt = this.title;\r\n");
      out.write("\toSpan.appendChild(oImg);\r\n");
      out.write("\tthis.element = oSpan;\r\n");
      out.write("\treturn oSpan;\r\n");
      out.write("};\r\n");
      out.write("CModule.prototype.hilight = function(){\r\n");
      out.write("\tthis.element.className = HilightModuleSpanClassName;\t\r\n");
      out.write("\tCModuleCollection.selectedModule = this;\r\n");
      out.write("};\r\n");
      out.write("\r\n");
      out.write("var CMSModule_Click= function(oModule){\r\n");
      out.write("\treturn function(){\r\n");
      out.write("\t\twindow.top.location = oModule.path;\r\n");
      out.write("\t}\r\n");
      out.write("};\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("var CMSModule_MouseOver= function(oModule){\r\n");
      out.write("\treturn function(){\r\n");
      out.write("\t oModule.element.className = \"spanModuleHilight\";\r\n");
      out.write("\t}\r\n");
      out.write("};\r\n");
      out.write("\r\n");
      out.write("var CMSModule_MouseOut = function(oModule){\r\n");
      out.write("\treturn function(){\r\n");
      out.write("\tif(CModuleCollection.selectedModule != oModule)\r\n");
      out.write("\t\t oModule.element.className = \"spanModule\";\r\n");
      out.write("\t}\r\n");
      out.write("};\r\n");
      out.write("var cmsModule = new CModule(\"security\",\"组织结构\", ContextPath + \"security/index.jsp\",IconsPath+ \"ums.png\");\r\n");
      out.write("CModuleCollection.add(cmsModule);\r\n");
      out.write("var cmsModule = new CModule(\"cms\",\"数据管理\", ContextPath + \"cms/manage/index.jsp\",IconsPath+ \"cms.png\");\r\n");
      out.write("CModuleCollection.add(cmsModule);\r\n");
      out.write("var cmsModule = new CModule(\"site\",\"网站设计\", ContextPath + \"site\",IconsPath+ \"site.png\");\r\n");
      out.write("CModuleCollection.add(cmsModule);\r\n");
      out.write("var serviceModule = new CModule(\"service\",\"服务管理\", ContextPath + \"service/sms/index.jsp\",IconsPath+ \"service.png\");\r\n");
      out.write("CModuleCollection.add(serviceModule);\r\n");
      out.write("var cmsModule = new CModule(\"process\",\"流程设计\", ContextPath + \"process/visual/index.jsp\",IconsPath+ \"workflow.png\");\r\n");
      out.write("CModuleCollection.add(cmsModule);\r\n");
      out.write("var cmsModule = new CModule(\"system\",\"系统监控\", \"/monitor/system/index.jsp\",IconsPath+ \"sysmgn.png\");\r\n");
      out.write("CModuleCollection.add(cmsModule);\r\n");
      out.write("var cmsModule = new CModule(\"log\",\"访问量分析\", \"/monitor/awstats/index.jsp\",IconsPath+ \"log.png\");\r\n");
      out.write("CModuleCollection.add(cmsModule);");
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
    _jspx_th_html_005frewrite_005f0.setPage("/");
    _jspx_th_html_005frewrite_005f0.setModule("");
    int _jspx_eval_html_005frewrite_005f0 = _jspx_th_html_005frewrite_005f0.doStartTag();
    if (_jspx_th_html_005frewrite_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f0);
    return false;
  }
}
