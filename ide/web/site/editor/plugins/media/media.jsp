<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>段落</title>
    <style type="text/css">
      body, a, table, div, span, td, th, input, select{font:9pt;font-family: Verdana, Arial, Helvetica, sans-serif;}
      body {padding:5px}
      .formTips{display:none}
    </style>
    <script type="text/javascript">

    var oEditor = window.parent.InnerDialogLoaded() ;
    var FCKConfig	= oEditor.FCKConfig ;
    // Gets the document DOM
    var oDOM = oEditor.FCK.EditorDocument ;

    // Gets the table if there is one selected.

    // Fired when the window loading process is finished. It sets the fields with the
    // actual values if a table is selected in the editor.
    window.onload = function()
    {
      // First of all, translate the dialog box texts
      oEditor.FCKLanguageManager.TranslatePage(document) ;
      window.parent.SetOkButton( true ) ;
      window.parent.SetAutoSize( true ) ;
    }

    // Fired when the user press the OK button
    function Ok()
    {
      //oEditor.FCKUndo.SaveUndoStep() ;
      file=document.getElementById("txtUrl");
      if(!file.value){
        alert("请选择媒体文件！");
        return false;
      }
      var autoPlay=document.getElementsByName('autoPlay');
      var embed = oEditor.FCK.InsertElement( 'embed' );
      var filename=file.value;
      filename=filename.replace(/\\/g,"\/");
      embed.src = filename;
      var width=document.getElementById('width').value;
      var height=document.getElementById('height').value;
      if(width)
      embed.width=width;
      if(height)
      embed.height=height;
      if(autoPlay[0].checked){
        embed.autostart="true";
      }else{
        embed.autostart="false";
      }
      var align=document.getElementById("align");
      for(var i=0;i<align.length;i++){
        if(align.options[i].selected){
          if(align.options[i].value!=""){
            embed.align=align.options[i].value;
          }
          break;
        }
      }
      window.parent.Cancel() ;
      return true;
    }
    function selectMedia()
    {

      // oEditor must be defined.
      var url=FCKConfig.ImageBrowserURL;
      var width=FCKConfig.ImageBrowserWindowWidth;
      var height= FCKConfig.ImageBrowserWindowHeight;
      var iLeft = ( oEditor.FCKConfig.ScreenWidth  - width ) / 2 ;
      var iTop  = ( oEditor.FCKConfig.ScreenHeight - height ) / 2 ;

      var sOptions = "toolbar=no,status=no,resizable=yes,dependent=yes,scrollbars=yes" ;
      sOptions += ",width=" + width ;
      sOptions += ",height=" + height ;
      sOptions += ",left=" + iLeft ;
      sOptions += ",top=" + iTop ;

      // The "PreserveSessionOnFileBrowser" because the above code could be
      // blocked by popup blockers.
      if ( oEditor.FCKConfig.PreserveSessionOnFileBrowser && oEditor.FCKBrowserInfo.IsIE )
      {
        // The following change has been made otherwise IE will open the file
        // browser on a different server session (on some cases):
        // http://support.microsoft.com/default.aspx?scid=kb;en-us;831678
        // by Simone Chiaretta.
        var oWindow = oEditor.window.open( url, 'FCKBrowseWindow', sOptions ) ;

        if ( oWindow )
        {
          // Detect Yahoo popup blocker.
          try
          {
            var sTest = oWindow.name ; // Yahoo returns "something", but we can't access it, so detect that and avoid strange errors for the user.
            oWindow.opener = window ;
          }
          catch(e)
          {
            alert( oEditor.FCKLang.BrowseServerBlocked ) ;
          }
        }
        else
        alert( oEditor.FCKLang.BrowseServerBlocked ) ;
      }
      else
      window.open( url, 'FCKBrowseWindow', sOptions ) ;
    }
    </script>
    </head>
    <body style="overflow: hidden">
      <table id="otable" cellspacing="0" cellpadding="0" width="100%" border="0" style="height: 100%">
        <tr>
          <td valign="top">
            <fieldset>
              <legend>媒体属性</legend>
              <table cellspacing="1" cellpadding="1" width="100%" border="0">
                <tr>
                  <td colspan="3"><input type="text" name="txtUrl" size="50"/>&nbsp;<input id="btnBrowse" onclick="selectMedia()" type="button" value="浏览服务器"/>
                  </tr>
                  <tr>
                    <td>宽度：<input type="text" size="5" name="height"/></td>
                    <td>高度：<input type="text" size="5" name="width"/></td>
                    <td>对齐：<select name="align">
                      <option value="">&nbsp;</option>
                      <option value="center">center</option>
                      <option value="left">left</option>
                      <option value="right">right</option>
                      <option value="top">top</option>
                      <option value="bottom">bottom</option>
                      <option value="baseline">baseline</option>
                      <option value="texttop">texttop</option>
                      <option value="middle">middle</option>
                      <option value="absmiddle">absmiddle</option>
                      <option value="absbottom">absbottom</option>
                    </select></td>
                  </tr>
                  <tr>
                  <td colspan="3">自动播放：是<input type="radio" name="autoPlay" value="1"/>&nbsp;否<input type="radio" name="autoPlay" checked="checked" value="0"/></td>
                  </tr>
                </table>
              </fieldset>
            </td>
          </tr>
        </table>
      </body>
    </html>
