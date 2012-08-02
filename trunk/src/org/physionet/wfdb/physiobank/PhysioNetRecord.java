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

public class PhysioNetRecord {

	private final String name;
	private final String dbName;
	private final String info;
		
	PhysioNetRecord(String db,String recname,String recinfo){
		name=recname;
		dbName=db;
		info=recinfo;
	}
	
	public String getRecordName() {
		return name;
	}

	public String getDbName() {
		return dbName;
	}

	public String getRecordInfo() {
		return info;
	}
	
	public void printRecord(){
		System.out.println(name);
		System.out.println("\tDatabase name: "+ dbName);
		System.out.println("\tInfo: "+ dbName);
	}
	
}
