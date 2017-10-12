<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
  <tiles:put name="title">内容转移目标分类</tiles:put>
  <tiles:put name="javascript">
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/tree.js.jsp"/>"></script>
  </tiles:put>
  <tiles:put name="dialog">
    <script type="text/Javascript" language="Javascript">
      function submitForm(){
        var form=document.forms[0];


        var categorys = document.getElementsByTagName("INPUT");
  	  var cDestination =0;
  	 　for (var i=0; i < categorys.length; i++)
     	 {
	    	  if (categorys[i].type == "radio")
  		  {
      		  if(categorys[i].checked){
      			  cDestination++;  
      	  }	
  	  }
      	 }
  	 if(cDestination<1)
  	 {
      	 alert("你还没有选择目的分类！")
      	 return;
  	 }

        


        
        var parameter='';
        var inputs = window.dialogArguments.parent.frames["list"].document.getElementsByTagName("INPUT");
	　　for (var i=0; i < inputs.length; i++) // 遍历页面上所有的 input 
    　　　{
  	　　　if (inputs[i].type == "checkbox" && inputs[i].id != 'chkAll' )
    　　　　{
  　　　　　　if(inputs[i].checked){
 			parameter=parameter+"&IDs="+inputs[i].parentElement.parentElement.id;
     		}
    	 }     
    	}
	  if(parameter.length>0)     parameter="?"+parameter.substring(1,parameter.length);
          else          parameter="?tmp=";
        
        form.action='saveMoveContent.do'+parameter;
        form.submit();
        window.returnValue="ok";

      }
      
      
    </script>
    <html:form action="saveMoveContent.do">
      <table width="99%" class="sheetClass" cellpadding="2" cellspacing="0" border="1">
          <input type="hidden" name="category" value="<bean:write name='category' property="ID"/>"/>
              <tr>
                
                <td bgcolor="white"><div style="height:450px;width:100%;overflow-y:auto; ">
                  <fulong:xtree name="categorylist" nodeId="categoryNode">
                    <fulong:xtreeText>
                    <input style="height:15px;" type="radio" name="categorys" id="<bean:write name="categoryNode" property="ID"/>" value="<bean:write name="categoryNode" property="ID"/>"/><label for="<bean:write name="categoryNode" property="ID"/>"><bean:write name="categoryNode" property="name"/></label>
                    </fulong:xtreeText>
                  </fulong:xtree>
                  </div>
               </td>
              </tr>
            </table>
      <div class="operation">
        <button class="commonbut" id="tijiao" onclick="submitForm()" >确定</button>
      </div>
    </html:form>
    </tiles:put>
    </tiles:insert>
