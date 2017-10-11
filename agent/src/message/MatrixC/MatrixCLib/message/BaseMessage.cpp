#include "Stdafx.h"
#include "BaseMessage.h"

string LyvcMessage::BaseMessage::getStringBetweenTag(
    string& xmlString, 
    const char* tagName)
{
    string beginTagName = string("<") + tagName + string(">");
    string endTagName = string("</") + tagName + string(">");

	string::size_type begin = xmlString.find(beginTagName);
	if(begin == string::npos){
		return "";
	}
	string::size_type end = xmlString.find(endTagName, begin);
	if(end == string::npos){
		return "";
	}

    unsigned int length = end-begin-beginTagName.length();
    unsigned int contentBegin = begin + beginTagName.length();
    return xmlString.substr(contentBegin, length);
}

static const char* amp = "&";
static const char* ampEscape = "&amp;";

static const char* gt = ">";
static const char* gtEscape = "&gt;";

static const char* lt = "<";
static const char* ltEscape = "&lt;";

static string replaceString(string content, const char* from, const char* to)
{
    string::size_type fromlen = ::strlen(from);
    string::size_type tolen   = ::strlen(to);

    string::size_type pos = 0;
    while( (pos = content.find(from, pos)) != string::npos)
    {
        content = content.replace(pos, fromlen, to);
        pos = pos + tolen;
    }
    return content;
}


static string UTF8ToUnicode(string utf8)
{
	//UTF8±àÂë×ª»»ÎªUnicode±àÂë
	/*
	int unicodeLen = MultiByteToWideChar(CP_UTF8, NULL, utf8.c_str(), utf8.length(), NULL, 0);
	WCHAR * pUnicode = new WCHAR[unicodeLen+1];
	if (!pUnicode)
	{
		return NULL;
	}
	MultiByteToWideChar(CP_UTF8, NULL, utf8.c_str(), utf8.length(), pUnicode, unicodeLen);
	pUnicode[unicodeLen] = '\0';

	//Unicode±àÂë×ª»»ÎªMulti-Byte±àÂë
	int len = WideCharToMultiByte(CP_ACP, 0, pUnicode, wcslen(pUnicode), NULL, 0, NULL, NULL);   
	char * buffer = new char[len + 1];
	WideCharToMultiByte(CP_ACP, 0, pUnicode, wcslen(pUnicode), buffer, len, NULL, NULL);   
	buffer[len] = '\0';   
	string strUnicode(buffer);   
	delete[] buffer;   
	delete [] pUnicode;

	return strUnicode;
	*/
	return utf8;
}

static string UnicodeToUTF8(string unicode)
{
	//Multi-Byte±àÂë×ª»»ÎªUnicode±àÂë
	/*
	int len = MultiByteToWideChar(CP_ACP, 0, unicode.c_str(), unicode.size(), NULL, 0);   
	WCHAR * buffer = new WCHAR[len + 1];   
	MultiByteToWideChar(CP_ACP, 0, unicode.c_str(), unicode.size(), buffer, len);   
	buffer[len] = '\0';
	wstring wunicode(buffer);     

	//Unicode±àÂë×ª»»ÎªUTF8±àÂë
	int utf8Len = WideCharToMultiByte(CP_UTF8, NULL, buffer, wcslen(buffer), NULL, 0, NULL, NULL);
	char * pUTF8 = new char[utf8Len+1];
	if (!pUTF8)
	{
		return NULL;
	}

	//×ªÎªUnicode
	WideCharToMultiByte(CP_UTF8, NULL, buffer, wcslen(buffer), pUTF8, utf8Len, NULL, NULL);
	pUTF8[utf8Len] = '\0';
	string strUTF8(pUTF8);
	delete[] buffer; 
	delete [] pUTF8;

	return strUTF8;
	*/
	return unicode;
}

string LyvcMessage::BaseMessage::encodeHtmlEscape(string content)
{
    string htmlString;
    htmlString = replaceString(content, amp, ampEscape);
    htmlString = replaceString(htmlString, gt, gtEscape);
    htmlString = replaceString(htmlString, lt, ltEscape);
    return UnicodeToUTF8(htmlString);
}

string LyvcMessage::BaseMessage::decodeHtmlEscape(string htmlString)
{
    string content;
    content = replaceString(htmlString, ltEscape, lt);
    content = replaceString(content, gtEscape, gt);
    content = replaceString(content, ampEscape, amp);
    return UTF8ToUnicode(content);
}