<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>选择流程 </title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/list.css"/>" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>">
</head>
<body>
    <form action="">
    <div style="height:220px;overflow-y:auto;background:white;">
      <table width="100%" class="sheetClass" cellpadding="2" cellspacing="0">
      	<logic:iterate id="process" name="processes">
      		<tr>
      			<td>
      				<logic:notEqual value="m" name="sel">
      					<input name="ids" type="checkbox" value='<bean:write name="process" property="id" ignore="true"/>'/>
                	</logic:notEqual>
                	<logic:equal value="m" name="sel">
                		<input name="ids" type="radio" value='<bean:write name="process" property="id" ignore="true"/>'/>
                	</logic:equal>
      			</td>
      			<td>
      				<bean:write name="process" property="name" ignore="true"/>
      			</td>
      		</tr>
      	</logic:iterate>
      </table>
      </div>
      <div class="operation">
        <button type="button" onclick="ok(this.form)" class="commonbut" id="btnOk">确定</button>
        <button type="button" onclick="window.close()" class="commonbut" id="btnCancel">取消</button>
      </div>
    </form>
    <script language="JavaScript" type="text/Javascript">
      function ok(aForm){
        var results=new Array();
        var flag='<%=request.getAttribute("sel")%>';
        if(flag=="m"){
          for(var i=0;i<aForm.elements['ids'].length;i++){
            if(aForm.elements['ids'][i].checked){
            	results.push(aForm.elements['ids'][i].value);
            }
          }
          window.returnValue=results;
          window.close();
        }
          else{
            for(var i=0;i<aForm.elements['ids'].length;i++){
              if(aForm.elements['ids'][i].checked){
            	  results.push(aForm.elements['ids'][i].value);
              }
            }
            window.returnValue=results;
			window.close();
          }
        }
   </script>
</body>
</html>
