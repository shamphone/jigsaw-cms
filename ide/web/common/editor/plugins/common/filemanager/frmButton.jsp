<%@ page contentType="text/html; charset=UTF-8" %>
<html>
  <head>
    <title>frmList</title>
    <style type="text/css">
      button {background-color:#c7c78f; color:#737357; border:1px solid #737357; height:19px}
    </style>
  </head>
  <script language="javascript" type="text/javascript">
    function SetOkButton(bool)
    {
      document.getElementById("btnFrmButtonOk").disabled = bool;
    }
  </script>
  <body style="margin:6px 6px 6px 6px; background-color:#e3e3c7; border-top:1px solid #d5d59d;">
    <div align="right" style="margin:0px">
      <button id="btnFrmButtonOk" onclick="top.returnValue=parent.frames['frmDetail'].GetReturnValues(); top.close();" disabled="true">确定</button>
      &nbsp;
      <button onclick="parent.close();">取消</button>
    </div>
  </body>
</html>
