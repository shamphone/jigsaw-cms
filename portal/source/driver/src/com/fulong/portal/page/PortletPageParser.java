package com.fulong.portal.page;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

import com.fulong.portal.model.PreferenceSet;
import org.apache.commons.io.FileUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.Tag;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.BaseHrefTag;
import org.htmlparser.tags.BodyTag;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.HeadTag;
import org.htmlparser.tags.Html;
import org.htmlparser.tags.JspTag;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * 
 * <p>
 * Title: 龙驭门户引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author 李雄锋
 * @version 1.0
 */
public class PortletPageParser {
	private File file;
	private String encoding;
	private static PrototypicalNodeFactory factory = null;
	private Parser parser;
	private NodeList nodes;

	public PortletPageParser(String file) throws ParserException {
		this(new File(file));
	}

	public PortletPageParser(File file) throws ParserException {
		this.file = file;
		this.encoding = "UTF-8";
		if (factory == null) {
			factory = new PrototypicalNodeFactory();
			factory.registerTag(new HtmlPortletTag());
			factory.registerTag(new HTMLPreferenceTag());
			factory.registerTag(new HtmlNameTag());
			factory.registerTag(new HtmlValueTag());
			factory.registerTag(new HLinkTag());
		}
		Parser parser = new Parser(file.getPath());
		parser.setEncoding(encoding);
		parser.setNodeFactory(factory);
		// pageContent = parser.getLexer().getPage().getText();
		this.nodes = parser.parse(new NodeFilter() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 7709232841348670288L;

			public boolean accept(Node node) {
				return true;
			};
		});
	}

	public void save() throws IOException {
		if (!this.file.exists()) {
			file.createNewFile();
		}
		String pageContent = this.getRootNodes().toHtml();
		FileUtils.writeStringToFile(this.file, pageContent, this.encoding);
	}

	public NodeList getRootNodes() {
		return this.nodes.extractAllNodesThatMatch(new NodeFilter() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -334041201184143028L;

			public boolean accept(Node node) {
				return node.getParent() == null;
			};
		});

	}

	/**
	 * 获取所有的meta数据
	 * 
	 * @return NodeList
	 * @throws ParserException
	 */
	public NodeList getMetaTags() throws ParserException {
		return this.nodes.extractAllNodesThatMatch(new NodeFilter() {
			private static final long serialVersionUID = 1L;

			public boolean accept(Node node) {
				return node instanceof MetaTag;
			};
		});
	}

	/**
	 * 获取所有的link标签，注意不是A标签
	 * 
	 * @return NodeList
	 * @throws ParserException
	 */
	public NodeList getLinkTags() throws ParserException {
		return this.nodes.extractAllNodesThatMatch(new NodeFilter() {
			private static final long serialVersionUID = 1L;

			public boolean accept(Node node) {
				if (node instanceof Tag) {
					Tag tag = (Tag) node;
					return tag.getTagName().equalsIgnoreCase("link");
				}
				return false;
			};
		});
	}

	/**
	 * 在head里面添加一个link标签
	 * 
	 * @param rel
	 *            String
	 * @param type
	 *            String
	 * @param href
	 *            String
	 * @return HLinkTag
	 */
	public HLinkTag addLinkTag(String rel, String type, String href) {
		HLinkTag tag = new HLinkTag();
		if ((rel != null) && (rel.length() != 0))
			tag.setAttribute("rel", rel);
		if ((type != null) && (type.length() != 0))
			tag.setAttribute("type", type);
		if ((href != null) && (href.length() != 0))
			tag.setAttribute("href", href);
		this.getHeadTag().getChildren().add(tag);
		return tag;
	}

	/**
	 * 
	 * @param name
	 *            String
	 * @param httpEquiv
	 *            String
	 * @param content
	 *            String 如果content为空，则删除这个meta
	 */
	public void setMetaData(final String name, final String httpEquiv,
			String content) throws ParserException {
		NodeList nodes = this.nodes.extractAllNodesThatMatch(new NodeFilter() {
			private static final long serialVersionUID = 1L;

			public boolean accept(Node node) {
				if (node instanceof MetaTag) {
					MetaTag meta = (MetaTag) node;
					String http = meta.getAttribute("http-equiv");
					if ((http != null) && (http.equalsIgnoreCase(httpEquiv)))
						return true;
					String metaName = meta.getAttribute("name");
					if ((metaName != null) && (metaName.equalsIgnoreCase(name)))
						return true;
				}
				return false;
			};
		});
		// if content is null,delete this tag;
		if ((content == null) || (content.length() == 0)) {
			if (nodes.size() == 0)
				return;
			for (int i = 0; i < nodes.size(); i++) {
				this.remove(nodes.elementAt(i));
			}
			return;
		}

		if (nodes.size() == 0) {
			HeadTag head = this.getHeadTag();
			NodeList children = head.getChildren();
			if (children == null) {
				children = new NodeList();
				head.setChildren(children);
			}
			MetaTag meta = new MetaTag();
			if (name != null)
				meta.setAttribute("name", name);
			if (httpEquiv != null)
				meta.setAttribute("http-equiv", httpEquiv);
			if (content != null)
				meta.setAttribute("content", content);
			children.add(meta);
		} else {
			MetaTag meta = (MetaTag) nodes.elementAt(0);
			if (name != null)
				meta.setAttribute("name", name);
			if (httpEquiv != null)
				meta.setAttribute("http-equiv", httpEquiv);
			if (content != null)
				meta.setAttribute("content", content);
		}
	}

	/**
	 * 设置Base标签
	 * 
	 * @param href
	 *            String
	 */
	public void setBaseHref(String href, String target) {
		NodeList nodes = this.nodes.extractAllNodesThatMatch(new NodeFilter() {
			private static final long serialVersionUID = 1L;

			public boolean accept(Node node) {
				return node instanceof BaseHrefTag;
			};
		});
		BaseHrefTag tag;
		if (nodes.size() == 0) {
			tag = new BaseHrefTag();
			this.getHeadTag().getChildren().add(tag);
		} else
			tag = (BaseHrefTag) nodes.elementAt(0);
		if ((href != null) && (href.length() > 0))
			tag.setAttribute("href", href);
		if ((target != null) && (target.length() > 0))
			tag.setAttribute("target", target);

	}

	/**
	 * 删除节点，同时将这个节点从NodeList和父节点中删除
	 * 
	 * @param node
	 *            Node
	 */
	public void remove(Node node) {
		node.getParent().getChildren().remove(node);
		this.nodes.remove(node);
	}

	/**
	 * 删除节点，同时将这个节点从NodeList和父节点中删除
	 * 
	 * @param nodeDefinition
	 *            Node
	 */
	public void remove(NodeList nodes) {
		for (int i = 0; i < nodes.size(); i++)
			this.remove(nodes.elementAt(i));
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 *            String
	 */
	public void setTitle(String title) {
		NodeList nodes = this.nodes.extractAllNodesThatMatch(new NodeFilter() {
			private static final long serialVersionUID = 1L;

			public boolean accept(Node node) {
				return node instanceof TitleTag;
			};
		});
		TitleTag tag;
		if (nodes.size() == 0) {
			tag = new TitleTag();
			this.getHeadTag().getChildren().add(tag);
		} else
			tag = (TitleTag) nodes.elementAt(0);
		TextNode textNode;
		if (tag.getChildCount() > 0) {
			textNode = (TextNode) tag.getChild(0);
			textNode.setText(title);
		} else {
			textNode = new TextNode(title);
			tag.getChildren().add(textNode);
		}
	}

	public String getTitle() {
		NodeList nodes = this.nodes.extractAllNodesThatMatch(new NodeFilter() {
			private static final long serialVersionUID = 1L;

			public boolean accept(Node node) {
				return node instanceof TitleTag;
			};
		});
		if (nodes.size() == 0)
			return null;
		TitleTag tag = (TitleTag) nodes.elementAt(0);
		return tag.getTitle();
	}

	/**
	 * 获取指定id的meta，如果不存在，则创建这标签
	 * 
	 * @param id
	 *            String
	 * @return MetaTag
	 */
	public MetaTag getMetaTag(final String id) {
		NodeList nodes = this.nodes.extractAllNodesThatMatch(new NodeFilter() {
			private static final long serialVersionUID = 1L;

			public boolean accept(Node node) {
				if (node instanceof MetaTag) {
					MetaTag meta = (MetaTag) node;
					String http = meta.getAttribute("http-equiv");
					if ((http != null) && (http.equalsIgnoreCase(id)))
						return true;
					String name = meta.getAttribute("name");
					if ((name != null) && (name.equalsIgnoreCase(id)))
						return true;
				}
				return false;
			};
		});
		if (nodes.size() == 0) {
			HeadTag head = this.getHeadTag();
			NodeList children = head.getChildren();
			MetaTag meta = new MetaTag();
			children.add(meta);
			return meta;

		} else {
			return (MetaTag) nodes.elementAt(0);
		}
	}

	/**
	 * 获取html标签，如果不存在，则创建这个标签
	 * 
	 * @return Html
	 */
	public Html getHtmlTag() {
		NodeList nodes = this.nodes.extractAllNodesThatMatch(new NodeFilter() {
			private static final long serialVersionUID = 1L;

			public boolean accept(Node node) {
				if (node instanceof Html) {
					return true;
				}
				return false;
			}
		});
		if (nodes.size() == 0) {
			Html tag = new Html();
			this.nodes.add(tag);
			return tag;
		}
		return (Html) nodes.elementAt(0);
	}

	/**
	 * 获取head标签，如果不存在，则创建这个标签。
	 * 
	 * @return HeadTag
	 */
	public HeadTag getHeadTag() {
		NodeList nodes = this.nodes.extractAllNodesThatMatch(new NodeFilter() {
			private static final long serialVersionUID = 1L;

			public boolean accept(Node node) {
				if (node instanceof HeadTag) {
					return true;
				}
				return false;
			}
		});
		if (nodes.size() == 0) {
			HeadTag tag = new HeadTag();
			Html html = this.getHtmlTag();
			html.getChildren().add(tag);
			return tag;
		}
		return (HeadTag) nodes.elementAt(0);
	}

	public BodyTag getBodyTag() {
		NodeList nodes = this.nodes.extractAllNodesThatMatch(new NodeFilter() {
			private static final long serialVersionUID = 1L;

			public boolean accept(Node node) {
				if (node instanceof BodyTag) {
					return true;
				}
				return false;
			}
		});
		if (nodes.size() == 0) {
			BodyTag tag = new BodyTag();
			this.getHtmlTag().getChildren().add(tag);
			return tag;
		}
		return (BodyTag) nodes.elementAt(0);

	}

	public HtmlPortletTag getPortletTag(final String portletID)
			throws ParserException {
		NodeList portlets = this.nodes.extractAllNodesThatMatch(new NodeFilter() {
					private static final long serialVersionUID = 1L;

					public boolean accept(Node node) {
						if (node instanceof TagNode) {
							TagNode tagNode = (TagNode) node;
							return (tagNode.getTagName() != null)
									&& tagNode.getTagName().equalsIgnoreCase(
											"fulong:portlet")
									&& (tagNode.getAttribute("id") != null)
									&& tagNode.getAttribute("id")
											.equalsIgnoreCase(portletID);
						} else {
							return false;
						}
					}
				});
		if (portlets.size() == 0)
			return null;
		return (HtmlPortletTag) portlets.elementAt(0);
	}

	/**
	 * 
	 * @param portletID
	 *            String
	 * @param preferences
	 *            PreferenceSet
	 * @throws ParserException
	 * @throws IOException
	 */
	public void updatePreference(final String portletID,
			PreferenceSet preferences) throws ParserException, IOException {
		HtmlPortletTag tag = this.getPortletTag(portletID);
		if (tag == null) {
			throw new IllegalArgumentException(
					"Unable to find portlet with id " + portletID + ".");
		}
		tag.setPreferences(preferences);
	}

	/**
	 * 更新页面模板.其中content仅包含显示（view）状态的页面，仅有body内的内容。整合后的内容是 1.
	 * 使用现有的文件的body标签外的内容。 2. content提供的内容，将view状态的占位符替换成源代码。
	 * 
	 * @param content
	 *            String
	 * @throws IOException
	 */
	public void updateTemplate(String content) throws IOException,
			ParserException {
		NodeList portlets = this.getPortletNodes();
		StringBuffer result = new StringBuffer();
		// 获取body标签之前的内容。
		BodyTag body = this.getBodyTag();
		int end = body.getEndPosition();
		String sourceText = body.getPage().getText();
		result.append(sourceText.substring(0, end));
		// 合并占位符
		PortletMerger merger = new PortletMerger(portlets, content);
		Parser parser2 = Parser.createParser(content, "UTF-8");
		parser2.parse(merger);
		result.append(merger.getResult());
		// 获取body标签以后的内容
		result.append(sourceText
				.substring((body.getEndTag().getStartPosition())));
		// 重置系统变量
		this.parser = Parser.createParser(result.toString(), this.encoding);
		parser.setEncoding(encoding);
		parser.setNodeFactory(factory);
		this.nodes = parser.parse(new NodeFilter() {
			private static final long serialVersionUID = 1L;

			public boolean accept(Node node) {
				return true;
			};
		});
	}

	/**
	 * 更新页面模板.其中content仅包含显示（view）状态的页面，仅有body内的内容。整合后的内容是 1.
	 * 使用现有的文件的body标签外的内容。 2. content提供的内容，将view状态的占位符替换成源代码。
	 * 
	 * @param content
	 *            String
	 * @throws IOException
	 */
	public void updateClips(String content) throws IOException, ParserException {
		NodeList portlets = this.getPortletNodes();
		StringBuffer result = new StringBuffer();
		result.append(this.getJspTags().toHtml());
		// 合并占位符
		PortletMerger merger = new PortletMerger(portlets, content);
		Parser parser2 = Parser.createParser(content, "UTF-8");
		parser2.parse(merger);
		result.append(merger.getResult());
		// 重置系统变量
		this.parser = Parser.createParser(result.toString(), this.encoding);
		parser.setEncoding(encoding);
		parser.setNodeFactory(factory);
		this.nodes = parser.parse(new NodeFilter() {
			private static final long serialVersionUID = 1L;

			public boolean accept(Node node) {
				return true;
			};
		});

	}

	public NodeList getJspTags() {
		return this.nodes.extractAllNodesThatMatch(new NodeFilter() {
			private static final long serialVersionUID = 1L;

			public boolean accept(Node node) {
				return node instanceof JspTag;
			};
		});
	}

	private boolean isPortletTag(TagNode tagNode) {
		return tagNode.getTagName().equalsIgnoreCase("div")
				&& "portletWindow".equalsIgnoreCase(tagNode
						.getAttribute("class"));
	}

	/**
	 * 获得所有的portlet HTML客户端标签
	 * 
	 * @return NodeList
	 */
	public NodeList getPortletHTMLTags() {
		return this.nodes.extractAllNodesThatMatch(new NodeFilter() {
			private static final long serialVersionUID = 1L;

			public boolean accept(Node node) {
				if (node instanceof Div) {
					Div tagNode = (Div) node;
					return isPortletTag(tagNode);
				}
				return false;
			}
		});
	}

	/**
	 * 获取文件编码
	 * 
	 * @return String
	 * @throws BadLocationException
	 * @throws IOException
	 * @throws ParserException
	 */
	public String getJspEncoding() throws BadLocationException, IOException,
			ParserException {
		Parser parser = new Parser(file.getPath());
		JspEncodingFilter filter = new JspEncodingFilter();
		parser.parse(filter);
		return filter.getEncoding();
	}

	/*
	 * public void merge(String fullText) throws ParserException, IOException,
	 * BadLocationException {
	 * 
	 * String encoding = getJspEncoding(); NodeList portlets = getPortlets();
	 * StringBuffer result = new StringBuffer(); result.append(
	 * "<%@page buffer=\"64kb\" contentType=\"text/html; charset=" + encoding +
	 * "\"%>"); result.append("\r\n"); result.append(JSP_PORTLET);
	 * result.append("\r\n"); Parser parser = Parser.createParser(fullText,
	 * encoding); DivMerger merger = new DivMerger(portlets, fullText);
	 * parser.parse(merger); result.append(merger.getResult());
	 * template.write(result.toString(), encoding); }
	 */

	private NodeList getPortletNodes() throws ParserException {
		NodeList portlets = this.nodes.extractAllNodesThatMatch(new NodeFilter() {
					private static final long serialVersionUID = 1L;

					public boolean accept(Node node) {
						return (node instanceof HtmlPortletTag);
					}
				});
		return portlets;
	}

	public static String parserEncoding(String contentType) {
		if (contentType != null) {
			StringTokenizer tk = new StringTokenizer(contentType, ";");
			while (tk.hasMoreTokens()) {
				String str = tk.nextToken().trim();
				if (str.startsWith("charset")) { // NOI18N
					str = str.substring(7).trim();
					if (str.charAt(0) == '=') {
						return str.substring(1).trim();
					}
				}
			}
		}
		return null;
	}
}

