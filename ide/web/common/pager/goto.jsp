<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %><%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %><%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %><%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
共计<bean:write name="com.fulong.pager" property="count"/>项内容<logic:notEqual value="0" name="com.fulong.pager" property="pageCount">，当前为<bean:write name="com.fulong.pager" property="nextPage"/>/<bean:write name="com.fulong.pager" property="pageCount"/>页</logic:notEqual><logic:present name="portlet.tag.first">，<a href="<bean:write name="portlet.tag.first" filter="false"/>" target="_self">首页</a></logic:present><logic:present name="portlet.tag.prev">，<a href="<bean:write name="portlet.tag.prev" filter="false"/>" target="_self">上一页</a></logic:present><logic:present name="portlet.tag.next">，<a href="<bean:write name="portlet.tag.next" filter="false"/>" target="_self">下一页</a></logic:present><logic:present name="portlet.tag.last">，<a href="<bean:write name="portlet.tag.last" filter="false"/>" target="_self">末页</a></logic:present>
<logic:greaterThan name="com.fulong.pager" property="count" value="2">，跳转到 <input size="4" name="pagenum" value="1" id="pagenum"/><button onclick="gotoPage()">跳转</button></logic:greaterThan>

<script language="JavaScript" type="text/Javascript">
function gotoPage(){
  var oValue = document.getElementById("pagenum").value;
  var maxPage = '<bean:write name="com.fulong.pager" property="pageCount"/>';
  if((oValue==null)||(oValue.length==0)||isNaN(parseInt(oValue))||(parseInt(oValue)<1)||(parseInt(oValue)>maxPage)){
    alert('请输入1到'+maxPage+'之间的数字');
  }else {
    window.location='<bean:write name="portlet.tag.URLPattern" filter="false"/>'.replace('XXX', (oValue-1));
  }
}
</script>
