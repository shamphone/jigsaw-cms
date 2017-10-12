package com.fulong.portlet;

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
public interface Constants {
	public static final String HyperLinkColumn = "link";
	public static final String ItemColumn = "item";
	public static final String TemplateColumn = "template";

	public static final String KEY_PREFERENCES = "com.fulong.portlet.preferences";
	public static final String KEY_ACTION_URL = "com.fulong.portlet.action.url";
	public static final String KEY_PORTLET_CONFIG = "com.fulong.portlet.config";
	public static final String KEY_ACTION_NAME = "portlet_action_name";
	public static final String TOKEN_KEY = "com.fulong.portlet.token";

	public static final String KEY_CSS_FILE = "com.fulong.portlet.css";

	// don't change this constant.
	public static final String KEY_FROM_URI = "javax.portlet.url";

	public static final String KEY_SITE = "com.fulong.longcon.site";
	public static final String KEY_FOLDER = "com.fulong.longcon.folder";
	public final static String PAGE_MODE = "javax.portlet.page.mode";
	public final static String PAGE_MODE_FINAL = "final";
	public final static String ATTRIBUTE_PORTLET_ID = "javax.portlet.id";

	// public final static String STR_CONTENTFOLDER = "contentFolderId";
	// public final static String STR_CONTENT = "contentId";

	public final static String PORTLET_REQUEST = "javax.portlet.request";
	public final static String PORTLET_RESPONSE = "javax.portlet.response";
	public final static String PORTLET_CONFIG = "javax.portlet.config";
	public final static String REQUESTS_ACTION_FIRER = "portlet_action";
	public final static String REQUESTS_ACTION_FIRER_TYPE = "portlet_action_type";
	public final static String PAGE_MODE_VIEW = "view";
	public final static String PAGE_MODE_EDIT = "edit";

	public final static String PAGE_MODE_PREVIEW = "preview";
	public final static String PORTLET_PROPERTY_NAME = "javax.portlet.name";

	public final static String REQUEST_PORTLET_MODE = "portlet_mode";
	public final static String REQUEST_WINDOW_STATE = "portlet_state";

	public final static String LONGCON_PORTAL_ID = "longcon.page.portlet.id";
	public final static String LONGCON_PORTAL_MODE = "longcon.page.portlet.mode";
	public final static String LONGCON_PORTAL_STAT = "longcon.page.portlet.stat";
	public final static String LONGCON_PORTAL_ACTIVE = "longcon.page.portlet.active";

	// the following constants is definited by JSR
	public final static String ATTRIBUTE_REQUEST_URI = "javax.servlet.request_uri";
	public static final String REQUEST_CONTENT = "com.fulong.longcon.Content";
	public static final String REQUEST_PARAMETER = "com.fulong.longcon.parameter";
	public static final String REQUEST_NODEDEFINITION = "com.fulong.longcon.nodeDefinition";
	public static final String CONTENT_TITLE = "com.fulong.longcon.Content.title";
}
