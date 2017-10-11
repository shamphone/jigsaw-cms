function _timer() {
      this.time = new Date();
}

_timer.prototype.setTime = function(time){
        try{
          var parm = time.split(" ");
          var parm1 = parm[0].split("-");
          var parm2 = parm[1].split(":");
          for(var i=0;i<3;i++){//兼容错误的日期格式，缺少的部分补0
        	  if(typeof(parm1[i])=='undefined'){
        		  if(i==1){
        			  parm1[i] = 1;
        		  }else{
        			  parm1[i] = 0;  
        		  }        		        		  
        	  }
        	  if(typeof(parm2[i])=='undefined'){
        		  parm2[i] = 0;        		  
        	  }
          }
          var temp = parm1[1]-1;
          var base=new Date(parm1[0],temp,parm1[2],parm2[0],parm2[1],parm2[2]);
          this.time = base;
        }catch(e){}
}
_timer.prototype.getTime = function(){
          today=new Date();
          return this.time>today?this.time-today:today-this.time;
}

_timer.prototype.renderD = function(obj,timer){
        obj.innerHTML=Math.floor(this.getTime()/(24*60*60*1000));
        window.setTimeout(function(){timer.renderD(obj,timer);}, 1000); 
}
_timer.prototype.renderH = function(obj,timer){
        obj.innerHTML=Math.floor((this.getTime()%(24*60*60*1000))/(60*60*1000));
        window.setTimeout(function(){timer.renderH(obj,timer);}, 1000);
}
_timer.prototype.renderM = function(obj,timer){
        obj.innerHTML=Math.floor(((this.getTime()%(24*60*60*1000))%(60*60*1000))/(60*1000));
        window.setTimeout(function(){timer.renderM(obj,timer);}, 1000);
}
_timer.prototype.renderS = function(obj,timer){
        obj.innerHTML=Math.floor((((this.getTime()%(24*60*60*1000))%(60*60*1000))%(60*1000))/1000);
        window.setTimeout(function(){timer.renderS(obj,timer);}, 1000);
}

