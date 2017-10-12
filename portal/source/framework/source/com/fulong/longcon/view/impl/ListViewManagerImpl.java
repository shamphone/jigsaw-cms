package com.fulong.longcon.view.impl;

import java.sql.SQLException;
import com.fulong.common.dao.JdbcDaoFactory;
import com.fulong.common.dao.DatabaseException;
import com.fulong.longcon.view.ListView;
import com.fulong.longcon.view.dao.NodesViewDao;
import com.fulong.longcon.view.data.NodesViewData;
import javax.sql.DataSource;
import com.fulong.common.dao.JdbcDaoProvider;
import com.fulong.common.dao.PropertiesDaoProvider;
import com.fulong.longcon.view.ext.ListViewManagerExt;
import com.fulong.common.dao.DaoFactory;

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
public class ListViewManagerImpl implements ListViewManagerExt {
    private DataSource dataSource;
    private JdbcDaoProvider provider;
    private String dao = "oracle";

    public void init() {
        PropertiesDaoProvider provider = new PropertiesDaoProvider();
        provider.addMappingFile("com.fulong.longcon.view.dao." + dao);
        this.provider = provider;

    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 设置dao映射。在ForumService.getInstance()方法中通过java bean的反射机制来调用这个方法。
     *
     * @param dao String
     */
    public void setDao(String dao) {
        this.dao = dao;
    }

    public DaoFactory getDaoFactory() {
        return new JdbcDaoFactory(this.dataSource, this.provider);
    }

    /**
     * 获取指定参数的视图设置
     * @param name String
     * @return ListView
     */
    public ListView getListView(String name) {
        return new ListViewImpl(this, name);
    }

    /**
     * 创建一个列表视图
     * @param name String
     * @param columns String[]
     * @return ListView
     */
    public ListView createListView(String name, String[] columns) {
        DaoFactory factory = this.getDaoFactory();

        try {
            factory.open();
            NodesViewDao dao = (NodesViewDao) factory.getDao(NodesViewDao.class);
            if(columns!=null){
            for (int i = 0; i < columns.length; i++) {
                NodesViewData data = new NodesViewData();
                data.setNodeDefinitionID(name);
                data.setProDefinitionID(columns[i]);
                data.setWidth(10); //set 10 by default
                data.setOrderNo(i + 1);
                dao.insert(data);
            }
        }
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        } finally {
            factory.close();
        }
        return this.getListView(name);
    }

}
