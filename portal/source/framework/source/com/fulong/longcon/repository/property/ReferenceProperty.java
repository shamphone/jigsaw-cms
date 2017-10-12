package com.fulong.longcon.repository.property;

import java.sql.SQLException;

import com.fulong.common.dao.DatabaseException;
import com.fulong.common.dao.JdbcDaoFactory;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.ValueFormatException;
import com.fulong.longcon.repository.core.InternalValue;
import com.fulong.longcon.repository.core.InternalRepository;
import com.fulong.longcon.repository.dao.ReferenceValueDao;
import com.fulong.longcon.repository.value.ReferenceValue;

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
public class ReferenceProperty extends BasicProperty {
	public ReferenceProperty(InternalRepository repository, Node content, PropertyDefinition definition) {
		super(repository, content, definition);
	}

	/**
	 * 
	 * @return Content
	 * @throws ValueFormatException
	 *             数据格式转换错误
	 */
	public Node getReference() throws ValueFormatException {
		if (this.getValue() == null)
			return null;
		String nodeID = this.getValue().getString();
		if (nodeID != null)
			return this.repository.getNode(nodeID);
		return null;
	}

	/**
	 * 创建缺省值
	 * 
	 * @return InternalValue
	 */
	protected InternalValue createDefaultValue() {
		return (InternalValue) this.repository.getValueFactory().createValue((Node) null, this.definition.getType());
	}

	public void setValue(String value) throws ValueFormatException {
		InternalValue svalue = this.createDefaultValue();
		svalue.setValue(this.getRepository().getNode(value));
		this.setValue(svalue);

	}

	public void setValue(String[] values) throws ValueFormatException {
		if ((values != null) && (values.length > 0)) {
			Value[] result = new Value[values.length];
			for (int i = 0; i < values.length; i++) {
				InternalValue svalue = this.createDefaultValue();
				svalue.setValue(this.getRepository().getNode(values[i]));
				result[i] = svalue;
			}
			this.setValue(result);
		}
	}

	protected Value[] loadValues() {
		JdbcDaoFactory factory = this.repository.newJdbcDaoFactory();
		try {
			factory.open();
			ReferenceValueDao dao = (ReferenceValueDao) factory.getDao(ReferenceValueDao.class);
			String[] values = dao.load(this.node.getID(), this.definition.getID());
			Value[] ivalues = new Value[values.length];
			for (int i = 0; i < values.length; i++) {
				InternalValue svalue = new ReferenceValue();
				svalue.setValue(this.getRepository().getNode(values[i]));
				ivalues[i] = svalue;
			}
			return ivalues;
		} catch (SQLException ex) {

			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
	}

	/**
	 * saveValues
	 * 
	 * @param values
	 *            InternalValue[]
	 */
	protected void saveValues(Value[] values) {
		JdbcDaoFactory factory = this.repository.newJdbcDaoFactory();
		try {
			factory.open();
			ReferenceValueDao dao = (ReferenceValueDao) factory.getDao(ReferenceValueDao.class);
			dao.delete(node.getID(), this.definition.getID());
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					String value = values[i].getString();
					if ((value != null) && (!value.equals("")))
						dao.insert(node.getID(), this.definition.getID(), i, value);
				}
			}
		} catch (SQLException ex) {
			factory.rollback();
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}

	}

}
