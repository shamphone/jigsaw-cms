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

package org.jstripe.tomcat.probe.beans.stats.providers;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jfree.data.xy.DefaultTableXYDataset;
import org.jstripe.tomcat.probe.model.stats.StatsCollection;
import org.springframework.web.bind.ServletRequestUtils;

@SuppressWarnings("deprecation")
public class StandardSeriesProvider extends AbstractSeriesProvider {

    private List<Object> statNames = new ArrayList<Object>(2);

    public List<Object> getStatNames() {
        return statNames;
    }

    public void setStatNames(List<Object> statNames) {
        this.statNames = statNames;
    }

    public void populate(DefaultTableXYDataset dataset, StatsCollection statsCollection, HttpServletRequest request) {
        String seriesParam = ServletRequestUtils.getStringParameter(request, "sp", null);
        for (int i = 0; i < statNames.size(); i++) {
            String statName = (String) statNames.get(i);
            if (seriesParam != null) {
                statName = MessageFormat.format(statName, new Object[]{seriesParam});
            }
            List<?> l = statsCollection.getStats(statName);
            if (l != null)
                dataset.addSeries(
                        toSeries(ServletRequestUtils.getStringParameter(request, "s" + (i + 1) + "l", "series" + i), l)
                );
        }
    }
}
