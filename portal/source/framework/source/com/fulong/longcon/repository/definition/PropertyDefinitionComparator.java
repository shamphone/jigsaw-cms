package com.fulong.longcon.repository.definition;

import java.util.Comparator;

import com.fulong.longcon.repository.PropertyDefinition;

/**
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author Lixf
 * @version 1.0
 */
public class PropertyDefinitionComparator implements Comparator<PropertyDefinition> {

    /**
     * Compares its two arguments for order.
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the first
     *   argument is less than, equal to, or greater than the second.
     */
    public int compare(PropertyDefinition def1, PropertyDefinition def2) {
        int result = def1.getOrderNo() - def2.getOrderNo();

        if (result != 0)
            return result;
        else
            return def1.getID().compareTo(def2.getID());

    }
}
