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

/** 
 * @author Ikaro Silva
 *  @version 1.0
 */



package org.physionet.wfdb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wfdbexec {

	private String commandName;
	protected static String WFDB_JAVA_HOME;
	protected static String WFDB_NATIVE_BIN;
	private static String osArch;
	private static String WFDB_NATIVE_BIN_FOLDER;
	private static String osName;
	private static String fileSeparator;
	public static final String WFDB_JAVA_VERSION="Beta";
	private List<String> commandInput = new ArrayList<String>();
	protected Map<String, String> argumentLabels = new HashMap<String, String>();
	protected Map<String, String> argumentValues = new HashMap<String, String>();
	protected static Map<String,String> env;
	protected static String LIBRARY_PATH;
	protected static String arch_library_path;

	//Abstracts be implemented by inherited classes
	public String help() {
		return null;
	}

	//Public Methods
	public Wfdbexec(){
		set_environment();
	}
	protected void setExecName(String execName) {
		commandName = execName;
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

	private void gen_exec_arguments() {
		// Generates a list to be passed to the process builder that
		// will eventually execute the code
		commandInput.add(WFDB_NATIVE_BIN + commandName);
		for (String key : argumentLabels.keySet()) {
			if(argumentValues.get(key).isEmpty()){
				commandInput.add(argumentLabels.get(key));
			}else{
				if(!argumentLabels.get(key).isEmpty()){
					//Some arguments do not have a WFDB Flag
					//but still have parameters, such as record name in Wfdbdesc
					commandInput.add(argumentLabels.get(key));
				}
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

	public ArrayList<String> execToStringList() {
		gen_exec_arguments();
		ProcessBuilder launcher = new ProcessBuilder();
		launcher.redirectErrorStream(true);
		ArrayList<String> results= new ArrayList<String>();

		env = launcher.environment();
		env.put(LIBRARY_PATH,arch_library_path);
		launcher.command(getCommandInput());
		try {
			Process p = launcher.start();
			BufferedReader output = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = output.readLine()) != null)
				results.add(line);
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return results;
	}

	public double[][] execToDoubleArray() {
		gen_exec_arguments();
		ProcessBuilder launcher = new ProcessBuilder();
		launcher.redirectErrorStream(true);
		ArrayList<Double[]>  results= new ArrayList<Double[]>();
		double[][] data=null;
		env = launcher.environment();
		env.put(LIBRARY_PATH,arch_library_path);
		launcher.command(getCommandInput());

		try {
			Process p = launcher.start();
			BufferedReader output = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			String[] tmpStr=null;
			Double[] tmpArr=null;
			int colInd;
			while ((line = output.readLine()) != null){
				line=line.replaceFirst("\\s+","");
				tmpStr=line.split("\\s+");
				tmpArr=new Double[tmpStr.length];
				//loop through columns
				for(colInd=0;colInd<tmpStr.length;colInd++){
					tmpArr[colInd]= Double.valueOf(tmpStr[colInd]);
				}
				results.add(tmpArr);	
			}
			p.waitFor();	

			//Convert data to Double Array
			data=new double[results.size()][tmpStr.length];
			for(int i=0;i<results.size();i++){
				Double[] tmpData=new Double[tmpStr.length];
				tmpData=results.get(i);
				 for(int k=0;k<tmpData.length;k++){
					 data[i][k]=tmpData[k];
				 }
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return data;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList[] execTo2DString() {
		gen_exec_arguments();
		ProcessBuilder launcher = new ProcessBuilder();
		launcher.redirectErrorStream(true);

		ArrayList[] results = new ArrayList[2];
		results[0] = new ArrayList<String>();
		results[1] = new ArrayList<String>();

		env = launcher.environment();
		env.put(LIBRARY_PATH,arch_library_path);
		launcher.command(getCommandInput());
		try {
			Process p = launcher.start();
			BufferedReader output = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			String[] tmpStr;
			while ((line = output.readLine()) != null){
				tmpStr=line.split("\\s+");
				results[0].add(tmpStr[1]);
				results[1].add(tmpStr[2]);
			}
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return results;
	}

	public String execToString() {
		gen_exec_arguments();
		ProcessBuilder launcher = new ProcessBuilder();
		launcher.redirectErrorStream(true);
		String results="";

		env = launcher.environment();
		env.put(LIBRARY_PATH,arch_library_path);
		launcher.command(getCommandInput());
		try {
			Process p = launcher.start();
			BufferedReader output = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = output.readLine()) != null)
				results+=line+"\n";
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return results;
	}

	//Private Methods
	private static void set_environment() {
		osArch = System.getProperty("os.arch");
		fileSeparator=System.getProperty("file.separator");
		osName=System.getProperty("os.name");
		osName=osName.replace(" ","");
		WFDB_NATIVE_BIN_FOLDER="nativelibs";
		String jar_bin_dir;
		String path = Wfdbexec.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		URL pp = Wfdbexec.class.getProtectionDomain().getCodeSource().getLocation();

		try {
			jar_bin_dir=URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.err.println("path = \n" + path);
			jar_bin_dir="";
			e.printStackTrace();
		}



		if(jar_bin_dir.endsWith(".jar")){
			//In JAR package, remove jar directory to get root location
			String[] jar_name=jar_bin_dir.split(fileSeparator);
			String tmpStr=fileSeparator + jar_name[jar_name.length-2] 
					+fileSeparator+ jar_name[jar_name.length-1];
			WFDB_JAVA_HOME=jar_bin_dir.replace(tmpStr,"")+fileSeparator;

		} else{
			String[] jar_name=jar_bin_dir.split(fileSeparator);
			String tmpStr=fileSeparator + "build";
			WFDB_JAVA_HOME=jar_bin_dir.replace(tmpStr,"")+fileSeparator;
		}
		//Set path to executables based on system/arch
		WFDB_NATIVE_BIN= WFDB_JAVA_HOME + WFDB_NATIVE_BIN_FOLDER + fileSeparator + 
				osName.toLowerCase() + "-" + osArch.toLowerCase() 
				+ fileSeparator + "bin" + fileSeparator;
		arch_library_path= WFDB_JAVA_HOME + WFDB_NATIVE_BIN_FOLDER + fileSeparator + 
				osName.toLowerCase() + "-" + osArch.toLowerCase() 
				+ fileSeparator + "lib" + fileSeparator;
		arch_library_path= arch_library_path + ":" + WFDB_JAVA_HOME + WFDB_NATIVE_BIN_FOLDER + fileSeparator + 
				osName.toLowerCase() + "-" + osArch.toLowerCase() 
				+ fileSeparator + "lib64" + fileSeparator;	
		LIBRARY_PATH="LD_LIBRARY_PATH";

	}




} //Of class
