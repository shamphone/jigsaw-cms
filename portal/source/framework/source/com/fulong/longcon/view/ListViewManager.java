package com.fulong.longcon.view;

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
public interface ListViewManager {
    /**
     * 获取指定参数的视图设置
     * @param name String
     * @return ListView
     */
    public ListView getListView(String name);

    /**
     * 创建一个列表视图
     * @param name String
     * @param columns String[],此参数不能为null
     * @return ListView
     */
    public ListView createListView(String name, String[] columns);


}
