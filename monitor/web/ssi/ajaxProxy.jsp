<%@ page import="java.net.*,java.util.*,java.lang.*,java.io.*"%>
<%@ page contentType="text/xml;charset=gb2312"%>
<%
String url = null;
StringBuffer params = new StringBuffer();
Enumeration enu = request.getParameterNames();
int total = 0;
while (enu.hasMoreElements()) {
String paramName=(String)enu.nextElement();
if(paramName.equals("url")){
   url=request.getParameter(paramName);
}else{
   if(total==0){
    params.append(paramName).append("=").append(URLEncoder.encode(request.getParameter(paramName), "UTF-8"));
   } else {
    params.append("&").append(paramName).append("=").append(URLEncoder.encode(request.getParameter(paramName), "UTF-8"));
   }
   ++total;
}
}
url = url + "?" + params.toString();
//out.println(url);
if(url != null){
// ä½¿ç\uFFFD\uFFFDGET\uFFFD\uFFFD¹å\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD¡å\uFFFD¨å\uFFFD\uFFFD\uFFFD\uFFFD\uFFFDè¯·æ\uFFFD\uFFFD
URL connect = new URL(url.toString());
URLConnection connection = connect.openConnection();
connection.connect();
BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
String line;
while((line = reader.readLine()) != null){
   out.println(line);
}
reader.close();
}
%>

