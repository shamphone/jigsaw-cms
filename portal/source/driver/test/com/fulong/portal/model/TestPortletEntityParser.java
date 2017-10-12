package com.fulong.portal.model;

import java.io.IOException;


import org.xml.sax.SAXException;
import junit.framework.TestCase;
/**
 *
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
public class TestPortletEntityParser extends TestCase {
    private PortletEntityParser portletEntityParser = null;

    protected void setUp() throws Exception {
        super.setUp();
        portletEntityParser = new PortletEntityParser();
    }

    protected void tearDown() throws Exception {
        portletEntityParser = null;
        super.tearDown();
    }

    public void testParser() throws SAXException, IOException {
        String portletXML = "<fulong:portlet id=\"pt0234\" type=\"org-repeater\"><fulong:preference><fulong:name>table</fulong:name><fulong:value>1</fulong:value></fulong:preference><fulong:preference><fulong:name>footer-style</fulong:name><fulong:value></fulong:value></fulong:preference><fulong:preference><fulong:name>show-pager</fulong:name><fulong:value>false</fulong:value></fulong:preference><fulong:preference><fulong:name>column-fields</fulong:name><fulong:value>org-picture</fulong:value><fulong:value>commonname</fulong:value><fulong:value>#</fulong:value><fulong:value>#</fulong:value><fulong:value>#</fulong:value><fulong:value>#</fulong:value><fulong:value>#</fulong:value><fulong:value>#</fulong:value><fulong:value>#</fulong:value><fulong:value>#</fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value></fulong:preference><fulong:preference><fulong:name>column-styles</fulong:name><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value></fulong:preference><fulong:preference><fulong:name>type</fulong:name><fulong:value>image-grid</fulong:value></fulong:preference><fulong:preference><fulong:name>column</fulong:name><fulong:value>2</fulong:value></fulong:preference><fulong:preference><fulong:name>length</fulong:name><fulong:value>0</fulong:value><fulong:value>15</fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value></fulong:preference><fulong:preference><fulong:name>column-headers</fulong:name><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value></fulong:preference><fulong:preference><fulong:name>column-types</fulong:name><fulong:value>link</fulong:value><fulong:value>link</fulong:value><fulong:value>link</fulong:value><fulong:value>link</fulong:value><fulong:value>link</fulong:value><fulong:value>link</fulong:value><fulong:value>link</fulong:value><fulong:value>link</fulong:value><fulong:value>link</fulong:value><fulong:value>link</fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value></fulong:preference><fulong:preference><fulong:name>filter-auto</fulong:name><fulong:value></fulong:value></fulong:preference><fulong:preference><fulong:name>group-id</fulong:name><fulong:value>2399392720254</fulong:value></fulong:preference><fulong:preference><fulong:name>row</fulong:name><fulong:value>7</fulong:value></fulong:preference><fulong:preference><fulong:name>column-footers</fulong:name><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value></fulong:preference><fulong:preference><fulong:name>order-field</fulong:name><fulong:value>create_date</fulong:value></fulong:preference><fulong:preference><fulong:name>show-footer</fulong:name><fulong:value>false</fulong:value></fulong:preference><fulong:preference><fulong:name>header-style</fulong:name><fulong:value></fulong:value></fulong:preference><fulong:preference><fulong:name>show-header</fulong:name><fulong:value>false</fulong:value></fulong:preference><fulong:preference><fulong:name>pager-style</fulong:name><fulong:value></fulong:value></fulong:preference><fulong:preference><fulong:name>column-formats</fulong:name><fulong:value><img src=# border=0/></fulong:value><fulong:value>#</fulong:value><fulong:value>#</fulong:value><fulong:value>#</fulong:value><fulong:value>#</fulong:value><fulong:value>#</fulong:value><fulong:value>#</fulong:value><fulong:value>#</fulong:value><fulong:value>#</fulong:value><fulong:value>#</fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value><fulong:value></fulong:value></fulong:preference><fulong:preference><fulong:name>table-style</fulong:name><fulong:value>qylb</fulong:value></fulong:preference><fulong:preference><fulong:name>order-style</fulong:name><fulong:value>DESC</fulong:value></fulong:preference></fulong:portlet>";
        PortletEntity actualReturn = portletEntityParser.parser(portletXML);
        assertEquals("return value", "pt0234", actualReturn.getId());
        /**@todo fill in the test code*/
    }

}
