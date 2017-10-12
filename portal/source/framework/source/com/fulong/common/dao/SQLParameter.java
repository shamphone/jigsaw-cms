/**
 * SQLParameter.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2beta3 Aug 01, 2004 (05:59:22 PDT) WSDL2Java emitter.
 */

package com.fulong.common.dao;

public class SQLParameter implements java.io.Serializable {
	
	private static final long serialVersionUID = -7188369995022320684L;
	
	private int type;
	private Object value;

	public SQLParameter() {
	}

	public SQLParameter(int type, Object value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Gets the type value for this SQLParameter.
	 * 
	 * @return type
	 */
	public int getType() {
		return type;
	}

	/**
	 * Sets the type value for this SQLParameter.
	 * 
	 * @param type
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Gets the value value for this SQLParameter.
	 * 
	 * @return value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets the value value for this SQLParameter.
	 * 
	 * @param value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	private java.lang.Object __equalsCalc = null;

	public boolean equals(java.lang.Object obj) {
		if (!(obj instanceof SQLParameter))
			return false;
		SQLParameter other = (SQLParameter) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true
				&& this.type == other.getType()
				&& ((this.value == null && other.getValue() == null) || (this.value != null && this.value
						.equals(other.getValue())));
		__equalsCalc = null;
		return _equals;
	}

	private boolean __hashCodeCalc = false;

	public int hashCode() {
		if (__hashCodeCalc) {
			return 0;
		}
		__hashCodeCalc = true;
		int _hashCode = 1;
		_hashCode += getType();
		if (getValue() != null) {
			_hashCode += getValue().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

}
