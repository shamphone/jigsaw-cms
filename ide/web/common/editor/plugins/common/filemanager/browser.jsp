<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
    <title>资源浏览器</title>
    <link href="browser.css" type="text/css" rel="stylesheet">
    <script language="javascript" type="text/javascript">
      var _resourceType = window.dialogArguments;

      function GetResourceType()
      {
        return _resourceType;
      }
    </script>
  </head>
  <frameset rows="36,*,36" frameBorder="0.5px" frameSpacing="0px">
    <frame id="frmPosition" name="frmPosition" src="frmPosition.jsp" scrolling="no" noresize/>
    <frameset cols="160,*">
      <frame id="frmList" name="frmList" src="frmList.jsp" scrolling="no"  noresize/>
      <frame id="frmDetail" name="frmDetail" src="javascript:void(0)" scrolling="auto"  noresize/>
    </frameset>
    <frame id="frmButton" name="frmButton" src="frmButton.jsp" scrolling="no" noresize/>
  </frameset>
</html>
