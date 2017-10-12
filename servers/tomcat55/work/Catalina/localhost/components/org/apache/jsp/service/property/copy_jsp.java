package org.apache.jsp.service.property;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class copy_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(4);
    _jspx_dependants.add("/WEB-INF/struts-bean.tld");
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
    _jspx_dependants.add("/WEB-INF/struts-tiles.tld");
    _jspx_dependants.add("/WEB-INF/struts-logic.tld");
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

      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("<title>设置复制属性</title>\r\n");
      out.write("<base target=\"_self\" />\r\n");
      out.write("<meta http-equiv=Content-Type content=\"text/html; charset=UTF-8\" />\r\n");
      out.write("<meta http-equiv=\"Cache-Control\" content=\"no-cache, must-revalidate\" />\r\n");
      out.write("<meta http-equiv=\"expires\" content=\"Wed, 26 Feb 1997 08:21:57 GMT\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/ide/service/styles.css\"/>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"/ide/common/script/common.js.jsp\"></script>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"/ide/service/scripts.js.jsp\"></script>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"/ide/cms/classes/cmsdialog.js\"></script>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"/ide/common/script/xmlhttp.js\"></script>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"/ide/common/script/ajax.js\"></script>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\">\r\n");
      out.write("var config = window.dialogArguments.parameters;\r\n");
      out.write("var definitionID = window.dialogArguments.definitionID;\r\n");
      out.write("function doInit(){\r\n");
      out.write("\tvar property = config.getValue(\"sourcePropID\");\r\n");
      out.write("\tif(property!=null){\r\n");
      out.write("\t\tdocument.all(\"sourcePropID\").value = property;\r\n");
      out.write("\t\tdocument.all(\"sourcePropName\").value = CMSDialog.GetPropertyDefinitionName(definitionID, property);\r\n");
      out.write("\t}\r\n");
      out.write("\tproperty = config.getValue(\"destinationPropID\");\r\n");
      out.write("\tif(property!=null){\r\n");
      out.write("\t\tdocument.all(\"destinationPropID\").value = property;\r\n");
      out.write("\t\tdocument.all(\"destinationPropName\").value = CMSDialog.GetPropertyDefinitionName(definitionID, property);\r\n");
      out.write("\t}\t\r\n");
      out.write("\tvar useID = (\"true\" == config.getValue(\"IDprop\"));\r\n");
      out.write("\tdocument.all(\"IDprop\").checked = useID;\r\n");
      out.write("\tdisablePropSelect(useID);\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function ok(){\r\n");
      out.write("\tconfig.setValue(\"sourcePropID\",document.all(\"sourcePropID\").value);\t \r\n");
      out.write("\tconfig.setValue(\"destinationPropID\",document.all(\"destinationPropID\").value);\r\n");
      out.write("\tconfig.setValue(\"IDprop\",\"\"+document.all(\"IDprop\").checked ); \r\n");
      out.write("\twindow.returnValue = config;\r\n");
      out.write("\twindow.close();  \r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function doSelectSourceProperty(){\r\n");
      out.write("\tvar ret = CMSDialog.PropertyDefinitionSelector(definitionID);\r\n");
      out.write("\tif(ret!=null){\r\n");
      out.write("\t\tdocument.all(\"sourcePropID\").value = ret.ID;\r\n");
      out.write("\t\tdocument.all(\"sourcePropName\").value = ret.name;\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("function doSelectDestProperty(){\r\n");
      out.write("\tvar ret = CMSDialog.PropertyDefinitionSelector(definitionID);\r\n");
      out.write("\tif(ret!=null){\r\n");
      out.write("\t\tdocument.all(\"destinationPropID\").value = ret.ID;\r\n");
      out.write("\t\tdocument.all(\"destinationPropName\").value = ret.name;\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("function disablePropSelect(disabled){\r\n");
      out.write("\tdocument.all(\"btnDest\").disabled = disabled;\r\n");
      out.write("\tdocument.all(\"destinationPropName\").disabled = disabled;\r\n");
      out.write("}\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body onload=\"doInit()\">\r\n");
      out.write("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td class=\"pannelDiv\" valign=\"top\"><select id=\"pannelSelect\" name=\"pannelSelect\" onchange=\"Service.SelectPanel(this)\" size=\"10\">\r\n");
      out.write("\t\t\t<option value=\"0\">基本信息</option>\r\n");
      out.write("\t\t</select></td>\r\n");
      out.write("\t\t<td id=\"tdFieldsets\" valign=\"top\" align=\"center\">\r\n");
      out.write("\t\t<fieldset class=\"fieldPanel\">\r\n");
      out.write("\t\t<table cellpadding=\"2\" cellspacing=\"0\" border=\"0\">\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>源属性</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"hidden\" name=\"sourcePropID\" /><input type=\"text\" name=\"sourcePropName\" readonly=\"readonly\" /></td>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t<button id=\"sourceButton\" onclick=\"doSelectSourceProperty(definitionID,document.getElementById('sourcePropID'),document.getElementById('sourcePropName'))\">选择...</button>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td></td>\r\n");
      out.write("\t\t\t\t<td><input type=\"checkbox\" name=\"IDprop\" onclick=\"disablePropSelect(this.checked)\"><label for=\"IDprop\">使用内容ID</label></td>\r\n");
      out.write("\t\t\t\t<td></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>目标属性</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"hidden\" name=\"destinationPropID\" /><input type=\"text\" name=\"destinationPropName\"  readonly=\"readonly\"/></td>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t<button id=\"btnDest\" onclick=\"doSelectDestProperty(definitionID,document.getElementById('destinationPropID'),document.getElementById('destinationPropName'))\">选择...</button>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t</fieldset>\r\n");
      out.write("\t\t<div id=\"toolbar\">\r\n");
      out.write("\t\t<button onclick=\"ok()\" id=\"btnOK\">确定</button>\r\n");
      out.write("\t\t<button onclick=\"window.close()\" id=\"btnCancel\">取消</button>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\t\r\n");
      out.write("</table>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
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
