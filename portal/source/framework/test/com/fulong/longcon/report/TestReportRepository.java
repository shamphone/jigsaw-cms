package com.fulong.longcon.report;

import junit.framework.TestCase;
import com.fulong.common.ResourceUtils;
import com.fulong.longcon.workflow.WorkflowService;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import java.util.Properties;
import org.springframework.core.io.FileSystemResource;
import java.net.URL;
import com.fulong.longcon.security.SecurityManager;
import javax.swing.table.TableModel;
import com.fulong.longcon.report.TableModelWrapper;

/**
 *
 * <p>Title: 龙驭论坛核心引擎</p>
 *
 * <p>Description: 龙驭论坛核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lixf
 * @version 2.0
 */
public class TestReportRepository
    extends TestCase {
    private ReportRepository reports = null;

    protected Properties properties;
    protected XmlBeanFactory beanFactory;
    protected SecurityManager provider;
    protected WorkflowService workflow;
    protected void setUp() throws Exception {
        super.setUp();
        URL file = this.getClass().getClassLoader().getResource(
            "test.config.xml");
        FileSystemResource resource = new FileSystemResource(file.getPath());
        this.beanFactory = new XmlBeanFactory(resource);
        this.reports = (ReportRepository) beanFactory.getBean("reports");
    }

/*
    public void testCount() {
        TableModel model = this.reports.getReport("test");
      //  this.assertEquals(model.getColumnCount(), 3);
       // String orgID = (String)model.getValueAt(0,0);

        //this.assertEquals(model.getRowCount(), 4);
        String[] paras = new String[]{"09", "2005"};
        TableModel provinceCount = this.reports.getReport("hysjk_jjxy_year1", paras);
        this.assertEquals(provinceCount.getColumnCount(), 2);

        String[] paras2 = new String[]{"2005"};
        provinceCount = this.reports.getReport("hysjk_jjxy_year2", paras2);
        this.assertEquals(provinceCount.getColumnCount(), 2);

        String[] paras3 = new String[]{"09"};
       provinceCount = this.reports.getReport("hysjk_jjxy_year3", paras3);
       this.assertEquals(provinceCount.getColumnCount(), 2);



       //this.assertEquals(model.getRowCount(), 4);



    }
    */

    public void testCount1() {
       TableModel model = this.reports.getReport("hygkfb_scgmjzd4");
       TableModelWrapper tmw = new TableModelWrapper(model);
       model = tmw.turnTableModel();

      this.assertEquals(model.getColumnCount(), 2);



   }


    protected void tearDown() throws Exception {
        provider = null;
        super.tearDown();
    }

}
