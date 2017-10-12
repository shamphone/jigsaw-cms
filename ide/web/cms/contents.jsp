<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>内容搜索结果</title>
        <meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
        <base target="content"/>
        <link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/list.css"/>"/>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/list.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/form.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ListTable.js"/>"></script>     
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
    	if(eventTarget!=oRow.firstChild.firstChild){
        if(oRow.firstChild.firstChild.checked==true){
        	oRow.firstChild.firstChild.checked=false;
            }
        else{
        	oRow.firstChild.firstChild.checked=true;
            }
    	}
      }
    </script>
    </head>
    <body style="border:0px;margin:0px;padding:0px;">    
     <div id="oListPanel">
            <table id="listTable" width="100%" cellpadding="0" cellspacing="0" >
                <thead>
                <tr>
                    <th width="20px"><input type="checkbox" id="chkAll" onclick="ChooseAll('chkAll')"/>&nbsp;</th>
                    <th width="20px">&nbsp;</th>
                    <logic:iterate id="property" name="propertyIDs">
                        <th><bean:write  name="category" property='<%="propertyDefinition("+property+").name"%>' ignore="true"/></th>
                    </logic:iterate>
                    </tr>
                </thead>
                <tbody>
                    <logic:iterate id="content" name="contents" length="20" indexId="index">
                        <tr id="<bean:write name="content" property="ID"/>">
                        <td><input type="checkbox" id="checkID"/></td>
                        <td><bean:write name="contents" property="position"/></td>
                        <logic:iterate id="property" name="propertyIDs" type="String">
                          <td><cms:node name="content" propertyName="<%=property%>" ignore="true" />&nbsp;</td>
                        </logic:iterate>
                    </tr>
                </logic:iterate>
            </tbody>
        </table>
       </div>
    <div id="gotobar" style="background-color:scrollbar;height:26px;text-align:right;">
          <fulong:pager pattern="goto" target="_self"/>
    </div>
    </body>
</html>
