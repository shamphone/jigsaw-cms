package com.fulong.portal.page;

import org.htmlparser.tags.CompositeTag;

/**
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
public class HLinkTag extends CompositeTag {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8196427029203609321L;
	private static final String[] mIds = new String[] {"LINK"};
      public HLinkTag ()
      {
      }
      public String[] getIds ()
      {
          return (mIds);
      }
      public String[] getEnders ()
      {
          return (mIds);
      }
      public String[] getEndTagEnders ()
      {
          return (new String[0]);
      }
      public boolean isEmptyXmlTag() {
          return true;
      }
}
