package org.apache.jsp.service;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class customerSync_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<title>银河培训学员课程同步服务</title>\r\n");
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
      out.write("\tvar config = window.dialogArguments.parameters;\r\n");
      out.write("\tfunction doInit() {\r\n");
      out.write("\t\tconfig.populateForm(configForm);\r\n");
      out.write("\t}\r\n");
      out.write("\tfunction ok() {\r\n");
      out.write("\t\tconfig.updateFromForm(configForm);\r\n");
      out.write("\t\twindow.returnValue = config;\r\n");
      out.write("\t\twindow.close();\r\n");
      out.write("\t}\r\n");
      out.write("\tfunction selectCategory(objID, objName) {\r\n");
      out.write("\t\tvar definition = CMSDialog.NodeDefinitionSelector();\r\n");
      out.write("\t\tif (definition != null) {\r\n");
      out.write("\t\t\tif (definition.length > 0) {\r\n");
      out.write("\t\t\t\tfor ( var j = 0; j < definition.length; j++) {\r\n");
      out.write("\t\t\t\t\tobjID.value = definition[j].ID;\r\n");
      out.write("\t\t\t\t\tobjName.value = definition[j].name;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\tfunction selectProperty(categoryID, objID, objName, type) {\r\n");
      out.write("\t\tif (categoryID == null) {\r\n");
      out.write("\t\t\talert(\"请选择分类！\");\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tvar result = CMSDialog.PropertyDefinitionSelector(categoryID, '', type);\r\n");
      out.write("\t\tif (result != null) {\r\n");
      out.write("\t\t\tobjID.value = result.ID;\r\n");
      out.write("\t\t\tobjName.value = result.name;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body onload=\"doInit()\">\r\n");
      out.write("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td class=\"pannelDiv\" valign=\"top\"><select id=\"pannelSelect\"\r\n");
      out.write("\t\t\tname=\"pannelSelect\" onchange=\"Service.SelectPanel(this)\" size=\"10\">\r\n");
      out.write("\t\t\t<option value=\"0\">基本信息</option>\r\n");
      out.write("\t\t</select></td>\r\n");
      out.write("\t\t<td id=\"tdFieldsets\" valign=\"top\" align=\"center\">\r\n");
      out.write("\t\t<form name=\"configForm\">\r\n");
      out.write("\t\t<fieldset class=\"fieldPanel\">\r\n");
      out.write("\t\t<div style=\"height: 200px;\">\r\n");
      out.write("\t\t<table>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>学员分类</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"hidden\" id=\"studentCategoryID\"\r\n");
      out.write("\t\t\t\t\tname=\"studentCategoryID\" /> <input type=\"text\"\r\n");
      out.write("\t\t\t\t\tid=\"studentCategoryName\" name=\"studentCategoryName\"\r\n");
      out.write("\t\t\t\t\treadonly=\"readonly\" /></td>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t<button\r\n");
      out.write("\t\t\t\t\tonclick=\"selectCategory(document.getElementById('studentCategoryID'),document.getElementById('studentCategoryName'))\">选择...</button>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>课程属性</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"hidden\" id=\"coursePropID\" name=\"coursePropID\" />\r\n");
      out.write("\t\t\t\t<input type=\"text\" id=\"coursePropName\" name=\"coursePropName\"\r\n");
      out.write("\t\t\t\t\treadonly=\"readonly\" /></td>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t<button\r\n");
      out.write("\t\t\t\t\tonclick=\"selectProperty(document.getElementById('studentCategoryID').value,document.getElementById('coursePropID'),document.getElementById('coursePropName'),['9'])\">选择...</button>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>课程分类</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"hidden\" id=\"courseCategoryID\"\r\n");
      out.write("\t\t\t\t\tname=\"courseCategoryID\" /> <input type=\"text\"\r\n");
      out.write("\t\t\t\t\tid=\"courseCategoryName\" name=\"courseCategoryName\"\r\n");
      out.write("\t\t\t\t\treadonly=\"readonly\" /></td>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t<button onclick=\"selectCategory(document.getElementById('courseCategoryID'),document.getElementById('courseCategoryName'))\">选择...</button>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>学员属性</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"hidden\" id=\"studentPropID\" name=\"studentPropID\" />\r\n");
      out.write("\t\t\t\t<input type=\"text\" id=\"studentPropName\" name=\"studentPropName\"\r\n");
      out.write("\t\t\t\t\treadonly=\"readonly\" /></td>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t<button\r\n");
      out.write("\t\t\t\t\tonclick=\"selectProperty(document.getElementById('courseCategoryID').value,document.getElementById('studentPropID'),document.getElementById('studentPropName'),['9'])\">选择...</button>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t</fieldset>\r\n");
      out.write("\t\t</form>\r\n");
      out.write("\t\t<div id=\"toolbar\">\r\n");
      out.write("\t\t\t<button onclick=\"ok()\" id=\"btnOK\">确定</button>\r\n");
      out.write("\t\t\t<button onclick=\"window.close()\" id=\"btnCancel\">取消</button>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
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
