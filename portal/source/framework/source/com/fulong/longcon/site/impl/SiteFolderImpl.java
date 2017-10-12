/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.longcon.site.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;

import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.ChannelType;
import com.fulong.longcon.site.SiteException;
import com.fulong.longcon.site.SiteFolder;
import com.fulong.longcon.site.SiteTemplate;

/**
 * SiteFolderImpl
 * 
 * @author <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date 2010-5-25
 */
public class SiteFolderImpl implements SiteFolder {
	private SiteTemplate template;
	private File folder;
	private Properties properties;
	private static final String INFOFILE="folder.properties";
	private static final String EXCLUDES[]=new String[]{"META-INF","WEB-INF","repeater","cache","clip"};

	public SiteFolderImpl(SiteTemplate template, File root) {
		this.template = template;
		this.folder = root;
		System.out.println("ffolder:"+folder.getPath());
		this.loadProperties();
	
	}

	public SiteFolderImpl(SiteFolder parent, String relativePath) {
		
		this.template = parent.getSiteTemplate();
		if ((relativePath == null) || (relativePath.trim().length() == 0))
			this.folder = parent.getFile();
		else if (relativePath.startsWith("/") || relativePath.startsWith("\\")) {
			// 相对网站根目录的路径
			this.folder = new File(this.template.getRootFolder().getFile(), relativePath.substring(1));
		} else {
			// 相对当前目录的路径；
			this.folder = new File(parent.getFile(), relativePath);
		}
		this.loadProperties();
	}
	/**
	 * 加载和文件夹相关的属性设置
	 */
	private void loadProperties(){
		this.properties = new Properties();
		File propFile = new File(this.folder, INFOFILE);
		if (propFile.exists()) {
			try {
				InputStream is = new FileInputStream(propFile);
				properties.load(is);
				is.close();
			} catch (IOException ioe) {
				//just ignore this exception;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.longcon.site.SiteFolder#addChannel(java.lang.String)
	 */
	@Override
	public Channel addChannel(String name,String type,InputStream content) throws IOException {
		ChannelType channelType = this.template.getSiteFactory().getChannelType(type);
		if(channelType==null){
			throw new IllegalArgumentException("no such channel type:"+ channelType+".");
		}
		String postfix = channelType.getPostfix();
		File file=new File(this.folder, name+"."+postfix);
		if(file.exists())
			throw new IllegalArgumentException("file "+ file.getPath()+" already exists.");
		file.createNewFile();
		com.fulong.common.FileUtils.write(file, content);
		Channel channel = this.template.getChannel(this.getContextPath()+"/"+name+"."+postfix);
		channel.create();
		return channel;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fulong.longcon.site.SiteFolder#delete(com.fulong.longcon.site.Channel )
	 */
	@Override
	public void delete() throws IOException {
		FileUtils.deleteDirectory(this.folder);
	}

	/*
	 * 
	 * 获取该文件夹下的所有栏目
	 * @see com.fulong.longcon.site.SiteFolder#getChannels()
	 */
	@Override
	public Iterator<String> getChannelNames() {
		final Vector<String> channels = new Vector<String>();
		
		this.folder.listFiles(new FileFilter(){
			@Override
			public boolean accept(File pathname) {
				if(pathname.isDirectory()){
					return false;
				}
				String name = pathname.getName();
				for(Enumeration<ChannelType> iterator = template.getSiteFactory().channelTypes();iterator.hasMoreElements();){
					ChannelType type = iterator.nextElement();
					if(FilenameUtils.isExtension(name, type.getPostfix())){
						channels.add(name);
					}
				}
				return false;
			}
		});
		return channels.iterator();
	}

	/*
	 * 获取所有直接的子文件夹
	 * 
	 * @see com.fulong.longcon.site.SiteFolder#getChildren()
	 */
	@Override
	public Iterator<String> getChildNames() {
		final Vector<String> folders = new Vector<String>();
		this.folder.listFiles(new FileFilter(){
			@Override
			public boolean accept(File dir) {
				if(dir.isDirectory()&&!dir.getName().startsWith(".")&&!dir.getName().startsWith("_")&&!(ArrayUtils.contains(EXCLUDES, dir.getName()))){
					folders.add(dir.getName());					
				}
				return false;
			}
		});
		return folders.iterator();
	}
	/*
	 * 获取所有直接的子文件夹
	 * 
	 * @see com.fulong.longcon.site.SiteFolder#getChildren()
	 */
	@Override
	public SiteFolder getChild(String name) {
		String contextPath = this.getContextPath();
		return this.template.getFolder(contextPath+"/"+name);
		
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.longcon.site.SiteFolder#getDisplayName()
	 */
	@Override
	public String getDisplayName() {		
		return this.properties.getProperty("display.name", this.getName());
	}
	/* (non-Javadoc)
	 * @see com.fulong.longcon.site.SiteFolder#setDisplayName(java.lang.String)
	 */
	@Override
	public void setDisplayName(String name) {		
		this.properties.setProperty("display.name", name);
		File file = new File(this.folder, INFOFILE);
		try{
		FileOutputStream fos = new FileOutputStream(file);
		this.properties.store(fos, "folder info");
		fos.close();
		}catch (IOException ios){
			throw new SiteException(ios);
			
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.longcon.site.SiteFolder#getFile()
	 */
	@Override
	public File getFile() {
		return this.folder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.longcon.site.SiteFolder#getName()
	 */
	@Override
	public String getName() {
		return this.folder.getName();
	}

	@Override
	public Channel getChannel(String contextPath) {
		String path = contextPath;
		if(path.startsWith("/")||path.startsWith("\\"))
			return template.getChannel(contextPath);
		path = this.getContextPath()+"/"+contextPath;
		return template.getChannel(path);
	}

	@Override
	public String getContextPath() {
		return this.template.getContextPath(this.folder.getPath());
	}

	@Override
	public SiteFolder getParent() {
		String path = this.getContextPath();
		if (path.length() == 0)
			return null;
		path = path.substring(0, path.lastIndexOf('/'));
		return new SiteFolderImpl(this, path);
	}

	@Override
	public SiteTemplate getSiteTemplate() {
		return this.template;
	}

	@Override
	public SiteFolder addChild(String name, String displayName) {
		File child = new File(this.folder, name);
		if(child.exists())
			throw new IllegalArgumentException("Folder with name "+ name +" already exists in "+ this.folder.getPath()+"." );
		child.mkdirs();
		SiteFolder newFolder = this.getChild(name);
		newFolder.setDisplayName(displayName);
		return newFolder;
	}
		

}
