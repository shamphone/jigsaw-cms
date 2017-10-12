package com.fulong.longcon.repository.dao;

import com.fulong.common.FileUtils;
import java.io.InputStream;
import java.io.IOException;
import org.apache.commons.logging.LogFactory;
import java.io.FileInputStream;
import org.apache.commons.logging.Log;
import java.io.File;

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
public class FileBlobInputStream   extends InputStream {
    private static long fileNameCounter=System.currentTimeMillis();
    private static final Log log = LogFactory.getLog(FileBlobInputStream.class);
    private File file;
    private FileInputStream is;
    public FileBlobInputStream(InputStream is) throws IOException {
        String fileName="longcon"+(fileNameCounter++);
       this.file=File.createTempFile(fileName,"tmp");
       FileUtils.write(this.file,is);
       file.deleteOnExit();
       log.trace("create temp file for blob input stream:"+file.getAbsolutePath());
    }


    /**
     * Reads the next byte of data from the input stream.
     *
     * @return the next byte of data, or <code>-1</code> if the end of the
     *   stream is reached.
     * @throws IOException if an I/O error occurs.
     */
    public int read() throws IOException {
        if(this.is==null)
            this.is=new FileInputStream(this.file);
        return this.is.read();
    }
    /**
     *
     * @param b byte[]
     * @return int
     * @throws IOException
     */
    public int read(byte b[]) throws IOException {
        if(this.is==null)
            this.is=new FileInputStream(this.file);
         return is.read(b);
    }


    public int read(byte b[], int off, int len) throws IOException {
        if(this.is==null)
            this.is=new FileInputStream(this.file);
         return is.read(b,off,len);

    }

    public long skip(long n) throws IOException {
        if(this.is==null)
            this.is=new FileInputStream(this.file);
         return is.skip(n);
    }

    public void close() throws IOException {
        log.trace("delete temp file for blob stream :"+this.file.getAbsolutePath());
        try{
            if (is != null)
                is.close();
        }finally{
            this.file.delete();
        }
    }
}
