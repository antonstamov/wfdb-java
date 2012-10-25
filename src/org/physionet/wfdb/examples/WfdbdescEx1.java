package org.physionet.wfdb.examples;

import org.physionet.wfdb.Wfdbdesc;

public class WfdbdescEx1 {
	public static void main(String[] args) {
		// Simple example showing how to get the description for of a WFDB Record
		Wfdbdesc wfdbdesc= new Wfdbdesc();
		wfdbdesc.setArgumentValue(Wfdbdesc.Arguments.recordName, "mitdb/100");
		System.out.println(wfdbdesc.execToString());
	}
}
