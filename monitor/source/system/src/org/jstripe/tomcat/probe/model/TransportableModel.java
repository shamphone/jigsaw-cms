/*
 * Licensed under the GPL License. You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://probe.jstripe.com/d/license.shtml
 *
 *  THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 *  IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 *  WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */

package org.jstripe.tomcat.probe.model;

import java.util.Map;
import java.util.HashMap;

/**
 * A wrapper class to assist marshalling of ModelAndView.getModel() Map to XML
 * representation.
 */
public class TransportableModel {
	
	@SuppressWarnings("unchecked")
	private Map items = new HashMap();

	@SuppressWarnings("unchecked")
	public Map getItems() {
		return items;
	}

	@SuppressWarnings("unchecked")
	public void setItems(Map items) {
		this.items = items;
	}

	@SuppressWarnings("unchecked")
	public void putAll(Map map) {
		items.putAll(map);
	}
}
