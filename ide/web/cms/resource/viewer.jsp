<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@ page import="com.fulong.longcon.repository.NodeIterator"%>
<%@ page import="com.fulong.longcon.repository.Node"%>
<%@ page import="org.apache.commons.io.FileUtils"%>
<%@ page import="java.text.NumberFormat"%>
<html>
	<head>
		<title>viewer</title>
		<style type="text/css">
			thead tr { position: relative; top: expression(offsetParent.scrollTop); heigth: 20px; }
			thead td { background-color: #cfcfcf; border: 2 outset #f0f0f0; }
		</style>
   		<link id="thumbnail" href="style/thumbnail.css" type="text/css" rel="stylesheet">
   		<link id="detail" href="style/detail.css" type="text/css" rel="stylesheet" disabled="true">
   		<link id="icon" href="style/icon.css" type="text/css" rel="stylesheet" disabled="true">
		<script language="javascript" type="text/javascript" src="viewer.js"></script>
		<script language="javascript" type="text/javascript" src='<html:rewrite module="/common" page="/script/ajax.js"/>'></script>
		<script language="javascript" type="text/javascript">
			CONTEXT_PATH = "<%= request.getContextPath()%>";
			var Initialize = function() {
				<%
				NodeIterator iter = (NodeIterator) request.getAttribute("resources");
		    	NumberFormat formatter = NumberFormat.getIntegerInstance();
		    	formatter.setGroupingUsed(true);
				for (int i=0; iter.hasNext(); i++) {
					Node node = iter.nextNode();
					String id = node.getID();
					String name = node.getName()!=null ? node.getName() : "";
					String length = node.getProperty("length").getString();
					String mime = node.getProperty("mime")!=null ? node.getProperty("mime").getString() : "";
					if(mime==null)  mime="";
					String mimePrefix = mime.indexOf("/")>-1 ? mime.split("/")[0] : mime;
					String mimeSuffix = mime.indexOf("/")>-1 ? mime.split("/")[1] : mime;
					String createdTime = node.getProperty("createdTime")!=null ? node.getProperty("createdTime").getString() : "";
					String lengthWithUnits = null;
					int numLength = Integer.parseInt(length);
					if (numLength < FileUtils.ONE_KB)
						lengthWithUnits = "1 KB";
					else 
						lengthWithUnits = formatter.format(Math.ceil(numLength / FileUtils.ONE_KB)) + " KB";

					String path = "/portal" +  node.getPath();
					System.out.print(path);
					out.println("items.push(new ViewerItem('" + id + "', \"" + name + "\", '" + length + "', '" + mime + "', '" + mimePrefix + "', '" + mimeSuffix + "', '" + createdTime + "', '" + lengthWithUnits + "', \"" + path + "\", " + i + "));");
				}
				%>
			}
		</script>
	</head>
	<body>
		<span id="viewer"></span>
	</body>
</html>
