<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>选择模板</title>
    <link type="text/css" rel="stylesheet" href="<html:rewrite  module="/site" page='/style/selector.css'/>"/>
    <link type="text/css" rel="stylesheet" href="<html:rewrite  module="/site" page='/style/tree.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page='/style/style.css'/>">
    <script type="text/javascript" src="<html:rewrite page="/script/common.js" module="/common"/>"></script>
  </head>

  <body>
    <html:form action="/templateSelect.do" method="POST">
      <bean:define id="groupID" name="groupID" type="java.lang.String"/>
      <div class="selector">
        模板类别：
        <html:select property="groups" onchange="changeTemp(this.value)">
          <html:options collection="groups" property="ID" labelProperty="displayName" />
        </html:select>
      </div>
      <div class="main">
        <div id="fileList">
          <logic:present name="templates">
            <table>
              <tr>
                <logic:notEqual value="0" name="templates" property="size">
                <logic:iterate id="template" name="templates" indexId="num">
                  <bean:define id="num" name="num" type="Integer"/>
                  <%
                  if(num!=null){
                    int No = num.intValue();
                    if(No>0&&(No%7==0)){
                      out.print("</tr><tr>");
                    }
                  }
                  %>
                  <td>
                    <div class="folderGrid">
                      <div class="image">
                        <img width="90" height="90" src="../sites/<bean:write name="template" property="name"/>/preview.jpg" alt=""/>
                      </div>
                      <div class="itemMark">
                        <bean:define id="siteModelTemp" name="template" property="ID"/>
                        <bean:define id="siteModelTempName" name="template" property="name"/>
                        <%String temp = (String)siteModelTemp+"*"+(String)siteModelTempName;%>
                        <html:radio property="siteModel" value="<%=temp%>"><span><bean:write name="template" property="displayName" ignore="true"/></span>
                        </html:radio>
                      </div>
                    </td>
                  </logic:iterate>
                </logic:notEqual>
                  </tr>
                </table>
              </div>

            </logic:present>
          </div>
        </div>
        <div class="operation">
          <button onclick="ok(this)" class="commonbut" id="tijiao">确认</button>
          <button onclick="window.close()" class="commonbut" id="back">取消</button>
        </div>
      </html:form>
      <script type="text/javascript" language="javascript">
        function changeTemp(tempID){
          window.location = "templateSelect.do?groupID="+'<bean:write name="groupID"/>'+"&groups="+tempID;
        }
        function ok(submitter){
          var item = submitter.form.siteModel;
          var ret=new Object();
          if(GetRadioObject(item)!=null){
            var idAndName = GetRadioObject(item).value;
            var temp = new Array();
            temp = idAndName.split("*");
            ret.displayName1=GetRadioObject(item).nextSibling.innerText;
            ret.name1=temp[0];
            ret.imgSrcName = temp[1];
            window.returnValue=ret;
            window.close();
          }
          else{
            alert('请选择一个模板！');
          }
        }
        </script>
        </body>
      </html>

