package com.fulong.longcon.site;

import com.fulong.common.util.RangeIterator;
import com.fulong.longcon.site.impl.SiteCollectionImpl;
import com.fulong.longcon.site.ext.SiteFactoryExt;
import java.util.Date;
import java.util.Iterator;
import java.security.Principal;
import com.fulong.longcon.security.User;
import com.fulong.longcon.security.Organization;
import java.util.Calendar;
import com.fulong.longcon.security.Group;
import com.fulong.longcon.security.Membership;
import java.awt.Robot;

/**
 *  SiteCollection接口完全测试
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author lishaobo
 * @version 2.0
 */
public class TestSiteCollection
    extends SiteTestCase {
    private SiteCollection siteCollectionImpl = null;
    private SiteCategory category = null;
    private SiteTemplate template = null;
    private Principal creator = null;
    private Principal org = null;
    private String name;
    protected void setUp() throws Exception {
        super.setUp();
        siteCollectionImpl = new SiteCollectionImpl( (SiteFactoryExt)this.
            factory);

        category = this.factory.createCategory(this.getTestString(
            "create.site.category"));
        String templateName = "templateName" + new Date().getTime();
        template = this.factory.createTemplate(templateName,
                                               category);

        //创建用户和机构
        String userName = "test" + new Date().getTime();
        String password = "123456";
        creator = provider.createUser(userName, password);
        org = provider.createOrganization(this.getTestString(
            "create.site.org.name"), creator);
        name = this.getTestString("create.site.name") +
            new Date().getTime();

    }

    protected void tearDown() throws Exception {
        this.factory.delete(template);
        this.factory.delete(category);
        this.provider.delete( (User) creator);
        this.provider.delete( (Organization) org);
        siteCollectionImpl = null;
        super.tearDown();
    }

    /**
     * 测试 按照网站类别过滤 搜索
     */
    public void testFilterByCategory() {
        //创建一个网站
        Site site = this.factory.createSite(name, org, template);
        //按照当前类别过滤
        siteCollectionImpl.filterByCategory(category);
        //搜索结果集
        RangeIterator<Site> actualReturn = siteCollectionImpl.iterator();
        //搜索结果为当前创建网站
        this.assertEquals(1, actualReturn.getSize());
        this.assertEquals(site, actualReturn.next());
        //删除测试用的网站
        this.factory.delete(site);
    }

    /**
     * 测试 按照网站模板过滤 搜索   *
     */
    public void testFilterByTemplate() {
        //创建一个网站
        Site site = this.factory.createSite(name, org, template);
        //按照当前模板过滤
        siteCollectionImpl.filterByTemplate(template);
        //搜过结果集
        RangeIterator<Site> actualReturn = siteCollectionImpl.iterator();
        //搜索结果为当前创建网站
        this.assertEquals(1, actualReturn.getSize());
        this.assertEquals(site, actualReturn.next());
        //删除测试用的网站
        this.factory.delete(site);
    }

    /**
     * 测试 按照创建时间过滤 搜索
     */
    public void testFilterByCreatedDate() {
        //创建一个网站
        Site site = this.factory.createSite(name, org, template);
        //今天
        Date today = new Date();
        //明天
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.set(Calendar.DATE, cal.get(Calendar.DAY_OF_MONTH) + 1);
        //在今天和明天之间搜索
        siteCollectionImpl.filterByCreatedDate(today, cal.getTime());
        //当前分类过滤，测试搜索结果为当前网站
        siteCollectionImpl.filterByCategory(this.category);
        //搜索结果集
        RangeIterator<Site> actualReturn = siteCollectionImpl.iterator();
        //搜索结果为当前创建网站
        this.assertEquals(1, actualReturn.getSize());
        this.assertEquals(site, actualReturn.next());
        //删除测试用的网站
        this.factory.delete(site);
    }

    /**
     * 测试 按照显示名称过滤 搜索
     */
    public void testFilterByName() {
        //创建一个网站
        Site site = this.factory.createSite(name, org, template);
        //网站的显示名称
        String displayName = "XXXX股份有限责任公司（XX分公司）";
        //设置网站的显示名称
        site.setDisplayName(displayName);
        //网站的显示名称过滤
        siteCollectionImpl.filterByName(displayName);
        //搜索结果集
        RangeIterator<Site> actualReturn = siteCollectionImpl.iterator();
        //搜索结果为当前创建网站
        this.assertEquals(1, actualReturn.getSize());
        this.assertEquals(site, actualReturn.next());
        //删除测试用的网站
        this.factory.delete(site);
    }

    /**
     *测试  按照域名过滤 搜索
     * 支持域名 ，网站名称
     */
    public void testFilterByDomain() {
        //网站的名称
        name = "my1site";
        //创建一个网站
        Site site = this.factory.createSite(name, org, template);
        //网站的域名,说明:在创建一个网站的时候,domain = name + getDefaultDomain()
        String domain = name + this.factory.getDefaultDomain();
        //网站的名称过滤
        siteCollectionImpl.filterByDomain(domain);
        //搜索结果集
        RangeIterator<Site> actualReturn = siteCollectionImpl.iterator();
        //搜索结果为当前创建网站
        this.assertEquals(1, actualReturn.getSize());
        this.assertEquals(site, actualReturn.next());
        //删除测试用的网站
        this.factory.delete(site);

    }

    /**
     * 测试 按照创建机构来过滤 搜索
     */
    public void testFilterByCreatedOrg() {
        //创建一个网站
        Site site = this.factory.createSite(name, org, template);
        //按照创建机构过滤,注意参数是机构的名称
        siteCollectionImpl.filterByCreatedOrg( ( (Organization) org).
                                              getEnterpriseName());
        //搜索结果集
        RangeIterator<Site> actualReturn = siteCollectionImpl.iterator();
        //搜索结果为当前创建网站
        this.assertEquals(1, actualReturn.getSize());
        this.assertEquals(site, actualReturn.next());
        //删除测试用的网站
        this.factory.delete(site);
    }

    /**
     * 测试 根据状态过滤 搜索
     */
    public void testFilterByState() {
        //创建一个网站
        Site site = this.factory.createSite(name, org, template);
        //状态
        String state = SiteObject.State.STOP + ".test";
        //设置状态
        site.setState(state);
        //按照状态过滤
        siteCollectionImpl.filterByState(state);
        //搜索结果集
        RangeIterator<Site> actualReturn = siteCollectionImpl.iterator();
        //搜索结果为当前创建网站
        this.assertEquals(1, actualReturn.getSize());
        this.assertEquals(site, actualReturn.next());
        //删除测试用的网站
        this.factory.delete(site);
    }

    /**
     * 测试 根据会员卡号过滤 搜索
     */
    public void testFilterByMembership() {
        //创建一个网站
        Site site = this.factory.createSite(name, org, template);
        //创建一个组
        Group group =this.provider.getRootGroup().addGroup("test.site.group");
        //网站分类所属的组
        category.setGroup(group);
        //组成员,卡号
//        Membership mem = group.createMembership(org);
        //按照卡号过滤
//        siteCollectionImpl.filterByMembership(mem.getId());
        //搜索结果集
        RangeIterator<Site> actualReturn = siteCollectionImpl.iterator();
        //搜索结果为当前创建网站
        this.assertEquals(1, actualReturn.getSize());
        this.assertEquals(site, actualReturn.next());
        //删除测试用的网站
        this.factory.delete(site);
    }

    /**
     * 按照关键字过滤 搜索
     * 支持网站名称,显示名称,域名,网站的机构名称
     */
    public void testFilterByKeyWord() {
        //创建一个网站
        Site site = this.factory.createSite(name, org, template);
        //机构名称
        String orgName = ( (Organization) org).getEnterpriseName();
        //按照关键字(机构名称)过滤
        siteCollectionImpl.filterByKeyWord(orgName);
        //搜索结果集
        RangeIterator<Site> actualReturn = siteCollectionImpl.iterator();
        //搜索结果为当前创建网站
        this.assertEquals(1, actualReturn.getSize());
        this.assertEquals(site, actualReturn.next());
        //删除测试用的网站
        this.factory.delete(site);
    }

    /**
     * 按照创建日期排序
     * 在一个分类下创建2个网站,按照创建时间排序取出网站进行判断
     */
    public void testSortByCreatedDate() {
        boolean asc = false;
        //创建第一个网站
        Site site1 = this.factory.createSite(name + "1", org, template);
        //系统延时 一秒钟,以区分这两个网站的创建时间,为了在排序的时候能区分创建的先后顺序
        try {
            Robot robot = new Robot();
            //延时一秒
            robot.delay(1000);
        }
        catch (Exception e) {
            //
        }
        //创建第二个网站
        Site site2 = this.factory.createSite(name + "2", creator, template);
        //当前分类过滤
        siteCollectionImpl.filterByCategory(category);
        //按照创建时间的倒序排
        siteCollectionImpl.sortByCreatedDate(asc);
        //结果集
        RangeIterator<Site> actualReturn = siteCollectionImpl.iterator();
        Site result = actualReturn.next();
        this.assertEquals(site2.getID(), result.getID());

        siteCollectionImpl = new SiteCollectionImpl( (SiteFactoryExt)this.
            factory);
        asc = true;
        //当前分类过滤
        siteCollectionImpl.filterByCategory(category);
        //按照创建时间排序
        siteCollectionImpl.sortByCreatedDate(asc);
        //结果集
        actualReturn = siteCollectionImpl.iterator();
        this.assertEquals(site1.getID(), actualReturn.next().getID());

        this.factory.delete(site1);
        this.factory.delete(site2);
    }

    /**
     * 按照最后修改日期排序
     * 最后修改时间,目前用生效时间
     * 查看打印结果
     */
    public void testSortByRevisionDate() {
        boolean asc = false;
        siteCollectionImpl.sortByRevisionDate(asc);
        //结果集
        RangeIterator<Site> actualReturn = siteCollectionImpl.iterator();
        System.out.println();
        while (actualReturn.hasNext()) {
            Site site = actualReturn.next();
            System.out.println( ("---Site name =" + site.getName() + " ;" +
                                 "START_DATE = ") + site.getStartDate());
        }
    }

}
