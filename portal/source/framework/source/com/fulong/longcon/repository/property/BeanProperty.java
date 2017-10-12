package com.fulong.longcon.repository.property;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.ValueFormatException;
import com.fulong.longcon.repository.core.InternalRepository;

/**
 * <p>
 * Title: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */
public class BeanProperty extends BasicProperty {

	public BeanProperty(InternalRepository repository, Node node,
			PropertyDefinition definition) {
		super(repository, node, definition);

	}

	private Object getPropertyValue() {
		try {
			return PropertyUtils.getProperty(node, definition.getID());
		} catch (NoSuchMethodException ex) {
			throw new ValueFormatException(ex);
		} catch (InvocationTargetException ex) {
			throw new ValueFormatException(ex);
		} catch (IllegalAccessException ex) {
			throw new ValueFormatException(ex);
		} catch (IllegalArgumentException ex) {
			return null;
		}
	}

	/**
	 * 
	 * @return Value[]
	 */
	protected Value[] loadValues() {
		Object value = this.getPropertyValue();
		if (value == null)
			return new Value[0];
		if (value.getClass().isArray()) {
			return new Value[] { repository.getValueFactory().createValue(
					value, definition.getType()) };
		} else {
			Object[] array = (Object[]) value;
			Value[] values = new Value[array.length];
			for (int i = 0; i < values.length; i++)
				values[i] = repository.getValueFactory().createValue(array[i],
						definition.getType());
			return values;
		}
	}

	/**
	 * 
	 * @param values
	 *            Value[]
	 */
	protected void saveValues(Value[] values) {
	}

	/**
	 * 
	 * @param values
	 *            PropertyValue 数据格式转换错误
	 * @throws ValueFormatException
	 */
	public void setValue(Value[] values) throws ValueFormatException {

	}

	/**
	 * 
	 * @return PropertyValue
	 */
	public Value[] getValues() {
		Object value = this.getPropertyValue();
		if (value == null)
			return new Value[0];
		if (!value.getClass().isArray()) {
			return new Value[] { repository.getValueFactory().createValue(
					value, definition.getType()) };
		} else {
			Object[] array = (Object[]) value;
			Value[] values = new Value[array.length];
			for (int i = 0; i < values.length; i++)
				values[i] = repository.getValueFactory().createValue(array[i],
						definition.getType());
			return values;
		}
	}

}
