package com.fulong.longcon.repository.property;


import java.sql.SQLException;

import com.fulong.common.dao.DatabaseException;
import com.fulong.common.dao.JdbcDaoFactory;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.core.InternalValue;
import com.fulong.longcon.repository.core.InternalRepository;
import com.fulong.longcon.repository.dao.StringValueDao;
import com.fulong.longcon.repository.value.StringValue;

/**
 * <p>Title: Coolink协同工作支撑平台</p>
 *
 * <p>Description: Coolink协同工作支撑平台</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 1.0
 */
public class StringProperty extends BasicProperty {

    public StringProperty(InternalRepository repository, Node content,PropertyDefinition definition)
    {
        super(repository, content, definition);
    }

    protected  Value[] loadValues() {
        JdbcDaoFactory factory = this.repository.newJdbcDaoFactory();
        try {
            factory.open();
            StringValueDao dao = (StringValueDao) factory.getDao(StringValueDao.class);
            String[] values = dao.load(this.node.getID(),this.definition.getID());
            Value[] ivalues = new Value[values.length];
            for (int i = 0; i < values.length; i++) {
                InternalValue svalue = new StringValue();
                svalue.setValue(values[i]);
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
     * @param values InternalValue[]
     */
    protected void saveValues(Value[] values) {
        JdbcDaoFactory factory = this.repository.newJdbcDaoFactory();
        try {
            factory.open();
            StringValueDao dao = (StringValueDao) factory.getDao(StringValueDao.class);
            dao.delete(node.getID(), this.definition.getID());
            if(values==null)
            	return;
            for (int i = 0; i < values.length; i++) {
                String value = values[i].getString();
                if (value != null)
                    dao.insert(node.getID(), this.definition.getID(), i,value);
            }
        } catch (SQLException ex) {
             factory.rollback();
            throw new DatabaseException(ex);
        } finally {
            factory.close();
        }

    }
}
