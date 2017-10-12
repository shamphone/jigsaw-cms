package com.fulong.longcon.repository;

import com.fulong.common.util.RangeIterator;

/**
 * <p>Title: 龙驭核心引擎</p>
 *
 * <p>Description: 龙驭核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lishaobo
 * @version 1.0
 */
public interface PropertyIterator<E>
    extends RangeIterator<E> {

    /**
     *
     * @return Property
     */
    public Node nextProperty();
}
