<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
  <table cellpadding="0" cellspacing="0" border="0">
<portlet:form action="save" method="POST">
  <tr>
      <td class="pannelDiv" rowspan="2">
        <select id="pannelSelect" name="pannelSelect" size="10" onchange="selectPanel(this)">
          <option value="0" selected="selected">基本设置</option>
          <option value="1">图片设置</option>
        </select></td>
        <td><fieldset>
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
            <tr>
              <td class="formTitle">QQ号码来源</td>
              <td class="formComponent">
                <table border=0 cellpadding=0 cellspacing=0>
                  <tr>
                    <td>
                      <html:radio style="width:25px" styleId="b1" property="preference(contentType)" value="default">
                        <label for="b1">使用URL参数指定内容</label></html:radio>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <html:radio style="width:25px" styleId="b2" property="preference(contentType)" value="user"><label for="b2">使用当前登录用户</label></html:radio>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <html:radio style="width:25px" styleId="b3" property="preference(contentType)" value="site"><label for="b3">使用当前网站所属用户</label></html:radio>
                      </td>
                    </tr>
                  </table>
              </td>
            </tr>
            <tr>
                <td>内容类别</td>
                <td><html:hidden property="preference(category)"/><input type="text" name="categoryName" readonly="readonly" value="<bean:write name="category" property="name" ignore="true"/>" />
                  <button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(category)'],form.elements['categoryName'],form.elements['fieldName'])">选择...</button>
                </td>
              </tr>
              <tr>
                <td class="formTitle" valign="top">显示属性</td>
                <td class="formComponent" valign="top">
                  <html:hidden property="preference(field)"/>
                  <input type="text" name="fieldName" readonly="readonly" value="<bean:write name="propertyName" ignore="true"/>" />
                  <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(category)'],form.elements['preference(field)'],form.elements['fieldName'],['0','2','4','5','6','7','8','9'])">选择...</button>
                </td>
              </tr>
              <tr>
                <td class="formTitle" valign="top">引用/复合属性</td>
                <td class="formComponent" valign="top">
                  <html:hidden property="preference(refField)"/>
                  <html:hidden property="preference(refDefinition)"/>
                  <input type="text" name="comDefinitionName" readonly="readonly" value="<bean:write name="property" property="name" ignore="true"/>" />
                  <button type="button" class="commonbut" id="search" onclick="searchRefPropertyDefinition(form.elements['preference(category)'],form.elements['preference(refField)'],form.elements['preference(refDefinition)'],form.elements['comDefinitionName'])">选择...</button>
                  <button type="button" class="commonbut" id="clear" onclick="clearTxt(form.elements['preference(refField)'],form.elements['comDefinitionName'])">清空</button>
                </td>
              </tr>
            <tr>
              <td class="formTitle">alt文字</td>
              <td class="formComponent">
                <html:text property="preference(text)"/>
              </td>
            </tr>
            <tr>
              <td class="formTitle">样式</td>
              <td class="formComponent">
                <html:text property="preference(style)"/>
                <button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(style)'],'')">选择...</button>
              </td>
            </tr>
          </table>
        </fieldset>
        <fieldset style="display:none">
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
            <tr>
              <td class="formTitle">宽度</td>
              <td class="formComponent"><html:text property="preference(width)" onblur="checkNum(this)"/></td>
            </tr>
            <tr>
              <td class="formTitle">高度</td>
              <td class="formComponent"><html:text property="preference(height)" onblur="checkNum(this)"/></td>
            </tr>
            <tr>
              <td class="formTitle">对齐方式</td>
              <td class="formComponent">
                <html:select property="preference(align)">
                  <html:option value="">默认(左对齐)</html:option>
                  <html:option value="right">右对齐</html:option>
                  <html:option value="top">顶端对齐</html:option>
                  <html:option value="texttop">文本上方</html:option>
                  <html:option value="middle">相对垂直居中</html:option>
                  <html:option value="absmiddle">绝对垂直居中</html:option>
                  <html:option value="baseline">基线</html:option>
                  <html:option value="bottom">相对底边对齐</html:option>
                  <html:option value="absbottom">绝对底边对齐</html:option>
                  <html:option value="center">居中</html:option>
                </html:select>
              </td>
            </tr>
            <tr>
              <td class="formTitle">边框粗细</td>
              <td class="formComponent"><html:text property="preference(border)" onblur="validatorInteger(this)"/></td>
            </tr>
            <tr>
              <td class="formTitle">显示图片</td>
              <td class="formComponent">
                <html:text property="preference(defaultImage)"/><button type="button" class="commonbut" onclick="openSelectorFileSelector(this.form['preference(defaultImage)']);">选择...</button>
              </td>
            </tr>
         </table>
        </fieldset>
            <div class="toolbar">
	       		<button type="button" onclick="ok(this.form)">保存</button>
	          	<button type="button" onclick="window.parent.close()">取消</button>
            </div></td>
      </tr></portlet:form>
    </table>
    <script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
  <script type="text/Javascript" language="Javascript">
      function checkNum(obj){
    	  var re=/^[A-Za-z0-9]*$/; 
    	  if(re.test(obj.value)==false){ 
    		  alert("只能输入数字和字母！"); 
    		  }
      }
      function openSelectorFileSelector($oEcho)
      {
          var templateID = window.parent.dialogArguments.template.ID;
          //var url = "/ide/site/resource/index.do?templateID="+templateID;
          var url = "/ide/site/resource/index.jsp?template="+templateID;
          var sOptions = "dialogHeight=600px;dialogWidth=780px;center=yes;resizable=no;status=no";
          var ret = window.showModalDialog(url, null, sOptions);
          if (ret != null && ret[0])
          $oEcho.value = ret[0];
      }

      function validate(oForm){
   		if(validateForward(oForm)==false){
   			return false;
   		}
   		validatorRequired(new Array(oForm.elements['preference(field)']),new Array('显示属性'),oForm);
   	  }

      /**
      *
      *选择引用属性
      **/
      function searchRefPropertyDefinition(oCategory, oID, oRefID, oRefName){
        var arr =  CMSDialog.PropertyDefinitionSelector(oCategory.value,['1','2','3','4','5','6','7','8','10']);
        if(arr!=null&&arr.ID!=null){
          oID.value = arr.ID;
          oRefName.value = arr.name;
          oRefID.value = arr.refID;
        }
      }

      function ok(oForm){
          oForm.submit();
      }
  </script>
    
