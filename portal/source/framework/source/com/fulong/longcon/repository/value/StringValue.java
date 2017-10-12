package com.fulong.longcon.repository.value;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.RepositoryException;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.ValueFormatException;

/**
 *
 * <p>Title: Coolink协同工作支撑平台</p>
 *
 * <p>Description: Coolink协同工作支撑平台</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 1.0
 */
public class StringValue
    extends BasicValue {
    private String value;
    public StringValue() {
        this.value = null;
    }

    public InputStream getStream() {
        if (this.value == null)
            return null;
        return new ByteArrayInputStream(value.getBytes());

    }

    public void setValue(InputStream is) {
        if (is == null)
        {    this.value = null;
            return;
        }
        byte[] cache = new byte[1024];
        int cursor = 0;
        int length = 1024;
        StringBuffer buffer = new StringBuffer();
        try {
            if (is.available() < length)
                length = is.available();
            while (is.read(cache, cursor, length) > 0) {
                buffer.append(new String(cache, "UTF-8"));
                cursor += 1024;
                if (is.available() < length)
                    length = is.available();
            }
            this.value = buffer.toString();
        }
        catch (UnsupportedEncodingException ex) {
            throw new RepositoryException(ex);
        }
        catch (IOException ex) {
            throw new RepositoryException(ex);
        }

    }

    /**
     * getType
     *
     * @return int
     */
    public int getType() {
        return PropertyType.STRING;
    }

    public String getString() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setValue(long value) {
        this.value = String.valueOf(value);
    }

    public void setValue(double value) {
        this.value = String.valueOf(value);
    }

    public void setValue(Calendar value) {
        this.value = String.valueOf(value);
    }

    public void setValue(boolean value) {
        this.value = String.valueOf(value);
    }

    public void setValue(Node value) {
        if (value == null)
            this.value = null;
        else
            this.value = value.getID();
    }

    public long getLong() {
        if ( (this.value == null) || (this.value.length() == 0))
            return 0l;
        else
            try {
                return Long.parseLong(this.value);
            }
            catch (NumberFormatException ex) {
                throw new ValueFormatException(ex);
            }
    }

    public double getDouble() {
        if ( (this.value == null) || (this.value.length() == 0))
            return 0l;
        else
            try {
                return Double.parseDouble(this.value);
            }
            catch (NumberFormatException ex) {
                throw new ValueFormatException(ex);
            }

    }

    public Calendar getDate() {
        Date date = DateValue.toDate(this.value);
        if (date == null)
            return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public boolean getBoolean() {
        if ( (this.value == null) || (this.value.length() == 0))
            return false;
        try {
            return Boolean.parseBoolean(this.value);
        }
        catch (NumberFormatException ex) {
            throw new ValueFormatException(ex);
        }

    }


    public int compareTo(Object o) {
        Value value = (Value) o;
        return this.value.compareTo(value.getString());
    }

    public boolean equals(Object o)
    {
        if(o==null)
            return false;
        if(o instanceof Value)
            return ((Value)o).getString().equals(this.value);
        return o.toString().equals(this.value);

    }

}
