<%@ page contentType="text/css; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
body,td,th {font-size: 12px;}
body {  overflow-y:auto;
        overflow-x:hidden;
        padding:5px 5px 5px 5px;
        margin:0px 0px 0px 0px;
        border:3px solid buttonshadow;
        }
form{margin: 0px 0px 0px 0px;padding: 0px 0px 0px 0px;}
a {font-size: 12px;color: #000000;}
a:link{
    color:#000000;
	text-decoration:none;
}
a:visited {color: #000000;
	text-decoration:none;}
a:hover {color: #666666;
	text-decoration:none;}

.anbg{
   background-image:url(../images/anbg.gif);
}
.anbgx{
   background-image:url(../images/anbgx.gif);
}
.card {
   text-align:center;
   background-image:url(../images/an1.gif);
}
.card a:link{
   color:#000000;
   text-decoration:none;
}
.card a:visited{
   color:#000000;
   text-decoration:none;
}
.card1{
   background-image:url(../images/an2.gif);
   text-align:center;
}
.card1 a:link{
   color:#000000;
   text-decoration:none;
}
.card1 a:visited{
   color:#000000;
   text-decoration:none;
}
.serch{
   background-image:url(../images/sousuo.gif);
   width:64px;
   height:23px;
}
.indentpx a:link{
   padding-left:25px;
   color:#000000;
   text-decoration:none;
}
.indentpx a:visited{
   padding-left:25px;
   color:#000000;
   text-decoration:none;
}
.red{ color:#FF0000;}
.red a:link{
   color:#FF0000;
}
.red a:visited{
   color:#FF0000;
}
.zuobian{
   background-image:url(../images/aotu2.gif);
}
.topNavigation{
   behavior: url(<html:rewrite module="/common" page="/style/navigation.htc"/>)
   margin-left:15px;
   margin-top:8px;
   line-height:22px;
}
.topNavigation img{
   margin-left:10px;
   margin-right:10px;
}
.jianju{
  margin-bottom:0px;
}
.jianju2{
   margin-top:5px;
   line-height:22px;
}
.emphasize {
   color:#ff5500;font-weight:bold;
}
.errors {
   font-size:12px;font-weight:bold;color:#ff0000;
}
.formTips{
   color: gray;
}
.disabled{
   color: gray;
}
.tdindent td{
   padding-left:5px;
}
.hand{
   cursor:pointer;
}
/* 主框架的 表格*/
#mainTable{ height:expression(document.body.clientHeight-1);}
/* 列表表头 */
#headerTable{
        width:100%;
        border-collapse:collapse;
	border:1 solid #cccccc;
	line-height: 20px;
	text-indent:3px;
	margin-left:1px;}
#headerTable th{
  border:solid;
  border-width: 1px;
  background-color:#eeeeee;
  border-lightcolor:#ffffff;
  border-darkcolor:#cccccc;
  height:25px;
  }
/* 表单区 */
 #formBlock{
    height:expression(document.body.clientHeight-100);
    overflow-y: scroll;
    border-bottom:1px solid #c0c0c0;
 }

.contentBlock{
    height:expression(document.body.clientHeight-130);
    overflow-y: scroll;
    border-bottom:1px solid #c0c0c0;
}
.ellipses{  white-space:nowrap;
            text-overflow:ellipsis;
            overflow:hidden;
            width:expression(parentElement.width-2);
        }
#propertyTable select{width:120px;}
#contentEditor ul{list-style-type:none}
#contentEditor .fileComponent{width:500px;}
#contentEditor .textComponent{width:500px;}
#contentEditor th{width:200px;}

table.tableClass { behavior: url(<html:rewrite module="/common" page="/style/odd.htc"/>); }
.odd {
  background-color:#f9f9f9;
}
table.sheetClass textarea { behavior: url(<html:rewrite module="/common" page="/style/textarea.htc"/>); }
table.tableClass,table.sheetClass{
        background-color:#fff;
        border-collapse:collapse;
	border:1 solid #cccccc;
	line-height: 20px;
	text-indent:3px;
	margin-left:1px;
}
table.sheetClass{
  behavior: url(<html:rewrite module="/common" page="/style/th.htc"/>);
}
table.tableClass th,table.sheetClass th{
  border:solid;
  border-width: 1px;
  background-color:#eeeeee;
  border-lightcolor:#ffffff;
  border-darkcolor:#cccccc;
}
table.tableClass td,table.sheetClass td{
  border:solid;
  border-width: 1px;
  border-lightcolor:#ffffff;
  LINE-HEIGHT: 20px;
}

table.tableClass tfoot td,table.sheetClass tfoot td{
  border:0px;
  text-align:right;
}
.leftDiv{
  height:expression(document.documentElement.clientHeight-30);
  overflow-x: hidden;
  overflow-y: hidden;
}
.scrollBar{
   position:absolute;
   bottom:0px;
   height:24px;
   text-align:center;
   border-top:1px solid #72b4c9;
   width:100%;
   background-color:#9bcaf6;
}
.footClass{
   line-height:24px;
   border-top:1px solid #72b4c9;
   background-color:#9bcaf6;
   text-align:center;
}

.operation{
   margin-top:5px;
   text-align:center;
}
.operation button{
   margin-left:10px;
   margin-right:10px;
}
.operation img{
   margin-left:5px;
   margin-right:5px;
}
tr.treeVis{
 display:visible;
}
tr.treeHid{
 display:none;
}
img.treeSign{
 margin-right: 3px;
 cursor:pointer;
}
.treeSelected{
    background-color:#fff;
    font-weight:bold;
}
.treeSelected a:link{
    color:#000000;
    text-decoration:none;
}
.treeSelected a:visited{
    color:#000000;
    text-decoration:none;
}
.treeUnselected A{
        COLOR: #000000;
TEXT-DECORATION: none;
padding-top: 1px;
padding-left: 2px;
}

.fontStyle{font-size:14px;color:#0057b9;}
.imageContentTitle{font-size:14px;color:#0057b9;}

.border0603{ border:1px solid #999999;}
.bule0603{ font-size:14px; color:#0046ae; font-weight:bold;}
.black0603{font-size:14px;}
.commonbut0603{
 background-image:url(../images/button1.gif);
 width:80px;
 height:26px;
 padding-left:0px;
 border:0px;
 font-size:14px;
 color:#FF0000;
}
.cccommonbut0603{
 background-image:url(../images/button2.gif);
 color:#000000;
  width:80px;
 height:26px;
 padding-left:0px;
 border:0px;
 font-size:14px;
}
.cscommonbut0603{
 background-image:url(../images/button2.gif);
 color:#969696;
  width:80px;
 height:26px;
 padding-left:0px;
 border:0px;
 font-size:14px;
}
.buleborder0603{
 background-image:url(../images/openWindow.gif); width:500px; height:165px;
}
.sure0603{
 background-image:url(../images/button3.gif);
 color:#000000;
  width:50px;
 height:23px;
 padding-left:0px;
 border:0px;
 font-size:12px;
}
.pageClass{
text-align:right;
padding-right:15px;
}
