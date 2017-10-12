package com.fulong.longcon.repository;

import com.fulong.longcon.repository.core.InternalValue;
import java.util.ArrayList;
import java.util.List;

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
public class ValueUtils {
    /**
     * 验证数据类型是否合适
     * @param value Value
     * @param type int
     * @return boolean
     */
    public static boolean checkType(Value value, int type) {
        try {
            switch (type) {
                case PropertyType.STRING:
                    value.getString();
                    break;

                case PropertyType.BINARY:
                    value.getStream();
                    break;
                case PropertyType.BOOLEAN:
                    value.getBoolean();
                    break;
                case PropertyType.DATE:
                    value.getDate();
                    break;
                case PropertyType.DOUBLE:
                    value.getDouble();
                    break;
                case PropertyType.LONG:
                    value.getLong();
                    break;
                case PropertyType.NAME:
                    value.getString();
                    break;
                case PropertyType.PATH:
                    value.getString();
                    break;
                case PropertyType.REFERENCE:
                    value.getString();
                    break;
                case PropertyType.TEXT:
                    value.getString();
                    break;
                default:
                    return false;
            }
            return true;
        } catch (ValueFormatException vfe) {
            return false;
        }
    }

    public static String[] toStringArray(Value[] values) {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < values.length; i++) {
            String value = values[i].getString();
            if (value != null)
                result.add(value);
        }
        return (String[]) result.toArray(new String[result.size()]);
    }

    public static boolean[] toBooleanArray(Value[] values) {
        boolean[] result = new boolean[values.length];
        for (int i = 0; i < values.length; i++)
            result[i] = values[i].getBoolean();
        return result;
    }

    public static long[] toLongArray(Value[] values) {
        long[] result = new long[values.length];
        for (int i = 0; i < values.length; i++)
            result[i] = values[i].getLong();
        return result;
    }

    public static Node[] toNodeArray(Value[] values) {
    	List<Node> list = new ArrayList<Node>();
    	for (int i = 0; i < values.length; i++) {
    		Node node = ( (InternalValue) values[i]).getReference();
    		if(node!=null){
    			list.add(node);
    		}
        }
        Node[] nodes = (Node[])list.toArray(new Node[list.size()]);
        return nodes;
    }

}
