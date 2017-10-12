<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<%@ page import="org.apache.commons.collections.IteratorUtils" %>
<tiles:insert definition="content_frame">
  <tiles:put name="title">创建内容分类项</tiles:put>
  <tiles:put name="body">
    <html:form action="insertDictDetails.do" enctype="multipart/form-data" method="POST">
      <input type="hidden" name="parentId" value="<bean:write name="parentId"/>" />
      <table width="100%" class="sheetClass" cellpadding="2" cellspacing="0">
        <logic:iterate id="property" name="properties">
          <bean:define id="definitionId" name="property" property="declaringNodeDefinition.ID" type="java.lang.String" />
          <bean:define id="propId" name="property" property="ID" type="java.lang.String" />
          <logic:equal value="10" name="property" property="type">
            <tr>
              <th><bean:write name="property" property="name"/></th>
              <td>
                <logic:present name='<%= "errors."+propId %>'><span class="errors" id="<%= definitionId.replace("-", "_") + "_" + propId %>.tips"><bean:write name="property" property="description"/> </span></logic:present>
                <logic:notPresent name='<%= "errors."+propId %>'><span class="formTips" id="<%= definitionId.replace("-", "_") + "_" + propId %>.tips"><bean:write name="property" property="description"/> </span></logic:notPresent>
              </td>
            </tr>
            <tr>
              <td class="formComponent" colspan="2">
                <fulong:propertyEditor definition="property" propValues='<%= definitionId.replace("-", "_") + "_" + propId + ".values" %>'/>
              </td>
            </tr>
          </logic:equal>
          <logic:notEqual value="10" name="property" property="type">
            <tr>
              <th><bean:write name="property" property="name"/></th>
              <td>
                <fulong:propertyEditor definition="property" propValues='<%= definitionId.replace("-", "_") + "_" + propId + ".values" %>'/>
                  <logic:present name='<%= "errors."+propId %>'><span class="errors" id="<%= definitionId.replace("-", "_") + "_" + propId %>.tips"><bean:write name="property" property="description"/> </span></logic:present>
                  <logic:notPresent name='<%= "errors."+propId %>'><span class="formTips" id="<%= definitionId.replace("-", "_") + "_" + propId %>.tips"><bean:write name="property" property="description"/> </span></logic:notPresent>
              </td>
            </tr>
          </logic:notEqual>
        </logic:iterate>
      </table>
      <div class="operation">
        <button id="save" class="commonbut" onclick="checkSubmit();">保存</button>
      </div>
    </html:form>
    <cms:nodeValidator propertyDefinitions="properties" formName="dictDetailsForm" categoryName="entry">
      <script type="text/Javascript" language="Javascript">
      //在应用时，必须包含这两个脚本文件，用来处理对属性property验证成功或者失败时的回调
      function validatedictDetailsFormSuccess(property){
        //    document.all(property+".tips").innerHTML="填写正确";
        document.all(property+".tips").className="formTips";
      }

      //在应用时，必须包含这两个脚本文件，用来处理对属性property验证成功或者失败时的回调
      function validatedictDetailsFormFailed(property){
        document.all(property+".tips").className="errors";
        //document.all(property).focus();
      }
      </script>
    </cms:nodeValidator>
    <script type="text/Javascript" language="Javascript">
      function timeCheck(str){
        var re = new RegExp("^([0-9]{4})-{1}([0-9]{1,2})-{1}([0-9]{1,2})[ ]{1}([0-9]{1,2}):{1}([0-9]{1,2})$");
        var ar;
        var res = true;

        if ((ar = re.exec(str)) != null){
          return res;
        }else{
          res = false;
        }

        if (!res){
          alert('时间格式为：yyyy-MM-dd hh:mm');
        }
        return res;
      }

      function checkSubmit(){
        var form=document.forms[0];
        if(checkdictDetailsFormAll(form)){
          form.submit();
        }
      }
    </script>
  </tiles:put>
</tiles:insert>
