package org.physionet.wfdb.examples;

import java.util.List;

import org.physionet.wfdb.physiobank.PhysioNetDB;
import org.physionet.wfdb.physiobank.PhysioNetRecord;

public class PhysioNetRecordEx1 {
	//Example showing how to use PhysioNetRecord class to obtain information regarding
	//a record from the PhysioNet server
	
	public static void main(String[] args) {
	
		//Query the server and load the results into pnDB
		List<PhysioNetDB> pnDBList=PhysioNetDB.getPhysioNetDBList();
		
		//Get a list of records in the first database
		List<PhysioNetRecord> pnRecordList = pnDBList.get(0).getDBRecordList();

		//Print all the signals associated with  the first record of the first database
		pnRecordList.get(0).printRecord();

	}
	

}
