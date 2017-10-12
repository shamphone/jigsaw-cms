package com.fulong.longcon.repository.remote.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Document;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.RepositoryTestCase;
import com.fulong.longcon.repository.remote.XMLHandler;

/**
 * <p>Title: ��Ԧ��վ���ݹ���ϵͳ��������</p>
 *
 * <p>Description: ��Ԧ��վ���ݹ���ϵͳ��������</p>
 *
 * <p>Copyright: �����пƸ������������޹�˾ 2006</p>
 *
 * <p>Company: �����пƸ������������޹�˾</p>
 *
 * @author not attributable
 * @version 2.0
 */
public class TestXMLTranslatorImpl extends RepositoryTestCase {


    protected void setUp() throws Exception {
        super.setUp();
        /**@todo verify the constructors*/

    }

    protected void tearDown() throws Exception {

        super.tearDown();
    }


    public void testReg() throws Exception{
        String pattern = "\\{+\\d*\\}+";
        String value ="fsdasjdfkjsdklf{{{{{{{{2}fdsfajskd姐夫大开杀戒{11}发生{1}大火反馈";
        Pattern p = Pattern.compile(pattern);
        String s1= "{1}";
        String s2= "bacdsafdsfsd{1}dfdfsdafdsf";
        String rep =  value.replaceAll(pattern, "HELLOWORLD");
        String[] temp = p.split(value);


        Matcher m = p.matcher(value);
        while (m.find()) {
             System.out.println(m.group());
        }


      //  temp = p.split(s2);
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<temp.length-1;i++){
            sb.append(temp[i]);
            String reg = value.substring(sb.length(), value.indexOf("}",sb.length())+1);
//            sb.append("{");
//            sb.append("");
//            sb.append("}");
            sb.append(reg);
        }
        sb.append(temp[temp.length-1]);
        String resu = sb.toString();

        Matcher m1 = p.matcher(s1);
        Matcher m2 = p.matcher(s2);

        boolean b = m.matches();
        if(b)
            s1 = m.replaceAll("--");

        b = m1.matches();
        if(b)
            s1 = m1.replaceAll("--");

        b = m2.matches();
        if(b)
            s1 = m2.replaceAll("--");

}
 public void testXMLHandler() throws Exception{
    Node content = this.repository.getNode("2455700415031");

    XMLHandler handler = new XMLHandler(this.remoteManager.getCategory("2455021123503"));
    Document doc =  handler.exportXML(content);

    XMLHandler importer = new XMLHandler();

    Node node = this.repository.getNode("1000000000000").addNode(this.repository.getDefinitionManager().getDefinition("resource-scheme"));

//    importer.importXML(node, doc);

    this.repository.delete(node);


}




}
