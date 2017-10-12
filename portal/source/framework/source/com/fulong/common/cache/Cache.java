package com.fulong.common.cache;

/**
 * <p>Title: 龙驭问答系统</p>
 *
 * <p>Description: 龙驭知识管理系统子系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author lixf
 * @version 1.0
 */
public interface Cache {
    /**
     * 增加对象到缓存中
     * @param key Object
     * @param value Object，可以为空
     */
    public void put(Object key,Object value);
    /**
     * 从缓存中获取对象
     * @param key Object
     * @return Object
     */
    public Object get(Object key);
    /**
     * 删除指定索引的对象
     * @param key Object
     */
    public void remove(Object key);
    /**
     * 判断缓存中是否包含指定的键值
     * @param key Object
     */
    public boolean containsKey(Object key);
    /**
     * 清空缓存
     */
    public void clear();
    /**
     * 当前大小
     * @return
     */
    public long getSize();
}
