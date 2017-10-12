<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
 <tiles:put name="title">新建属性</tiles:put>
  <tiles:put name="dialog">
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms" page="/classes/cmsdialog.js"/>"></script>
    <html:form action="/insert.do" method="POST" onsubmit="return check(this);">
      <input type="hidden" name="definitionId" value="<bean:write name='definition' property='ID'/>"/>
      <table width="100%" class="sheetClass" cellpadding="2" cellspacing="0">
        <tr>
          <th width="100">内容分类：</th>
          <td><bean:write name='definition' property="name"/>(<bean:write name='definition' property="ID"/>)</td>
        </tr>
        <tr>
          <th>标 识 符：</th>
          <td valign="top"  class="formComponent">
            <input type="text" name="ID" value='prop<bean:write name="index"/>'  maxlength="20" onpaste="return false;" style="ime-mode:disabled" title="在内容格式中唯一标识这个属性的标识符,使用英文字符,禁止使用非法字符（如：~！@#￥%……&*等）,一旦设定将无法修改。最长为20位英文字符。" />
            <span class="formTips"><span class="emphasize">*</span></span>
          </td>
        </tr>

        <tr>
          <th>显示名称：</th>
          <td valign="top"  class="formComponent">
            <input type="text" name="name" value='新属性<bean:write name="index"/>' maxlength="32" title="在页面上显示出来的名称，可以使用中文。"/>
              <span class="formTips"><span class="emphasize">*</span></span>
          </td>
        </tr>
        
        <tr style="display:none">
          <th>取值个数：</th>
          <td valign="top" >
            <input onclick="changeDataNum(this)" type="radio" value="1" name="dataNum" checked="checked" />一个
          </td>
        </tr>
        <!--
        <tr>
          <th></th>
          <td valign="top" >
            <input onclick="changeDataNum(this)" type="radio" value="0" name="dataNum" />任意个数
          </td>
        </tr>
        <tr>
          <th></th>
          <td valign="top" >
            <input onclick="changeDataNum(this)" type="radio" value="n" name="dataNum" />范围
            <input name="minDataNum" size="4" disabled="disabled"  onkeypress="return checkInteger()"/>到<input name="maxDataNum" size="4"  disabled="disabled" onkeypress="return checkInteger()"/>个
          </td>
        </tr>
        -->
        <tr>
          <th>数据类型：</th>
          <td>
            <span class="formTips">
              <html:select property="type" onchange="doSelect(this)">
                <option title="长度在0-256之间的文本，如邮件地址、名称等" value="1">字符串</option>
                <option title="支持长达2G的文本，如产品描述、介绍" value="10">文本</option>
                <option title=" 支持正、负的长整型数字，如年龄、数量等" value="3">整数</option>
                <option title="支持正负的双浮点型数字，如价格、长度等" value="4">浮点数</option>
                <option title="支持是否的布尔值，例如：男、女，真、假" value="6">布尔值</option>
                <option title="时间或者日期" value="5">日期</option>
                <option title="文件路径" value="8">路径</option>
                <option title="这种类型的属性本身就有多个通过格式定义的属性。" value="0">复合属性</option>
                <option title="用于表示对其他内容的引用" value="9">引用属性</option>
              </html:select>
              <span class="emphasize">*</span></span>
          </td>
        </tr>
        <tr>
        <th>关联内容分类：</th>
        <td><input type="text" disabled="disabled" readonly="readonly" id="selCategoryName"><input type="hidden" id="selCategoryId" name="referenceType"><input type="button" value="选择..." disabled="disabled" id="btnSelectContent" onclick="doSelectCategory()">
        <input type="hidden"  id="fixCategoryName"><input type="hidden" id="schemeID" name="schemeID">
        </td>
        </tr>
      </table>
      <div class="operation">
        <button class="commonbut" type="submit">确认</button>
        <button class="commonbut" onclick="window.close()" id="btnCancel">取消</button>
      </div>   
    </html:form>
     <script language="javascript" type="text/javascript" >
    function check(form)
    {    
         var list = window.dialogArguments;
         /**
          * liulei modified in 2010-1-19
          * 处理bug 5119：一个内容分类下面有多个属性，目前没有判断属性是否重名的情况，重复属性会造成系统使用性上的问题
          * 算法：获得全部的已存在的节点的名称，遍历这些节点并判断是否相等，如果相等则返回错误并阻止表单的提交
          */
         for(var i=0;i<window.dialogArguments.length;i++)
         {
            if(list[i].getAttribute("name")==form.name.value.trim())
            {
                alert("属性名称不能重复！");
                return false;
            }             
         }

    	  var pat=new RegExp("[^a-zA-Z0-9\_\u4e00-\u9fa5]","i"); 
          if(form.ID.value.trim()==''||pat.test(form.ID.value.trim())==true)
          {
			  alert("属性标识符输入不合法,请检查是否为空或包含非法字符！");
			  form.ID.value="";
			  return false;
          }
          if(form.name.value.trim()==''||pat.test(form.name.value.trim())==true)
          {
			  alert("属性显示名称输入不合法,请检查是否为空或包含非法字符！");
			  form.name.value="";
			  return false;
          }
          var oSelect = form.elements["type"];
          if(oSelect.value=='9'||oSelect.value=='0')
          {
               if(form.elements["referenceType"].value == '')
               {
                   alert("请选择关联分类！");
                   return false;
               }
                   
          }
          return true;
    }
    
    function changeDataNum(obj){
      if(obj.value=="1"){
        obj.form.minDataNum.disabled="disabled";
        obj.form.maxDataNum.disabled="disabled";
      }else if(obj.value=="0"){
        obj.form.minDataNum.disabled="disabled";
        obj.form.maxDataNum.disabled="disabled";
      }else if(obj.value=="n"){
        obj.form.minDataNum.disabled="";
        obj.form.maxDataNum.disabled="";
      }
    }
    function doSelect(oSelect){
		if(oSelect.value=='9'||oSelect.value=='0'){
			document.getElementById("selCategoryName").disabled=false;
			document.getElementById("btnSelectContent").disabled=false;
			}
		else{
			document.getElementById("selCategoryName").disabled=true;
			document.getElementById("btnSelectContent").disabled=true;
			}
        }
    function doSelectCategory(){
    	  var definition=CMSDialog.NodeDefinitionSelector();
    		if(definition!=null){
    	   if(definition.length>0){
    	       for(var j=0;j<definition.length;j++){
    	    	   document.getElementById("selCategoryName").value=definition[j].name;
    	    	   document.getElementById("selCategoryId").value=definition[j].ID;
    	    	   document.getElementById("fixCategoryName").value=definition[j].name;
    	    	   document.getElementById("schemeID").value=definition[j].ID;
    	       }
    	     }
    	  }
        }
    </script> 
  </tiles:put>
</tiles:insert>
