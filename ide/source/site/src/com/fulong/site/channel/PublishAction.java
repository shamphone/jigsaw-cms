package com.fulong.site.channel;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.SiteFolder;
import com.fulong.longcon.site.SiteTemplate;

/**
 * 发布页面：备份当前已发布版本，将工作页面复制到当前页面上。
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author 李雄锋
 * @version 1.0
 */
public class PublishAction extends ChannelBaseAction {
    public ActionForward templateExecute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        SiteTemplate site=this.getSiteFactory(request).getTemplate(request.getParameter("templateID"));
        Channel channel=site.getChannel(request.getParameter("ID"));
        request.setAttribute("template", site);
        request.setAttribute("defaultChannel", channel);
        request.setAttribute("channelTree", getUnpublishedChannels(site));
        return mapping.findForward("success");
    }
    /**
     * 获取网站模版中未发布的栏目
     * @param template
     * @return
     */
    private List<Channel> getUnpublishedChannels(SiteTemplate template){
    	List<Channel> channels = new Vector<Channel>();
    	this.getUnpublishedChannels(template.getRootFolder(),channels);
    	return channels;
    	
    }
    /**
     * 递归遍历一个目录下未发布的栏目
     * @param folder
     * @param channels
     */
  private void  getUnpublishedChannels(SiteFolder folder,List<Channel> channels){
	  
	  
	  for(Iterator<String>names =folder.getChannelNames();names.hasNext();){
		  Channel channel=folder.getChannel(names.next());
		  if(!channel.isPublished())
			  channels.add(channel);
	  }
	 
	  for(Iterator<String>names =folder.getChildNames();names.hasNext();){
		  SiteFolder child = folder.getChild(names.next());
		  getUnpublishedChannels(child,channels);
	  }
    }
}
