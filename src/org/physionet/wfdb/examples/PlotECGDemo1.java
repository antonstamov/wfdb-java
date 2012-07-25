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

package org.physionet.wfdb.examples;
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

    public class PlotECGDemo1 extends ApplicationFrame {

        public PlotECGDemo1(String title) {
            super(title);
            JPanel chartPanel = createDemoPanel();
            chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
            setContentPane(chartPanel);
        }
       
      
        private static JFreeChart createChart(XYDataset dataset) {
            // create the chart...
            JFreeChart chart = ChartFactory.createXYLineChart(
                "Plot ECG Demo1 ",       // chart title
                "Time (seconds)",                      // x axis label
                "mV",                      // y axis label
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
       
        public static XYDataset createDataset() {
            //XYDataset result = DatasetUtilities.sampleFunction2D(new X2(),
            //        -4.0, 4.0, 40, "f(x)");
        	XYSeriesCollection result = new XYSeriesCollection();
        	XYSeries series = new XYSeries(1);
        	int N =5000;

    		//Get ECG data from WFDB in number of samples
    		Rdsamp rdsampexec = new Rdsamp();
    		rdsampexec.setArgumentValue(Rdsamp.Arguments.stopTime, "s"+N);
    		//Print time in second and values in high precision
    		rdsampexec.setArgumentValue(Rdsamp.PrintTimeFormatLabel.P);
    		rdsampexec.setArgumentValue(Rdsamp.Arguments.recordName, "mitdb/100");
    		//List<String> results= new ArrayList<String>(); 
    		ArrayList[] results= rdsampexec.execTo2DString();
    		
    		//Insert data into plotting series 
    		for(int n=0;n<results[1].size();n++){
    			series.add(Double.valueOf((String) results[0].get(n)),
    					Double.valueOf((String) results[1].get(n)));
    		}
        	result.addSeries(series);
            return result;
        }

        /**
         * Creates a panel for the demo (used by SuperDemo.java).
         *
         * @return A panel.
         */
        public static JPanel createDemoPanel() {
            JFreeChart chart = createChart(createDataset());
            return new ChartPanel(chart);
        }
       
              
        /**
         * Starting point for the demonstration application.
         *
         * @param args  ignored.
         */
        public static void main(String[] args) {
            PlotECGDemo1 demo = new PlotECGDemo1(
                    "Plot ECG Demo1");
            demo.pack();
            RefineryUtilities.centerFrameOnScreen(demo);
            demo.setVisible(true);
        }

    }
