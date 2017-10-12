package com.fulong.longcon.site.impl;

import java.util.Collection;
import java.util.Iterator;

import com.fulong.common.util.ListRangeIterator;
import com.fulong.longcon.site.SiteCategory;

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
public class BasicSiteCategoryIterator extends ListRangeIterator<SiteCategory>
    implements Iterator<SiteCategory> {


    public BasicSiteCategoryIterator(Collection<SiteCategory> rows) {
        super(rows);
    }
}
