<%@ page contentType="text/html; charset=UTF-8" %><%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %><%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<marquee id='overallMar<%=(String)request.getAttribute("javax.portlet.id")%>' behavior="<bean:write name="preferences" property="value(behavior)" ignore="true"/>" direction="<bean:write name="preferences" property="value(direction)" ignore="true"/>" loop="<bean:write name="preferences" property="value(loop)" ignore="true"/>" scrollamount="<bean:write name="preferences" property="value(scrollamount)" ignore="true"/>" scrolldelay="<bean:write name="preferences" property="value(scrolldelay)" ignore="true"/>" width="<bean:write name="preferences" property="value(rollwidth)" ignore="true"/>" height="<bean:write name="preferences" property="value(rollheight)" ignore="true"/>" align="left" onMouseOver="this.stop()" onMouseOut="this.start ()">
</marquee>
<script type="text/javascript">
try{
	//add by lkl 给Element元素添加outerHTML属性
	if(typeof(HTMLElement)!="undefined" && !window.opera)
	{
	HTMLElement.prototype.__defineGetter__("outerHTML",function()
	{
	var a=this.attributes, str="<"+this.tagName, i=0;for(;i<a.length;i++)
	if(a[i].specified)
	str+=" "+a[i].name+'="'+a[i].value+'"';
	if(!this.canHaveChildren)
	return str+" />";
	return str+">"+this.innerHTML+"</"+this.tagName+">";
	});
	HTMLElement.prototype.__defineSetter__("outerHTML",function(s)
	{
	var r = this.ownerDocument.createRange();
	r.setStartBefore(this);
	var df = r.createContextualFragment(s);
	this.parentNode.replaceChild(df, this);
	return s;
	});
	HTMLElement.prototype.__defineGetter__("canHaveChildren",function()
	{
	return !/^(area|base|basefont|col|frame|hr|img|br|input|isindex|link|meta|param)$/.test(this.tagName.toLowerCase());
	});
	} 
	var theTable<%=(String)request.getAttribute("javax.portlet.id")%> = document.getElementById('<bean:write name="preferences" property="value(propertyId)" />');
	overallMar<%=(String)request.getAttribute("javax.portlet.id")%>.innerHTML = theTable<%=(String)request.getAttribute("javax.portlet.id")%>.outerHTML;
}catch(e){
	alert("系统出错（表格整体占位符有误）！");
} 
</script>