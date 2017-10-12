package com.fulong.common.util;

import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;

/**
 * 
 * <p>
 * Title: WebMaster sv3
 * </p>
 * 
 * <p>
 * Description: 内容管理系统中小企业版
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2005
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author 李雄锋
 * @version 1.0
 */

public class ListItemResourcesFactory extends MessageResourcesFactory {
	
	private static final long serialVersionUID = 23248389189232136L;

	public MessageResources createResources(String config) {
		return new ListItemResources(this, config, this.returnNull);
	}

}
