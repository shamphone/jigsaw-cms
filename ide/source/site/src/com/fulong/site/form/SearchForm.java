package com.fulong.site.form;

import com.fulong.common.PagerForm;
/**
 * <p>Title: 龙驭招聘系统</p>
 *
 * <p>Description: 龙驭招聘系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author liuzijun
 * @version 1.0
 */
public class SearchForm extends PagerForm {
    private String keywords;
    private String domain;
    private String state;
    private String beginDate;
    private String endDate;
    public String getKeywords() {
        return keywords;
    }

    public String getDomain() {

        return domain;
    }

    public String getState() {
        return state;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setDomain(String domain) {

        this.domain = domain;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
