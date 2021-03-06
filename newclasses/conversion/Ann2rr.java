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
package org.physionet.wfdb.conversion;

import org.physionet.wfdb.Wfdbexec;

/**
 * @author Ikaro Silva
 * 
 */
public class Ann2rr extends Wfdbexec {

	private static final String TAG = "ann2rr";
	public static final String URL="http://www.physionet.org/physiotools/wag/ann2rr-1.htm";
	public static enum Arguments {
		//Define input arguments syntax is:
		// argumentName(number of parameters,is optional,WDFD command string)
		printAllIntervals(0,true, "-A"),
		printValidIntervals(0,true, "-c"),
		startTime(1,true, "-f"),
		printTimeFormat(1,true, "-i"),
		printEndAnnotationType(1,true, "-p"),
		printBeginingAnnotationType(1,true, "-P"),
		stopTime(1,true, "-t"),
		printFinalTimesFormat(1,true, "-v"),
		printInitialTimesFormat(1,true, "-V"),
		printFinalAnnotations(0,true,"-w"),
		printInitialAnnotations(0,true,"-W");
		public int parameters;
		public boolean optional;
		public String label;
		Arguments(int parameters, boolean optional, String label) {
			this.parameters = parameters;
			this.optional = optional;
			this.label = label;
		}
	}

	
	public Ann2rr() {
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