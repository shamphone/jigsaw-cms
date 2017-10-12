package com.fulong.portal.core;

import javax.portlet.PortletMode;
import com.fulong.portal.utils.DesEncrypter;

/**
 *
 * <p>Title: WebMaster Core Library</p>
 *
 * <p>Description: Longcon WebMaster SV3</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Tech. LTD</p>
 *
 * @author Lixf
 * @version 1.0
 */

public interface Constants {
    public static DesEncrypter PORTAL_STATE_ENCRYPTER = new DesEncrypter("portal");
    public final static String LONGCON_PAGE = "com.fulong.portlet.page";
    public final static String LONGCON_BUFFERED_CONTENT="com.fulong.longcon.buffer.content";
    public final static String PORTAL_URL_PARSER="com.fulong.portal.core.PortalURL";
    public final static String PORTLET_REQUEST = "javax.portlet.request";
    public final static String PORTLET_RESPONSE = "javax.portlet.response";
    public final static String PORTLET_CONFIG = "javax.portlet.config";
    public final static String PORTLET_CONTAINER="com.fulong.longcon.portlet.container";
    public final static String PORTLET_XML="javax.portlet.xml";
    public final static String PORTLET_ENTITY="javax.portlet.entity";

    public static final String INCLUDE_SERVLET_PATH_ATTRIBUTE = "javax.servlet.include.servlet_path";
    public static final String INCLUDE_PORTLET_PATH_ATTRIBUTE = "javax.portlet.include.servlet_path";

    public final static String PAGE_MODE_VIEW = "view";
    public final static String PAGE_MODE_EDIT = "edit";
    public final static String PAGE_MODE_FINAL = "final";
    public final static String PAGE_MODE_PREVIEW = "preview";
    public final static String PORTLET_PROPERTY_NAME = "javax.portlet.name";

    public final static String REQUEST_PAGE_MODE = "javax.portlet.page.mode";
    public final static String REQUEST_PORTLET_MODE = "portlet.mode";
    public final static String REQUEST_WINDOW_STATE = "portlet.state";
    public final static String REQUESTS_ACTION_FIRER = "portlet.action";
    public final static String REQUESTS_PREFERENCES = "portlet.pref";
    public final static String REQUESTS_ACTION_FIRER_TYPE =
            "portlet.action.type";
    public final static String REQUEST_WINDOW_OWNER="portlet.window.owner";
    public final static String REQUEST_PORTLET_TYPE="portlet.type";
    public final static String REQUEST_PORTAL_STATE = "jportal";


    public final static PortletMode PORTLET_MODE_VIEW = new PortletMode("view");
    public final static PortletMode PORTLET_MODE_EDIT = new PortletMode("edit");
    public final static PortletMode PORTLET_MODE_HELP = new PortletMode("help");
    public final static PortletMode PORTLET_MODE_FINAL = new PortletMode("final");
    public final static PortletMode PORTLET_MODE_PREVIEW = new PortletMode("preview");

    public final static String LONGCON_PORTAL_ID = "longcon.page.portlet.id";
    public final static String LONGCON_PORTAL_MODE =
            "longcon.page.portlet.mode";
    public final static String LONGCON_PORTAL_STAT =
            "longcon.page.portlet.stat";
    public final static String LONGCON_PORTAL_ACTIVE =
            "longcon.page.portlet.active";

    public final static String ATTRIBUTE_PORTLET_ID="javax.portlet.id";
    //the following constants is definited by JSR
    public final static String ATTRIBUTE_REQUEST_URI="javax.servlet.request_uri";

    public final static String ATTRIBUTE_RESPONSE="javax.portlet.servlet.response";
    /*
    public final static String ATTRIBUTE_CONTEXT_PATH="javax.servlet.include.context_path";
    public final static String ATTRIBUTE_SERVLET_PATH="javax.servlet.include.servlet_path";
    public final static String ATTRIBUTE_PATH_INFO="javax.servlet.include.path_info";
    public final static String ATTRIBUTE_QUERY_STRING="javax.servlet.include.query_string";
    */


}
