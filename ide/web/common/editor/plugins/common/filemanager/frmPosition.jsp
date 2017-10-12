<%@ page contentType="text/html; charset=UTF-8" %>
<html>
  <head>
    <title>frmList</title>
    <style type="text/css">
      body, select {font-size:8pt}
    </style>
  </head>
  <script language="javascript" type="text/javascript">

    function SetPosition(sPath)
    {
      document.getElementById("spanPosition").innerHTML = "\\" + sPath;
    }

    function GetSortBasis()
    {
      return document.getElementById("selSortBasis").value;
    }

    function GetSortType()
    {
      return document.getElementById("selSortType").value;
    }

    function GetRenderPattern()
    {
      return document.getElementById("selRenderPattern").value;
    }

    function GetSelSortBasis()
    {
      return document.getElementById("selSortBasis");
    }

    function GetSelSortType()
    {
      return document.getElementById("selSortType");
    }

  </script>
  <body style="margin:3px 3px 3px 3px; background-color:#e3e3c7; border-bottom:1px solid #d5d59d;">
  <table style="width:100%; font-size:9pt;">
    <tr>
      <td><span id="spanPosition">\</span></td>
      <td style="width:300px; text-align:right;" nowrap>
        <span>排序：</span>
        <select id="selSortBasis" onchange="top.frames['frmDetail'].SetSortBasis(this.value); top.frames['frmDetail'].DoSort(); top.frames['frmDetail'].DoRender();">
          <option id="fileName" value="fileName"><span>名称</span></option>
          <option id="fileSize" value="fileSize"><span>大小</span></option>
          <option id="fileType" value="fileType"><span>类型</span></option>
          <option id="createTime" value="createTime"><span>创建时间</span></option>
        </select>
        <select id="selSortType" onchange="top.frames['frmDetail'].SetSortType(this.value); top.frames['frmDetail'].ReverseResources(); top.frames['frmDetail'].DoRender();">
          <option id="asc" value="asc"><span>升序</span></option>
          <option id="desc" value="desc"><span>降序</span></option>
        </select>
        <span>查看：</span>
        <select id="selRenderPattern" onchange="top.frames['frmDetail'].SetRenderPattern(this.value); top.frames['frmDetail'].DoRender();">
          <option id="thumbnail" value="thumbnail"><span>缩略图</span></option>
          <option id="detail" value="detail"><span>详细信息</span></option>
          <option id="icon" value="icon"><span>图标</span></option>
        </select>
      </td>
    </tr>
  </table>
  </body>
</html>
