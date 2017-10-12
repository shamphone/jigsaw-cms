<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>用户列表</title>
        <meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
        <base target="content"/>
        <link rel="stylesheet" type="text/css" href="role.css"/>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>     
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/form.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="classes/um.js"></script>
        <script language="Javascript" type="text/Javascript" src="classes/userlist.js"></script>     
 <script language="Javascript" type="text/Javascript" >
 	window.onload = function(){
 		parent.frames["tree"].UMTree.definitionID = '<bean:write name="roleID" ignore="true"/>';
 	    if(document.getElementById("listTable")!=null){
	 	      ListTable.Init(document.getElementById("listTable"));
 	    }
 	    if(!document.all){
 	    	document.getElementById("oListPanel").style.height = document.body.clientHeight-27;
 	    }
	   }
    </script>
    </head>
    <body><div id="oListPanel"><table id="listTable" width="100%" cellpadding="0" cellspacing="0" >    
                <thead>
                <tr>
                    <th width="20px"><input type="checkbox" id="chkAll" onclick="ChooseAll('chkAll')"/></th>                    
                    <th>ID</th>
                    <th>用户名</th>
                    <th>真实姓名</th>
                    <th>创建时间</th>
                    <th>最后登入时间</th>
                </tr>
                </thead>
                <logic:present name="members">
                <tbody>
                    <logic:iterate id="user" name="members" length="20" indexId="index">
                        <tr id="<bean:write name="user" property="ID" ignore="true"/>">
                        <td><input type="checkbox" name="users" value='<bean:write name="user" property="ID" ignore="true"/>'/></td>
                        <td><bean:write name="user" property="ID" ignore="true"/></td>              
                        <td><cms:node name="user" propertyName="user-username" ignore="true" />&nbsp;</td>
                        <td><cms:node name="user" propertyName="user-commonname" ignore="true" />&nbsp;</td>
                        <td><cms:node name="user" propertyName="createdTime" ignore="true" />&nbsp;</td>
                        <td><cms:node name="user" propertyName="lastLoginTime" ignore="true" />&nbsp;</td>                       
                    </tr>
                </logic:iterate>
            </tbody>
                </logic:present>
                
        </table>
       </div>
       <logic:present name="members">
    <div id="gotobar">                                
     <fulong:pager pattern="goto" target="_self"/> 
    </div>
    </logic:present>
    </body>
</html>