class TagNodeFilter implements NodeFilter {
	private static final long serialVersionUID = 2462154002008184849L;
	
	@SuppressWarnings("unchecked")
	private Class clazz;

	@SuppressWarnings("unchecked")
	public TagNodeFilter(Class clazz) {
		this.clazz = clazz;
	}

	public boolean accept(Node node) {
		return clazz.isInstance(node);
	}
}

class EncodingCallback extends HTMLEditorKit.ParserCallback {
	private String encoding = "UTF-8";

	public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos) {
		String value = (String) a.getAttribute(HTML.Attribute.CONTENT);
		if (value != null) {
			StringTokenizer tk = new StringTokenizer(value, ";");
			while (tk.hasMoreTokens()) {
				String str = tk.nextToken().trim();
				if (str.startsWith("charset")) { // NOI18N
					str = str.substring(7).trim();
					if (str.charAt(0) == '=') {
						this.encoding = str.substring(1).trim();
					}
				}
			}
		}
	};

	public String getEncoding() {
		return encoding;
	}
}

class JspEncodingFilter implements NodeFilter {

	private static final long serialVersionUID = -5712736471342781843L;
	
	private String encoding = "UTF-8";

	public boolean accept(Node node) {
		if (node instanceof JspTag) {
			JspTag tag = (JspTag) node;
			String text = tag.getText().toLowerCase();
			if (text.indexOf("charset=") > 0) {
				int index = text.indexOf("charset=") + 8;
				text = text.substring(index);
				index = text.indexOf("\"");
				text = text.substring(0, index);
				this.encoding = text;
			}
		}
		return true;
	}

	public String getEncoding() {
		return this.encoding;
	}

}

class JspEncodingCallback extends HTMLEditorKit.ParserCallback {
	private String encoding = "UTF-8";

	public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos) {
		String value = (String) a.getAttribute(HTML.Attribute.CONTENT);
		if (value != null) {
			this.encoding = PortletPageParser.parserEncoding(value);
		}

	};

	public String getEncoding() {
		return encoding;
	}
}
