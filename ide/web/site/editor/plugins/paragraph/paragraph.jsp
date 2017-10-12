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
      var oEditor		= window.parent.InnerDialogLoaded() ;
      var FCK			= oEditor.FCK ;
      var FCKLang		= oEditor.FCKLang ;
      var FCKConfig	= oEditor.FCKConfig ;

      var selectedText=FCK.EditorDocument.selection.createRange().htmlText; //选中的文本
      var oSpan = FCK.Selection.MoveToAncestorNode( 'SPAN' );
      var oSpan2= oSpan;
      if(oSpan){
        while (!(oSpan.style.lineHeight)&&oSpan.tagName=='SPAN'){
          oSpan=oSpan.parentElement;
        }
        if((oSpan.style.lineHeight)&&(!selectedText||(selectedText.length<=oSpan.outerHTML.length))){
          var lineHeight=oSpan.style.lineHeight;
        }
      }
      if(oSpan2){
        while (!(oSpan2.style.letterSpacing)&&oSpan2.tagName=='SPAN'){
          oSpan2=oSpan2.parentElement;
        }
        if((oSpan2.style.letterSpacing)&&(!selectedText||(selectedText.length<=oSpan2.outerHTML.length))){
          var letterSpacing=oSpan2.style.letterSpacing;
        }
      }

      window.onload = function()
      {
        if(lineHeight){
          var rowSpacing=document.getElementById('rowSpacing');
          var optionValue;
          switch(lineHeight){
            case "100%":{optionValue='1';break;}
            case "150%":{optionValue='1.5';break;}
            case "200%":{optionValue='2';break;}
            default:{optionValue='more';document.getElementById('rowSpacingValue').value=lineHeight;}
          }
          for(var i=0;i<rowSpacing.options.length;i++){
            if(rowSpacing.options[i].value==optionValue){
              var height=rowSpacing.options[i].selected=true;
              break;
            }
          }
        }
        if(letterSpacing){
          var charSpacing=document.getElementById('charSpacing');
          if(letterSpacing!='normal'){
            charSpacing.options[1].selected=true;document.getElementById('charSpacingValue').value=letterSpacing;
          }
        }
        window.parent.SetOkButton( true ) ;
      }

      // Fired when the user press the OK button
      function Ok()
      {
        oEditor.FCKUndo.SaveUndoStep() ;
        var rowSpacing=document.getElementById('rowSpacing');
        for(var i=0;i<rowSpacing.options.length;i++){
          if(rowSpacing.options[i].selected){
            var height=rowSpacing.options[i].value;
            break;
          }
        }
        var lineHeight;
        switch(height){
          case "auto":{lineHeight=null;break;}
          case "1":{lineHeight='100%';break;}
          case "1.5":{lineHeight='150%';break;}
          case "2":{lineHeight='200%';break;}
          case "more":{lineHeight=document.getElementById('rowSpacingValue').value;break;}
          default:lineHeight='100%';
        }
        var charSpacing=document.getElementById('charSpacingValue').value;

        var style;

        style = FCK.Styles.GetStyle( '_FCK_LineHeight');
        if(lineHeight){
          style.SetVariable('lineHeight', lineHeight);
          FCK.Styles.ApplyStyle( style ) ;
        }else{
          FCK.Styles.RemoveStyle( style) ;
        }

        style = FCK.Styles.GetStyle( '_FCK_LetterSpacing');
        if(charSpacing){
          style.SetVariable('letterSpacing', charSpacing);
          FCK.Styles.ApplyStyle( style ) ;
        }else{
          FCK.Styles.RemoveStyle( style) ;
        }
        oEditor.FCKUndo.SaveUndoStep();
        FCK.Focus() ;
        FCK.Events.FireEvent( 'OnSelectionChange' ) ;
        return true ;
      }

      var   flag1=0;
      var   flag2=0;
      var id;
      var space;
      var postifx;

      function add()
      {
        o = document.getElementById(id);
        var oldValue=parseFloat(o.value);
        if(isNaN(oldValue)){
          if(id=='rowSpacingValue'){
            o.value=0.25+postfix;
          }else{
            o.value=0+postfix;
          }
        }else{
          o.value = oldValue+parseFloat(space)+postfix;
        }
        return;
      }
      function minus()
      {
        o = document.getElementById(id);
        var oldValue=parseFloat(o.value);
        if(isNaN(oldValue)){
          o.value=0;
        }else{
          o.value = oldValue-parseFloat(space)+postfix;
        }
        return;
      }
      function   NumberInc()
      {
        if(flag1==1   &&   flag2==1)
        {alert("Error!");}
        else
        {
          if(flag1==1)
          {
            add();
            setTimeout("NumberInc()",150);
          }
          if(flag2==1)
          {
            minus();
            setTimeout("NumberInc()",150);
          }
        }
      }

      function   md(btn,_id,_space,_postfix)
      {
        id=_id;
        space=_space;
        postfix=_postfix;
        if(btn.value=="+")   flag1=1;
        if(btn.value=="-")   flag2=1;
        if(_id=='rowSpacingValue'){
          var sel=document.getElementById('rowSpacing');
          for(var i=0;i<sel.options.length;i++)
          {
            if(sel.options[i].value=='more')
            {
              sel.options[i].selected=true;
              break;
            }
          }
        }
        NumberInc();
      }
      function   md2(btn,_id,_space,_postfix)
      {
        id=_id;
        space=_space;
        postfix=_postfix;
        if(btn.value=="+")   flag1=1;
        if(btn.value=="-")   flag2=1;
        if(_id=='charSpacingValue'){
          var sel=document.getElementById('charSpacing');
          for(var i=0;i<sel.options.length;i++)
          {
            if(sel.options[i].value=='other')
            {
              sel.options[i].selected=true;
              break;
            }
          }
        }
        NumberInc();
      }
      function   mo(btn)
      {
        if(btn.value=="+")   flag1=0;
        if(btn.value=="-")   flag2=0;
      }
      function selectRowSpacing(sel){
        for(var i=0;i<sel.options.length;i++){
          if(sel.options[i].selected){
            if(sel.options[i].value=='more')
            {
              document.getElementById('rowSpacingValue').value='3';
            }else{
              document.getElementById('rowSpacingValue').value='';
            }
            return;
          }
        }
      }
      function selectCharSpacing(sel){
        for(var i=0;i<sel.options.length;i++){
          if(sel.options[i].selected){
            if(sel.options[i].value=='other')
            {
              document.getElementById('charSpacingValue').value='1';
            }else{
              document.getElementById('charSpacingValue').value='';
            }
            return;
          }
        }
      }
      </script>
      </head>
      <body style="overflow: hidden">
        <table id="otable" cellspacing="0" cellpadding="0" width="100%" border="0" style="height: 100%">
          <tr>
            <td valign="top">
              <fieldset>
                <legend>间距</legend>
                <table cellspacing="1" cellpadding="1" width="100%" border="0">
                  <tr>
                    <td valign="top">行&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;距：
                      <select id="rowSpacing" name="rowSpacing" onchange="selectRowSpacing(this)">
                        <option value="auto"  selected="">自动</option>
                        <option  value="1">单倍行距</option>
                        <option  value="1.5">1.5倍行距</option>
                        <option  value="2">2倍行距</option>
                        <option  value="more">多倍行距</option>
                      </select>
                    </td>
                    <td valign="top">设置值：
                      <input id="rowSpacingValue" type="text" size="5" value="" name="rowSpacingValue"/>
                      <input type="button" value="+" onMouseDown="md(this,'rowSpacingValue','0.25','')"   onMouseOut="mo(this)"   onMouseUp="mo(this)"/>
                      <input type="button" value="-" onMouseDown="md(this,'rowSpacingValue','0.25','')"   onMouseOut="mo(this)"   onMouseUp="mo(this)"/>
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">字符间距：
                      <select id="charSpacing" name="charSpacing" onchange="selectCharSpacing(this)">
                        <option value="auto" selected="selected">自动</option>
                        <option  value="other">设置值&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
                      </select>
                    </td>
                    <td valign="top">设置值：
                      <input id="charSpacingValue" type="text" size="5" value="" name="charSpacingValue"/>
                      <input type="button" value="+" onMouseDown="md2(this,'charSpacingValue','1','')"   onMouseOut="mo(this)"   onMouseUp="mo(this)"/>
                      <input type="button" value="-" onMouseDown="md2(this,'charSpacingValue','1','')"   onMouseOut="mo(this)"   onMouseUp="mo(this)"/>
                    </td>
                  </tr>
                </table>
              </fieldset>
            </td>
          </tr>
        </table>
      </body>
    </html>
