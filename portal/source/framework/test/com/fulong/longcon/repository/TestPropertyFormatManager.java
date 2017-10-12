package com.fulong.longcon.repository;

import junit.framework.*;
import java.util.*;
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
public class TestPropertyFormatManager extends RepositoryTestCase {
    private PropertyFormatManager manager = null;

    protected void setUp() throws Exception {
        super.setUp();
          beanFactory.getBean("formatManagers");
        manager = PropertyFormatManager.getInstance();
    }

    protected void tearDown() throws Exception {
        manager = null;
        super.tearDown();
    }

    public void testGetExpressions() {
        String expression = "#,###";
        String example = "1,234";
        this.assertEquals(manager.getExample("Long",expression),example);

    }

}
