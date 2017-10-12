package com.fulong.longcon.workflow.dao;

import junit.framework.*;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import java.net.URL;
import java.io.File;
import org.springframework.core.io.FileSystemResource;
import javax.sql.DataSource;
import com.fulong.common.dao.PropertiesDaoProvider;
import com.fulong.common.dao.JdbcDaoFactory;

/**
 *
 * <p>Title: 龙驭工作流系统</p>
 *
 * <p>Description: 龙驭工作流系统</p>
 *
 * <p>Copyright: Copyright (c)北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 1.0
 */
public class DaoTestCase extends TestCase {
    protected  JdbcDaoFactory factory;

    protected void setUp() throws Exception {
        URL url=this.getClass().getClassLoader().getResource("simplelog.properties");
         File file=new File(url.getFile());
         file=new File(file.getParentFile().getParent(),"config.xml");
         FileSystemResource resource = new FileSystemResource(file.getPath());
        XmlBeanFactory beanFactory= new XmlBeanFactory(resource);
        DataSource datasource=(DataSource)beanFactory.getBean("datasource");
        PropertiesDaoProvider provider = new PropertiesDaoProvider();
        provider.addMappingFile("com.fulong.longcon.workflow.dao.oracle");
        this.factory=new JdbcDaoFactory(datasource,provider);
        this.factory.open();
        super.setUp();
    }

    protected void tearDown() throws Exception {
        this.factory.close();
        super.tearDown();
    }



}
