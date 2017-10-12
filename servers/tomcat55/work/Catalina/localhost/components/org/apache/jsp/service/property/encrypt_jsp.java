package org.apache.jsp.service.property;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class encrypt_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<title>请选择待加密的属性</title>\r\n");
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
      out.write("function doInit(){\r\n");
      out.write("\tvar property = config.getValue(\"property\");\r\n");
      out.write("\tif(property!=null){\r\n");
      out.write("\t\tdocument.all(\"property\").value = property;\r\n");
      out.write("\t\tdocument.all(\"propertyName\").value = CMSDialog.GetPropertyDefinitionName(window.dialogArguments.definitionID, property);\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function ok(){\r\n");
      out.write("\tconfig.setValue(\"property\",document.all(\"property\").value); \r\n");
      out.write("\twindow.returnValue = config;\r\n");
      out.write("\twindow.close();  \r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function doSelectProperty(){\r\n");
      out.write("\tvar ret = CMSDialog.PropertyDefinitionSelector(window.dialogArguments.definitionID,window.dialogArguments.definitionID);\r\n");
      out.write("\tif(ret!=null){\r\n");
      out.write("\t\tdocument.all(\"property\").value = ret.ID;\r\n");
      out.write("\t\tdocument.all(\"propertyName\").value = ret.name;\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body onload=\"doInit()\">\r\n");
      out.write("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td class=\"pannelDiv\" valign=\"top\"><select id=\"pannelSelect\" name=\"pannelSelect\" onchange=\"Service.SelectPanel(this)\" size=\"10\">\r\n");
      out.write("\t\t\t<option value=\"0\">基本设置</option>\r\n");
      out.write("\t\t</select></td>\r\n");
      out.write("\t\t<td id=\"tdFieldsets\" valign=\"top\" align=\"center\">\r\n");
      out.write("\t\t<fieldset class=\"fieldPanel\">\r\n");
      out.write("\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\"><input type=\"hidden\" name=\"property\" />\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t<td>加密属性</td>\r\n");
      out.write("\t\t<td><input type=\"text\" name=\"propertyName\" readonly=\"readonly\" size=\"30\" /></td>\r\n");
      out.write("\t\t<td><button class=\"commonbut\" id=\"search\" onclick='doSelectProperty()'>选择...</button></td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t</table>\t \t\t\r\n");
      out.write("\t\t</fieldset><div id=\"toolbar\">\r\n");
      out.write("\t\t<button onclick=\"ok()\" id=\"btnOK\">确定</button>\r\n");
      out.write("\t\t<button onclick=\"window.close()\" id=\"btnCancel\">取消</button>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
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
