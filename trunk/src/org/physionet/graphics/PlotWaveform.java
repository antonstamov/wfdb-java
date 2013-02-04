/* ===========================================================
 * WFDB Java : Interface to WFDB Applications.
 *              
 * ===========================================================
 *
 * (C) Copyright 2012, by Ikaro Silva
 *
 * Project Info:
 *    Code: http://code.google.com/p/wfdb-java/
 *    WFDB: http://www.physionet.org/physiotools/wfdb.shtml
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *
 * Original Author:  Ikaro Silva
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * Check: http://code.google.com/p/wfdb-java/list
 */ 


/* Example on how to plot a ECG signal
 * 
 * This example requires the package jfreechar-1.0.14.jar
 * 
 * Available for download at in the download area of the wfdb-java Google project:
 * 
 * http://code.google.com/p/wfdb-java/downloads/detail?name=jfreechart-1.0.14.zip&can=2&q=
 * 
 * To install external package in Eclipse from a working "wfdb-java" project:
 * 
 * 1. Right click in the "src" folder under the wfdb-java project in Eclipse
 * 
 * 2. Select:
 * 	   Build Path -> Configure Build Path
 * 
 * 3. Go to:
 * 		"Libraries" Tab -> Add External Jars
 * 
 * select the jfreechar-1.0.14.jar and hit "Ok".
 * 
 * 4. Go to the "Order and Export" Tab, check "jfreechar-1.0.14.jar" and hit "Ok". 
 */

package org.physionet.graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.physionet.wfdb.Rdsamp;

    public class PlotWaveform extends ApplicationFrame {

        public PlotWaveform(String title, String timeLabel, 
        		String amplitudeLabel, ArrayList[] data) {
            super(title);
            JPanel chartPanel = createDemoPanel(title,timeLabel,
            									amplitudeLabel,data);
            chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
            setContentPane(chartPanel);
        }
       
      
        private static JFreeChart createChart(XYDataset dataset, String title,
        		String timeLabel, String amplitudeLabel) {
            // create the chart...
            JFreeChart chart = ChartFactory.createXYLineChart(
                title,       // chart title
                timeLabel,                      // x axis label
                amplitudeLabel,                      // y axis label
                dataset,                  // data
                PlotOrientation.VERTICAL, 
                true,                     // include legend
                true,                     // tooltips
                false                     // urls
            );

            XYPlot plot = (XYPlot) chart.getPlot();
            plot.getDomainAxis().setLowerMargin(0.0);
            plot.getDomainAxis().setUpperMargin(0.0);
            return chart;
        }
       
        public static XYDataset createDataset(ArrayList[] data) {
            //XYDataset result = DatasetUtilities.sampleFunction2D(new X2(),
            //        -4.0, 4.0, 40, "f(x)");
        	XYSeriesCollection result = new XYSeriesCollection();
        	XYSeries series = new XYSeries(1);
    		//Insert data into plotting series 
    		for(int n=0;n<data[1].size();n++){
    			series.add(Double.valueOf((String) data[0].get(n)),
    					Double.valueOf((String) data[1].get(n)));
    		}
        	result.addSeries(series);
            return result;
        }

        /**
         * Creates a panel for the demo (used by SuperDemo.java).
         *
         * @return A panel.
         */
        public static JPanel createDemoPanel(String title,String timeLabel, 
        								String amplitudeLabel,ArrayList[] data) {
            JFreeChart chart = createChart(createDataset(data),title,timeLabel,
            							   amplitudeLabel);
            return new ChartPanel(chart);
        }
       
        public void showPlot(){
    		this.pack();
    		RefineryUtilities.centerFrameOnScreen(this);
    		this.setVisible(true);
        }

    }
