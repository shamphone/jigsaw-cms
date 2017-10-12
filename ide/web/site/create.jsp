<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert definition="dialog_frame">
  <tiles:put name="title">新建</tiles:put>
  <tiles:put name="javascript">
    <style type="text/css">
      #modules td{width:85px;height:60px;vertical-align:middle;text-align:center;}
      td.module{border:1px solid buttonface;}
      td.hilightModule{border:1px solid black; background-color:#c0c0c0;}
      td.selectedModule{border:1px solid black; background-color:#d0d0d0;}
      </style>
      <script type="text/javascript" language="javascript">
        var selectedIndex = 0;
        function doMouseOut(oTD){
          if(oTD.parentElement.rowIndex == selectedIndex )
            oTD.className = 'selectedModule';
          else
            oTD.className = 'module';
        }
        function doMouseOver(oTD){
          oTD.className = 'hilightModule';
        }
        function doClick(oTD, path){
          if(oTD.parentElement.rowIndex != selectedIndex ) {
            modules.rows[selectedIndex].cells[0].className = "module";
            selectedIndex = oTD.parentElement.rowIndex;
            oTD.className = 'selectedModule';
            contentFrame.location.href= path + "&time="+new Date().getTime();
          }
        }
      </script>
  </tiles:put>
  <tiles:put name="dialog">
    <table height="100%" width="100%" border="0" cellpadding="2" cellspacing="2">
      <tr>
        <td height="350px" style="border:1px inset;" valign="top" width="87px" align="center">
          <table width="100%" cellpadding="0" cellspacing="1" id="modules" align="center">
	            <tr>
	              <td onmouseover="doMouseOver(this)" onmouseout="doMouseOut(this)" class="selectedModule" onclick="doClick(this,'<html:rewrite module="/site/channel" page="/create.do"/>?<%= request.getQueryString() %>')">
	                <html:img page="/images/channel32.gif" module="/common" width="32" height="32" border="0"/>
	                <div align="center">新建页面</div>
	            </td>
	            </tr>
	            <tr>
	              <td onmouseover="doMouseOver(this)" onmouseout="doMouseOut(this)" class="module" onclick="doClick(this,'<html:rewrite module="/site/clip" page="/createJspf.do"/>?<%= request.getQueryString() %>')">
	                <html:img page="/images/channel33.gif" module="/common" width="32" height="32" border="0"/>
	                <div align="center">新建页面片段</div>
	              </td>
	            </tr>
	            <tr>
					<td onmouseover="doMouseOver(this)" onmouseout="doMouseOut(this)" class="module" onclick="doClick(this,'<html:rewrite module="/site/script" page="/toCreate.do"/>?<%= request.getQueryString() %>')">
						<html:img module="/common" page="/images/new2.gif" width="32" height="32" border="0"/>
						<div align="center">新建脚本文件</div>
					</td>
	            </tr>
        	    <tr>
					<td onmouseover="doMouseOver(this)" onmouseout="doMouseOut(this)" class="module" onclick="doClick(this,'<html:rewrite module="/site/css" page="/toCreate.do"/>?<%= request.getQueryString() %>')">
				 		<html:img module="/common" page="/images/css16.gif" width="32" height="32" border="0"/>
					 	<div align="center">新建样式文件</div>
					</td>
	            </tr>
	            <!-- <tr>
					<td onmouseover="doMouseOver(this)" onmouseout="doMouseOut(this)" class="module" onclick="doClick(this,'<html:rewrite module="/site/rss" page="/toCreateRSS.do"/>?<%= request.getQueryString() %>')">
				 		<html:img module="/common" page="/images/css16.gif" width="32" height="32" border="0"/>
					 	<div align="center">新建rss页面</div>
					</td>
	            </tr>
	             -->
          </table>
        </td>
        <td>
          <iframe width="100%" height="100%" id="contentFrame" frameborder="0" marginheight="0" marginwidth="0" scrolling="no" src="channel/create.do?<%= request.getQueryString() %>"></iframe>
        </td>
      </tr>
      </table>
    </tiles:put>
  </tiles:insert>
