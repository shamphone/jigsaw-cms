package com.fulong.longcon.site.ext;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.SiteTemplate;

/**
 * 网站工厂,网站的总入口,提供对网站和域名的管理.
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lishaobo
 * @version 1.0
 */
public interface SiteTemplateExt
    extends SiteTemplate {
	   /**
     * 将分类内容发布到栏目channel上。如果已经设置了对bindingNode的发布，则取消已有的设置。
     * @param channel
     * @param bindingNode
     */
    public void setBindingChannel(NodeDefinition bindingNode,Channel channel);
    /**
     * 获取栏目对应的所有绑定
     * @param channel
     * @return
     */
    public NodeDefinition[] getBinding(Channel channel);
    
    /**
     * 清除所有绑定
     * @param channel
     */
    public void clearBinding(Channel channel);
    /**
     * 创建子栏目
     * @param name String 栏目名称，注意不是显示名称。缺省的，显示名称和栏目名称是一样的。之后可以修改显示名称，而栏目名称不能修改
     * @return Channel
     */
    public Channel addChannel(Channel parentChannel, String name, String type);



}
