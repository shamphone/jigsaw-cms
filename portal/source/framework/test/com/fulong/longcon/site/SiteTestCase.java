package com.fulong.longcon.site;

import com.fulong.longcon.BasicTestCase;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.repository.RepositoryTestCase;

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
 * @author 李雄锋
 * @version 1.0
 */
public class SiteTestCase extends RepositoryTestCase {
    protected SiteFactory factory = null;
    protected Repository repository = null;

    protected void setUp() throws Exception {
        super.setUp();

        this.factory = (SiteFactory)this.beanFactory.getBean("siteFactory");
        this.repository =(Repository)this.beanFactory.getBean("repository");
    }

    protected void tearDown() throws Exception {
        factory = null;
        super.tearDown();
    }


}
