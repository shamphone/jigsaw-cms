package org.apache.jsp.portlet.validator.constant;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class script_js_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(4);
    _jspx_dependants.add("/WEB-INF/struts-html.tld");
    _jspx_dependants.add("/WEB-INF/struts-bean.tld");
    _jspx_dependants.add("/WEB-INF/struts-logic.tld");
    _jspx_dependants.add("/WEB-INF/fulong-portal.tld");
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


/**
 *
 *数据校验类,用来在客户端完成对表单和控件的实时校验
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author lixf
 * @version 2.0
 *
 *
**/

      out.write("\r\n");
      out.write("var Validator=new Object();\r\n");
      out.write("\r\n");
      out.write("Validator.components=new Array();\r\n");
      out.write("Validator.forms=new Array();\r\n");
      out.write("Validator.Rules = new Array();\r\n");
      out.write("/**\r\n");
      out.write(" * 添加规则，在validator/script.js.jsp中调用\r\n");
      out.write(" *@ param formName 表单名称\r\n");
      out.write(" *@ param compName\t组件名称\r\n");
      out.write(" *@ param\trule\t验证规则，即对应的函数名称\r\n");
      out.write(" *@ param\tvalue\t校验值\r\n");
      out.write(" *\r\n");
      out.write("*/\r\n");
      out.write("Validator.AddRule\t\t=\tfunction(formName,compName,spanTrue, spanFalse,rule,value){\r\n");
      out.write("    if(this.forms[formName]==null)\r\n");
      out.write("       this.forms[formName]=new Array();\r\n");
      out.write("    if(this.components[formName+compName]==null)\r\n");
      out.write("       this.components[formName+compName]=new Array();\r\n");
      out.write("//    if(value!=null)\r\n");
      out.write("//    value = value.replace(/\\\\/g,'\\\\\\\\');\r\n");
      out.write("    var funcComp= new Object();\r\n");
      out.write("    funcComp.rule = rule;\r\n");
      out.write("    funcComp.form = document.formName;\r\n");
      out.write("    funcComp.component =compName;\r\n");
      out.write("    funcComp.spanTrue = document.all(spanTrue);\r\n");
      out.write("    funcComp.spanFalse = document.all(spanFalse);\r\n");
      out.write("    funcComp.value = value;\r\n");
      out.write("\tthis.components[formName+compName].push(funcComp);\t \r\n");
      out.write("\t\r\n");
      out.write("\tthis.forms[formName].push(funcComp);\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("/**\r\n");
      out.write(" * 检验控件的数据是否满足要求，当用户填写好组件数据后，调用这个方法来做数据验证，同时显示实时提示信息\r\n");
      out.write(" *@ param\toComp\t被校验的组件\r\n");
      out.write(" *\r\n");
      out.write("*/\r\n");
      out.write("Validator.ValidateComponent\t\t=\tfunction(oComp, validator){\r\n");
      out.write("    if(validator==null)\r\n");
      out.write("\t\tvalidator=oComp.name;\r\n");
      out.write("    var formName=oComp.form.name;  \r\n");
      out.write("\tif(this.components[formName+validator]==null)\r\n");
      out.write("\t\treturn true;\r\n");
      out.write("\t\t\r\n");
      out.write("\tvar theRules=this.components[formName+validator];\r\n");
      out.write("\tfor(i=0;i<theRules.length;i++){\r\n");
      out.write("\t\t  theRules[i].spanTrue.style.display = \"\";\r\n");
      out.write("\t\t  theRules[i].spanFalse.style.display = \"none\";\t\t\r\n");
      out.write("\t}\r\n");
      out.write("\tfor(i=0;i<theRules.length;i++){\r\n");
      out.write("\t\tif(!Validator.Rules[theRules[i].rule](oComp.form, theRules[i].component,theRules[i].value)){\r\n");
      out.write("\t\t  theRules[i].spanTrue.style.display = \"none\";\r\n");
      out.write("\t\t  theRules[i].spanFalse.style.display = \"\";\t\t\r\n");
      out.write("\t\t}\t\t\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("Validator.alert = function(oComp,valid){\t\r\n");
      out.write("\tvar validId=oComp.form.name+\"_\"+oComp.name+\"_\"+\"1\";\r\n");
      out.write("\tvar inValidId=oComp.form.name+\"_\"+oComp.name+\"_\"+\"0\";\r\n");
      out.write("\tif(valid){\r\n");
      out.write("\t\tdocument.all(validId).style.display='';\r\n");
      out.write("\t\tdocument.all(inValidId).style.display='none';\r\n");
      out.write("\t}else{\t\r\n");
      out.write("\t\tdocument.all(validId).style.display='none';\r\n");
      out.write("\t\tdocument.all(inValidId).style.display='';\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("/**\r\n");
      out.write(" *\r\n");
      out.write(" * 对表单中所有待校验的参数进行校验，在表单提交时调用\r\n");
      out.write("*/\r\n");
      out.write("Validator.ValidateForm\t=\tfunction(oForm){\r\n");
      out.write("    if(this.forms[oForm.name]==null)\r\n");
      out.write("        return true;\r\n");
      out.write("    var theRules = this.forms[oForm.name];\r\n");
      out.write("    var valid = true;\r\n");
      out.write("\tfor(i=0;i<theRules.length;i++){\r\n");
      out.write("\t\t  theRules[i].spanTrue.style.display = \"\";\r\n");
      out.write("\t\t  theRules[i].spanFalse.style.display = \"none\";\t\t\r\n");
      out.write("\t}\r\n");
      out.write("\tfor(i=0;i<theRules.length;i++){\r\n");
      out.write("\t\tif(!Validator.Rules[theRules[i].rule](oForm, theRules[i].component,theRules[i].value)){\r\n");
      out.write("\t\t  theRules[i].spanTrue.style.display = \"none\";\r\n");
      out.write("\t\t  theRules[i].spanFalse.style.display = \"\";\r\n");
      out.write("\t\t  valid = false;\t\t\r\n");
      out.write("\t\t}\t\t\r\n");
      out.write("\t}\r\n");
      out.write("    return valid;\r\n");
      out.write("}\r\n");
      out.write("/**\r\n");
      out.write(" * 验证不为空\r\n");
      out.write(" * @param oForm 表单\r\n");
      out.write(" * @param name：控件名称\r\n");
      out.write(" */\r\n");
      out.write("Validator.Rules['Required']\t=\tfunction (oForm, name){\r\n");
      out.write("\tvar oComp =oForm.elements[name]; \r\n");
      out.write("\tif(oComp==null)\r\n");
      out.write("\t\treturn false;\r\n");
      out.write("\tif((oComp.value==null)||(oComp.value.length==0))\r\n");
      out.write("\t\treturn false;\r\n");
      out.write("    var value=oComp.value.trim();\r\n");
      out.write("\tvar valid=true;\r\n");
      out.write("    if((value==null)||(value.length==0)) valid = false;\r\n");
      out.write("//    Validator.alert(oComp,valid);\r\n");
      out.write("    return valid;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("/**\r\n");
      out.write(" * 验证掩码\r\n");
      out.write(" * @param oForm 表单\r\n");
      out.write(" * @param name：控件名称\r\n");
      out.write(" * @param mask\t校验码\r\n");
      out.write(" */\r\n");
      out.write("Validator.Rules['Mask']\t\t=\tfunction (oForm, name, mask) {\r\n");
      out.write("\tvar oComp =oForm.elements[name]; \r\n");
      out.write("\tif(oComp==null)\r\n");
      out.write("\t\treturn false;\r\n");
      out.write("    var value=oComp.value.trim();\r\n");
      out.write("\treturn new RegExp(mask,\"ig\").test(value);\r\n");
      out.write("}\r\n");
      out.write("/**\r\n");
      out.write(" * 验证最大值\r\n");
      out.write(" * @param oForm 表单\r\n");
      out.write(" * @param name：控件名称\r\n");
      out.write(" * @param max\t最大值\r\n");
      out.write(" */\r\n");
      out.write("Validator.Rules['Max']\t=\tfunction (oForm, name, max){\r\n");
      out.write("\tvar oComp =oForm.elements[name]; \r\n");
      out.write("    var value=oComp.value;\r\n");
      out.write("\tvar valid=true;\r\n");
      out.write("    if((value==null)||(value.length==0)) return true;\r\n");
      out.write("\tvar intValue=parseInt(value);\r\n");
      out.write("\tif(isNaN(intValue))\r\n");
      out.write("\t\tvalid = false;\r\n");
      out.write("    else\r\n");
      out.write("    \tvalid = parseInt(value)<=parseInt(max);\r\n");
      out.write("//    Validator.alert(oComp,valid);\r\n");
      out.write("    return valid;\r\n");
      out.write("}\r\n");
      out.write("/**\r\n");
      out.write(" * 验证最小值\r\n");
      out.write(" * @param oForm 表单\r\n");
      out.write(" * @param name：控件名称\r\n");
      out.write(" * @param max\t最小值\r\n");
      out.write(" */\r\n");
      out.write("Validator.Rules['Min']\t=\tfunction (oForm, name,min){\r\n");
      out.write("\tvar oComp =oForm.elements[name]; \r\n");
      out.write("    var value=oComp.value;\r\n");
      out.write("\tvar valid=true;\r\n");
      out.write("    if((value==null)||(value.length==0)) return true;\r\n");
      out.write("\tvar intValue=parseInt(value);\r\n");
      out.write("\tif(isNaN(intValue))\r\n");
      out.write("\t\tvalid = false;\r\n");
      out.write("    else\r\n");
      out.write("    \tvalid = parseInt(value)>=parseInt(min);\r\n");
      out.write("//    Validator.alert(oComp,valid);\r\n");
      out.write("    return valid;\r\n");
      out.write("}\r\n");
      out.write("/**\r\n");
      out.write(" * 验证最大长度\r\n");
      out.write(" * @param oForm 表单\r\n");
      out.write(" * @param name：控件名称\r\n");
      out.write(" * @param max\t最大值\r\n");
      out.write(" */\r\n");
      out.write("Validator.Rules['MaxLength']\t=\tfunction (oForm, name, max){\r\n");
      out.write("\tvar oComp =oForm.elements[name]; \r\n");
      out.write("    var value=oComp.value;\r\n");
      out.write("\tvar valid=true;\r\n");
      out.write("    if((value==null)||(value.length==0)) \r\n");
      out.write("     valid= true;\r\n");
      out.write("    else\r\n");
      out.write("     valid = value.length<=parseInt(max);\r\n");
      out.write("//    Validator.alert(oComp,valid);\r\n");
      out.write("    return valid;\r\n");
      out.write("}\r\n");
      out.write("/**\r\n");
      out.write(" * 验证最小长度\r\n");
      out.write(" * @param oForm 表单\r\n");
      out.write(" * @param name：控件名称\r\n");
      out.write(" * @param max\t最小值\r\n");
      out.write(" */\r\n");
      out.write("Validator.Rules['MinLength']\t=\tfunction (oForm, name,min){\r\n");
      out.write("\tvar oComp =oForm.elements[name]; \r\n");
      out.write("    var value=oComp.value;\r\n");
      out.write("\tvar valid=true;\r\n");
      out.write("    if((value==null)||(value.length==0))\r\n");
      out.write("     valid = true;\r\n");
      out.write("    else\r\n");
      out.write("     valid = value.length>=parseInt(min);\r\n");
      out.write("//    Validator.alert(oComp,valid);\r\n");
      out.write("    return valid;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
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
