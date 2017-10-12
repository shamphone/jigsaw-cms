package com.fulong.cms.harvest;

import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;

/**
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author HaoJingwei
 * @version 1.0
 */
public class Harvest{
    private ScraperConfiguration config;
    public Scraper scraper;
  //  public String workingDir;
    public Harvest(String configPath, String workingDir){
        try{
            ScraperConfiguration config = new ScraperConfiguration(configPath);
            scraper = new Scraper(config, workingDir);
            scraper.setDebug(false);
            scraper.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    /*   返回webharvest工作状态码
     *   @return int
     *   STATUS_READY = 0;
     *   STATUS_RUNNING = 1;
     *   STATUS_PAUSED = 2;
     *   STATUS_FINISHED = 3;
     *   STATUS_STOPPED = 4;
     *   STATUS_ERROR = 5;
     *   STATUS_EXIT = 6;
     */
    public  int status(){
        return scraper.getStatus();
    }

    /*   返回webharvest工作路径
     *   @return String
     *   STATUS_READY = 0;
     *   STATUS_RUNNING = 1;
     *   STATUS_PAUSED = 2;
     *   STATUS_FINISHED = 3;
     *   STATUS_STOPPED = 4;
     *   STATUS_ERROR = 5;
     *   STATUS_EXIT = 6;
     */
    public String workD(){
        return scraper.getWorkingDir();
    }
}
