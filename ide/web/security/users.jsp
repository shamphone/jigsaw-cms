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
        <title>用户列表</title>
        <meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
        <base target="content"/>
        <link rel="stylesheet" type="text/css" href="role.css"/>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/form.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="classes/userlist.js"></script>     
 <script language="Javascript" type="text/Javascript" >
 	window.onload = function(){
 		parent.frames["tree"].UMTree.definitionID = 'principal-scheme';
 	    if(document.getElementById("listTable")!=null){
	 	      ListTable.Init(document.getElementById("listTable"));
 	    }
	   }
    </script>
    </head>
    <body><div id="oListPanel"><table id="listTable" width="100%" cellpadding="0" cellspacing="0" >    
                <thead>
                <tr>
                    <th width="20px"><input type="checkbox" id="chkAll" onclick="ChooseAll('chkAll')"/>&nbsp;</th>
                    <th>ID</th>
                    <th>用户名</th>
                    <th>真实姓名</th>
                    <th>创建时间</th>
                    <th>最后登入时间</th>
                    </tr>
                </thead>
                <tbody>
                    <logic:iterate id="user" name="users" length="30" indexId="index">
                        <tr id="<bean:write name="user" property="ID"/>">
                        <td><input type="checkbox" name="users" value='<bean:write name="user" property="ID"/>'/></td>
                        <td><bean:write name="user" property="ID"/></td>              
                        <td><cms:node name="user" propertyName="user-username" ignore="true" />&nbsp;</td>
                        <td><cms:node name="user" propertyName="user-commonname" ignore="true" />&nbsp;</td>
                        <td><cms:node name="user" propertyName="createdTime" ignore="true" />&nbsp;</td>
                        <td><cms:node name="user" propertyName="lastLoginTime" ignore="true" />&nbsp;</td>                       
                    </tr>
                </logic:iterate>
            </tbody>
        </table>
       </div>
    <div id="gotobar">                                
     <fulong:pager pattern="goto" target="_self" pageSize="30"/> </div>
    </body>
</html>
