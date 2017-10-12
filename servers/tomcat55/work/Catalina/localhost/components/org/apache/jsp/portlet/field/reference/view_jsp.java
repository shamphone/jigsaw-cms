package org.apache.jsp.portlet.field.reference;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class view_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(6);
    _jspx_dependants.add("/WEB-INF/struts-bean.tld");
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
    _jspx_dependants.add("/WEB-INF/struts-logic.tld");
    _jspx_dependants.add("/WEB-INF/struts-tiles.tld");
    _jspx_dependants.add("/WEB-INF/fulong.tld");
    _jspx_dependants.add("/WEB-INF/fulong-portal.tld");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fignore_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage_005fflush_005fnobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fignore_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage_005fflush_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.release();
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fignore_005fnobody.release();
    _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage_005fflush_005fnobody.release();
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

      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_005fdefine_005f0 = (org.apache.struts.taglib.bean.DefineTag) _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fdefine_005f0.setParent(null);
      _jspx_th_bean_005fdefine_005f0.setId("definitionId");
      _jspx_th_bean_005fdefine_005f0.setName("preferences");
      _jspx_th_bean_005fdefine_005f0.setProperty("value(category)");
      _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
      int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
      if (_jspx_th_bean_005fdefine_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
        return;
      }
      _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
      java.lang.String definitionId = null;
      definitionId = (java.lang.String) _jspx_page_context.findAttribute("definitionId");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_005fdefine_005f1 = (org.apache.struts.taglib.bean.DefineTag) _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fdefine_005f1.setParent(null);
      _jspx_th_bean_005fdefine_005f1.setId("path");
      _jspx_th_bean_005fdefine_005f1.setName("preferences");
      _jspx_th_bean_005fdefine_005f1.setProperty("value(clip-path)");
      _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
      int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
      if (_jspx_th_bean_005fdefine_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
        return;
      }
      _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
      java.lang.String path = null;
      path = (java.lang.String) _jspx_page_context.findAttribute("path");
      out.write("<ul id=\"");
      if (_jspx_meth_bean_005fwrite_005f0(_jspx_page_context))
        return;
      out.write("\" class=\"");
      if (_jspx_meth_bean_005fwrite_005f1(_jspx_page_context))
        return;
      out.write("\">\r\n");
      //  tiles:insert
      org.apache.struts.taglib.tiles.InsertTag _jspx_th_tiles_005finsert_005f0 = (org.apache.struts.taglib.tiles.InsertTag) _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage_005fflush_005fnobody.get(org.apache.struts.taglib.tiles.InsertTag.class);
      _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
      _jspx_th_tiles_005finsert_005f0.setParent(null);
      _jspx_th_tiles_005finsert_005f0.setPage( path +"?javax.portlet.page.mode=view&definition="+ definitionId );
      _jspx_th_tiles_005finsert_005f0.setFlush(false);
      int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
      if (_jspx_th_tiles_005finsert_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage_005fflush_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
        return;
      }
      _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage_005fflush_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
      //  tiles:insert
      org.apache.struts.taglib.tiles.InsertTag _jspx_th_tiles_005finsert_005f1 = (org.apache.struts.taglib.tiles.InsertTag) _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage_005fflush_005fnobody.get(org.apache.struts.taglib.tiles.InsertTag.class);
      _jspx_th_tiles_005finsert_005f1.setPageContext(_jspx_page_context);
      _jspx_th_tiles_005finsert_005f1.setParent(null);
      _jspx_th_tiles_005finsert_005f1.setPage( path +"?javax.portlet.page.mode=view&definition="+ definitionId );
      _jspx_th_tiles_005finsert_005f1.setFlush(false);
      int _jspx_eval_tiles_005finsert_005f1 = _jspx_th_tiles_005finsert_005f1.doStartTag();
      if (_jspx_th_tiles_005finsert_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage_005fflush_005fnobody.reuse(_jspx_th_tiles_005finsert_005f1);
        return;
      }
      _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage_005fflush_005fnobody.reuse(_jspx_th_tiles_005finsert_005f1);
      //  tiles:insert
      org.apache.struts.taglib.tiles.InsertTag _jspx_th_tiles_005finsert_005f2 = (org.apache.struts.taglib.tiles.InsertTag) _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage_005fflush_005fnobody.get(org.apache.struts.taglib.tiles.InsertTag.class);
      _jspx_th_tiles_005finsert_005f2.setPageContext(_jspx_page_context);
      _jspx_th_tiles_005finsert_005f2.setParent(null);
      _jspx_th_tiles_005finsert_005f2.setPage( path +"?javax.portlet.page.mode=view&definition="+ definitionId );
      _jspx_th_tiles_005finsert_005f2.setFlush(false);
      int _jspx_eval_tiles_005finsert_005f2 = _jspx_th_tiles_005finsert_005f2.doStartTag();
      if (_jspx_th_tiles_005finsert_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage_005fflush_005fnobody.reuse(_jspx_th_tiles_005finsert_005f2);
        return;
      }
      _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage_005fflush_005fnobody.reuse(_jspx_th_tiles_005finsert_005f2);
      //  tiles:insert
      org.apache.struts.taglib.tiles.InsertTag _jspx_th_tiles_005finsert_005f3 = (org.apache.struts.taglib.tiles.InsertTag) _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage_005fflush_005fnobody.get(org.apache.struts.taglib.tiles.InsertTag.class);
      _jspx_th_tiles_005finsert_005f3.setPageContext(_jspx_page_context);
      _jspx_th_tiles_005finsert_005f3.setParent(null);
      _jspx_th_tiles_005finsert_005f3.setPage( path +"?javax.portlet.page.mode=view&definition="+ definitionId );
      _jspx_th_tiles_005finsert_005f3.setFlush(false);
      int _jspx_eval_tiles_005finsert_005f3 = _jspx_th_tiles_005finsert_005f3.doStartTag();
      if (_jspx_th_tiles_005finsert_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage_005fflush_005fnobody.reuse(_jspx_th_tiles_005finsert_005f3);
        return;
      }
      _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage_005fflush_005fnobody.reuse(_jspx_th_tiles_005finsert_005f3);
      //  tiles:insert
      org.apache.struts.taglib.tiles.InsertTag _jspx_th_tiles_005finsert_005f4 = (org.apache.struts.taglib.tiles.InsertTag) _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage_005fflush_005fnobody.get(org.apache.struts.taglib.tiles.InsertTag.class);
      _jspx_th_tiles_005finsert_005f4.setPageContext(_jspx_page_context);
      _jspx_th_tiles_005finsert_005f4.setParent(null);
      _jspx_th_tiles_005finsert_005f4.setPage( path +"?javax.portlet.page.mode=view&definition="+ definitionId );
      _jspx_th_tiles_005finsert_005f4.setFlush(false);
      int _jspx_eval_tiles_005finsert_005f4 = _jspx_th_tiles_005finsert_005f4.doStartTag();
      if (_jspx_th_tiles_005finsert_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage_005fflush_005fnobody.reuse(_jspx_th_tiles_005finsert_005f4);
        return;
      }
      _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage_005fflush_005fnobody.reuse(_jspx_th_tiles_005finsert_005f4);
      out.write("</ul>");
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

  private boolean _jspx_meth_bean_005fwrite_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f0 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fwrite_005f0.setParent(null);
    _jspx_th_bean_005fwrite_005f0.setName("javax.portlet.id");
    int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
    if (_jspx_th_bean_005fwrite_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
    return false;
  }

  private boolean _jspx_meth_bean_005fwrite_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f1 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fignore_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fwrite_005f1.setParent(null);
    _jspx_th_bean_005fwrite_005f1.setName("preferences");
    _jspx_th_bean_005fwrite_005f1.setProperty("value(list-style)");
    _jspx_th_bean_005fwrite_005f1.setIgnore(true);
    int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
    if (_jspx_th_bean_005fwrite_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fignore_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fignore_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
    return false;
  }
}
