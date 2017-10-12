package com.fulong.longcon.site;

import java.util.Date;

import com.fulong.common.util.RangeIterator;
/**
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
public interface SiteCollection extends Iterable<Site>{
    /**
     * 根据类别过滤(废弃)
     * @param category SiteCategory
     
    public void filterByCategory(SiteCategory category);*/

    /**
     * 按照网站模板来过滤
     * @param site Site
     */
    public void filterByTemplate(String template);

    /**
     * 按创建时间来过滤
     * @param from Date
     * @param to Date
     */
    public void filterByCreatedDate(Date from, Date to);

    /**
     * 按照站点显示名称来过滤
     * @param name String
     */
    public void filterByName(String displayName);

    /**
     * 按照站点状态来过滤
     * @param state String
     */
    public void filterByState(String state);

    /**
     * 按照站点的卡号来过滤(membership表弃用，该方法失效)
     * @param membershipID String
    
    public void filterByMembership(String membershipID); */

    /**
     * 按照站点域名来过滤
     * @param domain String
     */
    public void filterByDomain(String domain);

    /**
     * 按照创建机构的名称来过滤(organization表弃用，该方法失效)
     * @param orgName String 机构名称
     
    public void filterByCreatedOrg(String orgName);
	*/

    /**
     * 按照创建日期排序
     * @param asc 排序方式
     */
    public void sortByCreatedDate(boolean asc);

    /**
     * 按照最后修改日期排序
     * @param  asc 排序方式
     */
    public void sortByRevisionDate(boolean asc);

    /**
     * 获取查询结果遍历器
     * @return SiteDomainIterator
     */
    public RangeIterator<Site> iterator();

    /**
     * 在网站名称，二级域名和建站机构中搜索
     * @param keyWord String 支持以下4种模糊搜索
     * 网站名称,显示名称,域名,网站的机构名称
     */
    public void filterByKeyWord(String keyWord);

    /**
     * 按照访问量排序
     * @param asc boolean
     */
    public void sortByClickCount(boolean asc);

}
