package com.fulong.longcon.repository;

/**
 *
 * <p>Title: Coolink协同工作支撑平台</p>
 *
 * <p>Description: Coolink协同工作支撑平台</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 1.0
 */
public class PropertyType {
    /*
     * The supported property types.
     */
    public static final int STRING = 1;
    public static final int BINARY = 2;
    public static final int LONG = 3;
    public static final int DOUBLE = 4;
    public static final int DATE = 5;
    public static final int BOOLEAN = 6;
    public static final int NAME = 7;
    public static final int PATH = 8;
    public static final int REFERENCE = 9;
    public static final int TEXT = 10;
    public static final int FIX = 0;

    /*
     * Undefined type.
     */
    public static final int UNDEFINED = -1;

    /*
     * The names of the supported property types,
     * as used in serialization.
     */
    public static final String TYPENAME_STRING = "String";
    public static final String TYPENAME_BINARY = "Binary";
    public static final String TYPENAME_LONG = "Long";
    public static final String TYPENAME_DOUBLE = "Double";
    public static final String TYPENAME_DATE = "Date";
    public static final String TYPENAME_BOOLEAN = "Boolean";
    public static final String TYPENAME_NAME = "Name";
    public static final String TYPENAME_PATH = "Path";
    public static final String TYPENAME_REFERENCE = "Reference";
    public static final String TYPENAME_TEXT="Text";
    public static final String TYPENAME_FIX="Fix";

    /*
     * String representation of <i>undefined</i> type.
     */
    public static final String TYPENAME_UNDEFINED = "undefined";

    /**
     * Returns the name of the specified <code>type</code>,
     * as used in serialization.
     *
     * @param type the property type
     * @return the name of the specified <code>type</code>
     * @throws IllegalArgumentException if <code>type</code>
     *                                  is not a valid property type.
     */
    public static String nameFromValue(int type) {
        switch (type) {
        case STRING:
            return TYPENAME_STRING;
        case BINARY:
            return TYPENAME_BINARY;
        case BOOLEAN:
            return TYPENAME_BOOLEAN;
        case LONG:
            return TYPENAME_LONG;
        case DOUBLE:
            return TYPENAME_DOUBLE;
        case DATE:
            return TYPENAME_DATE;
        case NAME:
            return TYPENAME_NAME;
        case PATH:
            return TYPENAME_PATH;
        case REFERENCE:
            return TYPENAME_REFERENCE;
        case TEXT:
            return TYPENAME_TEXT;
        case FIX:
            return TYPENAME_FIX;
        case UNDEFINED:
            return TYPENAME_UNDEFINED;
        default:
            throw new IllegalArgumentException("unknown type: " + type);
        }
    }

    /**
     * Returns the numeric constant value of the type with the specified name.
     *
     * @param name the name of the property type
     * @return the numeric constant value
     * @throws IllegalArgumentException if <code>name</code>
     *                                  is not a valid property type name.
     */
    public static int valueFromName(String name) {
        if (name.equals(TYPENAME_STRING)) {
            return STRING;
        } else if (name.equals(TYPENAME_BINARY)) {
            return BINARY;
        } else if (name.equals(TYPENAME_BOOLEAN)) {
            return BOOLEAN;
        } else if (name.equals(TYPENAME_LONG)) {
            return LONG;
        } else if (name.equals(TYPENAME_DOUBLE)) {
            return DOUBLE;
        } else if (name.equals(TYPENAME_DATE)) {
            return DATE;
        } else if (name.equals(TYPENAME_NAME)) {
            return NAME;
        } else if (name.equals(TYPENAME_PATH)) {
            return PATH;
        } else if (name.equals(TYPENAME_REFERENCE)) {
            return REFERENCE;
        } else if (name.equals(TYPENAME_TEXT)) {
            return TEXT;
        } else if (name.equals(TYPENAME_FIX)) {
            return FIX;
        } else if (name.equals(TYPENAME_UNDEFINED)) {
            return UNDEFINED;
        } else {
            throw new IllegalArgumentException("unknown type: " + name);
        }
    }

    /**
     * private constructor to prevent instantiation
     */
    private PropertyType() {
    }

}
