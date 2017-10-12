package com.fulong.longcon.site;

import java.util.*;
import com.fulong.longcon.site.ext.*;
import com.fulong.longcon.site.data.*;

public class TestSiteCategory
    extends SiteTestCase {
    private SiteCategory siteCategoryImpl = null;

    protected void setUp() throws Exception {
        super.setUp();
        siteCategoryImpl = this.factory.createCategory("category");
    }

    protected void tearDown() throws Exception {
        this.factory.delete(siteCategoryImpl);
        siteCategoryImpl=null;
        super.tearDown();
    }

    public void testGetCreateDate() {
        Date expectedReturn = new Date();
        Date actualReturn = siteCategoryImpl.getCreatedDate();
        assertEquals("return value", expectedReturn.getDate(), actualReturn.getDate());
    }

    public void testGetDescription() {
        String expectedReturn = "todo fill in the test code";
        siteCategoryImpl.setDescription(expectedReturn);
        String actualReturn = siteCategoryImpl.getDescription();
        assertEquals("return value", expectedReturn, actualReturn);
    }



    public void testGetID() {
        String expectedReturn = null;
        String actualReturn = siteCategoryImpl.getID();
        SiteCategory sc = this.factory.getCategory(actualReturn);
        assertEquals("return value", sc, siteCategoryImpl);
    }



}
