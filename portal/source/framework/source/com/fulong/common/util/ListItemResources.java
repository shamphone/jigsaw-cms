package com.fulong.common.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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

public class ListItemResources extends MessageResources {

	private static final long serialVersionUID = 8173956100656837893L;

	public ListItemResources(MessageResourcesFactory factory, String config) {
		super(factory, config);
		log.info("Initializing, config='" + config + "'");
	}

	public ListItemResources(MessageResourcesFactory factory, String config,
			boolean returnNull) {
		super(factory, config, returnNull);
		log.info("Initializing, config='" + config + "', returnNull="
				+ returnNull);

	}

	/**
	 * The set of locale keys for which we have already loaded messages, keyed
	 * by the value calculated in <code>localeKey()</code>.
	 */
	protected HashMap<String, String> locales = new HashMap<String, String>();

	/**
	 * The <code>Log</code> instance for this class.
	 */
	protected static final Log log = LogFactory.getLog(ListItemResources.class);

	/**
	 * The cache of messages we have accumulated over time, keyed by the value
	 * calculated in <code>messageKey()</code>.
	 */
	protected HashMap<String, String> messages = new HashMap<String, String>();

	// --------------------------------------------------------- Public Methods

	protected String messageKey(String locale, String list, String key) {

		return messageKey(locale, list + "." + key);

	}

	/**
	 * Returns a text message for the specified key, for the default Locale. A
	 * null string result will be returned by this method if no relevant message
	 * resource is found for this key or Locale, if the <code>returnNull</code>
	 * property is set. Otherwise, an appropriate error message will be
	 * returned.
	 * <p>
	 * This method must be implemented by a concrete subclass.
	 * 
	 * @param locale
	 *            The requested message Locale, or <code>null</code> for the
	 *            system default Locale
	 * @param key
	 *            The message key to look up
	 * @return text message for the specified key and locale
	 */
	public String getMessage(Locale locale, String list, String key) {
		String message = this.findMessage(locale, list + "." + key);
		if (message != null) {
			return message;
		}
		// Return an appropriate error indication
		if (returnNull) {
			return (null);
		} else {
			return key;
		}
	}

	public String getMessage(Locale locale, String key, Object args[]) {
		return this.getMessage(locale, key);
	}

	protected String findMessage(Locale locale, String key) {

		if (log.isDebugEnabled()) {
			log.debug("getMessage(" + locale + "," + key + ")");
		}

		// Initialize variables we will require
		String localeKey = localeKey(locale);
		String originalKey = messageKey(localeKey, key);
		String messageKey = null;
		String message = null;
		int underscore = 0;
		boolean addIt = false; // Add if not found under the original key

		// Loop from specific to general Locales looking for this message
		while (true) {

			// Load this Locale's messages if we have not done so yet
			loadLocale(localeKey);

			// Check if we have this key for the current locale key
			messageKey = messageKey(localeKey, key);
			synchronized (messages) {
				message = (String) messages.get(messageKey);
				if (message != null) {
					if (addIt) {
						messages.put(originalKey, message);
					}
					return (message);
				}
			}

			// Strip trailing modifiers to try a more general locale key
			addIt = true;
			underscore = localeKey.lastIndexOf("_");
			if (underscore < 0) {
				break;
			}
			localeKey = localeKey.substring(0, underscore);

		}

		// Try the default locale if the current locale is different
		if (!defaultLocale.equals(locale)) {
			localeKey = localeKey(defaultLocale);
			messageKey = messageKey(localeKey, key);
			loadLocale(localeKey);
			synchronized (messages) {
				message = (String) messages.get(messageKey);
				if (message != null) {
					messages.put(originalKey, message);
					return (message);
				}
			}
		}

		// As a last resort, try the default Locale
		localeKey = "";
		messageKey = messageKey(localeKey, key);
		loadLocale(localeKey);
		synchronized (messages) {
			message = (String) messages.get(messageKey);
			if (message != null) {
				messages.put(originalKey, message);
				return (message);
			}
		}
		return null;

	}

	public String getMessage(Locale locale, String key) {
		String message = this.findMessage(locale, key);
		if (message != null) {
			return message;
		}
		// Return an appropriate error indication
		if (returnNull) {
			return (null);
		} else {
			// return ("???" + messageKey(locale, key) + "???");
			int startIndex = key.indexOf(".");
			return key.substring(startIndex + 1);
		}

	}

	// ------------------------------------------------------ Protected Methods

	/**
	 * Load the messages associated with the specified Locale key. For this
	 * implementation, the <code>config</code> property should contain a fully
	 * qualified package and resource name, separated by periods, of a series of
	 * property resources to be loaded from the class loader that created this
	 * PropertyMessageResources instance. This is exactly the same name format
	 * you would use when utilizing the
	 * <code>java.util.PropertyResourceBundle</code> class.
	 * 
	 * @param localeKey
	 *            Locale key for the messages to be retrieved
	 */
	protected synchronized void loadLocale(String localeKey) {

		log.trace("loadLocale(" + localeKey + ")");

		// Have we already attempted to load messages for this locale?
		if (locales.get(localeKey) != null) {
			return;
		}
		locales.put(localeKey, localeKey);

		// Set up to load the property resource for this locale key, if we can
		String name = config.replace('.', '/');
		if (localeKey.length() > 0) {
			name += "_" + localeKey;
		}
		name += ".properties";
		InputStream is = null;
		// Load the specified property resource
		log.trace("  Loading resource '" + name + "'");

		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		if (classLoader == null) {
			classLoader = this.getClass().getClassLoader();
		}

		is = classLoader.getResourceAsStream(name);
		if (is != null) {
			synchronized (messages) {
				try {
					loadMessage(localeKey, is);
				} catch (Exception ex) {
					log.error(" Error in load resource '" + name + "'", ex);

				}

			}
		}
	}

	protected void loadMessage(String locale, InputStream is) throws Exception {

		Document document = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().parse(is);
		Element root = document.getDocumentElement();
		NodeList lists = root.getElementsByTagName("List");
		for (int i = 0; i < lists.getLength(); i++) {
			Element list = (Element) lists.item(i);
			String listName = list.getAttribute("name").trim();
			StringBuffer listValue = new StringBuffer();
			NodeList items = list.getElementsByTagName("Item");
			for (int nItem = 0; nItem < items.getLength(); nItem++) {
				Element item = (Element) items.item(nItem);
				String itemLabel = item.getFirstChild().getNodeValue();
				String itemValue = item.getAttribute("key");
				messages
						.put(messageKey(locale, listName, itemValue), itemLabel);
				listValue.append(itemValue + " ");
			}
			messages.put(messageKey(locale, listName), listValue.toString());
		}

	}
}
