package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class word_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

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
      response.setContentType("text/html; charset=ISO-8859-1");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<html>\r\n");
      out.write("<head><title>DsoFramer Control Test Page (Q311765)</title>\r\n");
      out.write("\r\n");
      out.write("<style>\r\n");
      out.write(".fontSize1 {font-size: 65%; \tfont-family: Verdana, Geneva, Arial, Helvetica, sans-serif;}\r\n");
      out.write(".fontSize2 {font-size: 70%; \tfont-family: Verdana, Geneva, Arial, Helvetica, sans-serif;}\r\n");
      out.write(".fontSize3 {font-size: 75%; \tfont-family: Verdana, Geneva, Arial, Helvetica, sans-serif;}\r\n");
      out.write(".fontSize4 {font-size: 80%; \tfont-family: Verdana, Geneva, Arial, Helvetica, sans-serif;}\r\n");
      out.write(".fontSize5 {font-size: 125%; \tfont-family: Verdana, Geneva, Arial, Helvetica, sans-serif;}\r\n");
      out.write("\r\n");
      out.write(".fakehlink {cursor: hand; text-decoration: underline; color: #0066CC; font-weight:normal;}\r\n");
      out.write("\r\n");
      out.write("</style>\r\n");
      out.write("<script language=\"javascript\">\r\n");
      out.write(" function doInit(){\r\n");
      out.write("\t oframe.Open(\"http://www.lixf.cn/portal/resources/abc/123.doc\");\r\n");
      out.write("\t oframe.Titlebar=false;\r\n");
      out.write("\t oframe.SetTrackRevisions(1);\r\n");
      out.write("\t \r\n");
      out.write(" }\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body bgcolor=\"#ffffff\" topmargin=\"0\" leftmargin=\"0\" marginheight=\"0\" marginwidth=\"0\" onload=\"doInit()\">\r\n");
      out.write("\r\n");
      out.write("<!-- web page script -->\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!--titlebar start-->\r\n");
      out.write("\r\n");
      out.write("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#FFCC00\">\r\n");
      out.write(" <tr>\r\n");
      out.write("    <td bgcolor=\"#FFCC00\" valign=\"top\" width=\"250\" rowspan=\"2\"><img src=\"officelogo.gif\" alt=\"Microsoft(R) Office\" border=\"0\" /></td>\r\n");
      out.write("    <td bgcolor=\"#FFCC00\" valign=\"middle\" height=\"20\" align=\"right\" class=\"fontSize1\" nowrap=\"true\">DSO Framer ActiveX Document Control Sample &nbsp;</td>\r\n");
      out.write(" </tr>\r\n");
      out.write(" <tr><td bgcolor=\"#FFCC00\" valign=\"top\" height=\"40\" align=\"right\"><a href=\"http://www.microsoft.com/\"><img src=\"mslogo.gif\" border=\"0\" alt=\"microsoft.com\"></a></td></tr>\r\n");
      out.write(" <tr><td bgcolor=\"#FFFFFF\" valign=\"top\" height=\"2\"></td><td bgcolor=\"#FFFFFF\" valign=\"top\" height=\"2\"></td></tr>\r\n");
      out.write(" <tr><td bgcolor=\"#FFCC00\" valign=\"top\" height=\"2\"></td><td bgcolor=\"#FFCC00\" valign=\"top\" height=\"2\"></td></tr>\r\n");
      out.write(" <tr><td bgcolor=\"#FFFFFF\" valign=\"top\" height=\"5\"></td><td bgcolor=\"#FFFFFF\" valign=\"top\" height=\"5\"></td></tr>\r\n");
      out.write("</table>\r\n");
      out.write("\r\n");
      out.write("<!--titlebar end-->\r\n");
      out.write("\r\n");
      out.write("<!--main body start-->\r\n");
      out.write("\r\n");
      out.write("<table width=\"100%\" height=\"87%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n");
      out.write("<tr>\r\n");
      out.write(" <td valign=\"top\" width=\"10\"></td>\r\n");
      out.write(" <td valign=\"top\" width=\"175\">\r\n");
      out.write("\r\n");
      out.write("<!--left-side start-->\r\n");
      out.write("\r\n");
      out.write("  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"175\" bgcolor=\"#f1f1f1\">\r\n");
      out.write("\r\n");
      out.write("   <!-- Load a Document -->\r\n");
      out.write("    <tr>\r\n");
      out.write("      <td valign=\"top\" bgcolor=\"#FFCC00\" colspan=\"2\"><img src=\"lefttopcurve.gif\" width=\"10\" height=\"10\"></td>\r\n");
      out.write("      <td valign=\"middle\" bgcolor=\"#FFCC00\" class=\"fontSize1\" width=\"155\" rowspan=\"2\"><B>Load a Document</B></td>\r\n");
      out.write("      <td valign=\"top\" bgcolor=\"#FFCC00\" colspan=\"2\"><img src=\"righttopcurve.gif\" width=\"10\" height=\"10\"></td>\r\n");
      out.write("    </tr>\r\n");
      out.write("    <tr>\r\n");
      out.write("      <td width=\"1\" bgcolor=\"#D6D3D6\"></td><td bgcolor=\"#FFCC00\" width=\"9\" height=\"9\"></td>\r\n");
      out.write("      <td bgcolor=\"#FFCC00\" width=\"9\" height=\"10\"></td><td width=\"1\" bgcolor=\"#D6D3D6\"></td>\r\n");
      out.write("    </tr>\r\n");
      out.write("    <tr>\r\n");
      out.write("      <td width=\"1\" bgcolor=\"#D6D3D6\"></td>\r\n");
      out.write("      <td width=\"9\"></td>\r\n");
      out.write("      <td class=\"fontSize1\" width=\"155\"><br>\r\n");
      out.write("        <div class=\"fakehlink\" onClick=\"NewDoc\">Create a New Document</div><br>\r\n");
      out.write("        <div class=\"fakehlink\" onClick=\"OpenDoc\">Open a Document</div><br>\r\n");
      out.write("        <div class=\"fakehlink\" onClick=\"OpenWebDoc\">Open a Web Document</div><br>\r\n");
      out.write("      </td>\r\n");
      out.write("      <td width=\"9\"></td>\r\n");
      out.write("      <td width=\"1\" bgcolor=\"#D6D3D6\"></td>\r\n");
      out.write("    </tr>\r\n");
      out.write("\r\n");
      out.write("   <!-- Perform Document Task -->\r\n");
      out.write("    <tr>\r\n");
      out.write("      <td width=\"1\" bgcolor=\"#D6D3D6\"></td><td width=\"9\" height=\"20\" bgcolor=\"#FFCC00\"></td>\r\n");
      out.write("      <td valign=\"middle\" bgcolor=\"#FFCC00\" class=\"fontSize1\" width=\"155\"><B>Perform Document Task</B></td>\r\n");
      out.write("      <td width=\"9\" height=\"20\" bgcolor=\"#FFCC00\"></td><td width=\"1\" bgcolor=\"#D6D3D6\"></td>\r\n");
      out.write("    </tr>\r\n");
      out.write("    <tr>\r\n");
      out.write("      <td width=\"1\" bgcolor=\"#D6D3D6\"></td>\r\n");
      out.write("      <td width=\"9\"></td>\r\n");
      out.write("      <td class=\"fontSize1\" width=\"155\"><br>\r\n");
      out.write("        <div class=\"fakehlink\" onClick=\"SaveCopyDoc\">Save a Copy</div><br>\r\n");
      out.write("        <div class=\"fakehlink\" onClick=\"ChgLayout\">Change Page Layout</div><br>\r\n");
      out.write("        <div class=\"fakehlink\" onClick=\"PrintDoc\">Print the Document</div><br>\r\n");
      out.write("        <div class=\"fakehlink\" onClick=\"CloseDoc\">Close the Document</div><br>\r\n");
      out.write("      </td>\r\n");
      out.write("      <td width=\"9\"></td>\r\n");
      out.write("      <td width=\"1\" bgcolor=\"#D6D3D6\"></td>\r\n");
      out.write("    </tr>\r\n");
      out.write("\r\n");
      out.write("   <!-- Control Appearance -->\r\n");
      out.write("    <tr>\r\n");
      out.write("      <td width=\"1\" bgcolor=\"#D6D3D6\"></td><td width=\"9\" height=\"20\" bgcolor=\"#FFCC00\"></td>\r\n");
      out.write("      <td valign=\"middle\" bgcolor=\"#FFCC00\" class=\"fontSize1\" width=\"155\"><B>Control Appearance</B></td>\r\n");
      out.write("      <td width=\"9\" height=\"20\" bgcolor=\"#FFCC00\"></td><td width=\"1\" bgcolor=\"#D6D3D6\"></td>\r\n");
      out.write("    </tr>\r\n");
      out.write("    <tr>\r\n");
      out.write("      <td width=\"1\" bgcolor=\"#D6D3D6\"></td>\r\n");
      out.write("      <td width=\"9\"></td>\r\n");
      out.write("      <td class=\"fontSize1\" width=\"155\"><br>\r\n");
      out.write("        <div class=\"fakehlink\" onClick=\"ToggleTitlebar\">Show/Hide Titlebar</div><br>\r\n");
      out.write("        <div class=\"fakehlink\" onClick=\"ToggleToolbars\">Show/Hide Toolbars</div><br>\r\n");
      out.write("      </td>\r\n");
      out.write("      <td width=\"9\"></td>\r\n");
      out.write("      <td width=\"1\" bgcolor=\"#D6D3D6\"></td>\r\n");
      out.write("    </tr>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("    <tr><td colspan=\"5\"><img src=\"bottomcurve.gif\" width=\"175\" height=\"10\"></td></tr>\r\n");
      out.write("  </table>\r\n");
      out.write("\r\n");
      out.write("  <p class=\"fontSize1\"><span id=\"tstat\">Click on the document icon on the titlebar to show the file menu, or use the commands above to perform an action.</span>\r\n");
      out.write("\r\n");
      out.write("<!--left-side end-->\r\n");
      out.write("\r\n");
      out.write(" </td>\r\n");
      out.write(" <td valign=\"top\" width=\"10\"></td>\r\n");
      out.write(" <td valign=\"top\">\r\n");
      out.write("\r\n");
      out.write("<!--right-side start-->\r\n");
      out.write("\r\n");
      out.write("  <table width=\"100%\" height=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\r\n");
      out.write("    <tr>\r\n");
      out.write("     <td valign=\"top\"></td>\r\n");
      out.write("     <td valign=\"top\" colspan=\"2\">\r\n");
      out.write("       <object classid=\"clsid:00460182-9E5E-11d5-B7C8-B8269041DD57\" id=\"oframe\" width=\"100%\" height=\"100%\" codebase=\"http://www.lixf.cn/component/portlet/field/word/dsoframer.ocx\">\r\n");
      out.write("         <param name=\"BorderStyle\" value=\"1\">\r\n");
      out.write("         <param name=\"TitlebarColor\" value=\"52479\">\r\n");
      out.write("         <param name=\"TitlebarTextColor\" value=\"0\">  \r\n");
      out.write("       </object>\r\n");
      out.write("     </td>\r\n");
      out.write("    </tr>\r\n");
      out.write("    <tr>\r\n");
      out.write("     <td valign=\"top\" height=\"15\"></td>\r\n");
      out.write("     <td valign=\"bottom\" class=\"fontSize1\" colspan=\"2\">Copyright  2001 Microsoft Corporation. All rights reserved.</td>\r\n");
      out.write("    </tr>\r\n");
      out.write("  </table>\r\n");
      out.write("\r\n");
      out.write("<!--right-side end-->\r\n");
      out.write("\r\n");
      out.write(" </td>\r\n");
      out.write(" <td valign=\"top\" width=\"10\"></td>\r\n");
      out.write("</tr>\r\n");
      out.write("</table>\r\n");
      out.write("\r\n");
      out.write("<!--main body end-->\r\n");
      out.write("\r\n");
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
