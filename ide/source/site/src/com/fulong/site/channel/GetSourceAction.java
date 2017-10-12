package com.fulong.site.channel;

import java.io.File;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.Channel;

/**
 * 获取指定路径下的文件的源代码。注意，如果是临时文件，则应该输入临时文件的路径。
 *
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
public class GetSourceAction extends ChannelBaseAction {
    public ActionForward templateExecute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        String path = request.getParameter("path");
        boolean working = (request.getParameter("working")!=null);
        Channel channel = this.parseChannel(path,request);
        File file = channel.getPage();
        if(working)
        	file = new File(file.getPath()+".bak");
        if (!file.exists())
            return mapping.findForward("file.not.exists");
        response.setContentType("text/plain");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        Writer writer = response.getWriter();
        writer.write(FileUtils.readFileToString(file, "UTF-8"));
        writer.flush();
        writer.close();
        return null;
    }

}
