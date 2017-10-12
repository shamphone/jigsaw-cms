<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@ page import="com.fulong.longcon.repository.NodeIterator" %>
<%@ page import="com.fulong.longcon.repository.Node" %>
<%@ page import="org.apache.commons.io.FileUtils" %>
<%@ page import="java.text.NumberFormat" %>
<html>
  <head>
    <title>frmDetail</title>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/editor/plugins/common/filemanager/script/ViewerItem.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/editor/plugins/common/filemanager/script/Render.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/editor/plugins/common/filemanager/script/Sorter.js"/>"></script>
  </head>
  <style type="text/css">
    thead tr{position:relative; top:expression(offsetParent.scrollTop); heigth:20px; }
    thead td{background-color:#cfcfcf; border:2 outset #f0f0f0; }
  </style>
  <script language="javascript" type="text/javascript">

    var _CONTEXT_PATH = "<%= request.getContextPath()%>";
    var _PATH_IMAGE_UP_ARROW = _CONTEXT_PATH + "/common/editor/plugins/common/filemanager/image/up.gif";
    var _PATH_IMAGE_DOWN_ARROW = _CONTEXT_PATH + "/common/editor/plugins/common/filemanager/image/down.gif";
    var _resources = [];
    var _sSortBasis;
    var _sSortType;
    var _sRenderPattern;
    var _sReturnValues;

    function _InitResources()
    {
      <%
      NodeIterator iter = (NodeIterator) request.getAttribute("resources");
      for (int i=0; iter.hasNext(); i++) {
        Node node = iter.nextNode();
        String variantName = "res" + i;
        String name = node.getName()!=null ? node.getName() : "";
        String length = node.getProperty("length").getString();
        String mime = node.getProperty("mime")!=null ? node.getProperty("mime").getString() : "";
        if(mime==null)  mime="";
        String mimePrefix = mime.indexOf("/")>-1 ? mime.split("/")[0] : mime;
        String mimeSuffix = mime.indexOf("/")>-1 ? mime.split("/")[1] : mime;
        String creatorTime = node.getProperty("createdTime")!=null ? node.getProperty("createdTime").getString() : "";
        String lengthWithUnits = null;
        double tmp = Double.parseDouble(length);
        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setMaximumFractionDigits(3);
//        formatter.setMinimumFractionDigits(0);
        if (tmp > FileUtils.ONE_GB) {
          Double r = new Double(tmp / FileUtils.ONE_GB);
          lengthWithUnits = formatter.format(r) + " GB";
        } else if (tmp > FileUtils.ONE_MB) {
          Double r = new Double(tmp / FileUtils.ONE_MB);
          lengthWithUnits = formatter.format(r) + " MB";
        } else if (tmp > FileUtils.ONE_KB) {
          Double r = new Double(tmp / FileUtils.ONE_KB);
          lengthWithUnits = formatter.format(r) + " KB";
        } else {
          lengthWithUnits = length + " B";
        }
        String path = "/resources" +  node.getPath();
        out.println("_resources.push(new ViewerItem('" + name + "', '" + length + "', '" + mime + "', '" + mimePrefix + "', '" + mimeSuffix + "', '" + creatorTime + "', '" + lengthWithUnits + "', '" + path + "'));");
      }
      %>
    }

    ViewerItem.prototype.OnClick = function()
    {
      _sReturnValues = this.GetPath();
      top.frames["frmButton"].SetOkButton(false);
    }

    function DoSort()
    {
      switch (_sSortBasis)
      {
        case "fileName" :
          _resources.sort(_GetSorter("_sName"));
          break;
        case "fileSize" :
          _resources.sort(_GetSorter("_sLength", "int"));
          break;
        case "fileType" :
          _resources.sort(_GetSorter("_sMimeSuffix"));
          break;
        case "createTime" :
          _resources.sort(_GetSorter("_sCreatorTime", "date"));
          break;
        default :
          _resources.sort(_GetSorter("_sName"));
      }
    }

    function DoRender()
    {
      switch (_sRenderPattern)
      {
        case "thumbnail":
          _ThumbnailRender();
          break;
        case "detail":
          _DetailRender();
          break;
        case "icon":
          _IconRender();
          break;
        case "list":
          _ListRender();
          break;
        default :
          _ThumbnailRender();
      }
    }

    function GetReturnValues() {
        var retValue = '';
        var splitValues = _sReturnValues.split('/');
        if(splitValues.length!=null&&splitValues.length>2){
            if(splitValues[1]=="resources"){
                for(var i=2;i<splitValues.length;i++){  //解决图片选择占位符中的路径错误，去掉选择控件返回值中的"/resources"
                	retValue = retValue + '/' + splitValues[i];
                }
            }else{
            	retValue = _sReturnValues;
            }
        }else{
        	retValue = _sReturnValues;
        }
      return retValue;}

    window.onload = function()
    {
      _sSortBasis = top.frames["frmPosition"].GetSortBasis();
      _sSortType = top.frames["frmPosition"].GetSortType();
      _sRenderPattern = top.frames["frmPosition"].GetRenderPattern();
      _InitResources();
      DoSort();
      DoRender();
    }
  </script>
  <body style="margin:0px 0px 0px 0px; ">
    <span id="viewer" style="width:105%; height:100%; overflow:scroll; "></span>
  </body>
</html>
