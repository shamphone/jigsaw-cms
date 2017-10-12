package com.fulong.longcon.repository.value;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.text.ParseException;
import com.fulong.longcon.repository.ValueFormatException;
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
public class DateValue extends BasicValue {

    public static SimpleDateFormat longFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat shortFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat luceneFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    private Calendar value;
    public DateValue() {
        this.value = null;
    }

    public static Date toDate(String strValue) 
    {   
    	if ((strValue == null) || (strValue.length() == 0))
            return null;
        try 
        {
        	return longFormat.parse(strValue);
        } 
        catch (ParseException ex) 
        {
            try 
            {
                return (shortFormat.parse(strValue));
            } 
            catch (ParseException ex2)
            {
                try
                {
                    return (luceneFormat.parse(strValue));
                } 
                catch (ParseException ex3)
                {
                    try 
                    {
                        return (dateFormat.parse(strValue));
                    } 
                    catch (ParseException ex4) 
                    {
                        throw new ValueFormatException(ex3);
                    }
                }
            }
        }
    }

    public int getType() {
        return PropertyType.DATE;
    }

    public Calendar getDate() {
        return value;
    }

    public String getString() {
        if (value != null)
            return longFormat.format(value.getTime());
        else
        return null;
    }

    public void setValue(Calendar date) {
        this.value = date;
    }

    public void setValue(String strValue) {
        if ((strValue == null) || (strValue.length() == 0))
           { this.value = null;
               return;
           }
        this.value = Calendar.getInstance();
        try {

            this.value.setTime(longFormat.parse(strValue));
        } catch (ParseException ex) {
            try {
                this.value.setTime(shortFormat.parse(strValue));
            } catch (ParseException ex2) {
                throw new ValueFormatException(ex2);
            }
        }
    }

    public void setValue(long value){
        this.value=Calendar.getInstance();
        this.value.setTimeInMillis(value);
    }

    public long getLong(){
        if(this.value==null)
            return 0;
        else
        return value.getTimeInMillis();
    }

    public void setValue(double value) {
        this.value = Calendar.getInstance();
        this.value.setTimeInMillis(Double.doubleToLongBits(value));
    }

    public double getDouble() {
        if (this.value == null)
            return 0;
        else
        return value.getTimeInMillis();
    }

    public int compareTo(Object o) {
        Value value=(Value)o;
        return this.value.compareTo(value.getDate());
    }

    public boolean equals(Object o)
    {
        if (o == null)
			return false;
		if (o instanceof Value) {
			if (this.compareTo(o) == 0)
				return true;
		} else if (o instanceof String) {
			Date date = toDate((String) o);
			return this.value.getTime().equals(date);
		} else if (o instanceof Date) {
			Date date = (Date) o;
			return this.value.getTime().equals(date);
		}
		return false;
    }
}