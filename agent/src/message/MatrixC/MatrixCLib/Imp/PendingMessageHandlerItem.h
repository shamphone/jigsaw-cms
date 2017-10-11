#ifndef PENDING_MESSAGE_HANDLER_ITEM_H
#define PENDING_MESSAGE_HANDLER_ITEM_H

#include "MessageTarget.h"

namespace LyvcMessage {

    struct PendingMessageHandlerItem {

        int messageId;
        MessageTarget* pMessageTarget;
        PMSG_HANDLER pMessageHandler;

        PendingMessageHandlerItem() {
            messageId = 0;
            pMessageTarget = NULL;
            pMessageHandler = NULL;
        }

    };

};

#endif
