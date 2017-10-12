package com.fulong.longcon.report;

import javax.swing.table.TableModel;

/**
 * <p>Title: 龙驭核心引擎</p>
 *
 * <p>Description: 龙驭核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lixf
 * @version 2.0
 */
public interface ReportRepository {
    /**
     * 执行SQL语句获得查询结果
     * @param sqlName String
     * @return TableModel
     */
    public TableModel getReport(String sqlName);

    /**
     * 执行SQL语句获得查询结果
     * @param sqlName String
     * @param params Object[]
     * @return TableModel
     */
    public TableModel getReport(String sqlName, Object[] params);


    /**
     * 通过拼接获得sql语句,执行SQL语句获得查询结果
     * @param sqlName String,配置文件中sql语句名
     * @param sqlParts String[],待拼接的部分
     * @param params Object[],参数
     * @return TableModel
     */
    public TableModel getReport(String sqlName, String[] sqlParts, Object[] params);


    /**
     * 通过拼接获得获得数量的sql语句,执行SQL语句获得查询结果，为该sql查询的记录数
     * @param sqlName String
     * @param sqlParts String[]
     * @param params Object[]
     * @return long
     */
    public long getRecordsCount(String sqlName, String[] sqlParts,
                              Object[] params);


  /**
   * 通过拼接获得获得数量的sql语句,执行SQL语句获得查询结果，为该sql查询的记录数
   * @param sqlName String
   * @param sqlParts String[]
   * @param params Object[]
   * @return long
   */
  public long getRecordsCount(String sqlName,Object[] params);

  public TableModel getPaidTable() ;

}
