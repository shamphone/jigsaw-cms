var MenuTextColor_enable = '#3C4885'
var MenuTextColor_disable = '#BABED3'

var appState = new applicationState()

function applicationState() {
  this.contextMenu = null
}

function loadContextMenu(xmlpath,xslpath) {
  var xmlDoc
  var xslDoc
  var contextMenu

  if(xmlpath != "") {
    xmlDoc = new ActiveXObject('MSXML2.DOMDocument')
    xmlDoc.async = false;

    xslDoc = new ActiveXObject('MSXML2.DOMDocument')
    xslDoc.async = false;

    xmlDoc.loadXML(xmlpath)	
    xslDoc.load(xslpath)

    if(appState.contextMenu != null) appState.contextMenu.removeNode(true)
  
    document.body.insertAdjacentHTML("beforeEnd", xmlDoc.documentElement.transformNode(xslDoc))
    contextMenu = document.body.childNodes(document.body.childNodes.length-1)
	
	var MenuHeight = xmlDoc.documentElement.childNodes.length*25
	var ValignD = document.body.clientHeight - window.event.y
    contextMenu.style.left = window.event.x-3
	if(ValignD > MenuHeight){contextMenu.style.top = window.event.y-5}
	else{
		contextMenu.style.top = window.event.y < MenuHeight? 5:window.event.y-MenuHeight+10
	}    		

    appState.contextMenu = contextMenu
    window.event.cancelBubble = true
  }
}

function loadContextMenuSub(obj) {
  var contextMenu
  var parentMenu

  parentMenu = returnContainer(obj)
  contextMenu = document.all[obj.id + "Sub"]
  contextMenu.style.display = "block"
  contextMenu.style.top = obj.offsetTop + parentMenu.style.pixelTop
  contextMenu.style.left = obj.offsetWidth + parentMenu.style.pixelLeft
  contextMenu.style.zIndex = '100'
  parentMenu.subMenu = contextMenu
}

function contextHighlightRow(obj) {
  var parentMenu
  var subMenu
  var i

  parentMenu = returnContainer(obj)

  if(obj.selected == "false") {
    for(i=0; i < obj.childNodes.length; i++) {

	  obj.childNodes(i).style.borderTop = "1px solid #919CD0"
      obj.childNodes(i).style.borderBottom = "1px solid #919CD0"

      if(obj.childNodes(i).cellIndex == 0) {
        obj.childNodes(i).style.borderLeft = "1px solid #919CD0"
      }
      else if (obj.childNodes(i).cellIndex == obj.cells.length-1) {
        obj.childNodes(i).style.borderRight = "1px solid #919CD0"
      }
    }

    if(parentMenu.subMenu != null && parentMenu != parentMenu.subMenu) {
      subMenu = parentMenu.subMenu

      while(subMenu != null) {
        subMenu.style.display = "none"
        subMenu = subMenu.subMenu
      }
    }
    obj.selected = "true"
  }
  else {
    for(i=0; i < obj.childNodes.length; i++) {

      if(i == 0) {
        obj.childNodes(i).style.borderTop = "1px solid " + obj.titlebar
        obj.childNodes(i).style.borderBottom = "1px solid " + obj.titlebar
      }
      else {
        obj.childNodes(i).style.borderTop = "1px solid " + obj.background
        obj.childNodes(i).style.borderBottom = "1px solid " + obj.background
      }
      
      if(obj.childNodes(i).cellIndex == 0) {
        obj.childNodes(i).style.borderLeft = "1px solid " + obj.titlebar
      }
      else if (obj.childNodes(i).cellIndex == obj.cells.length-1) {
        obj.childNodes(i).style.borderRight = "1px solid " + obj.background
      }
    }
    obj.selected = "false"
  }
}

function cleancontextMenu() {
  var contextMenu

  // remove and destroy context menu
  if(appState.contextMenu != null) {
    contextMenu = appState.contextMenu.removeNode(true)
    contextMenu = null
  }
}

function returnContainer(container) {
  while(container.tagName != "DIV") {
    container = container.parentNode  
  }
  return container
}


function contextmenu_nodechange(root,nodeid,menuText,imgURL,color,onclickTxt)

  var Node = root.childNodes.item(nodeid)
  if(menuText!=''){Node.childNodes.item(0).text = menuText}
  if(imgURL!=''){Node.childNodes.item(1).text = imgURL}
  if(color!=''){Node.childNodes.item(2).text = color}
  Node.childNodes.item(3).text = onclickTxt
  return root
}
