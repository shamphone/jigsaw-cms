package com.fulong.portlet.cms.list;

import com.fulong.portlet.LongconPortlet;

/**
 * 产生类似如下HTML的列表占位符：
 * <ul>
 * <li><span>内容1</span><span>（时间）<span></li>
 * <li><span>内容1</span><span>（时间）<span>
 * <li>
 * </ul>
 *其中li的内容是可配置的。每一个<span>是通过数据绑定添加到列表中来。对于列表占位符，用户可以作如下配置： 1． repository-id 内容库。
 * 2． filter-field,filter-pattern:过滤得内容域和过滤规则 选择内容过滤规则。 3. order-patterns:
 * 排序规则，例如 "name DESC",用户可以设置多个排序规则，对应排序优先级 4．
 * block-types:显示块的类型，item为普通属性，link为超链接属性，template为自定义的数据绑定格式。 5.
 * block-expressions
 * :如果是普通属性或者超链接属性，则为"property","format"形式的表达式，如果是自定义的数据绑定，则为数据绑定表达式。 6.
 * block-styles:每个显示块的样式 在preferences存储上，所有数据块的内容定义都保存在一起，例如，fields关键字对应的值是一个数组，
 * 描述从第一块到最后一块的数值。
 * 
 * <p>
 * Title: WebMaster Core Library
 * </p>
 * 
 * <p>
 * Description: Longcon WebMaster SV3
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Tech. LTD
 * </p>
 * 
 * @author Lixf
 * @version 1.0
 */
public class ContentListPortlet extends LongconPortlet {

	private static final long serialVersionUID = -5333421387053218669L;

}
