package com.fulong.longcon.repository.dao.mysql;

import org.apache.commons.dbcp.BasicDataSource;

import com.fulong.common.dao.JdbcDaoFactory;
import com.fulong.common.dao.PropertiesDaoProvider;

import junit.framework.TestCase;

/**mysql原始的testcase
 * @author songbo
 */
public class MysqlDaoTestCase extends TestCase{

	protected JdbcDaoFactory factory;
    protected BasicDataSource datasource;
    /**
     * Sets up the fixture, for example, open a network connection.
     * This method is called before a test is executed.
     */
    protected void setUp() throws Exception {
        datasource = new BasicDataSource();
        datasource.setDriverClassName("com.mysql.jdbc.Driver");
        datasource.setUrl("jdbc:mysql://192.168.0.177/coolink?");//user=root&password=fulong
        datasource.setUsername("root");
        datasource.setPassword("fulong");
        PropertiesDaoProvider provider = new PropertiesDaoProvider();
        provider.addMappingFile("com.fulong.longcon.counter.mysql");
        provider.addMappingFile("com.fulong.longcon.repository.impl.mysql");
        provider.addMappingFile("com.fulong.longcon.repository.definition.mysql");
        factory = new JdbcDaoFactory(datasource, provider);
        factory.open(false);

    }

    protected void tearDown() throws Exception {
         factory.close();
         super.tearDown();
    }
}
