package com.fulong.common;

import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * 
 * <p>
 * Title: WebMaster Core Library
 * </p>
 * 
 * <p>
 * Description: Longcon WebMaster SV3
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Tech. LTD
 * </p>
 * 
 * @author Lixf
 * @version 1.0
 */
public class ResourceUtils {
	/**
	 * Return the thread context class loader if available. Otherwise return
	 * null.
	 * 
	 * The thread context class loader is available for JDK 1.2 or later, if
	 * certain security conditions are met.
	 * 
	 * @exception LogConfigurationException
	 *                if a suitable class loader cannot be identified.
	 */

	public static ClassLoader getContextClassLoader() {
		return ResourceUtils.class.getClassLoader();
	}

	@SuppressWarnings("unchecked")
	public static InputStream getResourceAsStream(final String name) {
		return (InputStream) AccessController
				.doPrivileged(new PrivilegedAction() {
					public Object run() {
						ClassLoader threadCL = getContextClassLoader();

						if (threadCL != null) {
							return threadCL.getResourceAsStream(name);
						} else {
							return ClassLoader.getSystemResourceAsStream(name);
						}
					}
				});
	}

	@SuppressWarnings("unchecked")
	public static String getResource(final String path) {
		return (String) AccessController.doPrivileged(new PrivilegedAction() {
			public Object run() {
				ClassLoader threadCL = getContextClassLoader();
				if (threadCL != null) {
					return threadCL.getResource(path).getPath();
				} else {
					return ClassLoader.getSystemResource(path).getPath();
				}
			}
		});
	}

	@SuppressWarnings("unchecked")
	public static boolean exists(final String path) {
		return (Boolean) AccessController.doPrivileged(new PrivilegedAction() {
			public Object run() {
				ClassLoader threadCL = getContextClassLoader();
				if (threadCL != null) {
					return new Boolean(threadCL.getResource(path)!=null);
				} else {
					return new Boolean(ClassLoader.getSystemResource(path)!=null);
				}
			}
		});
	}
}
