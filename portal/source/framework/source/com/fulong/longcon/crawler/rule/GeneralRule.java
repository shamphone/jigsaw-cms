package com.fulong.longcon.crawler.rule;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Pattern;

import com.fulong.longcon.crawler.URLUtils;
import java.util.Iterator;

/**
 * 通过规则配置文件来配置的规则,规则文件的例子,参考crawler.rule.acs.xml
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
public class GeneralRule {
    private String pathPattern;
    private String filePattern;
    private List<String> queryKeyIncluded;
    private List<String> queryKeyExcluded;
    public GeneralRule() {
        queryKeyIncluded = new Vector<String>();
        queryKeyExcluded = new Vector<String>();
    }

    /**
     * 这个规则是否适用于url。
     * @param url URL
     * @return boolean
     */
    @SuppressWarnings("unchecked")
	public boolean applyTo(URL url) {
        String path = url.getPath();
        boolean applicable=Pattern.matches(pathPattern, new StringBuffer(path));
        Map queries = URLUtils.parseQuery(url);
        if(applicable){
            for(int i=0;i<queryKeyIncluded.size();i++){
                if(queries.get(queryKeyIncluded.get(i))==null)
                    applicable=false;
            }
        }
        if(applicable){
            for(int i=0;i<queryKeyExcluded.size();i++){
                if(queries.get(queryKeyExcluded.get(i))!=null)
                    applicable=false;
            }
        }
        return applicable;
    }

    @SuppressWarnings("unchecked")
	public String convert(URL url){
        String path = url.getPath();
        Map queries = URLUtils.parseQuery(url);
        String[] splits=path.split("[/\\.]");
        String filePath=this.filePattern;
        for(int i=0;i<splits.length-1;i++){
            String pattern="\\{"+i+"\\}";
            filePath=filePath.replaceAll(pattern,splits[i+1]);
        }
        for(Iterator keys=queries.keySet().iterator();keys.hasNext();){
            String key=(String)keys.next();
            String value=(String)queries.get(key);
            String pattern="\\{"+key+"\\}";
            filePath=filePath.replaceAll(pattern,value);
        }
        return filePath;
    }

    public String getPathPattern() {
        return pathPattern;
    }

    public String getFilePattern() {

        return filePattern;
    }

    public void setPathPattern(String pathPattern) {
        this.pathPattern = pathPattern;
    }

    public void setFilePattern(String filePattern) {

        this.filePattern = filePattern;
    }

    public void addQueryKeyIncluded(String key) {
        this.queryKeyIncluded.add(key);
    }

    public void addQueryKeyExcluded(String key) {
        this.queryKeyExcluded.add(key);
    }
}
