package com.fulong.longcon.security.impl;

import java.security.Principal;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import com.fulong.common.Duration;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeDefinitionIterator;
import com.fulong.longcon.repository.NodeDefinitionManager;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.security.Group;
import com.fulong.longcon.security.GroupIterator;
import com.fulong.longcon.security.PassportIdentity;
import com.fulong.longcon.security.ext.GeneralGroup;
import com.fulong.longcon.security.ext.SecurityManagerExt;

/**
 * <p>
 * Title: 龙驭门户引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author Lixf
 * @version 1.0
 */
public class GroupImpl extends GeneralGroup implements PassportIdentity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3395478559239153079L;
	private NodeDefinition definition;
	private Repository repository;
	private NodeDefinitionManager definitionManager;
	private SecurityManagerExt securityManager;

	public GroupImpl(SecurityManagerExt securityManager,
			NodeDefinition definition) {
		if (definition == null)
			throw new IllegalArgumentException("definition could not be null.");
		this.definition = definition;
		this.securityManager = securityManager;
		this.repository = this.securityManager.getRepository();
		this.definitionManager = repository.getDefinitionManager();
	}



	/**
	 * Adds the specified member to the group.
	 * 
	 * @param user
	 *            the principal to add to this group.
	 * @return true if the member was successfully added, false if the principal
	 *         was already a member.
	 */
	public boolean addMember(Principal user) {
		Node node = (Node) user;
		if (node.isNodeType(this.definition.getID()))
			return false;
		node.addMixinDefinition(this.definition);
		return true;
	}

	/**
	 * Principal的ID。
	 * 
	 * @return String
	 */
	public String getName() {
		return this.definition.getID();
	}

	/**
	 * 
	 * @return String
	 */
	public String getID() {
		return this.definition.getID();
	}

	/**
	 * 
	 * @return String
	 */
	public String getGroupType() {
		return this.definition.getID();
	}

	/**
	 * 
	 * @return int
	 */
	public int getMemberType() {
		if (this.definition.isNodeType(Group.ORG_ROOT))
			return ORGANIZATION;
		else
			return USER;
	}


	/**
	 * 所在父组
	 * 
	 * @return Group
	 */
	public Group getParentGroup() {
		return new GroupImpl(this.securityManager, this.definition
				.getSuperDefinition());
	}

	/**
	 * 根据ID或者名字来获取子组
	 * 
	 * @param ID
	 *            String
	 * @return Group
	 */
	public Group getGroup(String ID) {
		return new GroupImpl(this.securityManager, this.definitionManager
				.getDefinition(ID));

	}

	/**
	 * 
	 * @param parent
	 *            Group
	 * @return boolean
	 */

	public boolean isChild(Group parent) {
		return this.getNodeDefinition().isNodeType(parent.getNodeDefinition());
	}

	/**
	 * 缺省期限，保存在属性定义的数据校验中
	 * 
	 * @return Duration
	 */
	public Duration getDefaultPeriod() {
		String[] constraints = this.definition
				.getPropertyDefinition("duration").getValueConstraints();
		if ((constraints == null) || (constraints.length == 0))
			return null;
		return new Duration(constraints[0]);
	}

	/**
	 * 缺省期限，保存在属性定义的数据校验中
	 * 
	 * @param count
	 *            int
	 * @param field
	 *            char
	 */
	public void setDefaultPeriod(int count, String field) {
		Duration duration = new Duration(count, field);
		this.definition.getPropertyDefinition("duration").setValueConstraint(
				duration.toString());
	}

	/**
	 * 
	 * @return String
	 */
	public String getDescription() {
		return this.definition.getDescription();
	}

	/**
	 * 所有子组
	 * 
	 * @return GroupIterator
	 */
	public GroupIterator children() {
		return new ChildGroupDefinitionIterator(this.securityManager,
				this.definition);

	}

	/**
	 * 
	 * @param name
	 *            String
	 */
	public void setCommonname(String name) {
		this.definition.setName(name);
	}

	/**
	 * 
	 * @param description
	 *            String
	 */
	public void setDescription(String description) {
		this.definition.setDescription(description);
	}

	/**
	 * @todo
	 * @param parent
	 *            Group
	 */
	public void setParentGroup(Group parent) {
		this.definition.setSuperDefinition(parent.getNodeDefinition());
	}

	/**
	 * 
	 * @param principal
	 *            Principal
	 * @return boolean
	 */
	public boolean isMember(Principal principal) {
		Node node = (Node) principal;
		return node.isNodeType(this.definition.getID());
	}

	/**
	 * 递归删除这个组下的所有关系
	 */
	public void clearMember() {
		this.securityManager.getRepository().removeNodes(this.definition, true);
	}

	/**
	 * 
	 * @return int
	 */

	public int getDefaulPoints() {
		Value point = this.definition.getPropertyDefinition("points")
				.getDefaultValue();
		if (point == null)
			return 0;
		return (int) point.getLong();
	}

	/**
	 * 
	 * @param point
	 *            int
	 */
	public void setDefaultPoints(int point) {
		this.definition.getPropertyDefinition("points").setDefaultValue(point);
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isRoot() {
		return ROOT.equals(this.getID());
	}

	/**
	 * 从组里面删除用户
	 * 
	 * @param user
	 *            Principal
	 * @return boolean
	 */
	public boolean removeMember(Principal user) {
		Node node = (Node) user;
		node.removeMixin(this.definition);
		return true;
	}

	/**
	 * 显示名称
	 * 
	 * @return String
	 */
	public String getCommonname() {
		return this.definition.getName();
	}

	/**
	 * 
	 * @return Date
	 */
	public Date getRegisterDate() {
		return this.definition.getCreateDate();
	}

	/**
	 * 
	 * @return Date
	 */
	public Date getLastModifiedDate() {
		return this.definition.getCreateDate();
	}

	/**
	 * 
	 * @return int
	 */
	public int getType() {
		return GROUP;
	}

	/**
	 * 
	 * @param another
	 *            Object
	 * @return boolean
	 */
	public boolean equals(Object another) {
		if (another == null)
			return false;
		if (another instanceof Group) {
			return ((Group) another).getID().equals(this.getID());
		}
		return false;
	}

	/**
	 * 使用ID的hashcode
	 * 
	 * @return int
	 */
	public int hashCode() {
		return this.getID().hashCode();
	}

	/**
	 * 
	 * @return NodeDefinition
	 */
	public NodeDefinition getNodeDefinition() {
		return this.definition;
	}

	/**
	 * 
	 * @return int
	 * @throws SQLException 
	 * @todo
	 */
	public int getAllMemberCount() throws SQLException {
		Query query = this.repository.getQueryManager().createQuery(
				this.definition, Query.SQL);
		query.filterByParent(this.repository.getRootNode(), true);
		return (int) query.nodes().getSize();
	}

	/**
	 * 获取用户加入的组
	 * 
	 * @param principal
	 *            Principal
	 * @return Iterator
	 * @todo
	 */
	public Iterator<? extends Group> getAffiliatedGroups(Principal principal) {
		Node node = (Node) principal;
		Collection<NodeDefinition> definitions = new Vector<NodeDefinition>();
		Collections.addAll(definitions, node.getMixinDefinitions());
		return new GroupDefinitionIterator<Object>(this.securityManager,
				definitions.iterator());

	}



	@Override
	public Enumeration<? extends Principal> members() {
		return null;
	}

}

