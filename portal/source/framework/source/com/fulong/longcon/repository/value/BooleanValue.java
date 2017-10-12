package com.fulong.longcon.repository.value;

import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.Value;

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
public class BooleanValue extends BasicValue {
    private boolean value;
    public BooleanValue() {
        this.value = false;
    }

    /**
     * getType
     *
     * @return int
     * @todo Implement this com.fulong.longcon.content.Value method
     */
    public int getType() {
        return PropertyType.BOOLEAN;
    }

    public String getString() {
        return Boolean.toString(this.value);
    }


    public void setValue(String value) {
        this.value = Boolean.parseBoolean(value);
    }

    public void setValue(long value) {
        this.value = (value == 0);
    }

    public void setValue(double value) {
        this.value = (value == 0);
    }


    public void setValue(boolean value) {
        this.value = value;
    }

    public long getLong() {
        if (this.value)
            return 1;
        else
            return 0;
    }

    public double getDouble() {
        if (this.value)
            return 1;
        else
            return 0;
    }


    public boolean getBoolean() {
        return this.value;
    }

    public int compareTo(Object o) {
        Value another=(Value)o;
        return Boolean.valueOf(this.value).compareTo(Boolean.valueOf(another.getBoolean()));
    }

    public boolean equals(Object o){
        if(o instanceof Value)
        {
            if(this.compareTo(o)==0)
                return true;
        }
        return false;
    }


}
