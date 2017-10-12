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
function nav_removeSelected(oSelect){
    for(var i=0;i<oSelect.options.length;i++){
      if(oSelect.options[i].selected){
        if(confirm("确定删除这些对象吗？若确定，请点击提交按钮保存授权结果")){
          for(var i=0;i<oSelect.options.length;i++){
            if(oSelect.options[i].selected){
              oSelect.options.remove(i);
              i--;
            }
          }


        }
        return;
      }
    }
    alert("你没有选择待删除对象！")

}
function appendPassportIdentity(selector) {
  var selector=document.getElementById("memberships");
    var url = "<html:rewrite module='/common' page='/modalWrapper.jsp'/>?title="+encodeURIComponent(encodeURIComponent("搜索用户"))+"&url="+encodeURI('<html:rewrite module="/cms" page="/content/selectPrincipal.do"/>');
    var arr = showModalDialog(url,selector,"dialogWidth:600px;dialogHeight:590px;help:no;scrollbars:yes;status:no");

    if(arr!=null){
        for (var i=0; i<arr.length; i++) {
            var principal = arr[i];
            if (!isContentExist(selector, principal.value)) {
                var oOption = document.createElement("option");
                selector.options.add(oOption);
                oOption.value = principal.value;
                oOption.innerText = principal.text;
            }
        }
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
     document.getElementById("application").disabled=true;
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
  function do_submit(oForm){
    selectAll(oForm.elements["memberships"]);
    return true;
  }
    </script>
</tiles:put>
  <tiles:put name="body">
    <html:form action="updateAuth.do" onsubmit="return do_submit(this)">
      <input type="hidden" name="categoryID" value="<bean:write name='categoryId' ignore="true"/>"/>
      <input type="hidden" name="command" value="<bean:write name='command'/>"/>
      <table width="100%"  cellpadding="0" cellspacing="0" border="1">
        <tr>
          <th bgcolor="#eeeeee">授权对象</th>
          <td>
            <table cellpadding="2" cellspacing="0" border="0">
              <tr>
                <td rowspan="2">
                  <select name="memberships" multiple="multiple" size="10" style="width:200px" id="memberships">
                    <logic:iterate id="member" name="members">
                      <logic:present name="member">
                        <option value="<bean:write name="member" property="ID"/>">
                          <cms:node name="member" propertyName="commonname"/>
                        </option>
                      </logic:present>
                    </logic:iterate>
                  </select>
                </td>
                <td>
                  <button type="button" onclick="appendPassportIdentity(this.form.memberships)" class="commonbut" id="addBut" style="font-size:10px;width:80px">添加</button>
                </td>
              </tr>
              <tr>
                <td>
                  <button type="button" onclick="nav_removeSelected(this.form.memberships)" class="commonbut" id="deleteBut" style="font-size:10px;width:80px">删除</button>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <th width="100px" bgcolor="#eeeeee">&nbsp;</th>
          <td>
            <input checked="checked" type="checkbox" name="application" value="true" id="application"/>
            <label for="application">应用到所有子分类</label>
          </td>
        </tr>
      </table>
      <div class="operation">
        <button type="submit" class="commonbut" id="tijiao">保存</button>
      </div>
    </html:form>
  </tiles:put>
</tiles:insert>
