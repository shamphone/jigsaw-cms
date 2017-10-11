<?xml version="1.0" encoding="GB2312"?>
<HTML xmlns:xsl="http://www.w3.org/TR/WD-xsl">
<HEAD>
<TITLE>会议通知</TITLE>
<style type="text/css">
.f13{font-size:13px;}
</style>
</HEAD>
<BODY> 
<TABLE bgColor="#D5DAED" border="0" cellspacing="1" cellpadding="2" width="100%" style="WORD-BREAK: break-all" class='f13'>
<TR align='left'>
    <TH width="15%" bgColor="#e7ecf4">发送日期</TH>
    <TH width="10%" bgColor="#e7ecf4">通知标题</TH>
    <TH bgColor="#e7ecf4">通知内容</TH>
</TR>
<xsl:for-each select="lyvc/message">
<TR>
    <TD bgColor="#ffffff"><xsl:value-of select="date"/></TD>
    <TD bgColor="#ffffff"><xsl:value-of select="title"/></TD>
    <TD bgColor="#ffffff"><xsl:value-of select="content"/></TD>
</TR>
</xsl:for-each>
</TABLE>
</BODY>
</HTML>
