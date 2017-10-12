
    function _ThumbnailRender()
    {
      var viewer = document.getElementById("viewer");
      var df = document.createDocumentFragment();
      for (var i=0; i<_resources.length; i++)
      {
        var path;
        var imgWidth = "36px";
        var imgHeight = "36px";
        switch (_resources[i].GetMimePrefix())
        {
          case "image" :
            path = _CONTEXT_PATH + _resources[i].GetPath();
            imgWidth = "100px";
            imgHeight = "100px";
            break;
          case "video" :
            path = _CONTEXT_PATH + "/common/editor/plugins/common/filemanager/image/video.gif";
            break;
          case "audio" :
            path = _CONTEXT_PATH + "/common/editor/plugins/common/filemanager/image/audio.gif";
            break;
        }
        if (!path)
        {
          switch (_resources[i].GetMime())
          {
            case "application/x-shockwave-flash" :
              path = _CONTEXT_PATH + "/common/editor/plugins/common/filemanager/image/flash.gif";
              break;
            default :
              path = _CONTEXT_PATH + "/common/editor/plugins/common/filemanager/image/file.gif";
          }
        }
        var oSpan = document.createElement("span");
        var oTable = document.createElement("table");
        var oRow1 = oTable.insertRow();
        var oRow2 = oTable.insertRow();
        var oCol1 = oRow1.insertCell();
        var oCol2 = oRow2.insertCell();
        var oImg = document.createElement("img");

        oSpan.style.width = "100px";
        oSpan.style.height = "120px";
        oTable.iIndex = i;
        oTable.style.wordBreak = "break-all";
        oTable.style.fontSize = "9pt";
        oImg.src = path;
        oImg.alt = _resources[i].GetPath();
        oImg.style.width = imgWidth;
        oImg.style.height = imgHeight;
        oCol1.style.width = "100px";
        oCol1.style.height = "100px";
        oCol1.align = "center";
        oCol1.vAlign = "middle";
        oCol1.appendChild(oImg);
        oCol2.appendChild(document.createTextNode(_resources[i].GetName()));

        oSpan.appendChild(oTable);
        df.appendChild(oSpan);
        _resources[i].SetOwner(oTable);
        oTable.attachEvent("onclick", function(){_resources[_GetElderElementByTagName(event.srcElement, "table").iIndex]._OnClick()});
      }
      viewer.innerHTML = "";
      viewer.appendChild(df);
    }

    function _DetailRender()
    {
      var viewer = document.getElementById("viewer");
      var oTable = document.createElement("table");

      oTable.cellSpacing = "0px";
      oTable.style.width = "100%";
      oTable.style.height = "100%";
      oTable.style.fontSize = "9pt";

      var oTHead = oTable.createTHead();
      var oTHeadRow = oTHead.insertRow();
      var oTHeadCol1 = oTHeadRow.insertCell();
      var oTHeadCol2 = oTHeadRow.insertCell();
      var oTHeadCol3 = oTHeadRow.insertCell();
      var oTHeadCol4 = oTHeadRow.insertCell();

      oTHeadCol1.appendChild(document.createTextNode("名称"));
      oTHeadCol1.attachEvent("onclick", function() {_DetailRenderSorter("fileName"); });

      oTHeadCol2.appendChild(document.createTextNode("大小"));
      oTHeadCol2.attachEvent("onclick", function() {_DetailRenderSorter("fileSize");});

      oTHeadCol3.appendChild(document.createTextNode("类型"));
      oTHeadCol3.attachEvent("onclick", function() {_DetailRenderSorter("fileType");});

      oTHeadCol4.appendChild(document.createTextNode("创建日期"));
      oTHeadCol4.attachEvent("onclick", function() {_DetailRenderSorter("createTime");});

      var oArrowImg = document.createElement("img");
      oArrowImg.src = _sSortType=="asc" ? _PATH_IMAGE_UP_ARROW : _PATH_IMAGE_DOWN_ARROW;
      oArrowImg.style.width = "11px";
      oArrowImg.style.height = "7px";
      oArrowImg.style.margin = "0px";
      switch (_sSortBasis)
      {
        case "fileName" :
            oTHeadCol1.appendChild(oArrowImg);
            break;
        case "fileSize" :
            oTHeadCol2.appendChild(oArrowImg);
            break;
        case "fileType" :
            oTHeadCol3.appendChild(oArrowImg);
            break;
        case "createTime" :
            oTHeadCol4.appendChild(oArrowImg);
            break;
        default :
            oTHeadCol1.appendChild(oArrowImg);
      }

      var oTBody = oTable.tBodies[0];
      for (var i=0; i<_resources.length; i++)
      {
        var oRow = oTBody.insertRow();
        var oCol1 = oRow.insertCell();
        var oCol2 = oRow.insertCell();
        var oCol3 = oRow.insertCell();
        var oCol4 = oRow.insertCell();
        var oImg = document.createElement("img");

        oImg.src = _GetImagePath(_resources[i].GetMime(), _resources[i].GetMimePrefix(), _resources[i].GetMimeSuffix());
        oImg.style.width = "16px";
        oImg.style.height = "16px";
        oImg.style.verticalAlign = "bottom";
        oCol1.appendChild(oImg);
        oCol1.appendChild(document.createTextNode(_resources[i].GetName()));
        oCol2.appendChild(document.createTextNode(_resources[i].GetLengthWithUnits()));
        oCol3.appendChild(document.createTextNode(_resources[i].GetMimeSuffix()));
        oCol4.appendChild(document.createTextNode(_resources[i].GetCreatedTime()));
        oCol2.style.textAlign = "right";
        oCol3.style.textAlign = "right";
        oCol4.style.textAlign = "right";

        oRow.iIndex = i;
        _resources[i].SetOwner(oRow);
        oRow.attachEvent("onclick", function(){_resources[_GetElderElementByTagName(event.srcElement, "tr").iIndex]._OnClick()});
      }
      viewer.innerHTML = "";
      viewer.appendChild(oTable);
    }

    function _IconRender()
    {
      var viewer = document.getElementById("viewer");
      var df = document.createDocumentFragment();
      for (var i=0; i<_resources.length; i++)
      {
        var imgWidth = "24px";
        var imgHeight = "24px";
        var oSpan = document.createElement("span");
        var oTable = document.createElement("table");
        var oRow1 = oTable.insertRow();
        var oRow2 = oTable.insertRow();
        var oCol1 = oRow1.insertCell();
        var oCol2 = oRow2.insertCell();
        var oImg = document.createElement("img");

        oSpan.style.width = "60px";
        oSpan.style.height = "60px";
        oTable.iIndex = i;
        oTable.style.wordBreak = "break-all";
        oTable.style.fontSize = "9pt";
        oImg.src = _GetImagePath(_resources[i].GetMime(), _resources[i].GetMimePrefix(), _resources[i].GetMimeSuffix());
        oImg.alt = _resources[i].GetName();
        oImg.style.width = "24px";
        oImg.style.height = "24px";
        oCol1.style.width = "60px";
        oCol1.style.height = "40px";
        oCol1.align = "center";
        oCol1.vAlign = "middle";
        oCol1.appendChild(oImg);
        oCol2.appendChild(document.createTextNode(_resources[i].GetName()));

        oSpan.appendChild(oTable);
        df.appendChild(oSpan);
        _resources[i].SetOwner(oTable);
        oTable.attachEvent("onclick", function(){_resources[_GetElderElementByTagName(event.srcElement, "table").iIndex]._OnClick()});
      }
      viewer.innerHTML = "";
      viewer.appendChild(df);
    }

    function _ListRender()
    {
    }

    function _GetImagePath(sMime_, sMimePrefix_, sMimeSuffix_)
    {
        var path;
        switch (sMimePrefix_)
        {
          case "image" :
            path = _CONTEXT_PATH + "/common/editor/plugins/common/filemanager/image/image.gif";
            break;
          case "video" :
            path = _CONTEXT_PATH + "/common/editor/plugins/common/filemanager/image/video.gif";
            break;
          case "audio" :
            path = _CONTEXT_PATH + "/common/editor/plugins/common/filemanager/image/audio.gif";
            break;
        }
        if (!path)
        {
          switch (sMime_)
          {
            case "application/x-shockwave-flash" :
              path = _CONTEXT_PATH + "/common/editor/plugins/common/filemanager/image/flash.gif";
              break;
            default :
              path = _CONTEXT_PATH + "/common/editor/plugins/common/filemanager/image/file.gif";
          }
        }
        return path;
    }

    function _GetElderElementByTagName(oElement_, sTagName_)
    {
        if(!oElement_)
            return null;
        if (oElement_.tagName.toLowerCase() == sTagName_)
            return oElement_;
        else
            return _GetElderElementByTagName(oElement_.parentElement, sTagName_);
    }

    function _DetailRenderSorter(sColName_)
    {
        var selSortBasis = top.frames['frmPosition'].GetSelSortBasis();
        var selSortType = top.frames['frmPosition'].GetSelSortType();

        if (_sSortBasis == sColName_)
        {
            selSortType.options.namedItem(_sSortType=="asc" ? "desc" : "asc").selected = true;
            selSortType.fireEvent("onchange");
        }
        else
        {
            selSortBasis.options.namedItem(sColName_).selected = true;
            selSortType.options.namedItem("asc").selected = true;
            _sSortType = "asc";
            selSortBasis.fireEvent("onchange");
        }
    }
