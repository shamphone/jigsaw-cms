package com.fulong.longcon.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


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
public class Path implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5013485088525389527L;

	/**
     * Constant representing an undefined index value
     */
    public static final int INDEX_UNDEFINED = 0;

    /**
     * Constant representing the default (initial) index value.
     */
    public static final int INDEX_DEFAULT = 1;

    /**
     * Constant defining the depth of the root path
     */
    public static final int ROOT_DEPTH = 0;

    /**
     * Delimiter used in order to concatenate the Path.Element objects
     * upon {@link Path#getString()}.
     */
    public static final char DELIMITER = '\t';

    public static final String CURRENT_LITERAL = ".";
    public static final String PARENT_LITERAL = "..";

    public final static Name CURRENT_NAME = new Name(Name.NS_DEFAULT_URI,
        CURRENT_LITERAL);
    public final static Name PARENT_NAME = new Name(Name.
        NS_DEFAULT_URI, PARENT_LITERAL);
    public final static Name ROOT_NAME = new Name(Name.
                                                  NS_DEFAULT_URI, "");

    public static final Element CURRENT_ELEMENT = new SpecialElement(
        CURRENT_NAME);
    public static final Element PARENT_ELEMENT = new SpecialElement(
        PARENT_NAME);
    public static final Element ROOT_ELEMENT = new SpecialElement(
        ROOT_NAME);

    /**
     * the root path
     */
    public static final Path ROOT = new Path(new Element[] {
                                             ROOT_ELEMENT});

    /**
     * flag indicating if the current path has leading parent '..' elements
     */
    private boolean leadingParent = true;

    /**
     * the elements of this path
     */
    private List<Element> queue;

    /**
     * flag indicating if this path is normalized
     */
    private boolean normalized = true;

    /**
     * the cached 'toString' of this path
     */
    private String string;
    /**
     * 是否绝对路径
     */
    private boolean absolute = false;

    /**
     *
     * @param parent Path
     * @param relPath Path
     * @param normalize boolean
     * @throws IllegalArgumentException
     * @throws RepositoryException
     */
    public Path(Path parent, Path relPath) throws
        IllegalArgumentException, RepositoryException {
        if (relPath.isAbsolute()) {
            throw new IllegalArgumentException("relPath is not a relative path");
        }
        List<Element> queue = new ArrayList<Element> ();
        Collections.addAll(queue, parent.getElements());
        Collections.addAll(queue, relPath.getElements());
        this.init(queue);
    }
    /**
     *
     * @param parent Path
     * @param relPath Path
     * @param normalize boolean
     * @throws IllegalArgumentException
     * @throws RepositoryException
     */
    public Path(String parent, String relPath) throws
        IllegalArgumentException, RepositoryException {
        this(parent+Path.DELIMITER+relPath);
    }

    /**
     *
     * @param parent Path
     * @param relPath Path
     * @param normalize boolean
     * @throws IllegalArgumentException
     * @throws RepositoryException
     */
    public Path(String parent, String relPath, int index) throws
        IllegalArgumentException, RepositoryException {
        this(parent+Path.DELIMITER+relPath+"["+index+"]");
    }

    /**
     * @see PathFactory#create(String)
     */
    public Path(String pathString) throws IllegalArgumentException {
        if (pathString == null || "".equals(pathString)) {
            throw new IllegalArgumentException("Invalid Path literal");
        }
        // split into path elements
        this.init(this.parse(pathString));
    }

    /**
     * @see PathFactory#create(Path, Name, boolean)
     */
    public Path(Path parent, Name name) {
        List<Element> queue = new ArrayList<Element>();
        Collections.addAll(queue, parent.getElements());
        queue.add(createElement(name));
        this.init(queue);
    }

    /**
     * @see PathFactory#create(Path, Name, int, boolean)
     */
    public Path(Path parent, Name name, int index) throws
        IllegalArgumentException, RepositoryException {
        List<Element> queue = new ArrayList<Element>();
        Collections.addAll(this.queue, parent.getElements());
        queue.add(createElement(name, index));
        this.init(queue);
    }

    /**
     *
     * @param elements Collection
     */
    public Path(List<Element> elements) {
        this.init(elements);
    }

    /**
     *
     * @param elements Collection
     */
    public Path(Element[] elements) {
        if (elements == null || elements.length == 0) {
            throw new IllegalArgumentException(
                "Cannot build path from null or 0 elements.");
        }
        this.init(elements);
    }


    private List<Element> parse(String pathString){
        int lastPos = 0;
        int pos = pathString.indexOf(Path.DELIMITER);
        List<Element> queue = new ArrayList<Element>();
        while (lastPos >= 0) {
            Element elem;
            if (pos >= 0) {
                elem = createElement(pathString.substring(lastPos, pos));
                lastPos = pos + 1;
                pos = pathString.indexOf(Path.DELIMITER, lastPos);
            } else {
                elem = createElement(pathString.substring(lastPos));
                lastPos = -1;
            }
            queue.add(elem);
        }
        return queue;

    }

    /**
     *
     * @param elements Element[]
     */
    private void init(Element[] elements) {
        this.queue = new ArrayList<Element>();
        Collections.addAll(this.queue, elements);
        for (int i = 0; i < this.queue.size(); i++) {
            Element elem = this.queue.get(i);
            this.leadingParent &= elem.denotesParent();
            this.normalized &= !elem.denotesCurrent() &&
                (leadingParent || !elem.denotesParent());
        }
        this.absolute = this.queue.get(0).denotesRoot();
    }

    /**
     *
     * @param elements List
     */
    private void init(List<Element> elements) {
        this.queue = elements;
        for (int i = 0; i < this.queue.size(); i++) {
            Element elem = this.queue.get(i);
            this.leadingParent &= elem.denotesParent();
            this.normalized &= !elem.denotesCurrent() &&
                (leadingParent || !elem.denotesParent());
        }
        this.absolute = this.queue.get(0).denotesRoot();
    }

    /**
     * @see Path#denotesRoot()
     */
    public boolean denotesRoot() {
        return this.absolute && this.queue.size() == 1;
    }

    /**
     * @see Path#isAbsolute()
     */
    public boolean isAbsolute() {
        return this.absolute;
    }

    /**
     * @see Path#isCanonical()
     */
    public boolean isCanonical() {
        return this.isAbsolute() && this.isNormalized();
    }

    /**
     * @see Path#isNormalized()
     */
    public boolean isNormalized() {
        return normalized;

    }

    /**
     * @see Path#getNormalizedPath()
     */
    public Path getNormalizedPath() throws RepositoryException {
        if (isNormalized()) {
            return this;
        }
        LinkedList<Element> queue = new LinkedList<Element>();
        Element last = PARENT_ELEMENT;
        for (Iterator<Element> iterator = this.queue.iterator();
             iterator.hasNext(); ) {
            Element elem = iterator.next();
            if (elem.denotesParent() && !last.denotesParent()) {
                if (last.denotesRoot()) {
                    // the first element is the root element;
                    // ".." would refer to the parent of root
                    throw new RepositoryException(
                        "Path can not be canonicalized: unresolvable '..' element");
                }
                queue.removeLast();
                if (queue.isEmpty()) {
                    last = PARENT_ELEMENT;
                } else {
                    last = (Element) queue.getLast();
                }
            } else if (!elem.denotesCurrent()) {
                last = elem;
                queue.add(last);
            }
        }
        if (queue.isEmpty()) {
            throw new RepositoryException(
                "Path can not be normalized: would result in an empty path.");
        }
        return new Path( (Element[]) queue.toArray(new Element[
            queue.size()]));
    }

    /**
     * @see Path#getCanonicalPath()
     */
    public Path getCanonicalPath() throws RepositoryException {
        if (isCanonical()) {
            return this;
        }
        if (!isAbsolute()) {
            throw new RepositoryException(
                "Only an absolute path can be canonicalized.");
        }
        return getNormalizedPath();
    }

    /**
     * @see Path#computeRelativePath(Path)
     */
    public Path computeRelativePath(Path other) throws RepositoryException {
        if (other == null) {
            throw new IllegalArgumentException("null argument");
        }

        // make sure both paths are absolute
        if (!isAbsolute() || !other.isAbsolute()) {
            throw new RepositoryException(
                "Cannot compute relative path from relative paths");
        }

        // make sure we're comparing canonical paths
        Path p0 = getCanonicalPath();
        Path p1 = other.getCanonicalPath();

        if (p0.equals(p1)) {
            // both paths are equal, the relative path is therefore '.'
            return new Path(new Element[] {CURRENT_ELEMENT});
        }

        // determine length of common path fragment
        int lengthCommon = 0;
        Element[] elems0 = p0.getElements();
        Element[] elems1 = p1.getElements();
        for (int i = 0; i < elems0.length && i < elems1.length; i++) {
            if (!elems0[i].equals(elems1[i])) {
                break;
            }
            lengthCommon++;
        }
        List<Element> l = new ArrayList<Element>();
        if (lengthCommon < elems0.length) {
            /**
             * the common path fragment is an ancestor of this path;
             * this has to be accounted for by prepending '..' elements
             * to the relative path
             */
            int tmp = elems0.length - lengthCommon;
            while (tmp-- > 0) {
                l.add(0, PARENT_ELEMENT);
            }
        }
        // add remainder of other path
        for (int i = lengthCommon; i < elems1.length; i++) {
            l.add(elems1[i]);
        }
        return new Path(l);
    }

    /**
     * @see Path#getAncestor(int)
     */
    public Path getAncestor(int degree) throws IllegalArgumentException,
        PathNotFoundException {
        if (degree < 0) {
            throw new IllegalArgumentException("degree must be >= 0");
        } else if (degree == 0) {
            return this;
        }
        int length = this.queue.size() - degree;
        if (length < 1) {
            throw new PathNotFoundException(
                "no such ancestor path of degree " + degree);
        }
        return new Path(this.queue.subList(0, length));
    }

    /**
     * @see Path#getAncestorCount()
     */
    public int getAncestorCount() {
        return getDepth() - 1;
    }

    /**
     * @see Path#getLength()
     */
    public int getLength() {
        return this.queue.size();
    }

    /**
     * @see Path#getDepth()
     */
    public int getDepth() {
        int depth = ROOT_DEPTH;
        for (int i = 0; i < queue.size(); i++) {
            if (queue.get(i).denotesParent()) {
                depth--;
            } else if (!queue.get(i).denotesCurrent()) {
                depth++;
            }
        }
        return depth;
    }

    /**
     * @see Path#isAncestorOf(Path)
     */
    public boolean isAncestorOf(Path other) throws IllegalArgumentException,
        RepositoryException {
        if (other == null) {
            throw new IllegalArgumentException("null argument");
        }
        // make sure both paths are either absolute or relative
        if (isAbsolute() != other.isAbsolute()) {
            throw new IllegalArgumentException(
                "Cannot compare a relative path with an absolute path");
        }
        // make sure we're comparing normalized paths
        Path p0 = getNormalizedPath();
        Path p1 = other.getNormalizedPath();

        if (p0.equals(p1)) {
            return false;
        }
        // calculate depth of paths (might be negative)
        if (p0.getDepth() >= p1.getDepth()) {
            return false;
        }
        Element[] elems0 = p0.getElements();
        Element[] elems1 = p1.getElements();
        for (int i = 0; i < elems0.length; i++) {
            if (!elems0[i].equals(elems1[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * @see Path#isDescendantOf(Path)
     */
    public boolean isDescendantOf(Path other) throws
        IllegalArgumentException, RepositoryException {
        if (other == null) {
            throw new IllegalArgumentException("Null argument");
        }
        return other.isAncestorOf(this);
    }

    /**
     * @see Path#subPath(int, int)
     */
    public Path subPath(int from, int to) throws IllegalArgumentException,
        RepositoryException {
        if (from < 0 || to >= this.queue.size() || from >= to) {
            throw new IllegalArgumentException();
        }
        if (!isNormalized()) {
            throw new RepositoryException(
                "Cannot extract sub-Path from a non-normalized Path.");
        }
        return new Path(this.queue.subList(from, to));
    }

    /**
     * @see Path#getNameElement()
     */
    public Element getNameElement() {
        return this.queue.get(this.queue.size() - 1);
    }

    /**
     * @see Path#getString()
     */
    public String getString() {
        return toString();
    }

    /**
     * @see Path#getElements()
     */
    public Element[] getElements() {
        return (Element[])this.queue.toArray(new Element[this.queue.size()]);
    }

    //---------------------------------------------------------< Object >---
    /**
     * Returns the internal string representation of this <code>Path</code>.
     * <p/>
     * Note that the returned string is not a valid JCR path, i.e. the
     * namespace URI's of the individual path elements are not replaced with
     * their mapped prefixes.
     *
     * @return the internal string representation of this <code>Path</code>.
     */
    public String toString() {
        // Path is immutable, we can store the string representation
        if (string == null) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < this.queue.size(); i++) {
                if (i > 0) {
                    sb.append(Path.DELIMITER);
                }
                Element element = this.queue.get(i);
                String elem = element.toString();
                sb.append(elem);
            }
            string = sb.toString();
        }
        return string;
    }

    /**
     * Returns a hash code value for this path.
     *
     * @return a hash code value for this path.
     * @see Object#hashCode()
     */
    public int hashCode() {
        // Path is immutable, we can store the computed hash code value
        int h = 17;
        for (int i = 0; i < this.queue.size(); i++) {
            h = 37 * h + this.queue.get(i).hashCode();
        }
        return h;
    }

    /**
     * Compares the specified object with this path for equality.
     *
     * @param obj the object to be compared for equality with this path.
     * @return <tt>true</tt> if the specified object is equal to this path.
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Path) {
            Path other = (Path) obj;
            return this.queue.equals(other.getElements());
        }
        return false;
    }

    /**
     * Adds the {@link org.apache.jackrabbit.spi.PathFactory#getRootElement()}.

         public void addRoot() {
        addFirst(getRootElement());
         }
     */
    /**
     * Adds the given elemenets
     *
     * @param elements
     */
    public void addAll(Element[] elements) {
        for (int i = 0; i < elements.length; i++) {
            addLast(elements[i]);
        }
    }

    /**
     * Inserts the element at the beginning of the path to be built.
     *
     * @param elem

         public void addFirst(Element elem) {
        if (this.elements.isEmpty()) {
            isNormalized &= !elem.denotesCurrent();
            leadingParent = elem.denotesParent();
        } else {
            isNormalized &= !elem.denotesCurrent() &&
                (!leadingParent || elem.denotesParent());
            leadingParent |= elem.denotesParent();
        }
        queue.addFirst(elem);
         }
     */
    /**
     * Inserts the element at the beginning of the path to be built.
     *
     * @param name

         public void addFirst(Name name) {
        addFirst(createElement(name));
         }
     */
    /**
     * Inserts the element at the beginning of the path to be built.
     *
     * @param name
     * @param index

         public void addFirst(Name name, int index) {
        addFirst(createElement(name, index));
         }
     */
    /**
     * Inserts the element at the end of the path to be built.
     *
     * @param elem
     */
    public void addLast(Element elem) {
        queue.add(elem);
        leadingParent &= elem.denotesParent();
        this.normalized &= !elem.denotesCurrent() &&
            (leadingParent || !elem.denotesParent());
    }

    /**
     * Inserts the element at the end of the path to be built.
     *
     * @param name
     */
    public void addLast(Name name) {
        addLast(createElement(name));
    }

    /**
     * Inserts the element at the end of the path to be built.
     *
     * @param name
     * @param index
     */
    public void addLast(Name name, int index) {
        addLast(createElement(name, index));
    }

    /**
     * @see PathFactory#createElement(Name)
     */
    public Element createElement(Name name) throws
        IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException("name must not be null");
        } else if (name.equals(PARENT_NAME)) {
            return PARENT_ELEMENT;
        } else if (name.equals(CURRENT_NAME)) {
            return CURRENT_ELEMENT;
        } else if (name.equals(ROOT_NAME)) {
            return ROOT_ELEMENT;
        } else {
            return new Element(name, Path.INDEX_UNDEFINED);
        }
    }

    /**
     * @see PathFactory#createElement(Name, int)
     */
    public Element createElement(Name name, int index) throws
        IllegalArgumentException {
        if (index < Path.INDEX_UNDEFINED) {
            throw new IllegalArgumentException("The index may not be negative.");
        } else if (name == null) {
            throw new IllegalArgumentException("The name must not be null");
        } else if (name.equals(PARENT_NAME)
                   || name.equals(CURRENT_NAME)
                   || name.equals(ROOT_NAME)) {
            throw new IllegalArgumentException(
                "Special path elements (root, '.' and '..') can not have an explicit index.");
        } else {
            return new Element(name, index);
        }
    }

    /**
     * Create an element from the element string
     */
    private Element createElement(String elementString) {
        if (elementString == null) {
            throw new IllegalArgumentException("null PathElement literal");
        }
        if (elementString.equals(ROOT_NAME.toString())) {
            return ROOT_ELEMENT;
        } else if (elementString.equals(CURRENT_LITERAL)) {
            return CURRENT_ELEMENT;
        } else if (elementString.equals(PARENT_LITERAL)) {
            return PARENT_ELEMENT;
        }

        int pos = elementString.indexOf('[');
        if (pos == -1) {
            Name name = new Name(elementString);
            return new Element(name, Path.INDEX_UNDEFINED);
        }
        Name name = new Name(elementString.substring(0, pos));
        int pos1 = elementString.indexOf(']');
        if (pos1 == -1) {
            throw new IllegalArgumentException("invalid PathElement literal: " +
                                               elementString + " (missing ']')");
        }
        try {
            int index = Integer.valueOf(elementString.substring(pos + 1, pos1)).
                intValue();
            if (index < 1) {
                throw new IllegalArgumentException(
                    "invalid PathElement literal: " + elementString +
                    " (index is 1-based)");
            }
            return new Element(name, index);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("invalid PathElement literal: " +
                                               elementString + " (" +
                                               e.getMessage() + ")");
        }
    }

    /**
     * @see PathFactory#getCurrentElement()
     */
    public Element getCurrentElement() {
        return CURRENT_ELEMENT;
    }

    /**
     * @see PathFactory#getParentElement()
     */
    public Element getParentElement() {
        return PARENT_ELEMENT;
    }

    /**
     * @see PathFactory#getRootElement()
     */
    public Element getRootElement() {
        return ROOT_ELEMENT;
    }

    /**
     * @see PathFactory#getRootPath()
     */
    public Path getRootPath() {
        return ROOT;
    }

}
