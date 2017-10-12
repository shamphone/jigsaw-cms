package org.apache.jsp.service;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class scripts_js_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\r\n");
      out.write("/**\r\n");
      out.write(" * 对服务参数的dom模型的封装。这个dom模型对应xpdl中对ActualParameters元素的定义。\r\n");
      out.write(" **/\r\n");
      out.write("var Parameters = function(oXMLParameters){\r\n");
      out.write("\t\tthis.element = oXMLParameters;\r\n");
      out.write("\t\tif(this.element == null){\r\n");
      out.write("\t\t\tvar oXML = document.getElementById(\"serviceParameters\");\r\n");
      out.write("\t\t\tif(document.all){\r\n");
      out.write("\t\t\t\tif(oXML == null){\r\n");
      out.write("\t\t\t\t\toXML = document.createElement(\"xml\");\r\n");
      out.write("\t\t\t\t\toXML.id = \"serviceParameters\";\r\n");
      out.write("\t\t\t\t\tdocument.getElementsByTagName(\"head\")[0].appendChild(oXML);\r\n");
      out.write("\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\tthis.element = oXML.XMLDocument.documentElement;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\tif(this.element == null){\r\n");
      out.write("\t\t\t\t\tthis.element = oXML.XMLDocument.createElement(\"ActualParameters\");\r\n");
      out.write("\t\t\t\t\toXML.XMLDocument.appendChild(this.element);\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t//在火狐下第二次点击服务采用删除零时节点，重新建立零时节点传递参数\r\n");
      out.write("\t\t\t\tif(oXML == null){\r\n");
      out.write("\t\t\t\t\toXML = document.createElement(\"xml\");\r\n");
      out.write("\t\t\t\t\toXML.id = \"serviceParameters\";\r\n");
      out.write("\t\t\t\t\tdocument.getElementsByTagName(\"head\")[0].appendChild(oXML);\r\n");
      out.write("\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\toXML.parentNode.removeChild(oXML);\r\n");
      out.write("\t\t\t\t\toXML = document.createElement(\"xml\");\r\n");
      out.write("\t\t\t\t\toXML.id = \"serviceParameters\";\r\n");
      out.write("\t\t\t\t\tdocument.getElementsByTagName(\"head\")[0].appendChild(oXML);\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\tif(this.element == null){\r\n");
      out.write("\t\t\t\t\tthis.element = document.createElement(\"ActualParameters\");\r\n");
      out.write("\t\t\t\t\tdocument.lastChild.appendChild(this.element);\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("};\r\n");
      out.write("\r\n");
      out.write("Parameters.prototype.getParameterNode = function(param){\r\n");
      out.write("\tvar nodes = this.element.getElementsByTagName(\"ActualParameter\");\r\n");
      out.write("\tfor(var i=0; i<nodes.length; i++){\r\n");
      out.write("\t\tvar node = nodes[i];\r\n");
      out.write("\t\tvar names = node.getElementsByTagName(\"name\");\r\n");
      out.write("\t\tif(names.length == 1 ){\r\n");
      out.write("\t\t\tvar name = names[0].text;\r\n");
      out.write("\t\t\tif(name==param){\r\n");
      out.write("\t\t\t\treturn node;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\treturn null;\r\n");
      out.write("\r\n");
      out.write("};\r\n");
      out.write("/**\r\n");
      out.write("  * 获取参数param对应的值，返回值可能是单值、数组或者空值\r\n");
      out.write("  *\r\n");
      out.write("  **/\r\n");
      out.write("Parameters.prototype.getValue\t=\tfunction( param ){\r\n");
      out.write("\tvar node = this.getParameterNode(param);\r\n");
      out.write("\tif(node == null)\r\n");
      out.write("\t\treturn null;\r\n");
      out.write("\tvar values = node.getElementsByTagName(\"value\");\r\n");
      out.write("\tif(values.length == 0)\r\n");
      out.write("\t\treturn null;\r\n");
      out.write("\tif(values.length == 1)\r\n");
      out.write("\t\treturn values[0].text;\r\n");
      out.write("\tvar result = new Array();\r\n");
      out.write("\tfor(var v=0;v<values.length;v++)\r\n");
      out.write("\t\tresult.push(values[v].text);\r\n");
      out.write("\treturn result;\r\n");
      out.write("};\r\n");
      out.write("\r\n");
      out.write("/**\r\n");
      out.write(" * 获取参数param对应的值，返回值可能是单值、数组或者空值\r\n");
      out.write(" *\r\n");
      out.write(" **/\r\n");
      out.write("Parameters.prototype.getValues\t=\tfunction( param ){\r\n");
      out.write("\tvar node = this.getParameterNode(param);\r\n");
      out.write("\tif(node == null)\r\n");
      out.write("\t\treturn null;\r\n");
      out.write("\tvar values = node.getElementsByTagName(\"value\");\r\n");
      out.write("\tvar result = new Array();\r\n");
      out.write("\tfor(var v=0;v<values.length;v++)\r\n");
      out.write("\t\tresult.push(values[v].text);\r\n");
      out.write("\treturn result;\r\n");
      out.write("};\r\n");
      out.write("/**\r\n");
      out.write(" *设置单值\r\n");
      out.write("**/\r\n");
      out.write("Parameters.prototype.setValue\t=\tfunction( param , value){\r\n");
      out.write("\tvar node =this.getParameterNode(param);\r\n");
      out.write("\tif((value == null) && (node!=null))\r\n");
      out.write("\t\tthis.element.removeChild(node);\r\n");
      out.write("\telse if(value == null)\r\n");
      out.write("\t\treturn;\r\n");
      out.write("\telse\r\n");
      out.write("\t\tthis.setValues(param, [value]);\r\n");
      out.write("}\r\n");
      out.write("/**\r\n");
      out.write(" *设置单值\r\n");
      out.write("**/\r\n");
      out.write("Parameters.prototype.addValue\t=\tfunction( param , value){\r\n");
      out.write("\tvar values = this.getValues(param);\r\n");
      out.write("\tif(values == null)\r\n");
      out.write("\t\tvalues = [value];\r\n");
      out.write("\telse \r\n");
      out.write("\t\tvalues.push(value);\r\n");
      out.write("\tthis.setValues(param, values);\r\n");
      out.write("};\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("/**\r\n");
      out.write("  *设置多值\r\n");
      out.write(" **/\r\n");
      out.write("\r\n");
      out.write("Parameters.prototype.setValues\t=\tfunction( param , values){\r\n");
      out.write("\tvar doc =\tthis.element.ownerDocument;\r\n");
      out.write("\tvar node = this.getParameterNode(param);\r\n");
      out.write("\tif(node == null){\r\n");
      out.write("\t\tnode = doc.createElement(\"ActualParameter\");\r\n");
      out.write("\t\tthis.element.appendChild(node);\r\n");
      out.write("\t}\r\n");
      out.write("\telse{\r\n");
      out.write("\t\twhile(node.hasChildNodes())\r\n");
      out.write("\t\t\tnode.removeChild(node.firstChild);\r\n");
      out.write("\t}\r\n");
      out.write("\tvar nameElement = doc.createElement(\"name\");\r\n");
      out.write("\tnameElement.appendChild(doc.createTextNode(param));\r\n");
      out.write("\tnode.appendChild(nameElement);\r\n");
      out.write("\tfor(i=0;i<values.length;i++){\r\n");
      out.write("\t\tvar valueElement = doc.createElement(\"value\");\r\n");
      out.write("\t\tvalueElement.appendChild(doc.createTextNode(values[i]));\r\n");
      out.write("\t\tnode.appendChild(valueElement);\r\n");
      out.write("\t}\r\n");
      out.write("};\r\n");
      out.write("/**\r\n");
      out.write(" * 填充Form表单，如果控件的名称和参数名称一致，则填充。\r\n");
      out.write(" */\r\n");
      out.write("Parameters.prototype.populateForm = function (oForm){\r\n");
      out.write("\r\n");
      out.write("\t\r\n");
      out.write("\tfor(var i=0;i<oForm.elements.length;i++){\r\n");
      out.write("\t\tvar comp = oForm.elements[i];\r\n");
      out.write("\t\tif(this.getValues(comp.name)!=null)\r\n");
      out.write("\t\tswitch(comp.tagName){\r\n");
      out.write("\t\tcase \"SELECT\":\r\n");
      out.write("\t\t\tthis._populateSelect(comp);\r\n");
      out.write("\t\t\tbreak;\r\n");
      out.write("\t\tcase \"TEXTAREA\":\r\n");
      out.write("\t\t\tthis._populateText(comp);\r\n");
      out.write("\t\t\tbreak;\r\n");
      out.write("\t\tcase \"INPUT\":\r\n");
      out.write("\t\t\tswitch(comp.type){\r\n");
      out.write("\t\t\tcase \"hidden\":\r\n");
      out.write("\t\t\tcase \"text\":\r\n");
      out.write("\t\t\tcase \"password\":\r\n");
      out.write("\t\t\t\tthis._populateText(comp);\r\n");
      out.write("\t\t\t\tbreak;\r\n");
      out.write("\t\t\tcase \"checkbox\":\r\n");
      out.write("\t\t\tcase \"radio\":\r\n");
      out.write("\t\t\t\tthis._populateSelection(comp);\r\n");
      out.write("\t\t\t\tbreak;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\tbreak;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("};\r\n");
      out.write("/**\r\n");
      out.write(" * 从form控件中接收数据更新；\r\n");
      out.write(" */\r\n");
      out.write("Parameters.prototype.updateFromForm = function (oForm){\r\n");
      out.write("\tfor(var i=0;i<oForm.elements.length;i++){\r\n");
      out.write("\t\tvar comp = oForm.elements[i];\r\n");
      out.write("\t\tthis.setValue(comp.name, null);\r\n");
      out.write("\t}\r\n");
      out.write("\tfor(var i=0;i<oForm.elements.length;i++){\r\n");
      out.write("\t\tvar comp = oForm.elements[i];\t\t\r\n");
      out.write("\t\tswitch(comp.tagName){\r\n");
      out.write("\t\tcase \"SELECT\":\t\t\t\r\n");
      out.write("\t\t\tthis._updateSelect(comp);\r\n");
      out.write("\t\t\tbreak;\r\n");
      out.write("\t\tcase \"TEXTAREA\":\r\n");
      out.write("\t\t\tthis._updateText(comp);\r\n");
      out.write("\t\t\tbreak;\r\n");
      out.write("\t\tcase \"INPUT\":\r\n");
      out.write("\t\t\tswitch(comp.type){\r\n");
      out.write("\t\t\tcase \"hidden\":\r\n");
      out.write("\t\t\tcase \"text\":\r\n");
      out.write("\t\t\tcase \"password\":\r\n");
      out.write("\t\t\t\tthis._updateText(comp);\r\n");
      out.write("\t\t\t\tbreak;\r\n");
      out.write("\t\t\tcase \"checkbox\":\r\n");
      out.write("\t\t\tcase \"radio\":\r\n");
      out.write("\t\t\t\tthis._updateSelection(comp);\r\n");
      out.write("\t\t\t\tbreak;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\tbreak;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("};\r\n");
      out.write("\r\n");
      out.write("/**\r\n");
      out.write(" * 初始化select控件\r\n");
      out.write(" */\r\n");
      out.write("Parameters.prototype._populateSelect = function(comp){\r\n");
      out.write("\tvar values = this.getValues(comp.name);\r\n");
      out.write("\tfor(var i=0;i<comp.options.length;i++)\r\n");
      out.write("\t\tcomp.options[i].selected = values.contains(comp.options[i].value);\r\n");
      out.write("};\r\n");
      out.write("Parameters.prototype._updateSelect = function(comp){\r\n");
      out.write("\tfor(var i=0;i<comp.options.length;i++)\r\n");
      out.write("\t\tif(comp.options[i].selected)\r\n");
      out.write("\t\t\tthis.addValue(comp.name,comp.options[i].value);\r\n");
      out.write("};\r\n");
      out.write("\r\n");
      out.write("/**\r\n");
      out.write(" * 初始化textarea控件\r\n");
      out.write(" */\r\n");
      out.write("Parameters.prototype._populateText = function(comp){\r\n");
      out.write("\tvar values = this.getValues(comp.name);\t\r\n");
      out.write("\tif(comp.value!=null && comp.value.length>0)\r\n");
      out.write("\t\treturn;\r\n");
      out.write("\tif(values.length == 0)\r\n");
      out.write("\t\treturn;\r\n");
      out.write("\tcomp.value = values[0];\r\n");
      out.write("};\r\n");
      out.write("\r\n");
      out.write("Parameters.prototype._updateText = function(comp){\r\n");
      out.write("\tthis.addValue(comp.name,comp.value);\r\n");
      out.write("};\r\n");
      out.write("/**\r\n");
      out.write(" * 初始checkbox和radio类控件\r\n");
      out.write(" */\r\n");
      out.write("Parameters.prototype._populateSelection = function(comp){\r\n");
      out.write("\tvar values = this.getValues(comp.name);\t\r\n");
      out.write("\tif(comp.checked)\r\n");
      out.write("\t\treturn;\r\n");
      out.write("\tcomp.checked = values.contains(comp.value);\r\n");
      out.write("};\r\n");
      out.write("Parameters.prototype._updateSelection = function(comp){\r\n");
      out.write("\tif(comp.checked)\r\n");
      out.write("\t\tthis.addValue(comp.name,comp.value);\r\n");
      out.write("};\r\n");
      out.write("var Service = new Object();\r\n");
      out.write("//选择当前选项框\r\n");
      out.write("Service.SelectPanel= function(oSelect){\r\n");
      out.write("\tvar index=oSelect.options[oSelect.selectedIndex].value;\r\n");
      out.write("\tvar fieldsets=document.getElementsByTagName(\"fieldset\");\r\n");
      out.write("\tfor(i=0;i<fieldsets.length;i++){\r\n");
      out.write("\t\tfieldsets[i].style.display=\"none\";\r\n");
      out.write("\t}\r\n");
      out.write("\tfieldsets[parseInt(index)].style.display=\"\";\r\n");
      out.write("\tService.OnPanelSelect(index);\r\n");
      out.write("};\r\n");
      out.write("/**\r\n");
      out.write(" * 需要在Panel被展示时作初始化时调用，则可以覆盖这个方法\r\n");
      out.write(" */\r\n");
      out.write("Service.OnPanelSelect = function(index){\r\n");
      out.write("\treturn;\r\n");
      out.write("};\r\n");
      out.write("//用户点击OK按钮时的事件处理\r\n");
      out.write("Service.OK = function(oForm){\r\n");
      out.write("\t\r\n");
      out.write("}\r\n");
      out.write("//用户点击cancel按钮时的事件处理\r\n");
      out.write("Service.Cancel\t=\tfunction(oForm){\r\n");
      out.write("\twindow.close();\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("Service.Init\t=\tfunction(){\r\n");
      out.write("\tdocument.getElementById('pannelSelect').selectedIndex = 0;\r\n");
      out.write("\tvar fieldsets = tdFieldsets.children.tags(\"fieldset\");\r\n");
      out.write("\tif(fieldsets.length>0)\r\n");
      out.write("\t \tfieldsets[0].style.display = \"\";\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("/**\r\n");
      out.write(" * 服务管理的所有对话框；注意使用这个脚本依赖于common/script/dialog.jsp;common.js脚本\r\n");
      out.write(" */\r\n");
      out.write("var SMS = {\r\n");
      out.write("\tmodulePath\t: \t'");
      if (_jspx_meth_html_005frewrite_005f0(_jspx_page_context))
        return;
      out.write("',\r\n");
      out.write("\t/**\r\n");
      out.write("\t  * 选择服务\r\n");
      out.write("\t  **/\r\n");
      out.write("\tselectService : function(){\r\n");
      out.write("\t\treturn showDialog(this.modulePath+\"dialog/services.do\",\"\",450,360);\r\n");
      out.write("\t},\r\n");
      out.write("\t/**\r\n");
      out.write("\t *\r\n");
      out.write("\t * 修改服务\r\n");
      out.write("\t */\r\n");
      out.write("\teditService\t:\tfunction(serviceID, definitionID, oXMLParameters){\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar args = new Object();\r\n");
      out.write("\t\targs.parameters = new Parameters(oXMLParameters);\r\n");
      out.write("\t\targs.element = args.parameters.element;\r\n");
      out.write("\t\targs.definitionID = definitionID;\r\n");
      out.write("\t\treturn showDialog(this.modulePath+\"edit.do?serviceID=\"+serviceID,args,450,360);\r\n");
      out.write("\t},\r\n");
      out.write("\t/**\r\n");
      out.write("\t *\r\n");
      out.write("\t * 显示服务执行时的消息\r\n");
      out.write("\t */\r\n");
      out.write("\tshowMessages\t:\tfunction(msgcode){\r\n");
      out.write("\t\treturn showDialog(this.modulePath+\"dialog/messages.jsp\",msgcode,620,400);\r\n");
      out.write("\t}\t\r\n");
      out.write("}");
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
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f0 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_005frewrite_005f0.setPageContext(_jspx_page_context);
    _jspx_th_html_005frewrite_005f0.setParent(null);
    // /service/scripts.js.jsp(285,16) name = page type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f0.setPage("/");
    // /service/scripts.js.jsp(285,16) name = module type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005frewrite_005f0.setModule("/service");
    int _jspx_eval_html_005frewrite_005f0 = _jspx_th_html_005frewrite_005f0.doStartTag();
    if (_jspx_th_html_005frewrite_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005fpage_005fmodule_005fnobody.reuse(_jspx_th_html_005frewrite_005f0);
    return false;
  }
}
