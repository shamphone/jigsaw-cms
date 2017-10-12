package com.fulong.longcon.repository.dao.sqlserver;

import org.apache.commons.dbcp.BasicDataSource;

import com.fulong.common.dao.JdbcDaoFactory;
import com.fulong.common.dao.PropertiesDaoProvider;

import junit.framework.TestCase;

/**sqlserver原始的testcase
 * @author songbo
 */
public class SqlserverDaoTestCase extends TestCase{

	protected JdbcDaoFactory factory;
    protected BasicDataSource datasource;
    /**
     * Sets up the fixture, for example, open a network connection.
     * This method is called before a test is executed.
     */
    protected void setUp() throws Exception {
        datasource = new BasicDataSource();
        datasource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        datasource.setUrl("jdbc:sqlserver://192.168.0.178:1433;DataBaseName=newCoolink");
        datasource.setUsername("sa");
        datasource.setPassword("sa");
        PropertiesDaoProvider provider = new PropertiesDaoProvider();
        provider.addMappingFile("com.fulong.longcon.counter.sqlserver");
        provider.addMappingFile("com.fulong.longcon.repository.impl.sqlserver");
        provider.addMappingFile("com.fulong.longcon.repository.definition.sqlserver");
        factory = new JdbcDaoFactory(datasource, provider);
        factory.open(false);

    }

    protected void tearDown() throws Exception {
         factory.close();
         super.tearDown();
    }
}
