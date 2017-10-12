/**
 * 
 */
package com.fulong.lyvc.message;

/**
 * BaseMessage
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 * 最后修改时间：2010-9-10
 */

import java.io.StringWriter;
import java.lang.reflect.Field;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.fulong.lyvc.TCPMessage;

public abstract class BaseMessage extends TCPMessage {
	// Serialization
	public String toXML()  {
		try {
			return this.document2String(buildDocument());
		} catch (ParserConfigurationException e) {
			throw new MessageFormatException(e);
		} catch (TransformerException e) {
			throw new MessageFormatException(e);
		}
	}

	protected Document buildDocument() throws ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder= dbf.newDocumentBuilder();

		Document document = builder.newDocument();
		Element root = document.createElement("lyvcmessage");
		document.appendChild(root);
		root.appendChild(this.createPropertyNode(document, "id", this.id));
		root.appendChild(this.createPropertyNode(document, "_senderid",
				this.getSenderId()));
		root.appendChild(this.createPropertyNode(document, "_conferenceid",
				this.getConferenceId()));
		Field[] fields = this.getClass().getFields();
		for (int i = 0; i < fields.length; i++) {
			String name = fields[i].getName();
			if (!name.startsWith("_") && !name.equals("id")) {
				Object value;
				try {
					value = fields[i].get(this);
					root.appendChild(this.createPropertyNode(document, name,
							value.toString()));
				} catch (Exception e) {
					// just ignore this exception;
				}
			}
		}
		
		return document;
	}

	protected String document2String(Document doc) throws TransformerException {
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);
			return writer.toString();
	}

	private Element createPropertyNode(Document document, String name,
			String value) {
		Element id = document.createElement(name);
		id.appendChild(document.createTextNode(value));
		return id;
	}

	// Deserialization
	public void fromXML(String str) throws MessageFormatException {

	}

	// Helper function for deserialization, Find string between tag
	public static String getStringBetweenTag(String xmlString, // String to be
																// parsed
			String tagName) // Tag name
			throws MessageFormatException {

		String beginTagName = "<" + tagName + ">";
		String endTagName = "</" + tagName + ">";

		int begin = xmlString.indexOf(beginTagName);
		if (begin == -1) {
			throw new MessageFormatException(tagName + " not Found!");
		}
		int end = xmlString.indexOf(endTagName, begin);
		if (end == -1) {
			throw new MessageFormatException(tagName + " not Found!");
		}

		int contentBegin = begin + beginTagName.length();
		return xmlString.substring(contentBegin, end);
	}

	private static final String amp = "&";
	private static final String ampEscape = "&amp;";

	private static final String gt = ">";
	private static final String gtEscape = "&gt;";

	private static final String lt = "<";
	private static final String ltEscape = "&lt;";

	public static String encodeHtmlEscape(String content) {
		String htmlString;
		htmlString = content.replaceAll(amp, ampEscape);
		htmlString = htmlString.replaceAll(gt, gtEscape);
		htmlString = htmlString.replaceAll(lt, ltEscape);
		return htmlString;
		/*
		try {
			return URLEncoder.encode(htmlString,"");
		} catch (UnsupportedEncodingException e) {
			return htmlString;
		}*/
	}

	public static String decodeHtmlEscape(String htmlString) {
		String content;
		content = htmlString.replaceAll(ampEscape, amp);
		content = content.replaceAll(gtEscape, gt);
		content = content.replaceAll(ltEscape, lt);
		return content;
	}

}
