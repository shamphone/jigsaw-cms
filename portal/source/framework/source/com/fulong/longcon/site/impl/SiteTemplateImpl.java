package com.fulong.longcon.site.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

import com.fulong.longcon.repository.Query;
import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.ChannelType;
import com.fulong.longcon.site.SiteException;
import com.fulong.longcon.site.SiteFactory;
import com.fulong.longcon.site.SiteFolder;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.longcon.site.channel.GeneralChannel;
import com.fulong.longcon.site.config.WebXML24Parser;
import com.fulong.longcon.site.config.WebXMLParser;

/**
 * 网站模板，每个网站模板对应一个web应用。网站模板的目录结构如下：
 * 1. 根目录下存放所有的jsp栏目
 * 2. web.xml必须按照
 * <p>
 * Description: Coolink协同工作支撑平台
 * </p>
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author lixf
 * @date 2010-5-11
 */
public class SiteTemplateImpl implements SiteTemplate {
	private SiteFactory factory;
	private String name;
	private WebXMLParser webxmlParser;
	private SiteFolder root;
	private File folder;
	private Map<String, SiteFolder> folders; //folder缓存
	private Map<String,Channel>channels;//channel缓存

	public SiteTemplateImpl(SiteFactory factory, String name) {
		
		this(factory, name, new File(factory.getBaseDirectory(),name));
	}
	//private static final Log log = LogFactory.getLog(SiteTemplateImpl.class);
	public SiteTemplateImpl(SiteFactory factory, String name, File folder) {
		
		this.factory = factory;
		this.name = name;
		this.folder = folder;
		if(!folder.exists())
			throw new IllegalArgumentException("No template with name "+ name+" exists.");
		this.root= new SiteFolderImpl(this, folder);
		this.folders = new Hashtable<String, SiteFolder>();
		this.channels = new Hashtable<String, Channel>();		
		this.folders.put("", this.root);
		try {
			this.webxmlParser = new WebXML24Parser(new File(folder,"/WEB-INF/web.xml"));
		} catch (Exception e) {
			throw new SiteException(e);		
		}
	}



	/* 
	 * 获取网站所在的文件夹
	 * @see com.fulong.longcon.site.SiteTemplate#getFolder()
	 */
	@Override
	public SiteFolder getRootFolder() {
		return this.root;
	}
	
	public String getID(){
		return this.getName();
	}

	/**
	 * 模板名称
	 * 
	 * @return String
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * 网站的显示名称,缺省的displayName等于name属性
	 * 
	 * @return String
	 */
	@Override
	public String getDisplayName() {
		return this.webxmlParser.getDisplayName();
	}

	/**
	 * 网站的显示分辨率
	 * 
	 * @return String
	 */
	@Override
	public String getResolution() {
		return this.webxmlParser.getContextParamValue("resolution");
	}

	/**
	 * 当前模板下的栏目
	 * 
	 * @return Vector
	 */
/*
	private Map<String, Channel> processChannels() {		
		this._channels = new LinkedHashMap<String, Channel>();
		for(Enumeration<ChannelType> types=this.factory.channelTypes();types.hasMoreElements();){
			ChannelType type=types.nextElement();
			@SuppressWarnings("unchecked")
			Iterator<File> iterator=FileUtils.iterateFiles(this.getFolder(), new String[]{type.getPostfix()}, true);
			while(iterator.hasNext()){
				File file = iterator.next();
				Channel channel;
				try {
					channel = (Channel) Class.forName(type.getClassName()).newInstance();
					channel.init(this, file);
					_channels.put(channel.getName(), channel);
				} catch (Exception e) {
					log.info("Unable to load channel for "+ file.getPath()+":"+e.getMessage());
				}
			}
		}	
		
		return _channels;
	}

	private Map<String, String> bindings() {
		if (this._bindings != null)
			return this._bindings;
		this._bindings = new LinkedHashMap<String, String>();
		ChannelNodeDefinitionData[] data;
		DaoFactory factory = this.factory.newDaoFactory();
		try {
			factory.open();
			ChannelNodeDefinitionDao dao = (ChannelNodeDefinitionDao) factory.getDao(ChannelNodeDefinitionDao.class);
			data = dao.find(this.getID());
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		} finally {
			factory.close();
		}
		for (int i = 0; i < data.length; i++) {
			this._bindings.put(data[i].getNodeDdefinitionID(), data[i].getChannelID());
		}
		return _bindings;
	}
*/
	/**
	 * 修改网站显示名称
	 * 
	 * @param name
	 *            String
	 */
	@Override
	public void setDisplayName(String name) {
		this.webxmlParser.setDisplayName(name);
		try {
			this.webxmlParser.save();
		} catch (IOException e) {
			throw new SiteException(e);
		}
	}

	/**
	 * 修改网站显示分辨率
	 * 
	 * @param resolution
	 *            String
	 */
	@Override
	public void setResolution(String resolution) {
		this.webxmlParser.setContextParamValue("resolution", resolution);
		try {
			this.webxmlParser.save();
		} catch (IOException e) {
			throw new SiteException(e);
		}
	}

