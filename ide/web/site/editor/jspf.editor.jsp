<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"  %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
/* <p>模板页面编辑器</p>
*
* <p>Description: 龙驭网站内容管理系统核心引擎</p>
*
* <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
*
* <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
*
* @author 李雄锋
* @version 1.0
**/
%>
<html>
    <head>
        <title>页面片断编辑器</title>
        <meta name="robots" content="noindex, nofollow" />
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="Cache-Control" content="public" />
        <script Language='Javascript' src='<html:rewrite page="/script/ajax.js" module="/common"/>' type="text/javascript"></script>
        <script Language='Javascript' src='<html:rewrite page="/dialog.js" module="/site"/>' type="text/javascript"></script>
		<script type="text/javascript">
		var modulePath = "<html:rewrite module='/site' page='/'/>";
		var portalStyleURL="/portal/style.css";
        </script>
        <script type="text/javascript">
        var Editor = top.Editor;
        var oTemplate = Editor.template;        
        var oChannel = Editor.channelTree.getSelected().element;
        /**
        * 显示错误信息
        * @param html: HTML内容
        */
        function ShowError(error,msg)
        {
          var msg=new Object();
          msg.error=error;
          msg.errorMsg=msg;
          window.showModalDialog("fckerror.html?&timeStamp="+ new Date().getTime(),msg);
          return null;
        }
        // Instead of loading scripts and CSSs using inline tags, all scripts are
        // loaded by code. In this way we can guarantee the correct processing order,
        // otherwise external scripts and inline scripts could be executed in an
        // unwanted order (IE).
        function LoadScript( url )
        {
            document.write( '<script type="text/javascript" src="' + url + '"><\/script>' ) ;
        }
        function LoadCss( url )
        {
            document.write( '<link href="' + url + '" type="text/css" rel="stylesheet" />' ) ;
        }
        // Main editor scripts.
        var sSuffix = ( /*@cc_on!@*/false ) ? 'ie' : 'gecko' ;
        LoadScript("<html:rewrite page='/editor/' module='/common'/>"+ 'js/fckeditorcode_' + sSuffix + '.js' ) ;
        //expand the javascripts

        //加载配置文件
        LoadScript( 'jspf.editor.js') ;

        //expand end
        // Base configuration file.
        </script>
	<script type="text/javascript">
        if ( FCKBrowserInfo.IsIE )
        {
          // Remove IE mouse flickering.
          try
          {
            document.execCommand( 'BackgroundImageCache', false, true ) ;
          }
          catch (e)
          {
            // We have been reported about loading problems caused by the above
            // line. For safety, let's just ignore errors.
          }

          // Create the default cleanup object used by the editor.
          FCK.IECleanup = new FCKIECleanup( window ) ;
          FCK.IECleanup.AddItem( FCKTempBin, FCKTempBin.Reset ) ;
          FCK.IECleanup.AddItem( FCK, FCK_Cleanup ) ;
        }

        // The first function to be called on selection change must the the styles
        // change checker, because the result of its processing may be used by another
        // functions listening to the same event.
        FCK.Events.AttachEvent( 'OnSelectionChange', function() { FCKStyles.CheckSelectionChanges() ; } ) ;

        // The config hidden field is processed immediately, because
        // CustomConfigurationsPath may be set in the page.
        FCKConfig.ProcessHiddenField() ;

        // Load the custom configurations file (if defined).
        if ( FCKConfig.CustomConfigurationsPath.length > 0 )
        LoadScript( FCKConfig.CustomConfigurationsPath ) ;

        </script>
        <script type="text/javascript">

        // Load configurations defined at page level.
        FCKConfig_LoadPageConfig() ;

        FCKConfig_PreProcess() ;

        // Load the active skin CSS.
        LoadCss( FCKConfig.SkinPath + 'fck_editor.css' ) ;
        // Load the language file.
        FCKLanguageManager.Initialize() ;
        LoadScript("<html:rewrite page='/editor/' module='/common'/>"+ 'lang/' + FCKLanguageManager.ActiveLanguage.Code + '.js' ) ;

        </script>
        <script type="text/javascript">
        // Initialize the editing area context menu.
        FCK_ContextMenu_Init() ;
        FCKPlugins.Load() ;
        </script>
        <script type="text/javascript">
        // Set the editor interface direction.
        window.document.dir = FCKLang.Dir ;
        </script>
        <script type="text/javascript">
        window.onload = function()
        {
          InitializeAPI() ;
          if ( FCKBrowserInfo.IsIE )
          FCK_PreloadImages() ;
          else
          LoadToolbarSetup() ;
        }
        function LoadToolbarSetup()
        {
          FCKeditorAPI._FunctionQueue.Add( LoadToolbar ) ;
        }
        function LoadToolbar()
        {
          var oToolbarSet = FCK.ToolbarSet = FCKToolbarSet_Create() ;

          if ( oToolbarSet.IsLoaded )
          StartEditor() ;
          else
          {
            oToolbarSet.OnLoad = StartEditor ;
            oToolbarSet.Load( FCKURLParams['Toolbar'] || 'Default' ) ;
          }
        }
        function StartEditor()
        {
          // Remove the onload listener.
          FCK.ToolbarSet.OnLoad = null ;

          FCKeditorAPI._FunctionQueue.Remove( LoadToolbar ) ;

          FCK.Events.AttachEvent( 'OnStatusChange', WaitForActive ) ;

          // Start the editor.
          FCK.StartEditor() ;
        }
        function WaitForActive( editorInstance, newStatus )
        {
          if ( newStatus == FCK_STATUS_ACTIVE )
          {
            if ( FCKBrowserInfo.IsGecko )
            FCKTools.RunFunction( window.onresize ) ;

            _AttachFormSubmitToAPI() ;

            FCK.SetStatus( FCK_STATUS_COMPLETE ) ;

            // Call the special "FCKeditor_OnComplete" function that should be present in
            // the HTML page where the editor is located.
            if ( typeof( window.parent.FCKeditor_OnComplete ) == 'function' )
            window.parent.FCKeditor_OnComplete( FCK ) ;
          }
        }
        // Gecko browsers doens't calculate well that IFRAME size so we must
        // recalculate it every time the window size changes.
        if ( FCKBrowserInfo.IsGecko )
        {
          function Window_OnResize()
          {
            if ( FCKBrowserInfo.IsOpera )
            return ;

            var oCell = document.getElementById( 'xEditingArea' ) ;

            var eInnerElement = oCell.firstChild ;
            if ( eInnerElement )
            {
              eInnerElement.style.height = 0 ;
              eInnerElement.style.height = oCell.scrollHeight - 2 ;
            }
          }
          window.onresize = Window_OnResize ;
        }
        </script>
    </head>
    <body>
    <table width="100%" cellpadding="0" cellspacing="0" style="height: 100%; table-layout: fixed">
      <tr id="xToolbarRow" style="display: none">
        <td id="xToolbarSpace" style="overflow: hidden">
          <table width="100%" cellpadding="0" cellspacing="0">
            <tr id="xCollapsed" style="display: none">
              <td id="xExpandHandle" class="TB_Expand" colspan="3">
                <html:img styleClass="TB_ExpandImg" alt="" page="/editor/images/spacer.gif" module="/common" width="8" height="4" /></td>
            </tr>
            <tr id="xExpanded" style="display: none">
              <td id="xTBLeftBorder" class="TB_SideBorder" style="width: 1px; display: none;"></td>
              <td id="xCollapseHandle" style="display: none" class="TB_Collapse" valign="bottom">
                <html:img styleClass="TB_CollapseImg" alt="" page="/editor/images/spacer.gif" module="/common" width="8" height="4" /></td>
                <td id="xToolbar" class="TB_ToolbarSet"></td>
                <td class="TB_SideBorder" style="width: 1px"></td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td id="xEditingArea" valign="top" style="height: 100%"></td>
      </tr>
    </table>
    </body>
</html>
