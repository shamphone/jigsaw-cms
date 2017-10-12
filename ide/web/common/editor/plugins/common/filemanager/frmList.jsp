<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<html>
  <head>
    <title>frmList</title>
    <script language="javascript" type="text/javascript">
      var selectItem;

      function UpdateFrmDetail()
      {
        var it = event.srcElement;
        if (selectItem != it)
        {
          top.frames["frmDetail"].location = "<html:rewrite module='/cms' page='/fileManagerFrmDetail.do'/>?resourceType=" + top.GetResourceType() + "&timeDiff=" + it.timeDiff;
          top.frames["frmPosition"].SetPosition(it.firstChild ? it.firstChild.nodeValue : "");
          if (selectItem)
          {
            selectItem.style.backgroundColor = "";
            selectItem.style.color = "";
          }
          it.style.backgroundColor = "#0000ff";
          it.style.color = "#ffffff";
          selectItem = it;
        }
      }

      window.onload = function ()
      {
        var list = document.getElementById("listSpan");
        var df = document.createDocumentFragment();
        var root = document.createElement("div");
        var rootImg = document.createElement("img");
        rootImg.style.verticalAlign = "bottom";
        root.appendChild(rootImg);
        switch (top.GetResourceType())
        {
          case "media" :
            rootImg.src = "image/media.gif";
            root.appendChild(document.createTextNode(" 媒体"));
            break;
          case "image" :
            rootImg.src = "image/image.gif";
            root.appendChild(document.createTextNode(" 图像"));
            break;
          case "flash" :
            rootImg.src = "image/flash.gif";
            root.appendChild(document.createTextNode(" Flash"));
            break;
          default :
            rootImg.src = "image/file.gif";
            root.appendChild(document.createTextNode(" 文件"));
        }
        list.appendChild(root);

        var date = new Date();
        var dayCount = 7;
        var millSecondsPerDay = 1000 * 60 * 60 * 24;

        for (var i=0; i<dayCount; i++)
        {
          var d = new Date(date - (dayCount - (i + 1)) * millSecondsPerDay);
          var it = document.createElement("div");
          var itImg = document.createElement("img");
          var itText = document.createElement("span");
          itImg.src = "image/dots.gif";
          itImg.style.verticalAlign = "bottom";
          itText.appendChild(document.createTextNode(" " + d.toLocaleDateString()));
          itText.attachEvent("onclick", UpdateFrmDetail);
          itText.timeDiff = -(dayCount - (i + 1));
          itText.style.cursor = "hand";
          it.appendChild(itImg);
          it.appendChild(itText);
          list.appendChild(it);
        }
        if (list.lastChild)
          list.lastChild.firstChild.src = "image/lastdots.gif";
     }

    </script>
  </head>
  <body style="font-size:10pt; margin:0px 0px 0px 0px; background-color:#f1f1e3; border-right:1px solid #c0c0c0;">
    <span id="listSpan"></span>
  </body>
</html>