	/**
	 * 创建子栏目
	 * 
	 * @param name
	 *            String 栏目名称，注意不是显示名称。缺省的，显示名称和栏目名称是一样的。之后可以修改显示名称，而栏目名称不能修改
	 * @return Channel
	 */
/*
	public Channel addChannel(Channel parentChannel, String name, String type) {
		ChannelType channelType = this.factory.getChannelType(name);
		if (channelType == null)
			throw new IllegalArgumentException("Unknown channel type for " + type + ".");
		File file = this.getFolder();
		file = new File(file, name + "." + channelType.getPostfix());
		Channel newChannel;
		try {
			newChannel = (Channel) Class.forName(channelType.getClassName()).newInstance();
		} catch (InstantiationException e) {
			throw new SiteException(e);
		} catch (IllegalAccessException e) {
			throw new SiteException(e);
		} catch (ClassNotFoundException e) {
			throw new SiteException(e);
		}
		newChannel.init(this, file);
		this._channels.put(newChannel.getName(), newChannel);
		return newChannel;
	
	}
*/

	

	


	/**
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof SiteTemplate)) {
			return false;
		}
		return ((SiteTemplate) obj).getName().equalsIgnoreCase(this.getName());
	}



	/**
	 * 将当前网站模板克隆到目标模板
	 * 
	 * @param destTemplate
	 *            目标模板
	 * @throws IOException
	 */
	@Override
	public void clone(SiteTemplate destTemplate) throws IOException {
		File dest = ((SiteTemplateImpl)destTemplate).folder;
		IOFileFilter WEB_INF_FILTER = FileFilterUtils.notFileFilter(FileFilterUtils.andFileFilter(FileFilterUtils.directoryFileFilter(), FileFilterUtils.nameFileFilter("WEB-INF")));
		IOFileFilter CLIP_FILTER = FileFilterUtils.notFileFilter(FileFilterUtils.andFileFilter(FileFilterUtils.directoryFileFilter(), FileFilterUtils.nameFileFilter("clip")));
		FileUtils.copyDirectory(this.folder,dest, FileFilterUtils.makeSVNAware(FileFilterUtils.andFileFilter(WEB_INF_FILTER, CLIP_FILTER)));
	}

	/**
	 * 获取对本模板的描述
	 * 
	 * @return String
	 */
	public String getDescription() {
		return this.webxmlParser.getDescription();
	}

	/**
	 * 模板描述
	 * 
	 * @param description
	 *            String
	 */
	public void setDescription(String description) {
		this.webxmlParser.setDescription(description); 
		try {
			this.webxmlParser.save();
		} catch (IOException e) {
			throw new SiteException(e);
		}
	}

/**
	public Channel getBindingChannel(NodeDefinition bindingNode) {
		NodeDefinition definition = bindingNode;
		while (definition != null) {
			Channel channel = this.getChannel(this.bindings().get(definition.getID()));
			if (channel != null)
				return channel;
			definition = definition.getSuperDefinition();
		}
		return null;
	}

	
	public void setBindingChannel(NodeDefinition bindingNode, Channel channel) {
		DaoFactory factory = this.factory.newDaoFactory();
		try {
			factory.open();
			ChannelNodeDefinitionDao dao = (ChannelNodeDefinitionDao) factory.getDao(ChannelNodeDefinitionDao.class);
			dao.delete(this.getID(), bindingNode.getID());
			ChannelNodeDefinitionData data = new ChannelNodeDefinitionData();
			data.setTemplateID(this.getID());
			data.setChannelID(channel.getID());
			data.setNodeDdefinitionID(bindingNode.getID());
			dao.insert(data);
			this.bindings().put(bindingNode.getID(), channel.getID());
		} catch (SQLException e) {
			factory.rollback();
			throw new DatabaseException(e);
		} finally {
			factory.close();
		}
	}

	public void clearBinding(Channel channel) {
		DaoFactory factory = this.factory.newDaoFactory();
		try {
			factory.open();
			ChannelNodeDefinitionDao dao = (ChannelNodeDefinitionDao) factory.getDao(ChannelNodeDefinitionDao.class);
			dao.deleteAll(channel.getSiteTemplate().getID(), channel.getID());
			this.bindings().values().remove(channel.getID());
		} catch (SQLException e) {
			factory.rollback();
			throw new DatabaseException(e);
		} finally {
			factory.close();
		}

	}

	public NodeDefinition[] getBinding(Channel channel) {
		Vector<NodeDefinition> definitions = new Vector<NodeDefinition>();
		for (String key : this.bindings().keySet()) {
			if (this.bindings().get(key).equals(channel.getID())) {
				NodeDefinition definition = this.factory.getRepository().getDefinitionManager().getDefinition(key);
				if (definition != null)
					definitions.add(definition);
			}
		}
		return (NodeDefinition[]) definitions.toArray(new NodeDefinition[definitions.size()]);
	}
*/
	/**
	 * 获得指定ID的栏目
	 * 
	 * @param contextPath
	 *            String 栏目的ID或者name
	 * @return Channel
	 */
	@Override
	public Channel getChannel(String contextPath) {
		
		if (contextPath == null)
			return null;
		//兼容老版本数据
		if(contextPath.indexOf('.')<0)
			contextPath = contextPath+".jsp";
		//判断文件是否存在，不存在则返回空，并删除缓存；
		String path = this.getContextPath(contextPath);
		File file =null ;
		if(path.length()>0)
			file= new File(this.folder, path.substring(1));
		else
			file = this.folder;
		if(!file.exists()){
			this.channels.remove(path);
			return null;
		}
		//判断栏目是否已经加载，未加载，则加载；
		Channel channel = this.channels.get(path);
		if(channel!=null)
			return channel;
		//加载栏目；；
		for(Enumeration<ChannelType> types=this.factory.channelTypes();types.hasMoreElements();){
			ChannelType type = types.nextElement();
			if(FilenameUtils.isExtension(path,type.getPostfix())){
				try{
					GeneralChannel newChannel = (GeneralChannel)Class.forName(type.getClassName()).newInstance();
					newChannel.init(this, file);
					channel= newChannel;				
				}catch(Exception ex){
					throw new SiteException(ex);
				}
				this.channels.put(channel.getContextPath(), channel);
				return channel;
			}
		}
		return null;
	}
	/**
	 * ERROR栏目
	 * 
	 * @return Channel
	 */
	public Channel getErrorChannel() {
		return this.getChannel("/error.jsp");
	}
	/**
	 * Login栏目
	 */
	public Channel getLoginChannel() {
	
		return this.getChannel("/login.jsp");
	}

