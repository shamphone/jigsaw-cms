package com.fulong.report;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRValueStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lixf
 * @version 1.0
 */
public class ReportManager {
    private static final Log log = LogFactory.getLog(ReportManager.class);
    private Map<String, JasperReport> reports;
    private DataSource dataSource;

    @SuppressWarnings("unchecked")
	public void init() {
        this.reports = Collections.synchronizedMap(new HashMap());
    }

    /**
     * 加载file文件对应的报表，并对这个报表做解析，保存到缓存中。注意，如果报表数量较多，会可能导致大量的内存占用。
     * @param id String，唯一识别符
     * @param file File 报表文件
     * @return JasperReport 解析结果。
     * @throws JRException
     */
    public JasperReport load(String id, File file) throws JRException {
        if (this.reports.get(id) != null)
            throw new IllegalArgumentException("report with id" + id +
                                               " has exists. please check it.");
        long start = System.currentTimeMillis();
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(file);
        this.reports.put(id,
                         jasperReport);
        log.info("[" + (System.currentTimeMillis() - start) +
                 "] Successfully load jasper file:" +
                 file.getPath());
        return jasperReport;

    }

    /**
     * 抽取数据库数据，产生JasperPrint对象。
     * @param id String：对应的JasperReport对象，由load(id,file)方法加载到本管理器中的JasperReport对象。
     * @param parameters Map：相关的参数,为<String , String[]>类型的映射
     * @return JasperPrint：产生的对象。
     * @throws JRException
     * @throws SQLException
     * @todo 考虑如何使用缓存来提高性能
     */

    @SuppressWarnings("unchecked")
	public JasperPrint genReport(String id, Map parameters) throws JRException,
            SQLException {
        JasperReport jasperReport = this.reports.get(id);

        if (jasperReport == null)
            return null;
        Connection connection = null;
        try {
            connection = this.dataSource.getConnection();
            return JasperFillManager.fillReport(
                    jasperReport,
                    this.convertParameter(jasperReport, parameters),
                    connection);
        } finally {
            if (connection != null) {
                if (!connection.isClosed()) {
                    connection.close();
                }

            }
        }
    }

    /**
     * 将参数集合转换成标准的集合
     * @param report JasperReport
     * @param parameters Map
     * @return Map
     */
    @SuppressWarnings("unchecked")
	private Map convertParameter(JasperReport report, Map parameters) {
        Map converted = Collections.synchronizedMap(new HashMap());
        JRParameter[] JRParams = report.getParameters();
        for (int i = 0; i < JRParams.length; i++) {
            String name = JRParams[i].getName();
            Object objValue = parameters.get(name);
            if (objValue != null) {
                if (objValue instanceof String) {
                    String strValue = (String) objValue;
                    converted.put(name,
                                  JRValueStringUtils.deserialize(JRParams[i].
                            getValueClassName(), strValue));
                } else if (objValue instanceof String[]) {
                    String[] strValues = (String[]) objValue;
                    if (strValues.length == 1)
                        converted.put(name,
                                      JRValueStringUtils
                                      .deserialize(JRParams[i].
                                getValueClassName(),
                                strValues[0]));
                    else //do not convert if it is a string array.
                        converted.put(name, objValue);
                } else //do not convert if it is an anknown type.
                    converted.put(name, objValue);
            }
        }
        return converted;

    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
