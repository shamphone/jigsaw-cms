package com.fulong.longcon.repository.property;

import java.io.IOException;
import java.sql.SQLException;

import com.fulong.common.dao.DatabaseException;
import com.fulong.common.dao.JdbcDaoFactory;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.ValueFormatException;
import com.fulong.longcon.repository.core.InternalRepository;
import com.fulong.longcon.repository.core.InternalValue;
import com.fulong.longcon.repository.dao.BlobValueDao;
import com.fulong.longcon.repository.value.BinaryValue;

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
public class BinaryProperty extends BasicProperty {

	public BinaryProperty(InternalRepository repository, Node node,
			PropertyDefinition definition) {
		super(repository, node, definition);
		this.repository = repository;
		this.node = node;
		this.definition = definition;

	}

	/**
	 * 不能使用缓存
	 * 
	 * @return Value[]
	 */
	public Value[] getValues() {
		return this.loadValues();
	}

	/**
	 * loadValues
	 * 
	 * @return InternalValue[]
	 */
	protected Value[] loadValues() {
		JdbcDaoFactory factory = this.repository.newJdbcDaoFactory();
		int count;
		try {
			factory.open();
			BlobValueDao dao = (BlobValueDao) factory
					.getDao(BlobValueDao.class);
			count = dao.count(this.node.getID(), this.definition.getID());
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
		Value[] values = new Value[count];
		for (int i = 0; i < values.length; i++) {
			InternalValue svalue = new BinaryValue();
			svalue.setValue(new BlobInputStream(this.repository, this.node
					.getID(), this.definition.getID(), i));
			values[i] = svalue;
		}
		return values;
	}

	/**
	 * saveValues 当参数的长度为0时，删除现有的属性内容
	 * 
	 * @param values
	 *            InternalValue[]
	 */
	protected void saveValues(Value[] values) {
		JdbcDaoFactory factory = this.repository.newJdbcDaoFactory();
		try {
			factory.open();
			BlobValueDao dao = (BlobValueDao) factory
					.getDao(BlobValueDao.class);
			// 先把现有的属性内容删除掉，再插入参数表示的实行内容
			dao.delete(this.node.getID(), this.definition.getID());
			if (values == null)
				return;
			for (int i = 0; i < values.length; i++) {
				dao.insert(this.node.getID(), this.definition.getID(), i);
			}

			for (int i = 0; i < values.length; i++) {
				dao.insertValue(this.node.getID(), this.definition.getID(), i,
						values[i].getStream());
			}
		} catch (SQLException ex) {
			factory.rollback();
			throw new DatabaseException(ex);
		} catch (IOException ex) {
			factory.rollback();
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}

	}

	/**
	 * 
	 * @return String[]
	 * @throws ValueFormatException
	 *             数据格式转换错误
	 */
	public String[] getArray() throws ValueFormatException {
		throw new ValueFormatException("Can't convert binary to string array.");
	}

	/**
	 * @return an <code>long</code>.
	 * 
	 * @return an <code>long</code>.
	 */
	public long getLength() {
		long[] lengthes = this.getLengths();
		long length = 0;
		for (int i = 0; i < lengthes.length; i++)
			length += lengthes[i];
		return length;
	}

	public long[] getLengths() {
		JdbcDaoFactory factory = this.repository.newJdbcDaoFactory();
		try {
			factory.open();
			BlobValueDao dao = (BlobValueDao) factory
					.getDao(BlobValueDao.class);
			long[] result = dao.getLengthes(this.node.getID(), this.definition
					.getID());
			return result;
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
	}

	public String getMime() {
		return "";
	}

	public String getFileName() {
		return "";
	}

	public void setMime(String mime) {

	}

	public void setFileName(String fileName) {

	}

}
