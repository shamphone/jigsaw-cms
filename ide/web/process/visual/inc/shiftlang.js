
//-------------------ÓïÑÔÇÐ»»º¯Êý¼¯----------------------------------------------------------------------------

function shiftLanguage(lang,xmlName){
  if(lang=='' || xmlName=='') return false;
  var file = 'langs/'+xmlName+'_'+lang+'.xml';

  var xmlDoc = new ActiveXObject('MSXML2.DOMDocument');
  xmlDoc.async = false;
  var flag = xmlDoc.load(file);
  if(!flag){alert('mutil-Language function fail!');return false;}
  var xmlRoot = xmlDoc.documentElement;

  var Title = xmlRoot.getElementsByTagName("Title").item(0);
  if(Title!=null) {document.title=Title.getAttribute("text");}
  /*
  var Pros = xmlRoot.getElementsByTagName("Properties").item(0);
  if(Pros!=null){
	  for (var i = 0;i < Pros.childNodes.length;i++){
		Item = Pros.childNodes.item(i);
		id = Item.getAttribute("id");
		property = Item.getAttribute("property");
		text = Item.getAttribute("text");
		eval('document.all.'+id+'.'+property+'="'+text+'"');
	  }
  }
*/
  var Sels = xmlRoot.getElementsByTagName("Selects").item(0);
  if(Sels!=null){
	  for (var i = 0;i < Sels.childNodes.length;i++){
		Item = Sels.childNodes.item(i);
		id = Item.getAttribute("id");
		document.getElementById(id).innerHTML = '';
		
		for (var j = 0;j < Item.childNodes.length;j++){
		  Opt = Item.childNodes.item(j);
		  optValue = Opt.getAttribute("value");
		  optText = Opt.getAttribute("text");

		  var oOption = document.createElement("OPTION");
		  document.getElementById(id).options.add(oOption);
		  oOption.innerText = optText
		  oOption.value = optValue
		}
	  }
  }
}

//-------------------------------------------------------------------------------------------------------------