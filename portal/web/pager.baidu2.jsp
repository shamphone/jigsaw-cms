<%@ page contentType="text/html; charset=UTF-8" %><%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %><%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %><%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %><%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<bean:define id="num1" name="portlet.tag.pager" property="pageCount" ></bean:define>
<bean:define id="no1" name="portlet.tag.pager" property="nextPage" ></bean:define>
<table>
<tr>
<td id="headPage" align="center" valign="middle"><logic:present name="portlet.tag.prev"><a href="<bean:write name="portlet.tag.prev"/>" target="_self">上一页</a></logic:present></td>
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
int i = listBegin;
while(i < listEnd) {   
    if (i != no2) {//如果i不等于当前页   
%>
<td id="noClickPage"  valign="middle" align="center"><a href="<bean:write name="portlet.tag.URLPattern" filter="false"/>&pageNo=<%=i %>"><%=i+1 %></a></td>       
<%     } else {   %>
<td id="ClickedPage"  valign="middle" align="center"><%=i+1 %></td>   
 <% }
i++;
}
if(i < num2)
out.print("<td id='ClickedPage'>...</td>");
%> 
<logic:present name="portlet.tag.next"><td id="footPage" align="center"  valign="middle"><a href="<bean:write name="portlet.tag.next"/>" target="_self">下一页</a></td></logic:present>
<logic:notEqual value="1" name="portlet.tag.pager" property="pageCount"><td align="center" valign="middle">跳转到<input name="pagenum" value="1" id="pagenum" style="width:35px;" onblur="checkPageNum(this,<bean:write name="portlet.tag.pager" property="pageCount"/>)"/><button type="button" id="jump" onclick="gotoPage(this.parentElement.children('pagenum'),'<bean:write name="portlet.tag.URLPattern" filter="false"/>'+'&pageNo=')">跳转</button></td></logic:notEqual></tr></table>