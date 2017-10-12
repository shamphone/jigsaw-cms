package com.fulong.common.email.impl;

import com.fulong.common.email.Email;
import com.fulong.common.email.EmailException;
import junit.framework.TestCase;
import java.io.InputStream;
import java.io.FileInputStream;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

/**
 *
 * <p>Title: 龙驭会员管理系统2.0</p>
 *
 * <p>Description: 龙驭会员管理系统2.0</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author lixf
 * @version 2.0
 */
public class TestBasicEmailRepository
    extends TestCase {
  private BasicEmailRepository repository = null;

  protected void setUp() throws Exception {
    super.setUp();
    FileSystemResource   is = new FileSystemResource ("E:\\passport2.0\\branches\\passport2.0.1\\Tomcat\\webapps\\member\\WEB-INF\\config.xml");
    XmlBeanFactory factory = new XmlBeanFactory(is);

    repository = (BasicEmailRepository)factory.getBean("email");
  }

  protected void tearDown() throws Exception {
    repository = null;
    super.tearDown();
  }

  public void testCreateSimpleEmail() throws EmailException {
    Email expectedReturn = null;
    Email email = repository.createSimpleEmail();
    email.setMsg("This is subject.");
    email.setSubject("This is subject");
    email.addTo("lixf@fulong.com.cn");
    email.send();
    /**@todo fill in the test code*/
  }

}
