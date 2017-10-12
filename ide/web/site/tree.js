  /*
   * Content-seperated javascript tree widget
   * Copyright (C) 2005 SilverStripe Limited
   * Feel free to use this on your websites, but please leave this message in the fies
   * http://www.silverstripe.com/blog
  */

  /*
   * Initialise all trees identified by <ul class="tree">
   */

 function light(li){
              var all=document.getElementsByTagName("span");
              for(var i=0;i<all.length;i++){
              if(all[i].className=="light")
                   all[i].clearAttributes();
              }
              var link = li.getElementsByTagName("a");
              link[0].parentElement.className="light";
     }

  function autoInit_trees() {
          var candidates = document.getElementsByTagName('ul');
          var all = document.getElementsByTagName('span');
          //树根设为高亮显示
            for(var i=0;i<candidates.length;i++) {
              if(candidates[i].className=="tree"){
                       if(candidates[i].className && candidates[i].className.indexOf('tree') != -1) {
                          var litemp=candidates[i].firstChild;
                          litemp.firstChild.className="display";
                          initTree(candidates[i]);
                          candidates[i].className = candidates[i].className.replace(/ ?unformatted ?/, ' ');
                      }
                     //light(candidates[i].firstChild)
              }
    }
  }
  function setLight(path){
          var candidates = document.getElementsByTagName('ul');
            for(var i=0;i<candidates.length;i++) {
              if(candidates[i].className=="tree"){
                var links = candidates[i].getElementsByTagName("a");
                for(k=0;k<links.length;k++){
                  if(links[k].href.indexOf(path)!=-1){
                  var _li = links[k].parentElement;
                  while(_li.tagName!="LI")
                  {
                    _li=_li.parentElement;
                  }
                  light(_li);
                  }
                }
              }
            }
  }
  /*
   * Initialise a tree node, converting all its LIs appropriately
   */
  function initTree(el) {
          var i,j;
          var spanA, spanB, spanC;
          var startingPoint, stoppingPoint, childUL;
          // Find all LIs to process
          for(i=0;i<el.childNodes.length;i++) {
                  if(el.childNodes[i].tagName && el.childNodes[i].tagName.toLowerCase() == 'li') {
                          var li = el.childNodes[i];

                          // Create our extra spans
                          spanA = document.createElement('span');
                          spanB = document.createElement('span');
                          spanC = document.createElement('span');
                          spanA.appendChild(spanB);
                          spanB.appendChild(spanC);

                          spanA.className = 'a ' + li.className.replace('closed','spanClosed');

                          spanA.onMouseOver = function() {}
                          spanB.className = 'b';
                          spanB.onclick = treeToggle;
                          spanC.className = 'c';
                          // Find the UL within the LI, if it exists
                          stoppingPoint = li.childNodes.length;
                          startingPoint = 0;
                          childUL = null;
                          for(j=0;j<li.childNodes.length;j++) {
                                  if(li.childNodes[j].tagName && li.childNodes[j].tagName.toLowerCase() == 'div') {
                                          startingPoint = j + 1;
                                          continue;
                                  }

                                  if(li.childNodes[j].tagName && li.childNodes[j].tagName.toLowerCase() == 'ul') {
                                          childUL = li.childNodes[j];
                                          stoppingPoint = j;
                                          break;
                                  }
                          }

                          // Move all the nodes up until that point into spanC
                          for(j=startingPoint;j<stoppingPoint;j++) {
                                  spanC.appendChild(li.childNodes[startingPoint]);
                          }

                          // Insert the outermost extra span into the tree
                          if(li.childNodes.length > startingPoint) li.insertBefore(spanA, li.childNodes[startingPoint]);
                          else li.appendChild(spanA);

                          // Process the children
                          if(childUL != null) {
                                  if(initTree(childUL)) {
                                          addClass(li, 'children', 'closed');
                                          addClass(spanA, 'children', 'spanClosed');
                                  }
                          }
                                           }
          }

          if(li) {
                  // li and spanA will still be set to the last item

                  addClass(li, 'last', 'closed');
                  addClass(spanA, 'last', 'spanClosed');
                  return true;
          } else {
                  return false;
          }

  }


  /*
   * +/- toggle the tree, where el is the <span class="b"> node
   * force, will force it to "open" or "close"
   */
  function treeToggle(el, force) {
          el = this;
          var sty=el.getElementsByTagName("span");
          var all=document.getElementsByTagName("span");

          for(var i=0;i<all.length;i++){
          if(all[i].className=="light")
               all[i].clearAttributes();
          }
          var s=sty[1];
          s.className="light";
          var a=s.getElementsByTagName("a");
          a.className="";

  //点击连接不收缩目录树--jiangqi
  if(window.event.srcElement.tagName.toLowerCase()!='span') return;
  //--

          while(el != null && (!el.tagName || el.tagName.toLowerCase() != "li")) el = el.parentNode;
             //修改文件样式为闭合
             var aopen=document.getElementsByTagName("a");
             for(var i=0;i<aopen.length;i++){
                if(aopen[i].className=="open")
                   aopen[i].className="close";
            }

          // Get UL within the LI
          var childSet = findChildWithTag(el, 'ul');
          var topSpan = findChildWithTag(el, 'span');
          if( force != null ){
                   if( force == "open"){
                          treeOpen( topSpan, el )
                  }
                  else if( force == "close" ){
                          treeClose( topSpan, el )
                  }

          }

          else if( childSet != null) {
                  // Is open, close it
                  if(!el.className.match(/(^| )closed($| )/)) {
                          treeClose( topSpan, el )
                  // Is closed, open it
                  } else {
                          treeOpen( topSpan, el )
                  }

          }
          else{
           //设置样式为打开
            var a = el.getElementsByTagName("a");
            a[0].className="open";
          }
   }


  function treeOpen( a, b ){
          removeClass(a,'spanClosed');
          removeClass(b,'closed');
  }


  function treeClose( a, b ){
          addClass(a,'spanClosed');
          addClass(b,'closed');
  }

  /*
   * Find the a child of el of type tag
   */
  function findChildWithTag(el, tag) {
          for(var i=0;i<el.childNodes.length;i++) {
                  if(el.childNodes[i].tagName != null && el.childNodes[i].tagName.toLowerCase() == tag) return el.childNodes[i];
          }
          return null;
  }

  /*
   * Functions to add and remove class names
   * Mac IE hates unnecessary spaces
   */
  function addClass(el, cls, forceBefore) {
          if(forceBefore != null && el.className.match(new RegExp('(^| )' + forceBefore))) {
                  el.className =el.className.replace(new RegExp("( |^)" + forceBefore), '$1' + cls + ' ' + forceBefore);
          } else if(!el.className.match(new RegExp('(^| )' + cls + '($| )'))) {
                  el.className += ' ' + cls;
                  el.className =el.className.replace(/(^ +)|( +$)/g, '');
          }
  }
  function removeClass(el, cls) {
          var old = el.className;
          var newCls = ' ' + el.className + ' ';
          newCls = newCls.replace(new RegExp(' (' + cls + ' +)+','g'), ' ');
          el.className = newCls.replace(/(^ +)|( +$)/g, '');
  }

  /*
   * Handlers for automated loading
   */
   _LOADERS = Array();

  function callAllLoaders() {
          var i, loaderFunc;
          for(i=0;i<_LOADERS.length;i++) {
                  loaderFunc = _LOADERS[i];
                  if(loaderFunc != callAllLoaders) loaderFunc();
          }
  }

  function appendLoader(loaderFunc) {
          if(window.onload && window.onload != callAllLoaders)
                  _LOADERS[_LOADERS.length] = window.onload;

          window.onload = callAllLoaders;

          _LOADERS[_LOADERS.length] = loaderFunc;
  }

  appendLoader(autoInit_trees);
