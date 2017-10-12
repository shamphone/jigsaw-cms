package com.fulong.longcon.security;

import java.util.Date;
import com.fulong.longcon.dict.Entry;

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
public class TestUser extends PassportTestCase {
    private User user;
    private String username;
    private String password;
    protected void setUp() throws Exception {
        super.setUp();
        username = "username-test" + new Date();
        password = "password";
        user = this.provider.createUser(username, password);
    }

    protected void tearDown() throws Exception {
        this.provider.delete(user);
        super.tearDown();
    }

    /**
     * 测试用户名,用户不能更改用户名，但是可以根据用户名来找到这个用户．
     * @throws Exception
     */
    public void testUserName() throws Exception {
        this.assertEquals(username, user.getUsername());
        //可以根据用户名来找到这个用户
        this.assertEquals(user, this.provider.getUser(username));

        String question = "question";
        String answer = "answer";
        this.assertFalse(this.user.checkQuestion(question, answer));
    }



    /**
     * 测试密码和修改密码
     * @throws Exception
     */
    public void testPassword() throws Exception {
        String newPassword = "new-password";
        this.assertTrue(user.checkPassword(password));
        this.assertFalse(user.checkPassword(newPassword));
        user.changePassword(newPassword);
        this.assertTrue(user.checkPassword(newPassword));
        this.assertFalse(user.checkPassword(password));
    }



    /**
     * 设置地区,确认设置生效
     * @throws Exception

    public void testArea() throws Exception {
        //缺省地区为未知地区.
        Entry region = this.dictManager.getEntry("999999");
        this.assertEquals(this.user.getArea().getID(), region.getID());
        //设置地区;
        region = this.dictManager.getEntry("067400");
        this.user.setArea(region);
        //确认设置生效
        this.assertEquals(region, this.user.getArea());
        String IP = "192.168.0.81";
        this.user.setIP(IP);
        this.assertEquals(this.user.getIP(), IP);
    }
*/
}
