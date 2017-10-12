package org.apache.jsp.common.script;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class portlet_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\r\n");
      out.write("document.write( '<script type=\"text/javascript\" src=\"");
      if (_jspx_meth_html_005frewrite_005f0(_jspx_page_context))
        return;
      out.write("\"></script>' );\r\n");
      out.write("document.write( '<script type=\"text/javascript\" src=\"");
      if (_jspx_meth_html_005frewrite_005f1(_jspx_page_context))
        return;
      out.write("\"></script>' );\r\n");
      out.write("document.write( '<script type=\"text/javascript\" src=\"");
      if (_jspx_meth_html_005frewrite_005f2(_jspx_page_context))
        return;
      out.write("\"></script>' );\r\n");
      out.write("document.write( '<script type=\"text/javascript\" src=\"");
      if (_jspx_meth_html_005frewrite_005f3(_jspx_page_context))
        return;
      out.write("\"></script>' );\r\n");
      out.write("document.write( '<script type=\"text/javascript\" src=\"");
      if (_jspx_meth_html_005frewrite_005f4(_jspx_page_context))
        return;
      out.write("\"></script>' );\r\n");
      out.write("\r\n");
      out.write(" \r\n");
      out.write("      /** \r\n");
      out.write("  *超文本编辑器\r\n");
      out.write("  **/\r\n");
      out.write("  function advancedText(oControl)\r\n");
      out.write("  {\r\n");
      out.write("    var ret = CMSDialog.RichTextEditor(oControl.value);\r\n");
      out.write("    if (ret)\r\n");
      out.write("      oControl.value = ret;\r\n");
      out.write("  }\r\n");
      out.write("      /**\r\n");
      out.write("  *编辑页面片段\r\n");
      out.write("  **/\r\n");
      out.write("  function editClipFile(path,oCategory,oFrame, formDefinition, styleSheets){\r\n");
      out.write("  \t  path = '/'+ window.dialogArguments.template.name+ path;\r\n");
      out.write("\t  var definitionId=null;\r\n");
      out.write("\t  if(oCategory!=null){\r\n");
      out.write("\t\t  definitionId=oCategory.value;\r\n");
      out.write("\t  }\r\n");
      out.write("    var ret=SiteDialog.editClipFile(path,definitionId, formDefinition, styleSheets);\r\n");
      out.write("    if(ret!=null&&oFrame!=null){\r\n");
      out.write("    \toFrame.location.href=oFrame.location.href+\"&a=\"+Math.random();\r\n");
      out.write("    }\r\n");
      out.write("  }\r\n");
      out.write("\r\n");
      out.write("  /**\r\n");
      out.write("  *添加过滤器\r\n");
      out.write("  **/\r\n");
      out.write("  function newFilter(oCategory,oSelect){\r\n");
      out.write("    var filter=CMSDialog.FilterEditor(oCategory.value);\r\n");
      out.write("    if(filter!=null){\r\n");
      out.write("      var newOption=document.createElement(\"option\");\r\n");
      out.write("      newOption.value=filter.value;\r\n");
      out.write("      newOption.text=filter.name;\r\n");
      out.write("      oSelect.add(newOption);\r\n");
      out.write("    }\r\n");
      out.write("  }\r\n");
      out.write("  \r\n");
      out.write("  /**\r\n");
      out.write("  *添加支持搜索大纲条件的过滤器\r\n");
      out.write("  **/\r\n");
      out.write("  function newFilter_Search(oCategory,oSelect,searchDefID){\r\n");
      out.write("    var filter=CMSDialog.SearchFilterEditor(oCategory.value,searchDefID);\r\n");
      out.write("    if(filter!=null){\r\n");
      out.write("      var newOption=document.createElement(\"option\");\r\n");
      out.write("      newOption.value=filter.value;\r\n");
      out.write("      newOption.text=filter.name;\r\n");
      out.write("      oSelect.add(newOption);\r\n");
      out.write("    }\r\n");
      out.write("  }\r\n");
      out.write("\r\n");
      out.write(" /**\r\n");
      out.write("  *\r\n");
      out.write("  *选择栏目\r\n");
      out.write("  **/\r\n");
      out.write("  function chooseColumn(defaultTemplateId,oPath, oName){\r\n");
      out.write("  \tvar path = oPath.value;\r\n");
      out.write("  \tvar templateName = defaultTemplateId;\r\n");
      out.write("  \tif(path==null||path==\"\"){\r\n");
      out.write("  \t\tpath = \"/\" + templateName + \"/index.jsp\";\r\n");
      out.write("  \t}\r\n");
      out.write("\tvar channel = SiteDialog.selectChannel(templateName,path,true,true,\"选择跳转页面 \",[\"clip\",\"script\",\"style\"]);\r\n");
      out.write("\tif(channel!=null){\r\n");
      out.write("\t\tif(oPath!=null){\r\n");
      out.write("\t\t\t//oPath.value =\"");
      out.print(request.getContextPath());
      out.write("/\"+channel.name;\r\n");
      out.write("\t\t\toPath.value = channel.path;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tif(oName!=null){\r\n");
      out.write("\t\t\toName.value = channel.template.displayName+\".\"+channel.displayName;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("  }\r\n");
      out.write("\r\n");
      out.write("  function parseTemplate(path){\r\n");
      out.write("  \t var splits = path.split('/');\r\n");
      out.write("\t if(splits.length>2){\r\n");
      out.write("\t \treturn splits[1];\r\n");
      out.write("\t }\r\n");
      out.write("\t return null;\r\n");
      out.write("  }\r\n");
      out.write("  function parseChannel(path){\r\n");
      out.write("  \t var splits = path.split('/');\r\n");
      out.write("\t if(splits.length>1){\r\n");
      out.write("\t \treturn path.substring(path.indexOf(splits[2])-1,path.length);\r\n");
      out.write("\t }\r\n");
      out.write("\t return null;\r\n");
      out.write("  }\r\n");
      out.write("  \r\n");
      out.write("  /**\r\n");
      out.write("  *\r\n");
      out.write("  *添加分类\r\n");
      out.write("  **/\r\n");
      out.write("  function addNodeDefinition(oSelect,bSingle){\r\n");
      out.write("    var arr = CMSDialog.NodeDefinitionSelector(null,null,bSingle);\r\n");
      out.write("    if(arr!=null){\r\n");
      out.write("    if(arr.length>0){\r\n");
      out.write("    \tfor(i=0;i<arr.length;i++){\r\n");
      out.write("    \tif(!findSelectItem(oSelect,arr[i].ID)){\r\n");
      out.write("\t\t\tvar newOption=document.createElement(\"option\");\r\n");
      out.write("\t\t\tnewOption.value=arr[i].ID;\r\n");
      out.write("\t\t\tnewOption.text=arr[i].name;\r\n");
      out.write("\t\t\toSelect.add(newOption);\r\n");
      out.write("\t\t}\r\n");
      out.write("    \t}\r\n");
      out.write("    }\r\n");
      out.write("    }\r\n");
      out.write("  }\r\n");
      out.write("  /**\r\n");
      out.write("  *\r\n");
      out.write("  *选择分类\r\n");
      out.write("  **/\r\n");
      out.write("  function searchNodeDefinition(oID, oName,clearObj){\r\n");
      out.write("  //clearObj为需要清空内容的对象\r\n");
      out.write("  if(clearObj != null && clearObj !=\"undefine\"){\r\n");
      out.write("  clearObj.value=\"\";\r\n");
      out.write("  }\r\n");
      out.write("    var ret = CMSDialog.NodeDefinitionSelector('no-properties-scheme', null, true, false, false);     \r\n");
      out.write("    if(ret!=null){\r\n");
      out.write("      if(oID!=null){\r\n");
      out.write("      oID.value = ret.ID;\r\n");
      out.write("      }\r\n");
      out.write("      if(oName!=null)\r\n");
      out.write("      oName.value = ret.name;\r\n");
      out.write("    }\r\n");
      out.write("  }\r\n");
      out.write("\r\n");
      out.write("  /**\r\n");
      out.write("  *\r\n");
      out.write("  *选择内容\r\n");
      out.write("  **/\r\n");
      out.write("  function doSelectFilterProperty(_select){\r\n");
      out.write("    if(_select.value.length!=0){\r\n");
      out.write("      var categoryId=_select.form.elements('preference(category)').value;\r\n");
      out.write("      searchContent(categoryId);\r\n");
      out.write("    }\r\n");
      out.write("  }\r\n");
      out.write("\r\n");
      out.write("  /**\r\n");
      out.write("  *\r\n");
      out.write("  *选择内容\r\n");
      out.write("  **/\r\n");
      out.write("  function searchContent(oName,oValue){\r\n");
      out.write("    var categoryId=document.getElementById(\"preference(category)\").value;\r\n");
      out.write("    var arr = CMSDialog.NodeSelector(categoryId);\r\n");
      out.write("    if(arr!=null){\r\n");
      out.write("      oName = arr.name;\r\n");
      out.write("      oValue = arr.ID;\r\n");
      out.write("    }\r\n");
      out.write("  }\r\n");
      out.write("\r\n");
      out.write("  /**\r\n");
      out.write("  *\r\n");
      out.write("  *选择属性\r\n");
      out.write("  **/\r\n");
      out.write("  function searchPropertyDefinition(oCategory,oID, oName, excludes,hasChild){\r\n");
      out.write("    var arr =  CMSDialog.PropertyDefinitionSelector(oCategory.value,excludes);\r\n");
      out.write("    if(hasChild == \"true\"){\r\n");
      out.write("    \tif(!arr.hasChild()){\r\n");
      out.write("    \t\talert(\"该分类不含属性\");\r\n");
      out.write("    \t\treturn;\r\n");
      out.write("    \t}\r\n");
      out.write("    }else{\r\n");
      out.write("    \tif(arr!=null&&arr.ID!=null){\r\n");
      out.write("      oName.value = arr.name;\r\n");
      out.write("      oID.value = arr.ID;\r\n");
      out.write("    }\r\n");
      out.write("    }\r\n");
      out.write("   /*\r\n");
      out.write("    if(arr!=null&&arr.ID!=null){\r\n");
      out.write("      oName.value = arr.name;\r\n");
      out.write("      oID.value = arr.ID;\r\n");
      out.write("    }*/\r\n");
      out.write("  }\r\n");
      out.write("  \r\n");
      out.write("  /**\r\n");
      out.write("  *\r\n");
      out.write("  *添加属性\r\n");
      out.write("  **/\r\n");
      out.write("  function addPropertyDefinition(oCategory, oSelect, excludes){\r\n");
      out.write("    var arr =  CMSDialog.PropertyDefinitionSelector(oCategory.value,excludes);\r\n");
      out.write("    if(arr!=null){\r\n");
      out.write("    var newOption=document.createElement(\"option\");\r\n");
      out.write("\t\t\tnewOption.value=arr.ID;\r\n");
      out.write("\t\t\tnewOption.text=arr.name;\r\n");
      out.write("\t\t\toSelect.add(newOption);\r\n");
      out.write("    }\r\n");
      out.write("  }\r\n");
      out.write("\r\n");
      out.write("/**\r\n");
      out.write("  *\t  自动往过滤器中添加搜索大纲中的过滤条件\r\n");
      out.write("  **/\r\n");
      out.write("  function autoAddFilterPattern(oCategory,oSelect,searchDefID){\r\n");
      out.write("\t  var req = getXMLHttpRequest();\r\n");
      out.write("\t  var url='");
      if (_jspx_meth_html_005frewrite_005f5(_jspx_page_context))
        return;
      out.write("'+oCategory+'&searchDefID='+searchDefID+'&equal='+encodeURI(\"等于\")+'&more='+encodeURI(\"大于\")+'&less='+encodeURI(\"小于\") + '&timestamp=' + Math.random();;\r\n");
      out.write("  req.open(\"POST\",url, false);\r\n");
      out.write("  req.setRequestHeader(\"Content-Type\", \"text/html; charset=utf-8\");\r\n");
      out.write("  req.send(null);\r\n");
      out.write("  if(req.status!=200){\r\n");
      out.write("    alert(\"无法获取链接：\"+url);\r\n");
      out.write("    return;\r\n");
      out.write("  }\r\n");
      out.write("  var resXML=req.responseXML;\r\n");
      out.write("  if(resXML!=null){\r\n");
      out.write("\t  removeAll(oSelect);\r\n");
      out.write("\t  var properties=resXML.getElementsByTagName(\"parameter\");\r\n");
      out.write("  for(var i=0;i<properties.length;i++){\r\n");
      out.write("\tvar name=properties[i].getElementsByTagName(\"name\")[0].text;\r\n");
      out.write("var value=properties[i].getElementsByTagName(\"value\")[0].text;\r\n");
      out.write("var newOption=document.createElement(\"option\");\r\n");
      out.write("\t\t\tnewOption.value=value;\r\n");
      out.write("\t\t\tnewOption.text=name;\r\n");
      out.write("\t\t\toSelect.add(newOption);\r\n");
      out.write("\t\t  }\r\n");
      out.write("\t  }\r\n");
      out.write("  }\r\n");
      out.write("/**\r\n");
      out.write(" * 选择活动\r\n");
      out.write(" */\r\n");
      out.write("  function doSelectActivity(oProcess, oActivity,oName,oTemplate){\r\n");
      out.write("\t  var ret = showDialog('");
      if (_jspx_meth_html_005frewrite_005f6(_jspx_page_context))
        return;
      out.write("',oTemplate,620,450);\r\n");
      out.write("\t  if(ret!=null){\r\n");
      out.write("\t\t  oProcess.value = ret.definition.ID;\r\n");
      out.write("\t\t  oActivity.value = ret.activity.ID;\r\n");
      out.write("\t\t  oName.value=ret.definition.name +\".\"+ret.activity.name;\r\n");
      out.write("\t  }\r\n");
      out.write("  }\r\n");
      out.write("  /**\r\n");
      out.write("   * 显示活动的全称\r\n");
      out.write("   */\r\n");
      out.write("  function showActivityFullName(oProcess, oActivity, oName){\r\n");
      out.write("\t  if(oProcess.value.length == 0)\r\n");
      out.write("\t\t  return;\r\n");
      out.write("\t  if(oActivity.value.length == 0)\r\n");
      out.write("\t\t  return;\r\n");
      out.write("\t  var req = getXMLHttpRequest();\r\n");
      out.write("\t  var url='");
      if (_jspx_meth_html_005frewrite_005f7(_jspx_page_context))
        return;
      out.write("?timestamp=' + Math.random();\r\n");
      out.write("\t  url = url+\"&processID=\"+oProcess.value;\r\n");
      out.write("\t  url = url +\"&activityID=\"+oActivity.value;\r\n");
      out.write("\t  req.open(\"POST\",url, false);\r\n");
      out.write("\t  req.setRequestHeader(\"Content-Type\", \"text/html; charset=utf-8\");\r\n");
      out.write("\t  req.send(null);\r\n");
      out.write("\t  if(req.status == 200)\r\n");
      out.write("\t\t  oName.value = req.responseText;\r\n");
      out.write("  }\r\n");
      out.write("  \r\n");
      out.write(" /**\r\n");
      out.write("  *清空按钮\r\n");
      out.write("  **/\r\n");
      out.write("  function clearTxt(oPreference,oName){\r\n");
      out.write("  if (oPreference != null) {\r\n");
      out.write("      oPreference.value=\"\";\r\n");
      out.write("      }\r\n");
      out.write("      oName.value=\"\";\r\n");
      out.write("  }\r\n");
      out.write("  \r\n");
      out.write("   \tfunction validateForward(oForm){\r\n");
      out.write("\t\tif(oForm(\"preference(type)\").value==\"link\"||oForm(\"preference(type)\").value==\"index\"){\r\n");
      out.write("\t\t\tif(oForm(\"preference(channel)\").value==\"\"){\r\n");
      out.write("\t\t\t\talert(\"跳转到不能为空！\");\r\n");
      out.write("\t\t\t\treturn false;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\treturn true;\r\n");
      out.write("\t}");
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
    com.fulong.ide.RewriteTag _jspx_th_html_005frewrite_005f0 = (com.fulong.ide.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(com.fulong.ide.RewriteTag.class);
    _jspx_th_html_005frewrite_005f0.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f0.setParent(null);
    // /common/script/portlet.jsp(3,53) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f0.setModule("/common");
    // /common/script/portlet.jsp(3,53) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f0.setPage("/script/common.js.jsp");
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
    com.fulong.ide.RewriteTag _jspx_th_html_005frewrite_005f1 = (com.fulong.ide.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(com.fulong.ide.RewriteTag.class);
    _jspx_th_html_005frewrite_005f1.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f1.setParent(null);
    // /common/script/portlet.jsp(4,53) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f1.setModule("/common");
    // /common/script/portlet.jsp(4,53) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f1.setPage("/script/ajax.js");
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
    com.fulong.ide.RewriteTag _jspx_th_html_005frewrite_005f2 = (com.fulong.ide.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(com.fulong.ide.RewriteTag.class);
    _jspx_th_html_005frewrite_005f2.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f2.setParent(null);
    // /common/script/portlet.jsp(5,53) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f2.setModule("/cms");
    // /common/script/portlet.jsp(5,53) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f2.setPage("/classes/cmsdialog.js");
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
    com.fulong.ide.RewriteTag _jspx_th_html_005frewrite_005f3 = (com.fulong.ide.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(com.fulong.ide.RewriteTag.class);
    _jspx_th_html_005frewrite_005f3.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f3.setParent(null);
    // /common/script/portlet.jsp(6,53) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f3.setModule("/site");
    // /common/script/portlet.jsp(6,53) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f3.setPage("/dialog.js.jsp");
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
    com.fulong.ide.RewriteTag _jspx_th_html_005frewrite_005f4 = (com.fulong.ide.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(com.fulong.ide.RewriteTag.class);
    _jspx_th_html_005frewrite_005f4.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f4.setParent(null);
    // /common/script/portlet.jsp(7,53) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f4.setModule("/cms");
    // /common/script/portlet.jsp(7,53) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f4.setPage("/classes/filterparser.js.jsp");
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
    com.fulong.ide.RewriteTag _jspx_th_html_005frewrite_005f5 = (com.fulong.ide.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(com.fulong.ide.RewriteTag.class);
    _jspx_th_html_005frewrite_005f5.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f5.setParent(null);
    // /common/script/portlet.jsp(202,12) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f5.setPage("/autoAddFilter.do?defID=");
    // /common/script/portlet.jsp(202,12) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f5.setModule("/cms");
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
    com.fulong.ide.RewriteTag _jspx_th_html_005frewrite_005f6 = (com.fulong.ide.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(com.fulong.ide.RewriteTag.class);
    _jspx_th_html_005frewrite_005f6.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f6.setParent(null);
    // /common/script/portlet.jsp(228,25) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f6.setPage("/selector.jsp");
    // /common/script/portlet.jsp(228,25) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f6.setModule("/process/activity");
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
    com.fulong.ide.RewriteTag _jspx_th_html_005frewrite_005f7 = (com.fulong.ide.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(com.fulong.ide.RewriteTag.class);
    _jspx_th_html_005frewrite_005f7.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f7.setParent(null);
    // /common/script/portlet.jsp(244,12) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f7.setPage("/fullname.do");
    // /common/script/portlet.jsp(244,12) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f7.setModule("/process/activity");
    int _jspx_eval_html_005frewrite_005f7 = _jspx_th_html_005frewrite_005f7.doStartTag();
    if (_jspx_th_html_005frewrite_005f7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f7);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f7);
    return false;
  }
}
