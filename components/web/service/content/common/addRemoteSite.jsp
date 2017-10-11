<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<tiles:insert definition="dialog_frame">

	<tiles:put name="title">添加远程分类</tiles:put>
	<tiles:put name="javascript">        
        <script language="Javascript" type="text/javascript" src="<html:rewrite module="/cms" page="/classes/cmsdialog.js"/>"></script>
        <script type="text/Javascript" language="Javascript">
        window.onload = function(){
        	if(!document.all){
    			document.getElementById("toolbar").style.top = document.body.clientHeight-40;
    			document.getElementById("toolbar").style.left = 105;
    			document.getElementById("toolbar").style.textAlign = "center";
    			document.getElementById("pannelSelect").style.height = document.body.clientHeight-2;
    			document.body.style.overflow = "hidden"
    		}
        }
    function getName(url){
      var req = getXMLHttpRequest();
      var callback=function(){
        if ((req.readyState==4)&&(req.status==200)){
          var data=req.responseText;
           if (data.length>20)
          {
              document.forms[0].siteName.value="";
              document.getElementById("siteNameSpan").innerHTML = "网址不正确或者不存在，请重新输入！";
              document.getElementById("btnInsert").disabled = true;
            return;
          }
          switch (data)
          {
            case "400" :;
            case "403" :;
            case "404" :;
            case "500" :;
            case "503" :;
            case "505" :
              document.forms[0].siteName.value="";
              document.getElementById("siteNameSpan").innerHTML = "网址不正确或者不存在，请重新输入！";
              document.getElementById("btnInsert").disabled = true;
              return;
            case "invalid address error" :
              document.forms[0].siteName.value="";
              document.getElementById("siteNameSpan").innerHTML = "网址不正确或者不存在，请重新输入！";
              document.getElementById("btnInsert").disabled = true;
              return;

          }
          document.forms[0].siteName.value=data;
          document.getElementById("siteNameSpan").innerHTML='网站名称：'+data;
          document.getElementById("btnInsert").disabled = false;
          return;
        }
        document.getElementById("btnInsert").disabled = true;
      }
      var myurl = "<html:rewrite page='/getRemoteHTML.do' module='/common'/>?url=http://"+url+"/common/getSiteName.do";
      try{
        sendRequest(req, myurl, callback);
      }catch(e){
        alert('地址不正确，或网络状态不好，请稍后尝试。');
      }
    }
    function ok(){
      var categoryID=document.getElementById("categoryID").value;
      var type=document.getElementById("type").value;
      var URL=document.getElementById("URL").value;
      var username=document.getElementById("username").value;
      if(username.trim()=="")
      return;
      var password=document.getElementById("password").value;
      if(password.trim()=="")
      return;
      var siteName=encodeURI(document.getElementById("siteName").value);
      var definition=CMSDialog.AddRemoteCategory(categoryID,type,URL,username,password,siteName);
      window.close();
    }
    window.onload=function(){
		document.getElementById("localCategoryID").value='<%=request.getParameter("categoryID")%>';
		document.getElementById("processID").value='<%=request.getParameter("processID")%>';
		document.getElementById("activityID").value='<%=request.getParameter("activityID")%>';
		document.getElementById("serviceID").value='<%=request.getParameter("serviceID")%>';
        }
</script>
 </tiles:put>
    <tiles:put name="dialog">
    <html:form action="insertSite.do" method="POST">
      <table align="center">
      <table width="99%" class="sheetClass" cellpadding="0" cellspacing="0" border="0" id="tb">
        
          <html:hidden styleId="processID" property="processID"/>
          <html:hidden styleId="activityID" property="activityID"/>
          <html:hidden styleId="serviceID" property="serviceID"/>
          <html:hidden styleId="localCategoryID" property="value(localCategoryID)"/>
          <html:hidden styleId="siteName" property="value(remoteSiteName)"/>
          
                <tr>
                  <th width="100px">目标网站</th>
                  <td  height="30px">http://<html:text styleId="remoteDomain" size="40" property="value(remoteURL)"/>
                <button onclick="getName(document.getElementById('remoteDomain').value);">检测域名</button>
                </td>
                </tr>
                <tr >
                <th></th>
                <td height="20px"><span id="siteNameSpan"></span></td>
                </tr>
              </table>
            </td>
          </tr>
        
      </table>
      </table>
      <div class="operation">
      <button  type="submit" id="btnInsert" class="commonbut" style="width:75">确认</button>
      <button onclick="window.close();" class="commonbut" style="width:75"> 取消</button>
      </div>
      </html:form>
</tiles:put>
</tiles:insert>
