<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
<tiles:put name="title">导入内容定义和节点 </tiles:put>
<script language="javascript">
 window.onload = function(){
        if(navigator.userAgent.toLowerCase().indexOf("firefox")>=0){
        	 document.body.style.overflow = "hidden";
        }
    }
 </script>
  <tiles:put name="dialog">
  <form action="import.do"  enctype="multipart/form-data"   method="post">
  <table width="100%" border="0" cellpadding="4" cellspacing="0">
  <tr>
  <td width="180" valign="middle"><html:img page="/images/paddingleft.jpg" module="/common" style="border:2px inset"></html:img> </td>
  <td valign="bottom"><div>请输入文件：</div>
      <input type="file" name="file" maxlength="32" size="37"/>
      <fieldset style="padding:5px 5px 5px 5px;height:200px;">
      <legend>说明</legend>
      <div>该文件必须是通过本系统导出的内容定义或者节点定义的xml格式或excel格式的文件。</div>
      </fieldset>
  </td>
  </tr>
  </table>
      <div class="operation">
        <button onclick="check();">确定</button>
        <button id="btnCancel" type="button" onclick="window.close()">取消</button>
      </div>
    </form>
    <script>
    	function check(){
			if((document.forms[0].file.value.lastIndexOf(".xls") != -1) || (document.forms[0].file.value.lastIndexOf(".xml") != -1)){
				document.forms[0].submit();
			}else{
				alert("文件格式不对!");
			}
        }
    </script>
  </tiles:put>
</tiles:insert>
