package com.fulong.longcon.repository.value;

import java.util.Calendar;

import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.Value;

/**
 * <p>Title: WebMaster Core Library</p>
 *
 * <p>Description: Longcon WebMaster SV3</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Tech. LTD</p>
 *
 * @author Lixf
 * @version 1.0
 */
public class DoubleValue extends BasicValue {
    private double value;
    public DoubleValue() {
        this.value = 0;
    }

    public String getString() {
        return Double.toString(this.value);
    }

    public double getDouble() {
        return this.value;
    }

    public long getLong() {
        return (long)value;
    }

    public void setValue(String str) {
        if((str!=null)&&(str.length()>0))
        this.value = Double.parseDouble(str);
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setValue(long value) {
        this.value = value;
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
    public void setValue(Calendar date){
         if(date==null)
             this.value=0;
         else
         this.value=date.getTimeInMillis();
    }
    public Calendar getDate(){
          Calendar date=Calendar.getInstance();
          date.setTimeInMillis((long)this.value);
          return date;
    }
    /**
     * getType
     *
     * @return int
     */
    public int getType() {
        return PropertyType.DOUBLE;
    }

    public int compareTo(Object o) {
        Value value=(Value)o;
        return Double.compare(this.value, value.getDouble());
    }

    public boolean equals(Object o){
        if(this.compareTo(o)==0)
            return true;
        else
            return false;
    }
}
