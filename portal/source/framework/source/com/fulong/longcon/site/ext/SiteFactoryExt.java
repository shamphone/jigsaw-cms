package com.fulong.longcon.site.ext;

import java.io.File;

import com.fulong.common.dao.DaoFactory;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.site.SiteFactory;
import com.fulong.longcon.security.PassportProvider;
import com.fulong.longcon.counter.AccessCounterRepository;

/**
 * 网站工厂,网站的总入口,提供对网站和域名的管理.
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lishaobo
 * @version 1.0
 */
public interface SiteFactoryExt
    extends SiteFactory {
    /**
     * 获得内容库接口
     * @return Repository
     */
    public Repository getRepository();

    /**
     * 获得会员系统接口
     * @return InnerPassportProvider
     */
    public PassportProvider getPassport();

    /**
     * DAO
     * @return DaoFactory
     */
    public DaoFactory newDaoFactory();

    /**
     * 获得系统计数接口
     * @return AccessCounterRepository
     */
    public AccessCounterRepository getAccessCountRep();
    /**
     * 存放模板的基本目录
     * @return
     */
    public File getBaseDirectory();
    
    /**
     * 自动解除栏目写标记时间间隔
     * @return
     */
    public long getUnLockTime();
   

}
