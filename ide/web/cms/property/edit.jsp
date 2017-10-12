<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>修改属性</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>">
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/dialog.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms" page="/classes/cmsdialog.js"/>"></script>      
      <script language="javascript" type="text/javascript" >
      
      function check(form)
      {
          /**
           * liulei modified in 2010-1-19
           * 处理bug 5119：一个内容分类下面有多个属性，目前没有判断属性是否重名的情况，重复属性会造成系统使用性上的问题
           * 算法：遍历比较。注意，如果没有修改，则必然会出现重名，应排除这种情况
           */
          var list = window.dialogArguments;
          if(list!=null)
          {
        	  for(var i=0;i<window.dialogArguments.length;i++)
              {
                  var name = list[i].getAttribute("name");
                  var id = list[i].getAttribute("id");
        		  if(name==form.name.value)
                  {   
                      /*var ID = form.ID;
                      var idvalue = "";
                      if(ID.length!=null)
                	       idvalue = ID[0].value; 
                	  else
                    	   idvalue=ID.value;
                      if(id!=idvalue)
                      {   
                          alert("属性名称不能重复！");
                          return false;
                      }*/
                      if(id!='<bean:write name="propertyForm" property="ID" ignore="true"/>')
                      {   
                          alert("属性名称不能重复！");
                          return false;
                      }  
                  }            
              }
          }
    	  //以下是正则验证非法字符的方法，经过实践，该方法被认为是无效的，已经废弃。
    	  
    	  //经测试该方法有效，逻辑正确，废弃之理由不明，故再开启 by mali 2010-6-24
          if(form.name.value.trim()=='')
          {
			  alert("属性名称不能为空!");
			  return;
          }
          var pat=new RegExp("[^a-zA-Z0-9\_\u4e00-\u9fa5]","i"); 
          if(pat.test(form.name.value.trim())==true)
          {
        	  alert("属性名称不能包含非法字符!");
              return;   
          }
      	  form.submit();
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
      function doSelectCategory(){
    	  var definition=CMSDialog.NodeDefinitionSelector();
    		if(definition!=null){
    	
    	    	   document.getElementById("selCategoryName").value=definition[0].name;
    	    	   document.getElementById("selCategoryId").value=definition[0].ID;
    	     
    	  }
        }
      
      </script>

<base target="_self" />
</head>
<body>
          
    <html:form action="/update.do" method="POST">
      <html:hidden property="definitionId"/>
      <html:hidden property="ID"/>      
        <table width="100%" class="sheetClass" cellpadding="2" cellspacing="0">
          <tr>
            <th nowrap="nowrap">数据类型：</th>
            <td>
              <input type="text" readonly="readonly" disabled="disabled" value="<bean:message bundle="content-message" name="propertyForm" property="type"/>"/>
            </td>
          </tr>
          <tr>
            <th>标 识 符：</th>
            <td valign="top"  class="formComponent">
              <html:text readonly="true"  disabled="true" property="ID" />
            </td>
          </tr>
          <tr>
            <th>显示名称：</th>
            <td valign="top"  class="formComponent">
              <html:text property="name"  maxlength="32" title="在页面上显示出来的名称，可以使用中文"/>
              <span class="formTips"><span class="emphasize">*</span></span>
            </td>
          </tr>
          <tr style="display:none">
            <th>取值个数：</th>
            <td valign="top" >
              <html:radio onclick="changeDataNum(this)" value="1" property="dataNum"/>一个
            </td>
            <!--
          </tr>
          <tr>
            <th></th>
            <td valign="top" >
              <html:radio  onclick="changeDataNum(this)"  value="0" property="dataNum" />任意个数
            </td>
          </tr>
          <tr>
            <th></th>
            <td valign="top" >
              <html:radio  onclick="changeDataNum(this)"  value="n" property="dataNum" />范围
              <html:text  property="minLength" size="4" onkeypress="return checkInteger()"/>到<html:text property="maxLength" size="4" onkeypress="return checkInteger()" />个
            </td>
          </tr>
          -->
          <logic:equal value="0" name="propertyForm" property="type">
          	<tr>
        <th>关联内容分类：</th>
        <td>
            <input type="text" id="selCategoryName" disabled="disabled" value='<bean:write name="property" property="referenceDefinition.name" ignore="true"/>'>
        </td>
        </tr>
          </logic:equal>
          <logic:equal value="9" name="propertyForm" property="type">
          	<tr>
        <th>关联内容分类：</th>
        <td>
            <input type="text" id="selCategoryName" disabled="disabled" value='<bean:write name="property" property="referenceDefinition.name" ignore="true"/>'>
        </td>
        </tr>
          </logic:equal>         
          </table>
        <div class="operation">
          <button class="commonbut" onclick="check(this.form)" id="btnOk">确认</button>
          <button class="commonbut" onclick="window.close()" id="btnCancel">取消</button>
        </div>
      </html:form>
      </body>
      </html>
