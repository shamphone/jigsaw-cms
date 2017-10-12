package com.fulong.lyvc;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;

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

import com.fulong.lyvc.message.MessageFormatException;

/**
 * 
 * TCPMessage
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-14
 */
public abstract class TCPMessage {
	// ������ID
	private String _senderId;
	// ����ID, �����ڶ����֮�������Ϣ
	private String _conferenceId;
	protected String id;
	
	// Constructor
	public TCPMessage() {
		setSenderId("0");
		setConferenceId("0");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @param _conferenceId the _conferenceId to set
	 */
	public void setConferenceId(String _conferenceId) {
		this._conferenceId = _conferenceId;
	}

	/**
	 * @return the _conferenceId
	 */
	public String getConferenceId() {
		return _conferenceId;
	}
	
	public String toXML()  {
		try {
			return this.document2String(buildDocument());
		} catch (Exception e) {
			throw new MessageFormatException(e);
		}
	}

	protected Document buildDocument() throws ParserConfigurationException, IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder= dbf.newDocumentBuilder();

		Document document = builder.newDocument();
		Element root = document.createElement("lyvcmessage");
		document.appendChild(root);
		root.appendChild(this.createPropertyNode(document, "id", this.id));
		root.appendChild(this.createPropertyNode(document, "_senderid", this.getSenderId()));
		root.appendChild(this.createPropertyNode(document, "_conferenceid", this.getConferenceId()));
		
		PropertyDescriptor[] fields = Introspector.getBeanInfo(this.getClass()).getPropertyDescriptors();
		for (int i = 0; i < fields.length; i++) {
			String name = fields[i].getName();
			if (!name.startsWith("_") && !name.equals("id")) {
				Object value = fields[i].getReadMethod().invoke(this);
				
				if(value == null)
					value = "";
				
				root.appendChild(this.createPropertyNode(document, name, value.toString()));
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

	private Element createPropertyNode(Document document, String name, String value) {
		Element id = document.createElement(name);
		id.appendChild(document.createTextNode(value));
		return id;
	}

	public void setSenderId(String _senderId) {
		this._senderId = _senderId;
	}

	public String getSenderId() {
		return _senderId;
	}
}
