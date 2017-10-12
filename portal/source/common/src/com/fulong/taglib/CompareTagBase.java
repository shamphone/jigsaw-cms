package com.fulong.taglib;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.MessageResources;

import com.fulong.longcon.repository.Node;

/**
 * Abstract base class for comparison tags. Concrete subclasses need only define
 * values for desired1 and desired2.
 * 
 * @version $Rev: 54929 $ $Date: 2004-10-16 17:38:42 +0100 (Sat, 16 Oct 2004) $
 */

public abstract class CompareTagBase extends ConditionalTagBase {

	// ----------------------------------------------------- Instance Variables

	private static final long serialVersionUID = -3676957731882167854L;

	/**
	 * We will do a double/float comparison.
	 */
	protected static final int DOUBLE_COMPARE = 0;

	/**
	 * We will do a long/int comparison.
	 */
	protected static final int LONG_COMPARE = 1;

	/**
	 * We will do a String comparison.
	 */
	protected static final int STRING_COMPARE = 2;

	/**
	 * The message resources for this package.
	 */
	protected static MessageResources messages = MessageResources
			.getMessageResources("org.apache.struts.taglib.logic.LocalStrings");

	// ------------------------------------------------------------ Properties

	/**
	 * The value to which the variable specified by other attributes of this tag
	 * will be compared.
	 */
	public String value = null;

	public String getValue() {
		return (this.value);
	}

	public void setValue(String value) {
		this.value = value;
	}

	// --------------------------------------------------------- Public Methods

	/**
	 * Release all allocated resources.
	 */
	public void release() {

		super.release();
		value = null;

	}

	// ------------------------------------------------------ Protected Methods

	/**
	 * Evaluate the condition that is being tested by this particular tag, and
	 * return <code>true</code> if the nested body content of this tag should be
	 * evaluated, or <code>false</code> if it should be skipped. This method
	 * must be implemented by concrete subclasses.
	 * 
	 * @exception JspException
	 *                if a JSP exception occurs
	 */
	protected abstract boolean condition() throws JspException;

	/**
	 * Evaluate the condition that is being tested by this particular tag, and
	 * return <code>true</code> if the nested body content of this tag should be
	 * evaluated, or <code>false</code> if it should be skipped. This method
	 * must be implemented by concrete subclasses.
	 * 
	 * @param desired1
	 *            First desired value for a true result (-1, 0, +1)
	 * @param desired2
	 *            Second desired value for a true result (-1, 0, +1)
	 * 
	 * @exception JspException
	 *                if a JSP exception occurs
	 */
	protected boolean condition(int desired1, int desired2) throws JspException {

		// Acquire the value and determine the test type
		int type = -1;
		double doubleValue = 0.0;
		long longValue = 0;
		if ((type < 0) && (value.length() > 0)) {
			try {
				doubleValue = Double.parseDouble(value);
				type = DOUBLE_COMPARE;
			} catch (NumberFormatException e) {
				;
			}
		}
		if ((type < 0) && (value.length() > 0)) {
			try {
				longValue = Long.parseLong(value);
				type = LONG_COMPARE;
			} catch (NumberFormatException e) {
				;
			}
		}
		if (type < 0) {
			type = STRING_COMPARE;
		}

		// Acquire the unconverted variable value
		Object variable = TagUtils.getInstance().lookup(pageContext, name,
				property, scope);
		variable = ((Node) variable).getProperty(propertyName).getString();
		if (variable == null) {
			variable = ""; // Coerce null to a zero-length String
		}

		// Perform the appropriate comparison
		int result = 0;
		if (type == DOUBLE_COMPARE) {
			try {
				double doubleVariable = Double.parseDouble(variable.toString());
				if (doubleVariable < doubleValue) {
					result = -1;
				} else if (doubleVariable > doubleValue) {
					result = +1;
				}
			} catch (NumberFormatException e) {
				result = variable.toString().compareTo(value);
			}
		} else if (type == LONG_COMPARE) {
			try {
				long longVariable = Long.parseLong(variable.toString());
				if (longVariable < longValue) {
					result = -1;
				} else if (longVariable > longValue) {
					result = +1;
				}
			} catch (NumberFormatException e) {
				result = variable.toString().compareTo(value);
			}
		} else {
			result = variable.toString().compareTo(value);
		}

		// Normalize the result
		if (result < 0) {
			result = -1;
		} else if (result > 0) {
			result = +1;
		}

		// Return true if the result matches either desired value
		return ((result == desired1) || (result == desired2));

	}

}
