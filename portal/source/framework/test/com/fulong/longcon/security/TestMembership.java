package com.fulong.longcon.security;

import com.fulong.longcon.workflow.ProcessDefinition;
import com.fulong.longcon.workflow.Activity;
import java.util.Calendar;
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
public class TestMembership extends PassportTestCase {
    private Organization organization;
    private User creator;
    private User member; //卡用户
    private Group group; //卡所在的组
    private ProcessDefinition process; //卡组的工作流
    private Membership membership; //卡

    protected void setUp() throws Exception {
        super.setUp();
        this.creator = this.provider.createUser("u_test" + new Date(),
                                                "password");
        this.organization = this.provider.createOrganization("test.name",
            creator);

        this.group = this.provider.getRootGroup().addGroup("test.group.name",
            organization);
        //标准工作流
        this.process = this.workflow.getDefinition("standard");
        //this.group.setWorkflow(process);
        this.member = this.provider.createUser("card member" + new Date(),
                                               "password");
//        this.membership = group.createMembership(member);
    }

    protected void tearDown() throws Exception {
        this.provider.delete(group);
        this.provider.delete(organization);
        this.provider.delete(creator);
        this.provider.delete(member);
//        this.provider.delete(membership);
        super.tearDown();
    }


    /**
     * 测试过期状态
     * @throws Exception
     */
    public void testExpiryDate() throws Exception {
        //将卡置于有效状态
        Activity end = process.getActivity("end");
        //将到期日置于第二天,卡未到期,是有效的,用户成为组成员
        Calendar nextDay = Calendar.getInstance();
        nextDay.add(Calendar.DATE, 1);
        membership.setExpiringDate(nextDay.getTime());
        this.assertEquals(nextDay.getTime(), membership.getExpiringDate());
        this.assertEquals(group.members().nextElement(), this.member);
        //将到期日置于前一天,卡已到期,是无效的,用户不是组(正式)成员
        nextDay.add(Calendar.DATE, -2);
        membership.setExpiringDate(nextDay.getTime());
        this.assertFalse(group.members().hasMoreElements());
        this.membership.getApplyDate();
        this.membership.getJoinDate();
    }


/*
    public void testPoints() throws Exception {
        String itemCode = "123456";
        String itemName = "item name";
        //初始积分为0
        this.assertEquals(this.membership.getPoints(), 0);
        //冲值10个积分后，总积分为10
        Transfer transfer = this.membership.createTransfer(10, itemCode,
            itemName);
        transfer.commit();
        this.assertEquals(membership.getPoints(), 10);
        this.assertEquals(transfer.getAccount().getId(), membership.getId());

        this.assertEquals(this.membership.getGroup().getId(), this.group.getId());

        Group group1 = this.provider.getRootGroup().addGroup("group.name",
            organization);
        this.membership.setGroup(group1);
        this.assertEquals(this.membership.getGroup().getId(), group1.getId());
        this.provider.delete(group1);
        String reason = "reason";
        this.membership.setReason(reason);
        this.assertEquals(this.membership.getReason(), reason);

        this.assertNull(this.membership.getValidationCode());
        this.membership.isValid();

    }
*/

}
