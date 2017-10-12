package com.fulong.longcon.site;

import com.fulong.longcon.site.*;
import com.fulong.longcon.site.ext.SiteFactoryExt;
import com.fulong.longcon.site.impl.SiteTemplateCollectionImpl;
import java.util.Date;

public class TestSiteTemplateCollection
    extends SiteTestCase {
    private SiteTemplateCollection siteTemplateCollectionImpl = null;

    protected void setUp() throws Exception {
        super.setUp();
        siteTemplateCollectionImpl = new SiteTemplateCollectionImpl( (
            SiteFactoryExt)this.factory);
    }

    protected void tearDown() throws Exception {
        siteTemplateCollectionImpl = null;
        super.tearDown();
    }

   

}
