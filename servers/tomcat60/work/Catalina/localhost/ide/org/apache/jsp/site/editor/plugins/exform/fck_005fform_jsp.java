package org.apache.jsp.site.editor.plugins.exform;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class fck_005fform_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
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
      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\" >\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n");
      out.write("\t<head>\n");
      out.write("\t\t<title>新建FORM</title>\n");
      out.write("\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n");
      out.write("\t\t<meta content=\"noindex, nofollow\" name=\"robots\" />\n");
      out.write("\t\t<script src=\"");
      if (_jspx_meth_html_005frewrite_005f0(_jspx_page_context))
        return;
      out.write("\" type=\"text/javascript\"></script>\n");
      out.write("\t\t<script src=\"");
      if (_jspx_meth_html_005frewrite_005f1(_jspx_page_context))
        return;
      out.write("\" type=\"text/javascript\"></script>\n");
      out.write("\t\t<script type=\"text/javascript\"><!--\n");
      out.write("\t\t\tvar oEditor = window.parent.InnerDialogLoaded() ;\n");
      out.write("\t\t\tvar oDOM = oEditor.FCK.EditorDocument ;\n");
      out.write("\t\t\tvar oActiveEl = oEditor.FCKSelection.MoveToAncestorNode( 'FORM' ) ;\n");
      out.write("\t\t\twindow.onload = function() {\n");
      out.write("\t\t\t\t// First of all, translate the dialog box texts\n");
      out.write("\t\t\t\toEditor.FCKLanguageManager.TranslatePage(document) ;\n");
      out.write("\t\t\t\t\n");
      out.write("\t\t\t\tif ( oActiveEl ) {\n");
      out.write("\t\t\t\t\tdocument.title = \"修改FORM(\" + oActiveEl.name + \")\";\n");
      out.write("\t\t\t\t\tGetE('txtName').value\t= oActiveEl.name ;\n");
      out.write("\t\t\t\t\tGetE('txtMethod').value\t= oActiveEl.method\n");
      out.write("\t\t\t\t\ttry{\n");
      out.write("\t\t\t\t\t\tGetE('txtDefinitionID').value\t= oActiveEl.attributes(\"definition\").value ;\n");
      out.write("\t\t\t\t\t\tGetE('txtDefinitionName').value\t= oActiveEl.attributes(\"definitionname\").value ;\n");
      out.write("\t\t\t\t\t}catch(e){}\n");
      out.write("\t\t\t\t\ttry{\n");
      out.write("\t\t\t\t\t\tsetRadioValue(nodeType, oActiveEl.attributes(\"node\").value );\n");
      out.write("\t\t\t\t\t}catch(e){}\n");
      out.write("\n");
      out.write("\t\t\t\t\tif (oActiveEl.target)\n");
      out.write("\t\t\t\t\t\tGetE('txtTarget').value = oActiveEl.target ;\n");
      out.write("\n");
      out.write("\t\t\t\t\t//GetE('txtDefinitionName').value\t=  oActiveEl.attributes(\"title\").value ;\n");
      out.write("\t\t\t        GetE('isMultipart').checked = (oActiveEl.enctype=='multipart/form-data');\n");
      out.write("\t\t\t\t} else {\n");
      out.write("\t\t\t        oActiveEl = null ;\n");
      out.write("\t\t\t\t\tGetE('txtName').value\t= 'fm'+ new Date().getMilliseconds() ;\n");
      out.write("\t\t\t\t\tGetE('txtMethod').value\t= 'post' ;\n");
      out.write("\t\t        }\n");
      out.write("\t\t\t\twindow.parent.SetOkButton( true ) ;\n");
      out.write("\t\t\t\twindow.parent.SetAutoSize( true ) ;\n");
      out.write("\t\t\t}\n");
      out.write("\t\t\tfunction setRadioValue($object, value) {\n");
      out.write("\t\t\t\tfor (var i=0; i<$object.length; i++) {\n");
      out.write("\t\t\t\t\tif($object[i].value == value)\n");
      out.write("\t\t\t\t\t\t$object[i].checked = true;\n");
      out.write("\t\t\t\t}\n");
      out.write("\t\t\t}\n");
      out.write("\t\t\tfunction GetRadioObject($object) {\n");
      out.write("\t\t\t\tif ($object.length == null) {\n");
      out.write("\t\t\t\t\tif ($object.checked == true)\n");
      out.write("\t\t\t\t\t    return $object;\n");
      out.write("\t\t\t\t\telse\n");
      out.write("\t\t\t\t\t\treturn null;\n");
      out.write("\t\t\t    } else {\n");
      out.write("\t\t\t\t    for(var i=0; i<$object.length; i++) {\n");
      out.write("\t\t\t\t\t    if($object[i].checked == true)\n");
      out.write("\t\t\t\t\t    \treturn $object[i];\n");
      out.write("\t\t\t\t    }\n");
      out.write("\t\t\t\t    return null;\n");
      out.write("\t\t\t    }\n");
      out.write("\t\t\t}\n");
      out.write("\t\t\tfunction selectNodeDefinition() {\n");
      out.write("\t\t\t    var definition = CMSDialog.NodeDefinitionSelector('no-properties-scheme', null, true, false, false);\n");
      out.write("\t\t\t    if(definition != null) {\n");
      out.write("\t\t\t    \tGetE('txtDefinitionName').value=definition.name;\n");
      out.write("\t\t\t    \tGetE('txtDefinitionID').value=definition.ID;\n");
      out.write("\t\t\t    }\n");
      out.write("\t\t\t}\n");
      out.write("\t\t\tfunction Ok() {\n");
      out.write("\t\t\t\tif(GetE('txtDefinitionID').value==null||GetE('txtDefinitionID').value==\"\"){\n");
      out.write("\t\t\t\t\talert(\"请选择内容分类!\");\n");
      out.write("\t\t\t\t\treturn false;\n");
      out.write("\t\t\t\t}\n");
      out.write("\t\t\t\tif (!oActiveEl) {\n");
      out.write("\t\t\t          //插入form标签\n");
      out.write("\t\t\t\t\toActiveEl = oEditor.FCK.InsertElement( 'form' ) ;\n");
      out.write("\t\t\t\n");
      out.write("\t\t\t\t\tif ( oEditor.FCKBrowserInfo.IsGeckoLike )\n");
      out.write("\t\t\t\t\t\toEditor.FCKTools.AppendBogusBr( oActiveEl ) ;\n");
      out.write("\t\t\t\t}\n");
      out.write("\t\t\t\toActiveEl.name = GetE('txtName').value ;\n");
      out.write("\t\t\t\tSetAttribute( oActiveEl, 'action', '#' ) ;\n");
      out.write("\t\t\t\toActiveEl.method = GetE('txtMethod').value ;\n");
      out.write("\t\t        //设置隐藏域，内容分类的ID\n");
      out.write("\t\t        SetAttribute(oActiveEl, 'definition',GetE('txtDefinitionID').value);\n");
      out.write("\t\t        SetAttribute(oActiveEl, 'definitionname',GetE('txtDefinitionName').value);\n");
      out.write("\t\t        //node类别\n");
      out.write("\t\t        SetAttribute(oActiveEl, 'node', GetRadioObject(document.getElementsByName(\"nodeType\")).value);\n");
      out.write("\t        \t//SetAttribute(oActiveEl, 'node', GetRadioObject(nodeType).value);\n");
      out.write("\t\t        //SetAttribute(oActiveEl, 'title', GetE('txtDefinitionName').value);\n");
      out.write("\t\t        if(GetE('isMultipart').checked)\n");
      out.write("\t\t              SetAttribute( oActiveEl, 'enctype', 'multipart/form-data');\n");
      out.write("\t\t        else\n");
      out.write("\t\t             SetAttribute( oActiveEl, 'enctype', 'application/x-www-form-urlencoded');\n");
      out.write("\t\t        //设置隐藏域，内容ID，\n");
      out.write("\t\t        if(oActiveEl.getAttribute(\"onsubmit\") == null && oActiveEl.onsubmit == null && oActiveEl.getAttribute(\"onsubmit_fckprotectedatt\") == null)\n");
      out.write("\t\t            oActiveEl.onsubmit=\"return Validator.ValidateForm(this);\";\n");
      out.write("\t\t            \n");
      out.write("\t\t\t\tif (GetE('txtTarget').value)\n");
      out.write("\t\t          \toActiveEl.target = GetE('txtTarget').value;\n");
      out.write("\t\t\t\treturn true ;\n");
      out.write("\t\t\t}\n");
      out.write("\t\t</script>\n");
      out.write("\t</head>\n");
      out.write("\t<body style=\"overflow: hidden\">\n");
      out.write("\t\t<table width=\"100%\" style=\"height: 100%\">\n");
      out.write("\t\t\t<tr>\n");
      out.write("\t\t\t\t<td align=\"center\">\n");
      out.write("\t\t\t\t\t<table cellspacing=\"2\" cellpadding=\"2\" width=\"80%\" border=\"0\">\n");
      out.write("\t\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t\t<td align=\"right\"><span fcklang=\"DlgFormName\">Name</span></td>\n");
      out.write("\t\t\t\t\t\t\t<td align=\"left\"><input  size=\"40\"  type=\"text\" id=\"txtName\" readonly=\"readonly\" disabled=\"disabled\"/></td>\n");
      out.write("\t\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t\t<td align=\"right\"><span fcklang=\"DlgFormMethod\">Method</span></td>\n");
      out.write("\t\t\t\t\t\t\t<td align=\"left\">\n");
      out.write("\t\t\t\t\t\t\t\t<select id=\"txtMethod\">\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"get\" selected=\"selected\">GET</option>\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"post\">POST</option>\n");
      out.write("\t\t\t\t\t\t\t\t</select>\n");
      out.write("\t\t\t\t\t\t\t</td>\n");
      out.write("\t\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t\t<td align=\"right\"></td>\n");
      out.write("\t\t\t\t\t\t\t<td align=\"left\"><input type=\"checkbox\" value=\"true\" name=\"isMultipart\"/>支持文件上传</td>\n");
      out.write("\t\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t\t<td align=\"right\">目标</td>\n");
      out.write("\t\t\t\t\t\t\t<td align=\"left\"><input name=\"txtTarget\" id=\"txtTarget\"/></td>\n");
      out.write("\t\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t\t<td align=\"right\"><span>关联内容分类</span></td>\n");
      out.write("\t\t\t\t\t\t\t<td align=\"left\">\n");
      out.write("\t\t\t\t\t\t\t\t<input type=\"text\"   id=\"txtDefinitionName\" name=\"txtDefinitionName\"/>\n");
      out.write("\t\t\t\t\t\t\t\t<input type=\"hidden\" id=\"txtDefinitionID\" name=\"txtDefinitionID\"/>\n");
      out.write("\t\t\t\t\t\t\t\t<input type=\"button\" name=\"btnRepository\" value=\"选择...\" onclick=\"selectNodeDefinition()\"/>\n");
      out.write("\t\t\t\t\t\t\t</td>\n");
      out.write("\t\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t\t<td align=\"right\">内容来源</td>\n");
      out.write("\t\t\t\t\t\t\t<td align=\"left\">\n");
      out.write("\t\t\t\t\t\t\t\t<table border=0 cellpadding=0 cellspacing=0>\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td><input type=\"radio\" id=\"b0\" name=\"nodeType\" value=\"\" checked=\"true\"/><label for=\"b0\">无</label></td>\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td><input type=\"radio\" id=\"b1\" name=\"nodeType\" value=\"default\"/><label for=\"b1\">使用URL参数指定内容</label></td>\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td><input type=\"radio\" id=\"b2\" name=\"nodeType\" value=\"user\"/><label for=\"b2\">使用当前登录用户</label></td>\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td><input type=\"radio\" id=\"b3\" name=\"nodeType\" value=\"site\"/><label for=\"b3\">使用当前网站所属用户</label></td>\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t\t\t\t\t</table>\n");
      out.write("\t\t\t\t\t\t\t</td>\n");
      out.write("\t\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t\t</table>\n");
      out.write("\t\t\t\t</td>\n");
      out.write("\t\t\t</tr>\n");
      out.write("\t\t</table>\n");
      out.write("\t</body>\n");
      out.write("</html>\n");
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
    // /site/editor/plugins/exform/fck_form.jsp(9,15) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f0.setPage("/editor/dialog/common/fck_dialog_common.js");
    // /site/editor/plugins/exform/fck_form.jsp(9,15) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f0.setModule("/common");
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
    // /site/editor/plugins/exform/fck_form.jsp(10,15) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f1.setPage("/script/portlet.js");
    // /site/editor/plugins/exform/fck_form.jsp(10,15) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f1.setModule("/common");
    int _jspx_eval_html_005frewrite_005f1 = _jspx_th_html_005frewrite_005f1.doStartTag();
    if (_jspx_th_html_005frewrite_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f1);
    return false;
  }
}
