package com.fulong.portal.servlet;

import java.util.Map;

import com.fulong.portal.utils.ParametersCipher;
import junit.framework.TestCase;

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
public class TestParametersCipher extends TestCase {
    private ParametersCipher parametersCipher = null;

    protected void setUp() throws Exception {
        super.setUp();
        parametersCipher = new ParametersCipher();
    }

    protected void tearDown() throws Exception {
        parametersCipher = null;
        super.tearDown();
    }

    public void testDecode() {
        String encrypt = "Eue3VdtMPspSdgmsu4Zd37s0FQ1PVg5BOPpoYj%2BOsvIOFS3MuA6e1JxNSP%2BWlhszfgAwjjwTxoMi%0D%0AKmJAuSpULqs5PVI5IonXjSqfbw24plieiWMmubnJ7w%3D%3D";
        String encoding = "UTF-8";
        Map expectedReturn = null;
        Map actualReturn = parametersCipher.decode(encrypt, encoding);
        assertEquals("return value", expectedReturn, actualReturn);
        /**@todo fill in the test code*/
    }

    public void testEncode() {
        Map parameters = null;
        String expectedReturn = null;
        String actualReturn = parametersCipher.encode(parameters);
        assertEquals("return value", expectedReturn, actualReturn);
        /**@todo fill in the test code*/
    }

}
