package com.fulong.common;

/**
 *
 * <p>Title: Longcon Passport System</p>
 *
 * <p>Description: Longcon Passport</p>
 *
 * <p>Copyright: Copyright (c) Beijing Zhongke Fulong Computer Technology LTD. *
 * 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Technology LTD.</p>
 * @author JiangQi
 * @version 2.0
 */

public interface BufferStorage {
    public Object lookup(String key);

    public void add(String key, Object obj);

    public int getSize();

    public void clear();
}
