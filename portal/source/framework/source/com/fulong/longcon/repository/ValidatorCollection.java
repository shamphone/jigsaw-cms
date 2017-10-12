package com.fulong.longcon.repository;

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
public interface ValidatorCollection {

    /**
     * 是否必填
     * @return boolean
     */
    public boolean isRequired();

    /**
     * 是否唯一
     * @return boolean
     */
    public boolean isUnique();

    /**
     * 最大长度
     * @return int
     */
    public int getMaxLength();

    /**
     * 设置最大长度
     * @param length int
     */
    public void setMaxLength(int length);

    /**
     * 获取最小长度
     * @return int
     */
    public int getMinLength();

    /**
     * 设置最小长度
     * @param length int
     */
    public void setMinLength(int length);

    /**
     * 获取最大值设置
     * @return String
     */
    public String getMaxValue();

    /**
     * 设置最大值
     * @param value String
     */
    public void setMaxValue(String value);

    /**
     * 获取最小值
     * @return String
     */
    public String getMinValue();

    /**
     * 设置最小值
     * @param value String
     */
    public void setMinValue(String value);

    /**
     * 设置必填
     * @param required boolean
     */
    public void setRequired(boolean required);

    /**
     * 设置唯一性
     * @param unique boolean
     */
    public void setUnique(boolean unique);

    /**
     * 获得掩码校验，如果没有则返回空
     * @return String
     */
    public String getMask();

    /**
     * 设置掩码校验
     * @param mask String
     */
    public void setMask(String mask);

    public String[] toConstraints();

    public void setMaxnum(String value);

    public String getMaxnum();

////一下内容是DateRangeValidator用的
    public void setMinope(String value);

    public void setMindate(String value);

    public void setMinnow(String value);

    public void setMinOper(String value);

    public void setMinTime(String value);

    public void setMinNowflag(String value);

    public void setMaxope(String value);

    public void setMaxdate(String value);

    public void setMaxnow(String value);

    public void setMaxOper(String value);

    public void setMaxTime(String value);

    public void setMaxNowflag(String value);

    public String getMaxdate();

    public String getMaxNow();

    public String getMaxNowflag();

    public String getMaxope();

    public String getMaxOper();

    public String getMaxTime();

    public String getMindate();

    public String getMinNow();

    public String getMinNowflag();

    public String getMinope();

    public String getMinOper();

    public String getMinTime();
}

