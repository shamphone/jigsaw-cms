package com.fulong.longcon.repository.property;

import java.sql.SQLException;

import com.fulong.common.dao.DatabaseException;
import com.fulong.common.dao.JdbcDaoFactory;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.core.InternalValue;
import com.fulong.longcon.repository.core.InternalRepository;
import com.fulong.longcon.repository.dao.PathValueDao;
import com.fulong.longcon.repository.value.PathValue;

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
public class PathProperty extends BasicProperty {
    public PathProperty(InternalRepository repository, Node content,
                        PropertyDefinition definition) {
        super(repository, content, definition);
    }

    protected  Value[] loadValues() {
        JdbcDaoFactory factory = this.repository.newJdbcDaoFactory();
        try {
            factory.open();
            PathValueDao dao = (PathValueDao) factory.getDao(
                    PathValueDao.class);
            String[] values = dao.load(this.node.getID(),
                                       this.definition.getID());
            Value[] ivalues = new Value[values.length];
            for (int i = 0; i < values.length; i++) {
                InternalValue svalue = new PathValue();
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
            PathValueDao dao = (PathValueDao) factory.getDao(PathValueDao.class);
            dao.delete(node.getID(), this.definition.getID());
            if(values==null)
            	return;
            for (int i = 0; i < values.length; i++) {
                String value = values[i].getString();
// by lichengzhao : syxzsp the sequence of multi-attachment
//              	if ((value != null)&&(!value.trim().equals("")))
//                	if (value == null)
//                		value = "";
              //兼容异步上传占位符置空处理    by mali 2010-9-8
                if(value == null)
                	dao.delete(node.getID(), this.definition.getID());
                else
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
