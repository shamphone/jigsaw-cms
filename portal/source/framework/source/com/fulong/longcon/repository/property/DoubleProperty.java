package com.fulong.longcon.repository.property;

import java.sql.SQLException;

import com.fulong.common.dao.DatabaseException;
import com.fulong.common.dao.JdbcDaoFactory;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.core.InternalValue;
import com.fulong.longcon.repository.core.InternalRepository;
import com.fulong.longcon.repository.dao.DoubleValueDao;
import com.fulong.longcon.repository.value.DoubleValue;

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
public class DoubleProperty extends BasicProperty {
    public DoubleProperty(InternalRepository repository, Node content,
                          PropertyDefinition definition) {
        super(repository, content, definition);
    }

    /**
     *
     * @return Value[]
     */
    protected Value[] loadValues() {
        JdbcDaoFactory factory = this.repository.newJdbcDaoFactory();
        try {
            factory.open();
            DoubleValueDao dao = (DoubleValueDao) factory.getDao(DoubleValueDao.class);
            double[] values = dao.load(this.node.getID(),
                                       this.definition.getID());
            Value[] ivalues = new Value[values.length];
            for (int i = 0; i < values.length; i++) {
                InternalValue svalue = new DoubleValue();
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
     *
     * @param values Value[]
     */
    protected void saveValues(Value[] values) {
        JdbcDaoFactory factory = this.repository.newJdbcDaoFactory();
        try {
            factory.open();
            DoubleValueDao dao = (DoubleValueDao) factory.getDao(DoubleValueDao.class);
            dao.delete(node.getID(), this.definition.getID());
            if(values==null)
            	return;
            for (int i = 0; i < values.length; i++)
                dao.insert(node.getID(), this.definition.getID(),i,
                           values[i].getDouble());
        } catch (SQLException ex) {
            factory.rollback();
            throw new DatabaseException(ex);
        } finally {
            factory.close();
        }
    }
}
