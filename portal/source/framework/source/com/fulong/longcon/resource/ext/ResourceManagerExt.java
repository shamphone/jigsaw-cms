package com.fulong.longcon.resource.ext;

import com.fulong.common.dao.DaoFactory;
import com.fulong.longcon.resource.ResourceManager;
import com.fulong.longcon.security.PassportProvider;

/**
 * <p>
 * Title: 资源管理系统
 * </p>
 * 
 * <p>
 * Description: 资源管理系统
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author lishaobo
 * @version 2.0
 */
public interface ResourceManagerExt extends ResourceManager {
	/**
	 * 
	 * @return DaoFactory
	 */
	public DaoFactory newDaoFactory();

	/**
	 * @todo 返回值改为SecurityManager
	 * @return InnerPassportProvider
	 */
	public PassportProvider getPassport();

}
