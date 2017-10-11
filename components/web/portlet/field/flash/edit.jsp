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
          <option value="2">布局设置</option>
          <option value="3">影片设置</option>
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
                      <html:radio style="width:25px" style="width:25px" styleId="b1" property="preference(contentType)" value="default">
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
                <label for="cacheImage">缓存</label>
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
        <fieldset style="display:none">
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
            <tr>
              <td class="formTitle">宽度</td>
              <td class="formComponent"><html:text property="preference(width)"/></td>
            </tr>
            <tr>
              <td class="formTitle">高度</td>
              <td class="formComponent"><html:text property="preference(height)"/></td>
            </tr><!--
            <tr>
              <td class="formTitle">水平间距</td>
              <td class="formComponent"><html:text property="preference(hspace)" onblur="validatorInteger(this)"/></td>
            </tr>
            <tr>
              <td class="formTitle">垂直间距</td>
              <td class="formComponent"><html:text property="preference(vspace)" onblur="validatorInteger(this)"/></td>
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
            </tr>-->
          </table>
        </fieldset>
        <fieldset style="display:none">
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
            <tr>
              <td class="formTitle">画面质量</td>
              <td class="formComponent">
                <html:select property="preference(quality)">
                  <html:option value="Low">低</html:option>
                  <html:option value="High">高</html:option>
                </html:select>
              </td>
            </tr>
            <!--<tr>
              <td class="formTitle">影片对齐方式</td>
              <td class="formComponent">
                <html:select property="preference(salign)">
                  <html:option value="">默认(居中)</html:option>
                  <html:option value="L">左侧</html:option>
                  <html:option value="R">右侧</html:option>
                  <html:option value="T">顶端</html:option>
                  <html:option value="B">底端</html:option>
                  <html:option value="TL">顶端左侧</html:option>
                  <html:option value="TR">顶端右侧</html:option>
                  <html:option value="BL">底端左侧</html:option>
                  <html:option value="BR">底端右侧</html:option>
                </html:select>
              </td>
            </tr>
            <tr>
              <td class="formTitle">缩放比例</td>
              <td class="formComponent">
                <html:select property="preference(scale)">
                  <html:option value="">默认(全部显示)</html:option>
                  <html:option value="NoBorder">没有边框</html:option>
                  <html:option value="ExactFit">最佳</html:option>
                </html:select>
              </td>
            </tr>-->
            <tr>
              <td class="formTitle">&nbsp;</td>
              <td class="formComponent">
                <html:checkbox style="width:25px" property="preference(play)">自动播放</html:checkbox>
              </td>
            </tr>
            <tr>
              <td class="formTitle">&nbsp;</td>
              <td class="formComponent">
                <html:checkbox style="width:25px" property="preference(loop)">循环播放</html:checkbox>
              </td>
            </tr>
         </table>
        </fieldset>
        <div class="toolbar">
          		<button type="button" onclick="validatorRequired(new Array(form.elements['preference(field)']),new Array('显示属性'),this.form)">保存</button>
          		<button type="button" onclick="window.parent.close()">取消</button>
        </div>
      </td>
    </tr>
  </portlet:form>
</table>
<script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>

