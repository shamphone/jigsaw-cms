package com.fulong.longcon;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.fulong.common.ResourceUtils;
import junit.framework.TestCase;

/**
 * <p>Title: 龙驭会员管理系统2.0</p>
 *
 * <p>Description: 龙驭会员管理系统2.0</p>
 *
 * <p>Copyright: Copyright (c) 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author Lixf
 * @version 2.0
 */
public class BasicTestCase
    extends TestCase {
    public static final Log log = LogFactory.getLog(BasicTestCase.class);
    protected Properties properties;
    private long start;
    protected void setUp() throws Exception {
        super.setUp();
        this.properties = new Properties();
        properties.load(ResourceUtils.getResourceAsStream("test.properties"));
        this.start=System.currentTimeMillis();
    }

    protected String getTestString(String name) {
        try {
            return new String(this.properties.getProperty(name).getBytes(
                "ISO8859-1"), "UTF-8");
        }
        catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected String[] getTestStrings(String prefix) {
        Vector results = new Vector();
        for (Enumeration key = this.properties.keys(); key.hasMoreElements(); ) {
            String strKey = (String) key.nextElement();
            if (strKey.startsWith(prefix)) {
                results.add(this.getTestString(strKey));
            }
        }
        return (String[]) results.toArray(new String[results.size()]);
    }

    protected void tearDown() throws Exception {
        log.info(this.getName()+" use time:"+(System.currentTimeMillis()-start));
        super.tearDown();
    }

}
