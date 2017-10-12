package com.fulong.longcon.repository.definition;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fulong.common.dao.DatabaseException;
import com.fulong.common.dao.JdbcDaoFactory;
import com.fulong.longcon.ItemExistsException;
import com.fulong.longcon.repository.ChildNodeDefinition;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeDefinitionIterator;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.Validator;
import com.fulong.longcon.repository.ValidatorParser;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.ValueUtils;
import com.fulong.longcon.repository.core.InternalNodeDefinition;
import com.fulong.longcon.repository.core.InternalNodeDefinitionManager;
import com.fulong.longcon.repository.core.InternalPropertyDefinition;
import com.fulong.longcon.repository.dao.NodeDefinitionDao;
import com.fulong.longcon.repository.dao.PropertyDefaultValueDao;
import com.fulong.longcon.repository.dao.PropertyDefinitionDao;
import com.fulong.longcon.repository.data.NodeDefinitionData;
import com.fulong.longcon.repository.data.PropertyDefinitionData;

/**
 * <p>
 * Title: Coolink协同工作支撑平台
 * </p>
 * 
 * <p>
 * Description: Coolink协同工作支撑平台
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2007
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 1.0
 */
public class NodeDefinitionImpl implements InternalNodeDefinition {
	private InternalNodeDefinitionManager repository;
	private NodeDefinitionData data;
	private Comparator<PropertyDefinition> comparator;
	private static final Log log = LogFactory.getLog(NodeDefinitionImpl.class);
	/**
	 * 所有简单属性定义，使用缓加载策略，使用时调用properties()方法，而不是直接使用这个变量
	 */
	private LinkedHashMap<String, PropertyDefinition> _properties;
	private PropertyDefinitionData[] itsOwnProperty;

	public NodeDefinitionImpl(InternalNodeDefinitionManager repository, NodeDefinitionData data) {
		this.repository = repository;
		this.data = data;
		this._properties = null;
		this.comparator = new PropertyDefinitionComparator();
	}
	
