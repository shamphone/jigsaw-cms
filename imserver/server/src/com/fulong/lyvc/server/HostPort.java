/*******************************************************************************
* Product of NIST/ITL Advanced Networking Technologies Division (ANTD).        *
*******************************************************************************/
package com.fulong.lyvc.server;
import java.net.*;



/**
* Holds the hostname:port.
*
*@version  JAIN-SIP-1.1
*
*@author M. Ranganathan <mranga@nist.gov>  <br/>
*
*<a href="{@docRoot}/uncopyright.html">This code is in the public domain.</a>
*
*/
public final class HostPort{

	// host / ipv4/ ipv6/
	/** host field
	 */
	protected Host host;

	/** port field
	 *
	 */
	protected Integer port;

	/** Default constructor
	 */
	public HostPort() {

		host = null;
		port = null; // marker for not set.
	}

	/**
	 * Encode this hostport into its string representation.
	 * Note that this could be different from the string that has
	 * been parsed if something has been edited.
	 * @return String
	 */
	public String encode() {
		StringBuffer retval = new StringBuffer();
		retval.append(host.encode());
		if (port != null)
			retval.append(":").append(port.toString());
		return retval.toString();
	}

	/** returns true if the two objects are equals, false otherwise.
	 * @param other Object to set
	 * @return boolean
	 */
	public boolean equals(Object other) {
		if (!this.getClass().equals(other.getClass())) {
			return false;
		}
		HostPort that = (HostPort) other;
		if ((this.port == null && that.port != null)
			|| (this.port != null && that.port == null))
			return false;
		else if (this.port == that.port && this.host.equals(that.host))
			return true;
		else
			return this.host.equals(that.host) && this.port.equals(that.port);
	}

	/** get the Host field
	 * @return host field
	 */
	public Host getHost() {
		return host;
	}

	/** get the port field
	 * @return int
	 */
	public int getPort() {
		if (port == null) {
			return -1;
		} else {
			return port.intValue();
		}
	}

	/**
	 * Returns boolean value indicating if Header has port
	 * @return boolean value indicating if Header has port
	 */
	public boolean hasPort() {
		return port != null;
	}

	/** remove port.
	 */
	public void removePort() {
		port = null;
	}

	/**
	     * Set the host member
	     * @param h Host to set
	     */
	public void setHost(Host h) {
		host = h;
	}

	/**
	     * Set the port member
	     * @param p int to set
	     */
	public void setPort(int p) {
		// -1 is same as remove port.
		if (p == -1)
			port = null;
		else
			port = new Integer(p);
	}

	/** Return the internet address corresponding to the host.
	 *@throws java.net.UnkownHostException if host name cannot be resolved.
	 *@return the inet address for the host.
	 */
	public InetAddress getInetAddress() throws java.net.UnknownHostException {
		if (host == null)
			return null;
		else
			return host.getInetAddress();
	}

	public Object clone() {
		HostPort retval = new HostPort();
		if (this.host != null)
			retval.host = (Host) this.host.clone();
		if (this.port != null)
			retval.port = new Integer(this.port.intValue());
		return retval;
	}
}
/*
 * $Log: HostPort.java,v $
 * Revision 1.4  2004/01/22 13:26:27  sverker
 * Issue number:
 * Obtained from:
 * Submitted by:  sverker
 * Reviewed by:   mranga
 *
 * Major reformat of code to conform with style guide. Resolved compiler and javadoc warnings. Added CVS tags.
 *
 * CVS: ----------------------------------------------------------------------
 * CVS: Issue number:
 * CVS:   If this change addresses one or more issues,
 * CVS:   then enter the issue number(s) here.
 * CVS: Obtained from:
 * CVS:   If this change has been taken from another system,
 * CVS:   then name the system in this line, otherwise delete it.
 * CVS: Submitted by:
 * CVS:   If this code has been contributed to the project by someone else; i.e.,
 * CVS:   they sent us a patch or a set of diffs, then include their name/email
 * CVS:   address here. If this is your work then delete this line.
 * CVS: Reviewed by:
 * CVS:   If we are doing pre-commit code reviews and someone else has
 * CVS:   reviewed your changes, include their name(s) here.
 * CVS:   If you have not had it reviewed then delete this line.
 *
 */
