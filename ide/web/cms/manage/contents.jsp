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
	 	        if(!document.all){
		 	        document.getElementById("oListPanel").style.height = document.body.clientHeight-27;
	 	        }
 	         }
	       }
        </script>
    </head>
    <body><div id="oListPanel"><table id="listTable" width="100%" cellpadding="0" cellspacing="0" >    
                <thead>  <!-- 每一列的表头显示 -->
                <tr>
                    <th width="20px"><input type="checkbox" id="chkAll" onclick="ChooseAll('chkAll')"/>&nbsp;</th>
                    <th width="20px">&nbsp;</th>
                    <th width="20px">ID</th>
                    <logic:iterate id="property" name="columns">
                     <logic:notEqual name="property" property='type' value="0">
                        <th nowrap="nowrap"><bean:write name="property" property='name' ignore="true"/></th>
                     </logic:notEqual></logic:iterate>
                     </tr>
                </thead>
                <tbody>
                    <logic:iterate id="content" name="contents" length="20" indexId="index">
                        <tr id="<bean:write name="content" property="ID"/>">
                        <td><input type="checkbox" id="checkID"/></td>
                        <td><bean:write name="contents" property="position"/></td>
                        <td><bean:write name="content" property="ID"/></td>
                        <logic:iterate id="column" name="columns">
                        <bean:define id="property" name="column" property="ID" type="java.lang.String" />  
                        <logic:present name="content" property='<%= "nodeProperty("+property+")"%>'>
                        <bean:define id="ctype" name="content" property='<%= "nodeProperty("+property+").type" %>' />
                        <logic:equal value="1" name="ctype">                     
                          <td  class="cell1" title="<cms:node name="content" propertyName="<%=property%>" ignore="true" />">
                          <span><cms:node name="content" propertyName="<%=property%>" ignore="true"/>&nbsp;</span>
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
                          <td class="cell1">
                         <span><fulong:filterHtml  length="40"><cms:node name="content" propertyName="<%=property%>" ignore="true"/></fulong:filterHtml>&nbsp;</span>
                          </td>
                          </logic:equal>  
                          </logic:present>  
                        </logic:iterate>
                    </tr>
                </logic:iterate>
            </tbody>
        </table>
       </div>
    <div id="gotobar">                          
     <iframe id="tipsFrame" width="400" height="400" frameborder="1" scrolling="auto" style="display:none;z-index:10;position:absolute;left:0px;top:0px" ></iframe>        
     <fulong:pager pattern="goto" target="_self"/> </div>
    </body>
</html>
