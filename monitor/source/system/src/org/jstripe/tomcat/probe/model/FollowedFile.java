/*
 * Licensed under the GPL License. You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://probe.jstripe.com/d/license.shtml
 *
 *  THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 *  IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 *  WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */

package org.jstripe.tomcat.probe.model;

import java.util.List;
import java.io.Serializable;
import java.sql.Timestamp;

public class FollowedFile implements Serializable {
   
	private static final long serialVersionUID = 8508849226878140159L;
	
	private String fileName;
    private long lastKnowLength;
    @SuppressWarnings("unchecked")
	private List lines;
    private long size;
    private Timestamp lastModified;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getLastKnowLength() {
        return lastKnowLength;
    }

    public void setLastKnowLength(long lastKnowLength) {
        this.lastKnowLength = lastKnowLength;
    }

    @SuppressWarnings("unchecked")
	public List getLines() {
        return lines;
    }

    @SuppressWarnings("unchecked")
	public void setLines(List lines) {
        this.lines = lines;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }
}
