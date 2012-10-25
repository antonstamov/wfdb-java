package org.physionet.wfdb.examples;
import java.util.List;

import org.physionet.wfdb.physiobank.PhysioNetDB;

public class PhysioNetDBEx1 {

	public static void main(String[] args) {
		
		// Example showing all available databases in PhysioNet
		//Query the server and load the results into pnDB
		PhysioNetDB.printDBList();
		
	}
}
