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
        <title>重复器片断编辑器</title>
        <meta name="robots" content="noindex, nofollow" />
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="Cache-Control" content="public" />
        <script Language='Javascript' src='/ide/common/script/common.js' type="text/javascript"></script>
        <script Language='Javascript' src='/ide/common/script/ajax.js' type="text/javascript"></script>
        <script type="text/javascript">
          var editingFilePath='<%=request.getParameter("path")%>';
          var currentPath='<%=request.getParameter("path")%>';
          var portalStyleURL="/portal/style.css.jsp";
          portalStyleLink="<link id=\"portlet.style\" href=\""+portalStyleURL+"\" type=\"text/css\" rel=\"stylesheet\">";
          var imageFolder="/<bean:write name='channel' property='siteTemplate.name' ignore='true'/>/images";
          var modulePath = "/ide/site/";
          var contextPath="/ide";
          var pageFile='<%=request.getParameter("path")%>';
          var previewFile=pageFile+".bak";
          var workingFile=pageFile;
          var portalStyleLink="<link id=\"portlet.style\" href=\""+portalStyleURL+"\" type=\"text/css\" rel=\"stylesheet\">";
        </script>
        <script type="text/javascript">
        var styleSheets = window.parent.dialogArguments.styleSheets;
        var oTemplate = window.parent.dialogArguments.EditorWindow.oTemplate;
        var oChannel =  oTemplate.getChannel(editingFilePath.substring(oTemplate.name.length+1));
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
       LoadScript("/ide/common/editor/"+ 'js/fckeditorcode_' + sSuffix + '.js' ) ;
        //expand the javascripts
/**
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/fckconstants.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/fckjscoreextensions.js' ) ;

if ( sSuffix == 'ie' )
	LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fckiecleanup.js' ) ;

LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fckbrowserinfo.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fckurlparams.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fckevents.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fckdataprocessor.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fck.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fck_' + sSuffix + '.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fckconfig.js' ) ;

LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fckdebug.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fckdomtools.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fcktools.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fcktools_' + sSuffix + '.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/fckeditorapi.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fckimagepreloader.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fckregexlib.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fcklistslib.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fcklanguagemanager.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fckxhtmlentities.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fckxhtml.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fckxhtml_' + sSuffix + '.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fckcodeformatter.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fckundo.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fckeditingarea.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fckkeystrokehandler.js' ) ;

LoadScript( '<html:rewrite page="/editor/" module="/common"/>dtd/fck_xhtml10transitional.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fckstyle.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fckstyles.js' ) ;

LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fcklisthandler.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fckelementpath.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fckdomrange.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fckdocumentfragment_' + sSuffix + '.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fckw3crange.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fckdomrange_' + sSuffix + '.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fckdomrangeiterator.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fckenterkey.js' ) ;

LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fckdocumentprocessor.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fckselection.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fckselection_' + sSuffix + '.js' ) ;

LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fcktablehandler.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fcktablehandler_' + sSuffix + '.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fckxml.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fckxml_' + sSuffix + '.js' ) ;

LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/commandclasses/fcknamedcommand.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/commandclasses/fckstylecommand.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/commandclasses/fck_othercommands.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/commandclasses/fckshowblocks.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/commandclasses/fckspellcheckcommand_' + sSuffix + '.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/commandclasses/fcktextcolorcommand.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/commandclasses/fckpasteplaintextcommand.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/commandclasses/fckpastewordcommand.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/commandclasses/fcktablecommand.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/commandclasses/fckfitwindow.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/commandclasses/fcklistcommands.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/commandclasses/fckjustifycommands.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/commandclasses/fckindentcommands.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/commandclasses/fckblockquotecommand.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/commandclasses/fckcorestylecommand.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/commandclasses/fckremoveformatcommand.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fckcommands.js' ) ;

LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fckpanel.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fckicon.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fcktoolbarbuttonui.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fcktoolbarbutton.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fckspecialcombo.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fcktoolbarspecialcombo.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fcktoolbarstylecombo.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fcktoolbarfontformatcombo.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fcktoolbarfontscombo.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fcktoolbarfontsizecombo.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fcktoolbarpanelbutton.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fcktoolbaritems.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fcktoolbar.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fcktoolbarbreak_' + sSuffix + '.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fcktoolbarset.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fckdialog.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fckdialog_' + sSuffix + '.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fckmenuitem.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fckmenublock.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fckmenublockpanel.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fckcontextmenu.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fck_contextmenu.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/classes/fckplugin.js' ) ;
LoadScript( '<html:rewrite page="/editor/" module="/common"/>_source/internals/fckplugins.js' ) ;
**/
        //加载配置文件
        LoadScript( 'xrepeaterEditor.js') ;

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
        LoadScript("/ide/common//editor/"+ 'lang/' + FCKLanguageManager.ActiveLanguage.Code + '.js' ) ;

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
        <script type="text/javascript" type="javascript">
/* svn 更新
        function saveSource(){
	    	var ret = new Object();
	    	ret.view = FCK.GetView( FCKConfig.FormatSource );
	    	alert("ret.view="+ret.view)
	    	ret.content = FCK.GetXHTML( FCKConfig.FormatSource );
	    	alert("ret.content="+ret.content)
	    	window.parent.returnValue = ret;
	    	alert("ret.content="+ret.content)
	    	window.parent.close();
*/
        function saveSource(){            
	  	  	var sHtml = FCK.GetXHTML( FCKConfig.FormatSource ) ;
	    	var req=getXMLHttpRequest();
	    	var url= modulePath+ '/channel/saveSource.do?clip=true';
	     	url=url+"&path="+ workingFile+"&timeStamp=" + new Date().getTime();
	    	req.open("POST",url,false);
	    	req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
	    	req.send(sHtml); 
	    	if(req.status==200){
	          	window.parent.returnValue=true;
	    	}
	    	else{
	          	ShowError("页面保存时出错，请修改源代码后再保存。", req.status);
	          	window.returnValue = false;
	    	}
	    	window.parent.close();
        }
        
        </script>
    </head>
    <body style="padding:0px">
    <table cellpadding="0" cellspacing="0" style="height:100%; table-layout:fixed; width:expression(document.body.clientWidth)">
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
      <tr>
      <td style="height:40px;" align="center" valign="middle" bgcolor="#efefde" style="padding-left:10px;">
      <button onclick="saveSource()" style="margin-right:10px">确定</button>
      <button onclick="window.parent.close()">取消</button>
      </td>
      </tr>
    </table>
    </body>
</html>
