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

public class PhysioNetRecord {

	private final String name;
	private final String dbName;
	private ArrayList<String> signalList;
		
	PhysioNetRecord(String db,String recname){
		name=recname;
		dbName=db;
		signalList = new ArrayList<String>();
	}
	PhysioNetRecord(String db,String recname,ArrayList<String> sgList ){
		name=recname;
		dbName=db;
		signalList = sgList;
	}
	
	public String getRecordName() {
		return name;
	}

	public String getDbName() {
		return dbName;
	}
	
	public ArrayList<String> getSignalList() {
		return signalList;
	}

	public void printRecord(){
		System.out.println("Record: "+ name);
		System.out.println("\tDatabase name: "+ dbName);
		System.out.println("\tSignals: ");
		for(String sig: signalList){
			System.out.println("\t\t" + sig);
		}
	}
	
	
	public static void main(String[] args) {
		
		// Prints information regarding all databases
		// Currently available at PhysioNet
		System.out.println("Started");
		PhysioNetRecord re = new PhysioNetRecord("aami-ec13","aami3a");
		re.printRecord();
	}

}
