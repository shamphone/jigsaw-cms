<%@page contentType="text/javascript; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
      var _sPath;
      var _oBtnOk;
      var _oNetwork;
      var _fileType;

      window.onload = function()
      {
        _oBtnOk = document.getElementById("btnOk");
        _oNetwork = document.getElementById("txtNetwork");
        var args = window.dialogArguments;
        if (args) {
          document.getElementById("dialogTitle").innerHTML = args.dialogTitle;
          _fileType = args.fileType;
        }
      }

      function Return()
      {
        window.returnValue = _sPath;
        this.close();
      }

      function BrowseResources($sType, $oEcho)
      {
          var url="<html:rewrite module="/common" page="/editor/plugins/common/filemanager/browser.jsp"/>";
          var sOptions = "toolbar=no,status=no,resizable=no,dependent=yes,scrollbars=yes,width=500px,height=500px";
          var sRet = window.showModalDialog(url, $sType, sOptions);
          if (sRet)
          {
            _sPath = sRet;
            $oEcho.value = _sPath;
          }
      }

      function Ok()
      {
        if (document.getElementsByName("fileSource")[0].checked)
        {
          // upload
          if (!document.getElementById("file").value)
          {
            alert("请选择文件！");
            return;
          }
          _oBtnOk.disabled = true;
          _ShowWaitPrompt(true);
          document.getElementById("fm").submit();
        }
        else
        {
          // network
          if(!_sPath)
          {
            alert("请选择文件！");
            return false;
          }
          Return();
        }
      }

      function _RadioClick(value)
      {
        switch (value)
        {
          case "upload" :
          fm.file.disabled = false;
          fm.btnBrowse.disabled = true;
          break;
          case "network" :
          fm.file.disabled = true;
          fm.btnBrowse.disabled = false;
        }
      }

      function _ShowWaitPrompt(bDisplay)
      {
      	if (bDisplay) {
	      	document.all("formPanel").style.display = "none";
	      	document.all("tipPanel").style.display = "";
      	} else {
      	  	document.all("tipPanel").style.display = "none";
	      	document.all("formPanel").style.display = "";
      	}
      }

      function callback(path)
      {
     
        if (!path)
        {
          alert("上传失败");
          _ShowWaitPrompt(false);
          _oBtnOk.disabled = false;
          return;
        }
        //_sPath = "/resources" + path;
        _sPath =path;
        Return();
      }
