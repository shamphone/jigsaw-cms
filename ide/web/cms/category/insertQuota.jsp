<%--for Quota INSERT --%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="content_frame">
<tiles:put name="javascript">
<script language="JavaScript" type="text/Javascript">
function nav_removeSelected(oSelect){
    for(var i=0;i<oSelect.options.length;i++){
      if(oSelect.options[i].selected){
        oSelect.options.remove(i);
        i--;
      }
    }
  }
function appendPassportIdentity(selector) {
    var selector=document.getElementById("memberships");
    var url = "<html:rewrite module='/common' page='/modalWrapper.jsp'/>?title="+encodeURIComponent(encodeURIComponent("搜素用户"))+"&url="+encodeURI('<html:rewrite module="/cms" page="/content/selectPrincipal.do"/>');
    var arr = showModalDialog(url,selector,"dialogWidth:600px;dialogHeight:570px;help:no;scrollbars:yes;status:no");

    if(arr!=null){
        for (var i=0; i<arr.length; i++) {
            var principal = arr[i];
            if (!isContentExist(selector, principal.value)) {
                var oOption = document.createElement("OPTION");
                selector.options.add(oOption);
                oOption.value = principal.value;
                oOption.innerText = principal.text;
            }
        }
    }
}
function isContentExist(selector, value) {
    var is = false;
    var oOptions = selector.options;
    for (var i=0; i<oOptions.length; i++) {
        if (oOptions[i].value == value) {
            is = true;
            break;
        }
    }
    return is;
}

/*输入配额的正确性判断以及无限制情况下的条数框隐藏 */
function checkCount(){
  var unit = document.getElementById("unit").value;
  var countArea = document.getElementById("countArea");
  countArea.style.display='';
  var quotaNo = document.getElementById("quotaNo").value;
  if(quotaNo!=null&&quotaNo!=""){
    if(!isNumber(quotaNo)){
      alert("配额设置中您输入了非法字符！");
      document.getElementById("quotaNo").value = "";
      return false;
    }
  }
    if(unit=="noLimite"){
    countArea.style.display='none';
    }

  else{
    return false;
  }
     return true;
  }

function isNumber(txt){
      var regExp=/^[\d]{1,}$/;//至少一位
      return regExp.test(txt);
    }

function osubmit(){
  var quotaNo = document.getElementById("quotaNo").value;
  var unit = document.getElementById("unit").value;
  var selector=document.getElementById("memberships");
  selectAll(categoryForm.elements["memberships"]);
  var index=selector.selectedIndex;
   if(unit=="noLimite"){
      document.getElementById("quotaNo").value = "-1";
    }
  if(document.getElementById("quotaNo").value == 0)
  {
    alert("配额数不能为0！")
    document.getElementById("quotaNo").value = "";
    return;
  }
    quotaNo = document.getElementById("quotaNo").value;
    if((quotaNo.length < 0) || (index == -1))
    {
      alert("配额对象不能为空！");
    }
    else{
      selectAll(categoryForm.elements["memberships"]);
      document.categoryForm.submit();
    }
  }

function window.onload()
{
   var isAlert = '<bean:write name="isUserToBeMngedExist"/>';
   if(isAlert=='false'){
     alert('没有可管理用户');
     document.getElementById("addBut").disabled=true;
     document.getElementById("deleteBut").disabled=true;
     document.getElementById("tijiao").disabled=true;
     document.getElementById("memberships").disabled=true;
     document.getElementById("countArea").disabled=true;
     document.getElementById("unit").disabled=true;
   }
}
</script>
</tiles:put>
  <tiles:put name="body">
    <html:form action="insertQuota.do">
    <input type="hidden" name="categoryID" value="<bean:write name='categoryID' ignore="true"/>"/>
    <table width="100%" class="sheetClass" cellpadding="2" cellspacing="0" border="1">
      <tr>
        <th colspan="1" rowspan="1" width="100" nowrap align="center">配额对象</th>
        <td>
          <table cellpadding="2" cellspacing="0" border="0">
            <tr>
              <td rowspan="2">
                <select name="memberships" id="memberships" multiple="multiple" size="10" style="width:200px">
              </td>
              <td width="130" align="center">
                <button type="button" onclick="appendPassportIdentity(this.form.memberships)" class="commonbut" id="addBut" style="font-size:10px;width:80px">添加对象</button>
              </td>
            </tr>
            <tr>
              <td width="130" align="center">
                <button type="button" onclick="nav_removeSelected(this.form.memberships)" class="commonbut" id="deleteBut" style="font-size:10px;width:80px">删除对象</button>
              </td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <th width="100">&nbsp;</th>
        <td colspan="2" rowspan="1" width="">
          <span id="countArea" style="display=''">
            <input name="quotaNo" type=text size=6" onchange="checkCount()" maxlength="9"/>
            条/
          </span>
          <select name="unit" id="unit" onchange="checkCount()">
            <option value="yearly">年</option>
            <option value="quarterly">季度</option>
            <option value="monthly">月</option>
            <option value="weekly">周</option>
            <option value="daily">日</option>
            <option value="noLimite">不限</option>
          </select>
        </td>
      </tr>
    </table>
    <div class="operation">
      <button onclick="osubmit()" class="commonbut" id="tijiao">保存配额</button>
    </div>
  </html:form>
  </tiles:put>
</tiles:insert>
