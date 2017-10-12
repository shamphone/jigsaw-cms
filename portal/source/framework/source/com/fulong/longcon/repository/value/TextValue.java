package com.fulong.longcon.repository.value;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.RepositoryException;
import com.fulong.longcon.repository.Value;

/**
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
public class TextValue extends BasicValue {
    private String value;
    public TextValue() {
        this.value = null;
    }

    public InputStream getStream() {
        if (this.value == null)
            return null;
        return new ByteArrayInputStream(value.getBytes());

    }

    /**
     * getType
     *
     * @return int
     */
    public int getType() {
        return PropertyType.TEXT;
    }

    public String getString() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setValue(InputStream is) {
        if (is == null) {
            this.value = null;
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
        } catch (UnsupportedEncodingException ex) {
            throw new RepositoryException(ex);
        } catch (IOException ex) {
            throw new RepositoryException(ex);
        }

    }

    public int compareTo(Object o) {
        Value value = (Value) o;
        return this.value.compareTo(value.getString());
    }

    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (this.compareTo(o) == 0)
            return true;
        return false;

    }
}
