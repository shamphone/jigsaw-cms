
    var oEditor = window.parent.InnerDialogLoaded() ;
    var FCKLang = oEditor.FCKLang ;
    var FCKDocument = oEditor.FCK.EditorDocument;

    var _oFm;
    var _oBtnOk;
    var _oWaitPrompt;
    var _oFontFamily;
    var _oFontSize;
    var _oText;
    var _oFontBold;
    var _oFontItalic;
    var _oEffect;

    var _sFontFamily = FCKLang["DlgArtDefaultFamily"];
    var _sFontSize = "36";
    var _bFontBold = false;
    var _bFontItalic = false;
    var _sEffect;
    var _sArtId;

    document.write("<title>" + FCKLang["DlgComInsert"] + "</title>");

    function _DoClick(obj_){
      document.all(_sArtId).className = "shapenormal";
      obj_.className = "shapeselected";
      _sArtId = obj_.id;
      _sEffect = obj_.value;
    }

    function _DoBold(obj_){
      if (obj_.className=="imagenormal"){
        obj_.className = "imageselected";
        _bFontBold = true;
      }else{
        obj_.className = "imagenormal";
        _bFontBold = false;
      }
      _ChangeTextStyle();
    }

    function _DoItalic(obj_){
      if (obj_.className=="imagenormal"){
        obj_.className = "imageselected";
        _bFontItalic = true;
      }else{
        obj_.className = "imagenormal";
        _bFontItalic = false;
      }
      _ChangeTextStyle();
    }

    function _DoFontFamily(str_){
      _sFontFamily = str_;
      _ChangeTextStyle();
    }

    function _DoFontSize(obj_){
      var str = obj_.value;
      if (str.substring(0,1)=="0"){
        str = str.substr(1);
        obj_.value = str;
      }
      if (str!=""){
        _sFontSize = str;
        _ChangeTextStyle();
      }
    }

    function _ChangeTextStyle(){
      _oText.style.fontFamily = _sFontFamily;
      _oText.style.fontSize = _sFontSize + "pt";
      _oText.style.fontWeight = _bFontBold===true ? "bold" : "normal";
      _oText.style.fontStyle = _bFontItalic===true ? "italic" : "normal";
    }

    function _DoCheckNum(event){
      var key = event.keyCode;
      if (event.shiftKey){
        if ((key>=35)&&(key<=40)){
          return true;
        }else{
          return false;
        }
      }
      if ((key==35)||(key==36)||(key==37)||(key==38)||(key==39)||(key==40)||(key==8)||(key==46)){
        return true;
      }
      if ((key<=57)&&(key>=48)){
        return true;
      }
      return false;
    }

    function _AdjustDialog(){
        var w = tabDialogSize.offsetWidth + 30;
        var h = tabDialogSize.offsetHeight + 90;
        window.dialogWidth = w + "px";
        window.dialogHeight = h + "px";
        window.dialogLeft = (screen.availWidth - w) / 2;
        window.dialogTop = (screen.availHeight - h) / 2;
    }

    function _InitDocument() {
        _oBtnOk = top.document.getElementById("btnOk");
        _oFm = document.getElementById("fm");
        _oFontFamily = document.getElementById("d_fontfamily");
        _oFontSize = document.getElementById("d_fontsize");
        _oText = document.getElementById("d_text");
        _oFontBold = document.getElementById("fontBold");
        _oFontItalic = document.getElementById("fontItalic");
        _oEffect = document.getElementById("effect");

        var effectQuantity = 30;
        var builder = [];
        builder.push('<table id="artList" border=0 cellpadding=0 cellspacing=0>');
        for (var i=0; i<effectQuantity; i++)
        {
            if (i%6==0)
                builder.push("<tr valign=middle align=center>");

            var s = i + 1;
            if (s<10)
              s = "0" + s;

            builder.push("<td><span style='width:66px; height:49px'><img src='image/" + (i + 1) + ".gif' style='width:60px; height:43px' id='shape_x0000_i00" + s + "' onmouseover='_DoMouseOver(this)' onmouseout='_DoMouseOut(this)' onclick='_DoClick(this)' class='shapenormal' value='" + i + "'/></span></td>");
            if (i%6==5){
              builder.push("</tr>");
            }
        }
        builder.push("</table>");
        document.getElementById("td_shape").innerHTML = builder.join("");

        builder = [];
        var fontFamilies = FCKLang["FontNames"].split(";");
        var df = document.createDocumentFragment();
        for (var i=0; i<fontFamilies.length; i++){
            var opt = document.createElement("option");
            opt.value = fontFamilies[i];
            opt.innerHTML = fontFamilies[i];
            df.appendChild(opt);
        }
        _oFontFamily.appendChild(df);
        _oFontFamily.value = _sFontFamily;
        _oFontSize.value = _sFontSize;
        _ChangeTextStyle();
        _oText.value = FCKLang["DlgArtDefaultText"];
        _oText.select();
        _oText.focus();

        _AdjustDialog();
    }

    function _Callback(path_)
    {
      if (!path_)
      {
        alert(FCKLang["DlgArtGenerateFailed"]);
        _oWaitPrompt.style.visibility = "hidden";
        _oBtnOk.disabled = false;
        return;
      }
      oEditor.FCK.InsertHtml("<img src='" + path_ + "'>");
      top.Cancel();
    }

    function Ok()
    {
        if (!_oText.value)
        {
            alert(FCKLang["DlgArtMsgNull"]);
            _oText.focus();
            _oText.select();
            return false;
        }
        if (!_sEffect)
        {
            alert(FCKLang["DlgArtMsgChooseEffect"]);
            return false;
        }
        _ShowWaitPrompt();
        document.getElementById("fontSize").value   = _sFontSize;
        document.getElementById("fontFamily").value = _sFontFamily;
        _oEffect.value     = _sEffect;
        _oFontBold.value   = _bFontBold;
        _oFontItalic.value = _bFontItalic;
        _oBtnOk.disabled = true;
        _oFm.submit();
        return false;
    }

    window.onload = function()
    {
        oEditor.FCKLanguageManager.TranslatePage(document) ;
        window.parent.SetOkButton(true) ;
        window.parent.SetAutoSize(true) ;
        _InitDocument();
    }

    function _ShowWaitPrompt()
    {
        if (!_oWaitPrompt)
        {
            _oWaitPrompt = document.createElement("marquee");
            _oWaitPrompt.className = "waitPrompt";
            _oWaitPrompt.behavior="alternate";
            _oWaitPrompt.scrollAmount="5"
            _oWaitPrompt.style.top = (document.body.clientHeight - 30) / 2;
            _oWaitPrompt.style.left = (document.body.clientWidth - 300) / 2;
            _oWaitPrompt.appendChild(document.createTextNode(FCKLang["DlgArtMsgWaitPrompt"]));
            document.body.appendChild(_oWaitPrompt);
        }
        else
        {
            _oWaitPrompt.style.visibility = "visible";
        }
    }

    function _DoMouseOver(obj_){
      obj_.className = "shapeselected";
    }

    function _DoMouseOut(obj_){
      if (obj_.id!=_sArtId){
        obj_.className = "shapenormal";
      }
    }
