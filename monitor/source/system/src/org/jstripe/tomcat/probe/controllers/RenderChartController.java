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

package org.jstripe.tomcat.probe.controllers;

import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.ServletRequestUtils;
import org.jstripe.tomcat.probe.Utils;
import org.jstripe.tomcat.probe.beans.stats.providers.SeriesProvider;
import org.jstripe.tomcat.probe.model.stats.StatsCollection;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.ui.RectangleInsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;

/**
 * Plots data from "statsCollection" bean. The data is converted to XYSeries using SeriesProvider, name of which
 * would be passed as a request parameter. The servlet can only plot up to two series. It is customizable using these
 * request parameters:
 * <p/>
 * <ul>
 * <li>s1c - Series #1 main color</li>
 * <li>s1o - Series #1 outline color</li>
 * <li>s2c - Series #2 main color</li>
 * <li>s2o - Series #2 outline color</li>
 * <li>bc  - Chart background color</li>
 * <li>gc  - Chart grid lines color</li>
 * <li>xl  - X axis label</li>
 * <li>yl  - Y axis label</li>
 * <li>xz  - image width</li>
 * <li>yx  - image height</li>
 * <li>l   - show legend (boolean: true|false)</li>
 * <li>p   - name of series provider bean</li>
 * <p/>
 * Author: Vlad Ilyushchenko
 */
@SuppressWarnings("deprecation")
public class RenderChartController extends AbstractController {

    private StatsCollection statsCollection;

    public StatsCollection getStatsCollection() {
        return statsCollection;
    }

    public void setStatsCollection(StatsCollection statsCollection) {
        this.statsCollection = statsCollection;
    }

    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //
        // get Series1 Color from the request
        //
        int series1Color = Utils.toIntHex(request.getParameter("s1c"), 0x9bd2fb);
        //
        // get Series1 Outline Color from the request
        //
        int series1OutlineColor = Utils.toIntHex(request.getParameter("s1o"), 0x0665aa);
        //
        // get Series2 Color
        //
        int series2Color = Utils.toIntHex(request.getParameter("s2c"), 0xFF0606);
        //
        // get Series2 Outline Color
        //
        int series2OutlineColor = Utils.toIntHex(request.getParameter("s2o"), 0x9d0000);
        //
        // background color
        //
        int backgroundColor = Utils.toIntHex(request.getParameter("bc"), 0xFFFFFF);
        //
        // grid color
        //
        int gridColor = Utils.toIntHex(request.getParameter("gc"), 0);
        //
        // X axis title
        //
        String xLabel = ServletRequestUtils.getStringParameter(request, "xl", "");
        //
        // Y axis title
        //
        String yLabel = ServletRequestUtils.getStringParameter(request, "yl", "");
        //
        // image width
        //
        int width = ServletRequestUtils.getIntParameter(request, "xz", 800);
        //
        // image height
        //
        int height = ServletRequestUtils.getIntParameter(request, "yz", 400);
        //
        // show legend?
        //
        boolean showLegend = ServletRequestUtils.getBooleanParameter(request, "l", true);
        //
        // Series provider
        //
        String provider = ServletRequestUtils.getStringParameter(request, "p", null);
        //
        // Chart type
        //
        String chartType = ServletRequestUtils.getStringParameter(request, "ct", "area");


        DefaultTableXYDataset ds = new DefaultTableXYDataset();

        if (provider != null) {
            Object o = getApplicationContext().getBean(provider);
            if (o instanceof SeriesProvider) {
                ((SeriesProvider) o).populate(ds, statsCollection, request);
            } else {
                logger.error("SeriesProvider \"" + provider + "\" does not implement " + SeriesProvider.class);
            }

        }

        //
        // Build series data from the give statistic
        //

        JFreeChart chart = null;
        if ("area".equals(chartType)) {
            chart = ChartFactory.createXYAreaChart("", xLabel, yLabel, ds, PlotOrientation.VERTICAL,
                    showLegend, false, false);
        } else if ("stacked".equals(chartType)) {
            chart = ChartFactory.createStackedXYAreaChart("", xLabel, yLabel, ds, PlotOrientation.VERTICAL, showLegend,
                    false, false);
        }

        if (chart != null) {
            chart.setAntiAlias(true);
            chart.setBackgroundPaint(new Color(backgroundColor));
            ((XYAreaRenderer) chart.getXYPlot().getRenderer()).setOutline(true);
            chart.getXYPlot().getRenderer().setSeriesPaint(0, new Color(series1Color));
            chart.getXYPlot().getRenderer().setSeriesOutlinePaint(0, new Color(series1OutlineColor));
            chart.getXYPlot().getRenderer().setSeriesPaint(1, new Color(series2Color));
            chart.getXYPlot().getRenderer().setSeriesOutlinePaint(1, new Color(series2OutlineColor));
            chart.getXYPlot().setDomainGridlinePaint(new Color(gridColor));
            chart.getXYPlot().setRangeGridlinePaint(new Color(gridColor));
            chart.getXYPlot().setDomainAxis(0, new DateAxis());
            chart.getXYPlot().setDomainAxis(1, new DateAxis());
            chart.getXYPlot().setInsets(new RectangleInsets(-15, 0, 0, 10));

            response.setHeader("Content-type", "image/png");
            response.getOutputStream().write(ChartUtilities.encodeAsPNG(chart.createBufferedImage(width, height)));
        }

        return null;
    }
}
