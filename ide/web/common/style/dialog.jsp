<%@ page contentType="text/css; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
body, td, th,input
{
font-size: 12px;
}
th{
  font-weight:normal;
}
body
{
	overflow:visible;
	padding: 6px 6px 6px 6px;
	margin: 0px 0px 0px 0px;
        background-color:buttonface;
        
}
filedset{
	height:1500px;
}
form
{
	margin: 0px 0px 0px 0px;
	padding: 0px 0px 0px 0px;
}

ul{
	list-style:none;
	margin:5px 5px 5px 5px;
}
button{
  height:24px;
  font-size:12px;
  margin-left: 2px;
  margin-right: 2px;
}
.seperator{ width:1px;border:1px inset;}
.btnMore{  width:63px;margin-left:2px; height:21px;}
A {COLOR: #000000;TEXT-DECORATION: none}
A:active {COLOR: #000000;TEXT-DECORATION: none}
A:link {COLOR: #000000;TEXT-DECORATION: none}
A:visited {COLOR: #000000;TEXT-DECORATION: none}
A:hover {COLOR: #000000;TEXT-DECORATION: none}
.operation
{
border-top:2px groove;
position:absolute;
  left: 0px;
  top: expression(document.body.clientHeight-40);
  padding: 5px 5px 5px 5px;
  padding-left:5px;
  text-align: right;
  width:95%;
}
*+html .operation{
 width:100%;
}
*html .operation{
 width:100%;
}
.operation button
{
        padding-left:12px;
        padding-right:12px;
}
.operation img
{
	margin-left: 5px;
	margin-right: 5px;
}
.insetDiv{
    background-color: white;
    border: 2px inset;
    overflow-y: scroll;
    overflow-x: auto;
    padding: 2px 2px 2px 2px;
}

#definitionTree
{
	height: 220px;
}
#DLGToolbar{
  border-bottom:2px groove;
  margin: 2px 2px 2px 2px;
  height: 26px;
  vertical-align : middle;
  behavior: url(<html:rewrite module="/common" page="/style/dlgtoolbar.htc"/>);
}
#DLGToolbar img{
  border: 0px;
  height: 16px;
  width: 16px;
}
#DLGToolbar input{
border: 1px solid darkblue;
height: 20px;
margin: 1px 1px 1px 1px;
padding: 0px 1px 0px 1px;
vertical-align : middle;
}
#DLGToolbar select{
border: 1px solid darkblue;
height: 20px;
margin: 1px 1px 1px 1px;
padding: 0px 1px 0px 1px;
vertical-align : middle;
}

#DLGToolbar20{
  border-bottom:2px groove;
  margin: 2px 2px 2px 2px;
  height: 30px;
  vertical-align : middle;
  behavior: url(<html:rewrite module="/common" page="/style/dlgtoolbar.htc"/>);
}
#DLGToolbar20 img{
  border: 0px;
  height: 20px;
  width: 20px;
}

.DLGButton
{
    margin: 1px 1px 1px 1px;
    padding: 0px 1px 0px 1px;
    height: 16px;
    vertical-align : middle;
    border: buttonface 1px solid;
    cursor:pointer;
    display:inline-block;
    behavior: url(<html:rewrite module="/common" page="/style/dlgbutton.htc"/>);
}

.DLGButton_On_Over
{
    border: #0A246A 1px solid;
    background-color: #B6BDD2;
}

.DLGButton span{
   vertical-align : middle;
   height: 16px;
   margin-left: 2px;
}
.selectRightBtn {
 width:80px;
 margin-bottom: 10px;
 font-size:10px;
}
.hilight{
  color:white;
  background-color:darkblue;
}
.listCheckbox{
 margin: 0 0 0 0;
 padding: 0 0 0 0;
 vertical-align:middle;
 text-align:center;
 width:14px;
 height:14px;
}
#oListPanel {
    width: 100%;
    height:expression(document.body.clientHeight-50);
    overflow-y: scroll;
    overflow-x: hidden;
    padding:0px 0px 0px 0px;
    text-align:center;
    border-left:1px solid buttonshadow;
    }
#listTable {width:100%; border: none; background-color: window;}
#listTable thead tr{position:relative; top:expression(offsetParent.scrollTop); }
#listTable thead th {
	text-align: center;
	background-color: buttonface;
	font-weight: bold;
	border-top: 1px solid white;
	border-right:1px  solid buttonshadow;
	border-left:1px  solid window;
        border-bottom:2px  ridge buttonface;
	}
#listTable tbody td{
        text-align: left;
	border-bottom: solid 1px buttonshadow;
	border-right: solid 1px buttonshadow;
        height:16px;
        cursor:pointer
        }
#listTable tbody th{text-align: left;border-bottom: solid 1px buttonshadow;height:16px;cursor:pointer;font-weight:normal;}

#listTable input{height:16px;font-size:11px;}

*+html #listTable input{height:20px;font-size:11px;}

*html #listTable input{height:20px;font-size:11px;}

#listTable tfoot td{text-align: right;background-color: buttonface;border-top: solid 1px buttonshadow;}

#mappingContainer table{
	border-collapse:collapse;
	border:1px solid buttonface;	
}
#mappingContainer td{
	height:22px;
}
#mappingContainer input{
	border:0px;
	padding:0px 0px 0px 0px;
	margin:0px 0px 0px 0px;
	width:expression(document.body.clientWidth-335);	
}
#mappingContainer button{
	padding:0px 0px 0px 0px;
	margin:0px 0px 0px 0px;
	width:20px;
	height:20px;
	border-width:1px;
}

.PopupTitleBorder{
	border-bottom: #d5d59d 1px solid;
}
.PopupTitle{
	font-weight: bold;
	font-size: 14pt;
	color: #737357;
	background-color: #e3e3c7;
	padding: 3px 10px 3px 10px;
	margin-bottom: 10px;
}
.errorTip {color:red}
.indent {margin-left:6px}
#navcontainer input{width:16px;height:14px;}

#footer{
  height:20px;vertical-align:middle;text-align:right;padding:2px 2px 2px 2px;font-size:12px;
}
#footer a{font-size:12px;
} 
*+html #footer{
  height:20px;vertical-align:middle;text-align:right;padding:2px 10px 2px 2px;width:auto;
}
*html #footer{
  height:20px;vertical-align:middle;text-align:right;padding:2px 10px 2px 2px;width:auto;
}
.treeDiv {
	border-style: inset;
	border-width: 2;
	margin-right: 5px;
	background-color: window;
	height: 505px;
	width: 200px;
	overflow: scroll;
	padding-left:2 ;
	overflow-x:hidden;
}
*html .treeDiv {
	border-style: inset;
	border-width: 2;
	margin-right: 5px;
	background-color: window;
	width: 200px;
	height: 480px;
	overflow: scroll;
	padding-left:2 ;
}
*+html .treeDiv {
	border-style: inset;
	border-width: 2;
	margin-right: 5px;
	background-color: window;
	width: 200px;
	height: 480px;
	overflow: scroll;
	padding-left:2 ;
}