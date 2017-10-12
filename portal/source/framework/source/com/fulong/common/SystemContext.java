package com.fulong.common;

import java.util.Hashtable;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;

/**
 *
 * <p>Title: Longcon Passport System</p>
 *
 * <p>Description: Longcon Passport</p>
 *
 * <p>Copyright: Copyright (c) Beijing Zhongke Fulong Computer Technology LTD.
 * 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Technology LTD.</p>
 *
 * @author Lixf
 * @version 2.0.0
 */
public class SystemContext {
    static SystemContext instance = null;
    @SuppressWarnings("unchecked")
	static Hashtable properties = null;
    static Log log = LogFactory.getLog(SystemContext.class);

    public static SystemContext getInstance() {
        if (instance == null) {
            instance = new SystemContext();
        }
        return instance;
    }

    public static void main(String[] args) {
        SystemContext context = new SystemContext();
        try {
            context.init("d:\\work\\longcon\\trunk\\web\\WEB-INF\\config.xml");
        }
        catch (Exception ex) {
            log.error(
                "Errort to read d:\\work\\longcon\\trunk\\web\\WEB-INF\\config.xml，");
        }
    }

    @SuppressWarnings("unchecked")
	public synchronized void init(String path) {
        if (properties != null) {
            return;
        }
        properties = new Hashtable();
        /**
         * @todo 初始化并装载config.xml文件
         */
        Digester digester = null;
        digester = new Digester();
        digester.setNamespaceAware(true);
        digester.setValidating(false);
        digester.setUseContextClassLoader(true);
        configDigester(digester);
        try {
            digester.parse(path);
            log.info("success load config from file:" + path + ".");
        }
        catch (Exception ex) {
            throw new InitFailedException("Error in parsing system config file",
                                          ex);
        }
    }

    private void configDigester(Digester digester) {
        digester.addRule("longcon-config/param", new ParamRule());
        digester.addRule("longcon-config/param/set-property", new PropertyRule());

    }

    public Object getResource(String name) {
        if (properties == null) {
            throw new InitFailedException("System context is not init.");
        }
        return properties.get(name);
    }
}


final class ParamRule
    extends Rule {
    static Log log = LogFactory.getLog(ParamRule.class);
    private String value;
    private String paramName;
    private Object obj;
    @SuppressWarnings("unchecked")
	public void begin(String namespace, String name, Attributes attributes) throws
        Exception {
        String className = attributes.getValue("className");
        paramName = attributes.getValue("name");
        value = attributes.getValue("value");
        if (className != null) {
            Class clazz = digester.getClassLoader().loadClass(className);
            obj = clazz.newInstance();
            digester.push(obj);
        }
        else {
            obj = null;
        }
    }

    public void body(String namespace, String name, String text) throws
        Exception {
        if (value == null) {
            value = text.trim();
        }
    }

    @SuppressWarnings("unchecked")
	public void end(String namespace, String name) throws
        Exception {
        if (obj == null) {
            obj = value;
        }
        log.trace("Load " + paramName + "=" + obj + ".");
        SystemContext.properties.put(paramName, obj);
    }

}


final class PropertyRule
    extends Rule {
    static Log log = LogFactory.getLog(PropertyRule.class);
    private String prop;
    private String value;
    private Object obj;
    public void begin(String namespace, String name, Attributes attributes) throws
        Exception {
        prop = attributes.getValue("property");
        value = attributes.getValue("value");
        obj = digester.peek();
    }

    public void body(String namespace, String name, String text) throws
        Exception {
        if (value == null) {
            value = text.trim();
        }
    }

    public void end(String namespace, String name) throws
        Exception {
        log.trace("setProperty(" + prop + "," + value + ")");
        BeanUtils.setProperty(obj, prop, value);
    }
}
