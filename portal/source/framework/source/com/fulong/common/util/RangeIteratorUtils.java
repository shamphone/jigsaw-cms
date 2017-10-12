package com.fulong.common.util;

import org.apache.commons.collections.IteratorUtils;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * <p>
 * Title: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */
public class RangeIteratorUtils extends IteratorUtils {
	
	@SuppressWarnings("unchecked")
	public static List toSizedList(Iterator iterator, int size) {
		ArrayList list = new ArrayList();
		int count = 0;
		while (iterator.hasNext() && (count < size)) {
			list.add(iterator.next());
			count++;
		}
		return list;
	}
}
