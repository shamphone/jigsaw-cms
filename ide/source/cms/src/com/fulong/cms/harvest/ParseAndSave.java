package com.fulong.cms.harvest;

import java.io.File;
import java.security.Principal;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.fulong.longcon.repository.NodeDefinition;

/**
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author HaoJingwei
 * @version 1.0
 */

public class ParseAndSave {
    private NodeDefinition category;
    private Principal owner;
    private String filePath;
    private String baseNodeName;
    /*
     *构造函数，新建对象，需要知道category，Principal和Xml文件存放的路径+文件名
     */
    ParseAndSave(NodeDefinition cat, String path, Principal own) {
        category = cat;
        filePath = path;
        owner = own;
        baseNodeName = "article"; //可以从之前的action得到，也可以写死就是article，应该是指示每条记录开始的节点名称
    }

    public void doPaseAndSave() {
        try {
            File f = new File(filePath);
            SAXReader reader = new SAXReader();
            Document doc = reader.read(f);
            Element root = doc.getRootElement();
            Element foo;
            for (Iterator i = root.elementIterator(baseNodeName); i.hasNext(); ) {
                foo = (Element) i.next();
                SaveForHarvestedContent contentNode = new
                        SaveForHarvestedContent(category, owner);
                List pros = contentNode.properties(); //得到当前内容可更改（填写）的属性
                Calendar createTime = Calendar.getInstance();
                contentNode.doSaveNode().setProperty("createdTime", createTime);
                for (int l = 0; l < pros.size(); l++) { //在文档中寻找与属性匹配的标签，赋值
                    String s = pros.get(l).toString();
                    contentNode.doSaveNode().setProperty(s, foo.elementText(s));
                }
            }
            forceDelete(f); //删除解析完的文件
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
     *强制删除文件
     */
    public static boolean forceDelete(File f) {
        boolean result = false;
        int tryCount = 0;
        while (!result && tryCount++ < 10) {
            System.gc();
            result = f.delete();
        }
        return result;
    }

}

