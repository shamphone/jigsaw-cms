package com.fulong.longcon.repository.value;

import java.io.IOException;
import java.io.InputStream;
import com.fulong.common.dao.DaoFactory;

/**
 *
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author lishabo
 * @version 2.0
 */

public class BLOBInputStream extends InputStream {
    private InputStream in;
    private DaoFactory factory;

    public BLOBInputStream(InputStream in, DaoFactory factory){
        this.in = in;
        this.factory = factory;
    }

    /**
     * 读取流
     * @return int
     * @throws IOException
     */
    public int read() throws IOException{
         return in.read();
     }

     /**
      * 关闭流，同时关闭数据库链接
      * @throws IOException
      */
     public void close() throws IOException {
        this.in.close();
        this.factory.close();
    }



}
