package com.fulong.longcon.repository;

/**
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author 李少波
 * @version 1.0
 */
public class NodeIteratorWrapper<T extends Node> implements NodeIterator<T> {
    protected NodeIterator<T> _nodeIterator;

    public NodeIteratorWrapper(NodeIterator<T> nodeIterator) {
        this._nodeIterator = nodeIterator;

    }

    /**
     * 获得每次抓取的页面元素个数,缺省为20。
     * @param size int
     */
    public void setFetchSize(long size) {
        this._nodeIterator.setFetchSize(size);
    }

    /**
     * 遍历下一个节点
     * @return Node
     */
    public T nextNode() {
        return this._nodeIterator.nextNode();
    }

    /**
     * Skip a number of elements in the iterator.
     *
     * @param skipNum the non-negative number of elements to skip
     * @throws java.util.NoSuchElementException
     *          if skipped past the last element in the iterator.
     */
    public void skip(long skipNum) {
        this._nodeIterator.skip(skipNum);
    }

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
    public long getPosition() {
        return this._nodeIterator.getPosition();
    }

    public long getSize() {
        return this._nodeIterator.getSize();
    }

    public boolean hasNext() {
        return this._nodeIterator.hasNext();
    }

    public T next() {
        return this._nodeIterator.next();
    }

    public void remove() {
        this._nodeIterator.remove();
    }
}
