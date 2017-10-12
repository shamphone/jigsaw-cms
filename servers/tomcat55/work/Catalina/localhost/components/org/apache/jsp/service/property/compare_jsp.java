package org.apache.jsp.service.property;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class compare_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(4);
    _jspx_dependants.add("/WEB-INF/struts-bean.tld");
    _jspx_dependants.add("/WEB-INF/struts-logic.tld");
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
    _jspx_dependants.add("/WEB-INF/struts-tiles.tld");
  }

  public Object getDependants() {
    return _jspx_dependants;
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
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>日期属性值比较</title>\r\n");
      out.write("<base target=\"_self\" />\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n");
      out.write("<meta http-equiv=\"Cache-Control\" content=\"no-cache, must-revalidate\" />\r\n");
      out.write("<meta http-equiv=\"expires\" content=\"Wed, 26 Feb 1997 08:21:57 GMT\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/ide/service/styles.css\"/>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"/ide/common/script/common.js.jsp\"></script>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"/ide/service/scripts.js.jsp\"></script>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"/ide/cms/classes/cmsdialog.js\"></script>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"/ide/common/script/xmlhttp.js\"></script>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"/ide/common/script/ajax.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" language=\"javascript\">\r\n");
      out.write("\t\tvar config = window.dialogArguments.parameters;\r\n");
      out.write("\t\tfunction doInit(){\r\n");
      out.write("\t\t\tconfig.populateForm(configForm);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tfunction ok(){\r\n");
      out.write("\t\t\t/**\r\n");
      out.write("\t\t\t * luobin modified in 2010-3-17\r\n");
      out.write("\t\t\t * bug\r\n");
      out.write("\t\t\t * 修改目的：刷新间隔（小时）没有输入限制。\r\n");
      out.write("\t\t\t * 修改描述：刷新间隔（小时）只能是正数\r\n");
      out.write("\t\t\t */\r\n");
      out.write("\t\t\tif(!validatePositive(configForm.time.value)){\r\n");
      out.write("\t\t\t\talert(\"刷新间隔必须是正数！\");\r\n");
      out.write("\t\t\t\treturn;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\tconfig.updateFromForm(configForm);\r\n");
      out.write("\t\t\twindow.returnValue = config;\r\n");
      out.write("\t\t\twindow.close();   \r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tfunction selectCategory(objID,objName){\r\n");
      out.write("\t\t\t  var definition=CMSDialog.NodeDefinitionSelector();\r\n");
      out.write("\t\t\t\tif(definition!=null){\r\n");
      out.write("\t\t\t   if(definition.length>0){\r\n");
      out.write("\t\t\t       for(var j=0;j<definition.length;j++){\r\n");
      out.write("\t\t\t    \t   objID.value=definition[j].ID;\r\n");
      out.write("\t\t\t    \t   objName.value=definition[j].name;\r\n");
      out.write("\t\t\t       }\r\n");
      out.write("\t\t\t     }\r\n");
      out.write("\t\t\t  }\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\tfunction validatePositive(time){\r\n");
      out.write("\t\t\tvar TIME_PATTERN = /^\\d+(\\.\\d+)?$/;\r\n");
      out.write("\t\t\tif(!TIME_PATTERN.test(time)){\r\n");
      out.write("\t\t\t\treturn false;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\treturn true;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tfunction selectProperty(categoryID,objID,objName,type){\r\n");
      out.write("\t\t\tif(categoryID==null){\r\n");
      out.write("\t\t\t\talert(\"请选择分类！\");\r\n");
      out.write("\t\t\t\treturn;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t    var result=CMSDialog.PropertyDefinitionSelector(categoryID,'',type);\r\n");
      out.write("\t\t\t    if(result!=null){\r\n");
      out.write("\t\t\t    \tobjID.value=result.ID;\r\n");
      out.write("\t\t\t    \tobjName.value=result.name;\r\n");
      out.write("\t\t\t      }\r\n");
      out.write("\t\t}\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body onload=\"doInit()\">\r\n");
      out.write("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td class=\"pannelDiv\" valign=\"top\"><select id=\"pannelSelect\" name=\"pannelSelect\" onchange=\"Service.SelectPanel(this)\" size=\"10\">\r\n");
      out.write("\t\t\t\t\t<option value=\"0\">基本信息</option>\r\n");
      out.write("\t\t\t</select></td>\r\n");
      out.write("\t\t\t<td id=\"tdFieldsets\" valign=\"top\" align=\"center\">\r\n");
      out.write("\t\t\t<form name=\"configForm\">\r\n");
      out.write("\t\t\t<fieldset class=\"fieldPanel\">\r\n");
      out.write("\t\t\t<div\r\n");
      out.write("\t\t\t\tstyle=\"height: 200px;\">\r\n");
      out.write("\t\t\t\t<table>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t<td>内容分类<td>\r\n");
      out.write("\t\t\t<td>\r\n");
      out.write("\t\t\t\t<input type=\"hidden\" name=\"compareCategoryID\" />\r\n");
      out.write("\t\t\t    <input type=\"text\" name=\"compareCategoryName\" readonly=\"readonly\" />\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t<button onclick=\"selectCategory(document.getElementById('compareCategoryID'),document.getElementById('compareCategoryName'))\">选择...</button>\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t<td>属性1<td>\r\n");
      out.write("\t\t\t<td>\r\n");
      out.write("\t\t\t\t<input type=\"hidden\" name=\"prop1ID\" />\r\n");
      out.write("\t\t\t    <input type=\"text\" name=\"prop1Name\" readonly=\"readonly\" />\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t<button onclick=\"selectProperty(document.getElementById('compareCategoryID').value,document.getElementById('prop1ID'),document.getElementById('prop1Name'),['5'])\">选择...</button>\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t<td>属性2<td>\r\n");
      out.write("\t\t\t<td>\r\n");
      out.write("\t\t\t\t<input type=\"hidden\" name=\"prop2ID\" />\r\n");
      out.write("\t\t\t    <input type=\"text\" name=\"prop2Name\" readonly=\"readonly\" />\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t<button onclick=\"selectProperty(document.getElementById('compareCategoryID').value,document.getElementById('prop2ID'),document.getElementById('prop2Name'),['5'])\">选择...</button>\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t<td>比较结果属性<td>\r\n");
      out.write("\t\t\t<td>\r\n");
      out.write("\t\t\t\t<input type=\"hidden\" name=\"resultPropID\" />\r\n");
      out.write("\t\t\t    <input type=\"text\" name=\"resultPropName\" readonly=\"readonly\" />\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t<button onclick=\"selectProperty(document.getElementById('compareCategoryID').value,document.getElementById('resultPropID'),document.getElementById('resultPropName'))\">选择...</button>\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t<td>结果显示<td>\r\n");
      out.write("\t\t\t<td>\r\n");
      out.write("\t\t\t    <input type=\"text\" name=\"resultMore\" />\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t属性1>属性2\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t<td>结果显示<td>\r\n");
      out.write("\t\t\t<td>\r\n");
      out.write("\t\t\t\t<input type=\"text\" name=\"resultLess\" />\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t属性1<属性2\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t<td>差值属性<td>\r\n");
      out.write("\t\t\t<td>\r\n");
      out.write("\t\t\t\t<input type=\"hidden\" name=\"reducePropID\" />\r\n");
      out.write("\t\t\t    <input type=\"text\" name=\"reducePropName\" readonly=\"readonly\" />\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t<button onclick=\"selectProperty(document.getElementById('compareCategoryID').value,document.getElementById('reducePropID'),document.getElementById('reducePropName'))\">选择...</button>\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t<td>刷新间隔(小时)<td>\r\n");
      out.write("\t\t\t<td>\r\n");
      out.write("\t\t\t<input type=\"text\" name=\"time\" />\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</fieldset>\r\n");
      out.write("\t\t\t</form>\r\n");
      out.write("\t\t\t<div id=\"toolbar\">\r\n");
      out.write("\t\t    <button onclick=\"ok()\" id=\"btnOK\">确定</button>\r\n");
      out.write("\t\t    <button onclick=\"window.close()\" id=\"btnCancel\">取消</button>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
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
}
