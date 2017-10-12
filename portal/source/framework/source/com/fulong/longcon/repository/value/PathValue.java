package com.fulong.longcon.repository.value;

import com.fulong.longcon.repository.PropertyType;

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
public class PathValue extends StringValue {
    private String value;

    public PathValue() {
        this.value = null;
    }

    public void setValue(String value) {
        this.value = value;
    }
    /**
     * getType
     *
     * @return int
     */
    public int getType() {
        return PropertyType.PATH;
    }

    public String getValue(){
        return this.value;
    }

    public String getString(){
        return this.value;
    }

    public boolean equals(Object o){
        if(o==null)
            return false;
        return ((String)o).equals(this.value);
    }
}
