/**
 * Licensed under the GPL License. You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://probe.jstripe.com/d/license.shtml
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */

package org.jstripe.tomcat.probe.model.sql;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * A class to store data source test tool related data in a session attribute
 * <p/>
 * Author: Andy Shapoval
 */
public class DataSourceTestInfo implements Serializable {
   
	private static final long serialVersionUID = -6110043194515393981L;

	public static final String DS_TEST_SESS_ATTR = "dataSourceTestData";

    @SuppressWarnings("unchecked")
	List results = null;
    LinkedList<String> queryHistory = new LinkedList<String>();
    int maxRows = 0;
    int rowsPerPage = 0;
    int historySize = 0;

    public DataSourceTestInfo() {
    }

    public void addQueryToHistory(String sql) {
        queryHistory.remove(sql);
        queryHistory.addFirst(sql);

        while(historySize >= 0 && queryHistory.size() > historySize) {
            queryHistory.removeLast();
        }
    }

    @SuppressWarnings("unchecked")
	public List getResults() {
        return results;
    }

    @SuppressWarnings("unchecked")
	public void setResults(List results) {
        this.results = results;
    }

    public List<String> getQueryHistory() {
        return queryHistory;
    }

    public int getMaxRows() {
        return maxRows;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public int getRowsPerPage() {
        return rowsPerPage;
    }

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public int getHistorySize() {
        return historySize;
    }

    public void setHistorySize(int historySize) {
        this.historySize = historySize;
    }

}
