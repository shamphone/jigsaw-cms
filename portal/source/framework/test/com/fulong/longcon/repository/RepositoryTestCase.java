package com.fulong.longcon.repository;

import java.io.UnsupportedEncodingException;
import java.net.ProxySelector;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.fulong.longcon.BasicTestCase;

/**
 *
 * <p>Title: 龙驭内容管理引擎</p>
 *
 * <p>Description: 龙驭内容管理引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 1.0
 */
public class RepositoryTestCase extends BasicTestCase {
    protected XmlBeanFactory beanFactory;
    protected Properties stringResources = null;
    protected Repository repository = null;
 
    protected void setUp() throws Exception {
        super.setUp();
        ProxySelector.setDefault(null);
        URL url = this.getClass().getClassLoader().getResource(
            "simplelog.properties");

        ClassPathResource resource = new ClassPathResource("test.config.xml");
        this.beanFactory = new XmlBeanFactory(resource);
        String[] names=this.beanFactory.getBeanDefinitionNames();
        for(int i=0;i<names.length;i++)
        	this.beanFactory.getBean(names[i]);
        this.repository = (Repository) beanFactory.getBean("repository");
        this.stringResources = new Properties();
        this.stringResources.load(this.getClass().getClassLoader().getResourceAsStream("test.properties"));
//        this.resourceManager = (ResourceManager) beanFactory.getBean("resourceManager");
 
    }

    protected String getTestString(String name) {
        try {
            return new String(this.stringResources.getProperty(name).getBytes(
                "ISO8859-1"), "UTF-8");
        }
        catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected String[] getTestStrings(String prefix) {
        Vector results = new Vector();
        for (Enumeration key = this.stringResources.keys(); key.hasMoreElements(); ) {
            String strKey = (String) key.nextElement();
            if (strKey.startsWith(prefix)) {
                results.add(this.getTestString(strKey));
            }
        }
        return (String[]) results.toArray(new String[results.size()]);
    }

    protected void tearDown() throws Exception {
       
        super.tearDown();
    }



}
