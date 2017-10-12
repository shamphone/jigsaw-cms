<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>样式表单编辑</title>
    <META http-equiv=Content-Type content="text/html; charset=UTF-8">
    <link href="<html:rewrite module="/common" page="/textEditor/style/simpleEditor.css"/>" type="text/css" rel="stylesheet"/>
  </head>
  <body onresize="reSize()">
    <table align="center" border=0 cellpadding=0 cellspacing=0 width='100%'>
      <tr>
        <td>
          <table border=0 cellpadding=0 cellspacing=0 width='100%' class='Toolbar' id='eWebEditor_Toolbar'>
            <tr>
              <td>
                <SPAN   CLASS="TBHandle"></SPAN>
                <SPAN   CLASS="Btn" onMouseOver="this.className='BtnMouseOver'" onMouseOut="this.className='Btn'" onClick="createCSS()">
                  <html:img module="/common" alt="新建样式文件" page="/textEditor/buttonimage/standard/new.gif"/>
                </SPAN>
                <SPAN   CLASS="Btn" onMouseOver="this.className='BtnMouseOver'" onMouseOut="this.className='Btn'" onClick="saveFile()">
                  <html:img module="/common" alt="保存当前样式文件" page="/textEditor/buttonimage/standard/save.gif"/>
                </SPAN>
                <logic:notMatch value="/style.css" name="cssForm" property="path">
                  <SPAN   CLASS="Btn" onmouseover="this.className='BtnMouseOver'" onmouseout="this.className='Btn'" onclick="deleteCSS()">
                    <html:img module="/common" alt="删除当前样式文件" page="/textEditor/buttonimage/standard/delete.gif"/>
                  </SPAN>
                </logic:notMatch>
              </td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td height='100%'>
          <table border=0 cellpadding=0 cellspacing=0 width='100%' height='100%'>
            <tr>
              <td height='100%'>
                <html:form action="upLoadCSS.do"><%--
                  <input type="hidden" name="channelListPath" value="<bean:write name="channelListPath"/>" />--%>
                  <html:hidden property="path"/>
                  <html:textarea property="source" style="width:810px;height:570px"></html:textarea>
                </html:form>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
    <script language="JavaScript" type="text/Javascript">
      var msg = '<html:messages id="message" message="true"><bean:write name="message"/></html:messages>';
      if(msg!='')
      alert(msg);
      var currentPath='<%=request.getParameter("path")%>';
      function createCSS(){
        var url = "<html:rewrite module='/common' page='/modalWrapper.jsp'/>?title="+encodeURIComponent("新建样式文件")+"&url=<html:rewrite module="/site/css" page="/createCSS.do?path="/>"+currentPath;
        var ret = showModalDialog(url, window, "dialogWidth:300px;dialogHeight:190px;help:no;scrollbars:yes;status:no");
        if(ret!=null)
          top.index.CreateCssSuccess(ret);
      }
      function saveFile(){
        document.forms[0].submit();
      }
      function deleteCSS(){
        if(confirm('删除样式表单将有可能影响整个网站,并且无法恢复，请确定'))
          document.location='<html:rewrite module="/site" page="/css/deleteCSS.do"/>?path='+encodeURIComponent(currentPath);
      }
      function reSize(){
        document.forms[0].source.style.height=window.screen.availHeight-window.screenTop-62;
        document.forms[0].source.style.width=window.screen.availWidth-window.screenLeft-10;
      }
      reSize();
    </script>
  </body>
</html>
