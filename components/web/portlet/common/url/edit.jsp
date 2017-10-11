<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
      <table cellpadding="0" cellspacing="0" border="0">
    <portlet:form action="save" method="POST" onsubmit="return validateForm(this)">
        <tr>
          <td class="pannelDiv" rowspan="2">
            <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
              <option value="0" selected="selected"><bean:message key="com.fulong.portal.portlet.url.edit.option0.baseSet" bundle="portalUrl"/></option>
            </select></td>
            <td><fieldset>
              <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
                <tr>
                  <td class="formTitle"><bean:message key="com.fulong.portal.portlet.url.edit.formTitle1.pageAddress" bundle="portalUrl"/></td>
                  <td class="formComponent"><html:text property="preference(url)" size="30"/><br /><em>例如：http://www.fulong.com.cn</em></td>
                  </tr><tr>
                    <td class="formTitle"><bean:message key="com.fulong.portal.portlet.url.edit.formTitle2.code" bundle="portalUrl"/></td>
                    <td><html:select property="preference(encoding)" >
                  <html:option value=""><bean:message key="com.fulong.portal.portlet.url.edit.formTitle2.auto" bundle="portalUrl"/></html:option>
                  <html:options collection="charsets" labelProperty="label" property="value"/>
                </html:select></td>
                    </tr>
                  <td class="formTitle"><bean:message key="com.fulong.portal.portlet.url.edit.formTitle3.waitTime" bundle="portalUrl"/></td>
                  <td class="formComponent">
                    <html:text property="preference(timeout)" onblur="validatorInteger(this)"/><bean:message key="com.fulong.portal.portlet.url.edit.formTitle3.second" bundle="portalUrl"/>
                    <br /><em>页面获取超时等待时间</em>
                  </td>
                  </tr>
                  </table>
          </fieldset>
          
            <div class="toolbar">
	       		<button type="button" onclick="ok(this.form)">保存</button>
	          	<button type="button" onclick="window.parent.close()">取消</button>
            </div></td>
        </tr> </portlet:form>
      </table>
      <script type="text/javascript" language="javascript">
      function ok(oForm){
          oForm.submit();
      }
      function validateForm(oForm){
	      var oUrl = oForm.elements["preference(url)"];
	      if(oUrl.value==""){
			alert("页面地址不能为空！")
		  	return false;
	      }
		  if(!isURL(oUrl.value)){
			 alert("页面地址不是合法的url！");
			 return false;
		  }
		
		  //如果输入的url是相对于上下文的绝对路径,则拼上 当前网站的 protocol :// hostname:port /contextPath
	      if(oUrl.value.indexOf("/")==0){
	    	  oUrl.value = window.document.location.protocol + '//' + window.document.location.host+'/' +window.parent.dialogArguments.window.oTemplate.name + oUrl.value;
	      }

	      var u = window.document.location.host.substring(0,window.document.location.host.indexOf(":"))
	      var currURL = window.document.location.protocol + '//' + u+ window.parent.dialogArguments.window.oChannel.path;
	      if(oUrl.value == currURL || oUrl.value == (u+ window.parent.dialogArguments.window.oChannel.path) || oUrl.value == window.document.location.host+window.parent.dialogArguments.window.oChannel.path || oUrl.value == window.document.location.protocol + '//'+window.document.location.host+window.parent.dialogArguments.window.oChannel.path){
			alert("页面地址不能为当前栏目页地址！")
			return false;
	      }
	      return true;
       }

       /**
       	*  检验url的合法性
       	*  两个格式url地址可通过检验：1、全路径http://www.fulong.com.cn/sites/root/index.jsp
       	*  2、相对于上下文的绝对路径 /sites/root/index.jsp
        */
       function isURL(str_url){
    	    var strRegex = "^(((https|http|ftp|rtsp|mms)?://)" 
    	    + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@ 
    	          + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184 
    	          + "|" // 允许IP和DOMAIN（域名）
    	          + "([0-9a-z_!~*'()-]+\.)*" // 域名- www. 
    	          + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名 
    	          + "[a-z]{2,6})" // first level domain- .com or .museum 
    	          + "(:[0-9]{1,4})?)?" // 端口- :80 
    	          + "((/?)|" // a slash isn't required if there is no file name 
    	          + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$"; 
    	          var re=new RegExp(strRegex,"i"); 
    	          if (re.test(str_url)){
    	              return (true); 
    	          }else{ 
    	              return (false); 
    	          }
    	      }
       
       </script>
