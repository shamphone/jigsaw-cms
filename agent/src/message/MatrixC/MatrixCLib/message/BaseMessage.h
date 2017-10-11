#ifndef BASE_MESSAGE_H
#define BASE_MESSAGE_H

namespace LyvcMessage {

class BaseMessage {

public:

    // 发送者ID
    __int64 _senderId;

	// 会议ID, 用于在多会议之间分流消息
	__int64 _conferenceId;

public:

    // Constructor
    BaseMessage() {
        _senderId = 0;
		_conferenceId = 0;
    }

    // Destructor
    virtual ~BaseMessage(){};

public:

    // Serialization
    virtual string toXML() = 0;

    // Deserialization, if xmlString can't be serialized, return false;
    virtual bool fromXML(string& xmlString) = 0;

public:

    ///////////////////////////////////////
    // Helper function for deserialization
    ///////////////////////////////////////

    // Find string between tag, if no tag found, return NULL.
    static string getStringBetweenTag(
        string& xmlString,         // String to be parsed
        const char* tagName);      // Tag name

    // HTML Escape
    static string encodeHtmlEscape(string content);
    static string decodeHtmlEscape(string htmlString);
};

};

#endif
