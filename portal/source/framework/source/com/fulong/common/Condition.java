package com.fulong.common;

/**
 * <p>Title: LongCon WebMaster Core</p>
 *
 * <p>Description: Longcon WebMaster Core System</p>
 *
 * <p>Copyright: Beijing Zhongke Fulong Computer Tech. Co.LTD. 2005 </p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Tech. Co.LTD.</p>
 *
 * @author Li Xiongfeng
 * @version 1.0
 */
public class Condition {
    private String field;
    private Object value;
    public Condition() {
    }

    public Condition(String field, Object value) {
        this.field = field;
        this.value = value;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }
}
