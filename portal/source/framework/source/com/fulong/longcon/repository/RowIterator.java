package com.fulong.longcon.repository;

import com.fulong.common.util.RangeIterator;

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
public interface RowIterator
    extends RangeIterator<Row> {
    /**
     * Returns an array of all the property names (column names) in this result set.
     *
     * @return a <code>PropertyIterator</code>
     * @throws RepositoryException if an error occurs.
     */
    public String[] getColumnNames() throws RepositoryException;

    /**
     * 获得每次抓取的页面元素个数,缺省为20。
     * @param size int
     */
    public void setFetchSize(int size);

    /**
     * 获取下一行
     * @return Row
     */
    public Row nextRow();
}
