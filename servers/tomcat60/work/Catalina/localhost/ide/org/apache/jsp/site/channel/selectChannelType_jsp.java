package org.apache.jsp.site.channel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class selectChannelType_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(5);
    _jspx_dependants.add("/WEB-INF/struts-bean.tld");
    _jspx_dependants.add("/WEB-INF/struts-logic.tld");
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
    _jspx_dependants.add("/WEB-INF/struts-tiles.tld");
    _jspx_dependants.add("/WEB-INF/fulong.tld");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fdefinition;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fstyle_005fpage_005fmodule_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fdefinition = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fstyle_005fpage_005fmodule_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fdefinition.release();
    _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname.release();
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fstyle_005fpage_005fmodule_005fnobody.release();
    _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid.release();
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.release();
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
      //  tiles:insert
      org.apache.struts.taglib.tiles.InsertTag _jspx_th_tiles_005finsert_005f0 = (org.apache.struts.taglib.tiles.InsertTag) _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fdefinition.get(org.apache.struts.taglib.tiles.InsertTag.class);
      _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
      _jspx_th_tiles_005finsert_005f0.setParent(null);
      // /site/channel/selectChannelType.jsp(7,0) name = definition type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_tiles_005finsert_005f0.setDefinition("dialog_frame");
      int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
      if (_jspx_eval_tiles_005finsert_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write('\r');
          out.write('\n');
          out.write('	');
          if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
            return;
          out.write("\r\n");
          out.write("  \t");
          if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
            return;
          out.write("\r\n");
          out.write("  \t");
          //  tiles:put
          org.apache.struts.taglib.tiles.PutTag _jspx_th_tiles_005fput_005f2 = (org.apache.struts.taglib.tiles.PutTag) _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname.get(org.apache.struts.taglib.tiles.PutTag.class);
          _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
          _jspx_th_tiles_005fput_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005finsert_005f0);
          // /site/channel/selectChannelType.jsp(43,3) name = name type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_tiles_005fput_005f2.setName("dialog");
          int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
          if (_jspx_eval_tiles_005fput_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            if (_jspx_eval_tiles_005fput_005f2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.pushBody();
              _jspx_th_tiles_005fput_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
              _jspx_th_tiles_005fput_005f2.doInitBody();
            }
            do {
              out.write("\r\n");
              out.write("\t\t<table width=\"100%\" border=\"0\" cellpadding=\"4\" cellspacing=\"0\">\r\n");
              out.write("\t\t\t<tr>\r\n");
              out.write("\t\t\t\t<td width=\"180\" valign=\"middle\">");
              if (_jspx_meth_html_005fimg_005f0(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
                return;
              out.write("</td>\r\n");
              out.write("\t\t\t\t<td valign=\"bottom\">\r\n");
              out.write("\t\t\t\t<fieldset style=\"padding: 5px 5px 5px 5px; height: 144px;\">\r\n");
              out.write("\t\t\t\t<legend>请选择栏目类型：</legend>\r\n");
              out.write("\t\t\t\t<div style=\"margin-top: 8px;\">\r\n");
              out.write("\t\t\t\t\t\t\t<div class=\"Grid\">\r\n");
              out.write("\t\t\t\t\t\t\t\t<input type=\"radio\"  id=\"folder\" name=\"advanced\" value=\"folder\" onpropertychange=\"showDescription('文件夹','folder')\" /> \r\n");
              out.write("\t\t\t\t\t\t\t\t<label for=\"folder\">文件夹</label>\r\n");
              out.write("\t\t\t\t\t\t\t</div>\r\n");
              out.write("\t\t\t\t\t");
              //  logic:iterate
              org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_005fiterate_005f0 = (org.apache.struts.taglib.logic.IterateTag) _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid.get(org.apache.struts.taglib.logic.IterateTag.class);
              _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
              _jspx_th_logic_005fiterate_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005fput_005f2);
              // /site/channel/selectChannelType.jsp(57,5) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_logic_005fiterate_005f0.setId("type");
              // /site/channel/selectChannelType.jsp(57,5) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_logic_005fiterate_005f0.setName("channelTypes");
              int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
              if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                java.lang.Object type = null;
                if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.pushBody();
                  _jspx_th_logic_005fiterate_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
                  _jspx_th_logic_005fiterate_005f0.doInitBody();
                }
                type = (java.lang.Object) _jspx_page_context.findAttribute("type");
                do {
                  out.write("\r\n");
                  out.write("\t\t\t\t\t\t\t<div class=\"Grid\">\r\n");
                  out.write("\t\t\t\t\t\t\t\t<input type=\"radio\" onpropertychange=\"showDescription('");
                  if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
                    return;
                  out.write('\'');
                  out.write(',');
                  out.write('\'');
                  if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
                    return;
                  out.write("')\" id=\"");
                  if (_jspx_meth_bean_005fwrite_005f2(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
                    return;
                  out.write("\" name=\"advanced\" value=\"");
                  if (_jspx_meth_bean_005fwrite_005f3(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
                    return;
                  out.write("\" /> \r\n");
                  out.write("\t\t\t\t\t\t\t\t<label for=\"");
                  if (_jspx_meth_bean_005fwrite_005f4(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
                    return;
                  out.write('"');
                  out.write('>');
                  if (_jspx_meth_bean_005fwrite_005f5(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
                    return;
                  out.write("</label>\r\n");
                  out.write("\t\t\t\t\t\t\t</div>\r\n");
                  out.write("\t\t\t\t\t");
                  int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
                  type = (java.lang.Object) _jspx_page_context.findAttribute("type");
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
                if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.popBody();
                }
              }
              if (_jspx_th_logic_005fiterate_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
                return;
              }
              _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
              out.write("\r\n");
              out.write("\t\t\t\t</div>\r\n");
              out.write("\t\t\t\t</fieldset>\r\n");
              out.write("\t\t\t\t<fieldset style=\"padding: 5px 5px 5px 5px; height: 180px;\">\r\n");
              out.write("\t\t\t\t<legend>说明</legend>\r\n");
              out.write("\t\t\t\t<div id=\"description\"></div>\r\n");
              out.write("\t\t\t\t</fieldset>\r\n");
              out.write("\t\t\t\t</td>\r\n");
              out.write("\t\t\t</tr>\r\n");
              out.write("\t\t</table>\r\n");
              out.write("\t\t<div class=\"operation\">\r\n");
              out.write("\t\t<button id=\"btnOk\" type=\"button\" onclick=\"ok()\">下一步</button>\r\n");
              out.write("\t\t<button id=\"btnCancel\" type=\"button\" onclick=\"window.close()\">取消</button>\r\n");
              out.write("\t\t</div>\r\n");
              out.write("\t");
              int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
            if (_jspx_eval_tiles_005fput_005f2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.popBody();
            }
          }
          if (_jspx_th_tiles_005fput_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname.reuse(_jspx_th_tiles_005fput_005f2);
            return;
          }
          _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname.reuse(_jspx_th_tiles_005fput_005f2);
          out.write('\r');
          out.write('\n');
          int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_tiles_005finsert_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fdefinition.reuse(_jspx_th_tiles_005finsert_005f0);
        return;
      }
      _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fdefinition.reuse(_jspx_th_tiles_005finsert_005f0);
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

  private boolean _jspx_meth_tiles_005fput_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  tiles:put
    org.apache.struts.taglib.tiles.PutTag _jspx_th_tiles_005fput_005f0 = (org.apache.struts.taglib.tiles.PutTag) _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname.get(org.apache.struts.taglib.tiles.PutTag.class);
    _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
    _jspx_th_tiles_005fput_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005finsert_005f0);
    // /site/channel/selectChannelType.jsp(8,1) name = name type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_tiles_005fput_005f0.setName("title");
    int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
    if (_jspx_eval_tiles_005fput_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_tiles_005fput_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_tiles_005fput_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_tiles_005fput_005f0.doInitBody();
      }
      do {
        out.write("选择栏目类型");
        int evalDoAfterBody = _jspx_th_tiles_005fput_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_tiles_005fput_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_tiles_005fput_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname.reuse(_jspx_th_tiles_005fput_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname.reuse(_jspx_th_tiles_005fput_005f0);
    return false;
  }

  private boolean _jspx_meth_tiles_005fput_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  tiles:put
    org.apache.struts.taglib.tiles.PutTag _jspx_th_tiles_005fput_005f1 = (org.apache.struts.taglib.tiles.PutTag) _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname.get(org.apache.struts.taglib.tiles.PutTag.class);
    _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
    _jspx_th_tiles_005fput_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005finsert_005f0);
    // /site/channel/selectChannelType.jsp(9,3) name = name type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_tiles_005fput_005f1.setName("javascript");
    int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
    if (_jspx_eval_tiles_005fput_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_tiles_005fput_005f1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_tiles_005fput_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_tiles_005fput_005f1.doInitBody();
      }
      do {
        out.write("\r\n");
        out.write("  \t\t<meta http-equiv=\"pragma\" content=\"no-cache\">\r\n");
        out.write("\t\t<link rel=\"stylesheet\" type=\"text/css\" href=\"");
        if (_jspx_meth_html_005frewrite_005f0(_jspx_th_tiles_005fput_005f1, _jspx_page_context))
          return true;
        out.write("\" />\r\n");
        out.write("\t\t<link rel=\"stylesheet\" type=\"text/css\" href=\"");
        if (_jspx_meth_html_005frewrite_005f1(_jspx_th_tiles_005fput_005f1, _jspx_page_context))
          return true;
        out.write("\" />\r\n");
        out.write("\t    <style type=\"text/css\">\r\n");
        out.write("\t    \tdiv.Grid {\r\n");
        out.write("\t\t\t\twidth: 100px;\r\n");
        out.write("\t\t\t\theight: 30px;\r\n");
        out.write("\t\t\t\tfloat: left;\r\n");
        out.write("\t\t\t\ttext-align: left;\r\n");
        out.write("\t\t\t\tpadding-left: 25px;\r\n");
        out.write("\t\t\t}\r\n");
        out.write("\t    </style>\r\n");
        out.write("\t    <script language=\"Javascript\" type=\"text/Javascript\" src=\"");
        if (_jspx_meth_html_005frewrite_005f2(_jspx_th_tiles_005fput_005f1, _jspx_page_context))
          return true;
        out.write("\"></script>\r\n");
        out.write("\t\t<script type=\"text/javascript\">\r\n");
        out.write("\t\t\twindow.onload = function() {\r\n");
        out.write("\t\t\t\tdocument.getElementById(\"index\").checked = true;\r\n");
        out.write("\t\t\t}\r\n");
        out.write("\t\t\tfunction ok() {\r\n");
        out.write("\t\t\t\tvar oChannelTypes = document.getElementsByName(\"advanced\");\r\n");
        out.write("\t\t\t\tvar advance = GetRadioValue(oChannelTypes);\r\n");
        out.write("\t\t\t\twindow.returnValue = advance;\r\n");
        out.write("\t\t\t\twindow.close();\r\n");
        out.write("\t\t\t}\r\n");
        out.write("\t\t\tfunction showDescription(displayName,postfix){\r\n");
        out.write("\t\t\t\tvar oDescription = document.getElementById(\"description\");\r\n");
        out.write("\t\t\t\tif(postfix!=\"folder\"){\r\n");
        out.write("\t\t\t\t\toDescription.innerHTML = displayName + \"类型栏目是后缀为\"+postfix+\"的页面\";\r\n");
        out.write("\t\t\t\t}else{\r\n");
        out.write("\t\t\t\t\toDescription.innerHTML = \"创建一个文件夹\";\r\n");
        out.write("\t\t\t\t}\r\n");
        out.write("\t\t\t}\r\n");
        out.write("\t\t</script>\r\n");
        out.write("  \t");
        int evalDoAfterBody = _jspx_th_tiles_005fput_005f1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_tiles_005fput_005f1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_tiles_005fput_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname.reuse(_jspx_th_tiles_005fput_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname.reuse(_jspx_th_tiles_005fput_005f1);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005fput_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f0 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f0.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005fput_005f1);
    // /site/channel/selectChannelType.jsp(11,47) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f0.setModule("/common");
    // /site/channel/selectChannelType.jsp(11,47) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f0.setPage("/style/list.css");
    int _jspx_eval_html_005frewrite_005f0 = _jspx_th_html_005frewrite_005f0.doStartTag();
    if (_jspx_th_html_005frewrite_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f0);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005fput_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f1 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f1.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005fput_005f1);
    // /site/channel/selectChannelType.jsp(12,47) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f1.setModule("/common");
    // /site/channel/selectChannelType.jsp(12,47) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f1.setPage("/style/dialog.jsp");
    int _jspx_eval_html_005frewrite_005f1 = _jspx_th_html_005frewrite_005f1.doStartTag();
    if (_jspx_th_html_005frewrite_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f1);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005fput_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f2 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f2.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005fput_005f1);
    // /site/channel/selectChannelType.jsp(22,63) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f2.setModule("/common");
    // /site/channel/selectChannelType.jsp(22,63) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f2.setPage("/script/common.js");
    int _jspx_eval_html_005frewrite_005f2 = _jspx_th_html_005frewrite_005f2.doStartTag();
    if (_jspx_th_html_005frewrite_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f2);
    return false;
  }

  private boolean _jspx_meth_html_005fimg_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:img
    org.apache.struts.taglib.html.ImgTag _jspx_th_html_005fimg_005f0 = (org.apache.struts.taglib.html.ImgTag) _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fstyle_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.ImgTag.class);
    _jspx_th_html_005fimg_005f0.setPageContext(_jspx_page_context);
    _jspx_th_html_005fimg_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005fput_005f2);
    // /site/channel/selectChannelType.jsp(46,36) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fimg_005f0.setPage("/images/paddingleft.jpg");
    // /site/channel/selectChannelType.jsp(46,36) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fimg_005f0.setModule("/common");
    // /site/channel/selectChannelType.jsp(46,36) name = style type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fimg_005f0.setStyle("border:2px inset");
    int _jspx_eval_html_005fimg_005f0 = _jspx_th_html_005fimg_005f0.doStartTag();
    if (_jspx_th_html_005fimg_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fstyle_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005fimg_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fstyle_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005fimg_005f0);
    return false;
  }

  private boolean _jspx_meth_bean_005fwrite_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f0 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fwrite_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fiterate_005f0);
    // /site/channel/selectChannelType.jsp(59,63) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fwrite_005f0.setName("type");
    // /site/channel/selectChannelType.jsp(59,63) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fwrite_005f0.setProperty("displayName");
    int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
    if (_jspx_th_bean_005fwrite_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
    return false;
  }

  private boolean _jspx_meth_bean_005fwrite_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f1 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fwrite_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fiterate_005f0);
    // /site/channel/selectChannelType.jsp(59,114) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fwrite_005f1.setName("type");
    // /site/channel/selectChannelType.jsp(59,114) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fwrite_005f1.setProperty("postfix");
    int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
    if (_jspx_th_bean_005fwrite_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
    return false;
  }

  private boolean _jspx_meth_bean_005fwrite_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f2 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_005fwrite_005f2.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fwrite_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fiterate_005f0);
    // /site/channel/selectChannelType.jsp(59,166) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fwrite_005f2.setName("type");
    // /site/channel/selectChannelType.jsp(59,166) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fwrite_005f2.setProperty("name");
    int _jspx_eval_bean_005fwrite_005f2 = _jspx_th_bean_005fwrite_005f2.doStartTag();
    if (_jspx_th_bean_005fwrite_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
    return false;
  }

  private boolean _jspx_meth_bean_005fwrite_005f3(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f3 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_005fwrite_005f3.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fwrite_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fiterate_005f0);
    // /site/channel/selectChannelType.jsp(59,232) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fwrite_005f3.setName("type");
    // /site/channel/selectChannelType.jsp(59,232) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fwrite_005f3.setProperty("name");
    int _jspx_eval_bean_005fwrite_005f3 = _jspx_th_bean_005fwrite_005f3.doStartTag();
    if (_jspx_th_bean_005fwrite_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f3);
    return false;
  }

  private boolean _jspx_meth_bean_005fwrite_005f4(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f4 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_005fwrite_005f4.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fwrite_005f4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fiterate_005f0);
    // /site/channel/selectChannelType.jsp(60,20) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fwrite_005f4.setName("type");
    // /site/channel/selectChannelType.jsp(60,20) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fwrite_005f4.setProperty("name");
    int _jspx_eval_bean_005fwrite_005f4 = _jspx_th_bean_005fwrite_005f4.doStartTag();
    if (_jspx_th_bean_005fwrite_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f4);
    return false;
  }

  private boolean _jspx_meth_bean_005fwrite_005f5(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f5 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_005fwrite_005f5.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fwrite_005f5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fiterate_005f0);
    // /site/channel/selectChannelType.jsp(60,63) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fwrite_005f5.setName("type");
    // /site/channel/selectChannelType.jsp(60,63) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fwrite_005f5.setProperty("displayName");
    int _jspx_eval_bean_005fwrite_005f5 = _jspx_th_bean_005fwrite_005f5.doStartTag();
    if (_jspx_th_bean_005fwrite_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f5);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f5);
    return false;
  }
}
