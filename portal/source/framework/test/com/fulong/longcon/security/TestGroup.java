package com.fulong.longcon.security;

import com.fulong.longcon.workflow.ProcessDefinition;
import java.util.Date;
import com.fulong.common.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * <p>Title: 龙驭论坛核心引擎</p>
 *
 * <p>Description: 龙驭论坛核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lixf
 * @version 2.0
 */
public class TestGroup extends PassportTestCase {
    private Organization organization;
    private User creator;
    private Group group;
    protected void setUp() throws Exception {
        super.setUp();
        this.creator = this.provider.createUser("username-test" + new Date(),
                                                "password");
        this.organization = this.provider.createOrganization("test.name",
            creator);
        this.group = this.provider.getRootGroup().
            addGroup("test.group.name", this.creator, organization);
        //标准工作流
    }

    protected void tearDown() throws Exception {
        this.provider.delete(group);
        this.provider.delete(organization);
        this.provider.delete(creator);
        super.tearDown();
    }

    /**
     * 测试添加子组
     * @throws Exception
     */
    public void testChildrenGroup() throws Exception {
        this.assertEquals(this.group.getOrganization(), this.organization);
        this.assertEquals(this.group.getMemberType(), 0);
        this.assertEquals(this.group.getParentGroup(),
                          this.provider.getRootGroup());

        this.group.setCommonname("commonname");
        this.assertEquals(this.provider.getGroup(this.group.getID()).
                          getCommonname(), "commonname");

//        this.assertEquals(this.group.getAdministrator(), this.creator);

        this.assertEquals(this.group.getDefaulPoints(), 0);

        this.assertEquals(this.group.getDefaultPeriod(), new Duration("0d"));

        this.group.setDefaultPeriod(5, "y");
        Duration dura = new Duration(5, 'y');
        this.assertEquals(this.group.getDefaultPeriod(), dura);

//        this.assertEquals(this.group.getAdministrator(), this.creator);

        String description = "description";
        this.group.setDescription(description);
        this.assertEquals(this.provider.getGroup(this.group.getID()).
                          getDescription(), description);
        Group child = group.addGroup("chilren group");
        //组类型是一致的
        this.assertEquals(child.getGroupType(), group.getGroupType());
        //是子组
        this.assertEquals(group.children().next(), child);
        //新建组的父组是正确的
        this.assertEquals(child.getParentGroup(), group);
//        this.assertEquals(child.getParent(), group);
        this.assertTrue(child.isChild(group));

        this.provider.delete(child);
    }

    /**
     * 测试组名
     * @throws Exception
     */
    public void testGroupName() throws Exception {
        String groupName = "new group name";
        this.group.setCommonname(groupName);
        //确认设置成功.
        this.assertEquals(group.getCommonname(), groupName);
        this.assertEquals(this.provider.getGroup(group.getID()), group);
    }

    /**
     * 测试简单的创建Membership
     * @throws Exception
     */
    public void testAddMember() throws Exception {

        User member = this.provider.createUser("group member" + new Date(),
                                               "password");
        this.group.addMember(member);
        //Membership membership = (Membership)this.group.getAllMemberships(member).membershipIterator().next();
        // membership.setState(process.getActivity("end"));
        //addMember后，并没有设置这个membership是end状态，而后面的判断都是需要在end状态下
        //所以，都出错了。
        this.assertEquals(group.getMemberCount(), 1);
        this.assertEquals(group.getAllMemberCount(), 1);

        this.assertEquals(group.members().nextElement(), member);
        this.assertTrue(this.group.isMember(member));

        this.assertEquals(group.members().nextElement(), member);
        this.assertTrue(group.isMember(member));
//        Membership membership = this.group.getMembership(member);
//        this.assertEquals(this.group.getMembership(membership.getId()).getId(), membership.getId());

        this.group.clearMember();
        this.assertEquals(group.getMemberCount(), 0);
        this.assertEquals(group.getAllMemberCount(), 0);

         this.group.addMember(member);
         this.assertEquals(group.allMembers().nextElement(), member);
//         membership = this.group.getMembership(member);
 //        this.assertEquals(group.getAllMemberships().iterator().nextMembership().getId(),membership.getId());

         this.group.clearMember();
       this.assertEquals(group.getMemberCount(), 0);
       this.assertEquals(group.getAllMemberCount(), 0);


        //删除数据库记录，消除副作用。
        this.provider.delete(member);


    }


