package com.fulong.common.email.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.fulong.common.email.Email;
import com.fulong.common.email.EmailException;

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
public abstract class EmailImpl
    implements Email {
  public static final String TEXT_PLAIN = "text/plain";

  /** The email message to send. */
  protected MimeMessage message;

  /** The charset to use for this message */
  protected String charset;

  /** The Address of the sending party, mandatory */
  protected InternetAddress fromAddress;

  /** The Subject  */
  protected String subject;

  /** An attachment  */
  protected MimeMultipart emailBody;

  /** The content  */
  protected Object content;

  /** The content type  */
  protected String contentType;

  /** Sent date */
  protected Date sentDate;

  /** List of "to" email adresses */
  protected List<InternetAddress> toList = new ArrayList<InternetAddress>();

  /** List of "cc" email adresses */
  protected List<InternetAddress> ccList = new ArrayList<InternetAddress>();

  /** List of "bcc" email adresses */
  protected List<InternetAddress> bccList = new ArrayList<InternetAddress>();

  /** List of "replyTo" email adresses */
  protected List<InternetAddress> replyList = new ArrayList<InternetAddress>();

  /**
   * Used to specify the mail headers.  Example:
   *
   * X-Mailer: Sendmail, X-Priority: 1( highest )
   * or  2( high ) 3( normal ) 4( low ) and 5( lowest )
   * Disposition-Notification-To: user@domain.net
   */
  protected Map<String, String> headers = new HashMap<String, String>();


  /** The Session to mail with */
  protected Session session;
  protected BasicEmailRepository repository;

  public EmailImpl() {
  }

  public void init(BasicEmailRepository repository, Session session) {
    this.session = session;
    this.repository = repository;

  }

  /**
   * Set the charset of the message.
   *
   * @param newCharset A String.
   * @since 1.0
   */
  public void setCharset(String newCharset) {
    this.charset = newCharset;
  }

  /**
   * Set the emailBody to a MimeMultiPart
   *
   * @param aMimeMultipart aMimeMultipart
   * @since 1.0
   */
  public void setContent(MimeMultipart aMimeMultipart) {
    this.emailBody = aMimeMultipart;
  }

  /**
   * Set the content & contentType
   *
   * @param   aObject aObject
   * @param   aContentType aContentType
   * @since 1.0
   */
  public void setContent(Object aObject, String aContentType) {
    this.content = aObject;
    if (EmailUtils.isEmpty(aContentType)) {
      this.contentType = null;
    }
    else {
      // set the content type
      this.contentType = aContentType;

      // set the charset if the input was properly formed
      String strMarker = "; charset=";
      int charsetPos = aContentType.toLowerCase().indexOf(strMarker);

      if (charsetPos != -1) {
        // find the next space (after the marker)
        charsetPos += strMarker.length();
        int intCharsetEnd =
            aContentType.toLowerCase().indexOf(" ", charsetPos);

        if (intCharsetEnd != -1) {
          this.charset =
              aContentType.substring(charsetPos, intCharsetEnd);
        }
        else {
          this.charset = aContentType.substring(charsetPos);
        }
      }
    }
  }

  /**
   * Creates a InternetAddress.
   *
   * @param email An email address.
   * @param name A name.
   * @return An internet address.
   * @throws EmailException Thrown when the address supplied or name were invalid.
   */
  private InternetAddress createInternetAddress(String email, String name) throws
      EmailException {
    InternetAddress address = null;

    try {
      // check name input
      if (EmailUtils.isEmpty(name)) {
        name = email;
      }

      // Using this instead of new InternetAddress(email, name, [charset]) makes
      // commons-email usable with javamail 1.2 / J2EE 1.3
      address = new InternetAddress(email);

      if (EmailUtils.isNotEmpty(this.charset)) {
        address.setPersonal(name, this.charset);
      }
      else {
        address.setPersonal(name);
      }
    }
    catch (Exception e) {
      throw new EmailException(e);
    }
    return address;
  }

  /**
   * Set the FROM field of the email.
   *
   * @param email A String.
   * @return An Email.
   * @throws EmailException Indicates an invalid email address.
   * @since 1.0
   */
  public Email setFrom(String email) throws EmailException {
    return setFrom(email, null);
  }

  /**
   * Set the FROM field of the email.
   *
   * @param email A String.
   * @param name A String.
   * @throws EmailException Indicates an invalid email address.
   * @return An Email.
   * @since 1.0
   */
  public Email setFrom(String email, String name) throws EmailException {
    this.fromAddress = createInternetAddress(email, name);

    return this;
  }

  /**
   * Add a recipient TO to the email.
   *
   * @param email A String.
   * @throws EmailException Indicates an invalid email address.
   * @return An Email.
   * @since 1.0
   */
  public Email addTo(String email) throws EmailException {
    return addTo(email, null);
  }

  /**
   * Add a recipient TO to the email.
   *
   * @param email A String.
   * @param name A String.
   * @throws EmailException Indicates an invalid email address.
   * @return An Email.
   * @since 1.0
   */
  public Email addTo(String email, String name) throws EmailException {
    this.toList.add(createInternetAddress(email, name));
    return this;
  }

  /**
   * Set a list of "TO" addresses.
   *
   * @param  aCollection collection of InternetAddress objects.
   * @throws EmailException Indicates an invalid email address.
   * @return An Email.
   * @since 1.0
   */
public Email setTo(Collection<InternetAddress> aCollection) throws EmailException {
    if (aCollection == null || aCollection.isEmpty()) {
      throw new EmailException("Address List provided was invalid");
    }

    this.toList = new ArrayList<InternetAddress>(aCollection);
    return this;
  }

  /**
   * Add a recipient CC to the email.
   *
   * @param email A String.
   * @return An Email.
   * @throws EmailException Indicates an invalid email address.
   * @since 1.0
   */
  public Email addCc(String email) throws EmailException {
    return this.addCc(email, null);
  }

  /**
   * Add a recipient CC to the email.
   *
   * @param email A String.
   * @param name A String.
   * @throws EmailException Indicates an invalid email address.
   * @return An Email.
   * @since 1.0
   */
  public Email addCc(String email, String name) throws EmailException {
    this.ccList.add(createInternetAddress(email, name));
    return this;
  }

  /**
   * Set a list of "CC" addresses.
   *
   * @param aCollection collection of InternetAddress objects.
   * @return An Email.
   * @throws EmailException Indicates an invalid email address
   * @since 1.0.
   */
  public Email setCc(Collection<InternetAddress> aCollection) throws EmailException {
    if (aCollection == null || aCollection.isEmpty()) {
      throw new EmailException("Address List provided was invalid");
    }

    this.ccList = new ArrayList<InternetAddress>(aCollection);
    return this;
  }

  /**
   * Add a blind BCC recipient to the email.
   *
   * @param email A String.
   * @return An Email.
   * @throws EmailException Indicates an invalid email address
   * @since 1.0
   */
  public Email addBcc(String email) throws EmailException {
    return this.addBcc(email, null);
  }

  /**
   * Add a blind BCC recipient to the email.
   *
   * @param email A String.
   * @param name A String.
   * @return An Email.
   * @throws EmailException Indicates an invalid email address
   * @since 1.0
   */
  public Email addBcc(String email, String name) throws EmailException {
    this.bccList.add(createInternetAddress(email, name));
    return this;
  }

  /**
   * Set a list of "BCC" addresses
   *
   * @param   aCollection collection of InternetAddress objects
   * @return  An Email.
   * @throws EmailException Indicates an invalid email address
   * @since 1.0
   */
  public Email setBcc(Collection<InternetAddress> aCollection) throws EmailException {
    if (aCollection == null || aCollection.isEmpty()) {
      throw new EmailException("Address List provided was invalid");
    }

    this.bccList = new ArrayList<InternetAddress>(aCollection);
    return this;
  }

  /**
   * Add a reply to address to the email.
   *
   * @param email A String.
   * @return An Email.
   * @throws EmailException Indicates an invalid email address
   * @since 1.0
   */
  public Email addReplyTo(String email) throws EmailException {
    return this.addReplyTo(email, null);
  }

  /**
   * Add a reply to address to the email.
   *
   * @param email A String.
   * @param name A String.
   * @return An Email.
   * @throws EmailException Indicates an invalid email address
   * @since 1.0
   */
  public Email addReplyTo(String email, String name) throws EmailException {
    this.replyList.add(createInternetAddress(email, name));
    return this;
  }

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
public void setHeaders(Map map) {
    Iterator iterKeyBad = map.entrySet().iterator();

    while (iterKeyBad.hasNext()) {
      Map.Entry entry = (Map.Entry) iterKeyBad.next();
      String strName = (String) entry.getKey();
      String strValue = (String) entry.getValue();

      if (EmailUtils.isEmpty(strName)) {
        throw new IllegalArgumentException("name can not be null");
      }
      if (EmailUtils.isEmpty(strValue)) {
        throw new IllegalArgumentException("value can not be null");
      }
    }

    // all is ok, update headers
    this.headers = map;
  }

  /**
   * Adds a header ( name, value ) to the headers Map.
   *
   * @param name A String with the name.
   * @param value A String with the value.
   * @since 1.0
   */
  public void addHeader(String name, String value) {
    if (EmailUtils.isEmpty(name)) {
      throw new IllegalArgumentException("name can not be null");
    }
    if (EmailUtils.isEmpty(value)) {
      throw new IllegalArgumentException("value can not be null");
    }

    this.headers.put(name, value);
  }

  /**
   * Set the email subject.
   *
   * @param aSubject A String.
   * @return An Email.
   * @since 1.0
   */
  public Email setSubject(String aSubject) {
    this.subject = aSubject;
    return this;
  }

  /**
   * Define the content of the mail.  It should be overidden by the
   * subclasses.
   *
   * @param msg A String.
   * @return An Email.
   * @throws EmailException generic exception.
   * @since 1.0
   */
  public abstract Email setMsg(String msg) throws EmailException;

  /**
   * Build the internal MimeMessage to be sent.
   *
   * @throws EmailException if there was an error.
   * @since 1.0
   */
  public void buildMimeMessage() throws EmailException {
    try {

      this.message = new MimeMessage(this.session);

      if (EmailUtils.isNotEmpty(this.subject)) {
        if (EmailUtils.isNotEmpty(this.charset)) {
          this.message.setSubject(this.subject, this.charset);
        }
        else {
          this.message.setSubject(this.subject);
        }
      }

      // ========================================================
      // Start of replacement code
      if (this.content != null) {
        this.message.setContent(this.content, this.contentType);
      }
      // end of replacement code
      // ========================================================
      else if (this.emailBody != null) {
        this.message.setContent(this.emailBody);
      }
      else {
        this.message.setContent("", TEXT_PLAIN);
      }

      if (this.fromAddress != null) {
        this.message.setFrom(this.fromAddress);
      }
      else {
        throw new EmailException("Sender address required");
      }

      if (this.toList.size() + this.ccList.size() + this.bccList.size() == 0) {
        throw new EmailException(
            "At least one receiver address required");
      }

      if (this.toList.size() > 0) {
        this.message.setRecipients(
            Message.RecipientType.TO,
            this.toInternetAddressArray(this.toList));
      }

      if (this.ccList.size() > 0) {
        this.message.setRecipients(
            Message.RecipientType.CC,
            this.toInternetAddressArray(this.ccList));
      }

      if (this.bccList.size() > 0) {
        this.message.setRecipients(
            Message.RecipientType.BCC,
            this.toInternetAddressArray(this.bccList));
      }

      if (this.replyList.size() > 0) {
        this.message.setReplyTo(
            this.toInternetAddressArray(this.replyList));
      }

      if (this.headers.size() > 0) {
        Iterator<String> iterHeaderKeys = this.headers.keySet().iterator();
        while (iterHeaderKeys.hasNext()) {
          String name = (String) iterHeaderKeys.next();
          String value = (String) headers.get(name);
          this.message.addHeader(name, value);
        }
      }

      if (this.message.getSentDate() == null) {
        this.message.setSentDate(getSentDate());
      }

    }
    catch (MessagingException me) {
      throw new EmailException(me);
    }
  }

  /**
   * Sends the previously created MimeMessage to the SMTP server.
   *
   * @return the message id of the underlying MimeMessage
   * @throws EmailException the sending failed
   */
  public String sendMimeMessage() throws EmailException {
    EmailUtils.notNull(this.message, "message");

    try {
      Transport.send(this.message);
      return this.message.getMessageID();
    }
    catch (Throwable t) {
      String msg = "Sending the email to the following server failed : "
          + repository.getProperty("mail.smtp.host")
          + ":25";

      throw new EmailException(msg, t);
    }
  }

  /**
   * Returns the internal MimeMessage. Please not that the
   * MimeMessage is build by the buildMimeMessage() method.
   *
   * @return the MimeMessage
   */
  public MimeMessage getMimeMessage() {
    return this.message;
  }

  /**
   * Sends the email. Internally we build a MimeMessage
   * which is afterwards sent to the SMTP server.
   *
   * @return the message id of the underlying MimeMessage
   * @throws EmailException the sending failed
   */
  public String send() throws EmailException {
    this.buildMimeMessage();
    return this.sendMimeMessage();
  }

  /**
   * Sets the sent date for the email.  The sent date will default to the
   * current date if not explictly set.
   *
   * @param date Date to use as the sent date on the email
   * @since 1.0
   */
  public void setSentDate(Date date) {
    this.sentDate = date;
  }

  /**
   * Gets the sent date for the email.
   *
   * @return date to be used as the sent date for the email
   * @since 1.0
   */
  public Date getSentDate() {
    if (this.sentDate == null) {
      return new Date();
    }
    return this.sentDate;
  }

  /**
   * Gets the subject of the email.
   *
   * @return email subject
   */
  public String getSubject() {
    return this.subject;
  }

  /**
   * Gets the sender of the email.
   *
   * @return from address
   */
  public InternetAddress getFromAddress() {
    return this.fromAddress;
  }

  /**
   * Utility to copy List of known InternetAddress objects into an
   * array.
   *
   * @param list A List.
   * @return An InternetAddress[].
   * @since 1.0
   */
  protected InternetAddress[] toInternetAddressArray(List<InternetAddress> list) {
    InternetAddress[] ia =
        (InternetAddress[]) list.toArray(new InternetAddress[list.size()]);

    return ia;
  }

}

