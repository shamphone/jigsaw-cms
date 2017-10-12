package com.fulong.common.util;

import junit.framework.TestCase;
import java.text.MessageFormat;

public class TestStringUtils extends TestCase {
    private StringUtils stringUtils = null;

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testFormatAll() {
        String source = "This is a $-''{0}'' source,''{1}' source";
        Object[] args = new String[]{"abc$","cde"};
        String expectedReturn = "This is a $-''abc$'' source,''cde' source";
        String actualReturn = StringUtils.formatAll(source,args);
        assertEquals("return value", expectedReturn, actualReturn);
        /**@todo fill in the test code*/
    }

}
