package com.fulong.longcon.repository.property;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import com.fulong.common.dao.DatabaseException;
import com.fulong.common.dao.JdbcDaoFactory;
import com.fulong.longcon.repository.core.InternalRepository;
import com.fulong.longcon.repository.dao.BlobValueLoaderDao;

/**
 * 使用临时文件来实现对blob流的读写.
 * 存在问题：如果临时文件无法删除，则系统将积累很多临时文件，最终导致系统空间不足。
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
public class BlobInputStream extends InputStream {

	private InputStream is;
    private JdbcDaoFactory factory;
    private InternalRepository repository;
    private String nodeId;
    private String propertyId;
    private int index;
    private BlobValueLoaderDao dao;
    public BlobInputStream(InternalRepository repository,
                           String nodeId,
                           String propertyId,
                           int index)  {
        this.nodeId = nodeId;
        this.propertyId = propertyId;
        this.index = index;
        this.repository = repository;
    }

    private void open() throws IOException {
        if (this.dao == null) {
            factory = this.repository.newJdbcDaoFactory();
            factory.open();
            this.dao = (BlobValueLoaderDao) factory.getDao(BlobValueLoaderDao.class);
            try {
                this.is = this.dao.load(this.nodeId, this.propertyId,
                                        this.index);
            } catch (SQLException ex) {
                throw new DatabaseException(ex);
            }
        }
    }

    /**
     * Reads the next byte of data from the input stream.
     *
     * @return the next byte of data, or <code>-1</code> if the end of the
     *   stream is reached.
     * @throws IOException if an I/O error occurs.
     */
    public int read() throws IOException {
        this.open();
        if(is==null)
            return -1;
        return this.is.read();
    }

    /**
     *
     * @param b byte[]
     * @return int
     * @throws IOException
     */
    public int read(byte b[]) throws IOException {
        this.open();
        if(is==null)
            return -1;
        return is.read(b);
    }

    public int read(byte b[], int off, int len) throws IOException {
        this.open();
        if(is==null)
            return -1;
        return is.read(b, off, len);

    }

    public long skip(long n) throws IOException {
        this.open();
        if(is==null)
            return -1;
        return is.skip(n);
    }

    public void close() throws IOException {
        try {
            if (is != null)
                is.close();
        } finally {
            try {
                if(this.dao!=null)
                    this.dao.close();
                this.dao=null;
            } catch (SQLException ex) {
                throw new DatabaseException(ex);
            } finally {
            	if(this.factory!=null){
            		this.factory.close();
            	}
            }
        }
    }
}
