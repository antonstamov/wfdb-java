/* ===========================================================
 * WFDB Java : Interface to WFDB Applications.
 *
 * ===========================================================
 *
 * (C) Copyright 2012, by Daniel Scott
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
 * Original Author:  Daniel Scott 
 * Contributor(s):   Ikaro Silva;
 *
 * Changes
 * -------
 * Check: http://code.google.com/p/wfdb-java/list
 */

package org.physionet.wfdb;

public class Rdann extends Wfdbexec {
	private static final String TAG = "rdann";
	public static final String URL="http://www.physionet.org/physiotools/wag/rdann-1.htm";
	public static enum Arguments {
		//Define input arguments syntax is:
		// argumentName(number of parameters,is optional,WDFD command string)
		recordName(1, false, "-r"),
		startTime(1, true, "-f"),
		stopTime(1, true, "-t"),
		annotator(1, false, "-a"),
		channel(1, true, "-c"),
		printNum(1, true, "-n"),
		printType(1, true, "-p"),
		printSubType(1, true, "-s"),
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

	public Rdann() {
		setExecName(TAG);
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