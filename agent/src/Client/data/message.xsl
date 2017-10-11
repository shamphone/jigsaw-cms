<?xml version="1.0" encoding="GB2312"?>
<HTML xmlns:xsl="http://www.w3.org/TR/WD-xsl">
<HEAD>
<TITLE>聊天记录</TITLE>
<style type="text/css">
.f13{font-size:13px;}
</style>
</HEAD>
<BODY> 
<TABLE bgColor="#D5DAED" cellspacing="1" cellpadding="2" width="100%" style="WORD-BREAK: break-all" class='f13'>
<TR align='left'>
    <TD width="10%" bgcolor='#ECF0F9'><font color="#012680">日期</font></TD>
    <TD width="10%" bgcolor='#ECF0F9'><font color="#012680">时间</font></TD>
    <TD width="20%" bgcolor='#ECF0F9'><font color="#012680">会议</font></TD>
    <TD width="10%" bgcolor='#ECF0F9'><font color="#012680">发送</font></TD>
    <TD width="10%" bgcolor='#ECF0F9'><font color="#012680">接收</font></TD>
    <TD bgcolor='#ECF0F9'><font color="#012680">消息</font></TD>
</TR>
<xsl:for-each select="lyvc/message">
<TR>
    <TD bgcolor='#FFFFFF'><font color="#808080"><xsl:value-of select="date"/></font></TD>
    <TD bgcolor='#FFFFFF'><font color="#808080"><xsl:value-of select="time"/></font></TD>
    <TD bgcolor='#FFFFFF'><font color="#032684"><xsl:value-of select="conference"/></font></TD>
    <TD bgcolor='#FFFFFF'><font color="#E00403"><xsl:value-of select="from"/></font></TD>
    <TD bgcolor='#FFFFFF'><font color="#012680"><xsl:value-of select="to"/></font></TD>
    <TD bgcolor='#FFFFFF'><font color="#202020"><xsl:value-of select="text"/></font></TD>
</TR>
</xsl:for-each>
</TABLE>
</BODY>
</HTML>
