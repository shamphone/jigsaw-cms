package com.fulong.lyvc.message.generator;

import junit.framework.*;

import com.fulong.lyvc.message.parser.*;

public class TestMessageIdGenerator extends TestCase {
    private MessageIdGenerator messageIdHelper = null;

    protected void setUp() throws Exception {
        super.setUp();
        messageIdHelper = new MessageIdGenerator(
                "jdbc:postgresql://192.168.0.4:5432/lyvcmessage",
                "lyvcmessage",
                "lyvcmessage");
    }

    protected void tearDown() throws Exception {
        messageIdHelper = null;
        super.tearDown();
    }

    public void testgetId() throws Exception {

        System.out.println(messageIdHelper.getId("BaseMessage"));
        System.out.println(messageIdHelper.getId("NewMessage"));
        System.out.println(messageIdHelper.getId("BaseMessage"));
        System.out.println(messageIdHelper.getId("NewMessage"));
        System.out.println(messageIdHelper.getId("FooMessage"));
        System.out.println(messageIdHelper.getId("Foo2Message"));
        System.out.println(messageIdHelper.getId("Foo3Message"));

    }
}
