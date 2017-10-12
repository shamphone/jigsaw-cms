package com.fulong.webdav.server;



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
public class Range {

    private long start;
    private long end;
    private long length;

    public Range() {
        start = 0;
        end = 0;
        length = 0;

    }

    public Range(long start, long end, long length) {
        this.start = start;
        this.end = end;
        this.length = length;
    }


    public boolean validate() {
        if (end >= length)
            end = length - 1;
        return ((start >= 0) && (end >= 0) && (start <= end)
                && (length > 0));
    }

    public void recycle() {
        start = 0;
        end = 0;
        length = 0;
    }

    public long getEnd() {
        return end;
    }

    public long getLength() {
        return length;
    }

    public long getStart() {
        return start;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public void setStart(long start) {
        this.start = start;
    }

}
