package org.physionet.wfdb.examples;

import java.util.HashMap;

import org.physionet.wfdb.physiobank.PhysioNetDB;

public class PhysioNetDBEx2 {

	public static void main(String[] args) {
		// Example showing how to all the records from one database at PhysioNet (output of PhysioNetDBEx1)

		//Query the server and load the results into pnDB
		HashMap<String,PhysioNetDB> pnDBMap=PhysioNetDB.getPhysioNetDBMap();
		
		//Print all records associated with the first database
		pnDBMap.get("mitdb").printDBRecordList();
		

	}
}
