package com.fulong.site.channel;

import java.io.File;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.fulong.common.FileUtils;
import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.site.form.ChannelForm;
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
 * @author lichengzhao
 * @version 1.0
 */
public class DoImportAction extends ChannelBaseAction {
    protected ActionForward templateExecute(ActionMapping mapping,
                                            ActionForm tform,
                                            HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {
        ChannelForm form = (ChannelForm) tform;
        String templateName = form.getTemplateName();
        SiteTemplate template = this.getSiteFactory(request).getTemplate(templateName);
        Channel channel = template.getChannel(form.getPath());
        FormFile file=form.getTemplateFile();
        //ChannelPageParser parser=ChannelPageParser.createParser(new File(channel.getPage().getPath()+".bak"));        
		if (!(file == null || file.getFileSize() == 0)){
			String channelType = form.getType();
			String contentType = this.getContentType(channelType);
			InputStream in = new JSPUTF8InputStream(file.getInputStream(),contentType).getInputStream();
			FileUtils.write(new File(channel.getPage().getPath()+".bak"), in);
		}
        return mapping.findForward("success");
    }
}
