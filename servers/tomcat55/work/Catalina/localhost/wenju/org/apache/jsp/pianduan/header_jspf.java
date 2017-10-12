package org.apache.jsp.pianduan;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class header_jspf extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  public String getServletInfo() {
    return "头部页面片段";
  }

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(6);
    _jspx_dependants.add("/WEB-INF/fulong-portal.tld");
    _jspx_dependants.add("/WEB-INF/fulong-site.tld");
    _jspx_dependants.add("/WEB-INF/struts-bean.tld");
    _jspx_dependants.add("/WEB-INF/struts-logic.tld");
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
    _jspx_dependants.add("/WEB-INF/struts-tiles.tld");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fsite_005fscript_0026_005fportlets_005fnobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fsite_005fscript_0026_005fportlets_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fsite_005fscript_0026_005fportlets_005fnobody.release();
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
      response.setContentType("text/html; charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      if (_jspx_meth_site_005fscript_005f0(_jspx_page_context))
        return;
      out.write("<style type=\"text/css\"> \r\n");
      out.write(".white_content { \r\n");
      out.write("display: none; \r\n");
      out.write("position: absolute; \r\n");
      out.write("top: 37%; \r\n");
      out.write("left: 50%; \r\n");
      out.write("width: 302px;; \r\n");
      out.write("height: auto;  \r\n");
      out.write("background-color: white; \r\n");
      out.write("z-index:1002; \r\n");
      out.write("overflow: auto; \r\n");
      out.write("} </style>\n");
      out.write("<table cellspacing=\"0\" cellpadding=\"0\" width=\"955\" align=\"center\" border=\"0\">\n");
      out.write("  <tbody>\n");
      out.write("    <tr>\n");
      out.write("      <td><img height=\"43\" alt=\"\" width=\"955\" src=\"/template001/images/index_01.gif\" /></td>\n");
      out.write("    </tr>\n");
      out.write("  </tbody>\n");
      out.write("</table>\n");
      out.write("<table class=\"line1\" cellspacing=\"0\" cellpadding=\"0\" width=\"955\" align=\"center\" border=\"0\">\n");
      out.write("  <tbody>\n");
      out.write("    <tr>\n");
      out.write("      <td>\n");
      out.write("      <table class=\"topnav\" cellspacing=\"0\" cellpadding=\"0\" width=\"98%\" align=\"center\" border=\"0\">\n");
      out.write("        <tbody>\n");
      out.write("          <tr>\n");
      out.write("            <td width=\"1%\"><img height=\"5\" alt=\"\" width=\"3\" src=\"/template001/images/index_02.gif\" /></td>\n");
      out.write("            <td width=\"7%\"><a href=\"#\">东方商道 </a></td>\n");
      out.write("            <td width=\"1%\"><img height=\"5\" alt=\"\" width=\"3\" src=\"/template001/images/index_02.gif\" /></td>\n");
      out.write("            <td width=\"17%\">中小企业网</td>\n");
      out.write("            <td align=\"right\" width=\"74%\">\n");
      out.write("            <table cellspacing=\"0\" cellpadding=\"0\" width=\"380\" border=\"0\">\n");
      out.write("              <tbody>\n");
      out.write("                <tr>\n");
      out.write("                  <td width=\"61\"><a href=\"#\">下载BMS </a></td>\n");
      out.write("                  <td class=\"Point\" align=\"center\" width=\"13\">&middot;</td>\n");
      out.write("                  <td width=\"56\">发布信息</td>\n");
      out.write("                  <td align=\"center\" width=\"16\"><span class=\"Point\">&middot;</span></td>\n");
      out.write("                  <td width=\"55\">服务说明</td>\n");
      out.write("                  <td align=\"center\" width=\"19\"><span class=\"Point\">&middot;</span></td>\n");
      out.write("                  <td width=\"51\">咨询投诉</td>\n");
      out.write("                  <td align=\"center\" width=\"18\"><span class=\"Point\">&middot;</span></td>\n");
      out.write("                  <td width=\"26\">帮助</td>\n");
      out.write("                  <td align=\"center\" width=\"16\"><span class=\"Point\">&middot;</span></td>\n");
      out.write("                  <td width=\"49\">设为首页</td>\n");
      out.write("                </tr>\n");
      out.write("              </tbody>\n");
      out.write("            </table>\n");
      out.write("            </td>\n");
      out.write("          </tr>\n");
      out.write("        </tbody>\n");
      out.write("      </table>\n");
      out.write("      </td>\n");
      out.write("    </tr>\n");
      out.write("  </tbody>\n");
      out.write("</table>\n");
      out.write("<table cellspacing=\"0\" cellpadding=\"0\" width=\"955\" align=\"center\" border=\"0\">\n");
      out.write("  <tbody>\n");
      out.write("    <tr>\n");
      out.write("      <td width=\"250\">\n");
      out.write("      <table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" border=\"0\">\n");
      out.write("        <tbody>\n");
      out.write("          <tr>\n");
      out.write("            <td colspan=\"2\"><img height=\"60\" alt=\"\" width=\"250\" src=\"/template001/images/logo.gif\" /></td>\n");
      out.write("          </tr>\n");
      out.write("          <tr>\n");
      out.write("            <td valign=\"bottom\" width=\"4\"><img height=\"2\" alt=\"\" width=\"4\" src=\"/template001/images/index_11.gif\" /></td>\n");
      out.write("            <td width=\"246\" background=\"/template001/images/index_10.gif\">&nbsp;</td>\n");
      out.write("          </tr>\n");
      out.write("        </tbody>\n");
      out.write("      </table>\n");
      out.write("      </td>\n");
      out.write("      <td valign=\"bottom\" width=\"705\">\n");
      out.write("      <table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" background=\"/template001/images/index_09.gif\" border=\"0\">\n");
      out.write("        <tbody>\n");
      out.write("          <tr>\n");
      out.write("            <td width=\"553\">\n");
      out.write("            <table cellspacing=\"0\" cellpadding=\"0\" width=\"553\" border=\"0\">\n");
      out.write("              <tbody>\n");
      out.write("                <tr>\n");
      out.write("                  <td>\n");
      out.write("                  <table cellspacing=\"0\" cellpadding=\"0\" width=\"63\" border=\"0\">\n");
      out.write("                    <tbody>\n");
      out.write("                      <tr>\n");
      out.write("                        <td class=\"nav1\" background=\"/template001/images/index_03.gif\" height=\"36\"><a href=\"/template001/index.jsp\">首页</a></td>\n");
      out.write("                      </tr>\n");
      out.write("                    </tbody>\n");
      out.write("                  </table>\n");
      out.write("                  </td>\n");
      out.write("                  <td>\n");
      out.write("                  <table cellspacing=\"0\" cellpadding=\"0\" width=\"63\" border=\"0\">\n");
      out.write("                    <tbody>\n");
      out.write("                      <tr>\n");
      out.write("                        <td class=\"nav\" background=\"/template001/images/index_04.gif\" height=\"36\"><a href=\"/template001/product/channel010.jsp\">产品</a></td>\n");
      out.write("                      </tr>\n");
      out.write("                    </tbody>\n");
      out.write("                  </table>\n");
      out.write("                  </td>\n");
      out.write("                  <td>\n");
      out.write("                  <table cellspacing=\"0\" cellpadding=\"0\" width=\"63\" border=\"0\">\n");
      out.write("                    <tbody>\n");
      out.write("                      <tr>\n");
      out.write("                        <td class=\"nav\" background=\"/template001/images/index_04.gif\" height=\"36\"><a href=\"/template001/enterprise/channel022.jsp\"><font color=\"#ff7008\">企业</font></a></td>\n");
      out.write("                      </tr>\n");
      out.write("                    </tbody>\n");
      out.write("                  </table>\n");
      out.write("                  </td>\n");
      out.write("                  <td>\n");
      out.write("                  <table cellspacing=\"0\" cellpadding=\"0\" width=\"63\" border=\"0\">\n");
      out.write("                    <tbody>\n");
      out.write("                      <tr>\n");
      out.write("                        <td class=\"nav\" background=\"/template001/images/index_04.gif\" height=\"36\"><a href=\"/template001/supply/index.jsp\">供求</a></td>\n");
      out.write("                      </tr>\n");
      out.write("                    </tbody>\n");
      out.write("                  </table>\n");
      out.write("                  </td>\n");
      out.write("                  <td>\n");
      out.write("                  <table cellspacing=\"0\" cellpadding=\"0\" width=\"63\" border=\"0\">\n");
      out.write("                    <tbody>\n");
      out.write("                      <tr>\n");
      out.write("                        <td class=\"nav\" background=\"/template001/images/index_04.gif\" height=\"36\"><a href=\"/template001/brand/channel023.jsp\">品牌</a></td>\n");
      out.write("                      </tr>\n");
      out.write("                    </tbody>\n");
      out.write("                  </table>\n");
      out.write("                  </td>\n");
      out.write("                  <td>\n");
      out.write("                  <table cellspacing=\"0\" cellpadding=\"0\" width=\"63\" border=\"0\">\n");
      out.write("                    <tbody>\n");
      out.write("                      <tr>\n");
      out.write("                        <td class=\"nav\" background=\"/template001/images/index_04.gif\" height=\"36\"><a href=\"/template001/group/channel092.jsp\">团购</a></td>\n");
      out.write("                      </tr>\n");
      out.write("                    </tbody>\n");
      out.write("                  </table>\n");
      out.write("                  </td>\n");
      out.write("                  <td>\n");
      out.write("                  <table cellspacing=\"0\" cellpadding=\"0\" width=\"63\" border=\"0\">\n");
      out.write("                    <tbody>\n");
      out.write("                      <tr>\n");
      out.write("                        <td class=\"nav\" background=\"/template001/images/index_04.gif\" height=\"36\"><a href=\"/template001/twice/channel025.jsp\">商讯</a></td>\n");
      out.write("                      </tr>\n");
      out.write("                    </tbody>\n");
      out.write("                  </table>\n");
      out.write("                  </td>\n");
      out.write("                  <td>\n");
      out.write("                  <table cellspacing=\"0\" cellpadding=\"0\" width=\"63\" border=\"0\">\n");
      out.write("                    <tbody>\n");
      out.write("                      <tr>\n");
      out.write("                        <td class=\"nav\" background=\"/template001/images/index_04.gif\" height=\"36\"><a href=\"/template001/Exhibition/index.jsp\">会展</a></td>\n");
      out.write("                      </tr>\n");
      out.write("                    </tbody>\n");
      out.write("                  </table>\n");
      out.write("                  </td>\n");
      out.write("                </tr>\n");
      out.write("              </tbody>\n");
      out.write("            </table>\n");
      out.write("            </td>\n");
      out.write("            <td width=\"148\">&nbsp;</td>\n");
      out.write("            <td valign=\"bottom\" width=\"2\"><img height=\"2\" alt=\"\" width=\"4\" src=\"/template001/images/index_12.gif\" /></td>\n");
      out.write("          </tr>\n");
      out.write("        </tbody>\n");
      out.write("      </table>\n");
      out.write("      </td>\n");
      out.write("    </tr>\n");
      out.write("  </tbody>\n");
      out.write("</table>\n");
      out.write("<table cellspacing=\"0\" cellpadding=\"0\" width=\"955\" align=\"center\" border=\"0\">\n");
      out.write("  <tbody>\n");
      out.write("    <tr>\n");
      out.write("      <td width=\"4\"><img height=\"34\" alt=\"\" width=\"4\" src=\"/template001/images/index_05.gif\" /></td>\n");
      out.write("      <td background=\"/template001/images/index_06.gif\">\n");
      out.write("      <table class=\"whitelink\" cellspacing=\"0\" cellpadding=\"0\" width=\"98%\" align=\"center\" border=\"0\">\n");
      out.write("        <tbody>\n");
      out.write("          <tr>\n");
      out.write("            <td width=\"28\"><img height=\"16\" alt=\"\" width=\"21\" src=\"/template001/images/index_04.png\" /></td>\n");
      out.write("            <td class=\"whitelink\" width=\"127\"><a href=\"#\">购物车有10件商品</a></td>\n");
      out.write("            <td class=\"whitelink\" width=\"13\"><img height=\"15\" alt=\"\" width=\"2\" src=\"/template001/images/index_07.gif\" /></td>\n");
      out.write("            <td width=\"22\"><img height=\"15\" alt=\"\" width=\"13\" src=\"/template001/images/index_02.png\" /></td>\n");
      out.write("            <td width=\"56\"><a href=\"#\">去结算</a></td>\n");
      out.write("            <td width=\"14\"><img height=\"15\" alt=\"\" width=\"2\" src=\"/template001/images/index_07.gif\" /></td>\n");
      out.write("            <td width=\"25\"><img height=\"17\" alt=\"\" width=\"18\" src=\"/template001/images/index_03.png\" /></td>\n");
      out.write("            <td width=\"70\"><a href=\"#\">我的订单</a></td>\n");
      out.write("            <td width=\"13\"><img height=\"15\" alt=\"\" width=\"2\" src=\"/template001/images/index_07.gif\" /></td>\n");
      out.write("            <td width=\"26\"><img height=\"18\" alt=\"\" width=\"18\" src=\"/template001/images/index_01.png\" /></td>\n");
      out.write("            <td width=\"150\"><a href=\"#\">积分兑换</a></td>\n");
      out.write("            <td class=\"whitelink1\" align=\"right\" width=\"384\"><a href=\"#\">我的文具网</a> | <a href=\"#\">礼品卡</a> | <a href=\"#\">企业客户</a> | <a href=\"#\">客服中心</a></td>\n");
      out.write("          </tr>\n");
      out.write("        </tbody>\n");
      out.write("      </table>\n");
      out.write("      </td>\n");
      out.write("      <td width=\"4\"><img height=\"34\" alt=\"\" width=\"4\" src=\"/template001/images/index_08.gif\" /></td>\n");
      out.write("    </tr>\n");
      out.write("  </tbody>\n");
      out.write("</table>\n");
      out.write("<table cellspacing=\"0\" cellpadding=\"0\" width=\"955\" align=\"center\" border=\"0\">\n");
      out.write("  <tbody>\n");
      out.write("    <tr>\n");
      out.write("      <td background=\"/template001/images/index_13.gif\" height=\"50\">\n");
      out.write("      <table cellspacing=\"0\" cellpadding=\"0\" width=\"950\" align=\"center\" border=\"0\">\n");
      out.write("        <tbody>\n");
      out.write("          <tr>\n");
      out.write("            <td width=\"522\">\n");
      out.write("            <table cellspacing=\"0\" cellpadding=\"0\" width=\"514\" border=\"0\">\n");
      out.write("              <tbody>\n");
      out.write("                <tr>\n");
      out.write("                  <td background=\"/template001/images/index_14.gif\" height=\"39\">\n");
      out.write("                  <table style=\"margin-bottom: 2px\" cellspacing=\"0\" cellpadding=\"0\" width=\"95%\" align=\"center\" border=\"0\">\n");
      out.write("                    <tbody>\n");
      out.write("                      <tr>\n");
      out.write("                        <td align=\"center\" width=\"19%\"><select name=\"select\">\n");
      out.write("                        <option selected=\"selected\">所有类别</option>\n");
      out.write("                        </select></td>\n");
      out.write("                        <td width=\"40%\"><input class=\"line2\" name=\"textfield\" type=\"text\" /></td>\n");
      out.write("                        <td width=\"41%\"><img height=\"28\" alt=\"\" width=\"74\" usemap=\"#Map\" border=\"0\" src=\"/template001/images/index_24.gif\" /></td>\n");
      out.write("                      </tr>\n");
      out.write("                    </tbody>\n");
      out.write("                  </table>\n");
      out.write("                  </td>\n");
      out.write("                </tr>\n");
      out.write("              </tbody>\n");
      out.write("            </table>\n");
      out.write("            </td>\n");
      out.write("            <td class=\"bestserach\" width=\"66\"><a href=\"#\">高级搜索</a></td>\n");
      out.write("            <td width=\"98\"><a onclick=\"document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'\" href=\"javascript:void(0)\"><img alt=\"\" border=\"0\" src=\"/template001/images/index_16.gif\" /></a></td>\n");
      out.write("            <td width=\"98\"><a href=\"/template001/register/channel005.jsp\"><img height=\"28\" alt=\"\" width=\"94\" border=\"0\" src=\"/template001/images/index_17.gif\" /></a></td>\n");
      out.write("            <td class=\"why_member\" width=\"166\"><a href=\"#\">为什么要成为会员？忘记密码</a></td>\n");
      out.write("          </tr>\n");
      out.write("        </tbody>\n");
      out.write("      </table>\n");
      out.write("      <div class=\"white_content\" id=\"light\">\n");
      out.write("      <form id=\"reg\">\n");
      out.write("        <table cellspacing=\"0\" cellpadding=\"0\" width=\"302\" align=\"center\" border=\"0\">\n");
      out.write("          <tbody>\n");
      out.write("            <tr>\n");
      out.write("              <td background=\"/template001/images/login_16.gif\" height=\"24\">\n");
      out.write("              <table cellspacing=\"0\" cellpadding=\"0\" width=\"300\" border=\"0\">\n");
      out.write("                <tbody>\n");
      out.write("                  <tr>\n");
      out.write("                    <td class=\"whitefont12\" width=\"284\">　欢迎您注册为文具网的会员</td>\n");
      out.write("                    <td width=\"16\"><a onclick = \"document.getElementById('light').style.display='none';document.getElementById('fade').style.display='none'\" href=\"javascript:void(0)\"><img height=\"10\" alt=\"\" width=\"11\" border=\"0\" src=\"/template001/images/login_20.gif\" /></a></td>\n");
      out.write("                  </tr>\n");
      out.write("                </tbody>\n");
      out.write("              </table>\n");
      out.write("              </td>\n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("              <td valign=\"top\" background=\"/template001/images/login_17.gif\" height=\"173\"><br />\n");
      out.write("              <table cellspacing=\"0\" cellpadding=\"0\" width=\"95%\" align=\"center\" border=\"0\">\n");
      out.write("                <tbody>\n");
      out.write("                  <tr>\n");
      out.write("                    <td class=\"STYLE1\" align=\"left\">请选择注册会员种类：</td>\n");
      out.write("                    <td></td>\n");
      out.write("                  </tr>\n");
      out.write("                </tbody>\n");
      out.write("              </table>\n");
      out.write("              <table cellspacing=\"0\" cellpadding=\"0\" width=\"95%\" align=\"center\" border=\"0\">\n");
      out.write("                <tbody>\n");
      out.write("                  <tr>\n");
      out.write("                    <td height=\"20\">\n");
      out.write("                    <table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" border=\"0\">\n");
      out.write("                      <tbody>\n");
      out.write("                        <tr>\n");
      out.write("                          <td bgcolor=\"#a3dfdf\" height=\"1\"></td>\n");
      out.write("                        </tr>\n");
      out.write("                      </tbody>\n");
      out.write("                    </table>\n");
      out.write("                    </td>\n");
      out.write("                  </tr>\n");
      out.write("                </tbody>\n");
      out.write("              </table>\n");
      out.write("              <table class=\"conten_pinpai\" cellspacing=\"0\" cellpadding=\"0\" width=\"60%\" align=\"center\" border=\"0\">\n");
      out.write("                <tbody>\n");
      out.write("                  <tr>\n");
      out.write("                    <td align=\"center\"><input type=\"radio\" checked=\"checked\" name=\"content_type\" value=\"/template001/register/channel002.jsp\" /></td>\n");
      out.write("                    <td class=\"conten_pinpai\">供应商</td>\n");
      out.write("                  </tr>\n");
      out.write("                  <tr>\n");
      out.write("                    <td align=\"center\"><input type=\"radio\" name=\"content_type\" value=\"/template001/register/channel002.jsp\" /></td>\n");
      out.write("                    <td class=\"conten_pinpai\">经销商</td>\n");
      out.write("                  </tr>\n");
      out.write("                  <tr>\n");
      out.write("                    <td align=\"center\"><input type=\"radio\" name=\"content_type\" value=\"/template001/register/channel003.jsp\" /></td>\n");
      out.write("                    <td class=\"conten_pinpai\">团购会员</td>\n");
      out.write("                  </tr>\n");
      out.write("                  <tr>\n");
      out.write("                    <td align=\"center\">&nbsp;</td>\n");
      out.write("                    <td class=\"conten_pinpai\" height=\"40\"><a style=\"cursor: hand\" onclick=\"tijiao();\"><img height=\"22\" alt=\"\" width=\"71\" src=\"/template001/images/login_19.gif\" /></a> <script type=\"text/javascript\">\r\n");
      out.write("\t   function tijiao()\r\n");
      out.write("\t   {\r\n");
      out.write("\t   \t\tvar regtype = document.getElementsByName(\"content_type\");\r\n");
      out.write("\t\t\tfor(var i = 0 ; i<regtype.length ; i++)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif(regtype[i].checked == true)\r\n");
      out.write("\t\t\t\t\tdocument.location.href= regtype[i].value;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t   }\r\n");
      out.write("    </script></td>\n");
      out.write("                  </tr>\n");
      out.write("                </tbody>\n");
      out.write("              </table>\n");
      out.write("              </td>\n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("              <td><img height=\"5\" alt=\"\" width=\"302\" src=\"/template001/images/login_18.gif\" /></td>\n");
      out.write("            </tr>\n");
      out.write("          </tbody>\n");
      out.write("        </table>\n");
      out.write("      </form>\n");
      out.write("      </div>\n");
      out.write("      </td>\n");
      out.write("    </tr>\n");
      out.write("  </tbody>\n");
      out.write("</table>");
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

  private boolean _jspx_meth_site_005fscript_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  site:script
    com.fulong.taglib.site.ScriptTag _jspx_th_site_005fscript_005f0 = (com.fulong.taglib.site.ScriptTag) _005fjspx_005ftagPool_005fsite_005fscript_0026_005fportlets_005fnobody.get(com.fulong.taglib.site.ScriptTag.class);
    _jspx_th_site_005fscript_005f0.setPageContext(_jspx_page_context);
    _jspx_th_site_005fscript_005f0.setParent(null);
    _jspx_th_site_005fscript_005f0.setPortlets("constant");
    int _jspx_eval_site_005fscript_005f0 = _jspx_th_site_005fscript_005f0.doStartTag();
    if (_jspx_th_site_005fscript_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fsite_005fscript_0026_005fportlets_005fnobody.reuse(_jspx_th_site_005fscript_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fsite_005fscript_0026_005fportlets_005fnobody.reuse(_jspx_th_site_005fscript_005f0);
    return false;
  }
}
