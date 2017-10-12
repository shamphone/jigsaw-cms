package com.fulong.longcon.repository;

import java.util.Comparator;
/**
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */
public class NodeOrderComparator implements Comparator<Node> {
    public NodeOrderComparator() {
    }

    public int compare(Node node1, Node node2) {
        int result = (node1).getOrderNo()-(node2).getOrderNo();

        if(result!=0)
            return (node1).getOrderNo()-(node2).getOrderNo();
        else
            return (node1).getID().compareTo((node2).getID());

    }

}
