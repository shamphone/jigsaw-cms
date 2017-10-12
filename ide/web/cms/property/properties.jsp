<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
  <tiles:put name="title">属性选择器</tiles:put>
  <tiles:put name="javascript">
  #property {list-style-image:url(<html:rewrite page='/common/xtree/images/file.jpg' module=''/>)}
  </tiles:put>
  <tiles:put name="dialog">
      <html:form action="insert.do">
      <div id="DLGToolbar">
      <span onclick="create()" ><html:img page="/images/newprop.png" module="/common" alt="添加属性" border="0"/><span>新建</span></span>
      <span onclick="edit()"><html:img page="/images/editprop.png" module="/common" alt="修改属性"  border="0"/><span>修改</span></span>
      <span onclick="del()" ><html:img page="/images/delete.png" module="/common" alt="删除属性"  border="0"/><span>删除</span></span>
      </div>
      <div  class="insetDiv" style="height:190px">
        <ul id="property" style="list-style-image:url(<html:rewrite page='/common/xtree/images/file.png' module=''/>)">
            <logic:iterate id="propertyDefinition" name="propertylist">
            <bean:define id="ID" name="propertyDefinition" property="ID" type="String"/>
            <li id="<%=ID.replace("-","_")%>"><input onclick="setSelect(this)" definition='<bean:write name="propertyDefinition" property="ID"/>' style="height:15px;" type="radio" value="<%=ID%>" id="<%=ID.replace("-","_")%>" name="ID"><label for="<%=ID.replace("-","_")%>"><bean:write name="propertyDefinition" property="name"/>(<bean:message name="propertyDefinition" property="typeName"/>)</label></li>
            </logic:iterate>
            </ul>
          </div>
        <div class="operation">
          <button type="button" onclick="ok(this.form)" class="commonbut" id="tijiao">确定</button>
          <button type="button" onclick="window.close()" class="commonbut" id="back">取消</button>
        </div>
      </html:form>
      <script language="JavaScript" type="text/Javascript">
      var c=document.getElementsByName("ID");
      c[0].checked=true;
      c[0].onclick();
      </script>
      </tiles:put>

      <tiles:put name="javascript">
       <script language="JavaScript" type="text/Javascript">

       var definitions=new Array();
        <logic:iterate name="propertylist" id="categoryNode">
            definitions['<bean:write name="categoryNode" property="ID"/>']=new Object();
            definitions['<bean:write name="categoryNode" property="ID"/>'].name='<bean:write name="categoryNode" property="name"/>';
            definitions['<bean:write name="categoryNode" property="ID"/>'].ID='<bean:write name="categoryNode" property="ID"/>';
        </logic:iterate>
      </script>

        <script language="JavaScript" type="text/Javascript">

   var selectedID=null;
      function setSelect(input){
        selectedID=input.value;
      }
    var definitionId='<bean:write name="definitionId"/>';
      function getPropertyID(){
        var selectRadio = GetRadioObject(document.forms[0].elements("ID"));
        if(selectRadio==null){
          return "";
        }else{
          return selectRadio.value;
        }
      }
      function getDefinitionID(){
        var selectRadio = GetRadioObject(document.forms[0].elements("ID"));
        if(selectRadio==null){
          return "";
        }else{
          if(selectRadio.id.indexOf('.')!=-1){
            return selectRadio.definition;
          }else{
            return definitionId;
          }
        }
      }
      function create(){
        var url = "create.do?pseudoId="+getPropertyID()+"&definitionId="+getDefinitionID();
        var arr = showModalDialog(url,window,"dialogWidth:300px;dialogHeight:260px;help:no;scrollbars:yes;status:no");
        if(arr!=null){
          var name = '<input definition="'+arr.definition+'" type="radio" name="ID" value="'+arr.selfID+
            '" id="'+arr.definition+arr.ID+'" style="height:13px;"><label for="'+arr.definition+arr.ID+'">'+arr.name+"("+arr.typename+")"+'</label>';
          var mytype,TemO=document.getElementById("property");
          var newli=document.createElement("li");
          newli.id=arr.definition+arr.ID;
          newli.innerHTML=name;
          TemO.appendChild(newli);
          definitions[arr.selfID]=new Object();
          definitions[arr.selfID].name=arr.name;
          definitions[arr.selfID].ID=arr.selfID;
        }
      }
      function edit(){
        var selectRadio = GetRadioObject(document.forms[0].elements("ID"));
        if(selectRadio==null){
          alert('请选择属性');
          return "";
        }else{
          url = "edit.do?ID="+getPropertyID()+"&definitionId="+definitionId;
          var arr = showModalDialog(url,window,"dialogWidth:300px;dialogHeight:200px;help:no;scrollbars:yes;status:no");
          if(arr!=null){
            var name = '<input definition="'+arr.definition+'" checked="true" type="radio" name="ID" value="'+arr.ID+
            '" id="'+arr.definition+arr.ID+'" style="height:13px;"><label for="'+arr.definition+arr.ID+'">'+arr.name+"("+arr.typename+")"+'</label>';

          var li=document.getElementById(selectRadio.id);
          li.id=arr.definition+arr.ID;
          li.innerHTML=name;
          definitions[arr.selfID].name=arr.name;
          definitions[arr.selfID].ID=arr.selfID;
        }
        }
      }
      function del(){
        var selectRadio = GetRadioObject(document.forms[0].elements("ID"));
        if(selectRadio==null){
          alert('请选择属性');
          return "";
        }else{
          if(confirm('确定删除此属性？')) {
            var req = getXMLHttpRequest();
            var callback=function(){
              if ((req.readyState==4)&&(req.status==200)){
                var data=req.responseText;
                if(data!=null&&data!=""){
                //  tree.getSelected().remove();
                }
              }
            }
            var url = "delete.do?pseudoId="+getPropertyID()+"&definitionId="+definitionId;
            sendRequest(req, url, callback);
            var li=document.getElementById(selectRadio.id);
            var TemO=document.getElementById("property");
            TemO.removeChild(li);
            var c=document.getElementsByName("ID");
            c[0].checked=true;
            c[0].onclick();
          }
        }
      }
      function ok(aForm){
        var selectRadio = GetRadioObject(document.forms[0].elements("ID"));
        selectedID=selectRadio.value;
           if(selectedID!=null){
          window.returnValue=definitions[selectedID];
          window.close();
        }else{
          alert("请选择内容分类。");
        }
        /*

        var oScheme = aForm.ID;
          var ret=new Object();
          ret.ID = GetRadioObject(oScheme).value;
          ret.name = GetRadioObject(oScheme).nextSibling.innerHTML;
          window.returnValue=ret;
          window.close();
          */
      }
      </script>
</tiles:put>
</tiles:insert>
