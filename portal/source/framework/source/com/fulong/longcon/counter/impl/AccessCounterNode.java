package com.fulong.longcon.counter.impl;

import java.util.Date;

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

class AccessCounterNode {
    private String name;
    private Date accessDate;

    public AccessCounterNode(String name, Date accessDate) {
        this.name = name;
        this.accessDate = accessDate;
    }

    public String getName() {
        return this.name;
    }

    public Date getAccessDate() {
        return this.accessDate;
    }
}
