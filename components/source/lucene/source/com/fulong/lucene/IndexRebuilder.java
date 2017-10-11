package com.fulong.lucene;

import com.fulong.longcon.repository.Node;

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
public interface IndexRebuilder {
    /**
     * 是否空闲
     * @return boolean
     */
    public boolean isIdle();

    /**
     * 重建
     * @param node Node
     */
    public void rebuild(Node root);

    /**
     * 要重建的索引的总数量
     * @return int
     */
    public long getTotalCount();

    /**
     * 已经建立的索引的数量
     * @return int
     */
    public long getBuiltCount();
    /**
     *  已经成功建立的索引的数量
     * @return long
     */
    public long getSuccessCount();

}
