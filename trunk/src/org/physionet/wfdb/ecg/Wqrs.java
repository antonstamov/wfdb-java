/* ===========================================================
 * WFDB Java : Interface to WFDB Applications.
 *
 * ===========================================================
 *
 * (C) Copyright 2012, by Ikaro Silva and Daniel Scott
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
 * Original Author:  Ikaro Silva, Daniel Scott 
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * Check: http://code.google.com/p/wfdb-java/list
 */
package org.physionet.wfdb.ecg;

/**
 * @author Ikaro Silva
 * 
 */
public class Wqrs extends Wfdbexec {

	private static final String TAG = "wqrs";
	public static final String URL="http://www.physionet.org/physiotools/wag/wqrs-1.htm";
	public static enum Arguments {
		//Define input arguments syntax is:
		// argumentName(number of parameters,is optional,WDFD command string)
		recordName(1,false, "-r"),
		dumpSamples(1, true, "-d"),
		startTime(1, true, "-f"),
		stopTime(1, true, "-t"),
		highResolution(0, true, "-H"),
		findJPoints(1, true, "-j"),
		thresholdInmV(1,true,"-m"),
		powerLineFrequencyHz(1,true,"-p"),
		resample(0,true,"-R"),
		signal(1,true, "-s"),
		verbose(0, true, "-v");
		public int parameters;
		public boolean optional;
		public String label;
		Arguments(int parameters, boolean optional, String label) {
			this.parameters = parameters;
			this.optional = optional;
			this.label = label;
		}
	}

	
	public Wqrs() {
		setExecName(TAG);
	}
		
	public String getArgumentValue(Arguments arg) {
		return this.argumentValues.get(arg.label);
	}

	public void setArgumentValue(Arguments arg, String value) {
		if (arg.parameters > 0) {
			this.argumentValues.put(arg.name(), value);
		} else {
			this.argumentValues.put(arg.name(), "");
		}
		this.argumentLabels.put(arg.name(), arg.label);
	}

}