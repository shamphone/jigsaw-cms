package com.fulong.common.email.impl;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Session;

import com.fulong.common.email.EmailException;
import com.fulong.common.email.EmailRepository;
import com.fulong.common.email.Email;

/**
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
public class BasicEmailRepository
    extends EmailRepository {
  private Properties properties;
  private String username;
  private String password;
  private String fromUser;
  /**
   * Instance of an <code>Authenticator</code> object that will be used
   * when authentication is requested from the mail server.
   */
  protected Authenticator authenticator;
  private boolean popBeforeSmtp;
  private Session session;

  public BasicEmailRepository() {
    popBeforeSmtp = false;
  }

  public void init() {
    this.authenticator=new DefaultAuthenticator(this.username,this.password);
    this.session = Session.getDefaultInstance(this.properties,
                                              this.authenticator);
  }

  /**
   * 设置系统属性
   * @param properties Properties
   */
  public void setProperties(Properties properties) {
    this.properties = properties;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setFromUser(String fromUser) {
    this.fromUser = fromUser;
  }

  public void setPopBeforeSmtp(boolean popBeforeSmtp) {
    this.popBeforeSmtp = popBeforeSmtp;
  }


  public boolean isPopBeforeSmtp() {
    return popBeforeSmtp;
  }

  protected String getProperty(String name) {
    return this.properties.getProperty(name);
  }

  public Email createSimpleEmail() throws EmailException {
    SimpleEmail email=new SimpleEmail();
    email.init(this,this.session);
    email.setFrom(this.fromUser);
    return email;
  }
}
