<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<tiles:insert definition="dialog_frame">
  <tiles:put name="title">
    网站管理
    <logic:present name="category"><bean:write name="category" property="name"/>网站</logic:present><logic:notPresent name="category">网站搜索</logic:notPresent>
    网站升级
  </tiles:put>
  <tiles:put name="javascript">
    <script type="text/javascript" language="javascript">
      function upgrade(form){
        disableButton();
        if(form.siteModel.value!=null&&form.siteModel.value!=""){
          form.submit();
        }else{
          alert("请选择模板！");
          enableButton();
        }
      }
      function select(){
        var url = "<html:rewrite module='/common' page='/modalWrapper.jsp'/>?title="+encodeURIComponent("选择模板")+"&url="+encodeURI('<html:rewrite module="/common" page="/templateSelect.do?siteID=&categoryID="/>');
        var arr = showModalDialog(url,window,"dialogWidth:780px;dialogHeight:550px;help:no;scrollbars:yes;status:no");
        if(arr!=null){
          document.getElementById("displayName").value = arr.displayName1;
          document.getElementById("siteModel").value = arr.name1;
          document.getElementById("previewIMG").src = "../../sites/"+arr.imgSrcName+"/preview.jpg";
        }
      }
     </script>
  </tiles:put>
  <tiles:put name="body">
    <html:form action="upgrade.do" method="post">
      <input type="hidden" name="categoryID" value="<bean:write name="categoryID" ignore="true"/>"/>
      <input type="hidden" name="siteID" value="<bean:write name="siteID" ignore="true"/>"/>
      <table width="100%" class="tableClass" cellpadding="0" cellspacing="1">
        <tr>
          <th scope="row">网站类别</th>
          <td>
            <html:select property="groups">
              <html:option value="">选择网站类别</html:option>
              <html:options collection="groups" property="ID" labelProperty="displayName" />
            </html:select>
          </td>
        </tr>
        <tr>
          <th scope="row">当前模板</th>
          <td>
            <logic:present name="site" property="template">
              <bean:write name="site" property="template.displayName" ignore="true"/>&nbsp;&nbsp;&nbsp;&nbsp;
              <img width="90" height="90" border="0" src="../../sites/<bean:write name="site" property="template.name"/>/preview.jpg" alt=""/>
            </logic:present>
          </td>
        </tr>
        <tr>
          <th scope="row">备选模板</th>
          <td>
            <input type="hidden" name="siteModel" value=""/>
            <input name="displayName" type="text" size="40" onkeydown="blur()"/>
            <button type="button" onclick="select()" class="commonbut" id="edit">选择</button>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <img id="previewIMG" width="90" height="90" border="0" alt="模板缩略图" src=""/>
          </td>
        </tr>
      </table>
      <div class="operation">
        <button type="button" onclick="upgrade(this.form)" class="commonbut" id="tijiao">升级</button>
        <button type="button" onclick="window.history.go(-1)" class="commonbut" id="back">返回</button>
      </div>
    </html:form>
  </tiles:put>
</tiles:insert>
