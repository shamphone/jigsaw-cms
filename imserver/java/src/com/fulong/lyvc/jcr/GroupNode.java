/**
 * 
 */
package com.fulong.lyvc.jcr;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.NodeWrapper;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.Repository;
import com.fulong.lyvc.Group;
import com.fulong.lyvc.User;
import com.fulong.lyvc.util.PropertyConstant;
import com.fulong.lyvc.util.SchemeConstant;

/**
 * GroupNode
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-8-6
 */

public class GroupNode extends NodeWrapper implements Group {
	
	private static final String NAME = "title";						//组名称
	private static final String DESC = "desc";						//组描述
	private static final String MEMBERS = "member";				//组成员名称
//	private static final String SUBGROUP = "xjbm";				//子组名称
	private static final String GROUPNAME = "user-commonname";		//组名称
	
	public GroupNode(Node node) {
		super(node);
	}

	/**
	 * 判断组中是否能添加成员
	 * 
	 * @return
	 */
	public boolean canAddMember(Principal principal) {
		return false;
	}

	/**
	 * 获取组的创建者
	 * 
	 * @return
	 */
	public User getCreator() {
		return null;
	}

	/**
	 * 获取组的描述
	 * 
	 * @return
	 */
	public String getDesc() {
		String desc = null;
		
		Property property = this.getNode().getProperty(DESC);
		if(property != null)
			desc = property.getString();
		
		return desc;
	}

	/**
	 * 获取唯一标识
	 * 
	 * @return
	 */
	public String getId() {
		return this.getNode().getID();
	}

	/**
	 * 获取组的管理员
	 * 
	 * @return
	 */
	public User getManager() {
		return null;
	}

	/**
	 * 获取组的名称
	 * 
	 * @return
	 */
	public String getName() {
		String name = null;
		
		Property property = this.getNode().getProperty(NAME);
		if(property != null) {
			name = property.getString(); 
		}
		else {
			property = this.getNode().getProperty(GROUPNAME);
			if(property != null)
				name = property.getString();
		}
		
		return name;
	}

	/**
	 * 设置组的描述
	 * 
	 * @return
	 */
	public void setDesc(String desc) {
		Property property = this.getNode().getProperty(DESC);
		if(property != null)
			property.setValue(desc);
	}

	/**
	 * 设置组的名称
	 * 
	 * @return
	 */
	public void setName(String name) {
		Property property = this.getNode().getProperty(NAME);
		if(property != null)
			property.setValue(name);
	}

	/**
	 * 该方法用来向组中添加用户
	 *
	 */
	public boolean addMember(Principal user) {
		boolean flag = this.canAddMember(user);
		
		if(flag) {
			//用户是组的引用属性
			Property property = this.getNode().getProperty(MEMBERS);
			if(property != null) {
				String[] users = property.getArray();
				String[] newUsers = new String[users.length+1];
				System.arraycopy(users, 0, newUsers, 0, users.length);
				newUsers[newUsers.length-1] = ((User)user).getId();
				property.setValue(newUsers);
			}
		}

		return flag;
	}

	/**
	 * 判断一个用户是否是组中的成员
	 * 
	 * @return
	 */
	public boolean isMember(Principal member) {
		return false;
	}

	/**
	 * 该方法用来删除组中的某个成员
	 *
	 */
	public boolean removeMember(Principal user) {
		//先判断要删除的用户是否存在
		boolean flag = isMember(user);
		
		if(flag) {
			//所有成员都是联系组中的引用属性（可以有多个值）
			Property property = this.getNode().getProperty(MEMBERS);
			if(property != null) {
				String[] members = property.getArray();
				
				if(members.length > 1) {
					String[] newMembers = new String[members.length-1];
					
					int k = 0;
					String userId = ((User)user).getId();
					for(String member : members) {
						if(!member.equals(userId)) 
							newMembers[k++] = member;
					}
					
					property.setValue(newMembers);
				}
				else if(members.length == 1){
					property.setValue((String)null);
				}
			}
			else {
				flag = false;
			}
		}

		return flag;
	}

	/**
	 * 该方法用来得到组中所有的用户
	 *
	 */
	public Enumeration<? extends Principal> members() {
		Collection<? extends User> users = this.users();
		
		if(users != null)
			return Collections.enumeration(users());
		
		return null;
	}
	
	/**
	 * 该方法用来得到所有的第一层的子组
	 *
	 */
	public Collection<? extends Group> children() {
		ArrayList<Group> children = new ArrayList<Group>();
		
		boolean flag = this.getNode().getDefinition().isNodeType(SchemeConstant.contactGroupScheme);
		
		//得到组下的第一层的所有子组，子组为其子节点（复合属性）
		NodeIterator<Node> iterator = this.getNode().getNodes(PropertyConstant.groupName);
		while(iterator.hasNext()) {
			if(flag)
				children.add(new ContactGroupNode(iterator.next()));
			else
				children.add(new SystemGroupNode(iterator.next()));
		}
		
		return children;
	}
	
	/**
	 * 该方法用来得到组中所有的用户
	 *
	 */
	public Collection<? extends User> users() {
		Vector<User> users = new Vector<User>();
		
		//得到组中的第一层的所有成员，这些成员都是其属性（引用属性）
		Property property = this.getNode().getProperty(MEMBERS);
		if(property != null) {
			String[] members = property.getArray();
			
			Repository repository = this.getNode().getRepository();
			for(String member : members)
				users.add(new UserNode(repository.getNode(member)));
		}
		
		return users;
	}

	/**
	 * 该方法用来添加一个子组
	 *
	 */
	public Group addChild(String name, String desc, String creatorId) {
		return null;
	}
	
	/**
	 * 该方法用来得到该组的父节点
	 *
	 */
	public Node getParent() {
		return this.getNode().getParent();
	}

	/**
	 * 该方法用来得到该组的父组
	 *
	 */
	public Group getParentGroup() {
		return null;
	}

	/**
	 * 该方法用来设置该组的父组
	 *
	 */
	public void setParentGroup(Group parentGroup) {
		this.getNode().setParent((Node) parentGroup);
	}

	@Override
	public void setMaxOrderNo(int orderNo) {
		// TODO Auto-generated method stub
		
	}
}
