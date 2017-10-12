package com.fulong.longcon.security;

import com.fulong.longcon.dict.Entry;
import java.util.Date;

/**
 *
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
public class TestOrganization extends PassportTestCase {
    private Organization organization;
    private User creator;

    protected void setUp() throws Exception {
        super.setUp();
        this.creator = this.provider.createUser("username-test" + new Date(),
                                                "password");
        this.organization = this.provider.createOrganization("test.name",
            creator);
    }

    protected void tearDown() throws Exception {
        this.provider.delete(organization);
        this.provider.delete(creator);
        super.tearDown();
    }

  

    /**
     * 设置机构名称,机构名称同时放在org-enterprisename的属性中
     * @throws Exception
     */
    public void testOrganizationName() throws Exception {
        String newName = this.getTestString("org.name");
        this.organization.setEnterpriseName(newName);
        this.assertEquals(newName, this.organization.getEnterpriseName());
        //可以通过企业名称找到这个机构。
        this.assertEquals(this.organization,
                          this.provider.getOrganization(newName));
        //放到属性中
        this.assertEquals(this.organization.getProperty("org-enterprisename").
                          getString(), newName);
    }

    /**
     * 设置机构描述,支持大字段的描述
     * @throws Exception
     */
    public void testDescription() throws Exception {
        String description = "org.description1";
        /*
         description = description + this.getTestString("org.description1");
         description = description + this.getTestString("org.description2");
         description = description + this.getTestString("org.description3");
         description = description + this.getTestString("org.description4");
         */
        this.organization.setDescription(description);
        this.assertEquals(description, this.organization.getDescription());
        this.assertEquals(description,
                          this.organization.getProperty("org-description").
                          getString());
    }

    /**
     * 设置机构描述,支持大字段的描述
     * @throws Exception
     */
    public void testBussinessDescription() throws Exception {
        String description = "";
        description = description + this.getTestString("org.description1");
        description = description + this.getTestString("org.description2");
        description = description + this.getTestString("org.description3");
        description = description + this.getTestString("org.description4");
        this.organization.setBusinessDescription(description);
        this.assertEquals(description, this.organization.getBusinessDescription());
        this.assertEquals(description,
                          this.organization.getProperty(
                              "org-businessdescription").getString());
    }

    /**
     * 设置地区,确认设置生效
     * @throws Exception

    public void testArea() throws Exception {
        //缺省地区为未知地区.
        Entry region = this.dictManager.getRegionEntry().getEntry("999999");
        this.assertEquals(this.organization.getArea(), region);
        //设置地区;
        region = this.dictManager.getRegionEntry().getEntry("067400");
        this.organization.setArea(region);
        //确认设置生效
        this.assertEquals(region, this.organization.getArea());
    }
*/
    /**
     * 测试设置行业编码
     * @throws Exception
     */
  /*
     public void testSiccode() throws Exception {
        //初始化为空
        this.assertEquals(this.organization.getSiccodes().length, 0);
        Entry siccode2433 = this.dictManager.getEntry("5890id");
        Entry[] siccodes = new Entry[1];
        siccodes[0] = siccode2433;
        //设置行业编码
        this.organization.setSiccodes(siccodes);
        //确认设置结果
        this.assertEquals(this.organization.getSiccodes()[0], siccode2433);
        //修改行业编码
        Entry siccode2440 = this.dictManager.getEntry("5910id");
        siccodes[0] = siccode2440;
        this.organization.setSiccodes(siccodes);
        //确认修改结果
        this.assertEquals(this.organization.getSiccodes()[0], siccode2440);
    }
*/
    /**
     * 创建根组
     * @throws Exception
     */
    public void testCreateRootGroup() throws Exception {
        String type = "test.group";
        //根据类型标识来创建组
        Group group = this.provider.getRootGroup().addGroup(
            "Test group", this.creator, this.organization);
        //确认根组被创建

//        this.assertEquals(this.provider.getRootGroup().getGroup(organization), group);
        this.provider.delete(group);
        //确认删除
//        this.assertNull(this.provider.getRootGroup().getGroup(organization));
    }

    /**
     * 创建组.可以在根组下创建一个组.
     * @throws Exception
     */
    public void testCreateGroup() throws Exception {
        //根据类型标识来创建组
        Group root = this.provider.getRootGroup().addGroup(
            "Test group root", this.creator, this.organization);
        Group group = root.addGroup("Test group");
        this.assertEquals(group.getParentGroup(), root);
        this.assertEquals(root.children().nextGroup(), group);
        //子组的类型和根组是一样的
        this.assertEquals(group.getGroupType(), root.getGroupType());
        //删除
        this.provider.delete(root);
        //根组被删除,则根组以下的组都可以被删除
//        this.assertNull(this.provider.getRootGroup().getGroup(organization));
        this.assertNull(this.provider.getGroup(group.getID()));

        this.provider.delete(group);

        Group department = this.provider.getGroup("group").addGroup("Test group root", this.creator, this.organization);
//        this.assertEquals(this.organization.getRootDeparement().getId(), department.getId());



    }

}
