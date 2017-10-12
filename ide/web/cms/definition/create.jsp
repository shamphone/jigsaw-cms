<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
<tiles:put name="title">创建子分类 </tiles:put>
  <tiles:put name="dialog">
    <html:form action="insert.do" onsubmit="return submitForm(this);">
      <html:hidden property="parentID"/>
      <div style="text-align:left;">请输入分类名称：</div>
      <input type="text" name="name" maxlength="32" size="39"/>
      <!-- <div style="text-align:left;">
            <input type="checkbox" name="isRoot" value="false" title="该属性列置空值">在根分类节点下创建该分类
      </div> -->
      <div class="operation">
        <button type="submit">确定</button>
        <button id="btnCancel" type="button" onclick="window.close()">取消</button>
      </div>
    </html:form>
    <script language="JavaScript" type="text/Javascript">
    window.onload = function(){
        if(navigator.userAgent.toLowerCase().indexOf("firefox")>=0){
        	 document.body.style.overflow = "hidden";
        }
    }
    function submitForm(o)
    {	
        if(o.name.value.trim()=="")
        {
            alert("请输入分类名称!");
            return false;
        }
        if(o.name.value.trim().length>32)
        {
            alert("名称长度不得超过32个字符!");
            return false;
        }
        var pat=new RegExp("[^a-zA-Z0-9\_\u4e00-\u9fa5]","i"); 
        if(pat.test(o.name.value.trim())==true) 
   	    { 
       	    alert('只能输入数字，字母，文字，下划线!'); 
       	    o.name.value = '';
   	        return false;
   	    }

		//同级分类名称不能重复 --by mali 2010-8-23
        var defs = window.dialogArguments;
    	for(var i=0; i<defs.length; i++){
    		if(defs[i].text == o.name.value){
    			alert("分类名已存在！");
    			return false;
    		}
    	}
        return true;
    }
    </script>
  </tiles:put>
</tiles:insert>
