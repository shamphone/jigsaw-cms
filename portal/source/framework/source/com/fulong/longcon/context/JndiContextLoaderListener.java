/**
 * 
 */
package com.fulong.longcon.context;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;
/**
 * @author lixf
 *
 */
public class JndiContextLoaderListener extends ContextLoaderListener{
	
	
	protected ContextLoader createContextLoader() {
		return new JndiContextLoader();
	}
}
