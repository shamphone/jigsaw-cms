var _today = {
     render:function(oDay,format){
      var today = new Date(oDay.replace(/-/,"/"));
      document.write(dateFormat(today, format));
     }
};

