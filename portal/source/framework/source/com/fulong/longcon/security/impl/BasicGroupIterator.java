package com.fulong.longcon.security.impl;

import java.util.List;
import java.util.Collection;
import com.fulong.common.util.ListRangeIterator;
import com.fulong.longcon.security.GroupIterator;
import com.fulong.longcon.security.Group;

/**
 * <p>
 * Title: 龙驭会员管理系统2.0
 * </p>
 * 
 * <p>
 * Description: 龙驭会员管理系统2.0
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author Lixf
 * @version 2.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
class BasicGroupIterator extends ListRangeIterator implements GroupIterator {
	
	public BasicGroupIterator(List rows) {
		super(rows);
	}

	public BasicGroupIterator(Collection rows) {
		super(rows);
	}

	public Group nextGroup() {
		return (Group) this.next();
	}
}
