package com.fulong.site.channel;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.common.FileUtils;
import com.fulong.longcon.site.Channel;

/**
 * 保持源文件。注意，如果是临时文件，则应该输入临时文件的路径。
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
public class SaveSourceAction extends ChannelBaseAction {
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
        
        boolean isClip = (request.getParameter("clip")!=null);
        if(!isClip)
        	channel.submit(this.getIpAddr(request), is);
        else{
        	FileUtils.write(channel.getPage(),is);
        }
        is.close();
        return null;
    }
}
