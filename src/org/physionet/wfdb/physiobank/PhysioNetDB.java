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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PhysioNetDB {

	private final String name;
	private final String info;
	private final URL url;
	private static final String DB_URL="http://physionet.org/physiobank/database/pbi/";
	private static final String DB_LIST="http://physionet.org/physiobank/database/DBS";
	
	PhysioNetDB(String Name,String Info){
		name=Name;
		info=Info;
		url=setDBURL();
	}

	public String getname() {
		return name;
	}
	public String getinfo() {
		return info;
	}
	public URL getURL() {
		return url;
	}
	private URL setDBURL() {
		try {
			 return new URL(DB_URL + name.replaceAll("/","_"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<PhysioNetDB> getPhysioNetDBList(){
		String inputLine;
		BufferedReader in = null;
		List<PhysioNetDB> physionetDBList = new ArrayList<PhysioNetDB>();
		try {
			URL oracle = new URL(DB_LIST);
			in = new BufferedReader(
					new InputStreamReader(oracle.openStream()));
				String[] tmpStr;
				String tmpname;
				String tmpInfo;
			while ((inputLine = in.readLine()) != null){
				tmpStr=inputLine.split("\\t");
				tmpname=tmpStr[0];
				tmpInfo=(inputLine.replaceFirst(tmpname,"")).replaceAll("\\t","");
				physionetDBList.add(new PhysioNetDB(tmpname,tmpInfo));
			}			
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return physionetDBList;
	}
	
	public void printDB(){
		System.out.println(name);
		System.out.println("\tDescription: "+ info);
		System.out.println("\tURL: "+ url);
	}
	
	public List<PhysioNetRecord> getPhysioNetRecordList(PhysioNetDB db){
		String inputLine;
		BufferedReader in = null;
		String[] tmpStr;
		String recname;
		String recinfo;
		ArrayList<PhysioNetRecord> dbRecordList = new ArrayList<PhysioNetRecord>();
		try {
			in = new BufferedReader(
					new InputStreamReader(db.url.openStream()));
			while ((inputLine = in.readLine()) != null){
				tmpStr=inputLine.split("\\t");			
				recname=tmpStr[0];
				recinfo=(inputLine.replaceFirst(recname,"")).replaceAll("\\t","");
				dbRecordList.add(new PhysioNetRecord(db.name,recname,recinfo));
			}			
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return dbRecordList;

	}
	
	public static void main(String[] args) {
		
		//Prints information regarding all databases
		//Currently available at PhysioNet
		List<PhysioNetDB> pnDBList = PhysioNetDB.getPhysioNetDBList();
		for(PhysioNetDB db : pnDBList){
			db.printDB();
		}
		

	}
	

	
}
