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
 * @author 李雄锋
 * @version 1.0
 */
public interface NodeIterator<T extends Node> extends RangeIterator<T> {
        /**
         * 获得每次抓取的页面元素个数,缺省为20。
         * @param size int
         */
        public void setFetchSize(long size);
        /**
         * 遍历下一个节点
         * @return Node
         */
        public T nextNode();
		
}
