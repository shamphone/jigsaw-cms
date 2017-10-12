package com.fulong.longcon.crawler.rule;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Vector;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;
import com.fulong.common.ResourceUtils;
import com.fulong.longcon.crawler.ConvertRule;

/**
 * <p>
 * Title: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */
public class GeneralConvertRule implements ConvertRule {
	private List<GeneralRule> rules;

	public GeneralConvertRule() {
		rules = new Vector<GeneralRule>();
	}

	/**
	 * 设置规则文件的路径，相对于class的系统的路径
	 * 
	 * @param rule
	 *            String
	 */
	public void setRuleClassPath(String rule) throws SAXException, IOException {
		this.parseRule(ResourceUtils.getResourceAsStream(rule));
	}

	/**
	 * 设置规则路径，相对于文件系统的路径
	 * 
	 * @param rule
	 *            String
	 */
	public void setRuleFilePath(String rule) throws IOException, SAXException {
		this.parseRule(new FileInputStream(rule));
	}

	private void parseRule(InputStream is) throws SAXException, IOException {
		Digester digester = null;
		digester = new Digester();
		digester.setNamespaceAware(true);
		digester.setValidating(false);
		digester.setUseContextClassLoader(true);
		digester.clear();
		digester.push(this);
		digester.addObjectCreate("rules/rule", GeneralRule.class);
		digester
				.addBeanPropertySetter("rules/rule/path-pattern", "pathPattern");
		digester.addCallMethod("rules/rule/query-key/included",
				"addQueryKeyIncluded", 1);
		digester.addCallParam("rules/rule/query-key/included", 0);
		digester.addCallMethod("rules/rule/query-key/excluded",
				"addQueryKeyExcluded", 1);
		digester.addCallParam("rules/rule/query-key/excluded", 0);
		digester
				.addBeanPropertySetter("rules/rule/file-pattern", "filePattern");
		digester.addSetNext("rules/rule", "addRule");
		digester.parse(is);

	}

	public void addRule(GeneralRule data) {
		this.rules.add(data);
	}

	/**
	 * 用于做测试的方法
	 * 
	 * @param i
	 *            int
	 * @return GeneralRule
	 */
	public GeneralRule getRule(int i) {
		return (GeneralRule) this.rules.get(i);
	}

	/**
	 * 
	 * @param url
	 *            String 完整的页面地址
	 * @return String
	 *         相对页面路径和地址。这个返回值一方面作为静态化后的文件的相对（主机+Context的）页面路径，一方面作为静态文件保存的相对
	 *         （静态文件夹的）路径
	 */
	public String convert(URL url) {
		for (int i = 0; i < this.rules.size(); i++) {
			GeneralRule rule = (GeneralRule) rules.get(i);
			if (rule.applyTo(url))
				return rule.convert(url);
		}
		return null;
	}

	/**
	 * 
	 * @param url
	 *            String
	 * @return boolean
	 */
	public boolean needToChange(URL url) {
		for (int i = 0; i < this.rules.size(); i++) {
			GeneralRule rule = (GeneralRule) rules.get(i);
			if (rule.applyTo(url))
				return true;
		}
		return false;
	}
}
