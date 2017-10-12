
    function _AdjustDialog(){
      var w = tabDialogSize.offsetWidth + 30;
      var h = tabDialogSize.offsetHeight + 90;
      /*
      if(config.IsSP2){
        h += 20;
      }*/
      window.dialogWidth = w + "px";
      window.dialogHeight = h + "px";
      window.dialogLeft = (screen.availWidth - w) / 2;
      window.dialogTop = (screen.availHeight - h) / 2;
    }

	function _SetBgImg($path) {
		oEditor.FCKUndo.SaveUndoStep();
		var vbgpicRep = GetE('bgpic_Repeat').value;
		var vbgpicAtt = GetE('bgpic_Attachment').value;
		var ele = FCK.Selection.GetSelectedElement();
		if (!ele)
			ele = FCK.Selection.GetParentElement();
		if (ele) {
			if ($path) {
				ele.style.backgroundImage = "url(" + $path + ")";
				ele.style.backgroundImageRepeate = vbgpicRep;
				ele.style.backgroundImageAttachment = vbgpicAtt;
			} else {
				ele.style.backgroundImage = "";
				ele.style.backgroundImageRepeate = "";
				ele.style.backgroundImageAttachment = "";
			}
		}
	}

    function radioClick($value){
      switch($value){
        case "server":
          GetE('txtUrl').disabled = "";
          GetE('btnServer').disabled = "";
          break;
        default :
          GetE('txtUrl').disabled = true;
          GetE('btnServer').disabled = true;
      }
    }
    
    function BrowseServer()
    {
    	OpenFileBrowser( FCKConfig.ImageBrowserURL, FCKConfig.ImageBrowserWindowWidth, FCKConfig.ImageBrowserWindowHeight ) ;
    }
    
    function _SetPreviewImg($src)
    {
      if ($src)
        _oPreviewer.innerHTML = "<img src='" + $src + "' onload='adjustment(this, _oPreviewer)' style='position:relative'/>";
    }

    function adjustment($img, $div)
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

    function _PropChange()
    {
        if (event.propertyName=='value') _SetPreviewImg(GetE("file").value);
    }
