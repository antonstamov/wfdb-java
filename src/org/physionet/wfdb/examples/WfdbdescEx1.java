package org.physionet.wfdb.examples;

import org.physionet.wfdb.Wfdbdesc;

public class WfdbdescEx1 {
	public static void main(String[] args) {
		// Simple test of for the class Reads first 10 seconds of ECG
		Wfdbdesc wfdbdesc= new Wfdbdesc();
		wfdbdesc.setArgumentValue(Wfdbdesc.Arguments.recordName, "mitdb/100");
		System.out.println(wfdbdesc.exec());
	}
}
