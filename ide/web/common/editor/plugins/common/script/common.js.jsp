<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
function BrowseResources($sType)
{
    // oEditor must be defined.
    var url="<html:rewrite module="/common" page="/editor/plugins/common/filemanager/browser.jsp"/>";
    var width=FCKConfig.ImageBrowserWindowWidth;
    var height= FCKConfig.ImageBrowserWindowHeight;
    var iLeft = ( oEditor.FCKConfig.ScreenWidth  - width ) / 2 ;
    var iTop  = ( oEditor.FCKConfig.ScreenHeight - height ) / 2 ;
    var sOptions = "toolbar=no,status=no,resizable=no,dependent=yes,scrollbars=yes,width=" + width + ",height=" + height + ",left=" + iLeft + ",top=" + iTop ;
    return window.showModalDialog(url, $sType, sOptions);
}

function IsInteger($iKeyCode)
{
    //如果输入的字符是在0-9之间，或者是backspace、DEL键
    return (($iKeyCode>47 && $iKeyCode<58) || $iKeyCode==8);
}

function SetSize($oTarget, $sValue, $fProportion)
{
    var value = NaN;
    if ($sValue.length > 0)
        value = Number($sValue);
    if (!isNaN(value))
        $oTarget.value = Math.round(value * $fProportion);
    else
        $oTarget.value = "";
}



