package com.fulong.webdav.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.fulong.common.FileUtils;

/**
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author Lixf
 * @version 1.0
 */
public class FileResourceInfo
    implements ResourceInfo {
    private File resource;
    private String path;
    private static ExclusiveFileFilter filter = new ExclusiveFileFilter();
    public FileResourceInfo(File resource, String path) {
        this.resource = resource;
        this.path=path;
    }

    /**
     * 从source中复制资源。
     * @param source ResourceInfo
     */
    public void copy(ResourceInfo resource) throws IOException {
        FileResourceInfo source = (FileResourceInfo) resource;
        if (source.resource.isFile())
            FileUtils.copyFile(source.resource, this.resource);
        else
            FileUtils.copyDirectory(source.resource, this.resource, true);
    }

    public boolean isRoot(){
        return this.path.lastIndexOf("/")==0;
    }



    /**
     * 删除文件，返回错误集合
     * @return Map <错误页面的path, 错误代码>
     */
    public boolean delete() {
       return this.resource.delete();
    }


    public boolean mkdirs() {
        return this.resource.mkdirs();
    }

    public InputStream getInputStream() throws IOException {
        return new FileInputStream(resource);
    }

    /**
     * 更新文件内容
     * @param is InputStream
     * @param start long
     * @param length long
     */
    public void write(InputStream is, long start, long length) throws
        IOException {
        FileUtils.append(resource, is, start, length);

    }

    public long getLength() {
        return this.resource.length();
    }

    /**
     * 更新文件内容
     * @param is InputStream
     */
    public void write(InputStream is) throws IOException {
        FileUtils.write(resource, is);
    }

    public String getContentType() {
        return null;
    }

    public boolean exists() {
        return this.resource.exists();
    }
    public String getDisplayName() {
           return this.resource.getName();
    }
    public String getName() {
        return this.resource.getName();
    }

    public Date getLastModified() {
        return new Date(this.resource.lastModified());
    }

    public boolean isDirectory() {
        return this.resource.isDirectory();
    }

    public boolean isFile() {
        return this.resource.isFile();
    }

    /**
     * 获取readme文件的内容。readme文件内容定义在目录下的readme.txt文件中
     * @return String
     */
    public String getReadme() {
        try {
            File file = new File(this.resource, "readme.txt");

            if (file.exists() && file.isFile()) {
                StringWriter buffer = new StringWriter();
                InputStream is = new FileInputStream(file);
                FileUtils.copy(new InputStreamReader(is),
                               new PrintWriter(buffer));

                return buffer.toString();
            }
            return "";
        }
        catch (Throwable e) {
            return "";
        }
    }

    public ResourceInfo getParent() {
        String resourceName = path;
        if (path.endsWith("/"))
            resourceName = path.substring(0, path.length() - 1);
        int lastSlash = resourceName.lastIndexOf('/');
        if (lastSlash != -1)
            resourceName = resourceName.substring(lastSlash + 1);
        return new FileResourceInfo(this.resource.getParentFile(),
                                    resourceName);
    }

    /**
     * 遍历获取所有子资源
     * @return Iterator
     */
    public Iterator<ResourceInfo> getChildren() {
        String path = this.path;
        if (!path.endsWith("/"))
            path = path + "/";
        List<ResourceInfo> children = new Vector<ResourceInfo>();
        File[] files = this.resource.listFiles(filter);
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                children.add(new FileResourceInfo(files[i],
                                                  path + files[i].getName()));
            }
        }
        return children.iterator();
    }

    /**
     * 获取所有的子元素
     * @param depth int
     * @return Iterator
     */
    public Iterator<ResourceInfo> getAllChildren(int depth) {
        Vector<ResourceInfo> children = new Vector<ResourceInfo>();
        this.recursiveGetChildren(this, children, depth);
        return children.iterator();
    }

    private void recursiveGetChildren(ResourceInfo resource,
                                      Collection<ResourceInfo> children,
                                      int depth) {
        FileResourceInfo root = (FileResourceInfo) resource;
        if (depth <= 0)
            return;
        String path = root.getPath();
        if (!path.endsWith("/"))
            path = path + "/";
        File[] files = root.resource.listFiles(filter);
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                ResourceInfo child = new FileResourceInfo(
                    files[i],
                    path + files[i].getName());
                children.add(child);
                if (files[i].isDirectory())
                    recursiveGetChildren(child, children, depth - 1);

            }
        }

    }
    public boolean isReserved() {
           // Exclude any resource in the /WEB-INF and /META-INF subdirectories
           // (the "toUpperCase()" avoids problems on Windows systems)
           if (path.toUpperCase().startsWith("/WEB-INF") ||
               path.toUpperCase().startsWith("/META-INF"))
               return true;
           return false;

    }

    public void setContentType(String contentType) {
    }

    public String getPath() {
        return this.path;
    }

    public Principal getCreator() {
        return null;
    }

    public boolean isHidden() {
        return this.resource.isHidden();
    }

    public boolean isReadonly() {
        return this.resource.canWrite();
    }

    public Date getLastAccessed() {
        return new Date(this.resource.lastModified());
    }

    public void setCreator(Principal creator) {

    }

    public InputStream read() throws IOException{
        return new FileInputStream(this.resource);
    }

    public Date getCreationDate() {
        return new Date(this.resource.lastModified());
    }
    /*
    public void read(OutputStream ostream) throws IOException {
        FileInputStream is=new FileInputStream(this.resource);
        FileUtils.copy(is,ostream);
        is.close();
    }

    public void read(OutputStream ostream, long start, long end) throws
        IOException {
        FileInputStream is=new FileInputStream(this.resource);
             FileUtils.copy(is,ostream,start,end);
        is.close();
    }

    public void read(Writer writer) throws IOException {
        FileReader reader=new FileReader(this.resource);
        FileUtils.copy(reader,writer);
        reader.close();
    }

    public void read(Writer writer, long start, long end) throws IOException {
        FileReader reader=new FileReader(this.resource);
        FileUtils.copy(reader,writer,start,end);
        reader.close();
    }
*/

	@Override
	public boolean move(ResourceInfo dest) throws IOException {
		FileResourceInfo another =(FileResourceInfo)dest;
		return this.resource.renameTo(another.resource);
	}
}


class ExclusiveFileFilter
    implements FilenameFilter {
    public boolean accept(File dir, String name) {
        return! (name.equalsIgnoreCase("WEB-INF") ||
                 name.equalsIgnoreCase("META-INF") ||
                 name.startsWith(".") ||
                 name.endsWith(".old") ||
                 name.endsWith(".delete") ||
                 name.endsWith(".bak"));
    }
}
