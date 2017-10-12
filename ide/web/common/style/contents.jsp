<%@ page contentType="text/css; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
body,td,th {font-size: 12px;}
body {
    overflow:hidden;
    margin:2px 2px 0px 0px;
    padding:0px 0px 0px 0px;
    border-width:0px;
    }
form{margin: 0px 0px 0px 0px;padding: 0px 0px 0px 0px;}
fieldset{
 height:115px;
}

#searchDiv{
    background-color:scrollbar;
    border: 1px solid buttonshadow;
    padding-right:2px;
    padding-bottom:0px;
    }
button{
 margin-right:2px;
 margin-bottom:2px;
}
#oListPanel {
    width: 100%;
    overflow-y: scroll;
    overflow-x: hidden;
    padding:0px 0px 0px 0px;
    text-align:center;
    border-left:1px solid buttonshadow;
    }
.tableClass thead{
}
.tableClass th{
	text-align: center;
	background-color: window;
	font-weight: bold;
	border-top: solid 1px buttonshadow;
	border-bottom: solid 1px buttonshadow;
	border-right: solid 1px buttonshadow;
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
	border-bottom: solid 1px buttonshadow;
	border-right: solid 1px buttonshadow;
        height:16px;
        cursor:hand
        }
#listTable tbody th{text-align: left;border-bottom: solid 1px buttonshadow;height:16px;cursor:pointer;font-weight:normal;}

#listTable input{height:20px;font-size:11px;}

#listTable tfoot tr{
  position: relative; 
      overflow-x: hidden;
      top: expression(parentNode.parentNode.offsetHeight >=offsetParent.offsetHeight ? 0 - parentNode.parentNode.offsetHeight + offsetParent.offsetHeight + offsetParent.scrollTop : 0);
      }
}
#listTable tfoot td{
	text-align: right;
	background-color: buttonface;
	border-top: solid 1px slategray;
}
#listTable input{
 	height:16px;
}
