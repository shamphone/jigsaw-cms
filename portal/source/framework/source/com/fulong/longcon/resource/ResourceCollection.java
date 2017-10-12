package com.fulong.longcon.resource;

import java.security.Principal;

/**
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author <a href='mailto:lishaobo@fulong.com.cn'>lishaobo</a>
 * @version 2.0
 */
public interface ResourceCollection {

    /**
     * 按照所有者来过滤
     * @param owner Principal
     */
    public void filterByOwner(Principal owner);

    /**
     * 按照resource类型过滤，是文件或者文件夹,false为文件，true为文件夹
     * @param owner Principal
     */
    public void filterByType(boolean isfolder);

    /**
     * 按照创建者过滤
     * @param creator Principal
     */
    public void filterByCreator(Principal creator);

    /**
     * 按照创建日期排序
     * @param asc 排序方式
     */
    public void sortByCreatedDate(boolean asc);

    /**
     * 按照最后修改日期排序
     * @param  asc 排序方式
     */
    public void sortByLastModifiedDate(boolean asc);

    /**
     * 获取查询结果遍历器
     * @return SiteDomainIterator
     */
    public ResourceIterator resources();

}
