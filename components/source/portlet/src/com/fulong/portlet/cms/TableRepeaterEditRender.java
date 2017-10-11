package com.fulong.portlet.cms;

import java.io.File;


/**
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author lixiang
 * @version 2.0
 */
public abstract class TableRepeaterEditRender extends RepeaterEditRender {
    private static final String TEMPLATE_FILE = "/portlet/cms/tableXrepeater/blank.jsp";

    protected File getBlankTemplateFile() {
        return new File(this.portletContext
                        .getRealPath(TEMPLATE_FILE));
    }
}
