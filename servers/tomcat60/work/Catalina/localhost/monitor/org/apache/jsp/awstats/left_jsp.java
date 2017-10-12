package org.apache.jsp.awstats;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class left_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(4);
    _jspx_dependants.add("/WEB-INF/struts-bean.tld");
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
    _jspx_dependants.add("/WEB-INF/struts-logic.tld");
    _jspx_dependants.add("/WEB-INF/struts-tiles.tld");
  }

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
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
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n");
      out.write("<html lang='cn'>\r\n");
      out.write("<head>\r\n");
      out.write("<meta name=\"robots\" content=\"noindex,nofollow\">\r\n");
      out.write("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("<title>统计网站 ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.serverName}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write(" (2009-09) - left</title>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/ide/common/style/left.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/ide/common/xtree/xtree.css\" />\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"/ide/common/xtree/xtree.js\"></script>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"/ide/common/xtree/tree.js.jsp\"></script>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"/ide/common/script/ajax.js\"></script>\r\n");
      out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"/ide/common/module/modules.js.jsp\"></script>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("function doInit(){\r\n");
      out.write("\tvar oUL = document.all(\"tabnav\");\r\n");
      out.write("\tvar tree = WebFXTree_ConvertUL(oUL);\r\n");
      out.write("\ttree.text = \"统计网站");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.serverName}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\";\r\n");
      out.write("\tdocument.all(\"navcontainer\").innerHTML=tree.toString();\r\n");
      out.write("\tCModuleCollection.render(document.all(\"modules\"),\"log\");\r\n");
      out.write("}\r\n");
      out.write("</script>\r\n");
      out.write("<base target=\"mainright\" />\r\n");
      out.write("</head>\r\n");
      out.write("<body onload=\"doInit()\">\r\n");
      out.write("<div id=\"blockTitle\">访问量分析</div>\r\n");
      out.write("<div id=\"navcontainer\">\r\n");
      out.write("<ul id=\"tabnav\">\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright#top\" target=\"mainright\">摘要</a></li>\r\n");
      out.write("<li>按参观时间<ul>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright#month\" target=\"mainright\">按月历史统计</a></li>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright#daysofmonth\" target=\"mainright\">按日期统计</a></li>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright#daysofweek\" target=\"mainright\">按星期</a></li>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright#hours\" target=\"mainright\">每小时浏览次数</a></li></ul></li>\r\n");
      out.write("<li>按参观者<ul>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright&amp;output=alldomains\" target=\"mainright\">国家或地区</a></li>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright&amp;output=allhosts\" target=\"mainright\">主机</a></li>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright&amp;output=lasthosts\" target=\"mainright\">最近参观日期</a></li>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright&amp;output=unknownip\" target=\"mainright\">无法反解译的IP地址</a></li>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright&amp;output=allrobots\" target=\"mainright\">搜索引擎网站的机器人</a></li>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright&amp;output=lastrobots\" target=\"mainright\">最近参观日期</a></li></ul></li>\r\n");
      out.write("<li>浏览器统计<ul>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright#sessions\" target=\"mainright\">每次参观所花时间</a></li>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright#filetypes\" target=\"mainright\">文件类别</a></li>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright&amp;output=urldetail\" target=\"mainright\">存取次数</a></li>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright&amp;output=urlentry\" target=\"mainright\">入站处</a></li>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright&amp;output=urlexit\" target=\"mainright\">出站处</a></li>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright#os\" target=\"mainright\">操作系统</a></li>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright&amp;output=osdetail\" target=\"mainright\">版本</a></li>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright&amp;output=unknownos\" target=\"mainright\">无法得知</a></li>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright#browsers\" target=\"mainright\">浏览器</a></li>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright&amp;output=browserdetail\" target=\"mainright\">版本</a></li>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright&amp;output=unknownbrowser\" target=\"mainright\">无法得知</a></li></ul></li>\r\n");
      out.write("<li>反相链接<ul>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright#referer\" target=\"mainright\">来源网址</a></li>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright&amp;output=refererse\" target=\"mainright\">由那些搜索引擎转介</a></li>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright&amp;output=refererpages\" target=\"mainright\">由那些其他网站转介</a></li>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright#keys\" target=\"mainright\">搜索</a></li>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright&amp;output=keyphrases\" target=\"mainright\">用以搜索的短语</a></li>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright&amp;output=keywords\" target=\"mainright\">用以搜索的关键词</a></li></ul></li>\r\n");
      out.write("<li>其他<ul>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright#misc\" target=\"mainright\">其他</a></li>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright#errors\" target=\"mainright\">HTTP 错误码</a></li>\r\n");
      out.write("<li><a href=\"../cgi-bin/awstats.pl?framename=mainright&amp;output=errors404\" target=\"mainright\">找不到的网页</a></li></ul></li></ul></div>\r\n");
      out.write("<div id=\"modules\" align=\"right\"></div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
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
}
