package com.fulong.longcon.repository;



/**
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */
/**
 * Object representation of a single JCR path element.
 *
 * @see Path.Element
 */
public class Element {

    /**
     * Qualified name of the path element.
     */
    private final Name name;

    /**
     * Optional index of the path element. Set to zero, if not
     * explicitly specified, otherwise contains the 1-based index.
     */
    private final int index;

    /**
     * Private constructor for creating a path element with the given
     * qualified name and index. Instead of using this constructor directly
     * the factory methods {@link PathFactory#createElement(Name)} and
     * {@link PathFactory#create(Name, int)} should be used.
     *
     * @param name  qualified name
     * @param index index
     */
    public Element(Name name, int index) {
        this.index = index;
        this.name = name;
    }

    /**
     * @see Path.Element#getName()
     */
    public Name getName() {
        return name;
    }

    /**
     * @see Path.Element#getIndex()
     */
    public int getIndex() {
        return index;
    }

    /**
     * @see Path.Element#getNormalizedIndex()
     */
    public int getNormalizedIndex() {
        if (index == Path.INDEX_UNDEFINED) {
            return Path.INDEX_DEFAULT;
        } else {
            return index;
        }
    }

    /**
     * @return always returns false.
     * @see Path.Element#denotesRoot()
     */
    public boolean denotesRoot() {
        return false;
    }

    /**
     * @return always returns false.
     * @see Path.Element#denotesParent()
     */
    public boolean denotesParent() {
        return false;
    }

    /**
     * @return always returns false.
     * @see Path.Element#denotesCurrent()
     */
    public boolean denotesCurrent() {
        return false;
    }

    /**
     * @return always returns true.
     * @see Path.Element#denotesName()
     */
    public boolean denotesName() {
        return true;
    }

    /**
     * @see Path.Element#getString()
     */
    public String getString() {
        return toString();
    }

    /**
     * Returns a string representation of this path element. Note that
     * the path element name is expressed using the <code>{uri}name</code>
     * syntax.
     *
     * @return string representation of the path element
     * @see Object#toString()
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        // name
        sb.append(name.toString());
        // index
        if (index > Path.INDEX_DEFAULT) {
            sb.append('[');
            sb.append(index);
            sb.append(']');
        }
        return sb.toString();
    }

    /**
     * Computes a hash code for this path element.
     *
     * @return hash code
     * @see Object#hashCode()
     */
    public int hashCode() {
        int h = 17;
        h = 37 * h + getNormalizedIndex();
        h = 37 * h + name.hashCode();
        return h;
    }

    /**
     * Check for path element equality. Returns true if the given
     * object is a PathElement and contains the same name and index
     * as this one.
     *
     * @param obj the object to compare with
     * @return <code>true</code> if the path elements are equal
     * @see Object#equals(Object)
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Element) {
            Element other = (Element) obj;
            return name.equals(other.getName())
                && getNormalizedIndex() == other.getNormalizedIndex();
        }
        return false;
    }
}