	/* 
	 * 获取网站所使用的语言
	 * @see com.fulong.longcon.site.SiteTemplate#getLocale()
	 */
	public Locale getLocale() {
		String locale = this.webxmlParser.getContextParamValue("locale");
		if(locale == null)
			return Locale.CHINA;
		return new Locale(locale);
	}

	/* 
	 * 设置网站语言
	 * @see com.fulong.longcon.site.SiteTemplate#setLocale(java.util.Locale)
	 */
	public void setLocale(Locale locale) {
		this.webxmlParser.setContextParamValue("locale", locale.getLanguage()); 
		try {
			this.webxmlParser.save();
		} catch (IOException e) {
			throw new SiteException(e);
		}
	}

	/**
	 * 将相对于网站的路径转成绝对路径
	 * @param relative
	 * @return
	 */
	@Override
	public String getRealPath(String relative) {
		
		return new File(this.folder,relative).getPath();
	}
	/**
	 * 将路径转成相对路径
	 * @param relative
	 * @return
	 */
	@Override
	public String getContextPath(String relative) {
		
		String path = FilenameUtils.normalize(relative);
		String prefix = FilenameUtils.getPrefix(relative);
		path = FilenameUtils.separatorsToUnix(path);
		if(prefix == null || prefix.length()==0) //相对路径
			return "/"+path;
		
	//梁树辉修改于2011年8月3日,更正linux路径错误的问题
		if(path.indexOf(this.folder.getPath())==-1){
		if(prefix.length()==1) //以"/"或者"\"开始的上下文路径；
			return path;
		}
		//绝对路径，换算成相对于当前文件夹的路；
		path = path.substring(this.folder.getPath().length());
		return path;
	}
	@Override
	public SiteFactory getSiteFactory() {
		 return this.factory;
	}


	@Override
	public SiteFolder getFolder(String contextPath) {
		if(contextPath == null || contextPath.length()==0 || contextPath.equalsIgnoreCase("/")
				||contextPath.equalsIgnoreCase("\\"))
			return this.getRootFolder();
		String path = this.getContextPath(contextPath);
		File folder = null;
		if(path.length()==0)
			folder = this.folder;
		else
			folder =new File(this.folder, path.substring(1));
		if(!folder.exists())
			return null;
		SiteFolder siteFolder = this.folders.get(path);
		if(siteFolder!=null)
			return siteFolder;
		siteFolder = new SiteFolderImpl(this.getRootFolder(), path);
		this.folders.put(path, siteFolder);
		return siteFolder;
	}
	
	public int getSiteCount(){
		Query query = this.factory.getRepository().getQueryManager().
			createQuery(this.factory.getRepository().getDefinitionManager().getDefinition("site-scheme"), Query.SQL);
		query.filterByProperty("templates", this.name);
		return (int)query.nodes().getSize();
	}
	@SuppressWarnings("unchecked")
	@Override
	public Iterator<Channel> getChannels() {
		List channels = new ArrayList();
		for (Iterator<String> iterator = root.getChannelNames(); iterator.hasNext();) {
			Channel child = root.getChannel(iterator.next());
			channels.add(child);
		}
		// 递归调用，将子文件夹的栏目添加进来；
		for (Iterator<String> iterator = root.getChildNames(); iterator.hasNext();) {
			SiteFolder child = root.getChild(iterator.next());
			channels.add(child);
		}
		
		return channels.iterator();
	}
}
