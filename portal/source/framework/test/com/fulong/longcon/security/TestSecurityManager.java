package com.fulong.longcon.security;



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
public class TestSecurityManager
    extends PassportTestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * 测试创建用户
     * @throws Exception
     */
    public void testCreateUser() throws Exception {
        String username = "testuser"+System.currentTimeMillis();
        String password = "password";
        //根据用户名和密码创建用户
        User user = this.provider.createUser(username, password);
        this.assertEquals(user.getUsername(), username);
        //根据用户名获取这个用户
        this.assertEquals(user, this.provider.getUser(username));
        //根据ID获取这个用户
        this.assertEquals(user, this.provider.getUser(user.getID()));
        //密码是对的
        this.assertTrue(user.checkPassword(password));
        this.provider.delete(user);
        //删除之后,根据用户名和密码找不到这个用户
        this.assertNull(this.provider.getUser(username));
        this.assertNull(this.provider.getUser(user.getID()));
    }

    /**
     * 测试创建机构
     * @throws Exception
     */
    public void testCreateOrganization() throws Exception{
        String username = "testorg"+System.currentTimeMillis();
        String password = "password";
        String enterpriseName="enterprise name"+System.currentTimeMillis();
        User creator = this.provider.createUser(username, password);
        Organization org=this.provider.createOrganization(enterpriseName, creator);
       //机构名字被正确设置
        this.assertEquals(org.getEnterpriseName(),enterpriseName);
        //根据机构ID和企业名称都可以找到这个机构。
        this.assertEquals(this.provider.getOrganization(org.getID()),org);
        this.assertEquals(this.provider.getOrganization(enterpriseName),org);

        this.provider.delete(org);
        this.provider.delete(creator);
    }



    /**
     * 测试修改密码
     * @throws Exception
     */
    public void testChangePassword() throws Exception {
        String username = "testuser"+System.currentTimeMillis();
        String password = "password";
        String newPassword="newpassword";
        //根据用户名和密码创建用户
        User user = this.provider.createUser(username, password);
        this.assertTrue(user.checkPassword(password));
        user.changePassword(newPassword);
        this.assertTrue(user.checkPassword(newPassword));
        //清理数据
        this.provider.delete(user);
    }


    /**
     * 推荐机构
     * @throws Exception
     */
    public void testRecommendOrganizations() throws Exception {
        String username = "testuser"+System.currentTimeMillis();
        String password = "password";
        //根据用户名和密码创建用户,创建企业
        User user = this.provider.createUser(username, password);
        Organization org = this.provider.createOrganization("test.org.name",
            user);
        //将企业增加到推荐组里
        Group recommend = this.provider.getRootGroup().addGroup(
            "test.recommends");
        recommend.addMember(org);
        //推荐组里有一个推荐企业
        this.assertEquals(recommend.getMemberships().iterator().getSize(), 1);

        //删除推荐企业
        recommend.removeMember(org);
        this.assertEquals(recommend.getMemberships().iterator().getSize(), 0);
        //清理数据
        this.provider.delete(recommend);
        this.provider.delete(org);
        this.provider.delete(user);
    }




}
