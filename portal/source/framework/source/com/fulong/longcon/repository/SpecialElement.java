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
 * Object representation of a special JCR path element notably the root, the
 * current and the parent element.
 */
public final class SpecialElement extends Element {

    private final static int ROOT = 1;
    private final static int CURRENT = 2;
    private final static int PARENT = 4;

    private final int type;

    public SpecialElement(Name name) {
        super(name, Path.INDEX_UNDEFINED);
        if (Path.ROOT_NAME.equals(name)) {
            type = ROOT;
        } else if (Path.CURRENT_NAME.equals(name)) {
            type = CURRENT;
        } else if (Path.PARENT_NAME.equals(name)) {
            type = PARENT;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @return true if this is the {@link #ROOT root element}.
     * @see Path.Element#denotesRoot()
     */
    public boolean denotesRoot() {
        return type == ROOT;
    }

    /**
     * @return true if this is the {@link #PARENT parent element}.
     * @see Path.Element#denotesParent()
     */
    public boolean denotesParent() {
        return type == PARENT;
    }

    /**
     * @return true if this is the {@link #CURRENT current element}.
     * @see Path.Element#denotesCurrent()
     */
    public boolean denotesCurrent() {
        return type == CURRENT;
    }

    /**
     * @return Always returns false.
     * @see Path.Element#denotesParent()
     */
    public boolean denotesName() {
        return false;
    }
}
