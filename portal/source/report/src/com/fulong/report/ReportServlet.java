package com.fulong.report;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 解析用户的请求，调用相应的报表，生成html文档.
 * 需要在配置文件中配置初始化的数据源Springframework.
 * servlet在渲染所需要的报表时分为两步：
 * 1. 根据用户请求获取对应的jasper文件，调用数据库计算动态数据产生jasper print对象，该对象调用jasper的HTML exporter产生HTML代码，同时在这个页面上，对应的报表图形被以img标签来标记出来。
 * 2. 产生的html代码中如果有img标签，这个标签负责产生动态的图形报表。这个报表的数据和产生html代码的数据是相同的，但是它们是在浏览器不同 的request中产生的。所以这里需要特殊处理。
 * 处理的方式有：
 * 1. 利用session来缓存jasper print对象，这个对象在两个export中使用，一个用来产生html代码，另一个产生image图形。但是这样会引起session的内存溢出。
 * 2. 在application(servlet context)中缓存jasper pring 对象，这种方式同样会引起内存溢出。但是对于大部分的统计报表，他们对相同的人产生的结果都是一样的，所以性能是最好的。
 * 3. 实时计算，这种方式可能会导致html代码产生的报表结果和img的不一致，它不会引起内存溢出，但是性能最差。
 * 本实现采用第三种处理方式，使用ReportManager来管理报表数据，即jasperReport对象和jaserPrint对象。
 * 这个servlet负责处理reports目录下的Jasper文件请求，拦截所有的reports/***.jasper请求，产生对应的html代码发送给客户端。这个html代码中涉及到的报表图形，由imageServlet处理。
  * 参数的处理:
  * 在jsxml文件中定义的参数，可以使用URL的Query String来传递。对于参数的类型，目前支持常用的几种数据库数据类型，包括;
  * java.util.Date:输入时采用Date().getTime()对应的长整数。
  * java.sql.Timestamp:输入时采用yyyy-mm-dd hh:mm:ss.fffffffff的格式
  * Long\Integer\Double\Float\Numeric\Boolean等数据类型采用其通用的表示方式
 *@todo 目前仅支持Get模式，不支持Put和其他模式。
 *
 * <p>Title: 龙驭报表引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统3.0</p>
 *
 * <p>Copyright: Copyright 北京中科辅龙计算机技术股份有限公司 (c) 2008</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lixf
 * @version 1.0
 */
public class ReportServlet extends BaseServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6695182045664405009L;

    private static final Log log = LogFactory.getLog(ReportServlet.class);

    public void init() throws ServletException {
        super.init();
        //初始化加载所有报表到内存中;
        String folderPath=this.getServletConfig().getInitParameter("report-folder-path");
        File folder = new File(this.getServletContext().getRealPath(folderPath));
        File[] jasperFiles = folder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".jasper");
            }
        });
        for (int i = 0; i < jasperFiles.length; i++) {
            try {
                this.reports.load(jasperFiles[i].getName(),
                                  jasperFiles[i]);
            } catch (JRException ex) {
                log.error("Error in loading jasper file: " +
                          jasperFiles[i].getPath(), ex);
            }
        }
    }

    /**
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException
     * @throws ServletException
     */
    @SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request,
            HttpServletResponse response
            ) throws IOException, ServletException {
//        log.trace("Generate html for "+request.getRequestURL().toString());
        response.setContentType("text/html");
        String reportId = this.parserReportId(request);
        Map parameters = Collections.synchronizedMap(new HashMap(request.
                getParameterMap()));
        try {
            long start = 0;
            if (log.isDebugEnabled())
                start = System.currentTimeMillis();
            JasperPrint jasperPrint = this.reports.genReport(reportId, parameters);
            if (jasperPrint == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
  //          log.trace("[" + (System.currentTimeMillis() - start) +"]Jasper Print generated.");

            if (log.isDebugEnabled())
                start = System.currentTimeMillis();

            JRHtmlExporter exporter = new JRHtmlExporter();
            PrintWriter writer=response.getWriter();
            try{
                exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                      jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_WRITER,
                                      writer);
                exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,
                                      this.getImageURL(request));
                exporter.exportReport();
            }finally{
                writer.close();
            }
            log.debug("[" + (System.currentTimeMillis() - start) +"] Export report for " + reportId + "." + "memory size=" +  Runtime.getRuntime().freeMemory());
        } catch (JRException e) {
           throw new ServletException(e);
        } catch (SQLException e) {
           throw new ServletException(e);
        }
    }
    /**
     * 解析出所请求的jasper文件。
     * @param request HttpServletRequest
     * @return String
     */
    private String parserReportId(HttpServletRequest request){
        String path=request.getRequestURI();
        return path.substring(path.lastIndexOf("/")+1);
    }
    /**
     * 产生报表图形的 url地址。目前采用的地址生成方式是：
     * /report-images/****.jasper?[本servlet的请求Query String]&image=[该图形在这个html页面中的唯一标记]。
      * 这种方式的缺点是仅支持Get模式，不支持Put和其他模式。
     * @param request HttpServletRequest
     * @return String
     * @todo 可能需要和jasperreport的参数以及request参数结合起来产生url地址，以支持各种请求模式。
     */
    private String getImageURL(HttpServletRequest request) {
        StringBuffer buffer = new StringBuffer(request.getContextPath());
        buffer.append("/report-image.png?");
        String queryString = request.getQueryString();
        if (queryString != null) {
            buffer.append(queryString);
            buffer.append("&");
        }
        buffer.append("reportId=")
                .append(this.parserReportId(request))
                .append("&image=");
        return buffer.toString();
    }

}
