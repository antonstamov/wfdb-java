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
import org.physionet.graphics.PlotWaveform;
import org.physionet.wfdb.Rdsamp;

public class PlotECGDemo1 {

	public static void main(String[] args) {

		int N =5000;

		//Get ECG data from WFDB in number of samples
		Rdsamp rdsampexec = new Rdsamp();
		rdsampexec.setArgumentValue(Rdsamp.Arguments.stopTime, "s"+N);
		//Print time in second and values in high precision
		rdsampexec.setArgumentValue(Rdsamp.PrintTimeFormatLabel.P);
		rdsampexec.setArgumentValue(Rdsamp.Arguments.recordName, "mitdb/100");
		ArrayList<String>[] data= rdsampexec.execTo2DString();
		PlotWaveform demo = new PlotWaveform(
				"ECG","Time (ms)","Amplitude (mV)",data);
		demo.showPlot();
	}



}
