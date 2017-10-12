package org.apache.jsp.portlet.cms.xrepeater;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class view_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(7);
    _jspx_dependants.add("/WEB-INF/struts-bean.tld");
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
    _jspx_dependants.add("/WEB-INF/struts-logic.tld");
    _jspx_dependants.add("/WEB-INF/struts-tiles.tld");
    _jspx_dependants.add("/WEB-INF/fulong.tld");
    _jspx_dependants.add("/WEB-INF/fulong-portal.tld");
    _jspx_dependants.add("/WEB-INF/fulong-site.tld");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fignore_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fname_005fid_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005ffulong_005ffor_0026_005fproperty_005fname_005fid;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fsite_005finsert_0026_005fpage_005fflush_005fcontextName_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fignore_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fname_005fid_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005ffulong_005ffor_0026_005fproperty_005fname_005fid = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fsite_005finsert_0026_005fpage_005fflush_005fcontextName_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.release();
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fignore_005fnobody.release();
    _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
    _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fname_005fid_005fnobody.release();
    _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.release();
    _005fjspx_005ftagPool_005ffulong_005ffor_0026_005fproperty_005fname_005fid.release();
    _005fjspx_005ftagPool_005fsite_005finsert_0026_005fpage_005fflush_005fcontextName_005fnobody.release();
    _005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.release();
    _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.release();
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

    java.lang.Integer _jspx_column_1 = null;

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
      out.write("<table id=\"");
      if (_jspx_meth_bean_005fwrite_005f0(_jspx_page_context))
        return;
      out.write("\" class=\"");
      if (_jspx_meth_bean_005fwrite_005f1(_jspx_page_context))
        return;
      out.write("\" cellspacing=\"0\" cellpadding=\"1\" border=\"0\" width=\"100%\">\r\n");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_005fdefine_005f0 = (org.apache.struts.taglib.bean.DefineTag) _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fdefine_005f0.setParent(null);
      // /portlet/cms/xrepeater/view.jsp(3,0) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fdefine_005f0.setId("definitionId");
      // /portlet/cms/xrepeater/view.jsp(3,0) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fdefine_005f0.setName("preferences");
      // /portlet/cms/xrepeater/view.jsp(3,0) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fdefine_005f0.setProperty("value(category)");
      // /portlet/cms/xrepeater/view.jsp(3,0) name = type type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
      int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
      if (_jspx_th_bean_005fdefine_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
        return;
      }
      _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
      java.lang.String definitionId = null;
      definitionId = (java.lang.String) _jspx_page_context.findAttribute("definitionId");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_005fdefine_005f1 = (org.apache.struts.taglib.bean.DefineTag) _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fdefine_005f1.setParent(null);
      // /portlet/cms/xrepeater/view.jsp(4,0) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fdefine_005f1.setId("columnCount");
      // /portlet/cms/xrepeater/view.jsp(4,0) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fdefine_005f1.setName("preferences");
      // /portlet/cms/xrepeater/view.jsp(4,0) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fdefine_005f1.setProperty("value(column)");
      // /portlet/cms/xrepeater/view.jsp(4,0) name = type type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
      int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
      if (_jspx_th_bean_005fdefine_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
        return;
      }
      _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
      java.lang.String columnCount = null;
      columnCount = (java.lang.String) _jspx_page_context.findAttribute("columnCount");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_005fdefine_005f2 = (org.apache.struts.taglib.bean.DefineTag) _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fname_005fid_005fnobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fdefine_005f2.setParent(null);
      // /portlet/cms/xrepeater/view.jsp(5,0) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fdefine_005f2.setId("path");
      // /portlet/cms/xrepeater/view.jsp(5,0) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fdefine_005f2.setName("path");
      // /portlet/cms/xrepeater/view.jsp(5,0) name = type type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
      int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
      if (_jspx_th_bean_005fdefine_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
        return;
      }
      _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
      java.lang.String path = null;
      path = (java.lang.String) _jspx_page_context.findAttribute("path");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_005fdefine_005f3 = (org.apache.struts.taglib.bean.DefineTag) _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fname_005fid_005fnobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fdefine_005f3.setParent(null);
      // /portlet/cms/xrepeater/view.jsp(6,0) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fdefine_005f3.setId("contextName");
      // /portlet/cms/xrepeater/view.jsp(6,0) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fdefine_005f3.setName("contextName");
      // /portlet/cms/xrepeater/view.jsp(6,0) name = type type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
      int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
      if (_jspx_th_bean_005fdefine_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
        return;
      }
      _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
      java.lang.String contextName = null;
      contextName = (java.lang.String) _jspx_page_context.findAttribute("contextName");
      out.write('\r');
      out.write('\n');
      //  logic:notEmpty
      org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (org.apache.struts.taglib.logic.NotEmptyTag) _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
      _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
      _jspx_th_logic_005fnotEmpty_005f0.setParent(null);
      // /portlet/cms/xrepeater/view.jsp(7,0) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_logic_005fnotEmpty_005f0.setName("preferences");
      // /portlet/cms/xrepeater/view.jsp(7,0) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_logic_005fnotEmpty_005f0.setProperty("value(row)");
      int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
      if (_jspx_eval_logic_005fnotEmpty_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write('\r');
          out.write('\n');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_005fdefine_005f4 = (org.apache.struts.taglib.bean.DefineTag) _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
          _jspx_th_bean_005fdefine_005f4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fnotEmpty_005f0);
          // /portlet/cms/xrepeater/view.jsp(8,0) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_bean_005fdefine_005f4.setId("rowCount");
          // /portlet/cms/xrepeater/view.jsp(8,0) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_bean_005fdefine_005f4.setName("preferences");
          // /portlet/cms/xrepeater/view.jsp(8,0) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_bean_005fdefine_005f4.setProperty("value(row)");
          // /portlet/cms/xrepeater/view.jsp(8,0) name = type type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
          int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
          if (_jspx_th_bean_005fdefine_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
            return;
          }
          _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
          java.lang.String rowCount = null;
          rowCount = (java.lang.String) _jspx_page_context.findAttribute("rowCount");
          out.write('\r');
          out.write('\n');
          //  fulong:for
          com.fulong.taglib.common.ForTag _jspx_th_fulong_005ffor_005f0 = (com.fulong.taglib.common.ForTag) _005fjspx_005ftagPool_005ffulong_005ffor_0026_005fproperty_005fname_005fid.get(com.fulong.taglib.common.ForTag.class);
          _jspx_th_fulong_005ffor_005f0.setPageContext(_jspx_page_context);
          _jspx_th_fulong_005ffor_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fnotEmpty_005f0);
          // /portlet/cms/xrepeater/view.jsp(9,0) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_fulong_005ffor_005f0.setId("row");
          // /portlet/cms/xrepeater/view.jsp(9,0) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_fulong_005ffor_005f0.setName("preferences");
          // /portlet/cms/xrepeater/view.jsp(9,0) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_fulong_005ffor_005f0.setProperty("value(row)");
          int _jspx_eval_fulong_005ffor_005f0 = _jspx_th_fulong_005ffor_005f0.doStartTag();
          if (_jspx_eval_fulong_005ffor_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            java.lang.Integer row = null;
            if (_jspx_eval_fulong_005ffor_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.pushBody();
              _jspx_th_fulong_005ffor_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
              _jspx_th_fulong_005ffor_005f0.doInitBody();
            }
            row = (java.lang.Integer) _jspx_page_context.findAttribute("row");
            do {
              out.write("<tr>\r\n");
              out.write("  ");
              //  fulong:for
              com.fulong.taglib.common.ForTag _jspx_th_fulong_005ffor_005f1 = (com.fulong.taglib.common.ForTag) _005fjspx_005ftagPool_005ffulong_005ffor_0026_005fproperty_005fname_005fid.get(com.fulong.taglib.common.ForTag.class);
              _jspx_th_fulong_005ffor_005f1.setPageContext(_jspx_page_context);
              _jspx_th_fulong_005ffor_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005ffor_005f0);
              // /portlet/cms/xrepeater/view.jsp(10,2) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_fulong_005ffor_005f1.setId("column");
              // /portlet/cms/xrepeater/view.jsp(10,2) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_fulong_005ffor_005f1.setName("preferences");
              // /portlet/cms/xrepeater/view.jsp(10,2) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_fulong_005ffor_005f1.setProperty("value(column)");
              int _jspx_eval_fulong_005ffor_005f1 = _jspx_th_fulong_005ffor_005f1.doStartTag();
              if (_jspx_eval_fulong_005ffor_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                java.lang.Integer column = null;
                if (_jspx_eval_fulong_005ffor_005f1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.pushBody();
                  _jspx_th_fulong_005ffor_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
                  _jspx_th_fulong_005ffor_005f1.doInitBody();
                }
                column = (java.lang.Integer) _jspx_page_context.findAttribute("column");
                do {
                  out.write("\r\n");
                  out.write("      <td>\r\n");
                  out.write("        ");
                  //  site:insert
                  com.fulong.taglib.site.InsertTag _jspx_th_site_005finsert_005f0 = (com.fulong.taglib.site.InsertTag) _005fjspx_005ftagPool_005fsite_005finsert_0026_005fpage_005fflush_005fcontextName_005fnobody.get(com.fulong.taglib.site.InsertTag.class);
                  _jspx_th_site_005finsert_005f0.setPageContext(_jspx_page_context);
                  _jspx_th_site_005finsert_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005ffor_005f1);
                  // /portlet/cms/xrepeater/view.jsp(12,8) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
                  _jspx_th_site_005finsert_005f0.setPage( path +"?portlet.mode=final&definition="+ definitionId );
                  // /portlet/cms/xrepeater/view.jsp(12,8) name = contextName type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
                  _jspx_th_site_005finsert_005f0.setContextName( contextName );
                  // /portlet/cms/xrepeater/view.jsp(12,8) name = flush type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
                  _jspx_th_site_005finsert_005f0.setFlush(false);
                  int _jspx_eval_site_005finsert_005f0 = _jspx_th_site_005finsert_005f0.doStartTag();
                  if (_jspx_th_site_005finsert_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _005fjspx_005ftagPool_005fsite_005finsert_0026_005fpage_005fflush_005fcontextName_005fnobody.reuse(_jspx_th_site_005finsert_005f0);
                    return;
                  }
                  _005fjspx_005ftagPool_005fsite_005finsert_0026_005fpage_005fflush_005fcontextName_005fnobody.reuse(_jspx_th_site_005finsert_005f0);
                  out.write("\r\n");
                  out.write("      </td>\r\n");
                  out.write("  ");
                  int evalDoAfterBody = _jspx_th_fulong_005ffor_005f1.doAfterBody();
                  column = (java.lang.Integer) _jspx_page_context.findAttribute("column");
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
                if (_jspx_eval_fulong_005ffor_005f1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.popBody();
                }
              }
              if (_jspx_th_fulong_005ffor_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _005fjspx_005ftagPool_005ffulong_005ffor_0026_005fproperty_005fname_005fid.reuse(_jspx_th_fulong_005ffor_005f1);
                return;
              }
              _005fjspx_005ftagPool_005ffulong_005ffor_0026_005fproperty_005fname_005fid.reuse(_jspx_th_fulong_005ffor_005f1);
              out.write("\r\n");
              out.write("</tr>\r\n");
              int evalDoAfterBody = _jspx_th_fulong_005ffor_005f0.doAfterBody();
              row = (java.lang.Integer) _jspx_page_context.findAttribute("row");
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
            if (_jspx_eval_fulong_005ffor_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.popBody();
            }
          }
          if (_jspx_th_fulong_005ffor_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _005fjspx_005ftagPool_005ffulong_005ffor_0026_005fproperty_005fname_005fid.reuse(_jspx_th_fulong_005ffor_005f0);
            return;
          }
          _005fjspx_005ftagPool_005ffulong_005ffor_0026_005fproperty_005fname_005fid.reuse(_jspx_th_fulong_005ffor_005f0);
          out.write('\r');
          out.write('\n');
          int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
        return;
      }
      _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
      out.write('\r');
      out.write('\n');
      //  logic:empty
      org.apache.struts.taglib.logic.EmptyTag _jspx_th_logic_005fempty_005f0 = (org.apache.struts.taglib.logic.EmptyTag) _005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.get(org.apache.struts.taglib.logic.EmptyTag.class);
      _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
      _jspx_th_logic_005fempty_005f0.setParent(null);
      // /portlet/cms/xrepeater/view.jsp(18,0) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_logic_005fempty_005f0.setName("preferences");
      // /portlet/cms/xrepeater/view.jsp(18,0) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_logic_005fempty_005f0.setProperty("value(row)");
      int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
      if (_jspx_eval_logic_005fempty_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("    <tr>\r\n");
          out.write("      ");
          //  fulong:for
          com.fulong.taglib.common.ForTag _jspx_th_fulong_005ffor_005f2 = (com.fulong.taglib.common.ForTag) _005fjspx_005ftagPool_005ffulong_005ffor_0026_005fproperty_005fname_005fid.get(com.fulong.taglib.common.ForTag.class);
          _jspx_th_fulong_005ffor_005f2.setPageContext(_jspx_page_context);
          _jspx_th_fulong_005ffor_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fempty_005f0);
          // /portlet/cms/xrepeater/view.jsp(20,6) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_fulong_005ffor_005f2.setId("column");
          // /portlet/cms/xrepeater/view.jsp(20,6) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_fulong_005ffor_005f2.setName("preferences");
          // /portlet/cms/xrepeater/view.jsp(20,6) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_fulong_005ffor_005f2.setProperty("value(column)");
          int _jspx_eval_fulong_005ffor_005f2 = _jspx_th_fulong_005ffor_005f2.doStartTag();
          if (_jspx_eval_fulong_005ffor_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            java.lang.Integer column = null;
            if (_jspx_eval_fulong_005ffor_005f2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.pushBody();
              _jspx_th_fulong_005ffor_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
              _jspx_th_fulong_005ffor_005f2.doInitBody();
            }
            column = (java.lang.Integer) _jspx_page_context.findAttribute("column");
            do {
              out.write("\r\n");
              out.write("      <td>\r\n");
              out.write("        ");
              //  site:insert
              com.fulong.taglib.site.InsertTag _jspx_th_site_005finsert_005f1 = (com.fulong.taglib.site.InsertTag) _005fjspx_005ftagPool_005fsite_005finsert_0026_005fpage_005fflush_005fcontextName_005fnobody.get(com.fulong.taglib.site.InsertTag.class);
              _jspx_th_site_005finsert_005f1.setPageContext(_jspx_page_context);
              _jspx_th_site_005finsert_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fulong_005ffor_005f2);
              // /portlet/cms/xrepeater/view.jsp(22,8) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_site_005finsert_005f1.setPage( path +"?portlet.mode=final&definition="+ definitionId );
              // /portlet/cms/xrepeater/view.jsp(22,8) name = contextName type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_site_005finsert_005f1.setContextName( contextName );
              // /portlet/cms/xrepeater/view.jsp(22,8) name = flush type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_site_005finsert_005f1.setFlush(false);
              int _jspx_eval_site_005finsert_005f1 = _jspx_th_site_005finsert_005f1.doStartTag();
              if (_jspx_th_site_005finsert_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _005fjspx_005ftagPool_005fsite_005finsert_0026_005fpage_005fflush_005fcontextName_005fnobody.reuse(_jspx_th_site_005finsert_005f1);
                return;
              }
              _005fjspx_005ftagPool_005fsite_005finsert_0026_005fpage_005fflush_005fcontextName_005fnobody.reuse(_jspx_th_site_005finsert_005f1);
              out.write("\r\n");
              out.write("      </td>\r\n");
              out.write("      ");
              int evalDoAfterBody = _jspx_th_fulong_005ffor_005f2.doAfterBody();
              column = (java.lang.Integer) _jspx_page_context.findAttribute("column");
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
            if (_jspx_eval_fulong_005ffor_005f2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.popBody();
            }
          }
          if (_jspx_th_fulong_005ffor_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _005fjspx_005ftagPool_005ffulong_005ffor_0026_005fproperty_005fname_005fid.reuse(_jspx_th_fulong_005ffor_005f2);
            return;
          }
          _005fjspx_005ftagPool_005ffulong_005ffor_0026_005fproperty_005fname_005fid.reuse(_jspx_th_fulong_005ffor_005f2);
          out.write("\r\n");
          out.write("    </tr>\r\n");
          int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_005fempty_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f0);
        return;
      }
      _005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f0);
      out.write("\r\n");
      out.write("</table>\r\n");
      if (_jspx_meth_logic_005fequal_005f0(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
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

  private boolean _jspx_meth_bean_005fwrite_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f0 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fwrite_005f0.setParent(null);
    // /portlet/cms/xrepeater/view.jsp(2,11) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
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
    // /portlet/cms/xrepeater/view.jsp(2,57) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fwrite_005f1.setName("preferences");
    // /portlet/cms/xrepeater/view.jsp(2,57) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fwrite_005f1.setProperty("value(table-style)");
    // /portlet/cms/xrepeater/view.jsp(2,57) name = ignore type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fwrite_005f1.setIgnore(true);
    int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
    if (_jspx_th_bean_005fwrite_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fignore_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fignore_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
    return false;
  }

  private boolean _jspx_meth_logic_005fequal_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_005fequal_005f0 = (org.apache.struts.taglib.logic.EqualTag) _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_005fequal_005f0.setPageContext(_jspx_page_context);
    _jspx_th_logic_005fequal_005f0.setParent(null);
    // /portlet/cms/xrepeater/view.jsp(28,0) name = value type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_logic_005fequal_005f0.setValue("true");
    // /portlet/cms/xrepeater/view.jsp(28,0) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_logic_005fequal_005f0.setName("preferences");
    // /portlet/cms/xrepeater/view.jsp(28,0) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_logic_005fequal_005f0.setProperty("value(show-pager)");
    int _jspx_eval_logic_005fequal_005f0 = _jspx_th_logic_005fequal_005f0.doStartTag();
    if (_jspx_eval_logic_005fequal_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("    <div class=\"");
        if (_jspx_meth_bean_005fwrite_005f2(_jspx_th_logic_005fequal_005f0, _jspx_page_context))
          return true;
        out.write("\">共计100项内容，当前为3/5页，<a href=\"#\">首页</a>，<a href=\"#\">上一页</a>，<a href=\"#\">下一页</a>，<a href=\"#\">末页</a></div>\r\n");
        int evalDoAfterBody = _jspx_th_logic_005fequal_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_005fequal_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f0);
    return false;
  }

  private boolean _jspx_meth_bean_005fwrite_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fequal_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f2 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fignore_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_005fwrite_005f2.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fwrite_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fequal_005f0);
    // /portlet/cms/xrepeater/view.jsp(29,16) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fwrite_005f2.setName("preferences");
    // /portlet/cms/xrepeater/view.jsp(29,16) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fwrite_005f2.setProperty("value(pager-style)");
    // /portlet/cms/xrepeater/view.jsp(29,16) name = ignore type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fwrite_005f2.setIgnore(true);
    int _jspx_eval_bean_005fwrite_005f2 = _jspx_th_bean_005fwrite_005f2.doStartTag();
    if (_jspx_th_bean_005fwrite_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fignore_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fignore_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
    return false;
  }
}
