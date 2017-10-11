#ifndef MESSAGE_FACTORY_H
#define MESSAGE_FACTORY_H


namespace LyvcMessage{

    class BaseMessage;

    class MessageFactory {

    public:
        // Create a message object, and deserialize the object from XML string
        // Return value: MessageObject pointer.
        // Parameter:
        //       message: XML String
        //       messageId : Out parameter, the id of the message.
        static BaseMessage* createMessage(string& message, int& messageId);

    };

};

#endif
