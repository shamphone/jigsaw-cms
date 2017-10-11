<?xml version="1.0" encoding="GB2312"?>
<HTML xmlns:xsl="http://www.w3.org/TR/WD-xsl">
<HEAD>
<TITLE>已结束会议记录</TITLE>
<style type="text/css">
.f14{font-size:14px;}
</style>
</HEAD>
<BODY> 
<TABLE bgColor="#D5DAED" border="0" cellspacing="1" cellpadding="2" width="100%" style="WORD-BREAK: break-all" class='f14'>
<TR align='left'>
    <TH width="30%" bgColor="#e7ecf4">会议名称</TH>
    <TH bgColor="#e7ecf4">会议描述</TH>
    <TH width="15%" bgColor="#e7ecf4">开始时间</TH>
    <TH width="15%" bgColor="#e7ecf4">结束时间</TH>
</TR>
<xsl:for-each select="lyvc/conference">
<xsl:element name="a">
    <xsl:attribute name="name">#<xsl:value-of select="id"/></xsl:attribute>
</xsl:element>
<TR>
    <TD bgColor="#ffffff"><xsl:value-of select="confname"/></TD>
    <TD bgColor="#ffffff"><xsl:value-of select="desc"/></TD>
    <TD bgColor="#ffffff"><xsl:value-of select="startdate"/></TD>
    <TD bgColor="#ffffff"><xsl:value-of select="enddate"/></TD>
</TR>
</xsl:for-each>
</TABLE>
</BODY>
</HTML>
