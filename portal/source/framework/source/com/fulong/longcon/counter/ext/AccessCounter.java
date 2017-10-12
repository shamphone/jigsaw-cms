package com.fulong.longcon.counter.ext;


/**
 * 计数器
 * <p>Title: 龙驭会员管理系统2.0</p>
 *
 * <p>Description: 龙驭会员管理系统2.0</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author lixf
 * @version 2.0
 */
public interface AccessCounter {

	/**
	 * 添加增加计数值,这个方法并不直接增加计数值,而是保存到访问计数器库中,由计数器库统一保存.
	 */
    public void increase();
    /**
     * 获取当前计数
     * @return long
     */
    public long getCount();

    /**
     * 获取日访问量
     * @return long
     */
    public long getDayCount();
	
	public long getWeeklyCount();
	public long getMonthlyCount();
	public long getYearlyCount();
}
