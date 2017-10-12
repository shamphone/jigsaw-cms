package com.fulong.cms.form;

import com.fulong.common.PagerForm;

/**
 * <p>Title: 龙驭内容管理系统</p>
 *
 * <p>Description: 龙驭内容管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lishaobo
 * @version 3.0
 */
public class SearchNodesForm extends PagerForm{

	private String definitionID;//当前操作的分类
    private String[] filters;//当前过滤条件集合		
    private String[] displayColumns;//显示的列
    
    private String orderBy1;//当前排序条件
    private String asc1;//排序使用升序或者降序，0表示升序，1表示降序
    
    public SearchNodesForm() {
        super();
    }

    public String getDefinitionID(){
    	return this.definitionID;
    }
    
    public void setDefinitionID(String id){
    	this.definitionID=id;
    }
    
    public String[] getFilters() {
        return filters;
    }
    
    public String[] getDisplayColumns() {
        return displayColumns;
    }
    
    public void setFilters(String[] filters) {
        this.filters = filters;
    }
    
    public void setDisplayColumns(String[] displayColumns) {
        this.displayColumns = displayColumns;
    }
    
    public String getOrderBy1() {
        return this.orderBy1;
    }
    
    public void setOrderBy1(String orderBy) {
        this.orderBy1 = orderBy;
    }
    
    public String getAsc1(){
    	return this.asc1;
    }
    
    public void setAsc1(String asc)
    {
    	this.asc1=asc;
    }
   
}

