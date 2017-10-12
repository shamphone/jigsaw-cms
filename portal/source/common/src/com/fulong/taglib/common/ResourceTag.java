package com.fulong.taglib.common;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.ResponseUtils;

import com.fulong.taglib.SpringTagSupport;
/**
 * <p>Title: 根据Resource输出属性</p>
 *
 * <p>Description: 网上培训系统</p>
 *
 * <p>Copyright: Copyright (c) 北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author <a href='mailto:jiangqihome5@fulong.com.cn'>jiangqi</a>
 * @version 1.0
 */
public class ResourceTag extends SpringTagSupport {
    
	private static final long serialVersionUID = 8674824155632095258L;

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

    private TagUtils utils = TagUtils.getInstance();
    private String name;
    private String property;
    private boolean ignore;
    private String scope;
    private String formatStr;
    private String localeKey;
    private String bundle;
    private String dictType;
    private int length;
    private String type;
    private boolean filter;
    private String seperator=" ";

    public void release(){
        this.seperator=" ";
    }

    public int doStartTag() throws JspException {
        String output = "";
        Object value = TagUtils.getInstance().lookup(pageContext, name,
            property, scope);
        if (ignore) {
            try {
                if (TagUtils.getInstance().lookup(pageContext, name, scope) == null) {
                    return (SKIP_BODY); // Nothing to output
                }
                if (value == null) {
                    return (SKIP_BODY); // Nothing to output
                }
            } catch (Exception ex1) {
                utils.write(pageContext, output);
                return (SKIP_BODY);
            }

        }
        if (value != null)
            output = this.formatValue(value);

        if (this.length != 0) {
            if (this.length > output.length()) {
                //nothing
            } else {
                output = output.substring(0, this.length) + "...";
            }
        }
       
        if (this.filter) {
            output = this.filter(output);
        }
        utils.write(pageContext, output);
        return (SKIP_BODY);

    }

    private String filter(String output) {
        output = ResponseUtils.filter(output);
        output = output.replaceAll("\r\n", "</br>");
        return output;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setLocaleKey(String localeKey) {
        this.localeKey = localeKey;
    }

    public void setBundle(String bundle) {
        this.bundle = bundle;
    }

    public void setDictType(String dictType) {

        this.dictType = dictType;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    public void setSeperator(String seperator) {
        this.seperator = seperator;
    }

    public void setLenght(int lenght) {
        this.length = lenght;
    }

    public void setFormat(String format) {
        this.formatStr = format;
    }

    public String getName() {
        return name;
    }

    public String getProperty() {
        return property;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public String getScope() {
        return scope;
    }

    public String getLocaleKey() {
        return localeKey;
    }

    public String getBundle() {
        return bundle;
    }

    public String getDictType() {

        return dictType;
    }

    public int getLength() {
        return length;
    }

    public String getType() {
        return type;
    }

    public boolean isFilter() {
        return filter;
    }

    public String getSeperator() {
        return seperator;
    }

    public int getLenght() {
        return length;
    }

    public String getFormat() {
        return formatStr;
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
                    if ( (value instanceof Byte)
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
                            ( (DecimalFormat) format).applyLocalizedPattern(
                                formatString);
                        } else {
                            ( (DecimalFormat) format).applyPattern(formatString);
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

        if ( (result != null)
            && ! (result.startsWith("???") && result.endsWith("???"))) {

            return result;

        } else {
            return null;
        }

    }

}
