<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form action="save" method="POST">
    <tr>
      <td class="pannelDiv" rowspan="2">
        <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
          <option value="0" selected="selected">基本设置</option>
          <option value="1">域名设置</option>
          <option value="2">高级设置</option>
        </select>
      </td>
      <td>
        <fieldset>
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
            <tr>
              <td class="formTitle">属性</td>
              <td class="formComponent">
                <html:hidden property="preference(propertyId)"/>
                <input type="text" id="fieldName" readonly="readonly" value="<bean:write name="propertyName" ignore="true"/>" />
                <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition('<bean:write name="definition" ignore="true"/>')">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">字符宽度</td>
              <td class="formComponent">
                <html:text property="preference(size)" onblur="validatorInteger(this)"/>
              </td>
            </tr>
            <tr>
              <td class="formTitle">Tab键顺序</td>
              <td class="formComponent">
                <html:text property="preference(tabindex)"/>
              </td>
            </tr>
            <tr>
              <td class="formTitle">上传控件样式</td>
              <td class="formComponent">
                <html:text property="preference(fileStyle)"/><button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(fileStyle)'],'')">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">预览控件样式</td>
              <td class="formComponent">
                <html:text property="preference(previewStyle)"/><button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(previewStyle)'],'')">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">最大上传图片</td>
              <td class="formComponent">
                <html:text property="preference(maxSize)"/>&nbsp;&nbsp;KB</button>
              </td>
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
        <fieldset style="display:none; ">
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
            <tr>
              <td class="formTitle">预览控件宽度</td>
              <td class="formComponent">
                <html:text property="preference(previewWidth)"/>
              </td>
            </tr>
            <tr>
              <td class="formTitle">预览控件高度</td>
              <td class="formComponent">
                <html:text property="preference(previewHeight)"/>
              </td>
            </tr>
            <tr>
              <td class="formTitle">默认图片</td>
              <td class="formComponent">
                <html:text property="preference(defaultImage)"/><button type="button" class="commonbut" onclick="openSelectorFileSelector(this.form['preference(defaultImage)']);">选择...</button>
              </td>
            </tr>
          </table>
        </fieldset>
        <div class="toolbar">
          <button type="button" onclick="validatorRequired(new Array(form.elements['preference(propertyId)']),new Array('属性'),this.form)">保存</button>
          <button type="button" onclick="window.parent.close()">取消</button>
        </div>
      </td>
    </tr>
  </portlet:form>
</table>
<script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
<script type="text/javascript" language="javascript">
  /**
  *选择待显示的属性
  */
  function searchPropertyDefinition($categoryId){
    var arr = CMSDialog.PropertyDefinitionSelector($categoryId,['0','1','2','3','4','5','6','7','9','10']);
    if(arr!=null){
      document.getElementById("fieldName").value = arr.name;
      document.getElementsByName("preference(propertyId)")[0].value = arr.ID;
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
  function preview($div, $src)
  {
    var tmp = "<img src=\"" + $src + "\" onload=\"adjustment(this, document.getElementById('" + $div.id + "'))\" style=\"position:absolute;\"/>";
    $div.innerHTML = tmp;
  }
  function adjustment($img, $div)
  {
    var proportion = $div.style.pixelHeight / $div.style.pixelWidth;
    var realProportion = $img.offsetHeight / $img.offsetWidth;
    if ($img.clientWidth > $div.style.pixelWidth || $img.clientHeight > $div.style.pixelHeight) {
      if (realProportion >= 1)
      $img.height = $div.style.pixelHeight - 2;
      else
      $img.width = $div.style.pixelWidth - 2;
    }
    $img.style.top = $div.offsetTop + ($div.style.pixelHeight - $img.clientHeight)/2;
    $img.style.left = $div.offsetLeft + ($div.style.pixelWidth - $img.clientWidth)/2;
  }
</script>
