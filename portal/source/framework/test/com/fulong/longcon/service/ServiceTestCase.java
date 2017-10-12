package com.fulong.longcon.service;



import java.io.File;
import java.net.URL;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

import com.fulong.longcon.BasicTestCase;
import com.fulong.service.ServiceManager;

/**
*
* <p>Title: 服务模型系统</p>
*
* <p>Description: 服务模型系统</p>
*
* <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2009</p>
*
* <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
*
* @author liuzijun
* @version 1.0
*/


public class ServiceTestCase extends BasicTestCase {
	protected ServiceManager serviceManager;
	protected XmlBeanFactory beanFactory;
	protected void setUp() throws Exception {
        super.setUp();
        URL url = this.getClass().getClassLoader().getResource(
        "simplelog.properties");
        File file = new File(url.getFile());
        file = new File(file.getParentFile(), "config.xml");
        FileSystemResource resource = new FileSystemResource(file.getPath());
        this.beanFactory = new XmlBeanFactory(resource);
        this.serviceManager = (ServiceManager) this.beanFactory.getBean("serviceManager");        
    }
	protected void tearDown() throws Exception {
        super.tearDown();
    }
	protected ServiceManager getServiceManager() {
        return this.serviceManager;
    }
}