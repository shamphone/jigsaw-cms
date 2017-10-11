<%@ page contentType="text/html; charset=UTF-8" %><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
function redirectPagination($pageNum, $identifier)
{
  var url = document.location.protocol + "//" + document.location.host + document.location.pathname;
  var search = document.location.search;
  var params = [];
  if (search)
  {
    var tmps = search.substr(1).split("&");
    for (var i=0; i<tmps.length; i++)
    {
      if (tmps[i] && tmps[i].indexOf("pageNum=")!=0 && tmps[i].indexOf("pi=")!=0)
        params.push(tmps[i]);
    }
  }
  params.push("pageNum=" + $pageNum);
  params.push("pi=" + $identifier);
  document.location.href = url + "?" + params.join("&");
}
