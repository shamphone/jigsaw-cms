<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<tiles:insert definition="dialog_frame">
  <tiles:put name="title">添加过滤条件</tiles:put>
  <tiles:put name="dialog">
    <html:form action="filterEditor.do">
      <table width="100%" class="sheetClass" cellpadding="2" cellspacing="0">
        <tr>
          <td>属性定义</td>
          <td>
          <html:select styleId="propertyValue" onchange="checkRef(this)" property="property">
          	<logic:iterate id="property" name="properties">
          		<option value="<bean:write name="property" property="ID"/>" type="<bean:write name="property" property="type"/>" title="<bean:write name="property" property="name"/>">
          			<bean:write name="property" property="name"/>
          		</option>
          	</logic:iterate>
          	<option value="coolink_parentNode" type="" title="父节点">父节点</option>
          </html:select>
          <html:select styleId="propertyLogic" onchange="changeOp(this)" property="operation">
            <html:option value="equal">=</html:option>
            <html:option value="notEqual">&lt;&gt;</html:option>
            <html:option value="more">&gt;</html:option>
            <html:option value="less">&lt;</html:option>
            <html:option value="like">包含</html:option>
          </html:select>
          </td>
        </tr>
        <tr>
           <td>全文关键字检索</td>
           <td><input type="checkbox" name='cbFullTextSearchName' id="cbFullTextSearchId" value='7' onclick="funcFullTextSearch()"
                title="在所有字符串属性列进行全文检索"/></td>
        </tr>
        <tr>
          <td><html:radio styleId="nullValue" property="valueType" value="0"><label for="nullValue">空</label></html:radio></td>
          <td></td>
        </tr>
        <tr>
          <td><html:radio styleId="clValue" property="valueType" value="1"><label for="clValue">常量</label></html:radio></td>
          <td title="如果是时间格式为：yyyy-MM-dd HH:mm:ss;如果是布尔值格式为：true或者false"><html:text property="constant" style="width:150px"/></td>
        </tr>
        <tr>
          <td><html:radio styleId="blValue" property="valueType" value="2"><label for="blValue">系统变量</label></html:radio></td>
          <td><html:select styleId="sysVar" property="sysVariant" style="width:150px">
          <logic:iterate id="variable" name="variables" property="variables" type="java.lang.String">
          <html:option value="<%= variable %>"><bean:write name="variables" property='<%= "displayName("+variable+")"  %>'/> </html:option>
          </logic:iterate>
            </html:select>
          </td>
        </tr>
        <tr>
          <td><html:radio disabled="true" styleId="rValue" property="valueType" value="3"><label for="rValue">引用值</label></html:radio></td>
          <td><html:hidden property="reference"/>
            <input size="14" type="text" name="referenceName" readonly="readonly" value="<logic:present parameter="referenceNode"><cms:node name="referenceNode" propertyName="title" ignore="true"/></logic:present>" />
            <button disabled="disabled" class="commonbut" id="search" onclick="searchContent(this)">选择...</button></td>
        </tr>
        <tr>
          <td><input type="radio" id="dateValue" name="valueType" value="4"><label for="keyword">日期</label></td>
          <td><input type="text" id="dateFilterValue" name="dateFilterValue" style="width:150px">
          <button id="btnDate"  onclick="return showCalendar(this,document.getElementById('dateFilterValue'), '%Y-%m-%d');">..</button></td>
        </tr>
        <logic:present name="SearchProperties">
        	<tr>
	          <td><input type="radio" Id="SearchDefValue" name="valueType" value="5"><label for="SearchDefValue">目标属性</label></td>
	          <td>
		          <html:select styleId="SearchPropValue" property="searchPropValue">
		            <html:optionsCollection name="SearchProperties" label="name" value="ID"/>
		          </html:select>
	          </td>
	        </tr>
        </logic:present>        
      </table>
      <div class="operation">
        <button type="button" onclick="ok(this.form)" class="commonbut" id="tijiao">确定</button>
        <button type="button" onclick="window.close()" class="commonbut" id="back">取消</button>
      </div>
    </html:form>
  </tiles:put>
  <tiles:put name="javascript">
    <script type="text/Javascript" src='<html:rewrite page="/script/portlet.js" module="/common"/>'>
    </script>
    <script type="text/javascript" src='<html:rewrite module="/common" page="/calendar/calendar.js.jsp"/>'></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/dialog.js"/>"></script>
    <script language="JavaScript" type="text/Javascript">

    

       var referece=new Array();
        <logic:iterate name="referece" id="ref">
            referece['<bean:write name="ref" property="ID"/>']=new Object();
            referece['<bean:write name="ref" property="ID"/>'].name='<bean:write name="ref" property="name"/>';
            <logic:present name="ref" property="referenceDefinition">
            referece['<bean:write name="ref" property="ID"/>'].refID='<bean:write name="ref" property="referenceDefinition.ID" ignore="true"/>';
            </logic:present>
        </logic:iterate>
        referece['coolink_parentNode']=new Object();
        referece['coolink_parentNode'].name='父节点';

  	  /**
        * liulei modified at 2009-12-21
        * 修改原因：为了提高用户体验。
        * 修改目的：用户在页面上选择“全文关键字检索”，其他无关的输入框将全部被屏蔽掉,进而达到方便使用的目的。
        */
      function funcFullTextSearch()
      {
          var foo=document.getElementById("cbFullTextSearchId");
          if(foo.checked==true)
          {
        	  document.getElementById("nullValue").disabled=true;
        	  document.getElementById("blValue").disabled=true;
              document.getElementById("rValue").disabled=true;
              document.getElementById("dateValue").disabled=true;
              document.getElementById("propertyValue").disabled=true;
              document.getElementById("propertyLogic").disabled=true;
              document.getElementById("sysVar").disabled=true;
              document.getElementById("btnDate").disabled=true;
              document.getElementById("dateFilterValue").disabled=true;
              document.getElementById("clValue").checked=true;
              
          }
          if(foo.checked==false)
          {
        	  document.getElementById("nullValue").disabled=false;
        	  document.getElementById("blValue").disabled=false;
              document.getElementById("rValue").disabled=false;
              document.getElementById("dateValue").disabled=false;
              document.getElementById("propertyValue").disabled=false;
              document.getElementById("propertyLogic").disabled=false;
              document.getElementById("sysVar").disabled=false;
              document.getElementById("btnDate").disabled=false;
              document.getElementById("dateFilterValue").disabled=false;
          }          
      }

      function checkRef(oProperty)
      {
    	if(getSelectedItem(oProperty).type==8){
        	setRadioValue(oProperty.form.valueType,'0');
      	    document.getElementById("blValue").disabled=true;
            document.getElementById("rValue").disabled=true;
            document.getElementById("dateValue").disabled=true;
            document.getElementById("sysVar").disabled=true;
            document.getElementById("btnDate").disabled=true;
            document.getElementById("dateFilterValue").disabled=true;
            document.getElementById("clValue").disabled=true;
        }else{
        	document.getElementById("nullValue").disabled=false;
      	    document.getElementById("blValue").disabled=false;
            document.getElementById("rValue").disabled=false;
            document.getElementById("dateValue").disabled=false;
            document.getElementById("sysVar").disabled=false;
            document.getElementById("btnDate").disabled=false;
            document.getElementById("dateFilterValue").disabled=false;
            document.getElementById("clValue").disabled=false;
        	var value = oProperty.value;
            if(referece[value]!=null)
            {
                setRadioValue(oProperty.form.valueType,'3');
                document.getElementById("rValue").disabled="";
                document.getElementById("search").disabled="";
            }
            else
            {
                setRadioValue(oProperty.form.valueType,'1');
                document.getElementById("rValue").disabled="true";
                document.getElementById("search").disabled="true";
            }
        }
      }
      
      function ok(oForm)
      {
          var ret=new Object();
          if(getValueTypeValue(oForm)=="")
              return;
          var foo=document.getElementById("cbFullTextSearchId");
          if(foo.checked==true)
          {
        	  ret.value = "theKeyword"+ " " + "contains" + " " +getValueTypeValue(oForm);
              ret.name = "全文关键字"+ " " +"全文匹配"+ " " +getValueTypeName(oForm);
          }
          else
          {
        	  ret.value = oForm.property.value+ " " +oForm.operation.value + " " +getValueTypeValue(oForm);
              ret.name = getSelectedItem(oForm.property).text+ " " +getOperationName(oForm.operation)+ " " +getValueTypeName(oForm);
          }
          window.returnValue=ret;
          window.close();
      }
      
		function validateDateFormat(sDate){
			var datePattern = /^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))( ([01]\d|2[0-3]):([0-5]\d):([0-5]\d))?$/;
			if(!datePattern.exec(sDate)){
				return false;
			}
			return true;
		}

      
      function changeOp(oOperation)
      {
        var value = oOperation.value;
        if(value=='contains')
        {
        	document.getElementById("nullValue").disabled="true";
            document.getElementById("blValue").disabled="true";
            document.getElementById("rValue").disabled="true";
            document.getElementById("dateValue").disabled="true";
            setRadioValue(oOperation.form.valueType,'1');
        }
        else
        {
        	document.getElementById("nullValue").disabled="";
            document.getElementById("clValue").disabled="";
            document.getElementById("blValue").disabled="";
            document.getElementById("rValue").disabled="";
            document.getElementById("dateValue").disabled="";
            if(value=='more')
            {
                document.getElementById("nullValue").disabled="true";
                if(GetRadioValue(oOperation.form.valueType)=='0')
                {
                    setRadioValue(oOperation.form.valueType,'1')
                }
            }
            else if(value=='less')
                 {
                     document.getElementById("nullValue").disabled="true";
                     if(GetRadioValue(oOperation.form.valueType)=='0')
                     {
                         setRadioValue(oOperation.form.valueType,'1')
                     }
                 }else if(value=='equal')
                       {
                           document.getElementById("nullValue").disabled="";
                       }
                       else if(value=='notEqual')
                            {
                                document.getElementById("nullValue").disabled="";
                            }else if(value=='like')
                                  {
              	                      document.getElementById("nullValue").disabled="true";
                                      if(GetRadioValue(oOperation.form.valueType)=='0')
                                      {
                                          setRadioValue(oOperation.form.valueType,'1')
                                      } 
                                  }
        }
      }

      function getOperationName(oOperation){
        var value = oOperation.value;
        if(value=='more'){
          return '大于';
        }else if(value=='less'){
          return '小于';
        }else if(value=='equal'){
          return '等于';
        }else if(value=='notEqual'){
          return '不等于';
        }else if(value=='like'){
			return '包含';
            }
        else if(value=='contains'){
			return '全文匹配';
            }
      }
      
      function getValueTypeValue(oForm)
      {
           var value = GetRadioValue(oForm.valueType);        
           if(value=='0')
           {
               return 'null';
           }
           else if(value=='1')
           {        	
        	   var partens=/[\u3000]/g; 
        	   if(partens.test(oForm.constant.value)) 
               { 
                   alert("禁止包含半角、全角空格！"); 
                   oForm.constant.value="";
        	       return "";
               }
               if(oForm.constant.value.trim()=="")
               {
        	       alert("'常量'中不能只包含空格！");
        	       oForm.constant.value="";
        	       return "";
               }
              var propertyType = oForm.property.options[oForm.property.selectedIndex].type
			  if(propertyType&&propertyType=="5"&!validateDateFormat(oForm.constant.value)){
				  alert("日期格式不是yyyy-MM-dd或yyyy-MM-dd HH:mm:ss或日期无效");
				  return "";
 			  }else{
        	       return oForm.constant.value;
              }
           }
           else if(value=='2')
           {
               return "$"+oForm.sysVariant.value;
           }
           else if(value=='3')
           {
               return "#"+oForm.reference.value;
           }
           else if(value=='4')
           {	
               //对日期类型输入值做格式验证  by mali 2010-6-25
        	   var pt = oForm.property.options[oForm.property.selectedIndex].type;
 			   if(pt&&pt=="5"&!validateDateFormat(oForm.dateFilterValue.value)){
 				  alert("日期格式不是yyyy-MM-dd或yyyy-MM-dd HH:mm:ss或日期无效");
 				  return "";
  			  }else{
         	       return oForm.dateFilterValue.value;
               }
		       //return oForm.dateFilterValue.value;
           }
           else if(value=='5')
           {
        	   return "^"+oForm.SearchPropValue.value;
           }
      }
      
      function getValueTypeName(oForm){
        var value = GetRadioValue(oForm.valueType);
        if(value=='0'){
          return '空';
        }else if(value=='1'){
          return oForm.constant.value;
        }else if(value=='2'){
          return getSelectedItem(oForm.sysVariant).text;
        }else if(value=='3'){
          return oForm.referenceName.value;
        }else if(value=='4'){
  		  return oForm.dateFilterValue.value;
        }else if(value=='5'){
        	return getSelectedItem(oForm.SearchPropValue).text+' ('+'<bean:write name="searchDef" property="name" ignore="true"/>'+')';
        }
      }
    /**
    *选择内容
    */
    function searchContent(oButton){
      setRadioValue(oButton.form.valueType,'3')
      var arr = CMSDialog.NodeSelector(referece[document.getElementById("propertyValue").value].refID,true,'false','false','title','title*createdTime*','asc');
      if(arr!=null&&arr.length>0){
        var title = arr[0]['title']==null?arr[0]['user-commonname']:arr[0]['title'];
        if(title==undefined){
        	title=arr[0]['id'];
            }
        document.getElementById("referenceName").value = title;
        document.getElementById("reference").value = arr[0]['id'];
      }
    }
    </script>
  </tiles:put>
</tiles:insert>
