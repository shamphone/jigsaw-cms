package com.fulong.portal.model;

import org.apache.commons.digester.Digester;
import java.io.Reader;
import com.fulong.portal.model.impl.PortletEntityImpl;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.io.StringReader;

/**
 * <p>
 * Title: 龙驭门户引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author 李雄锋
 * @version 1.0
 */
public class PortletEntityParser {
	
	public PortletEntity parser(String portletXML) throws SAXException, IOException {
		return this.parser(new StringReader(this.normalize(portletXML)));
	}

	/**
	 * 
	 * @param reader
	 *            Reader
	 * @return PortletEntity
	 * @throws SAXException
	 * @throws IOException
	 */
	public PortletEntity parser(Reader reader) throws SAXException, IOException {
		PortletEntityImpl entity = new PortletEntityImpl();
		Digester digester = new Digester();
		digester.push(entity);
		digester.setNamespaceAware(false);
		digester.addSetProperties("fulong:portlet", "id", "id");
		digester.addSetProperties("fulong:portlet", "type", "type");
		digester.addObjectCreate("fulong:portlet/fulong:preference",
				Preference.class);
		digester.addBeanPropertySetter(
				"fulong:portlet/fulong:preference/fulong:name", "name");
		digester.addCallMethod("fulong:portlet/fulong:preference/fulong:value",
				"addValue", 1);
		digester.addCallParam("fulong:portlet/fulong:preference/fulong:value",
				0);
		digester
				.addSetNext("fulong:portlet/fulong:preference", "addPreference");
		digester.parse(reader);
		return entity;
	}

	/**
	 * 将xml字符串中不符合xml规则的地方调整为符合规则的。目前主要是fulong：value标签可能会包含这样的内容。
	 * 
	 * @param xml
	 *            String
	 * @return String
	 */
	private String normalize(String xml) {
		String result = xml;
		result = result.replaceAll("<fulong:value>", "<fulong:value><![CDATA[");
		result = result.replaceAll("</fulong:value>", "]]></fulong:value>");
		return result;
	}

	public PortletEntity extrace(String path, String portletId)
			throws SAXException, IOException {
		return null;
	}

}
