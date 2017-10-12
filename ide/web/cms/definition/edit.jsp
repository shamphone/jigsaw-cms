<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
<tiles:put name="title">修改分类</tiles:put>
  <tiles:put name="dialog">
  <html:form action="update.do" onsubmit="return submitForm(this);">
    <html:hidden property="ID"/>
    <div style="text-align:left;">请输入修改后的分类名称：</div> 
    <html:text property="name" size="39"></html:text>
    <div class="operation">
    <button type="submit">确定</button>
    <button id="btnCancel" type="button" onclick="window.close()">取消</button>
    </div>
  </html:form>
  <script language="JavaScript" type="text/Javascript">
    function submitForm(o){
        if(o.name.value.trim()=="")
        {
            alert("请输入分类名称!");
            return false;
        }
        if(o.name.value.trim().length>32)
        {
            alert("名称长度不得超过32个字!");
            return false;
        }
        var pat=new RegExp("[^a-zA-Z0-9\_\u4e00-\u9fa5]","i"); 
        if(pat.test(o.name.value.trim())==true) 
   	    { 
       	    alert('只能输入数字，字母，文字，下划线!'); 
   	        return false;
   	    }
     	 //同级分类名称不能重复 --by mali 2010-8-23
        var tree = window.dialogArguments;
        var parent = tree.getSelected().getParent();
		var defs = parent.childNodes;
		var selected = tree.getSelected;
    	for(var i=0; i<defs.length; i++){
    		if(defs[i].text == o.name.value && selected.text != o.name.value){
    			alert("分类名已存在！");
    			return false;
    		}
    	}
        return true;
    }
    window.onload = function(){
        if(navigator.userAgent.toLowerCase().indexOf("firefox")>=0){
        	 document.body.style.overflow = "hidden";
        }
    }
  </script>
</tiles:put>
</tiles:insert>
