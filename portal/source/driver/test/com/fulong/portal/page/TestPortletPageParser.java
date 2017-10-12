package com.fulong.portal.page;

import com.fulong.portal.model.PreferenceSet;
import junit.framework.TestCase;
import org.htmlparser.util.ParserException;
import java.io.File;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.util.NodeList;
/**
 *
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author 李雄锋
 * @version 1.0
 */
public class TestPortletPageParser extends TestCase {
    private  PortletPageParser parser;
    private File file;
    protected void setUp() throws Exception {
        super.setUp();
        file=new File("D:\\LongCon\\WebMaster\\trunk\\deployment\\portal\\templates\\test.jsp");

    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    /**
     * 更新占位符的preference
     * @throws Exception
     */
    public void testUpdatePreference() throws Exception {
          parser=new PortletPageParser(file);
        String portletID = "html2";
        PreferenceSet preferences = new PreferenceSet();
        preferences.put("text",new String[]{"my new text1","my new text2","my new text3"});
        preferences.put("text2","my new html text");
        preferences.put("text3",new String[]{"my 3text1","my 3text2"});
         parser.updatePreference(portletID, preferences);
         parser.save();
    }
    /**
     * 更新标题
     * @throws Exception
     */
    public void testTitle() throws Exception {
          parser=new PortletPageParser(file);
        String title=parser.getTitle();
        this.assertEquals(title,"title");
        String newTitle="new title";
        parser.setTitle(newTitle);
        this.assertEquals(parser.getTitle(),newTitle);
        parser.save();
        parser.setTitle(title);
        parser.save();
    }
    /**
     * 更新metadata
     * @throws Exception
     */

    public void testMetaData() throws Exception{
          parser=new PortletPageParser(file);
       MetaTag tag= parser.getMetaTag("robots");
       tag.setAttribute("content","index");
       this.assertEquals(parser.getMetaTag("robots").getAttribute("content"),"index");
       parser.setMetaData("author","","lixf");
       this.assertEquals(parser.getMetaTag("author").getAttribute("content"),"lixf");
       parser.save();
    }
    /**
     * 更新metadata
     * @throws Exception
     */

    public void testDeleteMetaData() throws Exception{
      parser=new PortletPageParser(file);
      parser.setMetaData("author",null,"");
//      this.assertNull(parser.getMetaTag("author"));
      parser.save();
  }

  public void testLinkTag() throws Exception{
      parser=new PortletPageParser(file);
      NodeList nodes=parser.getLinkTags();
      this.assertEquals(nodes.size(),2);
      parser.remove(nodes);
      parser.addLinkTag("stylesheet","text/css","style3.css");
      parser.save();
  }


}
