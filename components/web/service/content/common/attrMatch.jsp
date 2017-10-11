<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<tiles:insert definition="dialog_frame">
        <tiles:put name="title">属性匹配</tiles:put>
        <tiles:put name="javascript">
    <script type="text/Javascript" language="Javascript">
      function submitForm(){
          var propmatch=document.getElementsByName("propmatch");
          var result=new Array();
          for(var i=0;i<propmatch.length;i++){
				var propID=propmatch[i].value;
				if(propID=='$'){
					propID='$'+document.getElementById(propmatch[i].compid)[1].value;
					}
				document.getElementsByName(propmatch[i].compid)[0].value=propID;
				var propMatchObj=new Object();
		        propMatchObj.remoteId=propmatch[i].compid;
		        propMatchObj.remoteName=propmatch[i].compValue;
		        propMatchObj.localId=propID;
		        propMatchObj.localName=propmatch[i].options[propmatch[i].selectedIndex].text;
		        result.push(propMatchObj);
              }
          window.returnValue=result;
          window.close();
       
      }
      window.onload=function(){
    	  var propmatch=document.getElementsByName("propmatch");
          for(var i=0;i<propmatch.length;i++){
				var propID=propmatch[i].compid;
				for(var j=0;j<propmatch[i].options.length;j++){
					if(propmatch[i].options[j].value==propmatch[i].compid){
						propmatch[i].options[j].selected=true;
						}
					}
              }
          if(!document.all){
  			document.getElementById("toolbar").style.top = document.body.clientHeight-40;
  			document.getElementById("toolbar").style.left = 105;
  			document.getElementById("toolbar").style.textAlign = "center";
  			document.getElementById("pannelSelect").style.height = document.body.clientHeight-2;
  			document.body.style.overflow = "hidden"
  		}
      }
      </script>
    </tiles:put>

   <tiles:put name="dialog">
         <form action="saveAttrMatch.do" method="POST">
          <div id="layer" style="width:100%;height:280px;overflow-y:scroll;background-color:white"> 
                  <table width="99%"  cellpadding="0" cellspacing="0" border="0"  id="listTable">
                    <thead>
                      <th>目标属性</th>
                      <th width="140px">源属性</th>
                      <th>值</th> 
                    </thead>
                    <logic:iterate name="remoteProps" id="remoteProp">
                    <tr>
                    	<td>
                    	<label><bean:write name="remoteProp" property="value"/></label>
                    	</td>
                    	<td>
                    	<input type='hidden' name='<bean:write name="remoteProp" property="key"/>'>
                    	<select compid='<bean:write name="remoteProp" property="key"/>' compValue='<bean:write name="remoteProp" property="value"/>' name='propmatch'>
                    		<option  compID="" value="$">值</option>
           					<optgroup label="--请选择--" compID="" value="0">
            				<logic:iterate name="list" id="item">
            				<logic:equal value="class com.fulong.longcon.repository.definition.ChildNodeDefinitionImpl" name="item" property="class">
              				<option  compID="<bean:write name="item" property="nodeDefinition.ID"/>" value="<bean:write name="item" property="ID" ignore="true"/>"><bean:write name="item" property="name" ignore="true"/></option>
            				</logic:equal>
            				<logic:notEqual value="class com.fulong.longcon.repository.definition.ChildNodeDefinitionImpl" name="item" property="class">
              					<option  compID="" value="<bean:write name="item" property="ID" ignore="true"/>"><bean:write name="item" property="name" ignore="true"/></option>
            				</logic:notEqual>
            				</logic:iterate>
            				</optgroup>
                    	</select>
                    	</td>
                    	<td>
                    		<input id='<bean:write name="remoteProp" property="key"/>' type="text">
                    	</td>
                    	</tr>
                    </logic:iterate>
            </table>
            </div>
            </form>
          <div class="operation" style="20px">
            <button type="button" id="btnOK" onclick="submitForm()">完成</button>
          </div>
        
        <script type="text/javascript">
</script>
    </tiles:put>
    </tiles:insert>
