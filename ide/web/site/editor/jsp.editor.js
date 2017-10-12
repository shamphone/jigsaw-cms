FCKConfig.BasePath= Globals.contextPath + '/common/editor/';
FCKConfig.EditorPath= Globals.contextPath + '/site/editor/';
FCKConfig.FullBasePath = document.location.protocol + '//' + document.location.host + FCKConfig.BasePath ;
FCKConfig.CustomConfigurationsPath = '' ;
FCKConfig.EditorAreaCSS = [FCKConfig.BasePath + 'css/fck_editorarea.css',portalStyleURL] ;

FCKConfig.FullPage = true;

FCKConfig.EditorAreaStyles = '' ;
FCKConfig.ToolbarComboPreviewCSS = '' ;

FCKConfig.DocType = '' ;

FCKConfig.BaseHref = toAbsoluteURL(oChannel.path);
//FCKConfig.BaseHref = toAbsoluteURL(document.location.protocol + '//' + document.location.host + "/" + oTemplate.name+"/");
//FCKConfig.BaseHref = '';

// The following option determines whether the "Show Blocks" feature is enabled or not at startup.
FCKConfig.StartupShowBlocks = false ;

FCKConfig.Debug = false ;
FCKConfig.AllowQueryStringDebug = true ;

FCKConfig.SkinPath = FCKConfig.BasePath + 'skins/default/' ;
FCKConfig.PreloadImages = [ FCKConfig.SkinPath + 'images/toolbar.start.gif', FCKConfig.SkinPath + 'images/toolbar.buttonarrow.gif' ] ;

FCKConfig.PluginsPath = FCKConfig.EditorPath + 'plugins/' ;
FCKConfig.Plugins.Add( 'portlet','en,zh-cn' ) ;
FCKConfig.Plugins.Add( 'channel','en,zh-cn') ;
FCKConfig.Plugins.Add( 'template','en,zh-cn') ;
FCKConfig.Plugins.Add( 'jsp') ;
FCKConfig.Plugins.Add( 'tagpath') ;
FCKConfig.Plugins.Add( 'exform','en,zh-cn') ;
FCKConfig.Plugins.Add( 'style','en,zh-cn') ;
//FCKConfig.Plugins.Add( 'jsmanager') ;
//FCKConfig.Plugins.Add('tablecommands','en,zh-cn', FCKConfig.BasePath+ 'plugins/' );
FCKConfig.Plugins.Add( 'fck_media','en,zh-cn', FCKConfig.BasePath+ 'plugins/' ) ;
// highlight_plugins
//FCKConfig.Plugins.Add('ugeshi', 'zh-cn', FCKConfig.BasePath+ 'plugins/');


FCKConfig.AutoGrowMax = 400 ;
FCKConfig.AutoDetectLanguage	= true ;
FCKConfig.DefaultLanguage	= 'en' ;
FCKConfig.ContentLangDirection	= 'ltr' ;

FCKConfig.ProcessHTMLEntities	= true ;
FCKConfig.IncludeLatinEntities	= true ;
FCKConfig.IncludeGreekEntities	= true ;

FCKConfig.ProcessNumericEntities = false ;

FCKConfig.AdditionalNumericEntities = ''  ;		// Single Quote: "'"

FCKConfig.FillEmptyBlocks	= false ;
FCKConfig.IgnoreEmptyParagraphValue = true ;

FCKConfig.FormatSource		= true ;
FCKConfig.FormatOutput		= true ;
FCKConfig.FormatIndentator	= '  ' ;

