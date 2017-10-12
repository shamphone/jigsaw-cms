/**
 * 
 */
package com.fulong.taglib.common;

import java.net.URLEncoder;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.bean.WriteTag;
import org.apache.struts.util.ResponseUtils;
import org.jfree.util.Log;

/**
 *   
 * 
 * Coolink协同工作框架模型 
 *
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 *
 * Company: 北京中科辅龙计算机技术股份有限公司
 *
 * @author lixf
 *
 * @version 2.0
 *
 */
public class EncodeURIComponentTag   extends WriteTag {
    /**
	 * 
	 */
	private static final long serialVersionUID = -698019803722622005L;
	private String encode= "UTF-8";
	private TagUtils tagUtils= TagUtils.getInstance();
    public int doStartTag() throws JspException {

        // Look up the requested bean (if necessary)
        if (ignore) {
            if (tagUtils.lookup(pageContext, name, scope) == null)
                return (SKIP_BODY); // Nothing to output
        }
        if (encode == null)
            encode = "UTF-8";

        // Look up the requested property value
        Object value =
        	tagUtils.lookup(pageContext, name, property, scope);
        if (value == null)
            return (SKIP_BODY); // Nothing to output

        // Convert value to the String with some formatting
        String output = formatValue(value);

        try {
            output = URLEncoder.encode(output, encode)
            .replaceAll("\\+", "%20")
            .replaceAll("\\%21", "!")
            .replaceAll("\\%27", "'")
            .replaceAll("\\%28", "(")
            .replaceAll("\\%29", ")")
            .replaceAll("\\%7E", "~");            
        }
        catch (Exception ex) {
            Log.error("Error in encode value :"+ value, ex);
        }

        // Print this property value to our output writer, suitably filtered
        if (filter)
        	tagUtils.write(pageContext, ResponseUtils.filter(output));
        else
        	tagUtils.write(pageContext, output);

        // Continue processing this page
        return (SKIP_BODY);

    }

}
