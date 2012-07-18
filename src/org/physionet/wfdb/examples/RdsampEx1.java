package org.physionet.wfdb.examples;
import org.physionet.wfdb.Rdsamp;

public class RdsampEx1 {

	public static void main(String[] args) {
		// Simple test of for the class Reads first 10 seconds of ECG
		Rdsamp rdsampexec = new Rdsamp();
		rdsampexec.setArgumentValue(Rdsamp.Arguments.stopTime, "10s");
		rdsampexec.setArgumentValue(Rdsamp.PrintTimeFormatLabel.p);
		rdsampexec.setArgumentValue(Rdsamp.Arguments.recordName, "mitdb/100");
		System.out.println(rdsampexec.exec());
	}
	
}
