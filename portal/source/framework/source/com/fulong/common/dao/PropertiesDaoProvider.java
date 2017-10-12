package com.fulong.common.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import com.fulong.common.ResourceUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 
* PropertiesDaoProvider
* @author    <a href="lixf@fulong.com.cn">李雄峰</a>
* @date      2010-9-12
 */
public class PropertiesDaoProvider implements JdbcDaoProvider, Serializable {

	private static final long serialVersionUID = 8510450674816458311L;

	private static final Log log = LogFactory.getLog(PropertiesDaoProvider.class);
	private Map<String, String> daos;

	public PropertiesDaoProvider() {
		daos = new Hashtable<String, String>();
	}

	public void addMappingFile(InputStream in) throws IOException {
		Properties mappings = new Properties();

		mappings = new Properties();
		mappings.load(in);
		in.close();

		Enumeration<Object> keys = mappings.keys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			String daoClass = mappings.getProperty(key);
			try {
				if (Class.forName(key).isAssignableFrom(Class.forName(daoClass))) {
					daos.put(key, daoClass);
					log.trace("Use " + daoClass + " for " + key + ".");
				} else {
					log.error("Dao class " + daoClass + " is not assignable from " + key + "!");
				}

			} catch (Throwable ex) {
				log.error("Unable to load dao class " + daoClass + " for " + key + "!");
			}
		}
	}

	/**
	 * 加载映射文件
	 * 
	 * @param mappingfile
	 */
	public void addMappingFile(String mappingfile) {
		if (mappingfile == null)
			return;
		InputStream in = ResourceUtils.getResourceAsStream(mappingfile);
		if (null == in) {
			throw new DaoException("Unable to final dao mapping file:" + mappingfile);
		}
		try {
			this.addMappingFile(in);
			in.close();
		} catch (java.io.IOException e) {
			throw new DaoException("Error to load " + mappingfile + ".properties file.");

		}

	}

	/**
	 * 根据数据源的配置，自动加载所需要的映射文件； 映射文件命名规则：
	 * mappingfile[.databaseName][.majarVersion][.minVersion].properties;
	 * 
	 * @param mappingfile
	 * @param ds
	 * @throws SQLException
	 */
	public void loadMappingFile(String mappingfile, DataSource ds) throws SQLException {
		Connection connection = null;
		DatabaseMetaData meta = null;
		try {
			connection = ds.getConnection();
			meta = connection.getMetaData();
		} finally {
			if (connection != null)
				connection.close();
		}
		// 尝试加载mappingfile[.databaseName][.majorVersion][.minorVersion].properties;
		StringBuffer path = new StringBuffer(mappingfile.replace('.', '/'));
		path.append(".").append(meta.getDatabaseProductName().toLowerCase());
		path.append(".").append(meta.getDatabaseMajorVersion());
		path.append(".").append(meta.getDatabaseMinorVersion());
		String file = path.toString() + ".properties";
		if (ResourceUtils.exists(file)) {
			this.addMappingFile(file);
			return;
		}
		// 尝试加载mappingfile[.databaseName][.majorVersion].properties;
		path = new StringBuffer(mappingfile.replace('.', '/'));
		path.append(".").append(meta.getDatabaseProductName().toLowerCase());
		path.append(".").append(meta.getDatabaseMajorVersion());
		file = path.toString() + ".properties";
		if (ResourceUtils.exists(file)) {
			this.addMappingFile(file);
			return;
		}
		// 尝试加载mappingfile[.databaseName].properties;
		path = new StringBuffer(mappingfile.replace('.', '/'));
		path.append(".").append(meta.getDatabaseProductName().toLowerCase());
		file = path.toString() + ".properties";
		if (ResourceUtils.exists(file)) {
			this.addMappingFile(file);
			return;
		}
		// 尝试加载mappingfile.properties;
		path = new StringBuffer(mappingfile.replace('.', '/'));
		file = path.toString() + ".properties";
		if (ResourceUtils.exists(file)) {
			this.addMappingFile(file);
			return;
		}
		//尝试失败，抛出异常
	
		throw new IllegalArgumentException("Unable to load mapping file with path " + mappingfile);
	
	}

	/**
	 * getDao
	 * 
	 * @param clazz
	 *            Class
	 * @return Dao
	 * @todo Implement this com.fulong.common.dao.DaoProvider method
	 */
	@SuppressWarnings("unchecked")
	public JdbcDao getDao(Class clazz) {
		String className = (String) this.daos.get(clazz.getName());
		if (className == null) {
			throw new DaoNotFoundException("Dao class " + clazz.getName() + " is not defined.");
		}
		try {
			return (JdbcDao) Class.forName(className).newInstance();
		} catch (ClassNotFoundException ex) {
			throw new DaoNotFoundException("Unable to find class for " + className + ".");
		} catch (InstantiationException ex) {
			throw new DaoNotFoundException("Unable to find class for " + className + ".");
		} catch (IllegalAccessException ex) {
			throw new DaoNotFoundException("Unable to access class " + className + ".");
		}

	}
}
