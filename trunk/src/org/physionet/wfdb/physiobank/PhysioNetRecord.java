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

package org.physionet.wfdb.physiobank;

import java.util.ArrayList;

import org.physionet.wfdb.Wfdbdesc;

public class PhysioNetRecord {

	private final String name;
	private final String dbName;
	private ArrayList<String> signalStringList;
	private ArrayList<PhysioNetRecord> signalList;
	private Wfdbdesc wfdbdesc=new Wfdbdesc();

	PhysioNetRecord(String db,String recname){
		name=recname;
		dbName=db;
		signalStringList = new ArrayList<String>();
		wfdbdesc.setArgumentValue(Wfdbdesc.Arguments.recordName,name);
		initializeSignalList();
	}
	PhysioNetRecord(String db,String recname,ArrayList<String> sgList ){
		name=recname;
		dbName=db;
		signalStringList = sgList;
		wfdbdesc=new Wfdbdesc();
		wfdbdesc.setArgumentValue(Wfdbdesc.Arguments.recordName,name);
		initializeSignalList();
	}

	public String getRecordName() {
		return name;
	}

	public String getDbName() {
		return dbName;
	}

	public ArrayList<String> getWfdbdes(){
		return wfdbdesc.execToStringList();
	}


	public ArrayList<String> getSignalStringList() {
		return signalStringList;
	}

	public void printRecord(){
		System.out.println("Record: "+ name);
		System.out.println("\tDatabase name: "+ dbName);
		System.out.println("\tSignals: ");
		for(String sig: signalStringList){
			System.out.println("\t\t" + sig);
		}
	}
	private void initializeSignalList() {
		//Parse information from wfdbdesc to populate the record list
		ArrayList<String> tmpList= wfdbdesc.execToStringList();
		String startingTime=null;
		String startTime=null;
		String lengthTime=null;
		String lengthSample=null;
		String samplingFrequency=null;
		PhysioNetSignal tmpSignal=null;
		int index=-1;

		for(String i : tmpList){	
			if(i.startsWith("Starting time: ")){
				startingTime=i.replace("Starting time: ","");
			}
			else if (i.startsWith("Length: ")) {
				//String should have a format similar to :
				//Length:    30:05.556 (650000 sample intervals)
				i=i.replace("Length: ","");
				
			}else if (i.startsWith("Sampling frequency: ")) {
				samplingFrequency=i.replace("Sampling frequency: ","");
			}else if (i.startsWith("Group ")) {
				index++;
				tmpSignal=new PhysioNetSignal(index,name,dbName);
				tmpSignal.setStartTime(startTime);
				tmpSignal.setLengthTime(lengthTime);
				tmpSignal.setLengthSample(lengthSample);
				tmpSignal.setSamplingFrequency(samplingFrequency);
				//TODO: Finish with regexp parsing 			//Group 0, Signal 0:
				//tmpSignal.setGroup(group);
				
			}else if (i.startsWith("File: ")) {
				tmpSignal.setFile(i.replace("File: ",""));
			}else if (i.startsWith("Description: ")) {
				tmpSignal.setFile(i.replace("File: ",""));
			}else if (i.startsWith("Gain: ")) {
				tmpSignal.setFile(i.replace("Gain: ",""));
			}else if (i.startsWith("Initial value: ")) {
				tmpSignal.setFile(i.replace("Initial value: ",""));
			}else if (i.startsWith("Storage format: ")) {
				tmpSignal.setFile(i.replace("Storage format: ",""));
			}else if (i.startsWith("I/O:  ")) {
				tmpSignal.setFile(i.replace("I/O:  ",""));
			}else if (i.startsWith("ADC resolution:  ")) {
				tmpSignal.setFile(i.replace("ADC resolution:  ",""));
			}else if (i.startsWith("ADC zero:  ")) {
				tmpSignal.setFile(i.replace("ADC zero:  ",""));
			}else if (i.startsWith("Baseline:  ")) {
				tmpSignal.setFile(i.replace("Baseline:  ",""));
			}else if (i.startsWith("Checksum:  ")) {
				tmpSignal.setFile(i.replace("Checksum:  ",""));
			}			
		}

	}


	public static void main(String[] args) {

		// Prints information regarding all databases
		// Currently available at PhysioNet
		PhysioNetRecord re = new PhysioNetRecord("aami-ec13","aami3a");
		re.printRecord();
	}

}
