<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
<tiles:put name="title">设置服务参数 </tiles:put>
  <tiles:put name="dialog">
    
	  <div>服务参数1</div>
          <input id="parameter1" name="parameter" type="text">
        <div>服务参数2</div>
          <input id="parameter2" name="parameter" type="text">
        <div>服务参数3</div>
          <input id="parameter3" name="parameter" type="text">
       		
	<div class="operation">
		<button type="button" onclick="doSet()">确定</button>
		<button id="btnCancel" type="button" onclick="window.close()">取消</button>
		</div>
    <script language="JavaScript" type="text/Javascript">
    function doSet(){
    	var oParameters=document.getElementsByName("parameter");
		var result=new Array();
        for(var i=0;i<oParameters.length;i++){
			result.push(oParameters[i].value);
            }
        window.returnValue=result;
		window.close();
    }
    </script>
  </tiles:put>
</tiles:insert>
