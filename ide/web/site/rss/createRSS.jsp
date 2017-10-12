<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<tiles:insert definition="dialog_frame">
  <tiles:put name="title">创建RSS页面</tiles:put>
  <tiles:put name="javascript">
   
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/dialog.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms" page="/classes/cmsdialog.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="create.js"></script>
    <script language="Javascript" type="text/Javascript">
		window.onload = function() {
		}
    </script>
    <style type="text/css">
      .errorTip {color:red}
      .indent {margin-left:6px}
      .title {text-align:right}
      select {width:120px}
    </style>
  </tiles:put>
  <tiles:put name="dialog">
    <div class="errorTip indent"><html:errors property="url"/></div>
    <html:form action="createRSS.do" onsubmit="return validate(this)">
      <div style="color:red; margin-left:6px"><html:errors property="exist"/></div>
      <table border="0" cellpadding="2" cellspacing="2" align="center" width="100%">
        <html:hidden property="templateID"/>
        <tr>
          <td nowrap="nowrap">频道名称</td>
          <td>
            <html:text onchange="validateChannel(this.form)" style="IME-MODE: disabled" property="title" maxlength="32" title="系统中的ID及文件名称，全模板唯一。"/>
          </td>
          <td>频道URL</td>
          <td>
            <html:text property="link" title="能响应该频道的网站栏目URL"></html:text>
          </td>
        </tr>
        <tr>
          <td>语言</td>
          <td>
          	 <html:select property="language"  title="频道所使用的语言">
              <logic:iterate id="language" name="languages">
                <bean:define id="ID" name="language" property="ID" type="String"/>
                <html:option value="<%=ID%>"><cms:node name="language" propertyName="title"/></html:option>
              </logic:iterate>
            </html:select>
          </td>
           <td>样式</td>
          <td>
	          <html:select multiple="true" size="5" property="style" style="width:133px">
	          	<html:options name="styles"/>
	          </html:select>
          </td>
           <td>版权声明</td>
          <td>
             <html:text property="copyright" title="版权声明"></html:text>
          </td>
        </tr>
        <tr>
          <td >描述</td>
          <td colspan="3">
            <html:textarea property="description" cols="46" title="对该频道的描述信息"></html:textarea>
          </td>
        </tr>
         <tr>
          <td nowrap="nowrap">负责人Email</td>
          <td>
            <html:text property="managingEditor" title="内容负责人的Email"></html:text>
          </td>
          <td nowrap="nowrap">技术人员Email</td>
          <td>
             <html:text property="webMaster" title="技术人员的Email"></html:text>
          </td>
        </tr>
        <tr>
          <td>所属分类</td>
          <td>
            <html:text property="category" title="对该频道的描述信息"></html:text>
          </td>
          <td nowrap="nowrap">生成频道程序名</td>
          <td>
             <html:text property="generator" title="生成该频道的程序名称"></html:text>
          </td>
        </tr>
         <tr>
          <td>文档url</td>
          <td>
            <html:text property="docs" title="指向rss格式文档的url地址"></html:text>
          </td>
          <td>有效缓存时间</td>
          <td>
             <html:text property="ttl" title="cache的有效保存时间。"></html:text>
          </td>
        </tr>
      </table>
      <div class="operation">
        <button id="btnOk" type="submit">确定</button>
        <button id="btnCancel" onclick="window.close()">取消</button>
      </div>
    </html:form>
  </tiles:put>
</tiles:insert>
