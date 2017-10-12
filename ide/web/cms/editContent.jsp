<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>修改内容</title>
    <link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/style.css"/>">
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/stat.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/area.jsp"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/date.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/industry.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/form.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/editor.jsp"/>"></script>
  </head>
  <script type="text/Javascript" language="Javascript">
    <logic:present name="ok">
      <logic:equal value="true" name="ok">
        window.returnValue="true";
        window.close();
      </logic:equal>
    </logic:present>
    </script>
    <body>
      <div style="height:480px;overflow-y: scroll;">
        <table width="96%" class="tableClass" cellpadding="2" cellspacing="0">
          <html:form  action="updateContent.do" enctype="multipart/form-data" method="POST">
            <tr>
              <td valign="top">
                <table border="1" cellpadding="2" cellspacing="0" class="sheetClass" width="100%" id="contentEditor">
                  <input type="hidden" name="category" value="<logic:present name="category"><bean:write name='category' property='ID'/></logic:present>"/>
                  <input type="hidden" name="contentId" value="<logic:present name="content"><bean:write name='content' property='ID'/></logic:present>"/>
                  <input type="hidden" name="thrIDs" value="<bean:write name='allIDs'/>"/>
                  <logic:iterate id="property" name="properties">
                    <bean:define id="propID" name="property" property="ID" type="java.lang.String" />
                    <tr>
                      <th><bean:write name="property" property="name"/></th>
                      <td width="85%">
                        <logic:present name='<%= "errors."+propID %>'><span class="errors" id="<bean:write name="property" property="ID"/>.tips"><bean:write name="property" property="description"/> </span></logic:present>
                        <logic:notPresent name='<%= "errors."+propID %>'><span class="formTips" id="<bean:write name="property" property="ID"/>.tips"><bean:write name="property" property="description"/> </span></logic:notPresent>
                      </td>
                    </tr>
                    <tr>
                      <td class="formComponent" colspan="2">
                        <logic:notEqual value="0" name="property" property="type">
                          <fulong:propertyEditor definition="property" propValues='<%= propID+".values" %>'/>
                          </logic:notEqual>
                        </td>
                      </tr>
                    </logic:iterate>
                  </table>
                </td>
              </tr>
            </html:form>
          </table>
        </div>
        <div class="operation">
          <button type="button"  onclick="checkSubmit()" class="commonbut" id=""/>保存</button>
          <button name="name3" type="button" onclick="goBack()" class="commonbut" id="back">返回</button>
        </div>
        <cms:nodeValidator propertyDefinitions="properties" formName="contentForm" categoryName="category" contentName="content">
          <script type="text/Javascript" language="Javascript">
            //在应用时，必须包含这两个脚本文件，用来处理对属性property验证成功或者失败时的回调
            function validatecontentFormSuccess(property){
              //    document.all(property+".tips").innerHTML="填写正确";
              document.all(property+".tips").className="formTips";
            }
            //在应用时，必须包含这两个脚本文件，用来处理对属性property验证成功或者失败时的回调
            function validatecontentFormFailed(property){
              document.all(property+".tips").className="errors";
              document.all(property).focus();
            }
            </script>
            </cms:nodeValidator>
            <script type="text/Javascript" language="Javascript">
            var oTable = document.getElementById("contentEditor");

            /*document.body.clientHeight = oTable.offsetHeight;
            alert(document.body.clientHeight)
            if(document.body.clientHeight>100){
              document.body.clientHeight=document.body.clientHeight-100;
              alert(document.body.pixelHeight)
            }*/
              function checkSubmit(){
                var form=document.forms[0];
                if(checkcontentFormAll(form)){
                  form.submit();
                }
              }
              function goBack(){
                window.close();
              }
              </script>
              </body>
            </html>