FCKConfig.StartupFocus	= true ;
FCKConfig.ForcePasteAsPlainText	= false ;
FCKConfig.AutoDetectPasteFromWord = true ;	// IE only.
FCKConfig.ShowDropDialog = true ;
FCKConfig.ForceSimpleAmpersand	= false ;
FCKConfig.TabSpaces		= 0 ;
FCKConfig.ShowBorders	= true ;
FCKConfig.SourcePopup	= false ;
FCKConfig.ToolbarStartExpanded	= true ;
FCKConfig.ToolbarCanCollapse	= true ;
FCKConfig.IgnoreEmptyParagraphValue = true ;
FCKConfig.PreserveSessionOnFileBrowser = false ;
FCKConfig.FloatingPanelsZIndex = 10000 ;
FCKConfig.HtmlEncodeOutput = false ;

FCKConfig.UseCustomPaste = true ;			//自定义配置   配置是否使用自定义的针对占位符的复制粘贴功能,默认为false

FCKConfig.TemplateReplaceAll = true ;
FCKConfig.TemplateReplaceCheckbox = true ;

FCKConfig.ToolbarLocation = 'In' ;
/**
 * 配置在页面上使用的按钮
 */
FCKConfig.ToolbarSets["Default"] = [
	['NewObject','SaveChannel','Preview','PublishChannel','RestoreChannel','DeleteChannel','CopyChannel','MoveChannel','Checkout','PageSize','ImportHTML','EditChannel','Authorization'],
     '/',
	['Source','Cut','Copy','Paste','PasteText','PasteWord','-','Print','SpellCheck'],
	['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],
	['Bold','Italic','Underline','StrikeThrough','-','Subscript','Superscript'],
	['OrderedList','UnorderedList','-','Outdent','Indent','Blockquote'],
	['JustifyLeft','JustifyCenter','JustifyRight','JustifyFull'],
	['Link','Unlink','Anchor'],
        '/',
	['Image','Flash','Media','Table','Rule','Smiley','SpecialChar','PageBreak'],
	['CSS','FontFormat','FontName','FontSize'],
	['TextColor','BGColor','-','ExForm','Portlet'],
        '/',
        ['TagPath']
] ;

FCKConfig.ToolbarSets["Basic"] = [
	['Bold','Italic','-','OrderedList','UnorderedList','-','Link','Unlink','-','About']
] ;

	/**
	 * 删除表格时出错
	 * 经测试 FCKeditor在EnterMode为br时删除表格时会有bug  
	 */
FCKConfig.EnterMode = 'br' ;			// p | div | br
FCKConfig.ShiftEnterMode = 'p' ;	// p | div | br

FCKConfig.Keystrokes = [
	[ CTRL + 65 /*A*/, true ],
	[ CTRL + 67 /*C*/, "Copy" ],
	[ CTRL + 70 /*F*/, true ],
	[ CTRL + 83 /*S*/, true ],
	[ CTRL + 88 /*X*/, true ],
	[ CTRL + 86 /*V*/, 'Paste' ],
	[ SHIFT + 45 /*INS*/, 'Paste' ],
	[ CTRL + 88 /*X*/, 'Cut' ],
	[ SHIFT + 46 /*DEL*/, 'Cut' ],
	[ CTRL + 90 /*Z*/, 'Undo' ],
	[ CTRL + 89 /*Y*/, 'Redo' ],
	[ CTRL + SHIFT + 90 /*Z*/, 'Redo' ],
	[ CTRL + 76 /*L*/, 'Link' ],
	[ CTRL + 66 /*B*/, 'Bold' ],
	[ CTRL + 73 /*I*/, 'Italic' ],
	[ CTRL + 85 /*U*/, 'Underline' ],
	[ CTRL + SHIFT + 83 /*S*/, 'Save' ],
	[ CTRL + ALT + 13 /*ENTER*/, 'FitWindow' ],
	[ CTRL + 9 /*TAB*/, 'Source' ]
] ;

FCKConfig.ContextMenu = ['Generic','Link','Anchor','Image','Flash','Media','EditPortlet','Select','Textarea','Checkbox','Radio','TextField','HiddenField','ImageButton','Button','BulletedList','NumberedList','Table','Form'] ;
FCKConfig.BrowserContextMenuOnCtrl = false ;

FCKConfig.EnableMoreFontColors = true ;
FCKConfig.FontColors = '000000,993300,333300,003300,003366,000080,333399,333333,800000,FF6600,808000,808080,008080,0000FF,666699,808080,FF0000,FF9900,99CC00,339966,33CCCC,3366FF,800080,999999,FF00FF,FFCC00,FFFF00,00FF00,00FFFF,00CCFF,993366,C0C0C0,FF99CC,FFCC99,FFFF99,CCFFCC,CCFFFF,99CCFF,CC99FF,FFFFFF' ;

FCKConfig.FontFormats	= 'p;h1;h2;h3;h4;h5;h6;pre;address;div' ;
FCKConfig.FontNames		= 'Arial;Comic Sans MS;Courier New;Tahoma;Times New Roman;Verdana' ;
FCKConfig.FontSizes		= '42pt/初号;36pt/小初;26pt/一号;24pt/小一;22pt/二号;18pt/小二;16pt/三号;15pt/小三;14pt/四号;12pt/小四;10pt/五号;9pt/小五;7pt/六号;6pt/小六;5pt/七号;5pt/八号;8px/8;9px/9;10px/10;11px/11;12px/12;14px/14;16px/16;18px/18;20px/20;22px/22;24px/24;26px/26;28px/28;36px/36;48px/48;72px/72';

FCKConfig.StylesXmlPath		= FCKConfig.BasePath + 'fckstyles.xml' ;
FCKConfig.TemplatesXmlPath	= FCKConfig.BasePath + 'fcktemplates.xml' ;

FCKConfig.SpellChecker			= 'ieSpell' ;	// 'ieSpell' | 'SpellerPages'
FCKConfig.IeSpellDownloadUrl	= 'http://www.iespell.com/download.php' ;
FCKConfig.SpellerPagesServerScript = 'server-scripts/spellchecker.php' ;	// Available extension: .php .cfm .pl
FCKConfig.FirefoxSpellChecker	= false ;

FCKConfig.MaxUndoLevels = 15 ;

FCKConfig.DisableObjectResizing = false ;
FCKConfig.DisableFFTableHandles = true ;

FCKConfig.LinkDlgHideTarget		= false ;
FCKConfig.LinkDlgHideAdvanced	= false ;

FCKConfig.ImageDlgHideLink		= false ;
FCKConfig.ImageDlgHideAdvanced	= false ;

FCKConfig.FlashDlgHideAdvanced	= false ;

FCKConfig.ProtectedTags = '' ;

// This will be applied to the body element of the editor
FCKConfig.BodyId = '' ;
FCKConfig.BodyClass = '' ;

FCKConfig.DefaultStyleLabel = '' ;
FCKConfig.DefaultFontFormatLabel = '' ;
FCKConfig.DefaultFontLabel = '' ;
FCKConfig.DefaultFontSizeLabel = '' ;

FCKConfig.DefaultLinkTarget = '' ;

// The option switches between trying to keep the html structure or do the changes so the content looks like it was in Word
FCKConfig.CleanWordKeepsStructure = false ;

// Only inline elements are valid.
FCKConfig.RemoveFormatTags = 'b,big,code,del,dfn,em,font,i,ins,kbd,q,samp,small,span,strike,strong,sub,sup,tt,u,var' ;

FCKConfig.CustomStyles =
{
	'Red Title'	: { Element : 'h3', Styles : { 'color' : 'Red' } }
};

// Do not add, rename or remove styles here. Only apply definition changes.
FCKConfig.CoreStyles =
{
	// Basic Inline Styles.
	'Bold'			: { Element : 'b', Overrides : 'strong' },
	'Italic'		: { Element : 'i', Overrides : 'em' },
	'Underline'		: { Element : 'u' },
	'StrikeThrough'	: { Element : 'strike' },
	'Subscript'		: { Element : 'sub' },
	'Superscript'	: { Element : 'sup' },

	// Basic Block Styles (Font Format Combo).
	'p'				: { Element : 'p' },
	'div'			: { Element : 'div' },
	'pre'			: { Element : 'pre' },
	'address'		: { Element : 'address' },
	'h1'			: { Element : 'h1' },
	'h2'			: { Element : 'h2' },
	'h3'			: { Element : 'h3' },
	'h4'			: { Element : 'h4' },
	'h5'			: { Element : 'h5' },
	'h6'			: { Element : 'h6' },

	// Other formatting features.
	'FontFace' :
	{
		Element		: 'span',
		Styles		: { 'font-family' : '#("Font")' },
		Overrides	: [ { Element : 'font', Attributes : { 'face' : null } } ]
	},

	'Size' :
	{
		Element		: 'span',
		Styles		: { 'font-size' : '#("Size","fontSize")' },
		Overrides	: [ { Element : 'font', Attributes : { 'size' : null } } ]
	},

	'Color' :
	{
		Element		: 'span',
		Styles		: { 'color' : '#("Color","color")' },
		Overrides	: [ { Element : 'font', Attributes : { 'color' : null } } ]
	},

	'BackColor'		: { Element : 'span', Styles : { 'background-color' : '#("Color","color")' } }
};

// The distance of an indentation step.
FCKConfig.IndentLength = 40 ;
FCKConfig.IndentUnit = 'px' ;

// Alternatively, FCKeditor allows the use of CSS classes for block indentation.
// This overrides the IndentLength/IndentUnit settings.
FCKConfig.IndentClasses = [] ;

// [ Left, Center, Right, Justified ]
FCKConfig.JustifyClasses = [] ;

// The following value defines which File Browser connector and Quick Upload
// "uploader" to use. It is valid for the default implementaion and it is here
// just to make this configuration file cleaner.
// It is not possible to change this value using an external file or even
// inline when creating the editor instance. In that cases you must set the
// values of LinkBrowserURL, ImageBrowserURL and so on.
// Custom implementations should just ignore it.
var _FileBrowserLanguage	= 'php' ;	// asp | aspx | cfm | lasso | perl | php | py
var _QuickUploadLanguage	= 'php' ;	// asp | aspx | cfm | lasso | perl | php | py

// Don't care about the following two lines. It just calculates the correct connector
// extension to use for the default File Browser (Perl uses "cgi").
var _FileBrowserExtension = _FileBrowserLanguage == 'perl' ? 'cgi' : _FileBrowserLanguage ;
var _QuickUploadExtension = _QuickUploadLanguage == 'perl' ? 'cgi' : _QuickUploadLanguage ;

FCKConfig.LinkBrowser = true ;
FCKConfig.LinkBrowserURL = Globals.contextPath+'/site/resource/index.jsp?template='+oTemplate.name;
FCKConfig.LinkBrowserWindowWidth	=  780  ;	 
FCKConfig.LinkBrowserWindowHeight	=  600  ;	

FCKConfig.ImageBrowser = true ;
FCKConfig.ImageBrowserURL = Globals.contextPath+'/site/resource/index.jsp?template='+oTemplate.name+"&types=jpg,gif,jpeg,bmp,png,emf,wmf,xmp";
FCKConfig.ImageBrowserWindowWidth  =  780  ;	
FCKConfig.ImageBrowserWindowHeight =  600   ;

FCKConfig.FlashBrowser = true ;
FCKConfig.FlashBrowserURL = Globals.contextPath+'/site/resource/index.jsp?template='+oTemplate.name+"&types=swf,fla";
FCKConfig.FlashBrowserWindowWidth  =  780  ;	
FCKConfig.FlashBrowserWindowHeight =  600  ;

FCKConfig.LinkUpload = false ;
FCKConfig.LinkBrowserURL = Globals.contextPath+'/site/resource/index.jsp?template='+oTemplate.name;
FCKConfig.LinkUploadAllowedExtensions	= ".(7z|aiff|asf|avi|bmp|csv|doc|fla|flv|gif|gz|gzip|jpeg|jpg|mid|mov|mp3|mp4|mpc|mpeg|mpg|ods|odt|pdf|png|ppt|pxd|qt|ram|rar|rm|rmi|rmvb|rtf|sdc|sitd|swf|sxc|sxw|tar|tgz|tif|tiff|txt|vsd|wav|wma|wmv|xls|xml|zip)$" ;			// empty for all
FCKConfig.LinkUploadDeniedExtensions	= "" ;	// empty for no one

FCKConfig.ImageUpload = false ;
FCKConfig.ImageUploadURL = FCKConfig.BasePath + 'filemanager/connectors/' + _QuickUploadLanguage + '/upload.' + _QuickUploadExtension + '?Type=Image' ;
FCKConfig.ImageUploadAllowedExtensions	= ".(jpg|gif|jpeg|png|bmp)$" ;		// empty for all
FCKConfig.ImageUploadDeniedExtensions	= "" ;							// empty for no one

FCKConfig.FlashUpload = false ;
FCKConfig.FlashUploadURL = FCKConfig.BasePath + 'filemanager/connectors/' + _QuickUploadLanguage + '/upload.' + _QuickUploadExtension + '?Type=Flash' ;
FCKConfig.FlashUploadAllowedExtensions	= ".(swf|flv)$" ;		// empty for all
FCKConfig.FlashUploadDeniedExtensions	= "" ;					// empty for no one

FCKConfig.SmileyPath	= FCKConfig.BasePath + 'images/smiley/msn/' ;
FCKConfig.SmileyImages	= ['regular_smile.gif','sad_smile.gif','wink_smile.gif','teeth_smile.gif','confused_smile.gif','tounge_smile.gif','embaressed_smile.gif','omg_smile.gif','whatchutalkingabout_smile.gif','angry_smile.gif','angel_smile.gif','shades_smile.gif','devil_smile.gif','cry_smile.gif','lightbulb.gif','thumbs_down.gif','thumbs_up.gif','heart.gif','broken_heart.gif','kiss.gif','envelope.gif'] ;
FCKConfig.SmileyColumns = 8 ;
FCKConfig.SmileyWindowWidth		= 320 ;
FCKConfig.SmileyWindowHeight	= 240 ;


// fck_media

FCKConfig.MediaBrowser = true ;
FCKConfig.MediaBrowserURL = Globals.contextPath+'/site/resource/index.jsp?template='+oTemplate.name+"&types=asf|avi|fla|flv|mid|mov|mp3|mp4|mpc|mpeg|mpg|ram|rm|rmi|rmvb|swf|wav|wma|wmv";
FCKConfig.MediaBrowserWindowWidth  = 780 ;		// 70% ;
FCKConfig.MediaBrowserWindowHeight = 600 ;	// 70% ;

FCKConfig.MediaUpload = false ;
FCKConfig.MediaUploadURL = FCKConfig.BasePath + 'filemanager/connectors/' + _QuickUploadLanguage + '/upload.' + _QuickUploadExtension + '?Type=Flash' ;
FCKConfig.MediaUploadAllowedExtensions	= ".(asf|avi|fla|flv|mid|mov|mp3|mp4|mpc|mpeg|mpg|ram|rm|rmi|rmvb|swf|wav|wma|wmv)$" ;		// empty for all
FCKConfig.MediaUploadDeniedExtensions	= "" ;					// empty for no one

// Media Embeds.
var FCKMediaProcessor = FCKDocumentProcessor.AppendNew() ;
FCKMediaProcessor.ProcessDocument = function( document )
{
	/*
	Sample code:
	This is some <embed src="/UserFiles/Media/Yellow_Runners.avi"></embed><strong>sample text</strong>. You are&nbsp;<a name="fred"></a> using <a href="http://www.fckeditor.net/">FCKeditor</a>.
	*/

	var bIsDirty = FCK.IsDirty() ;

	var aEmbeds = document.getElementsByTagName( 'EMBED' ) ;

	var oEmbed ;
	var i = aEmbeds.length - 1 ;
	while ( i >= 0 && ( oEmbed = aEmbeds[i--] ) )
	{
		// IE doesn't return the type attribute with oEmbed.type or oEmbed.getAttribute("type") 
		// But it turns out that after accessing it then it doesn't gets copied later
		var oType = oEmbed.attributes[ 'type' ] ;

		// Check the extension and the type. Now it should be enough with just the type
		// Opera doesn't return oEmbed.src so oEmbed.src.EndsWith will fail
		if ( oType && oType.nodeValue == 'application/x-mplayer2' )
		{
			var oCloned = oEmbed.cloneNode( true ) ;

			var oImg = FCKDocumentProcessor_CreateFakeImage( 'FCK__Media', oCloned ) ;
			oImg.setAttribute( '_fckmedia', 'true', 0 ) ;

			FCKMediaProcessor.RefreshView( oImg, oEmbed ) ;

			oEmbed.parentNode.insertBefore( oImg, oEmbed ) ;
			oEmbed.parentNode.removeChild( oEmbed ) ;
		}
	}

	// Fix the IsDirty state (#1406).
	if ( !bIsDirty )
		FCK.ResetIsDirty() ;
}

FCKMediaProcessor.RefreshView = function( placeHolderImage, originalEmbed )
{
	if ( originalEmbed.getAttribute( 'width' ) > 0 )
		placeHolderImage.style.width = FCKTools.ConvertHtmlSizeToStyle( originalEmbed.getAttribute( 'width' ) ) ;

	if ( originalEmbed.getAttribute( 'height' ) > 0 )
		placeHolderImage.style.height = FCKTools.ConvertHtmlSizeToStyle( originalEmbed.getAttribute( 'height' ) ) ;
}

function FCK_ContextMenu_GetListener( listenerName )
{
	switch ( listenerName )
	{
		case 'Generic' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				menu.AddItem( 'Cut'		, FCKLang.Cut	, 7, FCKCommands.GetCommand( 'Cut' ).GetState() == FCK_TRISTATE_DISABLED ) ;
				menu.AddItem( 'Copy'	, FCKLang.Copy	, 8, FCKCommands.GetCommand( 'Copy' ).GetState() == FCK_TRISTATE_DISABLED ) ;
				menu.AddItem( 'Paste'	, FCKLang.Paste	, 9, FCKCommands.GetCommand( 'Paste' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			}} ;

		case 'Table' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				var bIsTable	= ( tagName == 'TABLE' ) ;
				var bIsCell		= ( !bIsTable && FCKSelection.HasAncestorNode( 'TABLE' ) ) ;

				if ( bIsCell )
				{
					menu.AddSeparator() ;
					var oItem = menu.AddItem( 'Cell'	, FCKLang.CellCM ) ;
					oItem.AddItem( 'TableInsertCellBefore'	, FCKLang.InsertCellBefore, 69 ) ;
					oItem.AddItem( 'TableInsertCellAfter'	, FCKLang.InsertCellAfter, 58 ) ;
					oItem.AddItem( 'TableDeleteCells'	, FCKLang.DeleteCells, 59 ) ;
					if ( FCKBrowserInfo.IsGecko )
						oItem.AddItem( 'TableMergeCells'	, FCKLang.MergeCells, 60,
							FCKCommands.GetCommand( 'TableMergeCells' ).GetState() == FCK_TRISTATE_DISABLED ) ;
					else
					{
						oItem.AddItem( 'TableMergeRight'	, FCKLang.MergeRight, 60,
							FCKCommands.GetCommand( 'TableMergeRight' ).GetState() == FCK_TRISTATE_DISABLED ) ;
						oItem.AddItem( 'TableMergeDown'		, FCKLang.MergeDown, 60,
							FCKCommands.GetCommand( 'TableMergeDown' ).GetState() == FCK_TRISTATE_DISABLED ) ;
					}
					oItem.AddItem( 'TableHorizontalSplitCell'	, FCKLang.HorizontalSplitCell, 61,
						FCKCommands.GetCommand( 'TableHorizontalSplitCell' ).GetState() == FCK_TRISTATE_DISABLED ) ;
					oItem.AddItem( 'TableVerticalSplitCell'	, FCKLang.VerticalSplitCell, 61,
						FCKCommands.GetCommand( 'TableVerticalSplitCell' ).GetState() == FCK_TRISTATE_DISABLED ) ;
					oItem.AddSeparator() ;
					oItem.AddItem( 'TableCellProp'		, FCKLang.CellProperties, 57,
						FCKCommands.GetCommand( 'TableCellProp' ).GetState() == FCK_TRISTATE_DISABLED ) ;

					menu.AddSeparator() ;
					oItem = menu.AddItem( 'Row'			, FCKLang.RowCM ) ;
					oItem.AddItem( 'TableInsertRowBefore'		, FCKLang.InsertRowBefore, 70 ) ;
					oItem.AddItem( 'TableInsertRowAfter'		, FCKLang.InsertRowAfter, 62 ) ;
					oItem.AddItem( 'TableDeleteRows'	, FCKLang.DeleteRows, 63 ) ;

					menu.AddSeparator() ;
					oItem = menu.AddItem( 'Column'		, FCKLang.ColumnCM ) ;
					oItem.AddItem( 'TableInsertColumnBefore', FCKLang.InsertColumnBefore, 71 ) ;
					oItem.AddItem( 'TableInsertColumnAfter'	, FCKLang.InsertColumnAfter, 64 ) ;
					oItem.AddItem( 'TableDeleteColumns'	, FCKLang.DeleteColumns, 65 ) ;
				}

				if ( bIsTable || bIsCell )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'TableDelete'			, FCKLang.TableDelete ) ;
					menu.AddItem( 'TableProp'			, FCKLang.TableProperties, 39 ) ;
				}
			}} ;

		case 'Link' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				var bInsideLink = ( tagName == 'A' || FCKSelection.HasAncestorNode( 'A' ) ) ;

				if ( bInsideLink || FCK.GetNamedCommandState( 'Unlink' ) != FCK_TRISTATE_DISABLED )
				{
					// Go up to the anchor to test its properties
					var oLink = FCKSelection.MoveToAncestorNode( 'A' ) ;
					var bIsAnchor = ( oLink && oLink.name.length > 0 && oLink.href.length == 0 ) ;
					// If it isn't a link then don't add the Link context menu
					if ( bIsAnchor )
						return ;

					menu.AddSeparator() ;
					if ( bInsideLink )
						menu.AddItem( 'Link', FCKLang.EditLink		, 34 ) ;
					menu.AddItem( 'Unlink'	, FCKLang.RemoveLink	, 35 ) ;
				}
			}} ;

		case 'Image' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( tagName == 'IMG' && !tag.getAttribute( '_fckfakelement' ) )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'Image', FCKLang.ImageProperties, 37 ) ;
				}
			}} ;

		case 'Anchor' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				// Go up to the anchor to test its properties
				var oLink = FCKSelection.MoveToAncestorNode( 'A' ) ;
				var bIsAnchor = ( oLink && oLink.name.length > 0 ) ;

				if ( bIsAnchor || ( tagName == 'IMG' && tag.getAttribute( '_fckanchor' ) ) )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'Anchor', FCKLang.AnchorProp, 36 ) ;
					menu.AddItem( 'AnchorDelete', FCKLang.AnchorDelete ) ;
				}
			}} ;

		case 'Flash' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( tagName == 'IMG' && tag.getAttribute( '_fckflash' ) )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'Flash', FCKLang.FlashProperties, 38 ) ;
				}
			}} ;

		case 'Form' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( FCKSelection.HasAncestorNode('FORM') )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'Form', FCKLang.FormProp, 48 ) ;
				}
			}} ;

		case 'Checkbox' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( tagName == 'INPUT' && tag.type == 'checkbox' )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'Checkbox', FCKLang.CheckboxProp, 49 ) ;
				}
			}} ;

		case 'Radio' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( tagName == 'INPUT' && tag.type == 'radio' )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'Radio', FCKLang.RadioButtonProp, 50 ) ;
				}
			}} ;

		case 'TextField' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( tagName == 'INPUT' && ( tag.type == 'text' || tag.type == 'password' ) )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'TextField', FCKLang.TextFieldProp, 51 ) ;
				}
			}} ;

		case 'HiddenField' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( tagName == 'IMG' && tag.getAttribute( '_fckinputhidden' ) )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'HiddenField', FCKLang.HiddenFieldProp, 56 ) ;
				}
			}} ;

		case 'ImageButton' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( tagName == 'INPUT' && tag.type == 'image' )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'ImageButton', FCKLang.ImageButtonProp, 55 ) ;
				}
			}} ;

		case 'Button' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( tagName == 'INPUT' && ( tag.type == 'button' || tag.type == 'submit' || tag.type == 'reset' ) )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'Button', FCKLang.ButtonProp, 54 ) ;
				}
			}} ;

		case 'Select' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( tagName == 'SELECT' )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'Select', FCKLang.SelectionFieldProp, 53 ) ;
				}
			}} ;

		case 'Textarea' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( tagName == 'TEXTAREA' )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'Textarea', FCKLang.TextareaProp, 52 ) ;
				}
			}} ;

		case 'BulletedList' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( FCKSelection.HasAncestorNode('UL') )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'BulletedList', FCKLang.BulletedListProp, 27 ) ;
				}
			}} ;

		case 'NumberedList' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( FCKSelection.HasAncestorNode('OL') )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'NumberedList', FCKLang.NumberedListProp, 26 ) ;
				}
			}} ;
		// 自定义
		case 'Media' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( tagName == 'IMG' && tag.getAttribute( '_fckmedia' ) )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'Media', '多媒体属性', 'plugins/fck_media/media.gif' ) ;
				}
			}} ;
		// 自定义
		case 'EditPortlet' :
			return {
			AddItems : function( menu, tag, tagName )
			{
				if ( tagName == 'DIV' && tag.getAttribute( 'className' ) == 'portletWindow' )
				{
					menu.AddSeparator() ;
					menu.AddItem( 'EditPortlet', '占位符属性' ) ;
				}
			}} ;
	}
	return null ;
}
//修复删除table删除出错的bug
//覆盖DeleteRows实现
FCKTableHandler.DeleteTable = function( table ){
	// If no table has been passed as a parameter,
	// then get the table where the selection is placed in.
	if ( !table )
	{
		table = FCKSelection.GetSelectedElement() ;
		if ( !table || table.tagName != 'TABLE' )
			table = FCKSelection.MoveToAncestorNode( 'TABLE' ) ;
	}
	if ( !table ) return ;

	// Delete the table.
	FCKSelection.SelectNode( table ) ;
	FCKSelection.Collapse();

	// if the table is wrapped with a singleton <p> ( or something similar ), remove
	// the surrounding tag -- which likely won't show after deletion anyway
	if ( table.parentNode.childNodes.length == 1 &&table.parentNode.nodeName=="p")
		table.parentNode.parentNode.removeChild( table.parentNode );
	else
		table.parentNode.removeChild( table  ) ;
}