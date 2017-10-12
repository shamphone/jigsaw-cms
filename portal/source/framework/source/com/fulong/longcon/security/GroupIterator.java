package com.fulong.longcon.security;

import com.fulong.common.util.RangeIterator;

/**
 * <p>Title: 龙驭会员管理系统2.0</p>
 *
 * <p>Description: 龙驭会员管理系统2.0</p>
 *
 * <p>Copyright: Copyright (c) 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author Lixf
 * @version 2.0
 */
@SuppressWarnings("rawtypes")
public interface GroupIterator extends RangeIterator {
    /**
     *
     * @return Group
     */
    public Group nextGroup();
}
