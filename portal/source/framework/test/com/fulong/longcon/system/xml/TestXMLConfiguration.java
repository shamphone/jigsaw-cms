package com.fulong.longcon.system.xml;

import com.fulong.longcon.system.Module;
import junit.framework.TestCase;
/**
 *
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */
public class TestXMLConfiguration extends TestCase {
    private XMLConfiguration xMLConfiguration = null;

    protected void setUp() throws Exception {
        super.setUp();
        xMLConfiguration = new XMLConfiguration();
    }

    protected void tearDown() throws Exception {
        xMLConfiguration = null;
        super.tearDown();
    }

    public void testLoad() throws Exception{
        this.xMLConfiguration.init();
        this.assertNotNull(this.xMLConfiguration.getProperty("system.name"));
    }
}
