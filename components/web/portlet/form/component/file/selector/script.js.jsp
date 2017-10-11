<%@ page contentType="text/html; charset=UTF-8" %><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
function openSelectorFileSelector($oEcho)
{
  var url = "/ide/cms/resource/fileSelector.jsp";
  var sOptions = "dialogHeight=130px,dialogWidth=150px,center=yes,,resizable=no,status=no";
  var args = {dialogTitle:"文件资源", fileType:"file"};
  var ret = window.showModalDialog(url, args, sOptions);
  if (ret)
    $oEcho.value = ret;
}
