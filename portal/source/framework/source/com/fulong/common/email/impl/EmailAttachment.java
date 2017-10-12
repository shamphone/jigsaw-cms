package com.fulong.common.email.impl;

import java.net.URL;

/**
 * This class models an email attachment.  Used by MultiPartEmail.
 *
 * @since 1.0
 * @author <a href="mailto:frank.kim@clearink.com">Frank Y. Kim</a>
 * @version $Id: EmailAttachment.java 225600 2005-07-27 20:16:23Z rdonkin $
 */
public class EmailAttachment
{
    /** Definition of the part being an attachment */
    public static final String ATTACHMENT = javax.mail.Part.ATTACHMENT;
    /** Definition of the part being inline */
    public static final String INLINE = javax.mail.Part.INLINE;

    /** The name of this attachment. */
    private String name = "";

    /** The description of this attachment. */
    private String description = "";

    /** The path to this attachment (ie c:/path/to/file.jpg). */
    private String path = "";

    /** The HttpURI where the file can be got. */
    private URL url;

    /** The disposition. */
    private String disposition = EmailAttachment.ATTACHMENT;

    /**
     * Get the description.
     *
     * @return A String.
     * @since 1.0
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Get the name.
     *
     * @return A String.
     * @since 1.0
     */
    public String getName()
    {
        return name;
    }

    /**
     * Get the path.
     *
     * @return A String.
     * @since 1.0
     */
    public String getPath()
    {
        return path;
    }

    /**
     * Get the URL.
     *
     * @return A URL.
     * @since 1.0
     */
    public URL getURL()
    {
        return url;
    }

    /**
     * Get the disposition.
     *
     * @return A String.
     * @since 1.0
     */
    public String getDisposition()
    {
        return disposition;
    }

    /**
     * Set the description.
     *
     * @param desc A String.
     * @since 1.0
     */
    public void setDescription(String desc)
    {
        this.description = desc;
    }

    /**
     * Set the name.
     *
     * @param aName A String.
     * @since 1.0
     */
    public void setName(String aName)
    {
        this.name = aName;
    }

    /**
     * Set the path to the attachment.  The path can be absolute or relative
     * and should include the filename.
     * <p>
     * Example: /home/user/images/image.jpg<br>
     * Example: images/image.jpg
     *
     * @param aPath A String.
     * @since 1.0
     */
    public void setPath(String aPath)
    {
        this.path = aPath;
    }

    /**
     * Set the URL.
     *
     * @param aUrl A URL.
     * @since 1.0
     */
    public void setURL(URL aUrl)
    {
        this.url = aUrl;
    }

    /**
     * Set the disposition.
     *
     * @param aDisposition A String.
     * @since 1.0
     */
    public void setDisposition(String aDisposition)
    {
        this.disposition = aDisposition;
    }
}
