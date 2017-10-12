package com.fulong.common.util;

import java.util.Iterator;

/**
 *
 * <p>Title: WebMaster Core Library</p>
 *
 * <p>Description: Longcon WebMaster SV3</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Tech. LTD</p>
 *
 * @author Lixf
 * @version 1.0
 */
public interface PageIterator<E> extends Iterator<E> {
        /**
         * 所有内容的大小
         * @return long
         */
        long getSize();

}
