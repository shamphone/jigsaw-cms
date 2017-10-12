<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<tiles:insert definition="default_main_frame">
      <tiles:putList name="pathes">
        <tiles:add>网站管理</tiles:add>
        <tiles:add><a href="siteList.jsp">金卡网站</a></tiles:add>
         <tiles:add>复制网站</tiles:add>
    </tiles:putList>
    <tiles:put name="body">
       <table width="100%" height="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="7" align="center" ></td>
        <td height="510"  align="left" valign="top">

    <h2>复制网站 安徽省商务厅</h2>

    <form name="copyForm" method="POST" action="/cms/admin/site/saveCopy.do">
      <input type="hidden" name="sourceID" value="2358885243501">

      <table class="formTable" cellpadding="2" cellspacing="2">

        <tr>
          <td class="formTitle">网站名称</td>
          <td>

            <input type="text" name="name" maxlength="50" size="40" value="复件安徽省商务厅">
              <div class="formTips">网站名称将显示在客户端浏览器的标题上。</div>
            </td>
          </tr>
          <tr>
            <td class="formTitle">网站域名</td>
            <td>
              http://
              <input type="text" name="root" maxlength="10" size="10" value="newsite187" style="ime-mode:disabled">
                .acs.gov.cn
                <div class="formTips">请提供一个名称，网站在建立之后，用户可以通过这个二级域名来访问。</div>
              </td>
            </tr>
            <tr>
              <td class="formTitle">一级域名</td>
              <td>
                <input type="text" name="domain" size="40" value="" class="imeDisabled">
                <div class="formTips">如果您已经申请了一级域名（例如:www.mysite.com;www.mysite.cn;www.mysite.com.cn等），请确认这个域名已经解析到这个服务器上。网站建立之后，用户即可通过这个域名来访问。 <a href="#">如何配置一级域名</a>
                </div>
              </td>
            </tr>
            <tr>
              <td class="formTitle">网站类别</td>
              <td>
                <select name="categoryID"><option value="2329734441353" selected="selected">金卡网站</option>
<option value="2304149708875">银卡网站</option>
<option value="2284694169577">成长卡网站</option>
<option value="2304011057187">自定义网站</option></select>
              </td>
            </tr>
          </table>
          <div class="operation">
            <input type="button" onclick="history.back()" value="确认创建">&nbsp;
            <input class="commonbut" onclick="history.back()" value="返回" type="button"/>
          </div>

        </form>
      </div>

        </td>
        <td width="7" align="center"></td>
      </tr>
    </table>

  <script language="JavaScript" type="text/javascript">
    function goBack(submitter){
      window.location="chooseTemplate.do?category="+submitter.form.category.value;
    }
    function selectCategory(category){
      var step2=document.all("step2");
      for(var i=0;i<step2.children.length;i++){
        step2.children[i].style.display="none";
      }
      step2.children(category).style.display="";
    }
    function Trim(str){
      return str.replace("/(^\s*)|(\s*$)/g","");
    }
    function checkForm(){
      if(Trim(siteForm.name.value)==""){
        alert("请输入网站名称！");
        siteForm.name.focus();
        return false;
      }
      if(Trim(siteForm.root.value)==""){
        alert("请输入存放目录！");
        siteForm.root.focus();
        return false;
      }
      var rule_value=/^\w+$/;
      if (!rule_value.test(siteForm.root.value)){
        alert("请输入符合要求的字符！");
        siteForm.root.focus();
        return false;
      }
      if(siteForm.memo.value.length>200){
        alert("请减少网站描述文字！");
        siteForm.memo.focus();
        return false;
      }
      return true;
    }
    function checkSubmit(src){
      if(checkForm()){
        src.disabled=true;
        return true;
      }
      return false;
    }

    function preview(theSelection){
      var selectId=theSelection.options[theSelection.selectedIndex].value;
      window.open("/cms/site.do?siteId="+selectId,"_blank");
    }
    function cancel(){
      document.forms[0].action="/cms/admin/site/index.do";
      document.forms[0].submit();
    }
    </script>
    </tiles:put>
</tiles:insert>