	/**
	 * liulei add
	 */
    public PropertyDefinitionData[] getItsOwnProperty()
    {
    	return itsOwnProperty;
    }
	/**
	 * 缓加载所有简单属性
	 * 
	 * @return Map
	 */
	protected synchronized Map<String, PropertyDefinition> properties() {
		if (this._properties != null) {
			return this._properties;
		}
		this._properties = new LinkedHashMap<String, PropertyDefinition>();
		JdbcDaoFactory factory = this.repository.newJdbcDaoFactory();
		try {
			factory.open();
			PropertyDefinitionDao dao = (PropertyDefinitionDao) factory.getDao(PropertyDefinitionDao.class);
			PropertyDefinitionData[] values = dao.findByNodeDefinition(this.data.getID());
			/**
			 * liulei add
			 */
			itsOwnProperty = values;
			for (int i = 0; i < values.length; i++) {
				if (values[i].getType() != 0) {
					InternalPropertyDefinition definition = new PropertyDefinitionImpl(this.repository, this, values[i]);
					this._properties.put(definition.getID(), definition);
				} else {
					ChildNodeDefinition definition = new ChildNodeDefinitionImpl(this.repository, this, values[i]);
					this._properties.put(definition.getID(), definition);
				}
			}
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
		if (this.getSuperDefinition() != null)
			for (Iterator<PropertyDefinition> iterator = this.getSuperDefinition().propertyDefinitions(); iterator.hasNext();) {
				PropertyDefinition property = iterator.next();
				if (!this._properties.containsKey(property.getID())) {
					if (property.getType() > 0)
						this._properties.put(property.getID(), new PropertyDefinitionImpl(this.repository, this, property));
					else
						this._properties.put(property.getID(), new ChildNodeDefinitionImpl(this.repository, this, property));
				}
			}
		log.trace(this._properties.size() + " properties loaded for scheme " + this.getName());
		return this._properties;
	}

	/**
	 * 添加属性定义 当参数id表示的属性在本大纲或父大纲中存在时，抛出异常ItemExistsException。
	 * 
	 * @param type
	 *            String 类型
	 * @param id
	 *            String ID
	 * @return PropertyDescriptor
	 */
	public PropertyDefinition addPropertyDefinition(int type, String id) {
		PropertyDefinitionData data = new PropertyDefinitionData();
		JdbcDaoFactory factory = this.repository.newJdbcDaoFactory();
		try {
			factory.open();
			PropertyDefinitionDao dao = (PropertyDefinitionDao) factory.getDao(PropertyDefinitionDao.class);

			PropertyDefinitionData[] datas = dao.findByRecPNodeDefinition(this.getID(), id);
			if ((datas != null) && (datas.length > 0))
				throw new ItemExistsException("the pkid named " + id + " has existed!");
			data.setType(type);
			data.setID(id);
			data.setName(id);
			data.setNodeDefinitionID(this.getID());
			data.setDeletable(true);
			data.setOrderNo(this.getMaxOrderNum() + 1);
			dao.insert(data);
			InternalPropertyDefinition definition = new PropertyDefinitionImpl(this.repository, this, data);
			this.properties().put(definition.getID(), definition);
			for (Iterator<NodeDefinition> iterator = this.getInheritDefinitions(true); iterator.hasNext();) {
				InternalNodeDefinition child = (InternalNodeDefinition) iterator.next();
				child.addInheritedPropertyDefinition(definition);
			}
			return definition;
		} catch (SQLException ex) {
			factory.rollback();
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
	}

	public void addInheritedPropertyDefinition(PropertyDefinition property) {
		this.properties().put(property.getID(), new PropertyDefinitionImpl(this.repository, this, property));
	}

	/**
	 * 
	 * @return java.lang.String
	 */
	public String getID() {
		return this.data.getID();
	}

	/**
	 * 
	 * @return java.lang.String
	 */
	public String getName() {
		return this.data.getName();
	}

	/**
	 * 当ID为abc.ef时，先去ID为abc的属性，如果取出来的属性为复合属性时， 则按ef再取递归取。
	 * 
	 * @param ID
	 *            String
	 * @return PropertyDescriptor
	 */
	public PropertyDefinition getPropertyDefinition(String ID) {
		if (ID == null)
			return null;
		String[] ids = ID.split("[\\.\\/]");
		if (ids.length < 1)
			return null;
		PropertyDefinition child = getDirectPropertyDefinition(ids[0]);
		if (ids.length < 2)
			return child;
		if (child == null)
			return null;
		int start = ID.indexOf('.');
		if (start < 0)
			start = ID.indexOf('/');
		String subID = ID.substring(start + 1);
		switch (child.getType()) {
		case PropertyType.FIX:
			return ((ChildNodeDefinition) child).getNodeDefinition().getPropertyDefinition(subID);
		case PropertyType.REFERENCE:
			return this.repository.getDefinition(child.getReferenceType()).getPropertyDefinition(subID);
		default:
			return null;
		}
	}

	private PropertyDefinition getDirectPropertyDefinition(String ID) {
		if ((ID == null) || (ID.length() == 0))
			return null;
		return this.properties().get(ID);

	}

	public String getPropertyDefinitionName(String ID) {
		return this.getPropertyDefinition(ID).getName();
	}

	/**
	 * @return java.util.Iterator
	 */
	public Iterator<PropertyDefinition> propertyDefinitions() {

		return this.getPropertyDefinitionCollection().iterator();
	}

	/**
	 * 
	 * @return Iterator
	 */
	public Iterator<PropertyDefinition> getDeclaredPropertyDefinitions() {
		List<PropertyDefinition> set = new Vector<PropertyDefinition>();
		Iterator<PropertyDefinition> values = this.properties().values().iterator();
		while (values.hasNext()) {
			PropertyDefinition value = values.next();
			// 非包裹属性定义即为自己定义的属性，不是从父亲大纲继承下来的。
			// 修正判断属性是否受保护的错误    by mali 2010-8-27
			NodeDefinition superDef = this.getSuperDefinition();
			if (!superDef.getID().equals("no-properties-scheme") && superDef != null){
				if(superDef.getPropertyDefinition(value.getID()) == null)
					set.add(value);
			}
		}
		Collections.sort(set, this.comparator);
		return set.iterator();
	}

	/**
	 * @return java.util.Iterator
	 */
	public List<PropertyDefinition> getPropertyDefinitionCollection() {
		List<PropertyDefinition> sort = new Vector<PropertyDefinition>();
		sort.addAll(properties().values());
		Collections.sort(sort, this.comparator);
		return sort;
	}

	/**
	 * 返回只读/不只读的属性集合
	 * 
	 * @param readonly
	 *            boolean
	 * @return Iterator
	 */
	public Iterator<PropertyDefinition> propertyDefinitions(boolean readonly) {
		Iterator<PropertyDefinition> iterator = this.getPropertyDefinitionCollection().iterator();
		List<PropertyDefinition> list = new Vector<PropertyDefinition>();
		while (iterator.hasNext()) {
			PropertyDefinition definition = (PropertyDefinition) iterator.next();
			if (definition.isReadonly() == readonly)
				list.add(definition);
		}
		Collections.sort(list, this.comparator);
		return list.iterator();
	}

	/**
	 * 
	 * @param type
	 *            String
	 * @return java.util.Iterator
	 */
	public Iterator<PropertyDefinition> propertyDefinitions(int type) {
		List<PropertyDefinition> list = new Vector<PropertyDefinition>();
		for (Iterator<PropertyDefinition> iterator = this.getPropertyDefinitionCollection().iterator(); iterator.hasNext();) {
			PropertyDefinition definition = (PropertyDefinition) iterator.next();
			if (definition.getType() == type)
				list.add(definition);
		}
		Collections.sort(list, this.comparator);
		return list.iterator();
	}

	/**
	 * 
	 * @param name
	 *            String
	 */
	public void setName(String name) {
		this.data.setName(name);
		this.save();
	}

	/**
	 * 
	 * @param property
	 *            String
	 * @param value
	 *            Value
	 * @return boolean
	 */
	public boolean canSetProperty(String property, Value value) {
		PropertyDefinition definition = this.getPropertyDefinition(property);
		if (definition == null)
			throw new IllegalArgumentException("Unknown property name:" + property + ".");
		if (!ValueUtils.checkType(value, definition.getType()))
			return false;
		ValidatorParser parser = this.repository.getValidatorParser();
		String[] constraints = definition.getValueConstraints();
		if (constraints != null)
			for (int i = 0; i < constraints.length; i++) {
				Validator validator = parser.parser(constraints[i]);
				if (!(validator.validate(definition, value)))
					return false;
			}
		return true;
	}

	/**
	 * 
	 * @param property
	 *            String
	 * @param value
	 *            Value[]
	 * @return boolean
	 */
	public boolean canSetProperty(String property, Value[] values) {
		PropertyDefinition definition = this.getPropertyDefinition(property);
		if (definition == null)
			throw new IllegalArgumentException("Unknown property name:" + property + ".");
		if ((definition.getMaxLength() > 0) && (values.length > definition.getMaxLength()))
			return false;
		if ((definition.getMinLength() > 0) && (values.length < definition.getMinLength()))
			return false;
		if (values != null)
			for (int i = 0; i < values.length; i++)
				if (!ValueUtils.checkType(values[i], definition.getType()))
					return false;
		ValidatorParser parser = this.repository.getValidatorParser();
		String[] constraints = definition.getValueConstraints();
		if (constraints != null)
			for (int i = 0; i < constraints.length; i++) {
				Validator validator = parser.parser(constraints[i]);
				for (int j = 0; j < values.length; j++) {
					if (!validator.validate(definition, values[j]))
						return false;
				}

			}
		return true;
	}

	/**
	 * 在该节点的应用下做验证
	 * 
	 * @param property
	 *            String
	 * @param values
	 *            Value[]
	 * @param node
	 *            String 节点的id
	 * @return boolean
	 */
	public boolean canSetProperty(String property, Value[] values, Node node) {
		if ((node == null) || (node.equals("")))
			return this.canSetProperty(property, values);
		PropertyDefinition definition = this.getPropertyDefinition(property);
		if (definition == null)
			throw new IllegalArgumentException("Unknown property name:" + property + ".");
		if ((definition.getMaxLength() > 0) && (values.length > definition.getMaxLength()))
			return false;
		if ((definition.getMinLength() > 0) && (values.length < definition.getMinLength()))
			return false;
		if (values != null)
			for (int i = 0; i < values.length; i++)
				if (!ValueUtils.checkType(values[i], definition.getType()))
					return false;
		ValidatorParser parser = this.repository.getValidatorParser();
		String[] constraints = definition.getValueConstraints();
		if (constraints != null)
			for (int i = 0; i < constraints.length; i++) {
				Validator validator = parser.parser(constraints[i]);
				for (int j = 0; j < values.length; j++) {
					if (!validator.validate(definition, values[j], node))
						return false;
				}
			}
		return true;
	}

	/**
	 * 
	 * @param property
	 *            String
	 * @param values
	 *            Value[]
	 * @param node
	 *            Node
	 * @param self
	 *            Node
	 * @return boolean
	 */
	public boolean canSetProperty(String property, Value[] values, Node node, Node self) {
		if ((node == null) || (node.equals("")))
			return this.canSetProperty(property, values);
		if ((self == null) || (self.equals("")))
			return this.canSetProperty(property, values, node);
		PropertyDefinition definition = this.getPropertyDefinition(property);
		if (definition == null)
			throw new IllegalArgumentException("Unknown property name:" + property + ".");
		if ((definition.getMaxLength() > 0) && (values.length > definition.getMaxLength()))
			return false;
		if ((definition.getMinLength() > 0) && (values.length < definition.getMinLength()))
			return false;
		if (values != null)
			for (int i = 0; i < values.length; i++)
				if (!ValueUtils.checkType(values[i], definition.getType()))
					return false;
		ValidatorParser parser = this.repository.getValidatorParser();
		String[] constraints = definition.getValueConstraints();
		if (constraints != null)
			for (int i = 0; i < constraints.length; i++) {
				Validator validator = parser.parser(constraints[i]);
				for (int j = 0; j < values.length; j++) {
					if (!validator.validate(definition, values[j], node, self))
						return false;
				}
			}
		return true;
	}

	/**
	 * 保存变化到数据库中
	 */
	private void save() {
		JdbcDaoFactory factory = this.repository.newJdbcDaoFactory();
		try {
			factory.open();
			NodeDefinitionDao dao = (NodeDefinitionDao) factory.getDao(NodeDefinitionDao.class);
			dao.update(this.data);
		} catch (SQLException ex) {
			factory.rollback();
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}

	}

	/**
	 * 等值判断
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof NodeDefinition))
			return false;
		NodeDefinition another = (NodeDefinition) obj;
		return this.getID().equals(another.getID());
	}
 
	public int hashCode(){
		return this.getID().hashCode();
	}
	
	/**
	 * 取得大纲下属性的最大序号数
	 * 
	 * @return int
	 */
	private int getMaxOrderNum() {
		int maxNum = 0;
		for (Iterator<PropertyDefinition> iterator = this.getPropertyDefinitionCollection().iterator(); iterator.hasNext();) {
			PropertyDefinition pro = (PropertyDefinition) iterator.next();
			if (pro.getOrderNo() > maxNum)
				maxNum = pro.getOrderNo();
		}
		return maxNum;
	}

	public void setDescription(String description) {
		this.data.setDescription(description);
		this.save();
	}

	public String getDescription() {
		return this.data.getDescription();
	}

	public boolean isProtected() {
		return data.is_system();
	}

	public NodeDefinition getSuperDefinition() {
		return this.repository.getDefinition(this.data.getSuperID());
	}

	/**
	 * 获得继承自该大纲的大纲列表
	 * 
	 * @return NodeDefinitionIterator
	 */
	public NodeDefinitionIterator getInheritDefinitions() {
		Vector<NodeDefinition> v = new Vector<NodeDefinition>();
		for (NodeDefinitionIterator definitions = this.repository.definitions(); definitions.hasNext();) {
			NodeDefinition definition = definitions.nextDefinition();
			NodeDefinition superDef = definition.getSuperDefinition();
			if ((superDef != null) && superDef.equals(this))
				v.add(definition);
		}
		return new NodeDefinitionIteratorImpl(v);
	}

	/**
	 * 是否递归遍历大纲
	 * 
	 * @param recursive
	 *            boolean
	 * @return NodeDefinitionIterator
	 */
	public NodeDefinitionIterator getInheritDefinitions(boolean recursive) {
		if (!recursive)
			return getInheritDefinitions();
		Vector<NodeDefinition> v = new Vector<NodeDefinition>();
		for (NodeDefinitionIterator definitions = this.repository.definitions(); definitions.hasNext();) {
			NodeDefinition definition = definitions.nextDefinition();
			if ((definition.isNodeType(this.getID())) && (!definition.getID().equals(this.getID())))
				v.add(definition);
		}
		return new NodeDefinitionIteratorImpl(v);
	}

	/**
	 * 如果这个节点类型是nodeDefinitionId所定义的节点定义的子节点，则返回true
	 * 
	 * @param nodeDefinitionId
	 *            the name of a node type.
	 * @return a boolean
	 */
	public boolean isNodeType(String nodeDefinitionId) {
		NodeDefinition def = this;
		while (def != null) {
			if (def.getID().equals(nodeDefinitionId))
				return true;
			else
				def = def.getSuperDefinition();
		}
		return false;
	}

	/**
	 * 
	 * @param property
	 *            PropertyDefinition
	 */
	public void delete(PropertyDefinition propertyDefinition) {
		// 父结点仍然有该属性设置，不能删除；
		if (this.getSuperDefinition().getPropertyDefinition(propertyDefinition.getID()) != null)
			return;
		// 系统大纲属性不能删除
		if(this.isProtected())
			return;
		JdbcDaoFactory factory = this.repository.newJdbcDaoFactory();
		try {
			factory.open();
			PropertyDefinitionDao dao = (PropertyDefinitionDao) factory.getDao(PropertyDefinitionDao.class);
			dao.delete(this.getID(), propertyDefinition.getID());

			PropertyDefaultValueDao pddao = (PropertyDefaultValueDao) factory.getDao(PropertyDefaultValueDao.class);
			pddao.delete(propertyDefinition.getID(), this.getID());
			this.properties().remove(propertyDefinition.getID());
		} catch (SQLException ex) {
			factory.rollback();
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
		for (NodeDefinitionIterator children = getInheritDefinitions(); children.hasNext();) {
			children.next().delete(propertyDefinition);
		}
	}

	/**
	 * 为当前NodeDefinition添加一个复合属性
	 * 
	 * @param type
	 *            NodeDefinition，这个参数设置该复合属性到底是用的哪个大纲
	 * @param id
	 *            String
	 * @return ChildNodeDefinition
	 */
	public ChildNodeDefinition addChildNodeDefinition(NodeDefinition type, String id) {
		PropertyDefinitionData data = new PropertyDefinitionData();
		JdbcDaoFactory factory = this.repository.newJdbcDaoFactory();
		try {
			factory.open();
			PropertyDefinitionDao dao = (PropertyDefinitionDao) factory.getDao(PropertyDefinitionDao.class);

			PropertyDefinitionData[] datas = dao.findByRecPNodeDefinition(this.getID(), id);
			if ((datas != null) && (datas.length > 0))
				throw new ItemExistsException("the pkid named " + id + " has existed!");
			data.setType(0); // 表示复合字段
			data.setID(id);
			data.setName(id);
			data.setNodeDefinitionID(this.getID());
			data.setDeletable(true);
			data.setNodeType(type.getID());
			data.setOrderNo(this.getMaxOrderNum() + 1);
			dao.insert(data);
			ChildNodeDefinition definition = new ChildNodeDefinitionImpl(this.repository, this, data);
			this.properties().put(definition.getID(), definition);
			for (Iterator<NodeDefinition> iterator = this.getInheritDefinitions(); iterator.hasNext();) {
				InternalNodeDefinition child = (InternalNodeDefinition) iterator.next();
				child.addInheritedPropertyDefinition(definition);
			}
			return definition;

		} catch (SQLException ex) {
			factory.rollback();
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}

	}

	public int getOrderNo(PropertyDefinition definition) {
		Vector<PropertyDefinition> vector = new Vector<PropertyDefinition>(this.getPropertyDefinitionCollection());
		return vector.indexOf(definition);
	}

	/**
	 * 
	 * @return Date
	 */
	public Date getCreateDate() {
		return this.data.getCreateTime();
	}

	/**
	 * 
	 * @param superDefinition
	 *            NodeDefinition
	 * @return boolean
	 */
	public boolean isNodeType(NodeDefinition superDefinition) {
		return this.isNodeType(superDefinition.getID());
	}

	/**
	 * 设置当前大纲的父大纲
	 * 
	 * @param superDefinition
	 *            NodeDefinition
	 */
	public void setSuperDefinition(NodeDefinition superDefinition) {
		this.data.setSuperID(superDefinition.getID());
		this.save();
		this._properties = null;
	}

	/**
	 * 获取引用自己的属性定义集合。
	 * 
	 * @return Iterator
	 */
	public Iterator<PropertyDefinition> getReferencedPropertyDefinitions() {
		JdbcDaoFactory factory = this.repository.newJdbcDaoFactory();
		Vector<PropertyDefinition> v = new Vector<PropertyDefinition>();
		try {
			factory.open();
			PropertyDefinitionDao dao = (PropertyDefinitionDao) factory.getDao(PropertyDefinitionDao.class);
			PropertyDefinitionData[] values = dao.findByRefNodeDefinition(this.data.getID());

			for (int i = 0; i < values.length; i++) {
				InternalPropertyDefinition definition = new PropertyDefinitionImpl(this.repository, this, values[i]);
				v.add(definition);
			}
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
		return v.iterator();
	}

}
