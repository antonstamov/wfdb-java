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
package org.physionet.wfdb;

/**
 * @author Ikaro Silva
 * 
 */
public class Rdsamp extends Wfdbexec {

	private static final String TAG = "rdsamp";

	public static enum Arguments {
		//Define input arguments syntax is:
		// argumentName(number of parameters,is optional,WDFD command string)
		recordName(1, false, "-r"),
		startTime(1, true, "-f"),
		stopTime(1, true, "-t"),
		highResolution(0, true, "-H"),
		interval(1, true, "-l"),
		printTextFormat(0, true, null),
		printTimeFormat(0, true, null),
		signalList(1, true, "-s"),
		signal(1, true, "-S"),
		columnHeadings(0, true, "-v");
		public int parameters;
		public boolean optional;
		public String label;
		Arguments(int parameters, boolean optional, String label) {
			this.parameters = parameters;
			this.optional = optional;
			this.label = label;
		}
	}

	public static enum PrintTimeFormatLabel {
		p("-p"), 
		P("-P"), 
		pd("-pd"), 
		Pd("-Pd"), 
		pe("-pe"), 
		Pe("-Pe"), 
		ph("-ph"), 
		Ph("-Ph"), 
		pm("-pm"), 
		Pm("-Pm"), 
		ps("-ps"), 
		Ps("-Ps");
		public String label;
		PrintTimeFormatLabel(String label) {
			this.label = label;
		}
	}

	public static enum PrintTextFormatLabel {
		c("-c"), 
		X("-X");
		public String label;
		PrintTextFormatLabel(String label) {
			this.label = label;
		}
	}

	public Rdsamp() {
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

	public void setArgumentValue(PrintTimeFormatLabel ptf) {
		// This parameter is a flag from a known set
		this.argumentValues.put(ptf.name(), "");
		this.argumentLabels.put(ptf.name(), ptf.label);
	}

	public void setArgumentValue(PrintTextFormatLabel ptf) {
		// This parameter is a flag from a known set
		this.argumentValues.put("printTextFormat", "");
		this.argumentLabels.put("printTextFormat", ptf.label);
	}

}