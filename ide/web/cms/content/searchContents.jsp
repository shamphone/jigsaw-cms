<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>搜索内容</title>
        <meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
		<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
		<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
		<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/list.css"/>"/>
        <link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>">
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/tree.js.jsp"/>"></script>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ListTable.js"/>"></script>

        <script language="Javascript" type="text/javascript">
			
			document.onkeydown = function() {
				var btn;
				if (event.keyCode == 13) {
					var src = event.srcElement;
				 	if (!src.tagName || src.tagName.toLowerCase() != "textarea") 
				  		btn = document.getElementById("btnOk");
				} else if (event.keyCode == 27) {
				 	btn = document.getElementById("btnCancel");
				}
				if (btn && !btn.disabled) {
					btn.click();
					if (event.keyCode == 13)
						return false;
				}
			}
			window.onload = function(){
				var div = document.getElementById("ffdivsty");
				div.style.width = document.body.clientWidth;
				div.style.height = document.body.clientHeight-85;
				
				var listTable =  document.getElementById("listTable");
				listTable.style.top = offsetParent.scrollTop;
			}
        </script>
        <base target="_self"/>
    </head>
<body>  
        <script type="text/javascript">var contents=new Array(); </script>
        <style>
         body{margin:0 0 0 0;padding:0 0 0 0;}
        </style>
		<div>
		<span <logic:equal value="on" parameter="searchText">style="display:none"</logic:equal>>
        	<input type="text" id="keywordsInput" value="" style="width:300px;"/> <button onclick="doSearchByKey()" style="font-size:12px;width:65px;height:23px;margin-left:5px;line-height:18px;">搜索</button>
        </span>
        </div>
        
        <form name="nodeForm" id="nodeForm" action="#">
                
        <div id="ffdivsty" style="width: expression(document.body.clientWidth);height:expression(document.body.clientHeight-85);overflow-y: scroll;overflow-x: scroll;">
          <table cellpadding="0" cellspacing="0" id="listTable">
            <thead>
            <tr>
                <th>&nbsp;</th>
                <logic:present name="selectedProps">
                 <logic:iterate id="selectedProp" name="selectedProps">
                    <th align="center" nowrap="nowrap"><bean:write name="selectedProp" property="name" ignore="true"/><img onclick="SortContentsDesc('<bean:write name="selectedProp" property="ID" ignore="true"/>')" src="../images/down.gif" alt="降序排列" align="absmiddle"  /><img  onclick="SortContentsAsc('<bean:write name="selectedProp" property="ID" ignore="true"/>')" src="../images/up.gif" alt="升序排列"  align="absmiddle" /></th>
                </logic:iterate>
                </logic:present>
                <logic:notPresent name="selectedProps">
                <th align="center" nowrap="nowrap">ID</th>
                </logic:notPresent>
                </tr>
            </thead>
            <tbody>
            <logic:iterate id="node" name="nodes" length="20">
                <script type="text/javascript">
                contents['<bean:write name="node" property="ID"/>']=new Object();
                contents['<bean:write name="node" property="ID"/>']['id']='<bean:write name="node" property="ID"/>';
                contents['<bean:write name="node" property="ID"/>'].hasSelect=0;
                </script>
                <tr bgcolor="white" name='<bean:write name="node" property="ID"/>'>
                    <td>
                        <logic:equal  value="true" parameter="multiple">
                            <input  type="checkbox" name="node" value='<bean:write name="node" property="ID"/>' class="idradio" id='<bean:write name="node" property="ID"/>'/>
                       </logic:equal>
                        <logic:notEqual value="true" parameter="multiple">
                            <input  type="radio" name="node" value='<bean:write name="node" property="ID"/>' class="idradio" id='<bean:write name="node" property="ID"/>'/>
                        </logic:notEqual>
                    </td>
                    <logic:present name="selectedProps">
                    <logic:iterate id="selectedProp" name="selectedProps"><bean:define id="propID" name="selectedProp" property="ID" type="java.lang.String"/>
                        <script type="text/javascript">contents['<bean:write name="node" property="ID"/>']['<%= propID%>']='<cms:node name="node" propertyName="<%= propID%>" ignore="true" filter="true"/>'; </script>
                        <td nowrap="nowrap">&nbsp;<cms:node name="node" propertyName="<%= propID%>" ignore="true" filter="true" /></td>
                        </logic:iterate>
                        </logic:present>
                        <logic:notPresent name="selectedProps">
                			<td nowrap="nowrap">&nbsp;<bean:write name="node" property="ID"/></td>
                		</logic:notPresent>
                    </tr>
                </logic:iterate>
            </tbody>
            </table>
          </div>
        </form>
        <logic:notEqual value="0" name="nodes" property="size">
        <div id="footer"><fulong:pager pattern="goto"/></div>
    </logic:notEqual>
        <script language="javascript" type="text/javascript">     
        var oValue = '';
        <logic:present name="selectedProps">
	        <logic:iterate id="selectedProp" name="selectedProps">
	        	oValue = oValue + '<bean:write name="selectedProp" property="ID" ignore="true"/>'+ '*' ;
	        </logic:iterate>
        </logic:present>
        
            var multiple='<bean:write name="multiple"/>'
            function IsOptionExists(value,arr){
              if(arr!=null)
              for(var j=0;j<arr.length;j++){
                if(arr[j]==value)
                return true;
              }
              return false;
            }
            window.onload = function(){
              if(document.getElementById("listTable")!=null){
                ListTable.Init(document.getElementById("listTable"));
              }
            }
            ListTable.OnRowSelected=function(oRow){
              var oSelected=document.getElementById(oRow.getAttribute("name"));
              if(multiple=="true"){
                if(contents[oSelected.value].hasSelect==0){
                if(!IsOptionExists(contents[oSelected.value],self.parent.results)){
                  self.parent.results.push(contents[oSelected.value]);
                  contents[oSelected.value].hasSelect=1;
                  oRow.className=ListTable.SelectedRowClassName;
                  oSelected.checked=true;
                }
              }
              else{
                parent.results.remove(contents[oSelected.value]);
                contents[oSelected.value].hasSelect=0;
                oRow.className="";
                  oSelected.checked=false;
                }
              }
              else{
                self.parent.results=contents[oSelected.value];
                  oSelected.checked=true;
              }
            }
            <logic:equal  value="true" parameter="multiple">
           ListTable.OnClickRow=function(oRow, ctrlKey){
             this.OnRowSelected(oRow);
           }
            </logic:equal>
            function SortContentsDesc(propertyName){
              var keywordsInput = document.getElementById("keywordsInput");
              var keyWords = keywordsInput.value;
              window.location='doSearch.do?definition=<bean:write name="definition"/>&searchText=<bean:write name="searchText"/>&multiple=<bean:write name="multiple"/>&sortproperty='+propertyName+'&sorttype=desc'+'&keywords='+encodeURIComponent('<bean:write name="keywords" ignore="true"/>')+'&selectedProps='+oValue;
            }
            function SortContentsAsc(propertyName){
              var keywordsInput = document.getElementById("keywordsInput");
           	  var keyWords = keywordsInput.value;
              window.location='doSearch.do?definition=<bean:write name="definition"/>&searchText=<bean:write name="searchText"/>&multiple=<bean:write name="multiple"/>&sortproperty='+propertyName+'&sorttype=asc'+'&keywords='+encodeURIComponent('<bean:write name="keywords" ignore="true"/>')+'&selectedProps='+oValue;
            }

            function doSearchByKey(){
                var keywordsInput = document.getElementById("keywordsInput");
                if(keywordsInput!=null){
                    var keyWords = keywordsInput.value;
                    if(keyWords!=null&&keyWords!=""){
                    	window.location='doSearch.do?lucene=on&definition=<bean:write name="definition"/>&searchText=<bean:write name="searchText"/>&multiple=<bean:write name="multiple"/>&keywords='+encodeURIComponent(keyWords)+'&selectedProps='+oValue;
                    }
                }            	
            }
            </script>
 </body>
 </html>
