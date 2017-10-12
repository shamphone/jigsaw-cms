package com.fulong.cms.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fulong.common.PagerForm;

import org.apache.struts.upload.FormFile;

/**
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author 马伟山
 * @version 1.0
 */
public class ContentForm extends PagerForm {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8770132214799187659L;
	private static final SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    private String[] IDs;
    private String auditContent;
    private String publishDate;
    private String expiredDate;
    private String[] categorys;
    private FormFile sendImg;
    private String category;
    private String contentId;

    private String keywords;
    private String state;
    private String creatDateE;
    private String publishDateE;
    private String creatDate;
    private String transition;
    private String publish;
    private String expired;
    private String inherit;
    private String[] correlationLinks;
    private String startDate;
    private String startDateE;
    private String author;
    private String[] categoryIDs;
    private Map xmls;
    private Map titles;
    private String[] fixProperty;
    private String thrIDs;
    private String nodeName;
    private String duty;
    private String registercapital;
    private String asslastincome;
    private String assbusiness;
    private String assqualification;
    private String area1;
    private String area2;
    private String owner;
    private String definitionId;
    private String propertyDefinitionId;
    private String nodeId;
    private String contentDefinitionId;
    private String title;
    private String ID;
    private String xml;
    public ContentForm(){
        this.xmls=new HashMap();
        this.titles=new HashMap();
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCreatDateE(String creatDateE) {
        this.creatDateE = creatDateE;
    }

    public void setPublishDateE(String publishDateE) {
        this.publishDateE = publishDateE;
    }

    public void setCreatDate(String creatDate) {
        this.creatDate = creatDate;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getState() {
        return state;
    }

    public String getCreatDateE() {
        return creatDateE;
    }

    public String getPublishDateE() {
        return publishDateE;
    }

    public String getCreatDate() {
        return creatDate;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getCategory() {
        return category;
    }

    public String getContentId() {
        return contentId;
    }

    public String[] getIDs() {
        return IDs;
    }

    public void setIDs(String[] IDs) {
        this.IDs = IDs;
    }

    public void setAuditContent(String auditContent) {
        this.auditContent = auditContent;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public void setCategorys(String[] categorys) {
        this.categorys = categorys;
    }

    public void setSendImg(FormFile sendImg) {
        this.sendImg = sendImg;
    }

    public void setTransition(String transition) {
        this.transition = transition;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public void setInherit(String inherit) {
        this.inherit = inherit;
    }

    public void setCorrelationLinks(String[] correlationLinks) {
        this.correlationLinks = correlationLinks;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setStartDateE(String startDateE) {
        this.startDateE = startDateE;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategoryIDs(String[] categoryIDs) {
        this.categoryIDs = categoryIDs;
    }

    public void setFixProperty(String[] fixProperty) {
        this.fixProperty = fixProperty;
    }

    public void setThrIDs(String thrIDs) {
        this.thrIDs = thrIDs;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public void setRegistercapital(String registercapital) {
        this.registercapital = registercapital;
    }

    public void setAsslastincome(String asslastincome) {
        this.asslastincome = asslastincome;
    }

    public void setAssbusiness(String assbusiness) {
        this.assbusiness = assbusiness;
    }

    public void setAssqualification(String assqualification) {
        this.assqualification = assqualification;
    }

    public void setArea1(String area1) {
        this.area1 = area1;
    }

    public void setArea2(String area2) {
        this.area2 = area2;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setDefinitionId(String definitionId) {
        this.definitionId = definitionId;
    }

    public void setPropertyDefinitionId(String propertyDefinitionId) {
        this.propertyDefinitionId = propertyDefinitionId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public void setContentDefinitionId(String contentDefinitionId) {
        this.contentDefinitionId = contentDefinitionId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAuditContent() {
        return auditContent;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public String getExpiredDate() {
        return expiredDate;
    }
    public Date getExpiredDateTime() throws ParseException {
        return this.formatTime.parse(this.expiredDate);
    }

    public String[] getCategorys() {
        return categorys;
    }

    public FormFile getSendImg() {
        return sendImg;
    }

    public String getTransition() {
        return transition;
    }

    public String getPublish() {
        return publish;
    }

    public boolean isSetPublishTime() {
        return ((publish!=null)&&publish.equals("fixedDate"));
    }

    public String getExpired() {
        return expired;
    }

    public boolean isSetExpiredTime(){
        return ((expired!=null)&&expired.equals("fixedDate"));
    }

    public String getInherit() {
        return inherit;
    }

    public String[] getCorrelationLinks() {
        return correlationLinks;
    }

    public String getStartDate() {
        return startDate;
    }

    public Date getStartDateTime() throws ParseException {
        return formatTime.parse(this.startDate);
    }

    public String getStartDateE() {
        return startDateE;
    }

    public String getAuthor() {
        return author;
    }

    public String[] getCategoryIDs() {
        return categoryIDs;
    }

    public String[] getFixProperty() {
        return fixProperty;
    }

    public String getThrIDs() {
        return thrIDs;
    }

    public String getNodeName() {
        return nodeName;
    }

    public String getDuty() {
        return duty;
    }

    public String getRegistercapital() {
        return registercapital;
    }

    public String getAsslastincome() {
        return asslastincome;
    }

    public String getAssbusiness() {
        return assbusiness;
    }

    public String getAssqualification() {
        return assqualification;
    }

    public String getArea1() {
        return area1;
    }

    public String getArea2() {
        return area2;
    }

    public String getOwner() {
        return owner;
    }

    public String getDefinitionId() {
        return definitionId;
    }

    public String getPropertyDefinitionId() {
        return propertyDefinitionId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getContentDefinitionId() {
        return contentDefinitionId;
    }

    public String getTitle() {
        return title;
    }

    public String getXml() {
        return xml;
    }

    public String getID() {
        return ID;
    }

    public String getXML(String id){
        return (String)this.xmls.get(id);
    }

    public void setXML(String id,String xml){
        this.xmls.put(id,xml);
    }

    public String getTitles(String title){
        return (String)this.titles.get(title);
    }

    public void setTitles(String id,String title){
        this.titles.put(id,title);
    }

    public Date getCreateDateBegin() throws ParseException {
        if ( (this.creatDate != null) && this.creatDate.length() > 0)
            return formatTime.parse(creatDate + " 00:00");
        return null;
    }

    public Date getCreateDateEnd() throws ParseException {
        if ( (this.creatDateE != null) && (this.creatDateE.length() > 0))
            return formatTime.parse(creatDateE + " 23:59");
        return null;

    }
    public Date getPublishDateBegin() throws ParseException {
      if ( (this.publishDate != null) && this.publishDate.length() > 0)
          return formatTime.parse(publishDate + " 00:00");
      return null;
  }

  public Date getPublishDateEnd() throws ParseException {
      if ( (this.publishDateE != null) && (this.publishDateE.length() > 0))
          return formatTime.parse(publishDateE + " 23:59");
      return null;

  }
  public Date getStartDateBegin() throws ParseException {
    if ( (this.startDate != null) && this.startDate.length() > 0)
        return formatTime.parse(startDate + " 00:00");
    return null;
}

public Date getStartDateEnd() throws ParseException {
    if ( (this.startDateE != null) && (this.startDateE.length() > 0))
        return formatTime.parse(startDateE + " 23:59");
    return null;

}

}
