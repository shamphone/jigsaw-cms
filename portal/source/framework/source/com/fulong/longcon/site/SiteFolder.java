/**
 * 
 */
package com.fulong.longcon.site;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * <p>
 * Title: SiteFolder
 * </p>
 * <p>
 * Description: Coolink协同工作支撑平台
 * </p>
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author lixf
 * @date 2010-5-23
 */
public interface SiteFolder {
	/**
	 * 文件夹名称
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 文件夹显示名称
	 * 
	 * @return
	 */
	public String getDisplayName();
	
	/**
	 * 文件夹显示名称
	 * 
	 * @return
	 */
	public void setDisplayName(String name);


	/**
	 * 对应的文件夹
	 * 
	 * @return
	 */
	public File getFile();

	/**
	 * 获取所有的子文件夹
	 * 
	 * @return ChannelIterator
	 */
	public Iterator<String> getChildNames(); 
	/**
	 * 获取子文件夹
	 * 
	 * @return ChannelIterator
	 */
	public SiteFolder getChild(String name);
	/**
	 * 新建子文件夹
	 * 
	 * @return ChannelIterator
	 */
	public SiteFolder addChild(String name, String displayName);


	/**
	 * \ 获取指定类型的子栏目
	 * 
	 * @param type
	 * @param thisType
	 *            false表示不是type类型的子栏目
	 * @return
	 */
	public Iterator<String> getChannelNames();

	/**
	 * 创建子栏目
	 * @param name  栏目名称，注意不是显示名称。缺省的，显示名称和栏目名称是一样的 之后可以修改显示名称，而栏目名称不能修改
	 * @param type	栏目类型
	 * @param content  内容
	 * @return
	 * @throws IOException
	 */
	public Channel addChannel(String name,String type,InputStream content) throws IOException;
	
	/**
	 * 通过栏目名称、上下文路径或者相对路径来获取该栏目
	 * @param contextPath：名称、上下文路径、相对路径
	 * @return
	 */
	public Channel getChannel(String contextPath); 
	
	 /**
     * 删除指定栏目及子栏目，注意在实现上仅将栏目标记为已删除Deleted状态，并没有实际删除
     * @param channel Channel
	 * @throws IOException 
     */
    public void delete() throws IOException;
    
    /**
     * 所在的网站模版
     * @return
     */
    public SiteTemplate getSiteTemplate();
    /**
     * 父目录，如果为根，则为空
     * @return
     */
    public SiteFolder getParent();
    /**
     * 相对于网站根目录的路径
     * @return
     */
    public String getContextPath();
}
