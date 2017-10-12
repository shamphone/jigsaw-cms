package com.fulong.portal.model;

import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

/**
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author 李雄锋
 * @version 1.0
 */
public class PreferenceParser {
	private String id;
	private String type;
	private PreferenceSet set;
	public PreferenceParser(){
		this.set = new PreferenceSet();	
	}
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public PreferenceSet parse(String xml) throws SAXException, IOException {
        if (xml == null)
            return null;
        if (xml.length() == 0)
            return new PreferenceSet();

        Digester digester = new Digester();
        digester.setNamespaceAware(false);
        digester.push(this);
        digester.addSetProperties("fulong:portlet", "id","id");
        digester.addSetProperties("fulong:portlet", "name","name");
        digester.addObjectCreate("fulong:portlet/fulong:preference", Preference.class);
        digester.addBeanPropertySetter(
                "fulong:portlet/fulong:preference/fulong:name", "name");
        digester.addCallMethod("fulong:portlet/fulong:preference/fulong:value",
                               "addValue", 1);
        digester.addCallParam("fulong:portlet/fulong:preference/fulong:value",
                              0);
        digester.addSetNext("fulong:portlet/fulong:preference", "put");
        digester.parse(new StringReader(this.normalize(xml)));
        return this.set;
    }
	
	public void put(Preference pref){
		this.set.put(pref);
	}

    private String normalize(String xml) {
        String result = xml;
        result = result.replaceAll("<fulong:value>", "<fulong:value><![CDATA[");
        result = result.replaceAll("</fulong:value>", "]]></fulong:value>");
        return result;
    }

}
