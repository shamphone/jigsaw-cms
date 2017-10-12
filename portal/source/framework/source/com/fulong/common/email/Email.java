package com.fulong.common.email;

import java.util.Date;
import java.util.Map;
import javax.mail.internet.MimeMessage;
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
public interface Email {
  /**
   * Add a blind BCC recipient to the email.
   *
   * @param email A String.
   * @return An Email.
   * @throws EmailException Indicates an invalid email address
   * @since 1.0
   */
  public Email addBcc(String email) throws EmailException;

  /**
   * Add a blind BCC recipient to the email.
   *
   * @param email A String.
   * @param name A String.
   * @return An Email.
   * @throws EmailException Indicates an invalid email address
   * @since 1.0
   */
  public Email addBcc(String email, String name) throws EmailException;

  /**
   * Add a recipient CC to the email.
   *
   * @param email A String.
   * @return An Email.
   * @throws EmailException Indicates an invalid email address.
   * @since 1.0
   */
  public Email addCc(String email) throws EmailException;

  /**
   * Add a recipient CC to the email.
   *
   * @param email A String.
   * @param name A String.
   * @throws EmailException Indicates an invalid email address.
   * @return An Email.
   * @since 1.0
   */
  public Email addCc(String email, String name) throws EmailException;

  /**
   * Adds a header ( name, value ) to the headers Map.
   *
   * @param name A String with the name.
   * @param value A String with the value.
   * @since 1.0
   */
  public void addHeader(String name, String value);

  /**
   * Add a reply to address to the email.
   *
   * @param email A String.
   * @return An Email.
   * @throws EmailException Indicates an invalid email address
   * @since 1.0
   */
  public Email addReplyTo(String email) throws EmailException;

  /**
   * Add a reply to address to the email.
   *
   * @param email A String.
   * @param name A String.
   * @return An Email.
   * @throws EmailException Indicates an invalid email address
   * @since 1.0
   */
  public Email addReplyTo(String email, String name) throws EmailException;

  /**
   * Add a recipient TO to the email.
   *
   * @param email A String.
   * @throws EmailException Indicates an invalid email address.
   * @return An Email.
   * @since 1.0
   */
  public Email addTo(String email) throws EmailException;

  /**
   * Add a recipient TO to the email.
   *
   * @param email A String.
   * @param name A String.
   * @throws EmailException Indicates an invalid email address.
   * @return An Email.
   * @since 1.0
   */
  public Email addTo(String email, String name) throws EmailException;

  /**
   * Returns the internal MimeMessage. Please not that the
   * MimeMessage is build by the buildMimeMessage() method.
   *
   * @return the MimeMessage
   */
  public MimeMessage getMimeMessage();

  /**
   * Gets the sent date for the email.
   *
   * @return date to be used as the sent date for the email
   * @since 1.0
   */
  public Date getSentDate();

  /**
   * Gets the subject of the email.
   *
   * @return email subject
   */
  public String getSubject();

  /**
   * Sends the email. Internally we build a MimeMessage
   * which is afterwards sent to the SMTP server.
   *
   * @return the message id of the underlying MimeMessage
   * @throws EmailException the sending failed
   */
  public String send() throws EmailException;

  /**
   * Sends the previously created MimeMessage to the SMTP server.
   *
   * @return the message id of the underlying MimeMessage
   * @throws EmailException the sending failed
   */
  public String sendMimeMessage() throws EmailException;


  /**
   * Set the FROM field of the email.
   *
   * @param email A String.
   * @param name A String.
   * @throws EmailException Indicates an invalid email address.
   * @return An Email.
   * @since 1.0
   */
  public Email setFrom(String email, String name) throws EmailException;

  /**
   * Used to specify the mail headers.  Example:
   *
   * X-Mailer: Sendmail, X-Priority: 1( highest )
   * or  2( high ) 3( normal ) 4( low ) and 5( lowest )
   * Disposition-Notification-To: user@domain.net
   *
   * @param map A Map.
   * @since 1.0
   */
  @SuppressWarnings("unchecked")
public void setHeaders(Map map);

  /**
   * Define the content of the mail.  It should be overidden by the
   * subclasses.
   *
   * @param msg A String.
   * @return An Email.
   * @throws EmailException generic exception.
   * @since 1.0
   */
  public Email setMsg(String msg) throws EmailException;

  /**
   * Sets the sent date for the email.  The sent date will default to the
   * current date if not explictly set.
   *
   * @param date Date to use as the sent date on the email
   * @since 1.0
   */
  public void setSentDate(Date date);

  /**
   * Set the email subject.
   *
   * @param aSubject A String.
   * @return An Email.
   * @since 1.0
   */
  public Email setSubject(String aSubject);

}
