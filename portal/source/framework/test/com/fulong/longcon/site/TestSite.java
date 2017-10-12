package com.fulong.longcon.site;

import java.util.Date;
import java.util.Locale;
import java.security.Principal;
import com.fulong.longcon.security.User;
import com.fulong.longcon.security.Organization;

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
public class TestSite
    extends SiteTestCase {
    private Site site = null;
    private SiteCategory category;
    private String templateName;
    private String domain;
    private SiteTemplate template;
    private User creator;
    private Organization org;
    protected void setUp() throws Exception {
        super.setUp();

        //创建用户和机构
        String userName = "test" + new Date().getTime();
        String password = "123456";
        creator = provider.createUser(userName, password);
        org = provider.createOrganization(this.getTestString(
            "create.site.org.name"), creator);

        //将建站的权限授予机构.
        category = this.factory.createCategory(this.getTestString(
            "create.site.category"));

        // 建立网站
        String name = this.getTestString("create.site.name") +
            new Date().getTime();
        template = this.factory.createTemplate(this.getTestString(
            "create.site.template"), category);
        site = this.factory.createSite(name, org, template);

    }

    protected void tearDown() throws Exception {
        this.provider.delete(creator);
        this.provider.delete(org);

        this.factory.delete(site);
        this.factory.delete(template);
        this.factory.delete(category);
        super.tearDown();
    }

    /**
     * 测试添加栏目，注意，用户必须具有网站管理的权限才可以创建栏目。关于权限设置，这里不提供测试代码。
     * @throws Exception
     */
    public void testSetDisplayName() throws Exception {
        String newName = "New Display Name";
        site.setDisplayName(newName);
        this.assertEquals(newName, site.getDisplayName());
    }

    public void testGetCategory() throws Exception {
        this.assertEquals(category, site.getCategory());
        this.assertEquals(template, site.getTemplate());

        SiteTemplate newTemplate = this.factory.createTemplate(
            "gold member site template",
            category);
        site.setTemplate(newTemplate);
        this.assertEquals(newTemplate, site.getTemplate());

        SiteCategory newCategory = this.factory.createCategory(
            "gold member site category");
        site.setCategory(newCategory);
        this.assertEquals(newCategory, site.getCategory());

        this.factory.delete(newCategory);
        this.factory.delete(newTemplate);

    }

}
