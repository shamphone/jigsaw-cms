package com.fulong.common.util;

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
public class RangeEnumerator<E> implements RangeEnumeration<E> {
    private RangeIterator<E> iterator;
    public RangeEnumerator(RangeIterator<E> iterator) {
        this.iterator=iterator;
    }
    /**
     *
     * @param skipNum long
     */
    public void skip(long skipNum) {
        this.iterator.skip(skipNum);
    }
    /**
     *
     * @return long
     */
    public long getPosition() {
        return this.iterator.getPosition();
    }
    /**
     *
     * @return boolean
     */
    public boolean hasMoreElements() {
        return this.iterator.hasNext();
    }
    /**
     *
     * @return Object
     */
    public E nextElement() {
        return this.iterator.next();
    }
    /**
     *
     * @return long
     */
    public long getSize() {
        return this.iterator.getSize();
    }
}
