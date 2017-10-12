package com.fulong.longcon.site;

import com.fulong.longcon.site.*;
import com.fulong.longcon.repository.*;
import java.util.*;
import java.security.Principal;

/**
 *
 * <p>Title: 龙驭建站系统测试用例</p>
 *
 * <p>Description: 龙驭建站系统测试用例</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author lishaobo
 * @version 2.0
 */
public class TestChannel
    extends SiteTestCase {
    private Channel channelImpl = null;
    private SiteCategory category;
    private SiteTemplate template;

    protected void setUp() throws Exception {
        super.setUp();
        String userName = "test" + new Date().getTime();
        String password = "123456";
        Principal creator = provider.createUser(userName, password);
        Principal org = provider.createOrganization(this.getTestString(
            "create.site.org.name"), creator);

        //将建站的权限授予机构.
        category = this.factory.createCategory(this.getTestString(
            "create.site.category") + new Date().getTime());
        // 建立网站
        String name = this.getTestString("create.site.name");
        template = this.factory.createTemplate(this.getTestString(
            "create.site.template") + new Date().getTime(), category);
        channelImpl = template.getRootChannel();
    }

    protected void tearDown() throws Exception {
        channelImpl = null;
        this.factory.delete(template);
        this.factory.delete(category);
        super.tearDown();
    }

    public void testAddChannel() {
        String name = "news";
        Channel actualReturn = channelImpl.addChannel(name);
        assertEquals("return value", actualReturn.getName(), name);
    }

    public void testGetChildren() {
        String childname = "news";
        Channel child = channelImpl.addChannel(childname);
        Iterator actualReturn = channelImpl.getChildren();
        assertEquals("return value", child, actualReturn.next());
    }

    public void testGetName() {
        String expectedReturn = this.template.getDisplayName() + "rootChannel";
        String actualReturn = channelImpl.getName();
        assertEquals("return value", expectedReturn, actualReturn);
    }


    public void testGetParentChannel() {
        String childname = "news";
        Channel child = channelImpl.addChannel(childname);
        Channel actualReturn = child.getParent();
        assertEquals("return value", channelImpl, actualReturn);
    }

    public void testGetSiteTemplate() {
        SiteTemplate expectedReturn = this.template;
        SiteTemplate actualReturn = channelImpl.getSiteTemplate();
        assertEquals("return value", expectedReturn, actualReturn);
    }

    public void testMove() {
        //构件2个子栏目用于测试
        Channel newsChild = channelImpl.addChannel("news");
        Channel blogChild = channelImpl.addChannel("blog");
        //新闻子栏目被移到博客栏目下
        newsChild.setParent(blogChild);
        assertEquals("return value", blogChild, newsChild.getParent());

        //任何一个栏目不能移到自己的子栏目下
        channelImpl.setParent(blogChild);
        assertEquals("return value", channelImpl, blogChild.getParent());

    }

    public void testSetDisplayName() {
        String name = "国内新闻";
        channelImpl.setDisplayName(name);
        String actualReturn = channelImpl.getDisplayName();
        assertEquals("return value", name, actualReturn);
    }

    public void testSetExpiryDate() {
        Date expiryDate = new Date();
        channelImpl.setExpiryDate(expiryDate);
        Date actualReturn = channelImpl.getExpiryDate();
        assertEquals("return value", expiryDate, actualReturn);
    }


    public void testSetStartDate() {
        Date StartDate = new Date();
        channelImpl.setStartDate(StartDate);
        Date actualReturn = channelImpl.getStartDate();
        assertEquals("return value", StartDate, actualReturn);

    }

    public void testSetState() {
        String state = SiteObject.State.PUBLISHED;
        channelImpl.setState(state);
        String actualReturn = channelImpl.getState();
        assertEquals("return value", state, actualReturn);
    }

}
