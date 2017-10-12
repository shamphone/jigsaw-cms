package com.fulong.longcon.site;

import com.fulong.common.util.RangeIterator;


/**
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
public interface SiteTemplateCollection extends Iterable<SiteTemplate>{
    /**
     * 根据类别过滤
     * @param category SiteCategory
     */
    public void filterByCategory(SiteCategory category);
    
    /**
     * 根据分辨率过滤
     * @param category SiteCategory
     */
    public void filterByResolution(String resolution);

    /**
     * 根据状态过滤
     * @param state String
     */
    public void filterByState(String state);

    /**
	 * 得到网站所使用的模板
	 * 
	 * @param state
	 *            String
	 */
	public void filterBySite(String siteid) ;
	
	/**
	 * 得到网站所使用导航模板
	 * 
	 * @param state
	 *            String
	 */
	public void filterBySiteNavigate(String siteid) ;
		
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
     * 结果集
     * @return SiteTemplateIterator
     */
    public RangeIterator<SiteTemplate> iterator();
}
