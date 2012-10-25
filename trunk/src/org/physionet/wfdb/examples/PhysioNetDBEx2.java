package org.physionet.wfdb.examples;

import java.util.List;

import org.physionet.wfdb.physiobank.PhysioNetDB;

public class PhysioNetDBEx2 {

	public static void main(String[] args) {
		// Example showing how to all the records from one database at PhysioNet (output of PhysioNetDBEx1)

		//Query the server and load the results into pnDB
		List<PhysioNetDB> pnDBList=PhysioNetDB.getPhysioNetDBList();
		
		//Print all records associated with the first database
		pnDBList.get(0).printDBRecordList();
		

	}
}
