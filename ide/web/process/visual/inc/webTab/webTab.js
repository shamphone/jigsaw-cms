

function hoverTab(tab) {
  var hoverTabId = ''
  var selectedTabId = '';

  if (tab=='' || tab==null) return;

  topRow = document.getElementById('WebTab');
  tabArray = topRow.childNodes;
  for (var i=0; i<tabArray.length; i++) {
    if (tabArray[i].className == 'hovertab') hoverTabId = tabArray[i].id;
	if (tabArray[i].className == 'selectedtab') selectedTabId = tabArray[i].id;
  }

  if(hoverTabId!='' && selectedTabId!='' && hoverTabId!=selectedTabId) document.getElementById(hoverTabId).className = 'tab';		
  if(selectedTabId!='' && tab!=selectedTabId) document.getElementById(tab).className = 'hovertab';	

  if(tab!=selectedTabId) {
    hoverTabId = tab;
  }else{ hoverTabId = ''; }
}

function switchTab(tab,contents) {        
  tabAllOff();
  document.getElementById(tab).className = 'selectedtab';
  document.getElementById(contents).className = 'selectedcontents';  
}

function clearTabTop() {
  topRow = document.getElementById('WebTab');
  tabArray = topRow.childNodes;
  for (var i=0; i<tabArray.length; i++) {
    if (tabArray[i].className != 'tabspacer') {
	  tabArray[i].className = 'tab';
    }
  }
}

function clearTabContents() {
  contentsCell = document.getElementById('contentscell');
  contentsArray = contentsCell.childNodes;
  for (var j=0; j<contentsArray.length; j++) {
    contentsArray[j].className = 'contents';
  }
}

function tabAllOff() {
  clearTabTop();
  clearTabContents();
}