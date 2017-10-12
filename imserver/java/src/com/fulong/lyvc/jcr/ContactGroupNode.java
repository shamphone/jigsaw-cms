package com.fulong.lyvc.jcr;

import java.security.Principal;
import java.util.Collection;
import java.util.Iterator;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.Repository;
import com.fulong.lyvc.Group;
import com.fulong.lyvc.User;
import com.fulong.lyvc.util.PropertyConstant;
import com.fulong.lyvc.util.SchemeConstant;

/**
 * ContactGroupNode
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-8-6
 */

public class ContactGroupNode extends GroupNode {
	
	private static final String MEMBERS = "member";				//组成员名称
//	private static final String SUBGROUP = "xjbm";		//子组名称
	
	public ContactGroupNode(Node node) {
		super(node);
	}
	
	/**
	 * 该方法用来添加子联系人组，忽略creatorId
	 *
	 */
	public Group addChild(String name, String desc, String creatorId) {
		boolean flag = true;
		
		//先判断是否存在重名的子组
		Collection<? extends Group> groups = children();
		Iterator<? extends Group> iterator = groups.iterator();
		while(iterator.hasNext()) {
			Group group = iterator.next();
			if(name.equals(group.getName())) {
				flag = false;
				break;
			}
		}
		
		if(flag) {
			//子联系人组为其复合属性（是一个节点）
			Node newNode = this.getNode().addNode(this.getNode().getDefinition(), PropertyConstant.groupName);
			ContactGroupNode group = new ContactGroupNode(newNode);
			group.setName(name);
			group.setDesc(desc);
			
			return group;
		}
		
		return null;
	}

	/**
	 * 该方法用来判断是否能添加成员
	 *
	 */
	public boolean canAddMember(Principal principal) {
		//TODO
		boolean flag = true;
		
		//添加的成员不能是该联系组的创建者（也就是不能添加自己为联系人组的成员）
		if(this.getCreator().getId().equals(((User)principal).getId())) {
			flag = false;
		}
		else {
			//联系人组成员中已经存在该用户，则不能添加
			flag = this.isMember(principal);
			flag = !flag;
		}
		
		return flag;
	}

	/**
	 * 该方法用来得到联系人组的创建者
	 *
	 */
	public User getCreator() {
		User user = null;
		Node creatorNode = this.getNode().getParent();
		if(creatorNode != null)
			user = new UserNode(creatorNode);
		
		return user;
	}

	/**
	 * 该方法用来得到联系人组的管理员
	 *
	 */
	public User getManager() {
		return getCreator();
	}

	/**
	 * 该方法用来得到联系人组的父联系人组
	 *
	 */
	public Group getParentGroup() {
		ContactGroupNode group = null;
		
		Node parentNode = this.getNode().getParent();
		
		//判断返回的节点类型是否是ContactGroupNode类型
		boolean flag = parentNode.getDefinition().isNodeType(SchemeConstant.contactGroupScheme);
		
		if(flag)
			group = new ContactGroupNode(parentNode);
		
		return group;
	}

	/**
	 * 该方法用来判断一个用户是否在一个联系人组中第一层的所有成员中
	 *
	 */
	public boolean isMember(Principal member){
		boolean flag = false;
		
		//以下两种方法都可以
		
		//获取所有的成员，然后判断该成员是否在里面
//		String[] members = this.getNode().getProperty(MEMBERS).getArray();
//		for(String user : members) {
//			String id = ((User)member).getId();
//			if(user.equals(id)) {
//				flag = true;
//				break;
//			}
//		}
		
		//使用查询构造器进行判断
		Repository repository = this.getNode().getRepository();
		Query query = repository.getQueryManager().createQuery(repository.getDefinitionManager().getDefinition(SchemeConstant.contactGroupScheme), Query.SQL);
		query.filterByProperty(MEMBERS, ((User)member).getId());
		NodeIterator<Node> iterator = query.nodes();
		while(iterator.hasNext())  {
			Node node = iterator.next();
			if(this.getNode().getID().equals(node.getID())) {
				flag = true;
				break;
			}
		}
		
		return flag;
	}
}
