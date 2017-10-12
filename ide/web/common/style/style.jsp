<%@ page contentType="text/css; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
body,td,th {font-size: 12px;}
body {overflow-y:auto;border-left:2px inset;margin:0px 2px 0px 0px}
form{margin: 0px 0px 0px 0px;padding: 0px 0px 0px 0px;}
a {font-size: 12px;color: #000000;}
a:visited {color: #000000;}
a:hover {color: #666666;}

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
    border-collapse:collapse;
	border:1 solid #cccccc;
	line-height: 20px;
	text-indent:3px;
	margin-left:1px;
}
table.sheetClass th{
  behavior: url(<html:rewrite module="/common" page="/style/th.htc"/>);
  text-align:right;
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
.commonbut{
 background-image:url(../images/qidong.gif);
 width:64px;
 height:23px;
 padding-top:1px;
 padding-left:18px;
 border:0px;
 letter-spacing:8px;
 cursor:pointer;
}
#delete.commonbut{
 background-image:url(../../images/delete.gif);
}
#delete2.commonbut{
 background-image:url(../images/delbut.gif);
 width:78px;
 letter-spacing:0px;
}
#tijiao.commonbut{
 background-image:url(../images/qidong.gif);
}
#stop.commonbut{
 background-image:url(../images/stop.gif);
}
#refuse.commonbut{
 width:75px;
 letter-spacing:3px;
 background-image:url(../images/refuse.gif);
}
#search.commonbut{
 background-image:url(../images/sousuo.gif);
}
#edit.commonbut{
 background-image:url(../images/edit.gif);
}
#setUser.commonbut{
 background-image:url(../images/user.gif);
 letter-spacing:0px;
 width:88px;
}
#edit2.commonbut{
 background-image:url(../images/editBut.gif);
 letter-spacing:0px;
 width:78px;
}
#new.commonbut{
 background-image:url(../images/xinjian.gif);
}
#back.commonbut{
 background-image:url(../images/fanhui.gif);
}
#move.commonbut{
 background-image:url(../images/move.gif);
 width:81px;
 letter-spacing:0px;
}
#userBut.commonbut{
 background-image:url(../images/grda.gif);
 width:106px;
 letter-spacing:0px;
}
#post.commonbut{
 background-image:url(../images/post.gif);
 width:81px;
 letter-spacing:0px;
}
#copy.commonbut{
 background-image:url(../images/copy.gif);
 width:81px;
 letter-spacing:0px;
}
#go.commonbut{
 background-image:url(../images/tiaozhuan.gif);
 width:45px;
 height:19px;
 padding-left:3px;
 border:0px;
}
#newbutton.commonbut{
 background-image:url(../images/newbut.gif);
 width:78px;
}
#newbutton1.commonbut{
 background-image:url(../images/newbut.gif);
 width:78px;
 letter-spacing:0px;
}
#newbutton2.commonbut{
 background-image:url(../images/xinjian97.gif);
 width:97px;
 letter-spacing:0px;
}
#addbutton.commonbut{
 background-image:url(../images/newbut.gif);
 width:78px;
 letter-spacing:0px;
}
#excelbutton.commonbut{
 background-image:url(../images/excelBut.gif);
 width:97px;
 letter-spacing:0px;
}
#yanqi.commonbut{
 background-image:url(../images/date1.gif);
 width:78px;
 letter-spacing:0px;
}
#charge.commonbut{
 background-image:url(../images/money.gif);
 width:78px;
 letter-spacing:0px;
}
#delbutton.commonbut{
 background-image:url(../images/delbut.gif);
 width:78px;
}
#delbutton2.commonbut{
 background-image:url(../images/delete2.gif);
 letter-spacing:0px;
 width:88px;
}
#trdbutton.commonbut{
 background-image:url(../images/trdbut.gif);
 width:78px;
}
#secbutton.commonbut{
 background-image:url(../images/secbut.gif);
 width:92px;
}
#maxbutton.commonbut{
 background-image:url(../images/maxbut.gif);
 width:155px;
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
