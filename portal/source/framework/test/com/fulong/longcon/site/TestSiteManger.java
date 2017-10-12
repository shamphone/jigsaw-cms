package com.fulong.longcon.site;

import com.fulong.longcon.repository.Node;


/**
 * 网站管理测试
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author not attributable
 * @version 2.0
 */
public class TestSiteManger
    extends SiteTestCase {


    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }


    /**
     * 测试 添加网站模板,修改网站模板,删除网站模板
     */
    public void testSite() {
    	Node owner = this.repository.getNode("1000000000");    	
    	//Site site = this.factory.createSite("www.lixf.cn", owner, "abc");
    	//assertEquals(site.getDomain(), "");
    	Site site2 =this.factory.getSite("www.lixf.cn");
    	Site site3 =this.factory.getSite("www.lixf.cn");
    	//this.assertEquals(site, site2);
    	//assertNull(site);
    	this.factory.getSites();
    	//site =this.factory.getSite("www.lixf.cn");
    	//assertNull(site);    	
    	//assertEquals(site.getDomain(),"www.lixf.cn");
    }

}
