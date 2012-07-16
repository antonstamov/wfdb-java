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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class wfdbexec {
	
	private static final String TAG="wfdbexec";
	private static String WFDB_HOME;
	private static String os_arch;
	private static final String config_file="/resources/config.xml";
	
	//Abstracts be implemented by inherited classes
	public String help() {
		return null;
	}

	//Public Methods
	public wfdbexec(){
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
	//Generic Public Method for collecting output
	public String output(String tag, String str){
	  	  String results="";
	  	  String tmp=null;
	  	  Runtime runtime = Runtime.getRuntime();
	  	  String exec_str=wfdbexec.get_WFDB_HOME() + tag + " " +str;
	  	  
	        try {
	      	 Process myProcess = runtime.exec(exec_str);
	      	 InputStream instream = myProcess.getInputStream();
	           BufferedInputStream bufstream = new BufferedInputStream(instream);
	           InputStreamReader inread = new InputStreamReader(bufstream);
	           BufferedReader bufferedreader = new BufferedReader(inread);

	           //Read output
	           while ((tmp = bufferedreader.readLine()) != null) {
	               results +=tmp+"\n";
	            }
	            //Error
	           try {
	                if (myProcess.waitFor() != 0) {
	              	  System.err.println("Error executing: ");
	              	  System.err.println(exec_str);
	                    System.err.println("Error value: " + myProcess.exitValue());
	                }
	            } catch (InterruptedException e) {
	                System.err.println(e);
	            } finally {
	                // close streams
	                bufferedreader.close();
	                inread.close();
	                bufstream.close();
	                instream.close();
	            }
	        } catch (IOException e) {
	            System.err.println(e.getMessage());
	        }
	        return results;
	   }
	
	   public static void main(String[] args) {
		   wfdbexec  lxp = new wfdbexec ();
	        try {
	            Properties properties = lxp.readProperties();
	            /*
	             * Display all properties information
	             */
	            properties.list(System.out);
	 
	            /*
	             * Read the value of data.folder and jdbc.url configuration
	            
	            String dataFolder = properties.getProperty("data.folder");
	            System.out.println("dataFolder = " + dataFolder);
	            String jdbcUrl = properties.getProperty("jdbc.url");
	            System.out.println("jdbcUrl = " + jdbcUrl);
	             */
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
	    public Properties readProperties() throws Exception {
	        Properties properties = new Properties();
	        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + config_file);
	        properties.loadFromXML(fis);
	        return properties;
	    }
	
	//Private Methods
	private static void set_os_arch() {
		os_arch = System.getProperty("os.arch");
	}
	
	
}
