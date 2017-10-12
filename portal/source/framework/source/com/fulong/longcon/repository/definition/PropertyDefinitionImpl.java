package com.fulong.longcon.repository.definition;

import java.sql.SQLException;
import java.util.Calendar;

import com.fulong.common.dao.DatabaseException;
import com.fulong.common.dao.JdbcDaoFactory;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.core.InternalNodeDefinition;
import com.fulong.longcon.repository.core.InternalNodeDefinitionManager;
import com.fulong.longcon.repository.core.InternalPropertyDefinition;
import com.fulong.longcon.repository.dao.PropertyConstraintsDao;
import com.fulong.longcon.repository.dao.PropertyDefaultValueDao;
import com.fulong.longcon.repository.dao.PropertyDefinitionDao;
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
public class PropertyDefinitionImpl implements InternalPropertyDefinition {
	protected InternalNodeDefinitionManager repository;
	protected InternalNodeDefinition nodeDefinition;
	protected PropertyDefinitionData data;
	protected PropertyDefinition overload; // 父结点的对应的PropertyDefinition
//	private Value[] _defaultValues;

	public PropertyDefinitionImpl(InternalNodeDefinitionManager repository, InternalNodeDefinition node, PropertyDefinitionData data) {
		this.repository = repository;
		this.nodeDefinition = node;
		this.data = data;
		if(node.getSuperDefinition()!=null)
			this.overload= node.getSuperDefinition().getPropertyDefinition(this.data.getID());
	}

	public PropertyDefinitionImpl(InternalNodeDefinitionManager repository, InternalNodeDefinition node, PropertyDefinitionData data, PropertyDefinition parent) {
		this.repository = repository;
		this.nodeDefinition = node;
		this.data = data;
		this.overload = parent;
	}

	public PropertyDefinitionImpl(InternalNodeDefinitionManager repository, InternalNodeDefinition node, PropertyDefinition parent) {
		this.repository = repository;
		this.nodeDefinition = node;
		this.overload = parent;
		this.data = new PropertyDefinitionData();
		this.data.setID(this.overload.getID());
		this.data.setMultiple(this.overload.isMultiple());
		this.data.setDeletable(false);
		this.data.setNodeDefinitionID(this.nodeDefinition.getID());
		this.data.setType(this.overload.getType());
	}

	/**
	 * 
	 * @return String
	 */
	public String getID() {
		return this.data.getID();
	}

