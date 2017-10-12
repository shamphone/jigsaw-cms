<%@ page contentType="text/html; charset=UTF-8" %><?xml version="1.0" encoding="utf-8"?>
<multistatus xmlns="DAV:">
    <response>
      <href>index.jsp</href>
      <propstat>
        <prop>
         <creationdate/>
         <displayname>显示页面</displayname>
           <getlastmodified/>
           <getcontenttype>text/html</getcontenttype>
           <resourcetype></resourcetype>
        </prop>
          <status>HTTP/1.1 200 OK</status>
        </propstat>
        </response>
    <response>
      <href>index.doc</href>
      <propstat>
        <prop>
         <creationdate/>
         <displayname>文档</displayname>
           <getlastmodified/>
           <getcontenttype>application/msword</getcontenttype>
           <resourcetype></resourcetype>
        </prop>
          <status>HTTP/1.1 200 OK</status>
        </propstat>
        </response>
  <response>
      <href>/channel2</href>
      <propstat>
        <prop>
         <creationdate/>
         <displayname>栏目2</displayname>
           <getlastmodified/>
           <supportedlock/>
           <resourcetype><collection/></resourcetype>
        </prop>
          <status>HTTP/1.1 200 OK</status>
        </propstat>
        </response>
</multistatus>
