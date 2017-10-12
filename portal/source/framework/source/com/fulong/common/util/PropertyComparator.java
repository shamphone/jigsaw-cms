package com.fulong.common.util;

import java.util.Comparator;
import org.apache.commons.beanutils.PropertyUtils;

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
@SuppressWarnings("unchecked")
public class PropertyComparator implements Comparator {
	
    private String property;
    
    public PropertyComparator(String property){
        this.property=property;
    }
    /**
     * Compares its two arguments for order.
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the first
     *   argument is less than, equal to, or greater than the second.
     * @todo Implement this java.util.Comparator method
     */
    public int compare(Object o1, Object o2) {
        Object prop1;
         Object prop2;
        try{
             prop1 = PropertyUtils.getProperty(o1, property);
             prop2 = PropertyUtils.getProperty(o2, property);
        }catch(Exception ex){
            throw new IllegalArgumentException("Illegal property "+property +".",ex);
        }
        if(prop1==null)
            return -1;
        if(prop2==null)
            return 1;
        if(prop1 instanceof Comparable){
            return ((Comparable)prop1).compareTo(prop2);
        }
        return prop1.toString().compareTo(prop2.toString());
    }
}
