package com.fulong.taglib.cms;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.ResponseUtils;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.resource.Resource;
import com.fulong.longcon.security.Organization;
import com.fulong.longcon.security.User;
import com.fulong.taglib.SpringTagSupport;

/**
 * <p>Title: 龙驭内容管理系统</p>
 *
 * <p>Description: 龙驭内容管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author liuzijun
 * @version 3.0
 */
public class FixPropertyTag extends SpringTagSupport {
    
	private static final long serialVersionUID = -8034749450164259840L;

	/**
     * The key to search default format string for java.sql.Timestamp in resources.
     */
    public static final String SQL_TIMESTAMP_FORMAT_KEY =
            "org.apache.struts.taglib.bean.format.sql.timestamp";

    /**
     * The key to search default format string for java.sql.Date in resources.
     */
    public static final String SQL_DATE_FORMAT_KEY =
            "org.apache.struts.taglib.bean.format.sql.date";

    /**
     * The key to search default format string for java.sql.Time in resources.
     */
    public static final String SQL_TIME_FORMAT_KEY =
            "org.apache.struts.taglib.bean.format.sql.time";

    /**
     * The key to search default format string for java.util.Date in resources.
     */
    public static final String DATE_FORMAT_KEY =
            "org.apache.struts.taglib.bean.format.date";

    /**
     * The key to search default format string for int (byte, short, etc.) in resources.
     */
    public static final String INT_FORMAT_KEY =
            "org.apache.struts.taglib.bean.format.int";

    /**
     * The key to search default format string for float (double, BigDecimal) in
     * resources.
     */
    public static final String FLOAT_FORMAT_KEY =
            "org.apache.struts.taglib.bean.format.float";

    protected static MessageResources messages =
            MessageResources.getMessageResources(
                    "org.apache.struts.taglib.bean.LocalStrings");

    private String name;
    private String property;
    private String propertyName;
    private String scope;
    private String formatStr;
    private String localeKey;
    private String bundle;
    private int length;
    private boolean ignore;
    private TagUtils utils = TagUtils.getInstance();
    private String seperator = " ";

    public void release() {
        this.seperator = " ";
    }

    public FixPropertyTag() {
        ignore = true;
    }


    @SuppressWarnings("deprecation")
	public int doStartTag() throws JspException {
        Node thisContent = null;
        Object o = null;
        StringBuffer value = new StringBuffer();
        try {
            o = utils.lookup(pageContext, name, scope);

        } catch (Exception ex) {

        }
        if (o != null && o instanceof Node) {
            thisContent = (Node) o;
        }

        if (thisContent == null) {
            if (ignore) {
                return (SKIP_BODY);
            } else {
                throw new JspException("Unable to find content!");
            }
        }
        NodeIterator<?> nodes = thisContent.getNodes(this.property);
        String proName = this.propertyName;
        String tempStr = "";
        while (nodes.hasNext()) {
            Node node = nodes.nextNode();
            if (proName.equals("commonname")) {
                if ((node instanceof User)) {
                    tempStr = tempStr + ((User) node).getCommonname() + "  ";
                } else if (node instanceof Organization) {
                    tempStr = tempStr + ((Organization) node).getCommonname() +
                              "  ";
                }
            } else {
                Property oProperty = node.getProperty(proName);
                if (oProperty != null) {
                    tempStr =  "  "+tempStr + this.formatProperty(oProperty);
                } else
                    try {
                        oProperty = (Property) PropertyUtils.getProperty(node,
                                proName);
                        tempStr = "  "+tempStr + formatValue(oProperty);
                    } catch (Exception ex) {
                        if (!this.ignore) {
                            throw new JspException("Unable to find property " +
                                    this.property + ".");
                        }
                    }
                if (this.length != 0) {
                    if (this.length > tempStr.length()) {
                        //nothing
                    } else {
                        tempStr = tempStr.substring(0, this.length) + "...";
                    }
                }
            }
        }
        value.append(tempStr);
        ResponseUtils.write(pageContext, value.toString());
        return (SKIP_BODY);
    }


