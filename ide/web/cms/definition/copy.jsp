<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
<tiles:put name="title">复制分类 </tiles:put>
  <tiles:put name="javascript">
    <script language="JavaScript" type="text/Javascript">
    window.onload = function(){
        if(navigator.userAgent.toLowerCase().indexOf("firefox")>=0){
        	 document.body.style.overflow = "hidden";
        }
    }
      function submitForm(o)
      {	
    	  var pat=new RegExp("[^a-zA-Z0-9\_\u4e00-\u9fa5]","i"); 
    	//同级分类名称不能重复 --by mali 2010-8-23
          var defs = window.dialogArguments;
	      	for(var i=0; i<defs.length; i++){
	      		if(defs[i].text == o.name.value){
	      			alert("分类名已存在！");
	      			return false;
	      		}
	      	}
          if(o.name.value.trim()=="")
          {
              alert("请输入分类名称!");
          }else if(o.name.value.trim().length>32)
          {
              alert("名称长度不得超过32个字符!");
          }else if(pat.test(o.name.value.trim())==true) 
   	   	  { 
       	   	  alert('只能输入数字，字母，文字，下划线!'); 
       	      o.name.value = '';
   	   	  }else{
			   o.submit();
   	   	  }  
      }
    </script>
  </tiles:put>
  <tiles:put name="dialog">
    <html:form action="doCopy.do">
      <input type="hidden" name="categoryID" value="<bean:write name='categoryID' ignore="true"/>"/>
      <table width="100%" cellpadding="0" cellspacing="0" border="0">
        <tr>
          <td>分类名称</td>
          </tr>
          <tr>
          <td><html:text property="name" size="39" maxlength="32" title="分类的显示名称，2-32个字符，可以使用中文"/></td>
        </tr>
        <tr>
          <logic:notPresent name="noInherit">
                    <td><html:checkbox  disabled="true" property="childrenCategory">包含子分类</html:checkbox></td>
          </logic:notPresent>
          <logic:present name="noInherit">
                    <td><html:checkbox property="childrenCategory">包含子分类</html:checkbox></td>
          </logic:present>
        </tr>
      </table>
      <div class="operation">
                        <button type="button" onclick="submitForm(this.form)" id="btnOk">保存</button>
                        <button type="button" onclick="window.close()" id="btnCancel">取消</button>
                    </div>
    </html:form>
  </tiles:put>
</tiles:insert>
