/**
 * 
 */
package com.fulong.lyvc.jcr;

import java.security.Principal;
import java.util.Collection;
import java.util.Iterator;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.Repository;
import com.fulong.lyvc.Group;
import com.fulong.lyvc.User;
import com.fulong.lyvc.util.PropertyConstant;
import com.fulong.lyvc.util.SchemeConstant;

/**
 * SystemGroupNode
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-8-6
 */

public class SystemGroupNode extends GroupNode {
	
	private static final String CREATORID = "creatorId";			//组创建者名称
	private static final String MEMBERS = "member";					//组成员名称
//	private static final String SUBGROUP = "xjbm";				//子组名称

	public SystemGroupNode(Node node) {
		super(node);
	}
	
	/**
	 * 该方法用来判断是否能添加一个联系人
	 *
	 */
	public boolean canAddMember(Principal principal) {
		return !isMember(principal);
	}

	/**
	 * 该方法用来向公共联系人组中添加一个子组
	 *
	 */
	public Group addChild(String name, String desc, String creatorId) {
		boolean flag = true;
		
		//先判断是否存在重名的子组
		Collection<? extends Group> groups = this.children();
		Iterator<? extends Group> iterator = groups.iterator();
		while(iterator.hasNext()) {
			Group group = iterator.next();
			if(name.equals(group.getName())) {
				flag = false;
				break;
			}
		}
		
		//与contactGroupNode的不同1：创建的节点的节点类型为im-group
		//与contactGroupNode的不同2：返回SystemGroupNode类型的节点
		//与contactGroupNode的不同3：可以设置组的创建者
		if(flag) {
			Node newNode = this.getNode().addNode(this.getNode().getRepository().getDefinitionManager().getDefinition(SchemeConstant.groupScheme), PropertyConstant.groupName);
			SystemGroupNode group = new SystemGroupNode(newNode);
			group.setName(name);
			group.setDesc(desc);
			group.setCreator(creatorId);
			
			return group;
		}
		
		return null;
	}
	
	/**
	 * 该方法用来设置创建者
	 *
	 */
	public void setCreator(String creatorId) {
		Property property = this.getNode().getProperty(CREATORID);
		if(property != null)
			property.setValue(creatorId);
	}

	/**
	 * 该方法用来判断一个用户是否是组中的成员
	 *
	 */
	public boolean isMember(Principal member) {
		boolean flag = false;
		
		//使用查询构造器进行判断
		Repository repository = this.getNode().getRepository();
		Query query = repository.getQueryManager().createQuery(this.getNode().getRepository().getDefinitionManager().getDefinition(SchemeConstant.groupScheme), Query.SQL);
		query.filterByProperty(MEMBERS, ((User)member).getId());
		NodeIterator<Node> iterator = query.nodes();
		String nodeId = this.getNode().getID();
		while(iterator.hasNext()) {
			Node node = iterator.next();
			if(node.getID().equals(nodeId))
				flag = true;
		}
		
		return flag;
	}
	
	/**
	 * 该方法用来得到公共联系人组的父公共联系人组
	 *
	 */
	public Group getParentGroup() {
		Node parentNode = this.getNode().getParent();
		
		return new SystemGroupNode(parentNode);
	}
}
