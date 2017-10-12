package org.apache.jsp.site;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class dialog_js_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\n");
      out.write("document.write( '<script type=\"text/javascript\" src=\"");
      if (_jspx_meth_html_005frewrite_005f0(_jspx_page_context))
        return;
      out.write("\"></script>' );\n");
      out.write("/**\n");
      out.write(" * 用来控制所有的弹出窗口\n");
      out.write(" *\n");
      out.write("**/\n");
      out.write("var SiteDialog\t=\tnew Object();\n");
      out.write(" /**\n");
      out.write("   * 创建栏目、模板、网站的集成对话框\n");
      out.write("   * @return SiteTemplate 对象\n");
      out.write("   * @throws Exception\n");
      out.write("   * @author lichengzhao\n");
      out.write("   */\n");
      out.write("SiteDialog.create = function(templateName, folderPath){\n");
      out.write("\tvar url='");
      if (_jspx_meth_html_005frewrite_005f1(_jspx_page_context))
        return;
      out.write("?';\n");
      out.write("\turl += 'templateName=' + templateName;\n");
      out.write("    url += '&folderPath=' + folderPath;\n");
      out.write("    return showDialog(url,null,620,460);\n");
      out.write("}\n");
      out.write("\n");
      out.write("/**\n");
      out.write("   * 修改栏目对话框\n");
      out.write("   * @param channelID 缺省栏目ID\n");
      out.write("   * @return Channel 对象\n");
      out.write("   * @throws Exception\n");
      out.write("   * @author lichengzhao\n");
      out.write("   */\n");
      out.write("SiteDialog.editChannel = function($channelID){\n");
      out.write("\tvar url='");
      if (_jspx_meth_html_005frewrite_005f2(_jspx_page_context))
        return;
      out.write("?&channelID=' + $channelID;\n");
      out.write("    return showDialog(url,null,510,400);\n");
      out.write("}\n");
      out.write("\n");
      out.write("/**\n");
      out.write("   * 修改js和css栏目属性对话框\n");
      out.write("   * @param channelID 缺省栏目ID\n");
      out.write("   * @return Channel 对象\n");
      out.write("   * @throws Exception\n");
      out.write("   */\n");
      out.write("SiteDialog.editeCssAndStyle = function($channelID){\n");
      out.write("\tvar url='");
      if (_jspx_meth_html_005frewrite_005f3(_jspx_page_context))
        return;
      out.write("?&channelID=' + $channelID;\n");
      out.write("    return showDialog(url,null,273,115);\n");
      out.write("}\n");
      out.write("\n");
      out.write(" /**\n");
      out.write("   * 选择网站模板\n");
      out.write("   * @param defaultCategoryId 默认选中的模板分类\n");
      out.write("   * @param defaultTemplateIds 默认选中的模板\n");
      out.write("   * @return null 或 SiteTemplate[] 数组，该数组长度可以为0\n");
      out.write("   * @throws Exception\n");
      out.write("   */\n");
      out.write("SiteDialog.selectTemplate = function($defaultCategoryId,$defaultTemplateIds){\n");
      out.write("    var url = '");
      if (_jspx_meth_html_005frewrite_005f4(_jspx_page_context))
        return;
      out.write("?type=selectTemplate';\n");
      out.write("\tif($defaultCategoryId!=null){\n");
      out.write("\t\turl += '&defaultCategoryId=' + $defaultCategoryId;\n");
      out.write("\t}\n");
      out.write("\tif($defaultTemplateIds != null){\n");
      out.write("\t\tfor(var i=0;i<$defaultTemplateIds.length;i++){\n");
      out.write("\t\t\turl += '&defaultTemplateId=' + $defaultTemplateIds[i];\n");
      out.write("\t\t}\n");
      out.write("\t}\n");
      out.write("    return showDialog(url, null, 580,330);\n");
      out.write("}\n");
      out.write("\n");
      out.write(" /**\n");
      out.write("   * 选择网站下的模板\n");
      out.write("   * @param domain 网站域名 如果不指定则使用当前域名\n");
      out.write("   * @return null 或 SiteTemplate,\n");
      out.write("   * @throws Exception\n");
      out.write("   */\n");
      out.write("SiteDialog.selectTemplateBySite = function($domain){\n");
      out.write("    var url = '");
      if (_jspx_meth_html_005frewrite_005f5(_jspx_page_context))
        return;
      out.write("?type=selectTemplateBySite';\n");
      out.write("    if($domain!=null){\n");
      out.write("    \turl += '&domain=' + $domain\n");
      out.write("    }\n");
      out.write("    return showDialog(url, null, 500,330);\n");
      out.write("}\n");
      out.write("\n");
      out.write(" /**\n");
      out.write("   * 选择导航网站模板\n");
      out.write("   * @param templateIds 模板ID,数组，不可为空\n");
      out.write("   * @param defaultTemplateIds 默认选中模板ID,数组，不可为空\n");
      out.write("   * @return null 或 SiteTemplate[] 数组，该数组长度可以为0\n");
      out.write("   * @throws Exception\n");
      out.write("   */\n");
      out.write("SiteDialog.selectNavigateTemplate = function($templateIds,$defaultTemplateIds){\n");
      out.write("    var url = '");
      if (_jspx_meth_html_005frewrite_005f6(_jspx_page_context))
        return;
      out.write("?type=selectNavigateTemplate'\n");
      out.write("\tfor(var i=0;i<$templateIds.length;i++){\n");
      out.write("\t\turl += '&templateId=' + $templateIds[i];\n");
      out.write("\t}\n");
      out.write("\tif($defaultTemplateIds != null){\n");
      out.write("\t\tfor(var i=0;i<$defaultTemplateIds.length;i++){\n");
      out.write("\t\t\turl += '&defaultTemplateId=' + $defaultTemplateIds[i];\n");
      out.write("\t\t}\n");
      out.write("\t}\n");
      out.write("    return showDialog(url, null, 500,320);\n");
      out.write("}\n");
      out.write("\n");
      out.write("/**\n");
      out.write("   * 选择网站栏目\n");
      out.write("   * @param templateName 缺省模板name，可以为空\n");
      out.write("   * @param path 缺省选择中的栏目path，可以为空\n");
      out.write("   * @param bSingle 是否支持多选，缺省为true,不支持\n");
      out.write("   * @param openTemplate 是否支持打开其它的网站模版\n");
      out.write("   * @param title  标题，缺省为“选择栏目”\n");
      out.write("   * @param sType 过滤掉指定类型\n");
      out.write("   * @return Channel 对象\n");
      out.write("   * @throws Exception\n");
      out.write("   */\n");
      out.write("SiteDialog.selectChannel= function(templateName, path, bSingle, openTemplate, title ,sType){\n");
      out.write("\tvar url = [];\n");
      out.write("    url.push('");
      if (_jspx_meth_html_005frewrite_005f7(_jspx_page_context))
        return;
      out.write("?time='+new Date().getTime());\n");
      out.write("\turl.push('&templateName='+templateName);\n");
      out.write("    if(path!=null){\n");
      out.write("\t    if(typeof path==\"Array\"){\n");
      out.write("\t    \tfor(var i=0;i<path.length;i++){\n");
      out.write("\t    \t\turl.push('&path='+path[i]);\n");
      out.write("\t    \t}\n");
      out.write("\t\t}else{\n");
      out.write("\t\t\turl.push('&path='+path);\n");
      out.write("\t\t}\n");
      out.write("    }\n");
      out.write("    if(openTemplate === true)\n");
      out.write("        url.push('&openTemplate=true');\n");
      out.write("\tif(!bSingle)\n");
      out.write("\t    url.push(\"&multi=true\");\n");
      out.write("\tif(title == null)\n");
      out.write("    \ttitle = \"选择页面\";\n");
      out.write("\t//url.push(\"&title\" + title);\n");
      out.write("\tif(sType!=null){\n");
      out.write("\t\tfor(var i=0; i<sType.length; i++)\n");
      out.write("\t\t\turl.push('&filterType='+sType[i]);\n");
      out.write("\t}\n");
      out.write("    return showDialog(url.join(\"\"), title, 400,380);\n");
      out.write("}\n");
      out.write("\n");
      out.write("\n");
      out.write("/**\n");
      out.write("   * 转移栏目\n");
      out.write("   * @param templateId 模板ID，必须提供\n");
      out.write("   * @param channelId 栏目ID，必须提供\n");
      out.write("   * @return true:操作成功\n");
      out.write("   * @throws Exception\n");
      out.write("   * @author lichengzhao\n");
      out.write("   */\n");
      out.write("   /**\n");
      out.write("SiteDialog.moveChannel = function(templateId,channelId){\n");
      out.write("\t    var url='");
      if (_jspx_meth_html_005frewrite_005f8(_jspx_page_context))
        return;
      out.write("?';\n");
      out.write("        url+='templateID='+templateId;\n");
      out.write("\t\turl+='&ID='+channelId;\n");
      out.write("\t\tvar version =isVersion();\n");
      out.write("\t\tvar ds=\"dialogWidth:400px;dialogHeight:350px;help:no;scrollbars:yes;status:no\";\n");
      out.write("\t\tif(version=='ie6'){\n");
      out.write("\t\t\tds=\"dialogWidth:400px;dialogHeight:400px;help:no;scrollbars:yes;status:no\";\n");
      out.write("\t\t\t}\n");
      out.write("\t\tif(version=='ie7'){\n");
      out.write("\t\t\tds=\"dialogWidth:400px;dialogHeight:350px;help:no;scrollbars:yes;status:no\";\n");
      out.write("\t\t\t}\n");
      out.write("\t\t\n");
      out.write("        return showModalDialog(url,\"\",ds);\n");
      out.write("}\n");
      out.write("**/\n");
      out.write("/**\n");
      out.write("   * 栏目授权\n");
      out.write("   * @param oTemplate 模板ID，必须提供\n");
      out.write("   * @param principalId 栏目ID，必须提供\n");
      out.write("   * @return true:操作成功\n");
      out.write("   * @throws Exception\n");
      out.write("   */\n");
      out.write("SiteDialog.Permission = function(oTemplate){\n");
      out.write("    var url='");
      if (_jspx_meth_html_005frewrite_005f9(_jspx_page_context))
        return;
      out.write("';\n");
      out.write("    return showDialog(url,oTemplate,350,370);\n");
      out.write("}\n");
      out.write("\n");
      out.write("/**\n");
      out.write(" * 选择文件夹\n");
      out.write(" * @param oTempalte 模板对象，必须提供\n");
      out.write(" * @param path 路径，缺省为根目录\n");
      out.write(" * @return folder对象\n");
      out.write(" */\n");
      out.write("SiteDialog.selectFolder = function(oTempalte) {\n");
      out.write("\tvar url = Globals.contextPath + \"/site/editor/plugins/folder/folders.jsp\";\n");
      out.write("\tvar args = new Object();\n");
      out.write("\targs.template = oTempalte;\n");
      out.write("\treturn showDialog(  url, args, 320,280, null, null,  true );\t\n");
      out.write("}\n");
      out.write("\n");
      out.write("/**\n");
      out.write("   * 编辑模板的资源目录\n");
      out.write("   * @param templateId 模板ID，必须提供\n");
      out.write("   * @param path 路径，缺省为根目录\n");
      out.write("   * @return true:操作成功\n");
      out.write("   * @throws Exception\n");
      out.write("   */\n");
      out.write("SiteDialog.editResourcesFolder = function(templateId, path){\n");
      out.write("\t\tvar url='");
      if (_jspx_meth_html_005frewrite_005f10(_jspx_page_context))
        return;
      out.write("?';\n");
      out.write("        url+='templateID='+templateId;\n");
      out.write("        if(path)\n");
      out.write("\t\t\turl += '&path=' + encodeURIComponent(path);\n");
      out.write("\t\turl += '&timestamp=' + Math.random();\n");
      out.write("\t\tvar version =isVersion();\n");
      out.write("\t\tvar ds=\"dialogWidth:260px;dialogHeight:30px;help:no;scrollbars:yes;status:no\";\n");
      out.write("\t\tif(version == 'ie6')\n");
      out.write("\t\t\tds=\"dialogWidth:260px;dialogHeight:80px;help:no;scrollbars:yes;status:no\";\n");
      out.write("        return showModalDialog(url,\"\",ds);\n");
      out.write("}\n");
      out.write("\n");
      out.write("/**\n");
      out.write("   * 修改模版信息\n");
      out.write("   * @param templateId 模板ID，必须提供\n");
      out.write("   * @return SiteTemplate对象\n");
      out.write("   * @throws Exception\n");
      out.write("   * @author lichengzhao\n");
      out.write("   */\n");
      out.write("\n");
      out.write("SiteDialog.editTemplate = function(templateId){\n");
      out.write("    var url = '");
      if (_jspx_meth_html_005frewrite_005f11(_jspx_page_context))
        return;
      out.write("' + templateId;\n");
      out.write("    var version =isVersion();\n");
      out.write("\tvar ds=\"dialogWidth:300px;dialogHeight:160px;help:no;scrollbars:yes;status:no\";\n");
      out.write("\tif(version=='ie6')\n");
      out.write("\t\tds=\"dialogWidth:300px;dialogHeight:210px;help:no;scrollbars:yes;status:no\";\n");
      out.write("\treturn showModalDialog(url,\"\",ds);\n");
      out.write("}\n");
      out.write("/**\n");
      out.write("   * 重命名模版显示名称\n");
      out.write("   * @param templateId 模板ID，必须提供\n");
      out.write("   * @return SiteTemplate对象\n");
      out.write("   * @throws Exception\n");
      out.write("   * @author lichengzhao\n");
      out.write("   */\n");
      out.write("\n");
      out.write("SiteDialog.renameTemplate = function(templateId){\n");
      out.write("    var url = '");
      if (_jspx_meth_html_005frewrite_005f12(_jspx_page_context))
        return;
      out.write("' + templateId;\n");
      out.write("    var version =isVersion();\n");
      out.write("\t\tvar ds=\"dialogWidth:300px;dialogHeight:110px;help:no;scrollbars:yes;status:no\";\n");
      out.write("\t\tif(version=='ie6'){\n");
      out.write("\t\t\tds=\"dialogWidth:300px;dialogHeight:160px;help:no;scrollbars:yes;status:no\";\n");
      out.write("\t\t\t}\n");
      out.write("\t\tif(version=='ie7'){\n");
      out.write("\t\t\tds=\"dialogWidth:300px;dialogHeight:110px;help:no;scrollbars:yes;status:no\";\n");
      out.write("\t\t\t}\n");
      out.write("\treturn showModalDialog(url,\"\",ds);\n");
      out.write("}\n");
      out.write("/**\n");
      out.write("   * 管理网站\n");
      out.write("   * @param templateId 模板ID，可以为空\n");
      out.write("   * @param $title 对话空的title，可以为空\n");
      out.write("   * @return 无\n");
      out.write("   * @throws Exception\n");
      out.write("   */\n");
      out.write("\n");
      out.write("SiteDialog.manageSite = function(templateId, $title){\n");
      out.write("    var url = '");
      if (_jspx_meth_html_005frewrite_005f13(_jspx_page_context))
        return;
      out.write("?';\n");
      out.write("  /*  if(templateId!=null)\n");
      out.write("\t    url = url+\"templateId=\" + templateId + \"&\";\n");
      out.write("\tif ($title)\n");
      out.write("\t\turl += \"title=\" + $title;*/\n");
      out.write("\tvar version =isVersion();\n");
      out.write("\t\tvar ds=\"dialogWidth:800px;dialogHeight:620px;help:no;scrollbars:yes;status:no\";\n");
      out.write("\t\tif(version=='ie6'){\n");
      out.write("\t\t\tds=\"dialogWidth:807px;dialogHeight:672px;help:no;scrollbars:yes;status:no\";\n");
      out.write("\t\t\t}\n");
      out.write("\t\tif(version=='ie7'){\n");
      out.write("\t\t\tds=\"dialogWidth:800px;dialogHeight:620px;help:no;scrollbars:yes;status:no\";\n");
      out.write("\t\t\t}\t\n");
      out.write("\treturn showModalDialog(url,\"\",ds);\n");
      out.write("}\n");
      out.write("/**\n");
      out.write("   * 管理网站\n");
      out.write("   * @param siteId 网站ID，可以为空，如果为空，则使用当前域名对应的网站\n");
      out.write("   * @return Site对象\n");
      out.write("   * @throws Exception\n");
      out.write("   */\n");
      out.write("SiteDialog.editSite = function(siteId){\n");
      out.write("    var url = '");
      if (_jspx_meth_html_005frewrite_005f14(_jspx_page_context))
        return;
      out.write("?';\n");
      out.write("     if(siteId!=null)\n");
      out.write("\t     url = url+\"siteID=\" + siteId;\n");
      out.write("\turl = url +\"&timestamp=\" + new Date().getTime();\n");
      out.write("\treturn showModalDialog(url,\"\",\"dialogWidth:380px;dialogHeight:220px;help:no;scrollbars:yes;status:no\");\n");
      out.write("\n");
      out.write("}\n");
      out.write("\n");
      out.write("\n");
      out.write("/**\n");
      out.write(" *\n");
      out.write(" * 编辑页面片段\n");
      out.write(" **/\n");
      out.write("SiteDialog.editClipFile\t=\tfunction(path,definitionId, formDefinition , styleSheets){\n");
      out.write("        var url='");
      if (_jspx_meth_html_005frewrite_005f15(_jspx_page_context))
        return;
      out.write("?definition='+definitionId+'&path='+path;\n");
      out.write("        if((formDefinition!=null)&&(formDefinition.length>0))\n");
      out.write("        url = url+\"&formDefinition=\"+formDefinition;\n");
      out.write("        var args=new Object();\n");
      out.write("        args.window=window;        \n");
      out.write("        args.EditorWindow = window.dialogArguments.window;   \n");
      out.write("        args.form = window.dialogArguments.form;\n");
      out.write("        args.definition = definitionId;\n");
      out.write("        args.styleSheets=styleSheets;\n");
      out.write("        return showModalDialog(url,args,\"dialogWidth:600px;dialogHeight:300px;help:no;scrollbars:yes;status:no;resizable:yes\");\n");
      out.write("}\n");
      out.write("/**\n");
      out.write(" *\n");
      out.write(" * 编辑页面片段（表格占位符专用）\n");
      out.write(" **/\n");
      out.write("SiteDialog.tableEditClipFile\t=\tfunction(path,definitionId, formDefinition){\n");
      out.write("\t\tpath = '/'+ window.dialogArguments.template.name+ path;\n");
      out.write("        var url='");
      if (_jspx_meth_html_005frewrite_005f16(_jspx_page_context))
        return;
      out.write("?definition='+definitionId+'&path='+path;\n");
      out.write("        if((formDefinition!=null)&&(formDefinition.length>0))\n");
      out.write("        url = url+\"&formDefinition=\"+formDefinition;\n");
      out.write("        var args=new Object();\n");
      out.write("        args.window=window;        \n");
      out.write("        args.EditorWindow = window.dialogArguments.window;   \n");
      out.write("        args.form = window.dialogArguments.form;\n");
      out.write("        args.definition = definitionId;\n");
      out.write("        return showModalDialog(url,args,\"dialogWidth:600px;dialogHeight:300px;help:no;scrollbars:yes;status:no;resizable:yes\");\n");
      out.write("}\n");
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
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f1 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f1.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f1.setParent(null);
    _jspx_th_html_005frewrite_005f1.setPage("/create.jsp");
    _jspx_th_html_005frewrite_005f1.setModule("/site");
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
    _jspx_th_html_005frewrite_005f2.setPage("/channel/edit.do");
    _jspx_th_html_005frewrite_005f2.setModule("/site");
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
    _jspx_th_html_005frewrite_005f3.setPage("/css/toModify.do");
    _jspx_th_html_005frewrite_005f3.setModule("/site");
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
    _jspx_th_html_005frewrite_005f4.setModule("/site/template");
    _jspx_th_html_005frewrite_005f4.setPage("/selectTemplate.do");
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
    _jspx_th_html_005frewrite_005f5.setModule("/site/template");
    _jspx_th_html_005frewrite_005f5.setPage("/selectTemplate.do");
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
    _jspx_th_html_005frewrite_005f6.setModule("/site/template");
    _jspx_th_html_005frewrite_005f6.setPage("/selectTemplate.do");
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
    _jspx_th_html_005frewrite_005f7.setPage("/open.do");
    _jspx_th_html_005frewrite_005f7.setModule("/site/channel");
    int _jspx_eval_html_005frewrite_005f7 = _jspx_th_html_005frewrite_005f7.doStartTag();
    if (_jspx_th_html_005frewrite_005f7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f7);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f7);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f8(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f8 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f8.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f8.setParent(null);
    _jspx_th_html_005frewrite_005f8.setPage("/move.do");
    _jspx_th_html_005frewrite_005f8.setModule("/site/channel");
    int _jspx_eval_html_005frewrite_005f8 = _jspx_th_html_005frewrite_005f8.doStartTag();
    if (_jspx_th_html_005frewrite_005f8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f8);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f8);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f9(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f9 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f9.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f9.setParent(null);
    _jspx_th_html_005frewrite_005f9.setPage("/permission.do");
    _jspx_th_html_005frewrite_005f9.setModule("/site/channel");
    int _jspx_eval_html_005frewrite_005f9 = _jspx_th_html_005frewrite_005f9.doStartTag();
    if (_jspx_th_html_005frewrite_005f9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f9);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f9);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f10(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f10 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f10.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f10.setParent(null);
    _jspx_th_html_005frewrite_005f10.setModule("/site/resource");
    _jspx_th_html_005frewrite_005f10.setPage("/edit.do");
    int _jspx_eval_html_005frewrite_005f10 = _jspx_th_html_005frewrite_005f10.doStartTag();
    if (_jspx_th_html_005frewrite_005f10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f10);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f10);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f11(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f11 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f11.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f11.setParent(null);
    _jspx_th_html_005frewrite_005f11.setModule("/site/template");
    _jspx_th_html_005frewrite_005f11.setPage("/edit.do?templateId=");
    int _jspx_eval_html_005frewrite_005f11 = _jspx_th_html_005frewrite_005f11.doStartTag();
    if (_jspx_th_html_005frewrite_005f11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f11);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f11);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f12(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f12 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f12.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f12.setParent(null);
    _jspx_th_html_005frewrite_005f12.setModule("/site/template");
    _jspx_th_html_005frewrite_005f12.setPage("/rename.do?templateId=");
    int _jspx_eval_html_005frewrite_005f12 = _jspx_th_html_005frewrite_005f12.doStartTag();
    if (_jspx_th_html_005frewrite_005f12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f12);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f12);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f13(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f13 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f13.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f13.setParent(null);
    _jspx_th_html_005frewrite_005f13.setModule("/site/management");
    _jspx_th_html_005frewrite_005f13.setPage("/sitesWapper.jsp");
    int _jspx_eval_html_005frewrite_005f13 = _jspx_th_html_005frewrite_005f13.doStartTag();
    if (_jspx_th_html_005frewrite_005f13.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f13);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f13);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f14(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f14 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f14.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f14.setParent(null);
    _jspx_th_html_005frewrite_005f14.setModule("/site/management");
    _jspx_th_html_005frewrite_005f14.setPage("/edit.do");
    int _jspx_eval_html_005frewrite_005f14 = _jspx_th_html_005frewrite_005f14.doStartTag();
    if (_jspx_th_html_005frewrite_005f14.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f14);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f14);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f15(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f15 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f15.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f15.setParent(null);
    _jspx_th_html_005frewrite_005f15.setPage("/xrepeaterEditorFrame.jsp");
    _jspx_th_html_005frewrite_005f15.setModule("/site/editor");
    int _jspx_eval_html_005frewrite_005f15 = _jspx_th_html_005frewrite_005f15.doStartTag();
    if (_jspx_th_html_005frewrite_005f15.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f15);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f15);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f16(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f16 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f16.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f16.setParent(null);
    _jspx_th_html_005frewrite_005f16.setPage("/tableXrepeaterEditorFrame.jsp");
    _jspx_th_html_005frewrite_005f16.setModule("/site/editor");
    int _jspx_eval_html_005frewrite_005f16 = _jspx_th_html_005frewrite_005f16.doStartTag();
    if (_jspx_th_html_005frewrite_005f16.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f16);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f16);
    return false;
  }
}
