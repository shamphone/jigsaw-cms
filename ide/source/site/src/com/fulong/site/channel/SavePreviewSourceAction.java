package com.fulong.site.channel;

import java.io.File;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.common.FileUtils;
import com.fulong.longcon.site.Channel;


/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @date 2010-6-18	
 * @version 1.0.1
 */
public class SavePreviewSourceAction extends ChannelBaseAction {
    public ActionForward templateExecute(ActionMapping mapping,
                                         ActionForm form,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws
            Exception {
    	
    	InputStream is = request.getInputStream();
    	String path = request.getParameter("path");
        if(path.indexOf('?')>0)
            path=path.substring(0,path.indexOf('?'));
        Channel channel=this.parseChannel(path,request);
        String previewPath = channel.getPage().toString()+".bak.bak";
        FileUtils.write(new File(previewPath), is);
        is.close();
        return null;
    }
}
