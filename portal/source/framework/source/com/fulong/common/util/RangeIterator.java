package com.fulong.common.util;


/**
 * <p>Title: 龙驭核心引擎</p>
 *
 * <p>Description: 龙驭论坛核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lixf
 * @version 2.0
 */
public interface RangeIterator<E>  extends PageIterator<E> {
    /**
     * Skip a number of elements in the iterator.
     *
     * @param skipNum the non-negative number of elements to skip
     * @throws java.util.NoSuchElementException
     *          if skipped past the last element in the iterator.
     */
    public void skip(long skipNum);

      /**
     * Returns the current position within the iterator. The number
     * returned is the 0-based index of the next element in the iterator,
     * i.e. the one that will be returned on the subsequent <code>next</code> call.
     * <p/>
     * Note that this method does not check if there is a next element,
     * i.e. an empty iterator will always return 0.
     *
     * @return a long
     */
    public long getPosition();

	
}
