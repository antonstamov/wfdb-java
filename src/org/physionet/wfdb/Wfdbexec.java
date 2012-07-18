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
package org.physionet.wfdb;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.physionet.wfdb.rdsamp.Arguments;

public class Wfdbexec {

	private String TAG;
	protected static String WFDB_HOME;
	private static String os_arch;
	public static final String WFDB_JAVA_VERSION="Beta";
	private List<String> commandInput = new ArrayList<String>();
	protected Map<String, String> argumentLabels = new HashMap<String, String>();
	protected Map<String, String> argumentValues = new HashMap<String, String>();
	
	
	//Abstracts be implemented by inherited classes
	public String help() {
		return null;
	}

	//Public Methods
	public Wfdbexec(){
		WFDB_HOME="/afs/ecg.mit.edu/software/wfdb/amd64_linux26/current/bin/";
		set_os_arch();
	}
	public static void set_WFDB_HOME(String str){
		WFDB_HOME=str;
	}
	public static String get_WFDB_HOME(){
		return WFDB_HOME;
	}
	public static String get_os_arch() {
		return os_arch;
	}

	protected void setExecName(String execName) {
		TAG = execName;
	}
	/**
	 * @return the commandInput
	 */
	public List<String> getCommandInput() {
		return commandInput;
	}

	/**
	 * @return the argumentLabels
	 */
	public Map<String, String> getArgumentLabels() {
		return argumentLabels;
	}

	/**
	 * @return the argumentValues
	 */
	public Map<String, String> getArgumentValues() {
		return argumentValues;
	}

	public String exec(String command,List<String> inputs){

		ProcessBuilder launcher=new ProcessBuilder();
		launcher.redirectErrorStream(true);
		inputs.add(0,Wfdbexec.get_WFDB_HOME() + command);
		launcher.command(inputs);
		String results="";
		try {
			Process p =launcher.start();
			BufferedReader output = new BufferedReader(
					new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = output.readLine()) != null)
				results +=line +"\n";
			p.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;

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

	protected int get_num_arguments() {
		return argumentLabels.size();
	}
	
	public String get_command_line() {
		String commandLine = "";
		for(String argument: commandInput) {
			commandLine += argument + " ";
		}
		return commandLine;
	}
	
	public String getArgumentValue(Arguments arg) {
		return this.argumentValues.get(arg.label);
	}

	public String exec() {
		gen_exec_arguments();
		ProcessBuilder launcher = new ProcessBuilder();
		launcher.redirectErrorStream(true);
		String results = "";
	
		Map<String,String> env = launcher.environment();
		env.put("LD_LIBRARY_PATH", "/afs/ecg.mit.edu/software/wfdb/@sys/current/lib64");
		launcher.command(getCommandInput());
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

	//Private Methods
	private static void set_os_arch() {
		os_arch = System.getProperty("os.arch");
	}


}
