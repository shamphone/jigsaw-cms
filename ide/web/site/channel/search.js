function ChannelSearcher(oTree){
  this.arrBackup = new Array();
  this.tree=oTree;
  this.treeDiv = document.all(this.tree.id).parentElement;
}

/**
  * 执行搜索
  *
 **/
ChannelSearcher.prototype.doSearch = function (keywords){
	if(keywords==null || keywords.trim().length==0)
		return;
	this.restoreAll();
	this.tree.expandAll();
	var channels=this.treeDiv.all.tags("a");
	if(channels!=null)
	   for(var i=0;i<channels.length;i++){
		 if(channels[i].innerText.indexOf(keywords)>=0){
		   this.backup(channels[i]);
		   this.hilight(channels[i],keywords);
		 }
	   }
  }
ChannelSearcher.prototype.hilight = function(oA,keywords){
	oA = oA.getElementsByTagName("label")[0];
	var newHTML=oA.innerHTML;
	var exp=new RegExp(keywords, "gi");
	newHTML = newHTML.replace(exp, "<span class='hilight'>"+keywords+"</span>");
	oA.innerHTML = newHTML;
	
  }
ChannelSearcher.prototype.backup = function (oA){
	oA = oA.getElementsByTagName("label")[0];
	this.arrBackup[oA.id]=oA.outerHTML;
  }
ChannelSearcher.prototype.restore = function (oA){
	oA = oA.getElementsByTagName("label")[0];
	if(this.arrBackup[oA.id]!=null)
	   oA.outerHTML=this.arrBackup[oA.id];
  }
ChannelSearcher.prototype.restoreAll =  function (){
    this.tree.expandAll();
    
	var channel=this.treeDiv.all.tags("a");
	if(channel!=null)
	   for(var i=0;i<channel.length;i++)
	   {
		   this.restore(channel[i]);
		   }
  }
