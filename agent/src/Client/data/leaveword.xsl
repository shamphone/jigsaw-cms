<?xml version="1.0" encoding="GB2312"?>
<HTML xmlns:xsl="http://www.w3.org/TR/WD-xsl">
<HEAD>
<TITLE>联系人留言</TITLE>
<style type="text/css">
.f13{font-size:13px;}
</style>
</HEAD>
<BODY> 
<TABLE bgColor="#D5DAED" border="0" cellspacing="1" cellpadding="2" width="100%" style="WORD-BREAK: break-all" class='f13'>
<TR align='left'>
    <TH width="15%" bgColor="#e7ecf4">发送日期</TH>
    <TH width="10%" bgColor="#e7ecf4">发送者</TH>
    <TH bgColor="#e7ecf4">内容</TH>
</TR>
<xsl:for-each select="lyvc/message">
<xsl:element name="a">
    <xsl:attribute name="name">#<xsl:value-of select="index"/></xsl:attribute>
</xsl:element>
<TR>
    <TD bgColor="#ffffff"><xsl:value-of select="date"/></TD>
    <TD bgColor="#ffffff"><xsl:value-of select="sender"/></TD>
    <TD bgColor="#ffffff"><xsl:value-of select="content"/></TD>
</TR>
</xsl:for-each>
</TABLE>
</BODY>
</HTML>
