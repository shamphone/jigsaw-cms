package com.fulong.longcon.repository.value;

import java.util.Calendar;

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
public class LongValue extends BasicValue {

	private long value;

    public LongValue() {
        this.value = 0;
    }


    /**
     * getType
     *
     * @return int
     */
    public int getType() {
        return PropertyType.LONG;
    }

    public Calendar getDate(){
        Calendar date=Calendar.getInstance();
        date.setTimeInMillis(this.value);
        return date;
    }

    public void setValue(Calendar date){
        if(date==null)
            this.value=0;
        else
        this.value=date.getTimeInMillis();
    }


    public String getString() {
        return Long.toString(this.value);
    }

    public long getLong() {
        return this.value;
    }
    
    public double getDouble() {
		return this.value;
	}
    
    public void setDouble(double value) {
        this.value = (long) value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public void setValue(String value) {
        if ((value == null) || (value.length() == 0))
            this.value = 0;
        else
        this.value = Long.parseLong(value);
    }

    public void setValue(boolean value) {
        if (value)
            this.value = 1;
        else
            this.value = 0;
    }

    public boolean getBoolean() {
        return this.value != 0;
    }

    public int compareTo(Object o) {
        Value value=(Value)o;
        return (int)(this.value-value.getLong());
    }

    public boolean equals(Object o){
        if(this.compareTo(o)==0)
            return true;
        else
            return false;

    }
}
