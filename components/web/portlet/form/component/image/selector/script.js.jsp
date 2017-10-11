<%@ page contentType="text/javascript; charset=UTF-8" %><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
function openImageSelector($oPreviewer, $oEcho)
{
  var url = "/ide/cms/resource/fileSelector.jsp";
   if(navigator.userAgent.indexOf("MSIE 6.0")>=0){
  	dialogHeight = 180;
  }else{
  	dialogHeight = 130;
  }
  var sOptions = "dialogHeight="+dialogHeight+"px,dialogWidth=150px,center=yes,,resizable=no,status=no";
  var args = {dialogTitle:"图片资源", fileType:"image"};
  var ret = window.showModalDialog(url, args, sOptions);
  if (ret)
  {
    
    $oEcho.value = ret;
    var tmp = "<img src=\"" + ret + "\" onload=\"adjustmentImageSelector(this, document.getElementById('" + $oPreviewer.id + "'))\" />";
    $oPreviewer.innerHTML = tmp;
  }
}

function adjustmentImageSelector($img, $div)
{
    var proportion = $div.style.pixelHeight / $div.style.pixelWidth;
    var realProportion = $img.offsetHeight / $img.offsetWidth;
    if ($img.clientWidth > $div.style.pixelWidth || $img.clientHeight > $div.style.pixelHeight) {
        if (realProportion >= 1)
            $img.height = $div.style.pixelHeight - 2;
        else
            $img.width = $div.style.pixelWidth - 2;
    }
    $img.style.top = $div.offsetTop + ($div.style.pixelHeight - $img.clientHeight)/2;
    $img.style.left = $div.offsetLeft + ($div.style.pixelWidth - $img.clientWidth)/2;
}