/**
 * GroupIterator和NodeDefinitionIterator的转换器
 * <p>
 * Title: 龙驭门户引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author Lixf
 * @version 1.0
 */
class ChildGroupDefinitionIterator implements GroupIterator {
	private NodeDefinitionIterator definitionIterator;
	private SecurityManagerExt securityManager;

	public ChildGroupDefinitionIterator(SecurityManagerExt securityManager,
			NodeDefinition parent) {
		this.securityManager = securityManager;
		this.definitionIterator = parent.getInheritDefinitions(true);
	}

	/**
	 * Returns the current position within the iterator.
	 * 
	 * @return a long
	 */
	public long getPosition() {
		return this.definitionIterator.getPosition();
	}

	/**
	 * Returns the number of elements in the iterator.
	 * 
	 * @return a long
	 */
	public long getSize() {
		return this.definitionIterator.getSize();
	}

	/**
	 * Returns <tt>true</tt> if the iteration has more elements.
	 * 
	 * @return <tt>true</tt> if the iterator has more elements.
	 */
	public boolean hasNext() {
		return this.definitionIterator.hasNext();
	}

	/**
	 * Returns the next element in the iteration.
	 * 
	 * @return the next element in the iteration.
	 */
	public Object next() {
		NodeDefinition definition = this.definitionIterator.nextDefinition();
		return new GroupImpl(this.securityManager, definition);
	}

	/**
	 * Removes from the underlying collection the last element returned by the
	 * iterator (optional operation).
	 * 
	 */
	public void remove() {
		this.definitionIterator.remove();
	}

	/**
	 * Skip a number of elements in the iterator.
	 * 
	 * @param skipNum
	 *            the non-negative number of elements to skip
	 */
	public void skip(long skipNum) {
		this.definitionIterator.skip(skipNum);
	}

	/**
	 * 
	 * @return Group
	 */
	public Group nextGroup() {
		return (Group) this.next();
	}
}

class GroupDefinitionIterator<E> implements Iterator<Group> {
	private Iterator<NodeDefinition> iterator;
	private SecurityManagerExt securityManager;

	public GroupDefinitionIterator(SecurityManagerExt securityManager,
			Iterator<NodeDefinition> iterator) {
		this.iterator = iterator;
		this.securityManager = securityManager;
	}

	public Group next() {
		return new GroupImpl(this.securityManager, iterator.next());
	}

	public boolean hasNext() {
		return this.iterator.hasNext();
	}

	public void remove() {
		this.iterator.remove();
	}
}
