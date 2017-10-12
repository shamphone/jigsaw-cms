<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>内容搜索结果</title>
        <meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
        <base target="content"/>
        <link rel="stylesheet" type="text/css" href="contents.css"/>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/form.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="classes/cmslist.js"></script>     
 <script language="Javascript" type="text/Javascript" >
 	window.onload = function(){
 	    if(document.getElementById("listTable")!=null){
	 	      ListTable.Init(document.getElementById("listTable"));
 	    }
	   }
    
    	var checkFlag = true;
    	function ChooseAll(CheckAll)
    	{
    	　　if( checkFlag ) // 全选　
    	　　{
    	　　　　　var inputs = document.getElementsByTagName("INPUT");
    	　　　　　for (var i=0; i < inputs.length; i++) // 遍历页面上所有的 input 
    	　　　　　{
    	　　　　　　　if (inputs[i].type == "checkbox" && inputs[i].id != CheckAll )
    	　　　　　　　{
    	　　　　　　　　　　inputs[i].checked = true;
					      
    	　　　　　　　}     
    	　　　　　}
    	　　　　　checkFlag = false;
    	　　}
    	　　else // 取消全选
    	　　{
    			 top.nodes = new Array();	
    	　　　　　var inputs = document.getElementsByTagName("INPUT");
    	　　　　　for (var i=0; i < inputs.length; i++) // 遍历页面上所有的 input 
    	　　　　　{
    	　　　　　　　if (inputs[i].type == "checkbox" && inputs[i].id != CheckAll )
    	　　　　　　　{
    	　　　　　　　　　　inputs[i].checked = false;
    	　　　　　　　}     
    	　　　　　}
    	　　　　　checkFlag = true;
    	　　}
    	}
    </script>
     <script language="Javascript" type="text/Javascript" >
    
    ListTable.OnRowSelected=function(oRow){
		//获取单击内容展示表的事件源，如果单击的是checkbox，则不修改checkbox状态
		//如果单击其他位置，则修改当前行的checkbox状态
    	var eventTarget = window.event.srcElement;
    	var checkbox = getFirstElement(getFirstElement(oRow));
    	if(eventTarget != checkbox){
        	if(checkbox.checked == true){
            	checkbox.checked = false;
        	}else{
            	checkbox.checked = true;
        	}
    	}
    	
      }
    </script>
    </head>
    <body><div id="oListPanel"><table id="listTable" width="100%" cellpadding="0" cellspacing="0" >
                <thead>
                <tr>
                    <th width="20px"><input type="checkbox" id="chkAll" onclick="ChooseAll('chkAll')"/>&nbsp;</th>
                    <th width="20px">&nbsp;</th>
                    <logic:iterate id="property" name="columns">
                        <th nowrap="nowrap"><bean:write  name="property" property='name' ignore="true"/></th>
                    </logic:iterate>
                    </tr>
                </thead>
                <tbody>
                    <logic:iterate id="content" name="contents" length="20" indexId="index">
                        <tr id="<bean:write name="content" property="ID"/>">
                        <td><input type="checkbox" id="checkID"/></td>
                        <td><bean:write name="contents" property="position"/></td>
                        <logic:iterate id="column" name="columns">
                        <bean:define id="property" name="column" property="ID" type="java.lang.String" />     
                        <logic:present name="content" property='<%= "nodeProperty("+property+")"%>'>
                        <bean:define id="ctype" name="content" property='<%= "nodeProperty("+property+").type" %>' />
                        <logic:equal value="1" name="ctype">                     
                          <td  class="cell1" title="<cms:node name="content" propertyName="<%=property%>" ignore="true" />">
                          <span><cms:node name="content" propertyName="<%=property%>" ignore="true" />&nbsp;</span>
                          </td>
                          </logic:equal>                      
                        <logic:equal value="2" name="ctype">                     
                          <td nowrap="nowrap">二进制文件</td>
                          </logic:equal>
                        <logic:equal value="3" name="ctype">                     
                          <td nowrap="nowrap"><cms:node name="content" propertyName="<%=property%>" ignore="true" />&nbsp;</td>
                          </logic:equal>
                        <logic:equal value="4" name="ctype">                     
                          <td nowrap="nowrap"><cms:node name="content" propertyName="<%=property%>" ignore="true" />&nbsp;</td>
                          </logic:equal>
                        <logic:equal value="5" name="ctype">                     
                          <td nowrap="nowrap"><cms:node name="content" propertyName="<%=property%>" ignore="true"/>&nbsp;</td>
                          </logic:equal>
                        <logic:equal value="6" name="ctype">                     
                          <td nowrap="nowrap"><cms:node name="content" propertyName="<%=property%>" ignore="true" />&nbsp;</td>
                          </logic:equal>
                        <logic:equal value="7" name="ctype">                     
                          <td nowrap="nowrap"><cms:node name="content" propertyName="<%=property%>" ignore="true"/>&nbsp;</td>
                          </logic:equal>
                        <logic:equal value="8" name="ctype">                     
                          <td nowrap="nowrap"><cms:node name="content" propertyName="<%=property%>" ignore="true"/>&nbsp;</td>
                          </logic:equal>
                        <logic:equal value="9" name="ctype">                     
                          <td nowrap="nowrap"><cms:node name="content" propertyName="<%=property%>" ignore="true"/>&nbsp;</td>
                          </logic:equal>                         
                        <logic:equal value="10" name="ctype">                     
                          <td  class="cell1" title='<cms:node name="content" propertyName="<%=property%>" ignore="true" filter="true" />'>
                          <span><cms:node name="content" propertyName="<%=property%>" ignore="true" length="40"  filter="true"/>&nbsp;</span>
                          </td>
                          </logic:equal>    
                          </logic:present>   
                          <logic:notPresent name="content" property='<%= "nodeProperty("+property+")"%>'>
                          <td>&nbsp;</td>
                          </logic:notPresent>               
                        </logic:iterate>
                    </tr>
                </logic:iterate>
            </tbody>
        </table>
       </div>
    <div id="gotobar">
          <fulong:pager pattern="goto" target="_self"/>
    </div></body>
</html>