    public void testAddOrgMember() throws Exception {
     Organization member = this.provider.createOrganization("group member" + new Date(), this.creator);
     this.group.addMember(member);
     //Membership membership = (Membership)this.group.getAllMemberships(member).membershipIterator().next();
     // membership.setState(process.getActivity("end"));
     //addMember后，并没有设置这个membership是end状态，而后面的判断都是需要在end状态下
     //所以，都出错了。
     this.assertEquals(group.getMemberCount(), 1);
     this.assertEquals(group.getAllMemberCount(), 1);

     this.assertEquals(group.members().nextElement(), member);
//     this.assertNotNull(this.group.getMembership(member));

     this.assertEquals(group.members().nextElement(), member);
     this.assertTrue(group.isMember(member));
//     Membership membership = this.group.getMembership(member);
//     this.assertEquals(this.group.getMembership(membership.getId()).getId(), membership.getId());

     this.group.clearMember();
     this.assertEquals(group.getMemberCount(), 0);
     this.assertEquals(group.getAllMemberCount(), 0);

      this.group.addMember(member);
      this.assertEquals(group.allMembers().nextElement(), member);
//      membership = this.group.getMembership(member);
//      this.assertEquals(group.getAllMemberships().iterator().nextMembership().getId(),membership.getId());

      this.group.clearMember();
    this.assertEquals(group.getMemberCount(), 0);
    this.assertEquals(group.getAllMemberCount(), 0);


     //删除数据库记录，消除副作用。
     this.provider.delete(member);
 }


    public void testSetParent() throws Exception {
        this.group.setDefaultPoints(5);
        this.assertEquals(this.group.getDefaulPoints(), 5);
        this.assertTrue(this.group.isRoot());
        Group parent = this.provider.getRootGroup().
            addGroup("parent", this.creator, organization);
        this.group.setParentGroup(parent);
        this.assertEquals(this.group.getParentGroup(), parent);

//        MembershipCollection mc = parent.getAllMemberships(this.group);
//        this.assertEquals(mc.iterator().nextMembership().getMember().getName(), this.group.getName());

        Iterator groups = parent.getAffiliatedGroups(this.group);
        this.assertEquals(((Group)groups.next()).getID(), parent.getID());

        this.provider.delete(parent);
    }
     public void testBuilder() throws Exception {
         Group root = this.provider.getRootGroup();
         GroupIterator gi = root.children();
/*
         List groups = new ArrayList();
         while (gi.hasNext()) {
                 Group group = root.getGroup(gi.nextGroup().getId()).getGroup(
                     organization);
                 if (group != null) {
                     groups.add(new GroupTreeBuilder(group, false).build());
                 }
             }
 */
     }

    /**
     * 获取机构所有的根组
     * @throws Exception
     */
    public void testGetRootGroupsForOrg() throws Exception{
    Group root = this.provider.getRootGroup();
      GroupIterator gi = root.children();
      Organization org = this.provider.getDefaultOrganization();
      List groups = new ArrayList();
      if (org != null) {
          while (gi.hasNext()) {
              Group group = gi.nextGroup();
              if (group != null) {
                  groups.add(new GroupTreeBuilder(group, false).build());
              }
          }
        }
}
    /**
     * 测试添加membership
     * @throws Exception

         public void testCreateMembership() throws Exception {
        //创建一个空的membership；
        Membership membership = group.createMembership();
        this.assertNull(membership.getMember());
        this.assertEquals(membership.getGroup(), group);
        this.assertEquals(membership.getId(),
                          group.getMembership(membership.getId()).getId());
        //将membership授予某人
        User member = this.provider.createUser("group member", "password");
        membership.setMember(member);
        this.assertEquals(membership.getMember(), member);
        //删除测试记录，消除数据副作用
        this.provider.delete(member);
        this.provider.delete(membership);
         }
     */
    /**
     * 测试在子组中增加用户
     * @throws Exception

         public void testAddMemberInChildren() throws Exception {
        Group sub = this.organization.createGroup("sub group", group);
        User member = this.provider.createUser("group member", "password");
        sub.addMember(member);
        Membership membership = sub.getMembership(member);
        //子组的用户算到父组

        this.assertEquals(group.getMemberCount(), 0);
        this.assertEquals(group.getAllMemberCount(), 1);
     this.assertEquals(group.getMemberships().membershipIterator().getSize(),
                          0);
        this.assertEquals(group.getAllMemberships().membershipIterator().
                          nextMembership().getId(), membership.getId());
        this.assertEquals(group.allMembers().nextElement(), member);
        this.assertEquals(group.allUsers().nextElement(), member);
        this.assertTrue(group.isMember(member));
        this.provider.delete(sub);
        this.provider.delete(member);
        //删除组,也删除了所有和组相关的信息
        this.assertEquals(group.getAllMemberCount(), 0);
        this.assertEquals(group.getAllMemberships(member).membershipIterator().
                          getSize(), 0);

        this.provider.delete(sub);
        this.provider.delete(member);
        this.provider.delete(membership);
         }
     */
}
