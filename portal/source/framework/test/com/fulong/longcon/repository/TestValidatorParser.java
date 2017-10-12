package com.fulong.longcon.repository;

import junit.framework.TestCase;
import com.fulong.longcon.BasicTestCase;
import com.fulong.longcon.repository.ValidatorParser;
import com.fulong.longcon.repository.Validator;

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
public class TestValidatorParser extends RepositoryTestCase {
    private NodeDefinition definition;
    protected void setUp() throws Exception {
        super.setUp();
        this.definition = this.repository.getDefinitionManager().getDefinition("user-scheme");
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }


}
