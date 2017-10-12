package org.apache.jsp.site.channel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class openSingle_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(5);
    _jspx_dependants.add("/WEB-INF/struts-bean.tld");
    _jspx_dependants.add("/WEB-INF/struts-logic.tld");
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
    _jspx_dependants.add("/WEB-INF/struts-tiles.tld");
    _jspx_dependants.add("/WEB-INF/fulong.tld");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fdefinition;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fdefinition = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fdefinition.release();
    _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname.release();
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.release();
    _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
    _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid.release();
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.release();
    _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.release();
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

      //  tiles:insert
      org.apache.struts.taglib.tiles.InsertTag _jspx_th_tiles_005finsert_005f0 = (org.apache.struts.taglib.tiles.InsertTag) _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fdefinition.get(org.apache.struts.taglib.tiles.InsertTag.class);
      _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
      _jspx_th_tiles_005finsert_005f0.setParent(null);
      _jspx_th_tiles_005finsert_005f0.setDefinition("dialog_frame");
      int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
      if (_jspx_eval_tiles_005finsert_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
            return;
          //  tiles:put
          org.apache.struts.taglib.tiles.PutTag _jspx_th_tiles_005fput_005f1 = (org.apache.struts.taglib.tiles.PutTag) _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname.get(org.apache.struts.taglib.tiles.PutTag.class);
          _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
          _jspx_th_tiles_005fput_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005finsert_005f0);
          _jspx_th_tiles_005fput_005f1.setName("javascript");
          int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
          if (_jspx_eval_tiles_005fput_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            if (_jspx_eval_tiles_005fput_005f1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.pushBody();
              _jspx_th_tiles_005fput_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
              _jspx_th_tiles_005fput_005f1.doInitBody();
            }
            do {
              out.write("<script language=\"Javascript\" type=\"text/Javascript\" src=\"");
              if (_jspx_meth_html_005frewrite_005f0(_jspx_th_tiles_005fput_005f1, _jspx_page_context))
                return;
              out.write("\"></script>\r\n");
              out.write("    <script type=\"text/javascript\" language=\"javascript\" src=\"");
              if (_jspx_meth_html_005frewrite_005f1(_jspx_th_tiles_005fput_005f1, _jspx_page_context))
                return;
              out.write("\"></script>\r\n");
              out.write("    <script language=\"Javascript\" type=\"text/Javascript\" src=\"");
              if (_jspx_meth_html_005frewrite_005f2(_jspx_th_tiles_005fput_005f1, _jspx_page_context))
                return;
              out.write("\"></script>\r\n");
              out.write("\t<script language=\"Javascript\" type=\"text/Javascript\" src=\"");
              if (_jspx_meth_html_005frewrite_005f3(_jspx_th_tiles_005fput_005f1, _jspx_page_context))
                return;
              out.write("\"></script>\r\n");
              out.write("\t<script language=\"Javascript\" type=\"text/Javascript\" src=\"");
              if (_jspx_meth_html_005frewrite_005f4(_jspx_th_tiles_005fput_005f1, _jspx_page_context))
                return;
              out.write("\"></script>\r\n");
              out.write("    <script language=\"Javascript\" type=\"text/Javascript\" src=\"");
              if (_jspx_meth_html_005frewrite_005f5(_jspx_th_tiles_005fput_005f1, _jspx_page_context))
                return;
              out.write("\"></script>\r\n");
              out.write("\t<script language=\"Javascript\" type=\"text/Javascript\" src=\"");
              if (_jspx_meth_html_005frewrite_005f6(_jspx_th_tiles_005fput_005f1, _jspx_page_context))
                return;
              out.write("\"></script>\r\n");
              out.write("\t<script language=\"Javascript\" type=\"text/Javascript\" src=\"");
              if (_jspx_meth_html_005frewrite_005f7(_jspx_th_tiles_005fput_005f1, _jspx_page_context))
                return;
              out.write("\"></script>\r\n");
              out.write("    <script language=\"Javascript\" type=\"text/Javascript\" src=\"");
              if (_jspx_meth_html_005frewrite_005f8(_jspx_th_tiles_005fput_005f1, _jspx_page_context))
                return;
              out.write("\"></script>\r\n");
              out.write("    <script type=\"text/javascript\" language=\"javascript\">\r\n");
              out.write("\tWebFXTreeAbstractNode.prototype.getText = function(){\r\n");
              out.write("\t\tswitch(this.nodeType){\r\n");
              out.write("\t\t\tcase 'CHANNEL':\r\n");
              out.write("\t\t\t\tvar displayName = this.element.displayName;\r\n");
              out.write("\t\t\t\tvar name = this.element.name;\r\n");
              out.write("\t\t\t\tvar text='<input type=\"radio\" onclick=\"CHANNELTree_OnClick(\\''+this.id+'\\')\" name=\"channelID\" value=\"'+name+\r\n");
              out.write("        \t\t\t'\" id=\"'+this.id+'\" style=\"height:13px;\"><label for=\"'+this.id+'\">'+displayName+'</label>';\r\n");
              out.write("        \t\t\treturn text;\r\n");
              out.write("\t\t\tdefault:\r\n");
              out.write("\t\t\t\treturn this.text;\t\r\n");
              out.write("\t}\r\n");
              out.write("};\r\n");
              out.write("   \t \r\n");
              out.write("     window.onload = function(){\r\n");
              out.write("        top.document.title= window.dialogArguments + \"-\"+window.document.title;\r\n");
              out.write("        document.getElementById(\"ok\").disabled=true;\r\n");
              out.write("        doInit();\r\n");
              out.write("      }\r\n");
              out.write("      function doInit(){\r\n");
              out.write("\t \t\tvar oTemplate = new SMTemplate(\"");
              if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_tiles_005fput_005f1, _jspx_page_context))
                return;
              out.write('"');
              out.write(',');
              out.write('"');
              if (_jspx_meth_bean_005fwrite_005f2(_jspx_th_tiles_005fput_005f1, _jspx_page_context))
                return;
              out.write('"');
              out.write(',');
              out.write('"');
              if (_jspx_meth_bean_005fwrite_005f3(_jspx_th_tiles_005fput_005f1, _jspx_page_context))
                return;
              out.write("\");\r\n");
              out.write("\t\t\tvar exclude = new Array();\r\n");
              out.write("\t\t\t");
              //  logic:notEmpty
              org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (org.apache.struts.taglib.logic.NotEmptyTag) _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
              _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
              _jspx_th_logic_005fnotEmpty_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005fput_005f1);
              _jspx_th_logic_005fnotEmpty_005f0.setName("filterTypes");
              int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
              if (_jspx_eval_logic_005fnotEmpty_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  //  logic:iterate
                  org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_005fiterate_005f0 = (org.apache.struts.taglib.logic.IterateTag) _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid.get(org.apache.struts.taglib.logic.IterateTag.class);
                  _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
                  _jspx_th_logic_005fiterate_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fnotEmpty_005f0);
                  _jspx_th_logic_005fiterate_005f0.setId("type");
                  _jspx_th_logic_005fiterate_005f0.setName("filterTypes");
                  int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
                  if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    java.lang.Object type = null;
                    if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                      out = _jspx_page_context.pushBody();
                      _jspx_th_logic_005fiterate_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
                      _jspx_th_logic_005fiterate_005f0.doInitBody();
                    }
                    type = (java.lang.Object) _jspx_page_context.findAttribute("type");
                    do {
                      out.write("\r\n");
                      out.write("\t    \t \t\texclude.push('");
                      if (_jspx_meth_bean_005fwrite_005f4(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
                        return;
                      out.write("'); \r\n");
                      out.write("\t    \t   ");
                      int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
                      type = (java.lang.Object) _jspx_page_context.findAttribute("type");
                      if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                        break;
                    } while (true);
                    if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                      out = _jspx_page_context.popBody();
                    }
                  }
                  if (_jspx_th_logic_005fiterate_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
                    return;
                  }
                  _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
                  int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
              }
              if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
                return;
              }
              _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
              out.write("\r\n");
              out.write("\t \t\tvar channelTree = new CHANNELTree(oTemplate,exclude);\r\n");
              out.write("\t \t\tdocument.getElementById(\"channelTree\").innerHTML = channelTree.toString();\r\n");
              out.write("\t \t\tchannelTree.expand();\r\n");
              out.write("      };\r\n");
              out.write("\r\n");
              out.write("      \r\n");
              out.write("      function changeTemplate(){\r\n");
              out.write("\t      var template=SiteDialog.selectTemplate();\r\n");
              out.write("\t      if(template!=null&&template.length>0){\r\n");
              out.write("\t        reload(template[0].ID);\r\n");
              out.write("\t      }\r\n");
              out.write("      }\r\n");
              out.write("      function reload(templateId){\r\n");
              out.write("          var url='");
              if (_jspx_meth_html_005frewrite_005f9(_jspx_th_tiles_005fput_005f1, _jspx_page_context))
                return;
              out.write("'+templateId;\r\n");
              out.write("          url = url+\"&openTemplate=true\";\r\n");
              out.write("          url = url + \"&title=");
              out.print( request.getParameter("title"));
              out.write("\";\r\n");
              out.write("          url = url+\"&time=\"+new Date().getTime();\r\n");
              out.write("          oRefresh.href= url;\r\n");
              out.write("          oRefresh.click();\r\n");
              out.write("      }\r\n");
              out.write("      var selectedChannel;\r\n");
              out.write("      function CHANNELTree_OnClick(path){\r\n");
              out.write("    \t var node = webFXTreeHandler.all[path];\r\n");
              out.write("    \t if(node.isEnable()){\r\n");
              out.write("    \t\t document.getElementById(\"ok\").disabled=false;\r\n");
              out.write("    \t\t selectedChannel = node.element;\r\n");
              out.write("    \t }else{\r\n");
              out.write("    \t\t document.getElementById(\"ok\").disabled=true;\r\n");
              out.write("    \t }\r\n");
              out.write("      }\r\n");
              out.write("      function ok(){\r\n");
              out.write("          if(selectedChannel){\r\n");
              out.write("        \t  window.top.returnValue=selectedChannel;\r\n");
              out.write("              window.top.close();\r\n");
              out.write("          }else{\r\n");
              out.write("\t\t\t alert(\"请选择栏目！\");\r\n");
              out.write("          }\r\n");
              out.write("      }\r\n");
              out.write("      </script>\r\n");
              out.write("      ");
              int evalDoAfterBody = _jspx_th_tiles_005fput_005f1.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
            if (_jspx_eval_tiles_005fput_005f1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.popBody();
            }
          }
          if (_jspx_th_tiles_005fput_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname.reuse(_jspx_th_tiles_005fput_005f1);
            return;
          }
          _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname.reuse(_jspx_th_tiles_005fput_005f1);
          if (_jspx_meth_tiles_005fput_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
            return;
          int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_tiles_005finsert_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fdefinition.reuse(_jspx_th_tiles_005finsert_005f0);
        return;
      }
      _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fdefinition.reuse(_jspx_th_tiles_005finsert_005f0);
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

  private boolean _jspx_meth_tiles_005fput_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  tiles:put
    org.apache.struts.taglib.tiles.PutTag _jspx_th_tiles_005fput_005f0 = (org.apache.struts.taglib.tiles.PutTag) _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname.get(org.apache.struts.taglib.tiles.PutTag.class);
    _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
    _jspx_th_tiles_005fput_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005finsert_005f0);
    _jspx_th_tiles_005fput_005f0.setName("title");
    int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
    if (_jspx_eval_tiles_005fput_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_tiles_005fput_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_tiles_005fput_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_tiles_005fput_005f0.doInitBody();
      }
      do {
        if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_tiles_005fput_005f0, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_tiles_005fput_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_tiles_005fput_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_tiles_005fput_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname.reuse(_jspx_th_tiles_005fput_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname.reuse(_jspx_th_tiles_005fput_005f0);
    return false;
  }

  private boolean _jspx_meth_bean_005fwrite_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005fput_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f0 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fwrite_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005fput_005f0);
    _jspx_th_bean_005fwrite_005f0.setName("template");
    _jspx_th_bean_005fwrite_005f0.setProperty("displayName");
    int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
    if (_jspx_th_bean_005fwrite_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005fput_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f0 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f0.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005fput_005f1);
    _jspx_th_html_005frewrite_005f0.setModule("/common");
    _jspx_th_html_005frewrite_005f0.setPage("/xtree/tree.js.jsp");
    int _jspx_eval_html_005frewrite_005f0 = _jspx_th_html_005frewrite_005f0.doStartTag();
    if (_jspx_th_html_005frewrite_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f0);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005fput_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f1 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f1.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005fput_005f1);
    _jspx_th_html_005frewrite_005f1.setPage("/dialog.js.jsp");
    _jspx_th_html_005frewrite_005f1.setModule("/site");
    int _jspx_eval_html_005frewrite_005f1 = _jspx_th_html_005frewrite_005f1.doStartTag();
    if (_jspx_th_html_005frewrite_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f1);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005fput_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f2 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f2.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005fput_005f1);
    _jspx_th_html_005frewrite_005f2.setModule("/site");
    _jspx_th_html_005frewrite_005f2.setPage("/classes/site.js");
    int _jspx_eval_html_005frewrite_005f2 = _jspx_th_html_005frewrite_005f2.doStartTag();
    if (_jspx_th_html_005frewrite_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f2);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f3(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005fput_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f3 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f3.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005fput_005f1);
    _jspx_th_html_005frewrite_005f3.setModule("/site");
    _jspx_th_html_005frewrite_005f3.setPage("/classes/webdav.js");
    int _jspx_eval_html_005frewrite_005f3 = _jspx_th_html_005frewrite_005f3.doStartTag();
    if (_jspx_th_html_005frewrite_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f3);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f4(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005fput_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f4 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f4.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005fput_005f1);
    _jspx_th_html_005frewrite_005f4.setModule("/site");
    _jspx_th_html_005frewrite_005f4.setPage("/classes/smresource.js");
    int _jspx_eval_html_005frewrite_005f4 = _jspx_th_html_005frewrite_005f4.doStartTag();
    if (_jspx_th_html_005frewrite_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f4);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f5(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005fput_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f5 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f5.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005fput_005f1);
    _jspx_th_html_005frewrite_005f5.setModule("/site");
    _jspx_th_html_005frewrite_005f5.setPage("/classes/smchannel.js");
    int _jspx_eval_html_005frewrite_005f5 = _jspx_th_html_005frewrite_005f5.doStartTag();
    if (_jspx_th_html_005frewrite_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f5);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f5);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f6(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005fput_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f6 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f6.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005fput_005f1);
    _jspx_th_html_005frewrite_005f6.setModule("/site");
    _jspx_th_html_005frewrite_005f6.setPage("/classes/smtemplate.js");
    int _jspx_eval_html_005frewrite_005f6 = _jspx_th_html_005frewrite_005f6.doStartTag();
    if (_jspx_th_html_005frewrite_005f6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f6);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f6);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f7(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005fput_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f7 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f7.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005fput_005f1);
    _jspx_th_html_005frewrite_005f7.setModule("/site");
    _jspx_th_html_005frewrite_005f7.setPage("/classes/smfolder.js");
    int _jspx_eval_html_005frewrite_005f7 = _jspx_th_html_005frewrite_005f7.doStartTag();
    if (_jspx_th_html_005frewrite_005f7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f7);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f7);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f8(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005fput_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f8 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f8.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005fput_005f1);
    _jspx_th_html_005frewrite_005f8.setModule("/site");
    _jspx_th_html_005frewrite_005f8.setPage("/classes/channeltree.js");
    int _jspx_eval_html_005frewrite_005f8 = _jspx_th_html_005frewrite_005f8.doStartTag();
    if (_jspx_th_html_005frewrite_005f8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f8);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f8);
    return false;
  }

  private boolean _jspx_meth_bean_005fwrite_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005fput_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f1 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fwrite_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005fput_005f1);
    _jspx_th_bean_005fwrite_005f1.setName("template");
    _jspx_th_bean_005fwrite_005f1.setProperty("name");
    int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
    if (_jspx_th_bean_005fwrite_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
    return false;
  }

  private boolean _jspx_meth_bean_005fwrite_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005fput_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f2 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_005fwrite_005f2.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fwrite_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005fput_005f1);
    _jspx_th_bean_005fwrite_005f2.setName("template");
    _jspx_th_bean_005fwrite_005f2.setProperty("displayName");
    int _jspx_eval_bean_005fwrite_005f2 = _jspx_th_bean_005fwrite_005f2.doStartTag();
    if (_jspx_th_bean_005fwrite_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
    return false;
  }

  private boolean _jspx_meth_bean_005fwrite_005f3(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005fput_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f3 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_005fwrite_005f3.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fwrite_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005fput_005f1);
    _jspx_th_bean_005fwrite_005f3.setName("template");
    _jspx_th_bean_005fwrite_005f3.setProperty("resolution");
    int _jspx_eval_bean_005fwrite_005f3 = _jspx_th_bean_005fwrite_005f3.doStartTag();
    if (_jspx_th_bean_005fwrite_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f3);
    return false;
  }

  private boolean _jspx_meth_bean_005fwrite_005f4(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f4 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_005fwrite_005f4.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fwrite_005f4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fiterate_005f0);
    _jspx_th_bean_005fwrite_005f4.setName("type");
    int _jspx_eval_bean_005fwrite_005f4 = _jspx_th_bean_005fwrite_005f4.doStartTag();
    if (_jspx_th_bean_005fwrite_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f4);
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f9(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005fput_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f9 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f9.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005fput_005f1);
    _jspx_th_html_005frewrite_005f9.setPage("/open.do?templateName=");
    _jspx_th_html_005frewrite_005f9.setModule("/site/channel");
    int _jspx_eval_html_005frewrite_005f9 = _jspx_th_html_005frewrite_005f9.doStartTag();
    if (_jspx_th_html_005frewrite_005f9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f9);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f9);
    return false;
  }

  private boolean _jspx_meth_tiles_005fput_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  tiles:put
    org.apache.struts.taglib.tiles.PutTag _jspx_th_tiles_005fput_005f2 = (org.apache.struts.taglib.tiles.PutTag) _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname.get(org.apache.struts.taglib.tiles.PutTag.class);
    _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
    _jspx_th_tiles_005fput_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005finsert_005f0);
    _jspx_th_tiles_005fput_005f2.setName("dialog");
    int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
    if (_jspx_eval_tiles_005fput_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_tiles_005fput_005f2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_tiles_005fput_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_tiles_005fput_005f2.doInitBody();
      }
      do {
        out.write("<a href=\"#\" id=\"oRefresh\" style=\"display:none\">doRefresh</a>\r\n");
        out.write("       \t<div id=\"channelTree\" style=\"width:100%;height:87.6%;background-color:#ffffff;border:2px inset;overflow:scroll;padding-top:1px; \">\r\n");
        out.write("\t\t</div>\r\n");
        out.write("        <div class=\"operation\">\r\n");
        out.write("          ");
        if (_jspx_meth_logic_005fpresent_005f0(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
          return true;
        out.write("<button type=\"button\" onclick=\"ok()\"  id=\"ok\">确定</button>\r\n");
        out.write("          <button type=\"button\" onclick=\"window.close()\">取消</button>\r\n");
        out.write("        </div>\r\n");
        out.write("      ");
        int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_tiles_005fput_005f2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_tiles_005fput_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname.reuse(_jspx_th_tiles_005fput_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005ftiles_005fput_0026_005fname.reuse(_jspx_th_tiles_005fput_005f2);
    return false;
  }

  private boolean _jspx_meth_logic_005fpresent_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:present
    org.apache.struts.taglib.logic.PresentTag _jspx_th_logic_005fpresent_005f0 = (org.apache.struts.taglib.logic.PresentTag) _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.get(org.apache.struts.taglib.logic.PresentTag.class);
    _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
    _jspx_th_logic_005fpresent_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_tiles_005fput_005f2);
    _jspx_th_logic_005fpresent_005f0.setParameter("openTemplate");
    int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
    if (_jspx_eval_logic_005fpresent_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("<button type=\"button\" onclick=\"changeTemplate()\" style=\"margin-left:0px;margin-right:66px; padding:0px\">打开其它网站模板...</button>");
        int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_005fpresent_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.reuse(_jspx_th_logic_005fpresent_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.reuse(_jspx_th_logic_005fpresent_005f0);
    return false;
  }
}
