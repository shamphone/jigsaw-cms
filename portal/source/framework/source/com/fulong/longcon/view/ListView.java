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
public interface ListView {
    /**
     * 获取所有的列
     * @return String[]
     */
    public String[] getColumns();
    /**
     * 列宽
     * @return int
     */
    public int getColumnWidth(String column);

    /**
     * 设置列
     * @param columns String[]，此参数不能为null
     */
    public void setColumns(String[] columns);

    /**
     * 设置列宽
     * @param column String
     * @param width int
     */
    public void setColumnWidth(String column, int width);
}
