package com.fulong.longcon.security.ext;

import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.security.PassportProvider;

/**
 * <p>Title: 龙驭会员管理系统2.0</p>
 *
 * <p>Description: 龙驭会员管理系统2.0</p>
 *
 * <p>Copyright: Copyright (c) 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author Lixf
 * @version 2.0
 */
public interface SecurityManagerExt
    extends PassportProvider,PassportContext {

    public Repository getRepository();
}
