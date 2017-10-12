package org.apache.jsp.site.css;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class css_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(4);
    _jspx_dependants.add("/WEB-INF/struts-bean.tld");
    _jspx_dependants.add("/WEB-INF/struts-logic.tld");
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
    _jspx_dependants.add("/WEB-INF/struts-tiles.tld");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fdefinition;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fpage_005fmodule_005fborder_005falt_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fdefinition = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fpage_005fmodule_005fborder_005falt_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fdefinition.release();
    _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname.release();
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fpage_005fmodule_005fborder_005falt_005fnobody.release();
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

/** 样式选择器。针对指定页面编辑该页面上的样式。用户可以修改当前样式文件，也可以添加、删除样式文件
  *  输入：
  *     1. styleSheets：数组，页面上当前的样式列表
  *		2. templateID：当前网站模板ID
  *     3. selector: 当前选中的选择器，可以为空。如果提供，则高亮这个选择器。
  *	 输出：
  *     1. 选中的选择器。
  **/

      out.write('\r');
      out.write('\n');
      if (_jspx_meth_tiles_005finsert_005f0(_jspx_page_context))
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

  private boolean _jspx_meth_tiles_005finsert_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  tiles:insert
    org.apache.struts.taglib.tiles.InsertTag _jspx_th_tiles_005finsert_005f0 = (org.apache.struts.taglib.tiles.InsertTag) _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fdefinition.get(org.apache.struts.taglib.tiles.InsertTag.class);
    _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
    _jspx_th_tiles_005finsert_005f0.setParent(null);
    // /site/css/css.jsp(16,0) name = definition type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_tiles_005finsert_005f0.setDefinition("dialog_frame");
    int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
    if (_jspx_eval_tiles_005finsert_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write('\r');
        out.write('\n');
        out.write('	');
        if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
          return true;
        out.write('\r');
        out.write('\n');
        out.write('	');
        if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
          return true;
        out.write('\r');
        out.write('\n');
        out.write('	');
        if (_jspx_meth_tiles_005fput_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
          return true;
        out.write('\r');
        out.write('\n');
        int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_tiles_005finsert_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fdefinition.reuse(_jspx_th_tiles_005finsert_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fdefinition.reuse(_jspx_th_tiles_005finsert_005f0);
    return false;
  }

  private boolean _jspx_meth_tiles_005fput_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  tiles:put
    org.apache.struts.taglib.tiles.PutTag _jspx_th_tiles_005fput_005f0 = (org.apache.struts.taglib.tiles.PutTag) _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname.get(org.apache.struts.taglib.tiles.PutTag.class);
    _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
    _jspx_th_tiles_005fput_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005finsert_005f0);
    // /site/css/css.jsp(17,1) name = name type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_tiles_005fput_005f0.setName("title");
    int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
    if (_jspx_eval_tiles_005fput_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_tiles_005fput_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_tiles_005fput_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_tiles_005fput_005f0.doInitBody();
      }
      do {
        out.write("选择样式");
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
    // /site/css/css.jsp(18,1) name = name type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
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
        out.write("\t\t<script language=\"Javascript\" type=\"text/Javascript\" src=\"style.js.jsp\"></script>\r\n");
        out.write("\t\t<script language=\"JavaScript\" type=\"text/javascript\">\r\n");
        out.write("\t\t\tvar hasChanged = false;\r\n");
        out.write("\t\t\tvar fileIndex = 0;\r\n");
        out.write("\t\t\tfunction doSelected() {\r\n");
        out.write("\t\t\t\tvar option = selRules.options[selRules.selectedIndex]; \r\n");
        out.write("\t\t\t\tvar cssText = option.css ? option.css.toString() : option.originalCss.toString();\r\n");
        out.write("\t\t\t\toCode.value = cssText;\r\n");
        out.write("\t\t\t\toCode.disabled = false;\r\n");
        out.write("\t\t\t\toPrev.style.cssText = cssText;\r\n");
        out.write("\t\t\t}\r\n");
        out.write("\t\t\tfunction _HasChanged() {\r\n");
        out.write("\t\t\t\tif (hasChanged)\r\n");
        out.write("\t\t\t\t\treturn true;\r\n");
        out.write("\t\t\t\tvar options = selRules.options;\r\n");
        out.write("\t\t\t\tfor (var i=0; i<options.length; i++) {\r\n");
        out.write("\t\t\t\t\tif (options[i].css && !_CompareStyle(options[i].css, options[i].originalCss))\r\n");
        out.write("\t\t\t\t\t\treturn true;\r\n");
        out.write("\t\t\t\t}\r\n");
        out.write("\t\t\t\treturn false;\r\n");
        out.write("\t\t\t}\r\n");
        out.write("\t\t\tfunction _CompareStyle($src, $dest) {\r\n");
        out.write("\t\t\t\tif (!($src instanceof Style) || !($dest instanceof Style))\r\n");
        out.write("\t\t\t\t\treturn false;\r\n");
        out.write("\t\t\t\tfor (var e in $src) {\r\n");
        out.write("\t\t\t\t\tif (typeof($src[e]) != \"function\" && $src[e] != $dest[e])\r\n");
        out.write("\t\t\t\t\t\treturn false;\r\n");
        out.write("\t\t\t\t}\r\n");
        out.write("\t\t\t\treturn true;\r\n");
        out.write("\t\t\t}\r\n");
        out.write("\t\t\tfunction doSave() {\r\n");
        out.write("\t\t\t\tif(document.getElementById(\"cssFiles\").options.length == 0){\r\n");
        out.write("\t\t\t\t\talert(\"保存失败，请在页面属性中添加脚本文件\");\r\n");
        out.write("\t\t\t\t\treturn false;\r\n");
        out.write("\t\t\t\t}else{\r\n");
        out.write("\t\t\t\t\tvar cssPath = document.getElementById(\"cssFiles\").options[fileIndex].text; \r\n");
        out.write("\t\t\t\t\tvar options = document.getElementById(\"selRules\").options;\r\n");
        out.write("\t\t\t\t\tvar cssContent = [];\r\n");
        out.write("\t\t\t\t\tfor(var i=0; i<options.length; i++)\r\n");
        out.write("\t\t\t\t\t\tcssContent.push(options[i].value + \" {\" + (options[i].css ? options[i].css.toString() : options[i].originalCss.toString()) + \"}\");\r\n");
        out.write("\t\t\t\t\tvar params = [];\r\n");
        out.write("\t\t\t\t\tparams.push(\"cssPath=\" + encodeURIComponent(cssPath));\r\n");
        out.write("\t\t\t\t\tparams.push(\"timestamp=\" + new Date().getTime());\r\n");
        out.write("\t\t\t\t\tvar url = '");
        if (_jspx_meth_html_005frewrite_005f0(_jspx_th_tiles_005fput_005f1, _jspx_page_context))
          return true;
        out.write("?' + params.join(\"&\");\r\n");
        out.write("\t\t\t\t\tvar request = getXMLHttpRequest();\r\n");
        out.write("\t\t\t\t\trequest.open(\"post\", url, false);\r\n");
        out.write("\t\t\t\t    //request.setRequestHeader(\"Content-Type\", \"application/x-www-form-urlencoded; charset=utf-8\");\r\n");
        out.write("\t\t\t\t\trequest.send(cssContent.join(\"\\n\"));\r\n");
        out.write("\t\t\t\t    if (request.status != 200 || !request.responseText) {\r\n");
        out.write("\t\t\t\t    \talert(\"保存失败\");\r\n");
        out.write("\t\t\t\t    \treturn;\r\n");
        out.write("\t\t\t\t    }\r\n");
        out.write("\t\t\t       \talert(\"保存成功！\");\r\n");
        out.write("\t\t\t\t\tfor (var i=0; i<options.length; i++) {\r\n");
        out.write("\t\t\t\t\t\tif (options[i].css) {\r\n");
        out.write("\t\t\t\t\t\t\toptions[i].originalCss = options[i].css;\r\n");
        out.write("\t\t\t\t\t\t\toptions[i].css = null;\r\n");
        out.write("\t\t\t\t\t\t}\r\n");
        out.write("\t\t\t\t\t}\r\n");
        out.write("\t\t\t\t\thasChanged = false;\r\n");
        out.write("\t\t\t\t\tvar sheet = window.dialogArguments.styleSheets[fileIndex];\r\n");
        out.write("\t\t\t       \tsheet.href = cssPath + \"?timestamp=\" + new Date().getTime();\r\n");
        out.write("\t\t\t       \treturn true;\r\n");
        out.write("\t\t\t\t}\r\n");
        out.write("\t\t\t}\r\n");
        out.write("\t\t\tfunction doOk() {\r\n");
        out.write("\t\t\t\tvar selectItems = getSelectedItems(document.getElementById(\"selRules\"));\r\n");
        out.write("\t\t\t\tif (!selectItems) {\r\n");
        out.write("\t\t\t\t\talert(\"请选择样式\");\r\n");
        out.write("\t\t\t\t\treturn;\r\n");
        out.write("\t\t\t\t}\r\n");
        out.write("\t\t\t\tif (_HasChanged()) {\r\n");
        out.write("\t\t\t\t\tif(confirm(\"Css文件已改变，是否保存\"))\r\n");
        out.write("\t\t\t\t\t\tvar savesucess = doSave();\r\n");
        out.write("\t\t\t\t\tif(!savesucess){\r\n");
        out.write("\t\t\t\t\t\twindow.close();\r\n");
        out.write("\t\t\t\t\t\treturn;\r\n");
        out.write("\t\t\t\t\t}\r\n");
        out.write("\t\t\t\t}\r\n");
        out.write("\t\t\t\tvar ret = [];\r\n");
        out.write("\t\t\t\tvar style;\r\n");
        out.write("\t\t\t\tfor (var i=0; i<selectItems.length; i++) {\r\n");
        out.write("\t\t\t\t\tstyle = selectItems[i].value;\r\n");
        out.write("\t\t\t\t\tvar pos=style.lastIndexOf(\".\");\r\n");
        out.write("\t\t\t\t\tstyle=style.substring(pos+1, style.length);\r\n");
        out.write("\t\t\t\t\tvar reg = new RegExp(\"\\\\W\");\r\n");
        out.write("\t\t\t\t\tpos = style.search(reg);\r\n");
        out.write("\t\t\t\t\tif(pos > 0)\r\n");
        out.write("\t\t\t\t\tstyle=style.substring(0, pos);\r\n");
        out.write("\t\t\t\t\tret.push(style);\r\n");
        out.write("\t\t\t\t}\r\n");
        out.write("\t\t\t\twindow.returnValue = ret.join(\" \");\r\n");
        out.write("\t\t\t\twindow.close();\r\n");
        out.write("\t\t\t}\r\n");
        out.write("\t\t\tfunction doClean() {\r\n");
        out.write("\t\t\t\tif (_HasChanged()) {\r\n");
        out.write("\t\t\t\t\tif(confirm(\"Css文件已改变，是否保存\"))\r\n");
        out.write("\t\t\t\t\t\tdoSave();\r\n");
        out.write("\t\t\t\t}\r\n");
        out.write("\t\t\t\twindow.returnValue = \":none\";\r\n");
        out.write("\t\t\t\twindow.close();\r\n");
        out.write("\t\t\t}\r\n");
        out.write("\t\t\tfunction doCancel() {\r\n");
        out.write("\t\t\t\tif (_HasChanged()) {\r\n");
        out.write("\t\t\t\t\tif(confirm(\"Css文件已改变，是否保存\"))\r\n");
        out.write("\t\t\t\t\t\tdoSave();\r\n");
        out.write("\t\t\t\t}\r\n");
        out.write("\t\t\t\twindow.close();\r\n");
        out.write("\t\t\t}\r\n");
        out.write("\t\t\tfunction _ChangeFile($index) {\r\n");
        out.write("\t\t\t\tif (_HasChanged()) {\r\n");
        out.write("\t\t\t\t\tif(confirm(\"Css文件已改变，是否保存\"))\r\n");
        out.write("\t\t\t\t\t\tdoSave();\r\n");
        out.write("\t\t\t\t}\r\n");
        out.write("\t\t\t\thasChanged = false;\r\n");
        out.write("\t\t\t\tfileIndex = $index;\r\n");
        out.write("\t\t\t\toCode.value = \"\";\r\n");
        out.write("\t\t\t\toCode.disabled = true;\r\n");
        out.write("\t\t\t\toPrev.style.cssText = \"\";\r\n");
        out.write("\t\t\t\tselRules.options.length = 0;\r\n");
        out.write("\t\t\t\tvar sheet = window.dialogArguments.styleSheets[$index];\r\n");
        out.write("\t\t\t\tvar rules = sheet.rules;\r\n");
        out.write("\t\t\t\tvar style, option, Scripting;\r\n");
        out.write("\t\t\t\tfor(var i=0; i<rules.length; i++) {\r\n");
        out.write("\t\t\t\t\tstyle = new Style();\r\n");
        out.write("\t\t\t\t\tfor (var e in style) {\r\n");
        out.write("\t\t\t\t\t\tscripting = _HtmlCssToScriptingCss(e);\r\n");
        out.write("\t\t\t\t\t\tif (rules[i].style[scripting])\r\n");
        out.write("\t\t\t\t\t\t\tstyle[e] = rules[i].style[scripting];\r\n");
        out.write("\t\t\t\t\t}\r\n");
        out.write("\t\t\t\t\toption = document.createElement(\"option\");\r\n");
        out.write("\t\t\t\t\toption.value = option.text = rules[i].selectorText;\r\n");
        out.write("\t\t\t\t\toption.originalCss = style;\r\n");
        out.write("\t\t\t\t\tselRules.options.add(option);\r\n");
        out.write("\t\t\t\t}\r\n");
        out.write("\t\t\t}\r\n");
        out.write("\t\t\tfunction _HtmlCssToScriptingCss($str) {\r\n");
        out.write("\t\t\t\tif ($str.indexOf(\"-\") == -1)\r\n");
        out.write("\t\t\t\t\treturn $str;\r\n");
        out.write("\t\t\t\tvar arr = $str.split(\"-\");\r\n");
        out.write("\t\t\t\tfor (var i=1; i<arr.length; i++) {\r\n");
        out.write("\t\t\t\t\tarr[i] = arr[i].substr(0, 1).toUpperCase() + arr[i].substr(1);\r\n");
        out.write("\t\t\t\t}\r\n");
        out.write("\t\t\t\treturn arr.join(\"\");\r\n");
        out.write("\t\t\t}\r\n");
        out.write("\t\t    function doCreate($defaultSelector){\r\n");
        out.write("\t\t\t    var args;\r\n");
        out.write("\t\t\t    if ($defaultSelector)\r\n");
        out.write("\t\t\t    \targs = $defaultSelector;\r\n");
        out.write("\t\t        var selector = showModalDialog(\"selector.jsp\", args, \"dialogWidth:250px;dialogHeight:110px;help:no;scrollbars:yes;status:no\");\r\n");
        out.write("\t\t        if (selector) {\r\n");
        out.write("\t\t        \tselRules.value = null;\r\n");
        out.write("\t\t            var option = document.createElement(\"option\");\r\n");
        out.write("\t\t            option.text = option.value = selector;\r\n");
        out.write("\t\t            option.originalCss = new Style();\r\n");
        out.write("\t\t            option.selected = true;\r\n");
        out.write("\t\t            selRules.add(option);\r\n");
        out.write("\t\t            doSelected();\r\n");
        out.write("\t\t\t\t\thasChanged = true;\r\n");
        out.write("\t\t\t\t\tdoEdit();\r\n");
        out.write("\t\t        }\r\n");
        out.write("\t\t    }\r\n");
        out.write("\t\t\tfunction doEdit() {\r\n");
        out.write("\t\t\t\tif (selRules.selectedIndex == -1) {\r\n");
        out.write("\t\t\t\t\talert(\"请选择css\");\r\n");
        out.write("\t\t\t\t\treturn;\r\n");
        out.write("\t\t\t\t}\r\n");
        out.write("\t\t\t\tvar args = {};\r\n");
        out.write("\t\t\t\tvar currentPath=window.dialogArguments.path;\r\n");
        out.write("\t\t    \tvar template = currentPath.split(\"/\");\r\n");
        out.write("\t\t    \targs.templateId = template.length>=2 ? template[2] : null;\r\n");
        out.write("\t\t\t\t\r\n");
        out.write("\t\t\t\tvar option = selRules.options[selRules.selectedIndex];\r\n");
        out.write("\t\t\t\targs.css = option.css ? option.css : option.originalCss; \r\n");
        out.write("\t\t\t\t\r\n");
        out.write("\t\t\t\tvar ret = showModalDialog(\"ruleDlg.jsp\", args, \"dialogWidth:360px;dialogHeight:350px;help:no;scrollbars:yes;status:no\");\r\n");
        out.write("\t\t\t\tif (ret) {\r\n");
        out.write("\t\t\t\t\tvar dest = new Style();\r\n");
        out.write("\t\t\t\t\t_CloneStyle(ret, dest);\r\n");
        out.write("\t\t\t\t\toption.css = dest;\r\n");
        out.write("\t\t            doSelected();\r\n");
        out.write("\t\t\t\t}\r\n");
        out.write("\t\t\t}\r\n");
        out.write("\t\t\tfunction _CloneStyle($src, $dest) {\r\n");
        out.write("\t\t\t\tfor (var e in $src) {\r\n");
        out.write("\t\t\t\t\tif ($src[e])\r\n");
        out.write("\t\t\t\t\t\t$dest[e] = $src[e];\r\n");
        out.write("\t\t\t\t}\r\n");
        out.write("\t\t\t}\r\n");
        out.write("\t\t\tfunction doUpdate(){\r\n");
        out.write("\t\t\t\tvar option = selRules.options[selRules.selectedIndex];\r\n");
        out.write("\t\t\t\tvar code = document.getElementById(\"oCode\").value.toLowerCase();\r\n");
        out.write("\t\t\t\tcode = code.replace(/\\s+/gm, \"\");\r\n");
        out.write("\t\t\t\tvar style = new Style();\r\n");
        out.write("\t\t\t\tvar arr = code.split(\";\");\r\n");
        out.write("\t\t\t\tvar pos, selectorText, value;\r\n");
        out.write("\t\t\t\tfor (var i=0; i<arr.length; i++) {\r\n");
        out.write("\t\t\t\t\tpos = arr[i].indexOf(\":\");\r\n");
        out.write("\t\t\t\t\tif (pos > 0 && pos < arr[i].length - 1) {\r\n");
        out.write("\t\t\t\t\t\tstyleName = arr[i].substr(0, pos);\r\n");
        out.write("\t\t\t\t\t\tstyleValue= arr[i].substr(pos + 1);\r\n");
        out.write("\t\t\t\t\t\tif (style[styleName] != null)\r\n");
        out.write("\t\t\t\t\t\t\tstyle[styleName] = styleValue;\r\n");
        out.write("\t\t\t\t\t}\r\n");
        out.write("\t\t\t\t}\r\n");
        out.write("\t\t\t\toption.css = style;\r\n");
        out.write("\t\t        oPrev.style.cssText = style.toString();\r\n");
        out.write("\t\t\t}\r\n");
        out.write("\t\t\tfunction doDisplay() {\r\n");
        out.write("\t\t\t\tif (event.ctrlKey)\r\n");
        out.write("\t\t\t        oPrev.style.cssText = document.getElementById(\"oCode\").value;\r\n");
        out.write("\t\t\t}\r\n");
        out.write("\t\t\tfunction doDelete() {\r\n");
        out.write("\t\t\t\tif (selRules.selectedIndex == -1) {\r\n");
        out.write("\t\t\t\t\talert('请选择css');\r\n");
        out.write("\t\t\t\t\treturn;\r\n");
        out.write("\t\t\t\t}\r\n");
        out.write("\t\t\t\tselRules.remove(selRules.selectedIndex);\r\n");
        out.write("\t\t\t\toCode.value = oPrev.style.cssText = \"\";\r\n");
        out.write("\t\t\t\thasChanged = true;\r\n");
        out.write("\t\t\t}\r\n");
        out.write("\t\t\tfunction doInput($value) {\r\n");
        out.write("\t\t\t\tif ($value) {\r\n");
        out.write("\t\t\t\t\tvar option = findSelectItem(selRules, $value);\r\n");
        out.write("\t\t\t\t\tif (!option) {\r\n");
        out.write("\t\t\t\t\t\tif(confirm(\"该css类未找到，是否创建？\"))\r\n");
        out.write("\t\t\t\t\t\t\tdoCreate($value);\r\n");
        out.write("\t\t\t\t\t\treturn;\r\n");
        out.write("\t\t\t\t\t}\r\n");
        out.write("\t\t\t\t\tselRules.value = $value;\r\n");
        out.write("\t\t\t\t\tdoSelected();\r\n");
        out.write("\t\t\t\t}\r\n");
        out.write("\t\t\t}\r\n");
        out.write("\t\t\twindow.onload = function() {\r\n");
        out.write("\t\t\t\tvar styleSheets = window.dialogArguments.styleSheets;\r\n");
        out.write("\t\t\t\tif(styleSheets) {\r\n");
        out.write("\t\t\t\t\tvar href, pos;\r\n");
        out.write("\t\t\t\t\tfor(var i=0; i<styleSheets.length; i++) {\r\n");
        out.write("\t\t\t\t\t\thref = styleSheets[i].href;\r\n");
        out.write("\t\t\t\t\t\tif (href) {\r\n");
        out.write("\t\t\t\t\t\t\tpos = href.indexOf(\"?\");\r\n");
        out.write("\t\t\t\t\t\t\tif (pos > 0)\r\n");
        out.write("\t\t\t\t\t\t\t\thref = href.substr(0, pos);\r\n");
        out.write("\t\t\t\t\t\t\tcssFiles.options[cssFiles.options.length] = new Option(href, i);\r\n");
        out.write("\t\t\t\t\t\t}\r\n");
        out.write("\t\t\t\t\t}\r\n");
        out.write("\t\t\t\t}\r\n");
        out.write("\t\t\t\tif (cssFiles.options.length > 0){\r\n");
        out.write("\t\t\t\t\t_ChangeFile(cssFiles.options[0].value);\r\n");
        out.write("\t\t\t\t}else{\r\n");
        out.write("\t\t\t\t\t\r\n");
        out.write("\t\t\t\t}\r\n");
        out.write("\t\t\t}\r\n");
        out.write("      \t</script>\r\n");
        out.write("\t");
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
    com.fulong.ide.RewriteTag _jspx_th_html_005frewrite_005f0 = (com.fulong.ide.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(com.fulong.ide.RewriteTag.class);
    _jspx_th_html_005frewrite_005f0.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005fput_005f1);
    // /site/css/css.jsp(62,16) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f0.setModule("/site");
    // /site/css/css.jsp(62,16) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f0.setPage("/css/saveCSSDlg.do");
    int _jspx_eval_html_005frewrite_005f0 = _jspx_th_html_005frewrite_005f0.doStartTag();
    if (_jspx_th_html_005frewrite_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f0);
    return false;
  }

  private boolean _jspx_meth_tiles_005fput_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  tiles:put
    org.apache.struts.taglib.tiles.PutTag _jspx_th_tiles_005fput_005f2 = (org.apache.struts.taglib.tiles.PutTag) _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname.get(org.apache.struts.taglib.tiles.PutTag.class);
    _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
    _jspx_th_tiles_005fput_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005finsert_005f0);
    // /site/css/css.jsp(274,1) name = name type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
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
        out.write("\t\t<div id=\"DLGToolbar\"><span onclick=\"doCreate()\">");
        if (_jspx_meth_html_005fimg_005f0(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
          return true;
        out.write("<span>添加</span></span> <span onclick=\"doEdit()\">");
        if (_jspx_meth_html_005fimg_005f1(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
          return true;
        out.write("<span>修改</span></span> <span onclick=\"doSave()\">");
        if (_jspx_meth_html_005fimg_005f2(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
          return true;
        out.write("<span>保存</span></span> <span onclick=\"doDelete()\">");
        if (_jspx_meth_html_005fimg_005f3(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
          return true;
        out.write("<span>删除</span></span> <span class=\"seperator\"></span></div>\r\n");
        out.write("\t\t<table width=\"100%\" cellspacing=\"3\" cellpadding=\"1\" border=\"0\">\r\n");
        out.write("\t\t\t<tr>\r\n");
        out.write("\t\t\t\t<td width=\"160px\">输入名称或从列表中选择</td>\r\n");
        out.write("\t\t\t\t<td align=\"left\">显示样式来源</td>\r\n");
        out.write("\t\t\t</tr>\r\n");
        out.write("\t\t\t<tr>\r\n");
        out.write("\t\t\t\t<td><input type=\"text\" id=\"keywords\" name=\"keywords\" onchange=\"doInput(this.value)\" size=\"12\" style=\"width: 160px\" /></td>\r\n");
        out.write("\t\t\t\t<td align=\"left\"><select id=\"cssFiles\" style=\"width: 280px;\" onchange=\"_ChangeFile(this.value)\"></select></td>\r\n");
        out.write("\t\t\t</tr>\r\n");
        out.write("\t\t\t<tr>\r\n");
        out.write("\t\t\t\t<td rowspan=\"3\">\r\n");
        out.write("\t\t\t\t\t<select multiple=\"multiple\" size=\"20\" id=\"selRules\" style=\"width: 160px\" onchange=\"doSelected()\" />\r\n");
        out.write("\t\t\t\t</td>\r\n");
        out.write("\t\t\t\t<td>\r\n");
        out.write("\t\t\t\t\t<div style=\"color:#ff0000;\">注意：空的样式保存后会被自动清除</div>\r\n");
        out.write("\t\t\t\t\t<textarea id=\"oCode\" style=\"width: 280px; height: 133px;\" onchange=\"doUpdate()\" disabled=\"true\" onkeydown=\"doDisplay()\" title=\"按ctrl健预览\"></textarea>\r\n");
        out.write("\t\t\t\t</td>\r\n");
        out.write("\t\t\t</tr>\r\n");
        out.write("\t\t\t<tr>\r\n");
        out.write("\t\t\t\t<td>预览</td>\r\n");
        out.write("\t\t\t</tr>\r\n");
        out.write("\t\t\t<tr>\r\n");
        out.write("\t\t\t\t<td>\r\n");
        out.write("\t\t\t\t<div style='color: #c0c0c0; background-color: white; border: 2px inset; height: 150px; width: 280px; padding: 2px 2px 2px 2px; overflow: hidden'>Coolink协同平台不是一个完成单一功能的业务系统。\r\n");
        out.write("\t\t\t\t<div id=\"oPrev\">每个实施机构能够根据应用领域的工作方式建立符合业务流程的协同工作全面解决方案。</div>\r\n");
        out.write("\t\t\t\t为此，Coolink协同工作支撑平台必须是高度可定制、可扩展、可重用的，并提供一个可描述的框架，支持实施人员能够在平台上重建甚至优化公司的协同流程。</div>\r\n");
        out.write("\t\t\t\t</td>\r\n");
        out.write("\t\t\t</tr>\r\n");
        out.write("\t\t</table>\r\n");
        out.write("\t\t</td>\r\n");
        out.write("\t\t</tr>\r\n");
        out.write("\t\t</table>\r\n");
        out.write("\t\t<div class=\"operation\"><!-- button type=\"button\" onclick=\"doSelectCSS()\" style=\"margin-right:130px\">管理页面样式文件...</button-->\r\n");
        out.write("\t\t<button type=\"button\" onclick=\"doOk()\" title=\"使用选中样式\">确定</button>\r\n");
        out.write("\t\t<button type=\"button\" onclick=\"doClean()\" title=\"取消已使用的样式\">清除</button>\r\n");
        out.write("\t\t<button type=\"button\" onclick=\"doCancel()\" title=\"关闭对话框\">取消</button>\r\n");
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
      return true;
    }
    _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname.reuse(_jspx_th_tiles_005fput_005f2);
    return false;
  }

  private boolean _jspx_meth_html_005fimg_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:img
    org.apache.struts.taglib.html.ImgTag _jspx_th_html_005fimg_005f0 = (org.apache.struts.taglib.html.ImgTag) _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fpage_005fmodule_005fborder_005falt_005fnobody.get(org.apache.struts.taglib.html.ImgTag.class);
    _jspx_th_html_005fimg_005f0.setPageContext(_jspx_page_context);
    _jspx_th_html_005fimg_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005fput_005f2);
    // /site/css/css.jsp(275,50) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fimg_005f0.setPage("/images/css16.gif");
    // /site/css/css.jsp(275,50) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fimg_005f0.setModule("/common");
    // /site/css/css.jsp(275,50) name = alt type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fimg_005f0.setAlt("添加样式");
    // /site/css/css.jsp(275,50) name = border type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fimg_005f0.setBorder("0");
    int _jspx_eval_html_005fimg_005f0 = _jspx_th_html_005fimg_005f0.doStartTag();
    if (_jspx_th_html_005fimg_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fpage_005fmodule_005fborder_005falt_005fnobody.reuse(_jspx_th_html_005fimg_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fpage_005fmodule_005fborder_005falt_005fnobody.reuse(_jspx_th_html_005fimg_005f0);
    return false;
  }

  private boolean _jspx_meth_html_005fimg_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:img
    org.apache.struts.taglib.html.ImgTag _jspx_th_html_005fimg_005f1 = (org.apache.struts.taglib.html.ImgTag) _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fpage_005fmodule_005fborder_005falt_005fnobody.get(org.apache.struts.taglib.html.ImgTag.class);
    _jspx_th_html_005fimg_005f1.setPageContext(_jspx_page_context);
    _jspx_th_html_005fimg_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005fput_005f2);
    // /site/css/css.jsp(275,174) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fimg_005f1.setPage("/images/editcss16.gif");
    // /site/css/css.jsp(275,174) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fimg_005f1.setModule("/common");
    // /site/css/css.jsp(275,174) name = alt type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fimg_005f1.setAlt("修改样式");
    // /site/css/css.jsp(275,174) name = border type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fimg_005f1.setBorder("0");
    int _jspx_eval_html_005fimg_005f1 = _jspx_th_html_005fimg_005f1.doStartTag();
    if (_jspx_th_html_005fimg_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fpage_005fmodule_005fborder_005falt_005fnobody.reuse(_jspx_th_html_005fimg_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fpage_005fmodule_005fborder_005falt_005fnobody.reuse(_jspx_th_html_005fimg_005f1);
    return false;
  }

  private boolean _jspx_meth_html_005fimg_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:img
    org.apache.struts.taglib.html.ImgTag _jspx_th_html_005fimg_005f2 = (org.apache.struts.taglib.html.ImgTag) _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fpage_005fmodule_005fborder_005falt_005fnobody.get(org.apache.struts.taglib.html.ImgTag.class);
    _jspx_th_html_005fimg_005f2.setPageContext(_jspx_page_context);
    _jspx_th_html_005fimg_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005fput_005f2);
    // /site/css/css.jsp(275,302) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fimg_005f2.setPage("/images/save2web.gif");
    // /site/css/css.jsp(275,302) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fimg_005f2.setModule("/common");
    // /site/css/css.jsp(275,302) name = alt type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fimg_005f2.setAlt("保存样式");
    // /site/css/css.jsp(275,302) name = border type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fimg_005f2.setBorder("0");
    int _jspx_eval_html_005fimg_005f2 = _jspx_th_html_005fimg_005f2.doStartTag();
    if (_jspx_th_html_005fimg_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fpage_005fmodule_005fborder_005falt_005fnobody.reuse(_jspx_th_html_005fimg_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fpage_005fmodule_005fborder_005falt_005fnobody.reuse(_jspx_th_html_005fimg_005f2);
    return false;
  }

  private boolean _jspx_meth_html_005fimg_005f3(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:img
    org.apache.struts.taglib.html.ImgTag _jspx_th_html_005fimg_005f3 = (org.apache.struts.taglib.html.ImgTag) _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fpage_005fmodule_005fborder_005falt_005fnobody.get(org.apache.struts.taglib.html.ImgTag.class);
    _jspx_th_html_005fimg_005f3.setPageContext(_jspx_page_context);
    _jspx_th_html_005fimg_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005fput_005f2);
    // /site/css/css.jsp(275,431) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fimg_005f3.setPage("/images/delete.png");
    // /site/css/css.jsp(275,431) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fimg_005f3.setModule("/common");
    // /site/css/css.jsp(275,431) name = alt type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fimg_005f3.setAlt("删除样式");
    // /site/css/css.jsp(275,431) name = border type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fimg_005f3.setBorder("0");
    int _jspx_eval_html_005fimg_005f3 = _jspx_th_html_005fimg_005f3.doStartTag();
    if (_jspx_th_html_005fimg_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fpage_005fmodule_005fborder_005falt_005fnobody.reuse(_jspx_th_html_005fimg_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fpage_005fmodule_005fborder_005falt_005fnobody.reuse(_jspx_th_html_005fimg_005f3);
    return false;
  }
}
