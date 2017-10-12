package com.fulong.longcon.repository;

import java.io.InputStream;
import java.util.Calendar;

/**
 * 属性值，这个类提供一个和类型无关的属性设置方法。
 * <p>Title: WebMaster Core Library</p>
 *
 * <p>Description: Longcon WebMaster SV3</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Tech. LTD</p>
 *
 * @author Lixf
 * @version 1.0
 */
public interface Value extends Comparable<Object>{
    /**
     * 类型，参见PropertyValueType中的类型定义
     * @return int
     */
    public int getType();


    /**
     * 将属性值转换成布尔值类型输出
     * @return boolean
     * @exception ValueFormatException 数据格式转换错误
     */
    public boolean getBoolean() throws ValueFormatException;

    /**
     * 将属性值转换成日期类型输出
     * @return Calendar
     * @exception ValueFormatException 数据格式转换错误
     */

    public Calendar getDate() throws ValueFormatException;

    /**
     * 将属性值转换成浮点类型输出，如果无法转换，则抛出ValueFormatException
     * @return double
     * @exception ValueFormatException 数据格式转换错误
     */
    public double getDouble() throws ValueFormatException;

    /**
     * 将属性值转换成长整形输出，如果无法转换，则抛出ValueFormatException
     * @return long
     * @throws ValueFormatException 数据格式转换错误
     */
    public long getLong() throws ValueFormatException;

    /**
     * 将属性值转换成流输出，如果无法转换，则抛出ValueFormatException
     * @return InputStream
     * @throws ValueFormatException 数据格式转换错误
     */
    public InputStream getStream() throws ValueFormatException;
    /**
     * 将属性值转换成字符串输出，如果无法转换，则抛出ValueFormatException
     * @return String
     * @throws ValueFormatException 数据格式转换错误
     */
    public String getString() throws ValueFormatException;


}
