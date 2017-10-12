package com.fulong.common;

import junit.framework.*;
/**
 *
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author Lixf
 * @version 1.0
 */
public class TestDesEncrypter extends TestCase {
    private DesEncrypter desEncrypter = null;

    protected void setUp() throws Exception {
        super.setUp();
        /**@todo verify the constructors*/
        desEncrypter = new DesEncrypter("");
    }

    protected void tearDown() throws Exception {
        desEncrypter = null;
        super.tearDown();
    }

    public void testDecrypt() {
        String str = "北京中科辅龙计算机技术股份有限公司";
        String actualReturn = desEncrypter.encrypt(str);
        assertEquals("return value", "", actualReturn);
        /**@todo fill in the test code*/
    }

}