	/**
	 * 如果当前结点已经设置了name,则使用当前结点的，否则使用继承的父结点。
	 * @return java.lang.String
	 */
	public String getName() {
		if(this.data.getName()!=null)
			return this.data.getName();
		if(this.overload!=null)
		return this.overload.getName();
		return null;
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isMultiple() {
		if(this.overload!=null)
			return this.overload.isMultiple();
		return this.data.isMultiple();
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

	protected void save() {
		JdbcDaoFactory factory = this.repository.newJdbcDaoFactory();
		try {
			factory.open();
			PropertyDefinitionDao dao = (PropertyDefinitionDao) factory.getDao(PropertyDefinitionDao.class);
			if(dao.findByNodeDefinition(this.nodeDefinition.getID(), this.data.getID()).length==0)
				dao.insert(data);
			else
				dao.update(this.data);
		} catch (SQLException ex) {
			factory.rollback();
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}

	}

	public int getMinLength() {
		if(this.data.getMinLength()>0)
			return this.data.getMinLength();
		if(this.overload!=null)
			return this.overload.getMinLength();
		return 0;
	}

	public int getMaxLength() {
		if(this.data.getMaxLength()>0)
			return this.data.getMaxLength();
		if(this.overload!=null)
			return this.overload.getMaxLength();
		return 0;

	}

	public void setLength(int min, int max) {
		this.data.setMinLength(min);
		this.data.setMaxLength(max);
		this.save();
	}

	/**
	 * 获得属性定义的序号
	 * 
	 * @return int
	 */
	public int getOrderNo() {
		if(this.data.getOrderNo()>0)
			return this.data.getOrderNo();
		if(this.overload!=null)
			return this.overload.getOrderNo();
		return 0;

	}

	/**
	 * 设置属性定义的序号
	 * 
	 * @param orderNo
	 *            int
	 */
	public void setOrderNo(int orderNo) {
		this.data.setOrderNo(orderNo);
		this.save();
	}

	public int getType() {
		return this.data.getType();
	}

	public void setDescription(String description) {
		this.data.setDescription(description);
		this.save();
	}

	public void setMultiple(boolean multiple) {
		this.data.setMultiple(multiple);
		this.save();
	}

	public String getDescription() {
		if(this.data.getDescription()!=null)
			return this.data.getDescription();
		if(this.overload!=null)
		return this.overload.getDescription();
		return null;
	}

	public boolean isProtected() {
		//修正原来的错误  by mali 2010-8-26
		NodeDefinition superDef = this.nodeDefinition.getSuperDefinition();
		if (superDef != null && !superDef.getID().equals("no-properties-scheme")){
			if(superDef.getPropertyDefinition(this.getID()) != null)
				return true;
		}
		if(!(this.data.isDeletable())){
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @return NodeDefinition
	 */
	public NodeDefinition getDeclaringNodeDefinition() {
		return this.repository.getDefinition(this.data.getNodeDefinitionID());
	}

	/**
	 * 
	 * @param readonly
	 *            boolean
	 */
	public void setReadonly(boolean readonly) {
		this.data.setReadOnly(readonly);
		this.save();
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isReadonly() {
		return this.data.isReadOnly();
	}

	/**
	 * 
	 * @return java.lang.String
	 */
	public Value[] getDefaultValues() {
			Value[] result = new Value[0];
			JdbcDaoFactory factory = this.repository.newJdbcDaoFactory();
			try {
				factory.open();
				PropertyDefaultValueDao dao = (PropertyDefaultValueDao) factory.getDao(PropertyDefaultValueDao.class);
				String[] values = dao.findByPropertyDefinition(this.data.getID(), this.nodeDefinition.getID());
				result = new Value[values.length];
				// 如果是PropertyType.REFERENCE类型的属性，现在只是按照string类型的返回
				if (this.data.getType() == PropertyType.REFERENCE) {
					for (int i = 0; i < values.length; i++)
						result[i] = this.repository.getValueFactory().createValue(values[i]);

				} else {
					for (int i = 0; i < values.length; i++)
						result[i] = this.repository.getValueFactory().createValue(values[i], this.data.getType());
				}			
			} catch (SQLException ex) {
				throw new DatabaseException(ex);
			} finally {
				factory.close();
			}
		if(result.length>0)
			return result;			
		if(this.overload!=null)
			return this.overload.getDefaultValues();
		return new Value[0];

	}

	public void setDefaultValues(Value[] values) {
		JdbcDaoFactory factory = this.repository.newJdbcDaoFactory();
		try {
			factory.open();
			PropertyDefaultValueDao dao = (PropertyDefaultValueDao) factory.getDao(PropertyDefaultValueDao.class);
			dao.delete(this.data.getID(), this.nodeDefinition.getID());
			for (int i = 0; i < values.length; i++) {
				dao.insert(this.data.getID(), values[i].getString(), this.nodeDefinition.getID());
			}
		} catch (SQLException ex) {
			factory.rollback();
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}

	}

	public String getTypeName() {
		return PropertyType.nameFromValue(this.data.getType());
	}

	public String[] getValueConstraints() {
		String[] values = new String[0];
		JdbcDaoFactory factory = this.repository.newJdbcDaoFactory();
		try {
			factory.open();
			PropertyConstraintsDao dao = (PropertyConstraintsDao) factory.getDao(PropertyConstraintsDao.class);
			values = dao.findByPropDefAndNodeDefID(this.data.getID(), this.data.getNodeDefinitionID());
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
		if((values.length==0)&& (this.overload!=null))
			return this.overload.getValueConstraints();
		return values;
	}

	public void setValueConstraints(String[] constraints) {
		JdbcDaoFactory factory = this.repository.newJdbcDaoFactory();
		try {
			factory.open();
			PropertyConstraintsDao dao = (PropertyConstraintsDao) factory.getDao(PropertyConstraintsDao.class);
			dao.delete(this.data.getID(), this.data.getNodeDefinitionID());
			if ((constraints != null) && (constraints.length > 0)) {
				for (int i = 0; i < constraints.length; i++) {
					dao.insert(this.data.getID(), constraints[i], this.data.getNodeDefinitionID());
				}
			}
		} catch (SQLException ex) {
			factory.rollback();
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
	}

	public String getEnumEntry() {
		return this.data.getEnumEntry();
	}

	/**
	 * 设置枚举型值的跟节点
	 * 
	 * @param entry
	 *            String
	 */
	public void setEnumEntry(String entry) {
		this.data.setEnumEntry(entry);
		this.save();
	}

	/**
	 * 获取引用类型的属性定义具体的引用类型
	 * 
	 * @return String
	 */
	public String getReferenceType() {
		if(this.data.getReferenceType()!=null)
			return this.data.getReferenceType();
		if(this.overload!=null)
		return this.overload.getReferenceType();
		return null;
	}

	/**
	 * 
	 * @return NodeDefinition
	 */
	public NodeDefinition getReferenceDefinition() {
		if(this.getReferenceType()!=null){
			return this.repository.getDefinition(this.getReferenceType());
		}
		/*if(this.data.getReferenceType()!=null)
			return this.repository.getDefinition(this.data.getReferenceType());
		if(this.overload!=null)
			return this.overload.getReferenceDefinition();*/
		return null;
	}

	/**
	 * 设置引用类型的属性定义具体的引用类型
	 * 
	 * @param referenceType
	 *            String
	 */
	public void setReferenceType(String referenceType) {
		this.data.setReferenceType(referenceType);
		this.save();
	}

	/**
	 * 内部序号
	 * 
	 * @return int
	 */
	public int getInnerOrderNumber() {
		return this.data.getOrderNo();
	}

	public void setInnerOrderNumber(int orderNo) {
		this.data.setOrderNo(orderNo);
	}

	/**
	 * 设置该属性定义是否为受保护，
	 * 
	 * @param isProtected
	 *            boolean， 为true时，该属性定义为受保护。
	 */
	public void setProtected(boolean isProtected) {
		this.data.setDeletable(!isProtected);
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isRequired() {
		String[] values = this.getValueConstraints();
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				if (values[i].equals("required"))
					return true;
			}
		}
		return false;
	}

	public void setDefaultValue(String value) {
		Value svalue = this.repository.getValueFactory().createValue(value, this.data.getType());
		this.setDefaultValues(new Value[] { svalue });
	}

	public void setDefaultValue(long value) {
		Value svalue = this.repository.getValueFactory().createValue(value);
		this.setDefaultValues(new Value[] { svalue });
	}

	public void setDefaultValue(Calendar value) {
		Value svalue = this.repository.getValueFactory().createValue(value);
		this.setDefaultValues(new Value[] { svalue });
	}

	public void setDefaultValue(double value) {
		Value svalue = this.repository.getValueFactory().createValue(value);
		this.setDefaultValues(new Value[] { svalue });
	}

	public void setValueConstraint(String constraint) {
		this.setValueConstraints(new String[] { constraint });
	}

	public Value getDefaultValue() {
		Value[] values = this.getDefaultValues();
		if ((values == null) || (values.length == 0))
			return null;
		return values[0];
	}
}
