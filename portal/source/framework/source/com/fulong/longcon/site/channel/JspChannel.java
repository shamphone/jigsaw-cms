/**
 * 
 */
package com.fulong.longcon.site.channel;

import java.io.IOException;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.Tag;
import org.htmlparser.tags.JspTag;
import org.htmlparser.util.ParserException;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.site.impl.SiteFactoryImpl;



/**
 * <p>Title: JspChannel</p>
 * <p>Description: Coolink协同工作支撑平台</p>
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p> 
 * @author    lixf
 * @date      2010-5-12
 */
public class JspChannel extends GeneralChannel{
	static final PrototypicalNodeFactory factory = new PrototypicalNodeFactory();
	static{
		factory.registerTag(new Title2Tag());
		factory.registerTag(new HtmlTag());
	}
	/*
	 * 读取文件第一行，如果是一//fileinfo开始，则为系统注释，读出displayname的字段；否则使用文件名；
	 * 
	 * @see com.fulong.longcon.site.Channel#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		String name = null;
		try {
			name = this.parseDisplayName();
		} catch (Exception ex) {
			//just ignore;
		}
		if((name == null)||(name.length()==0)){
			try{
			name = this.parseTitle();
			}catch(Exception ex){
				//just ignore;
			}
		}
		if((name == null)||(name.length()==0))
			return this.getName();
		return name;
	}
	
	/**
	 * jsp指令可能并不在第一行	比如第一行是文档类型声明
	 * 导致获取显示名称出错
	 * 从jsp指令中解析出显示名称;
	 * @return
	 * @throws IOException
	 * @throws ParserException 
	 */
	private String parseDisplayName() throws IOException, ParserException{
		final String[] displayName={""};
		Parser parser = new Parser(this.file.getPath());		
		parser.setEncoding("UTF-8");
		parser.parse(new NodeFilter() {

			private static final long serialVersionUID = 1L;

			public boolean accept(Node node) {
				if(node instanceof JspTag){
					JspTag tag = (JspTag)node;
					String text = tag.getText();
					if(text.indexOf("%@page")>=0){
						int index = text.indexOf("info=");
						if(index>=0){
							displayName[0] = text.substring(index+6, text.indexOf("\"", index+6));
							return true;
						}
					}
				}
				return false;
			};
		});
		return displayName[0];
	}
	/**
	 * 从title标签中解析出标题；
	 * @return
	 * @throws IOException
	 * @throws ParserException 
	 */
	private String parseTitle() throws IOException, ParserException{
		final String[] title={""};
		Parser parser = new Parser(this.file.getPath());		
		parser.setEncoding("UTF-8");
		parser.setNodeFactory(factory);
		parser.parse(new NodeFilter() {
			private static final long serialVersionUID = 8081693127898613904L;
			public boolean accept(Node node) {
				if(node instanceof Tag){
					Tag tag = (Tag)node;
					if(tag.getTagName().equalsIgnoreCase("title")){
						if(title[0].length()==0)
							title[0]=tag.toPlainTextString();
					}						
					if(tag.getTagName().equalsIgnoreCase("site:title2")){
						if(title[0].length()==0)
							title[0]=tag.toPlainTextString();
					}						
					return true;
				}
				return false;
			};
		});
		return title[0];
	}

	@Override
	public String getType() {		
		return "index";
	}

	
	public NodeDefinition getBindingNode(){
		final String[] title={""};
		try{
		Parser parser = new Parser(this.file.getPath());		
		parser.setEncoding("UTF-8");
		parser.setNodeFactory(factory);
		parser.parse(new NodeFilter() {
			private static final long serialVersionUID = 8081693127898613904L;
			public boolean accept(Node node) {
				if(node instanceof HtmlTag){
					HtmlTag tag = (HtmlTag)node;
					title[0]=tag.getDefinition();
					return true;
				}
				return false;
			};
		});
		if(title[0].length()>0)
			return ((SiteFactoryImpl)this.template.getSiteFactory()).getRepository().getDefinitionManager().getDefinition(title[0]);
		return null;
		}catch(Exception ex){
			return null;
		}
	}

}