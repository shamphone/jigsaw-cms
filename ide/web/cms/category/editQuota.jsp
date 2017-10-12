<%--for Quota INSERT  --%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<tiles:insert definition="content_frame">
<tiles:put name="javascript">
<script language="JavaScript" type="text/Javascript">


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
function checkCountForNolimite(){
  var countArea = document.getElementById("countArea");
  countArea.style.display='';
  checkCount();
}

function isNumber(txt){
      var regExp=/^[\d]{1,}$/;//至少一位
      return regExp.test(txt);
    }
/*提交之前判断配额数量不能为0，如果设为“不限”则将quotaNo空间中数字设为-1*/
function osubmit(){
  var unit = document.getElementById("unit").value;
  var quotaNo = document.getElementById("quotaNo").value;
  if(unit=="noLimite"){
      document.getElementById("quotaNo").value = "-1";
    }
  quotaNo = document.getElementById("quotaNo").value;
  if(document.getElementById("quotaNo").value == 0 || quotaNo.size < 0)
  {
    alert("配额数不能为0!")
    document.getElementById("quotaNo").value = "";
  }
    else{
      document.categoryForm.submit();
    }
  }
function window.onload()
{
   var isAlert = '<bean:write name="isUserToBeMngedExist"/>';
   if(isAlert=='false'){
     alert('对当前用户没有配额权限');
     document.getElementById("tijiao").disabled=true;
   }
}
</script>
</tiles:put>
  <tiles:put name="body">
    <html:form action="insertQuota.do" onsubmit="return do_submit(this)">
    <input type="hidden" name="categoryID" value="<bean:write name='categoryID' ignore="true"/>"/>
    <input type="hidden" name="memberships" id="memberships" size="10" style="width:200px" value="<bean:write name="principalID"/>">
    <table width="100%" class="sheetClass" cellpadding="2" cellspacing="0" border="1">
      <tr>
        <th colspan="1" rowspan="1" width="100" nowrap align="center">配额对象</th>
        <td>
          <logic:equal name="isUserToBeMngedExist" value="true">
         <cms:node name="principal" propertyName="commonname"/>
         </logic:equal>
        </td>
      </tr>
      <tr>
        <th width="100">&nbsp;</th>
        <td colspan="2" rowspan="1" width="">
           <logic:equal name="isUserToBeMngedExist" value="true">
            <bean:define id="principal" name="principalID" />
            <logic:equal value="-1" name="quotaCount">
              <span id="countArea" style="display='none'">
                <input name="quotaNo"  maxlength="9" type="text" size="6" onchange="checkCountForNolimite()" value=""/>条/
              </span>
            </logic:equal>
            <logic:notEqual value="-1" name="quotaCount">
              <span id="countArea" style="display=''">
                <input name="quotaNo"  type=text size=6" onchange="checkCount()"  maxlength="9"  value="<bean:write name="quotaCount"/>"/>
                条/
              </span>
            </logic:notEqual>
            <logic:equal value="yearly" name="quotaUnit" >
              <select name="unit" onchange="checkCount()">
                <option id="1"  value="yearly" selected="selected">年</option>
                <option id="2" value="quarterly">季度</option>
                <option id="3" value="monthly">月</option>
                <option id="4" value="weekly">周</option>
                <option id="5" value="daily">日</option>
                <option id="6" value="noLimite">不限</option>
              </select>
            </logic:equal>
            <logic:equal value="quarterly" name="quotaUnit">
              <select name="unit" onchange="checkCount()">
                <option id="1"  value="yearly">年</option>
                <option id="2" value="quarterly" selected>季度</option>
                <option id="3" value="monthly">月</option>
                <option id="4" value="weekly">周</option>
                <option id="5" value="daily">日</option>
                <option id="6" value="noLimite">不限</option>
              </select>
            </logic:equal>
            <logic:equal value="monthly" name="quotaUnit">
              <select name="unit" onchange="checkCount()">
                <option id="1"  value="yearly">年</option>
                <option id="2" value="quarterly">季度</option>
                <option id="3" value="monthly" selected>月</option>
                <option id="4" value="weekly">周</option>
                <option id="5" value="daily">日</option>
                <option id="6" value="noLimite">不限</option>
              </select>
            </logic:equal>
             <logic:equal value="weekly" name="quotaUnit">
              <select name="unit" onchange="checkCount()">
                <option id="1"  value="yearly">年</option>
                <option id="2" value="quarterly">季度</option>
                <option id="3" value="monthly">月</option>
                <option id="4" value="weekly" selected="selected">周</option>
                <option id="5" value="daily">日</option>
                <option id="6" value="noLimite">不限</option>
              </select>
            </logic:equal>
             <logic:equal value="daily" name="quotaUnit">
              <select name="unit" onchange="checkCount()">
                <option id="1"  value="yearly">年</option>
                <option id="2" value="quarterly">季度</option>
                <option id="3" value="monthly">月</option>
                <option id="4" value="weekly" >周</option>
                <option id="5" value="daily" selected="selected">日</option>
                <option id="6" value="noLimite">不限</option>
              </select>
            </logic:equal>
             <logic:equal value="noLimite" name="quotaUnit">
              <select name="unit" onchange="checkCount()">
                <option id="1"  value="yearly">年</option>
                <option id="2" value="quarterly">季度</option>
                <option id="3" value="monthly">月</option>
                <option id="4" value="weekly" >周</option>
                <option id="5" value="daily">日</option>
                <option id="6" value="noLimite" selected>不限</option>
              </select>
            </logic:equal>
            </logic:equal>
        </td>
      </tr>
    </table>
    <div class="operation">
      <button onclick="osubmit()" class="commonbut" id="tijiao">保存配额</button>
    </div>
  </html:form>
  </tiles:put>
</tiles:insert>
