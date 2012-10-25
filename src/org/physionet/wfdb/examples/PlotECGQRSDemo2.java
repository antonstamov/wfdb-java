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
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.physionet.wfdb.Rdann;
import org.physionet.wfdb.Rdsamp;
import org.physionet.wfdb.ecg.Wqrs;

    public class PlotECGQRSDemo2 extends ApplicationFrame {

        public PlotECGQRSDemo2(String title) {
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
            XYItemRenderer r = plot.getRenderer();
            if (r instanceof XYLineAndShapeRenderer) {
                XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
                renderer.setSeriesLinesVisible(1,false);
                renderer.setSeriesShapesVisible(1,true);
                renderer.setSeriesShapesFilled(1,true);
            }
            return chart;
        }
       
        public static XYDataset createDataset() {
        	XYSeriesCollection result = new XYSeriesCollection();
        	XYSeries ecgSignal = new XYSeries(1);
        	XYSeries ecgAnnotation = new XYSeries(2);
        	String recordName="mitdb/100";
        	int N =5000;
        	Double mxEcg=(double) 0;
        	        	
    		//Get ECG data from WFDB in number of samples
    		Rdsamp rdsampexec = new Rdsamp();
    		rdsampexec.setArgumentValue(Rdsamp.Arguments.stopTime, "s"+N);
    		
    		//Print time in second and values in high precision
    		rdsampexec.setArgumentValue(Rdsamp.PrintTimeFormatLabel.P);
    		rdsampexec.setArgumentValue(Rdsamp.Arguments.recordName,recordName);
    		ArrayList[] results= rdsampexec.execTo2DString();
    		
    		//Insert data into plotting series 
    		Double ecgSamp;
    		for(int n=0;n<results[1].size();n++){
    			ecgSamp=Double.valueOf((String) results[1].get(n));
    			ecgSignal.add(n,ecgSamp);
    			mxEcg= (mxEcg > ecgSamp) ? mxEcg : ecgSamp; 
    		}
        	result.addSeries(ecgSignal);
            
        	
        	//Get the QRS annotations
        	Wqrs wqrsExec = new Wqrs();
        	wqrsExec.setArgumentValue(Wqrs.Arguments.recordName,recordName);
        	wqrsExec.setArgumentValue(Wqrs.Arguments.stopTime,"s"+N);
        	wqrsExec.execToString();
        	
        	//The annotation file will be stored at the current directory (./mitdb/100.wqrs)
        	
        	//Use RDANN to read the annotation file and store values in memory
        	Rdann rdannExec = new Rdann();
        	rdannExec.setArgumentValue(Rdann.Arguments.annotator,"wqrs");
        	rdannExec.setArgumentValue(Rdann.Arguments.recordName,recordName);
        	ArrayList<String> annotations=rdannExec.execToStringList();
        	String[] tmpArr;
        	for (String temp : annotations) {
        		tmpArr=temp.split("\\s+");
        		ecgAnnotation.add(Double.valueOf(tmpArr[2]), mxEcg);
    		}
            
        	result.addSeries(ecgAnnotation);
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
            PlotECGQRSDemo2 demo = new PlotECGQRSDemo2(
                    "Plot ECG Demo1");
            demo.pack();
            RefineryUtilities.centerFrameOnScreen(demo);
            demo.setVisible(true);
        }

    }
