<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form action="save" method="POST">
    <input type="hidden" name="defaultCategoryId" value="<bean:write name='defaultCategory' property="ID" ignore="true"/>"/>
    <tr>
      <td class="pannelDiv" rowspan="2">
        <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
          <option value="0" selected="selected">基本设置</option>
          <option value="1">域名设置</option>
          <option value="2">图片设置</option>
        </select>
      </td>
      <td>
        <fieldset>
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
            <tr>
              <td class="formTitle" valign="top">内容来源</td>
              <td class="formComponent" valign="top">
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
              <td class="formTitle">内容类别</td>
              <td class="formComponent"><html:hidden property="preference(category)"/><input type="text" name="categoryName" readonly="readonly" value="<bean:write name="category" property="name" ignore="true"/>" />
                <button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(category)'],form.elements['categoryName'])">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">显示属性</td>
              <td class="formComponent">
                <html:hidden property="preference(field)"/>
                <input type="text" name="fieldName" readonly="readonly" value="<bean:write name="propertyName" ignore="true"/>" />
                  <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(category)'],form.elements['preference(field)'],form.elements['fieldName'],['0','1','2','3','4','5','6','7','9','10'])">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">显示图片个数</td>
              <td class="formComponent">
                <html:text property="preference(picNum)"/>
              </td>
            </tr>
            <tr>
                <td></td>
                <td><html:checkbox style="width:25px" property="preference(hasAlt)" styleId="a1" value="true"/><label for="a1">不显示Alt属性</label></td>
              </tr>
            <tr>
              <td class="formTitle">Alt关联属性</td>
              <td class="formComponent">
                <html:hidden property="preference(altfield)"/>
                <input type="text" name="altFieldName" readonly="readonly" value="<bean:write name="altProperty" property="name" ignore="true"/>" />
                  <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(category)'],form.elements['preference(altfield)'],form.elements['altFieldName'])">选择...</button>
                  <button type="button" class="commonbut" id="clear" onclick="clearTxt(form.elements['preference(altfield)'],form.elements['altFieldName'])">清空</button>
              </td>
            </tr>
            <tr>
                <td class="formTitle">类型</td>
                <td class="formComponent"><html:select property='<%= "preference(type)" %>' onchange="showChannel(this)" style="width:210px">
                  <html:option value="item">简单属性块</html:option>
                  <html:option value="index">栏目页</html:option>
                </html:select></td>
              </tr>
              <tbody id="selectChannel" style="display:none">
              	<tr>
                  <td class="formTitle">跳转到</td>
                  <td class="formComponent">
                      <html:hidden property="preference(channel)"/>
                      <input type="text" name="channelName" <logic:notEmpty name="channel">value="<bean:write name="siteTemplate" property="displayName" ignore="true"/>.<bean:write name="channel" property="displayName" ignore="true"/>"</logic:notEmpty>/><button type="button" class="commonbut" onclick="chooseColumn('<bean:write name="siteTemplate" property="name"/>',this.form.elements['preference(channel)'],this.form.elements['channelName'])">选择...</button>
                  </td>
                </tr>
              </tbody>
              <tr>
                <td class="formTitle">打开方式</td>
                <td><html:select property="preference(newWindows)" styleId="itemColumn" style="width:210px">
                  <html:option value="_self">原窗口</html:option>
                  <html:option value="_blank">新窗口</html:option>
                  <html:option value="_parent">父窗口</html:option>
                  <html:option value="_top">顶级窗口</html:option>
                </html:select>
              </td>
            </tr>
            <tr>
                <td class="formTitle">自定义目标</td>
                <td class="formComponent"><html:text property="preference(target)"></html:text></td>
              </tr>
            
         </table>
        </fieldset>
        <fieldset style="display:none">
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
            <tr>
              <td class="formTitle" valign="top">域名来源</td>
              <td class="formComponent" valign="top">
                <table border=0 cellpadding=0 cellspacing=0>
                  <tr>
                    <td>
                      <html:radio style="width:25px" styleId="b1" property="preference(siteType)" value="default">
                        <label for="b1">使用当前节点所有者的网站</label></html:radio>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <html:radio style="width:25px" styleId="b2" property="preference(siteType)" value="site"><label for="b2">使用指定的网站</label></html:radio>
                      <html:select property="preference(specifySite)">
                      		<logic:iterate id="site" name="sites">
	                      		<bean:define id="domain"><cms:node name="site" propertyName="domain"/></bean:define>
	                      		<bean:define id="displayName"><cms:node name="site" propertyName="displayName"/></bean:define>
                      			<html:option value="${domain}">${displayName}</html:option>
                      		</logic:iterate>
                      </html:select>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <html:radio style="width:25px" styleId="b3" property="preference(siteType)" value="user"><label for="b3">使用当前登录用户</label></html:radio>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <html:radio style="width:25px" styleId="b4" property="preference(siteType)" value="custom"><label for="b4">自定义&nbsp;</label></html:radio><html:text style="width:196px;" property="preference(customValue)"></html:text>
                    </td>
                  </tr>
                </table>
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
              <td class="formTitle">替换文字</td>
              <td class="formComponent"><html:text property="preference(alt)"/></td>
            </tr>
            <!-- <tr>
              <td class="formTitle"></td>
              <td><html:checkbox style="width:25px" property="preference(showPreview)" styleId="a0" value="true"/><label for="a0">鼠标滑过显示原图</label></td>
            </tr> -->
            <tr>
              <td class="formTitle">默认图片</td>
              <td class="formComponent">
                <html:text property="preference(defaultImage)"/><button type="button" class="commonbut" onclick="openSelectorFileSelector(this.form['preference(defaultImage)']);">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">样式</td>
              <td class="formComponent">
                <html:text property="preference(style)"/>
                <button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(style)'],'')">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">&nbsp;</td>
              <td class="formComponent">
                <html:checkbox styleId="cacheImage" value="true" property="preference(cacheImage)"></html:checkbox>
                <label for="cacheImage">将图片缓存到文件系统</label>
              </td>
            </tr>
            <tr>
              <td class="formTitle">&nbsp;</td>
              <td class="formComponent">
                <html:checkbox styleId="scalingByRatio" value="true" property="preference(scalingByRatio)"></html:checkbox>
                <label for="scalingByRatio">按原比例展示图片</label>
              </td>
            </tr>
         </table>
        </fieldset>
        <div class="toolbar">
          		<button type="button" onclick="validate(this.form)">保存</button>
          		<button type="button" onclick="window.parent.close()">取消</button>
        </div>
      </td>
    </tr>
  </portlet:form>
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
      <logic:equal value="index" name="configForm" property="preference(type)">
      	document.getElementById( "selectChannel" ).style.display='';
      </logic:equal>
      function showChannel(oSelect){
    	  if(oSelect.value=="index"){
    		  document.getElementById( "selectChannel" ).style.display='';
    	  }else{
    		  document.getElementById( "selectChannel" ).style.display='none';
    	  }
      }

      function validate(oForm){
   		if(validateForward(oForm)==false){
   			return false;
   		}
   		validatorRequired(new Array(oForm.elements['preference(field)']),new Array('显示属性'),oForm);
   	  }

     
  </script>
