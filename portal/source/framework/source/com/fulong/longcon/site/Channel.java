package com.fulong.longcon.site;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import com.fulong.longcon.repository.NodeDefinition;

/**
 * 组织页面的虚拟存储空间。通过指定栏目权限，系统管理员可以设置页面的浏览、编辑和发布的权限。 网站的栏目组成树状结构
 * <p>
 * Title: 龙驭门户引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author 李雄锋
 * @version 1.0
 */
public interface Channel {
	/**
	 * 索引栏目
	 */
	public static final String INDEX = "index";
	/**
	 * 内容栏目
	 */
	public static final String CONTENT = "content";

	/**
	 * 搜索栏目
	 */
	public static final String CLIP = "clip";

	/**
	 * 样式文件栏目
	 */
	public static final String STYLE = "style";
	/**
	 * 脚本文件栏目
	 */
	public static final String SCRIPT = "script";

	/**
	 * word类型栏目
	 */
	public static final String WORD = "word";
	
	public static final String DEFAULT_ENCODING = "UTF-8";
	
	/**
	 * 
	 * @return
	 */
	@Deprecated
	public String getID();

	/**
	 * 栏目名称.
	 * 
	 * @return String
	 */
	public String getName();


	/**
	 * 栏目显示名称，在页面做展示
	 * 
	 * @return String
	 */
	public String getDisplayName();

	
	/**
	 * 栏目所在的网站模板
	 * 
	 * @return Site
	 */
	public SiteTemplate getSiteTemplate();
	
	/**
	 * 所在的文件夹
	 * 
	 * @return SiteFolder
	 */
	public SiteFolder getFolder();
	
	/**
	 * 创建该栏目
	 */
	public void create() throws IOException;
	

	/**
	 * 获取模板设置
	 * 
	 * @return String
	 */
	public File getPage();

	/**
	 * 栏目的相对于Web context的路径，即不包含web context的路径
	 * 
	 * @return
	 */
	public String getContextPath();
	/**
	 * 获取工作路径
	 * @return
	 */
	public String getWorkingPath();
	

	/**
	 * 获得当前栏目的类型
	 * 
	 * @return String
	 */
	public String getType();

	/**
	 * 栏目类型，主要用来定义地址的产生方式
	 * 
	 * @return ChannelType
	 */
	public ChannelType getChannelType();

	
	/**
	 * 栏目是否正在被修改
	 * 
	 * @return
	 */
	public boolean isWriting();

	/**
	 * 栏目修改者的IP
	 * 
	 * @return
	 */
	public String getWriter();

	/**
	 * 最后修改栏目持有锁时间
	 * 
	 * @return
	 */
	public Date getLastWritingTime();

	/**
	 * 签出，如果已经签出，则返回false，表示签出失败；
	 * 
	 * @param IP
	 * @return
	 * @throws IOException 
	 */
	public boolean checkout(String IP) throws IOException;

	/**
	 * 签入，如果已经签入，则返回false,表示签入失败；
	 * 
	 * @param IP
	 * @return
	 */
	public boolean checkin(String IP) throws IOException;
	/**
	 * 签入，如果已经签入，则返回false,表示签入失败；
	 * 签入的内容保存在临时工作文件中，必须调用publish操作，才能使修改的内容被最终用户看到；
	 * @param IP
	 * @return
	 * @throws IOException 
	 */
	public boolean checkin(String IP, InputStream content) throws IOException;
	/**
	 * 提交栏目内容，同时保持签出状态，提交人必须先签出才可以作提交。
	 * 提交内容保存在临时工作文件中，必须调用publish操作，才能使修改的内容被最终用户看到；
	 * @param IP
	 * @return
	 * @throws IOException 
	 */
	public boolean submit(String IP, InputStream content) throws IOException;
	
	/**
	 * 发布栏目；
	 * @throws IOException
	 */
	public boolean publish() throws IOException;
	
	
	/**
	 * 放弃修改，恢复到已发布状态；
	 */
	public boolean restore() throws IOException;
	
	/**
	 * 删除栏目，同时删除和本栏目相关的其他文件
	 * @throws IOException
	 */
	public void delete() throws IOException;
	/**
	 * 最后的修改是否已经发布；
	 * @return
	 * @throws IOException
	 */
	public boolean isPublished();
	/**
	 * 
	 * @return
	 */
	@Deprecated
	public NodeDefinition getBindingNode();
}
