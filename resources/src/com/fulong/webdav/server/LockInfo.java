package com.fulong.webdav.server;

import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import com.fulong.webdav.utils.FastHttpDateFormat;
import com.fulong.webdav.utils.XMLWriter;

/**
 * 
 * 
 * Coolink协同工作框架模型
 * 
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 * 
 * Company: 北京中科辅龙计算机技术股份有限公司
 * 
 * @author lixf
 * 
 * @version 2.0
 * 
 */
public class LockInfo {

	String path = "/";
	String type = "write";
	String scope = "exclusive";
	int depth = 0;
	String owner = "";
	Vector<String> tokens = new Vector<String>();
	long expiresAt = 0;
	Date creationDate = new Date();

	/**
	 * Constructor.
	 */
	public LockInfo() {

	}

	// ----------------------------------------------------- Public Methods

	/**
	 * Get a String representation of this lock token.
	 */
	public String toString() {

		String result = "Type:" + type + "\n";
		result += "Scope:" + scope + "\n";
		result += "Depth:" + depth + "\n";
		result += "Owner:" + owner + "\n";
		result += "Expiration:"
				+ FastHttpDateFormat.formatDate(expiresAt, null) + "\n";
		Enumeration<String> tokensList = tokens.elements();
		while (tokensList.hasMoreElements()) {
			result += "Token:" + tokensList.nextElement() + "\n";
		}
		return result;

	}

	/**
	 * Return true if the lock has expired.
	 */
	public boolean hasExpired() {
		return (System.currentTimeMillis() > expiresAt);
	}

	/**
	 * Return true if the lock is exclusive.
	 */
	public boolean isExclusive() {
		return (scope.equals("exclusive"));
	}

	public String getTimeoutString() {
		long timeout = (expiresAt - System.currentTimeMillis()) / 1000;
		return "Second-" + timeout;
	}

	public boolean checkIfHeader(String ifHeader) {
		Enumeration<String> tokenList = null;

		// At least one of the tokens of the locks must have been given

		tokenList = tokens.elements();
		while (tokenList.hasMoreElements()) {
			String token = (String) tokenList.nextElement();
			if (ifHeader.indexOf(token) != -1) {
				return true;
			}
		}
		return false;

	}

	/**
	 * Get an XML representation of this lock token. This method will append an
	 * XML fragment to the given XML writer.
	 */
	public void toXML(XMLWriter generatedXML) {

		generatedXML.writeElement(null, "activelock", XMLWriter.OPENING);

		generatedXML.writeElement(null, "locktype", XMLWriter.OPENING);
		generatedXML.writeElement(null, type, XMLWriter.NO_CONTENT);
		generatedXML.writeElement(null, "locktype", XMLWriter.CLOSING);

		generatedXML.writeElement(null, "lockscope", XMLWriter.OPENING);
		generatedXML.writeElement(null, scope, XMLWriter.NO_CONTENT);
		generatedXML.writeElement(null, "lockscope", XMLWriter.CLOSING);

		generatedXML.writeElement(null, "depth", XMLWriter.OPENING);
		if (depth == WebDavServlet.INFINITY) {
			generatedXML.writeText("Infinity");
		} else {
			generatedXML.writeText("0");
		}
		generatedXML.writeElement(null, "depth", XMLWriter.CLOSING);

		generatedXML.writeElement(null, "owner", XMLWriter.OPENING);
		generatedXML.writeText(owner);
		generatedXML.writeElement(null, "owner", XMLWriter.CLOSING);

		generatedXML.writeElement(null, "timeout", XMLWriter.OPENING);
		long timeout = (expiresAt - System.currentTimeMillis()) / 1000;
		generatedXML.writeText("Second-" + timeout);
		generatedXML.writeElement(null, "timeout", XMLWriter.CLOSING);

		generatedXML.writeElement(null, "locktoken", XMLWriter.OPENING);
		Enumeration<String> tokensList = tokens.elements();
		while (tokensList.hasMoreElements()) {
			generatedXML.writeElement(null, "href", XMLWriter.OPENING);
			generatedXML.writeText("opaquelocktoken:"
					+ tokensList.nextElement());
			generatedXML.writeElement(null, "href", XMLWriter.CLOSING);
		}
		generatedXML.writeElement(null, "locktoken", XMLWriter.CLOSING);

		generatedXML.writeElement(null, "activelock", XMLWriter.CLOSING);

	}

	public Date getCreationDate() {
		return creationDate;
	}

	public int getDepth() {
		return depth;
	}

	public long getExpiresAt() {
		return expiresAt;
	}

	public String getOwner() {
		return owner;
	}

	public String getPath() {
		return path;
	}

	public String getScope() {
		return scope;
	}

	public String getType() {
		return type;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public void setExpiresAt(long expiresAt) {
		this.expiresAt = expiresAt;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public void addToken(String token) {
		this.tokens.add(token);
	}

	public void setType(String type) {
		this.type = type;
	}

}
