package com.fulong.taglib.common;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

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
public class ForTEI extends TagExtraInfo {

    /**
     * Return information about the scripting variables to be created.
     */
    public VariableInfo[] getVariableInfo(TagData data) {

        // prime this array with the maximum potential variables.
        // will be arraycopy'd out to the final array based on results.
        VariableInfo[] variables = new VariableInfo[1];

        /* indexId : number value of the current iteration */
        String indexId = data.getAttributeString("id");
        variables[0] = new VariableInfo(indexId, "java.lang.Integer",
                                        true, VariableInfo.NESTED);
        return variables;
    }
}
