package com.fulong.longcon.site.dao.mysql;

import org.apache.commons.dbcp.BasicDataSource;

import junit.framework.TestCase;
import com.fulong.common.dao.JdbcDaoFactory;
import com.fulong.common.dao.PropertiesDaoProvider;


/**
 * 
 * 
 * Coolink协同工作框架模型
 * 
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 * 
 * Company: 北京中科辅龙计算机技术股份有限公司
 * 
 * @author LJY
 * 
 * @version 2.0
 * 
 */
public class MysqlSiteDaoTestCase extends TestCase {
    protected JdbcDaoFactory factory;
    protected BasicDataSource datasource;
    /**
     * Sets up the fixture, for example, open a network connection.
     * This method is called before a test is executed.
     */
    protected void setUp() throws Exception {
        datasource = new BasicDataSource();
        datasource.setDriverClassName("com.mysql.jdbc.Driver");
        datasource.setUrl("jdbc:mysql://192.168.0.81:3306/coolinkclean");
        datasource.setUsername("root");
        datasource.setPassword("fulong");
        PropertiesDaoProvider provider = new PropertiesDaoProvider();
        provider.addMappingFile("com.fulong.longcon.site.mysql");
        factory = new JdbcDaoFactory(datasource, provider);
        factory.open(false);

    }

    /**
     * Tears down the fixture, for example, close a network connection.
     * This method is called after a test is executed.
     */
    protected void tearDown() throws Exception {
         factory.close();
         super.tearDown();
    }
}
