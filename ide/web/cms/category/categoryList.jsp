<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="default_main_frame">
 <tiles:putList name="pathes">
        <tiles:add>内容库设计</tiles:add>
        <tiles:add><a href="categoryList.do?categoryID=root">内容分类</a></tiles:add>
        <logic:present name="parentCategories">
        <logic:iterate id="parentCategory" name="parentCategories" offset="1">
          <tiles:add>
          <a href="categoryList.do?categoryID=<bean:write name="parentCategory" property="ID" ignore="true"/>">
            <bean:write name="parentCategory" property="displayName" ignore="true"/>
          </a>
          </tiles:add>
        </logic:iterate>
        </logic:present>
      </tiles:putList>
    <tiles:put name="body">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <html:form action="deleteCategory.do">
                <tr>
                    <td>
                <table width="100%" border="1" cellpadding="2" cellspacing="0" class="tableClass">
                        <input type="hidden" name="categoryID" value="<bean:write name='categoryID' ignore="true"/>"/>
                        <tr>
                            <th width="20px"></th>
                            <logic:notEqual value="root" name="categoryID">
                            <th width="10px" nowrap="nowrap"><input onclick="selectAll()" type="checkbox"/></th>
                            </logic:notEqual>
                            <!--th width="20px">&nbsp;</th-->
                            <th width="135px">名称</th>
                            <th width="90px">内容格式</th>
                            <th width="100px">操作流程</th>
                            <th width="105px">创建时间</th>
                            <th width="125px">所有者</th>
                            <th>操作</th>
                        </tr>
                    </table>
                  </td>
                </tr>
                <tr>
                    <td><div  class="contentBlock">
                        <table width="100%" border="1" cellpadding="2" cellspacing="0" class="tableClass">
                            <logic:iterate id="category" name="categories" indexId="index">
                                <tr>
                                    <td width="20px"><%= (index.intValue()+1) %></td>
                                    <logic:notEqual value="root" name="categoryID">
                                    <td width="10px"><input type="checkbox" value="<bean:write name="category" property="ID"/>" name="IDs"/></td>
                                    </logic:notEqual>
                                    <!--td width="20px">
                                      <logic:present name="category" property="shared">
                                        <logic:equal value="true" name="category" property="shared">
                                          <img alt="" src="../images/shared.gif"/>
                                        </logic:equal>
                                      </logic:present>
                                    </td-->
                                    <td width="135px"><span class="ellipses"><a href="categoryList.do?categoryID=<bean:write name="category" property="ID"/>"><logic:empty  name="category" property="displayName">[无名称]</logic:empty><bean:write name="category" property="displayName"/></a><logic:greaterThan value="0" name="category" property="children.size">(<bean:write name="category" property="children.size"/>)</logic:greaterThan></span></td>
                                    <td width="90px"><span class="ellipses"><logic:present name="category" property="nodeDefinition"><bean:write name="category" property="nodeDefinition.name" ignore="true"/></logic:present></span></td>
                                    <td width="100px"><span class="ellipses"><logic:present name="category" property="processDefinition"><bean:write name="category" property="processDefinition.name"/></logic:present></span></td>
                                    <td width="105px"><bean:write name="category" property="createdTime" ignore="true" format="yyyy-MM-dd HH:mm"/> </td>
                                    <td width="125px"><span class="ellipses"><logic:present name="category" property="owner"><bean:write name="category" property="owner.commonname"/></logic:present></span></td>
                                    <td nowrap="nowrap"><a href="modifyCategory.do?categoryID=<bean:write name="category" property="ID"/>">修改</a> <a href="copyCategory.do?categoryID=<bean:write name="category" property="ID"/>">复制</a> <a href="authorization.do?categoryID=<bean:write name="category" property="ID"/>&authType=manage">权限</a> <!--<a href="shareCategory.do?categoryID=<bean:write name="category" property="ID"/>">共享</a> --><a href="categoryQuota.do?categoryID=<bean:write name="category" property="ID"/>">配额</a></td>
                                </tr>
                            </logic:iterate>
                        </table></div></td>
                    </tr>
                    <!--<tr>
                        <td><div class="operation">
                          <logic:present name="categoryID">
                            <logic:notEqual value="root" name="categoryID">
                              <input id="new" class="commonbut" type="button" onclick="window.location='creatCategory.do?categoryID=<bean:write name='categoryID' ignore="true"/>'" value="新建"/>
                              <input id="move" class="commonbut" type="button" onclick="transfer()" value="移动到..."/>
                              <input id="delete" class="commonbut" type="button" onclick="del()" value="删除"/>
                            </logic:notEqual>
                          </logic:present>
                        </div>

                        </td>
                    </tr>-->
                </html:form>
            </table>
            <script language="JavaScript" type="text/Javascript">
                var flag=false;
                function selectAll(){
                    var form = document.forms[0];
                    flag = (flag==true)?false:true;
                    selectCheckBoxAll(form.IDs,flag);
                }
                function del(){
                    disableButton();
                    if (categoryCheck(document.forms[0])){
                        if(confirm('您的操作将无法撤销，请确定')) {
                            document.forms[0].submit();
                        }
                    }
                    else{
                        enableButton();
                    }
                }
                function transfer(){
                    disableButton();
                    if (categoryCheck(document.forms[0])){
                        if(true) {
                            document.forms[0].action = "transferCategory.do";
                            document.forms[0].submit();
                        }
                    }
                    else{
                        enableButton();
                    }
                }
                function categoryCheck(form){
                    if(form==null)return false;
                    if(checkNull(form.IDs)){
                        return true;
                    }else{
                        alert("请选择内容分类！");
                        return false;
                    }
                }
                </script>
    </tiles:put>
</tiles:insert>
