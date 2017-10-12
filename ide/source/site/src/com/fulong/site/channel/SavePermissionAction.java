package com.fulong.site.channel;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.site.form.PermissionForm;

/**
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lichengzhao
 * @version 1.0
 */
public class SavePermissionAction  extends ChannelBaseAction {

    protected ActionForward templateExecute(ActionMapping mapping,
                                            ActionForm aform,
                                            HttpServletRequest request,
                                            HttpServletResponse response) throws
            Exception {
    	PermissionForm form = (PermissionForm) aform;
//		SiteTemplate template = this.getSiteFactory(request).getTemplate(form.getTemplateId());
//		Group group = this.getPassportProvider().getGroup(form.getPrincipalId());
//		List<String> selectedChannelIds = null;
//		if (form.getChannelIds() == null)
//			selectedChannelIds = new ArrayList<String>(0);
//		else
//			selectedChannelIds = Arrays.asList(form.getChannelIds());
//		Channel channel = null;
//		Iterator<Channel> channels =  template.getChannels();
//		while (channels.hasNext()) {
//			channel = channels.next();
//			if (channel == null)
//				continue;
//			if (selectedChannelIds.contains(channel.getID())) {
//				channel.setSecure(true);
//				channel.getAuthorization().addMember(group);
//			} else {
//				channel.getAuthorization().removeMember(group);
//			}
//		}
		return mapping.findForward("success");
	}
}