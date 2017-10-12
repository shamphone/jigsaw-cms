package com.fulong.longcon.counter.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.ArrayList;

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


class AccessCounterQueue {
    private Set<AccessCounterNode> nodes;
    public AccessCounterQueue() {
        this.nodes = Collections.synchronizedSet(new LinkedHashSet<AccessCounterNode>());
    }

    public synchronized AccessCounterNode[] get(int length) {
        while (nodes.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException ie) {
                return null;
            }

        }
        
        Iterator<AccessCounterNode> iterator = nodes.iterator();
        ArrayList<AccessCounterNode> list = new ArrayList<AccessCounterNode>();
        for (int i = 0; i < length; i++) {
            if (iterator.hasNext()) {
                AccessCounterNode node = iterator.next();
                iterator.remove();
                list.add(node);
            } else
                break;
        }
        return (AccessCounterNode[]) list.toArray(new AccessCounterNode[list.
                                                  size()]);
    }


    public synchronized void put(AccessCounterNode node) {
        this.nodes.add(node);
        this.notifyAll();
    }
}
