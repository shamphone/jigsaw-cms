package com.fulong.common.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author 李雄锋
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public interface RangeCollection extends Collection {

    public Iterator iterator(int fromIndex);

}
