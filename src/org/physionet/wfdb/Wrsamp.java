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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ikaro Silva
 * 
 */
public class Wrsamp extends wfdbexec {

	private static final String TAG = "wrsamp";
	private Map<String, String> argumentLabels = new HashMap<String, String>();
	private Map<String, String> argumentValues = new HashMap<String, String>();
	private List<String> commandInput = new ArrayList<String>();

	public static enum Arguments {
		//Define input arguments syntax is:
		// argumentName(number of parameters,is optional,WDFD command string)
		checkrows(0,true, "-c"),
		dither(0, true, "-d"),
		startLine(1,true, "-f"),
		samplingFrequency(1,true, "-F"),
		gain(1, true, "-G"),
		file(1, true,"-i"),
		numberofCharacterperLine(1,true,"-l"),
		outputFile(1, true, "-o"),
		signal(1, true, "-S"),
		format(1,true, "-O"),
		lineSeparator(1,true, "-r"),
		fieldSeparator(1,true, "-s"),
		stopLine(1,true, "-t"),
		mulitplyRows(1,true, "-x"),
		dontCopyColumn0(0,true, "-z");
		public int parameters;
		public boolean optional;
		public String label;
		Arguments(int parameters, boolean optional, String label) {
			this.parameters = parameters;
			this.optional = optional;
			this.label = label;
		}
	}

	private void gen_exec_arguments() {
		// Generates a list to be passed to the process builder that
		// will eventually execute the code
		commandInput.add(WFDB_HOME + TAG);
		for (String key : argumentLabels.keySet()) {
			if(argumentValues.get(key).isEmpty()){
				commandInput.add(argumentLabels.get(key));
			}else{
				commandInput.add(argumentLabels.get(key));
				commandInput.add(argumentValues.get(key));
			}
		}
	}

	public String exec() {
		gen_exec_arguments();
		ProcessBuilder launcher = new ProcessBuilder();
		launcher.redirectErrorStream(true);
		String results = "";
		launcher.command(commandInput);
		try {
			Process p = launcher.start();
			BufferedReader output = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = output.readLine()) != null)
				results += line + "\n";
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return results;
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