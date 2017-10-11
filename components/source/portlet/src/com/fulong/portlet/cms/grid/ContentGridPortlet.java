package com.fulong.portlet.cms.grid;

import com.fulong.portlet.LongconPortlet;

/**
 * 内容表格占位符,以表格的形式展示内容列表. 占位符可配置的参数： 1.show-header:是否显示表头 2.show-footer:是否显示表尾
 * 3.show-pager:是否显示分页 3. repository-id：内容库 4.
 * filter-patterns:过滤规则,例如枚举类型的，使用[source
 * ]#[beijing,shanghai]或者[source]=[beijing] 5. order-patterns：排序规则，例如publishTime
 * DESC 5. table-style：表格的样式 6. header-style：表头的样式 7. footer-style:表尾的样式 8.
 * item-styles:数据行的样式； 以下以column开始的都是针对列的设置，这些都为等长的数组： 1.
 * column-types:类型，分为普通、链接、自定义三种类型,与ContentListPortlet相同 2.
 * column-expressions:列的内容的表达式,与ContentListPortlet相同 3. column-headers:列头的显示文字
 * 4. column-footers:列尾的显示文字 5. column-styles:列的样式
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
public class ContentGridPortlet extends LongconPortlet {

	private static final long serialVersionUID = -5027568434979672168L;

}
