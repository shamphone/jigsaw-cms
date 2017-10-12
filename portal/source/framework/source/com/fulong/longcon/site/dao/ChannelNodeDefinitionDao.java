package com.fulong.longcon.site.dao;

import java.sql.SQLException;

import com.fulong.common.dao.Dao;
import com.fulong.longcon.site.data.ChannelNodeDefinitionData;

/**
 * 
 * 
 * 
 * Coolink协同工作框架模型
 * 
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 * 
 * Company: 北京中科辅龙计算机技术股份有限公司
 * 
 * @author lixf
 * 
 * @version 2.0
 * 
 */
public interface ChannelNodeDefinitionDao extends Dao {
	/**
	 * 将栏目ID与内容分类ID写入数据
	 * 
	 * @param templateID
	 * @param channelID
	 * @param NodeDefinitionID
	 * @throws SQLException
	 */
	public void insert(ChannelNodeDefinitionData data) throws SQLException;

	/**
	 * 删除指定模板下指定内容分类的绑定关系
	 * 
	 * @param templateID
	 * @param ID
	 * @throws SQLException
	 */
	public void delete(String templateID, String nodeDefinitionID) throws SQLException;

	/**
	 * 查找与指定模板指定内容分类绑定的栏目
	 * 
	 * @param templateID
	 * @param NodeDefinitionID
	 * @return
	 * @throws SQLException
	 */
	public ChannelNodeDefinitionData[] find(String templateID) throws SQLException;

	/**
	 * 删除指定模板指定栏目的全部已绑定的内容分类
	 * 
	 * @param templateID
	 * @param channelID
	 * @throws SQLException
	 */
	public void deleteAll(String templateID, String channelID) throws SQLException;

}