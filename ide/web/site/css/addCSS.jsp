<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>创建样式文件</title>
    <style type="text/css">
      body, a, table, div, span, td, th, input, select{font:9pt;font-family: Verdana, Arial, Helvetica, sans-serif;}
      body {padding:5px}
      .formTips{display:none}
    </style>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
  </head>
  <body>
    <table border=0 cellpadding=0 cellspacing=0 align=center>
      <html:form   action="/saveCSS.do" method="post"  >
        <html:hidden property="path"/>
        <tr>
          <td>
            <fieldset>
              <legend>样式文件属性</legend>
              <table border=0 cellpadding=5 cellspacing=0>
                <tr>
                  <td>文件名称</td>
                  <td>
                    <html:text property="cssID"/>
                    <div class="formTips">请填写一个与已有样式文件不重复的文件名称。不要包含~！·#$%^&amp;*()等特殊字符。</div>
                  </td>
                </tr>
                <tr><td height=5></td></tr>
                <tr>
                  <td colspan="2" align=right>
                    <button onclick="check(this)">确认</button>
                    &nbsp;&nbsp;
                    <button onclick="window.close()">取消</button>
                  </td>
                </tr>
              </table>
            </fieldset>
          </td>
        </tr>
      </html:form>
    </table>
    <html:javascript formName="cssForm" bundle="css"/>
    <script language="javascript" type="text/Javascript">
      function check(submitter){
        if(validateCssForm(submitter.form)){
          submitter.disabled=true;
          submitter.form.submit();
        }
      }
      </script>
      </body>
    </html>
