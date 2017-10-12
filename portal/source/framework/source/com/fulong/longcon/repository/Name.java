package com.fulong.longcon.repository;

import java.io.Serializable;


/**
 * A <code>Name</code> is a combination of a namespace URI and a local part.
 * Instances of this class are used to internally represent the names of JCR
 * content items and other objects within a content repository.
 * <p/>
 * A <code>Name</code> is immutable once created.
 * <p/>
 * The String representation of a <code>Name</code> object must be in the
 * format "<code>{namespaceURI}localPart</code>".
 * <p/>
 * An implementation of the <code>Name</code> interface must implement the
 * {@link Object#equals(Object)} method such that two Name objects are equal if
 * both the namespace URI and the local part are equal.
 */
public class Name implements Comparable<Name>, Cloneable, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5543294968549357287L;
	// default namespace (empty uri)
    public static final String NS_EMPTY_PREFIX = "";
    public static final String NS_DEFAULT_URI = "";

    // reserved namespace for repository internal node types
    public static final String NS_REP_PREFIX = "rep";
    public static final String NS_REP_URI = "internal";

    // reserved namespace for items defined by built-in node types
    public static final String NS_JCR_PREFIX = "jcr";
    public static final String NS_JCR_URI = "http://www.jcp.org/jcr/1.0";

    // reserved namespace for built-in primary node types
    public static final String NS_NT_PREFIX = "nt";
    public static final String NS_NT_URI = "http://www.jcp.org/jcr/nt/1.0";

    // reserved namespace for built-in mixin node types
    public static final String NS_MIX_PREFIX = "mix";
    public static final String NS_MIX_URI = "http://www.jcp.org/jcr/mix/1.0";

    // reserved namespace used in the system view XML serialization format
    public static final String NS_SV_PREFIX = "sv";
    public static final String NS_SV_URI = "http://www.jcp.org/jcr/sv/1.0";

    // reserved namespaces that must not be redefined and should not be used
    public static final String NS_XML_PREFIX = "xml";
    public static final String NS_XML_URI =
        "http://www.w3.org/XML/1998/namespace";
    public static final String NS_XMLNS_PREFIX = "xmlns";
    public static final String NS_XMLNS_URI = "http://www.w3.org/2000/xmlns/";

    /**
     * Empty array of <code>Name</code>
     */
    public static final Name[] EMPTY_ARRAY = new Name[0];

    /** The memorized hash code of this qualified name. */
    private transient int hash;

    /** The memorized string representation of this qualified name. */
    private transient String string;

    /** The internalized namespace URI of this qualified name. */
    private final String namespaceURI;

    /** The local part of this qualified name. */
    private final String localName;

    public Name(String nameString) {
        if (nameString == null || "".equals(nameString)) {
            throw new IllegalArgumentException("Invalid Name literal.");
        }
        if (nameString.charAt(0) != '{') {
            throw new IllegalArgumentException("Invalid Name literal");
        }
        int i = nameString.indexOf('}');
        if (i == -1) {
            throw new IllegalArgumentException("Invalid Name literal");
        }
        if (i == nameString.length() - 1)
            throw new IllegalArgumentException("Invalid Name literal");
        this.namespaceURI = nameString.substring(1, i);
        this.localName = nameString.substring(i + 1);

    }

    public Name(String namespaceURI, String localName) {
        if (namespaceURI == null) {
            throw new IllegalArgumentException("invalid namespaceURI specified");
        }
        if (localName == null) {
            throw new IllegalArgumentException("invalid localName specified");
        }

        // internalize namespaceURI to improve performance of comparisons.
        this.namespaceURI = namespaceURI.intern();
        // localName is not internalized in order not to risk huge perm
        // space for large repositories
        this.localName = localName;
        hash = 0;
    }

    //-----------------------------------------------------------< Name >---
    /**
     * @see Name#getLocalName()
     */
    public String getLocalName() {
        return localName;
    }

    /**
     * @see Name#getNamespaceURI()
     */
    public String getNamespaceURI() {
        return namespaceURI;
    }

    //---------------------------------------------------------< Object >---
    /**
     * Returns the string representation of this <code>Name</code> in the
     * following format:
     * <p/>
     * <code><b>{</b>namespaceURI<b>}</b>localName</code>
     *
     * @return the string representation of this <code>Name</code>.
     * @see NameFactory#create(String)
     * @see Object#toString()
     */
    public String toString() {
        // Name is immutable, we can store the string representation
        if (string == null) {
            string = '{' + namespaceURI + '}' + localName;
        }
        return string;
    }

    /**
     * Compares two qualified names for equality. Returns <code>true</code>
     * if the given object is a qualified name and has the same namespace URI
     * and local part as this qualified name.
     *
     * @param obj the object to compare this qualified name with
     * @return <code>true</code> if the object is equal to this qualified name,
     *         <code>false</code> otherwise
     * @see Object#equals(Object)
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        // some other Name implementation
        if (obj instanceof Name) {
            Name other = (Name) obj;
            return namespaceURI.equals(other.getNamespaceURI()) &&
                localName.equals(other.getLocalName());
        }
        return false;
    }

    /**
     * Returns the hash code of this qualified name. The hash code is
     * computed from the namespace URI and local part of the qualified
     * name and memorized for better performance.
     *
     * @return hash code
     * @see Object#hashCode()
     */
    public int hashCode() {
        // Name is immutable, we can store the computed hash code value
        int h = hash;
        if (h == 0) {
            h = 17;
            h = 37 * h + namespaceURI.hashCode();
            h = 37 * h + localName.hashCode();
            hash = h;
        }
        return h;
    }

    //------------------------------------------------------< Cloneable >---
    /**
     * Creates a clone of this qualified name.
     * Overriden in order to make <code>clone()</code> public.
     *
     * @return a clone of this instance
     * @throws CloneNotSupportedException never thrown
     * @see Object#clone()
     */
    public Object clone() throws CloneNotSupportedException {
        // Name is immutable, no special handling required
        return super.clone();
    }

    //-----------------------------------------------------< Comparable >---
    /**
     * Compares two qualified names.
     *
     * @param o the object to compare this qualified name with
     * @return comparison result
     * @throws ClassCastException if the given object is not a qualified name
     * @see Comparable#compareTo(Object)
     */
    public int compareTo(Name o) {
        if (this == o) {
            return 0;
        }
        Name other = (Name) o;
        if (namespaceURI.equals(other.getNamespaceURI())) {
            return localName.compareTo(other.getLocalName());
        } else {
            return namespaceURI.compareTo(other.getNamespaceURI());
        }
    }

    //---------------------------------------------------< Serializable >---
    /**
     * Creates a new <code>Name</code> instance using the proper constructor
     * during deserialization in order to make sure that internalized strings
     * are used where appropriate.
     */
    private Object readResolve() {
        return new Name(namespaceURI, localName);
    }
}

