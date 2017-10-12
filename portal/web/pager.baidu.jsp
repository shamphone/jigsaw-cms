<%@ page contentType="text/html; charset=UTF-8" %><%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %><%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %><%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %><%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<bean:define id="num1" name="portlet.tag.pager" property="pageCount" ></bean:define>
<bean:define id="no1" name="portlet.tag.pager" property="nextPage" ></bean:define>
<td id="headPage"><logic:present name="portlet.tag.first"><a href="<bean:write name="portlet.tag.first"/>" target="_self">首页</a></logic:present><logic:present name="portlet.tag.prev"> <a href="<bean:write name="portlet.tag.prev"/>" target="_self">上一页</a></logic:present></td>
<%
int no2 = Integer.parseInt(no1.toString()) - 1;
int num2 = Integer.parseInt(num1.toString());
int listStep = 10;//最多显示分页页数   
int listBegin = ( no2 - (int) Math.ceil((double) listStep / 2));//从第几页开始显示分页信息
if (listBegin < 0) { //当前页-(总显示的页列表数/2)   
	listBegin = 0;   
}   
int listEnd = no2 + listStep / 2;//分页信息显示到第几页//当前页+(总显示的页列表数/2)   
if (listEnd > num2) {    
    listEnd = num2;   
}   
for (int i = listBegin; i < listEnd; i++) {   
    if (i != no2) {//如果i不等于当前页   
%>
<td id="noClickPage"><a href="<bean:write name="portlet.tag.URLPattern" filter="false"/>&pageNo=<%=i %>">[<%=i+1 %>]</a></td>       
<%     } else {   %>
<td id="ClickedPage">[<%=i+1 %>]</td>   
 <% }}%> 
<td id="footPage"><logic:present name="portlet.tag.next"><a href="<bean:write name="portlet.tag.next"/>" target="_self">&nbsp;&nbsp;下一页</a></logic:present><logic:present name="portlet.tag.last"> <a href="<bean:write name="portlet.tag.last"/>" target="_self">末页</a></logic:present><br></td>