    public int doEndTag() throws JspException {

        // Clean up our started state
        name = null;
        property = null;
        ignore = true;

        // Continue processing this page
        return (EVAL_PAGE);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    public void setLocaleKey(String localeKey) {
        this.localeKey = localeKey;
    }

    public void setBundle(String bundle) {
        this.bundle = bundle;
    }

    public void setFormat(String format) {
        this.formatStr = format;
    }

    public String getLocaleKey() {
        return localeKey;
    }

    public String getBundle() {
        return bundle;
    }

    public int getLength() {
            return length;
    }

    public String getFormat() {
        return formatStr;
    }


    protected String formatProperty(Property property) throws JspException {
        try {
            if (property.getValue() == null) {
                return "";
            }
        } catch (Exception ex) {
        }
        if (property.getValues().length == 0)
            return "";
        Value[] values = property.getValues();
        StringBuffer output = new StringBuffer("");
        if ((this.formatStr == null) || (this.formatStr.length() == 0)) {
            for (int i = 0; i < values.length; i++) {
                if (i > 1)
                    output.append(this.seperator);
                if (values[i].getString() != null)
                    output.append(values[i].getString());
            }
            return output.toString();
        }

        int type = property.getDefinition().getType();
        String contextPath = ((HttpServletRequest)this.pageContext.getRequest()).
                             getContextPath();
        for (int i = 0; i < values.length; i++) {
            if (values[i].getString() == null)
                output.append("");
            else {
                if (i > 1)
                    output.append(this.seperator);
                switch (type) {
                case PropertyType.DATE:
                    Calendar date = values[i].getDate();
                    SimpleDateFormat format = new SimpleDateFormat(this.
                            formatStr);
                    output.append(format.format(date.getTime()));
                    break;
                case PropertyType.DOUBLE:
                    DecimalFormat dformat = new DecimalFormat(this.
                            formatStr);
                    output.append(dformat.format(values[i].getDouble()));
                    break;
                case PropertyType.LONG:
                    DecimalFormat lformat = new DecimalFormat(this.
                            formatStr);
                    output.append(lformat.format(values[i].getLong()));
                    break;
                case PropertyType.REFERENCE: //引用属性,目前仅处理resource；
                    Resource resource = this.getResourceManager().
                                        getResource(values[i].getString());
                    if (resource != null) {
                        String path = contextPath + resource.getPath();
                        output.append(this.formatStr.replaceAll("#", path));
                    } else {
                        Node content = this.getRepository().getNode(values[i].
                                getString());
                        if (content != null)
                            output.append(this.formatStr.replaceAll("#",
                            		content.getProperty("title").getString()));
                    }
                    break;
                default:
                    String value = values[i].getString();
                /*
                    if (property.getDefinition().getEnumEntry() != null) {
                        Entry entry = this.getDictManager().getRootEntry().
                                      getEntry(property.getDefinition().
                                               getEnumEntry());
                        if (entry != null)
                            entry = entry.getEntry(value);
                        if (entry != null)
                            value = entry.getLabel();
                    }
                    */
                    if (value == null)
                        output.append("");
                    else if ("#".equalsIgnoreCase(formatStr))
                        return value;
                    else
                        output.append(this.formatStr.replaceAll("#", value));
                    break;
                }
            }
        }
        return output.toString();
    }

    protected String formatValue(Object valueToFormat) throws JspException {
        Format format = null;
        Object value = valueToFormat;
        Locale locale = TagUtils.getInstance().getUserLocale(pageContext,
                this.localeKey);
        boolean formatStrFromResources = false;
        String formatString = this.formatStr;
        if (value == null) {
            return "";
        }

        // Return String object as is.
        if (value instanceof java.lang.String) {
            return (String) value;
        } else {

            // Try to retrieve format string from resources by the key from formatKey.
            /*
                         if ((formatString == null) && (formatKey != null)) {
                formatString = retrieveFormatString(this.formatKey);
                if (formatString != null) {
                    formatStrFromResources = true;
                }
                         }
             */

            // Prepare format object for numeric values.
            if (value instanceof Number) {

                if (formatString == null) {
                    if ((value instanceof Byte)
                        || (value instanceof Short)
                        || (value instanceof Integer)
                        || (value instanceof Long)
                        || (value instanceof BigInteger)) {

                        formatString = retrieveFormatString(INT_FORMAT_KEY);

                    } else if (
                            (value instanceof Float)
                            || (value instanceof Double)
                            || (value instanceof BigDecimal)) {

                        formatString = retrieveFormatString(FLOAT_FORMAT_KEY);
                    }

                    if (formatString != null) {
                        formatStrFromResources = true;
                    }
                }

                if (formatString != null) {
                    try {
                        format = NumberFormat.getNumberInstance(locale);
                        if (formatStrFromResources) {
                            ((DecimalFormat) format).applyLocalizedPattern(
                                    formatString);
                        } else {
                            ((DecimalFormat) format).applyPattern(formatString);
                        }

                    } catch (IllegalArgumentException e) {
                        JspException ex =
                                new JspException(
                                        messages.getMessage("write.format",
                                formatString));
                        TagUtils.getInstance().saveException(pageContext, ex);
                        throw ex;
                    }
                }

            } else if (value instanceof java.util.Date) {

                if (formatString == null) {

                    if (value instanceof java.sql.Timestamp) {
                        formatString =
                                retrieveFormatString(SQL_TIMESTAMP_FORMAT_KEY);

                    } else if (value instanceof java.sql.Date) {
                        formatString = retrieveFormatString(SQL_DATE_FORMAT_KEY);

                    } else if (value instanceof java.sql.Time) {
                        formatString = retrieveFormatString(SQL_TIME_FORMAT_KEY);

                    } else if (value instanceof java.util.Date) {
                        formatString = retrieveFormatString(DATE_FORMAT_KEY);
                    }

                }

                if (formatString != null) {
                    format = new SimpleDateFormat(formatString, locale);
                }
            }
        }

        if (format != null) {
            return format.format(value);
        } else {
            return value.toString();
        }

    }

    protected String retrieveFormatString(String formatKey) throws JspException {
        String result =
                TagUtils.getInstance().message(
                        pageContext,
                        this.bundle,
                        this.localeKey,
                        formatKey);

        if ((result != null)
            && !(result.startsWith("???") && result.endsWith("???"))) {

            return result;

        } else {
            return null;
        }

    }


}
