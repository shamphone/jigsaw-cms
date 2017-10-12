package com.fulong.longcon.site.impl;

import java.sql.SQLException;
import java.util.Date;

import com.fulong.common.dao.DaoFactory;
import com.fulong.common.dao.DatabaseException;
import com.fulong.longcon.security.Group;
import com.fulong.longcon.site.SiteCategory;
import com.fulong.longcon.site.dao.SiteCategoryDao;
import com.fulong.longcon.site.dao.SiteDao;
import com.fulong.longcon.site.dao.SiteTemplateDao;
import com.fulong.longcon.site.data.SiteCategoryData;
import com.fulong.longcon.site.ext.SiteFactoryExt;

/**
 *
 * <p>Title: 龙驭建站系统核心引擎－－网站分类</p>
 *
 * <p>Description: 龙驭建站系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author lishaobo
 * @version 2.0
 */
public class SiteCategoryImpl implements SiteCategory {
    private SiteFactoryExt factory;
    private SiteCategoryData data;

    public SiteCategoryImpl(SiteFactoryExt factory, SiteCategoryData data) {
        this.factory = factory;
        this.data = data;
    }

    /**
     * 类别名字
     * @return String
     */
    public String getDisplayName() {
        return this.data.getDisplayName();
    }

    /**
     *
     * @return String
     */
    public String getID() {
        return this.data.getPkid();
    }

    /**
     *
     * @return String
     */
    public String getName() {
        return this.data.getName();
    }

    /**
     * 修改名称
     * @param name String
     */
    public void setDisplayName(String name) {
        this.data.setDisplayName(name);
        this.save();
    }

    /**
     * 获取对本类型的描述
     * @return String
     */
    public String getDescription() {
       return this.data.getDescription();
    }

    /**
     * 类别描述
     * @param description String
     */
    public void setDescription(String description) {
        this.data.setDescription(description);
        this.save();
    }

    /**
     * 创建时间
     * @return Date
     */
    public Date getCreatedDate() {
        return this.data.getCreateDate();
    }

    /**
     * 对象状态，刚的对象处于CREATED状态。
     * @return String
     */
    public String getState() {
        return this.data.getState();
    }

    /**
     * 设置起始时间，起始时间后进入发布状态，在expiryDate后进入失效状态。
     * @param StartDate Date
     * @param expiryDate Date
     */
    public void setStartDate(Date StartDate) {
        this.data.setStartDate(StartDate);
        this.save();
    }

    /**
     * 起始时间
     * @return Date
     */
    public Date getStartDate() {
        return this.data.getStartDate();
    }

    /**
     * 设置过期时间，网站在过期时间之后，进入Site.Expiry
     * @param expiryDate Date
     */

    public void setExpiryDate(Date expiryDate) {
        this.data.setExpiryDate(expiryDate);
        this.save();
    }

    /**
     * 过期时间
     * @return Date
     */
    public Date getExpiryDate() {
        return this.data.getExpiryDate();
    }

    /**
     *
     */
    private void save() {
        DaoFactory daoFactory = this.factory.newDaoFactory();
        try {
            daoFactory.open();
            SiteCategoryDao dao = (SiteCategoryDao) daoFactory.getDao(
                SiteCategoryDao.class);
            dao.update(this.data);
        }
        catch (SQLException se) {
            daoFactory.rollback();
            throw new DatabaseException(se);
        }
        finally {
            daoFactory.close();
        }
    }

    /**
     * 设置状态
     * @param state String
     */
    public void setState(String state) {
        this.data.setState(state);
        this.save();
    }

    /**
     * 获得该分类下的模板的数量
     * @return int
     */
    public int getTemplateCount() {
        DaoFactory daoFactory = this.factory.newDaoFactory();
        try {
            daoFactory.open();
            SiteTemplateDao dao = (SiteTemplateDao) daoFactory.getDao(
                SiteTemplateDao.class);
            int result = dao.getCountbyCategory(this.getID());
            return result;
        }
        catch (SQLException se) {
            throw new DatabaseException(se);
        }
        finally {
            daoFactory.close();
        }

    }

    /**
     * 获得该分类下的站点的数量
     * @return int
     */
    public int getSiteCount() {
        DaoFactory daoFactory = this.factory.newDaoFactory();
        try {
            daoFactory.open();
            SiteDao dao = (SiteDao) daoFactory.getDao(
                SiteDao.class);
            int result = dao.getSitesCountByCategory(this.getID());
            return result;
        }
        catch (SQLException se) {
            throw new DatabaseException(se);
        }
        finally {
            daoFactory.close();
        }

    }

    /**
     * 所在分组
     * @return Group
     */
    public Group getGroup() {
        return this.factory.getPassport().getGroup(this.data.getGroupID());
    }

    /**
     * 设置分组
     * @param group Group
     */

    public void setGroup(Group group) {
        this.data.setGroupID(group.getID());
        this.save();
    }

    /**
     *
     * @param o Object
     * @return boolean
     */
    public boolean equals(Object o) {
        if (! (o instanceof SiteCategory))
            return false;
        return ( (SiteCategory) o).getID().equals(this.getID());
    }

    /**
     *
     * @return int
     */
    public int hashCode() {
        return this.getID().hashCode();
    }
}
