#ifndef MESSAGE_HANDLER_ITEM_H
#define MESSAGE_HANDLER_ITEM_H

#include "MessageTarget.h"

namespace LyvcMessage {

    struct MessageHandlerItem {

        MessageTarget* pMessageTarget;
        PMSG_HANDLER pMessageHandler;

        MessageHandlerItem() {
            pMessageTarget = NULL;
            pMessageHandler = NULL;
        }

        friend int operator == (const MessageHandlerItem& item1, const MessageHandlerItem& item2)
        {
            if(  (item1.pMessageTarget == item2.pMessageTarget)
              && (item1.pMessageHandler == item2.pMessageHandler) )
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
    };

};

#endif
