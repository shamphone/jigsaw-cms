package com.fulong.longcon.site;

import java.util.Locale;



public class TestSiteTemplate
    extends SiteTestCase {
    private SiteTemplate siteTemplateImpl = null;
    private SiteCategory category = null;
    protected void setUp() throws Exception {
        super.setUp();
        category = this.factory.createCategory(this.getTestString(
            "create.site.category"));
        siteTemplateImpl = this.factory.createTemplate(
            "silver group site template", category);
    }

    protected void tearDown() throws Exception {
        this.factory.delete(siteTemplateImpl);
        this.factory.delete(category);
        super.tearDown();
    }

    public void testDelete() {
        Channel channel = siteTemplateImpl.getRootChannel();
        String name = "news";
        Channel newChannel = channel.addChannel(name);
        Channel actualReturn = siteTemplateImpl.getChannel(name);
        assertEquals("return value", newChannel, actualReturn);

        siteTemplateImpl.delete(newChannel);
        actualReturn = siteTemplateImpl.getChannel(name);
        assertEquals("return value", null, actualReturn);

    }

    public void testGetCategory() {
        SiteCategory expectedReturn = category;
        SiteCategory actualReturn = siteTemplateImpl.getCategory();
        assertEquals("return value", expectedReturn, actualReturn);

        SiteCategory category = this.factory.createCategory("new category");
        siteTemplateImpl.setCategory(category);
        actualReturn = siteTemplateImpl.getCategory();
        assertEquals("return value", category, actualReturn);

    }

    public void testGetDisplayName() {
        String expectedReturn = "testGetDisplayName";
        siteTemplateImpl.setDisplayName(expectedReturn);
        String actualReturn = siteTemplateImpl.getDisplayName();
        assertEquals("return value", expectedReturn, actualReturn);

    }

    public void testGetID() {
        String ID = siteTemplateImpl.getID();
        SiteTemplate expectedReturn = this.factory.getTemplate(ID);
        assertEquals("return value", expectedReturn, siteTemplateImpl);
    }

    public void testSetDefuatlChannel() {
        Channel channel = siteTemplateImpl.getRootChannel();
        String channelName = "TEST_FOR_CHANNEL";
        Channel channel2 = channel.addChannel(channelName);
        siteTemplateImpl.setDefaultChannel(Locale.CHINA, channel);

        String expectedReturn = siteTemplateImpl.getDefaultChannel(Locale.CHINA).
            getID();
        assertEquals("return value", expectedReturn, channel.getID());

        String language = siteTemplateImpl.getChannelLocale(channel).
            getLanguage();

        assertEquals("return value", Locale.CHINA.getLanguage(), language);

        siteTemplateImpl.delete(siteTemplateImpl.getRootChannel());
    }

}
