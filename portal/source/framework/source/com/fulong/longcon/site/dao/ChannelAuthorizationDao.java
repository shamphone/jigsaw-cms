package com.fulong.longcon.site.dao;

import java.sql.SQLException;

import com.fulong.common.dao.Dao;
import com.fulong.longcon.site.data.ChannelAuthorizationData;

/**
 * 
 * <p>
 * Title: 龙驭建站系统核心引擎－栏目授权表数据操作接口
 * </p>
 * 
 * <p>
 * Description: 龙驭建站系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author sunyuchao
 * 
 */

public interface ChannelAuthorizationDao extends Dao{

	/**
	 * 向栏目授权表中插入数据
	 * @param data
	 * @throws SQLException
	 */
	public void create(ChannelAuthorizationData data)throws SQLException;
	
	
	/**
	 * 删除栏目授权表中的数据
	 * @param data
	 * @throws SQLException
	 */
	public void delete(ChannelAuthorizationData data)throws SQLException;
	
	/**
	 * 删除栏目授权表中的数据
	 * @param templateId
	 * @throws SQLException
	 */	
	public void delete(String templateId, String channelId) throws SQLException ;	
	
	/**
	 * 根据模板ID获取该模板下全部授权栏目
	 * @param templateId
	 * @return
	 * @throws SQLException
	 */
    public ChannelAuthorizationData[] findByTemplateID(String templateId)throws SQLException;

	/**
	 * 根据模板ID和栏目ID获取授权栏目
	 * @param templateId
	 * @param channelID
	 * @return
	 * @throws SQLException
	 */
    public ChannelAuthorizationData[] findByChannelID(String templateId,String channelId)throws SQLException;

    /**
     * 根据模板ID和栏目ID，用户ID获取授权栏目
     * @param templateId
     * @param channelId
     * @param principalID
     * @return
     * @throws SQLException
     */
    public ChannelAuthorizationData findByPrincipalID(String templateId,String channelId,String principalID)throws SQLException;

    /**
     * 根据模板ID和栏目ID，用户ID获取授权给该用户所在组的全部栏目
     * @param tmeplateId
     * @param channelId
     * @param principalId
     * @return
     * @throws SQLException
     */
    //public ChannelAuthorizationData[] findByGroupID(String templateId,String channelId,String principalId) throws SQLException;

	/**
     * 根据用户组ID和模板ID获取授权给该用户组的全部栏目
     * @param groupID
     * @param templateID
     * @return ChannelAuthorizationData[]
     * @throws SQLException
     */
	public ChannelAuthorizationData[] findByGroupIDAndTemplateID(String groupID, String templateID) throws SQLException;

	/**
	 * 实现同findByGroupID 
     * 根据用户组ID和模板ID和栏目ID获取对应栏目
     * @param groupID
     * @param templateID
     * @param channelID
     * @return ChannelAuthorizationData
     * @throws SQLException
     */
	public ChannelAuthorizationData findByGroupIDAndTemplateIDAndChannelID(String groupID, String templateID, String channelID) throws SQLException;
}
