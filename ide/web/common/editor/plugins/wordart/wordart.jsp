<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<HTML>
  <HEAD>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/editor/plugins/wordart/script.js"/>"></script>
    <style type="text/css">
      body {overflow:hidden; }
      .waitPrompt {position:absolute; background-color:#3a6ea5; color:#ffffff; width:300px; padding:10px 25px; border:1px solid black; }
      .shapeselected {border:1px solid #0000ff; }
      .shapenormal {border-width:0px; }
      .imageselected {border:1px inset #cccccc; background-color:#ffffff; }
      .imagenormal {border:1px outset #ffffff; background-color:;}
    </style>
  </HEAD>
  <body>
  <form action="<html:rewrite module="/cms" page="/wordArt.do"/>" id="fm" method="post" enctype="multipart/form-data" target="hidden_frame">
  <table border=0 cellpadding=0 cellspacing=0 id=tabDialogSize>
    <tr>
      <td>
        <table border=0 cellpadding=0 cellspacing=0 align=center>
          <tr valign=top align=center>
            <td width=483px id=td_shape rowspan=2 valign="middle" align="center"></td>
            <td align="center">
              <table border=0 cellspacing=2 cellpadding=0 width="100%">
                <tr>
                  <td><span fckLang=DlgArtFontFamily></span>:</td>
                  <td><span fckLang=DlgArtFontSize></span>:</td>
                  <td colspan=2></td>
                </tr>
                <tr>
                  <td id=td_fontfamily><select id="d_fontfamily" onchange='_DoFontFamily(this.value)' style="width:100px;"></select></td>
                  <td><input type=text id=d_fontsize size=3 onkeyup="_DoFontSize(this)" onkeydown="return _DoCheckNum(event)"></td>
                  <td id=td_bold><img onclick="_DoBold(this)" border="0" width="20" height="20" src="image/bold.gif" id="s_bold" class="imagenormal" alt="" /></td>
                  <td id=td_italic><img onclick="_DoItalic(this)" border="0" width="20" height="20" src="image/italic.gif" id="s_italic" class="imagenormal" alt="" /></td>
                </tr>
                <tr>
                  <td colspan=4><span fckLang=DlgArtText></span>:</td>
                </tr>
                <tr>
                  <td colspan=4 height="100%"><textarea name="text" id=d_text style="width:100%; height:200px"></textarea></td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <input type="hidden" name="fontSize" id="fontSize" />
  <input type="hidden" name="fontFamily" id="fontFamily" />
  <input type="hidden" name="effect" id="effect" />
  <input type="hidden" name="fontBold" id="fontBold" />
  <input type="hidden" name="fontItalic" id="fontItalic" />
  <iframe name='hidden_frame' id="hidden_frame" style='display:none'></iframe>
  </form>
  </body>
</html>
