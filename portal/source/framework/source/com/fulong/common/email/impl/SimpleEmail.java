package com.fulong.common.email.impl;

import com.fulong.common.email.EmailException;
import com.fulong.common.email.Email;
/**
 * This class is used to send simple internet email messages without
 * attachments.
 *
 * @since 1.0
 * @author <a href="mailto:quintonm@bellsouth.net">Quinton McCombs</a>
 * @author <a href="mailto:colin.chalmers@maxware.nl">Colin Chalmers</a>
 * @author <a href="mailto:jon@latchkey.com">Jon S. Stevens</a>
 * @author <a href="mailto:frank.kim@clearink.com">Frank Y. Kim</a>
 * @author <a href="mailto:bmclaugh@algx.net">Brett McLaughlin</a>
 * @author <a href="mailto:unknown">Regis Koenig</a>
 * @version $Id: SimpleEmail.java 279285 2005-09-07 09:52:44Z henning $
*/
public class SimpleEmail extends EmailImpl
{
    /**
     * Set the content of the mail
     *
     * @param msg A String.
     * @return An Email.
     * @throws EmailException see javax.mail.internet.MimeBodyPart
     *  for definitions
     * @since 1.0
     */
    public Email setMsg(String msg) throws EmailException
    {
        if (EmailUtils.isEmpty(msg))
        {
            throw new EmailException("Invalid message supplied");
        }

        setContent(msg, TEXT_PLAIN);
        return this;
    }
}
