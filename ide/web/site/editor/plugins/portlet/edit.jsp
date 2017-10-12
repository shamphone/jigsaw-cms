<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<html>
  <head>
    <title>编辑占位符</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta http-equiv="EXPIRES" content="0"/>
    <meta http-equiv="Pragma" Content="No-cach" />
    <base target="_self"/>
    <script type="text/javascript">
      function doPost(){
        var param=window.parent.dialogArguments;
        oForm.action="<html:rewrite module='/site/editor' page='/editPortlet.do'/>";
        oForm.elements["portlet.window.owner"].value=param.portlet;
        oForm.elements["portlet.pref"].value=param.preferences;
        oForm.elements["portlet.type"].value= param.type;
        oForm.elements["channel.page"].value= param.page;
        if(param.channel!=null)
            oForm.elements["channel"].value=param.channel.ID;
        if(param.definition!=null)
            oForm.elements["definition"].value=param.definition;
        if(param.formDefinition!=null)
            oForm.elements["formDefinition"].value=param.form.getAttribute("formDefinition");
       // alert("param.form.formDefinition="+param.form.formDefinition+"@@@@@@@@"+param.form.outerHTML)
		/*
         for(var i in param.form){
           alert(i+"是："+param.form[i])
        }*/
        if(param.node!=null)
            oForm.elements["node"].value=param.node;
        if(param.form){
          if(param.form.getAttribute("definition")!=null)
          	oForm.elements["formDefinition"].value=param.form.getAttribute("definition");
          if(param.form.getAttribute("node")!=null)
          oForm.elements["node"].value=param.form.getAttribute("node");
        }
        oForm.submit();
      }
      </script>
      </head>
      <body onload="doPost()">
        <form action="" method="POST" id="oForm">
          <input type="hidden" name="portlet.mode" value="edit"/>
          <input type="hidden" name="portlet.window.owner"/>
          <input type="hidden" name="definition"/>
          <input type="hidden" name="formDefinition"/>
          <input type="hidden" name="node"/>
          <input type="hidden" name="portlet.pref"/>
          <input type="hidden" name="portlet.type"/>
          <input type="hidden" name="channel.page"/>
          <input type="hidden" name="channel"/>
        </form>
      </body>
    </html>
