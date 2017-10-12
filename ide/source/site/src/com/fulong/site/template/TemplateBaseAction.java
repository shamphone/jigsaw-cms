package com.fulong.site.template;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.util.StringUtils;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.SiteFolder;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.site.SiteBaseAction;



/**
 *
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author jiangqi
 * @version 1.0
 */
public abstract class TemplateBaseAction extends SiteBaseAction {

    /**
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws
            Exception {
        return templateExecute(mapping, form, request, response);
    }

    protected abstract ActionForward templateExecute(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception;
    /**
     * 获取语言列表
     */
  	protected Iterator<Node> getLanguages(HttpServletRequest request) {
  		NodeDefinition languageDefinition = this.getRepository(request).getDefinitionManager().getDefinition("locale-language");
  	    Query query = this.getRepository(request).getQueryManager().createQuery(languageDefinition, Query.SQL);
  	    query.sortByOrdinal(true);
      	return query.nodes();
    }
  	
  	@SuppressWarnings("unchecked")
	protected File getChannelFile(SiteTemplate template,String name) {
        SiteFolder root = template.getRootFolder();
        File rootFolder = root.getFile();
        IOFileFilter fileFilter = FileFilterUtils.andFileFilter(new RegexFileFilter(name+".[\\w]*"), FileFilterUtils.notFileFilter(FileFilterUtils.suffixFileFilter(".delete")));
  		IOFileFilter dirFilter = FileFilterUtils.andFileFilter(FileFilterUtils.makeSVNAware(null), FileFilterUtils.notFileFilter(FileFilterUtils.nameFileFilter("WEB-INF")));
  		Iterator<File> it = FileUtils.iterateFiles(rootFolder,fileFilter,dirFilter);
        if(it.hasNext()){
        	return it.next();
        }
    	return null;
    }
	
  	/**
  	 * 获取所有的模板
  	 */
  	protected List<SiteTemplate> getTemplates(HttpServletRequest request){
  		List<SiteTemplate> templates = new ArrayList<SiteTemplate>();
  		Iterator<String> stringIT = this.getSiteFactory(request).getTemplateNames();
  		while(stringIT.hasNext()){
  			String templateName = stringIT.next();
  			if(templateName!=null){
  				SiteTemplate template = this.getSiteFactory(request).getTemplate(templateName);
  				if(template!=null){
  					templates.add(template);
  				}
  			}
  		}
  		return templates;
  	}
  	
  	/**
  	 * 如果当前栏目已被签出，则检查栏目签出的ip和ipAddress是否是同一个ip，如果相同签出成功，否则失败
  	 * 如果当前栏目还没有被签出，则签出该栏目，签出成功
  	 * @param channel
  	 * @param ipAddress
  	 * @return
  	 * @throws IOException
  	 */
  	protected boolean mildCheckout(Channel channel,String IP) throws IOException {
  		if(channel.isWriting()){
			//如果锁住channel的ip和ipAddress不相等 ，
			String writer = channel.getWriter();
			if(!IP.equals(writer)){
				return false;
			}
		}else{
			//签出channel,并记录下ip
			channel.checkout(IP);
		}
  		return true;
	}
  	
  	/**
  	 * 如果当前用户已签入，则签出成功，否则签出失败
  	 * @param channel
  	 * @param ipAddress
  	 * @return
  	 * @throws IOException
  	 */
  	protected boolean mildCheckin(Channel channel,String IP) throws IOException {
  		if(channel.isWriting()){
			//如果锁住channel的ip和ipAddress不相等 ，
			String writer = channel.getWriter();
			if(IP.equals(writer)){
				channel.checkin(IP);
				return true;
			}
		}
  		return false;
	}
  	
	/**
	 * 复制占位符片段
	 * @param sourceChannel
	 * @param sourcePortletId
	 * @param destChannel
	 * @param destPortletId
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	protected void copyClip(Channel sourceChannel,String sourcePortletId,Channel destChannel,String destPortletId ) throws IOException {
		
		File folder = getFolder(sourceChannel);
		if(!folder.exists()){
			return;
		}
		
		File destFolder = getFolder(destChannel);
		if(!destFolder.exists()){
			destFolder.mkdirs();
		}
		Collection<File> files = FileUtils.listFiles(folder, new RegexFileFilter(sourcePortletId+".*\\.jspf"), null);
		for(File file:files){
			
			String name = file.getName().replace(sourcePortletId, destPortletId);
			
			File destFile = new File(destFolder,name);
			FileUtils.copyFile(file, destFile);
			
			File portletFolder = new File(folder,"_"+file.getName());
			File destPortletFolder = new File(destFolder,"_"+name);
			if(portletFolder.exists()){
				if(!destPortletFolder.getParentFile().exists()){
					destPortletFolder.getParentFile().mkdirs();
				}
				FileUtils.copyDirectory(portletFolder, destPortletFolder,FileFilterUtils.makeSVNAware(null));
			}
		}
	}
	
	protected File getFolder(Channel channel){
		return getFolder(channel.getPage());
	}
	
	protected File getFolder(File file){
		String path = file.getPath();
		path = StringUtils.cleanPath(path);
		int index = path.lastIndexOf("/");
		String portletPath = path.substring(0,index+1);
		portletPath += "_";
		portletPath += path.substring(index+1);
		return new File(portletPath);
	}
	
	/**
	 * 清空片段文件夹
	 * @param channel
	 * @throws IOException 
	 */
	public void clearClip(Channel channel) throws IOException{
		File folder = getFolder(channel);
		if(folder.exists()){
			FileUtils.deleteDirectory(folder);
		}
	}
	
	/**
	 * 清空片段文件夹
	 * @param channel
	 * @throws IOException 
	 */
	public void clearClip(File channelFile) throws IOException{
		File folder = getFolder(channelFile);
		if(folder.exists()){
			FileUtils.deleteDirectory(folder);
		}
	}
	
}
