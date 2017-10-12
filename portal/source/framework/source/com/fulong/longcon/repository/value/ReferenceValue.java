package com.fulong.longcon.repository.value;

import com.fulong.longcon.repository.Node;
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
 *
 * 可能存在问题的地方：现在不支持通过setValue(String)来将节点的ID直接赋予这个ReferenceValue，
 * 但是可以在ReferenceProperty.setValue(String value) 里面处理
 */
public class ReferenceValue extends BasicValue {
    private Node value;
    public ReferenceValue() {
        this.value = null;
    }


    public void setValue(Node value) {
        this.value = value;
    }


    public Node getReference() {
        return this.value;
    }

    public String getString() {
        if (this.value == null)
            return null;
        else
        return this.value.getID();
    }

    public int getType() {
        return PropertyType.REFERENCE;
    }

    public int compareTo(Object o) {
        return 0;
    }


    public boolean equals(Object o){
        if(o ==null)
            return false;
        if(o instanceof ReferenceValue)
        {
        	return ((ReferenceValue)o).getReference().getID().equals(this.value.getID());
        }
        if(o instanceof Node)
            return ((Node)o).getID().equals(this.value.getID());
        return false;
    }



}
