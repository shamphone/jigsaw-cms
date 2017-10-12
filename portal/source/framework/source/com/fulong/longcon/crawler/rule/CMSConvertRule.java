package com.fulong.longcon.crawler.rule;

import java.net.URL;
import java.util.Map;

import com.fulong.longcon.crawler.ConvertRule;
import com.fulong.longcon.crawler.URLUtils;

/**
 * 针对内容管理系统2.5提供的转换规则
 *
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */
public class CMSConvertRule  implements ConvertRule {
    private String prefix="/cms";
    /**
     *
     * @param url String
     * @return String
     */
    @SuppressWarnings("unchecked")
	public String convert(URL url) {
        String query=url.getQuery();
        if((query==null)||(query.length()==0))
            return this.prefix+"/index.html";
        Map parameters=URLUtils.parseQuery(url);
        if(parameters.size()==0)
            return this.prefix+"/index.html";
        if(parameters.size()>2)
            return url.toString();
        String channel=(String)parameters.get("channelID");
        String content=(String)parameters.get("contentID");
        if(channel==null)
            return url.toString();
        if(content==null)
            return this.prefix+"/"+channel+"/index.html";
        return  this.prefix+"/"+channel+"/"+content+".html";
    }


    /**
     *
     * @param url String
     * @return boolean
     */
    public boolean needToChange(URL url) {
        return url.getPath().indexOf("browsePage.do")>=0;
    }


    public void setContextPath(String path){
        this.prefix=path;
    }
}
